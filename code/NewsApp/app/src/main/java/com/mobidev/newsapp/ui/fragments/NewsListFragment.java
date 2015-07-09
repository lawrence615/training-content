package com.mobidev.newsapp.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mobidev.newsapp.adapter.NewsCursorAdapter;
import com.mobidev.newsapp.ui.widgets.StaggeredGridView;
import com.mobidev.newsapp.utils.ParserUtils;
import com.mobidev.newsapp.R;
import com.mobidev.newsapp.VolleySingleton;
import com.mobidev.newsapp.database.AppContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lawrence on 4/24/15.
 */
public class NewsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_NEWS_TYPE = "news_type_id";
    private static final String LOADER_ARG_TAG = "news_type";
    private static final String requestUrl = "http://testing.mlab-training.devs.mobi/php_news_api/news.php";


    private String tag_json_obj = "request_news_list";

    private NewsCursorAdapter mAdapter;

    private int news_type_id;

    private ProgressDialog mProgress;
    boolean mListShown;
    private View mProgressContainer;
    private View mListContainer;
    private SwipeRefreshLayout swipeContainer;
    private ListView lv;
    private StaggeredGridView mGridView;

    private OnNewsItemSelectedListener itemSelectedListener;

    private ContentResolver cr = null;


    public static NewsListFragment newInstance(int news_type_id) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_NEWS_TYPE, news_type_id);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_news_headlines,
                container, false);

        swipeContainer = (SwipeRefreshLayout) rootView
                .findViewById(R.id.swipeProjectsContainer);
        lv = (ListView) rootView.findViewById(R.id.list);
        mGridView = (StaggeredGridView) rootView.findViewById(R.id.grid_view);


        if (mGridView == null) {
            lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view,
                                                 int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    int topRowVerticalPosition = (lv == null || lv
                            .getChildCount() == 0) ? 0 : lv.getChildAt(0)
                            .getTop();
                    swipeContainer.setEnabled(topRowVerticalPosition >= 0);
                }
            });
//            int INTERNAL_EMPTY_ID = 0x00ff0001;
//            (rootView.findViewById(android.R.id.empty))
//                    .setId(INTERNAL_EMPTY_ID);
            mAdapter = new NewsCursorAdapter(
                    getActivity().getApplicationContext(), null, 0);
            lv.setAdapter(mAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (itemSelectedListener != null) {
                        itemSelectedListener.onNewsItemSelected(position, news_type_id);
                    }
                }
            });
        } else {

            mAdapter = new NewsCursorAdapter(
                    getActivity().getApplicationContext(), null, 0);
            mGridView.setAdapter(mAdapter);
            mGridView.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {
                @Override
                public void onItemClick(StaggeredGridView parent, View view, int position, long id) {
                    if (itemSelectedListener != null) {
                        itemSelectedListener.onNewsItemSelected(position, news_type_id);
                    }
                }
            });
        }


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                fetchNews(news_type_id);
            }
        });
        swipeContainer.setColorSchemeResources(R.color.google_green,
                R.color.google_blue, R.color.google_red,
                R.color.google_orange);


        mListContainer = rootView.findViewById(R.id.listContainer);
        mProgressContainer = rootView.findViewById(R.id.progressContainer);
        mListShown = true;


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        news_type_id = getArguments().getInt(ARG_NEWS_TYPE);


        setListShown(false);

        Bundle args = new Bundle();
        args.putInt(LOADER_ARG_TAG, news_type_id);

        getLoaderManager().initLoader(NewsQuery._TOKEN, args, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        super.onAttach(activity);
        try {
            itemSelectedListener = (OnNewsItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTutSelectedListener");
        }

        cr = getActivity().getContentResolver();

    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        if (itemSelectedListener != null) {
//            itemSelectedListener.onNewsItemSelected(position, news_type_id);
//        }
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int token, Bundle bundle) {
        Loader<Cursor> loader = null;

        if (token == NewsQuery._TOKEN) {

            int newstype = bundle.getInt(LOADER_ARG_TAG);

            ArrayList<String> selectionArgs = new ArrayList<String>();
            ArrayList<String> selectionClauses = new ArrayList<String>();

            if (newstype > 0) {
                selectionClauses.add(AppContract.News.NEWS_TYPE_ID + "=?");
                selectionArgs.add(String.format(Locale.US, "%d", newstype));
            }

            String selection = selectionClauses.isEmpty() ? null : ParserUtils
                    .joinStrings(" AND ", selectionClauses, null);
            String[] args = selectionArgs.isEmpty() ? null : selectionArgs
                    .toArray(new String[0]);


            return new CursorLoader(
                    getActivity().getApplicationContext(),
                    AppContract.News.CONTENT_URI, NewsQuery.PROJECTION,
                    selection, args, AppContract.News.DEFAULT_SORT);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int token = loader.getId();

        if (token == NewsQuery._TOKEN) {
            if (data.getCount() == 0) {
                fetchNews(news_type_id);
            }
            mAdapter.swapCursor(data);
        }

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    private void fetchNews(int universityId) {
        Uri.Builder builder = Uri.parse(requestUrl).buildUpon();
        builder.appendQueryParameter("news_type_id", Integer.toString(universityId));

        // http://testing.mlab-training.devs.mobi/php_list_db_example/universityinfo.php?university_id=2
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, builder.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {//checking if response is null
                    try {
                        JSONArray unisArray = response.getJSONArray("news");
                        System.err.println(">>>> " + unisArray);
                        for (int i = 0; i < unisArray.length(); i++) {
                            JSONObject news_item = unisArray.getJSONObject(i);


                            ContentValues mNewValues = new ContentValues();
                            mNewValues.put(AppContract.NewsColumns.NEWS_ID,
                                    news_item.getInt("id"));
                            mNewValues.put(AppContract.NewsColumns.NEWS_TYPE_ID,
                                    news_item.getInt("news_type"));
                            mNewValues.put(AppContract.NewsColumns.SLUG,
                                    news_item.getString("slug"));
                            mNewValues.put(AppContract.NewsColumns.TITLE,
                                    news_item.getString("title"));
                            mNewValues.put(AppContract.NewsColumns.CONTENT,
                                    news_item.getString("content"));
                            mNewValues.put(AppContract.NewsColumns.IMAGE_URL,
                                    news_item.getString("image_url"));
                            mNewValues.put(AppContract.NewsColumns.CREATED_AT,
                                    news_item.getString("created_at"));

                            Uri mNewUri = cr
                                    .insert(AppContract.News.CONTENT_URI,
                                            mNewValues);
                            cr.registerContentObserver(
                                    mNewUri,
                                    true,
                                    new MyContentObserver(new Handler()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    swipeContainer.setRefreshing(false);
                } else {

                    if (swipeContainer.isShown()) {
                        swipeContainer.setRefreshing(false);
                    }
                    try {
                        Toast.makeText(getActivity(),
                                "Sorry! No results found", Toast.LENGTH_LONG)
                                .show();
                    } catch (NullPointerException npe) {
                        System.out.println(npe);
                    }

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    try {
                        Toast.makeText(getActivity(),
                                "Network Error. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof ServerError) {
                    try {
                        Toast.makeText(
                                getActivity(),
                                "Problem Connecting to Server. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    try {
                        Toast.makeText(getActivity(),
                                "No Connection", Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof TimeoutError) {
                    try {
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Timeout Error. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                }

                if (swipeContainer.isShown()) {
                    swipeContainer.setRefreshing(false);
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };


        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }


    /**
     * @param shown
     * @param animate
     * @see http
     * ://stackoverflow.com/questions/12869779/error-using-setlistshown
     * -on-a-listfragment-with-a-custom-view?answertab=active#tab-top
     */
    public void setListShown(boolean shown, boolean animate) {
        if (mListShown == shown) {
            return;
        }
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils
                        .loadAnimation(getActivity(),
                                android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
            }
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils
                        .loadAnimation(getActivity(),
                                android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.INVISIBLE);
        }
    }

    public void setListShown(boolean shown) {
        setListShown(shown, true);
    }

    public void setListShownNoAnimation(boolean shown) {
        setListShown(shown, false);
    }

    class MyContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

        }
    }

    public interface OnNewsItemSelectedListener {
        public void onNewsItemSelected(int itemId, int news_type_id);
    }


    private interface NewsQuery {
        int _TOKEN = 0x1;
        String[] PROJECTION = {BaseColumns._ID,
                AppContract.NewsColumns.NEWS_ID,
                AppContract.NewsColumns.TITLE,
                AppContract.NewsColumns.IMAGE_URL,
                AppContract.NewsColumns.CREATED_AT};

    }

}

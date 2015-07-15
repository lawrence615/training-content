package com.mobidev.newsapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobidev.newsapp.utils.HtmlTagHandler;
import com.mobidev.newsapp.R;
import com.mobidev.newsapp.database.AppContract;
import com.mobidev.newsapp.ui.widgets.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

/**
 * Created by lawrence on 4/25/15.
 */
public class ViewArticleFragment extends Fragment {

    private String title;
    private String content;
    private String date;
    private String mImageUrl;
    private String postId;

    private TextView txtTitle, txtContent;
    private RelativeTimeTextView txtDate;
    private ImageView mImageView;

    private ImageButton imgComments;

    public static final String NEWS_IMAGE_URL = "image_url";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_POST_ID = "post_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments().containsKey("POSITION")) {

            title = getArguments().getString(AppContract.NewsColumns.TITLE);
            content = getArguments()
                    .getString(AppContract.NewsColumns.CONTENT);
            date = getArguments()
                    .getString(AppContract.NewsColumns.CREATED_AT);
            mImageUrl = getArguments().getString(
                    AppContract.NewsColumns.IMAGE_URL);
            postId = getArguments().getString(AppContract.NewsColumns.NEWS_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.article_view, container,
                false);

        txtTitle = ((TextView) rootView.findViewById(R.id.title));
        txtContent = ((TextView) rootView.findViewById(R.id.content));
        txtContent.setMovementMethod(LinkMovementMethod.getInstance());
        txtDate = ((RelativeTimeTextView) rootView
                .findViewById(R.id.created_at));
        mImageView = ((ImageView) rootView.findViewById(R.id.image));

        /**
         * button to view comments
         */
        imgComments = (ImageButton) rootView.findViewById(R.id.viewcommentsbtn);
        imgComments.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Launch a comments activity here", Toast.LENGTH_LONG).show();

            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtTitle.setText(title);
        txtContent.setText(Html.fromHtml(content, null, new HtmlTagHandler()));
        txtDate.setReferenceTime(java.sql.Timestamp.valueOf(date).getTime());

        if (TextUtils.isEmpty(mImageUrl)) {
            mImageView.setVisibility(View.GONE);
        } else {
            Picasso.with(getActivity().getApplicationContext())
                    .load(mImageUrl)
                    .into(mImageView);
        }

    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.action_share:
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, title + " - "
//                        + AppConstants.APP_DOWNLOAD_LINK_1);
//                sendIntent.setType("text/plain");
//                startActivity(Intent.createChooser(sendIntent, title));
//                break;
//
//            default:
//                break;
//        }
//
//        return false;
//    }
}

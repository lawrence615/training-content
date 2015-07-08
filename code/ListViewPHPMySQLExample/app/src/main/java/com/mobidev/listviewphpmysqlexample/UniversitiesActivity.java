package com.mobidev.listviewphpmysqlexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lawrence on 4/19/15.
 */
public class UniversitiesActivity extends ActionBarActivity {

    private ListView lvUniversities;
    //    private String universitiesUrl = "http://10.0.2.2/php_tizzi/php_list_db_example/universities.php";
    private String universitiesUrl = "http://testing.mlab-training.devs.mobi/php_list_db_example/universities.php";
    private String tag_json_obj = "request_unis_list";// Tag used to cancel the request

    private CustomListAdapter adapter;
    private ArrayList<UniversitiesModel> unisList = new ArrayList<UniversitiesModel>();;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        lvUniversities = (ListView) findViewById(R.id.lvUniversities);
//        lvUniversities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                Intent intent_more_details = new Intent(getApplicationContext(), MoreDetailsActivity.class);
////                intent_more_details.putExtra(MoreDetailsActivity.UNIVERSITY_ID, unisList.get(position).getId());
////                startActivity(intent_more_details);
//                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        lvUniversities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent_more_details = new Intent(getApplicationContext(), MoreDetailsActivity.class);
                intent_more_details.putExtra(MoreDetailsActivity.UNIVERSITY_ID, unisList.get(position).getId());
                startActivity(intent_more_details);
            }
        });


//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(postsUrl, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                System.out.println(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Accept", "application/json");
//                return params;
//            }
//        };

        showProgress();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, universitiesUrl, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {//checking if response is null
                    try {
                        JSONArray unisArray = response.getJSONArray("universities");
                        System.err.println(unisArray);
                            for (int i = 0; i < unisArray.length(); i++) {
                                JSONObject unisItem = unisArray.getJSONObject(i);

                                int id = unisItem.getInt("id");
                                String university_name = unisItem.getString("university_name");
                                String description = unisItem.getString("description");

                                UniversitiesModel universitiesModel = new UniversitiesModel(id, university_name, unisItem.getString("description"), null, null, null, null, null);
                                unisList.add(universitiesModel);

                                addToAdapter();
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                stopProgress();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    try {
                        Toast.makeText(getApplicationContext(),
                                "Network Error. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof ServerError) {
                    try {
                        Toast.makeText(
                                getApplicationContext(),
                                "Problem Connecting to Server. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    try {
                        Toast.makeText(getApplicationContext(),
                                "No Connection", Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                } else if (error instanceof TimeoutError) {
                    try {
                        Toast.makeText(
                                getApplicationContext().getApplicationContext(),
                                "Timeout Error. Try Again Later",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException npe) {
                        System.err.println(npe);
                    }
                }

                stopProgress();

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


    private void addToAdapter() {
        adapter = new CustomListAdapter(getApplicationContext(), unisList);
        adapter.notifyDataSetChanged();
        lvUniversities.setAdapter(adapter);

    }


    private void showProgress() {
        mProgress = ProgressDialog.show(UniversitiesActivity.this, "Please Wait",
                "Accessing server...");
    }

    private void stopProgress() {
        mProgress.cancel();
    }


}

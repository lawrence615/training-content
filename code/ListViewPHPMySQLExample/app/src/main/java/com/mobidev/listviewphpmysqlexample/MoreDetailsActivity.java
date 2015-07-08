package com.mobidev.listviewphpmysqlexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lawrence on 4/20/15.
 */
public class MoreDetailsActivity extends ActionBarActivity {

    public static final String UNIVERSITY_ID = "university_id";
    private Bundle extras;

    private String requestUrl = "http://testing.mlab-training.devs.mobi/php_list_db_example/universityinfo.php";

    private ProgressDialog mProgress;
    private String tag_json_obj = "request_single_uni_details";// Tag used to cancel the request

    private int universityId;

    private TextView txtUniName, txtUniAdd, txtUniDesc, txtLat, txtLng;
    private Button btnCall, btnViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_details_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getExtras();
        if (extras != null) {
            universityId = extras.getInt(UNIVERSITY_ID);
        }

        txtUniName = (TextView) findViewById(R.id.university_name);
        txtUniAdd = (TextView) findViewById(R.id.university_address);
        txtUniDesc = (TextView) findViewById(R.id.university_desc);
        txtLat = (TextView) findViewById(R.id.lat);
        txtLng = (TextView) findViewById(R.id.lng);


        btnViewMap = (Button) findViewById(R.id.btnViewMap);
        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map_intent = new Intent(getApplicationContext(), MapActivity.class);
                map_intent.putExtra(MapActivity.ARG_UNIVERSITY_NAME, txtUniName.getText().toString().trim());
                map_intent.putExtra(MapActivity.ARG_LATITUDE, Double.parseDouble(txtLat.getText().toString().trim()));
                map_intent.putExtra(MapActivity.ARG_LONGITUDE, Double.parseDouble(txtLng.getText().toString().trim()));
                startActivity(map_intent);
            }
        });

        btnCall = (Button) findViewById(R.id.btnCallUs);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_enquiry = new Intent(getApplicationContext(), EnquiriesActivity.class);
                startActivity(intent_enquiry);
            }
        });

        fetchDetails();

    }


    private void fetchDetails() {
        showProgress();


        Uri.Builder builder = Uri.parse(requestUrl).buildUpon();
        builder.appendQueryParameter("university_id", Integer.toString(universityId));

        // http://testing.mlab-training.devs.mobi/php_list_db_example/universityinfo.php?university_id=2
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, builder.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    txtUniName.setText(response.getString("university_name"));
                    txtUniAdd.setText(response.getString("address"));
                    txtUniDesc.setText(response.getString("description"));
                    txtLat.setText(response.getString("latitude"));
                    txtLng.setText(response.getString("longitude"));
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void showProgress() {
        mProgress = ProgressDialog.show(MoreDetailsActivity.this, "Please Wait",
                "Accessing server...");
    }

    private void stopProgress() {
        mProgress.cancel();
    }
}

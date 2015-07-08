package com.mobidev.listviewphpmysqlexample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lawrence on 4/22/15.
 */
public class EnquiriesActivity extends ActionBarActivity {

    private EditText edEmail, edSubject, edMessage;
    private Button btnSubmit;

    private String email, subject, message;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiries);

        edEmail = (EditText) findViewById(R.id.email);
        edSubject = (EditText) findViewById(R.id.subject);
        edMessage = (EditText) findViewById(R.id.message);

        btnSubmit = (Button) findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    email = edEmail.getText().toString().trim();
                    subject = edSubject.getText().toString().trim();
                    message = edMessage.getText().toString().trim();

                    showProgress();
                    postToServer(email, subject, message);
                }
            }
        });
    }


    private boolean Validate() {
        /**
         * validating the email
         */
        if (TextUtils.isEmpty(edEmail.getText())) {
            Toast.makeText(getApplicationContext(), "Please enter your email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString().trim()).matches()) {
            Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        /**
         * if this input field is empty, we request the user to provide a value
         */
        if (edSubject.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please provide a subject", Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         * if this input field is empty, we request the user to provide a value
         */
        if (edMessage.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please give a message", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


//    public final static boolean isValidEmail(String target) {
//        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
//    }


    protected void postToServer(String email, String subject, String message) {

        JSONObject params = new JSONObject();
        try {
            params.put("university_id", Integer.toString(1));
            params.put("email", email);
            params.put("subject", subject);
            params.put("message", message);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(params);
        JsonObjectRequest myRequest = new JsonObjectRequest(
                Request.Method.POST,
                "http://testing.mlab-training.devs.mobi/php_list_db_example/enquiries.php",
                params,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (Integer.parseInt(response.getString("success")) == 1) {
                                edEmail.setText("");
                                edSubject.setText("");
                                edMessage.setText("");
                                Toast.makeText(getApplicationContext(), response.getString("msg"), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        stopProgress();
                    }
                },
                new Response.ErrorListener() {
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
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance().addToRequestQueue(myRequest, "tag");

    }

    private void showProgress() {
        mProgress = ProgressDialog.show(EnquiriesActivity.this, "Please Wait",
                "Posting your enquiry");
    }

    private void stopProgress() {
        mProgress.cancel();
    }
}

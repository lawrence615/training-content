package com.mobidev.httprequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobidev.httprequest.adapter.MyCustomAdapter;
import com.mobidev.httprequest.model.University;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lawrence on 7/8/15.
 */
public class UniversitiesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ProgressDialog mProgress;

    private ListView listView;
    private ArrayList<University> arrList = new ArrayList<University>();
    private MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        listView = (ListView) findViewById(R.id.lvUniversities);
        listView.setOnItemClickListener(this);


        if (isNetworkAvailable()) {
            showProgress();
            fetchDataFromRemoteServer();
        } else {
            /**
             * android.R.id.content gives you the root element of a view, without having to know its actual name
             *
             * @see http://stackoverflow.com/questions/7776768/android-what-is-android-r-id-content-used-for
             */
            Snackbar.make(findViewById(android.R.id.content), "Check your internet connectivity", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void fetchDataFromRemoteServer() {
        new NetworkAsyncTask().execute("http://testing.mlab-training.devs.mobi/php_list_db_example/universities.php");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView txtId = (TextView) view.findViewById(R.id.univesity_id);
        System.out.println(txtId.getText());
    }

    /**
     * @see http://developer.android.com/reference/android/os/AsyncTask.html
     */
    private class NetworkAsyncTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... myUrl) {

            try {
                /**
                 * declare URL connection
                 *
                 * @see http://developer.android.com/reference/java/net/HttpURLConnection.html
                 */
                URL url = new URL(myUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
//                conn.setConnectTimeout(10000);//Sets the maximum time in milliseconds to wait while connecting.
//                conn.setReadTimeout(15000);//Sets the maximum time to wait for an input stream read to complete before giving up.
                conn.setRequestProperty("Accept", "application/json");

                /**
                 * open InputStream to connection
                 */
                conn.connect();

                InputStream in = conn.getInputStream();
                int status = conn.getResponseCode();
                StringBuilder stringBuilder = new StringBuilder();
                switch (status) {
                    case 200:
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        reader.close();
                        return stringBuilder.toString();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
//            System.err.println(result);
            try {
                JSONObject response = new JSONObject(result);
                JSONArray unisArray = response.getJSONArray("universities");
                for (int i = 0; i < unisArray.length(); i++) {
                    JSONObject unisItem = unisArray.getJSONObject(i);
                    int id = unisItem.getInt("id");
                    String university_name = unisItem.getString("university_name");
                    String description = unisItem.getString("description");

                    University obj = new University(id, university_name, description);

                    arrList.add(obj);
                    adapter = new MyCustomAdapter(getApplicationContext(), arrList);

                    addToAdapter();


                    System.err.println(id + " " + university_name + " " + description);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            stopProgress();
        }
    }


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void addToAdapter() {
        adapter = new MyCustomAdapter(getApplicationContext(), arrList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }

    private void showProgress() {
        mProgress = ProgressDialog.show(UniversitiesActivity.this, "Please Wait",
                "Accessing server...");
    }

    private void stopProgress() {
        mProgress.cancel();
    }


}

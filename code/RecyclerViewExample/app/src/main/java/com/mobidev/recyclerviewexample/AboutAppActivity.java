package com.mobidev.recyclerviewexample;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by lawrence on 7/17/15.
 */
public class AboutAppActivity extends AppCompatActivity {

    private TextView txtTitle, txtDescription;

    private PackageInfo pi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //display the up button on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtTitle = (TextView) findViewById(R.id.title);
        txtDescription = (TextView) findViewById(R.id.description);


        try {
            pi = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        txtTitle.setText("Version " + getVersionName() + " (" + getVersionCode() + ")");
        txtDescription.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean tempus nisl sit amet purus tempus, et commodo sem sodales. Mauris mattis magna id urna laoreet lacinia. Integer eleifend condimentum vehicula. In hac habitasse platea dictumst. Sed et ullamcorper lectus. Suspendisse congue id elit a pulvinar. Suspendisse potenti. Pellentesque tincidunt arcu sed tristique mollis. Nam pharetra blandit facilisis.");

    }


    /**
     * version number shown to users
     *
     * @return
     */
    public String getVersionName() {
        PackageInfo pi = getPackageInfo();
        if (pi != null) {
            return pi.versionName;
        } else {
            return "";
        }
    }

    /**
     * internal version number. Used to determine whether one version is more recent than the other. It is not shown to users. The value is an integer
     * <p/>
     * NB: higher numbers indicates more recent versions
     *
     * @return
     */
    public int getVersionCode() {
        PackageInfo pi = getPackageInfo();
        if (pi != null) {
            return pi.versionCode;
        } else {
            return 0;
        }
    }


    public PackageInfo getPackageInfo() {
        return pi;
    }
}

package com.mobidev.navigation.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.mobidev.navigation.R;
import com.mobidev.navigation.ui.fragments.FirstFragment;
import com.mobidev.navigation.ui.fragments.SecondFragment;
import com.mobidev.navigation.ui.utils.PrefUtils;
import com.mobidev.navigation.ui.widgets.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private DrawerLayout mDrawerLayout;
    private TextView txtUsername;
    private RoundedImageView roundedImageView;

    private int mNavItemId;
    private final Handler mDrawerActionHandler = new Handler();
    private static final long DRAWER_CLOSE_DELAY_MS = 350;
    private static final String NAV_ITEM_ID = "navItemId";
    private ActionBarDrawerToggle mDrawerToggle;

    //fragments
    private final FirstFragment mFirstFragment = new FirstFragment();
    private final SecondFragment mSecondFragment = new SecondFragment();

    /* Client for accessing Google APIs */
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PrefUtils.init(getApplicationContext());
        /**
         * check whether user has signed in
         */
        if (!PrefUtils.isLoginDone(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
            finish();
        }

        System.err.println(PrefUtils.isLoginDone(getApplicationContext()));

        // Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        txtUsername = (TextView) findViewById(R.id.username);
        roundedImageView = (RoundedImageView) findViewById(R.id.userPhoto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.nav_home;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open,
                R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigate(mNavItemId);

    }


    /**
     * Performs the actual navigation logic, updating the main content fragment.
     */
    private void navigate(final int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, mFirstFragment)
                        .commit();
                break;
            case R.id.nav_messages:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, mSecondFragment)
                        .commit();
                break;
            default:
                // ignore
                break;
        }
    }


    /**
     * Handles clicks on the navigation menu.
     */
    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                return mDrawerToggle.onOptionsItemSelected(item);
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        //check if connection has been done
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            txtUsername.setText(currentPerson.getDisplayName());
            Picasso.with(getApplicationContext())
                    .load(currentPerson.getImage().getUrl())
                    .into(roundedImageView);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

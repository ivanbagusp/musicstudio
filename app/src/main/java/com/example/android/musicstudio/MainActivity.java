package com.example.android.musicstudio;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static boolean dapatLokasi = false;
    public static Location mLastLocation;
    public static Location bLocation;
    public static GoogleApiClient mGoogleApiClient;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = this.getSharedPreferences("MyPrefs", this.MODE_PRIVATE);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView ImageMenuUtama = (ImageView) findViewById(R.id.backgroundDpn);
        Picasso.with(this).load( "http://cloudofoasis.com/api/Ivan/Gambar/bkgutama.jpg")
                .placeholder(R.drawable.placeholder)
                .fit()
                .error(R.drawable.error)
                .into(ImageMenuUtama);

        ImageView mapsBtn = (ImageView) findViewById(R.id.mapsBtn);
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        ImageView favBtn = (ImageView) findViewById(R.id.favBtn);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, Favorites.class);
                    startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.mnPopuler:
//                requestJsonObject(0); //Popular
//                break;
//            case R.id.mnFavorit:
//                requestJsonObject(1); //Top Rated
//                break;
//            case R.id.mnComing:
//                requestJsonObject(2); //Up Coming
//                break;
            case R.id.nav_about:
                Intent i = new Intent(this, About.class);
                startActivity(i);
                break;
//                Toast.makeText(getApplicationContext().getApplicationContext(),
//                        "Developer by : \n" + "Ivan Bagus Pinuntun",
//                        Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_favorites) {
            Intent i = new Intent(this, Favorites.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {
            Intent i = new Intent(this, About.class);
            startActivity(i);
        }else if (id == R.id.nav_howtouse) {
            Intent i = new Intent(this, HowToUse.class);
            startActivity(i);
        }
        else if (id == R.id.nav_info) {
            Intent i = new Intent(this, InfoMusic.class);
            startActivity(i);
        }
        else if (id == R.id.nav_exit) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("login", false);
            editor.putString("id", "");
            editor.commit();
            Toast.makeText(MainActivity.this, "Successfully Logged Out",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(MainActivity.this, "Get Location...", Toast.LENGTH_SHORT).show();
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            try {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                bLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    dapatLokasi = true;
                    Toast.makeText(MainActivity.this, "Get the locations of you!", Toast.LENGTH_SHORT).show();
                       }
                else {
                    showGPSDisabledAlert();
                }
            } catch (SecurityException se) {
                Toast.makeText(MainActivity.this, "Security Exception", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showGPSDisabledAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle("GPS Not Active")
                .setIcon(R.drawable.icn_alert)
                .setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}






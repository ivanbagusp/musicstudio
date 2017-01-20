package com.example.android.musicstudio;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] nama, alamat, harga, jam, update, alatmusik, call, id,
             gambar, ratingalatmusik, ratingrecording, ratingtempat, distance;
    private Double[] latitude, longitude;
    int numData;
    LatLng latLng[];
    Boolean markerD[];

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLokasi();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
//        mMap.getUiSettings().setRotateGesturesEnabled(true);
//        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
//        mMap.getUiSettings().setTiltGesturesEnabled(true);
//        mMap.getUiSettings().setScrollGesturesEnabled(true);
    }

    //MENGAMBIL DATA
    private void getLokasi() {
        String url = "http://cloudofoasis.com/api/Ivan/getStudio.php";
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        numData = response.length();
                        Log.d("DEBUG_", "Parse JSON");
                        latLng = new LatLng[numData];
                        markerD = new Boolean[numData];
                        nama = new String[numData];
                        alamat = new String[numData];
                        harga = new String[numData];
                        jam = new String[numData];
                        call = new String[numData];
                        update = new String[numData];
                        alatmusik = new String[numData];
                        ratingalatmusik = new String[numData];
                        ratingrecording = new String[numData];
                        ratingtempat = new String[numData];
                        id = new String[numData];
                        gambar = new String[numData];
                        latitude = new Double[numData];
                        longitude = new Double[numData];
                        distance = new String[numData];

                        if (MainActivity.mLastLocation != null) {
                            Double latUser = MainActivity.mLastLocation.getLatitude();
                            Double lngUser = MainActivity.mLastLocation.getLongitude();
                            for (int i = 0; i < numData; i++) {
                                try {
                                    JSONObject data = response.getJSONObject(i);
                                    id[i] = data.getString("id");
                                    latLng[i] = new LatLng(data.getDouble("latitude"),
                                            data.getDouble("longitude"));
                                    nama[i] = data.getString("nama");
                                    alamat[i] = data.getString("alamat");
                                    harga[i] = data.getString("harga");
                                    jam[i] = data.getString("jam");
                                    call[i] = data.getString("call");
                                    update[i] = data.getString("lastupdate");
                                    alatmusik[i] = data.getString("alatmusik");
                                    ratingalatmusik[i] = data.getString("ratingalatmusik");
                                    ratingrecording[i] = data.getString("ratingrecording");
                                    ratingtempat[i] = data.getString("ratingtempat");

                                    gambar[i] = data.getString("gambar");
                                    latitude[i] = data.getDouble("latitude");
                                    longitude[i] = data.getDouble("longitude");
                                    Double lat = data.getDouble("latitude");
                                    Double lng = data.getDouble("longitude");

                                    float[] jarak = new float[10];
                                    Location.distanceBetween(latUser, lngUser, lat, lng, jarak);
                                    distance[i] = (jarak[0]/1000) + "";

                                    //Jarak tampil marker di maps :  / 5km
                                    if (jarak[0]/1000 < 5) {
                                        markerD[i] = false;
                                        mMap.addMarker(new MarkerOptions()
                                                .position(latLng[i])
                                                .title(nama[i] + " - " + distance[i] + " km from your location")
                                                .snippet(alamat[i])
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerstudio)));
                                    }


                                } catch (JSONException je) {
                                }
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng[i], 15.5f));
                            }
                        }

                        //LOKASI USER
                        if (MainActivity.dapatLokasi) {
                            LatLng marker = new LatLng(MainActivity.mLastLocation.getLatitude(),
                                    MainActivity.mLastLocation.getLongitude());

                            mMap.addMarker(new MarkerOptions()
                                    .position(marker)
                                    .title("My Location")
                                    .snippet("The distance radius 500 meters")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.user)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                                    LatLng(MainActivity.mLastLocation.getLatitude(),
                                    MainActivity.mLastLocation.getLongitude()), 15.5f));

                            Circle cir = (mMap.addCircle(new CircleOptions()
                                    .center(marker)
                                    .radius(500)
                                    .strokeWidth(2)
                                    .strokeColor(Color.parseColor("#F5AB35"))
                                    .fillColor(Color.parseColor("#101565C0"))));

                        } else {
                            Toast.makeText(MapsActivity.this, "GPS not active", Toast.LENGTH_SHORT).show();
                        }

                        //MARKER KLIK
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Log.d("DEBUG_", "Marker clicked");
                                for (int i = 0; i < numData; i++) {
                                    if (marker.getTitle().equals("My Location")){
                                        marker.showInfoWindow();
                                    }
                                    if (marker.getTitle().toString().equals(nama[i] + " - " + distance[i] + " km from your location")) {
                                        if (markerD[i]) {
                                            Log.d("DEBUG_", "panggil activity");
                                            ContentStudio.id = id[i];
                                            ContentStudio.namaStudio = nama[i];
                                            ContentStudio.alamat = alamat[i];
                                            ContentStudio.harga = harga[i];
                                            ContentStudio.jam = jam[i];
                                            ContentStudio.callSave = call[i];
                                            ContentStudio.alatmusik = alatmusik[i];
                                            ContentStudio.lastUpdate = update[i];
                                            ContentStudio.ratingAlat = ratingalatmusik[i];
                                            ContentStudio.ratingRec = ratingrecording[i];
                                            ContentStudio.ratingTmpt = ratingtempat[i];
                                            ContentStudio.gambar = gambar[i];
                                            ContentStudio.latitude = latitude[i];
                                            ContentStudio.longitude = longitude[i];

                                            Intent intent = new Intent(MapsActivity.this, ContentStudio.class);
                                            startActivity(intent);
                                            markerD[i] = false;
                                        } else {
                                            Log.d("DEBUG_", "show info");
                                           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.5f));
                                            markerD[i] = true;
                                            marker.showInfoWindow();
                                            Toast ts = Toast.makeText(MapsActivity.this,"Tap once again on marker, for detail Studio Music",Toast.LENGTH_LONG);
                                            TextView v = (TextView) ts.getView().findViewById(android.R.id.message);
                                            if( v != null)
                                            v.setGravity(Gravity.CENTER);
                                            ts.show();
                                        }
                                    } else {
                                        markerD[i] = false;
                                    }
                                }
                                return false;
                            }

                        });

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setTitle("Error!");
                        builder.setMessage("No Internet Connection");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getLokasi();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
        Volley.newRequestQueue(this).add(request);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.musicstudio/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO : Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.musicstudio/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}

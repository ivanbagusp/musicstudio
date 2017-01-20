package com.example.android.musicstudio;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ContentStudio extends AppCompatActivity {

    private ImageView callPhone, callShare, buttonf;
    private TextView studioName, studioAddress, studioPrice, studioHour, studioAlatMusik,studioUpdate;
    private RatingBar ratingBarAlat, ratingBarRec, ratingBarTmpt;

    public static Double latitude, longitude;
    public static String id, namaStudio, alamat, harga, jam, alatmusik, lastUpdate, gambar,
            callSave, ratingAlat, ratingRec, ratingTmpt;
    //public static Float ratingAlat, ratingRec, ratingTmpt;

    SliderLayout mDemoSlider;
    private DBDataSource db;
    private boolean favorite;
    private static final String TAG = "ListDislay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_studio);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ratingBarAlat = (RatingBar) findViewById(R.id.rating_alatmusik);
        ratingBarRec = (RatingBar) findViewById(R.id.rating_recording);
        ratingBarTmpt = (RatingBar) findViewById(R.id.rating_tempat);

        Log.i("CEK_RATING",String.valueOf(ratingAlat));
            ratingBarAlat.setRating(Float.parseFloat(ratingAlat));
            ratingBarRec.setRating(Float.parseFloat(ratingRec));
            ratingBarTmpt.setRating(Float.parseFloat(ratingTmpt));

        studioName = (TextView) findViewById(R.id.studioname);
        studioAddress = (TextView) findViewById(R.id.studio_address);
        studioPrice = (TextView) findViewById(R.id.studio_price);
        studioHour = (TextView) findViewById(R.id.studio_hour);
        studioAlatMusik = (TextView) findViewById(R.id.textalatmusik);
        studioUpdate = (TextView) findViewById(R.id.lastupdate);

        Log.i("CEK_JAM",String.valueOf(jam));
            studioName.setText(namaStudio);
            studioAddress.setText(alamat);
            studioPrice.setText(harga);
            studioHour.setText(jam);
            studioAlatMusik.setText(alatmusik);
            studioUpdate.setText(lastUpdate);

         mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        MapView map = (MapView) findViewById(R.id.mapdetail);
        map.onCreate(savedInstanceState);
        map.onResume();
        GoogleMap googleMap = map.getMap();
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        if (MainActivity.dapatLokasi) {
            Double latUser = latitude;
            Double lngUser = longitude;
            LatLng lokasi = new LatLng(latUser, lngUser);
                googleMap.addMarker(new MarkerOptions()
                        .position(lokasi)
                        .title(namaStudio)
                        .snippet(alamat)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerstudio)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(latUser, lngUser), 14.5f));


        } else {
            Toast.makeText(ContentStudio.this, "Cannot find location", Toast.LENGTH_SHORT).show();
            MainActivity.mGoogleApiClient.connect();
        }


        getGambar();


        //Set favorite dan Insert data dari API ke Sqlite database
        db = new DBDataSource(ContentStudio.this);
        db.open();
        //Cek fav
        favorite = db.isFavorite(Integer.valueOf(id));

        buttonf = (ImageView) findViewById(R.id.favbuttonwhite);
        if (favorite) {
            buttonf.setImageResource(R.drawable.starred);
        }
        else {
            buttonf.setImageResource(R.drawable.starwhite);
        }
        buttonf.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.i("CEK", String.valueOf(favorite));
                if (favorite) {
                    buttonf.setImageResource(R.drawable.starwhite);
                    db.deleteStudioMusik(Integer.valueOf(id));
                    favorite = false;
                }
                else {
                    buttonf.setImageResource(R.drawable.starred);
                    favorite = true;

                    if (db.insertStudioMusik(id,namaStudio,alamat,harga,gambar,
                            jam,callSave,alatmusik,lastUpdate,ratingAlat,ratingRec,ratingTmpt,
                            latitude,longitude)) {
                    }
                    else {

                    }
                }
            }
        });

        //Button Call
        callPhone = (ImageView) findViewById(R.id.call_button);
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContentStudio.this);
                builder.setMessage("Call " + callSave + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + callSave));
                        try {
                            startActivity(i);
                        } catch (SecurityException se) {
                            Toast.makeText(ContentStudio.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        //Button SHARE
        callShare = (ImageView) findViewById(R.id.share_button);
        callShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = namaStudio + "\n\n"
                        +" "
                        + "Alamat:" + "\n" + alamat + "\n\n"
                        +" "
                        + "Jam Operasional:" + "\n" + jam + "\n\n"
                        +" "
                        + "Harga:" + "\n" + harga + "\n\n"
                        +" "
                        + "Alat Musik:" + "\n" + alatmusik + "\n\n"
                        +" "
                        + "No Telepon:" + "\n" + callSave + "\n\n"
                        +" "
                        + "Update Terakhir:" + "\n" + lastUpdate + "\n\n"
                        +" "
                        + "Lokasi Studio Musik:" + "\n" +
                        "http://maps.google.com/?q=" + latitude + "," + longitude + "\n\n"
                        +" "
                        + "'Find Your Music Studio!'";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Pilih Aplikasi"));
            }
        });
    }

    private void getGambar() {
        String url = "http://cloudofoasis.com/api/Ivan/getGambar.php?StudioMusik=" + id;
        Log.i(TAG, url);
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int numData = response.length();
                        if (numData == 0) mDemoSlider.setVisibility(View.GONE);
                        else {
                            JSONObject slider_studio;
                            Log.i(TAG, "On Response Get Gambar");
                            String[] gambar_studio = new String[numData], nama_studio = new String[numData];
                            HashMap<String, String> url_maps = new HashMap<String, String>();
                            for (int i = 0; i < numData; i++) {
                                try {
                                    slider_studio = response.getJSONObject(i);
                                    gambar_studio[i ] = slider_studio.getString("gambar");
                                    nama_studio[i] = slider_studio.getString("nama");
                                    url_maps.put(nama_studio[i],"http://cloudofoasis.com/api/Ivan/slider_studio/" + gambar_studio[i]);
                                } catch (JSONException je) {
                                    Toast.makeText(ContentStudio.this, "JSON ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }
                            for (String name : url_maps.keySet()) {
                                TextSliderView textSliderView = new TextSliderView(ContentStudio.this);
                                textSliderView.description(name).image(url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit);
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle().putString("extra", name);
                                mDemoSlider.addSlider(textSliderView);
                                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
                                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                                mDemoSlider.setDuration(4000);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //untuk tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MapsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

}

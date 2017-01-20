package com.example.android.musicstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.HashMap;

public class Favorites extends AppCompatActivity {

    RecyclerView list_item;
    ArrayList<HashMap<String, String>> array_list;
    Button button;
    ProgressBar progressBar;
    private DBDataSource db;

    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favorites);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DBDataSource(Favorites.this);
        db.open();
//        unfavbutton = (ImageView) findViewById(R.id.unfavbutton);
//        unfavbutton.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                    db.deleteStudioMusik(Integer.valueOf(id));
//            }
//        });

//        Log.i("CEK_DATA", db.getStudioMusik().get(1).getRatingalat());
//        Log.i("CEK_DATA2", db.getStudioMusik().get(1).getNama());
        array_list = new ArrayList<>();

        list_item = (RecyclerView) findViewById(R.id.list_item);
        list_item.setLayoutManager(new LinearLayoutManager(this));
        list_item.setItemAnimator(new DefaultItemAnimator());

        progressBar = (ProgressBar) findViewById(R.id.list_item_progress);
        button = (Button) findViewById(R.id.refresh_list_item);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        getData();

    }

    public void getData() {
        list_item.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        Log.d("DEBUG_", "Set data to Adapter");
        for (int i = 0; i < db.getStudioMusik().size(); i++) {
            HashMap<String,String> data = new HashMap<>();
            data.put("id", String.valueOf(db.getStudioMusik().get(i).getId()));
            data.put("nama", db.getStudioMusik().get(i).getNama());
            data.put("alamat", db.getStudioMusik().get(i).getAlamat());
            data.put("harga", db.getStudioMusik().get(i).getHarga());
            data.put("gambar", db.getStudioMusik().get(i).getGambar());
            data.put("jam", db.getStudioMusik().get(i).getJam());
            data.put("calllite", db.getStudioMusik().get(i).getCall());
            data.put("alatmusik", db.getStudioMusik().get(i).getAlatmusik());
            data.put("lastupdate", db.getStudioMusik().get(i).getLastupdate());
            data.put("ratingalatmusik", db.getStudioMusik().get(i).getRatingalat());
            data.put("ratingrecording", db.getStudioMusik().get(i).getRatingrecording());
            data.put("ratingtempat", db.getStudioMusik().get(i).getRatingtempat());
            data.put("latitude", db.getStudioMusik().get(i).getLatitude());
            data.put("longitude", db.getStudioMusik().get(i).getLongitude());
            array_list.add(data);
        }

        progressBar.setVisibility(View.INVISIBLE);
        list_item.setVisibility(View.VISIBLE);

        ListAdapter adapter = new ListAdapter(Favorites.this, array_list);
        list_item.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

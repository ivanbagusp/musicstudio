package com.example.android.musicstudio;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class InfoMusic extends AppCompatActivity {
    FragmentManager fm= getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_music);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle("About Music");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);


//        Button ButtonPlay1 = (Button) findViewById(R.id.play1);
//    ButtonPlay1.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            Intent i = new Intent(InfoMusic.this, Youtube1.class);
//            try {
//                startActivity(i);
//            } catch (SecurityException se) {
//
//            }
//        }
//    });
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
               finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

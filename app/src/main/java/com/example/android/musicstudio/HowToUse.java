package com.example.android.musicstudio;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class HowToUse extends AppCompatActivity {
    FragmentManager fm= getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar2);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar2);
        collapsingToolbar.setTitle("How To Use");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);

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

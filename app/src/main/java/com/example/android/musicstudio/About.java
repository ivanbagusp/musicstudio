package com.example.android.musicstudio;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;

/**
 * Created by IVAN BAGUS PINUNTUN on 08/05/2016.
 */
public class About extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        DisplayMetrics popAbout = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(popAbout);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        int width = popAbout.widthPixels;
        int height = popAbout.heightPixels;

        getWindow().setLayout((int) (width*.7),(int)(height*.5));
    }
}

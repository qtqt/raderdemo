package com.qt.raderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RadarView radarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radarView=findViewById(R.id.radarView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        radarView.startSearch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        radarView.stopSearch();
    }
}

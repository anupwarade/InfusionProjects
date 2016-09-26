package com.example.a6grpzz.findmyconferenceroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ImageView img = (ImageView) findViewById(R.id.img);
        img.setBackgroundResource(R.drawable.map);

    }
}

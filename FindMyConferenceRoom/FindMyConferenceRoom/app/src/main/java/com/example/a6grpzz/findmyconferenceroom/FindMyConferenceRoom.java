package com.example.a6grpzz.findmyconferenceroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindMyConferenceRoom extends AppCompatActivity implements View.OnClickListener{


    private Button button;

    // In Constructor



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_my_conference_room);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {

            startActivity(new Intent(FindMyConferenceRoom.this, Map.class));
        }

}

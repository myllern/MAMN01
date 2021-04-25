package com.example.hellosensor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCompass(View view) {

        Intent intent = new Intent(this, DisplayCompassActivity.class);
        startActivity(intent);
    }

    public void openAccelerometer(View view) {

        Intent intent = new Intent(this, DisplayAccelerometerActivity.class);
        startActivity(intent);
    }


    }


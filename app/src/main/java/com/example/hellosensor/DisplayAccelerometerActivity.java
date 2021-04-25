package com.example.hellosensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;




public class DisplayAccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] mLastAccelerometer = new float[3];
    float xVal, yVal, zVal;
    static final float ALPHA = 0.25f;
    private TextView X,Y,Z;
    View acc_view;
    MediaPlayer mediaPlayer;
    private Vibrator v;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_accelerometer);

        X =  findViewById(R.id.x);
        Y =  findViewById(R.id.y);
        Z =  findViewById(R.id.z);
        acc_view = findViewById(R.id.acc_view);
        mediaPlayer = MediaPlayer.create(this, R.raw.falling);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);






    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mLastAccelerometer = lowPass(event.values.clone(),  mLastAccelerometer);

            xVal = event.values[0];
            yVal = event.values[1];
            zVal = event.values[2];

            X.setText("X: " + Float.toString(xVal));
            Y.setText("Y: " + Float.toString(yVal));
            Z.setText("Z: " + Float.toString(zVal));

            if(yVal > 0 )
                acc_view.setBackgroundColor(Color.YELLOW);
            if(yVal < 0 )
                acc_view.setBackgroundColor(Color.GREEN);

            if(xVal > 3) {
                mediaPlayer.start();
                v.vibrate(200);
            }
  //          if(xVal < - 3)
//                Record and play sound







        }


    }
    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
package com.example.hellosensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayCompassActivity extends AppCompatActivity {
    private ImageView image_compass;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;
    private TextView textView;

    private float[] floatGravity = new float[3];
    private float[] floatGeoMagnetic = new float[3];
    private float[] floatOrientation = new float[3];
    private float[] floatRotationMatrix = new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_compass);
        image_compass = findViewById(R.id.compass_image);
       // textView = findViewById(R.id.text_compass);


        //create the sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        // create sensors
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // create sense SensorEventListener
        SensorEventListener sensorEventListenerAccelerometer = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                floatGravity =  event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity,floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix,floatOrientation);

                image_compass.setRotation((float)(-floatOrientation[0]*180/3.14159));
                // textView.setText((int)(-floatOrientation[0]*180/3.14159));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        SensorEventListener sensorEventListenerMagneticField = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                floatGeoMagnetic = event.values;
                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity,floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix,floatOrientation);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };








        sensorManager.registerListener(sensorEventListenerAccelerometer,sensorAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerMagneticField,sensorMagneticField,SensorManager.SENSOR_DELAY_NORMAL);


    }



}
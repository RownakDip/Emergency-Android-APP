package com.ric.gyroscopetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener2
{
    private TextView x,y,z;
    private SensorManager sensorManager;
    private SensorEvent   sensorEvent;
    private Sensor accelorometter;
    private boolean isAccelorometeravAilable,isNotFirstTime=false;
    private  float currentX,currentY,currentZ,lastX,lastY,lastZ, xDiferrence, yDiferrence, zDiferrence;
    private float threshold = 5f;
    private Vibrator Vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = findViewById(R.id.x);
        y= findViewById(R.id.y);
        z= findViewById(R.id.z);

        Vibrator = (android.os.Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null)
        {
            accelorometter =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelorometeravAilable = true;
        }
        else
        {
            accelorometter =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelorometeravAilable = false;
        }

    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {

        x.setText(sensorEvent.values[0]+"m/s2");

        y.setText(sensorEvent.values[1]+"m/s2");

        z.setText(sensorEvent.values[2]+"m/s2");


        currentX= sensorEvent.values[0];
        currentY= sensorEvent.values[1];
        currentZ= sensorEvent.values[2];

        if(isNotFirstTime)
        {
             xDiferrence = Math.abs(lastX-currentX);
            yDiferrence = Math.abs(lastY-currentY);
             zDiferrence = Math.abs(lastZ-currentZ);

            if ((xDiferrence > threshold && yDiferrence>threshold)||
            (xDiferrence>threshold && zDiferrence >threshold) ||
                    (yDiferrence>threshold && zDiferrence>threshold))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Vibrator.vibrate(VibrationEffect.createOneShot( 500,VibrationEffect.DEFAULT_AMPLITUDE));

                }
                else Vibrator.vibrate(555);
            }
        }
        lastX= currentX;
        lastY=currentY;
        lastZ=currentZ;
        isNotFirstTime=true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAccelorometeravAilable)
        {
            sensorManager.registerListener(this,accelorometter,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAccelorometeravAilable)sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
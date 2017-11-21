package com.apphunters.sensorstat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.widget.TextView;

import java.util.Date;

public class SensorDetials extends AppCompatActivity implements SensorEventListener{


    TextView mandata;
    TextView senread;
    SensorManager snmng;
    Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detials);
        int type = getIntent().getIntExtra("type",0);

        snmng = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mandata = (TextView) findViewById(R.id.deviceInfo);
        senread = (TextView) findViewById(R.id.data);

        sensor = snmng.getDefaultSensor(type);

        if(sensor != null)
        {
            StringBuilder build = new StringBuilder("");
            build.append("Sensor Type: " + sensor.getType() + "\n");
            build.append("Sensor Vendor: " + sensor.getVendor() + "\n");
            build.append("Sensor Name: " + sensor.getName() + "\n");
            build.append("Sensor Range: " + sensor.getMaximumRange() + "\n");
            build.append("Sensor Power: " + sensor.getPower() + "\n");
            mandata.setText(build.toString());

        }







    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensor != null)
        snmng.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(sensor != null)
            snmng.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        snmng = null;
        sensor = null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        StringBuilder build = new StringBuilder("");
        build.append("Time Stamp: "+sensorEvent.timestamp+"\n");
        build.append("Sensor Current Accuracy: "+sensorEvent.accuracy+"\n");
        build.append("Value\n");
        for(int i = 0; i<sensorEvent.values.length;i++)
        {
            build.append("Value"+(i+1)+": "+sensorEvent.values[i]+"\n");
        }

        senread.setText(build.toString());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

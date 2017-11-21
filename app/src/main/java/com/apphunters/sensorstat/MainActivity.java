package com.apphunters.sensorstat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensmng;
    Sensor sensor;
    String manInfo;
    boolean typeset;
    TextView sensdata;
    ListView listview;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context home = this;
        sensmng = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);


        final List<Sensor> senList = sensmng.getSensorList(Sensor.TYPE_ALL);
        listview = (ListView) findViewById(R.id.sensorList);
        ArrayList<String> datalist = new ArrayList<>();
        ArrayList<Sensor> sortedList = new ArrayList<>(senList);
        Collections.sort(sortedList, new Comparator<Sensor>() {
            @Override
            public int compare(Sensor sensor, Sensor t1) {
                if(sensor == null)
                    return -1;
                if(t1 == null)
                    return 1;

                if(sensor.getType() > t1.getType())
                    return 1;
                else if(sensor.getType()== t1.getType())
                    return 0;
                else
                    return -1;



            }
        });



        for(int i = 0; i<sortedList.size(); i++)
        {
            sensor = sortedList.get(i);
            if(sensor != null) {
                StringBuilder build = new StringBuilder("");
                build.append("Sensor Type: " + sensor.getType() + "\n");
                build.append("Sensor Vendor: " + sensor.getVendor() + "\n");
                build.append("Sensor Name: " + sensor.getName() + "\n");
                build.append("Sensor Range: " + sensor.getMaximumRange() + "\n");
                build.append("Sensor Wake Status: " + sensor.isWakeUpSensor() + "\n");
                manInfo = build.toString();
             datalist.add(build.toString());
            }


        }




        ArrayAdapter<String> adaptlist = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datalist);


        listview.setAdapter(adaptlist);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent tent = new Intent(home,SensorDetials.class);
                tent.putExtra("type", senList.get(i).getType());
                startActivity(tent);



            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

       // sensmng.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }






    @Override
    protected void onPause() {
        super.onPause();
        //sensmng.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //sensmng = null;
        //sensor = null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        StringBuilder build = new StringBuilder("");

            build.append(manInfo);
            build.append(setdata(sensorEvent));
            sensdata.setText(build.toString());







    }


    private String setdata(SensorEvent event)
    {
        StringBuilder build = new StringBuilder("");

        build.append("Sensor Reading Time: "+event.timestamp+"\n");
        build.append("Sensor Readings : \n");
        for(int i = 0; i<event.values.length;i++)
                build.append(event.values[i]+"\n");

        return build.toString();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

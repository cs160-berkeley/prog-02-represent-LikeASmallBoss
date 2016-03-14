package com.example.qingyuwang.test;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.WearableListenerService;

import java.util.Random;



public class Shake extends WearableListenerService implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Context context;
    private long lastUpdate = -1;
    private float x, y, z;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 800;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        Toast.makeText(this, "My Shaker Service Created", Toast.LENGTH_LONG).show();
        Log.d("T", "sensor created");
        context = Shake.this.getApplicationContext();
    }


    @Override
    public void onDestroy() {
        sensorManager.unregisterListener((SensorEventListener) this, sensor);
        super.onDestroy();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("T", "sensored");
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z)
                        / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD) {
//                    Random rand = new Random();
//                    int newzip = 10000 + rand.nextInt(90000);
//                    ((Helper) this.getApplication()).setZip(newzip);
//                    Intent intent = new Intent(this, Main2Activity.class);
//                    intent.putExtra("ZIP", newzip);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    Toast.makeText(context, "Random Zipcode: " + Integer.toString(newzip), Toast.LENGTH_LONG).show();

                    Log.d("TAG", "shaked");
                    Toast.makeText(this, "Shaked", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    intent.putExtra("R", "R");
                    startService(intent);

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

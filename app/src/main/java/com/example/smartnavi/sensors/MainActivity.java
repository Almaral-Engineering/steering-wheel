package com.example.smartnavi.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private GyroControl mGyroControl;
    private NeedleControl mNeedleControl;

    private SensorManager mSensorManager;
    private Sensor mLight;
    private TextView values_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //values_text = (TextView) findViewById(R.id.sensors);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mGyroControl = (GyroControl) findViewById(R.id.gyro_control);
        mNeedleControl = (NeedleControl) findViewById(R.id.needle_control);

        /*List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        String mySensors = "";

        for(Sensor sensor : deviceSensors){
            mySensors = mySensors + "\n" + sensor.toString();
        }

        sensors.setText(mySensors);*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        //float lux = event.values[0];
        /*String myValues = "";
        for (int i = 0 ; i < 3 ; ++i){
            myValues = myValues + "\n" + event.values[i];

        }

        values_text.setText(myValues);*/
        double value = event.values[1];
        double degrees = -(180 * value)/(9.8);
        if(degrees > 100){
            degrees = 100;
        }
        else if(degrees < -100) {
            degrees = -100;
        }

        // Do something with this sensor value.
        mGyroControl.setDegrees((float)degrees);

        double needleValue = event.values[2];
        double needleDegrees = ((180 * needleValue)/(9.8));

        if(needleDegrees > 90){
            needleDegrees = 90;
        }
        else if(needleDegrees < 0) {
            needleDegrees = -0;
        }
        //double viewNeedleDegrees = (needleDegrees * 90)/180;

        Log.d("degrees", (float) needleDegrees + "");

        mNeedleControl.setDegrees((float) needleDegrees);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

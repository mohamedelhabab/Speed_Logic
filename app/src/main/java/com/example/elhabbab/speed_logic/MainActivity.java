package com.example.elhabbab.speed_logic;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

// TODO: 4/3/2017 Try To Encapsulate This Logic In Service To Run In Background
// TODO: 4/3/2017 Copy This Logic To The Primary Project



public class MainActivity extends AppCompatActivity {

    //timer class
    private Timer myTimer;

    //variables used in speed_test function later
    int counter;
    boolean report;

//clases needed for the location speed
    LocationManager locManager;
    LocationListener li;



TimerTask timertask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        li = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Float thespeed=(location.getSpeed()*(3600/1000));//speed in km/h

                //default unit for measurement is meter/second
                //we mutliplied of 3600 to convert secs into 1 hour
                //we divided on 1000 to convert the meter into km

                //Report Activity Will open if report value returned by speed_test Function is true
                //instead of showing the status message in a Toast
              //  Toast.makeText(MainActivity.this,"Speed : "+thespeed, Toast.LENGTH_SHORT).show();

                TextView text1=(TextView)findViewById(R.id.textView);

                text1.setText(thespeed.toString());



                if (! speed_test(thespeed)){

                    //don't do anything here

                    //status message
                    Toast.makeText(MainActivity.this,"Status : Report Closed  ", Toast.LENGTH_SHORT).show();

                }
                else if ( speed_test(thespeed)){

                    //write the code to navigate to the Report Activity From Here

                    //delete the toast
                    Toast.makeText(MainActivity.this,"Status : Report Opened  ", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(MainActivity.this, "Provider Enabled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this, "Provider Disabled", Toast.LENGTH_SHORT).show();
            }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, li);
        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, li);


    /*    myTimer = new Timer();
        myTimer.schedule(timertask=new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 10000);*/

    }

    @Override
    protected void onPause() {
        super.onPause();

        locManager.removeUpdates(li);
        Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show();
    }

    //Timer Code
   /* private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }


    @Override

    protected void onStop() {
        super.onStop();
timertask.cancel();

        myTimer.cancel();
        myTimer.purge();


        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();

    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.

            //Do something to the UI thread here

         //   Toast.makeText(MainActivity.this, "Report Wanted :"+speed_test(18.6), Toast.LENGTH_SHORT).show();

            // TODO: 4/3/2017 Capture Device Speed From GPS

            //Accident Report




        }
    };*/


    //speed test function to make decesions if the obtained speed less or greater than 20 km
    public boolean speed_test(float speed){
        if (speed <20)
        {
            counter++;
//Todo : change counter to 15 or any desired number
            if (counter==5)
            {
                report = true;
                counter = 0;
            }
        }
        else
        {
             report=false;counter=0;
        }

        return report;}

}

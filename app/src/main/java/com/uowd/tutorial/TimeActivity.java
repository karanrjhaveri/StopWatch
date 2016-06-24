package com.uowd.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Dev on 5/6/2015.
 */
public class TimeActivity extends Activity {
    private Handler mHandler = new Handler();
    private long startTime;
    private long elapsedTime;
    private final int REFRESH_RATE = 100;
    private String hours, minutes, seconds, milliseconds;
    private long secs, mins, hrs, msecs;
    private boolean stopped = false;
    private ListView listView;
    private ArrayList<String> laps = new ArrayList<String>();
    private int lapCtr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        //Intent base = getIntent();
        ((Button) findViewById(R.id.btn_stop)).setEnabled(false); // added code
        ((Button) findViewById(R.id.btn_stop)).setVisibility(View.GONE); // added code
        ((Button) findViewById(R.id.btn_clearLaps)).setVisibility(View.GONE); // Clean UI
        ((Button) findViewById(R.id.btn_addLap)).setVisibility(View.GONE); // Clean UI
        ((Button) findViewById(R.id.btn_reset)).setVisibility(View.GONE); // Clean UI
        listView=((ListView) findViewById(R.id.myListView)); // added code
        lapCtr=0; // added code
        //getActionBar().setTitle("Hello world App");
        //getSupportActionBar().setTitle("Hello world App");  // provide compatibility to all the versions
    }

    /*
    * This is the function that will update the time
    * it will check the float time that was passed to it and set the text of the textview
    * that we have on our UI.
    * */
    private void updateTimer(float time) {

        secs = (long) (time / 1000);
        mins = (long) ((time / 1000) / 60);
        hrs = (long) (((time / 1000) / 60) / 60); /* Convert the seconds to String * and format to ensure it has * a leading zero when required */
        secs = secs % 60;
        seconds = String.valueOf(secs);
        if (secs == 0) {
            seconds = "00";
        }
        if (secs < 10 && secs > 0) {
            seconds = "0" + seconds;
        } /* Convert the minutes to String and format the String */
        mins = mins % 60;
        minutes = String.valueOf(mins);
        if (mins == 0) {
            minutes = "00";
        }
        if (mins < 10 && mins > 0) {
            minutes = "0" + minutes;
        } /* Convert the hours to String and format the String */
        hours = String.valueOf(hrs);
        if (hrs == 0) {
            hours = "00";
        }
        if (hrs < 10 && hrs > 0) {
            hours = "0" + hours;
        } /* Although we are not using milliseconds on the timer in this example * I included the code in the event that you wanted to include it on your own */
        // milliseconds = String.valueOf((long) time); -- original code
        milliseconds = String.valueOf((long) time%60000); // modified original code
        if (milliseconds.length() == 2) {
            milliseconds = "0" + milliseconds;
        }
        if (milliseconds.length() <= 1) {
            milliseconds = "00";
        }
        /*UPDATE UI*/
       ((TextView) findViewById(R.id.timer)).setText(hours+":"+minutes+":"+seconds); // added code
       ((TextView) findViewById(R.id.timerMs)).setText("." + milliseconds); // added code
    }

    private Runnable startTimer = new Runnable() {
        /*
        THREADS
        * We have used the run function
        * get the elapsed time and set it to the variable elapsedTime
        * call the update timer function and give it the elapsed time
        * */
        public void run() {
            //add your code here
            // find the time that has passed since the timer started -- added
            // pass the number to the update timer function -- added
            elapsedTime = System.currentTimeMillis() - startTime; // added code
            updateTimer(elapsedTime); // added code

            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };

    public void startClick(View view) {
/*GOOD UI*/
        //write code to disable other buttons
        ((Button) findViewById(R.id.btn_start)).setEnabled(false); // added code
        ((Button) findViewById(R.id.btn_reset)).setEnabled(false); // added code
        ((Button) findViewById(R.id.btn_stop)).setEnabled(true); // added code
        ((Button) findViewById(R.id.btn_start)).setVisibility(View.GONE); // added code
        ((Button) findViewById(R.id.btn_reset)).setVisibility(View.GONE); // added code
        ((Button) findViewById(R.id.btn_stop)).setVisibility(View.VISIBLE); // added code
        if (stopped) {
            startTime = System.currentTimeMillis() - elapsedTime;
        } else {
            startTime = System.currentTimeMillis();
        }
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
        if (stopped == false)
        Log.d("KaranTimeActivity", "Invoked startClick"); // added code
        else
        Log.d("KaranTimeActivity", "Invoked resumeClick");

        ((Button) findViewById(R.id.btn_addLap)).setVisibility(View.VISIBLE); // Clean UI
    }

    public void stopClick(View view) {
/*GOOD UI*/
       //write code to disable other buttons
        ((Button) findViewById(R.id.btn_start)).setEnabled(true); // added code
        ((Button) findViewById(R.id.btn_reset)).setEnabled(true); // added code
        ((Button) findViewById(R.id.btn_stop)).setEnabled(false); // added code
        ((Button) findViewById(R.id.btn_start)).setVisibility(View.VISIBLE); // added code
        ((Button) findViewById(R.id.btn_reset)).setVisibility(View.VISIBLE); // added code
        ((Button) findViewById(R.id.btn_stop)).setVisibility(View.GONE); // added code
        mHandler.removeCallbacks(startTimer);
        stopped = true;

        ((Button) findViewById(R.id.btn_start)).setText("Resume Timer");
    }

    public void resetClick(View view) {
        stopped = false;
/*GOOD UI*/
        //write code to disable other buttons
        ((Button) findViewById(R.id.btn_start)).setEnabled(true); // added code
        ((Button) findViewById(R.id.btn_reset)).setEnabled(false); // added code
        ((Button) findViewById(R.id.btn_stop)).setEnabled(false); // added code
        ((Button) findViewById(R.id.btn_start)).setVisibility(View.VISIBLE); // added code
        ((Button) findViewById(R.id.btn_reset)).setVisibility(View.GONE); // added code
        ((Button) findViewById(R.id.btn_stop)).setVisibility(View.GONE); // added code
        ((TextView) findViewById(R.id.timer)).setText("00:00:00");
        ((TextView) findViewById(R.id.timerMs)).setText(".0000");
        hours="00"; minutes = "00"; seconds = "00"; milliseconds = "0000";

        ((Button) findViewById(R.id.btn_start)).setText("Start Timer");
        ((Button) findViewById(R.id.btn_addLap)).setVisibility(View.GONE);
    }

    public void addLap(View view) {
        lapCtr++; // added code
        laps.add(lapCtr + " - " + hours+":"+minutes+":"+seconds+"." + milliseconds); // added code
        ArrayAdapter adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, laps ); // added code
        listView.setAdapter(adapter); // added code
        ((Button) findViewById(R.id.btn_clearLaps)).setVisibility(View.VISIBLE);
    }

    public void clearLaps(View view) {
        lapCtr=0;
        laps.clear();
        ArrayAdapter adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, laps );
        listView.setAdapter(adapter);
        ((Button) findViewById(R.id.btn_clearLaps)).setVisibility(View.GONE);
    }
}

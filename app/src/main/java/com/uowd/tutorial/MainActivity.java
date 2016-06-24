package com.uowd.tutorial;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*this line states what view to show for this activity
        the view that you show must be related to the functions that are performed
        in the activity
        */
        setContentView(R.layout.activity_main);
    }

    //this is the click handler for the move to list_style
    public void nextClick(View view) {
        //you need to write code to move from this activity to the next activity.
        Intent intent = new Intent(this, TimeActivity.class); // this -> refers to context | Activity is a subclass of context

        /*
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,TimeActivity.class);
         */
        startActivity(intent);
    }
}

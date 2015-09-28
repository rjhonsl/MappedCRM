package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 9/28/2015.
 */
public class Activity_AddPond extends Activity {

    Intent passedintentt;
    int custid = 0;

    Activity activity;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpond);
        activity = this;
        context = Activity_AddPond.this;
        passedintentt =  getIntent();
        if (passedintentt != null) {
            if (passedintentt.hasExtra("custid")) {
                custid = passedintentt.getIntExtra("custid", 0);
            }
        }

    }
}

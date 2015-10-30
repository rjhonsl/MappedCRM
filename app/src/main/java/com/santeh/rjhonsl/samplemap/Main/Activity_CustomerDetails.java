package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 10/30/2015.
 */
public class Activity_CustomerDetails extends Activity {

    Activity activity;
    Context context;

    GpsDB_Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        db = new GpsDB_Query(this);
        context = Activity_CustomerDetails.this;
        activity = this;
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

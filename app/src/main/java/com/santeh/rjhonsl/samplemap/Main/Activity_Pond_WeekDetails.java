package com.santeh.rjhonsl.samplemap.Main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 9/30/2015.
 */
public class Activity_Pond_WeekDetails extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond_weekly_details);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

    }
}

package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 7/31/2015.
 */
public class Activity_Settings extends Activity{

    TextView txtTitle, txtSettings_custinfo, txtAbout, txtChangeLog, txtCustActivity, txtCustMonitoring;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = Activity_Settings.this;

        txtTitle = (TextView) findViewById(R.id.txt_settings_title);
        txtAbout = (TextView) findViewById(R.id.txt_settings_about);
        txtSettings_custinfo = (TextView) findViewById(R.id.txt_settings_farmInfo);
        txtChangeLog = (TextView) findViewById(R.id.txt_settings_changelog);
        txtCustActivity = (TextView) findViewById(R.id.txt_settings_customerInfo);
        txtCustMonitoring = (TextView) findViewById(R.id.txt_settings_customerMonitoring);

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtSettings_custinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Settings.this, Activity_ViewCustomerInfo.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        txtAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        txtChangeLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        txtCustActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        txtCustMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 9/19/2015.
 */
public class Activity_UserMonitoringOptions extends Activity{


    TextView txtArea, txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermonitoring_options);
        initViews();
        initListeners();
    }

    private void initListeners() {
        txtArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_UserMonitoringOptions.this, Activity_UserMonitoring_ViewByUser.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        txtArea = (TextView) findViewById(R.id.txt_usrmonitoring_area);
        txtUser = (TextView) findViewById(R.id.txt_usrmonitoring_user);
    }


}

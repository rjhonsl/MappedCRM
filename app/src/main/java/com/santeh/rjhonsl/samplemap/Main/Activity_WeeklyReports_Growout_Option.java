package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 8/28/2015.
 */
public class Activity_WeeklyReports_Growout_Option extends Activity {

    TextView current, prev, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeklyreports_growout_summary_option);

        current = (TextView) findViewById(R.id.txt_WR_growout_summary_this);
        prev    = (TextView) findViewById(R.id.txt_WR_growout_summary_prev);
        next    = (TextView) findViewById(R.id.txt_WR_growout_summary_next);

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity("current");
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gotoNextActivity("prev");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gotoNextActivity("next");
            }
        });


    }

    public void gotoNextActivity(String type){
        Intent intent = new Intent(Activity_WeeklyReports_Growout_Option.this, Activity_WeeklyReports_Growout_FeedDemands.class);
        intent.putExtra("type", type );
        startActivity(intent);
    }
}

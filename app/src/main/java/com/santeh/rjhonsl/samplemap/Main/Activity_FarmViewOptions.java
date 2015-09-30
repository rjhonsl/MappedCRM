package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 8/24/2015.
 */
public class Activity_FarmViewOptions extends Activity{

    String customerID="", farmname="";
    Activity activity;

    TextView txtManagePonds, txtWeeklyReports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infowindowselect_option);
        activity = this;

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            customerID = extras.getString("customerID");
            farmname = extras.getString("farmname");

        }else{
            customerID = "null";
        }

//        Helper.toastLong(activity, "id"+customerID);
        txtManagePonds = (TextView) findViewById(R.id.txt_farmoptions_managePonds);
        txtWeeklyReports = (TextView) findViewById(R.id.txt_farmoptions_WeeklyReports);


        txtManagePonds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int converted = Integer.parseInt(customerID.trim());
                Intent intent = new Intent(Activity_FarmViewOptions.this, Activity_ManagePonds.class);
                intent.putExtra("id", converted );
                intent.putExtra("farmname", farmname);
                startActivity(intent);

            }
        });

        txtWeeklyReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int converted = Integer.parseInt(customerID.trim());
                Intent intent = new Intent(Activity_FarmViewOptions.this, Activity_PondWeeklyConsumption.class);
                intent.putExtra("id", converted );
                intent.putExtra("farmname", farmname);
                startActivity(intent);
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

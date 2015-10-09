package com.santeh.rjhonsl.samplemap.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 10/7/2015.
 */
public class Activity_Add_CustomerInfomration_Basic extends FragmentActivity{

    ImageButton btnBack, btnNext;
    double lat=0, lng=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customerinformation_basicinfo);

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        if (getIntent().hasExtra("latitude")){lat= getIntent().getDoubleExtra("latitude", 0);}
        if (getIntent().hasExtra("longtitude")){lng= getIntent().getDoubleExtra("longtitude", 0);}


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Activity_Add_CustomerInfomration_Basic.this, Activity_Add_CustomerInfomration_Address.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longtitude", lng);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }
}


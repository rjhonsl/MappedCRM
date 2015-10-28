package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

/**
 * Created by rjhonsl on 10/7/2015.
 */
public class Activity_Add_CustomerInformation_Address extends FragmentActivity{

    Activity activity;
    Context context;
    double lat = 0, lng = 0;
    String farmid, fname, lname, mname, birthday, birthplace;
    ImageButton btnBack, btnNext;

    EditText edtHouseNumber, edtStreet, edtSubdivision, edtBarangay, edtCity, edtProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customerinformation_address);
        activity = this;
        context = Activity_Add_CustomerInformation_Address.this;
        Helper.hideKeyboardOnLoad(activity);

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnNext = (ImageButton) findViewById(R.id.btn_next);

        edtHouseNumber = (EditText) findViewById(R.id.edt_housenumber);
        edtStreet = (EditText) findViewById(R.id.edt_street);
        edtSubdivision = (EditText) findViewById(R.id.edt_subdivision);
        edtBarangay = (EditText) findViewById(R.id.edt_barangay);
        edtCity = (EditText) findViewById(R.id.edt_city);
        edtProvince = (EditText) findViewById(R.id.edt_province);


        if (getIntent().hasExtra("latitude")){lat= getIntent().getDoubleExtra("latitude", 0);}
        if (getIntent().hasExtra("longtitude")){lng= getIntent().getDoubleExtra("longtitude", 0);}
        if (getIntent().hasExtra("farmid")){farmid = getIntent().getStringExtra("farmid");}
        if (getIntent().hasExtra("fname")){fname = getIntent().getStringExtra("fname");}
        if (getIntent().hasExtra("lname")){lname = getIntent().getStringExtra("lname");}
        if (getIntent().hasExtra("mname")){mname = getIntent().getStringExtra("mname");}
        if (getIntent().hasExtra("birthday")){birthday = getIntent().getStringExtra("birthday");}
        if (getIntent().hasExtra("birthplace")){birthplace = getIntent().getStringExtra("birthplace");}

        TextView txtnote = (TextView) findViewById(R.id.txt_note);
//        txtnote.setText(lat + " "+lng +" " + farmid + " " + fname+ " " + lname + " " + mname+ " " + birthday+ " " + birthplace);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtHouseNumber.getText().toString().equalsIgnoreCase("") &&  !edtBarangay.getText().toString().equalsIgnoreCase("") &&
                        !edtCity.getText().toString().equalsIgnoreCase("") && !edtProvince.getText().toString().equalsIgnoreCase("")){

                    final Intent intent = new Intent(Activity_Add_CustomerInformation_Address.this, Activity_Add_CustomerInformation_ContactInfo.class);
                    intent.putExtra("latitude", lat);
                    intent.putExtra("longtitude", lng);

                    intent.putExtra("farmid",   farmid+"");
                    intent.putExtra("fname",    fname+"");
                    intent.putExtra("lname",    lname+"");
                    intent.putExtra("mname",    mname+"");
                    intent.putExtra("birthday", birthday+"");
                    intent.putExtra("birthplace", birthplace+"");

                    intent.putExtra("housenumber", edtHouseNumber.getText()+"");
                    intent.putExtra("street", edtStreet.getText()+"");
                    intent.putExtra("subdivision", edtSubdivision.getText()+"");
                    intent.putExtra("barangay", edtBarangay.getText()+"");
                    intent.putExtra("city", edtCity.getText()+"");
                    intent.putExtra("province", edtProvince.getText()+"");

                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }else{
                    Helper.createCustomThemedColorDialogOKOnly(activity, "Warning","You must complete fields with '*' to continue.", "OK", R.color.red);
                }
            }
        });
    }
}


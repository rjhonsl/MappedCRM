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

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.Calendar;

/**
 * Created by rjhonsl on 10/7/2015.
 */
public class Activity_Add_CustomerInformation_Basic extends FragmentActivity implements DatePickerDialog.OnDateSetListener{

    ImageButton btnBack, btnNext;
    double lat=0, lng=0;

    public static final String DATEPICKER_TAG = "datepicker";

    DatePickerDialog datePickerDialog;
    int y, m, d;

    Activity activity;
    Context context;

    EditText edtBirhday, edtFarmId, edtFname, edtMname, edtLname, edtBirthPlace;
    TextView txtnote, txttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customerinformation_basicinfo);

        activity = this;
        context = Activity_Add_CustomerInformation_Basic.this;

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnNext = (ImageButton) findViewById(R.id.btn_next);


        Helper.hideKeyboardOnLoad(activity);
        if (getIntent().hasExtra("latitude")){lat= getIntent().getDoubleExtra("latitude", 0);}
        if (getIntent().hasExtra("longtitude")){lng= getIntent().getDoubleExtra("longtitude", 0);}

        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        txttitle = (TextView) findViewById(R.id.title);
        txtnote = (TextView) findViewById(R.id.txt_note);
        edtBirhday = (EditText) findViewById(R.id.edt_birthday);
        edtFarmId = (EditText) findViewById(R.id.edt_farmid);
        edtFname = (EditText) findViewById(R.id.edt_fname);
        edtLname = (EditText) findViewById(R.id.edt_lname);
        edtMname = (EditText) findViewById(R.id.edt_mname);
        edtBirhday = (EditText) findViewById(R.id.edt_birthday);
        edtBirthPlace = (EditText) findViewById(R.id.edt_BirthPlace);

        txtnote.setText(+lat + " " + lng);

        edtBirhday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1980, 2030);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtFarmId.getText().toString().equalsIgnoreCase("") && !edtFname.getText().toString().equalsIgnoreCase("") &&
                        !edtMname.getText().toString().equalsIgnoreCase("") && !edtLname.getText().toString().equalsIgnoreCase("") &&
                        !edtBirhday.getText().toString().equalsIgnoreCase("") && !edtBirthPlace.getText().toString().equalsIgnoreCase("")){

                    final Intent intent = new Intent(Activity_Add_CustomerInformation_Basic.this, Activity_Add_CustomerInformation_Address.class);
                    intent.putExtra("latitude", lat);
                    intent.putExtra("longtitude", lng);
                    intent.putExtra("farmid",   edtFarmId.getText()+"");
                    intent.putExtra("fname",    edtFname.getText()+"");
                    intent.putExtra("lname",    edtLname.getText()+"");
                    intent.putExtra("mname",    edtMname.getText()+"");
                    intent.putExtra("birthday", edtBirhday.getText()+"");
                    intent.putExtra("birthplace", edtBirthPlace.getText()+"");
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }else {
                    Helper.createCustomThemedColorDialogOKOnly(activity, "Warning","You must complete fields with '*' to continue.", "OK", R.color.red);
                }

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        y = year;
        m = month+1;
        d = day;

        edtBirhday.setText(m+"/"+d+"/"+y);
    }
}


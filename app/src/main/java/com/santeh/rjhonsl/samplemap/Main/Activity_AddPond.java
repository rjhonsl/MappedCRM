package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.Calendar;

/**
 * Created by rjhonsl on 9/28/2015.
 */
public class Activity_AddPond extends FragmentActivity  implements DatePickerDialog.OnDateSetListener{

    Intent passedintentt;
    int custid = 0;

    EditText edtPondNumber, edtSpecie, edtABW, edtSurvivalRate, edtDateStocked, edtQuantity, edtArea, edtCultureSystem, edtRemarks;
    Button btnSave;

    public static final String DATEPICKER_TAG = "datepicker";

    DatePickerDialog datePickerDialog;
    int y, m, d;

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


        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        edtSpecie = (EditText) findViewById(R.id.edtSpecie);
        edtPondNumber = (EditText) findViewById(R.id.edtpondnumber);
        edtABW = (EditText) findViewById(R.id.edtAbw);
        edtSurvivalRate = (EditText) findViewById(R.id.edtSurvivalRate);
        edtDateStocked = (EditText) findViewById(R.id.edtDateStocked);
        edtQuantity = (EditText) findViewById(R.id.edtQuantity);
        edtArea = (EditText) findViewById(R.id.edtArea);
        edtCultureSystem = (EditText) findViewById(R.id.edtCultureSystem);
        edtRemarks = (EditText) findViewById(R.id.edtRemarks);
        btnSave = (Button) findViewById(R.id.btnSave);

        edtSpecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = Helper.variables.ARRAY_SPECIES;
                final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Species", R.color.deepteal_500);
                d.show();

                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        edtSpecie.setText( Helper.variables.ARRAY_SPECIES[position]);
                        d.hide();
                    }
                });
            }
        });

        edtDateStocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1980, 2030);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        edtCultureSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = Helper.variables.ARRAY_CULTURE_SYSTEM;
                final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Systems", R.color.deepteal_500);
                d.show();

                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        edtCultureSystem.setText(Helper.variables.ARRAY_CULTURE_SYSTEM[position]);
                        d.hide();
                    }
                });
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPondNumber.getText().toString().equalsIgnoreCase("")
                        || edtSpecie.getText().toString().equalsIgnoreCase("")
                        || edtABW.getText().toString().equalsIgnoreCase("")
                        || edtSurvivalRate.getText().toString().equalsIgnoreCase("")
                        || edtDateStocked.getText().toString().equalsIgnoreCase("")
                        || edtQuantity.getText().toString().equalsIgnoreCase("")
                        || edtCultureSystem.getText().toString().equalsIgnoreCase("")
                        || edtRemarks.getText().toString().equalsIgnoreCase("")
                        ) {
                    final Dialog d = Helper.createCustomThemedColorDialogOKOnly(activity, "Message","You have to complete all the following fields to continue.", "OK", R.color.red);
                    d.show();
                    Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.hide();

                            if (edtPondNumber.getText().toString().equalsIgnoreCase("")) {
                                edtPondNumber.requestFocus();
                            }else if (edtSpecie.getText().toString().equalsIgnoreCase("")) {
                                edtSpecie.requestFocus();
                            }else if (edtABW.getText().toString().equalsIgnoreCase("")) {
                                edtABW.requestFocus();
                            }else if (edtSurvivalRate.getText().toString().equalsIgnoreCase("")) {
                                edtSurvivalRate.requestFocus();
                            }else if (edtDateStocked.getText().toString().equalsIgnoreCase("")) {
                                edtDateStocked.requestFocus();
                            }else if (edtQuantity.getText().toString().equalsIgnoreCase("")) {
                                edtQuantity.requestFocus();
                            }else if (edtCultureSystem.getText().toString().equalsIgnoreCase("")) {
                                edtCultureSystem.requestFocus();
                            }else if (edtRemarks.getText().toString().equalsIgnoreCase("")) {
                                edtRemarks.requestFocus();
                            }
                        }

                    });
                }else{

                }
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        edtDateStocked.setText(month + "/" + day + "/"+year);
        y = year;
        m = month;
        d = day;
    }

}

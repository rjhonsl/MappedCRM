package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjhonsl on 9/28/2015.
 */
public class Activity_AddPond extends FragmentActivity  implements DatePickerDialog.OnDateSetListener{

    Intent passedintentt;
    int custid = 0;

    ProgressDialog PD;

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

        PD = new ProgressDialog(this);
        PD.setCancelable(false);

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
                    final Dialog d = Helper.createCustomThemedColorDialogOKOnly(activity, "Oops","You have to complete all the following fields to continue.", "OK", R.color.red);
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
                }else {
                    final Dialog x = Helper.createCustomDialogThemedYesNO(activity, "Save all information on database?", "Save", "NO", "YES",
                            R.color.green_400);
                    x.show();
                    Button no = (Button) x.findViewById(R.id.btn_dialog_yesno_opt1);
                    Button yes = (Button) x.findViewById(R.id.btn_dialog_yesno_opt2);
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            x.hide();
                        }
                    });

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            x.hide();
                            updateCustomerInfoDB(custid, Helper.variables.URL_INSERT_PONDINFO);
                        }
                    });

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



    public void updateCustomerInfoDB(final int customerID, final String url) {

            PD.setMessage("Updating database... ");
            PD.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            String responseCode = Helper.extractResponseCode(response);
                            String title, prompt;


                            if (responseCode.equalsIgnoreCase("0")){
                                oopsprompt();
                            }else if (responseCode.equalsIgnoreCase("1")) {

                                Logging.loguserAction(activity, activity.getBaseContext(), Helper.userActions.TSR.Edit_POND, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);
                                title = "SUCCESS";
                                prompt = "You have successfully updated database.";
                                PD.dismiss();

                                final Dialog d = Helper.createCustomThemedColorDialogOKOnly(Activity_AddPond.this, title,
                                        prompt, "OK", R.color.skyblue_500);
                                TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                                d.show();
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.hide();
                                        finish();
                                    }
                                });
                            }
                            else {
                                oopsprompt();
                            }

                        }

                        private void oopsprompt() {
                            String title="OOPS";
                            String prompt = "Something went wrong. Please try again later.";
                            PD.dismiss();

                            final Dialog d = Helper.createCustomDialogOKOnly(Activity_AddPond.this, title,
                                    prompt, "OK");
                            TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                            d.setCancelable(false);
                            d.show();
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.hide();
                                }
                            });
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();

                    final Dialog d = Helper.createCustomDialogOKOnly(Activity_AddPond.this, "OOPS",
                            "Something went wrong. error( "+ error +" )", "OK");
                    TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                    d.setCancelable(false);
                    d.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                    finish();
                            d.hide();
                        }
                    });
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();


                    params.put("specie", String.valueOf(edtSpecie.getText()));
                    params.put("pondid", String.valueOf(edtPondNumber.getText()));
                    params.put("dateStocked", String.valueOf(edtDateStocked.getText()));
                    params.put("quantity", String.valueOf(edtQuantity.getText()));
                    params.put("area", String.valueOf(edtArea.getText()));
                    params.put("culturesystem", String.valueOf(edtCultureSystem.getText()));
                    params.put("remarks", String.valueOf(edtRemarks.getText()));
//                    params.put("id", String.valueOf(ed));
                    params.put("customerId", String.valueOf(customerID));
                    params.put("sizeofStock", String.valueOf(edtABW.getText()));
                    params.put("survivalrate", String.valueOf(edtSurvivalRate.getText()));

                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);
    }

}

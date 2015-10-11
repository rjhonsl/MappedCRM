package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjhonsl on 10/7/2015.
 */
public class Activity_Add_CustomerInformation_Summary extends FragmentActivity{

    Activity activity;
    Context context;
    private double lat = 0, lng = 0;
    private String farmid, fname, lname, mname, birthday, birthplace;
    private ImageButton btnBack, btnNext;
    private String housenumber="", street="", subdivision="", city="", barangay="", province="", housestat="", telephone="",
            cellphone="", s_birthday="", s_fname="", s_lname="", s_mname="", civilstatus="";


    public static final String DATEPICKER_TAG = "datepicker";

    DatePickerDialog datePickerDialog;

    TextView txtFarmID, txtFname, txtMname, txtLname, txtBirthday, txtBirthPlace, txtHouseNumber, txtStreet, txtSubdivision, txtBarangay,
        txtCity, txtProvince, txttelephone, txtCellphone, txtHouseStatus, txtCivilStatus, txt_S_fname, txt_S_mname, txt_S_lname, txt_S_Birthday;

    LinearLayout ll;

    Dialog PD;
    TextView dialogmessage;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customerinformation_summary);
        activity = this;
        context = Activity_Add_CustomerInformation_Summary.this;
        Helper.hideKeyboardOnLoad(activity);

        PD = Helper.initProgressDialog(activity);
        dialogmessage = (TextView) PD.findViewById(R.id.progress_message);



        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnNext = (ImageButton) findViewById(R.id.btn_next);

        ll = (LinearLayout) findViewById(R.id.ll_ifNotSingle);


        if (getIntent().hasExtra("latitude")){lat= getIntent().getDoubleExtra("latitude", 0);}else{lat=0;}
        if (getIntent().hasExtra("longtitude")){lng= getIntent().getDoubleExtra("longtitude", 0);}else{lng=0;}
        if (getIntent().hasExtra("farmid")){farmid = getIntent().getStringExtra("farmid");}
        if (getIntent().hasExtra("fname")){fname = getIntent().getStringExtra("fname");}else{fname="";}
        if (getIntent().hasExtra("lname")){lname = getIntent().getStringExtra("lname");}else{lname="";}
        if (getIntent().hasExtra("mname")){mname = getIntent().getStringExtra("mname");}else{mname="";}
        if (getIntent().hasExtra("birthday")){birthday = getIntent().getStringExtra("birthday");}else{birthday="";}
        if (getIntent().hasExtra("birthplace")){birthplace = getIntent().getStringExtra("birthplace");}else{birthplace="";}

        if (getIntent().hasExtra("housenumber")){housenumber = getIntent().getStringExtra("housenumber");}else{housenumber ="";}
        if (getIntent().hasExtra("street")){street = getIntent().getStringExtra("street");}else{street ="";}
        if (getIntent().hasExtra("subdivision")){subdivision = getIntent().getStringExtra("subdivision");}else{subdivision ="";}
        if (getIntent().hasExtra("barangay")){barangay = getIntent().getStringExtra("barangay");}else{barangay ="";}
        if (getIntent().hasExtra("city")){city = getIntent().getStringExtra("city");}else{city ="";}
        if (getIntent().hasExtra("province")){province = getIntent().getStringExtra("province");}

        if (getIntent().hasExtra("tel")){telephone = getIntent().getStringExtra("tel");}else{telephone ="";}
        if (getIntent().hasExtra("cel")){cellphone = getIntent().getStringExtra("cel");}else{cellphone ="";}
        if (getIntent().hasExtra("housestat")){housestat = getIntent().getStringExtra("housestat");}else{housestat ="";}

        if (getIntent().hasExtra("civilstatus")){civilstatus = getIntent().getStringExtra("civilstatus");}else{civilstatus ="";}
        if (getIntent().hasExtra("s_fname")){s_fname = getIntent().getStringExtra("s_fname");}else{s_fname =" --- ";}
        if (getIntent().hasExtra("s_mname")){s_mname = getIntent().getStringExtra("s_mname");}else{s_mname =" --- ";}
        if (getIntent().hasExtra("s_lname")){s_lname = getIntent().getStringExtra("s_lname");}else{s_lname =" --- ";}
        if (getIntent().hasExtra("s_birthday")){s_birthday = getIntent().getStringExtra("s_birthday");}else{s_birthday =" --- ";}

        txtFarmID = (TextView) findViewById(R.id.txt_farmid);
        txtFname = (TextView) findViewById(R.id.txt_fname);
        txtMname = (TextView) findViewById(R.id.txt_mname);
        txtLname = (TextView) findViewById(R.id.txt_lname);
        txtBirthday = (TextView) findViewById(R.id.txt_birthday);
        txtBirthPlace = (TextView) findViewById(R.id.txt_birthPlace);
        txtHouseNumber = (TextView) findViewById(R.id.txt_houseNumber);
        txtStreet = (TextView) findViewById(R.id.txt_street);
        txtSubdivision = (TextView) findViewById(R.id.txt_subdivision);
        txtBarangay = (TextView) findViewById(R.id.txt_barangay);
        txtCity = (TextView) findViewById(R.id.txt_city);
        txtProvince = (TextView) findViewById(R.id.txt_province);
        txttelephone = (TextView) findViewById(R.id.txt_telephone);
        txtCellphone = (TextView) findViewById(R.id.txt_cellphone);
        txtHouseStatus = (TextView) findViewById(R.id.txt_houseStatus);
        txtCivilStatus = (TextView) findViewById(R.id.txt_civilStatus);
        txt_S_fname = (TextView) findViewById(R.id.txt_s_fname);
        txt_S_mname = (TextView) findViewById(R.id.txt_s_mname);
        txt_S_lname = (TextView) findViewById(R.id.txt_s_lname);
        txt_S_Birthday = (TextView) findViewById(R.id.txt_s_birthday);


        if (s_fname.equalsIgnoreCase("")){
            s_fname =" --- ";
        }

        if (s_mname.equalsIgnoreCase("")){
            s_mname =" --- ";
        }

        if (s_lname.equalsIgnoreCase("")){
            s_lname =" --- ";
        }

        if (s_birthday.equalsIgnoreCase("")){
            s_birthday =" --- ";
        }
        if (subdivision.equalsIgnoreCase("")){
            subdivision =" --- ";
        }
        if (telephone.equalsIgnoreCase("")){
            telephone =" --- ";
        }
        if (street.equalsIgnoreCase("")){
            street =" --- ";
        }

        String[] splitter;
        if (!birthday.equalsIgnoreCase("")){
            splitter = birthday.split("/");
            birthday = splitter[2] + "-" + splitter[0] + "-" + splitter[1];
        }

        if (!s_birthday.equalsIgnoreCase("")){
            splitter = s_birthday.split("/");
            s_birthday = splitter[2] + "-" + splitter[0] + "-" + splitter[1];
        }


        txtFarmID.setText(farmid + " ");
        txtFname.setText(fname + " ");
        txtMname.setText(mname + " ");
        txtLname.setText(lname + " ");
        txtBirthday.setText(birthday + " ");
        txtBirthPlace.setText(birthplace +" ");
        txtHouseNumber.setText(housenumber +" ");
        txtStreet.setText(street + " ");
        txtSubdivision.setText(subdivision+" ");
        txtBarangay.setText(barangay +" ");
        txtCity.setText(city +" ");
        txtProvince.setText(province + " ");
        txttelephone.setText(telephone+" ");
        txtCellphone.setText(cellphone+" ");
        txtHouseStatus.setText(housestat+" ");
        txtCivilStatus.setText(civilstatus+" ");
        txt_S_fname.setText(s_fname+" ");
        txt_S_mname.setText(s_mname+" ");
        txt_S_lname.setText(s_lname+" ");
        txt_S_Birthday.setText(s_birthday+" ");


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = Helper.createCustomDialogThemedYesNO(activity, "Add " + fname + " " + lname + " to customer information?",
                        "Save", "YES", "NO", R.color.blue);
                d.show();

                Button btnYes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                Button btnNo = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                        insertCustomerMainInfo(Helper.variables.URL_INSERT_MAIN_CUSTOMERINFO);
                    }
                });

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
            }
        });
    }


    public void insertCustomerMainInfo(final String url) {

        PD.show();
        dialogmessage.setText("Updating. Please wait...");

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String responseCode = Helper.extractResponseCode(response);
                        PD.hide();


                        if (!responseCode.equalsIgnoreCase("0")){

                            final Dialog d = Helper.createCustomThemedColorDialogOKOnly(activity, "Save", "Saving successfull. ", "OK", R.color.amber_600);

                            Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, MapsActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish(); // call this to finish the current activity

                                    //ugygff
                                }
                            });

                            Logging.loguserAction(activity, activity.getBaseContext(), Helper.userActions.TSR.ADD_MAIN_CUSTOMERINFO, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);


                        }else{

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();

                final Dialog d = Helper.createCustomDialogOKOnly(activity, "OOPS",
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

                params.put("deviceid", Helper.getMacAddress(activity));
                params.put("username", Helper.variables.getGlobalVar_currentUsername(activity));
                params.put("password", Helper.variables.getGlobalVar_currentUserpassword(activity));
                params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                params.put("userlvl", Helper.variables.getGlobalVar_currentlevel(activity)+"");

                params.put("mci_lname", lname+"");
                params.put("mci_fname", fname+"");
                params.put("mci_mname", mname+"");
                params.put("mci_farmid", farmid+"");
                params.put("mci_housenumber", housenumber+"");
                params.put("mci_street", street+"");
                params.put("mci_subdivision", subdivision+"");
                params.put("mci_barangay", barangay+"");
                params.put("mci_city", city+"");
                params.put("mci_province", province+"");
                params.put("mci_customerbirthday", birthday+"");
                params.put("mci_birthplace", birthplace+"");
                params.put("mci_telephone", telephone+"");
                params.put("mci_cellphone", cellphone+"");
                params.put("mci_civilstatus", civilstatus+"");
                params.put("mci_s_fname", s_fname+"");
                params.put("mci_s_lname", s_lname+"");
                params.put("mci_s_mname", s_mname+"");
                params.put("mci_s_birthday", s_birthday+"");
                params.put("mci_housestatus", housestat+"");
                params.put("mci_latitude", lat+""); // sample test
                params.put("mci_longitude", lng+"");


                return params;
            }
        };

        // Adding request to request queue
        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }

}


package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customerinformation_summary);
        activity = this;
        context = Activity_Add_CustomerInformation_Summary.this;
        Helper.hideKeyboardOnLoad(activity);

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

            }
        });

    }


}


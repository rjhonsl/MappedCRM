package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.Calendar;

/**
 * Created by rjhonsl on 10/7/2015.
 */
public class Activity_Add_CustomerInformation_Summary extends FragmentActivity implements DatePickerDialog.OnDateSetListener{

    Activity activity;
    Context context;
    private double lat = 0, lng = 0;
    private String farmid, fname, lname, mname, birthday, birthplace;
    private ImageButton btnBack, btnNext;
    private String housenumber, street, subdivision, city, barangay, province, housestat, telephone, cellphone, s_birthday, s_fname, s_lname, s_mname, civilstatus;

    EditText edtfname, edtmname, edtlname, edtBirthday, edtCivilStatus;
    LinearLayout ll;

    public static final String DATEPICKER_TAG = "datepicker";

    DatePickerDialog datePickerDialog;

    TextView txtFarmID, txtFname, txtMname, txtLname, txtBirthday, txtBirthPlace, txtHouseNumber, txtStreet, txtSubdivision, txtBarangay,
        txtCity, txtProvince, txttelephone, txtCellphone, txtHouseStatus, txtCivilStatus, txt_S_fname, txt_S_mname, txt_S_lname, txt_S_Birthday;

    int y, m, d;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customerinformation_spouse);
        activity = this;
        context = Activity_Add_CustomerInformation_Summary.this;
        Helper.hideKeyboardOnLoad(activity);

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        edtfname = (EditText) findViewById(R.id.edt_telephone);
        edtlname = (EditText) findViewById(R.id.edt_fname);
        edtmname = (EditText) findViewById(R.id.edt_mname);
        edtlname = (EditText) findViewById(R.id.edt_lname);
        edtBirthday = (EditText) findViewById(R.id.edt_birthday);
        edtCivilStatus = (EditText) findViewById(R.id.edt_civilStatus);
        ll = (LinearLayout) findViewById(R.id.ll_ifNotSingle);

        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


//        edt = (EditText) findViewById(R.id.edt_cellphone);
//        edtHouseStatus = (EditText) findViewById(R.id.edt_housestatus);


        if (getIntent().hasExtra("latitude")){lat= getIntent().getDoubleExtra("latitude", 0);}
        if (getIntent().hasExtra("longtitude")){lng= getIntent().getDoubleExtra("longtitude", 0);}
        if (getIntent().hasExtra("farmid")){farmid = getIntent().getStringExtra("farmid");}
        if (getIntent().hasExtra("fname")){fname = getIntent().getStringExtra("fname");}
        if (getIntent().hasExtra("lname")){lname = getIntent().getStringExtra("lname");}
        if (getIntent().hasExtra("mname")){mname = getIntent().getStringExtra("mname");}
        if (getIntent().hasExtra("birthday")){birthday = getIntent().getStringExtra("birthday");}
        if (getIntent().hasExtra("birthplace")){birthplace = getIntent().getStringExtra("birthplace");}

        if (getIntent().hasExtra("housenumber")){housenumber = getIntent().getStringExtra("housenumber");}
        if (getIntent().hasExtra("street")){street = getIntent().getStringExtra("street");}
        if (getIntent().hasExtra("subdivision")){subdivision = getIntent().getStringExtra("subdivision");}
        if (getIntent().hasExtra("barangay")){barangay = getIntent().getStringExtra("barangay");}
        if (getIntent().hasExtra("city")){city = getIntent().getStringExtra("city");}
        if (getIntent().hasExtra("province")){province = getIntent().getStringExtra("province");}

        if (getIntent().hasExtra("tel")){telephone = getIntent().getStringExtra("tel");}
        if (getIntent().hasExtra("cel")){cellphone = getIntent().getStringExtra("cel");}
        if (getIntent().hasExtra("housestat")){housestat = getIntent().getStringExtra("housestat");}

        if (getIntent().hasExtra("civilstatus")){civilstatus = getIntent().getStringExtra("civilstatus");}
        if (getIntent().hasExtra("s_fname")){s_fname = getIntent().getStringExtra("s_fname");}
        if (getIntent().hasExtra("s_mname")){s_mname = getIntent().getStringExtra("s_mname");}
        if (getIntent().hasExtra("s_lname")){s_lname = getIntent().getStringExtra("s_lname");}
        if (getIntent().hasExtra("s_birthday")){s_birthday = getIntent().getStringExtra("s_birthday");}


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


        txtFarmID.setText(farmid);
        txtFname.setText(fname);
        txtMname.setText(mname);
        txtLname.setText(lname);
        txtBirthday.setText(birthday);
        txtBirthPlace.setText(birthplace);
        txtHouseNumber.setText(housenumber);
        txtStreet.setText(street);
        txtSubdivision.setText(subdivision);
        txtBarangay.setText(barangay);
        txtCity.setText(city);
        txtProvince.setText(province);
        txttelephone.setText(telephone);
        txtCellphone.setText(cellphone);
        txtHouseStatus.setText(housestat);
        txtCivilStatus.setText(civilstatus);
        txt_S_fname.setText(s_fname);
        txt_S_mname.setText(s_mname);
        txt_S_lname.setText(s_lname);
        txt_S_Birthday.setText(s_birthday);

        if (civilstatus.equalsIgnoreCase("Married")){
            ll.setVisibility(View.VISIBLE);
        }else{
            ll.setVisibility(View.GONE);
        }

        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1980, 2030);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        edtCivilStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] options = {"Single", "Married", "Widowed"};
                final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Status", R.color.deepteal_500);
                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        edtCivilStatus.setText(options[position]);
                        d.hide();

                        if (options[position].equalsIgnoreCase("married")) {
                            ll.setVisibility(View.VISIBLE);
                        } else {
                            ll.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (edtCivilStatus.getText().toString().equalsIgnoreCase("married")) {
                    if (!edtCivilStatus.getText().toString().equalsIgnoreCase("") && !edtBirthday.getText().toString().equalsIgnoreCase("")
                            && !edtlname.getText().toString().equalsIgnoreCase("") && !edtmname.getText().toString().equalsIgnoreCase("")
                            && !edtmname.getText().toString().equalsIgnoreCase("")) {
                    }
                } else {
                    if (!edtCivilStatus.getText().toString().equalsIgnoreCase("")) {
                    }
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        y = year;
        m = month+1;
        d = day;

        edtBirthday.setText(m+"/"+d+"/"+y);
    }
}


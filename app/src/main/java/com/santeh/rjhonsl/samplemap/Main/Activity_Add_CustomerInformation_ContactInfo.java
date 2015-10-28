package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

/**
 * Created by rjhonsl on 10/7/2015.
 */
public class Activity_Add_CustomerInformation_ContactInfo extends FragmentActivity{

    Activity activity;
    Context context;
    private double lat = 0, lng = 0;
    private String farmid, fname, lname, mname, birthday, birthplace;
    private ImageButton btnBack, btnNext;
    private String housenumber, street, subdivision, city, barangay, province;

    EditText edtTelephone, edtCellphone, edtHouseStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customerinformation_contactinfo);
        activity = this;
        context = Activity_Add_CustomerInformation_ContactInfo.this;
        Helper.hideKeyboardOnLoad(activity);

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        edtTelephone = (EditText) findViewById(R.id.edt_telephone);
        edtCellphone = (EditText) findViewById(R.id.edt_cellphone);
        edtHouseStatus = (EditText) findViewById(R.id.edt_housestatus);

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


        edtHouseStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] options = {"Owned","Rented"};
                final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Status", R.color.deepteal_500);
                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        edtHouseStatus.setText(options[position]);
                        d.hide();
                    }
                });
            }
        });

        TextView txtnote = (TextView) findViewById(R.id.txt_note);
//        txtnote.setText(lat + " "+lng +" " + farmid + " " + fname+ " " + lname + " " + mname+ " " + birthday+ " " + birthplace +
//                " " + housenumber + " " + street + " " + subdivision + " " + barangay + " " + city + " " + province);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtCellphone.getText().toString().equalsIgnoreCase("") &&  !edtHouseStatus.getText().toString().equalsIgnoreCase("")){

                    final Intent intent = new Intent(Activity_Add_CustomerInformation_ContactInfo.this, Activity_Add_CustomerInformation_SpouseInfo.class);
                    intent.putExtra("latitude", lat);
                    intent.putExtra("longtitude", lng);

                    intent.putExtra("farmid",   farmid+"");
                    intent.putExtra("fname",    fname+"");
                    intent.putExtra("lname",    lname+"");
                    intent.putExtra("mname",    mname+"");
                    intent.putExtra("birthday", birthday+"");
                    intent.putExtra("birthplace", birthplace+"");

                    intent.putExtra("housenumber", housenumber+"");
                    intent.putExtra("street", street+"");
                    intent.putExtra("subdivision", subdivision+"");
                    intent.putExtra("barangay", barangay+"");
                    intent.putExtra("city", city+"");
                    intent.putExtra("province", province+"");

                    intent.putExtra("tel", edtTelephone.getText()+"");
                    intent.putExtra("cel", edtCellphone.getText()+"");
                    intent.putExtra("housestat", edtHouseStatus.getText() + "");

                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }else{
                    Helper.createCustomThemedColorDialogOKOnly(activity, "Warning","You must complete fields with '*' to continue.", "OK", R.color.red);
                }
            }
        });
    }
}


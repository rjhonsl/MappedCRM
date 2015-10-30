package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.DBase.GpsSQLiteHelper;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.CustAndPondParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 10/30/2015.
 */
public class Activity_CustomerDetails extends Activity {

    Activity activity;
    Context context;

    List<CustInfoObject> custInfoObjectList;

    GpsDB_Query db;
    String id;
    ProgressDialog PD;

    TextView txtmiddlename, txtfirstname, txtlastname, txtbirthday, txtfarmid, txtBirthPlace, txtHouseNumber, txtStreet, txtSubdivision,
                txtBarangay, txtCity, txtProvince, txthouseStatus, txttelePhone, txtCellphone, txtSpouseFname, txtSpouseMname, txtSpouseLname,
                txtSpouseBirthday;
    int userlvl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        db = new GpsDB_Query(this);
        context = Activity_CustomerDetails.this;
        activity = this;

        userlvl = Helper.variables.getGlobalVar_currentUserID(activity);

        PD = new ProgressDialog(this);
        PD.setCancelable(false);
        PD.setIndeterminate(true);

        txtmiddlename = (TextView) findViewById(R.id.txtMiddleName);
        txtfirstname = (TextView) findViewById(R.id.txtFirstName);
        txtlastname = (TextView) findViewById(R.id.txtLastName);
        txtbirthday = (TextView) findViewById(R.id.txtBirthday);
        txtfarmid = (TextView) findViewById(R.id.txtFarmid);
        txtBirthPlace = (TextView) findViewById(R.id.txtBirthPlace);
        txtHouseNumber = (TextView) findViewById(R.id.txtHouseNumber);
        txtStreet = (TextView) findViewById(R.id.txtStreet);
        txtSubdivision = (TextView) findViewById(R.id.txtSubdivision);
        txtBarangay = (TextView) findViewById(R.id.txtBarangay);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtProvince = (TextView) findViewById(R.id.txtProvince);
        txthouseStatus = (TextView) findViewById(R.id.txtHouseStatus);
        txttelePhone = (TextView) findViewById(R.id.txtTelephone);
        txtCellphone = (TextView) findViewById(R.id.txtCellphone);
        txtSpouseFname = (TextView) findViewById(R.id.txt_S_FirstName);
        txtSpouseLname = (TextView) findViewById(R.id.txt_S_LastName);
        txtSpouseMname = (TextView) findViewById(R.id.txt_S_MiddleName);
        txtSpouseBirthday = (TextView) findViewById(R.id.txt_S_Birthday);

        if (getIntent() != null) {

            if (getIntent().hasExtra("id")) {
                id = getIntent().getStringExtra("id");
            }
        }

        txtmiddlename.setText(id);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    public void showAllCustomerLocation(){
        PD.setMessage("Please wait...");
        PD.show();
        String url = Helper.variables.URL_SELECT_CUST_LOCATION_BY_ASSIGNED_AREA;



        if (userlvl == 1 || userlvl == 2 || userlvl == 3){
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {

                            PD.dismiss();

                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                custInfoObjectList = CustAndPondParser.parseFeed(response);
                                showCustomerLocation();
                            } else {
                                Helper.createCustomThemedColorDialogOKOnly(activity, "Error", "Something happened. Please try again later", "OK", R.color.red);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Helper.toastShort(activity,"Something happened. Please try again later");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity)+"");
                    return params;
                }
            };
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);

        }else if(userlvl == 0 || userlvl == 4) {
            Cursor cur = db.getCUST_LOCATION_BY_ASSIGNED_AREA(Helper.variables.getGlobalVar_currentUserID(activity)+"");
            if(cur != null) {
                if(cur.getCount() > 0) {
                    custInfoObjectList = new ArrayList<>();
                    while (cur.moveToNext()) {
                        CustInfoObject custInfoObject = new CustInfoObject();

                        custInfoObject.setMainCustomerId(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_ID))+"" );
                        custInfoObject.setLastname(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_LastName)));
                        custInfoObject.setFirstname(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_FirstName)));
                        custInfoObject.setMiddleName(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_MiddleName)));
                        custInfoObject.setFarmID(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_FarmId)));
                        custInfoObject.setStreet(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_Street)));
                        custInfoObject.setHouseNumber(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_HouseNumber)));
                        custInfoObject.setSubdivision(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_Subdivision)));
                        custInfoObject.setBarangay(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_Barangay)));
                        custInfoObject.setCity(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_City)));
                        custInfoObject.setProvince(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_Province)));
                        custInfoObject.setBirthday(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_CBirthday)));
                        custInfoObject.setBirthPlace(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_CBirthPlace)));
                        custInfoObject.setTelephone(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_Telephone)));
                        custInfoObject.setCellphone(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_Cellphone)));
                        custInfoObject.setCivilStatus(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_CivilStatus)));
                        custInfoObject.setSpouse_lname(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_S_LastName)));
                        custInfoObject.setSpouse_fname(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_S_FirstName)));
                        custInfoObject.setSpouse_mname(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_S_MiddleName)));
                        custInfoObject.setSpouse_birthday(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_S_BirthDay)));
                        custInfoObject.setHouseStatus(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_HouseStatus)));
                        custInfoObject.setCust_longtitude(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_Longitude)));
                        custInfoObject.setCust_latitude(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_Latitude)));
                        custInfoObject.setDateAddedToDB(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_DateAdded)));
                        custInfoObject.setAddedBy(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_MAINCUSTINFO_AddedBy)));

                        custInfoObjectList.add(custInfoObject);
                    }
                    showCustomerLocation();
                    PD.dismiss();
                }else{
                    PD.dismiss();
                    Helper.createCustomThemedColorDialogOKOnly(activity, "Error", "Something happened. Please try again later", "OK", R.color.red);
                }
            }else{
                PD.dismiss();
                Helper.createCustomThemedColorDialogOKOnly(activity, "Error", "Something happened. Please try again later", "OK", R.color.red);
            }
        }

    }

    private void showCustomerLocation() {


    }
}

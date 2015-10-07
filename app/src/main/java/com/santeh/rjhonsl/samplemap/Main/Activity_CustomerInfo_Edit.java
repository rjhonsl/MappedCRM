package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjhonsl on 8/11/2015.
 */
public class Activity_CustomerInfo_Edit extends Activity{

    Intent intentExtras;

    TextView txtactivityHeader, txtlat, txtlong;

    EditText edtContactname, edtCompany, edtAddress, edtFarmName, edtFarmId, edtContactNumber, edtCultureSystem, edtLevelOfCulture, edtWaterType;
    private String latitude, longitude;
    private int farmIndexId;
    private String contactName, address, farmname, farmID, contactnumber, culturesystem, levelofculture, watertype;

    Button btnSaveChanges;
    ProgressDialog PD;

    Activity activity;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        context = Activity_CustomerInfo_Edit.this;
        PD = new ProgressDialog(this);
        PD.setCancelable(false);

        setContentView(R.layout.activity_add_farminformation);
        intentExtras = getIntent();
        getextras(intentExtras);
        initXmlViews();

        txtlat.setText(latitude);
        txtlong.setText(longitude);
        edtContactname.setText(contactName);
        edtAddress.setText(address);
        edtFarmName.setText(farmname);
        edtFarmId.setText(farmID);
        edtContactNumber.setText(contactnumber);
        edtCultureSystem.setText(culturesystem);
        edtLevelOfCulture.setText(levelofculture);
        edtWaterType.setText(watertype);

        txtactivityHeader.setText("EDIT CUSTOMER INFORMATION");

        initListeners();
    }

    private void initListeners() {

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomerInformation();
            }
        });
        edtCultureSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_CustomerInfo_Edit.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listculturetype);
                d.show();

                ListView lstCultureType = (ListView) d.findViewById(R.id.lstDialog_culturetype);
                lstCultureType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            edtCultureSystem.setText("Cage");
                            d.hide();
                        } else if (position == 1) {
                            edtCultureSystem.setText("Pen");
                            d.hide();
                        } else if (position == 2) {
                            edtCultureSystem.setText("Pond");
                            d.hide();
                        }
                    }
                });
            }
        });

        edtLevelOfCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_CustomerInfo_Edit.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listculturelevel);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_culturelevel);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            edtLevelOfCulture.setText("Extensive");
                            d.hide();
                        } else if (position == 1) {
                            edtLevelOfCulture.setText("Intensive");
                            d.hide();
                        }else if (position == 2) {
                            edtLevelOfCulture.setText("Semi-Intensive");
                            d.hide();
                        }else if (position == 3) {
                            edtLevelOfCulture.setText("Mono-Culture");
                            d.hide();
                        }else if (position == 4) {
                            edtLevelOfCulture.setText("Poly-Culture");
                            d.hide();
                        }
                    }
                });
            }
        });

        edtWaterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_CustomerInfo_Edit.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listwatertype);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_watertype);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            edtWaterType.setText("Fresh Water");
                            d.hide();
                        } else if (position == 1) {
                            edtWaterType.setText("Marine Water");
                            d.hide();
                        } else if (position == 2) {
                            edtWaterType.setText("Brackish Water");
                            d.hide();
                        }
                    }
                });
            }
        });

        edtCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_CustomerInfo_Edit.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listcompany);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_company);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            edtCompany.setText("PetOne");
                            d.hide();
                        } else if (position == 1) {
                            edtCompany.setText("Pronatural");
                            d.hide();
                        }
                        else if (position == 2) {
                            edtCompany.setText("Santeh");
                            d.hide();
                        }
                        else if (position == 3) {
                            edtCompany.setText("Tateh");
                            d.hide();
                        }
                    }
                });
            }
        });
    }

    private void initXmlViews() {
        txtactivityHeader = (TextView) findViewById(R.id.titleHeader);
        txtlat = (TextView) findViewById(R.id.addMarker_lat);
        txtlong= (TextView) findViewById(R.id.addMarker_long);

        btnSaveChanges = (Button) findViewById(R.id.btn_markerdetail_OK);

        edtContactname = (EditText) findViewById(R.id.txt_markerdetails_contactName);
        edtCompany = (EditText) findViewById(R.id.txt_markerdetails_company);
        edtAddress = (EditText) findViewById(R.id.txt_markerdetails_address);
        edtFarmName = (EditText) findViewById(R.id.txt_markerdetails_farmname);
        edtFarmId = (EditText) findViewById(R.id.txt_markerdetails_farmID);
        edtContactNumber = (EditText) findViewById(R.id.txt_markerdetails_contactNumber);
        edtCultureSystem = (EditText) findViewById(R.id.txt_markerdetails_cultureType);
        edtLevelOfCulture = (EditText) findViewById(R.id.txt_markerdetails_levelofCulture);
        edtWaterType = (EditText) findViewById(R.id.txt_markerdetails_waterType);
    }

    private void getextras(Intent intent) {

        if (intent != null){
            if (intent.hasExtra("lat")){latitude = intent.getStringExtra("lat");}
            if (intent.hasExtra("long")){longitude = intent.getStringExtra("long"); }

            if (intent.hasExtra("userid")){farmIndexId = intent.getIntExtra("userid", 0);}

            if (intent.hasExtra("contactName")){contactName = intent.getStringExtra("contactName");}
            if (intent.hasExtra("address")){address = intent.getStringExtra("address");}
            if (intent.hasExtra("farmname")){farmname = intent.getStringExtra("farmname");}
            if (intent.hasExtra("farmID")){farmID = intent.getStringExtra("farmID");}
            if (intent.hasExtra("contactnumber")){contactnumber = intent.getStringExtra("contactnumber");}
            if (intent.hasExtra("culturesystem")){culturesystem = intent.getStringExtra("culturesystem");}
            if (intent.hasExtra("levelofculture")){levelofculture = intent.getStringExtra("levelofculture");}
            if (intent.hasExtra("watertype")){watertype = intent.getStringExtra("watertype");}
        }
    }


    public void updateCustomerInformation() {

        latitude = txtlat.getText().toString();
        longitude = txtlong.getText().toString();
        if (    latitude.equalsIgnoreCase("") || longitude.equalsIgnoreCase("") ||
                edtContactname.getText().toString().equalsIgnoreCase("") ||
                edtCompany.getText().toString().equalsIgnoreCase("") ||
                edtAddress.getText().toString().equalsIgnoreCase("") ||
                edtFarmName.getText().toString().equalsIgnoreCase("") ||
                edtFarmId.getText().toString().equalsIgnoreCase("") ||
                edtContactNumber.getText().toString().equalsIgnoreCase("") ||
                edtCultureSystem.getText().toString().equalsIgnoreCase("") ||
                edtLevelOfCulture.getText().toString().equalsIgnoreCase("") ||
                edtWaterType.getText().toString().equalsIgnoreCase(""))
        {
            Helper.toastShort(activity,    Helper.variables.URL_DELETE_CUSTINFO_BY_ID);

        } else {
            PD.show();
            PD.setMessage("Saving changes...");

            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_UPDATE_CUSTOMERINFORMATION_BY_ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (!Helper.extractResponseCodeBySplit(response).equalsIgnoreCase("0")) {
                                PD.dismiss();
                                Helper.toastShort(activity, "Update successful.");
                                Logging.loguserAction(activity, context, Helper.userActions.TSR.Edit_FARM + ": index "+farmIndexId, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);
                            } else {
                                PD.dismiss();
                                Helper.toastShort(activity, getResources().getString(R.string.VolleyUnexpectedError));
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Helper.toastShort(activity, "Failed to connect to server.");
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", farmIndexId+"");
                    params.put("latitude", String.valueOf(txtlat.getText()));
                    params.put("longitude", String.valueOf(txtlong.getText()));
                    params.put("contactName", edtContactname.getText().toString());
                    params.put("company", edtCompany.getText().toString());
                    params.put("address", edtAddress.getText().toString());
                    params.put("farmName", edtFarmName.getText().toString());
                    params.put("farmID", edtFarmId.getText().toString());
                    params.put("contactNumber", edtContactNumber.getText().toString());
                    params.put("cultureType", edtCultureSystem.getText().toString());
                    params.put("cultureLevel", edtLevelOfCulture.getText().toString());
                    params.put("waterType", edtWaterType.getText().toString());
                    params.put("username", Helper.variables.getGlobalVar_currentUsername(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserpassword(activity));
                    params.put("deviceid", Helper.getMacAddress(activity));

                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_CustomerInfo_Edit.this);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



}

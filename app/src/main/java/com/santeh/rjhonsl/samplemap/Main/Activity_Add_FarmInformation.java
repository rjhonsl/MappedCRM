package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.DBase.GpsSQLiteHelper;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

/**
 * Created by rjhonsl on 7/28/2015.
 */
public class Activity_Add_FarmInformation extends Activity {

    EditText txtContactName, txtCultureType, txtCultureLevel, txtWaterType, txtCompany, txtAddress, txtFarmname,txtFarmID,txtContactNumber;
    TextView txtLat, txtLong;
    double curlatitude=0, curlongtitude=0;
    Activity context1;
    Activity activity;

    Context context;

    String url = Helper.variables.URL_INSERT_FARM_INFO;

    String latitude, longtitude, id, imageName;

    ProgressDialog PD;
    Button insert, btnOK,btnCancel, btnAddPond;

    ImageButton titleback;
    FusedLocation fusedLocation;

    GpsDB_Query db;
    GpsSQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farminformation);
        context1 = this;
        context = Activity_Add_FarmInformation.this;
        activity = this;

        dbHelper = new GpsSQLiteHelper(this);
        db = new GpsDB_Query(this);
        db.open();

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.buildGoogleApiClient(context);
        fusedLocation.connectToApiClient();
    try{
        curlatitude = (double) getIntent().getExtras().get("latitude");
        curlongtitude = (double) getIntent().getExtras().get("longtitude");

    }catch (Exception e){
        Helper.toastShort(context1, "No location passed");
    }


        titleback = (ImageButton) findViewById(R.id.title);
        txtCultureType  = (EditText) findViewById(R.id.txt_markerdetails_cultureType);
        txtCultureLevel = (EditText) findViewById(R.id.txt_markerdetails_levelofCulture);
        txtWaterType    = (EditText) findViewById(R.id.txt_markerdetails_waterType);
        txtCompany      = (EditText) findViewById(R.id.txt_markerdetails_company);
        txtContactName  = (EditText) findViewById(R.id.txt_markerdetails_contactName);
        txtAddress      = (EditText) findViewById(R.id.txt_markerdetails_address);
        txtFarmname     = (EditText) findViewById(R.id.txt_markerdetails_farmname);
        txtFarmID       = (EditText) findViewById(R.id.txt_markerdetails_farmID);
        txtContactNumber = (EditText) findViewById(R.id.txt_markerdetails_contactNumber);
        txtLat          = (TextView) findViewById(R.id.addMarker_lat);
        txtLong         = (TextView) findViewById(R.id.addMarker_long);

        btnOK = (Button) findViewById(R.id.btn_markerdetail_OK);
        btnCancel = (Button) findViewById(R.id.btn_markerdetail_CANCEL);
        btnAddPond = (Button) findViewById(R.id.btn_markerdetail_ADDPOND);

        txtCultureType.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);
        txtCultureLevel.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);
        txtWaterType.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);
        txtCompany.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);
        txtContactName.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);
        txtAddress.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);
        txtFarmname.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);
        txtFarmID.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);
        txtContactNumber.getBackground().setColorFilter(getResources().getColor(R.color.material_deep_teal_500), PorterDuff.Mode.SRC_IN);

        txtLat.setText("lat: "+String.valueOf(curlatitude));
        txtLong.setText("long: "+String.valueOf(curlongtitude));

        PD = new ProgressDialog(this);
        PD.setMessage("Updating database. Please wait....");
        PD.setCancelable(false);

        initOnClickListeners();

    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
    }

    public void insertCustomerInfo() {
        fusedLocation.connectToApiClient();

        latitude = String.valueOf(curlatitude);
        longtitude = String.valueOf(curlongtitude);
        if(latitude.equalsIgnoreCase("") ||
                longtitude.equalsIgnoreCase("") ||
                txtContactName.getText().toString().equalsIgnoreCase("") ||
                txtCompany.getText().toString().equalsIgnoreCase("") ||
                txtAddress.getText().toString().equalsIgnoreCase("") ||
                txtFarmname.getText().toString().equalsIgnoreCase("") ||
                txtFarmID.getText().toString().equalsIgnoreCase("") ||
                txtContactNumber.getText().toString().equalsIgnoreCase("") ||
                txtCultureType.getText().toString().equalsIgnoreCase("") ||
                txtCultureLevel.getText().toString().equalsIgnoreCase("") ||
                txtWaterType.getText().toString().equalsIgnoreCase(""))
        {
            final Dialog d = Helper.createCustomDialogOKOnly(Activity_Add_FarmInformation.this, "OOPS",
                    "There seems to be field(s) that you have left behind... Please check then try again.", "OK");
            TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
            d.show();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   d.hide();
                }
            });
        }
        else{
            PD.setMessage("Saving farm information...");
            PD.show();


            final long id = db.insertFarmInformation(
                    String.valueOf(curlatitude),
                    String.valueOf(curlongtitude),
                    txtContactName.getText().toString(),
                    txtCompany.getText().toString(),
                    txtAddress.getText().toString(),
                    txtFarmname.getText().toString(),
                    txtFarmID.getText().toString(),
                    txtContactNumber.getText().toString(),
                    txtCultureType.getText().toString(),
                    txtCultureLevel.getText().toString(),
                    txtWaterType.getText().toString(),
                    Helper.getDateDBformat(),
                    Helper.variables.getGlobalVar_currentUserID(activity) + "");
            if (id != -1){
                PD.dismiss();
                Dialog d = Helper.createCustomThemedColorDialogOKOnly(Activity_Add_FarmInformation.this, "SUCCESS",
                        "You have successfully added " + txtContactName.getText().toString() + " to database", "OK", R.color.blue);
                TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                d.setCancelable(false);
                d.show();
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MapsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("fromActivity", "addfarminfo");

                        Logging.loguserAction(activity, activity.getBaseContext(),
                                Helper.userActions.TSR.ADD_FARM +":"+ Helper.variables.getGlobalVar_currentUserID(activity)+"-"+id + "-"+txtFarmID.getText().toString(),
                                Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);

                        startActivity(intent);
                        finish(); // call this to finish the current activity
                    }
                });
            }else{
                PD.dismiss();
                Helper.createCustomThemedColorDialogOKOnly(activity, "Error", "Saving Failed! Please try again.", "OK", R.color.red);
            }

//
//            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
//                            fusedLocation.connectToApiClient();
//                            if (response.substring(1,2).equalsIgnoreCase("1")) {
//
////                                Log.d("LOGGING", "" + activity.toString() + " " + context.toString() + " " + Helper.variables.getGlobalVar_currentUserID(activity) + ""
////                                        + " " + fusedLocation.getLastKnowLocation().latitude + " " + fusedLocation.getLastKnowLocation().longitude);
//                                Logging.loguserAction(activity, context,
//                                        Helper.userActions.TSR.ADD_FARM, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);
//
//
//                                PD.dismiss();
//                                Dialog d = Helper.createCustomThemedColorDialogOKOnly(Activity_Add_FarmInformation.this, "SUCCESS",
//                                        "You have successfully added " + txtContactName.getText().toString() + " to database", "OK", R.color.blue);
//                                TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
//                                d.setCancelable(false);
//                                d.show();
//                                ok.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        finish();
//                                        Intent intent = new Intent(activity, MapsActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        intent.putExtra("fromActivity", "addfarminfo");
//                                        startActivity(intent);
//                                        finish(); // call this to finish the current activity
//
//                                    }
//                                });
//
//
//                            }else {
//                                Helper.createCustomThemedColorDialogOKOnly(activity, "Error", "Something happened. Please try again." , "OK", R.color.red);
//                                PD.dismiss();
//                            }
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    PD.dismiss();
//                    Helper.toastShort(activity, "Failed to connect to server.");
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("latitude", String.valueOf(curlatitude));
//                    params.put("longtitude", String.valueOf(curlongtitude));
//                    params.put("contactName", txtContactName.getText().toString());
//                    params.put("company", txtCompany.getText().toString());
//                    params.put("address", txtAddress.getText().toString());
//                    params.put("farmName", txtFarmname.getText().toString());
//                    params.put("farmID",  txtFarmID.getText().toString());
//                    params.put("contactNumber", txtContactNumber.getText().toString());
//                    params.put("cultureType", txtCultureType.getText().toString());
//                    params.put("cultureLevel", txtCultureLevel.getText().toString());
//                    params.put("waterType",   txtWaterType.getText().toString());
//
//                    params.put("dateAdded", Helper.convertLongtoDateString(System.currentTimeMillis()));
//                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
//                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
//                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
//                    params.put("deviceid", Helper.getMacAddress(activity));
//
//
//                    return params;
//                }
//            };
//
//            // Adding request to request queue
//            MyVolleyAPI api = new MyVolleyAPI();
//            api.addToReqQueue(postRequest, Activity_Add_FarmInformation.this);
        }


    }







    private void initOnClickListeners() {

        titleback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddPond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Activity_Add_FarmInformation.this, Activity_ManagePonds.class);
                startActivity(intent);
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = Helper.createCustomDialogThemedYesNO(activity,
                        "Are you sure you want to save this information to our database?",
                        "Save", "YES", "NO", R.color.deepteal_500);
                Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertCustomerInfo();
                        d.hide();
                    }
                });
                d.show();
            }
        });

        txtCultureType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_Add_FarmInformation.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listculturetype);
                d.show();

                ListView lstCultureType = (ListView) d.findViewById(R.id.lstDialog_culturetype);
                lstCultureType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            txtCultureType.setText("Cage");
                            d.hide();
                        } else if (position == 1) {
                            txtCultureType.setText("Pen");
                            d.hide();
                        } else if (position == 2) {
                            txtCultureType.setText("Pond");
                            d.hide();
                        }
                    }
                });
            }
        });

        txtCultureLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_Add_FarmInformation.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listculturelevel);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_culturelevel);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            txtCultureLevel.setText("Extensive");
                            d.hide();
                        } else if (position == 1) {
                            txtCultureLevel.setText("Intensive");
                            d.hide();
                        }else if (position == 2) {
                            txtCultureLevel.setText("Semi-Intensive");
                            d.hide();
                        }else if (position == 3) {
                            txtCultureLevel.setText("Mono-Culture");
                            d.hide();
                        }else if (position == 4) {
                            txtCultureLevel.setText("Poly-Culture");
                            d.hide();
                        }
                    }
                });
            }
        });

        txtWaterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_Add_FarmInformation.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listwatertype);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_watertype);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            txtWaterType.setText("Fresh Water");
                            d.hide();
                        } else if (position == 1) {
                            txtWaterType.setText("Marine Water");
                            d.hide();
                        } else if (position == 2) {
                            txtWaterType.setText("Brackish Water");
                            d.hide();
                        }
                    }
                });
            }
        });

        txtCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Activity_Add_FarmInformation.this);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_listcompany);
                d.show();

                ListView lstCultureLevel = (ListView) d.findViewById(R.id.lstDialog_company);
                lstCultureLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            txtCompany.setText("PETONE");
                            d.hide();
                        } else if (position == 1) {
                            txtCompany.setText("PRONATURAL");
                            d.hide();
                        }
                        else if (position == 2) {
                            txtCompany.setText("SANTEH");
                            d.hide();
                        }
                        else if (position == 3) {
                            txtCompany.setText("TATEH");
                            d.hide();
                        }
                    }
                });
            }
        });
    }
}

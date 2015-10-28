package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.Adapter_UserMonitoring_SideNav_Activities;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.UserActivityParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 9/11/2015.
 */
public class MapsActivity_UserMonitoring extends AppCompatActivity implements OnMapReadyCallback, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Activity activity;
    Context context;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    FusedLocation fusedLocation;
    Intent extras;
    ProgressDialog PD;

    boolean isDrawerOpen;


    Adapter_UserMonitoring_SideNav_Activities adapter_useractivity;
    GoogleMap gmap;

    int yyyy= 1970;
    int mm = 1;
    int dd = 1;

    String firstname, lastname;

    ImageButton btnChangeMaptype, btnFilterByCalendars, btnviewDrawer;
    TextView txtdate, txtfiller, txtViewAllActivity;

    int passedUserid;

    public static final String DATEPICKER_TAG = "datepicker";

    List <CustInfoObject> useractivityList;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    ActionBarDrawerToggle drawerListener;

    ListView lvUserActivity;


    String givendate;

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_usermonitoring);
        activity = this;
        context = MapsActivity_UserMonitoring.this;



        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.buildGoogleApiClient(context);
        fusedLocation.connectToApiClient();

        useractivityList = new ArrayList<>();

        PD = new ProgressDialog(this);
        PD.setCancelable(false);

        final Calendar calendar = Calendar.getInstance();

        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        timePickerDialog = TimePickerDialog.newInstance(MapsActivity_UserMonitoring.this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

        txtdate = (TextView) findViewById(R.id.date_usermonitoring);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_usermonitoring);
        lvUserActivity = (ListView) findViewById(R.id.lv_userActivity);
        txtfiller = (TextView) findViewById(R.id.usermonitoring_filler);
        txtViewAllActivity = (TextView) findViewById(R.id.txt_usermonitoring_viewAll);

        txtdate.setText(Helper.convertLongtoDate_Gregorian(System.currentTimeMillis()));


        DateTime dateTime = new DateTime();
        ActionToggleDrawerListner();
        drawerListener.syncState();

        yyyy = dateTime.getYear();
        mm = dateTime.getMonthOfYear();
        dd =  dateTime.getDayOfMonth();
        givendate = Helper.convertLongtoDateTime_DB_Format(System.currentTimeMillis());
        extras = getIntent();
        if (extras != null){

            if (extras.hasExtra("userid")){
                passedUserid= extras.getIntExtra("userid",0);
            }

            if (extras.hasExtra("firstname") && extras.hasExtra("lastname")){
                firstname= extras.getStringExtra("firstname");
                lastname= extras.getStringExtra("lastname");
                setTitle(firstname + " " + lastname);
            }
        }


        btnChangeMaptype = (ImageButton) findViewById(R.id.btn_changeMaptype);
        btnFilterByCalendars = (ImageButton) findViewById(R.id.btn_viewCalendar);
        btnviewDrawer = (ImageButton) findViewById(R.id.btn_viewDrawer);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity_UserMonitoring.this);

    }


    

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        gmap = googleMap;

        fusedLocation.connectToApiClient();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    LatLng latLng = fusedLocation.getLastKnowLocation();
                    Helper.moveCameraAnimate(googleMap, latLng, 14);
                } catch (Exception e) {
                    Helper.toastShort(activity, "Location is not available: " + e);
                }
            }
        }, 200);

        btnviewDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(drawerLayout)){
                    openDrawer();
//                }
            }
        });

        lvUserActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                closeDrawer();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Helper.moveCameraAnimate(gmap, new LatLng(Double.parseDouble(useractivityList.get(position).getLatitude()),
                                Double.parseDouble(useractivityList.get(position).getLongtitude()))
                                , 14);
                    }
                }, 220);
            }
        });

        btnFilterByCalendars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                datePickerDialog.setVibrate(isVibrate());
                datePickerDialog.setYearRange(1980, 2030);
//                datePickerDialog.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);

            }
        });

        txtViewAllActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityInfoByUserIDandDate(givendate, Helper.variables.URL_SELECT_ALL_USERS_ACTIVITY_BY_ID);
            }
        });

        btnChangeMaptype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] maptypes = {"Normal","Satellite","Terrain", "Hybrid"};
                final Dialog dd = Helper.createCustomListDialog(activity, maptypes, "Map Types");
                ListView lstMapType = (ListView) dd.findViewById(R.id.dialog_list_listview);
                dd.show();

                lstMapType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0){
                            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            dd.hide();
                        }else if (position == 1) {
                            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            dd.hide();
                        }else if (position == 2) {
                            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            dd.hide();
                        }
                        else if (position == 3) {
                            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            dd.hide();
                        }
                    }
                });

            }
        });

        getActivityInfoByUserIDandDate(givendate, Helper.variables.URL_SELECT_USERS_ACTIVITY_BY_DATE_AND_ID);

    }


    public void getActivityInfoByUserIDandDate(final String setDate, String url) {
        PD.setMessage("Please wait...");
        PD.show();

        if(!Helper.isNetworkAvailable(activity)) {
            Helper.toastShort(activity, "Internet Connection is not available. Please try again later.");
            PD.dismiss();
        }
        else{
            gmap.clear();


            final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

//                            Helper.toastLong(activity, response + " "+passedUserid);
                            if (response.substring(1,2).equalsIgnoreCase("0")){
                                PD.dismiss();
                                Helper.toastShort(activity, "No activity/map data to display");
                                txtfiller.setVisibility(View.VISIBLE);
                                lvUserActivity.setVisibility(View.GONE);
                            }else{



                                useractivityList  = new ArrayList<CustInfoObject>();
                                useractivityList = UserActivityParser.parseFeed(response);
                                if (useractivityList != null){
                                    if (useractivityList.size() > 0){
                                        for (int i = 0; i < useractivityList.size(); i++) {

                                            Helper.map_addMarkerIconGen(gmap,
                                                    new LatLng(Double.parseDouble(useractivityList.get(i).getLatitude()), Double.parseDouble(useractivityList.get(i).getLongtitude())),//gets latlong
                                                    Helper.iconGeneratorSample(context, (i + 1) + "", activity), //creates an icon with a number
                                                    useractivityList.get(i).getActionDone(), //action done by the user in the latlong
                                                    Helper.convertLongtoDate_GregorianWithTime(Helper.convertDateTimeStringToMilis_DB_Format(useractivityList.get(i).getDateTime()))//date when action was done
                                            );
                                        }
                                        txtfiller.setVisibility(View.GONE);
                                        lvUserActivity.setVisibility(View.VISIBLE);
                                        adapter_useractivity = new Adapter_UserMonitoring_SideNav_Activities(MapsActivity_UserMonitoring.this, R.layout.item_lv_useractivity, useractivityList);
                                        lvUserActivity.setAdapter(adapter_useractivity);

                                    }
                                }else{
                                    txtfiller.setVisibility(View.VISIBLE);
                                    lvUserActivity.setVisibility(View.GONE);
                                    Helper.toastLong(activity, firstname + " " + lastname + " has no logs on current date selected");
                                }

                                PD.dismiss();

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    txtfiller.setVisibility(View.VISIBLE);
                    lvUserActivity.setVisibility(View.GONE);
                    Helper.toastShort(activity, error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("deviceid", Helper.getMacAddress(context));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                    params.put("idofuser", passedUserid+"");
                    params.put("date", setDate);
                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);

        }

    }

    private void ActionToggleDrawerListner() {
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_menu_white_24dp,
                R.string.openDrawer, R.string.closeDrawer) {

    
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isDrawerOpen = false;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpen = true;
            }

        };
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        setYyyy(year);
        setDd(day);
        setMm(month + 1);
        txtdate.setText(Helper.convertDatetoGregorain(year, month + 1, day));
        getActivityInfoByUserIDandDate(year + "-" + (month + 1) + "-" + day, Helper.variables.URL_SELECT_USERS_ACTIVITY_BY_DATE_AND_ID);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public int getDd() {
        return dd;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getYyyy() {
        return yyyy;
    }

    public void setYyyy(int yyyy) {
        this.yyyy = yyyy;
    }
}//end of class

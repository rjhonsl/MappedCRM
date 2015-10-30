package com.santeh.rjhonsl.samplemap.Main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;
import com.santeh.rjhonsl.samplemap.DBase.GpsSQLiteHelper;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Obj.Var;
import com.santeh.rjhonsl.samplemap.Parsers.CustAndPondParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.GPSTracker;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ProgressDialog PD;
    boolean isDrawerOpen = false;


    PopupWindow popUp;
    LinearLayout layout;
    TextView tvBottomPopUp;
    ViewGroup.LayoutParams params;
    LinearLayout mainLayout;

    Location mLastLocation;

    String username, firstname, lastname, userdescription;
    int userlevel, userid;

    DrawerLayout drawerLayout;

    ImageButton btn_add_marker, btn_cancelAddmarker;
    ActionBarDrawerToggle drawerListener;

    Marker clickedMarker;

    double curlat, curLong;
    int zoom = 15,
        activeFilter;

    Activity activity;
    Context context;
    GoogleApiClient mGoogleApiClient;
    GoogleMap maps;

    LatLng curLatlng, lastlatlng;

    TextView textView;
    TextView nav_fingerlings, nav_customerAddress, nav_sperms, nav_logout, nav_maptype, nav_displayAllMarkers, nav_settings, nav_growout,nav_usermonitoring, txtusername;

    String activeSelection;


    List<CustInfoObject> custInfoObjectList;

    Bundle extrass;
    Intent passedintent;

    CircleOptions circleOptions_addLocation;
    Circle mapcircle;

    FusedLocation fusedLocation;
    GpsDB_Query db;
    int userlvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activity = MapsActivity.this;
        context = MapsActivity.this;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        passedintent = getIntent();
        extrass = getIntent().getExtras();
        db = new GpsDB_Query(this);
        db.open();

        userlvl = Helper.variables.getGlobalVar_currentLevel(activity);

        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        tvBottomPopUp = new TextView(this);

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.buildGoogleApiClient(context);
        fusedLocation.connectToApiClient();
        activeSelection = "farm";
        lastlatlng = fusedLocation.getLastKnowLocation();




        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nav_displayAllMarkers = (TextView) findViewById(R.id.txt_Nav_displayAll);
        nav_fingerlings = (TextView) findViewById(R.id.txt_Nav_fingerlings);
        nav_customerAddress = (TextView) findViewById(R.id.txt_Nav_customeraddress);
        nav_sperms = (TextView) findViewById(R.id.txt_Nav_sperms);
        nav_maptype = (TextView) findViewById(R.id.txt_Nav_changeMapType);
        nav_settings = (TextView) findViewById(R.id.txt_Nav_settings);
        btn_add_marker = (ImageButton) findViewById(R.id.btnaddMarker);
        btn_cancelAddmarker = (ImageButton) findViewById(R.id.btnCloseAddMarker);
        nav_growout = (TextView) findViewById(R.id.txt_Nav_growOut);
        nav_logout = (TextView) findViewById(R.id.txt_Nav_logout);
        textView = (TextView) findViewById(R.id.latLong);
        nav_usermonitoring = (TextView) findViewById(R.id.txt_Nav_UserMonitoring);
        txtusername = (TextView) findViewById(R.id.username);

//
//



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActionToggleDrawerListner();
        drawerListener.syncState();

        PD = new ProgressDialog(this);
        PD.setMessage("Getting data from server.\nPlease wait....");
        PD.setCancelable(false);
    }





    private void initMarkers() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {  //if extras is not nul
            if (extras.getString("fromActivity") != null){
                String from = extras.getString("fromActivity");

                if (from.equalsIgnoreCase("viewCustinfo")) { //if from Activity View Customer Info
                    if (extras.getString("lat")!=null && extras.get("long")!= null) {//if lat & long is not null// passed from intent
                        LatLng latLng = new LatLng(
                                Double.parseDouble(extras.getString("lat")),
                                Double.parseDouble(extras.getString("long"))  );

                        if(((Var) this.getApplication()).getGoogleMap() != null){ //if google maps is ready
                            moveCameraAnimate(((Var) this.getApplication()).getGoogleMap(), latLng, 15);
                            maps.setInfoWindowAdapter(new FarmInfoWindow());
                            maps.clear();

                            Helper.map_addMarker(((Var) this.getApplication()).getGoogleMap(), latLng, R.drawable.ic_place_red_24dp,
                                    extras.getString("contactName"), extras.getString("address"), extras.getInt("id")+"",null, null);

                        PD.dismiss();
                        }
                        else{//google map not ready
                            Helper.toastShort(activity, "Can't find current location. Please try again later.");
                        }

                    }
                }else{//if not from view Customer Info
                    showAllRelatedMarkers();
                }
            }
            else {//if opened from login screen
                    showAllRelatedMarkers();  }
        }
        else{//if opened from login screen
            showAllRelatedMarkers();
        }
    }

    //when map is read
    @Override
    public void onMapReady(final GoogleMap map) {
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        maps = map;

        ((Var) this.getApplication()).setGoogleMap(map);

        txtusername.setText(Helper.variables.getGlobalVar_currentUserFirstname(activity) + " " + Helper.variables.getGlobalVar_currentUserLastname(activity));
        map.setInfoWindowAdapter(new FarmInfoWindow());
        initListeners(map);
        fusedLocation.connectToApiClient();


        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layout.setOrientation(LinearLayout.VERTICAL);
        btn_cancelAddmarker.setVisibility(View.GONE);


        tvBottomPopUp.setBackgroundColor(ContextCompat.getColor(context, R.color.white_200));
        tvBottomPopUp.setText("Owner Location");
        layout.addView(tvBottomPopUp, params);
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.white_200));
        popUp.setContentView(layout);

        tvBottomPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
//                Helper.toastShort(activity, clickedMarker.getTitle());
                double lat = 0,lng = 0;
                String farmidd;
                String[] splitted = clickedMarker.getTitle().split("#-#");
                if (!splitted[4].equalsIgnoreCase("") && !splitted[4].equalsIgnoreCase("null")){
                    lat = Double.parseDouble(splitted[4]);
                }

                if (!splitted[5].equalsIgnoreCase("") && !splitted[5].equalsIgnoreCase("null")){
                    lng = Double.parseDouble(splitted[5]);
                }

                farmidd = splitted[6];

                if (lat > 0 && lng > 0){
                    Helper.moveCameraAnimate(maps, new LatLng(lat, lng), 15);
                    maps.clear();

                    showAllCustomerLocation();
                }else{
                    Helper.createCustomThemedColorDialogOKOnly(activity, "Oops", "Address of farm owner is currently not available. \n\nFarm ID: "+farmidd+
                            splitted[0] +" " + splitted[1] +" " +splitted[2] +" " +splitted[3] +" " +splitted[4] +" " +splitted[5] +" " +splitted[6] +" "
                            , "OK", R.color.blue);
                }


            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (popUp.isShowing()){
                    popUp.dismiss();
                }
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                clickedMarker = marker;
                if (activeSelection.equalsIgnoreCase("farm")) {

                    if (!marker.isInfoWindowShown()) {
                        popUp.showAtLocation(mainLayout, Gravity.BOTTOM, 0, 20);
                        popUp.update(0, 30, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                    else{
                        popUp.dismiss();
                    }
                }


                return false;
            }
        });
        if (Helper.variables.getGlobalVar_currentLevel(activity) > 1){
            nav_usermonitoring.setVisibility(View.GONE);
        }else{
            nav_usermonitoring.setVisibility(View.VISIBLE);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            try{
                    if (checkIfLocationAvailable()){
                        moveCameraAnimate(map, fusedLocation.getLastKnowLocation(), zoom);
                        insertloginlocation();
                        initMarkers();
                    }
                    else{
                        PD.hide();
                        curlat = 14.651391;
                        curLong = 121.029335;
                        zoom = 9;
                    }
                }catch(Exception e){
                    Helper.toastShort(activity, "Location is not available: "+e);
                    Log.e("Error", e.toString());
                }
            }
        }, 500);
    }

    private void initListeners(final GoogleMap map) {

        nav_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        nav_displayAllMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                showAllRelatedMarkers();
                closeDrawer();
                activeSelection = "farm";
                if (popUp.isShowing()) {
                    popUp.dismiss();
                }
            }
        });


        nav_fingerlings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                closeDrawer();

            }
        });

        nav_customerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                closeDrawer();
                activeSelection = "customer";
                showAllCustomerLocation();
                if (popUp.isShowing()) {
                    popUp.dismiss();
                }
            }
        });

        nav_sperms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                closeDrawer();
            }
        });

        nav_usermonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MapsActivity.this, Activity_UserMonitoring_ViewByUser.class);
                closeDrawer();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                }, 280);
            }
        });


        nav_maptype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeDrawer();

                String[] maptypes = {"Normal", "Satellite", "Terrain", "Hybrid"};
                final Dialog dd = Helper.createCustomListDialog(activity, maptypes, "Map Types");
                ListView lstMapType = (ListView) dd.findViewById(R.id.dialog_list_listview);
                dd.show();

                lstMapType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            dd.hide();
                        } else if (position == 1) {
                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            dd.hide();
                        } else if (position == 2) {
                            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            dd.hide();
                        } else if (position == 3) {
                            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            dd.hide();
                        }
                    }
                });
            }
        });
        btn_cancelAddmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelMarkerAdding();
            }
        });


        btn_add_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setUpMap();
                fusedLocation.disconnectFromApiClient();
                if (Helper.isLocationEnabled(context)) {

                    fusedLocation.connectToApiClient();
                    final Handler handler = new Handler();
                    final Handler handler1 = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            curLatlng = fusedLocation.getLastKnowLocation();
                            if (mapcircle == null || !mapcircle.isVisible()){
                                circleOptions_addLocation = Helper.addCircle(activity, curLatlng, 1, R.color.skyblue_20,
                                        R.color.skyblue_20, 1000);
                                mapcircle = maps.addCircle(circleOptions_addLocation);
                            }
                            btn_cancelAddmarker.setVisibility(View.VISIBLE);
                            moveCameraAnimate(map, new LatLng(curLatlng.latitude, curLatlng.longitude), 15);
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Helper.createCustomThemedColorDialogOKOnly(activity, "Add Marker", "Long press any location within 1000 meters of your current location to Add a Marker.", "OK", R.color.blue);
                                }
                            }, 1200);

                        }
                    }, 280);


                    if (btn_add_marker.isEnabled()) {
                        btn_add_marker.setEnabled(false);
                    }


                    maps.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latLng) {

                            final LatLng touchLocation = latLng;
                            LatLng center = fusedLocation.getLastKnowLocation();

                            float[] results = new float[1];
                            Location.distanceBetween(center.latitude, center.longitude,
                                    touchLocation.latitude, touchLocation.longitude, results);
//                        Helper.toastLong(activity, results[0]+"");

                            if (results[0] > 1000) {
                                final Dialog d = Helper.createCustomThemedColorDialogOKOnly(activity, "Out of range", "Selection is out of 1km range from your location", "OK", R.color.red);
                                d.show();

                                Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.hide();
                                    }
                                });
                            } else {

                                String[] options = {"Farm Information", "Customer Information"};
                                final Dialog d1 = Helper.createCustomThemedListDialogWithPrompt(activity, options, "Add Marker",
                                        "Select the type of marker you want to add on this location.\n\nLat. " + touchLocation.latitude + "     Lng. " + touchLocation.longitude, R.color.blue);
                                ListView lvoptions = (ListView) d1.findViewById(R.id.dialog_list_listview);
                                lvoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if (position == 0) {
                                            cancelMarkerAdding();
                                            d1.hide();
                                            final Intent intent = new Intent(MapsActivity.this, Activity_Add_FarmInformation.class);
                                            intent.putExtra("latitude", touchLocation.latitude);
                                            intent.putExtra("longtitude", touchLocation.longitude);
                                            startActivity(intent);
                                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                        }

                                        if (position == 1) {
                                            d1.hide();
                                            cancelMarkerAdding();
                                            final Intent intent = new Intent(MapsActivity.this, Activity_Add_CustomerInformation_Basic.class);
                                            intent.putExtra("latitude", touchLocation.latitude);
                                            intent.putExtra("longtitude", touchLocation.longitude);
                                            startActivity(intent);
                                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                        }
                                    }
                                });

                            }
                        }
                    });


//
//                    final Handler handler1 = new Handler();
//                    handler1.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            final LatLng latlng = fusedLocation.getLastKnowLocation();
//
//                            if (checkIfLocationAvailable() || latlng != null) {
//                                try {
//                                    //getLastKnownLocation();
//
//
//
//                                    final Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//
//                                            String[] options = {"Farm Information", "Customer Information"};
//                                            final Dialog d1 = Helper.createCustomThemedListDialogWithPrompt(activity, options, "Add Marker",
//                                                    "Select the type of marker you want to add on this location.\n\nLat. " + latlng.latitude + "     Lng. " + latlng.longitude, R.color.blue);
//                                            ListView lvoptions = (ListView) d1.findViewById(R.id.dialog_list_listview);
//                                            lvoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                                @Override
//                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                    if (position == 0) {
//
//                                                        d1.hide();
//                                                        final Intent intent = new Intent(MapsActivity.this, Activity_Add_FarmInformation.class);
//                                                        intent.putExtra("latitude", latlng.latitude);
//                                                        intent.putExtra("longtitude", latlng.longitude);
//                                                        startActivity(intent);
//                                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//                                                    }
//
//                                                    if (position == 1) {
//                                                        d1.hide();
//                                                        final Intent intent = new Intent(MapsActivity.this, Activity_Add_CustomerInformation_Basic.class);
//                                                        intent.putExtra("latitude", latlng.latitude);
//                                                        intent.putExtra("longtitude", latlng.longitude);
//                                                        startActivity(intent);
//                                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//                                                    }
//                                                }
//                                            });
//                                        }
//                                    }, 1200);
//
//                                } catch (Exception e) {
//                                    dialogLocationNotAvailableOkOnly();
//                                }
//                            } else {
//                                dialogLocationNotAvailableOkOnly();
//                            }
//                        }
//                    }, 200);
                } else {
                    Helper.isLocationAvailablePrompt(context, activity);
                }

            }
        });

        nav_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, Activity_Settings.class);
                closeDrawer();
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {



                final String[] details = marker.getTitle().split("#-#");
                if (activeSelection.equalsIgnoreCase("farm")) {
                    String  curId = "";
                    for (int i = 0; i < marker.getTitle().length(); i++) {
                        char c = marker.getTitle().charAt(i);
                        if (c == '-') {
                            break;
                        }
                        curId = curId + c;
                    }

                    LatLng location = marker.getPosition();

                    Intent intent = new Intent(MapsActivity.this, Activity_ManagePonds.class);
                    intent.putExtra("id", Integer.parseInt(details[0]));
                    intent.putExtra("farmname", "" + details[1]);
                    intent.putExtra("latitude", location.latitude + "");
                    intent.putExtra("longitude", location.longitude + "");
                    startActivity(intent);
                } else if (activeSelection.equalsIgnoreCase("customer")) {

                    String[] options = new String[] {"Customer Details","Owned Farms"};
                    final Dialog d = Helper.createCustomThemedListDialog(activity,options, "Options", R.color.blue);
                    ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {


                                final Intent intent = new Intent(MapsActivity.this, Activity_CustomerDetails.class);
                                intent.putExtra("id", details[2]);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(intent);
                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    }
                                }, 100);
                                d.hide();
                            } else if (position == 1){
                                Log.d("SHOW MARKER", "getListOfFarms");
                                getListOfFarms(details[0]);
                                d.hide();
                            }
                        }
                    });

                }

            }

        });

        nav_growout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(MapsActivity.this, Activity_WeeklyReports_Growout_FeedDemands.class);
                closeDrawer();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                }, 280);

            }
        });

//        getCurrentLoc();
        // Acquire a reference to the system Location Manager
    }

    private void cancelMarkerAdding() {
        removeCircle();
        btn_cancelAddmarker.setVisibility(View.GONE);
        btn_add_marker.setEnabled(true);
        maps.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });
    }


    private boolean checkIfLocationAvailable() {
        GPSTracker gpstracker = new GPSTracker(this);
        return gpstracker.getIsGPSTrackingEnabled();
    }

    private void moveCameraAnimate(final GoogleMap map, final LatLng latlng, final int zoom) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
    }


    private void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void openDrawer() {
        drawerLayout.closeDrawer(Gravity.RIGHT);
    }



    private void mapClear(GoogleMap map) {
        map.clear();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
         @Override
         public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

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
    public void onLocationChanged(Location location) {
        textView.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            textView.setText(mLastLocation.getLatitude()+" "+mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void showAllRelatedMarkers() {
        PD.setMessage("Please wait...");
        PD.show();
        String url;
        if (passedintent != null){
            if (passedintent.hasExtra("fromActivity")){
                if (passedintent.getStringExtra("fromActivity").equalsIgnoreCase("login")
                        || passedintent.getStringExtra("fromActivity").equalsIgnoreCase("addfarminfo") ){
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
                    activeSelection = "farm";
                    Log.d("URL", "login and farminfo");
                }else if (passedintent.getStringExtra("fromActivity").equalsIgnoreCase("addcustomerinfo")){
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
                    Log.d("URL", "addcustomerinfo");
                    activeSelection = "customer";
                }else{
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
                    Log.d("URL", "default");
                    activeSelection = "farm";
                }
            }else{
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
                Log.d("URL", "fromActivity null");
            }
        }else{
                    url  = Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO;
            Log.d("URL", "passed intent null");
        }


        //get current userlvl
        if (userlvl == 1 || userlvl == 2 || userlvl == 3){ //if user is not TSR/Technician
            if (Helper.isNetworkAvailable(context)){ //if internet is availble
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {
                                if (response.substring(1, 2).equalsIgnoreCase("0")) {
                                    PD.dismiss(); updateDisplay();
                                } else {
                                    PD.dismiss();
                                    custInfoObjectList = CustAndPondParser.parseFeed(response);
                                    if (custInfoObjectList != null) {
                                        if (custInfoObjectList.size() > 0) {
                                            if (passedintent != null) {
                                                if (passedintent.hasExtra("fromActivity")) {
                                                    if (passedintent.getStringExtra("fromActivity").equalsIgnoreCase("login")) {
                                                        userid = extrass.getInt("userid");
                                                        userlevel = extrass.getInt("userlevel");
                                                        username = extrass.getString("username");
                                                        firstname = extrass.getString("firstname");
                                                        lastname = extrass.getString("lastname");
                                                        userdescription = extrass.getString("userdescription");
//                                                insertloginlocation();
                                                    }
                                                    activeSelection = "farm";
                                                    updateDisplay();
                                                } else { updateDisplay(); }
                                            } else { updateDisplay(); }
                                        } else {updateDisplay(); }
                                    } else {updateDisplay();}
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                PD.dismiss();
                                Helper.toastShort(MapsActivity.this, "Something happened. Please try again later");
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
                api.addToReqQueue(postRequest, MapsActivity.this);
            }

        }else if(userlvl == 0 || userlvl == 4) {//if user is tsr/technician... then query local database
            Log.d("DEBUG", "Before get Cursor from db");
            activeSelection = "farm";
            Cursor cur = db.getAll_FARMINFO_LEFTJOIN_PONDINFO_LEFTJOIN_CUSTOMERINFO(Helper.variables.getGlobalVar_currentUserID(activity) + "");
            getFarmPondCustFromDB(cur);
        }

    }

    private void getFarmPondCustFromDB(Cursor cur) {
        Log.d("DEBUG", "before cur not null");
        if(cur != null) {
            if(cur.getCount() > 0) {
                custInfoObjectList = new ArrayList<>();
                Log.d("DEBUG", "after new array list");
                while (cur.moveToNext()) {
                    CustInfoObject custInfoObject = new CustInfoObject();
                    /** FARM INFO **/
                    custInfoObject.setCi_id(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_ID)));
                    custInfoObject.setLatitude(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_LAT)));
                    custInfoObject.setLongtitude(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_LNG)));
                    custInfoObject.setContact_name(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_CONTACT_NAME)));
                    custInfoObject.setCompany(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_COMPANY)));
                    custInfoObject.setAddress(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_FARM_ADDRESS)));
                    custInfoObject.setFarmname(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_FARM_NAME)));
                    custInfoObject.setCounter(0);
                    custInfoObject.setFarmID(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_FARM_ID)));
                    custInfoObject.setContact_number(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_C_NUMBER)));
                    custInfoObject.setCultureType(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_CULTYPE)));
                    custInfoObject.setCulturelevel(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_CULTlVL)));
                    custInfoObject.setWaterType(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_WATTYPE)));
                    custInfoObject.setDateAddedToDB(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_FARMINFO_dateAdded)));
                    custInfoObject.setAllSpecie(cur.getString(cur.getColumnIndex("allSpecie"))); //(obj.getString("allSpecie"));

                    /** POND INFO **/
                    custInfoObject.setId(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_INDEX)));
                    custInfoObject.setTotalStockOfFarm(cur.getInt(cur.getColumnIndex("Totalquantity")));//(obj.getInt("Totalquantity"));
                    custInfoObject.setSizeofStock(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_sizeofStock)));
                    custInfoObject.setPondID(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_PID)));
                    custInfoObject.setQuantity(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_quantity)));
                    custInfoObject.setArea(cur.getInt(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_area)));
                    custInfoObject.setSpecie(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_specie)));
                    custInfoObject.setDateStocked(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_dateStocked)));
                    custInfoObject.setCulturesystem(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_culturesystem)));
                    custInfoObject.setRemarks(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_remarks)));
                    custInfoObject.setCustomerID(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_customerId)));
                    custInfoObject.setSurvivalrate_per_pond(cur.getString(cur.getColumnIndex(GpsSQLiteHelper.CL_POND_survivalrate)));

                    /** ADDRESS **/
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

                Log.d("DEBUG", "before active selection"+ activeSelection);
                if (activeSelection=="farm"){
                    updateDisplay();
                }
                PD.dismiss();
            }else{
                PD.dismiss();
                prompt_noFarm();
            }
        }else{
            PD.dismiss();
            prompt_noFarm();
        }
    }


    public void showAllCustomerFarmByID(final String farmid) {


        if (userlvl == 1 || userlvl == 2 || userlvl == 3){
            PD.setMessage("Please wait...");
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_SELECT_FARMINFO_LF_POND_AND_MCI_BY_FARMID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {

                            if (response.substring(1, 2).equalsIgnoreCase("0")) {
                                PD.dismiss();
                                updateDisplay();
                            } else {
                                PD.dismiss();
                                custInfoObjectList = CustAndPondParser.parseFeed(response);
                                if (custInfoObjectList != null) {
                                    if (custInfoObjectList.size() > 0) {

                                        maps.clear();
                                        activeSelection = "farm";
                                        maps.setInfoWindowAdapter(new FarmInfoWindow());

                                        for (int i = 0; i < custInfoObjectList.size(); i++) {
                                            final CustInfoObject ci;
                                            ci = custInfoObjectList.get(i);
                                            Log.d("MARKER", "ADDING FARM MARKER"+1);
                                            LatLng custLatlng = new LatLng(Double.parseDouble(ci.getLatitude() + ""), Double.parseDouble(ci.getLongtitude() + ""));
                                            Helper.map_addMarker(maps, custLatlng,
                                                    R.drawable.ic_place_red_24dp, ci.getFarmname(), ci.getAddress(), ci.getCi_id() + "", ci.getTotalStockOfFarm() + "",
                                                    ci.getAllSpecie() + "#-#" + ci.getCust_latitude() + "#-#" + ci.getCust_longtitude() + "#-#" + ci.getMainCustomerId());
                                        }
                                    }else {maps.clear();}
                                }else{maps.clear();}
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Helper.toastShort(MapsActivity.this, "Something happened. Please try again later");
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
                    params.put("farmid", farmid+"");

//
                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, MapsActivity.this);
        }else if(userlvl == 0 || userlvl == 4) {
            updateDisplay();
        }

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
                                prompt_noCustomerLocation();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Helper.toastShort(MapsActivity.this,"Something happened. Please try again later");
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
            api.addToReqQueue(postRequest, MapsActivity.this);

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
                    prompt_noCustomerLocation();
                }
            }else{
                PD.dismiss();
                prompt_noCustomerLocation();
            }
        }

    }

    private void showCustomerLocation() {
        if (custInfoObjectList!=null){
            if (custInfoObjectList.size() > 0){
                for (int i = 0; i < custInfoObjectList.size(); i++) {

                    String address = custInfoObjectList.get(i).getHouseNumber()+"";

                    if(!custInfoObjectList.get(i).getStreet().equalsIgnoreCase(" --- ")){
                        address = address + " " + custInfoObjectList.get(i).getStreet();
                    }
                    if(!custInfoObjectList.get(i).getSubdivision().equalsIgnoreCase(" --- ")){
                        address = address + ", " + custInfoObjectList.get(i).getSubdivision();
                    }
                    address = address + "" + ", " + custInfoObjectList.get(i).getBarangay() + ", " + custInfoObjectList.get(i).getCity() + ", " + custInfoObjectList.get(i).getProvince();

                    maps.setInfoWindowAdapter(new CustomerInfoWindow());
                    Log.d("DEBUG", "SHOW all custlocation - Active selection customer");
                    activeSelection = "customer";
                    Helper.map_addMarker(maps, new LatLng(Double.parseDouble(custInfoObjectList.get(i).getCust_latitude()), Double.parseDouble(custInfoObjectList.get(i).getCust_longtitude())),
                            R.drawable.ic_housemarker_24dp,
                            custInfoObjectList.get(i).getFirstname() + " " + custInfoObjectList.get(i).getLastname(), //Firstname and LastName
                            address, custInfoObjectList.get(i).getFarmID(), custInfoObjectList.get(i).getMainCustomerId(), "0");
                }
            }else{ prompt_noCustomerLocation();}
        }else{ prompt_noCustomerLocation();}
    }

    private void prompt_noCustomerLocation() {
        Helper.createCustomThemedColorDialogOKOnly(activity, "Warning", "You have not added any customer address", "OK", R.color.red);
    }


    public void getListOfFarms(final String farmid){
        PD.setMessage("Please wait...");
        PD.show();
        String url = Helper.variables.URL_SELECT_FARM_BY_FARMID;
        if (userlvl == 1 || userlvl == 2 || userlvl == 3){
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {

                            PD.dismiss();

                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                custInfoObjectList = CustAndPondParser.parseFeed(response);

                                showAllCustomerFarmByFarmID();
                            } else {Helper.createCustomThemedColorDialogOKOnly(activity, "Warning", "No farm related to selected customer. Please check Farm ID", "OK", R.color.red);}
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Helper.toastShort(MapsActivity.this,"Something happened. Please try again later");
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
                    params.put("farmid", farmid+"");
//
                    return params;
                }
            };

            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, MapsActivity.this);
        }else if(userlvl == 0 || userlvl == 4) {
            Cursor cur = db.getFARM_POND_CUSTOMER_BY_FARMID(Helper.variables.getGlobalVar_currentUserID(activity)+"", farmid);
            getFarmPondCustFromDB(cur);
            Log.d("SHOW MARKER", "showAllCustomerFarmByFarmID");
            showAllCustomerFarmByFarmID();

        }

    }

    private void showAllCustomerFarmByFarmID() {
        if (custInfoObjectList!=null){
            if (custInfoObjectList.size() > 0) {

                Log.d("DEBUG", "Show all custinfo by farmID - active selection customer");
                activeSelection = "customer";
                String farmnames[] = new String[custInfoObjectList.size()];
                for (int i = 0; i < custInfoObjectList.size(); i++) {
                    farmnames[i] = custInfoObjectList.get(i).getFarmname();
                }

                final Dialog d = Helper.createCustomThemedListDialog(activity, farmnames, "Farm(s)", R.color.darkgreen_800);
                d.show();
                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        maps.setInfoWindowAdapter(new FarmInfoWindow());
                        double lat = Double.parseDouble(custInfoObjectList.get(position).getLatitude() + "");
                        double lng = Double.parseDouble(custInfoObjectList.get(position).getLongtitude() + "");
                        LatLng latLng = new LatLng(lat, lng);
//                                            Helper.toastShort(activity, custInfoObjectList.get(position).getFarmID() + " " + custInfoObjectList.get(position).getLongtitude() + " " + custInfoObjectList.get(position).getLatitude());
                        Helper.moveCameraAnimate(maps, latLng, 15);
                        maps.clear();
                        activeSelection = "farm";
                        showAllCustomerFarmByID(custInfoObjectList.get(position).getFarmID());
                        d.hide();
                    }
                });
            }else{Helper.createCustomThemedColorDialogOKOnly(activity, "Warning", "No farm related to selected customer. Please check Farm ID" , "OK", R.color.red);}
        }else{ Helper.createCustomThemedColorDialogOKOnly(activity, "Warning", "No farm related to selected customer. Please check Farm ID", "OK", R.color.red);}
    }


    private void insertloginlocation(){
        fusedLocation.connectToApiClient();
        if (Helper.isIntentKeywordNotNull("fromActivity", passedintent)){
          if (extrass.getString("fromActivity").equalsIgnoreCase("login")) {
              Log.d("EXTRAS", "fromactivity = login");

              userid = extrass.getInt("userid");
              userlevel = extrass.getInt("userlevel");
              username = extrass.getString("username");
              firstname = extrass.getString("firstname");
              lastname = extrass.getString("lastname");
              userdescription = extrass.getString("userdescription");

              if (Logging.loguserAction(activity, context, Helper.userActions.TSR.USER_LOGIN, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING)){
                  Helper.toastShort(activity, "Location found :) ");
                  passedintent = null;
              }
          }

        }

    }



    protected void updateDisplay() {
        Log.d("UPDATE DISPLAY", "CUSTINFOOBJECT");
        if (custInfoObjectList != null) {
            Log.d("UPDATE DISPLAY", "not null");
            if (custInfoObjectList.size() > 0) {
                Log.d("UPDATE DISPLAY", "not zero");
                for (int i = 0; i < custInfoObjectList.size(); i++) {
                    final CustInfoObject ci;
                    ci = custInfoObjectList.get(i);
                    maps.setInfoWindowAdapter(new FarmInfoWindow());
                    LatLng custLatlng = new LatLng(Double.parseDouble(ci.getLatitude() + ""), Double.parseDouble(ci.getLongtitude() + ""));
                    Helper.map_addMarker(maps, custLatlng,
                            R.drawable.ic_place_red_24dp, ci.getFarmname(), ci.getAddress(), ci.getCi_id() + "", ci.getTotalStockOfFarm() + "",
                            ci.getAllSpecie() + "#-#" + ci.getCust_latitude() + "#-#" + ci.getCust_longtitude() + "#-#" +ci.getFarmID());
                }
            } else {prompt_noFarm();}
        } else {prompt_noFarm(); }
    }

    private void prompt_noFarm() {
        final Dialog d = Helper.createCustomThemedColorDialogOKOnly(activity, "MAP", "You have not added a farm yet. \n You can start by pressing the  plus '+' on the upper right side of the screen.", "OK", R.color.skyblue_400);
        Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
        d.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        db.close();
        Log.d("PROCESS", "Onpause");
    }


    //class for infoWindow
    class FarmInfoWindow implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;
        FarmInfoWindow() {
            myContentsView = getLayoutInflater().inflate(R.layout.infowindow_farminfo, null);
        }
        @Override
        public View getInfoWindow(Marker marker) {
            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.addres));
            TextView txtStock = ((TextView) myContentsView.findViewById(R.id.stocks));
            TextView txtSpecie = ((TextView) myContentsView.findViewById(R.id.specie));
            String[] details = marker.getTitle().split("#-#");

            tvTitle.setText(details[1]);

            if (details[2].equalsIgnoreCase("") || details[2].equalsIgnoreCase("null")){
                txtStock.setText("n/a");
            } else{txtStock.setText("" + details[2]);}

            if (details[3].equalsIgnoreCase("") || details[3].equalsIgnoreCase("null")){
                txtSpecie.setText("n/a"); } else{ txtSpecie.setText("" + details[3]); }
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }


    class CustomerInfoWindow implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;
        CustomerInfoWindow() {
            myContentsView = getLayoutInflater().inflate(R.layout.infowindow_customer_address, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.address));
            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
            String[] details = marker.getTitle().split("#-#");

            tvSnippet.setText(marker.getSnippet());
            tvTitle.setText(details[1]+"");
            return myContentsView;
        }


        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        fusedLocation.connectToApiClient();
        db.open();
        if(activeFilter==0){
            activeFilter = 0;
        }
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    private void exitApp() {
        final Dialog d = Helper.createCustomDialogYesNO(activity, R.layout.dialog_material_yesno, "Do you wish to wish to exit the app? You will have to login next time.", "EXIT", "YES", "NO");
        d.show();
        Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
        Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
                finishAffinity();
                Intent setIntent = new Intent(Intent.ACTION_MAIN);
                setIntent.addCategory(Intent.CATEGORY_HOME);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
    }


    private void logout() {
        final Dialog d = Helper.createCustomDialogYesNO(activity, R.layout.dialog_material_yesno, "Do you wish to wish to return to Login Screen?", "Log Out", "YES", "NO");
        d.show();
        Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
        Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
                closeDrawer();
                final Dialog d2 =  Helper.initProgressDialog(activity);
                d2.show();

                TextView message = (TextView) d2.findViewById(R.id.progress_message);
                message.setText("Logging out...");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        d2.hide();
                        logoff();
                    }
                }, 1500);


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
    }

    public void logoff(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }, 300);
    }

    private void removeCircle(){
        if(mapcircle!=null){
            mapcircle.remove();
            mapcircle = null;
        }

    }


}
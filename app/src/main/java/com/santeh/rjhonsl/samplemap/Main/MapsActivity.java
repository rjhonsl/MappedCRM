package com.santeh.rjhonsl.samplemap.Main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.ui.IconGenerator;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Obj.Var;
import com.santeh.rjhonsl.samplemap.Parsers.CustAndPondParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.GPSTracker;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ProgressDialog PD;
    boolean isDrawerOpen = false;
    private boolean mPaused;

    private LocationSource.OnLocationChangedListener mListener;
    Location mLastLocation;

    String username, firstname, lastname, userdescription;
    int userlevel, userid;

    DrawerLayout drawerLayout;

    ImageButton map_add_marker;
    ActionBarDrawerToggle drawerListener;


    double curlat, curLong;
    int zoom = 15,
        activeFilter;

    Activity activity;
    Context context;
    GoogleApiClient mGoogleApiClient;
    GoogleMap maps, googleMap;

    LatLng curLatlng, lastlatlng;

    TextView textView, tvlat, tvlong;
    TextView nav_fingerlings, nav_Stockings, nav_sperms, nav_logout, nav_maptype, nav_displayAllMarkers, nav_settings, nav_growout,nav_usermonitoring, txtusername;

    EditText editSearch;
    String Stritem;


    List<CustInfoObject> custInfoObjectList;
    List<CustInfoObject> searchedIDlist = null;

    Bundle extrass;
    Intent passedintent;

    FusedLocation fusedLocation;


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


        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.buildGoogleApiClient(context);
        fusedLocation.connectToApiClient();

        lastlatlng = fusedLocation.getLastKnowLocation();


        extrass = getIntent().getExtras();
        passedintent = getIntent();



        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nav_displayAllMarkers = (TextView) findViewById(R.id.txt_Nav_displayAll);
        nav_fingerlings = (TextView) findViewById(R.id.txt_Nav_fingerlings);
        nav_Stockings = (TextView) findViewById(R.id.txt_Nav_customeraddress);
        nav_sperms = (TextView) findViewById(R.id.txt_Nav_sperms);
        nav_maptype = (TextView) findViewById(R.id.txt_Nav_changeMapType);
        nav_settings = (TextView) findViewById(R.id.txt_Nav_settings);
        map_add_marker = (ImageButton) findViewById(R.id.btnaddMarker);
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

        if (extras != null) {
            if (extras.getString("fromActivity") != null){
                String from = extras.getString("fromActivity");

                if (from.equalsIgnoreCase("viewCustinfo")) {
                    if (extras.getString("lat")!=null && extras.get("long")!= null) {
                        LatLng latLng = new LatLng(
                                Double.parseDouble(extras.getString("lat")),
                                Double.parseDouble(extras.getString("long"))  );

                        if(((Var) this.getApplication()).getGoogleMap() != null){
                            moveCameraAnimate(((Var) this.getApplication()).getGoogleMap(), latLng, 14);
                        maps.clear();
                        Helper.map_addMarker(((Var) this.getApplication()).getGoogleMap(), latLng, R.drawable.ic_place_red_24dp,
                                extras.getString("contactName"), extras.getString("address"), extras.getInt("id")+"",null, null);
                        PD.dismiss();
                        }
                        else{
                            Helper.toastShort(activity, "Can't find current location. Please try again later.");
                        }

                    }
                    
                }else{
                    showAllRelatedMarkers();
                }

            }
            else {
                    showAllRelatedMarkers();
            }

        }
        else{
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
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        initListners(map);
        fusedLocation.connectToApiClient();


        if (Helper.variables.getGlobalVar_currentlevel(activity) > 1){
            nav_usermonitoring.setVisibility(View.GONE);
        }else{
            nav_usermonitoring.setVisibility(View.VISIBLE);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
//            Helper.toastLong(activity, extrass.getString("lat") + " " + extrass.getString("long") );


                    if (checkIfLocationAvailable()){
                        moveCameraAnimate(map, fusedLocation.getLastKnowLocation(), zoom);
                        initMarkers();

                    }
                    else{
                        PD.hide();
                        //West avenue
                        curlat = 14.651391;
                        curLong = 121.029335;
                        zoom = 9;

                    }

                }catch(Exception e){
                    Helper.toastShort(activity, "Location is not available: "+e);
                }
            }
        }, 500);



    }

    private void initListners(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {

            }

        });

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
            }
        });


        nav_fingerlings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                closeDrawer();
            }
        });

        nav_Stockings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClear(map);
                closeDrawer();
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

                String[] maptypes = {"Normal","Satellite","Terrain", "Hybrid"};
                final Dialog dd = Helper.createCustomListDialog(activity, maptypes, "Map Types");
                ListView lstMapType = (ListView) dd.findViewById(R.id.dialog_list_listview);
                dd.show();

                lstMapType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0){
                            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            dd.hide();
                        }else if (position == 1) {
                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            dd.hide();
                        }else if (position == 2) {
                            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            dd.hide();
                        }
                        else if (position == 3) {
                            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            dd.hide();
                        }
                    }
                });
            }
        });


        map_add_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setUpMap();
                fusedLocation.connectToApiClient();


                final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final LatLng latlng = fusedLocation.getLastKnowLocation();

                        if (checkIfLocationAvailable() || latlng != null) {
                            try {
                                //getLastKnownLocation();

                                moveCameraAnimate(map, new LatLng(latlng.latitude, latlng.longitude), 15);
//                        Helper.toastShort(MapsActivity.this,latlng.latitude+ " "+ latlng.longitude);

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        String[] options = {"Farm Information", "Customer Information"};
                                        final Dialog d1 = Helper.createCustomThemedListDialogWithPrompt(activity, options, "Add Marker",
                                            "Select the type of marker you want to add on this location.\n\nLat. " + latlng.latitude + "     Lng. " + latlng.longitude, R.color.blue);
                                        ListView lvoptions = (ListView) d1.findViewById(R.id.dialog_list_listview);
                                        lvoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                if (position == 0){

                                                    d1.hide();
                                                    final Intent intent = new Intent(MapsActivity.this, Activity_Add_FarmInformation.class);
                                                    intent.putExtra("latitude", latlng.latitude);
                                                    intent.putExtra("longtitude", latlng.longitude);
                                                    startActivity(intent);
                                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                                }

                                                if (position == 1){
                                                    d1.hide();
                                                    final Intent intent = new Intent(MapsActivity.this, Activity_Add_CustomerInfomration.class);
                                                    intent.putExtra("latitude", latlng.latitude);
                                                    intent.putExtra("longtitude", latlng.longitude);
                                                    startActivity(intent);
                                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                                }
                                            }
                                        });

//                                        final Dialog d = Helper.createCustomDialogYesNO(MapsActivity.this, R.layout.dialog_material_yesno,
//                                                "Do you wish to add customer information here at your current location?\n\n" +
//                                                        "Latitude  : " + curlat + "\n" +
//                                                        "Longtitude: " + curLong, "ADD MARKER", "YES", "NO");
//                                        d.show();
//                                        TextView opt1 = (TextView) d.findViewById(R.id.btn_dialog_yesno_opt1),
//                                                opt2 = (TextView) d.findViewById(R.id.btn_dialog_yesno_opt2);
//
//                                        opt1.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                d.hide();
//                                                startActivity(intent);
//                                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//                                            }
//                                        });
//
//                                        opt2.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                d.hide();
//                                            }
//                                        });
                                    }
                                }, 1200);

                            } catch (Exception e) {
                                dialogLocationNotAvailableOkOnly();
                            }
                        } else {
                            dialogLocationNotAvailableOkOnly();
                        }
                    }
                }, 200);
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
            public void onInfoWindowClick(Marker marker) {

                String ID = marker.getId(), curId = "";
                for (int i = 0; i < marker.getTitle().length(); i++) {
                    char c = marker.getTitle().charAt(i);
                    if (c == '-') {
                        break;
                    }
                    curId = curId + c;
                }

                String[] details = marker.getTitle().split("-");

//                Helper.toastShort(activity, "."+marker.getTitle()+"."+details[0]);
                LatLng location = marker.getPosition();

                Intent intent = new Intent(MapsActivity.this, Activity_ManagePonds.class);
                intent.putExtra("id", Integer.parseInt(details[0]));
                intent.putExtra("farmname", "" + details[1]);
                intent.putExtra("latitude", location.latitude +"");
                intent.putExtra("longitude", location.longitude+ "");
                startActivity(intent);
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

    private void dialogLocationNotAvailableOkOnly() {
        final Dialog d = Helper.createCustomDialogOKOnly(MapsActivity.this,
                "LOCATION SERVICE",
                "Location is not available. Please turn your Location(GPS) Service ON and stand in an area with no obstruction for better accuracy.",
                "OK");
        TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
    }

    private Bitmap iconGeneratorSample(Context context, String str) {

        IconGenerator iconGenerator = new IconGenerator(context);
        iconGenerator.setBackground(getResources().getDrawable(R.drawable.ic_place_red_24dp));
//        iconGenerator.setColor(Color.parseColor("#00000"));
        iconGenerator.setColor(R.color.yellow);
        iconGenerator.setTextAppearance(R.style.IconGeneratorTextView);
        return  iconGenerator.makeIcon(str);
    }

    private boolean checkIfLocationAvailable() {
        GPSTracker gpstracker = new GPSTracker(this);
        return gpstracker.getIsGPSTrackingEnabled();
    }

    private void moveCameraAnimate(final GoogleMap map, final LatLng latlng, final int zoom) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
    }

    public void getDistance(LatLng oldposition, LatLng newPosition) {
        float[] results = new float[1];

        Location.distanceBetween(oldposition.latitude, oldposition.longitude,
                newPosition.latitude, newPosition.longitude, results);

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

    private LatLng getLastKnownLocation() {
        fusedLocation.connectToApiClient();

        LatLng latLng = fusedLocation.getLastKnowLocation();

        curlat = latLng.latitude;
        curLong = latLng.longitude;

//        String location_context = Context.LOCATION_SERVICE;
//        LocationManager mLocationManager = (LocationManager) activity.getSystemService(location_context);
//
//        List<String> providers = mLocationManager.getProviders(true);
//        Location bestLocation = null;
//        for (String provider : providers) {
//            final Location l = mLocationManager.getLastKnownLocation(provider);
////            int d = Log.d("last known location, provider: %s, location: %s", provider);
//
//            if (l == null) {
//                continue;
//            }
//            if (bestLocation == null
//                    || l.getAccuracy() < bestLocation.getAccuracy()) {
////                Log.d("found best last known location: %s", String.valueOf(l));
//                bestLocation = l;
//            }
//        }
//        if (bestLocation == null) {
//            return null;
//        }
        return latLng;
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

    public void showAllRelatedMarkers(){
        PD.setMessage("Please wait...");
        PD.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

///                        Helper.toastLong(activity, response);
                        if (response.substring(1, 2).equalsIgnoreCase("0")) {
                            PD.dismiss();
                            updateDisplay();
                        } else {
                            PD.dismiss();
                            custInfoObjectList = CustAndPondParser.parseFeed(response);
//                            Helper.toastShort(activity, "after parse feed");
                            if (custInfoObjectList != null) {
//                                Helper.toastShort(activity, "obj not null");
                                if (custInfoObjectList.size() > 0) {
                                 //   Helper.toastShort(activity, "obj not zero");
                                    if (passedintent != null) {
                                       // Helper.toastShort(activity, "intent not null");
                                        if (passedintent.hasExtra("fromActivity")) {
                                         //   Helper.toastShort(activity, "ihas extra");
                                            if (passedintent.getStringExtra("fromActivity").equalsIgnoreCase("login")) {
                                             //   Helper.toastShort(activity, "from login");

                                                userid = extrass.getInt("userid");
                                                userlevel = extrass.getInt("userlevel");
                                                username = extrass.getString("username");
                                                firstname = extrass.getString("firstname");
                                                lastname = extrass.getString("lastname");
                                                userdescription = extrass.getString("userdescription");
                                                insertloginlocation();
                                                updateDisplay();
                                            } else {
                                               // Helper.toastShort(activity, "not login");
                                                updateDisplay();
                                            }
                                        } else {
                                            updateDisplay();
                                        }
                                    } else {
                                        updateDisplay();
                                    }
                                } else {
                                    updateDisplay();
                                }
                            } else {
                                updateDisplay();
                            }
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", Helper.variables.getGlobalVar_currentUsername(activity));
                params.put("password", Helper.variables.getGlobalVar_currentUserpassword(activity));
                params.put("deviceid", Helper.getMacAddress(context));
                params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                params.put("userlvl", Helper.variables.getGlobalVar_currentlevel(activity)+"");
//
                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, MapsActivity.this);
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
        Log.d("UPDATE DISPLAY", "before condition "+ String.valueOf("a"));



        if(custInfoObjectList != null){

            Log.d("UPDATE DISPLAY", "after condition");
            for (int i = 0; i < custInfoObjectList.size(); i++) {
                final CustInfoObject ci;
                ci = custInfoObjectList.get(i);
                Log.d("JSON PARSE", "" + ci.getFarmID() +" " +ci.getFarmname());
                LatLng custLatlng = new LatLng(Double.parseDouble(ci.getLatitude()), Double.parseDouble(ci.getLongtitude()));
                Marker marker = Helper.map_addMarker(maps, custLatlng,
                        R.drawable.ic_place_red_24dp, ci.getFarmname(), ci.getAddress(), ci.getCi_id() + "", ci.getTotalStockOfFarm() + "", ci.getAllSpecie());

            }
        }else {
            final Dialog d = Helper.createCustomThemedColorDialogOKOnly(activity, "MAP", "You have not added a farm yet. You can start by pressing the  plus(' + ') on the upper right side of the screen.", "OK", R.color.skyblue_400);
            Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
            d.show();

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("PROCESS","Onpause");
    }



    //class that handles CustomInfowWindow
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        CustomInfoWindowAdapter() {
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }


        @Override
        public View getInfoWindow(Marker marker) {

            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.addres));
            TextView txtStock = ((TextView) myContentsView.findViewById(R.id.stocks));
            TextView txtSpecie = ((TextView) myContentsView.findViewById(R.id.specie));
//            id + "-" + farmname +"-"+ totalstock + "-" + specie
            String[] details = marker.getTitle().split("-");

            tvTitle.setText(details[1]);
            if (details[2].equalsIgnoreCase("") || details[2].equalsIgnoreCase("null")){
                txtStock.setText("n/a");
            } else{
                txtStock.setText("" + details[2]);
            }

            if (details[3].equalsIgnoreCase("") || details[3].equalsIgnoreCase("null")){
                txtSpecie.setText("n/a");
            } else{
                txtSpecie.setText("" + details[3]);
            }


            tvSnippet.setText(marker.getSnippet());


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
        Log.d("PROCESS", "REsume");

        if(activeFilter==0){
            activeFilter = 0;
        }
        else{

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

}
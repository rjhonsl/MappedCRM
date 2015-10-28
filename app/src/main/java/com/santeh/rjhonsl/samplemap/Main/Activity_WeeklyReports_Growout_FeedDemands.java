package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.Adapter_Growouts_AllFarmDemands;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.CustAndPondParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 8/28/2015.
 */
public class Activity_WeeklyReports_Growout_FeedDemands extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    String type = "";

    TextView title, txtheader;

    SimpleDateFormat formatter = new SimpleDateFormat("MMM d");//EEE, MMM d, ''yy
    SimpleDateFormat sdf_sql   = new SimpleDateFormat("yyyy/MM/dd");//EEE, MMM d, ''yy
    Adapter_Growouts_AllFarmDemands custinfoAdapter;
    Activity activity;

    ProgressDialog PD;

    List<CustInfoObject> pondInfoObj;
    List<CustInfoObject> currentDemandList;

    CustInfoObject tempOBJ;
    String strstartdate="", strenddate="";

    ImageButton btnfilter;
    LinearLayout llNoQuery;

    public static final String DATEPICKER_TAG = "datepicker";
    DatePickerDialog datePickerDialog;
    int y, m, d;

    ListView lvFarmlist;

    int[] allStockedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeklyreports_growout_feeddemandsummary);
        activity = this;

        PD = new ProgressDialog(this);
        PD.setCancelable(false);
        llNoQuery = (LinearLayout) findViewById(R.id.llnoQuery);

        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        lvFarmlist = (ListView) findViewById(R.id.lv_growout_feedconsumption_summary);
        btnfilter = (ImageButton) findViewById(R.id.btn_filter);

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            type = extras.getString("type");
        }else{
            type = "";
        }

        title = (TextView) findViewById(R.id.titlebar_header);
        txtheader = (TextView) findViewById(R.id.lbl_weeklyreports_grow_out_summary);
        txtheader.setMovementMethod(new ScrollingMovementMethod());

        Calendar cal = new GregorianCalendar();
        getStartAndEndOfCurrentWeek(cal);

        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1980, 2030);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });


    }


    private void getStartAndEndOfCurrentWeek(Calendar cal) {

        // get start of this week in milliseconds

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        txtheader.setText("Demands for this week, "  + formatter.format(cal.getTimeInMillis()) + " - ");
        strstartdate = sdf_sql.format(cal.getTimeInMillis());

        // start of the next week
        cal.add(Calendar.DAY_OF_WEEK, 6);
        txtheader.setText(txtheader.getText() + formatter.format(cal.getTimeInMillis()));
        strenddate = sdf_sql.format(cal.getTimeInMillis());

//        txtheader.setText(strstartdate + " - " + strenddate +" "+ y + m + d);
        getData(Helper.variables.URL_SELECT_ALL_POND_WEEKLYUPDATES_INNERJOIN_PONDINFO_GROUPBY_FARMNAME);
    }


    public void getData(String url) {

        if(!Helper.isNetworkAvailable(activity)) {
            Helper.toastShort(activity, "Internet Connection is not available. Please try again later.");
        }
        else{
            PD.setMessage("Retrieving info... Please wait.");
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PD.dismiss();
//                            txtheader.setText(response);
                            if (!response.substring(1,2).equalsIgnoreCase("0")){
                                pondInfoObj = CustAndPondParser.parseFeed(response);
                                if (pondInfoObj != null) {
                                    if (pondInfoObj.size() > 0) {
                                        custinfoAdapter = new Adapter_Growouts_AllFarmDemands(Activity_WeeklyReports_Growout_FeedDemands.this,
                                                R.layout.item_lv_weeklyreport_allfeeddemands, pondInfoObj);
                                        lvFarmlist.setAdapter(custinfoAdapter);
                                        lvFarmlist.setVisibility(View.VISIBLE);
                                        llNoQuery.setVisibility(View.GONE);
                                    }else{ Helper.toastLong(activity, "There are no demands for this week.");}
                                }else{Helper.toastLong(activity, "There are no demands for this week.");}
                            }
                            else {
                                if (custinfoAdapter != null){
                                    custinfoAdapter.clear();
                                }
                                lvFarmlist.setVisibility(View.GONE);
                                llNoQuery.setVisibility(View.VISIBLE);
                            }
//
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Toast.makeText(Activity_WeeklyReports_Growout_FeedDemands.this,
                            "Failed to search " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                    params.put("deviceid", Helper.getMacAddress(activity));
                    params.put("username", Helper.variables.getGlobalVar_currentUserName(activity));
                    params.put("password", Helper.variables.getGlobalVar_currentUserPassword(activity));
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentLevel(activity)+"");
                    params.put("startdate", strstartdate);
                    params.put("enddate", strenddate);
                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_WeeklyReports_Growout_FeedDemands.this);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        y = year;
        m = month;
        d = day;
        Calendar cal = new GregorianCalendar();
        cal.clear();
        Date date = new Date(y, m, d);
        cal.setTime(date);
        cal.set(Calendar.YEAR, year);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        getStartAndEndOfCurrentWeek(cal);
    }
}

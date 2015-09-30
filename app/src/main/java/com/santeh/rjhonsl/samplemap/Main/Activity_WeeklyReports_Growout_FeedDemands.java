package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.Adapter_Growouts_AllFarmDemands;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.CustAndPondParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 8/28/2015.
 */
public class Activity_WeeklyReports_Growout_FeedDemands extends Activity {

    String type = "";

    TextView title, txtheader;

    SimpleDateFormat formatter = new SimpleDateFormat("MMM d");//EEE, MMM d, ''yy
    SimpleDateFormat sdf_sql   = new SimpleDateFormat("MM/dd/yy");//EEE, MMM d, ''yy

    Activity activity;

    ProgressDialog PD;

    List<CustInfoObject> pondInfoObj;
    List<CustInfoObject> currentDemandList;

    CustInfoObject tempOBJ;
    String strstartdate="", strenddate="";

    ListView lvFeedConsumption;

    int[] allStockedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeklyreports_growout_feeddemandsummary);
        activity = this;

        PD = new ProgressDialog(this);
        PD.setCancelable(false);

        lvFeedConsumption = (ListView) findViewById(R.id.lv_growout_feedconsumption_summary);

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            type = extras.getString("type");
        }else{
            type = "";
        }

        title = (TextView) findViewById(R.id.titlebar_header);
        txtheader = (TextView) findViewById(R.id.lbl_weeklyreports_grow_out_summary);
        txtheader.setMovementMethod(new ScrollingMovementMethod());

        getStartAndEndOfWeek();


    }

    private void getStartAndEndOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        if(type.equalsIgnoreCase("current")){
            // get start of this week in milliseconds
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            txtheader.setText("Demands for this week, " + formatter.format(cal.getTimeInMillis()) + " - ");
            strstartdate = sdf_sql.format(cal.getTimeInMillis());

            // start of the next week
            cal.add(Calendar.DAY_OF_WEEK, 6);
            txtheader.setText(txtheader.getText() + formatter.format(cal.getTimeInMillis()));
            strenddate = sdf_sql.format(cal.getTimeInMillis());

            getData(Helper.variables.URL_SELECT_ALL_PONDINFO_LEFTJOIN_CUSTINFO);
        }
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
                            pondInfoObj = CustAndPondParser.parseFeed(response);
//                            txtheader.setText(response);
                            if (!response.substring(1,2).equalsIgnoreCase("0")){

                                DateTime dt = new DateTime();
                                GregorianCalendar jdkGcal = dt.toGregorianCalendar();
                                dt = new DateTime(jdkGcal);
                                if (!pondInfoObj.isEmpty()){
                                    if (pondInfoObj.size() > 0) {
                                        currentDemandList = new ArrayList<CustInfoObject>();
                                        for (int i = 0; i < pondInfoObj.size(); i++) {
                                            tempOBJ = new CustInfoObject();


                                            String[] datesplitter = pondInfoObj.get(i).getDateStocked().split("/");
                                            int weekStocked = Helper.get_Tilapia_WeekNum_byABW(pondInfoObj.get(i).getSizeofStock());
                                            int weeknoStarted = Helper.get_Tilapia_WeekNum_byABW(pondInfoObj.get(i).getQuantity());
                                            long currendate = System.currentTimeMillis();
                                            long stockeddate = Helper.convertDateToLong(Integer.parseInt(datesplitter[1]), Integer.parseInt(datesplitter[0]), Integer.parseInt(datesplitter[2]));

                                            Log.d("Week Summary", stockeddate + " " + currendate + " " + weekStocked);
                                            DateTime start = new DateTime(
                                                    Integer.parseInt(datesplitter[2]), //year
                                                    Integer.parseInt(datesplitter[0]), //month
                                                    Integer.parseInt(datesplitter[1]), //day
                                                    0, 0, 0, 0);

                                            datesplitter = Helper.convertLongtoDateString(currendate).split("/");
                                            DateTime end = new DateTime(
                                                    Integer.parseInt(datesplitter[2]), //year
                                                    Integer.parseInt(datesplitter[1]), //month
                                                    Integer.parseInt(datesplitter[0]), //day
                                                    0, 0, 0, 0);


                                            Weeks weeks = Weeks.weeksBetween(start, end);

                                            int currentWeekOfStock = weekStocked + weeks.getWeeks();
                                            String str_currentConsumption = "Blind Feeding";
                                            double currentABW = 0;

                                            if ((currentWeekOfStock > 0) && (currentWeekOfStock < 19)) {

                                                currentABW = Helper.get_ABW_BY_WEEK_NO(currentWeekOfStock);

                                                if (Double.parseDouble(Helper.computeWeeklyFeedConsumption(currentABW, pondInfoObj.get(i).getQuantity(),
                                                        Helper.get_TilapiaFeedingRate_by_WeekNum(currentWeekOfStock), 0.7)) > 0) {
                                                    str_currentConsumption = "" + (currentABW * pondInfoObj.get(i).getQuantity() * Helper.get_TilapiaFeedingRate_by_WeekNum(currentWeekOfStock) * 0.7 * 7);
                                                }
                                                tempOBJ.setQuantity(pondInfoObj.get(i).getQuantity());
                                                tempOBJ.setFarmname(pondInfoObj.get(i).getFarmname());
                                                tempOBJ.setCurrentweekofStock(currentWeekOfStock);
                                                tempOBJ.setSpecie(pondInfoObj.get(i).getSpecie());
                                                tempOBJ.setCurrentFeedType(Helper.getFeedTypeByNumberOfWeeks(currentWeekOfStock));
                                                tempOBJ.setWeeklyConsumptionInGrams(Double.parseDouble(Helper.computeWeeklyFeedConsumption(currentABW, pondInfoObj.get(i).getQuantity(),
                                                        Helper.get_TilapiaFeedingRate_by_WeekNum(currentWeekOfStock), 0.7)));
                                                currentDemandList.add(tempOBJ);
//                                                txtheader.setText(txtheader.getText() + "\nCurrent Feed Consumption: " + str_currentConsumption);
                                              
                                            }

                                            Adapter_Growouts_AllFarmDemands custinfoAdapter = new Adapter_Growouts_AllFarmDemands(Activity_WeeklyReports_Growout_FeedDemands.this, R.layout.item_lv_weeklypondsummary, currentDemandList);
                                            lvFeedConsumption.setAdapter(custinfoAdapter);

                                        }//end of for



                                    }
                                }


                            }
                            else{
                                Helper.toastLong(activity, "There are no demands for this week.");
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

}

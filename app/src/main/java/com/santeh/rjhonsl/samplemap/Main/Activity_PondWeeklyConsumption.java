package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.Adapter_Growouts_PondWeekLyConsumption;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.PondWeeklyUpdateParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 8/25/2015.
 */
public class Activity_PondWeeklyConsumption extends Activity {


    Adapter_Growouts_PondWeekLyConsumption adapterPondWeeklyReport;

    String farmName;
    int id;

    Activity activity;
    Context context;

    ProgressDialog PD;

    TextView txtheadr, txtdateStocked, txtQuantity;

    List<CustInfoObject> pondInfoList;
    List<CustInfoObject> pondweeklyList;

    ImageButton btn_details;

    ListView lvPonds;
    String [] pondListArray;
    private int pondid;
    private int abw;
    private String survivalrate;
    private int area;
    private int quantity;
    private String specie;
    private String datestocked;
    private String culturesystem;
    private String remarks;
    int startWeek, currentweek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growout_weeklyreport);
        activity = this;
        context = Activity_PondWeeklyConsumption.this;

        PD = new ProgressDialog(this);
        PD.setMessage("Updating database. Please wait....");
        PD.setCancelable(false);

        initViewsFromXML();

        if (getIntent() != null){
            if (getIntent().hasExtra("pondid")){ pondid = getIntent().getIntExtra("pondid",0); }
            if (getIntent().hasExtra("id")){id = getIntent().getIntExtra("id",0);}
            if (getIntent().hasExtra("abw")){ abw = getIntent().getIntExtra("abw",0);}
            if (getIntent().hasExtra("survivalrate")){survivalrate = getIntent().getStringExtra("survivalrate");}
            if (getIntent().hasExtra("area")){ area = getIntent().getIntExtra("area",0);}
            if (getIntent().hasExtra("quantity")){ quantity = getIntent().getIntExtra("quantity", 0);}
            if (getIntent().hasExtra("specie")){ specie = getIntent().getStringExtra("specie");}
            if (getIntent().hasExtra("datestocked")){ datestocked = getIntent().getStringExtra("datestocked");}
            if (getIntent().hasExtra("culturesystem")){culturesystem = getIntent().getStringExtra("culturesystem");}
            if (getIntent().hasExtra("remarks")){ remarks = getIntent().getStringExtra("remarks");}

            getpondData(id, Helper.variables.URL_SELECT_POND_WEEKLY_UPDATES_BY_ID);
        }


        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            farmName = extras.getString("farmname");
        }

        txtheadr.setText("Pond " + pondid + " - " + specie);
        txtQuantity.setText("Quantity: " + quantity);
        txtdateStocked.setText("Date Stocked: " + datestocked);

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = Helper.createCustomThemedColorDialogOKOnly(activity, "Pond Details",
                        "Pond No.: " + pondid + "\n\n" +
                                "Specie: " + specie + "\n\n" +
                                "ABW when stocked: " + abw + "g\n\n" +
                                "Suvival Rate: " + survivalrate + "%\n\n" +
                                "Date Stocked: " + datestocked + "\n\n" +
                                "Case Area: " + area + "m²\n\n" +
                                "CultureSystem: " + culturesystem + "\n"
//                                + "currentweek: " + currentweek + "startweek: " + startWeek
                        ,
                        "OK",
                        R.color.skyblue_500);
                d.show();

                Button ok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
            }
        });


        lvPonds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Object o = adapterPondWeeklyReport.getItem(position);
//////                prestationEco str=(prestationEco)o;//As you are using Default String Adapter
//////                Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(activity, Activity_Pond_WeekDetails.class);
//                intent.putExtra("pondindex", id);
//                intent.putExtra("")
//                startActivity(intent);
//

            }
        });

    }


    private void scrollMyListViewToBottom(final ListView myListView, final Adapter_Growouts_PondWeekLyConsumption myListAdapter, final int position ) {
        myListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                myListView.setSelection(position - 1);
            }
        });
    }


    private void initViewsFromXML() {

        txtheadr = (TextView) findViewById(R.id.lbl_weeklyreports_farmName);
        txtdateStocked = (TextView) findViewById(R.id.txt_weeklyreports_DateStocked);
        txtQuantity = (TextView) findViewById(R.id.txt_weeklyreports_quantity);
        lvPonds = (ListView) findViewById(R.id.lv_pond_weeklyReports);
        btn_details = (ImageButton) findViewById(R.id.btn_details);

    }



    public void getpondData( final int pondid, String url) {

        PD.setMessage("Retrieving Data. Please wait... ");
        PD.show();



        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        PD.dismiss();
                        if (!response.substring(1,2).equalsIgnoreCase("0")) {
                            pondInfoList = PondWeeklyUpdateParser.parseFeed(response);

                            if (pondInfoList!=null) {
                                if (pondInfoList.size() > 0){
                                    Log.d("null", "before for");
                                    pondweeklyList = new ArrayList<>();
                                    for (int i = 0; i < pondInfoList.size() + 1; i++) {
                                        String strrecommended = "", strRemarks = "", strFeedtype="";
                                        int strweeknum, strabw;
                                        CustInfoObject weekinfo = new CustInfoObject();

                                        if (i == pondInfoList.size() ){

                                            strRemarks = remarks;
                                            strabw = abw;
                                            strweeknum = Helper.get_Tilapia_WeekNum_byABW(abw);
                                            strrecommended = Helper.computeWeeklyFeedConsumption(Double.parseDouble(abw + ""), quantity,
                                                    Helper.get_TilapiaFeedingRate_by_WeekNum(Helper.get_Tilapia_WeekNum_byABW(abw)),
                                                    Double.parseDouble(survivalrate));
                                            strFeedtype = Helper.getFeedTypeByNumberOfWeeks(Helper.get_Tilapia_WeekNum_byABW(abw));
                                            Log.d("null", strRemarks + " x " + strabw + " x " + strweeknum + " x " + strrecommended + " x " + strFeedtype + " ");
                                        }else {
                                            Log.d("null", "if 1");
                                            strRemarks = pondInfoList.get(i).getRemarks();
                                            strabw = pondInfoList.get(i).getSizeofStock();
                                            strweeknum = Helper.get_Tilapia_WeekNum_byABW(strabw);
                                            strrecommended = Helper.computeWeeklyFeedConsumption(Double.parseDouble(strabw + ""), quantity,
                                                    Helper.get_TilapiaFeedingRate_by_WeekNum(Helper.get_Tilapia_WeekNum_byABW(strabw)),
                                                    Double.parseDouble(survivalrate));
                                            strFeedtype = Helper.getFeedTypeByNumberOfWeeks(Helper.get_Tilapia_WeekNum_byABW(strabw));

                                        }

                                        weekinfo.setRemarks(strRemarks);
                                        weekinfo.setSizeofStock(strabw);
                                        weekinfo.setWeek(strweeknum);
                                        weekinfo.setRecommendedConsumption(strrecommended);
                                        weekinfo.setCurrentFeedType(strFeedtype);
                                        Log.d("null", "before add weekinfo");
                                        pondweeklyList.add(weekinfo);
                                    }//end of loop

                                    adapterPondWeeklyReport = new Adapter_Growouts_PondWeekLyConsumption(Activity_PondWeeklyConsumption.this,
                                            R.layout.item_lv_weeklypondsummary, pondweeklyList);
                                    lvPonds.setAdapter(adapterPondWeeklyReport);

//                                    scrollMyListViewToBottom(lvPonds, adapterPondWeeklyReport, currentweek);
                                }


                            }

                        }else { Helper.toastShort(activity, "No recods"); }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Dialog d = Helper.createCustomThemedColorDialogOKOnly(activity, "Error", "Something unexpected happened: " + error.toString(), "OK", R.color.red);
                d.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", Helper.variables.getGlobalVar_currentUsername(activity));
                params.put("password", Helper.variables.getGlobalVar_currentUserpassword(activity));
                params.put("deviceid", Helper.getMacAddress(context));
                params.put("pondindex", pondid+"");
                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);


//
//        CustInfoObject pondobj = new CustInfoObject();
//        pondobj.setPondID(pondid);
//        pondobj.setId(id);
//        pondobj.setSizeofStock(abw);
//        pondobj.setSurvivalrate_per_pond(survivalrate);
//        pondobj.setArea(area);
//        pondobj.setQuantity(quantity);
//        pondobj.setSpecie(specie);
//        pondobj.setDateStocked(datestocked);
//        pondobj.setCulturesystem(culturesystem);
//        pondobj.setRemarks(remarks);
//        pondInfoList.add(pondobj);
//
//        assert pondInfoList != null;
//        if (pondInfoList != null){
//            pondListArray = new String[pondInfoList.size()];
//            for (int i = 0; i < pondInfoList.size(); i++) {
//
//                pondListArray[i] = pondInfoList.get(i).getPondID() + " - " + pondInfoList.get(i).getSpecie();
//            }
//
//            populate_rows(0);
//        }


    }

//    private void populate_rows(int position, List<CustInfoObject> obj2 ) {
//        startWeek = Helper.get_Tilapia_WeekNum_byABW(pondInfoList.get(position).getSizeofStock());
//        int quantity = pondInfoList.get(position).getQuantity();
//        currentweek =  Helper.get_currentWeek_by_stockedDate(pondInfoList.get(position).getDateStocked(), pondInfoList.get(position).getSizeofStock());
//
//        pondweeklyList = obj2;
//        for (int i = 0; i < 18; i++) {
//
//            CustInfoObject obj = new CustInfoObject();
////            Log.d("REPORT", "" + i);
//
//            obj.setStartweekofStock(startWeek);
//            obj.setCurrentFeedType(Helper.getFeedTypeByNumberOfWeeks(i + 1));
//            obj.setCurrentweekofStock(i + 1);
//            obj.setSurvivalrate_per_pond(survivalrate);
//            obj.setWeek(currentweek);
//            obj.setIsVisited(0);
//            obj.setRemarks("N/A");
//
//            double abw = Helper.get_ABW_BY_WEEK_NO(i + 1),
//                    quantity1 = quantity ,
//                    feedingrate = Helper.get_TilapiaFeedingRate_by_WeekNum(i+1);
//            double recommended = (abw * quantity1 * Double.parseDouble(survivalrate) * feedingrate * 7) / 1000;
////            + " abw: "+ abw+ " quatity: " + quantity1 + " survival: " + survivalrate + " feedingrate: " +feedingrate
//            DecimalFormat df = new DecimalFormat("#.##");
//            obj.setRecommendedConsumption("" + df.format(recommended));
//            obj.setActualConsumption("N/A");
//            pondweeklyList.add(obj);
//        }
//
//        adapterPondWeeklyReport = new Adapter_Growouts_PondWeekLyConsumption(Activity_PondWeeklyConsumption.this,
//                R.layout.item_lv_weeklypondsummary, pondweeklyList);
//        lvPonds.setAdapter(adapterPondWeeklyReport);
//
//        scrollMyListViewToBottom(lvPonds, adapterPondWeeklyReport, currentweek);
//    }



    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }




}

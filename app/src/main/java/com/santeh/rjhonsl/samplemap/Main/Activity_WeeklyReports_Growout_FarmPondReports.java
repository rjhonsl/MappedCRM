package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.Adapter.Adapter_Growouts_PondWeekLyConsumption;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 8/25/2015.
 */
public class Activity_WeeklyReports_Growout_FarmPondReports extends Activity {


    Adapter_Growouts_PondWeekLyConsumption adapterPondWeeklyReport;

    String farmName;
    int id;

    Activity activity;

    ProgressDialog PD;

    TextView txtheadr, txtdateStocked, txtQuantity;

    List<CustInfoObject> pondInfoList;
    List<CustInfoObject> pondconsumptionList;

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

            getpondData(id);
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
                        "Case No. : " + pondid + "\n\n" +
                                "Specie: " + specie + "\n\n" +
                                "ABW when stocked: " + abw + "g\n\n" +
                                "Suvival Rate: " + survivalrate + "%\n\n" +
                                "Date Stocked: " + datestocked + "\n\n" +
                                "Case Area: " + area + "m²\n\n" +
                                "CultureSystem: " + culturesystem + "\n" +
                                "currentweek: " + currentweek + "startweek: " + startWeek
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

    public void setStartWeekHighLight(int weekstarted, int weekCurrent) {



    }

    private void initViewsFromXML() {

        txtheadr = (TextView) findViewById(R.id.lbl_weeklyreports_farmName);
        txtdateStocked = (TextView) findViewById(R.id.txt_weeklyreports_DateStocked);
        txtQuantity = (TextView) findViewById(R.id.txt_weeklyreports_quantity);
        lvPonds = (ListView) findViewById(R.id.lv_pond_weeklyReports);
        btn_details = (ImageButton) findViewById(R.id.btn_details);

    }



    public void getpondData( final int pondid) {

        pondInfoList = new ArrayList<>();

        CustInfoObject pondobj = new CustInfoObject();
        pondobj.setPondID(pondid);
        pondobj.setId(id);
        pondobj.setSizeofStock(abw);
        pondobj.setSurvivalrate_per_pond(survivalrate);
        pondobj.setArea(area);
        pondobj.setQuantity(quantity);
        pondobj.setSpecie(specie);
        pondobj.setDateStocked(datestocked);
        pondobj.setCulturesystem(culturesystem);
        pondobj.setRemarks(remarks);
        pondInfoList.add(pondobj);

        assert pondInfoList != null;
        if (pondInfoList != null){
            pondListArray = new String[pondInfoList.size()];
            for (int i = 0; i < pondInfoList.size(); i++) {

                pondListArray[i] = pondInfoList.get(i).getPondID() + " - " + pondInfoList.get(i).getSpecie();
            }

            populate_rows(0);
        }


    }

    private void populate_rows(int position) {
        startWeek = Helper.get_StartWeekOf_Tilapia_ByABW(pondInfoList.get(position).getSizeofStock());
        int quantity = pondInfoList.get(position).getQuantity();
        currentweek =  Helper.get_currentWeek_by_stockedDate(pondInfoList.get(position).getDateStocked(), pondInfoList.get(position).getSizeofStock());

        pondconsumptionList = new ArrayList<>();
        for (int i = 0; i < 18; i++) {

            CustInfoObject obj = new CustInfoObject();
            Log.d("REPORT", "" + i);

            obj.setStartweekofStock(startWeek);
            obj.setCurrentfeedType(Helper.getFeedTypeByNumberOfWeeks(i + 1));
            obj.setCurrentweekofStock(i + 1);
            obj.setSurvivalrate_per_pond(survivalrate);
            obj.setWeek(currentweek);
            obj.setIsVisited(0);
            obj.setRemarks("N/A");

            double abw = Helper.get_ABW_BY_WEEK_NO(i + 1),
                    quantity1 = quantity ,
                    feedingrate = Helper.get_FeedingRate_by_WeekNum(i+1);
            double recommended = (abw * quantity1 * Double.parseDouble(survivalrate) * feedingrate * 7) / 1000;
//            + " abw: "+ abw+ " quatity: " + quantity1 + " survival: " + survivalrate + " feedingrate: " +feedingrate
            DecimalFormat df = new DecimalFormat("#.##");
            obj.setRecommendedConsumption("" + df.format(recommended));
            obj.setActualConsumption("N/A");
            pondconsumptionList.add(obj);
        }






        adapterPondWeeklyReport = new Adapter_Growouts_PondWeekLyConsumption(Activity_WeeklyReports_Growout_FarmPondReports.this,
                R.layout.item_lv_weeklypondsummary, pondconsumptionList);
        lvPonds.setAdapter(adapterPondWeeklyReport);

        scrollMyListViewToBottom(lvPonds, adapterPondWeeklyReport, currentweek);


    }



    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }




}

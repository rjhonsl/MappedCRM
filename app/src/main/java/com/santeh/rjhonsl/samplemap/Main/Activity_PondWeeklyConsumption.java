package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

    ImageButton btn_details, btn_addreport;

    String strrecommended = "", strRemarks = "", strFeedtype="";
    int strweeknum, strabw;

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


                final Dialog d = new Dialog(activity);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
                d.setContentView(R.layout.dialog_material_themed_ponddetails);//Set the xml view of the dialog
                Button btnOK1 = (Button) d.findViewById(R.id.btnOK);
                TextView txtpondNum = (TextView) d.findViewById(R.id.txtPondNum);
                TextView txtSpecie = (TextView) d.findViewById(R.id.txtSpecies);
                TextView txtqty = (TextView) d.findViewById(R.id.txtQuantity);
                TextView txtabw = (TextView) d.findViewById(R.id.txtABW);
                TextView txtSurvivalRate = (TextView) d.findViewById(R.id.txtSurvivalRate);
                TextView txtDateStocked = (TextView) d.findViewById(R.id.txtDateStocked);
                TextView txtArea = (TextView) d.findViewById(R.id.txtArea);
                TextView txtCultureSystem = (TextView) d.findViewById(R.id.txtCultureSystem);

                txtpondNum.setText(pondid + "");
                txtSpecie.setText(specie + "");
                txtqty.setText(quantity + "");
                txtabw.setText(abw + "");
                txtSurvivalRate.setText(survivalrate + "");
                txtDateStocked.setText(datestocked + "");
                txtArea.setText(area + "");
                txtCultureSystem.setText(culturesystem + "");

                btnOK1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
                btnOK1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
                btnOK1.setText("OK");
                d.show();
            }
        });

        btn_addreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(activity);//
                d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
                d.setContentView(R.layout.dialog_material_themed_addpondreport);//Set the xml view of the dialog
                Button add = (Button) d.findViewById(R.id.btnAdd);
                Button cancel = (Button) d.findViewById(R.id.btnCancel);
                final EditText edtAbw = (EditText) d.findViewById(R.id.edtAbw);
                final EditText edtRemarks = (EditText) d.findViewById(R.id.edtRemarks);
                if (strabw ==  0){
                    edtAbw.setText(""+abw);
                }else{
                    edtAbw.setText(""+strabw);
                }

                d.show();
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!edtAbw.getText().toString().equalsIgnoreCase("") || !edtRemarks.getText().toString().equalsIgnoreCase("")) {
                            d.hide();
                            AddReport(edtAbw.getText().toString(), Helper.variables.URL_INSERT_POND_REPORT, edtRemarks.getText().toString());
                        } else {
                            Helper.toastLong(activity, "You have to complete all fields to continue");
                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
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


        lvPonds.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if (position==0) {
                    Helper.createCustomThemedColorDialogOKOnly(activity, "Warning", "You cannot Edit or Delete Initial Stocking Data", "OK",R.color.red);
                }else {
                    String[] options = {"Edit ABW and Remarks", "Delete"};
                    final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Options ", R.color.deepteal_400);
                    d.show();

                    ListView lvOptions = (ListView) d.findViewById(R.id.dialog_list_listview);
                    lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                            if (position1 == 0 ){

                                d.hide();

                                final Dialog d2 = new Dialog(activity);//
                                d2.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
                                d2.setContentView(R.layout.dialog_material_themed_addpondreport);//Set the xml view of the dialog
                                Button add = (Button) d2.findViewById(R.id.btnAdd);
                                TextView txttitle = (TextView) d2.findViewById(R.id.txtTitle);
                                txttitle.setText("Edit Week Details ");
                                add.setText("UPDATE");

                                Button cancel = (Button) d2.findViewById(R.id.btnCancel);
                                final EditText edtAbw = (EditText) d2.findViewById(R.id.edtAbw);
                                final EditText edtRemarks = (EditText) d2.findViewById(R.id.edtRemarks);

                                edtAbw.setText(""+ pondweeklyList.get(position).getSizeofStock());
                                edtRemarks.setText(""+ pondweeklyList.get(position).getRemarks());

                                d2.show();
                                add.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d2.hide();
                                        modifyWeeklyDetail(pondweeklyList.get(position).getId() + "", edtRemarks.getText().toString(), edtAbw.getText().toString(),
                                                Helper.variables.URL_UPDATE_POND_WEEKLY_DETAIL_BY_ID);
                                    }
                                });

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                        d2.hide();
                                    }
                                });

                            }else if (position1 == 1){
                                d.hide();
                                final Dialog del = Helper.createCustomDialogThemedYesNO(activity, "Are you sure you want to delete selected week?", "Delete", "NO", "DELETE", R.color.red);
                                del.show();

                                Button cancel = (Button) del.findViewById(R.id.btn_dialog_yesno_opt1);
                                Button delete = (Button) del.findViewById(R.id.btn_dialog_yesno_opt2);
                                delete.setTextColor(getResources().getColor(R.color.red));

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        del.hide();
                                    }
                                });

                                delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        del.hide();
                                        modifyWeeklyDetail(pondweeklyList.get(position).getId() + "","none", "none",
                                                Helper.variables.URL_DELETE_POND_WEEKLY_DETAILS_BY_ID);
                                    }
                                });

                            }
                        }
                    });
                }
                return false;
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
        btn_addreport = (ImageButton) findViewById(R.id.btn_addreport);
    }



    public void getpondData( final int pondid, String url) {

        PD.setMessage("Retrieving Data. Please wait... ");
        PD.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pondweeklyList = new ArrayList<>();
                        PD.dismiss();


//                        CustInfoObject weekinfo = new CustInfoObject();
//                        weekinfo.setId(0);
//                        weekinfo.setRemarks(remarks);
//                        weekinfo.setSizeofStock(abw);
//                        weekinfo.setWeek(Helper.get_Tilapia_WeekNum_byABW(abw));
//                        weekinfo.setRecommendedConsumption(Helper.computeWeeklyFeedConsumption(Double.parseDouble(abw + ""), quantity,
//                                Helper.get_TilapiaFeedingRate_by_WeekNum(Helper.get_Tilapia_WeekNum_byABW(abw)),
//                                (Double.parseDouble(survivalrate) / 100)));
//                        weekinfo.setCurrentFeedType(Helper.getFeedTypeByNumberOfWeeks(Helper.get_Tilapia_WeekNum_byABW(abw)));
//                        pondweeklyList.add(weekinfo);

                        if (!response.substring(1,2).equalsIgnoreCase("0")) {
                            pondInfoList = PondWeeklyUpdateParser.parseFeed(response);
                            if (pondInfoList!=null) {
                                if (pondInfoList.size() > 0){
                                    Log.d("null", "before for");

                                    for (int i = 0; i < pondInfoList.size(); i++) {

                                        CustInfoObject weekinfo1 = new CustInfoObject();

                                        strRemarks = pondInfoList.get(i).getRemarks();
                                        strabw = pondInfoList.get(i).getSizeofStock();
                                        strweeknum = Helper.get_Tilapia_WeekNum_byABW(strabw);
                                        strrecommended = Helper.computeWeeklyFeedConsumption(Double.parseDouble(strabw + ""), quantity,
                                                Helper.get_TilapiaFeedingRate_by_WeekNum(Helper.get_Tilapia_WeekNum_byABW(strabw)),
                                                (Double.parseDouble(survivalrate) / 100));
                                        strFeedtype = Helper.getFeedTypeByNumberOfWeeks(Helper.get_Tilapia_WeekNum_byABW(strabw));

                                        weekinfo1.setId(pondInfoList.get(i).getId());
                                        weekinfo1.setRemarks(strRemarks);
                                        weekinfo1.setSizeofStock(strabw);
                                        weekinfo1.setWeek(strweeknum);
                                        weekinfo1.setRecommendedConsumption(strrecommended);
                                        weekinfo1.setCurrentFeedType(strFeedtype);

//                                        Log.d("null", strRemarks + " x " + strabw + " x " + strweeknum + " x " + strrecommended + " x " + strFeedtype);

                                        pondweeklyList.add(weekinfo1);
                                        Log.d("null", "end of loop");
                                    }//end of loop
                                }
                            }


                        }else {
//                            Helper.toastShort(activity, "No records");
                        }


                        adapterPondWeeklyReport = new Adapter_Growouts_PondWeekLyConsumption(Activity_PondWeeklyConsumption.this,
                                R.layout.item_lv_weeklypondsummary, pondweeklyList);
                        lvPonds.setAdapter(adapterPondWeeklyReport);

                        scrollMyListViewToBottom(lvPonds, adapterPondWeeklyReport, pondweeklyList.size());
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
                params.put("pondindex", id+"");
                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);

    }




    private void AddReport(final String abw2, String url, final String remarks2){
        PD.setMessage("Saving Report. Please wait... ");
        PD.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pondweeklyList = new ArrayList<>();
                        PD.dismiss();

//                        Helper.createCustomThemedColorDialogOKOnly(activity, "Responze", response, "OK", R.color.red);
                        if (!response.substring(1,2).equalsIgnoreCase("0")) {
                            Helper.toastShort(activity, "Report Added Successfully!");
                            getpondData(id, Helper.variables.URL_SELECT_POND_WEEKLY_UPDATES_BY_ID);
                        }else { Helper.toastShort(activity, "No records"); }
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
                params.put("abw", abw2);
                params.put("remarks", remarks2);
                params.put("pondindex", id+"");
                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }



    private void modifyWeeklyDetail(final String idToBeDeleted, final String remarks1, final String abw1, String url2){
        PD.setMessage("Updating database. Please wait... ");
        PD.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pondweeklyList = new ArrayList<>();
                        PD.dismiss();
                        if (!response.substring(1,2).equalsIgnoreCase("0")) {
                            Helper.toastShort(activity, "Success");
                            getpondData(id, Helper.variables.URL_SELECT_POND_WEEKLY_UPDATES_BY_ID);
                        }else { Helper.toastShort(activity, "Something went wrong. Please try again later"); }
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
                Map<String, String> params = new HashMap<>();

                params.put("username", Helper.variables.getGlobalVar_currentUsername(activity));
                params.put("password", Helper.variables.getGlobalVar_currentUserpassword(activity));
                params.put("deviceid", Helper.getMacAddress(context));
                params.put("pondindex", idToBeDeleted+"");
                params.put("abw",       abw1+"");
                params.put("remarks",   remarks1+"");
                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
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

package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.AdapterPonds;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.PondInfoJsonParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 8/13/2015.
 */
public class Activity_ManagePonds extends Activity {

    Activity activity;
    Context context;
    ProgressDialog PD;
    List<CustInfoObject> pondInfoList;
    AdapterPonds pondadapter;

    String farmname = "";
    ImageButton btnaddpond;

    ListView lvPonds;
    LinearLayout llnoPond;

    int id=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managepond);
        activity = Activity_ManagePonds.this;
        context  = Activity_ManagePonds.this;


        PD = new ProgressDialog(this);
        PD.setMessage("Updating database. Please wait....");
        PD.setCancelable(false);


        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            id = extras.getInt("id");
            getdataByID(id);
        }else{
            id=42;
            getdataByID(42);
        }
        if (getIntent() != null) {
            if (getIntent().hasExtra("farmname")) {
                farmname = getIntent().getStringExtra("farmname");
            }
        }


        lvPonds = (ListView) findViewById(R.id.listofponds);
        llnoPond = (LinearLayout) findViewById(R.id.ll_nopond);
        btnaddpond = (ImageButton) findViewById(R.id.btn_addpond);
        TextView txtfarmname = (TextView) findViewById(R.id.farmname);
        txtfarmname.setText(farmname + "");


        btnaddpond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ManagePonds.this, Activity_AddPond.class);
                intent.putExtra("custid", id);
                intent.putExtra("farmname", farmname);
                startActivity(intent);
            }
        });

        lvPonds.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position1, long id) {
                String[] options = {"View and Edit Details", "View Weekly Reports", "Delete"};
                final Dialog d = Helper.createCustomThemedListDialog(activity, options, "Systems", R.color.deepteal_500);
                d.show();

                ListView lv = (ListView) d.findViewById(R.id.dialog_list_listview);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        d.hide();
                        if (position == 1){

                        }else if (position == 0) {
                            Intent intent = new Intent(activity, Activity_EditPonds.class);
                            intent.putExtra("pondid", pondInfoList.get(position1).getPondID());
                            intent.putExtra("id", pondInfoList.get(position1).getId());
                            intent.putExtra("specie", pondInfoList.get(position1).getSpecie());
                            intent.putExtra("abw", pondInfoList.get(position1).getSizeofStock());
                            intent.putExtra("survivalrate", pondInfoList.get(position1).getSurvivalrate_per_pond());
                            intent.putExtra("datestocked", pondInfoList.get(position1).getDateStocked());
                            intent.putExtra("quantity", pondInfoList.get(position1).getQuantity());
                            intent.putExtra("area", pondInfoList.get(position1).getArea());
                            intent.putExtra("culturesystem", pondInfoList.get(position1).getCulturesystem());
                            intent.putExtra("remarks", pondInfoList.get(position1).getRemarks());
                            startActivity(intent);

                        }else if (position == 2) {
                            final Dialog dd = Helper.createCustomDialogThemedYesNO(activity, "Changes cannot be undone once implemented. \n\nAre you sure you want to delete this pond?"
                                    , "Delete", "NO", "YES", R.color.red);
                            dd.show();
                            Button yes = (Button) dd.findViewById(R.id.btn_dialog_yesno_opt2);
                            Button no = (Button) dd.findViewById(R.id.btn_dialog_yesno_opt1);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dd.hide();
                                    deletePondByID(pondInfoList.get(position1).getId());
                                }
                            });

                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dd.hide();
                                }
                            });

                        }
                    }
                });
                return false;
            }
        });
    }


    public void getdataByID(final int custID) {

        if(!Helper.isNetworkAvailable(activity)) {
            Helper.toastShort(activity, "Internet Connection is not available. Please try again later.");
        }
        else{
            PD.setMessage("Getting information from server...");
            PD.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_SELECT_PONDINFO_BY_CUSTOMER_ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PD.dismiss();

                            if(!response.substring(1,2).equalsIgnoreCase("0")){
                                pondInfoList = PondInfoJsonParser.parseFeed(response);
                                if (pondInfoList != null){
                                    pondadapter = new AdapterPonds(context, R.layout.item_lv_manageponds, pondInfoList);
                                    lvPonds.setAdapter(pondadapter);
                                    lvPonds.setVisibility(View.VISIBLE);
                                    llnoPond.setVisibility(View.GONE);
                                }
                                else{
                                    lvPonds.setVisibility(View.GONE);
                                    llnoPond.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    final Dialog d = Helper.createCustomDialogOKOnly(Activity_ManagePonds.this, "OOPS",
                            "Something went wrong. Please try again later.", "OK");
                    TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                    d.setCancelable(false);
                    d.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.hide();
                        }
                    });
                    PD.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("customerId", String.valueOf(custID));
                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_ManagePonds.this);

        }

    }


    public void deletePondByID(final int pondIndexID) {

        if(!Helper.isNetworkAvailable(activity)) {
            Helper.toastShort(activity, "Internet Connection is not available. Please try again later.");
        }
        else{
            PD.setMessage("Getting information from server...");
            PD.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_DELETE_POND_BY_ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PD.dismiss();

                            if(!response.substring(1,2).equalsIgnoreCase("0")){
                                getdataByID(id);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    final Dialog d = Helper.createCustomDialogOKOnly(Activity_ManagePonds.this, "OOPS",
                            "Something went wrong. Please try again later.", "OK");
                    TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                    d.setCancelable(false);
                    d.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.hide();
                        }
                    });
                    PD.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(pondIndexID));
                    params.put("password", Helper.variables.getGlobalVar_currentUserpassword(activity));
                    params.put("username", Helper.variables.getGlobalVar_currentUsername(activity));
                    params.put("deviceid", Helper.getMacAddress(activity));
                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_ManagePonds.this);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getdataByID(id);
    }
}

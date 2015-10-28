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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.AdapterUserMonitoring_ViewByUser;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.AccountsParser;
import com.santeh.rjhonsl.samplemap.Parsers.AreaParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 9/19/2015.
 */
public class Activity_UserMonitoring_ViewByUser extends Activity {

    Activity activity;
    Context context;
    ListView lvUsers;
    EditText edt_searchbox;

    AdapterUserMonitoring_ViewByUser custInfoAdapter;

    List<CustInfoObject> userlist;
    List<CustInfoObject> areaList;
    ImageButton btnsearch, btnfilterByArea;
    String[] strAvailableArea;
    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermonitoring_viewbyuser);
        activity = this;
        context = Activity_UserMonitoring_ViewByUser.this;


        PD = new ProgressDialog(this);
        PD. setCancelable(false);
        Helper.hideKeyboardOnLoad(activity);

        lvUsers = (ListView) findViewById(R.id.listview_userMonitoring);
        btnsearch = (ImageButton) findViewById(R.id.btn_viewUserActivity_search);
        edt_searchbox = (EditText) findViewById(R.id.edt_viewUserActivity_search);
        btnfilterByArea = (ImageButton) findViewById(R.id.btn_filter);

        getAllUsers(edt_searchbox.getText().toString(), Helper.variables.URL_SELECT_ALL_USERS);



        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_searchbox.getText().toString().equalsIgnoreCase("")) {
                    getAllUsers(edt_searchbox.getText().toString(),Helper.variables.URL_SELECT_ALL_USERS);
                } else {
                    Helper.toastShort(activity, "You must enter a name or keyword.");
                }
            }
        });

        btnfilterByArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllAreaAvailable();
            }
        });

        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Dialog d = Helper.createCustomDialogThemedYesNO(activity, "See " + userlist.get(position).getFirstname() + " " +
                        userlist.get(position).getLastname() + "'s activity in map?", "View in Map", "Cancel", "GO!", R.color.blue );
                d.show();

                Button cancel = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                Button ok = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
                cancel.setTextColor(getResources().getColor(R.color.red));

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                        Intent intent = new Intent(Activity_UserMonitoring_ViewByUser.this, MapsActivity_UserMonitoring.class);
                        intent.putExtra("userid", userlist.get(position).getUserid());
                        intent.putExtra("firstname", userlist.get(position).getFirstname());
                        intent.putExtra("lastname", userlist.get(position).getLastname());
                        startActivity(intent);
                    }
                });


            }
        });

    }

    private void getAllUsers(final String keyword, String url) {
        PD.setMessage("Please wait...");
        PD.show();

        if(!Helper.isNetworkAvailable(activity)) {
            Helper.toastShort(activity, "Internet Connection is not available. Please try again later.");
            PD.dismiss();
        }
        else{
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

//                            Helper.toastLong(activity,response);


                            if (response.substring(1,2).equalsIgnoreCase("0")){
                                Helper.toastShort(activity, "Something happened. Please try again later.");
                                PD.dismiss();

                            }else{

                                PD.dismiss();
                                userlist = new ArrayList<>();
                                userlist = AccountsParser.parseFeed(response);

                                if (userlist != null) {
                                    if (custInfoAdapter != null) {
                                        custInfoAdapter.clear();
                                    }

                                    custInfoAdapter = new AdapterUserMonitoring_ViewByUser(context, R.layout.item_lv_useractivity, userlist);
                                    lvUsers.setAdapter(custInfoAdapter);
                                }else{
//                                    Helper.toastLong(activity, "novalue on userlist " + response);
//                                    Dialog d = Helper.createCustomDialogOKOnly(activity, "", response, "");
//                                    d.show();

                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
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
                    params.put("keyword", keyword);
                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);
        }
    }




    private void getAllAreaAvailable() {
        PD.setMessage("Please wait...");
        PD.show();

        if(!Helper.isNetworkAvailable(activity)) {
            Helper.toastShort(activity, "Internet Connection is not available. Please try again later.");
            PD.dismiss();
        }
        else{
            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_SELECT_ALL_AREA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            areaList = new ArrayList<>();
                            areaList = AreaParser.parseFeed(response);
//                            Helper.toastShort(activity, response);
                            PD.dismiss();
                            if (areaList != null){
                                if (areaList.size() > 0){
                                    strAvailableArea = new String[areaList.size()];

//                                    Helper.toastShort(activity, "not zero");

                                    for (int i = 0; i < areaList.size(); i++) {
                                        strAvailableArea[i] = areaList.get(i).getProvince();
                                    }

                                    final Dialog d = Helper.createCustomThemedListDialog(activity, strAvailableArea , "Select an Area", R.color.red);
                                    d.show();
                                    ListView lvprovince = (ListView) d.findViewById(R.id.dialog_list_listview);
                                    lvprovince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            getAllUsers(areaList.get(position).getProvince_id()+"", Helper.variables.URL_SELECT_ALL_USERS_IN_AREA);
                                            d.hide();
                                        }
                                    });
                                }else{
//                                    Helper.toastShort(activity, "not null but zero");
                                }
                            }else{
//                                Helper.toastShort(activity, "null");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
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
                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);

        }
    }



}

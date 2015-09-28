package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Adapter.AdapterViewCustInfo;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.CustomerInfoJsonParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 8/1/2015.
 */
public class Activity_ViewCustomerInfo extends Activity {

    TextView title;
    ProgressDialog PD;
    String keyword = "";
    EditText txtsearch;
    Activity thisActivity;
    private List<CustInfoObject> searchedList;
    private List<CustInfoObject> beforesearchedList;
    ListView lvSearch;
    AdapterViewCustInfo custinfoAdapter;
    Activity activity;
    Context context;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcustomerinfo);
        thisActivity =Activity_ViewCustomerInfo.this;
        activity = this;
        context = Activity_ViewCustomerInfo.this;

        title = (TextView) findViewById(R.id.txt_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvSearch = (ListView) findViewById(R.id.listview_viewCustomerInfo);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading stuff....");
        PD.setCancelable(false);

        url = "http://mysanteh.site50.net/santehweb/selectAllCustomerInfo.php";
        search();

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lvSearch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                String [] options = {"See in Map","View and Edit Delails", "Delete"};
                final Dialog d = Helper.createCustomListDialog(Activity_ViewCustomerInfo.this, options,"OPTIONS" );

                ListView list = (ListView) d.findViewById(R.id.dialog_list_listview);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                        if(index==0){//See in map

                            Intent intent = new Intent(Activity_ViewCustomerInfo.this, MapsActivity.class);
                            intent.putExtra("lat", searchedList.get(position).getLatitude());
                            intent.putExtra("long", searchedList.get(position).getLongtitude());
                            intent.putExtra("contactName", searchedList.get(position).getContact_name());
                            intent.putExtra("address", searchedList.get(position).getAddress());
                            intent.putExtra("id", searchedList.get(position).getId());

                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("fromActivity", "viewCustinfo");
                            startActivity(intent);

                        }
                        else if (index == 1){ //View and Edit details
                            Intent intent = new Intent(Activity_ViewCustomerInfo.this, Activity_CustomerInfo_Edit.class);
                            intent.putExtra("lat", searchedList.get(position).getLatitude());
                            intent.putExtra("userid", searchedList.get(position).getId());
                            intent.putExtra("long", searchedList.get(position).getLongtitude());
                            intent.putExtra("contactName", searchedList.get(position).getContact_name());
                            intent.putExtra("address", searchedList.get(position).getAddress());
                            intent.putExtra("farmname", searchedList.get(position).getFarmname());
                            intent.putExtra("farmID", searchedList.get(position).getFarmID());
                            intent.putExtra("contactnumber", searchedList.get(position).getContact_number());
                            intent.putExtra("culturesystem", searchedList.get(position).getCultureType());
                            intent.putExtra("levelofculture", searchedList.get(position).getCulturelevel());
                            intent.putExtra("watertype", searchedList.get(position).getWaterType());

                            intent.putExtra("fromActivity", "viewCustinfo");
                            startActivity(intent);
                        }else if(index ==2) {
                            final Dialog yn = Helper.createCustomDialogYesNO(Activity_ViewCustomerInfo.this, R.layout.dialog_material_yesno,
                                    "Changes you are going to do is permanent and irreversible. \n\nAre you sure you want to delete *"
                                            + searchedList.get(position).getContact_name() + "* from list?",
                                    "DELETE", "YES", "NO");
                            Button yes = (Button) yn.findViewById(R.id.btn_dialog_yesno_opt1);
                            Button no = (Button) yn.findViewById(R.id.btn_dialog_yesno_opt2);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteByID("" + searchedList.get(position).getId());
                                    d.hide();
                                    yn.hide();
                                }
                            });


                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    yn.hide();
                                }
                            });
                            yn.show();
                        }
                        d.hide();
                    }
                });
                d.show();

                return false;
            }
        });

        txtsearch = (EditText) findViewById(R.id.txt_viewcustomerInfo_search);
        txtsearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (txtsearch.getRight() - txtsearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (txtsearch.getText().toString().trim().equalsIgnoreCase("") ||txtsearch.getText().toString().trim().equalsIgnoreCase(null)){
                            url = "http://mysanteh.site50.net/santehweb/selectAllCustomerInfo.php";
                            search();
                        }else{
                            url = "http://mysanteh.site50.net/santehweb/searchCustomerInfoByKey.php";
                            keyword = txtsearch.getText().toString();
                            search();
                        }

                        return true;
                    }
                }
                return false;
            }
        });


    }





    public void search() {

        if(!Helper.isNetworkAvailable(thisActivity)) {
            Helper.toastShort(thisActivity, "Internet Connection is not available. Please try again later.");
        }
        else{
        PD.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        PD.dismiss();
                        beforesearchedList = searchedList;
                        searchedList = CustomerInfoJsonParser.parseFeed(response);
                        if (searchedList != null) {
                                custinfoAdapter = new AdapterViewCustInfo(Activity_ViewCustomerInfo.this, R.layout.item_lv_viewcustomerinfo, searchedList);
                                lvSearch.setAdapter(custinfoAdapter);
                        }else {
                            searchedList = beforesearchedList;
                            final Dialog d = Helper.createCustomDialogOKOnly(Activity_ViewCustomerInfo.this,
                                    "OOPS!", "Your keyword does not seem to match any of our records.", "OK");
                            TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.hide();
                                }
                            });
                            d.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(Activity_ViewCustomerInfo.this,
                        "Failed to search " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);
                return params;
            }
        };

        // Adding request to request queue
        MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_ViewCustomerInfo.this);

        }

    }


    public void deleteByID(final String id) {


        if (!Helper.isNetworkAvailable(thisActivity)) {
            Helper.toastShort(thisActivity, "Internet Connection is not available. Please try again later.");
        } else {
            PD.setMessage("Deleting....");
            PD.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_DELETE_CUSTINFO_BY_ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Helper.toastShort(thisActivity,
                                    "Data has been deleted successfully");
                            PD.setMessage("Refreshing...");
                            Logging.loguserAction(activity, context, Helper.userActions.TSR.DELETE_FARM+": index "+id, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);
                            search();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "Something Unexpected happened. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", id);
                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_ViewCustomerInfo.this);
        }
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

package com.santeh.rjhonsl.samplemap.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.PondInfoJsonParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;
import com.santeh.rjhonsl.samplemap.Utils.Logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 8/13/2015.
 */
public class Activity_ManagePonds extends Activity {

    EditText txtpondId, txtSpecie, txtDateofStocking, txtQuantity, txtculturetype, txtarea, txtRemarks, txtsizeOfStock, txtsurvivalRate;
    TextView txttotalPonds, txtTotalQty;
    Button btnSavePond, btnCancel, btnAddPond, btnEditPond, btnDeletePond;

    Activity activity;


    ProgressDialog PD;

    List<CustInfoObject> pondInfoList;

    String[] pondIdArray ;
    String requestType ="";

    buttonHandler btnhandler = new buttonHandler();                                      //0 for old, 1 for new
    int id=1, totalspecie = 0, totalstock = 0, totalponds = 0, highestPondID = 0, saveStat = 0, indexID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpond);
        activity = Activity_ManagePonds.this;

        PD = new ProgressDialog(this);
        PD.setMessage("Updating database. Please wait....");
        PD.setCancelable(false);

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            id = extras.getInt("id");
//            Helper.toastShort(activity, "ID: " + id);
            getdataByID(id);
        }else{
//            Helper.toastShort(activity, "ID has not been received.");
            id=42;
            getdataByID(42);
        }


        Helper.hidekeyboardOnLoad(Activity_ManagePonds.this);

        txtpondId           = (EditText) findViewById(R.id.txt_pondetails_pondID);
        txtSpecie           = (EditText) findViewById(R.id.txt_pondDetails_specie);
        txtDateofStocking   = (EditText) findViewById(R.id.txt_pondDetails_dateStock);
        txtQuantity         = (EditText) findViewById(R.id.txt_pondDetails_quantity);
        txtculturetype      = (EditText) findViewById(R.id.txt_pondDetails_culturetype);
        txtarea             = (EditText) findViewById(R.id.txt_pondDetails_area);
        txtRemarks          = (EditText) findViewById(R.id.txt_pondDetails_remarks);
        txtsizeOfStock      = (EditText) findViewById(R.id.txt_pondDetails_SizeofStock);
        txtsurvivalRate     = (EditText) findViewById(R.id.txt_pondDetails_survivalrate);

        txttotalPonds       = (TextView) findViewById(R.id.txt_pondDetails_totalPonds);
        txtTotalQty         = (TextView) findViewById(R.id.txt_pondDetails_totalQuantity);

        btnAddPond          = (Button) findViewById(R.id.btn_add_Pond);
        btnEditPond         = (Button) findViewById(R.id.btn_edit_pond);
        btnDeletePond       = (Button) findViewById(R.id.btn_delete_pond);

        btnSavePond     = (Button) findViewById(R.id.btn_pond_OK);
        btnCancel       = (Button) findViewById(R.id.btn_pond_cancel);

        initListeners();


    }

    private void initListeners() {


        btnDeletePond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestType = "delete";
                final Dialog d =Helper.createCustomDialoYesNO(activity, R.layout.dialog_material_yesno, "Are you sure you want to delete this record?", "DELETE"
                        , "YES", "NO");

                Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                Button no  = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
                d.show();

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                        updateCustomerInfoDB(indexID, Helper.variables.URL_DELETE_POND_BY_ID);
                    }
                });

            }
        });

        btnEditPond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStat=1;
                btnhandler.editPond();
                requestType = "update";
                btnSavePond.setText("Update");
                Helper.toastShort(activity, "You can now edit pond details.");
            }
        });

        txtpondId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pondInfoList != null) {
                    pondIdArray = new String[pondInfoList.size()];
                    for (int i = 0; i < pondInfoList.size(); i++) {
                        pondIdArray[i] = String.valueOf(pondInfoList.get(i).getPondID());
                    }

                    final Dialog d = Helper.createCustomListDialog(Activity_ManagePonds.this, pondIdArray, "POND ID");
                    ListView lvSpecie = (ListView) d.findViewById(R.id.dialog_list_listview);
                    lvSpecie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            txtpondId.setText(pondIdArray[position]);
                            indexID = (pondInfoList.get(position).getId());

                            txtpondId.setText("" + pondInfoList.get(position).getPondID());
                            txtDateofStocking.setText("" + pondInfoList.get(position).getDateStocked());
                            txtSpecie.setText("" + pondInfoList.get(position).getSpecie());
                            txtQuantity.setText("" + pondInfoList.get(position).getQuantity());
                            txtculturetype.setText("" + pondInfoList.get(position).getCulturesystem());
                            txtarea.setText("" + pondInfoList.get(position).getArea());
                            txtRemarks.setText("" + pondInfoList.get(position).getRemarks());
                            txtsurvivalRate.setText(""+pondInfoList.get(position).getSurvivalrate_per_pond());
                            d.hide();
                        }
                    });

                }


            }
        });


        btnSavePond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != 0) {
                    String prompt = null, title = null, leftBtn = null, url = null;
                    if (requestType.equalsIgnoreCase("new")) {
                        prompt = "Save all information to database?";
                        title = "SAVE INFO";
                        leftBtn = "Save";
                        url = Helper.variables.URL_INSERT_PONDINFO;

                    } else {
                        prompt = "Update pond information?";
                        title = "UPDATE INFO";
                        leftBtn = "Update";
                        url = Helper.variables.URL_UPDATE_PONDINFO_BY_ID;
                    }

                    final Dialog d = Helper.createCustomDialoYesNO(Activity_ManagePonds.this, R.layout.dialog_material_yesno, prompt, title, leftBtn, "Cancel");
                    d.show();
                    Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                    Button no  = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
                    final String finalUrl = url;

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateCustomerInfoDB(id, finalUrl);
                            d.hide();
                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.hide();
                        }
                    });

                }
            }
        });

        btnAddPond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (saveStat == 0) {
                    saveStat = 1;
                    btnhandler.newPond();
                    requestType = "new";
                    btnSavePond.setText("  Save");
                    int high = highestPondID + 1;
                    txtpondId.setText("" + high);
                    clearEditexts();
                }//if(pondInfoList!=null)
                else {
                    saveStat = 0;
                    btnhandler.onresume();
                    fillEditTextWithData();
                }


                String pondID = txtpondId.getText().toString();
                if (pondID.equalsIgnoreCase(null) || pondID.equalsIgnoreCase("")) {
                    txtpondId.setText("1");

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView_pondinfo);
                            scrollView.scrollTo(0, txtpondId.getScrollY());
                        }
                    });
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = Helper.createCustomDialoYesNO(activity, R.layout.dialog_material_yesno, "Are you sure you want to go back?", "BACK", "YES", "NO");
                Button yes = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
                Button no = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
                d.show();

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.hide();
                    }
                });
            }
        });

        txtSpecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String specie[] = Helper.variables.ARRAY_SPECIES;
                final Dialog d = Helper.createCustomListDialog(Activity_ManagePonds.this, specie, "SPECIES");
                ListView lvSpecie = (ListView) d.findViewById(R.id.dialog_list_listview);
                lvSpecie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        txtSpecie.setText(specie[position]);
                        d.hide();
                    }
                });
            }
        });

        txtculturetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String system[] = Helper.variables.ARRAY_CULTURE_SYSTEM;
                final Dialog d = Helper.createCustomListDialog(Activity_ManagePonds.this, system, "CULTURE SYSTEMS");
                ListView lvsystems = (ListView) d.findViewById(R.id.dialog_list_listview);
                lvsystems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        txtculturetype.setText(system[position]);
                        d.hide();
                    }
                });
            }
        });


        txtDateofStocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] date;
                int month, day, year;

                final Dialog d = Helper.createCustomDatePicker(Activity_ManagePonds.this);
                final DatePicker datePicker = (DatePicker) d.findViewById(R.id.dialog_datepicker);
                Button btnok = (Button) d.findViewById(R.id.dialog_datepicker_btnok);


                if (txtDateofStocking.getText().toString().equalsIgnoreCase("")) {
                    date = Helper.convertLongtoDateFormat(System.currentTimeMillis());
                    datePicker.updateDate(date[2], date[0] - 1, date[1]);
                    d.show();
                } else {
                    String[] datee = txtDateofStocking.getText().toString().split("/");
                    datePicker.updateDate(Integer.parseInt(datee[2]), Integer.parseInt(datee[0]) - 1, Integer.parseInt(datee[1]));
                    d.show();
                }


                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();
                        txtDateofStocking.setText((month) + "/" + day + "/" + year);
                        d.hide();

                    }
                });

            }
        });
    }

    private void fillEditTextWithData() {
        if(pondInfoList != null){
            txtpondId.setText(""+pondInfoList.get(0).getPondID());
            txtsurvivalRate.setText(""+pondInfoList.get(0).getSurvivalrate_per_pond());
            txtsizeOfStock.setText(""+pondInfoList.get(0).getSizeofStock());
            txtDateofStocking.setText(""+pondInfoList.get(0).getDateStocked());
            txtSpecie.setText(""+pondInfoList.get(0).getSpecie());
            txtQuantity.setText(""+pondInfoList.get(0).getQuantity());
            txtculturetype.setText(""+pondInfoList.get(0).getCulturesystem());
            txtarea.setText(""+pondInfoList.get(0).getArea());
            txtRemarks.setText(""+pondInfoList.get(0).getRemarks());
        }
        else{
            clearEditexts();
        }

    }

    private void clearEditexts() {
        txtDateofStocking.setText("");
        txtsurvivalRate.setText("");
        txtsizeOfStock.setText("");
        txtSpecie.setText("");
        txtQuantity.setText("");
        txtculturetype.setText("");
        txtarea.setText("");
        txtRemarks.setText("");
        Helper.toastShort(activity, "You can now add a new pond!");
    }


    public void updateCustomerInfoDB(final int customerID, final String url) {

        if(     txtSpecie.getText().toString().equalsIgnoreCase("") ||
                txtDateofStocking.getText().toString().equalsIgnoreCase("") ||
                txtsizeOfStock.getText().toString().equalsIgnoreCase("") ||
                txtQuantity.getText().toString().equalsIgnoreCase("") ||
                txtculturetype.getText().toString().equalsIgnoreCase("") ||
                txtarea.getText().toString().equalsIgnoreCase("") ||
                txtRemarks.getText().toString().equalsIgnoreCase("")||
                txtpondId.getText().toString().equalsIgnoreCase("")||
                txtsurvivalRate.getText().toString().equalsIgnoreCase("")
                )
        {
            final Dialog d = Helper.createCustomDialogOKOnly(Activity_ManagePonds.this, "OOPS",
                    "There seems to be field(s) that you have left behind... Please check then try again.", "OK");
            TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
            d.show();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
        }
        else{
            PD.setMessage("Updating database... ");
            PD.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            String responseCode = Helper.extractResponseCode(response);
                            String title, prompt;


                            if (responseCode.equalsIgnoreCase("0")){
                                oopsprompt();
                            }else if (responseCode.equalsIgnoreCase("1")) {

                                Logging.loguserAction(activity, activity.getBaseContext(), Helper.userActions.TSR.Edit_POND, Helper.variables.ACTIVITY_LOG_TYPE_TSR_MONITORING);
                                title = "SUCCESS";
                                prompt = "You have successfully updated database.";
                                PD.dismiss();
                                btnhandler.onresume();

                                final Dialog d = Helper.createCustomDialogOKOnly(Activity_ManagePonds.this, title,
                                        prompt, "OK");
                                TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                                d.show();
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.hide();
                                        getdataByID(id);
                                    }
                                });
                            }
                            else {
                                oopsprompt();
                            }

                        }

                        private void oopsprompt() {
                            String title;
                            String prompt;
                            title="OOPS";
                            prompt = "Something went wrong. Please try again later.";
                            PD.dismiss();

                            final Dialog d = Helper.createCustomDialogOKOnly(Activity_ManagePonds.this, title,
                                    prompt, "OK");
                            TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                            d.setCancelable(false);
                            d.show();
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.hide();
                                }
                            });
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();

                    final Dialog d = Helper.createCustomDialogOKOnly(Activity_ManagePonds.this, "OOPS",
                            "Something went wrong. error("+ error +")", "OK");
                    TextView ok = (TextView) d.findViewById(R.id.btn_dialog_okonly_OK);
                    d.setCancelable(false);
                    d.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                    finish();
                            d.hide();
                        }
                    });
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();


                    params.put("specie", String.valueOf(txtSpecie.getText()));
                    params.put("pondid", String.valueOf(txtpondId.getText()));
                    params.put("dateStocked", String.valueOf(txtDateofStocking.getText()));
                    params.put("quantity", String.valueOf(txtQuantity.getText()));
                    params.put("area", String.valueOf(txtarea.getText()));
                    params.put("culturesystem", String.valueOf(txtculturetype.getText()));
                    params.put("remarks", String.valueOf(txtRemarks.getText()));
                    params.put("id", String.valueOf(indexID));
                    params.put("customerId", String.valueOf(customerID));
                    params.put("sizeofStock", String.valueOf(txtsizeOfStock.getText()));
                    params.put("survivalrate", String.valueOf(txtsurvivalRate.getText()));

                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_ManagePonds.this);
        }

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

                            pondInfoList = PondInfoJsonParser.parseFeed(response);
//                            Helper.toastLong(activity, response);
                            if(pondInfoList !=null ){
                                totalponds = 0; totalstock=0;
                                for (int i = 0; i < pondInfoList.size() ; i++) {

                                    if (pondInfoList.get(i).getPondID() > highestPondID)
                                    {
                                        highestPondID = pondInfoList.get(i).getPondID();
                                    }


                                    totalponds = pondInfoList.size();
                                    totalstock = totalstock + pondInfoList.get(i).getQuantity();
                                    Log.d("POND ID",""+pondInfoList.get(i).getPondID());

                                }

                                btnhandler.onresume();
                                indexID = pondInfoList.get(0).getId();
                                txtpondId.setText(""+pondInfoList.get(0).getPondID());
                            txtTotalQty.setText("" + totalstock);
                            txttotalPonds.setText("" + totalponds);
                                fillEditTextWithData();

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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("customerId", String.valueOf(custID));
                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_ManagePonds.this);

        }

    }


    class buttonHandler{

        void disableEditexts() {
            LinearLayout ll = (LinearLayout) findViewById(R.id.llayout_ponddetails_stats);
            LinearLayout llpid = (LinearLayout) findViewById(R.id.ll_ponddetails_pondid);
            llpid.setEnabled(true);
            txtpondId.setEnabled(true);
            ll.setEnabled(false);

            txtSpecie.setEnabled(false);
            txtsurvivalRate.setEnabled(false);
            txtsizeOfStock.setEnabled(false);
            txtculturetype.setEnabled(false);
            txtDateofStocking.setEnabled(false);
            txtQuantity.setEnabled(false);
            txtarea.setEnabled(false);
            txtRemarks.setEnabled(false);
        }

        void enableEditexts(){
            LinearLayout ll = (LinearLayout) findViewById(R.id.llayout_ponddetails_stats);
            LinearLayout llpid = (LinearLayout) findViewById(R.id.ll_ponddetails_pondid);
            llpid.setEnabled(false);
            txtpondId.setEnabled(false);
            ll.setEnabled(true);

            txtSpecie.setEnabled(true);
            txtsurvivalRate.setEnabled(true);
            txtsizeOfStock.setEnabled(true);
            txtculturetype.setEnabled(true);
            txtDateofStocking.setEnabled(true);
            txtQuantity.setEnabled(true);
            txtarea.setEnabled(true);
            txtRemarks.setEnabled(true);
        }

        void onresume(){
            btnSavePond.setEnabled(false);

            btnAddPond.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_circle_outline_darkteal_24dp, 0, 0, 0);
            btnAddPond.setText("  New");

            if (pondInfoList != null){
                btnEditPond.setEnabled(true);
                btnDeletePond.setEnabled(true);
            }else{
                btnEditPond.setEnabled(false);
                btnDeletePond.setEnabled(false);
            }



            disableEditexts();
        }

        void newPond(){
            btnSavePond.setEnabled(true);

            btnAddPond.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove_circle_outline_darkteal_24dp, 0, 0, 0);
            btnAddPond.setText("  Cancel");

            btnEditPond.setEnabled(false);
            btnDeletePond.setEnabled(false);

            enableEditexts();
        }

        void editPond(){
            btnSavePond.setEnabled(true);

            btnAddPond.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove_circle_outline_darkteal_24dp, 0, 0, 0);
            btnAddPond.setText("  Cancel");

            btnEditPond.setEnabled(false);
            btnDeletePond.setEnabled(false);

            enableEditexts();
        }

    }




    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        btnhandler.onresume();
        super.onResume();
    }
}

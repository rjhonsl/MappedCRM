package com.santeh.rjhonsl.samplemap.Main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Parsers.AccountsParser;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.FusedLocation;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 9/4/2015.
 */
public class Activity_LoginScreen extends Activity{

    EditText txtusername, txtpassword;
    TextView txtappname1, txtappname2, txtshowpassword, txtforgot, txtrequestaccount, txttester, txtprogressdialog_message, lblusername, lblpassword;

    CheckBox chkshowpasword;
    ImageButton btnLogin;

    LatLng curlatlng;
    String longitude = " ", latitude=" ";

    Activity activity; Context context;
    Dialog PD;

    List<CustInfoObject> listaccounts = new ArrayList<>();
    FusedLocation fusedLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        activity = this;
        context = Activity_LoginScreen.this;

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.buildGoogleApiClient(context);


        PD =  Helper.initProgressDialog(activity);
        txtprogressdialog_message = (TextView) PD.findViewById(R.id.progress_message);

        initViews();

        Typeface font_roboto = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");


        Helper.hidekeyboardOnLoad(activity);

        btnLogin.requestFocus();
        txtappname1.setTypeface(font_roboto);
        txtappname2.setTypeface(font_roboto);
        txtpassword.setTypeface(font_roboto);
        txtusername.setTypeface(font_roboto);

        txttester.setText(
                Helper.getIMEI(context)
                        + " " +
                        Helper.getMacAddress(context)
        );

        txtshowpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (chkshowpasword.isChecked()) {
                    chkshowpasword.setChecked(false);
                } else {
                    chkshowpasword.setChecked(true);
                }

                toggle_showpassword();
            }
        });



        if (txtpassword.isFocused() || !txtpassword.getText().toString().equalsIgnoreCase("")){
            txtpassword.setVisibility(View.VISIBLE);
        }

        txtusername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    if (!txtusername.getText().toString().equalsIgnoreCase("") || hasFocus){
                        txtusername.setVisibility(View.VISIBLE);

                        lblusername.setAlpha(0);
                        lblusername.setVisibility(View.VISIBLE);
                        lblusername.animate()
                                .translationY(0)
                                .alpha(255)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        txtusername.setHint("");
                                    }
                                });

                    }
                    else{
                        lblusername.animate()
                                .alpha(0)
                                .translationY(lblpassword.getHeight())
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        lblusername.setVisibility(View.GONE);
                                        txtusername.setHint("Username...");
                                    }
                                });
                    }
            }
        });

        txtpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!txtpassword.getText().toString().equalsIgnoreCase("") || hasFocus){

                    lblpassword.setAlpha(0);
                    lblpassword.setVisibility(View.VISIBLE);
                    lblpassword.animate()
                            .translationY(0)
                            .alpha(255)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    txtpassword.setHint("");
                                }
                            });

                }
                else{
                    lblpassword.animate()
                            .alpha(0)
                            .translationY(lblpassword.getHeight())
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    lblpassword.setVisibility(View.GONE);
                                    txtpassword.setHint("Password...");
                                }
                            });
                }
            }
        });


        txtrequestaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ((txtpassword.getText().toString().equalsIgnoreCase("") || txtpassword.getText().toString().trim().equalsIgnoreCase(""))) &&
                        (txtusername.getText().toString().equalsIgnoreCase("") || txtusername.getText().toString().trim().equalsIgnoreCase(""))){
                    Helper.toastShort(activity, "Username and Password is needed to continue");
                }else if(txtpassword.getText().toString().equalsIgnoreCase("") || txtpassword.getText().toString().trim().equalsIgnoreCase("")){
                    Helper.toastShort(activity, "Password is needed to continue");
                }else if(txtusername.getText().toString().equalsIgnoreCase("") || txtusername.getText().toString().trim().equalsIgnoreCase(""))
                {
                    Helper.toastShort(activity,"Username is needed to continue");
                }else
                {
                    login();
                }

            }
        });


        chkshowpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle_showpassword();
            }
        });

    }

    private void initViews() {

        txtusername = (EditText) findViewById(R.id.txt_loginscreen_username);
        txtpassword = (EditText) findViewById(R.id.txt_loginscreen_password);
        txtappname1 = (TextView) findViewById(R.id.txt_loginscreen_apptitle1);
        txtappname2 = (TextView) findViewById(R.id.txt_loginscreen_apptitle2);
        txtshowpassword = (TextView) findViewById(R.id.txt_loginscreen_showpassword);
        txtforgot = (TextView) findViewById(R.id.txtforgot_password);
        txtrequestaccount = (TextView) findViewById(R.id.txt_requestaccount);
        lblpassword = (TextView) findViewById(R.id.lbl_login_password);
        lblusername = (TextView) findViewById(R.id.lbl_login_username);
        txttester = (TextView) findViewById(R.id.txttester);

        btnLogin = (ImageButton) findViewById(R.id.btn_login);

        chkshowpasword = (CheckBox) findViewById(R.id.chk_loginscreen_showpassword);

        txtusername.getBackground().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
        txtpassword.getBackground().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);

        txtpassword.setText("10");
        txtusername.setText("jhonar10");
    }

    private void toggle_showpassword() {
        if (chkshowpasword.isChecked()){
            txtpassword.setTransformationMethod(null);
        }else{
            txtpassword.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    public void login() {

        fusedLocation.connectToApiClient();
        Helper.isLocationAvailable(context, activity);

        if(!Helper.isNetworkAvailable(activity)) {
            Helper.toastShort(activity, "Internet Connection is not available. Please try again later.");
        }
        else{
            txtprogressdialog_message.setText("Logging in...");
            PD.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {

                            String accountDetail="";
                                        PD.dismiss();
                                        if (response.substring(1, 2).equalsIgnoreCase("0")) {
                                            Helper.toastShort(activity, "Username and password does not seem to match");
                                        } else {
                                          listaccounts = AccountsParser.parseFeed(response);
                                            if (listaccounts.size() > 0){
                                                accountDetail = ""+
                                                listaccounts.get(0).getUserid()+" "+
                                                listaccounts.get(0).getUserlevel()+" " +
                                                listaccounts.get(0).getUsername() + " " +
                                                listaccounts.get(0).getFirstname() + " " +
                                                listaccounts.get(0).getAccountlevelDescription() + " " +
                                                listaccounts.get(0).getLastname();
                                            }

//                                            Helper.toastShort(activity, "Success: "+accountDetail);
                                            Intent intent = new Intent(Activity_LoginScreen.this, MapsActivity.class);
                                            Helper.variables.setGlobalVar_currentlevel(listaccounts.get(0).getUserlevel(), activity);
                                            Helper.variables.setGlobalVar_currentUserID(listaccounts.get(0).getUserid(), activity);
                                            Helper.variables.setGlobalVar_currentFirstname(listaccounts.get(0).getFirstname(), activity);
                                            Helper.variables.setGlobalVar_currentLastname(listaccounts.get(0).getLastname(), activity);
                                            Helper.variables.setGlobalVar_currentUsername(txtusername.getText().toString(),activity);
                                            Helper.variables.setGlobalVar_currentUserpassword(txtpassword.getText().toString(),activity);
                                            Helper.variables.setGlobalVar_currentAssignedArea(listaccounts.get(0).getAssingedArea(), activity);

                                            intent.putExtra("userid", listaccounts.get(0).getUserid());
                                            intent.putExtra("userlevel", listaccounts.get(0).getUserlevel());
                                            intent.putExtra("username", listaccounts.get(0).getUsername());
                                            intent.putExtra("firstname", listaccounts.get(0).getFirstname());
                                            intent.putExtra("lastname", listaccounts.get(0).getLastname());
                                            intent.putExtra("userdescription", listaccounts.get(0).getAccountlevelDescription());
                                            intent.putExtra("fromActivity", "login");
                                            intent.putExtra("lat",fusedLocation.getLastKnowLocation().latitude+"");
                                            intent.putExtra("long",fusedLocation.getLastKnowLocation().longitude+"");


                                            startActivity(intent);
                                        }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("deviceid", Helper.getMacAddress(activity));
                    params.put("username", txtusername.getText().toString());
                    params.put("password", txtpassword.getText().toString());

                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_LoginScreen.this);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
       Helper.isLocationAvailable(context, activity);
        fusedLocation.connectToApiClient();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        exitApp();
    }

    private void exitApp() {
        final Dialog d = Helper.createCustomDialoYesNO(activity, R.layout.dialog_material_yesno, "Do you wish to wish to exit the app?", "EXIT", "YES", "NO");
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
}//end of class

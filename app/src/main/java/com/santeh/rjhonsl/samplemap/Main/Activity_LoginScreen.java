package com.santeh.rjhonsl.samplemap.Main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    ProgressDialog mProgressDialog;

    String versionName, fileDir = "/sdcard/Download/", fulladdress, latestVersionName, versionFile;
    int versionCode;
    List<CustInfoObject> listaccounts = new ArrayList<>();
    FusedLocation fusedLocation;
    PackageInfo pInfo = null;

    float filesize;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        activity = this;
        context = Activity_LoginScreen.this;

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.buildGoogleApiClient(context);



        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (pInfo !=null){
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
        }


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
                Helper.getMacAddress(context)
                        + "\n" +
                        "update: " + versionCode + "    V." + versionName

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

        checkVersionUpdates();

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
                    Helper.createCustomThemedColorDialogOKOnly(activity, "Error", error.toString(), "OK", R.color.red);
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
        final Dialog d = Helper.createCustomDialogYesNO(activity, R.layout.dialog_material_yesno, "Do you wish to wish to exit the app?", "EXIT", "YES", "NO");
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

    public void checkVersionUpdates(){
        if(!Helper.isNetworkAvailable(activity)) {
            Helper.toastShort(activity, "Internet Connection is not available. Please try again later.");
        }
        else{
            txtprogressdialog_message.setText("Checking if app is up to date...");
            PD.show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_SELECT_CURRENT_VERSION_NUMBER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            PD.dismiss();

//                            Helper.createCustomThemedColorDialogOKOnly(activity, "RESONSE", response, "OK", R.color.red);
                            if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                                String[] splitted = response.split("!-!");
                                String versionNumber = splitted[0];
                                versionFile = splitted[1];

                                if (Integer.parseInt(versionNumber) > versionCode) {
                                    //download updates
                                    // declare the dialog as a member field of your activity


                                    // instantiate it within the onCreate method
                                    mProgressDialog = new ProgressDialog(context);
                                    mProgressDialog.setMessage("Getting the latest version...");
                                    mProgressDialog.setIndeterminate(true);
                                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                    mProgressDialog.setCancelable(false);

                                    // execute this when the downloader must be fired
                                    final DownloadTask downloadTask = new DownloadTask(context);
                                    downloadTask.execute(Helper.variables.sourceAddress_bizNF_downloadable+versionFile+".apk");

                                    mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            downloadTask.cancel(true);
                                        }
                                    });


                                }else{
                                    Helper.toastShort(activity, "Your application is up to date!");
                                }
                            } else {
                                Helper.createCustomThemedColorDialogOKOnly(activity, "Error", "Update Failed. Please try again later. \n"
                                        , "OK", R.color.red);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Helper.createCustomThemedColorDialogOKOnly(activity, "Error", error.toString(), "OK", R.color.red);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("deviceid", Helper.getMacAddress(activity)+"");
                    params.put("username", Helper.variables.getGlobalVar_currentUsername(activity)+"");
                    params.put("password", Helper.variables.getGlobalVar_currentUserpassword(activity)+"");
                    params.put("userid", Helper.variables.getGlobalVar_currentUserID(activity)+"");
                    params.put("userlvl", Helper.variables.getGlobalVar_currentlevel(activity)+"");

                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, Activity_LoginScreen.this);
        }
    }


    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                filesize = connection.getContentLength();
                fulladdress = fileDir+versionFile+".apk";
                output = new FileOutputStream(fulladdress);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setMessage("Getting updates... ("+filesize+")");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null){
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            }
            else {
                InstallAPK(fulladdress);
            }
        }

    }//end of AsyncTask

    public void InstallAPK(String filename){


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filename)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        File file = new File(filename);
//        if(file.exists()){
//            try {
//                String command;
//                command = "adb install -r " + filename;
//                Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", command });
//                proc.waitFor();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

}//end of class

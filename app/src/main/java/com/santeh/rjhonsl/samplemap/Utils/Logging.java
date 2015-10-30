package com.santeh.rjhonsl.samplemap.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.samplemap.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.samplemap.DBase.GpsDB_Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjhonsl on 9/10/2015.
 */
public class Logging {

    static boolean isRecorded = false;
    static FusedLocation fusedLocation;
    static GpsDB_Query db;

    public static boolean loguserAction(final Activity activity, final Context context, final String userAction, final String actionType ) {
        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.buildGoogleApiClient(context);
        fusedLocation.connectToApiClient();
        db = new GpsDB_Query(context);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Logging.InsertUserActivity(activity, context, Helper.variables.getGlobalVar_currentUserID(activity) + "",
                        userAction, actionType,
                        fusedLocation.getLastKnowLocation().latitude + "", fusedLocation.getLastKnowLocation().longitude + "");
            }
        }, 280);

       return true;
    }



    public static boolean InsertUserActivity(final Activity activity, final Context context,
    final String userid, final String action, final String actiontype, final String latitude, final String longitude){
        db.open();

        String dt =  Helper.convertLongtoDateTime_DB_Format(System.currentTimeMillis());
        db.insertUserActivityData(Integer.parseInt(userid), action, latitude, longitude, dt, actiontype);

        if (Helper.variables.getGlobalVar_currentLevel(activity) != 4){
            StringRequest postRequest = new StringRequest(Request.Method.POST,
                    Helper.variables.URL_INSERT_USER_ACTIVITY,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.substring(1,2).equalsIgnoreCase("0")) {
                                Log.d("USER LOGGING", "Activity logging for user " + userid + " SUCEEDED. " + response);
                                isRecorded = true;
                            }
                            else{
                                Log.d("USER LOGGING", "Activity logging for user "+userid+" FAILED. " + response);
                                InsertUserActivity(activity, context, userid, action, actiontype, latitude, longitude);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("USER LOGGING", "Activity logging for user "+userid+" FAILED. " + error);
                    InsertUserActivity(activity, context, userid, action, actiontype, latitude, longitude);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("userid", String.valueOf(userid));
                    params.put("action", String.valueOf(action));
                    params.put("actiontype", String.valueOf(actiontype));
                    params.put("latitude", fusedLocation.getLastKnowLocation().latitude+"");
                    params.put("longitude", fusedLocation.getLastKnowLocation().longitude+"");

                    return params;
                }
            };

            // Adding request to request queue
            MyVolleyAPI api = new MyVolleyAPI();
            api.addToReqQueue(postRequest, context);
        }
        return isRecorded;
    }
}

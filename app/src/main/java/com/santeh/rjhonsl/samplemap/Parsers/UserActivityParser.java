package com.santeh.rjhonsl.samplemap.Parsers;

import android.util.Log;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 9/19/2015.
 */
public class UserActivityParser {

    public static List<CustInfoObject> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<CustInfoObject> custInfoObjectList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {


//
//                user_act_id
//                user_act_userid
//                user_act_actiondone
//                user_act_latitude
//                user_act_longitude
//                user_act_datetime
//                user_act_actiontype


                JSONObject obj = ar.getJSONObject(i);
                CustInfoObject custInfoObject = new CustInfoObject();

                if (obj.has("user_act_id")) {
                    custInfoObject.setId(obj.getInt("user_act_id"));
                }

                if (obj.has("user_act_actiondone")) {
                    custInfoObject.setActionDone(obj.getString("user_act_actiondone"));
                }

                if (obj.has("user_act_latitude")) {
                    custInfoObject.setLatitude(obj.getString("user_act_latitude"));
                }

                if (obj.has("user_act_longitude")) {
                    custInfoObject.setLongtitude(obj.getString("user_act_longitude"));
                }

                if (obj.has("user_act_datetime")) {
                    custInfoObject.setDateTime(obj.getString("user_act_datetime"));
                }

                if (obj.has("user_act_actiontype")) {
                    custInfoObject.setActionType(obj.getString("user_act_actiontype"));
                }

                custInfoObject.setPondIndex(i+1);


                Log.d("JSON PARSE", "ID: " + custInfoObject.getId() + " " + custInfoObject.getLatitude() + " " + custInfoObject.getLongtitude());
                custInfoObjectList.add(custInfoObject);

            }

            return custInfoObjectList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}

package com.santeh.rjhonsl.samplemap.Parsers;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 9/9/2015.
 */
public class AccountsParser {


    public static List<CustInfoObject> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<CustInfoObject> custInfoObjectList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                CustInfoObject custInfoObject = new CustInfoObject();


                if (obj.has("users_id")){
                    custInfoObject.setUserid(obj.getInt("users_id"));
                }

                if (obj.has("assigned_area")){
                    custInfoObject.setAssingedArea(obj.getString("assigned_area"));
                }

                if (obj.has("users_userlvl")){
                    custInfoObject.setUserlevel(obj.getInt("users_userlvl"));
                }

                if (obj.has("users_firstname")){
                    custInfoObject.setFirstname(obj.getString("users_firstname"));
                }

                if (obj.has("users_lastname")){
                    custInfoObject.setLastname(obj.getString("users_lastname"));
                }

                if (obj.has("users_username")){
                    custInfoObject.setUsername(obj.getString("users_username"));
                }


                if (obj.has("dateAdded")){
                    custInfoObject.setDateAddedToDB(obj.getString("dateAdded"));
                }

                if (obj.has("isactive")){
                    if(!obj.isNull("isactive")){
                        custInfoObject.setIsactive(obj.getInt("isactive"));
                    }
                }

                if (obj.has("users_device_id")){
                    if(!obj.isNull("users_device_id")){
                        custInfoObject.setDeviceId(obj.getString("users_device_id"));
                    }
                }

                if (obj.has("user_lvl_description")){
                    custInfoObject.setAccountlevelDescription(obj.getString("user_lvl_description"));
                }

                if (obj.has("user_lvl_description")){
                    custInfoObject.setAccountlevelDescription(obj.getString("user_lvl_description"));

                }


                custInfoObjectList.add(custInfoObject);

            }

            return custInfoObjectList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}

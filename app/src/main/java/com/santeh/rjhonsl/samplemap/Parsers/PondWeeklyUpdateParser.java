package com.santeh.rjhonsl.samplemap.Parsers;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 9/24/2015.
 */
public class PondWeeklyUpdateParser {


    public static List<CustInfoObject> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<CustInfoObject> custInfoObjectList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                CustInfoObject custInfoObject = new CustInfoObject();



                // wu_id
                // wu_currentabw
                // wu_remakrs
                // wu_pondid
                // wu_dateAdded

                if (obj.has("wu_id")){
                    custInfoObject.setId(obj.getInt("wu_id"));
                }

                if (obj.has("wu_currentabw")){
                    custInfoObject.setSizeofStock(obj.getInt("wu_currentabw"));
                }

                if (obj.has("wu_remakrs")){
                    custInfoObject.setRemarks(obj.getString("wu_remakrs"));
                }

                if (obj.has("wu_pondid")){
                    custInfoObject.setPondID(obj.getInt("wu_pondid"));
                }

                if (obj.has("wu_dateAdded")){
                    custInfoObject.setDateStocked(obj.getString("wu_dateAdded"));
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

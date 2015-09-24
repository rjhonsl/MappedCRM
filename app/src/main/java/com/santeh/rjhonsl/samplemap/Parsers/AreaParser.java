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
public class AreaParser {


    public static List<CustInfoObject> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<CustInfoObject> custInfoObjectList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                CustInfoObject custInfoObject = new CustInfoObject();



//                          area_id
//                          area_description
//                          municipality_id
//                          municipality_description
//                          municipality_province

                if (obj.has("area_id")){
                    custInfoObject.setProvince_id(obj.getInt("area_id"));
                }

                if (obj.has("area_description")){
                    custInfoObject.setProvince(obj.getString("area_description"));
                }

                if (obj.has("municipality_id")){
                    custInfoObject.setMunicipality_id(obj.getInt("municipality_id"));
                }

                if (obj.has("municipality_description")){
                    custInfoObject.setMunicipality(obj.getString("municipality_description"));
                }

                if (obj.has("municipality_province")){
                    custInfoObject.setMunicipality_province(obj.getInt("municipality_province"));
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

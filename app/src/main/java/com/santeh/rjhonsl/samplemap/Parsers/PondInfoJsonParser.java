package com.santeh.rjhonsl.samplemap.Parsers;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 8/17/2015.
 */
public class PondInfoJsonParser {

    public static List<CustInfoObject> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<CustInfoObject> custInfoObjectList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                CustInfoObject custInfoObject = new CustInfoObject();


                if (obj.has("id")) {
                    custInfoObject.setId(obj.getInt("id"));
                }

                if (obj.has("sizeofStock")){
                    custInfoObject.setSizeofStock(obj.getInt("sizeofStock"));
                }

                if (obj.has("pondid")){
                    custInfoObject.setPondID(obj.getInt("pondid"));
                }

                if (obj.has("quantity")){
                    custInfoObject.setQuantity(obj.getInt("quantity"));
                }

                if (obj.has("area")){
                    custInfoObject.setArea(obj.getInt("area"));
                }

                if (obj.has("specie")){
                    custInfoObject.setSpecie(obj.getString("specie"));
                }

                if (obj.has("dateStocked")){
                    custInfoObject.setDateStocked(obj.getString("dateStocked"));
                }

                if (obj.has("culturesystem")){
                    custInfoObject.setCulturesystem(obj.getString("culturesystem"));
                }

                if (obj.has("remarks")){
                    custInfoObject.setRemarks(obj.getString("remarks"));
                }

                if (obj.has("customerId")){
                    custInfoObject.setCustomerID(obj.getString("customerId"));
                }

                if (obj.has("survivalrate")){
                    custInfoObject.setSurvivalrate_per_pond(obj.getString("survivalrate"));
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

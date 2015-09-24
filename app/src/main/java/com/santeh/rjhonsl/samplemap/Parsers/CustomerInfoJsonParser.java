package com.santeh.rjhonsl.samplemap.Parsers;


import android.util.Log;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerInfoJsonParser {
	
	public static List<CustInfoObject> parseFeed(String content) {
	
		try {
			JSONArray ar = new JSONArray(content);
			List<CustInfoObject> custInfoObjectList = new ArrayList<>();
			
			for (int i = 0; i < ar.length(); i++) {
				
				JSONObject obj = ar.getJSONObject(i);
				CustInfoObject custInfoObject = new CustInfoObject();

				Log.d("PARSE", "ciid");
				if(obj.has("ci_customerId")){
					custInfoObject.setId(obj.getInt("ci_customerId"));
				}else{
					custInfoObject.setId(0);
				}

				Log.d("PARSE", "lat");
				if(obj.has("latitude")){
					custInfoObject.setLatitude(obj.getString("latitude"));
				}else{
					custInfoObject.setLatitude("n/a");
				}

				Log.d("PARSE", "long");
				if(obj.has("longtitude")){
					custInfoObject.setLongtitude(obj.getString("longtitude"));
				}else{
					custInfoObject.setLongtitude("n/a");
				}

				Log.d("PARSE", "cname");
				if(obj.has("contact_name")){
					custInfoObject.setContact_name(obj.getString("contact_name"));
				}else{
					custInfoObject.setContact_name("n/a");
				}

				Log.d("PARSE", "comp");
				if(obj.has("company")){
					custInfoObject.setCompany(obj.getString("company"));
				}else{
					custInfoObject.setCompany("n/a");
				}


				Log.d("PARSE", "address");
				if(obj.has("address")){
					custInfoObject.setAddress(obj.getString("address"));
				}else{
					custInfoObject.setAddress("n/a");
				}

				Log.d("PARSE", "farmname");
				if(obj.has("farm_name")){
					custInfoObject.setFarmname(obj.getString("farm_name"));
				}else{
					custInfoObject.setFarmname("n/a");
				}

				Log.d("PARSE", "farmid");
				if(obj.has("farmid")){
					custInfoObject.setFarmID(obj.getString("farmid"));
				}else{
					custInfoObject.setFarmID("n/a");
				}

				Log.d("PARSE", "contactnum");
				if(obj.has("contact_number")){
					custInfoObject.setContact_number(obj.getString("contact_number"));
				}else{
					custInfoObject.setContact_number("n/a");
				}

				Log.d("PARSE", "ctype");
				if(obj.has("culture_type")){
					custInfoObject.setCultureType(obj.getString("culture_type"));
				}else{
					custInfoObject.setCultureType("n/a");
				}

				Log.d("PARSE", "clevel");
				if(obj.has("culture_level")){
					custInfoObject.setCulturelevel(obj.getString("culture_level"));
				}else{
					custInfoObject.setCulturelevel("n/a");
				}


				Log.d("PARSE", "watertype");
				if(obj.has("water_type")){
					custInfoObject.setWaterType(obj.getString("water_type"));
				}else{
					custInfoObject.setWaterType("n/a");
				}


				Log.d("PARSE", "dateadded");
				if(obj.has("dateAdded")){
					custInfoObject.setDateAddedToDB(obj.getString("dateAdded"));
				}else{
					custInfoObject.setDateAddedToDB("n/a");
				}

				Log.d("PARSE", "parse");
				Log.d("JSON PARSE", "ID: "+custInfoObject.getId());
				custInfoObjectList.add(custInfoObject);

			}
			
			return custInfoObjectList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}

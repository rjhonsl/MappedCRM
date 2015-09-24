package com.santeh.rjhonsl.samplemap.Parsers;


import android.util.Log;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustAndPondParser {
	
	public static List<CustInfoObject> parseFeed(String content) {

		int totalstockOfPond = 0;


		int previousPondStock=0;
		int prevCust = 0;
	
		try {
			Log.d("PARSE", "before passingcontent");
			JSONArray ar = new JSONArray(content);
			List<CustInfoObject> custInfoObjectList = new ArrayList<>();

			Log.d("PARSE", "before loop");
			for (int i = 0; i < ar.length(); i++) {
				Log.d("PARSE", "before ar obj");

				JSONObject obj = ar.getJSONObject(i);
				CustInfoObject custInfoObject = new CustInfoObject();


				Log.d("PARSING", "cid");
				try {
					if (obj.has("ci_customerId")) {
					custInfoObject.setCi_id(obj.getInt("ci_customerId"));}

				}catch (Exception e){
					custInfoObject.setCi_id(0);
				}


				Log.d("PARSING", "lat");
				try {
					if (obj.has("latitude"))
					custInfoObject.setLatitude(obj.getString("latitude"));
				}catch (Exception e){
					custInfoObject.setLatitude("0.0");
				}


				Log.d("PARSING", "long");
				try {
					if (obj.has("longtitude"))
					custInfoObject.setLongtitude(obj.getString("longtitude"));
				}catch (Exception e){
					custInfoObject.setLongtitude("0.0");
				}


				Log.d("PARSING", "c_name");
				try {
					if (obj.has("contact_name"))
					custInfoObject.setContact_name(obj.getString("contact_name"));
				}catch (Exception e){
					custInfoObject.setContact_name("None");
				}

				Log.d("PARSING", "company");
				try {
					if (obj.has("company"))
					custInfoObject.setCompany(obj.getString("company"));
				}catch (Exception e){
					custInfoObject.setCompany("None");
				}

				Log.d("PARSING", "c address");
				try {
					if (obj.has("address"))
					custInfoObject.setAddress(obj.getString("address"));
				}catch (Exception e){
					custInfoObject.setAddress("None");
				}


				Log.d("PARSING", "farmname");
				try {
					if (obj.has("farm_name"))
					custInfoObject.setFarmname(obj.getString("farm_name"));
				}catch (Exception e){
					custInfoObject.setFarmname("None");
				}


				Log.d("PARSING", "farm id");
				try {
					if (obj.has("farmid"))
					custInfoObject.setFarmID(obj.getString("farmid"));
				}catch (Exception e){
					custInfoObject.setFarmID("None");
				}


				Log.d("PARSING", "c number");
				try {
					if (obj.has("contact_number"))
					custInfoObject.setContact_number(obj.getString("contact_number"));
				}catch (Exception e){
					custInfoObject.setContact_number("None");
				}

				Log.d("PARSING", "culture type");
				try {
					if (obj.has("culture_type"))
					custInfoObject.setCultureType(obj.getString("culture_type"));
				}catch (Exception e){
					custInfoObject.setCultureType("None");
				}


				Log.d("PARSING", "culture level");
				try {
					if (obj.has("culture_level"))
					custInfoObject.setCulturelevel(obj.getString("culture_level"));
				}catch (Exception e){
					custInfoObject.setCulturelevel("None");
				}


				Log.d("PARSING", "watertype");
				try {
					if (obj.has("water_type"))
					custInfoObject.setWaterType(obj.getString("water_type"));
				}catch (Exception e){
					custInfoObject.setWaterType("None");
				}


				Log.d("PARSING", "dateadded");
				try {
					if (obj.has("dateAdded"))
					custInfoObject.setDateAddedToDB(obj.getString("dateAdded"));
				}catch (Exception e){
					custInfoObject.setDateAddedToDB("None");
				}


				/***
				 *ponds
				 * */

				Log.d("PARSING", "allspce");

				try {
					if (obj.has("allSpecie")) {
						custInfoObject.setAllSpecie(obj.getString("allSpecie"));
					}
				}catch (Exception e){
					custInfoObject.setAllSpecie(obj.getString("None"));
				}

				Log.d("PARSING", "p index id");
				try {
					if (obj.has("id"))
					custInfoObject.setId(obj.getInt("id"));
				}catch (Exception e){
					custInfoObject.setId(0);
				}


				Log.d("PARSING", "total qty");
				try {
					if (obj.has("Totalquantity"))
					custInfoObject.setTotalStockOfFarm(obj.getInt("Totalquantity"));
				}catch (Exception e){
					custInfoObject.setTotalStockOfFarm(0);
				}


				Log.d("PARSING", "size abw");
				try {
					if (obj.has("sizeofStock"))
					custInfoObject.setSizeofStock(obj.getInt("sizeofStock"));
				}catch (Exception e){
					custInfoObject.setSizeofStock(0);
				}


				Log.d("PARSING", "p id");
				try {
					if (obj.has("pondid"))
					custInfoObject.setPondID(obj.getInt("pondid"));
				}catch (Exception e){
					custInfoObject.setPondID(0);
				}


				Log.d("PARSING", "qty");
				try {
					if (obj.has("quantity"))
					custInfoObject.setQuantity(obj.getInt("quantity"));
				}catch (Exception e){
					custInfoObject.setQuantity(0);
				}

				Log.d("PARSING", "area");
				try {
					if (obj.has("area"))
					custInfoObject.setArea(obj.getInt("area"));
				}catch (Exception e){
					custInfoObject.setArea(0);
				}


				Log.d("PARSING", "specie");
				try {
					if (obj.has("specie"))
					custInfoObject.setSpecie(obj.getString("specie"));
				}catch (Exception e){
					custInfoObject.setSpecie("n/a");
				}

				Log.d("PARSING", "datestocked");
				try {
					if (obj.has("dateStocked"))
					custInfoObject.setDateStocked(obj.getString("dateStocked"));
				}catch (Exception e){
					custInfoObject.setDateStocked("n/a");
				}


				Log.d("PARSING", "culture sys");
				try {
					if (obj.has("culturesystem"))
					custInfoObject.setCulturesystem(obj.getString("culturesystem"));
				}catch (Exception e){
					custInfoObject.setCulturesystem("n/a");
				}

				Log.d("PARSING", "rem");
				try {
					if (obj.has("remarks"))
					custInfoObject.setRemarks(obj.getString("remarks"));
				}catch (Exception e){
					custInfoObject.setRemarks("n/a");
				}

				Log.d("PARSING", "p cid");
				try {
					if (obj.has("customerId"))
					custInfoObject.setCustomerID(obj.getString("customerId"));
				}catch (Exception e){
					custInfoObject.setCustomerID("n/a");
				}


				if (obj.has("survivalrate")){
					custInfoObject.setSurvivalrate_per_pond(obj.getString("survivalrate"));
				}



//
//

				Log.d("JSON PARSE",
						"ID: "+custInfoObject.getId()+
						"Latitude: "+custInfoObject.getLatitude()+
						"Longtitude: "+custInfoObject.getLongtitude()+
						"contact name: "+custInfoObject.getContact_name()+
						"company: "+custInfoObject.getCompany()+
						"address: "+custInfoObject.getAddress()+
						"farm_name: "+custInfoObject.getFarmname()+
						"farmid: "+custInfoObject.getFarmID()+
						"contact number: "+custInfoObject.getContact_number()+
						"culture type: "+custInfoObject.getCultureType()+
						"culture level: "+custInfoObject.getCulturelevel()+
						"watertype: "+custInfoObject.getWaterType()+
						"date added: "+custInfoObject.getDateAddedToDB()+

						"sizeofstock: "+custInfoObject.getSizeofStock()+
						"pond id: "+custInfoObject.getPondID()+
						"quantity: "+custInfoObject.getQuantity()+
						"area: "+custInfoObject.getArea()+
						"specie: "+custInfoObject.getSpecie()+
						"datestocked: "+custInfoObject.getDateStocked()+
						"culture system: "+custInfoObject.getCulturesystem()+
						"remarks: "+custInfoObject.getRemarks()+
						"custoemrId: "+custInfoObject.getCustomerID()+
						"survivalRate: " + custInfoObject.getSurvivalrate_per_pond()
				);
				custInfoObjectList.add(custInfoObject);

			}
			
			return custInfoObjectList;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("PARSE", "CustAndPondParse error");
			return null;
		}
		
	}
}

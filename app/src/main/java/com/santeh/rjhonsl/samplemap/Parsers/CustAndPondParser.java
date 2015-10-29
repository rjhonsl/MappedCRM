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

				Log.d("PARSING", "counter");
				try {
					if (obj.has("counter"))
						custInfoObject.setCounter(obj.getInt("counter"));
				}catch (Exception e){
					custInfoObject.setCounter(0);
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


				//CUSTOMER ADDRESS INFO
				if (obj.has("mci_id")){
					if (!obj.isNull("mci_id")){
						custInfoObject.setMainCustomerId(obj.getString("mci_id"));
					}

				}

				if (obj.has("mci_lname")){
					if (!obj.isNull("mci_lname")) {
						custInfoObject.setLastname(obj.getString("mci_lname"));
					}
				}

				if (obj.has("mci_fname")){
					if (!obj.isNull("mci_fname")) {
					custInfoObject.setFirstname(obj.getString("mci_fname"));
					}
				}

				if (obj.has("mci_mname")){
					if (!obj.isNull("mci_mname")) {
						custInfoObject.setMiddleName(obj.getString("mci_mname"));
					}
				}

				if (obj.has("mci_farmid")){
					if (!obj.isNull("mci_farmid")) {
					custInfoObject.setFarmID(obj.getString("mci_farmid"));
					}
				}

				if (obj.has("mci_street")){
					if (!obj.isNull("mci_street")) {
					custInfoObject.setStreet(obj.getString("mci_street"));}
				}
				if (obj.has("mci_housenumber")){
					if (!obj.isNull("mci_housenumber")) {
					custInfoObject.setHouseNumber(obj.getInt("mci_housenumber"));}
				}

				if (obj.has("mci_subdivision")){
					if (!obj.isNull("mci_subdivision")) {
					custInfoObject.setSubdivision(obj.getString("mci_subdivision"));}
				}

				if (obj.has("mci_barangay")){
					if (!obj.isNull("mci_barangay")) {
					custInfoObject.setBarangay(obj.getString("mci_barangay"));}
				}

				if (obj.has("mci_city")){
					if (!obj.isNull("mci_city")) {
					custInfoObject.setCity(obj.getString("mci_city"));}
				}

				if (obj.has("mci_province")){
					if (!obj.isNull("mci_province")) {
					custInfoObject.setProvince(obj.getString("mci_province"));}
				}

				if (obj.has("mci_customerbirthday")){
					if (!obj.isNull("mci_customerbirthday")) {
					custInfoObject.setBirthday(obj.getString("mci_customerbirthday"));}
				}

				if (obj.has("mci_birthplace")){
					if (!obj.isNull("mci_birthplace")) {
					custInfoObject.setBirthPlace(obj.getString("mci_birthplace"));}
				}

				if (obj.has("mci_telephone")){
					if (!obj.isNull("mci_telephone")) {
					custInfoObject.setTelephone(obj.getString("mci_telephone"));}
				}

				if (obj.has("mci_cellphone")){
					if (!obj.isNull("mci_cellphone")) {
					custInfoObject.setCellphone(obj.getString("mci_cellphone"));}
				}

				if (obj.has("mci_civilstatus")){
					if (!obj.isNull("mci_civilstatus")) {
					custInfoObject.setCivilStatus(obj.getString("mci_civilstatus"));}
				}

				if (obj.has("mci_s_lname")){
					if (!obj.isNull("mci_s_lname")) {
					custInfoObject.setSpouse_lname(obj.getString("mci_s_lname"));}
				}

				if (obj.has("mci_s_fname")){
					if (!obj.isNull("mci_s_fname")) {
					custInfoObject.setSpouse_fname(obj.getString("mci_s_fname"));}
				}

				if (obj.has("mci_s_mname")){
					if (!obj.isNull("mci_s_mname")) {
					custInfoObject.setSpouse_mname(obj.getString("mci_s_mname"));}
				}

				if (obj.has("mci_s_birthday")){
					if (!obj.isNull("mci_s_birthday")) {
					custInfoObject.setSpouse_birthday(obj.getString("mci_s_birthday"));}
				}

				if (obj.has("mci_housestatus")){
					if (!obj.isNull("mci_housestatus")) {
					custInfoObject.setHouseStatus(obj.getString("mci_housestatus"));}
				}
				if (obj.has("mci_longitude")){
					if (!obj.isNull("mci_longitude")) {
					custInfoObject.setCust_longtitude(obj.getString("mci_longitude"));}
				}

				if (obj.has("mci_latitude")){
					if (!obj.isNull("mci_latitude")) {
					custInfoObject.setCust_latitude(obj.getString("mci_latitude"));}
				}

				if (obj.has("mci_dateadded")){
					if (!obj.isNull("mci_dateadded")) {
					custInfoObject.setDateAddedToDB(obj.getString("mci_dateadded"));}
				}

				if (obj.has("mci_addedby")){
					if (!obj.isNull("mci_addedby")) {
						custInfoObject.setAddedBy(obj.getString("mci_addedby"));
					}
				}

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

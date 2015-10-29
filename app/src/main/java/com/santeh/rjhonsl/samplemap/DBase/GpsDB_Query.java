package com.santeh.rjhonsl.samplemap.DBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.santeh.rjhonsl.samplemap.Utils.Helper;


public class GpsDB_Query {

	private static final String LOGTAG = "GPS";
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase db;

	/********************************************
	 * 				DEFAULTS					*
	 ********************************************/
	public GpsDB_Query(Context context){
		//Log.d("DBSource", "db connect");
		dbhelper = new GpsSQLiteHelper(context);
		//opens the db connection
	}

	public void open(){
		//Log.d("DBSource", "db open");
		db = dbhelper.getWritableDatabase();
	}
	public void close(){
		//Log.d("DBSource", "db close");
		dbhelper.close();
	}



	/********************************************
	 * 				INSERTS						*
	 ********************************************/
	public void insertUserAccountInfo(int userid, int userlvl, String firstname, String lastname, String username, String password, String deviceID, String dateAdded, int isActive){
		ContentValues values = new ContentValues();
		values.put(GpsSQLiteHelper.CL_USERS_ID, userid);
		values.put(GpsSQLiteHelper.CL_USERS_userlvl, userlvl);
		values.put(GpsSQLiteHelper.CL_USERS_lastName, lastname);
		values.put(GpsSQLiteHelper.CL_USERS_firstName, firstname);
		values.put(GpsSQLiteHelper.CL_USERS_username, username);
		values.put(GpsSQLiteHelper.CL_USERS_password, password);
		values.put(GpsSQLiteHelper.CL_USERS_deviceid, deviceID);
		values.put(GpsSQLiteHelper.CL_USERS_dateAdded, dateAdded );
		values.put(GpsSQLiteHelper.CL_USERS_isactive, isActive);
		db.insert(GpsSQLiteHelper.TBLUSERS, null, values);
	}

	public long insertUserActivityData(int userid, String actiondone, String lat, String lng, String dateTime, String actionType){

		ContentValues values = new ContentValues();
//		values.put(GpsSQLiteHelper.CL_USER_ACTIVITY_ID, null);
		values.put(GpsSQLiteHelper.CL_USER_ACTIVITY_USERID, userid);
		values.put(GpsSQLiteHelper.CL_USER_ACTIVITY_ACTIONDONE, actiondone);
		values.put(GpsSQLiteHelper.CL_USER_ACTIVITY_LAT, lat);
		values.put(GpsSQLiteHelper.CL_USER_ACTIVITY_LNG, lng);
		values.put(GpsSQLiteHelper.CL_USER_ACTIVITY_DATETIME, dateTime);
		values.put(GpsSQLiteHelper.CL_USER_ACTIVITY_ACTIONTYPE, actionType);

		return  db.insert(GpsSQLiteHelper.TBLUSER_ACTIVITY, null, values);
	}

	public long insertWeeklyUpdates(String abw, String remakrs, String pondid, String dateAdded){

		ContentValues values = new ContentValues();
		values.put(GpsSQLiteHelper.CL_WEEKLY_UPDATES_CURRENT_ABW, abw);
		values.put(GpsSQLiteHelper.CL_WEEKLY_UPDATES_REMARKS, remakrs);
		values.put(GpsSQLiteHelper.CL_WEEKLY_UPDATES_PONDID, pondid);
		values.put(GpsSQLiteHelper.CL_WEEKLY_UPDATES_DATEADDED, dateAdded);
		values.put(GpsSQLiteHelper.CL_WEEKLY_UPDATES_isposted, 0);

		return  db.insert(GpsSQLiteHelper.TBLPOND_WeeklyUpdates, null, values);
	}

	public long insertPondData(String pondid, String specie, String sizeofStock, String survivalRate, String dateStocked, String quantity, String area, String culturesystem, String remarks, String customerid) {

		ContentValues values = new ContentValues();
		values.put(GpsSQLiteHelper.CL_POND_PID, pondid);
		values.put(GpsSQLiteHelper.CL_POND_specie, specie);
		values.put(GpsSQLiteHelper.CL_POND_sizeofStock, sizeofStock);
		values.put(GpsSQLiteHelper.CL_POND_survivalrate, survivalRate);
		values.put(GpsSQLiteHelper.CL_POND_dateStocked, dateStocked);
		values.put(GpsSQLiteHelper.CL_POND_quantity, quantity);
		values.put(GpsSQLiteHelper.CL_POND_area, area);
		values.put(GpsSQLiteHelper.CL_POND_culturesystem, culturesystem);
		values.put(GpsSQLiteHelper.CL_POND_remarks, remarks);
		values.put(GpsSQLiteHelper.CL_POND_customerId, customerid);
		values.put(GpsSQLiteHelper.CL_POND_isPosted, 0);
		long id = db.insert(GpsSQLiteHelper.TBLPOND, null, values);
		insertWeeklyUpdates(sizeofStock, remarks, pondid, dateStocked);
		return id;
	}


	public long insertMainCustomerInformation(int userid, String lname, String mname, String fname, String farmid, String housenumber, String street,
											  String subdivision, String barangay, String city, String province, String birthday, String birthplace, String telephone,
											  String cellphone, String civilstatus, String s_fname, String s_lname, String s_mname, String s_birthday, String housestat,
											  String lat, String lng){
		ContentValues values = new ContentValues();

		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_AddedBy, userid);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_LastName, lname);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_FirstName, fname);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_MiddleName, mname);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_FarmId, farmid);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_HouseNumber, housenumber);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_Street, street);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_Subdivision, subdivision);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_Barangay, barangay);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_City, city);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_Province, province);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_CBirthday, birthday);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_CBirthPlace, birthplace);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_Telephone, telephone);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_Cellphone, cellphone);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_CivilStatus, civilstatus);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_S_FirstName, s_fname);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_S_LastName, s_lname);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_S_MiddleName, s_mname);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_S_BirthDay, s_birthday);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_HouseStatus, housestat);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_DateAdded, Helper.getDateDBformat());
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_Latitude, lat);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_Longitude, lng);
		values.put(GpsSQLiteHelper.CL_MAINCUSTINFO_isposted, 0);

		return  db.insert(GpsSQLiteHelper.TBLMAINCUSTOMERINFO, null, values);
	}



	public long insertFarmInformation(String latitude, String longitude, String contactName, String company, String address,
									  String farmname, String farmid, String contactnumber, String cultureType, String cultureLevel, String waterType, String dateAdded,
									  String userID){

		ContentValues values = new ContentValues();
//		values.put(GpsSQLiteHelper.CL_FARMINFO_ID, customerid);
		values.put(GpsSQLiteHelper.CL_FARMINFO_LAT, latitude);
		values.put(GpsSQLiteHelper.CL_FARMINFO_LNG, longitude);
		values.put(GpsSQLiteHelper.CL_FARMINFO_CONTACT_NAME, contactName);
		values.put(GpsSQLiteHelper.CL_FARMINFO_COMPANY, company);
		values.put(GpsSQLiteHelper.CL_FARMINFO_FARM_ADDRESS, address);
		values.put(GpsSQLiteHelper.CL_FARMINFO_FARM_NAME, farmname);
		values.put(GpsSQLiteHelper.CL_FARMINFO_FARM_ID, farmid);
		values.put(GpsSQLiteHelper.CL_FARMINFO_C_NUMBER, contactnumber);
		values.put(GpsSQLiteHelper.CL_FARMINFO_CULTYPE, cultureType);
		values.put(GpsSQLiteHelper.CL_FARMINFO_CULTlVL, cultureLevel);
		values.put(GpsSQLiteHelper.CL_FARMINFO_WATTYPE, waterType);
		values.put(GpsSQLiteHelper.CL_FARMINFO_dateAdded, dateAdded);
		values.put(GpsSQLiteHelper.CL_FARMINFO_addedby, userID);
		values.put(GpsSQLiteHelper.CL_FARMINFO_IsPosted, 0);

		return  db.insert(GpsSQLiteHelper.TBLFARMiNFO, null, values);
	}



	/********************************************
	 * 				VALIDATIONS					*
	 ********************************************/
	public int selectUserinDB(String userID){
		String query = "SELECT * FROM "+GpsSQLiteHelper.TBLUSERS+" WHERE "+GpsSQLiteHelper.CL_USERS_ID+" = ?;";
		String[] params = new String[] {userID};
//		rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}
	
	


	/********************************************
	 * 				SELECTS						*
	 ********************************************/
	public Cursor getUserIdByLogin(String username, String password, String deviceid){
		String query = "SELECT * FROM "+GpsSQLiteHelper.TBLUSERS+" WHERE "
				+ GpsSQLiteHelper.CL_USERS_username + " = ? AND "
				+ GpsSQLiteHelper.CL_USERS_password + " = ? AND "
				+ GpsSQLiteHelper.CL_USERS_deviceid + " = ? "
				;
		String[] params = new String[] {username, password, deviceid };
		return db.rawQuery(query, params);
	}

	public Cursor getAll_FARMINFO_LEFTJOIN_PONDINFO(String userid){
		String query = "SELECT \n" +
				"tblCustomerInfo.*, \n" +
				"tblPond.*, \n" +
				"SUM(tblPond.quantity) AS Totalquantity, \n" +

				"(SELECT DISTINCT GROUP_CONCAT( DISTINCT tblPond.specie ) \n" +
				"FROM tblPond WHERE tblPond.customerId = tblCustomerInfo.ci_customerId ORDER BY tblPond.specie ASC) AS allSpecie \n" +

				"FROM tblCustomerInfo \n" +

				"LEFT JOIN  tblPond \n" +
				"ON tblPond.customerId = tblCustomerInfo.ci_customerId \n" +

				"WHERE tblCustomerInfo.addedby = ? \n" +

				"GROUP BY "+GpsSQLiteHelper.CL_FARMINFO_ID+"  \n" +
				"ORDER BY "+GpsSQLiteHelper.CL_FARMINFO_ID+"  ASC;"
				;
		String[] params =  new String[] {userid};
		return db.rawQuery(query, params);
	}

	public Cursor getCUST_LOCATION_BY_ASSIGNED_AREA(String userid){
		String query =
				"Select tblmaincustomerinfo.* from tblmaincustomerinfo\n" +
				"WHERE tblmaincustomerinfo.mci_addedby= ? ;";

		String[] params =  new String[] {userid};
		return db.rawQuery(query, params);
	}


	public int getUser_Count() {
		String query = "SELECT * FROM "+GpsSQLiteHelper.TBLUSERS+";";
		String[] params = new String[] {};
//		rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getPond_Count() {
		String query = "SELECT * FROM "+GpsSQLiteHelper.TBLPOND+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getArea_Count(){
		String query = "SELECT * FROM "+GpsSQLiteHelper.TBLAREA+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getMainCustInfo_Count(){
		String query = "SELECT * FROM "+GpsSQLiteHelper.TBLMAINCUSTOMERINFO+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getUserActivity_Count(){
		String query = "SELECT * FROM "+GpsSQLiteHelper.TBLUSER_ACTIVITY+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getMunicipality_Count(){
		String query = "SELECT * FROM "+GpsSQLiteHelper.TBLAREA_MUNICIPALITY+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getAssigned_Count(){
		String query = "SELECT* FROM "+GpsSQLiteHelper.TBLAREA_ASSIGNED+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getWeeklyUpdates_Count(){
		String query = "SELECT* FROM "+GpsSQLiteHelper.TBLPOND_WeeklyUpdates+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getFarmInfo_Count(){
		String query = "SELECT* FROM "+GpsSQLiteHelper.TBLFARMiNFO+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}


	/********************************************
	 * 				UPDATES						*
	 ********************************************/
	public int updateRowOneUser(String userid, String lvl, String firstname, String lastname, String username, String password, String deviceid, String dateAdded) {
		String where = GpsSQLiteHelper.CL_USERS_ID + " = " + userid;

		ContentValues newValues = new ContentValues();
		newValues.put(GpsSQLiteHelper.CL_USERS_userlvl, lvl);
		newValues.put(GpsSQLiteHelper.CL_USERS_firstName, firstname);
		newValues.put(GpsSQLiteHelper.CL_USERS_lastName, lastname);
		newValues.put(GpsSQLiteHelper.CL_USERS_username, username);
		newValues.put(GpsSQLiteHelper.CL_USERS_password, password);
		newValues.put(GpsSQLiteHelper.CL_USERS_deviceid, deviceid);
		newValues.put(GpsSQLiteHelper.CL_USERS_dateAdded, dateAdded);

		return 	db.update(GpsSQLiteHelper.TBLUSERS, newValues, where, null);
	}


}

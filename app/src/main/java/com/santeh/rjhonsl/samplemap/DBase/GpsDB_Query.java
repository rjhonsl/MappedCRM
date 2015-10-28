package com.santeh.rjhonsl.samplemap.DBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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

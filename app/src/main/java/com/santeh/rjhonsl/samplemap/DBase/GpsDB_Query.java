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



	//DB HANDLER CLASS
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
		db.close(); // Closing database connection
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

}

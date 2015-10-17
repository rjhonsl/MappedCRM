package com.santeh.rjhonsl.samplemap.DBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class GpsDB_Query {

	private static final String LOGTAG = "GPS";
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	public GpsDB_Query(Context context){
		//Log.d("DBSource", "database connect");
		dbhelper = new GpsSQLiteHelper(context);
		//opens the database connection
	}

	public void open(){
		//Log.d("DBSource", "database open");
		database = dbhelper.getWritableDatabase();
	}
	public void close(){
		//Log.d("DBSource", "database close");
		dbhelper.close();
	}
}

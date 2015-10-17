package com.santeh.rjhonsl.samplemap.DBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GpsSQLiteHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "DB_GPS";
	private static final String DATABASE_NAME = "local.db";
	//each time you change data structure, you must increment this by 1
	private static final int DATABASE_VERSION = 0;

	//Reference for tblSMS
	public static final String TBLMAINCUSTOMERINFO 				= "tblmaincustomerinfo";
	public static final int cn_maincustinfo_index 				= 0;

	public static final String COLUMN_ID 						= "_id"; //Column name for all ID in every table

	public static final String[] ALL_KEY_MAINCUSTOMERINFO 		= new String[]{COLUMN_ID};

	//Query to create tables
	private static final String TBL_CREATE_MAINCUSTOMERINFO =
			"CREATE TABLE " + TBLMAINCUSTOMERINFO + " " +
			"(" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +

			")";



	//connects db
	public GpsSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(LOGTAG, "table " + DATABASE_NAME + " has been opened: " + String.valueOf(context));
	}

	@Override
	//create tb
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TBL_CREATE_MAINCUSTOMERINFO);
		Log.d(LOGTAG, "tables has been created: " + String.valueOf(db));

	}

	@Override
	//on update version renew tb
	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
		_db.execSQL("DROP TABLE IF EXISTS " + TBL_CREATE_MAINCUSTOMERINFO);

		Log.d(LOGTAG, "table has been deleted: " + String.valueOf(_db));
		onCreate(_db);
	}

}

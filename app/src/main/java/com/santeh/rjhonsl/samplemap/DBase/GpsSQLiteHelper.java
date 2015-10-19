package com.santeh.rjhonsl.samplemap.DBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GpsSQLiteHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "DB_GPS";
	private static final String DATABASE_NAME = "local.db";
	//each time you change data structure, you must increment this by 1
	private static final int DATABASE_VERSION = 2;

	//Reference for tblmaincustomerinfo
	public static final String TBLMAINCUSTOMERINFO 				= "tblmaincustomerinfo";

	public static final String CL_MAINCUSTINFO_ID 				= "mci_id"; //Column name for all ID in every table
	public static final String CL_MAINCUSTINFO_LastName 		= "mci_lname";
	public static final String CL_MAINCUSTINFO_FirstName 		= "mci_fname";
	public static final String CL_MAINCUSTINFO_MiddleName 		= "mci_mname";
	public static final String CL_MAINCUSTINFO_FarmId			= "mci_farmid";
	public static final String CL_MAINCUSTINFO_HouseNumber 		= "mci_housenumber";
	public static final String CL_MAINCUSTINFO_Street 			= "mci_street";
	public static final String CL_MAINCUSTINFO_Subdivision 		= "mci_subdivision";
	public static final String CL_MAINCUSTINFO_Barangay 		= "mci_barangay";
	public static final String CL_MAINCUSTINFO_City 			= "mci_city";
	public static final String CL_MAINCUSTINFO_Province 		= "mci_province";
	public static final String CL_MAINCUSTINFO_CBirthday 		= "mci_customerbirthday";
	public static final String CL_MAINCUSTINFO_CBirthPlace 		= "mci_birthplace";
	public static final String CL_MAINCUSTINFO_Telephone 		= "mci_telephone";
	public static final String CL_MAINCUSTINFO_Cellphone 		= "mci_cellphone";
	public static final String CL_MAINCUSTINFO_CivilStatus 		= "mci_civilstatus";
	public static final String CL_MAINCUSTINFO_S_FirstName 		= "mci_s_fname";
	public static final String CL_MAINCUSTINFO_S_LastName 		= "mci_s_lname";
	public static final String CL_MAINCUSTINFO_S_MiddleName 	= "mci_s_mname";
	public static final String CL_MAINCUSTINFO_S_BirthDay 		= "mci_s_birthday";
	public static final String CL_MAINCUSTINFO_HouseStatus 		= "mci_housestatus";
	public static final String CL_MAINCUSTINFO_Latitude 		= "mci_latitude";
	public static final String CL_MAINCUSTINFO_Longitude 		= "mci_longitude";
	public static final String CL_MAINCUSTINFO_DateAdded 		= "mci_dateadded";
	public static final String CL_MAINCUSTINFO_AddedBy			= "mci_addedby";

	public static final String[] ALL_KEY_MAINCUSTOMERINFO 		= new String[]{CL_MAINCUSTINFO_ID, CL_MAINCUSTINFO_LastName, CL_MAINCUSTINFO_FirstName, CL_MAINCUSTINFO_MiddleName,
			CL_MAINCUSTINFO_FarmId, CL_MAINCUSTINFO_HouseNumber, CL_MAINCUSTINFO_Street, CL_MAINCUSTINFO_Subdivision, CL_MAINCUSTINFO_Barangay, CL_MAINCUSTINFO_City, CL_MAINCUSTINFO_Province,
			CL_MAINCUSTINFO_CBirthday, CL_MAINCUSTINFO_CBirthPlace, CL_MAINCUSTINFO_Telephone, CL_MAINCUSTINFO_Cellphone, CL_MAINCUSTINFO_CivilStatus, CL_MAINCUSTINFO_S_FirstName,
			CL_MAINCUSTINFO_S_LastName, CL_MAINCUSTINFO_S_MiddleName, CL_MAINCUSTINFO_S_BirthDay, CL_MAINCUSTINFO_HouseStatus, CL_MAINCUSTINFO_Latitude, CL_MAINCUSTINFO_Longitude,
			CL_MAINCUSTINFO_DateAdded, CL_MAINCUSTINFO_AddedBy};


	//reference for tblarea
	public static final String TBLAREA 				= "tblarea";

	public static final String CL_AREA_ID			= "area_id";
	public static final String CL_AREA_DESCRIPTION	= "area_description";
	public static final String[] ALL_KEY_AREA		= new String[]{CL_AREA_ID, CL_AREA_DESCRIPTION};

	// reference for tblarea
	public static final String TBLAREA_ASSIGNED			= "tblarea_assigned";

	public static final String CL_ASSIGNED_ID			= "assigned_id";
	public static final String CL_ASSIGNED_USERID 		= "assigned_userid";
	public static final String CL_ASSIGNED_AREA 		= "assigned_area";
	public static final String CL_ASSIGNED_MUNICIPALITY	= "assigned_municpality";
	public static final String[] ALL_KEY_ASSIGNED 	= new String[]{CL_ASSIGNED_ID, CL_ASSIGNED_USERID, CL_ASSIGNED_AREA, CL_ASSIGNED_MUNICIPALITY};


	//Query to create tables
	private static final String TBL_CREATE_MAINCUSTOMERINFO =
			"CREATE TABLE " + TBLMAINCUSTOMERINFO + " " +
					"(" +
					CL_MAINCUSTINFO_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_MAINCUSTINFO_LastName 	+ " TEXT, " +
					CL_MAINCUSTINFO_FirstName 	+ " TEXT, " +
					CL_MAINCUSTINFO_MiddleName 	+ " TEXT, " +
					CL_MAINCUSTINFO_FarmId 		+ " TEXT, " +
					CL_MAINCUSTINFO_HouseNumber + " INTEGER, " +
					CL_MAINCUSTINFO_Street 		+ " TEXT, " +
					CL_MAINCUSTINFO_Subdivision + " TEXT, " +
					CL_MAINCUSTINFO_Barangay 	+ " TEXT, " +
					CL_MAINCUSTINFO_City 		+ " TEXT, " +
					CL_MAINCUSTINFO_Province 	+ " TEXT, " +
					CL_MAINCUSTINFO_CBirthday 	+ " DATE, " +
					CL_MAINCUSTINFO_CBirthPlace + " TEXT, " +
					CL_MAINCUSTINFO_Telephone 	+ " TEXT, " +
					CL_MAINCUSTINFO_Cellphone 	+ " TEXT, " +
					CL_MAINCUSTINFO_CivilStatus + " TEXT, " +
					CL_MAINCUSTINFO_S_FirstName + " TEXT, " +
					CL_MAINCUSTINFO_S_LastName 	+ " TEXT, " +
					CL_MAINCUSTINFO_S_MiddleName + " TEXT, " +
					CL_MAINCUSTINFO_S_BirthDay 	+ " DATE, " +
					CL_MAINCUSTINFO_HouseStatus + " TEXT, " +
					CL_MAINCUSTINFO_Latitude 	+ " TEXT, " +
					CL_MAINCUSTINFO_Longitude 	+ " TEXT, " +
					CL_MAINCUSTINFO_DateAdded 	+ " DATETIME, " +
					CL_MAINCUSTINFO_AddedBy		+ " INTEGER " +
					")";


	private static final String TBL_CREATE_AREA =
			"CREATE TABLE " + TBLAREA + " " +
					"(" +
					CL_AREA_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_AREA_DESCRIPTION + " TEXT " +
					")";


	private static final String TBL_CREATE_ASSIGNED_AREA =
			"CREATE TABLE " + TBLAREA_ASSIGNED + " " +
					"(" +
					CL_ASSIGNED_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_ASSIGNED_USERID 			+ " INTEGER, " +
					CL_ASSIGNED_AREA 			+ " INTEGER, " +
					CL_ASSIGNED_MUNICIPALITY 	+ " INTEGER " +
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
		db.execSQL(TBL_CREATE_AREA);
		db.execSQL(TBL_CREATE_ASSIGNED_AREA);
		Log.d(LOGTAG, "tables has been created: " + String.valueOf(db));
		//

	}

	@Override
	//on update version renew tb
	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
		_db.execSQL("DROP TABLE IF EXISTS " + TBLMAINCUSTOMERINFO);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLAREA);
		_db.execSQL("DROP TABLE IF EXISTS " + TBL_CREATE_ASSIGNED_AREA);

		Log.d(LOGTAG, "table has been deleted: " + String.valueOf(_db));
		onCreate(_db);
	}

}

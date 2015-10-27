package com.santeh.rjhonsl.samplemap.DBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GpsSQLiteHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "DB_GPS";
	private static final String DATABASE_NAME = "local.db";
	//each time you change data structure, you must increment this by 1
	private static final int DATABASE_VERSION = 10;

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
	public static final String CL_MAINCUSTINFO_isposted			= "mci_isposted";

	public static final String[] ALL_KEY_MAINCUSTOMERINFO 		= new String[]{CL_MAINCUSTINFO_ID, CL_MAINCUSTINFO_LastName, CL_MAINCUSTINFO_FirstName, CL_MAINCUSTINFO_MiddleName,
			CL_MAINCUSTINFO_FarmId, CL_MAINCUSTINFO_HouseNumber, CL_MAINCUSTINFO_Street, CL_MAINCUSTINFO_Subdivision, CL_MAINCUSTINFO_Barangay, CL_MAINCUSTINFO_City, CL_MAINCUSTINFO_Province,
			CL_MAINCUSTINFO_CBirthday, CL_MAINCUSTINFO_CBirthPlace, CL_MAINCUSTINFO_Telephone, CL_MAINCUSTINFO_Cellphone, CL_MAINCUSTINFO_CivilStatus, CL_MAINCUSTINFO_S_FirstName,
			CL_MAINCUSTINFO_S_LastName, CL_MAINCUSTINFO_S_MiddleName, CL_MAINCUSTINFO_S_BirthDay, CL_MAINCUSTINFO_HouseStatus, CL_MAINCUSTINFO_Latitude, CL_MAINCUSTINFO_Longitude,
			CL_MAINCUSTINFO_DateAdded, CL_MAINCUSTINFO_AddedBy, CL_MAINCUSTINFO_isposted};


	//reference for tblarea
	public static final String TBLAREA 				= "tblarea";

	public static final String CL_AREA_ID			= "area_id";
	public static final String CL_AREA_DESCRIPTION	= "area_description";
	public static final String[] ALL_KEY_AREA		= new String[]{CL_AREA_ID, CL_AREA_DESCRIPTION};

	// reference for tblarea_assigned
	public static final String TBLAREA_ASSIGNED			= "tblarea_assigned";

	public static final String CL_ASSIGNED_ID			= "assigned_id";
	public static final String CL_ASSIGNED_USERID 		= "assigned_userid";
	public static final String CL_ASSIGNED_AREA 		= "assigned_area";
	public static final String CL_ASSIGNED_MUNICIPALITY	= "assigned_municpality";
	public static final String[] ALL_KEY_ASSIGNED 	= new String[]{CL_ASSIGNED_ID, CL_ASSIGNED_USERID, CL_ASSIGNED_AREA, CL_ASSIGNED_MUNICIPALITY};



	//reference for tblarea_municipality
	public static final String TBLAREA_MUNICIPALITY	= "tblarea_municipality";

	public static final String CL_MUNICIPALITY_ID			= "municipality_id";
	public static final String CL_MUNICIPALITY_DESCRIPTION	= "municipality_description";
	public static final String CL_MUNICIPALITY_PROVINCE		= "municipality_province";
	public static final String[] ALL_KEY_MUNICIPALITY		= new String[]{CL_MUNICIPALITY_ID, CL_MUNICIPALITY_DESCRIPTION, CL_MUNICIPALITY_PROVINCE};



	//reference for tblarea
	public static final String TBLFARMiNFO 				= "tblCustomerInfo";

	public static final String CL_FARMINFO_ID			= "ci_customerId";
	public static final String CL_FARMINFO_LAT			= "latitude";
	public static final String CL_FARMINFO_LNG			= "longtitude";
	public static final String CL_FARMINFO_CONTACT_NAME	= "contact_name";
	public static final String CL_FARMINFO_COMPANY		= "company";
	public static final String CL_FARMINFO_FARM_ADDRESS	= "address";
	public static final String CL_FARMINFO_FARM_NAME	= "farm_name";
	public static final String CL_FARMINFO_FARM_ID		= "farmid";
	public static final String CL_FARMINFO_C_NUMBER		= "contact_number";
	public static final String CL_FARMINFO_CULTYPE		= "culture_type";
	public static final String CL_FARMINFO_CULTlVL		= "culture_level";
	public static final String CL_FARMINFO_WATTYPE		= "water_type";
	public static final String CL_FARMINFO_dateAdded	= "dateAdded";
	public static final String CL_FARMINFO_addedby		= "addedby";
	public static final String CL_FARMINFO_IsPosted		= "isposted";
	public static final String[] ALL_KEY_fARM		= new String[]{CL_FARMINFO_ID, CL_FARMINFO_LAT,CL_FARMINFO_LNG,CL_FARMINFO_CONTACT_NAME, CL_FARMINFO_COMPANY, CL_FARMINFO_FARM_ADDRESS
	,CL_FARMINFO_FARM_NAME, CL_FARMINFO_FARM_ID, CL_FARMINFO_C_NUMBER, CL_FARMINFO_CULTYPE, CL_FARMINFO_CULTlVL, CL_FARMINFO_WATTYPE,CL_FARMINFO_dateAdded,  CL_FARMINFO_addedby, CL_FARMINFO_IsPosted};




	//reference for tblPond
	public static final String TBLPOND 				= "tblPond";

	public static final String CL_POND_INDEX			= "id";
	public static final String CL_POND_PID				= "pondid";
	public static final String CL_POND_specie			= "specie";
	public static final String CL_POND_sizeofStock		= "sizeofStock";
	public static final String CL_POND_survivalrate		= "survivalrate";
	public static final String CL_POND_dateStocked		= "dateStocked";
	public static final String CL_POND_quantity			= "quantity";
	public static final String CL_POND_area				= "area";
	public static final String CL_POND_culturesystem	= "culturesystem";
	public static final String CL_POND_remarks			= "remarks";
	public static final String CL_POND_customerId		= "customerId";
	public static final String CL_POND_isPosted			= "p_isposted";
	public static final String[] ALL_KEY_POND			= new String[]{CL_POND_INDEX, CL_POND_PID, CL_POND_specie, CL_POND_sizeofStock, CL_POND_survivalrate,
			CL_POND_dateStocked, CL_POND_quantity, CL_POND_area, CL_POND_culturesystem, CL_POND_remarks, CL_POND_customerId, CL_POND_isPosted};


	public static final String TBLPOND_WeeklyUpdates 	= "tblpond_weeklyupdates";

	public static final String CL_WEEKLY_UPDATES_ID				= "wu_id";
	public static final String CL_WEEKLY_UPDATES_CURRENT_ABW	= "wu_currentabw";
	public static final String CL_WEEKLY_UPDATES_REMARKS		= "wu_remakrs";
	public static final String CL_WEEKLY_UPDATES_PONDID			= "wu_pondid";
	public static final String CL_WEEKLY_UPDATES_DATEADDED 		= "wu_dateAdded";
	public static final String CL_WEEKLY_UPDATES_isposted		= "wu_isposted";
	public static final String[] ALL_KEY_WEEKLY_UPDATES			= new String[]{CL_WEEKLY_UPDATES_ID, CL_WEEKLY_UPDATES_CURRENT_ABW,CL_WEEKLY_UPDATES_REMARKS,CL_WEEKLY_UPDATES_PONDID,
			CL_WEEKLY_UPDATES_DATEADDED, CL_WEEKLY_UPDATES_isposted};


	public static final String TBLUSERS = "tblusers";
	public static final String CL_USERS_ID				= "users_id";
	public static final String CL_USERS_userlvl			= "userlvl";
	public static final String CL_USERS_firstName		= "users_firstname";
	public static final String CL_USERS_lastName		= "users_lastname";
	public static final String CL_USERS_username		= "users_username";
	public static final String CL_USERS_password		= "users_password";
	public static final String CL_USERS_deviceid		= "users_device_id";
	public static final String CL_USERS_dateAdded		= "dateAdded";
	public static final String CL_USERS_isactive		= "isactive";
	public static final String[] ALL_KEY_USERS	= new String[]{CL_USERS_ID, CL_USERS_userlvl, CL_USERS_firstName, CL_USERS_lastName, CL_USERS_username,
			CL_USERS_password, CL_USERS_deviceid, CL_USERS_dateAdded , CL_USERS_isactive};


	//reference for tblarea
	public static final String TBLUSER_ACTIVITY 				= "tbluser_activity";

	public static final String CL_USER_ACTIVITY_ID			= "user_act_id";
	public static final String CL_USER_ACTIVITY_USERID		= "user_act_userid";
	public static final String CL_USER_ACTIVITY_ACTIONDONE	= "user_act_actiondone";
	public static final String CL_USER_ACTIVITY_LAT			= "user_act_latitude";
	public static final String CL_USER_ACTIVITY_LNG			= "user_act_longitude";
	public static final String CL_USER_ACTIVITY_DATETIME	= "user_act_datetime";
	public static final String CL_USER_ACTIVITY_ACTIONTYPE	= "user_act_actiontype";
	public static final String[] ALL_KEY_USERACTIVITY		= new String[]{CL_USER_ACTIVITY_ID, CL_USER_ACTIVITY_USERID, CL_USER_ACTIVITY_ACTIONDONE,
			CL_USER_ACTIVITY_LAT, CL_USER_ACTIVITY_LNG, CL_USER_ACTIVITY_DATETIME, CL_USER_ACTIVITY_ACTIONTYPE};



	//////////////////////////////////////////////////////////////////
	///////////// STRINGS FOR CREATING AND UPDATING TABLE ////////////
	//////////////////////////////////////////////////////////////////

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
					CL_MAINCUSTINFO_AddedBy		+ " INTEGER, " +
					CL_MAINCUSTINFO_isposted	+ " INTEGER " +
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

	private static final String TBL_CREATE_MUNICIPALITY =
			"CREATE TABLE " + TBLAREA_MUNICIPALITY + " " +
					"(" +
					CL_MUNICIPALITY_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_ASSIGNED_USERID 			+ " INTEGER, " +
					CL_MUNICIPALITY_DESCRIPTION + " TEXT, " +
					CL_MUNICIPALITY_PROVINCE 	+ " INTEGER " +
					")";


	private static final String TBL_CREATE_FARMINFO =
			"CREATE TABLE " + TBLFARMiNFO + " " +
					"(" +
					CL_FARMINFO_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_FARMINFO_LAT 			+ " TEXT, " +
					CL_FARMINFO_LNG 			+ " TEXT, " +
					CL_FARMINFO_CONTACT_NAME	+ " TEXT, " +
					CL_FARMINFO_COMPANY			+ " TEXT, " +
					CL_FARMINFO_FARM_ADDRESS	+ " TEXT, " +
					CL_FARMINFO_FARM_NAME		+ " TEXT, " +
					CL_FARMINFO_FARM_ID			+ " TEXT, " +
					CL_FARMINFO_C_NUMBER		+ " TEXT, " +
					CL_FARMINFO_CULTYPE			+ " TEXT, " +
					CL_FARMINFO_CULTlVL			+ " TEXT, " +
					CL_FARMINFO_WATTYPE			+ " TEXT, " +
					CL_FARMINFO_dateAdded		+ " DATE, " +
					CL_FARMINFO_addedby			+ " INTEGER, " +
					CL_FARMINFO_IsPosted		+ " INTEGER " +
					")";

	private static final String TBL_CREATE_POND =
			"CREATE TABLE " + TBLPOND + " " +
					"(" +
					CL_POND_INDEX				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_POND_PID 				+ " INTEGER, " +
					CL_POND_specie 				+ " TEXT, " +
					CL_POND_sizeofStock 		+ " TEXT, " +
					CL_POND_survivalrate 		+ " INTEGER, " +
					CL_POND_dateStocked 		+ " DATE, " +
					CL_POND_quantity 			+ " INTEGER, " +
					CL_POND_area 				+ " INTEGER, " +
					CL_POND_culturesystem 		+ " TEXT, " +
					CL_POND_remarks 			+ " TEXT, " +
					CL_POND_customerId			+ " INTEGER, " +
					CL_POND_isPosted			+ " INTEGER " +
					")";


	private static final String TBL_CREATE_WEEKLYUPDATES =
			"CREATE TABLE " + TBLPOND_WeeklyUpdates + " " +
					"(" +
					CL_WEEKLY_UPDATES_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_WEEKLY_UPDATES_CURRENT_ABW 		+ " INTEGER, " +
					CL_WEEKLY_UPDATES_REMARKS 			+ " TEXT, " +
					CL_WEEKLY_UPDATES_PONDID 			+ " TEXT, " +
					CL_WEEKLY_UPDATES_DATEADDED 		+ " INTEGER, " +
					CL_WEEKLY_UPDATES_isposted 			+ " INTEGER " +
					")";


	private static final String TBL_CREATE_USERS =
			"CREATE TABLE " + TBLUSERS + " " +
					"(" +
					CL_USERS_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_USERS_userlvl 		+ " INTEGER, " +
					CL_USERS_firstName 			+ " TEXT, " +
					CL_USERS_lastName 			+ " TEXT, " +
					CL_USERS_username 		+ " TEXT, " +
					CL_USERS_password 		+ " TEXT, " +
					CL_USERS_deviceid 		+ " TEXT, " +
					CL_USERS_dateAdded 		+ " DATE, " +
					CL_USERS_isactive 		+ " INTEGER " +
					")";

	private static final String TBL_CREATE_USERS_ACTIVITY =
			"CREATE TABLE " + TBLUSER_ACTIVITY + " " +
					"(" +
					CL_USER_ACTIVITY_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_USER_ACTIVITY_USERID 		+ " INTEGER, " +
					CL_USER_ACTIVITY_ACTIONDONE 	+ " TEXT, " +
					CL_USER_ACTIVITY_LAT 			+ " TEXT, " +
					CL_USER_ACTIVITY_LNG 			+ " TEXT, " +
					CL_USER_ACTIVITY_DATETIME 		+ " DATETIME, " +
					CL_USER_ACTIVITY_ACTIONTYPE 	+ " TEXT" +
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
		db.execSQL(TBL_CREATE_MUNICIPALITY);
		db.execSQL(TBL_CREATE_FARMINFO);
		db.execSQL(TBL_CREATE_POND);
		db.execSQL(TBL_CREATE_WEEKLYUPDATES);
		db.execSQL(TBL_CREATE_USERS);
		db.execSQL(TBL_CREATE_USERS_ACTIVITY);
		Log.d(LOGTAG, "tables has been created: " + String.valueOf(db));
	}

	@Override
	//on update version renew tb
	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
		_db.execSQL("DROP TABLE IF EXISTS " + TBLMAINCUSTOMERINFO);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLAREA);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLAREA_ASSIGNED);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLAREA_MUNICIPALITY);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLFARMiNFO);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLPOND);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLPOND_WeeklyUpdates);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLUSERS);
		_db.execSQL("DROP TABLE IF EXISTS " + TBLUSER_ACTIVITY);

		Log.d(LOGTAG, "table has been deleted: " + String.valueOf(_db));
		onCreate(_db);
	}

}

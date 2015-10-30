package com.santeh.rjhonsl.samplemap.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.Obj.Var;
import com.santeh.rjhonsl.samplemap.R;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.crypto.Cipher;

/**
 * Created by rjhonsl on 7/24/2015.
 */
public class Helper {

    public static class userActions{
        public static class TSR{

            public static String ADD_FARM   ="Add Farm Information";
            public static String DELETE_FARM="Delete Farm Information";
            public static String Edit_FARM  ="Edit Farm Information";

            public static String ADD_POND   ="Add Pond Information";
            public static String DELETE_POND="Delete Pond Information";
            public static String Edit_POND  ="Modify Pond Information";
            public static String ADD_MAIN_CUSTOMERINFO ="Add Customer Address Information";
            public static String ADD_WEEKLYREPORT ="Add Weekly Report";

            public static String USER_LOGIN = "Login";


        }
    }

    public static class variables{
        public static String sourceAddress_bizNF                    = "http://santeh.co.nf/phpsql/";
        public static String sourceAddress_goDaddy                  = "http://santeh-webservice.com/php/android_json_post/";
        public static String sourceAddress_bizNF_downloadable       = "http://santeh.co.nf/downloadable/";
        public static String sourceAddress_downloadable_downloadable       = "http://santeh-webservice.com/downloadables/";
        public static String sourceAddress_000webhost               = "http://mysanteh.site50.net/santehweb/";

        public static final String ACTIVITY_LOG_TYPE_FARM_REPORTING= "1";
        public static final String ACTIVITY_LOG_TYPE_TSR_MONITORING= "2";
        public static final String ACTIVITY_LOG_TYPE_ADMIN_ACTIVITY= "3";

        public static String URL_SELECT_ALL_CUSTINFO                        = sourceAddress_goDaddy + "selectAllCustomerInfo.php";
        public static String URL_SELECT_CURRENT_VERSION_NUMBER              = sourceAddress_goDaddy + "selectCurrentVersion.php";
        public static String URL_SELECT_ALL_CUSTINFO_LEFTJOIN_PONDINFO      = sourceAddress_goDaddy + "selectCustinfoLeftJoinPondinf.php";
        public static String URL_SELECT_CUSTINFO_BY_ID                      = sourceAddress_goDaddy + "selectByID.php";
        public static String URL_SELECT_PONDINFO_BY_CUSTOMER_ID             = sourceAddress_goDaddy + "selectPondByCustomerID.php";
        public static String URL_SELECT_PONDINFO_BY_CUSTOMERID_AND_PONDID   = sourceAddress_goDaddy + "selectPondByCustomerIDandPondID.php";
        public static String URL_SELECT_ALL_PONDINFO_GROUPBY_CUSTINFO       = sourceAddress_goDaddy + "selectAllPondGroupBy.php";
        public static String URL_SELECT_ALL_PONDINFO                        = sourceAddress_goDaddy + "selectAllPond.php";
        public static String URL_SELECT_ALL_USERS_IN_AREA                   = sourceAddress_goDaddy + "selectAlluserInAnArea.php";
        public static String URL_SELECT_ALL_USERS                           = sourceAddress_goDaddy + "selectAllUsers.php";
        public static String URL_SELECT_ALL_AREA                            = sourceAddress_goDaddy + "selectAllArea.php";
        public static String URL_SELECT_USERS_ACTIVITY_BY_DATE_AND_ID       = sourceAddress_goDaddy + "selectUserActivityByID.php";
        public static String URL_SELECT_POND_WEEKLY_UPDATES_BY_ID           = sourceAddress_goDaddy + "selectPondWeeklyUpdateByPondID.php";
        public static String URL_SELECT_ALL_USERS_ACTIVITY_BY_ID            = sourceAddress_goDaddy + "selectAllUserActivityByID.php";
        public static String URL_SELECT_ALL_PONDINFO_BETWEEN_DATES          = sourceAddress_goDaddy + "selectAllPondBetweenDate.php";
        public static String URL_SELECT_ALL_PONDINFO_LEFTJOIN_CUSTINFO      = sourceAddress_goDaddy + "selectAllPondInfoLeftJoinCustInfo.php";
        public static String URL_SELECT_FARM_BY_FARMID                      = sourceAddress_goDaddy + "selectFarmByFarmID.php";
        public static String URL_SELECT_FARMINFO_LF_POND_AND_MCI_BY_FARMID  = sourceAddress_goDaddy + "selectCustomerInfoLeftJoinPondInfoLeftJoinMciByFarmID.php";
        public static String URL_SELECT_CUST_LOCATION_BY_ASSIGNED_AREA      = sourceAddress_goDaddy + "selectCustomerLocationByAssingedUser.php";
        public static String URL_SELECT_ALL_POND_WEEKLYUPDATES_INNERJOIN_PONDINFO_GROUPBY_FARMNAME
                = sourceAddress_bizNF + "selectPondWeeklyUpdate_InnerJoin_Pond_GroupByFarmName.php";

        public static String URL_DELETE_CUSTINFO_BY_ID                  = sourceAddress_goDaddy + "deleteByID.php";
        public static String URL_DELETE_POND_BY_ID                      = sourceAddress_goDaddy + "deletePondInfoByID.php";
        public static String URL_DELETE_POND_WEEKLY_DETAILS_BY_ID       = sourceAddress_goDaddy + "deletePondWeeklyDetailsByID.php";

        public static String URL_INSERT_PONDINFO                        = sourceAddress_goDaddy + "insertPondInformation.php";
        public static String URL_INSERT_LOGINLOCATION                   = sourceAddress_goDaddy + "insertLoginLocationOffUser.php";
        public static String URL_INSERT_USER_ACTIVITY                   = sourceAddress_goDaddy + "insertUserActivity.php";
        public static String URL_INSERT_POND_REPORT                     = sourceAddress_goDaddy + "insertPondReport.php";
        public static String URL_INSERT_MAIN_CUSTOMERINFO               = sourceAddress_goDaddy + "insertMainCustomerInfo.php";
        public static String URL_INSERT_FARM_INFO                       = sourceAddress_goDaddy + "insertFarmInformation.php";

        public static String URL_UPDATE_PONDINFO_BY_ID                  = sourceAddress_goDaddy + "updatePondInformationByID.php";
        public static String URL_UPDATE_CUSTOMERINFORMATION_BY_ID       = sourceAddress_goDaddy + "updateCustomerInformationByID.php";
        public static String URL_UPDATE_POND_WEEKLY_DETAIL_BY_ID        = sourceAddress_goDaddy + "updatePodWeeklyDetails.php";

        public static String URL_LOGIN                                  = sourceAddress_goDaddy + "login.php";

        public static String[] ARRAY_SPECIES = {
                "Bangus",   //0
//                "Catfish",  //1
//                "Crab",     //2
//                "Grouper",  //3
//                "Pangasius",//5
//                "Pompano",  //6
                "Prawns",   //7
//                "Rabbit Fish",//8
//                "Seabass",  //9
//                "Snapper",  //10
                "Tilapia",  //12
                "Vannamei"   //11

        };


        public static String[] ARRAY_CULTURE_LEVEL = {
                "Extensive",
                "Intensive",
                "Semi-Intesive",
                "Mono-Culture",
                "Poly-Culture"
        };


        public static String[] ARRAY_CULTURE_SYSTEM = {
                "Cage",
                "Pen",
                "Pond"
        };


        public static double[] ARRAY_TILAPIA_FEEDING_RATE_PER_WEEK ={
                0,       //week 1
                0.1,     //week 2
                0.09,    //week 3
                0.09,    //week 4
                0.07,    //week 5
                0.06,    //week 6
                0.053,   //week 7
                0.048,   //week 8
                0.040,   //week 9
                0.038,   //week 10
                0.035,   //week 11
                0.032,   //week 12
                0.030,   //week 13
                0.030,   //weel 14
                0.028,   //week 15
                0.028,   //week 16
                0.025,   //week 17
                0.025,   //week 18
        };

        public static double[] ARRAY_BANGUS_FEEDING_RATE_PER_WEEK ={
                0.30,   //1
                0.2332, //2
                0.1668, //3
                0.10,   //4
                0.096,  //5
                0.092,  //6
                0.088,  //7
                0.084,  //8
                0.08,   //9
                0.075,  //10
                0.07,   //11
                0.065,  //12
                0.06,   //13
                0.055,  //14
                0.05,   //15
                0.045,  //16
                0.04,   //17
                0.036,  //18
                0.032,  //19
                0.024,  //21
                0.028,  //20
                0.02    //22
        };


        public static double[] ARRAY_TILAPIA_ABW_WEEKLY ={
                0,       //week 1
                7.5,     //week 2
                10.0,    //week 3
                12.5,    //week 4
                20.0,    //week 5
                25.0,    //week 6
                32.5,    //week 7
                42.5,    //week 8
                60.00,   //week 9
                67.50,   //week 10
                82.50,   //week 11
                95.00,   //week 12
                102.5,   //week 13
                135.0,   //weel 14
                155.0,   //week 15
                175.0,   //week 16
                197.5,   //week 17
                200.0,   //week 18
        };

        public static double[] ARRAY_BANGUS_ABW_WEEKLY={
                0,      //week 1
                3.33,   //week 2
                6.67,   //week 3
                10,     //week 4
                13,     //week 5
                16,     //week 6
                19,     //week 7
                22,     //week 8
                25,     //week 9
                56.25,  //week 10
                87.5,   //week 11
                118.75, //week 12
                150,    //week 13
                182.5,  //week 14
                215,    //week 15
                247.5,  //week 16
                280,    //week 17
                324,    //week 18
                368,    //week 19
                412,    //week 20
                456,    //week 21
                500     //week 22
        };


        public static double[] ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY={
                2.5,    //week 4
                3.67,   //week 5
                4.83,   //week 6
                6,      //week 7
                8,      //week 8
                10,     //week 9
                12,     //week 10
                14,     //week 11
                16.5,   //week 12
                19,     //week 13
                20.5,   //week 14
                22,     //week 15
                23.5,   //week 16
                25      //week 17
        };








        public static class tables{

            public static class PONDINFO{
                public static String id = "id";
                public static String pondID = "pondid";
                public static String specie = "specie";
                public static String dateOfStocking = "dateStocked";
                public static String quantity = "quantity";
                public static String areaOfPond = "area";
                public static String cultureSysten = "culturesystem";
                public static String remarks = "culturesystem";
                public static String customerID = "customerID";
            }
        }

        public static void setGlobalVar_currentUserID(int ID, Activity activity){
            ((Var) activity.getApplication()).setCurrentuser(ID);
        }

        public static void setGlobalVar_currentlevel(int lvl, Activity activity){
            ((Var) activity.getApplication()).setCurrentuserLvl(lvl);
        }

        public static int getGlobalVar_currentUserID( Activity activity ){
            return  ((Var) activity.getApplication()).getCurrentuser();
        }

        public static int getGlobalVar_currentLevel(Activity activity){
            return  ((Var) activity.getApplication()).getCurrentuserLvl();
        }


        public static String getGlobalVar_currentUserName(Activity activity){
            return  ((Var) activity.getApplication()).getCurrentUserName();
        }

        public static void setGlobalVar_currentUsername(String username, Activity activity ){
            ((Var) activity.getApplication()).setCurrentUserName(username);
        }


        public static String getGlobalVar_currentUserFirstname( Activity activity ){
            return  ((Var) activity.getApplication()).getCurrentUserFirstname();
        }


        public static String getGlobalVar_DateAdded( Activity activity ){
            return  ((Var) activity.getApplication()).getDateAddedToDB();
        }

        public static void setGlobalVar_DateAddedToDb(String date, Activity activity ){
            ((Var) activity.getApplication()).setDateAddedToDB(date);
        }


        public static void setGlobalVar_currentFirstname(String firstname, Activity activity ){
            ((Var) activity.getApplication()).setCurrentUserFirstname(firstname);
        }


        public static String getGlobalVar_currentUserLastname( Activity activity ){
            return  ((Var) activity.getApplication()).getCurrentUserLastname();
        }

        public static void setGlobalVar_currentLastname(String lastname, Activity activity ){
            ((Var) activity.getApplication()).setCurrentUserLastname(lastname);
        }


        public static String getGlobalVar_currentUserPassword(Activity activity){
            return  ((Var) activity.getApplication()).getCurrentPassword();
        }

        public static void setGlobalVar_currentUserpassword(String password, Activity activity ){
            ((Var) activity.getApplication()).setCurrentPassword(password);
        }


        public static String getGlobalVar_currentAssignedArea( Activity activity ){
            return  ((Var) activity.getApplication()).getAssignedArea();
        }

        public static void setGlobalVar_currentAssignedArea(String area, Activity activity ){
            ((Var) activity.getApplication()).setAssignedArea(area);
        }

        public static int getGlobalVar_currentisActive( Activity activity ){
            return  ((Var) activity.getApplication()).getIsactive();
        }

        public static void setGlobalVar_currentIsActive(int isactive, Activity activity ){
            ((Var) activity.getApplication()).setIsactive(isactive);
        }

        public static String getGlobalVar_currentDeviceID(Activity activity){
            return  ((Var) activity.getApplication()).getDeviceID();
        }

        public static void setGlobalVar_deviceID(String deviceID, Activity activity ){
            ((Var) activity.getApplication()).setDeviceID(deviceID);
        }


        public static CustInfoObject getGlobalVar_CustInfoInterface(Activity activity){
            return  ((Var) activity.getApplication()).getCustInfoObject();
        }

        public static void setGlobalVar_CustInfoInterface(Activity activity, CustInfoObject obj){
            ((Var) activity.getApplication()).setCustInfoObject(obj);
        }



    }



    public class cipher{

        private  KeyPair javkey;

        public void main (String args) throws Exception {
            //
            // check args and get plaintext
            if (args.length() !=1) {
                System.err.println("Usage: java PublicExample text");
                System.exit(1);
            }
            byte[] plainText = args.getBytes("UTF8");

            // generate an RSA key
            Log.d("Cipher", "Start generating RSA key");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(128);
            KeyPair key = keyGen.generateKeyPair();
            Log.d("Cipher", key.toString());
            javkey = key;

            System.out.println("Finish generating RSA key");
            //
            // get an RSA cipher object and print the provider
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            System.out.println( "\n" + cipher.getProvider().getInfo() );
            //
            // encrypt the plaintext using the public key
            System.out.println( "\nStart encryption" );
            cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
            byte[] cipherText = cipher.doFinal(plainText);
            System.out.println( "Finish encryption: " );
            System.out.println( new String(cipherText, "UTF8") );
            //
            // decrypt the ciphertext using the private key
            System.out.println( "\nStart decryption" );
            cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
            byte[] newPlainText = cipher.doFinal(cipherText);
            System.out.println( "Finish decryption: " );
            System.out.println( new String(newPlainText, "UTF8") );
        }
    }

    public static double get_ABW_BY_WEEK_NO(int weekNo) {
        return variables.ARRAY_TILAPIA_ABW_WEEKLY[(weekNo-1)];
    }

    public static String computeWeeklyFeedConsumption(double ABW, double NumberofStock, double feedingrate, double survivalrate) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format (ABW*NumberofStock*feedingrate*survivalrate*7);
    }

    public static String deciformat(double num, int numberOfDecimalPlace) {
        String str = "";

        for (int i = 0; i < numberOfDecimalPlace; i++) {
                str = str +"#";
        }
        DecimalFormat df = new DecimalFormat("#."+str);
        return df.format(num);
    }


    public static double get_TilapiaFeedingRate_by_WeekNum(int weeknum){
        if (weeknum > 18){
            return Helper.variables.ARRAY_TILAPIA_FEEDING_RATE_PER_WEEK[17];
        }else{
            return Helper.variables.ARRAY_TILAPIA_FEEDING_RATE_PER_WEEK[weeknum-1];
        }

    }

    public static int get_currentWeek_by_stockedDate(String stockedDate, int abw){

        DateTime dt = new DateTime();
        GregorianCalendar jdkGcal = dt.toGregorianCalendar();
        dt = new DateTime(jdkGcal);


        String[] datesplitter = stockedDate.split("/");
        int weekStocked = Helper.get_Tilapia_WeekNum_byABW(abw);
        long currendate = System.currentTimeMillis();
        long stockeddate = Helper.convertDateToLong(Integer.parseInt(datesplitter[1]), Integer.parseInt(datesplitter[0]), Integer.parseInt(datesplitter[2]));

        Log.d("Week Summary", stockeddate + " " + currendate + " " + weekStocked);
        DateTime start = new DateTime(
                Integer.parseInt(datesplitter[2]), //year
                Integer.parseInt(datesplitter[0]), //month
                Integer.parseInt(datesplitter[1]), //day
                0, 0, 0, 0);

        datesplitter = Helper.convertLongtoDateString(currendate).split("/");
        DateTime end = new DateTime(
                Integer.parseInt(datesplitter[2]), //year
                Integer.parseInt(datesplitter[1]), //month
                Integer.parseInt(datesplitter[0]), //day
                0, 0, 0, 0);


        Weeks weeks = Weeks.weeksBetween(start, end);

        int currentWeekOfStock = weekStocked + weeks.getWeeks();

        return currentWeekOfStock;
    }


    public static int get_Bangus_WeekNum_byABW(int abw){

        if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[21]){
            return 22;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[20]){
            return 21;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[19]){
            return 20;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[18]){
            return 19;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[17]){
            return 18;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[16]){
            return 17;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[15]){
            return 16;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[14]){
            return 15;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[13]){
            return 14;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[12]){
            return 13;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[11]){
            return 12;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[10]){
            return 11;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[9]){
            return 10;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[8]){
            return 9;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[7]){
            return 8;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[6]){
            return 7;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[5]){
            return 6;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[4]){
            return 5;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[3]){
            return 4;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[2]){
            return 3;
        }else if (abw >= variables.ARRAY_BANGUS_ABW_WEEKLY[1]){
            return 2;
        }else{
            return 1;
        }//
    }


    public static int get_Vannamei_Extenxive_WeekNum_byABW(int abw){

        if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[13]){
            return 17;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[12]){
            return 16;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[11]){
            return 15;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[10]){
            return 14;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[9]){
            return 13;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[8]){
            return 12;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[7]){
            return 11;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[6]){
            return 10;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[5]){
            return 9;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[4]){
            return 8;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[3]){
            return 7;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[2]){
            return 6;
        }else if (abw >= variables.ARRAY_VANNAMEI_EXTENSIVE_ABW_WEEKLY[1]){
            return 5;
        }else{
            return 4;
        }
    }



    public static int get_Tilapia_WeekNum_byABW(int abw){

        if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[17]){
            return 18;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[16]){
            return 17;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[15]){
            return 16;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[14]){
            return 15;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[13]){
            return 14;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[12]){
            return 13;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[11]){
            return 12;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[10]){
            return 11;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[9]){
            return 10;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[8]){
            return 9;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[7]){
            return 8;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[6]){
            return 7;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[5]){
            return 6;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[4]){
            return 5;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[3]){
            return 4;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[2]){
            return 3;
        }else if (abw >= variables.ARRAY_TILAPIA_ABW_WEEKLY[1]){
            return 2;
        }else{
            return 1;
        }
    }



    public static String getTilapiaTypeByNumberOfWeeks(int week){
        String feedtype;

        if (week <= 6) {
            feedtype = "Frymash";
        } else if(week <= 8){
            feedtype = "Crumble";
        } else if(week <= 10){
            feedtype = "Starter";
        } else if(week <= 14){
            feedtype = "Grower";
        } else {
            feedtype = "Finisher";
        }

        return feedtype;
    }

    public static String getBangusFeedtypeByABW(double abw){
        String feedtype;

        if (abw <= 10) {
            feedtype = "Pre-Starter Zero";
        } else if(abw <= 25){
            feedtype = "Pre-Starter";
        } else if(abw <= 150){
            feedtype = "Starter";
        } else if(abw <= 280){
            feedtype = "Grower";
        } else {
            feedtype = "Finisher";
        }

        return feedtype;
    }



    public static void toastShort(Activity context, String msg){

//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) context.findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
        text.setTypeface(font);
        text.setText(msg);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setMargin(0, 0);
        toast.setView(layout);
        toast.show();


    }

    public static void toastLong(Activity context, String msg){

        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) context.findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
        text.setTypeface(font);
        text.setText(msg);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setMargin(0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static Marker map_addMarker(GoogleMap map, LatLng latlng, int iconResID,
                                        final String farmname, final String address, String id, String totalstock, String specie){


        Marker marker = map.addMarker(new MarkerOptions()
                        .title(id + "#-#" + farmname + "#-#" + totalstock + "#-#" + specie)
                        .icon(BitmapDescriptorFactory.fromResource(iconResID))
                        .snippet(address)
                        .position(latlng)
                        .draggable(false)
        );
        return marker;
    }

    public static Marker map_addMarkerIconGen(GoogleMap map, LatLng latlng, Bitmap iconResID, final String activity, String datetime){
        Marker marker = map.addMarker(new MarkerOptions()
                .title(activity)
                .icon(BitmapDescriptorFactory.fromBitmap(iconResID))
                .snippet(datetime)
                .position(latlng)
                .draggable(false)
        );
        return marker;
    }



    public static Dialog createCustomDialogYesNO(Activity activity, int dialogResID, String prompt, String title, String strButton1, String strButton2){
        final Dialog d = new Dialog(activity);//
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(dialogResID);//Set the xml view of the dialog
        Button btn1 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
        Button btn2 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
        TextView txttitle = (TextView) d.findViewById(R.id.dialog_yesno_title);
        TextView txtprompt = (TextView) d.findViewById(R.id.dialog_yesno_prompt);

        txtprompt.setText(prompt);
        txttitle.setText(title);
        btn1.setText(strButton1);
        btn2.setText(strButton2);
        return d;
    }


    public static Dialog createCustomDialogThemedYesNO(Activity activity, String prompt, String title, String strButton1, String strButton2, int resIdColor){
        final Dialog d = new Dialog(activity);//
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_material_themed_yesno);//Set the xml view of the dialog
        Button btn1 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
        Button btn2 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
        TextView txttitle = (TextView) d.findViewById(R.id.dialog_yesno_title);
        TextView txtprompt = (TextView) d.findViewById(R.id.dialog_yesno_prompt);

        txtprompt.setText(prompt);
        txttitle.setText(title);
        txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
        btn1.setText(strButton1);
        btn2.setText(strButton2);
        return d;
    }




    public static Dialog createCustomDialogOKOnly(Activity activity, String title, String prompt, String button){
        final Dialog d = new Dialog(activity);//
        d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
        d.setContentView(R.layout.dialog_material_okonly);//Set the xml view of the dialog
        TextView txttitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
        TextView txtprompt = (TextView) d.findViewById(R.id.dialog_okonly_prompt);
        Button txtok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
        txtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
        txtprompt.setText(prompt);
        txttitle.setText(title);
        txtok.setText(button);
        d.show();

        return d;
    }


    public static Dialog createCustomThemedColorDialogOKOnly(Activity activity, String title, String prompt, String button, int resIdColor){
        final Dialog d = new Dialog(activity);//
        d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
        d.setContentView(R.layout.dialog_material_themed_okonly);//Set the xml view of the dialog
        TextView txttitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
        TextView txtprompt = (TextView) d.findViewById(R.id.dialog_okonly_prompt);
        Button txtok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
        txtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
        txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
        txtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
        txtprompt.setText(prompt);
        txttitle.setText(title);
        txtok.setText(button);
        d.show();
        return d;
    }

    public static Dialog createCustomListDialog(Activity activity, String[] options, String title){
        final Dialog d = new Dialog(activity);//
        d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
        d.setContentView(R.layout.dialog_material_list);//Set the xml view of the dialog

        ListView listview = (ListView) d.findViewById(R.id.dialog_list_listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_item_material, options); //selected item will look like a spinner set from XML
        listViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listview.setAdapter(listViewAdapter);

        TextView txtTitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
        txtTitle.setText(title);
        d.show();
        return d;
    }

    public static Dialog createCustomThemedListDialog(Activity activity, String[] options, String title, int resIdColor){
        final Dialog d = new Dialog(activity);//
        d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
        d.setContentView(R.layout.dialog_material_themed_list);//Set the xml view of the dialog

        ListView listview = (ListView) d.findViewById(R.id.dialog_list_listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_item_material, options); //selected item will look like a spinner set from XML
        listViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listview.setAdapter(listViewAdapter);

        TextView txtTitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
        txtTitle.setBackground(activity.getResources().getDrawable(resIdColor));
        txtTitle.setText(title);
        d.show();
        return d;
    }

    public static Dialog createCustomThemedListDialogWithPrompt(Activity activity, String[] options, String title, String message, int resIdColor){
        final Dialog d = new Dialog(activity);//
        d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
        d.setContentView(R.layout.dialog_material_themed_list_with_prompt);//Set the xml view of the dialog

        ListView listview = (ListView) d.findViewById(R.id.dialog_list_listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_item_material, options); //selected item will look like a spinner set from XML
        listViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listview.setAdapter(listViewAdapter);

        TextView txtTitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
        TextView txtmessage = (TextView) d.findViewById(R.id.txt_message);

        txtTitle.setBackground(activity.getResources().getDrawable(resIdColor));
        txtTitle.setText(title);
        txtmessage.setText(message);

        d.show();
        return d;
    }

    public static long convertDateToLong(int dd, int MM, int yyyy){
        long startDate=000000;
        try {
            String dateString = dd+"/"+MM+"/"+yyyy;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);
            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String convertDatetoGregorain(int yyyy, int MM, int dd){
        String dateString_gregorian="";
        try {
            String dateString = dd+"/"+MM+"/"+yyyy;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);
            dateString_gregorian = convertLongtoDate_Gregorian(date.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString_gregorian;
    }


    public static long convertDateTimeStringToMilis_DB_Format(String datetime){
        long startDate=000000;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(datetime);
            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String convertLongtoDateString(long dateInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        return formatter.format(calendar.getTime());
    }

    public static String convertLongtoDateString_DB_format(long dateInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        return formatter.format(calendar.getTime());
    }


    public static String convertLongtoDate_Gregorian(long dateInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        return formatter.format(calendar.getTime());
    }

    public static String convertLongtoDate_GregorianWithTime(long dateInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy hh:mmaa");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        return formatter.format(calendar.getTime());
    }


    public static String convertLongtoDateTimeString(long dateInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mmaa");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        return formatter.format(calendar.getTime());
    }

    public static String convertLongtoDateTime_DB_Format(long dateInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        return formatter.format(calendar.getTime());
    }


    public static String convertLongtoDate_DB_Format(long dateInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        return formatter.format(calendar.getTime());
    }

    public static String getDateTimeDBformat(){
        return Helper.convertLongtoDateTime_DB_Format(System.currentTimeMillis());
    }

    public static String getDateDBformat(){
        return Helper.convertLongtoDate_DB_Format(System.currentTimeMillis());
    }


    public static int[] convertLongtoDateFormat(long dateinMilis) {
//        Calendar calendar = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(dateinMilis));
        String[] date = dateString.split("/");

//        calendar.setTimeInMillis(dateinMilis);
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);


        return new int[]{month,day,year};
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void hideKeyboardOnLoad(Activity activity){
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }



    public static void moveCursortoEnd(Activity activity, int resId){
        EditText et = (EditText)activity.findViewById(resId);
        et.setSelection(et.getText().length());
    }

    public static String extractResponseCode(String response){

        int coloncounter = 0;
        String responseCode="";
        for (int i = 0; i < response.length(); i++) {
            char c = response.charAt(i);
            if (coloncounter>0){
                responseCode = responseCode + String.valueOf(c);
                break;
            }
            if (c ==':'){
                coloncounter=coloncounter+1;
            }
        }
        return responseCode;
    }

    public static String getMacAddress(Context context){
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getIMEI(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static Dialog initProgressDialog(Activity activity){
        Dialog PD = new Dialog(activity);
        PD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        PD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        PD.setCancelable(false);
        PD.setContentView(R.layout.progressdialog);
        return  PD;
    }


    public static void isLocationAvailablePrompt(final Context context, Activity activity){
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled) {
            final Dialog d = createCustomDialogYesNO(activity, R.layout.dialog_material_yesno, "Location services is needed to use this application. Please turn on Location in settings", "GPS Service", "OK", "No");
            Button b1 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
            Button b2 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);

            b2.setVisibility(View.GONE);
            d.setCancelable(false);
            d.show();

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                    Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(gpsOptionsIntent);

                }
            });

        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    public static LatLng getLastKnownLocation(Context context){

            String location_context = Context.LOCATION_SERVICE;
            LocationManager mLocationManager = (LocationManager) context.getSystemService(location_context);

            List<String> providers = mLocationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                final Location l = mLocationManager.getLastKnownLocation(provider);
//            int d = Log.d("last known location, provider: %s, location: %s", provider);

                if (l == null) {
                    continue;
                }
                if (bestLocation == null
                        || l.getAccuracy() < bestLocation.getAccuracy()) {
//                Log.d("found best last known location: %s", String.valueOf(l));
                    bestLocation = l;
                }
            }
            if (bestLocation == null) {
                assert bestLocation != null;
                bestLocation.setLatitude(0.0);
                bestLocation.setLongitude(0.0);
                return null;
            }
            return new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude());
    }


    public static boolean isIntentKeywordNotNull(String keyword, Intent extras){
        if (extras != null){
            if (extras.hasExtra(keyword)) {
                Log.d("EXTRAS", "true");
                return true;
            }else {
                Log.d("EXTRAS", "false");
                return false;
            }
        }else {
            return false;
        }

    }

    public static String extractResponseCodeBySplit(String response) {
        String[] splitted = response.split(":");
        return splitted[1].substring(0,1);
    }

    public static String getStringResource(Activity activity, int resID) {
        return activity.getResources().getString(resID);
    }

    public static void moveCameraAnimate(final GoogleMap map, final LatLng latlng, final int zoom) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
    }

    public static Bitmap iconGeneratorSample(Context context, String str, Activity activity) {

        IconGenerator iconGenerator = new IconGenerator(context);
        iconGenerator.setBackground(activity.getResources().getDrawable(R.drawable.ic_place_red_24dp));
        iconGenerator.setStyle(IconGenerator.STYLE_GREEN);
        iconGenerator.setTextAppearance(R.style.IconGeneratorTextView);
        return  iconGenerator.makeIcon(str);
    }
    public static CircleOptions addCircle(Activity activity, LatLng latlng, float strokeWidth, int strokeColor, int fillColor, int radius){

        CircleOptions circleOptions = new  CircleOptions()
                .center(new LatLng(latlng.latitude, latlng.longitude))
                .radius(radius)
                .strokeColor(activity.getResources().getColor(strokeColor))
                .fillColor(fillColor)
                .strokeWidth(strokeWidth);
        return circleOptions;
    }




}//end of class

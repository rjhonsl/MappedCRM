package com.santeh.rjhonsl.samplemap.Obj;

import android.app.Application;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by rjhonsl on 8/12/2015.
 */
public class Var extends Application {


//    intent.putExtra("userid", listaccounts.get(0).getUserid());
//    intent.putExtra("userlevel", listaccounts.get(0).getUserid());
//    intent.putExtra("username", listaccounts.get(0).getUserid());
//    intent.putExtra("firstname", listaccounts.get(0).getUserid());
//    intent.putExtra("userid", listaccounts.get(0).getUserid());
//    intent.putExtra("userdescription", listaccounts.get(0).getAccountlevelDescription());

    public int currentuser;
    public int currentuserLvl;

    public int isactive;
    private String currentUserName;
    private String currentUserFirstname;
    private String currentUserLastname;
    private String currentPassword;
    private String dateAddedToDB;
    private String assignedArea;
    private String deviceID;

    private CustInfoObject custInfoObject;


    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    private GoogleMap googleMap;


    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getCurrentUserFirstname() {
        return currentUserFirstname;
    }

    public void setCurrentUserFirstname(String currentUserFirstname) {
        this.currentUserFirstname = currentUserFirstname;
    }

    public String getCurrentUserLastname() {
        return currentUserLastname;
    }

    public void setCurrentUserLastname(String currentUserLastname) {
        this.currentUserLastname = currentUserLastname;
    }


    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public int getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(int currentuser) {
        this.currentuser = currentuser;
    }

    public int getCurrentuserLvl() {
        return currentuserLvl;
    }

    public void setCurrentuserLvl(int currentuserLvl) {
        this.currentuserLvl = currentuserLvl;
    }


    public String getAssignedArea() {
        return assignedArea;
    }

    public void setAssignedArea(String assignedArea) {
        this.assignedArea = assignedArea;
    }

    public String getDateAddedToDB() {
        return dateAddedToDB;
    }

    public void setDateAddedToDB(String dateAddedToDB) {
        this.dateAddedToDB = dateAddedToDB;
    }

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public CustInfoObject getCustInfoObject() {
        return custInfoObject;
    }

    public void setCustInfoObject(CustInfoObject custInfoObject) {
        this.custInfoObject = custInfoObject;
    }
}

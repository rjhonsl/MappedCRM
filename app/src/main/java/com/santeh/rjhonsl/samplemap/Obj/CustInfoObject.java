package com.santeh.rjhonsl.samplemap.Obj;

import android.graphics.Bitmap;

public class CustInfoObject {
	
	private int id;
	private int ci_id;


	private String latitude;
	private String longtitude;
	private String contact_name;
	private String company;
	private String address;
	private String farmname;
	private String farmID;
	private String contact_number;
	private String cultureType;
	private String culturelevel;
	private String waterType;
	private String dateAddedToDB;
	private Bitmap bitmap;

	private String dateTime;
	private String assingedArea;
	private String actionType;


	private String username;
	private String firstname;
	private String lastname;

	private String accountlevelDescription;
	private int userlevel;
	private int userid;
	private int pondID;
	private int area;
	private int quantity;
	private int sizeofStock;
	private int totalStockOfFarm;
    private int pondIndex;
	private int currentweekofStock;
	private int startweekofStock;
	private int week;

	private int isVisited;

	private String customerID;
	private String specie;
	private String culturesystem;
	private String dateStocked;
	private String remarks;
	private String allSpecie;
	private String currentfeedType;

	private String province;
	private String municipality;



	int province_id;
	int municipality_id;
	int municipality_province;



	private String actionDone;
	private String recommendedConsumption;
	private String actualConsumption;

	private double weeklyConsumptionInGrams;
	private String survivalrate_per_pond;

	public CustInfoObject() {
	}



	public String getAssingedArea() {
		return assingedArea;
	}

	public void setAssingedArea(String assingedArea) {
		this.assingedArea = assingedArea;
	}


	public String getSurvivalrate_per_pond() {
		return survivalrate_per_pond;
	}

	public void setSurvivalrate_per_pond(String survivalrate_per_pond) {
		this.survivalrate_per_pond = survivalrate_per_pond;
	}

	public int getIsVisited() {
		return isVisited;
	}

	public void setIsVisited(int isVisited) {
		this.isVisited = isVisited;
	}

	public int getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(int userlevel) {
		this.userlevel = userlevel;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAccountlevelDescription() {
		return accountlevelDescription;
	}

	public void setAccountlevelDescription(String accountlevelDescription) {
		this.accountlevelDescription = accountlevelDescription;
	}


	public double getWeeklyConsumptionInGrams() {
		return weeklyConsumptionInGrams;
	}

	public void setWeeklyConsumptionInGrams(double weeklyConsumptionInGrams) {
		this.weeklyConsumptionInGrams = weeklyConsumptionInGrams;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}


	public int getStartweekofStock() {
		return startweekofStock;
	}

	public void setStartweekofStock(int startweekofStock) {
		this.startweekofStock = startweekofStock;
	}


	public String getRecommendedConsumption() {
		return recommendedConsumption;
	}

	public void setRecommendedConsumption(String recommendedConsumption) {
		this.recommendedConsumption = recommendedConsumption;
	}

	public String getActualConsumption() {
		return actualConsumption;
	}

	public void setActualConsumption(String actualConsumption) {
		this.actualConsumption = actualConsumption;
	}




	public String getCurrentfeedType() {
		return currentfeedType;
	}

	public void setCurrentfeedType(String currentfeedType) {
		this.currentfeedType = currentfeedType;
	}

	public int getCurrentweekofStock() {
		return currentweekofStock;
	}

	public void setCurrentweekofStock(int currentweekofStock) {
		this.currentweekofStock = currentweekofStock;
	}


	public String getAllSpecie() {
		return allSpecie;
	}

	public void setAllSpecie(String allSpecie) {
		this.allSpecie = allSpecie;
	}


	public int getTotalStockOfFarm() {
		return totalStockOfFarm;
	}

	public void setTotalStockOfFarm(int totalStockOfFarm) {
		this.totalStockOfFarm = totalStockOfFarm;
	}



	public int getCi_id() {
		return ci_id;
	}

	public void setCi_id(int ci_id) {
		this.ci_id = ci_id;
	}


	public int getPondIndex() {
        return pondIndex;
    }

    public void setPondIndex(int pondIndex) {
        this.pondIndex = pondIndex;
    }

	public int getSizeofStock() {
		return sizeofStock;
	}

	public void setSizeofStock(int sizeofStock) {
		this.sizeofStock = sizeofStock;
	}

	public int getPondID() {
		return pondID;
	}

	public void setPondID(int pondID) {
		this.pondID = pondID;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public String getCulturesystem() {
		return culturesystem;
	}

	public void setCulturesystem(String culturesystem) {
		this.culturesystem = culturesystem;
	}

	public String getDateStocked() {
		return dateStocked;
	}

	public void setDateStocked(String dateStocked) {
		this.dateStocked = dateStocked;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public String getCulturelevel() {
		return culturelevel;
	}

	public void setCulturelevel(String culturelevel) {
		this.culturelevel = culturelevel;
	}

	public String getCultureType() {
		return cultureType;
	}

	public void setCultureType(String cultureType) {
		this.cultureType = cultureType;
	}

	public String getDateAddedToDB() {
		return dateAddedToDB;
	}

	public void setDateAddedToDB(String dateAddedToDB) {
		this.dateAddedToDB = dateAddedToDB;
	}

	public String getFarmID() {
		return farmID;
	}

	public void setFarmID(String farmID) {
		this.farmID = farmID;
	}

	public String getFarmname() {
		return farmname;
	}

	public void setFarmname(String farmname) {
		this.farmname = farmname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getWaterType() {
		return waterType;
	}

	public void setWaterType(String waterType) {
		this.waterType = waterType;
	}

	public String getActionDone() {
		return actionDone;
	}

	public void setActionDone(String actionDone) {
		this.actionDone = actionDone;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String provice) {
		this.province = provice;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}

	public int getMunicipality_id() {
		return municipality_id;
	}

	public void setMunicipality_id(int municipality_id) {
		this.municipality_id = municipality_id;
	}

	public int getMunicipality_province() {
		return municipality_province;
	}

	public void setMunicipality_province(int municipality_province) {
		this.municipality_province = municipality_province;
	}
}

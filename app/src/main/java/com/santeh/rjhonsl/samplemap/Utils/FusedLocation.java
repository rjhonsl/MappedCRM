package com.santeh.rjhonsl.samplemap.Utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rjhonsl on 9/11/2015.
 */


public class FusedLocation implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
//        ,LocationListener
{

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Location mCurrentLocation;
    LocationRequest mLocationRequest;

    Context fixContext;
    Activity fixActivity;
    LatLng latLng;

    public FusedLocation(Context context, Activity activity){

        fixContext = context;
        fixActivity = activity;
        buildGoogleApiClient(fixContext);
//        createLocationRequest();
    }

    public void buildGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        connectToApiClient();
    }

    public void connectToApiClient() {
        mGoogleApiClient.connect();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

    }
    public void disconnectFromApiClient() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


//    protected void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(10000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }


    public LatLng getLastKnowLocation() {
        latLng = new LatLng(0.0, 0.0);
        connectToApiClient();

        if (mLastLocation != null) {
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        } else {
            latLng = new LatLng(0.0, 0.0);
        }

        return latLng;
    }

    @Override
    public  void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
//        startLocationUpdates();
    }
//
//    protected void startLocationUpdates() {
//        LocationServices.FusedLocationApi.requestLocationUpdates(
//                mGoogleApiClient, mLocationRequest, this);
//    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {


    }


//    @Override
//    public void onLocationChanged(Location location) {
//        mCurrentLocation = location;
//        String mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
////        updateUI();
//    }
}




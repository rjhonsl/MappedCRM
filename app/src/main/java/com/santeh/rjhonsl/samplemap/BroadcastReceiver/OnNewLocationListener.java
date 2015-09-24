package com.santeh.rjhonsl.samplemap.BroadcastReceiver;

import android.location.Location;

/**
 * Created by rjhonsl on 8/18/2015.
 */
public interface OnNewLocationListener {
    public abstract void onNewLocationReceived(Location location);
}

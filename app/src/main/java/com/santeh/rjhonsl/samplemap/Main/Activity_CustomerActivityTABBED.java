package com.santeh.rjhonsl.samplemap.Main;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 8/3/2015.
 */
public class Activity_CustomerActivityTABBED extends FragmentActivity implements ActionBar.TabListener {

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actionBar = getActionBar();
        setContentView(R.layout.activity_customer_activity);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1= actionBar.newTab();
        tab1.setText("TAB1");
        tab1.setTabListener(this);

        ActionBar.Tab tab2= actionBar.newTab();
        tab1.setText("TAB2");
        tab1.setTabListener(this);

        ActionBar.Tab tab3= actionBar.newTab();
        tab1.setText("TAB3");
        tab1.setTabListener(this);

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("TABS Selected", tab.getPosition() + " " + tab.getText());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("TABS Unselected", tab.getPosition() + " " + tab.getText());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("TABS Reselected", tab.getPosition() + " " + tab.getText());
    }
}

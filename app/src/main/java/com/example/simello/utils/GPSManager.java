package com.example.simello.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.simello.controller.varie.User;
import com.example.simello.guanxy.R;

import java.util.Timer;
import java.util.TimerTask;

public class GPSManager extends Service implements LocationListener {

    GPSManager gpsManager;

    Timer timer = new Timer();

    private final Context mContext;
    //flag for Text on Alert
    static boolean notFirstTime = false;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 100 * 60 * 5; // 5 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSManager(Context context) {
        this.mContext = context;
        getLocation();
    }

    public static GPSManager newInstance(Context context) {

        GPSManager manager= new GPSManager(context);
        return manager;
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);


            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.i("NoGPS", "NoGPSATTIVO " + isNetworkEnabled);
                showSettingsAlert();
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSManager.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        AlertDialog builder = alertDialog.create();

        if(builder.isShowing())
        {
            builder.dismiss();
        }
        else {

            // Setting Dialog Title
            alertDialog.setTitle(mContext.getResources().getString(R.string.gpsOff));

            if(!notFirstTime)
            {
                // Setting Dialog Message
                alertDialog.setMessage(mContext.getResources().getString(R.string.gpsOffText1) + " " + User.getUser().getNickname() + mContext.getResources().getString(R.string.gpsOffText2));
                notFirstTime = true;
            }
            else
            {

                alertDialog.setMessage(mContext.getResources().getString(R.string.gpsOffText1) + " " + User.getUser().getNickname() + mContext.getResources().getString(R.string.gpsOffTextNoHello));
            }

            // On pressing Settings button
            alertDialog.setPositiveButton(mContext.getResources().getString(R.string.impostazioni), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });

            alertDialog.setCancelable(false);
            // Showing Alert Message
            alertDialog.show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        gpsManager = new GPSManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        doTimerThings();
        return super.onStartCommand(intent, flags, startId);
    }

    private void doTimerThings()
    {
        timer.scheduleAtFixedRate(new TimerTask() {

            @SuppressLint("DefaultLocale")
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void run() {

                latitude = gpsManager.getLatitude();
                longitude = gpsManager.getLongitude();

                // you get the lat and lng , do your server stuff here-----

                Log.i("GPS","lat------ "+latitude);
                Log.i("GPS","lng-------- "+longitude);
            }

        }, 0, MIN_TIME_BW_UPDATES);

    }


}


/*
Link Guida GPS, op gg isi best EU
http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
 */
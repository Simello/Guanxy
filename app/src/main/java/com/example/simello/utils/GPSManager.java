package com.example.simello.utils;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;



/**
 * Created by Sunfury & Simello on 19/02/15.
 */



public class GPSManager {

    private static GPSManager manager = null;
    long TIME_GPS_LIFE = 1000000000L*60*15;			//15 minuti


    /** L'ultima locazione acquisita */ private Location location = null;
    /** L'oggetto LocationManager */ private LocationManager lm = null;
    /** Tempo di validit� della locazione */ private long locationLife = -1;
    /** Modalit� update */ private boolean searching = false;

    /**
     * Restituisce l'instanza singletone della classe Con_GPSManager
     * @return Un oggetto Con_GPSManager
     */
    public static GPSManager getInstance(Context context) {
        if (manager == null){
            manager = new GPSManager(context.getApplicationContext());
        }
        return manager;
    }

    /**
     * Crea un oggetto Con_GPSManager
     */
    private GPSManager(Context context) {
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager = this;
    }

    /**
     * Chiede al GPS di aggiornare la posizione. Restituisce l'ultima posizione trovata, se � ancora valida, altrimenti null
     * @return	L'ultima posizione trovata, se ancora valida, altrimenti null
     * @throws GPSException	Se il servizio di localizzazione non � attivo
     */
    public void updateLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setSpeedRequired(false);
        criteria.setSpeedAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setCostAllowed(false);
        lm.requestSingleUpdate(criteria, locationListener, null);
        setLocation(null);
        searching = true;
    }

    /**
     * Restituisce l'ultima posizione trovata
     * @return	L'ultima posizione trovata
     * @throws GPSException
     */
    public Location getLocation() throws GPSException {
        if (location != null){
            if (System.nanoTime() < locationLife){
                return location;
            }
            setLocation(null);
        }
        switch(getStatus()){
            case 0: updateLocation(); throw new GPSException("Avviata ricerca", 0);
            case 1: throw new GPSException("Ricerca in corso...", 1);
            case 2: throw new GPSException("Servizi disattivati", 2);
            case 3: throw new GPSException("Servizi disattivi", 3);
            default: throw new IllegalStateException("Il valore deve esssere tra 0 e 3");
        }
    }

    /**
     * Controlla lo stato del servizio di localizzazione
     * @return
     * 0: servizi in attesa
     * 1: servizi in ricerca
     * 2: servizi interrotti
     * 3: servizi non attivi
     */
    public int getStatus(){
        if (isGPSAvailable() || isNetworkAvailable()){
            return (searching ? 1 : 0);
        } else {
            return (searching ? 2 : 3);
        }
    }

    /**
     * Controlla se il GPS � abilitato
     * @return true se il GPS � abilitato, false altrimenti
     */
    private boolean isGPSAvailable(){
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Controlla se la localizzazione tramite rete � abilitata
     * @return true la localizzazione tramite rete � abilitata, false altrimenti
     */
    private boolean isNetworkAvailable(){
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * Interrompe la ricerca della posizione
     */
    public void stop(){
        lm.removeUpdates(locationListener);
    }


    /**
     * Imposta la locazione
     * @param location	La nuova locazione
     */
    private synchronized void setLocation(Location location){
        this.location = location;
    }








    public LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location newLocation){
            setLocation(newLocation);
            locationLife = System.nanoTime() + TIME_GPS_LIFE;
            searching = false;
        }
        @Override
        public void onProviderDisabled(String arg0){
            //
        }
        @Override
        public void onProviderEnabled(String arg0){
            //
        }
        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2){
            if (arg1 == LocationProvider.OUT_OF_SERVICE){
                lm.removeUpdates(this);
            }
        }
    };

    public class GPSException extends Exception {

        /**
         *
         */
        //private static final long serialVersionUID = 5346257514969992915L;

        private int code;

        public GPSException(String message, int code){
            super(message);
            this.code = code;
        }

        public int getCode(){
            return code;
        }

    }

}


/*
Creazione e utilizzo della classe GPSManager

-Creazione dell oggetto:
GPSManager gpsManager = GPSManager.getInstance(this);

-Controllo disponibilità GPS:
        if(gpsManager.getStatus() == 0)
        {
            Log.i("GPS", "In Attesa");
        }


        //Altro codice
           GPSManager gpsManager = GPSManager.getInstance(this);
// DA CONTROLLARE IL GETPOSITION
        if(gpsManager.getStatus() == 0)
        {
            try {
                gpsManager.updateLocation();
                Location loc = gpsManager.getLocation();
                Log.i("GPS", "Lat "+ loc.getLatitude() + " Long " + loc.getLongitude());

            }catch(GPSManager.GPSException e)
                {
                    e.printStackTrace();
                }
        }

 */


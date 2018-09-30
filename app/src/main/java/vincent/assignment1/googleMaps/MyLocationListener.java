package vincent.assignment1.googleMaps;


import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import vincent.assignment1.view.SuggestActivity;


public class MyLocationListener implements LocationListener{

    private Activity activity;
    private double latitude;
    private double longitude;

    public MyLocationListener(Activity activity) {
        this.activity = activity;
        Log.d("GPStest", "new location listener");
    }

    @Override
    public void onLocationChanged(Location location) {

        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();

        ((SuggestActivity) activity).setCurrentLocation(location.getLatitude()+"," + location.getLongitude());

        Log.d("GPStest", location.getLatitude() + "     " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

package vincent.assignment1.googleMaps;


import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import vincent.assignment1.controller.suggestionControl.SuggestionControl;

import static vincent.assignment1.view.MainActivity.TAG_STAGE;


public class MyLocationListener implements LocationListener{

    private Activity activity;

    public MyLocationListener(Activity activity) {
        this.activity = activity;
        Log.d(TAG_STAGE + getClass().getName(), "new location listener");
    }

    @Override
    public void onLocationChanged(Location location) {

        SuggestionControl.getInstance().setLocation(activity, location.getLatitude()+"," + location.getLongitude());

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

package vincent.assignment1.googleMaps;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;
import static vincent.assignment1.view.MainActivity.TAG_STAGE;


/**
 * @author Yu Liu
 *
 * step 1: determine if the google services is work or not
 * step 2: determine if GPS can be used with the APP;
 */

public class MyPermissionChecker {

    //private boolean mLocationPermissionGranted = true;
    private Activity activity;
    private static MyPermissionChecker Instance;

    public static final int ERROR_DIALOG_REQUEST = 9001;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    public static final int PERMISSION_REQUEST_ENABLE_GPS = 9003;

    public MyPermissionChecker(Activity activity){
        this.activity = activity;
    }

    public static MyPermissionChecker getInstance(Activity activity){
        if(Instance == null) {
            Instance = new MyPermissionChecker(activity);
        }

        return Instance;
    }

    public void getLocationPermission(){
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if(ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG_STAGE + getClass().getName(), "init permission， getLocationPermission() is called");
            //mLocationPermissionGranted = true;
            getLocation(activity);
        } else {
            //after this will call activity's onRequestPermissionResult()
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public void getLocation(Activity activity){
        LocationManager mLocationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.d(TAG_STAGE + getClass().getName(), "create new location listener， getLocation() is called");

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1, 0, new MyLocationListener(activity));
    }
}

package vincent.assignment1.googleMaps;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static android.content.Context.LOCATION_SERVICE;


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

//    public boolean isServicesOk(){
//        int avaiable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
//
//        if (avaiable == ConnectionResult.SUCCESS){
//            //everything is fine and the user can make map requests
//            return true;
//        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(avaiable)){
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, avaiable, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }  else {
//            Toast.makeText(activity, "You can't make map requests", Toast.LENGTH_SHORT).show();
//        }
//
//        return false;
//    }

//    public boolean isMapsEnabled(){
//        final LocationManager manager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
//
//        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            buildAlertMessageNoGps();
//            return false;
//        }
//        return true;
//    }

//    private void buildAlertMessageNoGps(){
//        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent enableGpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        //get activity's onActivityResult() run
//                        activity.startActivityForResult(enableGpsIntent, PERMISSION_REQUEST_ENABLE_GPS);
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }

    public void getLocationPermission(){
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if(ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d("GPStest", "init permission");
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

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1, 0, new MyLocationListener(activity));
    }


//    public boolean checkMapServices(){
//        if(isServicesOk()){
//            if(isMapsEnabled()){
//                return true;
//            }
//        }
//        return false;
//    }
}

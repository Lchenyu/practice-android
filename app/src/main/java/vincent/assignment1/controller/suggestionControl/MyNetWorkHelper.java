package vincent.assignment1.controller.suggestionControl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MyNetWorkHelper {

    public MyNetWorkHelper () {

    }

    public static boolean isNetworkAvailable(Context context) {
        //Use ConnectivityManager class to access connectivity nfo
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //checks if internet is available
        boolean isAvailable = false;

        //check if network exists and network is connected
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        } else {
            Toast.makeText(context,"Network is unavailable", Toast.LENGTH_LONG).show();
        }

        return isAvailable;
    }
}

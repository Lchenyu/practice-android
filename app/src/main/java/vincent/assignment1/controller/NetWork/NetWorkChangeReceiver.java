package vincent.assignment1.controller.NetWork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import vincent.assignment1.controller.suggestionControl.SuggestionIntentService;


public class NetWorkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isNetworkAvailable(context))
        {
            Log.d("autosuggestion", "new work is connect");
            Intent serviceIntent = new Intent(context, SuggestionIntentService.class);
            context.startService(serviceIntent);
        }
    }

    private boolean isNetworkAvailable(Context context) {
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

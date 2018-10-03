package vincent.assignment1.controller.NetWork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NetWorkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(checkInternet(context))
        {

        }
    }

    private boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }


}

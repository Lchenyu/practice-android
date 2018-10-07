package vincent.assignment1.controller.suggestionControl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import vincent.assignment1.service.SuggestionIntentService;

import static vincent.assignment1.view.MainActivity.TAG_STAGE;


public class NetWorkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(MyNetWorkHelper.isNetworkAvailable(context))
        {
            Log.d(TAG_STAGE + getClass().getName(), "new work is connect");
            Intent serviceIntent = new Intent(context, SuggestionIntentService.class);
            context.startService(serviceIntent);
        }
    }

}

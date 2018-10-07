package vincent.assignment1.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import vincent.assignment1.controller.suggestionControl.SuggestionControl;
import vincent.assignment1.view.DialogActivity;

public class SuggestionIntentService extends IntentService {


    public SuggestionIntentService() {
        super("SuggestionIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d("autosuggestion", "Suggestion Intent Service Run");

        while (isNetworkAvailable(this)){

            SuggestionControl.getInstance().setSuggestedList();


            try {
                Thread.sleep( 5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showSuggest = new Intent(this, DialogActivity.class);
            showSuggest.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(showSuggest);

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

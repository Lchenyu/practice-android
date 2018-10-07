package vincent.assignment1.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import vincent.assignment1.controller.suggestionControl.MyNetWorkHelper;
import vincent.assignment1.controller.suggestionControl.SuggestionControl;
import vincent.assignment1.view.DialogActivity;

import static vincent.assignment1.view.MainActivity.TAG_STAGE;

public class SuggestionIntentService extends IntentService {


    public SuggestionIntentService() {
        super("SuggestionIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG_STAGE + getClass().getName(), "Suggestion Intent Service Run");

        while (MyNetWorkHelper.isNetworkAvailable(this)){

            SuggestionControl.getInstance().setSuggestedList();

            //sleep awhile waiting for http request (distance matrix)
            try {
                Thread.sleep( 5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent showSuggest = new Intent(this, DialogActivity.class);
            showSuggest.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(showSuggest);

            //sleep awhile then trigger next suggestion
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}

package vincent.assignment1.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import vincent.assignment1.controller.suggestionControl.SuggestionControl;
import vincent.assignment1.view.DialogActivity;

public class SuggestionIntentService extends IntentService {


    public SuggestionIntentService() {
        super("SuggestionIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d("autosuggestion", "Suggestion Intent Service Run");

        while (true){

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

}

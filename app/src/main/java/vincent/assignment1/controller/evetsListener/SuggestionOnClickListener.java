package vincent.assignment1.controller.evetsListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.adapter.TrackableAdapter;
import vincent.assignment1.controller.ObjectWrapperForBinder;
import vincent.assignment1.controller.suggestionControl.SuggestionControl;
import vincent.assignment1.controller.suggestionControl.SuggestionDialog;
import vincent.assignment1.model.SimpleTrackable;


public class SuggestionOnClickListener implements View.OnClickListener {

    private TrackableAdapter adapter;
    private Activity activity;
    private List<SimpleTrackable> suggestedList;

    public SuggestionOnClickListener(TrackableAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }


    @Override
    public void onClick(View v) {

        if(suggestedList == null || suggestedList.size() == 0){
            this.suggestedList = SuggestionControl.getInstance().getSuggestedList(activity, adapter.getFilteredTrackableList());
        }

        Log.d("suggestion", "suggested list size: " + suggestedList.size());
        new SuggestionDialog(activity, v, suggestedList);

    }
}

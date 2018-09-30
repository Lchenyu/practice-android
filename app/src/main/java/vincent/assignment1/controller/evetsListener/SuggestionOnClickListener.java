package vincent.assignment1.controller.evetsListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vincent.assignment1.adapter.TrackableAdapter;
import vincent.assignment1.controller.ObjectWrapperForBinder;
import vincent.assignment1.view.MainActivity;
import vincent.assignment1.view.SuggestActivity;


public class SuggestionOnClickListener implements View.OnClickListener {

    private TrackableAdapter adapter;
    private Activity activity;

    public SuggestionOnClickListener(TrackableAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }


    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();
        bundle.putBinder("filtered", new ObjectWrapperForBinder(adapter));
        Intent intent = new Intent(activity, SuggestActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}

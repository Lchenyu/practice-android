package vincent.assignment1.controller.suggestionControl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.List;

import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.view.AddingActivity;

public class SuggestionDialog {

    private Context activity;
    private View view;
    private List<SimpleTrackable> suggestedList;

    private int action;

    public SuggestionDialog ( Activity activity,  View view,  List<SimpleTrackable> suggestedList){
        this.activity = activity;
        this.view = view;
        this.suggestedList = suggestedList;

        getDialog();
    }

    private void getDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Suggestion");
        if(suggestedList.size() > 0){
            builder.setMessage(suggestedList.get(0).getName());
        }else {
            builder.setMessage("NO SUGGESTION NOW !!!!");
        }

        builder.setPositiveButton("Accept",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Log.d("suggestion", "accept button");

                        Intent intent = new Intent(activity, AddingActivity.class);
                        intent.putExtra("trackable_ID", String.valueOf(suggestedList.get(0).getId()));
                        activity.startActivity(intent);
                        dialog.cancel();
                    }
                });

        builder.setNeutralButton("Skip",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Log.d("suggestion", "skip button");
                        if(suggestedList != null){
                            suggestedList.remove(0);
                        }
                        if(suggestedList.size() > 0){

                            view.callOnClick();
                        } else {
                            dialog.cancel();
                        }

                        //dialog.cancel();
                    }
                });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Log.d("suggestion", "cancel button");
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

}

package vincent.assignment1.controller.suggestionControl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import java.util.List;

import vincent.assignment1.model.SimpleTrackable;

public class SuggestionDialog {

    public SuggestionDialog (Activity activity, final View view, final List<SimpleTrackable> suggestedList){


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
                        dialog.cancel();
                    }
                });

        builder.setNeutralButton("Skip",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Log.d("suggestion", "skip button");
                        suggestedList.remove(0);
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

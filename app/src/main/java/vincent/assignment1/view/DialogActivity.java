package vincent.assignment1.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.controller.suggestionControl.SuggestionControl;
import vincent.assignment1.model.SimpleTrackable;

public class DialogActivity extends Activity {


    private Button cancelBtn;
    private Button acceptBtn;
    private Button skipBtn;
    private TextView suggestion;
    private List<SimpleTrackable> suggestedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        //adjust window size of this dialog activity
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -10;
        params.height = 750;
        params.width = 800;
        params.y = -10;
        getWindow().setAttributes(params);

        cancelBtn = (Button) findViewById(R.id.suggestion_cancel_btn);
        acceptBtn = (Button) findViewById(R.id.suggestion_accept_btn);
        skipBtn = (Button) findViewById(R.id.suggestion_skip_btn);
        suggestion = (TextView) findViewById(R.id.suggestion_text);

        suggestedList = SuggestionControl.getInstance().getMutableList();

        if(suggestedList.size() > 0 && suggestedList != null){
            suggestion.setText(suggestedList.get(0).getName());
        }


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(suggestedList.size() > 0){
                    Intent intent = new Intent(v.getContext(), AddingActivity.class);
                    intent.putExtra("trackable_ID", String.valueOf(suggestedList.get(0).getId()));
                    v.getContext().startActivity(intent);
                }

                finish();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(suggestedList.size() > 0){
                    suggestedList.remove(0);
                }
                finish();
                if(suggestedList.size() > 0) {

                    startActivity(getIntent());
                }

            }
        });

    }




}

package vincent.assignment1.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.controller.evetsListener.EditListener;
import vincent.assignment1.model.SimpleTracking;

public class EditActivity extends AppCompatActivity {

    private TextView titleView;
    private EditText editView;
    private Button saveChange;
    private Spinner timeSpinner;

    private List<String> availableTimelist = new ArrayList<>();
    private List<Date> dateList = new ArrayList<>();
    private SimpleTracking trackingObj;
    private Date selectedDate;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);



        index = Integer.parseInt(getIntent().getStringExtra("tracking_index"));
        trackingObj = TrackingHolder.getINSTANCE().getTrackingList().get(index);

        Toast.makeText(this, "Click field to edit", Toast.LENGTH_LONG).show();

        titleView = (TextView) findViewById(R.id.edit_tracking_title);
        editView = (EditText) findViewById(R.id.edit_edit_title);
        saveChange = (Button) findViewById(R.id.edit_save_btn);
        titleView.setText(trackingObj.getTilte());
        editView.setText(titleView.getText());
        editView.setVisibility(View.GONE);

        EditListener editListener = new EditListener(index, trackingObj,this, editView);

        availableTimelist = editListener.getAvailableTimelist();
        /**
         * have not separate this OnClickListener, because there is no logic code here.
         * but can move into new class if necessary
         */
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleView.setVisibility(View.GONE);
                editView.setVisibility(View.VISIBLE);
            }
        });

        timeSpinner = (Spinner) findViewById(R.id.edit_time_spinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                availableTimelist
        );

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        timeSpinner.setAdapter(spinnerAdapter);


        timeSpinner.setOnItemSelectedListener(editListener.getOnItemSelectedListener());
        saveChange.setOnClickListener(editListener.getOnClickListener());

    }


}

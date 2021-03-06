package vincent.assignment1.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.adapter.TrackableAdapter;
import vincent.assignment1.controller.TrackableReader;
import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.controller.evetsListener.CategorySpinnerOnItemSelectedListener;
import vincent.assignment1.controller.evetsListener.SuggestionOnClickListener;
import vincent.assignment1.controller.suggestionControl.SuggestionControl;
import vincent.assignment1.database.InsertTrackableTask;
import vincent.assignment1.database.LoadTrackingsTask;
import vincent.assignment1.googleMaps.MyPermissionChecker;
import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.controller.suggestionControl.NetWorkChangeReceiver;

public class MainActivity extends AppCompatActivity {

    public final static String SEARCH_DATE = "07/10/2018 1:05:00 PM";

    public final static String TAG_STAGE = "FINAL STAGE: ";

    private List<SimpleTrackable> simpleTrackableList = new ArrayList<>();
    private List<String> categoryList = new ArrayList<>();
    private Toolbar myToolBar;
    private TrackableReader trackableReader;
    private RecyclerView recyclerView;
    private Spinner categorySpinner;
    private Button suggestionBtn;
    private MyPermissionChecker permissionChecker;

    private IntentFilter intentFilter;
    private NetWorkChangeReceiver netWorkChangeReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackable_layout);


        suggestionBtn = (Button) findViewById(R.id.suggestionBtn);

        //set ToolBar instead of ActionBar
        myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

        /**
         * read trackable information from food_truck_data.txt
         * TrackableReader will generate 2 lists:
         * 1. trackablelist contains all information from food_truck_data.txt
         * 2. categorylist contains all categories which will be used in spinner items
         */
        trackableReader = TrackableReader.getINSTANCE(this);
        simpleTrackableList = trackableReader.getTrackableList();
        categoryList = trackableReader.getCategroyList();

        //set recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final TrackableAdapter adapter = new TrackableAdapter(simpleTrackableList);
        recyclerView.setAdapter(adapter);

        //set spinner
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryList
        );

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        categorySpinner.setAdapter(spinnerAdapter);
        categorySpinner.setOnItemSelectedListener(new CategorySpinnerOnItemSelectedListener(adapter));


        permissionChecker = new MyPermissionChecker(this);
        permissionChecker.getLocationPermission();
        permissionChecker.getLocation(this);


        suggestionBtn.setOnClickListener(new SuggestionOnClickListener(adapter, this));


        Log.d(TAG_STAGE + getClass().getName(), "boolean of SuggestionControl instance" +(SuggestionControl.getInstance() == null));

        SuggestionControl.getInstance().setAvailableList(this, adapter.getFilteredTrackableList());

        initializeReceiver();
    }


    //initialize network change receiver
    private void initializeReceiver(){
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        registerReceiver(netWorkChangeReceiver, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkChangeReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG_STAGE + getClass().getName(), "onStart");
        TrackingHolder.getINSTANCE().getTrackingList().clear();
        LoadTrackingsTask loadTrackingsTask = new LoadTrackingsTask(this);
        loadTrackingsTask.execute();
    }


    @Override
    protected void onStop() {
        super.onStop();

        InsertTrackableTask insertTrackableTask = new InsertTrackableTask(trackableReader.getTrackableList(), this);
        insertTrackableTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_trackinglist:
                Intent intent = new Intent(this, TrackingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

package vincent.assignment1.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.controller.TrackableReader;
import vincent.assignment1.adapter.TrackableAdapter;
import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.controller.evetsListener.CategorySpinnerOnItemSelectedListener;
import vincent.assignment1.controller.evetsListener.SuggestionOnClickListener;
import vincent.assignment1.database.MyDatabaseHelper;
import vincent.assignment1.googleMaps.MyGoogleMapPermissionChecker;
import vincent.assignment1.googleMaps.MyLocationListener;
import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.service.TestTrackingService;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ReadFile";
    private List<SimpleTrackable> simpleTrackableList = new ArrayList<>();
    private List<String> categoryList = new ArrayList<>();
    private Toolbar myToolBar;
    private TrackableReader trackableReader;
    private RecyclerView recyclerView;
    private Spinner categorySpinner;
    private Button suggestionBtn;

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private boolean mLocationPermission = false;


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

        //create database if there is no db
        dbHelper = new MyDatabaseHelper(this, "assignment.db");
        db = dbHelper.getWritableDatabase();
        //loading trackable list into db
        dbHelper.InsertTrackableDB(trackableReader.getTrackableList(), db);
        //loading tracking list into tracking holder
        dbHelper.loadTrackingDB(db);







        suggestionBtn.setOnClickListener(new SuggestionOnClickListener(adapter, this));

    }


    @Override
    protected void onRestart() {
        super.onRestart();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //close db connection when the app turn off
        dbHelper.InsertTrackingDB(TrackingHolder.getINSTANCE().getTrackingList(),dbHelper.getWritableDatabase());
        dbHelper.close();
    }



}

package vincent.assignment1.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.adapter.TrackableAdapter;
import vincent.assignment1.controller.ObjectWrapperForBinder;
import vincent.assignment1.controller.suggestionControl.DistanceMatrixAsyncTask;
import vincent.assignment1.controller.suggestionControl.SuggestionTrackableManager;
import vincent.assignment1.googleMaps.MyGoogleMapPermissionChecker;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTrackable;


public class SuggestActivity extends AppCompatActivity {

    private TrackableAdapter adapter;
    private TrackableAdapter suggestionAdapter;
    private RecyclerView recyclerView;
    private String currentLocation;
    private MyGoogleMapPermissionChecker googleMapPermissionChecker;

    private List<SimpleTrackable> mSimpleTrackableList;
    private List<SimpleRoute> mRouteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        adapter = (TrackableAdapter)((ObjectWrapperForBinder) getIntent().getExtras().getBinder("filtered")).getData();



        mSimpleTrackableList = adapter.getFilteredTrackableList();

        SuggestionTrackableManager suggestionTrackableManager = new SuggestionTrackableManager(this, mSimpleTrackableList);

        suggestionTrackableManager.getRouteInfo();



        suggestionAdapter = new TrackableAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.suggestion_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(suggestionAdapter);


        googleMapPermissionChecker = new MyGoogleMapPermissionChecker(this);
        googleMapPermissionChecker.getLocationPermission();
        googleMapPermissionChecker.getLocation(this);


    }


    public void setCurrentLocation(String location, List<SimpleRoute> routeList){
        this.currentLocation = location;

        DistanceMatrixAsyncTask distanceMatrixAsyncTask = new DistanceMatrixAsyncTask(this,currentLocation,routeList);


        distanceMatrixAsyncTask.execute();
    }



    public void getNearest(String s){
        Log.d("httptest", "suggestion activity" + s);
    }
}

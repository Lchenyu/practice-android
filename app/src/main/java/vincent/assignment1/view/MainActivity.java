package vincent.assignment1.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.controller.TrackableReader;
import vincent.assignment1.adapter.TrackableAdapter;
import vincent.assignment1.controller.evetsListener.CategorySpinnerOnItemSelectedListener;
import vincent.assignment1.model.SimpleTrackable;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ReadFile";
    private List<SimpleTrackable> simpleTrackableList = new ArrayList<>();
    private List<String> categoryList = new ArrayList<>();
    private Toolbar myToolBar;
    private TrackableReader trackableReader;
    private RecyclerView recyclerView;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackable_layout);

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
        //readTrackable(simpleTrackableList);



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

//        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                adapter.getFilter(parent.getItemAtPosition(position).toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });


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

//    private void readTrackable(List<SimpleTrackable> list){
//        int id;
//        String name;
//        String description;
//        String url;
//        String cateGory;
//        addCategoryList("All");
//        try (Scanner reader = new Scanner(getResources().openRawResource(R.raw.food_truck_data)))
//        {
//            // match comma and 0 or more whitespace OR trailing space and newline
//
//            reader.useDelimiter("\",\"|,\"|\"\\n");
//            while (reader.hasNext())
//            {
//                id = Integer.parseInt(reader.next());
//                name = reader.next();
//                description = reader.next();
//                url = reader.next();
//                cateGory = reader.next();
//
//                addCategoryList(cateGory);
//
//                SimpleTrackable food_truck = new SimpleTrackable(id,name,description,cateGory,url,0);
//
//                list.add(food_truck);
//            }
//        }
//        catch (Resources.NotFoundException e)
//        {
//            Log.i(LOG_TAG, "File Not Found Exception Caught");
//        }
//
//    }
//
//
//    private void addCategoryList(String caterGory){
//        boolean flag = false;
//        for(String c : categroyList){
//            if(c.equals(caterGory))
//                flag = true;
//        }
//
//        if(!flag) {
//            categroyList.add(caterGory);
//        }
//    }
}

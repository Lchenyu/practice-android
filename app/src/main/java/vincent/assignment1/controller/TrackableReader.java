package vincent.assignment1.controller;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vincent.assignment1.R;
import vincent.assignment1.model.SimpleTrackable;

/**
 * @author Yu Liu
 * @version 1.0
 *
 * A singleton class to read "food_truck_data.txt" and to create list of trackables
 */

public class TrackableReader {

    private static TrackableReader INSTANCE = null;
    private List<SimpleTrackable> trackables_list = new ArrayList<>();
    private List<String> categroyList = new ArrayList<>();


    public TrackableReader (Context context){
        readFile(context);

    }

    public static TrackableReader getINSTANCE(Context context) {

        if(INSTANCE == null){
            INSTANCE = new TrackableReader(context);
        }

        return INSTANCE;
    }


    private void readFile(Context context){
        int id;
        String name;
        String description;
        String url;
        String cateGory;
        addCategoryList("All");
        try (Scanner reader = new Scanner(context.getResources().openRawResource(R.raw.food_truck_data)))
        {
            // match comma and 0 or more whitespace OR trailing space and newline

            reader.useDelimiter("\",\"|,\"|\"\\n");
            while (reader.hasNext())
            {
                id = Integer.parseInt(reader.next());
                name = reader.next();
                description = reader.next();
                url = reader.next();
                cateGory = reader.next();
                addCategoryList(cateGory);

                SimpleTrackable food_truck = new SimpleTrackable(id,name,description,cateGory,url,0);

                trackables_list.add(food_truck);
            }
        }
        catch (Resources.NotFoundException e)
        {
            Log.i("Trackable_Reader", "File Not Found Exception Caught");
        }
    }

    public List<SimpleTrackable> getTrackableList(){
        return trackables_list;
    }

    public List<String> getCategroyList(){
        return categroyList;
    }



    public SimpleTrackable getSelectedTrackableObj (int id){
        SimpleTrackable tempTrackable;
        for(SimpleTrackable trackable :trackables_list){
            if(trackable.getId() == id){
                tempTrackable = trackable;
                return tempTrackable;
            }
        }
        return null;
    }


    private void addCategoryList(String caterGory){
        boolean flag = false;
        for(String c : categroyList){
            if(c.equals(caterGory))
                flag = true;
        }

        if(!flag) {
            categroyList.add(caterGory);
        }
    }


}

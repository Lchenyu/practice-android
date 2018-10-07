package vincent.assignment1.controller.suggestionControl;

import android.app.Activity;
import android.util.Log;

import java.util.List;

import vincent.assignment1.model.SimpleTrackable;

public class SuggestionControl {

    private static SuggestionControl Instance;

    private String curLocation;
    private List<SimpleTrackable> availableList;
    private Activity activity;
    private List<SimpleTrackable> suggestedList;
    private List<SimpleTrackable> mutableList;

    public SuggestionControl (){
    }


    public static SuggestionControl getInstance(){
        if(Instance == null){
            Instance = new SuggestionControl();
        }
        return Instance;
    }

    public void setLocation (Activity activity, String location){
        if(this.activity == null){
            this.activity = activity;
        }
        this.curLocation = location;

//        setSuggestedList();

    }

    public List<SimpleTrackable> getMutableList(){

        if(mutableList == null){
            Log.d("autosuggestion","get mutable list");
            this.mutableList = this.suggestedList;
        }else if ( mutableList.size() == 0){
            this.mutableList = this.suggestedList;
        }

        Log.d("autosuggestion", "mutableList size :" + mutableList.size());
        return this.mutableList;
    }


    public void setAvailableList (Activity activity, List<SimpleTrackable> availableList){
        if(this.activity == null){
            this.activity = activity;
        }
        this.availableList = availableList;
    }

    public void updateAvailableList ( List<SimpleTrackable> availableList) {
        this.availableList = availableList;

    }

    public void setSuggestedList (){

        this.suggestedList = getSuggestedList();
        Log.d("autosuggestion", "SuggestedList size: " + suggestedList.size());

    }

    public List<SimpleTrackable> getSuggestedList() {

        SuggestionTrackableManager manager = new SuggestionTrackableManager(activity, availableList);

        DistanceMatrixManager distanceMatrixManager = new DistanceMatrixManager(activity, curLocation, manager.getRouteInfo());

        return distanceMatrixManager.getFinalSuggestionList(distanceMatrixManager.getResponseList());
    }

}

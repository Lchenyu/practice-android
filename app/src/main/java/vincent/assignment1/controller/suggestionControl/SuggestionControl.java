package vincent.assignment1.controller.suggestionControl;

import android.app.Activity;

import java.util.List;

import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTrackable;

public class SuggestionControl {

    private static SuggestionControl Instance;

    private String curLocation;

    public SuggestionControl (String location){
        this.curLocation = location;
    }

    public static void initializeInsance (String location){
        Instance = new SuggestionControl(location);
    }

    public static SuggestionControl getInstance(){
        return Instance;
    }


    public List<SimpleTrackable> getSuggestedList(Activity activity, List<SimpleTrackable> availableList) {

        SuggestionTrackableManager manager = new SuggestionTrackableManager(activity, availableList);

        DistanceMatrixManager distanceMatrixManager = new DistanceMatrixManager(activity, curLocation, manager.getRouteInfo());


        return distanceMatrixManager.getFinalSuggestionList(distanceMatrixManager.getResponseList());
    }

}

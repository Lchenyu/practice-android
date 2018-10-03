package vincent.assignment1.controller.suggestionControl;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.controller.TrackableReader;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTrackable;


public class DistanceMatrixManager {

    private String disMatrixURL;
    private Activity activity;
    private List<SimpleRoute> routeList;
    private List<SimpleRoute> prioritizedRouteList;
    private String curLocation;

    public DistanceMatrixManager(Activity activity, String curLocation, List<SimpleRoute> routeList){
        this.curLocation = curLocation;
        this.activity = activity;
        this.prioritizedRouteList = new ArrayList<>();
        this.routeList = routeList;

    }


    public List<Integer> getResponseList() {

        List<Integer> valueList = new ArrayList<>();

        for(SimpleRoute routeObject : routeList){
            disMatrixURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + curLocation
                    + "&destinations="+ routeObject.getLatitude() + "," + routeObject.getLongitude()
                    + "&key=" + activity.getResources().getString(R.string.google_map_api_key);

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet getRequest = new HttpGet(disMatrixURL);

            String responseBody = null;
            HttpEntity entity;
            try{

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //your codes here

                    HttpResponse response = httpClient.execute(getRequest);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    responseBody = responseHandler.handleResponse(response);

                }




            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject result = new JSONObject(responseBody);
                JSONArray rows = result.getJSONArray("rows");
                JSONObject row1 = rows.getJSONObject(0);
                JSONArray elements = row1.getJSONArray("elements");
                JSONObject ele1 = elements.getJSONObject(0);

                Log.d("jsontest", ele1.toString());

                JSONObject distance = ele1.getJSONObject("distance");
                JSONObject duration = ele1.getJSONObject("duration");

                int dis = distance.getInt("value");
                int dur = duration.getInt("value");
                valueList.add(dur);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return valueList;
    }


    public List<SimpleTrackable> getFinalSuggestionList(List<Integer> valueList) {




        //sort by duration and only store the route object which has stoptime greater than its duration
        while (valueList.size() != 0){

            int index = 0;

            for(int i = 1; i < valueList.size(); i++){

                if(valueList.get(index) > valueList.get(i)){
                    index = i;
                }
            }

            prioritizedRouteList.add(routeList.get(index));


            routeList.remove(index);
            valueList.remove(index);
        }

        List<SimpleTrackable> suggestionTackableList = new ArrayList<>();
        List<SimpleTrackable> trackableList = TrackableReader.getINSTANCE(activity).getTrackableList();

        for(SimpleRoute routeObject : prioritizedRouteList){
            for(SimpleTrackable trackableObject : trackableList){
                if(routeObject.getTrackableId() == trackableObject.getId()){
                    suggestionTackableList.add(trackableObject);
                }
            }
        }
        return suggestionTackableList;
    }
}

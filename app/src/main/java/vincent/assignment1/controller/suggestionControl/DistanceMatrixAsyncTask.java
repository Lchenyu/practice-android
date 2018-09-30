package vincent.assignment1.controller.suggestionControl;


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.view.SuggestActivity;

public class DistanceMatrixAsyncTask extends AsyncTask<Void, Void, String> {

    private String disMatrixURL;
    private SuggestActivity activity;
    private List<SimpleRoute> routeList;

    public DistanceMatrixAsyncTask (SuggestActivity activity, String curLocation, List<SimpleRoute> routeList){
        disMatrixURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + curLocation
                + "&destinations="+ targetLocation
                + "&key=" + activity.getResources().getString(R.string.google_map_api_key);

        this.activity = activity;

    }

    @Override
    protected String doInBackground(Void... voids) {

        Log.d("httptest", "doinbackground");

        HttpClient httpClient = new DefaultHttpClient();


        HttpGet getRequest = new HttpGet(disMatrixURL);
        String responseBody = null;
        try{

            responseBody = httpClient.execute(getRequest, new BasicResponseHandler());

            Log.d("httptest", responseBody);

//            HttpResponse response = httpClient.execute(getRequest);
//
//            HttpEntity entity = response.getEntity();
//
//            if(entity != null){
//                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
//            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPreExecute();

        try {
            JSONObject result = new JSONObject(s);
            JSONArray rows = result.getJSONArray("rows");
            JSONObject row1 = rows.getJSONObject(0);
            JSONArray elements = row1.getJSONArray("elements");
            JSONObject ele1 = elements.getJSONObject(0);

            Log.d("jsontest", ele1.toString());

            JSONObject distance = ele1.getJSONObject("distance");
            JSONObject duration = ele1.getJSONObject("duration");

            int dis = distance.getInt("value");
            int dur = duration.getInt("value");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("httpTest", "onPostExecute   " + s);
    }
}

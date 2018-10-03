package vincent.assignment1.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.List;

import vincent.assignment1.model.SimpleTrackable;

public class InsertTrackableTask extends AsyncTask<Void, Void, Void> {


    private List<SimpleTrackable> trackableList;
    private Activity activity;


    public InsertTrackableTask (List<SimpleTrackable> trackableList, Activity activity){
        this.activity = activity;
        this.trackableList = trackableList;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        MyDatabaseManager dbManager = MyDatabaseManager.getInstance(activity);
        SQLiteDatabase db = dbManager.openDatabase();

        ContentValues values = new ContentValues();

        for(SimpleTrackable trackable : trackableList){
            values.put("trackableID", trackable.getId());
            values.put("name", trackable.getName());
            values.put("description", trackable.getDescription());
            values.put("category", trackable.getCategory());
            values.put("webSite", trackable.getWebSite());
            db.insertWithOnConflict(MyDatabaseHelper.TRACKABLE_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            values.clear();
        }

        dbManager.closeDatabase();

        return null;
    }
}

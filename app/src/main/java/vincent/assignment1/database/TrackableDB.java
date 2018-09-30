package vincent.assignment1.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.model.Trackable;


public class TrackableDB {

    private List<SimpleTrackable> trackableList;
    private SQLiteDatabase db;

    public TrackableDB(List<SimpleTrackable> trackableList, SQLiteDatabase db){
        this.trackableList = trackableList;
        this.db = db;
    }

    public void InsertData(){
        ContentValues values = new ContentValues();

        for(SimpleTrackable trackable : trackableList){
            values.put("trackableID", trackable.getId());
            values.put("name", trackable.getName());
            values.put("description", trackable.getDescription());
            values.put("category", trackable.getCategory());
            values.put("webSite", trackable.getWebSite());
            db.insertWithOnConflict("trackable", null, values, SQLiteDatabase.CONFLICT_REPLACE);
            values.clear();
        }
    }

}

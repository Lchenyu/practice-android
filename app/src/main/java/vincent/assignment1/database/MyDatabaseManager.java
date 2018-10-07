package vincent.assignment1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Yu Liu
 *
 * this singleten class is going to make sure there is only one database connection exist
 * when there may be a lot database thread to access connection.
 */

public class MyDatabaseManager {

    private static MyDatabaseManager INSTANCE;
    private static MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private int mOpenCounter;

    public MyDatabaseManager(){

    }


    public static synchronized MyDatabaseManager getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new MyDatabaseManager();
            myDatabaseHelper = new MyDatabaseHelper(context);
        }
        return INSTANCE;
    }


    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            // Opening new database
            mDatabase = myDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if(mOpenCounter == 0) {
            // Closing database
            mDatabase.close();
        }
    }

}

package vincent.assignment1.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTimeHelper {



    // hard code time because the trackingservice is using different date format which make a lot problem
    public static int getMeasurableTime(Date date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        String strDate = dateFormat.format(date);

        String[] splitedDate = strDate.split(":");
        int hh = Integer.valueOf(splitedDate[0]);
        int mm = Integer.valueOf(splitedDate[1]);
        int ss = Integer.valueOf(splitedDate[2]);

        return hh* 60 * 60 + mm * 60 + ss;
    }

    public static int getTimeDiff(int date1, int date2){
        return (date1 - date2);
    }
}

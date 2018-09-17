package vincent.assignment1.controller;

import java.util.Comparator;

import vincent.assignment1.model.SimpleTracking;

public class DateComparator implements Comparator<SimpleTracking> {
    @Override
    public int compare(SimpleTracking o1, SimpleTracking o2) {
        return o1.getMeetTime().compareTo(o2.getMeetTime());
    }
}

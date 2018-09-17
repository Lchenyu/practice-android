package vincent.assignment1.controller.evetsListener;

import android.view.View;
import android.widget.AdapterView;

import vincent.assignment1.adapter.TrackableAdapter;

/**
 * @author Yu Liu
 *
 * custom listener for category spinner OnItemSelectedListener
 */

public class CategorySpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

    private TrackableAdapter adapter;
    public CategorySpinnerOnItemSelectedListener(TrackableAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        adapter.getFilter(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

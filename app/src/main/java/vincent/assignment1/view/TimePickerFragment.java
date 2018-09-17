package vincent.assignment1.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    int minute = Calendar.getInstance().get(Calendar.MINUTE);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(
                getActivity(),
                (TimePickerDialog.OnTimeSetListener)getActivity(),
                hour,
                minute,
                false);
    }
}

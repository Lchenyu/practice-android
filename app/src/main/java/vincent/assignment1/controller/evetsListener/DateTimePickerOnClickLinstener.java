package vincent.assignment1.controller.evetsListener;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yu Liu
 *
 * click to open date or time picker fragment
 */
public class DateTimePickerOnClickLinstener implements View.OnClickListener {

    private DialogFragment pickerDialogFragment;
    private FragmentActivity context;


    public DateTimePickerOnClickLinstener (DialogFragment pickerDialogFragment, FragmentActivity  context){
        this.pickerDialogFragment = pickerDialogFragment;
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        pickerDialogFragment.show(context.getSupportFragmentManager(), "Date and Time");
    }
}

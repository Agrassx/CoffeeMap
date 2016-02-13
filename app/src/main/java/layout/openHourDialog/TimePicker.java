package layout.openHourDialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePicker extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    private TimePickedListener onTimePickedListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new android.app.TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        String strHour;
        String strMinute;
        if (hourOfDay < 10) {
            strHour = "0"+String.valueOf(hourOfDay);
        } else {
            strHour = String.valueOf(hourOfDay);
        }
        if (minute < 10) {
            strMinute = "0"+String.valueOf(minute);
        } else {
            strMinute = String.valueOf(minute);
        }
        onTimePickedListener.onTimePicked(strHour, strMinute);
    }

    public void setOnTimePickedListener(TimePickedListener onTimePickedListener) {
        this.onTimePickedListener = onTimePickedListener;
    }
}

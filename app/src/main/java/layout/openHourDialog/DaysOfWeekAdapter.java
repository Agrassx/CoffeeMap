package layout.openHourDialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.agrass.coffeemap.R;
@Deprecated
public class DaysOfWeekAdapter extends ArrayAdapter<Day> implements AdapterView.OnItemClickListener {

    Day[] weekDays = null;
    Context context;

    public DaysOfWeekAdapter(Context context, Day[] resource) {
        super(context, R.layout.row_days_of_the_week, resource);
        this.context = context;
        this.weekDays = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_days_of_the_week, parent, false);
        CheckBox row = (CheckBox) convertView.findViewById(R.id.rowDayCheckBox);
        TextView name = (TextView) convertView.findViewById(R.id.dayName);

        name.setText(weekDays[position].getName());

        if(weekDays[position].isChecked()) {
            row.setChecked(true);
        } else {
            row.setChecked(false);
        }

        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (weekDays[position].isChecked()) {
            weekDays[position].setChecked(false);
            Toast.makeText(context, String.valueOf(weekDays[position].isChecked()), Toast.LENGTH_LONG).show();
        } else {
            weekDays[position].setChecked(true);
            Toast.makeText(context, String.valueOf(weekDays[position].isChecked()), Toast.LENGTH_LONG).show();
        }
    }
}

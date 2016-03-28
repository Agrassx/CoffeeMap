package layout.openHourDialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.agrass.coffeemap.R;

public class SetOpenHoursFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Day[] days;

    private OnFragmentInteractionListener mListener;

    public SetOpenHoursFragment() {
        // Required empty public constructor
    }

    public static SetOpenHoursFragment newInstance(String param1, String param2) {
        SetOpenHoursFragment fragment = new SetOpenHoursFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_set_open_hours, null);

        final ListView weekDaysList = (ListView) view.findViewById(R.id.listView);

        final EditText editOpenHour = (EditText) view.findViewById(R.id.editOpenHours);
        final EditText editCloseHour = (EditText) view.findViewById(R.id.editCloseHours);

        editOpenHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                TimePicker timePicker = new TimePicker();
                timePicker.setOnTimePickedListener(new TimePickedListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        editOpenHour.setText(String.format("%s:%s", hour, minute));
                    }
                });
                timePicker.show(getFragmentManager(),"timePickerHour");
            }
        });

        editCloseHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                TimePicker timePicker = new TimePicker();
                timePicker.setOnTimePickedListener(new TimePickedListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        editCloseHour.setText(String.format("%s:%s", hour, minute));
                    }
                });
                timePicker.show(getFragmentManager(),"timePickerHour");
            }
        });

        days = new Day[7];
        days[0] = new Day("Понедельник", false);
        days[1] = new Day("Вторник", false);
        days[2] = new Day("Среда", false);
        days[3] = new Day("Четверг", false);
        days[4] = new Day("Пятница", false);
        days[5] = new Day("Суббота", false);
        days[6] = new Day("Воскресенье", false);

        final DaysOfWeekAdapter weekAdapter = new DaysOfWeekAdapter(getActivity(), days);

        weekDaysList.setAdapter(weekAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Режим работы");
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Day cc = (Day) weekDaysList.getAdapter().getItem(0);

                Toast.makeText(getActivity(), String.valueOf(cc.isChecked()), Toast.LENGTH_LONG).show();
            }
        });
        return builder.create();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void hideSoftKeyboard(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}

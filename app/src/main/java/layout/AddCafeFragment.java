package layout;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.agrass.coffeemap.R;

import layout.openHourDialog.SetOpenHoursFragment;

public class AddCafeFragment extends Fragment {

    private String cafeName;
    private String openHours;
    private float userRating;
    private String comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_cafe, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final FloatingActionButton saveButton = (FloatingActionButton) getView().findViewById(R.id.buttonSavePoint);
        final RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.ratingBar);
        final RadioGroup radioGroupOpenHours = (RadioGroup) getView().findViewById(R.id.radioGroupOpenHours);
        final EditText editCafeName = (EditText) getView().findViewById(R.id.editTextCafeName);
        final EditText editComment = (EditText) getView().findViewById(R.id.editTextComment);

        radioGroupOpenHours.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButAlwaysOpen:
                        openHours = "24/7";
                        break;
                    case R.id.radioButSecond:
                        openHours = "09:00-20:00";
                        break;
                    case R.id.radioButAnother:
                        getOpenHours();
                        break;
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userRating = rating;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNewPlace(editCafeName.getText().toString(), openHours, userRating, editComment.getText().toString());
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();

            }
        });

    }

    private void getOpenHours() {
        DialogFragment dialog = new SetOpenHoursFragment();
        dialog.show(getFragmentManager(), "openHours");
//        TODO: get data from dialog
    }

    private void postNewPlace(String cafeName, String openHours, float rating, String comment) {
        checkFields();
//        TODO: post data about new place to server
    }

    private void checkFields() {
//        TODO: check fields cafeName, rating bar and comment
    }


}

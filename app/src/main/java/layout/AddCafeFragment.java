package layout;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agrass.coffeemap.R;

public class AddCafeFragment extends Fragment {


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

//        TextView textView = (TextView) getActivity().findViewById(R.id.exampleText);
//        textView.setText(
//                String.format("Latitude: %f \n Longitude: %f ",
//                        getArguments().getDouble("Latitude"),
//                        getArguments().getDouble("Longitude")
//                )
//        );

    }

}

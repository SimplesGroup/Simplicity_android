package simplicity_an.simplicity_an.Tamil.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 2/25/2016.
 */
public class TamilFragmentFixures extends Fragment {
    public TamilFragmentFixures() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sports_fixtures, container, false);

        return view;
    }
}

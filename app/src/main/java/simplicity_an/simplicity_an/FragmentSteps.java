package simplicity_an.simplicity_an;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by kuppusamy on 1/30/2016.
 */
public class FragmentSteps extends Fragment {
    WebView stepsdescription;
    public FragmentSteps() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsteps, container, false);
        stepsdescription=(WebView)view.findViewById(R.id.webView_steps);
        Intent get=getActivity().getIntent();
        String ingredients=get.getStringExtra("STEPS");
        String ss=ingredients;

        // Toast.makeText(Second.this,ss,Toast.LENGTH_LONG).show();


        String s=ss;
        // s = s.replace("\"", "'");
        s = s.replace("\\", "");
        stepsdescription.loadData(s, "text/html; charset=UTF-8", null);
        stepsdescription.setBackgroundColor(0x054A99);
        return view;
    }
}

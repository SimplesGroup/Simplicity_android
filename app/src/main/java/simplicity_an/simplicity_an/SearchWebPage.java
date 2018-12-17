package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import simplicity_an.simplicity_an.MainEnglish.CityFragment;

public class SearchWebPage extends Fragment {
    SharedPreferences sharedpreferences;
    String myuserids;
    public static final String GcmId = "gcmid";
    public static final String MYUSERID = "myprofileid";
    public static final String mypreference = "mypref";
    private final String TAG_REQUEST = "MY_TAG";
    String url_notification_count_valueget,myprofileid;
    WebView search;
    ProgressDialog pdialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static SearchWebPage newInstance() {
        SearchWebPage fragment = new SearchWebPage();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchweb_layout, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            Log.e("MUUSERID:", myprofileid);
        }
       /* Intent get=getIntent();
        search_valueone=get.getStringExtra("IDSEARCH");
        search_value = get.getStringExtra("QUERY");*/
        search = (WebView) view.findViewById(R.id.textView_desc);


        search.getSettings().setJavaScriptEnabled(true);
        search.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        search.loadUrl("https://simplicity.in/app/home.php");
        search.setWebViewClient(new MyBrowser());
        pdialog = new ProgressDialog(getActivity());
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (search.canGoBack()) {
                        search.goBack();
                       // Dlog.d(“canGoBack”);
                    } else {
                      //  Dlog.d(“canNotGoBack”);
                        (getActivity()).onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pdialog.dismiss();
            // ((EditText) getActionBar().getCustomView().findViewById(R.id.editText)).setText(url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("http://simpli")){
                Intent ads=new Intent(getContext().getApplicationContext(),AdvertisementPage.class);
                ads.putExtra("ID",url);
                startActivity(ads);
            }else {
                Intent ads=new Intent(getContext().getApplicationContext(),AdvertisementPage.class);                 ads.putExtra("ID",url);                 startActivity(ads);
                // view.loadUrl(url);
            }
            //

            return true;
        }
    }


    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event){
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if(search.canGoBack()){
                        search.goBack();
                    }/*else{
                        getActivity().finish();
                    }*/
                    return true;
            }
        }
        return false;
    }

}
package simplicity_an.simplicity_an.MainEnglish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.PrefManager;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SimplicitySearchview;
import simplicity_an.simplicity_an.TabColumns;
import simplicity_an.simplicity_an.TabEducation;
import simplicity_an.simplicity_an.TabFarming;
import simplicity_an.simplicity_an.TabFood;
import simplicity_an.simplicity_an.TabHealth;
import simplicity_an.simplicity_an.TabScience;
import simplicity_an.simplicity_an.TabTravel;
import simplicity_an.simplicity_an.Tab_All;
import simplicity_an.simplicity_an.Tab_new_news;
import simplicity_an.simplicity_an.Tabgovt;
import simplicity_an.simplicity_an.Tabjobs;
import simplicity_an.simplicity_an.Tabsports;
import simplicity_an.simplicity_an.Utils.ChangeFont;
import simplicity_an.simplicity_an.Utils.Configurl;
import simplicity_an.simplicity_an.Utils.Fonts;

/**
 * Created by kuppusamy on 5/18/2017.
 */

public class CityFragment extends Fragment {
    TextView notification_batge_count,date_text,weather_update,title_coimbatore,language_title,weathercentre_textview;
    RequestQueue requestQueue;
    SharedPreferences sharedpreferences;
    int cDay,cMonth,cYear,cHour,cMinute,cSecond;
    String selectedMonth,selectedYear;
    String URLPOSTQTYPE,postid;
    private PrefManager prefManager;
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    public static final String FONT= "font";
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    String WEATHER_URL="https://simplicity.in/request2.php?rtype=weather&key=simples";
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_change_lang="http://simpli-city.in/request2.php?rtype=updatelanguage&key=simples";
    String playerid;
    String url_notification_count_valueget,myprofileid,weather_degree_value,notification_counts;
    int value;
    public static final String Activity = "activity";
    public static final String backgroundcolor = "color";
    public static final String Language = "lamguage";
    public static final String CONTENTID = "contentid";
    ImageButton beyond,search,explore,notifications,themechange_button,settings_button;
    ImageView font_button;
    String activity,contentid,colorcodes;
Button btnspecials,btnevents,btnmore;
ImageView btnsearch;
Button city;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ViewPager mPager;
    private HealthViewPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    final Context context = getActivity();
    RelativeLayout mainlayout;
    NetworkImageView specialday_image;
    public static final String GcmId = "gcmid";
    String specialdayurl="http://simpli-city.in/request2.php?rtype=specialday&key=simples&language=1";
    ImageLoader mImageLoader;

    FloatingActionButton fabplus,fabup,fabsearch,fabinnerplus;
    String weatherdata=null;

    LinearLayout layout;
    private Boolean isFabOpen = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    String dayOfTheWeek;
    String sMonthNamefull;
    String tab_id;
    String fontname;
    Typeface tf_play,tf;
    TextView line_vertical_textview,line_settings_vertical_textview;
    RelativeLayout main_english_layout;
    public static CityFragment newInstance() {
        CityFragment fragment = new CityFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mainactivityversiontwo,container,false);
        city=(Button) getActivity().findViewById(R.id.btn_news);
        btnspecials=(Button)getActivity().findViewById(R.id.btn_specials);
        btnevents = (Button)getActivity().findViewById(R.id.btn_events);
        btnsearch = (ImageView) getActivity().findViewById(R.id.btn_citys);
        btnmore = (Button)getActivity().findViewById(R.id.btn_shop);

 font_button=(ImageView) view.findViewById(R.id.fontbutton);
settings_button=(ImageButton)view.findViewById(R.id.settingsbutton) ;
main_english_layout=(RelativeLayout)getActivity().findViewById(R.id.mainenglish);
        sharedpreferences = getActivity(). getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                Log.e("ColorCodes",userId);
                SharedPreferences.Editor gcmidseditor=sharedpreferences.edit();
                gcmidseditor.putString(GcmId,userId);
                gcmidseditor.commit();
                playerid=userId;
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

            }
        });
Intent get=getActivity().getIntent();
tab_id=get.getStringExtra("TAB");
        requestQueue= Volley.newRequestQueue(getActivity());

        // prefManager = new PrefManager(getActivity().getApplicationContext());
        contentid=sharedpreferences.getString(CONTENTID,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(FONT,"");
        //   colorcodes = colorcodes.replaceAll("\\D+","");
        Log.e("ColorCodes",colorcodes+"hihi");
        activity=sharedpreferences.getString(Activity,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }

        url_notification_count_valueget=url_noti_count+myprofileid;
        URLPOSTQTYPE=urlpost;
        if(activity.equalsIgnoreCase("mainversion")){

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove(Activity);
            editor.remove(CONTENTID);
            editor.apply();

        }

        fabplus=(FloatingActionButton)view.findViewById(R.id.fabButtonplus) ;
        fabsearch=(FloatingActionButton)view.findViewById(R.id.fabsearch) ;
        fabinnerplus=(FloatingActionButton)view.findViewById(R.id.fabinnerplus) ;
        search=(ImageButton)view.findViewById(R.id.searchbutton);
        search.setVisibility(View.GONE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchpage=new Intent(getActivity(), SimplicitySearchview.class);
                startActivity(searchpage);
            }
        });

line_vertical_textview=(TextView)view.findViewById(R.id.linevertical_versiontwo);
     line_vertical_textview.setText(Html.fromHtml("&nbsp;"+"|"+"&nbsp;"));
        line_settings_vertical_textview=(TextView) view.findViewById(R.id.linevertical_settings_versiontwo);
        line_settings_vertical_textview.setText(Html.fromHtml("&nbsp;"+"|"+"&nbsp;"));
settings_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Fragment fragment = new SettingsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        btnevents.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        btnspecials.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        btnmore.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        city.setTextColor(Color.parseColor("#666666"));
        btnevents.setTextColor(Color.parseColor("#666666"));
        btnspecials.setTextColor(Color.parseColor("#666666"));
        btnmore.setTextColor(Color.parseColor("#666666"));
    }
});
       /* if(fontname.equals("sanfrancisco")){
            String playfair ="fonts/Oxygen-Bold.ttf";
            tf_play = Typeface.createFromAsset(getActivity().getAssets(), playfair);
            String simplycity_title_bold = "fonts/SystemSanFranciscoDisplayRegular.ttf";
            tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_bold);
            city.setTypeface(tf);
            btnevents.setTypeface(tf);
            btnspecials.setTypeface(tf);
            btnmore.setTypeface(tf);
        }else {
            String playfair = "fonts/playfairDisplayRegular.ttf";
            tf_play = Typeface.createFromAsset(getActivity().getAssets(), playfair);
            String simplycity_title_bold = "fonts/Lora-Regular.ttf";
            tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_bold);
            city.setTypeface(tf_play);
            btnevents.setTypeface(tf_play);
            btnspecials.setTypeface(tf_play);
            btnmore.setTypeface(tf_play);
        }
*/



        mainlayout=(RelativeLayout)view.findViewById(R.id.version_main_layout);





       if(colorcodes!=null) {
            if (colorcodes.equals("#FFFFFFFF")) {
                int[] colors = {Color.parseColor(colorcodes),  Color.parseColor("#FFFFFFFF")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);


            } else {
                int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);
                // city.setBackgroundColor(getResources().getColor(R.color.theme1button));
                fabplus.setBackgroundResource(R.color.theme1button);
                fabinnerplus.setBackgroundResource(R.color.theme1button);
                fabsearch.setBackgroundResource(R.color.theme1button);
                SharedPreferences.Editor editor = sharedpreferences.edit();
               editor.putString(backgroundcolor, "#262626");

                editor.commit();

            }
        }else{
           int[] colors = {Color.parseColor("#262626"),  Color.parseColor("#00000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);

            mainlayout.setBackgroundDrawable(gd);

            fabplus.setBackgroundResource(R.color.theme1button);
            fabinnerplus.setBackgroundResource(R.color.theme1button);
            fabsearch.setBackgroundResource(R.color.theme1button);
            SharedPreferences.Editor editor = sharedpreferences.edit();
           editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }



        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
        String simplycity_title_reugular= "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_reugular);
        String simplycity_title= "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);
        title_coimbatore=(TextView)view.findViewById(R.id.title_versiontwo) ;

        //title_coimbatore.setTypeface(tf_pala);

        title_coimbatore.setText("Vanakkam Kovai");
        if(colorcodes.equals("#FFFFFFFF"))
        {
            title_coimbatore.setTextColor(Color.BLACK);
            line_vertical_textview.setTextColor(Color.BLACK);
            line_settings_vertical_textview.setTextColor(Color.BLACK);
        }
        else
        {
            title_coimbatore.setTextColor(Color.WHITE);
            line_vertical_textview.setTextColor(Color.WHITE);
            line_settings_vertical_textview.setTextColor(Color.WHITE);
        }
        if(colorcodes.equals("#FFFFFFFF")){
            /*city.setBackgroundColor(getResources().getColor(R.color.theme13));
            btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
            btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
            btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
            btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
            /*city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
            city.setTextColor(Color.BLACK);
            btnevents.setTextColor(Color.parseColor("#666666"));
            btnspecials.setTextColor(Color.parseColor("#666666"));
            btnmore.setTextColor(Color.parseColor("#666666"));
        }
        else{

            if(colorcodes.equals("#262626")) {
                /*city.setBackgroundColor(getResources().getColor(R.color.theme1button));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
                /*btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#59247c")) {
                /*city.setBackgroundColor(getResources().getColor(R.color.theme2));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
               /* btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#1d487a")) {
              /*  city.setBackgroundColor(getResources().getColor(R.color.theme3));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
                /*btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#7A4100")) {
               /* city.setBackgroundColor(getResources().getColor(R.color.theme4));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
                /*btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#6E0138")) {
               /* city.setBackgroundColor(getResources().getColor(R.color.theme5));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
               /* btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#00BFD4")) {
              /*  city.setBackgroundColor(getResources().getColor(R.color.theme6));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
                /*btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#185546")) {
                /*city.setBackgroundColor(getResources().getColor(R.color.theme7));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
               /* btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#D0A06F")) {
                /*city.setBackgroundColor(getResources().getColor(R.color.theme8));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
               /* btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#82C6E6")) {
                /*city.setBackgroundColor(getResources().getColor(R.color.theme9));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
               /* btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#339900")) {
               /* city.setBackgroundColor(getResources().getColor(R.color.theme10));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
                /*btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#CC9C00")) {
               /* city.setBackgroundColor(getResources().getColor(R.color.theme11));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
                /*btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
            else if(colorcodes.equals("#00B09B")) {
               /* city.setBackgroundColor(getResources().getColor(R.color.theme12));
                btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                btnsearch.setBackgroundColor(getResources().getColor(R.color.mytransparent));*/
               /* btnsearch.setImageResource(R.mipmap.search);
                btnevents.setImageResource(R.mipmap.events);
                btnmore.setImageResource(R.mipmap.more);
                btnspecials.setImageResource(R.mipmap.specials);*/
                city.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#666666"));
                btnspecials.setTextColor(Color.parseColor("#666666"));
                btnmore.setTextColor(Color.parseColor("#666666"));
            }
        }
        layout = (LinearLayout)view. findViewById(R.id.title);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)layout.getLayoutParams();
        params.setMargins(0, 180, 0, 0);
        layout.setLayoutParams(params);
        specialday_image=(NetworkImageView)view.findViewById(R.id.special_day_images);
        if (mImageLoader == null)
            mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(getActivity()).getImageLoader();
        StringRequest special=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
Log.e("RES",response.toString());

                        JSONObject object=new JSONObject(response.toString());
                        JSONArray array=object.getJSONArray("result");

                    for (int k=0;k<array.length();k++) {
                        if (array.length() > 0) {


                            JSONObject obj = (JSONObject) array.get(k);
                            ItemModel model = new ItemModel();
                            model.setImage_special(obj.getString("doodle_image"));
                            model.setSpecialdaytitle(obj.getString("doodle_title"));
                            if (obj.getString("doodle").equals("no")) {
                                title_coimbatore.setText("Vanakkam Kovai");
                                layout = (LinearLayout) getActivity().findViewById(R.id.title);
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                                params.setMargins(0, 160, 0, 0);
                                layout.setLayoutParams(params);
                            } else {
                                // model.setHeight(obj.getInt("height"));
                                model.setWidth(obj.getInt("doodle_width"));
                                if (obj.getString("doodle_title") != null) {
                                    title_coimbatore.setText(obj.getString("doodle_title"));

                                } else {
                                    title_coimbatore.setText("Vanakkam Kovai");
                                    layout = (LinearLayout) getActivity().findViewById(R.id.title);
                                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                                    params.setMargins(0, 160, 0, 0);
                                    layout.setLayoutParams(params);
                                }
                                if (obj.getString("doodle_image") != null) {
                                    final float scale = getActivity().getResources().getDisplayMetrics().density;
                                    int pixels = (int) (obj.getInt("doodle_height") * scale + 0.5f);
                                    int width = (int) (obj.getInt("doodle_width") * scale + 0.5f);
                                    Log.e("Dp", String.valueOf(pixels) + "," + String.valueOf(width));
                     /*  specialday_image.getLayoutParams().height=obj.getInt("height");
                        specialday_image.getLayoutParams().width=obj.getInt("width");*/
                                    specialday_image.getLayoutParams().height = pixels;
                                    specialday_image.getLayoutParams().width = width;
                                    specialday_image.setVisibility(View.VISIBLE);
                                    specialday_image.setImageUrl(obj.getString("doodle_image"), mImageLoader);
                                } else {
                                    specialday_image.setVisibility(View.GONE);

                                }
                            }
                        }
                    }
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();
                param.put("Key","Simplicity");
                param.put("Token","8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("rtype","playerid_new");
                param.put("player_id",playerid);
                param.put("language","1");
                param.put("phone","android");

                return param;
            }
        };
        special.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(special);

        weather_update=(TextView)view. findViewById(R.id.weather_degree_versiontwo);
        weathercentre_textview=(TextView)view.findViewById(R.id.weathercenter) ;
        weathercentre_textview.setText(Html.fromHtml("&nbsp;"+"|"+"&nbsp;"));


        Calendar calander = Calendar.getInstance();
        cDay = calander.get(Calendar.DAY_OF_MONTH);
        cMonth = calander.get(Calendar.MONTH) + 1;
        cYear = calander.get(Calendar.YEAR);
        selectedMonth = "" + cMonth;
        selectedYear = "" + cYear;
        cHour = calander.get(Calendar.HOUR);
        cMinute = calander.get(Calendar.MINUTE);
        cSecond = calander.get(Calendar.SECOND);
        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM");
        Date d1 = new Date();
        String sMonthName =  sdf1.format(d1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 1);
        int mon=calander.get(Calendar.DAY_OF_MONTH);
        final SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        Date d1full = new Date();
         sMonthNamefull =  fmt.format(d1full);
        Log.e("DAY_OF_MONTH: ", "DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH)+sMonthName);

        date_text=(TextView)view.findViewById(R.id.date_versiontwo) ;

        themechange_button=(ImageButton)view.findViewById(R.id.themebutton);
        language_title=(TextView)view.findViewById(R.id.language_versiontwo) ;
        //language_title.setTypeface(tf);

        // title_coimbatore.setTypeface(tf);
        date_text.setTypeface(tf);
        weather_update.setTypeface(tf);
        language_title.setText(Html.fromHtml("&nbsp;"+"<a>தமிழ்</a>"+"&nbsp;"+"|"+"&nbsp;"));

        if(colorcodes.equals("#FFFFFFFF"))
        {
            language_title.setTextColor(Color.BLACK);
        }
        else{
            language_title.setTextColor(Color.WHITE);
        }

        if(colorcodes.equals("#FFFFFFFF"))
        {
            date_text.setTextColor(Color.BLACK);

        }
        else{
            date_text.setTextColor(Color.WHITE);
        }

        if(colorcodes.equals("#FFFFFFFF"))
        {
            weather_update.setTextColor(Color.BLACK);

        }
        else{
            weather_update.setTextColor(Color.WHITE);
        }
        if(fontname.equals("playfair")){
            title_coimbatore.setTypeface(tf_pala);
            language_title.setTypeface(tf_pala);
            date_text.setTypeface(tf_pala);
            line_vertical_textview.setTypeface(tf_pala);
        }else {
            Typeface sanf=Typeface.createFromAsset(getActivity().getAssets(),Fonts.sanfranciscobold);
            title_coimbatore.setTypeface(sanf);
            title_coimbatore.setTextSize(30);
            date_text.setTypeface(sanf);
            language_title.setTypeface(sanf);
            line_vertical_textview.setTypeface(sanf);

        }
        StringRequest weather=new StringRequest(Request.Method.GET, WEATHER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


               /* if(response!=null){
                    weather_update.setText(Html.fromHtml(response+"<sup>o</sup>"));
                }else {
                    weather_update.setVisibility(View.GONE);
                }
*/
                weatherdata=response.toString();
                Log.e("WEATHER",response+",,"+weatherdata);
                date_text.setText(Html.fromHtml(dayOfTheWeek+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        weather.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(weather);
if(weatherdata!=null){
    date_text.setText(Html.fromHtml(dayOfTheWeek+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));

}else {
    date_text.setText(Html.fromHtml(dayOfTheWeek+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));

}
        if(colorcodes.equals("#FFFFFFFF")){
            themechange_button.setImageResource(R.drawable.themenormal);
            settings_button.setImageResource(R.drawable.settingsblack);
            if(fontname.equals("playfair")){
                font_button.setImageResource(R.mipmap.sanblack);
            }else {
                font_button.setImageResource(R.mipmap.playfairblack);

            }

        }else {
            themechange_button.setImageResource(R.drawable.themewhite);
            settings_button.setImageResource(R.drawable.settingswhite);
            if(fontname.equals("playfair")){
                font_button.setImageResource(R.mipmap.sanwhite);
            }else {
                font_button.setImageResource(R.mipmap.playfairwhite);

            }

        }






    /*if(dayOfTheWeek.equalsIgnoreCase("Sun")){
        date_text.setText(Html.fromHtml("Sun"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"&#9729;"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"+"&nbsp;"+"<b>தமிழ்</b>"+"&nbsp;"+"|"+"&nbsp;"));
    }else if(dayOfTheWeek.equalsIgnoreCase("Mon")){
        date_text.setText(Html.fromHtml("Mon"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"&#9729;"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"+"&nbsp;"+"<b>தமிழ்</b>"+"&nbsp;"+"|"+"&nbsp;"));
    }else if(dayOfTheWeek.equalsIgnoreCase("Tue")){
        date_text.setText(Html.fromHtml("Tue"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"&#9729;"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"+"&nbsp;"+"<b>தமிழ்</b>"+"&nbsp;"+"|"+"&nbsp;"));
    }else if (dayOfTheWeek.equalsIgnoreCase("Wed")){
        date_text.setText(Html.fromHtml("Wed"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"&#9729;"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"+"&nbsp;"+"<b>தமிழ்</b>"+"&nbsp;"+"|"+"&nbsp;"));
    }else if(dayOfTheWeek.equalsIgnoreCase("Thu")){
        date_text.setText(Html.fromHtml("Thu"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"&#9729;"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"+"&nbsp;"+"<b>தமிழ்</b>"+"&nbsp;"+"|"+"&nbsp;"));
    }else if(dayOfTheWeek.equalsIgnoreCase("Fri")){
        date_text.setText(Html.fromHtml("Fri"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"&#9729;"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"+"&nbsp;"+"<b>தமிழ்</b>"+"&nbsp;"+"|"+"&nbsp;"));
    }else if(dayOfTheWeek.equalsIgnoreCase("Sat")){
        date_text.setText(Html.fromHtml("Sat"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"&#9729;"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"+"&nbsp;"+"<b>தமிழ்</b>"+"&nbsp;"+"|"+"&nbsp;"));
    }*/




       /* beyond=(ImageButton)view.findViewById(R.id.btn_versiontwobeyond);
        search=(ImageButton)view.findViewById(R.id.btn_versiontwosearch);
        explore=(ImageButton)view.findViewById(R.id.btn_versiontwoexplore);
        notifications=(ImageButton)view.findViewById(R.id.btn_versiontwonotifications);
*/


        if(colorcodes.equalsIgnoreCase("#262626")){
           //city.setBackgroundColor(getResources().getColor(R.color.theme1button));;
            fabplus.setBackgroundResource(R.color.theme1button);
            fabinnerplus.setBackgroundResource(R.color.theme1button);
            fabsearch.setBackgroundResource(R.color.theme1button);
        }else if(colorcodes.equalsIgnoreCase("#59247c")){
           //city.setBackgroundColor(getResources().getColor(R.color.theme2));
            fabplus.setBackgroundResource(R.color.theme2);
            fabinnerplus.setBackgroundResource(R.color.theme2);
            fabsearch.setBackgroundResource(R.color.theme2);
        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
          // city.setBackgroundColor(getResources().getColor(R.color.theme3));
            fabplus.setBackgroundResource(R.color.theme3);
            fabinnerplus.setBackgroundResource(R.color.theme3);
            fabsearch.setBackgroundResource(R.color.theme3);
        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
          // city.setBackgroundColor(getResources().getColor(R.color.theme4));
            fabplus.setBackgroundResource(R.color.theme4);
            fabinnerplus.setBackgroundResource(R.color.theme4);
            fabsearch.setBackgroundResource(R.color.theme4);
        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
          // city.setBackgroundColor(getResources().getColor(R.color.theme5));
            fabplus.setBackgroundResource(R.color.theme5);
            fabinnerplus.setBackgroundResource(R.color.theme5);
            fabsearch.setBackgroundResource(R.color.theme5);
        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
         //  city.setBackgroundColor(getResources().getColor(R.color.theme6));
            fabplus.setBackgroundResource(R.color.theme6);
            fabinnerplus.setBackgroundResource(R.color.theme6);
            fabsearch.setBackgroundResource(R.color.theme6);
        }else if(colorcodes.equalsIgnoreCase("#185546")){
          //  city.setBackgroundColor(getResources().getColor(R.color.theme7));
            fabplus.setBackgroundResource(R.color.theme7);
            fabinnerplus.setBackgroundResource(R.color.theme7);
            fabsearch.setBackgroundResource(R.color.theme7);
        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
        //  city.setBackgroundColor(getResources().getColor(R.color.theme8));
            fabplus.setBackgroundResource(R.color.theme8);
            fabinnerplus.setBackgroundResource(R.color.theme8);
            fabsearch.setBackgroundResource(R.color.theme8);
        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
         //   city.setBackgroundColor(getResources().getColor(R.color.theme9));
            fabplus.setBackgroundResource(R.color.theme9);
            fabinnerplus.setBackgroundResource(R.color.theme9);
            fabsearch.setBackgroundResource(R.color.theme9);
        }else if(colorcodes.equalsIgnoreCase("#339900")){
         //  city.setBackgroundColor(getResources().getColor(R.color.theme10));
            fabplus.setBackgroundResource(R.color.theme10);
            fabinnerplus.setBackgroundResource(R.color.theme10);
            fabsearch.setBackgroundResource(R.color.theme10);
        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
        //   city.setBackgroundColor(getResources().getColor(R.color.theme11));
            fabplus.setBackgroundResource(R.color.theme11);
            fabinnerplus.setBackgroundResource(R.color.theme11);
            fabsearch.setBackgroundResource(R.color.theme11);
        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
         //  city.setBackgroundColor(getResources().getColor(R.color.theme12));
            fabplus.setBackgroundResource(R.color.theme12);
            fabinnerplus.setBackgroundResource(R.color.theme12);
            fabsearch.setBackgroundResource(R.color.theme12);
        }
        else if(colorcodes.equalsIgnoreCase("#FFFFFFFF")){
         //  city.setBackgroundColor(getResources().getColor(R.color.theme13));
            fabplus.setBackgroundResource(R.color.theme13);
            fabinnerplus.setBackgroundResource(R.color.theme13);
            fabsearch.setBackgroundResource(R.color.theme13);
        }

        language_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Language, "Tamil");

                editor.commit();
                StringRequest language=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
Log.e("CHANGE LAMG",response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String>params=new Hashtable<String, String>();
                        params.put("Key","Simplicity");
                        params.put("Token","8d83cef3923ec6e4468db1b287ad3fa7");
                        params.put("rtype","playerid_new");
                        params.put("phone","android");
                        params.put("player_id",playerid);
                        params.put("language","1");
                        return params;
                    }
                };
                RequestQueue likesqueue=Volley.newRequestQueue(getActivity());
                language.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                likesqueue.add(language);
                // getActivity().overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
                Intent tamil=new Intent(getActivity(),MainPageTamil.class);
                startActivity(tamil);
            }
        });



        font_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontname=sharedpreferences.getString(FONT,"");
                if(fontname.equals("playfair")){
                    Fonts fonts=new Fonts();

                    Typeface tf=fonts.font("sanfrancisco",getActivity());
                    Typeface tf1=fonts.font1(getActivity());
                    if(colorcodes.equals("#FFFFFFFF")){
                        font_button.setImageResource(R.mipmap.playfairblack);
                    }else {
                        font_button.setImageResource(R.mipmap.playfairwhite);

                    }
                    SharedPreferences.Editor editor=sharedpreferences.edit();
                    editor.putString(FONT,"sanfrancisco");
                    editor.commit();
                    title_coimbatore.setTypeface(tf);
                    title_coimbatore.setTextSize(30);


ChangeFont new_news=new Tab_new_news();
            new_news.change();
            new_news=new Tab_All();
            new_news.change();
                   /* MainPageEnglish english=new MainPageEnglish();
                    english.Changefont("sanfrancisco",getActivity());*/
                  /*  ChangeFont   column=new TabColumns();
                    column.change();
                    ChangeFont education=new TabEducation();
                    education.change();*/
                    /*new_news=new TabEntertainmentAll();
                    new_news.change();
                    new_news=new TabEntertainmentMusic();
                    new_news.change();
                    new_news=new TabEntertainmentTheatre();
                    new_news.change();
                    new_news=new TabEntertainmentTheatre();
                    new_news.change();
                    new_news=new Tabevent();
                    new_news.change();
                    new_news=new Tabeventtoday();
                    new_news.change();
                    new_news=new Tabeventtomorrow();
                    new_news.change();*/
                   /* new_news=new TabFarming();
                    new_news.change();
                    new_news=new TabFood();
                    new_news.change();
                    new_news=new Tabgovt();
                    new_news.change();
                    new_news=new TabHealth();
                    new_news.change();
                    new_news=new Tabjobs();
                    new_news.change();
                    new_news=new Tabphotostories();
                    new_news.change();
                    new_news=new TabScience();
                    new_news.change();
                    new_news=new Tabsports();
                    new_news.change();
                    new_news=new TabTravel();
                    new_news.change();*/
                    setupTabIcons();
                  /*  new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MainPageEnglish english=new MainPageEnglish();
                            english.Changefont("sanfrancisco",getActivity());
                        }
                    },1000);*/

                }else  {
                    Fonts fonts=new Fonts();

                    Typeface tf=fonts.font("playfair",getActivity());


                    SharedPreferences.Editor editor=sharedpreferences.edit();
                    editor.putString(FONT,"playfair");
                    editor.commit();
                    title_coimbatore.setTypeface(tf);
                    title_coimbatore.setTextSize(40);

                    if(colorcodes.equals("#FFFFFFFF")){
                        font_button.setImageResource(R.mipmap.sanblack);
                    }else {
                        font_button.setImageResource(R.mipmap.sanwhite);

                    }
                    ChangeFont chfont=new Tab_new_news();
                    chfont.change();
                    chfont=new Tab_All();
                    chfont.change();
                   /* MainPageEnglish english=new MainPageEnglish();
                    english.Changefont("playfair",getActivity());*/
                    setupTabIcons();
                   /* chfont=new Tabarticle();
                    chfont.change();*/
                    /*new_news=new Tabcolumnist();
                    new_news.change();
                    new_news=new TabColumns();
                    new_news.change();
                    new_news=new TabEducation();
                    new_news.change();
                    new_news=new TabEntertainmentAll();
                    new_news.change();
                    new_news=new TabEntertainmentMusic();
                    new_news.change();
                    new_news=new TabEntertainmentTheatre();
                    new_news.change();
                    new_news=new TabEntertainmentTheatre();
                    new_news.change();
                    new_news=new Tabevent();
                    new_news.change();
                    new_news=new Tabeventtoday();
                    new_news.change();
                    new_news=new Tabeventtomorrow();
                    new_news.change();
                    new_news=new TabFarming();
                    new_news.change();
                    new_news=new TabFood();
                    new_news.change();
                    new_news=new Tabgovt();
                    new_news.change();
                    new_news=new TabHealth();
                    new_news.change();
                    new_news=new Tabjobs();
                    new_news.change();
                    new_news=new Tabphotostories();
                    new_news.change();
                    new_news=new TabScience();
                    new_news.change();
                    new_news=new Tabsports();
                    new_news.change();
                    new_news=new TabTravel();
                    new_news.change();
                    setupTabIcons();*/

                }
            }
        });



        themechange_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(colorcodes.equals("#FFFFFFFF")){


                 Fragment fragment = new CityFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    int[] colors = { Color.parseColor("#262626"),Color.parseColor("#FF000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);
                    main_english_layout.setBackgroundColor(Color.parseColor("#FF000000"));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                   editor.putString(backgroundcolor, "#262626");
                    editor.commit();


                }else if(colorcodes.equals("#262626")) {



                    Fragment fragment = new CityFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    int[] colors = {Color.parseColor("#FFFFFFFF"),  Color.parseColor("#FFFAF6F6")};
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);
                    main_english_layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(backgroundcolor, "#FFFFFFFF");
                    editor.commit();


                }





                // showAlertDialog();
               /* final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.theme);
                dialog.setTitle("Title...");
                ImageButton colorone = (ImageButton) dialog.findViewById(R.id.color);
                ImageButton colortwo = (ImageButton) dialog.findViewById(R.id.color2);
                ImageButton colorthree = (ImageButton) dialog.findViewById(R.id.color3);
                ImageButton colorfour = (ImageButton) dialog.findViewById(R.id.color4);
                ImageButton colorfive = (ImageButton) dialog.findViewById(R.id.color5);
                ImageButton colorsix = (ImageButton) dialog.findViewById(R.id.color6);
                ImageButton colorseven = (ImageButton) dialog.findViewById(R.id.color7);
                ImageButton coloreight= (ImageButton) dialog.findViewById(R.id.color8);
                ImageButton colornine = (ImageButton) dialog.findViewById(R.id.color9);
                ImageButton colorten = (ImageButton) dialog.findViewById(R.id.color10);
                ImageButton coloreleven = (ImageButton) dialog.findViewById(R.id.color11);
                ImageButton colortwelve = (ImageButton) dialog.findViewById(R.id.color12);
                ImageButton colorthirteen = (ImageButton) dialog.findViewById(R.id.color13);
                Button closebutton=(Button)dialog.findViewById(R.id.close_button);
                colorone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                       *//*city.setBackgroundColor(getResources().getColor(R.color.theme1button));

                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*

                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                        //btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme1button));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme1button));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme1button));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                       editor.putString(backgroundcolor, "#262626");
                        editor.commit();
                       // dialog.dismiss();

                    }
                });
                colortwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#59247c"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                       *//* city.setBackgroundColor(getResources().getColor(R.color.theme2));;
                      //  city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*

                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                       // btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#59247c");
                        editor.commit();
                     //   dialog.dismiss();

                    }
                });
                colorthree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#1d487a"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                       *//* city.setBackgroundColor(getResources().getColor(R.color.theme3));
                       // city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
*//*
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                       // btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#1d487a");
                        editor.commit();
                     //   dialog.dismiss();

                    }
                });
                colorfour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#7A4100"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);
                        *//*city.setBackgroundColor(getResources().getColor(R.color.theme4));
                       // city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*

                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                       // btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme4));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme4));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme4));
                        mainlayout.setBackgroundDrawable(gd);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#7A4100");
                        editor.commit();
                       // dialog.dismiss();

                    }
                });
                colorfive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#6E0138"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                        *//*city.setBackgroundColor(getResources().getColor(R.color.theme5));
                       // city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*

                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                        //btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#6E0138");
                        editor.commit();
                       // dialog.dismiss();
                    }
                });
                colorsix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#00BFD4"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                        *//*city.setBackgroundColor(getResources().getColor(R.color.theme6));
                      //  city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
*//*
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                      //  btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#00BFD4");
                        editor.commit();
                     //   dialog.dismiss();
                    }
                });
                colorseven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#185546"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                        *//*city.setBackgroundColor(getResources().getColor(R.color.theme7));
                       // city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
*//*
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                        //btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#185546");
                        editor.commit();
                       // dialog.dismiss();
                    }
                });
                coloreight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#D0A06F"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
//                        city.setBackgroundColor(getResources().getColor(R.color.theme8));
//                      //  city.setImageResource(R.mipmap.news);
//                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
//
//                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                      //  btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme8));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme8));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme8));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#D0A06F");
                        editor.commit();
                       // dialog.dismiss();
                    }
                });
                colornine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#82C6E6"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                        *//*city.setBackgroundColor(getResources().getColor(R.color.theme9));
                       // city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
*//*
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                        //btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme9));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme9));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme9));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#82C6E6");
                        editor.commit();
                       // dialog.dismiss();
                    }
                });
                colorten.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#339900"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                        *//*city.setBackgroundColor(getResources().getColor(R.color.theme10));
                       // city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                 //       btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme10));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme10));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme10));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#339900");
                        editor.commit();
                       // dialog.dismiss();
                    }
                });
                coloreleven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#CC9C00"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                       *//* city.setBackgroundColor(getResources().getColor(R.color.theme11));
                       // city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
*//*
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                      //  btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#CC9C00");
                        editor.commit();
                      //  dialog.dismiss();
                    }
                });
                colortwelve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#00B09B"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};
                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                        *//*city.setBackgroundColor(getResources().getColor(R.color.theme12));
                       // city.setImageResource(R.mipmap.news);
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.mytransparent));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.mytransparent));
*//*
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                    //    btnmore.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#666666"));
                        btnspecials.setTextColor(Color.parseColor("#666666"));
                        btnmore.setTextColor(Color.parseColor("#666666"));

                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme12));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme12));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme12));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#00B09B");
                        editor.commit();
                       // dialog.dismiss();
                    }
                });

                colorthirteen.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new CityFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        int[] colors = {Color.parseColor("#FFFFFFFF"), Color.parseColor("#FFFAF6F6")};
                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                      //  city.setImageResource(R.mipmap.newsone);
                       *//* city.setBackgroundColor(getResources().getColor(R.color.theme13));
                        btnspecials.setBackgroundColor(getResources().getColor(R.color.theme14));

                        btnevents.setBackgroundColor(getResources().getColor(R.color.theme14));
*//*
                        btnsearch.setBackgroundResource(R.color.theme14);
                        btnsearch.setImageResource(R.mipmap.cityfooterlogo);
                     //   btnmore.setBackgroundColor(getResources().getColor(R.color.theme14));
                        city.setTextColor(Color.WHITE);
                        btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                        btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
                        btnmore.setTextColor(Color.parseColor("#CCCCCC"));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme13));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme13));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme13));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#FFFFFFFF");
                        editor.commit();

                       // dialog.dismiss();
                    }
                });


                closebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();*/
            }
        });
        mCoordinator = (CoordinatorLayout) view.findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mAdapter = new HealthViewPagerAdapter(getChildFragmentManager());
        mPager = (ViewPager)view. findViewById(R.id.viewPager);
        //mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(1);


        if(tab_id==null||tab_id.equals("")){

        }else {
            final int pos = 3;
           mPager.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if(tab_id.equals("specialcolumn")){
                        mPager.setCurrentItem(2);
                    }else if(tab_id.equals("beyond")){
                        mPager.setCurrentItem(1);
                    }
                }
            }, 100);



        }
        setupViewPager(mPager);
        //Notice how the Tab Layout links with the Pager Adapter
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
      //  mTabLayout.setOnTabSelectedListener(this);
        //Notice how The Tab Layout adn View Pager object are linked
        mTabLayout.setupWithViewPager(mPager);
        if(colorcodes.equals("#FFFFFFFF")){
            mTabLayout.setSelectedTabIndicatorColor(Color.BLACK);
            mTabLayout.setTabTextColors(Color.parseColor("#666666"),Color.BLACK);
        }
        else{
            mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        }
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("POSOTION",String.valueOf(position));
                // mPager.setCurrentItem(mTabLayout.getPosition());
                //  getActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupTabIcons();

        return view;
    }


    private void setupTabIcons() {

        fontname=sharedpreferences.getString(FONT,"");

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lora-Regular.ttf");
        ViewGroup vg = (ViewGroup) mTabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(tf);
                    ((TextView) tabViewChild).setTextSize(8);
                    if(fontname.equals("sanfrancisco")){
                        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oxygen-Bold.ttf");
                        ((TextView) tabViewChild).setTypeface(tf1);
                    }

                }
            }
        }
    }


    class ItemModel{
        String count,specialdaytitle,image_special;
        int height,width;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getImage_special() {
            return image_special;
        }

        public String getSpecialdaytitle() {
            return specialdaytitle;
        }

        public void setImage_special(String image_special) {
            this.image_special = image_special;
        }

        public void setSpecialdaytitle(String specialdaytitle) {
            this.specialdaytitle = specialdaytitle;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
    private void Uloaddataservernotify(){
        //final ProgressDialog loading = ProgressDialog.show(getApplicationContext(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLPOSTQTYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //  loading.dismiss();
                        //Showing toast message of the response
                        if(s.equalsIgnoreCase("error")) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                        }else {


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                //Getting Image Name


                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                if(myprofileid!=null) {


                    params.put(KEY_QTYPE, "notify");
                    params.put(KEY_POSTID,"1");
                    params.put(KEY_MYUID, myprofileid);


                }
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    private void setupViewPager(ViewPager mPager) {
        HealthViewPagerAdapter adapter = new HealthViewPagerAdapter(getChildFragmentManager());


       /* adapter.addFragment(new Tab_new_news(), "City");
        adapter.addFragment(new Tab_All(), "Beyond");
        adapter.addFragment(new TabColumns(), "Columns");
        adapter.addFragment(new Tabgovt(), "Govt.Notif");
        adapter.addFragment(new Tabjobs(), "Jobs");
        adapter.addFragment(new TabFood(), "Food");
        adapter.addFragment(new TabEducation(), "Education");
        adapter.addFragment(new TabTravel(), "Travels");
        adapter.addFragment(new Tabsports(), "Sports");
        adapter.addFragment(new TabScience(), "Science");
        adapter.addFragment(new TabFarming(), "Farming");
        adapter.addFragment(new TabHealth(), "Health");
        adapter.addFragment(new TabAdvertorial(),"Advertorial");*/

        adapter.addFragment(new Tab_new_news(), "CITY");
        adapter.addFragment(new Tab_All(), "BEYOND");
        adapter.addFragment(new TabColumns(), "COLUMNS");
        adapter.addFragment(new Tabgovt(), "GOVT.NOTIF");
        adapter.addFragment(new Tabjobs(), "JOBS");
        adapter.addFragment(new TabFood(), "FOOD");
        adapter.addFragment(new TabEducation(), "EDUCATION");
        adapter.addFragment(new TabTravel(), "TRAVELS");
        adapter.addFragment(new Tabsports(), "SPORTS");
        adapter.addFragment(new TabScience(), "SCIENCE");
        adapter.addFragment(new TabFarming(), "FARMING");
        adapter.addFragment(new TabHealth(), "HEALTH");
        //adapter.addFragment(new TabAdvertorial(),"Advertorial");

        mPager.setAdapter(adapter);
    }
    class HealthViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public HealthViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }


}

package simplicity_an.simplicity_an.MainTamil;

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
import com.android.volley.toolbox.JsonArrayRequest;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SimplicitySearchview;
import simplicity_an.simplicity_an.Tamil.Activity.Tabphotostoriestamil;
import simplicity_an.simplicity_an.Tamil.TamilEntertainmentall;
import simplicity_an.simplicity_an.Tamil.Tamilentertainmentmovies;
import simplicity_an.simplicity_an.Tamil.Tamilentertainmentradio;
import simplicity_an.simplicity_an.Utils.Fonts;

/**
 * Created by kuppusamy on 5/18/2017.
 */

public class EntertainmentFragmentTamil extends Fragment  {

    TextView date_text,weather_update,title_coimbatore,language_title,weathercentre_textview;
    RequestQueue requestQueue;
    SharedPreferences sharedpreferences;
    int cDay,cMonth,cYear,cHour,cMinute,cSecond;
    String selectedMonth,selectedYear;
    String URLPOSTQTYPE;

    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    String WEATHER_URL="https://simplicity.in/request2.php?rtype=weather&key=simples";
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid,weather_degree_value,notification_counts;
    int value;
    public static final String Activity = "activity";
    public static final String Language = "lamguage";
    public static final String CONTENTID = "contentid";
    ImageButton beyond,more,search,explore,notifications,themechange_button,settings_button;
    String activity,contentid,colorcodes;
    Button btnspecials,btnevents,btnmore,city;
    ImageView btnsearch;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ViewPager mPager;
    private HealthViewPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    final Context context = getActivity();
    RelativeLayout mainlayout;
    public static final String backgroundcolor = "color";
    FloatingActionButton fabsearch,fabinnerplus,fabplus,fabup;
    LinearLayout footerbar;
    private ImageButton play_music,fastforward,backforward,close_player;
    TextView music_title_name;
    String url_change_lang="http://simpli-city.in/request2.php?rtype=updatelanguage&key=simples";
    String playerid;
    LinearLayout layout;
    public static final String GcmId = "gcmid";
    String dayOfTheWeek,sMonthNamefull;
    String fontname;
    Typeface tf1;
     ImageView font_button;
    public static final String FONT= "font";
    RelativeLayout main_tamil_layout;
    TextView line_vertical_textview,line_settings_vertical_textview;
    String      weatherdata;
    public static EntertainmentFragmentTamil newInstance() {
        EntertainmentFragmentTamil fragment = new EntertainmentFragmentTamil();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.entertainmentnewtamil,container,false);
        String simplycity_title_reugular= "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_reugular);

        requestQueue= Volley.newRequestQueue(getActivity());
        sharedpreferences = getActivity(). getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        contentid=sharedpreferences.getString(CONTENTID,"");
        activity=sharedpreferences.getString(Activity,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        font_button=(ImageView) view.findViewById(R.id.fontbutton);
        city=(Button) getActivity().findViewById(R.id.btn_news_tamil);
        btnspecials=(Button)getActivity().findViewById(R.id.btn_specials_tamil);
        btnevents = (Button)getActivity().findViewById(R.id.btn_events_tamil);
        btnsearch = (ImageView) getActivity().findViewById(R.id.btn_city_tamil);
        btnmore = (Button)getActivity().findViewById(R.id.btn_shop_tamil);
        more = (ImageButton)getActivity().findViewById(R.id.btn_versiontwonotifications);
        url_notification_count_valueget=url_noti_count+myprofileid;
        URLPOSTQTYPE=urlpost;
        if(activity.equalsIgnoreCase("entertainmentversion")){

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove(Activity);
            editor.remove(CONTENTID);
            editor.apply();

        }
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
        mainlayout=(RelativeLayout)view.findViewById(R.id.version_main_layout);
        main_tamil_layout=(RelativeLayout)getActivity().findViewById(R.id.maintamil);
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

        settings_button=(ImageButton)view.findViewById(R.id.settingsbutton);
        line_vertical_textview=(TextView)view.findViewById(R.id.linevertical_versiontwo);
        line_vertical_textview.setText(Html.fromHtml("&nbsp;"+"|"+"&nbsp;"));
        line_settings_vertical_textview=(TextView) view.findViewById(R.id.linevertical_settings_versiontwo);
        line_settings_vertical_textview.setText(Html.fromHtml("&nbsp;"+"|"+"&nbsp;"));
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SettingsFragmentTamil();
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
            int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

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
        if(myprofileid!=null) {

            JsonArrayRequest jsonReq = new JsonArrayRequest(url_notification_count_valueget, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    // TODO Auto-generated method stub


                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                          ItemModel model = new ItemModel();
                            model.setCount(obj.getString("count"));
                            notification_counts = obj.getString("count");
                            Log.e("unrrad:", notification_counts);
                            value= Integer.parseInt(notification_counts);
                            if(value==0){
                              //  notification_batge_count.setVisibility(View.GONE);
                            }else {
                               // notification_batge_count.setVisibility(View.VISIBLE);
                               // notification_batge_count.setText(notification_counts);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    // VolleyLog.d(TAG, "ERROR" + error.getMessage());
                }
            });
            jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonReq);
        }else {

        }
        themechange_button=(ImageButton)view.findViewById(R.id.themebutton);
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
        SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        Date d1full = new Date();
        sMonthNamefull =  fmt.format(d1full);
        Log.e("DAY_OF_MONTH: ", "DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH)+sMonthName);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
        title_coimbatore=(TextView)view.findViewById(R.id.title_versiontwo) ;
        date_text=(TextView)view.findViewById(R.id.date_versiontwo) ;
        language_title=(TextView)view.findViewById(R.id.language_versiontwo) ;
        language_title.setTypeface(tf);
        language_title.setText(Html.fromHtml("&nbsp;"+"<b>Eng</b>"+"&nbsp;"+"|"+"&nbsp;"));
        layout = (LinearLayout)view. findViewById(R.id.title);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)layout.getLayoutParams();
        params.setMargins(0, 170, 0, 0);
        layout.setLayoutParams(params);
        String simplycity_title= "fonts/robotoSlabRegular.ttf";
        Typeface tf_roboto= Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);
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
        if(fontname.equals("playfair")){
            tf1 = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_reugular);
            title_coimbatore.setTypeface(tf1);
            line_vertical_textview.setTypeface(tf1);
            line_settings_vertical_textview.setTypeface(tf1);
        }else {
            tf1=Typeface.createFromAsset(getActivity().getAssets(),Fonts.muktamalar);
            title_coimbatore.setTypeface(tf1);
            title_coimbatore.setTextSize(30);
            line_vertical_textview.setTypeface(tf1);
            line_settings_vertical_textview.setTypeface(tf1);
        }
       /* if(colorcodes.equals("#FFFFFFFF")){
            explore.setBackgroundResource(R.color.theme13);
            explore.setImageResource(R.mipmap.specialstamilone);
           *//* city.setBackgroundResource(R.color.white);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*//*
        }
        else{

            if(colorcodes.equals("#262626")) {
                explore.setBackgroundResource(R.color.theme1button);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#59247c")) {
                explore.setBackgroundResource(R.color.theme2);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#1d487a")) {
                explore.setBackgroundResource(R.color.theme3);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#7A4100")) {
                explore.setBackgroundResource(R.color.theme4);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#6E0138")) {
                explore.setBackgroundResource(R.color.theme5);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#00BFD4")) {
                explore.setBackgroundResource(R.color.theme6);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#185546")) {
                explore.setBackgroundResource(R.color.theme7);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#D0A06F")) {
                explore.setBackgroundResource(R.color.theme8);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#82C6E6")) {
                explore.setBackgroundResource(R.color.theme9);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#339900")) {
                explore.setBackgroundResource(R.color.theme10);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#CC9C00")) {
                explore.setBackgroundResource(R.color.theme11);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
            else if(colorcodes.equals("#00B09B")) {
                explore.setBackgroundResource(R.color.theme12);
                explore.setImageResource(R.mipmap.specialtamil);
                city.setBackgroundResource(R.color.mytransparent);
                beyond.setBackgroundResource(R.color.mytransparent);
                more.setBackgroundResource(R.color.mytransparent);
                btnsearch.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.newstamil);
                beyond.setImageResource(R.mipmap.eventstamil);
                more.setImageResource(R.mipmap.moretamil);
                btnsearch.setImageResource(R.mipmap.searchtamil);
            }
        }*/

        date_text.setTypeface(tf_roboto);

        StringRequest weather=new StringRequest(Request.Method.GET, WEATHER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                /*if(response!=null){
                    weather_update.setText(Html.fromHtml(response+"<sup>o</sup>"));
                }else {
                    weather_update.setVisibility(View.GONE);
                }*/
                weatherdata=response.toString();
                if(dayOfTheWeek.equalsIgnoreCase("Sun")){
                    date_text.setText(Html.fromHtml("ஞாயிறு"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
                }else if(dayOfTheWeek.equalsIgnoreCase("Mon")){
                    date_text.setText(Html.fromHtml("திங்கள்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
                }else if(dayOfTheWeek.equalsIgnoreCase("Tue")){
                    date_text.setText(Html.fromHtml("செவ்வாய் "+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
                }else if (dayOfTheWeek.equalsIgnoreCase("Wed")){
                    date_text.setText(Html.fromHtml("புதன்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
                }else if(dayOfTheWeek.equalsIgnoreCase("Thu")){
                    date_text.setText(Html.fromHtml("வியாழன்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
                }else if(dayOfTheWeek.equalsIgnoreCase("Fri")){
                    date_text.setText(Html.fromHtml("வெள்ளி"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
                }else if(dayOfTheWeek.equalsIgnoreCase("Sat")){
                    date_text.setText(Html.fromHtml("சனி"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        weather.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(weather);
        if(weatherdata!=null){
            if(dayOfTheWeek.equalsIgnoreCase("Sun")){
                date_text.setText(Html.fromHtml("ஞாயிறு"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Mon")){
                date_text.setText(Html.fromHtml("திங்கள்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Tue")){
                date_text.setText(Html.fromHtml("செவ்வாய் "+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if (dayOfTheWeek.equalsIgnoreCase("Wed")){
                date_text.setText(Html.fromHtml("புதன்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Thu")){
                date_text.setText(Html.fromHtml("வியாழன்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Fri")){
                date_text.setText(Html.fromHtml("வெள்ளி"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Sat")){
                date_text.setText(Html.fromHtml("சனி"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }
        }else {
            if(dayOfTheWeek.equalsIgnoreCase("Sun")){
                date_text.setText(Html.fromHtml("ஞாயிறு"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Mon")){
                date_text.setText(Html.fromHtml("திங்கள்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Tue")){
                date_text.setText(Html.fromHtml("செவ்வாய் "+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if (dayOfTheWeek.equalsIgnoreCase("Wed")){
                date_text.setText(Html.fromHtml("புதன்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Thu")){
                date_text.setText(Html.fromHtml("வியாழன்"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Fri")){
                date_text.setText(Html.fromHtml("வெள்ளி"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }else if(dayOfTheWeek.equalsIgnoreCase("Sat")){
                date_text.setText(Html.fromHtml("சனி"+","+"&nbsp;"+sMonthNamefull+"&nbsp;"+"|"+"&nbsp;"+"<a style=font-size:xx-large>&#9729;</a>"+weatherdata+"<sup>o</sup>"+"&nbsp;"+"|"));
            }
        }

        if(colorcodes.equals("#FFFFFFFF")){
            themechange_button.setImageResource(R.drawable.themenormal);
            settings_button.setImageResource(R.drawable.settingsblack);
            if(fontname.equals("playfair")){
                font_button.setImageResource(R.mipmap.playfairblack);
            }else {
                font_button.setImageResource(R.mipmap.sanblack);
            }

        }else {
            themechange_button.setImageResource(R.drawable.themewhite);
            settings_button.setImageResource(R.drawable.settingswhite);
            if(fontname.equals("playfair")){
                font_button.setImageResource(R.mipmap.playfairwhite);
            }else {
                font_button.setImageResource(R.mipmap.sanwhite);
            }


        }

        language_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Language, "English");

                editor.commit();
                StringRequest language=new StringRequest(Request.Method.POST, url_change_lang, new Response.Listener<String>() {
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
                Intent tamil=new Intent(getActivity(),MainPageEnglish.class);
                startActivity(tamil);
               getActivity(). finish();
              // getActivity(). overridePendingTransition(R.anim.lefttoright, R.anim.lefttoright);
            }
        });
        title_coimbatore.setTypeface(tf1);
        date_text.setTypeface(tf);
        weather_update.setTypeface(tf);
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

        title_coimbatore.setText("சிறப்பு தொகுப்பு  ");
        if(dayOfTheWeek.equalsIgnoreCase("Sun")){
            date_text.setText(Html.fromHtml("ஞாயிறு"+","+"&nbsp;"+sMonthNamefull));
        }else if(dayOfTheWeek.equalsIgnoreCase("Mon")){
            date_text.setText(Html.fromHtml("திங்கள்"+","+"&nbsp;"+sMonthNamefull));
        }else if(dayOfTheWeek.equalsIgnoreCase("Tue")){
            date_text.setText(Html.fromHtml("செவ்வாய் "+","+"&nbsp;"+sMonthNamefull));
        }else if (dayOfTheWeek.equalsIgnoreCase("Wed")){
            date_text.setText(Html.fromHtml("புதன்"+","+"&nbsp;"+sMonthNamefull));
        }else if(dayOfTheWeek.equalsIgnoreCase("Thu")){
            date_text.setText(Html.fromHtml("வியாழன்"+","+"&nbsp;"+sMonthNamefull));
        }else if(dayOfTheWeek.equalsIgnoreCase("Fri")){
            date_text.setText(Html.fromHtml("வெள்ளி"+","+"&nbsp;"+sMonthNamefull));
        }else if(dayOfTheWeek.equalsIgnoreCase("Sat")){
            date_text.setText(Html.fromHtml("சனி"+","+"&nbsp;"+sMonthNamefull));
        }
       /* city=(ImageButton)view.findViewById(R.id.btn_versiontwocity);
        beyond=(ImageButton)view.findViewById(R.id.btn_versiontwobeyond);
        search=(ImageButton)view.findViewById(R.id.btn_versiontwosearch);
        explore=(ImageButton)view.findViewById(R.id.btn_versiontwoexplore);
        notifications=(ImageButton)view.findViewById(R.id.btn_versiontwonotifications);*/

        if(colorcodes.equalsIgnoreCase("#262626")){
         //  explore.setBackgroundResource(R.color.theme1button);
            fabplus.setBackgroundResource(R.color.theme1button);
            fabinnerplus.setBackgroundResource(R.color.theme1button);
            fabsearch.setBackgroundResource(R.color.theme1button);
        }else if(colorcodes.equalsIgnoreCase("#59247c")){
          // explore.setBackgroundResource(R.color.theme2);
            fabplus.setBackgroundResource(R.color.theme2);
            fabinnerplus.setBackgroundResource(R.color.theme2);
            fabsearch.setBackgroundResource(R.color.theme2);
        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
         //  explore.setBackgroundResource(R.color.theme3);
            fabplus.setBackgroundResource(R.color.theme3);
            fabinnerplus.setBackgroundResource(R.color.theme3);
            fabsearch.setBackgroundResource(R.color.theme3);
        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
          // explore.setBackgroundResource(R.color.theme4);
            fabplus.setBackgroundResource(R.color.theme4);
            fabinnerplus.setBackgroundResource(R.color.theme4);
            fabsearch.setBackgroundResource(R.color.theme4);
        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
         //  explore.setBackgroundResource(R.color.theme5);
            fabplus.setBackgroundResource(R.color.theme5);
            fabinnerplus.setBackgroundResource(R.color.theme5);
            fabsearch.setBackgroundResource(R.color.theme5);
        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
           // explore.setBackgroundResource(R.color.theme6);
            fabplus.setBackgroundResource(R.color.theme6);
            fabinnerplus.setBackgroundResource(R.color.theme6);
            fabsearch.setBackgroundResource(R.color.theme6);
        }else if(colorcodes.equalsIgnoreCase("#185546")){
         //  explore.setBackgroundResource(R.color.theme7);
            fabplus.setBackgroundResource(R.color.theme7);
            fabinnerplus.setBackgroundResource(R.color.theme7);
            fabsearch.setBackgroundResource(R.color.theme7);
        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
          // explore.setBackgroundResource(R.color.theme8);
            fabplus.setBackgroundResource(R.color.theme8);
            fabinnerplus.setBackgroundResource(R.color.theme8);
            fabsearch.setBackgroundResource(R.color.theme8);
        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
            //explore.setBackgroundResource(R.color.theme9);
            fabplus.setBackgroundResource(R.color.theme9);
            fabinnerplus.setBackgroundResource(R.color.theme9);
            fabsearch.setBackgroundResource(R.color.theme9);
        }else if(colorcodes.equalsIgnoreCase("#339900")){
          // explore.setBackgroundResource(R.color.theme10);
            fabplus.setBackgroundResource(R.color.theme10);
            fabinnerplus.setBackgroundResource(R.color.theme10);
            fabsearch.setBackgroundResource(R.color.theme10);
        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
          // explore.setBackgroundResource(R.color.theme11);
            fabplus.setBackgroundResource(R.color.theme11);
            fabinnerplus.setBackgroundResource(R.color.theme11);
            fabsearch.setBackgroundResource(R.color.theme11);
        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
           //explore.setBackgroundResource(R.color.theme12);
            fabplus.setBackgroundResource(R.color.theme12);
            fabinnerplus.setBackgroundResource(R.color.theme12);
            fabsearch.setBackgroundResource(R.color.theme12);
        }
        else if(colorcodes.equalsIgnoreCase("#FFFFFFFF")){
//            explore.setBackgroundResource(R.color.theme13);
            fabplus.setBackgroundResource(R.color.theme13);
            fabinnerplus.setBackgroundResource(R.color.theme13);
            fabsearch.setBackgroundResource(R.color.theme13);
        }
       /* explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getActivity(),EntertainmentVersiontwo.class);
                startActivity(entairnment);
              getActivity().  overridePendingTransition(R.anim.lefttoright, R.anim.lefttoright);
            }
        });
        beyond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent beyond=new Intent(getActivity(),EventsVersionTwo.class);
                startActivity(beyond);

              getActivity().  overridePendingTransition(R.anim.lefttoright, R.anim.lefttoright);
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent beyond = new Intent(getActivity(), MainPageEnglish.class);
                startActivity(beyond);
              getActivity().  overridePendingTransition(R.anim.lefttoright, R.anim.lefttoright);




            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent old_main=new Intent(getActivity(),MainActivity.class);
                startActivity(old_main);
             getActivity().   overridePendingTransition(R.anim.righttoleft, R.anim.righttoleft);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent notification_page = new Intent(getActivity(), MainPageEnglish.class);
                startActivity(notification_page);
             getActivity().   overridePendingTransition(R.anim.righttoleft, R.anim.righttoleft);


            }
        });*/
        font_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontname=sharedpreferences.getString(FONT,"");
                if(fontname.equals("playfair")){
                    Fonts fonts=new Fonts();

                    Typeface tf=fonts.font("sanfrancisco",getActivity());
                    Typeface tf1=fonts.font1(getActivity());

                    SharedPreferences.Editor editor=sharedpreferences.edit();
                    editor.putString(FONT,"sanfrancisco");
                    editor.commit();
                    title_coimbatore.setTypeface(tf);
                    title_coimbatore.setTextSize(30);
                    if(colorcodes.equals("#FFFFFFFF")){
                        font_button.setImageResource(R.mipmap.sanwhite);
                    }else {
                        font_button.setImageResource(R.mipmap.sanblack);
                    }
                    Fragment selectedFragment = null;
                    selectedFragment = EntertainmentFragmentTamil.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                   /* Intent in=new Intent(getActivity(), MainPageEnglish.class);
                    in.putExtra("ID","1");
                    startActivity(in);*/
                }else  {
                    Fonts fonts=new Fonts();

                    Typeface tf=fonts.font("playfair",getActivity());
                    // Typeface tf1=fonts.font1(getActivity());

                    SharedPreferences.Editor editor=sharedpreferences.edit();
                    editor.putString(FONT,"playfair");
                    editor.commit();
                    title_coimbatore.setTypeface(tf);
                    title_coimbatore.setTextSize(40);
                    if(colorcodes.equals("#FFFFFFFF")){
                        font_button.setImageResource(R.mipmap.playfairwhite);
                    }else {
                        font_button.setImageResource(R.mipmap.playfairblack);
                    }

                    Fragment selectedFragment = null;
                    selectedFragment = EntertainmentFragmentTamil.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                }
            }
        });
        themechange_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(colorcodes.equals("#FFFFFFFF")){

                    Fragment fragment = new EntertainmentFragmentTamil();
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
                    main_tamil_layout.setBackgroundColor(Color.parseColor("#FF000000"));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                   editor.putString(backgroundcolor, "#262626");
                    editor.commit();


                }else if(colorcodes.equals("#262626")) {

                    Fragment fragment = new EntertainmentFragmentTamil();
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
                    main_tamil_layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(backgroundcolor, "#FFFFFFFF");
                    editor.commit();

                }
                // showAlertDialog();
                /*final Dialog dialog = new Dialog(getActivity());
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

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                        explore.setBackgroundResource(R.color.theme1button);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
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

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                       explore.setBackgroundResource(R.color.theme2);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                        // fabup.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#59247c");


                        editor.commit();
                       // dialog.dismiss();

                    }
                });
                colorthree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                       explore.setBackgroundResource(R.color.theme3);
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#1d487a");
                        editor.commit();
                       // dialog.dismiss();

                    }
                });
                colorfour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                       explore.setBackgroundResource(R.color.theme4);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
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

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                     explore.setBackgroundResource(R.color.theme5);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#6E0138");

                        editor.commit();
                     //   dialog.dismiss();
                    }
                });
                colorsix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                        explore.setBackgroundResource(R.color.theme6);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#00BFD4");

                        editor.commit();
                       // dialog.dismiss();
                    }
                });
                colorseven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                     explore.setBackgroundResource(R.color.theme7);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#185546");

                        editor.commit();
                      //  dialog.dismiss();
                    }
                });
                coloreight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                        explore.setBackgroundResource(R.color.theme8);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
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

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                       explore.setBackgroundResource(R.color.theme9);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
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

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                        explore.setBackgroundResource(R.color.theme10);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
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

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                     explore.setBackgroundResource(R.color.theme11);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
                        fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                        fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                        fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#CC9C00");

                        editor.commit();
                       // dialog.dismiss();
                    }
                });
                colortwelve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                     explore.setBackgroundResource(R.color.theme12);
                        explore.setImageResource(R.mipmap.specials);
                        beyond.setBackgroundResource(R.color.mytransparent);
                        beyond.setImageResource(R.mipmap.events);
                        city.setBackgroundResource(R.color.mytransparent);
                        city.setImageResource(R.mipmap.news);
                        btnsearch.setBackgroundResource(R.color.mytransparent);
                        btnsearch.setImageResource(R.mipmap.search);
                        more.setBackgroundResource(R.color.mytransparent);
                        more.setImageResource(R.mipmap.more);
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
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new EntertainmentFragmentTamil();
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
                        explore.setBackgroundResource(R.color.theme13);
                        explore.setImageResource(R.mipmap.specialstamilone);
                        beyond.setImageResource(R.mipmap.eventstamilone);
                        city.setImageResource(R.mipmap.newstamilone);
                        city.setBackgroundResource(R.color.theme14);
                        btnsearch.setBackgroundResource(R.color.theme14);
                        btnsearch.setImageResource(R.mipmap.searchtamilone);
                        more.setBackgroundResource(R.color.theme14);
                        more.setImageResource(R.mipmap.moretamilone);
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
        mCoordinator = (CoordinatorLayout)view. findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)view. findViewById(R.id.collapsing_toolbar_layout);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mAdapter = new HealthViewPagerAdapter(getChildFragmentManager());
        mPager = (ViewPager) view.findViewById(R.id.viewPager);
        //mPager.setAdapter(mAdapter);
        setupViewPager(mPager);
        //Notice how the Tab Layout links with the Pager Adapter
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
      //  mTabLayout.setOnTabSelectedListener(this);
        //Notice how The Tab Layout adn View Pager object are linked
        mTabLayout.setupWithViewPager(mPager);
        if(colorcodes.equals("#FFFFFFFF")){
            mTabLayout.setSelectedTabIndicatorColor(Color.BLACK);
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
                    if(colorcodes.equals("#FFFFFFFF")){
                        ((TextView) tabViewChild).setTextColor(Color.BLACK);
                    }
                }
            }
        }
    }

    class ItemModel{
        String count;

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
        adapter.addFragment(new TamilEntertainmentall(), "அனைத்தும்");

       // adapter.addFragment(new Tabarticle(),"கட்டுரைகள்");
       // adapter.addFragment(new Tabcolumnisttamil(),"கட்டுரையாளர் ");
        adapter.addFragment(new Tamilentertainmentmovies(), "வீடியோ ");
        adapter.addFragment(new Tamilentertainmentradio(), "ஆடியோ ");
        adapter.addFragment(new Tabphotostoriestamil(),"புகைப்படங்கள்  ");
       // adapter.addFragment(new Tamilentertainmentmusic(), "இசை");



       // adapter.addFragment(new RadioJockeyTamil(),"ரேடியோ ஜாக்கி" );
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

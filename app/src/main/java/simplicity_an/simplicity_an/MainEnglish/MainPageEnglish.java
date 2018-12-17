package simplicity_an.simplicity_an.MainEnglish;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import java.util.Hashtable;
import java.util.Map;

import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MusicplayerBottom;
import simplicity_an.simplicity_an.PrefManager;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SearchWebPage;
import simplicity_an.simplicity_an.TabEntertainmentAll;
import simplicity_an.simplicity_an.TabEntertainmentMusic;
import simplicity_an.simplicity_an.TabMusic;
import simplicity_an.simplicity_an.TabRadio;
import simplicity_an.simplicity_an.Tab_All;
import simplicity_an.simplicity_an.TabentertainmentRadio;
import simplicity_an.simplicity_an.Tabnews;
import simplicity_an.simplicity_an.Utils.Fonts;

/**
 * Created by kuppusamy on 5/18/2017.
 */

public class MainPageEnglish extends AppCompatActivity implements Tab_All.OnFragmentInteractionListener,TabRadio.OnFragmentInteractionListener,TabMusic.OnFragmentInteractionListener, TabEntertainmentAll.OnFragmentInteractionListener,TabEntertainmentMusic.OnFragmentInteractionListener,TabentertainmentRadio.OnFragmentInteractionListener,Tabnews.OnFragmentInteractionListener {
    ImageView search;
   private static Button happening,audio_video,settings,explore;
   private static Button city;
    String UPLOAD_CHECK_USER="http://simpli-city.in/request2.php?rtype=checkplayer&key=simples";
    LinearLayout footerbar;
    private ImageButton play_music,fastforward,backforward,close_player;
    TextView music_title_name;
    SharedPreferences sharedpreferences;
    public static final String backgroundcolor = "color";
    String activity,contentid,colorcodes;
    public static final String mypreference = "mypref";
    String myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public static final String GcmId = "gcmid";
    private PrefManager prefManager;
    public static final String Language = "lamguage";

    String id,radio_title,radio_url;
    RelativeLayout topLevelLayout;
    private static int SPLASH_TIME_OUT = 4000;
    Dialog dialog;
    boolean doubleBackToExitPressedOnce = false;
LinearLayout footer;
    String language_data,radio_button_values;
    String url_change_lang="http://simpli-city.in/request2.php?rtype=updatelanguage&key=simples";
    String playerid,fontname;
    Typeface tf_play,tf;
    RelativeLayout main_english_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainpageenglish);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        main_english_layout=(RelativeLayout)findViewById(R.id.mainenglish);
        Intent get=getIntent();
        id=get.getStringExtra("ID");
        radio_title=get.getStringExtra("TITLE");
        radio_url=get.getStringExtra("URL");
//Check();
//isFirstTime();
        Instructions();
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

        CheckPlayerid();


        city=(Button) findViewById(R.id.btn_news);
        happening=(Button)findViewById(R.id.btn_events);
        search=(ImageView) findViewById(R.id.btn_citys);
        audio_video=(Button)findViewById(R.id.btn_specials);
        settings=(Button)findViewById(R.id.btn_shop);
        topLevelLayout=(RelativeLayout)findViewById(R.id.top_layout);
        footer=(LinearLayout) findViewById(R.id.footer);

        /*if(fontname.equals("sanfrancisco")){
            String playfair ="fonts/Oxygen-Bold.ttf";
            tf_play = Typeface.createFromAsset(getApplicationContext().getAssets(), playfair);
            String simplycity_title_bold = "fonts/SystemSanFranciscoDisplayRegular.ttf";
            tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);
          city.setTypeface(tf);
            happening.setTypeface(tf);
            audio_video.setTypeface(tf);
            settings.setTypeface(tf);
        }else {
            String playfair = "fonts/playfairDisplayRegular.ttf";
            tf_play = Typeface.createFromAsset(getApplicationContext().getAssets(), playfair);
            String simplycity_title_bold = "fonts/Lora-Regular.ttf";
            tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);
            city.setTypeface(tf_play);
            happening.setTypeface(tf_play);
            audio_video.setTypeface(tf_play);
            settings.setTypeface(tf_play);
        }*/


        if(colorcodes.equals("#FFFFFFFF")){


            happening.setTextColor(Color.parseColor("#666666"));
            audio_video.setTextColor(Color.parseColor("#666666"));
            settings.setTextColor(Color.parseColor("#666666"));
            main_english_layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            city.setTextColor(Color.BLACK);

        }
        else{


            main_english_layout.setBackgroundColor(Color.parseColor("#FF000000"));
            city.setTextColor(Color.WHITE);
            happening.setTextColor(Color.parseColor("#666666"));
            audio_video.setTextColor(Color.parseColor("#666666"));
            settings.setTextColor(Color.parseColor("#666666"));
        }

       city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment selectedFragment = null;
                selectedFragment = CityFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                if(colorcodes.length()==0){

                }else {
                    if(colorcodes.equalsIgnoreCase("004")){
                        Log.e("Msg","hihihi");
                    }else {
                        if(colorcodes.equals("#FFFFFFFF")){
                            city.setTextColor(Color.BLACK);
                        }else {
                            city.setTextColor(Color.WHITE);
                        }
                        city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
                        happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        happening.setTextColor(Color.parseColor("#666666"));
                        audio_video.setTextColor(Color.parseColor("#666666"));
                        settings.setTextColor(Color.parseColor("#666666"));
                    }
                    /*else {
                        city.setTextColor(Color.WHITE);
                        happening.setTextColor(Color.parseColor("#666666"));
                        audio_video.setTextColor(Color.parseColor("#666666"));
                        settings.setTextColor(Color.parseColor("#666666"));
                        if(colorcodes.equalsIgnoreCase("#262626")){
                            *//*city.setBackgroundColor(getResources().getColor(R.color.theme1button));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                        }else if(colorcodes.equalsIgnoreCase("#59247c")){
                           *//* city.setBackgroundColor(getResources().getColor(R.color.theme2));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                           *//* city.setBackgroundColor(getResources().getColor(R.color.theme3));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                           *//* city.setBackgroundColor(getResources().getColor(R.color.theme4));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                           *//* city.setBackgroundColor(getResources().getColor(R.color.theme5));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                           *//* city.setBackgroundColor(getResources().getColor(R.color.theme6));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#185546")){
                            *//*city.setBackgroundColor(getResources().getColor(R.color.theme7));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                           *//* city.setBackgroundColor(getResources().getColor(R.color.theme8));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));*//*
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                            city.setBackgroundColor(getResources().getColor(R.color.theme9));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#339900")){
                            city.setBackgroundColor(getResources().getColor(R.color.theme10));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                            city.setBackgroundColor(getResources().getColor(R.color.theme11));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                            city.setBackgroundColor(getResources().getColor(R.color.theme12));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            city.setTextColor(Color.WHITE);
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }
                        else if(colorcodes.equalsIgnoreCase("#FFFFFFFF")){
                            city.setBackgroundColor(getResources().getColor(R.color.theme13));
                            happening.setBackgroundColor(getResources().getColor(R.color.white));
                            search.setBackgroundColor(getResources().getColor(R.color.white));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.white));
                            settings.setBackgroundColor(getResources().getColor(R.color.white));
                            //city.setImageResource(R.mipmap.newsone);
                            *//*happening.setImageResource(R.mipmap.eventone);
                            search.setImageResource(R.mipmap.searchone);
                            audio_video.setImageResource(R.mipmap.specialone);
                            settings.setImageResource(R.mipmap.moreone);*//*
                            city.setTextColor(Color.BLACK);
                            happening.setTextColor(Color.parseColor("#CCCCCC"));
                            audio_video.setTextColor(Color.parseColor("#CCCCCC"));
                            settings.setTextColor(Color.parseColor("#CCCCCC"));
                        }
                    }*/
                }
            }
        });
        happening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              /*  if(colorcodes.equals("#FFFFFFFF")){
                    city.setBackgroundResource(R.color.white);
                    //happening.setBackgroundResource(R.color.white);
                    search.setBackgroundResource(R.color.white);
                    audio_video.setBackgroundResource(R.color.white);
                    settings.setBackgroundResource(R.color.white);
                    city.setImageResource(R.mipmap.newsone);
                    //happening.setImageResource(R.mipmap.eventone);
                    search.setImageResource(R.mipmap.searchone);
                    audio_video.setImageResource(R.mipmap.specialone);
                    settings.setImageResource(R.mipmap.moreone);
                }
                else{
                    //happening.setBackgroundResource(R.color.mytransparent);
                    search.setBackgroundResource(R.color.mytransparent);
                    city.setBackgroundResource(R.color.mytransparent);
                    audio_video.setBackgroundResource(R.color.mytransparent);
                    settings.setBackgroundResource(R.color.mytransparent);
                    city.setImageResource(R.mipmap.news);
                    happening.setImageResource(R.mipmap.events);
                    search.setImageResource(R.mipmap.search);
                    audio_video.setImageResource(R.mipmap.specials);
                    settings.setImageResource(R.mipmap.more);
                }*/
                Fragment selectedFragment = null;
                selectedFragment = HappeningFrag.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                if(colorcodes.length()==0){

                }else {
                    if(colorcodes.equalsIgnoreCase("004")){

                    }else {
                        if(colorcodes.equals("#FFFFFFFF")){
                            happening.setTextColor(Color.BLACK);
                        }else {
                            happening.setTextColor(Color.WHITE);
                        }
                        happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
                        city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        Log.e("Msg","hievent");

                        city.setTextColor(Color.parseColor("#666666"));
                        audio_video.setTextColor(Color.parseColor("#666666"));
                        settings.setTextColor(Color.parseColor("#666666"));
                    }

                    /*else {
                        if(colorcodes.equalsIgnoreCase("#262626")){
                           happening.setBackgroundColor(getResources().getColor(R.color.theme1button));;
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            happening.setTextColor(Color.WHITE);
                           city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#59247c")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme2));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                           happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme3));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme4));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme5));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme6));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            happening.setTextColor(Color.WHITE);
                          city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#185546")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme7));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme8));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                           happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme9));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                           happening.setTextColor(Color.WHITE);
                           city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#339900")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme10));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme11));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));

                        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme12));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*

                           happening.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }
                        else if(colorcodes.equalsIgnoreCase("#FFFFFFFF")){
                            happening.setBackgroundColor(getResources().getColor(R.color.theme13));
                            city.setBackgroundColor(getResources().getColor(R.color.white));
                            //happening.setBackgroundResource(R.color.white);
                            search.setBackgroundColor(getResources().getColor(R.color.white));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.white));
                            settings.setBackgroundColor(getResources().getColor(R.color.white));
                           *//* city.setImageResource(R.mipmap.newsone);
                            //happening.setImageResource(R.mipmap.eventone);
                            search.setImageResource(R.mipmap.searchone);
                            audio_video.setImageResource(R.mipmap.specialone);
                            settings.setImageResource(R.mipmap.moreone);*//*
                            city.setTextColor(Color.parseColor("#CCCCCC"));
                            happening.setTextColor(Color.BLACK);
                            audio_video.setTextColor(Color.parseColor("#CCCCCC"));
                            settings.setTextColor(Color.parseColor("#CCCCCC"));
                        }
                    }*/
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if(myprofileid!=null) {


                /*if(colorcodes.equals("#FFFFFFFF")){
                    city.setBackgroundResource(R.color.white);
                    happening.setBackgroundResource(R.color.white);
                    //search.setBackgroundResource(R.color.white);
                    audio_video.setBackgroundResource(R.color.white);
                    settings.setBackgroundResource(R.color.white);
                    city.setImageResource(R.mipmap.newsone);
                    happening.setImageResource(R.mipmap.eventone);
                    //search.setImageResource(R.mipmap.searchone);
                    audio_video.setImageResource(R.mipmap.specialone);
                    settings.setImageResource(R.mipmap.moreone);
                }
                else{
                    happening.setBackgroundResource(R.color.mytransparent);
                    city.setBackgroundResource(R.color.mytransparent);
                    audio_video.setBackgroundResource(R.color.mytransparent);
                    settings.setBackgroundResource(R.color.mytransparent);
                    city.setImageResource(R.mipmap.news);
                    happening.setImageResource(R.mipmap.events);
                   search.setImageResource(R.mipmap.search);
                    audio_video.setImageResource(R.mipmap.specials);
                    settings.setImageResource(R.mipmap.more);
                }*/
             /* Intent in = new Intent(getApplicationContext(), ExploreMain.class);
                    startActivity(in);*/

                Fragment selectedFragment = null;
                selectedFragment = SearchWebPage.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                    if (colorcodes.length() == 0) {

                    } else {
                        if (colorcodes.equalsIgnoreCase("004")) {
                            Log.e("Msg", "hihihi");
                        }else {
                            happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                            city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                            audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                            settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }
                        /*else {
                            if (colorcodes.equalsIgnoreCase("#383838")) {
                                search.setBackgroundResource(R.color.theme1button);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                                *//*city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*
                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));

                            } else if (colorcodes.equalsIgnoreCase("#59247c")) {
                                search.setBackgroundResource(R.color.theme2);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                                *//*city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*

                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#1d487a")) {
                                search.setBackgroundResource(R.color.theme3);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                               *//* city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*

                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#7A4100")) {
                                search.setBackgroundResource(R.color.theme4);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                                *//*city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*
                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#6E0138")) {
                                search.setBackgroundResource(R.color.theme5);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                               *//* city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*

                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#00BFD4")) {
                                search.setBackgroundResource(R.color.theme6);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                               *//* city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*

                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#185546")) {
                                search.setBackgroundResource(R.color.theme7);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                               *//* city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*

                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#D0A06F")) {
                                search.setBackgroundResource(R.color.theme8);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                                *//*city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*

                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#82C6E6")) {
                                search.setBackgroundResource(R.color.theme9);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                               *//* city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*

                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#339900")) {
                                search.setBackgroundResource(R.color.theme10);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                                *//*city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*

                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#CC9C00")) {
                                search.setBackgroundResource(R.color.theme11);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                                *//*city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*
                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            } else if (colorcodes.equalsIgnoreCase("#00B09B")) {
                                search.setBackgroundResource(R.color.theme12);
                                happening.setBackgroundResource(R.color.mytransparent);
                                city.setBackgroundResource(R.color.mytransparent);
                                audio_video.setBackgroundResource(R.color.mytransparent);
                                settings.setBackgroundResource(R.color.mytransparent);
                                *//*city.setImageResource(R.mipmap.news);
                                happening.setImageResource(R.mipmap.events);
                                search.setImageResource(R.mipmap.search);
                                audio_video.setImageResource(R.mipmap.specials);
                                settings.setImageResource(R.mipmap.more);*//*
                                city.setTextColor(Color.parseColor("#666666"));
                                happening.setTextColor(Color.parseColor("#666666"));
                                audio_video.setTextColor(Color.parseColor("#666666"));
                                settings.setTextColor(Color.parseColor("#666666"));
                            }
                            else if (colorcodes.equalsIgnoreCase("#FFFFFFFF")) {
                                //search.setBackgroundResource(R.color.theme13);
                                city.setBackgroundResource(R.color.white);
                                happening.setBackgroundResource(R.color.white);
                                //search.setBackgroundResource(R.color.white);
                                audio_video.setBackgroundResource(R.color.white);
                                settings.setBackgroundResource(R.color.white);
                                *//*city.setImageResource(R.mipmap.newsone);
                                happening.setImageResource(R.mipmap.eventone);
                                //search.setImageResource(R.mipmap.searchone);
                                audio_video.setImageResource(R.mipmap.specialone);
                                settings.setImageResource(R.mipmap.moreone);*//*
                                city.setTextColor(Color.parseColor("#CCCCCC"));
                                audio_video.setTextColor(Color.parseColor("#CCCCCC"));
                                happening.setTextColor(Color.parseColor("#CCCCCC"));
                                settings.setTextColor(Color.parseColor("#CCCCCC"));
                            }
                        }*/
                    }
                /*}else {
                    Intent in = new Intent(getApplicationContext(), SigninpageActivity.class);
                    startActivity(in);
                }*/
            }
        });
        audio_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*if(colorcodes.equals("#FFFFFFFF")){
                    city.setBackgroundResource(R.color.white);
                    happening.setBackgroundResource(R.color.white);
                    search.setBackgroundResource(R.color.white);
                    //audio_video.setBackgroundResource(R.color.white);
                    settings.setBackgroundResource(R.color.white);
                    city.setImageResource(R.mipmap.newsone);
                    happening.setImageResource(R.mipmap.eventone);
                    search.setImageResource(R.mipmap.searchone);
                    //audio_video.setImageResource(R.mipmap.specialone);
                    settings.setImageResource(R.mipmap.moreone);
                }
                else{
                    happening.setBackgroundResource(R.color.mytransparent);
                    search.setBackgroundResource(R.color.mytransparent);
                    city.setBackgroundResource(R.color.mytransparent);
                    settings.setBackgroundResource(R.color.mytransparent);
                    city.setImageResource(R.mipmap.news);
                    happening.setImageResource(R.mipmap.events);
                    search.setImageResource(R.mipmap.search);
                    audio_video.setImageResource(R.mipmap.specials);
                    settings.setImageResource(R.mipmap.more);
                }*/
                Fragment selectedFragment = null;
                selectedFragment = EntertainmentFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                if(colorcodes.length()==0){

                }else {
                    if(colorcodes.equalsIgnoreCase("004")){
                        Log.e("Msg","hihihi");
                    }else {
                        if(colorcodes.equals("#FFFFFFFF")){
                            audio_video.setTextColor(Color.BLACK);
                        }else {
                            audio_video.setTextColor(Color.WHITE);
                        }
                        audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
                        happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

                        city.setTextColor(Color.parseColor("#666666"));
                        happening.setTextColor(Color.parseColor("#666666"));
                        settings.setTextColor(Color.parseColor("#666666"));
                    }
                    /*else {
                        if(colorcodes.equalsIgnoreCase("#262626")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme1button));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                          *//*  city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#59247c")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme2));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme3));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme4));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme5));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme6));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#185546")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme7));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme8));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme9));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#339900")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme10));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme11));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme12));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            settings.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            audio_video.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            settings.setTextColor(Color.parseColor("#666666"));
                        }
                        else if(colorcodes.equalsIgnoreCase("#FFFFFFFF")){
                            audio_video.setBackgroundColor(getResources().getColor(R.color.theme13));
                            happening.setBackgroundColor(getResources().getColor(R.color.white));
                           search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.white));
                            settings.setBackgroundColor(getResources().getColor(R.color.white));
                            city.setTextColor(Color.parseColor("#CCCCCC"));
                            audio_video.setTextColor(Color.BLACK);
                            happening.setTextColor(Color.parseColor("#CCCCCC"));
                            settings.setTextColor(Color.parseColor("#CCCCCC"));
                        }
                    }*/
                }
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                /*if(colorcodes.equals("#FFFFFFFF")){
                    city.setBackgroundResource(R.color.white);
                    happening.setBackgroundResource(R.color.white);
                    search.setBackgroundResource(R.color.white);
                    audio_video.setBackgroundResource(R.color.white);
                    //settings.setBackgroundResource(R.color.white);
                    city.setImageResource(R.mipmap.newsone);
                    happening.setImageResource(R.mipmap.eventone);
                    search.setImageResource(R.mipmap.searchone);
                    audio_video.setImageResource(R.mipmap.specialone);
                    //settings.setImageResource(R.mipmap.moreone);
                }
                else{
                    happening.setBackgroundResource(R.color.mytransparent);
                    search.setBackgroundResource(R.color.mytransparent);
                    audio_video.setBackgroundResource(R.color.mytransparent);
                    city.setBackgroundResource(R.color.mytransparent);
                    city.setImageResource(R.mipmap.news);
                    happening.setImageResource(R.mipmap.events);
                    search.setImageResource(R.mipmap.search);
                    audio_video.setImageResource(R.mipmap.specials);
                    settings.setImageResource(R.mipmap.more);
                }*/
                FragmentManager fragmentManager = getSupportFragmentManager();
                SettingsFragment fragment = new SettingsFragment();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.commit();
                if(colorcodes.length()==0){

                }else {
                    if(colorcodes.equalsIgnoreCase("004")){
                        Log.e("Msg","hihihi");
                    }else {
                        if(colorcodes.equals("#FFFFFFFF")){
                            settings.setTextColor(Color.BLACK);
                        }else {
                            settings.setTextColor(Color.WHITE);
                        }
                        settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
                        happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

                        city.setTextColor(Color.parseColor("#666666"));
                        happening.setTextColor(Color.parseColor("#666666"));
                        audio_video.setTextColor(Color.parseColor("#666666"));
                    }
                    /*else {
                        if(colorcodes.equalsIgnoreCase("#262626")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme1button));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                          *//*  city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#59247c")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme2));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme3));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme4));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme5));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme6));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#185546")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme7));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme8));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme9));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#339900")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme10));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme11));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                           *//* city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme12));
                            happening.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            city.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            *//*city.setImageResource(R.mipmap.news);
                            happening.setImageResource(R.mipmap.events);
                            search.setImageResource(R.mipmap.search);
                            audio_video.setImageResource(R.mipmap.specials);
                            settings.setImageResource(R.mipmap.more);*//*
                            settings.setTextColor(Color.WHITE);
                            city.setTextColor(Color.parseColor("#666666"));
                            happening.setTextColor(Color.parseColor("#666666"));
                            audio_video.setTextColor(Color.parseColor("#666666"));
                        }
                        else if(colorcodes.equalsIgnoreCase("#FFFFFFFF")){
                            settings.setBackgroundColor(getResources().getColor(R.color.theme13));
                            happening.setBackgroundColor(getResources().getColor(R.color.white));
                            search.setBackgroundColor(getResources().getColor(R.color.mytransparent));
                            audio_video.setBackgroundColor(getResources().getColor(R.color.white));
                            city.setBackgroundColor(getResources().getColor(R.color.white));
                            city.setTextColor(Color.parseColor("#CCCCCC"));
                            settings.setTextColor(Color.BLACK);
                            audio_video.setTextColor(Color.parseColor("#CCCCCC"));
                            happening.setTextColor(Color.parseColor("#CCCCCC"));
                        }
                    }*/
                }
            }
        });
/*if(radio_url!=null){
    onFragmentInteraction(radio_url,radio_title);
}else {

}*/
        if(id!=null){
            changefrag();
        }else {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, CityFragment.newInstance());
        transaction.commit();
            city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
            if(colorcodes.equals("#FFFFFFFF")){
                city.setTextColor(Color.BLACK);
            }else {
                city.setTextColor(Color.WHITE);
            }
            happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            
            happening.setTextColor(Color.parseColor("#666666"));
            audio_video.setTextColor(Color.parseColor("#666666"));
            settings.setTextColor(Color.parseColor("#666666"));
        /*if(colorcodes.length()==0){

        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi");
            }else {
                if(colorcodes.equalsIgnoreCase("#262626")){
                    city.setBackgroundResource(R.color.theme1button);
                }else if(colorcodes.equalsIgnoreCase("#59247c")){
                    city.setBackgroundResource(R.color.theme2);
                }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                    city.setBackgroundResource(R.color.theme3);
                }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                    city.setBackgroundResource(R.color.theme4);
                }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                    city.setBackgroundResource(R.color.theme5);
                }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                    city.setBackgroundResource(R.color.theme6);
                }else if(colorcodes.equalsIgnoreCase("#185546")){
                    city.setBackgroundResource(R.color.theme7);
                }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                    city.setBackgroundResource(R.color.theme8);
                }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                    city.setBackgroundResource(R.color.theme9);
                }else if(colorcodes.equalsIgnoreCase("#339900")){
                    city.setBackgroundResource(R.color.theme10);
                }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                    city.setBackgroundResource(R.color.theme11);
                }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                    city.setBackgroundResource(R.color.theme12);
                }
                else if(colorcodes.equalsIgnoreCase("#ffffff")){
                    city.setBackgroundResource(R.color.theme13);
                }
            }
        }*/
        }
    }
    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
       if (!ranBefore) {

           SharedPreferences.Editor editor = preferences.edit();
           editor.putBoolean("RanBefore", true);
           editor.commit();
          // themeselection();

       }

        /*SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);*/
       /* if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();*/
            /*topLevelLayout.setVisibility(View.VISIBLE);
            topLevelLayout.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    topLevelLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

            });*/


       // }
       // return ranBefore;
        return true;

    }
public void Changefont(String s,Context ctx){



   if(s.equals("sanfrancisco")){
        String playfair ="fonts/Oxygen-Bold.ttf";
        tf_play = Typeface.createFromAsset(ctx.getAssets(), playfair);
        String simplycity_title_bold = "fonts/SystemSanFranciscoDisplayRegular.ttf";
        tf = Typeface.createFromAsset(ctx.getAssets(), simplycity_title_bold);
        city.setTypeface(tf);
        happening.setTypeface(tf);
        audio_video.setTypeface(tf);
        settings.setTypeface(tf);
    }else {
        String playfair = "fonts/playfairDisplayRegular.ttf";
        tf_play = Typeface.createFromAsset(ctx.getAssets(), playfair);
        String simplycity_title_bold = "fonts/Lora-Regular.ttf";
        tf = Typeface.createFromAsset(ctx.getAssets(), simplycity_title_bold);
        city.setTypeface(tf_play);
        happening.setTypeface(tf_play);
        audio_video.setTypeface(tf_play);
        settings.setTypeface(tf_play);
    }
}

    private void  Instructions(){
/*
      topLevelLayout.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                topLevelLayout.setVisibility(View.INVISIBLE);
                return false;
            }

        });*/

    }
    public void onFragmentInteraction(String playurl, String title,String image) {
        MusicplayerBottom secondFragment = (MusicplayerBottom) getSupportFragmentManager().findFragmentById(R.id.musicbottomplayer);
        secondFragment.PlaySong(playurl, title ,image);
    }

    public void Check(){
        if( MusicplayerBottom.mediaPlayer.isPlaying()){
            MusicplayerBottom.mediaPlayer.stop();
        }else {

        }
    }

    private void CheckPlayerid(){
        StringRequest request=new StringRequest(Request.Method.POST, UPLOAD_CHECK_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
Log.e("RES",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new Hashtable<String, String>();
                params.put("player_id",playerid);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainPageEnglish.this);
        requestQueue.add(request);
    }


    private void changefrag(){
        if(id.equals("1")){
            happening.setBackgroundResource(R.color.mytransparent);
            search.setBackgroundResource(R.color.mytransparent);
            audio_video.setBackgroundResource(R.color.mytransparent);
            city.setBackgroundResource(R.color.mytransparent);
            FragmentManager fragmentManager = getSupportFragmentManager();
            CityFragment fragment = new CityFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();
            city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);

            happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            city.setTextColor(Color.WHITE);
            happening.setTextColor(Color.parseColor("#666666"));
            audio_video.setTextColor(Color.parseColor("#666666"));
            settings.setTextColor(Color.parseColor("#666666"));
           /* if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#262626")){
                        settings.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        settings.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        settings.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        settings.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        settings.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        settings.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        settings.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        settings.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        settings.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        settings.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        settings.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        settings.setBackgroundResource(R.color.theme12);
                    }
                    else if(colorcodes.equalsIgnoreCase("#ffffff")){
                        settings.setBackgroundResource(R.color.theme13);
                    }
                }
            }*/
        }else if(id.equals("2")){
            happening.setBackgroundResource(R.color.mytransparent);
            search.setBackgroundResource(R.color.mytransparent);
            city.setBackgroundResource(R.color.mytransparent);
            settings.setBackgroundResource(R.color.mytransparent);
            Fragment selectedFragment = null;
            selectedFragment = EntertainmentFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
            happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            audio_video.setTextColor(Color.WHITE);
            city.setTextColor(Color.parseColor("#666666"));
            happening.setTextColor(Color.parseColor("#666666"));
            settings.setTextColor(Color.parseColor("#666666"));
            /*if(colorcodes.length()==0){

            }else {
                if (colorcodes.equalsIgnoreCase("004")) {
                    Log.e("Msg", "hihihi");
                } else {
                    if (colorcodes.equalsIgnoreCase("#383838")) {
                        audio_video.setBackgroundResource(R.color.theme1button);
                    } else if (colorcodes.equalsIgnoreCase("#59247c")) {
                        audio_video.setBackgroundResource(R.color.theme2);
                    } else if (colorcodes.equalsIgnoreCase("#1d487a")) {
                        audio_video.setBackgroundResource(R.color.theme3);
                    } else if (colorcodes.equalsIgnoreCase("#7A4100")) {
                        audio_video.setBackgroundResource(R.color.theme4);
                    } else if (colorcodes.equalsIgnoreCase("#6E0138")) {
                        audio_video.setBackgroundResource(R.color.theme5);
                    } else if (colorcodes.equalsIgnoreCase("#00BFD4")) {
                        audio_video.setBackgroundResource(R.color.theme6);
                    } else if (colorcodes.equalsIgnoreCase("#185546")) {
                        audio_video.setBackgroundResource(R.color.theme7);
                    } else if (colorcodes.equalsIgnoreCase("#D0A06F")) {
                        audio_video.setBackgroundResource(R.color.theme8);
                    } else if (colorcodes.equalsIgnoreCase("#82C6E6")) {
                        audio_video.setBackgroundResource(R.color.theme9);
                    } else if (colorcodes.equalsIgnoreCase("#339900")) {
                        audio_video.setBackgroundResource(R.color.theme10);
                    } else if (colorcodes.equalsIgnoreCase("#CC9C00")) {
                        audio_video.setBackgroundResource(R.color.theme11);
                    } else if (colorcodes.equalsIgnoreCase("#00B09B")) {
                        audio_video.setBackgroundResource(R.color.theme12);
                    }
                    else if(colorcodes.equalsIgnoreCase("#ffffff")){
                        settings.setBackgroundResource(R.color.theme13);
                    }
                }
            }*/
        }else if(id.equals("3")){
            city.setBackgroundResource(R.color.mytransparent);
            search.setBackgroundResource(R.color.mytransparent);
            audio_video.setBackgroundResource(R.color.mytransparent);
            settings.setBackgroundResource(R.color.mytransparent);
            Fragment selectedFragment = null;
            selectedFragment = HappeningFrag.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();

            happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
            city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            Log.e("Msg","hievent");
            happening.setTextColor(Color.WHITE);
            city.setTextColor(Color.parseColor("#666666"));
            audio_video.setTextColor(Color.parseColor("#666666"));
            settings.setTextColor(Color.parseColor("#666666"));
            /*if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#262626")){
                        happening.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        happening.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        happening.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        happening.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        happening.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        happening.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        happening.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        happening.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        happening.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        happening.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        happening.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        happening.setBackgroundResource(R.color.theme12);
                    }
                    else if(colorcodes.equalsIgnoreCase("#ffffff")){
                        settings.setBackgroundResource(R.color.theme13);
                    }
                }
            }*/
        }else if(id.equals("4")){
            happening.setBackgroundResource(R.color.mytransparent);
            city.setBackgroundResource(R.color.mytransparent);
            audio_video.setBackgroundResource(R.color.mytransparent);
            settings.setBackgroundResource(R.color.mytransparent);
            Fragment selectedFragment = null;
            selectedFragment = MainFrag.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            Log.e("Msg","hievent");
            happening.setTextColor(Color.parseColor("#666666"));
            city.setTextColor(Color.parseColor("#666666"));
            audio_video.setTextColor(Color.parseColor("#666666"));
            settings.setTextColor(Color.parseColor("#666666"));
            /*if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#262626")){
                       search.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        search.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        search.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        search.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        search.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        search.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        search.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        search.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        search.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        search.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        search.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        search.setBackgroundResource(R.color.theme12);
                    }
                    else if(colorcodes.equalsIgnoreCase("#ffffff")){
                        settings.setBackgroundResource(R.color.theme13);
                    }
                }
            }*/
        }
        else if(id.equals("5")){
            happening.setBackgroundResource(R.color.mytransparent);
            search.setBackgroundResource(R.color.mytransparent);
            audio_video.setBackgroundResource(R.color.mytransparent);
            settings.setBackgroundResource(R.color.mytransparent);
            Fragment selectedFragment = null;
            selectedFragment = SettingsFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            settings.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
            happening.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            city.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            audio_video.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            settings.setTextColor(Color.WHITE);
            city.setTextColor(Color.parseColor("#666666"));
            happening.setTextColor(Color.parseColor("#666666"));
            audio_video.setTextColor(Color.parseColor("#666666"));
            /*if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#262626")){
                        city.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        city.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        city.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        city.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        city.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        city.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        city.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        city.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        city.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        city.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        city.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        city.setBackgroundResource(R.color.theme12);
                    }
                    else if(colorcodes.equalsIgnoreCase("#ffffff")){
                        settings.setBackgroundResource(R.color.theme13);
                    }
                }
            }*/
        }
        else {

        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
            return ;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

private void themeselection(){
    dialog = new Dialog(MainPageEnglish.this);
    dialog.setContentView(R.layout.themeandlaguageselect);

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
    Button continue_button=(Button)dialog.findViewById(R.id.continue_button);
    final RadioGroup radioGroup = (RadioGroup)dialog. findViewById(R.id.radiogroup);
    colorone.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
           editor.putString(backgroundcolor, "#262626");
            editor.commit();

        }
    });
    colortwo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#59247c"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#59247c");


            editor.commit();

        }
    });
    colorthree.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#1d487a"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#1d487a");
            editor.commit();

        }
    });
    colorfour.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#7A4100"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#7A4100");
            editor.commit();

        }
    });
    colorfive.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#6E0138"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#6E0138");

            editor.commit();
        }
    });
    colorsix.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#00BFD4"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#00BFD4");

            editor.commit();
        }
    });
    colorseven.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#185546"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#185546");

            editor.commit();
        }
    });
    coloreight.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#D0A06F"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#D0A06F");

            editor.commit();
        }
    });
    colornine.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#82C6E6"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#82C6E6");

            editor.commit();
        }
    });
    colorten.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#339900"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#339900");

            editor.commit();
        }
    });
    coloreleven.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#CC9C00"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#CC9C00");

            editor.commit();
        }
    });
    colortwelve.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#00B09B"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#00B09B");

            editor.commit();
        }
    });
    colorthirteen.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] colors = {Color.parseColor("#FFFFFFFF"), Color.parseColor("#FFFAF6F6")};
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#FFFFFFFF");

            editor.commit();
        }
    });


    closebutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // prefManager.setFirstTimeLaunch(false);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Language, "English");

            editor.commit();
            if(playerid!=null){
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
                        params.put("playerid",playerid);
                        params.put("language","1");
                        return params;
                    }
                };
                RequestQueue likesqueue= Volley.newRequestQueue(getApplicationContext());
                language.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                likesqueue.add(language);
            }else {

            }
            dialog.dismiss();
            Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
            startActivity(i);
            finish();
        }
    });
    continue_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //prefManager.setFirstTimeLaunch(false);
            int selected=radioGroup.getCheckedRadioButtonId();
            RadioButton new_radiobutton=(RadioButton)dialog.findViewById(selected);
            radio_button_values=new_radiobutton.getText().toString();
            //Toast.makeText(getApplicationContext(),radio_button_values, Toast.LENGTH_LONG).show();
            if(radio_button_values.equals("Tamil")){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Language, "Tamil");

                editor.commit();
                if(playerid!=null){
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
                            params.put("playerid",playerid);
                            params.put("language","2");
                            return params;
                        }
                    };
                    RequestQueue likesqueue= Volley.newRequestQueue(getApplicationContext());
                    language.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    likesqueue.add(language);
                }
                Intent i = new Intent(getApplicationContext(), MainPageTamil.class);
                startActivity(i);
                finish();
            }else {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Language, "English");
                editor.commit();
                if(playerid!=null){
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
                            params.put("playerid",playerid);
                            params.put("language","1");
                            return params;
                        }
                    };
                    RequestQueue likesqueue= Volley.newRequestQueue(getApplicationContext());
                    language.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    likesqueue.add(language);
                }
                //dialog.dismiss();
               Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
                startActivity(i);
                finish();
            }
        }
    });

    dialog.show();

}
}

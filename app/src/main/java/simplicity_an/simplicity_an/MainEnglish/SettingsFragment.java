package simplicity_an.simplicity_an.MainEnglish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import simplicity_an.simplicity_an.AboutPage;
import simplicity_an.simplicity_an.Contactpage;
import simplicity_an.simplicity_an.CustomizeNotification;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.OurTeamPage;
import simplicity_an.simplicity_an.PrivacyPolicy;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SavedArticle;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.TermsandCondition;
import simplicity_an.simplicity_an.Utils.Fonts;

/**
 * Created by kuppusamy on 5/19/2017.
 */

public class SettingsFragment extends Fragment {
    ImageButton followuson_twitter,aboutpage,ourteampage,ratethisapp,contactapp,privacyapp,termsapp,savedarticle_button,closebutton,followusnon_instagram,followuson_fb;
    TextView lgin_out,terms,about,ourteam,followus,rate,contact,privacy,language,location,languagedata,locationdata,savedarticle_title,settings_title_text,updateprofile_text;
    NetworkImageView profileimage;
    ArrayAdapter<String> spinnerAdapter;
    TextView notification_title,notification_data;
    Spinner language_spin;
    ArrayList<String> languagelist=new ArrayList<String>();
    public static final String mypreference = "mypref";
    public static final String myACTIVITY = "myactivity";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    SharedPreferences sharedpreferences;
    String myprofileid,username,userimage,notification,language_news="1",user_email;
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    public static final String USERMAILID= "myprofileemail";
    NotificationManagerCompat mNotificationManagerCompat;
    ImageButton specials,events,more;
    String activity,contentid,colorcodes;
    public static final String backgroundcolor = "color";
    public static final String Language = "lamguage";
    Button btnspecials,btnevents,btnmore,city;
    ImageView btnsearch;
    String fontname;
    Typeface tf;
    RelativeLayout mainlayout;
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_settings,container,false);

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {
            contentid=sharedpreferences.getString(CONTENTID,"");
            activity=sharedpreferences.getString(Activity,"");
            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+", "");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        Log.e("ColorCodes",colorcodes+"hihi");
        city=(Button) getActivity().findViewById(R.id.btn_news);
        btnspecials=(Button)getActivity().findViewById(R.id.btn_specials);
        btnevents = (Button)getActivity().findViewById(R.id.btn_events);
        btnsearch = (ImageView) getActivity().findViewById(R.id.btn_citys);
        btnmore = (Button)getActivity().findViewById(R.id.btn_shop);
        if(fontname.equals("playfair")){
            String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";
            tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
        }else {
            String simplycity_title_fontPath = "fonts/SystemSanFranciscoDisplayRegular.ttf";
            tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
        }

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


            SharedPreferences.Editor editor = sharedpreferences.edit();
           editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }
        mNotificationManagerCompat = NotificationManagerCompat.from(getActivity());

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(getActivity());
            notificationManagerCompat.areNotificationsEnabled();
            if(!notificationManagerCompat.areNotificationsEnabled()){
            /*  Intent intent = new Intent();
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", getApplicationInfo().uid);
                startActivity(intent);*/
                notification="no";
            }else {
                notification="yes";
                Log.e("ELSE","else");
            }
        }else {
            notification="yes";
            Log.e("ELSE","else");
        }
        final String fontPath = "fonts/Lora-Regular.ttf";;
        if(activity!=null){
            if(activity.equalsIgnoreCase("notification")){

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(Activity);
                editor.remove(CONTENTID);
                editor.apply();

            }
        }
        if (sharedpreferences.contains(MYUSERID)) {
            myprofileid=sharedpreferences.getString(MYUSERID,"");
            Log.e("MUUSERID:",myprofileid);

            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        if (sharedpreferences.contains(USERNAME)) {
            // name.setText(sharedpreferences.getString(Name, ""));
            username=sharedpreferences.getString(USERNAME,"");
            // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
        }
        if (sharedpreferences.contains(USERIMAGE)) {
            // name.setText(sharedpreferences.getString(Name, ""));
            userimage=sharedpreferences.getString(USERIMAGE,"");
            // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
        }
        if (sharedpreferences.contains(USERMAILID)) {
            // name.setText(sharedpreferences.getString(Name, ""));
            user_email=sharedpreferences.getString(USERMAILID,"");
            // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
        }
        Log.e("MUUSERID:",myprofileid+user_email+userimage+username);
        languagelist.add("English");
        languagelist.add("தமிழ்");
        ImageLoader mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
        followuson_twitter=(ImageButton)view.findViewById(R.id.followsus);
        followusnon_instagram=(ImageButton)view.findViewById(R.id.followsus_instagram);
        followuson_fb=(ImageButton)view.findViewById(R.id.followsus_facebook);
        closebutton=(ImageButton)view.findViewById(R.id.close_settings_button);
        aboutpage=(ImageButton)view.findViewById(R.id.aboutappbutton);
        ourteampage=(ImageButton)view.findViewById(R.id.ourteamappbutton);
        ratethisapp=(ImageButton)view.findViewById(R.id.ratethsappbutton);
        contactapp=(ImageButton)view.findViewById(R.id.contactappbutton);
        termsapp=(ImageButton)view.findViewById(R.id.termsappbutton);
        privacyapp=(ImageButton)view.findViewById(R.id.privacyappbutton);
        savedarticle_button=(ImageButton)view.findViewById(R.id.savearticleappbutton);
        profileimage=(NetworkImageView)view.findViewById(R.id.profile_settings_image) ;
        settings_title_text=(TextView)view.findViewById(R.id.settings_title) ;
        lgin_out=(TextView)view.findViewById(R.id.login_out_button) ;
        terms=(TextView)view.findViewById(R.id.terms) ;
        about=(TextView)view.findViewById(R.id.aboutthis) ;
        ourteam=(TextView)view.findViewById(R.id.ourteam) ;
        followus=(TextView)view.findViewById(R.id.followuson) ;
        rate=(TextView)view.findViewById(R.id.ratethis) ;
        contact=(TextView)view.findViewById(R.id.contact) ;
        privacy=(TextView)view.findViewById(R.id.privacy) ;
        language=(TextView)view.findViewById(R.id.language_title) ;
        location=(TextView)view.findViewById(R.id.locations_title) ;
        // languagedata=(TextView)findViewById(R.id.language_data) ;
        language_spin=(Spinner)view.findViewById(R.id.language_settings_spin);
        locationdata=(TextView)view.findViewById(R.id.location_data) ;
        savedarticle_title=(TextView)view.findViewById(R.id.savearticle) ;
        updateprofile_text=(TextView)view.findViewById(R.id.update_title) ;
        notification_data=(TextView)view.findViewById(R.id.notificationdata_data);
        notification_title=(TextView)view.findViewById(R.id.notification_title);
        if(colorcodes.equals("#FFFFFFFF")){

            btnmore.setTextColor(Color.WHITE);
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));



            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);

        }

        /*if(colorcodes.equals("#FFFFFFFF")){

            btnmore.setTextColor(Color.WHITE);
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           *//* more.setBackgroundResource(R.color.theme13);
            more.setImageResource(R.mipmap.moreone);*//*
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

                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
               *//* more.setBackgroundResource(R.color.theme1button);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#59247c")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
               *//* more.setBackgroundResource(R.color.theme2);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#1d487a")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
                *//*more.setBackgroundResource(R.color.theme3);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#7A4100")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
               *//* more.setBackgroundResource(R.color.theme4);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#6E0138")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
               *//* more.setBackgroundResource(R.color.theme5);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#00BFD4")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
               *//* more.setBackgroundResource(R.color.theme6);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#185546")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
               *//* more.setBackgroundResource(R.color.theme7);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#D0A06F")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
              *//*  more.setBackgroundResource(R.color.theme8);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#82C6E6")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
               *//* more.setBackgroundResource(R.color.theme9);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#339900")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
                *//*more.setBackgroundResource(R.color.theme10);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#CC9C00")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
               *//* more.setBackgroundResource(R.color.theme11);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }
            else if(colorcodes.equals("#00B09B")) {
                btnmore.setTextColor(Color.WHITE);
                btnevents.setTextColor(Color.parseColor("#CCCCCC"));
                city.setTextColor(Color.parseColor("#CCCCCC"));
                btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
              *//*  more.setBackgroundResource(R.color.theme12);
                more.setImageResource(R.mipmap.more);
                city.setBackgroundResource(R.color.mytransparent);
                events.setBackgroundResource(R.color.mytransparent);
                btsearch.setBackgroundResource(R.color.mytransparent);
                specials.setBackgroundResource(R.color.mytransparent);
                city.setImageResource(R.mipmap.news);
                events.setImageResource(R.mipmap.events);
                btsearch.setImageResource(R.mipmap.search);
                specials.setImageResource(R.mipmap.specials);*//*
            }


        }
*/
        profileimage.setImageUrl(userimage,mImageLoader);
        settings_title_text.setTypeface(tf);
        lgin_out.setTypeface(tf);
        terms.setTypeface(tf);
        about.setTypeface(tf);
        ourteam.setTypeface(tf);
        followus.setTypeface(tf);
        rate.setTypeface(tf);
        contact.setTypeface(tf);
        privacy.setTypeface(tf);
        language.setTypeface(tf);
        location.setTypeface(tf);
        locationdata.setTypeface(tf);
        // languagedata.setTypeface(tf);
        savedarticle_title.setTypeface(tf);
        updateprofile_text.setTypeface(tf);
        notification_title.setTypeface(tf);
        notification_data.setTypeface(tf);

      /*  notification_title.setText("Customize Notification");
        notification_data.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrowright, 0, 0, 0);
        notification_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent(getApplicationContext(),CustomizeNotification.class);
                startActivity(data);
            }
        });*/
        if(notification.equals("no")){
            notification_title.setText("Notification");
            notification_data.setText("Enable");
            notification_data.setTextColor(Color.parseColor("#EA5D4D"));
            if(notification_data.getText().equals("Enable")){
                notification_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CallDialog();
                    }
                });
            }
        }else {
            notification_title.setText("Customize Notification");
            notification_data.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrowright, 0, 0, 0);
            if(notification_data.getText().equals("Enable")){

            }else {
                notification_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent data=new Intent(getActivity(),CustomizeNotification.class);
                        startActivity(data);
                    }
                });
            }
        }
        spinnerAdapter=new ArrayAdapter<String>(getActivity(), R.layout.view_spinner_setting,languagelist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        language_spin.setAdapter(spinnerAdapter);

       /* spinnerAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, languagelist)
        {
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(15);
                ((TextView) v).setTypeface(tf);
                //((TextView) v).setCompoundDrawablesWithIntrinsicBounds(R.drawable.droparrows, 0, 0, 0);
                //((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.droparrows, 0);
                // ((TextView) v).setCompoundDrawablePadding(12);

                return v;
            }

            // By using this method we will define how the listview appears after clicking a spinner.
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent){
                View v = super.getDropDownView(position, convertView,
                        parent);
                GradientDrawable gd = new GradientDrawable();


                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(14);
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setPadding(50, 0, 10, 0);
                return v;
            }
        };
        language_spin.setAdapter(spinnerAdapter);*/
        language_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                }else {
                    /*Intent english=new Intent(getActivity(), MainPageTamil.class);
                    startActivity(english);*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(colorcodes.equals("#FFFFFFFF")){
            terms.setTextColor(Color.BLACK);
            about.setTextColor(Color.BLACK);
            ourteam.setTextColor(Color.BLACK);
            followus.setTextColor(Color.BLACK);
            rate.setTextColor(Color.BLACK);
            contact.setTextColor(Color.BLACK);
            privacy.setTextColor(Color.BLACK);
            language.setTextColor(Color.BLACK);
 //                 languagedata.setTextColor(Color.BLACK);
            location.setTextColor(Color.BLACK);
            locationdata.setTextColor(Color.BLACK);
            savedarticle_title.setTextColor(Color.BLACK);
            settings_title_text.setTextColor(Color.BLACK);
            updateprofile_text.setTextColor(Color.BLACK);
            notification_title.setTextColor(Color.BLACK);
            notification_data.setTextColor(Color.BLACK);
            followuson_twitter.setImageResource(R.mipmap.twitterblack);
            followuson_twitter.setBackgroundResource(R.color.mytransparent);
            followusnon_instagram.setBackgroundResource(R.color.mytransparent);
            followusnon_instagram.setImageResource(R.mipmap.instagramblack);
            followuson_fb.setImageResource(R.mipmap.fbblack);
            followuson_fb.setBackgroundResource(R.color.mytransparent);
            aboutpage.setImageResource(R.mipmap.arrowrightblack);
            ourteampage.setImageResource(R.mipmap.arrowrightblack);
            ratethisapp.setImageResource(R.mipmap.arrowrightblack);
            contactapp.setImageResource(R.mipmap.arrowrightblack);
            termsapp.setImageResource(R.mipmap.arrowrightblack);
            privacyapp.setImageResource(R.mipmap.arrowrightblack);
            savedarticle_button.setImageResource(R.mipmap.arrowrightblack);
            language_spin.setBackgroundColor(Color.BLACK);
            notification_data.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.arrowrightblack, 0, 0, 0);

        }



        terms.setText("Terms & Conditions");
        about.setText("About");
        ourteam.setText("Our Team");
        followus.setText("Follow us on");
        rate.setText("Rate this App");
        contact.setText("Contact");
        privacy.setText("Privacy Policy");
        language.setText("Language");
        //  languagedata.setText("English");
        location.setText("LocationSelection");
        locationdata.setText("Coimbatore");
        savedarticle_title.setText("Saved Article");
        settings_title_text.setText("Settings");

        if(myprofileid!=null){
            lgin_out.setText("Signed in as "+username+"- Sign Out");
            updateprofile_text.setText("Update Profile");
        }else {
            lgin_out.setText("Sign In");
            updateprofile_text.setVisibility(View.GONE);
        }
        lgin_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lgin_out.getText().toString().equalsIgnoreCase("Signed in as "+username+"- Sign Out")){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(Activity);
                    editor.remove(CONTENTID);
                    editor.remove(MYUSERID);
                    editor.remove(USERMAILID);
                    editor.remove(USERIMAGE);
                    editor.remove(USERNAME);
                    editor.apply();
                    lgin_out.setText("Sign In");
                    Intent signin=new Intent(getActivity(),MainPageEnglish.class);
                    startActivity(signin);
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "notifications");
                    editor.putString(CONTENTID, "0");
                    editor.commit();
                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                    startActivity(signin);
                    lgin_out.setText("Signed in as "+username+"- Sign Out");
                }
            }
        });
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent close=new Intent(getApplicationContext(),MainActivityVersiontwo.class);
                startActivity(close);
                finish();*/
                //onBackPressed();
            }
        });
        aboutpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about=new Intent(getActivity(),AboutPage.class);
                startActivity(about);

            }
        });
        ourteampage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ourteam=new Intent(getActivity(),OurTeamPage.class);
                startActivity(ourteam);
            }
        });
        ratethisapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=simplicity_an.simplicity_an&hl=en"));
                startActivity(i);
            }
        });
        followuson_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookfollow = new Intent(Intent.ACTION_VIEW);
                facebookfollow.setData(Uri.parse("https://twitter.com/simplicitycbe"));
                startActivity(facebookfollow);
            }
        });
        followuson_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookfollow = new Intent(Intent.ACTION_VIEW);
                facebookfollow.setData(Uri.parse("https://www.facebook.com/simplicitycoimbatore"));
                startActivity(facebookfollow);
            }
        });
        followusnon_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookfollow = new Intent(Intent.ACTION_VIEW);
                facebookfollow.setData(Uri.parse("https://www.instagram.com/simplicitycoimbatore/"));
                startActivity(facebookfollow);
            }
        });

        contactapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ourteam=new Intent(getActivity(),Contactpage.class);
                startActivity(ourteam);
            }
        });

        privacyapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ourteam=new Intent(getActivity(),PrivacyPolicy.class);
                startActivity(ourteam);
            }
        });
        termsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ourteam=new Intent(getActivity(),TermsandCondition.class);
                startActivity(ourteam);
            }
        });
        savedarticle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent savearticle=new Intent(getActivity(),SavedArticle.class);
                startActivity(savearticle);
            }
        });
        return view;
    }
    private void  CallDialog(){
        //  AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        if(language_news.equalsIgnoreCase("1")) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
            //   AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            //alertDialog.setTitle("Delete Post");

            // Setting Dialog Message
            alertDialog.setMessage("SimpliCity works best with  notifications. Please Turn ON notifications in  SimpliCity Settings");

            // Setting Icon to Dialog

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event

                    dialog.cancel();
                }
            });
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
                    notificationManagerCompat.areNotificationsEnabled();
                    if (!notificationManagerCompat.areNotificationsEnabled()) {
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package", getActivity().getPackageName());
                        intent.putExtra("app_uid", getActivity().getApplicationInfo().uid);
                        startActivity(intent);
                    } else {
                        Log.e("ELSE", "else");
                    }
                    // }

                }
            }).create();


            // Setting Negative "NO" Button


            // Showing Alert Message
            alertDialog.show();

        }else {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
            //alertDialog.setTitle("Delete Post");

            // Setting Dialog Message
            alertDialog.setMessage("சிம்ப்ளிசிட்டி அறிவிப்புகளை நீங்கள் பெற , சிம்ப்ளிசிட்டி பயன்பாட்டுத்தகவல் > அறிவிப்பை காமி என்பதை கிளிக் செய்வதன் மூலம் பெறலாம் ");

            // Setting Icon to Dialog

            alertDialog.setNegativeButton("நீக்கு", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event

                    dialog.cancel();
                }
            });
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("செயல்படுத்த", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
                    notificationManagerCompat.areNotificationsEnabled();
                    if (!notificationManagerCompat.areNotificationsEnabled()) {
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package",getActivity().getPackageName());
                        intent.putExtra("app_uid", getActivity().getApplicationInfo().uid);
                        startActivity(intent);
                    } else {
                        Log.e("ELSE", "else");
                    }


                }
            });

            // Setting Negative "NO" Button


            // Showing Alert Message
            alertDialog.show();
        }
    }
}

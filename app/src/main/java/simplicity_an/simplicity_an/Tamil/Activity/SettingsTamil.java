package simplicity_an.simplicity_an.Tamil.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import simplicity_an.simplicity_an.AboutPage;
import simplicity_an.simplicity_an.Contactpage;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.OurTeamPage;
import simplicity_an.simplicity_an.PrivacyPolicy;
import simplicity_an.simplicity_an.R;

import simplicity_an.simplicity_an.SigninpageActivity;
//import simplicity_an.simplicity_an.Tamil.MainPageTamil;
import simplicity_an.simplicity_an.TermsandCondition;

/**
 * Created by kuppusamy on 10/5/2016.
 */
public class SettingsTamil extends AppCompatActivity {
    ImageButton followuson_twitter,aboutpage,ourteampage,ratethisapp,contactapp,privacyapp,termsapp,savedarticle_button,closebutton,followusnon_instagram,followuson_fb;
    TextView lgin_out,terms,about,ourteam,followus,rate,contact,privacy,language,location,languagedata,locationdata,savedarticle_title,settings_title_text,updateprofile_text;
    NetworkImageView profileimage;
    TextView notification_title,notification_data;
    NotificationManagerCompat mNotificationManagerCompat;
Spinner language_spin;
    ArrayAdapter<String> spinnerAdapter;
    public static final String mypreference = "mypref";
    public static final String myACTIVITY = "myactivity";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    public static final String USERMAILID= "myprofileemail";
    SharedPreferences sharedpreferences;
    String contentid,activity,myprofileid,username,userimage,user_email,notification,language_news="2";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    ArrayList<String> languagelist = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.frag_settings);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
     final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {
            contentid=sharedpreferences.getString(CONTENTID,"");
            activity=sharedpreferences.getString(Activity,"");
            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+", "");
        }
        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());


           NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(getApplicationContext());
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
        languagelist.add("தமிழ்");
        languagelist.add("English");
        ImageLoader mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
        followuson_twitter=(ImageButton)findViewById(R.id.followsus);
        followusnon_instagram=(ImageButton)findViewById(R.id.followsus_instagram);
        followuson_fb=(ImageButton)findViewById(R.id.followsus_facebook);
        closebutton=(ImageButton)findViewById(R.id.close_settings_button);
        aboutpage=(ImageButton)findViewById(R.id.aboutappbutton);
        ourteampage=(ImageButton)findViewById(R.id.ourteamappbutton);
        ratethisapp=(ImageButton)findViewById(R.id.ratethsappbutton);
        contactapp=(ImageButton)findViewById(R.id.contactappbutton);
        termsapp=(ImageButton)findViewById(R.id.termsappbutton);
        privacyapp=(ImageButton)findViewById(R.id.privacyappbutton);
        savedarticle_button=(ImageButton)findViewById(R.id.savearticleappbutton);
        profileimage=(NetworkImageView)findViewById(R.id.profile_settings_image) ;
        settings_title_text=(TextView)findViewById(R.id.settings_title) ;
        lgin_out=(TextView)findViewById(R.id.login_out_button) ;
        terms=(TextView)findViewById(R.id.terms) ;
        about=(TextView)findViewById(R.id.aboutthis) ;
        ourteam=(TextView)findViewById(R.id.ourteam) ;
        followus=(TextView)findViewById(R.id.followuson) ;
        rate=(TextView)findViewById(R.id.ratethis) ;
        contact=(TextView)findViewById(R.id.contact) ;
        privacy=(TextView)findViewById(R.id.privacy) ;
        language=(TextView)findViewById(R.id.language_title) ;
        location=(TextView)findViewById(R.id.locations_title) ;
        //languagedata=(TextView)findViewById(R.id.language_data) ;
        language_spin=(Spinner)findViewById(R.id.language_settings_spin);
        locationdata=(TextView)findViewById(R.id.location_data) ;
        savedarticle_title=(TextView)findViewById(R.id.savearticle) ;
        updateprofile_text=(TextView)findViewById(R.id.update_title) ;
        notification_data=(TextView)findViewById(R.id.notificationdata_data);
        notification_title=(TextView)findViewById(R.id.notification_title);
        spinnerAdapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.view_spinner_setting,languagelist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        language_spin.setAdapter(spinnerAdapter);
       /* spinnerAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, languagelist)
     {
            public View getView(int position, View convertView,
                    ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextColor(Color.parseColor("#f93030"));
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
        /*language_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                }else {
                    Intent english=new Intent(getApplicationContext(), Settings.class);
                    startActivity(english);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
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
        //languagedata.setTypeface(tf);
        savedarticle_title.setTypeface(tf);
        updateprofile_text.setTypeface(tf);
        notification_title.setTypeface(tf);
        notification_data.setTypeface(tf);

       /* notification_title.setText("நோட்டிபிகேஷன்");
        notification_data.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrowright, 0, 0, 0);
        notification_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent(getApplicationContext(),CustomizeNotificationTamil.class);
                startActivity(data);
            }
        });*/
       if(notification.equalsIgnoreCase("no")){
            notification_title.setText("அறிவிப்பு");
            notification_data.setText("செயல்படுத்த");
            notification_data.setTextColor(Color.parseColor("#EA5D4D"));

                notification_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CallDialog();
                    }
                });

        }else {
            notification_title.setText("நோட்டிபிகேஷன்");
            notification_data.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrowright, 0, 0, 0);

                notification_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"TAMIL NOTIFY", Toast.LENGTH_SHORT).show();
                    }
                });

        }


        terms.setText("நிபந்தனைகள்");
        about.setText("எங்களை பற்றி");
        ourteam.setText(" எங்கள் அணி");
        followus.setText("சமூக வலைத்தளங்களில்");
        rate.setText("மதிப்பிடுக");
        contact.setText("தொடர்பு ");
        privacy.setText("தனியுரிமை  நிபந்தனைகள் ");
        language.setText("மொழி");
       // languagedata.setText("English");
        location.setText("இடம்");
        locationdata.setText("கோவை");
        savedarticle_title.setText(" சேமித்த கட்டுரை ");
        settings_title_text.setText("அமைப்பு");

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
                    Intent signin=new Intent(getApplicationContext(),MainPageTamil.class);
                    startActivity(signin);
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "notification");
                    editor.putString(CONTENTID, "0");
                    editor.commit();
                    Intent signin=new Intent(getApplicationContext(),SigninpageActivity.class);
                    startActivity(signin);
                    lgin_out.setText("Signed in as "+username+"- Sign Out");
                }
            }
        });
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent close=new Intent(getApplicationContext(),MainPageTamil.class);
                startActivity(close);
                finish();*/
                onBackPressed();
            }
        });
        aboutpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about=new Intent(getApplicationContext(),AboutPage.class);
                startActivity(about);

            }
        });
        ourteampage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ourteam=new Intent(getApplicationContext(),OurTeamPage.class);
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
                facebookfollow.setData(Uri.parse("https://www.facebook.com/%E0%AE%9A%E0%AE%BF%E0%AE%AE%E0%AF%8D%E0%AE%AA%E0%AF%8D%E0%AE%B3%E0%AE%BF%E0%AE%9A%E0%AE%BF%E0%AE%9F%E0%AF%8D%E0%AE%9F%E0%AE%BF-%E0%AE%95%E0%AF%8B%E0%AE%B5%E0%AF%88-212090702537256/"));
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
                Intent ourteam=new Intent(getApplicationContext(),Contactpage.class);
                startActivity(ourteam);
            }
        });

        privacyapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ourteam=new Intent(getApplicationContext(),PrivacyPolicy.class);
                startActivity(ourteam);
            }
        });
        termsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ourteam=new Intent(getApplicationContext(),TermsandCondition.class);
                startActivity(ourteam);
            }
        });
        savedarticle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent savearticle=new Intent(getApplicationContext(),SavedArticleTamil.class);
                startActivity(savearticle);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void  CallDialog(){
        //  AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        if(language_news.equalsIgnoreCase("1")) {
            final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(this, R.style.MyDialogTheme);
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

                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                        notificationManagerCompat.areNotificationsEnabled();
                        if (!notificationManagerCompat.areNotificationsEnabled()) {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                            intent.putExtra("app_package", getPackageName());
                            intent.putExtra("app_uid", getApplicationInfo().uid);
                            startActivity(intent);
                        } else {
                            Log.e("ELSE", "else");
                        }


                }
            });

            // Setting Negative "NO" Button


            // Showing Alert Message
            alertDialog.show();
        }else {
            final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(this, R.style.MyDialogTheme);
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

                       NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                        notificationManagerCompat.areNotificationsEnabled();
                        if (!notificationManagerCompat.areNotificationsEnabled()) {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                            intent.putExtra("app_package", getPackageName());
                            intent.putExtra("app_uid", getApplicationInfo().uid);
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

package simplicity_an.simplicity_an;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by kuppusamy on 7/4/2016.
 */

public class Notificationsettingmy extends Fragment {
  ImageButton followuson,aboutpage,ourteampage,ratethisapp,contactapp,privacyapp,termsapp,savedarticle_button;
    TextView lgin_out,terms,about,ourteam,followus,rate,contact,privacy,language,location,languagedata,locationdata,savedarticle_title,settings_title_text;
    NetworkImageView profileimage;

    public static final String mypreference = "mypref";
    public static final String myACTIVITY = "myactivity";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    SharedPreferences sharedpreferences;
    String contentid,activity,myprofileid,username,userimage;
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    public Notificationsettingmy(){

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_settings, container, false);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
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
        ImageLoader  mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
followuson=(ImageButton)view.findViewById(R.id.followsus);
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
       // languagedata=(TextView)view.findViewById(R.id.language_data) ;
        locationdata=(TextView)view.findViewById(R.id.location_data) ;
        savedarticle_title=(TextView)view.findViewById(R.id.savearticle) ;

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

        terms.setText("Terms & Conditions");
        about.setText("About");
        ourteam.setText("Our Team");
        followus.setText("Follow us on");
        rate.setText("Rate this App");
        contact.setText("Contact");
        privacy.setText("Privacy Policy");
        language.setText("Language");
       // languagedata.setText("English");
        location.setText("LocationSelection");
        locationdata.setText("Coimbatore");
        savedarticle_title.setText("Saved Article");
        settings_title_text.setText("Settings");
        if(myprofileid!=null){
            lgin_out.setText("Signed in as "+username+"- Sign Out");
        }else {
            lgin_out.setText("Sign In");
        }
        lgin_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lgin_out.getText().toString().equalsIgnoreCase("Signed in as "+username+"- Sign Out")){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(Activity);
                    editor.remove(CONTENTID);
                    editor.apply();
                    lgin_out.setText("Sign In");
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "notification");
                    editor.putString(CONTENTID, "0");
                    editor.commit();
                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                    startActivity(signin);
                    lgin_out.setText("Signed in as "+username+"- Sign Out");
                }
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
        followuson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookfollow = new Intent(Intent.ACTION_VIEW);
                facebookfollow.setData(Uri.parse("https://www.facebook.com/simplicitycoimbatore"));
                startActivity(facebookfollow);
            }
        });

        contactapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        privacyapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
termsapp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});

       /* if(myprofileid==null){
            signouts.setText("Sign In");
            //signout.setTransformationMethod(null);
        }else {
            signouts.setText("Sign Out");

           // signout.setTransformationMethod(null);
        }*/
      /*  signouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signouts.getText().equals("Sign Out")) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(Activity);
                    editor.remove(CONTENTID);
                    editor.apply();
                    signouts.setText("Sign In");
                 //   signout.setTransformationMethod(null);
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "notification");
                    editor.putString(CONTENTID, "0");
                    editor.commit();
                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                    startActivity(signin);
                    getActivity().finish();
                    signouts.setText("Sign Out");
                   // signout.setTransformationMethod(null);
                }
            }
        });*/
        return view;

    }

}

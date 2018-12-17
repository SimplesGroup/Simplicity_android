package simplicity_an.simplicity_an;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by kuppusamy on 4/7/2016.
 */
public class SigninpageActivity extends android.app.Activity {
    Button closebutton;
    String activity_name;
    TextView needsign_typeaccount,facebooksignin,googlesignin;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";

    String activity,contentid;
    RelativeLayout gmail_login_layout,facebook_login_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signinpage);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Activity)) {

            activity=sharedpreferences.getString(Activity,"");
            Log.e("GCMID:",activity);
           // Log.e("IMAGEURL:",profilepicturefromfacebook);

        }
        Intent get=getIntent();
        activity_name=get.getStringExtra("ACTIVITY");
        //LinearLayout layout =(LinearLayout)findViewById(R.id.signinpagexml);
       /* if(activity_name.equals("citycenter")){
            layout.setBackgroundResource(R.drawable.city);
        }else if (activity_name.equals("news")){
            layout.setBackgroundResource(R.drawable.newsbg);
        }else if(activity_name.equals("article")){
            layout.setBackgroundResource(R.drawable.custombgtwo);
        }else if(activity_name.equals("events")){
            layout.setBackgroundResource(R.drawable.eventsbg);
        }else if (activity_name.equals("sports")){
            layout.setBackgroundResource(R.drawable.sportsbg);
        }else if(activity_name.equals("govt")){
            layout.setBackgroundResource(R.drawable.governmentnotificationsbg);
        }else if (activity_name.equals("jobs")){
            layout.setBackgroundResource(R.drawable.jobsbg);
        }else if (activity_name.equals("science")){
            layout.setBackgroundResource(R.drawable.scienceandtechnology);
        }else if (activity_name.equals("radio")){
            layout.setBackgroundResource(R.drawable.radiobg);
        }else if (activity_name.equals("music")){
            layout.setBackgroundResource(R.drawable.musicbg);
        }else if (activity_name.equals("theatre")){
            layout.setBackgroundResource(R.drawable.theatrebg);
        }else if (activity_name.equals("agriculture")){
            layout.setBackgroundResource(R.drawable.farmingagriculturebg);
        }else if (activity_name.equals("doit")){
            layout.setBackgroundResource(R.drawable.doitbg);
        }else if (activity_name.equals("travel")){
            layout.setBackgroundResource(R.drawable.travelsbg);
        }else if (activity_name.equals("directories")){
            layout.setBackgroundResource(R.drawable.directoriesbg);
        }else if(activity_name.equals("rentals")){
            layout.setBackgroundResource(R.drawable.custombgtwo);
        }*/
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

        closebutton=(Button)findViewById(R.id.close_button_sign);
        facebooksignin=(TextView)findViewById(R.id.facebooklogin) ;
        googlesignin=(TextView)findViewById(R.id.gmaillogin) ;
        gmail_login_layout=(RelativeLayout)findViewById(R.id.gmaillayout);
        facebook_login_layout=(RelativeLayout)findViewById(R.id.facebooklayout);

        //needsign_typeaccount=(TextView)findViewById(R.id.sign_needtext);
        //facebooksignin=(TextView)findViewById(R.id.facebook_logintext);
      //  googlesignin=(TextView)findViewById(R.id.google_logintext);
        facebooksignin.setTypeface(seguiregular);
        googlesignin.setTypeface(seguiregular);
        facebooksignin.setText("Sign in with Facebook");
        googlesignin.setText("Sign in with Google");
        facebooksignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook=new Intent(getApplicationContext(),FaceBooklogin.class);
                startActivity(facebook);
            }
        });
        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gmailsignin=new Intent(getApplicationContext(),GoogleSignintwo.class);
                startActivity(gmailsignin);
            }
        });
        gmail_login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gmailsignin=new Intent(getApplicationContext(),GoogleSignintwo.class);
                startActivity(gmailsignin);
            }
        });
        facebook_login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook=new Intent(getApplicationContext(),FaceBooklogin.class);
                startActivity(facebook);
            }
        });
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(activity.equalsIgnoreCase("articledesc")){
                    Intent back=new Intent(getApplicationContext(),Articledescription.class);
                    startActivity(back);
                }else if(activity.equalsIgnoreCase("newsdesc")){
                    Intent back=new Intent(getApplicationContext(),NewsDescription.class);
                    startActivity(back);
                }else if(activity.equalsIgnoreCase("eventdesc")){
                    Intent backevent=new Intent(getApplicationContext(),EventsDescription.class);
                    startActivity(backevent);
                }else if(activity.equalsIgnoreCase("sportsdesc")){
                    Intent backsports=new Intent(getApplicationContext(),SportsnewsDescription.class);
                    startActivity(backsports);
                }else if(activity.equalsIgnoreCase("govtdesc")){
                    Intent backgovt=new Intent(getApplicationContext(),GovernmentnotificationsDescriptions.class);
                    startActivity(backgovt);
                }else if(activity.equalsIgnoreCase("sciencedesc")){
                    Intent backscience=new Intent(getApplicationContext(),ScienceandTechnologyDescription.class);
                    startActivity(backscience);
                }else if(activity.equalsIgnoreCase("doitdesc")){
                    Intent backdoit=new Intent(getApplicationContext(),DoitDescription.class);
                    startActivity(backdoit);
                }else if(activity.equalsIgnoreCase("edunewsdesc")){
                    Intent backeducationnews=new Intent(getApplicationContext(),EducationDescription.class);
                    startActivity(backeducationnews);
                }else if(activity.equalsIgnoreCase("eduintsdesc")){
                    Intent backeducationints=new Intent(getApplicationContext(),EducationInstitutionsDescription.class);
                    startActivity(backeducationints);
                }else if(activity.equalsIgnoreCase("edulearndesc")){
                    Intent backedulearn=new Intent(getApplicationContext(),EducationLearningDescription.class);
                    startActivity(backedulearn);
                }else if(activity.equalsIgnoreCase("farmingdesc")){
                    Intent backfarming=new Intent(getApplicationContext(),Farmingdescription.class);
                    startActivity(backfarming);
                }else if(activity.equalsIgnoreCase("healthdesc")){
                    Intent backhealth=new Intent(getApplicationContext(),Healthylivingdescription.class);
                    startActivity(backhealth);
                }else if(activity.equalsIgnoreCase("fooddesc")){
                    Intent backfood=new Intent(getApplicationContext(),FoodAndCookDescriptionPage.class);
                    startActivity(backfood);
                }else if(activity.equalsIgnoreCase("tipsdesc")){
                    Intent backfoodtip=new Intent(getApplicationContext(),TipsDescription.class);
                    startActivity(backfoodtip);
                }else if(activity.equalsIgnoreCase("traveldesc")){
                    Intent backtravel=new Intent(getApplicationContext(),TravelsDescription.class);
                    startActivity(backtravel);
                }else if(activity.equalsIgnoreCase("photoesc")){
                    Intent backphoto=new Intent(getApplicationContext(),PhotoStories.class);
                    startActivity(backphoto);
                }else if(activity.equalsIgnoreCase("radiofav")){
                    Intent backradiofav=new Intent(getApplicationContext(),Radioplayerfavplaylist.class);
                    startActivity(backradiofav);
                }
                else if(activity.equalsIgnoreCase("radio")){
                    Intent backradio=new Intent(getApplicationContext(),Radioplayeractivity.class);
                    startActivity(backradio);
                }else if(activity.equalsIgnoreCase("musicfav")){
                    Intent backmusicfav=new Intent(getApplicationContext(),Musicfavplayerpage.class);
                    startActivity(backmusicfav);
                }else if(activity.equalsIgnoreCase("musicnew")){
                    Intent backmusicnew=new Intent(getApplicationContext(),MusicnewReleaseplayer.class);
                    startActivity(backmusicnew);
                }else if(activity.equalsIgnoreCase("music")){
                    Intent backmusic= new Intent(getApplicationContext(),Musicplayerpage.class);
                    startActivity(backmusic);
                }else if(activity.equalsIgnoreCase("theatre")){
                    Intent backtheatre= new Intent(getApplicationContext(),TheatreItemDetail.class);
                    startActivity(backtheatre);
                }else if(activity.equalsIgnoreCase("citycenter")){
                    Intent citycenter= new Intent(getApplicationContext(),CityCenter.class);
                    startActivity(citycenter);
                }else if(activity.equalsIgnoreCase("citycenterprofile")){
                    Intent citycenterprofile= new Intent(getApplicationContext(),CityProfilePage.class);
                    startActivity(citycenterprofile);
                }else if(activity.equalsIgnoreCase("notifications")){
                    Intent notifymyprofilee= new Intent(getApplicationContext(),Settings.class);
                    startActivity(notifymyprofilee);
                }else if(activity.equalsIgnoreCase("mainversion")){
                    Intent mainversion= new Intent(getApplicationContext(),MainActivityVersiontwo.class);
                    startActivity(mainversion);
                }else if(activity.equalsIgnoreCase("entertainmentversion")){
                    Intent entertainversion=new Intent(getApplicationContext(),EntertainmentVersiontwo.class);
                    startActivity(entertainversion);
                }else if(activity.equalsIgnoreCase("beyondversion")){
                    Intent entertainversion=new Intent(getApplicationContext(),BeyondActivity.class);
                    startActivity(entertainversion);
                }else if(activity.equalsIgnoreCase("mainversiontamil")){
                    Intent mainversion= new Intent(getApplicationContext(),MainPageTamil.class);
                    startActivity(mainversion);
                }else if(activity.equalsIgnoreCase("entertainmentversiontamil")){
                    Intent entertainversion=new Intent(getApplicationContext(),TamilEntertainment.class);
                    startActivity(entertainversion);
                }else if(activity.equalsIgnoreCase("beyondversiontamil")){
                    Intent entertainversion=new Intent(getApplicationContext(),TamilBeyond.class);
                    startActivity(entertainversion);
                }
                else if(activity.equalsIgnoreCase("articledesctamil")){
                    Intent back=new Intent(getApplicationContext(),TamilArticledescription.class);
                    startActivity(back);
                }else if(activity.equalsIgnoreCase("newsdesctamil")) {
                    Intent back=new Intent(getApplicationContext(),TamilNewsDescription.class);
                    startActivity(back);
                }else if(activity.equalsIgnoreCase("eventdesctamil")){
                    Intent backevent=new Intent(getApplicationContext(),TamilEventsDescription.class);
                    startActivity(backevent);
                }else if(activity.equalsIgnoreCase("sportsdesctamil")){
                    Intent backsports=new Intent(getApplicationContext(),TamilSportsnewsDescription.class);
                    startActivity(backsports);
                }else if(activity.equalsIgnoreCase("govtdesctamil")){
                    Intent backgovt=new Intent(getApplicationContext(),Govtdescriptiontamil.class);
                    startActivity(backgovt);
                }else if(activity.equalsIgnoreCase("sciencedesctamil")){
                    Intent backscience=new Intent(getApplicationContext(),ScienceandTechnologyDescriptiontamil.class);
                    startActivity(backscience);

                }else if(activity.equalsIgnoreCase("doitdesctamil")){
                    Intent backdoit=new Intent(getApplicationContext(),DoitDescriptiontamil.class);
                    startActivity(backdoit);
                }else if(activity.equalsIgnoreCase("edunewsdesctamil")){
                    Intent backeducationnews=new Intent(getApplicationContext(),EducationDescriptiontamil.class);
                    startActivity(backeducationnews);
                }else if(activity.equalsIgnoreCase("eduintsdesctamil")){
                    Intent backeducationints=new Intent(getApplicationContext(),EducationInstitutionsDescriptiontamil.class);
                    startActivity(backeducationints);
                }else if(activity.equalsIgnoreCase("edulearndesctamil")){
                    Intent backedulearn=new Intent(getApplicationContext(),EducationLearningDescriptiontamil.class);
                    startActivity(backedulearn);
                }else if(activity.equalsIgnoreCase("farmingdesctamil")){
                    Intent backfarming=new Intent(getApplicationContext(),Farmingdescriptiontamil.class);
                    startActivity(backfarming);
                }else if(activity.equalsIgnoreCase("healthdesctamil")){
                    Intent backhealth=new Intent(getApplicationContext(),Healthdescriptiontamil.class);
                    startActivity(backhealth);
                }else if(activity.equalsIgnoreCase("fooddesctamil")){
                    Intent backfood=new Intent(getApplicationContext(),FoodAndCookDescriptionPagetamil.class);
                    startActivity(backfood);
                }else if(activity.equalsIgnoreCase("tipsdesctamil")){
                    Intent backfoodtip=new Intent(getApplicationContext(),TipsDescriptionTamil.class);
                    startActivity(backfoodtip);
                }else if(activity.equalsIgnoreCase("traveldesctamil")){
                    Intent backtravel=new Intent(getApplicationContext(),TravelsDescriptiontamil.class);
                    startActivity(backtravel);
                }else if(activity.equalsIgnoreCase("radiofavtamil")){
                    Intent backradiofav=new Intent(getApplicationContext(),Radioplayerfavplaylisttamil.class);
                    startActivity(backradiofav);
                }else if(activity.equalsIgnoreCase("radiotamil")){
                    Intent backradio=new Intent(getApplicationContext(),Radioplayeractivitytamil.class);
                    startActivity(backradio);
                }*/
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

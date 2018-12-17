package simplicity_an.simplicity_an.MainEnglish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

import simplicity_an.simplicity_an.Articleoftheday;
import simplicity_an.simplicity_an.Directories;
import simplicity_an.simplicity_an.Education;
import simplicity_an.simplicity_an.Events;
import simplicity_an.simplicity_an.FarmingAgricultureActivity;
import simplicity_an.simplicity_an.FoodActivity;
import simplicity_an.simplicity_an.GCMRegistrationIntentService;
import simplicity_an.simplicity_an.GovernmentNotifications;
import simplicity_an.simplicity_an.HealthAndLiving;
import simplicity_an.simplicity_an.JobsActivity;
import simplicity_an.simplicity_an.MusicActivity;
import simplicity_an.simplicity_an.NewsActivity;
import simplicity_an.simplicity_an.PhotoStories;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.RadioActivity;
import simplicity_an.simplicity_an.ScienceandTechnology;
import simplicity_an.simplicity_an.SimplicitySearchview;
import simplicity_an.simplicity_an.Sports;
import simplicity_an.simplicity_an.Theatre;
import simplicity_an.simplicity_an.TravelsActivity;

/**
 * Created by kuppusamy on 5/19/2017.
 */

public class MainFrag extends Fragment {
    SharedPreferences sharedpreferences;
    String myuserids;
    public static final String GcmId = "gcmid";
    public static final String MYUSERID= "myprofileid";
    private static int SPLASH_TIME_OUT = 3000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static final String mypreference = "mypref";
    private final String TAG_REQUEST = "MY_TAG";
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid;

    String msg = "Android : ";
    Button foodandcook,article,jobs,farming_agriculture,news,healthiliving,govt_notifications,events,education,scienceandtech,directories;
    Button sports,radio,theatre,citycenter,travels,photostories,music;
    ImageButton beyond,citycenterbuttton,entertainment,notification,homebutton,closebuttton;
    android.support.v7.widget.SearchView search;
    String search_value;
    Typeface barkentina;
    String notification_counts;
    TextView toolbartitle,notification_batge_count;
    TextView footer_latest,footer_city,footer_home,footer_entertainment,footer_notifications;
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE;
    int value;
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    RequestQueue requestQueue;
    String activity,contentid,colorcodes;
    public static final String backgroundcolor = "color";
    public static final String Language = "lamguage";
    public static final String CONTENTID = "contentid";


    Button btnspecials,btnevents,btnmore,city;
    ImageView btnsearch;
    public static MainFrag newInstance() {
        MainFrag fragment = new MainFrag();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mainpagetwo,container,false);
        sharedpreferences =  getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            Log.e("MUUSERID:",myprofileid);
        }
        url_notification_count_valueget=url_noti_count+myprofileid;
        Log.e("notification Url:",url_notification_count_valueget);
        contentid=sharedpreferences.getString(CONTENTID,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        //   colorcodes = colorcodes.replaceAll("\\D+","");
        Log.e("ColorCodes",colorcodes+"hihi");
        URLPOSTQTYPE=urlpost;
        requestQueue= Volley.newRequestQueue(getActivity());
        String fontPathbarkendina = "fonts/Barkentina.otf";
        barkentina = Typeface.createFromAsset(getActivity().getAssets(), fontPathbarkendina);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
        city=(Button) getActivity().findViewById(R.id.btn_news);
        btnspecials=(Button)getActivity().findViewById(R.id.btn_specials);
        btnevents = (Button)getActivity().findViewById(R.id.btn_events);
        btnsearch = (ImageView) getActivity().findViewById(R.id.btn_citys);
        btnmore = (Button)getActivity().findViewById(R.id.btn_shop);
        toolbartitle=(TextView)view.findViewById(R.id.simplicitynewtitle);
        toolbartitle.setTypeface(barkentina);
      search=(android.support.v7.widget.SearchView)view.findViewById(R.id.searchview_main);
        search.setActivated(true);
        search.setQueryHint("Search for anything in Coimbatore");
        search.onActionViewExpanded();
        search.setIconified(false);
        search.clearFocus();
        toolbartitle.setText("SimpliCity");


        setupSearchView();

        if(colorcodes.equals("#FFFFFFFF")){
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme13);
            btnsearch.setImageResource(R.mipmap.searchone);*/
           /* city.setBackgroundResource(R.color.white);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else   if(colorcodes.equals("#262626")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme1button);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#59247c")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme2);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#1d487a")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme3);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#7A4100")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme4);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#6E0138")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
            /*btnsearch.setBackgroundResource(R.color.theme5);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#00BFD4")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
            /*btnsearch.setBackgroundResource(R.color.theme6);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#185546")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme7);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#D0A06F")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
            /*btnsearch.setBackgroundResource(R.color.theme8);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#82C6E6")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
            /*btnsearch.setBackgroundResource(R.color.theme9);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#339900")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme10);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#CC9C00")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme11);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        else if(colorcodes.equals("#00B09B")) {
            btnmore.setTextColor(Color.parseColor("#CCCCCC"));
            btnevents.setTextColor(Color.parseColor("#CCCCCC"));
            city.setTextColor(Color.parseColor("#CCCCCC"));
            btnspecials.setTextColor(Color.parseColor("#CCCCCC"));
           /* btnsearch.setBackgroundResource(R.color.theme12);
            btnsearch.setImageResource(R.mipmap.search);
            city.setBackgroundResource(R.color.mytransparent);
            btnevents.setBackgroundResource(R.color.mytransparent);
            btnmore.setBackgroundResource(R.color.mytransparent);
            btnspecials.setBackgroundResource(R.color.mytransparent);
            city.setImageResource(R.mipmap.news);
            btnevents.setImageResource(R.mipmap.events);
            btnmore.setImageResource(R.mipmap.more);
            btnspecials.setImageResource(R.mipmap.specials);*/
        }
        


        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


            }
        });



        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub


                search_value = query;

                Intent simplicity = new Intent(getActivity(), SimplicitySearchview.class);
                simplicity.putExtra("QUERY", search_value);
                startActivity(simplicity);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        });


        closebuttton=(ImageButton)view.findViewById(R.id.close_mainpage);
        closebuttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				/*Intent main_page=new Intent(MainActivity.this,MainActivityVersiontwo.class);
				startActivity(main_page);
				finish();*/
              //  onBackPressed();
            }
        });
		/*beyond=(ImageButton)findViewById(R.id.btn_versiontwobeyond);
		citycenterbuttton=(ImageButton)findViewById(R.id.btn_versiontwocity);
		entertainment=(ImageButton)findViewById(R.id.btn_versiontwoexplore) ;
		notification=(ImageButton)findViewById(R.id.btn_versiontwonotifications);
		homebutton=(ImageButton)findViewById(R.id.btn_versiontwosearch);
		footer_latest=(TextView)findViewById(R.id.footer_latest);
		footer_city=(TextView)findViewById(R.id.footer_city);
		footer_home=(TextView)findViewById(R.id.footer_home);
		footer_entertainment=(TextView)findViewById(R.id.footer_entertainment);
		footer_notifications=(TextView)findViewById(R.id.footer_notifications);
		footer_latest.setTypeface(tf);
		footer_notifications.setTypeface(tf);
		footer_entertainment.setTypeface(tf);
		footer_home.setTypeface(tf);
		footer_city.setTypeface(tf);
		footer_city.setText("City Center");
		footer_latest.setText("Latest News");
		footer_home.setText("Home");
		footer_entertainment.setText("Entertainment");
		footer_notifications.setText("Notifications");*/
		/*BadgeView badge = new BadgeView(this, notification);
		badge.setText("1");
		badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge.show();*/
		/*homebutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent main_page=new Intent(MainActivity.this,MainActivityVersiontwo.class);
				startActivity(main_page);
				finish();
			}
		});

		entertainment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent entertainment_page=new Intent(MainActivity.this,EntertainmentVersiontwo.class);
				startActivity(entertainment_page);
				finish();
			}
		});
		citycenterbuttton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent citycentermain=new Intent(MainActivity.this,MainActivityVersiontwo.class);
				startActivity(citycentermain);
				finish();
			}
		});
		beyond.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent latest= new Intent(MainActivity.this,BeyondActivity.class);
				startActivity(latest);
				finish();
			}
		});
	notification.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

					if (value == 0) {
						Intent notification_page = new Intent(MainActivity.this, Settings.class);
						startActivity(notification_page);
						finish();
					} else {
						Uloaddataserver();
						Intent notification_page = new Intent(MainActivity.this, Settings.class);
						startActivity(notification_page);
						finish();
					}
				}

		});
*/
        foodandcook=(Button)view.findViewById(R.id.imageButton_foodandcookingnew);
        article=(Button)view.findViewById(R.id.imageButton_Articlenew);
        jobs=(Button)view.findViewById(R.id.imageButton_jobsnew);
        farming_agriculture=(Button)view.findViewById(R.id.imageButtonone_agriculturenew);
        news=(Button)view.findViewById(R.id.imageButton_newsnew);
        healthiliving=(Button)view.findViewById(R.id.imageButton_healthynew);
        govt_notifications=(Button)view.findViewById(R.id.imageButton_govtnew);
        events=(Button)view.findViewById(R.id.imageButton_eventsnew);
        education=(Button)view.findViewById(R.id.imageButton_education);
        scienceandtech=(Button)view.findViewById(R.id.imageButton_sciencenew);
        directories=(Button)view.findViewById(R.id.imageButton_directorynew);

        sports=(Button)view.findViewById(R.id.imageButton_sportsnew);
        radio=(Button)view.findViewById(R.id.imageButton_radionew);
        photostories=(Button)view.findViewById(R.id.imageButton_photostories);
        theatre=(Button)view.findViewById(R.id.imageButton_theatrenew);
        citycenter=(Button)view.findViewById(R.id.imageButton_citycentrenew);
        travels=(Button)view.findViewById(R.id.imageButtonone_travelsnew);

        music=(Button)view.findViewById(R.id.imageButton_musicnew);


        foodandcook.setText("Food & Cooking");
        foodandcook.setTransformationMethod(null);
        foodandcook.setTypeface(tf);
        article.setText("Article of the day");
        article.setTransformationMethod(null);
        article.setTypeface(tf);
        jobs.setText("Jobs");
        jobs.setTransformationMethod(null);
        jobs.setTypeface(tf);
        farming_agriculture.setText("Agriculture");
        farming_agriculture.setTransformationMethod(null);
        farming_agriculture.setTypeface(tf);
        news.setText("News");
        news.setTransformationMethod(null);
        news.setTypeface(tf);
        healthiliving.setText("Health & Living");
        healthiliving.setTransformationMethod(null);
        healthiliving.setTypeface(tf);
        govt_notifications.setText("Government");
        govt_notifications.setTransformationMethod(null);
        govt_notifications.setTypeface(tf);
        events.setText("Events");
        events.setTransformationMethod(null);
        events.setTypeface(tf);
        education.setText("Educationtamil");
        education.setTransformationMethod(null);
        education.setTypeface(tf);
        scienceandtech.setText("Science");
        scienceandtech.setTransformationMethod(null);
        scienceandtech.setTypeface(tf);
        directories.setText("Directories");
        directories.setTransformationMethod(null);
        directories.setTypeface(tf);
        sports.setText("Sports");
        sports.setTransformationMethod(null);
        sports.setTypeface(tf);
        radio.setText("Radio");
        radio.setTransformationMethod(null);
        radio.setTypeface(tf);
        photostories.setText("Photo Stories");
        photostories.setTransformationMethod(null);
        photostories.setTypeface(tf);
        theatre.setText("Theatre");
        theatre.setTransformationMethod(null);
        theatre.setTypeface(tf);
        citycenter.setText("Lifestyle");
        citycenter.setTransformationMethod(null);
        citycenter.setTypeface(tf);
        travels.setText("Travels");
        travels.setTransformationMethod(null);
        travels.setTypeface(tf);

        music.setText("Music");
        music.setTransformationMethod(null);
        music.setTypeface(tf);

        foodandcook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent food = new Intent(getActivity(), FoodActivity.class);
                    startActivity(food);

                } catch (Exception e) {

                }
            }
        });
        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent article = new Intent(getActivity(),Articleoftheday.class);
                    startActivity(article);

                } catch (Exception e) {

                }
            }
        });
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobs = new Intent(getActivity(),JobsActivity.class);
                startActivity(jobs);

            }
        });
        farming_agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agriculture= new Intent(getActivity(),FarmingAgricultureActivity.class);
                startActivity(agriculture);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agriculture= new Intent(getActivity(),NewsActivity.class);
                startActivity(agriculture);

            }
        });
        healthiliving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent healthy= new Intent(getActivity(),HealthAndLiving.class);
                startActivity( healthy);

            }
        });
        govt_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notifications= new Intent(getActivity(),GovernmentNotifications.class);
                startActivity(notifications);

            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent events= new Intent(getActivity(),Events.class);
                startActivity(events);

            }
        });
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doit= new Intent(getActivity(),Education.class);
                startActivity(doit);

            }
        });
        scienceandtech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scienceandtech= new Intent(getActivity(),ScienceandTechnology.class);
                startActivity(scienceandtech);

            }
        });
        directories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent directoriesservices= new Intent(getActivity(),Directories.class);
                startActivity(directoriesservices);

            }
        });
        radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent radio= new Intent(getActivity(),RadioActivity.class);
                startActivity(radio);

            }
        });
        theatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theatre= new Intent(getActivity(),Theatre.class);
                startActivity(theatre);

            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sports= new Intent(getActivity(),Sports.class);
                startActivity(sports);

            }
        });
        travels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent travels= new Intent(getActivity(),TravelsActivity.class);
                startActivity(travels);

            }
        });
        citycenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

				/*Intent citycenter= new Intent(MainActivity.this,CityCenter.class);
				startActivity(citycenter);
				finish();*/
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent music= new Intent(getActivity(),MusicActivity.class);
                startActivity(music);

            }
        });
        photostories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photostories= new Intent(getActivity(),PhotoStories.class);
                startActivity(photostories);

            }
        });
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(MYUSERID)) {
            myuserids=sharedpreferences.getString(MYUSERID,"");
            Log.e("MUUSERID:",myuserids);

        }
	/*	mRegistrationBroadcastReceiver = new BroadcastReceiver() {

			//When the broadcast received
			//We are sending the broadcast from GCMRegistrationIntentService

			@Override
			public void onReceive(Context context, Intent intent) {
				//If the broadcast has received with success
				//that means device is registered successfully
				if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
					//Getting the registration token from the intent
					String token = intent.getStringExtra("token");
					SharedPreferences.Editor editor = sharedpreferences.edit();
					editor.putString(GcmId, token);
					/// editor.putString(Email, e);
					editor.commit();

					////Displaying the token as toast
					// Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();

					//if the intent is not with success then displaying error messages
				} else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
					//  Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
				} else {
					//Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
				}
			}
		};

		//Checking play service is available or not
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

		//if play service is not available
		if(ConnectionResult.SUCCESS != resultCode) {
			//If play service is supported but not installed
			if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				//Displaying message that play service is not installed
				Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
				GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

				//If play service is not supported
				//Displaying an error message
			} else {
				Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
			}

			//If play service is available
		} else {
			//Starting intent to register device
			Intent itent = new Intent(this, GCMRegistrationIntentService.class);
			startService(itent);
		}*/


        return view;
    }

    private void setupSearchView()
    {
        // search hint
        search.setQueryHint(getString(R.string.queryhintsearchview));

        // background
        View searchPlate = search.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundColor(Color.TRANSPARENT);

        // icon
        ImageView searchIcon = (ImageView) search.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
      searchIcon.setImageResource(android.R.drawable.ic_menu_search);

        // clear button
        ImageView searchClose = (ImageView) search.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
      //searchClose.setImageResource(android.R.drawable.ic_menu_delete);

        // text color
        AutoCompleteTextView searchText = (AutoCompleteTextView) search.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(getResources().getColor(R.color.white));
        searchText.setHintTextColor(getResources().getColor(R.color.white));
    }


   /* private void notificationpage(){

        Log.e("hi","hihi");
        JsonArrayRequest jsonReq = new JsonArrayRequest(url_notification_count_valueget, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                       ItemModel model = new ItemModel();
                        model.setCount(obj.getString("count"));
                        String notification_counts = obj.getString("count");
                        Log.e("unrrad:", notification_counts);
                        value= Integer.parseInt(notification_counts);
                        if(value==0){
                            notification_batge_count.setVisibility(View.GONE);
                        }else {
                            notification_batge_count.setVisibility(View.VISIBLE);
                            notification_batge_count.setText(notification_counts);
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
        //AppController.getInstance().addToRequestQueue(jsonReq);
        requestQueue.add(jsonReq);

    }*/
    private void Uloaddataserver(){
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
    class ItemModel {
        private String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
    public void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    public void onResume() {
        super.onResume();
        Log.w("MainActivityoldtamil", "onResume");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }


    //Unregistering receiver on activity paused
    @Override
    public void onPause() {
        super.onPause();
        Log.w("MainActivityoldtamil", "onPause");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    /** Called when the activity is no longer visible. */
    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
        }
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }
}

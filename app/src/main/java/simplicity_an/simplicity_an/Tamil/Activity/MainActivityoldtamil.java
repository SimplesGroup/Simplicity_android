package simplicity_an.simplicity_an.Tamil.Activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import simplicity_an.simplicity_an.Directories;
import simplicity_an.simplicity_an.GCMRegistrationIntentService;
import simplicity_an.simplicity_an.MusicActivity;
import simplicity_an.simplicity_an.PhotoStories;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.RadioActivity;
import simplicity_an.simplicity_an.SimplicitySearchview;
import simplicity_an.simplicity_an.Tamil.TamilArticleoftheday;
import simplicity_an.simplicity_an.Theatre;


public class MainActivityoldtamil extends AppCompatActivity {

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
	SearchView search;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.mainpagetwo);
		//notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
		//notification_batge_count.setVisibility(View.GONE);
		sharedpreferences =  getSharedPreferences(mypreference,
				Context.MODE_PRIVATE);
		if (sharedpreferences.contains(MYUSERID)) {

			myprofileid=sharedpreferences.getString(MYUSERID,"");
			myprofileid = myprofileid.replaceAll("\\D+","");
			Log.e("MUUSERID:",myprofileid);
		}
		url_notification_count_valueget=url_noti_count+myprofileid;
		Log.e("notification Url:",url_notification_count_valueget);

		URLPOSTQTYPE=urlpost;
requestQueue=Volley.newRequestQueue(this);
		String fontPathbarkendina = "fonts/Barkentina.otf";
		barkentina = Typeface.createFromAsset(MainActivityoldtamil.this.getAssets(), fontPathbarkendina);
		String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
		Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

		toolbartitle=(TextView)findViewById(R.id.simplicitynewtitle);
		toolbartitle.setTypeface(barkentina);
		search=(SearchView)findViewById(R.id.searchmain);
		toolbartitle.setText("சிம்ப்ளிசிட்டி");
		if(myprofileid!=null){
			notificationpage();
		}else {

		}


		int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
		ImageView magImage = (ImageView) search.findViewById(magId);
		magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
		magImage.setVisibility(View.GONE);
		int searchPlateId = search.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
		View searchPlate = search.findViewById(searchPlateId);
		if (searchPlate!=null) {
			searchPlate.setBackgroundColor(Color.TRANSPARENT);
			//searchPlate.setBottom(Color.WHITE);

			int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
			TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
			if (searchText!=null) {
				searchText.setTextColor(Color.WHITE);

				searchText.setHintTextColor(getResources().getColor(R.color.searchviewhintcolor));
				searchText.setPadding(70, 0, 0, 0);
				//searchText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
			}
		}


		search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub


			}
		});


		search.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub


				search_value = query;

				Intent simplicity = new Intent(getApplicationContext(), SimplicitySearchview.class);
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


		closebuttton=(ImageButton)findViewById(R.id.close_mainpage);
		closebuttton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Intent main_page=new Intent(MainActivityoldtamil.this,MainPageTamil.class);
				startActivity(main_page);
				finish();*/
				onBackPressed();
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
		badge.show();
		homebutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent main_page=new Intent(MainActivityoldtamil.this,MainPageTamil.class);
				startActivity(main_page);
				finish();
			}
		});

		entertainment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent entertainment_page=new Intent(MainActivityoldtamil.this,TamilEntertainment.class);
				startActivity(entertainment_page);
				finish();
			}
		});
		citycenterbuttton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent citycentermain=new Intent(MainActivityoldtamil.this,MainPageTamil.class);
				startActivity(citycentermain);
				finish();
			}
		});
		beyond.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent latest= new Intent(MainActivityoldtamil.this,TamilBeyond.class);
				startActivity(latest);
				finish();
			}
		});
	notification.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

					if (value == 0) {
						Intent notification_page = new Intent(MainActivityoldtamil.this, MainPageTamil.class);
						startActivity(notification_page);
						finish();
					} else {
						Uloaddataserver();
						Intent notification_page = new Intent(MainActivityoldtamil.this, MainPageTamil.class);
						startActivity(notification_page);
						finish();
					}
				}

		});*/

		foodandcook=(Button)findViewById(R.id.imageButton_foodandcookingnew);
		article=(Button)findViewById(R.id.imageButton_Articlenew);
		jobs=(Button)findViewById(R.id.imageButton_jobsnew);
		farming_agriculture=(Button)findViewById(R.id.imageButtonone_agriculturenew);
		news=(Button)findViewById(R.id.imageButton_newsnew);
		healthiliving=(Button)findViewById(R.id.imageButton_healthynew);
		govt_notifications=(Button)findViewById(R.id.imageButton_govtnew);
		events=(Button)findViewById(R.id.imageButton_eventsnew);
		education=(Button)findViewById(R.id.imageButton_education);
		scienceandtech=(Button)findViewById(R.id.imageButton_sciencenew);
		directories=(Button)findViewById(R.id.imageButton_directorynew);

		sports=(Button)findViewById(R.id.imageButton_sportsnew);
		radio=(Button)findViewById(R.id.imageButton_radionew);
		photostories=(Button)findViewById(R.id.imageButton_photostories);
		theatre=(Button)findViewById(R.id.imageButton_theatrenew);
		citycenter=(Button)findViewById(R.id.imageButton_citycentrenew);
		travels=(Button)findViewById(R.id.imageButtonone_travelsnew);

		music=(Button)findViewById(R.id.imageButton_musicnew);


		foodandcook.setText("சமையல் குறிப்பு");
		foodandcook.setTransformationMethod(null);
		foodandcook.setTypeface(tf);
		article.setText("கட்டுரை");
		article.setTransformationMethod(null);
		article.setTypeface(tf);
		jobs.setText("வேலைவாய்ப்பு");
		jobs.setTransformationMethod(null);
		jobs.setTypeface(tf);
		farming_agriculture.setText("விவசாயம்");
		farming_agriculture.setTransformationMethod(null);
		farming_agriculture.setTypeface(tf);
		news.setText("செய்திகள்");
		news.setTransformationMethod(null);
		news.setTypeface(tf);
		healthiliving.setText("ஆரோக்கியம்");
		healthiliving.setTransformationMethod(null);
		healthiliving.setTypeface(tf);
		govt_notifications.setText("அரசு அறிவிப்பு");
		govt_notifications.setTransformationMethod(null);
		govt_notifications.setTypeface(tf);
		events.setText("நிகழ்வுகள்");
		events.setTransformationMethod(null);
		events.setTypeface(tf);
		education.setText("கல்வி செய்தி");
		education.setTransformationMethod(null);
		education.setTypeface(tf);
		scienceandtech.setText("அறிவியல்");
		scienceandtech.setTransformationMethod(null);
		scienceandtech.setTypeface(tf);
		directories.setText("அடைவு சேவைகள்");
		directories.setTransformationMethod(null);
		directories.setTypeface(tf);
		sports.setText("விளையாட்டு");
		sports.setTransformationMethod(null);
		sports.setTypeface(tf);
		radio.setText("போட்காஸ்ட்");
		radio.setTransformationMethod(null);
		radio.setTypeface(tf);
		photostories.setText("புகைப்பட செய்திகள்");
		photostories.setTransformationMethod(null);
		photostories.setTypeface(tf);
		theatre.setText("குறும்படம்");
		theatre.setTransformationMethod(null);
		theatre.setTypeface(tf);
		citycenter.setText("லைப்ஸ்டைல்");
		citycenter.setTransformationMethod(null);
		citycenter.setTypeface(tf);
		travels.setText("பயணங்கள்");
		travels.setTransformationMethod(null);
		travels.setTypeface(tf);

		music.setText(" இசை");
		music.setTransformationMethod(null);
		music.setTypeface(tf);

		foodandcook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent food = new Intent(MainActivityoldtamil.this, FoodActivityTamil.class);
					startActivity(food);
					finish();
				} catch (Exception e) {

				}
			}
		});
		article.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent article = new Intent(MainActivityoldtamil.this,TamilArticleoftheday.class);
					startActivity(article);
					finish();
				} catch (Exception e) {

				}
			}
		});
		jobs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent jobs = new Intent(MainActivityoldtamil.this,JobsActivitytamil.class);
				startActivity(jobs);
				finish();
			}
		});
		farming_agriculture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent agriculture= new Intent(MainActivityoldtamil.this,FarmingAgricultureActivitytamil.class);
				startActivity(agriculture);
				finish();
			}
		});
		news.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent agriculture= new Intent(MainActivityoldtamil.this,NewsActivityTamil.class);
				startActivity(agriculture);
				finish();
			}
		});
		healthiliving.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent healthy= new Intent(MainActivityoldtamil.this,HealthAndLivingtamil.class);
				startActivity( healthy);
				finish();
			}
		});
		govt_notifications.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent notifications= new Intent(MainActivityoldtamil.this,GovernmentNotificationstamil.class);
				startActivity(notifications);
				finish();
			}
		});
		events.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent events= new Intent(MainActivityoldtamil.this,TamilEvents.class);
				startActivity(events);
				finish();
			}
		});
		education.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent doit= new Intent(MainActivityoldtamil.this,Educationtamil.class);
				startActivity(doit);
				finish();
			}
		});
		scienceandtech.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent scienceandtech= new Intent(MainActivityoldtamil.this,ScienceandTechnologytamil.class);
				startActivity(scienceandtech);
				finish();
			}
		});
		directories.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent directoriesservices= new Intent(MainActivityoldtamil.this,Directories.class);
				startActivity(directoriesservices);
				finish();
			}
		});
		radio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent radio= new Intent(MainActivityoldtamil.this,RadioActivity.class);
				startActivity(radio);
				finish();
			}
		});
		theatre.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent theatre= new Intent(MainActivityoldtamil.this,Theatre.class);
				startActivity(theatre);
				finish();
			}
		});
		sports.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sports= new Intent(MainActivityoldtamil.this,TamilSports.class);
				startActivity(sports);
				finish();
			}
		});
		travels.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent travels= new Intent(MainActivityoldtamil.this,TravelsActivitytamil.class);
				startActivity(travels);
				finish();
			}
		});
		citycenter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				/*Intent citycenter= new Intent(MainActivityoldtamil.this,CityCenter.class);
				startActivity(citycenter);
				finish();*/
			}
		});

		music.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent music= new Intent(MainActivityoldtamil.this,MusicActivity.class);
				startActivity(music);
				finish();
			}
		});
		photostories.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent photostories= new Intent(MainActivityoldtamil.this,PhotoStories.class);
				startActivity(photostories);
				finish();
			}
		});
		sharedpreferences = getSharedPreferences(mypreference,
				Context.MODE_PRIVATE);

		if (sharedpreferences.contains(MYUSERID)) {
			myuserids=sharedpreferences.getString(MYUSERID,"");
			myprofileid = myprofileid.replaceAll("\\D+","");
			Log.e("MUUSERID:",myuserids);

		}
		mRegistrationBroadcastReceiver = new BroadcastReceiver() {

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
		}



	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private void notificationpage(){
		String url="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=276";
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
							//notification_batge_count.setVisibility(View.GONE);
						}else {
							//notification_batge_count.setVisibility(View.VISIBLE);
							//notification_batge_count.setText(notification_counts);
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

	}
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
							Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show() ;
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
		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

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
	protected void onStart() {
		super.onStart();
		Log.d(msg, "The onStart() event");
	}

	/** Called when the activity has become visible. */
	@Override
	protected void onResume() {
		super.onResume();
		Log.w("MainActivityoldtamil", "onResume");
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
	}


	//Unregistering receiver on activity paused
	@Override
	protected void onPause() {
		super.onPause();
		Log.w("MainActivityoldtamil", "onPause");
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
	}

	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop() {
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

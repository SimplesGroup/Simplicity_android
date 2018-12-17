package simplicity_an.simplicity_an;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class LatestNews extends Activity {

    private RecyclerView mRecyclerView,reone,retwo,rethree,refour,refive,resix,reseven,reeigtht;
    private RecyclerView.LayoutManager mLayoutManager,layoutManagerone,layoutManagertwo,layoutManagerthree,layoutManagerfour,layoutManagerfive,layoutManagersix,layoutManagerseven,layoutManagereight;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    List<ItemModel> modelListsecond=new ArrayList<ItemModel>();
    List<ItemModel> modelListgovt=new ArrayList<ItemModel>();
    List<ItemModel> modelListevent=new ArrayList<ItemModel>();
    List<ItemModel> modelListsports=new ArrayList<ItemModel>();
    List<ItemModel> modelListscience=new ArrayList<ItemModel>();
    List<ItemModel> modelListhealth=new ArrayList<ItemModel>();
    List<ItemModel> modelListfarming=new ArrayList<ItemModel>();
    List<ItemModel> modelListdoit=new ArrayList<ItemModel>();

    RecyclerViewAdapter rcadap;
    RecyclerViewAdapterArticles rearticle;
    RecyclerViewAdaptergovt rcgovt;
    RecyclerViewAdapterevents rcevents;
    RecyclerViewAdaptersports rcspotrs;
    RecyclerViewAdapterscience rcscience;
    RecyclerViewAdapterhealth rchealth;
    RecyclerViewAdapterfarming rcfarming;
    RecyclerViewAdapterdoit rcdoit;
    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue queues;
    ImageButton homepage,citycenter,entertainment,notification;
    int cDay,cMonth,cYear,cHour,cMinute,cSecond;
    String selectedMonth,selectedYear;
    ImageButton move_news_page,move_article_page,move_govt_page,move_event_page,move_sports_page,move_science_page,move_health_page,move_farming_page,move_doit_page;
    String URL="http://simpli-city.in/request2.php?rtype=latest-updates&key=simples";
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    protected RecyclerView recycler_view_list;
    String URLPOSTQTYPE,postid;
    protected Button btnMore;
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid,weather_degree_value;
    int value;
    String notification_counts;
    static NetworkInfo activeNetwork;
    SharedPreferences sharedpreferences;

    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    TextView titlecoimbatore,date,weather_update;
    String WEATHER_URL="http://simpli-city.in/request2.php?rtype=weather&key=simples";
    String ADS_URL="http://simpli-city.in/request2.php?rtype=ads&key=simples&id=";
    String ADS_URL_WITH_ID;
    List<ItemModel> adList=new ArrayList<ItemModel>();
    RequestQueue requestQueue;
    NetworkImageView adnewsimageview,adarticleimageview,adgovtimageview,adeventimageview,adsportsimageview,adsscieceimageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.latestnews);
        queues = simplicity_an.simplicity_an.MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);
        requestQueue=Volley.newRequestQueue(this);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");

        }
        url_notification_count_valueget=url_noti_count+myprofileid;
        URLPOSTQTYPE=urlpost;

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
            AppControllers.getInstance().addToRequestQueue(jsonReq);
        }else {

        }
        weather_update=(TextView) findViewById(R.id.weather_degree);
        StringRequest weather=new StringRequest(Request.Method.GET, WEATHER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                weather_update.setText(Html.fromHtml(response+"<sup>o</sup>"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        weather.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppControllers.getInstance().addToRequestQueue(weather);
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
        String dayOfTheWeek = sdf.format(d);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM");
        Date d1 = new Date();
        String sMonthName =  sdf1.format(d1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 1);
        int mon=calander.get(Calendar.DAY_OF_MONTH);
        Log.e("DAY_OF_MONTH: ", "DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH)+sMonthName);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        titlecoimbatore=(TextView)findViewById(R.id.title_coimbatore) ;
        date=(TextView)findViewById(R.id.weather) ;

        homepage=(ImageButton)findViewById(R.id.btn_3);
titlecoimbatore.setTypeface(tf);
        date.setTypeface(tf);
        weather_update.setTypeface(tf);
        titlecoimbatore.setText("Coimbatore");
        date.setText(Html.fromHtml(dayOfTheWeek+","+"&nbsp;"+sMonthName+"&nbsp;"+calendar.get(Calendar.DAY_OF_MONTH)+"&nbsp;"+"|"+"&nbsp;"));

        adnewsimageview=(NetworkImageView) findViewById(R.id.ad_news_imageview);
        adarticleimageview=(NetworkImageView) findViewById(R.id.ad_article_imageview);
        adgovtimageview=(NetworkImageView) findViewById(R.id.ad_govt_imageview);
        adeventimageview=(NetworkImageView) findViewById(R.id.ad_event_imageview);
        adsportsimageview=(NetworkImageView) findViewById(R.id.ad_sports_imageview);
        adsscieceimageview=(NetworkImageView) findViewById(R.id.ad_science_imageview);
        citycenter=(ImageButton)findViewById(R.id.btn_2) ;
        entertainment=(ImageButton)findViewById(R.id.btn_4) ;
        notification=(ImageButton)findViewById(R.id.btn_5) ;
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(main);
                finish();
            }
        });
        citycenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent city=new Intent(LatestNews.this,CityCenter.class);
                startActivity(city);
                finish();*/
            }
        });
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent latestnewss=new Intent(LatestNews.this,Enternainment.class);
                startActivity(latestnewss);
                finish();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(LatestNews.this, simplicity_an.simplicity_an.NotificationSettingsactivity.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(LatestNews.this, simplicity_an.simplicity_an.NotificationSettingsactivity.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });
        //dateof_title.setText(dayOfTheWeek+","+sMonthName+""+cDay+""+"|");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            reone=(RecyclerView)findViewById(R.id.my_recycler_viewtwo);
        retwo=(RecyclerView)findViewById(R.id.my_recycler_viewthree);
        rethree=(RecyclerView)findViewById(R.id.my_recycler_viewfour);
        refour=(RecyclerView)findViewById(R.id.my_recycler_viewfive);
        refive=(RecyclerView)findViewById(R.id.my_recycler_viewsix);
        resix=(RecyclerView)findViewById(R.id.my_recycler_viewseven);
        reseven=(RecyclerView)findViewById(R.id.my_recycler_vieweight);
        reeigtht=(RecyclerView)findViewById(R.id.my_recycler_view_doit);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        reone.setHasFixedSize(true);
        retwo.setHasFixedSize(true);
        rethree.setHasFixedSize(true);
        refour.setHasFixedSize(true);
        refive.setHasFixedSize(true);
        resix.setHasFixedSize(true);
        reseven.setHasFixedSize(true);
        reeigtht.setHasFixedSize(true);

        // use a linear layout manager set to horizontal
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        layoutManagerone = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        layoutManagertwo= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        layoutManagerthree = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        layoutManagerfour = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        layoutManagerfive = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        layoutManagersix = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        layoutManagerseven = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        layoutManagereight = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        reone.setLayoutManager(layoutManagerone);
        retwo.setLayoutManager(layoutManagertwo);
        rethree.setLayoutManager(layoutManagerthree);
        refour.setLayoutManager(layoutManagerfour);
        refive.setLayoutManager(layoutManagerfive);
        resix.setLayoutManager(layoutManagersix);
        reseven.setLayoutManager(layoutManagerseven);
        reeigtht.setLayoutManager(layoutManagereight);
/*** declare the more buttons********/
        move_news_page=(ImageButton)findViewById(R.id.move_to_news);
        move_article_page=(ImageButton)findViewById(R.id.move_to_article);
        move_govt_page=(ImageButton)findViewById(R.id.move_to_govt);
        move_event_page=(ImageButton)findViewById(R.id.move_to_event);
        move_sports_page=(ImageButton)findViewById(R.id.move_to_sports);
        move_science_page=(ImageButton)findViewById(R.id.move_to_science);
        move_health_page=(ImageButton)findViewById(R.id.move_to_health);
        move_farming_page=(ImageButton)findViewById(R.id.move_to_farming);
        move_doit_page=(ImageButton)findViewById(R.id.move_to_doit);

/********end the more button declaration*****/


        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {


                if (response != null) {

                    parseJsonFeed(response);
                  //  parseJsonFeedtwo(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonreq.setTag(TAG_REQUEST);

        simplicity_an.simplicity_an.MySingleton.getInstance(this).addToRequestQueue(jsonreq);
        ADURLS();
       // CustomRecyclerAdapter mAdapter = new CustomRecyclerAdapter(array);
        rcadap = new RecyclerViewAdapter(this, modelList);
        rearticle=new RecyclerViewAdapterArticles(this,modelListsecond);
        rcgovt=new RecyclerViewAdaptergovt(this,modelListgovt);
        rcevents=new RecyclerViewAdapterevents(this,modelListevent);
        rcspotrs=new RecyclerViewAdaptersports(this,modelListsports);
        rcscience=new RecyclerViewAdapterscience(this,modelListscience);
        rchealth=new RecyclerViewAdapterhealth(this,modelListhealth);
        rcfarming=new RecyclerViewAdapterfarming(this,modelListfarming);
        rcdoit=new RecyclerViewAdapterdoit(this,modelListdoit);



        mRecyclerView.setAdapter(rcadap);
        reone.setAdapter(rearticle);
        retwo.setAdapter(rcgovt);
        rethree.setAdapter(rcevents);
        refour.setAdapter(rcspotrs);
        refive.setAdapter(rcscience);
        resix.setAdapter(rchealth);
        reseven.setAdapter(rcfarming);
        reeigtht.setAdapter(rcdoit);


        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), NewsDescription.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        reone.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), reone, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), Articledescription.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        retwo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), retwo, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), GovernmentnotificationsDescriptions.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        rethree.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rethree, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), EventsDescription.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        refour.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), refour, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), SportsnewsDescription.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        refive.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), refive, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), ScienceandTechnologyDescription.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        resix.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), resix, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), simplicity_an.simplicity_an.Healthylivingdescription.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        reseven.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), reseven, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), Farmingdescription.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        reeigtht.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), reeigtht, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), DoitDescription.class);
                i.putExtra("ID", bid);

                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        move_news_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newspage = new Intent(getApplicationContext(), NewsActivity.class);
                startActivity(newspage);
            }
        });
        move_article_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articlepage = new Intent(getApplicationContext(), Articleoftheday.class);
                startActivity(articlepage);
            }
        });
        move_govt_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent govtpage = new Intent(getApplicationContext(), GovernmentNotifications.class);
                startActivity(govtpage);
            }
        });
        move_event_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eventpage = new Intent(getApplicationContext(), Events.class);
                startActivity(eventpage);
            }
        });
        move_sports_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sportspage = new Intent(getApplicationContext(), Sports.class);
                startActivity(sportspage);
            }
        });
        move_science_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sciencepage = new Intent(getApplicationContext(), ScienceandTechnology.class);
                startActivity(sciencepage);
            }
        });
       move_health_page.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent sciencepage = new Intent(getApplicationContext(), ScienceandTechnology.class);
               startActivity(sciencepage);
           }
       });
        move_farming_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent farmingpage = new Intent(getApplicationContext(), FarmingAgricultureActivity.class);
                startActivity(farmingpage);
            }
        });
        move_doit_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doitpage = new Intent(getApplicationContext(), DoitYourself.class);
                startActivity(doitpage);
            }
        });
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
private void ADURLS(){
 final ImageLoader   mImageLoaders = CustomVolleyRequest.getInstance(LatestNews.this).getImageLoader();
    ADS_URL_WITH_ID="http://simpli-city.in/request2.php?rtype=ads&key=simples";
    Log.e("URL:",ADS_URL_WITH_ID);
    /*StringRequest res=new StringRequest(Request.Method.GET, ADS_URL_WITH_ID, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
Log.e("RES:",response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });*/

JsonArrayRequest adurlsnews=new JsonArrayRequest(Request.Method.GET, ADS_URL_WITH_ID, new Response.Listener<JSONArray>() {
    @Override
    public void onResponse(JSONArray response) {
    for (int i=0;i<response.length();i++){
        try {
            JSONObject adobj=response.getJSONObject(i);
       ItemModel admodl=new ItemModel();
        admodl.setAdimageurl(adobj.getString("image_url"));
            admodl.setAdurl(adobj.getString("ad_url"));
            adnewsimageview.setImageUrl(adobj.getString("image_url"),mImageLoaders);
            weather_degree_value=adobj.getString("image_url");
            Log.e("ADFIRST:",weather_degree_value);
        }catch (JSONException e){

        }
    }
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {

    }
});


   adurlsnews.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
   requestQueue.add(adurlsnews);
   /*String  ADS_URL_WITH_ID_TWO="http://simpli-city.in/request2.php?rtype=ads2&key=simples";
    Log.e("URL:",ADS_URL_WITH_ID_TWO);
    JsonArrayRequest adurlsarticle=new JsonArrayRequest(Request.Method.GET, ADS_URL_WITH_ID_TWO, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject adobj=response.getJSONObject(i);
                    ItemModel admodl=new ItemModel();
                    admodl.setAdimageurl(adobj.getString("image_url"));
                    admodl.setAdurl(adobj.getString("ad_url"));
                    adarticleimageview.setImageUrl(adobj.getString("image_url"),mImageLoaders);
            String        weathertwo=adobj.getString("image_url");
                    Log.e("ADTWO:",weathertwo);
                }catch (JSONException e){

                }
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    adurlsarticle.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    requestQueue.add(adurlsarticle);
    String  ADS_URL_WITH_ID_THREE=ADS_URL+"3";
    Log.e("URL:",ADS_URL_WITH_ID_THREE);
    JsonArrayRequest adurlsgovt=new JsonArrayRequest(Request.Method.GET, ADS_URL_WITH_ID_THREE, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject adobj=response.getJSONObject(i);
                    ItemModel admodl=new ItemModel();
                    admodl.setAdimageurl(adobj.getString("image_url"));
                    admodl.setAdurl(adobj.getString("ad_url"));
                    adgovtimageview.setImageUrl(adobj.getString("image_url"),mImageLoaders);
                }catch (JSONException e){

                }
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    adurlsgovt.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    requestQueue.add(adurlsgovt);
    String  ADS_URL_WITH_ID_FOUR=ADS_URL+"4";
    Log.e("URL:",ADS_URL_WITH_ID_FOUR);
    JsonArrayRequest adurlsevent=new JsonArrayRequest(Request.Method.GET, ADS_URL_WITH_ID_FOUR, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject adobj=response.getJSONObject(i);
                    ItemModel admodl=new ItemModel();
                    admodl.setAdimageurl(adobj.getString("image_url"));
                    admodl.setAdurl(adobj.getString("ad_url"));
                    adeventimageview.setImageUrl(adobj.getString("image_url"),mImageLoaders);
                }catch (JSONException e){

                }
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    adurlsevent.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    requestQueue.add(adurlsevent);
    String  ADS_URL_WITH_ID_FIVE=ADS_URL+"5";
    Log.e("URL:",ADS_URL_WITH_ID_FIVE);
    JsonArrayRequest adurlssports=new JsonArrayRequest(Request.Method.GET, ADS_URL_WITH_ID_FIVE, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject adobj=response.getJSONObject(i);
                    ItemModel admodl=new ItemModel();
                    admodl.setAdimageurl(adobj.getString("image_url"));
                    admodl.setAdurl(adobj.getString("ad_url"));
                    adsportsimageview.setImageUrl(adobj.getString("image_url"),mImageLoaders);
                }catch (JSONException e){

                }
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    adurlssports.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    requestQueue.add(adurlssports);
    String  ADS_URL_WITH_ID_SIX=ADS_URL+"6";
    Log.e("URL:",ADS_URL_WITH_ID_SIX);
    JsonArrayRequest adurlsscience=new JsonArrayRequest(Request.Method.GET, ADS_URL_WITH_ID_SIX, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject adobj=response.getJSONObject(i);
                    ItemModel admodl=new ItemModel();
                    admodl.setAdimageurl(adobj.getString("image_url"));
                    admodl.setAdurl(adobj.getString("ad_url"));
                    adsscieceimageview.setImageUrl(adobj.getString("image_url"),mImageLoaders);
                }catch (JSONException e){

                }
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    adurlsscience.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    requestQueue.add(adurlsscience);*/
}
    public void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
        }
    }
    private void parseJsonFeed(JSONObject response) {
        try {
            //JSONArray feedArray = response.getJSONArray("result");
            String newsarray="news";
            String articlearray="article";
            String govtarray="govt";
            String eventarray="event";
            String sportsarray="sports";
            String sciencearray="science";
            String healtharray="health";
            String farmingarray="farming";
            String doitarray="doit";
            if(newsarray.equals("news")) {
                JSONArray feedArray = response.getJSONArray("news");
                // JSONArray feedArraytwo = response.getJSONArray("article");

                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject obj = (JSONObject) feedArray.get(i);


                    ItemModel model = new ItemModel();
                    String image = obj.isNull("thumb") ? null : obj
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("title"));
                    model.setQtype(obj.getString("qtype"));
                    model.setId(obj.getString("id"));
                    modelList.add(model);
                    // modelListsecond.add(model);
                }
            }
            if(articlearray.equals("article")) {
                JSONArray feedArraytwo = response.getJSONArray("article");

                for (int i = 0; i < feedArraytwo.length(); i++) {
                    JSONObject objt = (JSONObject) feedArraytwo.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListsecond.add(model);
                }
            }
            if(govtarray.equals("govt")) {
                JSONArray feedArraygovt = response.getJSONArray("govt");

                for (int i = 0; i < feedArraygovt.length(); i++) {
                    JSONObject objt = (JSONObject) feedArraygovt.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListgovt.add(model);
                }
            }
        if(eventarray.equals("event")) {
                JSONArray feedArrayevent = response.getJSONArray("event");

                for (int i = 0; i < feedArrayevent.length(); i++) {
                    JSONObject objt = (JSONObject) feedArrayevent.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListevent.add(model);
                }
            }
            if(sportsarray.equals("sports")) {
                JSONArray feedArraysports = response.getJSONArray("sports");

                for (int i = 0; i < feedArraysports.length(); i++) {
                    JSONObject objt = (JSONObject) feedArraysports.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListsports.add(model);
                }
            }
            if (sciencearray.equals("science")) {
                JSONArray feedArrayscience = response.getJSONArray("science");

                for (int i = 0; i < feedArrayscience.length(); i++) {
                    JSONObject objt = (JSONObject) feedArrayscience.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListscience.add(model);
                }
            }
            if(healtharray.equals("health")) {
                JSONArray feedArrayhealth = response.getJSONArray("health");

                for (int i = 0; i < feedArrayhealth.length(); i++) {
                    JSONObject objt = (JSONObject) feedArrayhealth.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListhealth.add(model);
                }
            }
            if (farmingarray.equals("farming")) {
                JSONArray feedArrayfarming = response.getJSONArray("farming");

                for (int i = 0; i < feedArrayfarming.length(); i++) {
                    JSONObject objt = (JSONObject) feedArrayfarming.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListfarming.add(model);
                }
            }
            if (doitarray.equals("doit")) {
                JSONArray feedArraydoit = response.getJSONArray("doit");

                for (int i = 0; i < feedArraydoit.length(); i++) {
                    JSONObject objt = (JSONObject) feedArraydoit.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListdoit.add(model);
                }
            }

            rcadap.notifyDataSetChanged();
            rearticle.notifyDataSetChanged();
            rcgovt.notifyDataSetChanged();
            rcevents.notifyDataSetChanged();
            rcspotrs.notifyDataSetChanged();
            rcscience.notifyDataSetChanged();
            rchealth.notifyDataSetChanged();
            rcfarming.notifyDataSetChanged();
            rcdoit.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    class ItemModel{
        private int typeid;
        private String name;
        private String image;
        private String pdate;
        private String description;
        private String title;
        private String qtype;
        private String id;
        private String adurl,adimageurl;
        private String count;

        public String getAdimageurl() {
            return adimageurl;
        }

        public void setAdimageurl(String adimageurl) {
            this.adimageurl = adimageurl;
        }

        public String getAdurl() {
            return adurl;
        }

        public void setAdurl(String adurl) {
            this.adurl = adurl;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getQtype() {
            return qtype;
        }

        public void setQtype(String qtype) {
            this.qtype = qtype;
        }
        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }
        public void setImage(String image) {
            this.image = image;
        }

        public String getPdate(){return  pdate;}
        public void setPdate(String pdate) {
            this.pdate = pdate;
        }
        public String getTitle(){return  title;}

        public void setTitle(String title) {
            this.title = title;
        }
        public String getId(){return  id;}

        public void setId(String id) {
            this.id = id;
        }


    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelList=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapter(Context context, List<ItemModel> modelList) {
            this.modelList = modelList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
            ItemModel itemmodel=modelList.get(position);
            String types=itemmodel.getQtype();
           // if(types.equals("news")) {
                holder.titles.setText(itemmodel.getTitle());
                holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                holder.profileimage.setAdjustViewBounds(true);
                holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
           /* }else {
                holder.profileimage.setVisibility(View.GONE);
                holder.titles.setVisibility(View.GONE);
            }*/
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }
        public class RecyclerViewHolders extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHolders(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }
    public class RecyclerViewAdapterArticles extends RecyclerView.Adapter<RecyclerViewAdapterArticles.RecyclerViewHoldersArticles> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelListsecond=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapterArticles(Context context, List<ItemModel> modelListsecond) {
            this. modelListsecond =  modelListsecond;
            this.context = context;
        }

        @Override
        public RecyclerViewHoldersArticles onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
            RecyclerViewHoldersArticles rcv = new RecyclerViewHoldersArticles(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHoldersArticles holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
            ItemModel itemmodel= modelListsecond.get(position);
            String types=itemmodel.getQtype();
            // holder.titles.setText("atricle news ");
            //if(types.equals("article")) {
                holder.titles.setText(itemmodel.getTitle());
                holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                holder.profileimage.setAdjustViewBounds(true);
                holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
           /* }else {
                holder.profileimage.setVisibility(View.GONE);
                holder.titles.setVisibility(View.GONE);
            }*/
        }

        @Override
        public int getItemCount() {
            return  modelListsecond.size();
        }
        public class RecyclerViewHoldersArticles extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHoldersArticles(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }
    public class RecyclerViewAdaptergovt extends RecyclerView.Adapter<RecyclerViewAdaptergovt.RecyclerViewHoldersgovts> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelListgovt=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdaptergovt(Context context, List<ItemModel> modelListgovt) {
            this.modelListgovt = modelListgovt;
            this.context = context;
        }

        @Override
        public RecyclerViewHoldersgovts onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
            RecyclerViewHoldersgovts rcv = new RecyclerViewHoldersgovts(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHoldersgovts holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
            ItemModel itemmodel=modelListgovt.get(position);
            String types=itemmodel.getQtype();
          //  if(types.equals("govt")) {
                holder.titles.setText(itemmodel.getTitle());
                holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                holder.profileimage.setAdjustViewBounds(true);
                holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
            /*}else {
                holder.profileimage.setVisibility(View.GONE);
                holder.titles.setVisibility(View.GONE);
            }*/
        }

        @Override
        public int getItemCount() {
            return modelListgovt.size();
        }
        public class RecyclerViewHoldersgovts extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHoldersgovts(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }

    public class RecyclerViewAdapterevents extends RecyclerView.Adapter<RecyclerViewAdapterevents.RecyclerViewHoldersevent> {
         private LayoutInflater inflater;
         ImageLoader mImageLoader;
         private List<ItemModel> modelListevent=new ArrayList<ItemModel>();
         private Context context;

         public RecyclerViewAdapterevents(Context context, List<ItemModel> modelListevent) {
             this.modelListevent = modelListevent;
             this.context = context;
         }

         @Override
         public RecyclerViewHoldersevent onCreateViewHolder(ViewGroup parent, int viewType) {

             View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
             RecyclerViewHoldersevent rcv = new RecyclerViewHoldersevent(layoutView);
             return rcv;
         }

         @Override
         public void onBindViewHolder(RecyclerViewHoldersevent holder, int position) {

             //if (mImageLoader == null)
             mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
             ItemModel itemmodel=modelListevent.get(position);
             String types=itemmodel.getQtype();
            // if(types.equals("event")) {
            holder.titles.setText(itemmodel.getTitle());
               holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                 holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                 holder.profileimage.setAdjustViewBounds(true);
                 holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
            /* }else {
             holder.profileimage.setVisibility(View.GONE);
                 holder.titles.setVisibility(View.GONE);
             }*/
         }

         @Override
         public int getItemCount() {
             return modelListevent.size();
         }
         public class RecyclerViewHoldersevent extends RecyclerView.ViewHolder{
             public TextView titles;
             public NetworkImageView profileimage;


             public RecyclerViewHoldersevent(View itemView) {
                 super(itemView);
               profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                 titles = (TextView) itemView.findViewById(R.id.name);


             }
         }
     }
     public class RecyclerViewAdaptersports extends RecyclerView.Adapter<RecyclerViewAdaptersports.RecyclerViewHolderssport> {
         private LayoutInflater inflater;
         ImageLoader mImageLoader;
         private List<ItemModel> modelListsports=new ArrayList<ItemModel>();
         private Context context;

         public RecyclerViewAdaptersports(Context context, List<ItemModel> modelListsports) {
             this.modelListsports = modelListsports;
             this.context = context;
         }

         @Override
         public RecyclerViewHolderssport onCreateViewHolder(ViewGroup parent, int viewType) {

             View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
             RecyclerViewHolderssport rcv = new RecyclerViewHolderssport(layoutView);
             return rcv;
         }

         @Override
         public void onBindViewHolder(RecyclerViewHolderssport holder, int position) {

             //if (mImageLoader == null)
             mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
             ItemModel itemmodel=modelListsports.get(position);
             String types=itemmodel.getQtype();
            // if(types.equals("sports")) {
                holder.titles.setText(itemmodel.getTitle());
              holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                 holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                 holder.profileimage.setAdjustViewBounds(true);
                 holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
            /* }else {
                holder.profileimage.setVisibility(View.GONE);
                 holder.titles.setVisibility(View.GONE);
             }*/
         }

         @Override
         public int getItemCount() {
             return modelListsports.size();
         }
         public class RecyclerViewHolderssport extends RecyclerView.ViewHolder{
             public TextView titles;
             public NetworkImageView profileimage;


             public RecyclerViewHolderssport(View itemView) {
                 super(itemView);
               profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                 titles = (TextView) itemView.findViewById(R.id.name);


             }
         }
     }

     public class RecyclerViewAdapterscience extends RecyclerView.Adapter<RecyclerViewAdapterscience.RecyclerViewHoldersscience> {
         private LayoutInflater inflater;
         ImageLoader mImageLoader;
         private List<ItemModel> modelListscience=new ArrayList<ItemModel>();
         private Context context;

         public RecyclerViewAdapterscience(Context context, List<ItemModel> modelListscience) {
             this.modelListscience = modelListscience;
             this.context = context;
         }

         @Override
         public RecyclerViewHoldersscience onCreateViewHolder(ViewGroup parent, int viewType) {

             View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
             RecyclerViewHoldersscience rcv = new RecyclerViewHoldersscience(layoutView);
             return rcv;
         }

         @Override
         public void onBindViewHolder(RecyclerViewHoldersscience holder, int position) {

             //if (mImageLoader == null)
             mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
             ItemModel itemmodel=modelListscience.get(position);
             String types=itemmodel.getQtype();
            // if(types.equals("science")) {
            holder.titles.setText(itemmodel.getTitle());
                holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                 holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                 holder.profileimage.setAdjustViewBounds(true);
                 holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
            /* }else {
                holder.profileimage.setVisibility(View.GONE);
                 holder.titles.setVisibility(View.GONE);
             }*/
         }

         @Override
         public int getItemCount() {
             return modelListscience.size();
         }
         public class RecyclerViewHoldersscience extends RecyclerView.ViewHolder{
             public TextView titles;
             public NetworkImageView profileimage;


             public RecyclerViewHoldersscience(View itemView) {
                 super(itemView);
               profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                 titles = (TextView) itemView.findViewById(R.id.name);


             }
         }
     }
     public class RecyclerViewAdapterhealth extends RecyclerView.Adapter<RecyclerViewAdapterhealth.RecyclerViewHoldershealth> {
         private LayoutInflater inflater;
         ImageLoader mImageLoader;
         private List<ItemModel> modelListhealth=new ArrayList<ItemModel>();
         private Context context;

         public RecyclerViewAdapterhealth(Context context, List<ItemModel> modelListhealth) {
             this.modelListhealth = modelListhealth;
             this.context = context;
         }

         @Override
         public RecyclerViewHoldershealth onCreateViewHolder(ViewGroup parent, int viewType) {

             View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
             RecyclerViewHoldershealth rcv = new RecyclerViewHoldershealth(layoutView);
             return rcv;
         }

         @Override
         public void onBindViewHolder(RecyclerViewHoldershealth holder, int position) {

             //if (mImageLoader == null)
             mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
             ItemModel itemmodel=modelListhealth.get(position);
             String types=itemmodel.getQtype();
            // if(types.equals("health")) {
              holder.titles.setText(itemmodel.getTitle());
              holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                 holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                 holder.profileimage.setAdjustViewBounds(true);
                 holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
           /*  }else {
                holder.profileimage.setVisibility(View.GONE);
                 holder.titles.setVisibility(View.GONE);
             }*/
         }

         @Override
         public int getItemCount() {
             return modelListhealth.size();
         }
         public class RecyclerViewHoldershealth extends RecyclerView.ViewHolder{
             public TextView titles;
             public NetworkImageView profileimage;


             public RecyclerViewHoldershealth(View itemView) {
                 super(itemView);
               profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                 titles = (TextView) itemView.findViewById(R.id.name);


             }
         }
     }

     public class RecyclerViewAdapterfarming extends RecyclerView.Adapter<RecyclerViewAdapterfarming.RecyclerViewHoldersfarming> {
         private LayoutInflater inflater;
         ImageLoader mImageLoader;
         private List<ItemModel> modelListfarming=new ArrayList<ItemModel>();
         private Context context;

         public RecyclerViewAdapterfarming(Context context, List<ItemModel> modelListfarming) {
             this.modelListfarming = modelListfarming;
             this.context = context;
         }

         @Override
         public RecyclerViewHoldersfarming onCreateViewHolder(ViewGroup parent, int viewType) {

             View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
             RecyclerViewHoldersfarming rcv = new RecyclerViewHoldersfarming(layoutView);
             return rcv;
         }

         @Override
         public void onBindViewHolder(RecyclerViewHoldersfarming holder, int position) {

             //if (mImageLoader == null)
             mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
             ItemModel itemmodel=modelListfarming.get(position);
             String types=itemmodel.getQtype();
            // if(types.equals("farming")) {
             holder.titles.setText(itemmodel.getTitle());
                holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                 holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                 holder.profileimage.setAdjustViewBounds(true);
                 holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
             /*}else {
                holder.profileimage.setVisibility(View.GONE);
                 holder.titles.setVisibility(View.GONE);
             }*/
         }

         @Override
         public int getItemCount() {
             return modelListfarming.size();
         }
         public class RecyclerViewHoldersfarming extends RecyclerView.ViewHolder{
             public TextView titles;
             public NetworkImageView profileimage;


             public RecyclerViewHoldersfarming(View itemView) {
                 super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                 titles = (TextView) itemView.findViewById(R.id.name);


             }
         }
     }



    public class RecyclerViewAdapterdoit extends RecyclerView.Adapter<RecyclerViewAdapterdoit.RecyclerViewHoldersdoit> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelListdoit=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapterdoit(Context context, List<ItemModel> modelListdoit) {
            this.modelListdoit = modelListdoit;
            this.context = context;
        }

        @Override
        public RecyclerViewHoldersdoit onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_latestnews, null);
            RecyclerViewHoldersdoit rcv = new RecyclerViewHoldersdoit(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHoldersdoit holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(LatestNews.this).getImageLoader();
            ItemModel itemmodel=modelListdoit.get(position);
            String types=itemmodel.getQtype();
            // if(types.equals("farming")) {
            holder.titles.setText(itemmodel.getTitle());
            holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
            holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
            holder.profileimage.setAdjustViewBounds(true);
            holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
             /*}else {
                holder.profileimage.setVisibility(View.GONE);
                 holder.titles.setVisibility(View.GONE);
             }*/
        }

        @Override
        public int getItemCount() {
            return modelListdoit.size();
        }
        public class RecyclerViewHoldersdoit extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHoldersdoit(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }




    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

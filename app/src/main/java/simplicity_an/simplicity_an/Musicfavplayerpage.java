package simplicity_an.simplicity_an;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 8/22/2016.
 */
public class Musicfavplayerpage extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
    TextView category,musicdirectorname,authername,title,timecurrentseekbar,timebalanceseekbar;
    SeekBar songduration,sound;
    NetworkImageView imageview;
    ImageButton prev,next,play,soundbutton,menu,favbutton;
    private AudioManager myAudioManager;
    ImageLoader mImageLoader;
    ImageButton back,home,comment;
    Context context;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();;
    MediaPlayer mediaPlayers;
    public static int oneTimeOnly = 0;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    String myprofileid;
    private String KEY_POSTID = "mid";
    private String KEY_MYID = "user_id";
    String URLFAV="http://simpli-city.in/request2.php?rtype=musicfav&key=simples";
    int favs,fav_list_count;
    String catid,URLTWO,URLTHREE;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private List<ItemModel> modelListtwo=new ArrayList<ItemModel>();

    private String URL="http://simpli-city.in/request2.php?rtype=musicplaylist&key=simples&user_id=";
    private String URLS="http://simpli-city.in/request2.php?rtype=music&key=simples&mid=";
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pdialog;
    JSONArray feedArray;
    int ii,i,myuserinteger,positionss,position;
    private int currentSongIndex = 0;
    String bitmap,linksong="";

    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    int radio_urltype;
    Boolean isPlayClicked = false;
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid,activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.musicplayerpage);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        contentid=sharedpreferences.getString(CONTENTID,"");
        activity=sharedpreferences.getString(Activity,"");
        //Intent gets=getIntent();
        // favs=gets.getIntExtra("FAV",0);
        // radio_urltype=gets.getIntExtra("TYPEVALUE",0);
        //catid=gets.getStringExtra("ID");
        if(activity==null){
            Intent getnotifi=getIntent();
            catid=getnotifi.getStringExtra("ID");
            catid = catid.replaceAll("\\D+","");
            Log.e("ID",catid);

        } else {
            if(activity.equalsIgnoreCase("musicfav")){
                catid=contentid;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(Activity);
                editor.remove(CONTENTID);
                editor.apply();

            }else {
                Intent getnotifi=getIntent();
                catid=getnotifi.getStringExtra("ID");
               catid = catid.replaceAll("\\D+","");
                Log.e("ID",catid);
            }
        }
        Log.e("ID:",catid);
        if (sharedpreferences.contains(MYUSERID)) {
            myprofileid=sharedpreferences.getString(MYUSERID,"");
            Log.e("MUUSERID:",myprofileid);

        }
        requestQueue = Volley.newRequestQueue(this);
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(catid!=null){
            URLTWO=URLS+catid;
            // myprofileid = myprofileid.replaceAll("\\D+","");

            myuserinteger = Integer.parseInt(catid);
        }else {
            URLTWO=URLS;
        }
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    pdialog.dismiss();
                    //dissmissDialog();
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppControllers.getInstance().addToRequestQueue(jsonreq);
        requestQueue.add(jsonreq);
        getData();
        mImageLoader =  MySingleton.getInstance(getApplicationContext()).getImageLoader();
        imageview=(NetworkImageView)findViewById(R.id.thumbnailone_music);
        menu=(ImageButton)findViewById(R.id.menuinterface);
        favbutton=(ImageButton)findViewById(R.id.faveheart);
        comment = (ImageButton) findViewById(R.id.btn_4);
       /* Intent get=getIntent();
        String song_timing=get.getStringExtra("TIMING");*/
        back=(ImageButton)findViewById(R.id.btn_1);
        home=(ImageButton)findViewById(R.id.btn_3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayers.stop();
                /*Intent backmusicplayer=new Intent(getApplicationContext(),Musicfavlist.class);
                startActivity(backmusicplayer);
                finish();*/
                onBackPressed();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayers.stop();
                Intent menumusicplayer=new Intent(getApplicationContext(),MainPageEnglish.class);
                startActivity(menumusicplayer);
                finish();
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    MyDialogFragment frag;
                    frag = new MyDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("POSTID", catid);
                    args.putString("USERID", myprofileid);
                    frag.setArguments(args);
                    frag.show(ft, "txn_tag");
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "musicfav");
                    editor.putString(CONTENTID, catid);
                    editor.commit();
                    Intent signin=new Intent(Musicfavplayerpage.this,SigninpageActivity.class);
                    startActivity(signin);
                    finish();
                }
            }
        });
       /* String music_category=get.getStringExtra("MUSIC_CAT");
        String musicdirector_name=get.getStringExtra("MUSIC_DIRECTOR");
        String song_title=get.getStringExtra("TITLE");
        linksong=get.getStringExtra("SONGLINK");
        final String imagesong=get.getStringExtra("IMAGE");*/
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivityVersiontwo.class);
                /*in.putExtra("ID",catid);
                in.putExtra("IMAGE",imagesong);*/
                startActivity(in);
            }
        });

        if(favs==1){

            favbutton.setImageResource(R.drawable.greyhandfriends);
        }else {
            favbutton.setImageResource(R.drawable.handaddfriend);
        }


        fav_list_count=favs;



        favbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myprofileid != null) {
                    if (fav_list_count == 1) {
                        favbutton.setImageResource(R.drawable.handaddfriend);
                        fav_list_count--;


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLFAV,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        //Disimissing the progress dialog

                                        //Showing toast message of the response
                                        if (s.equalsIgnoreCase("no")) {
                                            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                        } else {
                                            Log.e("response:", s);

                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //Dismissing the progress dialog
                                        //loading.dismiss();

                                        //Showing toast
                                        //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> params = new Hashtable<String, String>();
                                String friendid = catid;
                                //Adding parameters
                                if (friendid != null) {


                                    params.put(KEY_POSTID, friendid);
                                    params.put(KEY_MYID, myprofileid);
                                } else {


                                }


                                return params;
                            }
                        };

                        //Creating a Request Queue
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        //Adding request to the queue
                        requestQueue.add(stringRequest);

                    } else {
                        favbutton.setImageResource(R.drawable.greyhandfriends);
                        fav_list_count++;

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLFAV,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        //Disimissing the progress dialog

                                        //Showing toast message of the response
                                        if (s.equalsIgnoreCase("no")) {
                                            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                        } else {
                                            Log.e("response:", s);

                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //Dismissing the progress dialog
                                        //loading.dismiss();

                                        //Showing toast
                                        //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                //Converting Bitmap to String

                                //Getting Image Name

                                //Creating parameters
                                Map<String, String> params = new Hashtable<String, String>();
                                String friendid = catid;
                                //Adding parameters
                                if (friendid != null) {


                                    params.put(KEY_POSTID, friendid);
                                    params.put(KEY_MYID, myprofileid);
                                } else {


                                }


                                return params;
                            }
                        };

                        //Creating a Request Queue
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        //Adding request to the queue
                        requestQueue.add(stringRequest);
                        //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();
                    }

                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "musicfav");
                    editor.putString(CONTENTID, catid);
                    editor.commit();
                    Intent in=new Intent(Musicfavplayerpage.this,SigninpageActivity.class);
                    startActivity(in);
                }
            }
        });

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                    mediaPlayers.pause();
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                    mediaPlayers.start();
                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //A call is dialing, active or on hold
                    mediaPlayers.pause();
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {

            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        timebalanceseekbar=(TextView)findViewById(R.id.songDuration);
        timecurrentseekbar=(TextView)findViewById(R.id.songDurationfullleft);
        category=(TextView)findViewById(R.id.textView_catgory);
        authername=(TextView)findViewById(R.id.textView_authername);
        title=(TextView)findViewById(R.id.title_radioplayer);
        musicdirectorname=(TextView)findViewById(R.id.subtitle_radioplayer);

        songduration=(SeekBar)findViewById(R.id.seekBar_music);

        prev=(ImageButton)findViewById(R.id.previous);
        next=(ImageButton)findViewById(R.id.next);
        play=(ImageButton)findViewById(R.id.play);

        category.setTypeface(seguiregular);
        authername.setTypeface(seguiregular);
        title.setTypeface(seguiregular);
        musicdirectorname.setTypeface(seguiregular);

        timecurrentseekbar.setTypeface(seguiregular);
        timebalanceseekbar.setTypeface(seguiregular);



        songduration.setMax((int) finalTime);


        mediaPlayers = new MediaPlayer();
        mediaPlayers.setAudioStreamType(AudioManager.STREAM_MUSIC);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSongIndex < (modelListtwo.size() - 1)){


                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                }else{
                    // play first song
                    playSong(0);
                    currentSongIndex = 0;
                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSongIndex > 0){
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                }else{
                    // play last song
                    playSong(modelListtwo.size() - 1);
                    currentSongIndex = modelListtwo.size() - 1;
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callsong();
            }
        });



        songduration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                try {
                    if (mediaPlayers.isPlaying() || mediaPlayers != null) {
                        if (fromUser)
                            mediaPlayers.seekTo(progress);
                    } else if (mediaPlayers == null) {
                        Toast.makeText(getApplicationContext(), "Media is not running",
                                Toast.LENGTH_SHORT).show();
                        seekBar.setProgress(0);
                    }
                } catch (Exception e) {
                    Log.e("seek bar", "" + e);
                    seekBar.setEnabled(false);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //SeekBar sb = (SeekBar);
                //mediaPlayer.seekTo(songduration.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

      /* sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //change the volume, displaying a toast message containing the current volume and playing a feedback sound
                myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);

            }
        });*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;
    }
    private JsonObjectRequest getDataFromTheServer(int requestCount) {




            URLTHREE=URL+"&userid="+myprofileid;





        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLTHREE);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    //dissmissDialog();
                    parseJsonFeeds(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLTHREE,  new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                    if (response != null) {
                        // dissmissDialog();
                        parseJsonFeeds(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
                }
            });
            jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
           // AppControllers.getInstance().addToRequestQueue(jsonreq);
            // Adding request to volley request queue
            requestQueue.add(jsonReq);
        }
        return jsonReq;
    }
    public void onCompletion(MediaPlayer mp) {
        mediaPlayers.stop();
        mediaPlayers.reset();
        songduration.setProgress(0);
        play.setImageResource(R.drawable.play);

    }
    private  void parseJsonFeed(JSONObject response){
        try {
            feedArray = response.getJSONArray("result");
            for (int i = 0; i < feedArray.length(); i++) {
                ItemModel model = new ItemModel();
                //FeedItem model=new FeedItem();
                JSONObject obj = (JSONObject) feedArray.get(i);
                //FeedItem model=new FeedItem();
                String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
                model.setImage(image);
                model.setTypeid(obj.getInt("id"));
                String id_city = obj.isNull("id") ? null : obj
                        .getString("id");
                model.setId(id_city);
                String date_city = obj.isNull("pdate") ? null : obj
                        .getString("pdate");
                model.setPdate(date_city);
                String title_city = obj.isNull("title") ? null : obj
                        .getString("title");
                model.setTitle(title_city);
                String musicdirector= obj.isNull("music_director") ? null : obj
                        .getString("music_director");
                model.setMusicdirector(musicdirector);
                String musiccategory= obj.isNull("music_category") ? null : obj
                        .getString("music_category");
                model.setMusiccategory(musiccategory);
                String musiclanguage= obj.isNull("language") ? null : obj
                        .getString("language");
                model.setLanguage(musiclanguage);
                String musiclink= obj.isNull("link") ? null : obj
                        .getString("link");
                model.setLink(musiclink);
                imageview.setDefaultImageResId(R.mipmap.ic_launcher);
                imageview.setErrorImageResId(R.mipmap.ic_launcher);
                imageview.setImageUrl(obj.getString("thumb"),mImageLoader);
                category.setText(obj.getString("music_category"));
                 title.setText(Html.fromHtml(obj.getString("title")));
                musicdirectorname.setText(obj.getString("music_director"));
                String by="By&nbsp;";
                int  favs = obj.isNull("fav") ? null : obj
                        .getInt("fav");
                model.setFavourite(favs);
                modelList.add(model);
                favs=obj.getInt("fav");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private  void parseJsonFeeds(JSONObject response){
        try {
            feedArray = response.getJSONArray("result");
            for (int i = 0; i < feedArray.length(); i++) {
                ItemModel model = new ItemModel();
                //FeedItem model=new FeedItem();
                JSONObject obj = (JSONObject) feedArray.get(i);


                //FeedItem model=new FeedItem();
                String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
                model.setImage(image);
                model.setTypeid(obj.getInt("id"));
                String id_city = obj.isNull("id") ? null : obj
                        .getString("id");
                model.setId(id_city);
                String date_city = obj.isNull("pdate") ? null : obj
                        .getString("pdate");
                model.setPdate(date_city);
                String title_city = obj.isNull("title") ? null : obj
                        .getString("title");
                model.setTitle(title_city);
                String musicdirector= obj.isNull("music_director") ? null : obj
                        .getString("music_director");
                model.setMusicdirector(musicdirector);
                String musiccategory= obj.isNull("music_category") ? null : obj
                        .getString("music_category");
                model.setMusiccategory(musiccategory);
                String musiclanguage= obj.isNull("language") ? null : obj
                        .getString("language");
                model.setLanguage(musiclanguage);
                String musiclink= obj.isNull("link") ? null : obj
                        .getString("link");
                model.setLink(musiclink);
                int  favs = obj.isNull("fav") ? null : obj
                        .getInt("fav");
                model.setFavourite(favs);

                modelListtwo.add(model);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void callsong( ){

        int idval=0;
        int  next=0;


        try {


            for (int i = 0; i < modelList.size(); i++) {

                idval=((ItemModel)modelList.get(i)).getTypeid();


                if(myuserinteger==idval) {
                    mediaPlayers.setDataSource(((ItemModel)modelList.get(position)).getLink());
                    // prev=((ItemModel)modelList.get(position-1)).getId();


                    // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    mediaPlayers.prepare();
                }
            }

            // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
        } catch (IllegalStateException e) {
            //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!mediaPlayers.isPlaying()) {
            mediaPlayers.start();
            play.setImageResource(R.drawable.pause);
        } else {
            if(mediaPlayers.isPlaying()){
                mediaPlayers.pause();
                play.setImageResource(R.drawable.play);
            } else {
                mediaPlayers.reset();
                play.setImageResource(R.drawable.play);
            }

        }
        finalTime = mediaPlayers.getDuration();
        startTime = mediaPlayers.getCurrentPosition();

        if (oneTimeOnly == 0) {
            songduration.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        timebalanceseekbar.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        timecurrentseekbar.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );

        songduration.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);
    }
    class ItemModel{

        private String name;
        private String image;
        private String bimage;
        private String pdate;
        private String description;
        private String title;
        private String publisher;
        private String company;
        private String qtype;
        private String link;
        private String musiccategory;
        private String musicdirector;
        private String language;
        String count;
        private int typeid;
        int favourite;

        public int getFavourite() {
            return favourite;
        }

        public void setFavourite(int favourite) {
            this.favourite = favourite;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCount() {
            return count;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getMusiccategory() {
            return musiccategory;
        }

        public void setMusiccategory(String musiccategory) {
            this.musiccategory = musiccategory;
        }

        public String getMusicdirector() {
            return musicdirector;
        }

        public void setMusicdirector(String musicdirector) {
            this.musicdirector = musicdirector;
        }

        /******** start the Food category names****/
        private String category_name;
        /******** start the Food category names****/
        private String id;

        public String getQtype() {
            return qtype;
        }

        public void setQtype(String qtype) {
            this.qtype = qtype;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
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
        public String getBimage() {
            return bimage;
        }
        public void setBimage(String bimage) {
            this.bimage = bimage;
        }
        public String getImage() {
            return image;
        }
        public void setImage(String image) {
            this.image = image;
        }
        public String getDescription(){return description;}
        public  void setDescription(String description){
            this.description=description;
        }
        public String getPdate(){return  pdate;}

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }
        public String getTitle(){return  title;}

        public void setTitle(String title) {
            this.title = title;
        }
        public String getPublisher(){return  publisher;}

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }
        public String getCompany(){return company;}
        public void setCompany(String company){
            this.company=company;
        }
        /******** start the Food category names****/
        public String getCategory_name(){return  category_name;}

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayers.getCurrentPosition();
            timecurrentseekbar.setText(String.format("%d:%d",

                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            songduration.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    private void nextsong() {
        mediaPlayers.stop();
        positionss += 1;

        if(positionss >= modelList.size()) {
            positionss = (modelList.size() - 1);

            finalTime = 0;
            //songduration.setMax((int) finalTime);



        } else {

            // update view's values
            //tvname.setText(list.get(position));

            isPlayClicked = true;
            play.setBackgroundResource(R.drawable.play);

            try {
                mediaPlayers = new MediaPlayer();
                mediaPlayers.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayers.setDataSource("http://simpli-city.in/smp_interface/images/radio/audio/Gathiman%20ads22_mixdown.mp3");

                mediaPlayers.prepare();
                mediaPlayers.start();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    public void  playSong(int songIndex){
        // Play song
        try {
            mediaPlayers.reset();
            mediaPlayers.setDataSource(((ItemModel) modelListtwo.get(songIndex)).getLink());
            mediaPlayers.prepare();
            mediaPlayers.start();
            imageview.setDefaultImageResId(R.mipmap.ic_launcher);
            imageview.setErrorImageResId(R.mipmap.ic_launcher);
            imageview.setImageUrl(((ItemModel) modelListtwo.get(songIndex)).getImage(),mImageLoader);
            category.setText(((ItemModel) modelListtwo.get(songIndex)).getMusiccategory());
            String by="By&nbsp;";
            title.setText(((ItemModel) modelListtwo.get(songIndex)).getTitle());
            musicdirectorname.setText(((ItemModel) modelListtwo.get(songIndex)).getMusicdirector());
            favs=((ItemModel) modelListtwo.get(songIndex)).getFavourite();
            // Displaying Song title
            //String songTitle = songsList.get(songIndex).get("songTitle");
            //songTitleLabel.setText(songTitle);

            // Changing Button Image to pause image
            play.setImageResource(R.drawable.pause);

            // set Progress bar values

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class MyDialogFragment extends DialogFragment {
        private String KEY_COMMENT = "comment";
        private String KEY_TYPE = "qtype";
        private String KEY_MYUID = "user_id";
        private String KEY_POSTID = "id";
        String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=music&id=";
        String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";
        EditText commentbox;
        Button post_review;
        ImageButton close_back;

        JSONObject obj, objtwo;
        JSONArray feedArray;
        int ii, i;
        TextView titles;
        CoordinatorLayout mCoordinator;
        private final String TAG_REQUEST = "MY_TAG";
        List<ItemModels> commentlist = new ArrayList<ItemModels>();
        //Need this to set the title of the app bar
        CollapsingToolbarLayout mCollapsingToolbarLayout;
        RecyclerViewAdapter rcAdapter;
        ProgressDialog pdialog;
        RequestQueue requestQueue;
        private int requestCount = 1;
        JsonObjectRequest jsonReq;
        String URLTWO;
        String bimage;
        RecyclerView recycler;
        LinearLayoutManager mLayoutManager;
        String postid, myuserid;

        public MyDialogFragment() {

        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.commentmusicfragment, container, false);
            titles = (TextView) root.findViewById(R.id.comments_title);
            requestQueue = Volley.newRequestQueue(getActivity());
            postid = getArguments().getString("POSTID");
            myuserid = getArguments().getString("USERID");
            String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
            commentbox = (EditText) root.findViewById(R.id.comment_description);
            post_review = (Button) root.findViewById(R.id.post_button);
            close_back = (ImageButton) root.findViewById(R.id.btn_back_comment_review);
           // mCoordinator = (CoordinatorLayout) root.findViewById(R.id.root_coordinator);             mCollapsingToolbarLayout = (CollapsingToolbarLayout) root.findViewById(R.id.collapsing_toolbar_layout);
            recycler = (RecyclerView) root.findViewById(R.id.commentpagelist_recyclerview);
            recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycler.setNestedScrollingEnabled(false);
            pdialog = new ProgressDialog(getActivity());
            pdialog.show();
            pdialog.setContentView(R.layout.custom_progressdialog);
            pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            titles.setTypeface(tf);
            titles.setText("Comments");
            post_review.setTypeface(tf);
            post_review.setText("POST");
            close_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialogFragment.this.dismiss();
                }
            });
            post_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Showing the progress dialog
                    //final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlpost,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    //Disimissing the progress dialog
                                    //  loading.dismiss();
                                    //Showing toast message of the response
                                    if (s.equalsIgnoreCase("error")) {
                                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                                    } else {
                                        MyDialogFragment.this.dismiss();

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    //Dismissing the progress dialog
                                    //  loading.dismiss();

                                    //Showing toast
                                    // Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //Converting Bitmap to String


                            //Getting Image Name
                            String description = commentbox.getText().toString().trim();

                            //Creating parameters
                            Map<String, String> params = new Hashtable<String, String>();

                            //Adding parameters
                            if (postid != null) {
                                if (description != null) {
                                    params.put(KEY_COMMENT, description);
                                    params.put(KEY_TYPE, "music");
                                    params.put(KEY_POSTID, postid);
                                    params.put(KEY_MYUID, myuserid);
                                }

                            }
                            return params;
                        }
                    };

                    //Creating a Request Queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    //Adding request to the queue
                    requestQueue.add(stringRequest);
                    //queue.add(stringRequest);
                }

            });

            rcAdapter = new RecyclerViewAdapter(commentlist,recycler);
            recycler.setAdapter(rcAdapter);
            getData();
            rcAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    Log.e("haint", "Load More");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("haint", "Load More 2");


                            getData();


                            rcAdapter.setLoaded();
                        }
                    }, 2000);
                }
            });

            return root;
        }

        public void dissmissDialog() {
            // TODO Auto-generated method stub
            if (pdialog != null) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
                pdialog = null;
            }

        }

        public void onDestroy() {
            super.onDestroy();
            dissmissDialog();
        }

        static class UserViewHolder extends RecyclerView.ViewHolder {
            public TextView name, locations,commentsdecription;


            public NetworkImageView imageview;

            public UserViewHolder(View itemView) {
                super(itemView);

                // im = (ImageView) itemView.findViewById(R.id.imageViewlitle);

                imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


                name = (TextView) itemView.findViewById(R.id.name);
                locations = (TextView) itemView.findViewById(R.id.location);

                commentsdecription = (TextView) itemView.findViewById(R.id.all_page_descriptions);
            }
        }

        static class LoadingViewHolder extends RecyclerView.ViewHolder {
            public ProgressBar progressBar;

            public LoadingViewHolder(View itemView) {
                super(itemView);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
            }
        }

        private void getData() {
            //Adding the method to the queue by calling the method getDataFromServer
            requestQueue.add(getDataFromTheServer(requestCount));
            // getDataFromTheServer();
            //Incrementing the request counter
            requestCount++;
        }

        private JsonObjectRequest getDataFromTheServer(int requestCount) {

            URLTWO = URLCOMMENT +postid+"&page="+ requestCount;


            Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(URLTWO);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                        // dissmissDialog();
                        parseJsonFeed(new JSONObject(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } else {
                // making fresh volley request and getting json
                jsonReq = new JsonObjectRequest(Request.Method.GET,
                        URLTWO, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                        if (response != null) {
                            //   dissmissDialog();
                            parseJsonFeed(response);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
                    }
                });

                // Adding request to volley request queue
                jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsonReq);
            }
            return jsonReq;
        }

        private void parseJsonFeed(JSONObject response) {
            try {
                feedArray = response.getJSONArray("result");


                for (ii = 0; ii < feedArray.length(); ii++) {
                    obj = (JSONObject) feedArray.get(ii);

                    ItemModels model = new ItemModels();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("thumb") ? null : obj
                            .getString("thumb");
                    model.setProfilepic(image);
                    model.setComment(obj.getString("comment"));
                    model.setPadate(obj.getString("pdate"));
                    model.setName(obj.getString("name"));
                    model.setId(obj.getString("id"));


                    commentlist.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();

                // notify data changes to list adapater


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        class  ItemModels{
            private String id,comment,padate,name,profilepic;

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPadate() {
                return padate;
            }

            public void setPadate(String padate) {
                this.padate = padate;
            }

            public String getProfilepic() {
                return profilepic;
            }

            public void setProfilepic(String profilepic) {
                this.profilepic = profilepic;
            }

        }
        class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
            private LayoutInflater inflater;

            ImageLoader mImageLoader;
            private final int VIEW_TYPE_ITEM = 1;
            private final int VIEW_TYPE_LOADING = 2;
            private final int VIEW_TYPE_FEATURE = 0;
            private boolean loading;
            private OnLoadMoreListener onLoadMoreListener;

            private int visibleThreshold = 5;
            private int lastVisibleItem, totalItemCount;
            Context context;

            public RecyclerViewAdapter(List<ItemModels> students, RecyclerView recyclerView) {
                commentlist = students;

                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

                    final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                            .getLayoutManager();


                    recyclerView
                            .addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView,
                                                       int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    totalItemCount = linearLayoutManager.getItemCount();
                                    lastVisibleItem = linearLayoutManager
                                            .findLastVisibleItemPosition();
                                    if (!loading
                                            && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                        // End has been reached
                                        // Do something
                                        if (onLoadMoreListener != null) {
                                            onLoadMoreListener.onLoadMore();
                                        }
                                        loading = true;
                                    }
                                }
                            });
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
                if (viewType == VIEW_TYPE_ITEM) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_comment_all, parent, false);
                    return new UserViewHolder(view);
                } else if (viewType == VIEW_TYPE_LOADING) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading_item, parent, false);
                    return new LoadingViewHolder(view);
                }
                return null;

            }


            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                if (holder instanceof UserViewHolder) {

                    final UserViewHolder userViewHolder = (UserViewHolder) holder;

                    String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                    Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                    if (mImageLoader == null)
                        mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();


                    ItemModels itemmodel = commentlist.get(position);


                    userViewHolder.name.setText(itemmodel.getName());
                    userViewHolder.name.setTypeface(seguiregular);
                    userViewHolder.commentsdecription.setText(itemmodel.getComment());
                    userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                    userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

                    userViewHolder.imageview.setImageUrl(itemmodel.getProfilepic(), mImageLoader);
                    // userViewHolder.im.setVisibility(View.VISIBLE);

                } else if (holder instanceof LoadingViewHolder) {
                    LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                    loadingViewHolder.progressBar.setIndeterminate(true);
                }

            }

            public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
                this.onLoadMoreListener = onLoadMoreListener;
            }


            public int getItemViewType(int position) {


                return commentlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

            }

            public void setLoaded() {
                loading = false;
            }

            public int getItemCount() {
                return commentlist.size();
            }
        }
    }


}

package simplicity_an.simplicity_an.MusicPlayer;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.R;

import static simplicity_an.simplicity_an.MusicPlayer.BackgroundAudioService.mMediaPlayer;
import static simplicity_an.simplicity_an.R.id.seekBar;

/**
 * Created by KuppuSamy on 8/30/2017.
 */

public class RadioNotificationplayer extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,MediaPlayer.OnCompletionListener  {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    RequestQueue requestQueue;
    public static final String backgroundcolor = "color";
    String colorcodes,myprofileid;
    RelativeLayout mainlayout;
    ImageButton back,main,settings;
    boolean doubleBackToExitPressedOnce = false;
   public static TextView category,subtitle,authername,title,timecurrentseekbar,timebalanceseekbar;
   public static SeekBar songduration,sound;
    NetworkImageView imageview;
    ImageButton prev,next,play,soundbutton,menu,favbutton;
    private AudioManager myAudioManager;
    ImageLoader mImageLoader;
    Context context;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();;
    MediaPlayer mediaPlayer;
    public static int oneTimeOnly = 0;
    Boolean isPlayClicked = false;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pdialog;
    String radio_play_url,radio_title,radio_image,radio_page;

    static Handler progressBarHandler = new Handler();
    public static final String Language = "lamguage";
    private static final int STATE_PAUSED = 0;
    private static final int STATE_PLAYING = 1;
    private PlaybackStateCompat mLastPlaybackState;
    private int mCurrentState;
    private MediaBrowserCompat mMediaBrowserCompat;
    String language_data;
    private MediaControllerCompat mMediaControllerCompat;
    private MediaBrowserCompat.ConnectionCallback mMediaBrowserCompatConnectionCallback=new MediaBrowserCompat.ConnectionCallback(){
        @Override
        public void onConnected() {
            super.onConnected();
            try {
                mMediaControllerCompat = new MediaControllerCompat(RadioNotificationplayer.this, mMediaBrowserCompat.getSessionToken());
                mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback);
          //setSupportMediaController(getApplicationContext(),mMediaControllerCompat);
                MediaControllerCompat.setMediaController(RadioNotificationplayer.this, mMediaControllerCompat);

            }catch (RemoteException e){

            }

        }
    };
    private  MediaControllerCompat.Callback mMediaControllerCompatCallback=new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            if( state == null ) {
                return;
            }
            switch (state.getState()){
                case PlaybackStateCompat.STATE_PLAYING :{
                    mCurrentState=STATE_PLAYING;

                    play.setImageResource(R.drawable.pause);
                    break;

                }
                case PlaybackStateCompat.STATE_PAUSED:{
                    mCurrentState=STATE_PAUSED;
                    play.setImageResource(R.drawable.play);
                    break;
                }
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            if (metadata != null) {
                updateMediaDescription(metadata.getDescription());
                updateProgressBar();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateduration();
                    }
                },2000);

                mCurrentState = STATE_PLAYING;
                //updateDuration(metadata);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.radionotificationplayer);
        String simplycity_title_reugular= "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_reugular);
        requestQueue= Volley.newRequestQueue(this);
        mMediaBrowserCompat=new MediaBrowserCompat(this,new ComponentName(this,BackgroundAudioService.class),mMediaBrowserCompatConnectionCallback,getIntent().getExtras());
        mMediaBrowserCompat.connect();


        Intent get=getIntent();
        radio_play_url=get.getStringExtra("URL");
        radio_title=get.getStringExtra("TITLE");
        radio_image=get.getStringExtra("IMAGE");
        radio_page=get.getStringExtra("PAGE");
if(radio_play_url!=null){
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            callSong();
        }
    },2000);
}else {

}












        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        language_data=sharedpreferences.getString(Language,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        mainlayout=(RelativeLayout)findViewById(R.id.version_main_layout);
        if(colorcodes.length()==0){

        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi");
            }else {

                if(colorcodes!=null){
                    int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);
                }else {
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
            }
        }

        back=(ImageButton)findViewById(R.id.btn_ex_back) ;
        main=(ImageButton)findViewById(R.id.btn_ex_search);
        settings=(ImageButton)findViewById(R.id.btn_ex_more);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();

                if(language_data.equals("English")) {
                    if(radio_page!=null){
                        Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
                        MediaControllerCompat controller = MediaControllerCompat.getMediaController(RadioNotificationplayer.this);
                        MediaMetadataCompat metadata = controller.getMetadata();
                        if (metadata != null) {
                            i.putExtra("DESC",
                                    metadata.getDescription());
                        }
                        i.putExtra("ID","2");
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
                        MediaControllerCompat controller = MediaControllerCompat.getMediaController(RadioNotificationplayer.this);
                        MediaMetadataCompat metadata = controller.getMetadata();
                        if (metadata != null) {
                            i.putExtra("DESC",
                                    metadata.getDescription());
                        }
                        i.putExtra("ID", "1");
                        startActivity(i);
                    }

                }else {
                    if(radio_page!=null){
                        Intent i = new Intent(getApplicationContext(), MainPageTamil.class);
                        MediaControllerCompat controller = MediaControllerCompat.getMediaController(RadioNotificationplayer.this);
                        MediaMetadataCompat metadata = controller.getMetadata();
                        if (metadata != null) {
                            i.putExtra("DESC",
                                    metadata.getDescription());
                        }
                        i.putExtra("ID","2");
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getApplicationContext(), MainPageTamil.class);
                        MediaControllerCompat controller = MediaControllerCompat.getMediaController(RadioNotificationplayer.this);
                        MediaMetadataCompat metadata = controller.getMetadata();
                        if (metadata != null) {
                            i.putExtra("DESC",
                                    metadata.getDescription());
                        }
                        i.putExtra("ID", "1");
                        startActivity(i);
                    }

                }

                //finish();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(language_data.equals("English")) {
                    if(radio_page!=null){
                        Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
                        i.putExtra("ID","2");
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
                        i.putExtra("ID", "1");
                        startActivity(i);
                    }

                }else {
                    if(radio_page!=null){
                        Intent i = new Intent(getApplicationContext(), MainPageTamil.class);
                        i.putExtra("ID","2");
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getApplicationContext(), MainPageTamil.class);
                        i.putExtra("ID", "1");
                        startActivity(i);
                    }

                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(language_data.equals("English")) {
                    if(radio_page!=null){
                        Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
                        i.putExtra("ID","2");
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
                        i.putExtra("ID", "1");
                        startActivity(i);
                    }

                }else {
                    if(radio_page!=null){
                        Intent i = new Intent(getApplicationContext(), MainPageTamil.class);
                        i.putExtra("ID","2");
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getApplicationContext(), MainPageTamil.class);
                        i.putExtra("ID", "1");
                        startActivity(i);
                    }

                }
            }
        });
        mImageLoader =  MySingleton.getInstance(getApplicationContext()).getImageLoader();
        imageview=(NetworkImageView)findViewById(R.id.thumbnailone);





        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        timebalanceseekbar=(TextView)findViewById(R.id.songDuration);
        timecurrentseekbar=(TextView)findViewById(R.id.songDurationfullleft);
        category=(TextView)findViewById(R.id.textView_catgory);
        authername=(TextView)findViewById(R.id.textView_authername);

        songduration=(SeekBar)findViewById(seekBar);

        prev=(ImageButton)findViewById(R.id.previous);
        next=(ImageButton)findViewById(R.id.next);
        play=(ImageButton)findViewById(R.id.play);

        category.setTypeface(seguiregular);
        authername.setTypeface(seguiregular);
        category.setText(radio_title);
        timecurrentseekbar.setTypeface(seguiregular);
        timebalanceseekbar.setTypeface(seguiregular);

songduration.setOnSeekBarChangeListener(this);



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if( mCurrentState == STATE_PAUSED ) {
                    MediaControllerCompat.getMediaController(RadioNotificationplayer.this).getTransportControls().play();
                    play.setImageResource(R.drawable.pause);
                    Log.e("media","PAUSE");
                    mCurrentState = STATE_PLAYING;
                } else {
                    Log.e("media","PLAY");
                    if( MediaControllerCompat.getMediaController(RadioNotificationplayer.this).getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING ) {
                        MediaControllerCompat.getMediaController(RadioNotificationplayer.this).getTransportControls().pause();
                        play.setImageResource(R.drawable.play);
                    }

                    mCurrentState = STATE_PAUSED;
                }
            }
        });




if(radio_image!=null){
    imageview.setImageUrl(radio_image,mImageLoader);
    imageview.setErrorImageResId(R.drawable.ic_launcher);
    imageview.setDefaultImageResId(R.drawable.ic_launcher);
}else {
    imageview.setErrorImageResId(R.drawable.ic_launcher);
    imageview.setDefaultImageResId(R.drawable.ic_launcher);
}
        imageview.setErrorImageResId(R.drawable.ic_launcher);
        imageview.setDefaultImageResId(R.drawable.ic_launcher);


        if (savedInstanceState == null) {
            updateFromParams(getIntent());
        }




    }
    private void updateFromParams(Intent intent) {
        if (intent != null) {
            MediaDescriptionCompat description = intent.getParcelableExtra(
                    "DESC");
            if (description != null) {
                updateMediaDescription(description);
            }
        }
    }
    private void updateMediaDescription(MediaDescriptionCompat description) {
        if (description == null) {
            return;
        }
       // LogHelper.d(TAG, "updateMediaDescription called ");
       category.setText(description.getTitle());
        play.setImageResource(R.drawable.pause);
        updateProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateduration();
            }
        },2000);

        mCurrentState = STATE_PLAYING;
      /*  mLine2.setText(description.getSubtitle());
        fetchImageAsync(description);*/
    }



    private void callSong(){

        play.setImageResource(R.drawable.pause);
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",radio_title);
        bundle.putString("IMAGE", radio_image);
        MediaControllerCompat.getMediaController(RadioNotificationplayer.this).getTransportControls().playFromMediaId(radio_play_url,bundle);
        MediaControllerCompat.getMediaController(RadioNotificationplayer.this).getTransportControls().play();

        updateProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateduration();
            }
        },2000);

        mCurrentState = STATE_PLAYING;
    }
    private void updateduration(){

        int duration = BackgroundAudioService.mMediaPlayer.getDuration();
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );
        Log.e("Time",time);
        timebalanceseekbar.setText(time.toString());
    }
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
            return;
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





  @Override
  protected void onDestroy() {
      super.onDestroy();
      if( MediaControllerCompat.getMediaController(RadioNotificationplayer.this).getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING ) {
          MediaControllerCompat.getMediaController(RadioNotificationplayer.this).getTransportControls().pause();
      }

      mMediaBrowserCompat.disconnect();
  }

    @Override
    protected void onStart() {
        super.onStart();

        startService(new Intent(RadioNotificationplayer.this, BackgroundAudioService.class));
    }

    private void callsong( ){

        int idval=0;
        int  next=0;


        try {
            if(radio_play_url!=null){
                mediaPlayer.setDataSource(radio_play_url);
            }


          //  for (int i = 0; i < modelList.size(); i++) {
//idval=Integer.parseInt(((ItemModel)modelList.get(i)).getId());
               /* idval=((Radioplayeractivity.ItemModel)modelList.get(i)).getTypeid();
                Log.e("valnext",String.valueOf(idval));
*/
               // if(myuserinteger==idval) {
                   // mediaPlayer.setDataSource(((Radioplayeractivity.ItemModel)modelList.get(position)).getLinks());
                    // prev=((ItemModel)modelList.get(position-1)).getId();


                    // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    mediaPlayer.prepare();
              /*  }
            }*/

            // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
        } catch (IllegalStateException e) {
            //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            play.setImageResource(R.drawable.pause);
        } else {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                play.setImageResource(R.drawable.play);
            } else {
                mediaPlayer.reset();
                play.setImageResource(R.drawable.play);
            }

        }
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

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
      //  myHandler.postDelayed(UpdateSongTime,100);
    }




    public void updateProgressBar(){
        try{
            progressBarHandler.postDelayed(mUpdateTimeTask, 100);
        }catch(Exception e){

        }
    }

    static Runnable mUpdateTimeTask = new Runnable() {
        public void run(){
            long totalDuration = 0;
            long currentDuration = 0;

            try {
                totalDuration = mMediaPlayer.getDuration();
                currentDuration = mMediaPlayer.getCurrentPosition();
                timecurrentseekbar.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) currentDuration),
                        TimeUnit.MILLISECONDS.toSeconds((long) currentDuration) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) currentDuration)))
                );

                int progress = (int)(getProgressPercentage(currentDuration, totalDuration));
                songduration.setProgress(progress);	/* Running this thread after 100 milliseconds */
                progressBarHandler.postDelayed(this, 100);

            } catch(Exception e){
                e.printStackTrace();
            }

        }
    };





    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        progressBarHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        progressBarHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mMediaPlayer.getDuration();
        int currentPosition = progressToTimer(seekBar.getProgress(),totalDuration);
        mMediaPlayer.seekTo(currentPosition);
        updateProgressBar();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        if( BackgroundAudioService.mMediaPlayer != null ) {
            BackgroundAudioService.mMediaPlayer.release();
            play.setImageResource(R.drawable.play);
        }
    }
}

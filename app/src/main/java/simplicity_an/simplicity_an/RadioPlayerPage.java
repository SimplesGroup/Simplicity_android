package simplicity_an.simplicity_an;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 4/5/2016.
 */
public class RadioPlayerPage extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
    TextView category,subtitle,authername,title,timecurrentseekbar,timebalanceseekbar;
    SeekBar songduration,sound;
    NetworkImageView imageview;
    ImageButton prev,next,play,soundbutton,menu,favbutton;
    private AudioManager myAudioManager;

    ImageLoader mImageLoader;
  static String linksong;
    ImageButton back,home;
   Context context;


    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();;
    private TextView tx1,tx2,tx3;
    MediaPlayer mediaPlayer;
    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.radioplayerpage);
        mImageLoader =  MySingleton.getInstance(getApplicationContext()).getImageLoader();
        imageview=(NetworkImageView)findViewById(R.id.thumbnailone);
        menu=(ImageButton)findViewById(R.id.menuinterface);
        favbutton=(ImageButton)findViewById(R.id.faveheart);
        back=(ImageButton)findViewById(R.id.btn_1);
        home=(ImageButton)findViewById(R.id.btn_3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent back_radio=new Intent(RadioPlayerPage.this,RadioActivity.class);
                startActivity(back_radio);
                finish();*/
                onBackPressed();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home_radio=new Intent(RadioPlayerPage.this,MainPageEnglish.class);
                startActivity(home_radio);
                finish();
            }
        });
        Intent get=getIntent();
        String catname=get.getStringExtra("CATNAME");
        final String catid=get.getStringExtra("ID");
        String partname=get.getStringExtra("PART");
        String date=get.getStringExtra("DATE");
        String name=get.getStringExtra("NAME");
        String titlestring=get.getStringExtra("TITLE");
         linksong=get.getStringExtra("LINK");
        final String imagesong=get.getStringExtra("IMAGE");
        Toast.makeText(getApplicationContext(), linksong, Toast.LENGTH_SHORT).show();
menu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in=new Intent(getApplicationContext(),RadioprogramsInnerpage.class);
        in.putExtra("ID",catid);
        in.putExtra("IMAGE",imagesong);
        startActivity(in);
        finish();
    }
});
        favbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googlelogin=new Intent(getApplicationContext(),SigninpageActivity.class);
                googlelogin.putExtra("ACTIVITY","radio");
                startActivity(googlelogin);
                finish();
            }
        });
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                    mediaPlayer.pause();
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                    mediaPlayer.start();
                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //A call is dialing, active or on hold
                    mediaPlayer.pause();
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
        subtitle=(TextView)findViewById(R.id.subtitle_radioplayer);
        songduration=(SeekBar)findViewById(R.id.seekBar);

        prev=(ImageButton)findViewById(R.id.previous);
        next=(ImageButton)findViewById(R.id.next);
        play=(ImageButton)findViewById(R.id.play);

        category.setTypeface(seguiregular);
        authername.setTypeface(seguiregular);
        title.setTypeface(seguiregular);
        subtitle.setTypeface(seguiregular);
        timecurrentseekbar.setTypeface(seguiregular);
        timebalanceseekbar.setTypeface(seguiregular);
        category.setText(catname);
        String by="By&nbsp;";
        authername.setText(Html.fromHtml(by+""+name));

        title.setText(partname);
        subtitle.setText(titlestring);

        imageview.setImageUrl(imagesong, mImageLoader);
        songduration.setMax((int) finalTime);
        /*myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        sound.setMax(myAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sound.setProgress(myAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));*/
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callsong();

        /*        try {

                    String url = "http:\\/\\/simpli-city.in\\/smp_interface\\/images\\/radio\\/audio\\/Fugu_mixdown.mp3";
                    url = url.replace("\\", "");
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        play.setImageResource(R.drawable.pause);
                    } else {
                        if (mediaPlayer.isPlaying()) {
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

                    songduration.setProgress((int) startTime);
                    myHandler.postDelayed(UpdateSongTime, 100);
                    // might take long! (for buffering, etc)
                    //mediaPlayer.start();


                } catch (IOException e) {
                    e.printStackTrace();
                }*/

               /* int duration = mediaPlayer.getDuration();

                String maxTimeString = RadioPlayerPagetamil.this.millisecondsToString(duration);
                timebalanceseekbar.setText(maxTimeString);
                finalTime = mediaPlayer.getDuration();
                timeElapsed = mediaPlayer.getCurrentPosition();
                songduration.setProgress((int) timeElapsed);
                durationHandler.postDelayed(moveSeekBarThread, 100);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.pause);
                } else {
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.play);
                }*/
                // callsong();
            }
        });

       // mediaPlayer.setOnCompletionListener(this);


        /*play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    linksong=linksong.replace("\\", "");
                    mediaPlayer.setDataSource(linksong);
                  //  mediaPlayer.prepare(); // might take long! (for buffering, etc)


                } catch (IllegalArgumentException e) {
                   // Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                   // Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                  //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

               *//* int duration = mediaPlayer.getDuration();

                String maxTimeString = RadioPlayerPagetamil.this.millisecondsToString(duration);
                timebalanceseekbar.setText(maxTimeString);
                finalTime = mediaPlayer.getDuration();
                timeElapsed = mediaPlayer.getCurrentPosition();
                songduration.setProgress((int) timeElapsed);
                durationHandler.postDelayed(moveSeekBarThread, 100);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.pause);
                } else {
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.play);
                }*//*
             // callsong();
            }
        });*/
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
     songduration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                try {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        if (fromUser)
                            mediaPlayer.seekTo(progress);
                    } else if (mediaPlayer == null) {
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
    }

    public void onCompletion(MediaPlayer mp) {
        mediaPlayer.stop();
        mediaPlayer.reset();
        songduration.setProgress(0);
        play.setImageResource(R.drawable.play);
        // Log.i("Completion Listener","Song Complete");
        // Toast.makeText(getApplicationContext(), "Media Completed", Toast.LENGTH_SHORT).show();
    }

    public void callsong(){
        try {

         /*   mediaPlayer.setDataSource(linksong); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
            mediaPlayer.prepare();*/
           //String url="http://simpli-city.in//smp_interface//images//radio//audio//Company%20names%2022_mixdown.mp3";
         //  linksong= linksong.replace("\\", "");
            String url=linksong;
            mediaPlayer.setDataSource(url);
            //mediaPlayer.prepare();
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
        myHandler.postDelayed(UpdateSongTime,100);
    }





  /* public void callsong(){
       try {
           mediaPlayer.setDataSource(linksong); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
           mediaPlayer.prepare();

           // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
       } catch (Exception e) {
           e.printStackTrace();
       }
       int duration = mediaPlayer.getDuration();

       String maxTimeString = this.millisecondsToString(duration);
       timebalanceseekbar.setText(maxTimeString);
       finalTime = mediaPlayer.getDuration();
       timeElapsed = mediaPlayer.getCurrentPosition();
       songduration.setProgress((int) timeElapsed);
       durationHandler.postDelayed(moveSeekBarThread, 100);
       if (!mediaPlayer.isPlaying()) {
           mediaPlayer.start();
           play.setImageResource(R.drawable.pause);
       } else {
           mediaPlayer.pause();
           play.setImageResource(R.drawable.play);
       }
   }
    private String millisecondsToString(int milliseconds)  {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds =  TimeUnit.MILLISECONDS.toSeconds((long) milliseconds) ;
        return minutes+":"+ seconds;
    }
*/
   /* private Runnable moveSeekBarThread = new Runnable() {
        public void run() {
            if(mediaPlayer.isPlaying()){


                int currentPosition = mediaPlayer.getCurrentPosition();
                String currentPositionStr = RadioPlayerPagetamil.this.millisecondsToString(currentPosition);
                timecurrentseekbar.setText(currentPositionStr);
                timeElapsed = mediaPlayer.getCurrentPosition();
                int mediaPos_new = mediaPlayer.getCurrentPosition();
                int mediaMax_new = mediaPlayer.getDuration();
                songduration.setMax(mediaMax_new);
                songduration.setProgress(mediaPos_new);
                double timeRemaining = finalTime - timeElapsed;

            //timebalanceseekbar.setText(String.format("%dmin, %dsec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
//timecurrentseekbar.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed), TimeUnit.MILLISECONDS.toSeconds((long) timeElapsed) ));

                durationHandler.postDelayed(this, 100); //Looping the thread after 0.1 second
            }

        }
    };*/
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //if one of the volume keys were pressed
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            //change the seek bar progress indicator position
            sound.setProgress(myAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
        //propagate the key event
        return super.onKeyDown(keyCode, event);
    }

}

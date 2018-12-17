package simplicity_an.simplicity_an;

import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import simplicity_an.simplicity_an.MusicPlayer.BackgroundAudioService;
import simplicity_an.simplicity_an.MusicPlayer.RadioNotificationplayer;

/**
 * Created by kuppusamy on 2/16/2017.
 */
public class MusicplayerBottom extends Fragment  {
ImageButton playpause,forward,backward,close;
 public static LinearLayout musicbotttomfraglayout;
   public static MediaPlayer mediaPlayer;
    String playurl,music_title;
    TextView music_title_labels;
    MediaPlayer mediaPlayers;
    private PlaybackStateCompat mLastPlaybackState;
    private int mCurrentState;
    private MediaBrowserCompat mMediaBrowserCompat;
    String language_data;
    String radio_play_url,radio_title,radio_image,radio_page;
    private MediaControllerCompat mMediaControllerCompat;
    public static final String Language = "lamguage";
    private static final int STATE_PAUSED = 0;
    private static final int STATE_PLAYING = 1;



    private MediaBrowserCompat.ConnectionCallback mMediaBrowserCompatConnectionCallback=new MediaBrowserCompat.ConnectionCallback(){
        @Override
        public void onConnected() {
            super.onConnected();
            try {
                mMediaControllerCompat = new MediaControllerCompat(getActivity(), mMediaBrowserCompat.getSessionToken());
                mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback);
               //getActivity().setSupportMediaController(mMediaControllerCompat);
                MediaControllerCompat.setMediaController(getActivity(), mMediaControllerCompat);
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

                    playpause.setImageResource(R.mipmap.pauseblack);

                        musicbotttomfraglayout.setVisibility(View.VISIBLE);


                        Log.e("TAG","NO RUN");


                    break;

                }
                case PlaybackStateCompat.STATE_PAUSED:{
                    mCurrentState=STATE_PAUSED;
                    playpause.setImageResource(R.mipmap.playblack);
                    break;
                }
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            if (metadata != null) {
                updateMediaDescription(metadata.getDescription());
                //updateProgressBar();

                mCurrentState = STATE_PLAYING;
                //updateDuration(metadata);
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.musicbottomplayer,container,false);

        mMediaBrowserCompat=new MediaBrowserCompat(getActivity(),new ComponentName(getActivity(),BackgroundAudioService.class),mMediaBrowserCompatConnectionCallback,null);
        mMediaBrowserCompat.connect();

        musicbotttomfraglayout=(LinearLayout)view.findViewById(R.id.musicfrag) ;
close=(ImageButton)view.findViewById(R.id.closemusics);
        playpause=(ImageButton)view.findViewById(R.id.play);

        music_title_labels=(TextView)view.findViewById(R.id.title_musicname) ;
        music_title_labels.setText(music_title);

        //mediaPlayer.stop();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      /*  if(playurl!=null){
            callsong();
        }else {

        }*/

       /* if(BackgroundAudioService.mMediaPlayer.isPlaying()){
            musicbotttomfraglayout.setVisibility(View.VISIBLE);
        }else {
            musicbotttomfraglayout.setVisibility(View.INVISIBLE);
            Log.e("TAG","NO RUN");
        }
        if(BackgroundAudioService.mMediaPlayer.isPlaying()){
            musicbotttomfraglayout.setVisibility(View.VISIBLE);
        }*/
musicbotttomfraglayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), RadioNotificationplayer.class);
        intent.putExtra("IMAGE",radio_image);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        MediaMetadataCompat metadata = controller.getMetadata();
        if (metadata != null) {
            intent.putExtra("DESC",
                    metadata.getDescription());
        }
        startActivity(intent);
/*Intent in=new Intent(getActivity(), RadioNotificationplayer.class);
        startActivity(in);*/
    }
});
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               BackgroundAudioService.mMediaPlayer.stop();
               musicbotttomfraglayout.setVisibility(View.INVISIBLE);
//MusicplayerBottom.this.dismiss();
            }
        });
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(mediaPlayer.isPlaying()){
                    playpause.setImageResource(R.mipmap.playblack);
                    mediaPlayer.pause();
                }else {
                    playpause.setImageResource(R.mipmap.pauseblack);
                    mediaPlayer.start();
                }*/

                if( mCurrentState == STATE_PAUSED ) {
                    MediaControllerCompat.getMediaController(getActivity()).getTransportControls().play();
                    playpause.setImageResource(R.mipmap.pauseblack);
                    Log.e("media","PAUSE");
                    mCurrentState = STATE_PLAYING;
                } else {
                    Log.e("media","PLAY");
                    if( MediaControllerCompat.getMediaController(getActivity()).getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING ) {
                        MediaControllerCompat.getMediaController(getActivity()).getTransportControls().pause();
                        playpause.setImageResource(R.mipmap.playblack);
                    }

                    mCurrentState = STATE_PAUSED;
                }

            }
        });

        if (savedInstanceState == null) {
            updateFromParams(getActivity().getIntent());
        }


        return view;
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
        music_title_labels.setText(description.getTitle());
        playpause.setImageResource(R.mipmap.pauseblack);
musicbotttomfraglayout.setVisibility(View.VISIBLE);
        mCurrentState = STATE_PLAYING;
      /*  mLine2.setText(description.getSubtitle());
        fetchImageAsync(description);*/
    }
    public void PlaySong(String playurl, String title,String image){
        musicbotttomfraglayout.setVisibility(View.VISIBLE);
        music_title_labels.setText(title);
        radio_play_url=playurl;
        radio_title=title;
        radio_image=image;

        callSong();

    }
    private void callSong(){

        playpause.setImageResource(R.mipmap.pauseblack);
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",radio_title);
        bundle.putString("IMAGE", radio_image);
        MediaControllerCompat.getMediaController(getActivity()).getTransportControls().playFromMediaId(radio_play_url,bundle);
        MediaControllerCompat.getMediaController(getActivity()).getTransportControls().play();



        mCurrentState = STATE_PLAYING;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if( MediaControllerCompat.getMediaController(getActivity()).getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING ) {
            MediaControllerCompat.getMediaController(getActivity()).getTransportControls().pause();
        }

        mMediaBrowserCompat.disconnect();
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().startService(new Intent(getActivity(),BackgroundAudioService.class));

    }

}

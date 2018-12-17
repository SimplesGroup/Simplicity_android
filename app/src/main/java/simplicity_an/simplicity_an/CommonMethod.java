package simplicity_an.simplicity_an;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by kuppusamy on 2/17/2017.
 */
public class CommonMethod {
    public static MediaPlayer mediaPlayer;
    public static void SoundPlayer(String playurl, String title){
        try {
mediaPlayer=new MediaPlayer();

           // musicbotttomfraglayout.setVisibility(View.VISIBLE);
            //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(playurl);
            mediaPlayer.prepare();
            mediaPlayer.start();
            //music_title_labels.setText(title);
        } catch (IllegalStateException e) {
            //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }


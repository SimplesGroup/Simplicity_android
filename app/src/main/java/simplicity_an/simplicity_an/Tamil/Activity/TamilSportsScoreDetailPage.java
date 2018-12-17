package simplicity_an.simplicity_an.Tamil.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.R;


/**
 * Created by kuppusamy on 3/16/2016.
 */
public class TamilSportsScoreDetailPage extends Activity {
    TextView sportsname,trophyname,finalscore,finalscoreone,finalscoretwo,scorecard,firstinnings,secondinnings,firstinningsdetail,secondinningsdetail;
    TextView teamnameone,teamnametwo,teamtwoinnigsone,teamtwoinningstwo,bestplayer,bestplayername,gameinformation,venue,time,refree,gallery;
    WebView galleryImages;
    /*********************tennis***/
TextView setone,settwo,setthree,setfour,setfive,total,teamnameone_tennis,teamnametwo_tennis,teamnameone_tennis_setone,teamnameone_tennis_settwo,teamnameone_tennis_setthree,teamnameone_tennis_setfour,teamnameone_tennis_setfive,teamnameone_tennis_settotal;
    TextView teamnametwo_tennis_setone,teamnametwo_tennis_settwo,teamnametwo_tennis_setthree,teamnametwo_tennis_setfour,teamnametwo_tennis_setfive,teamnametwo_tennis_settotal;
    /*****foootball************/
    TextView firsthalf,secondhalf,footballtotal,teamname_footone,teamname_foottwo,teamone_foot_firsthalf,teamone_foot_secondhalf,teamone_totalgoal,teamtwo_foot_firsthalf,teamtwo_foot_secondhalf,teamtwo_totalgoal;


    ImageButton back,main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sports_score_detail);
        sportsname=(TextView)findViewById(R.id.sports_category);
        trophyname=(TextView)findViewById(R.id.sports_title);
        finalscore=(TextView)findViewById(R.id.result);
        finalscoreone=(TextView)findViewById(R.id.teamnameone_result);
        finalscoretwo=(TextView)findViewById(R.id.teamnametwo_result);
        scorecard=(TextView)findViewById(R.id.sports_scorecard);
        firstinnings=(TextView)findViewById(R.id.inningsone);
        secondinnings=(TextView)findViewById(R.id.inningstwo);
        firstinningsdetail=(TextView)findViewById(R.id.teamnameone_score_firstinnings);
        secondinningsdetail=(TextView)findViewById(R.id.teamnameone_score_twoinnings);
        teamnameone=(TextView)findViewById(R.id.teamnameone);
        teamnametwo=(TextView)findViewById(R.id.teamnametwo);
        teamtwoinnigsone=(TextView)findViewById(R.id.teamnametwo_score_firstinnings);
        teamtwoinningstwo=(TextView)findViewById(R.id.teamnametwo_score_twoinnings);
        bestplayer=(TextView)findViewById(R.id.sports_bestplayer);
        bestplayername=(TextView)findViewById(R.id.sports_bestplayer_name);
        gameinformation=(TextView)findViewById(R.id.sports_gameinformationtitle);
        venue=(TextView)findViewById(R.id.sports_gameinformation_venue);
        time=(TextView)findViewById(R.id.sports_gameinformation_time);
        refree=(TextView)findViewById(R.id.sports_gameinformation_refree);
        gallery=(TextView)findViewById(R.id.sports_gallery);
        galleryImages=(WebView)findViewById(R.id.gallery_webview);
/************Tennis declaration*************/

        setone=(TextView)findViewById(R.id.setone);
        settwo=(TextView)findViewById(R.id.settwo);
        setthree=(TextView)findViewById(R.id.setthree);
        setfour=(TextView)findViewById(R.id.setfour);
        setfive=(TextView)findViewById(R.id.setfive);
        total=(TextView)findViewById(R.id.total);
        teamnameone_tennis=(TextView)findViewById(R.id.teambameone_tennis);
        teamnameone_tennis_setone=(TextView)findViewById(R.id.setone_score);
        teamnameone_tennis_settwo=(TextView)findViewById(R.id.settwoscore);
        teamnameone_tennis_setthree=(TextView)findViewById(R.id.setthreescore);
        teamnameone_tennis_setfour=(TextView)findViewById(R.id.setfourscore);
        teamnameone_tennis_setfive=(TextView)findViewById(R.id.setfivescore);
        teamnameone_tennis_settotal=(TextView)findViewById(R.id.totalscore);

        teamnametwo_tennis=(TextView)findViewById(R.id.teambameone_tennistwo);
        teamnametwo_tennis_setone=(TextView)findViewById(R.id.setone_scoretwo);
        teamnametwo_tennis_settwo=(TextView)findViewById(R.id.settwoscoretwo);
        teamnametwo_tennis_setthree=(TextView)findViewById(R.id.setthreescoretwo);
        teamnametwo_tennis_setfour=(TextView)findViewById(R.id.setfourscoretwo);
        teamnametwo_tennis_setfive=(TextView)findViewById(R.id.setfivescoretwo);
        teamnametwo_tennis_settotal=(TextView)findViewById(R.id.totalscoretwo);
        /************Tennis End declaration*************/
        /************FOOTBALL declaration*************/
        firsthalf=(TextView)findViewById(R.id.inningsonefoot);
        secondhalf=(TextView)findViewById(R.id.inningstwofoot);
        footballtotal=(TextView)findViewById(R.id.totalfoot);
        teamname_footone=(TextView)findViewById(R.id.teamnameonefoot);
        teamname_foottwo=(TextView)findViewById(R.id.teamnametwofoot_);
        teamone_foot_firsthalf=(TextView)findViewById(R.id.teamnameonefoot__score_firstinnings);
        teamone_foot_secondhalf=(TextView)findViewById(R.id.teamnameonefoot__score_twoinnings);
        teamone_totalgoal=(TextView)findViewById(R.id.totalfootone);

        teamtwo_foot_firsthalf=(TextView)findViewById(R.id.teamnametwofoot__score_firstinnings);
        teamtwo_foot_secondhalf=(TextView)findViewById(R.id.teamnametwofoot_score_twoinnings);
        teamtwo_totalgoal=(TextView)findViewById(R.id.totalfoottwo);
        /************FOOTBALL end declaration*************/
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        sportsname.setTypeface(tf);
        trophyname.setTypeface(tf);
        finalscore.setTypeface(tf);
        finalscoreone.setTypeface(tf);
        finalscoretwo.setTypeface(tf);
        scorecard.setTypeface(tf);
        firstinnings.setTypeface(tf);
        secondinnings.setTypeface(tf);
        firstinningsdetail.setTypeface(tf);
        secondinningsdetail.setTypeface(tf);
        teamnameone.setTypeface(tf);
        teamnametwo.setTypeface(tf);
        teamtwoinnigsone.setTypeface(tf);
        teamtwoinningstwo.setTypeface(tf);
        bestplayer.setTypeface(tf);
        bestplayername.setTypeface(tf);
        gameinformation.setTypeface(tf);
        venue.setTypeface(tf);
        time.setTypeface(tf);
        refree.setTypeface(tf);
        gallery.setTypeface(tf);
        /************Tennis declaration*************/
        total.setTypeface(tf);
        teamnameone_tennis.setTypeface(tf);
       teamnametwo_tennis.setTypeface(tf);
        /************Tennis End declaration*************/
        firsthalf.setTypeface(tf);
        secondhalf.setTypeface(tf);
        footballtotal.setTypeface(tf);
        Intent in = getIntent();
        String sportsname_category=in.getStringExtra("TITLESPORTS");
        Log.e("sportstitle:",sportsname_category);
        String trophynames=in.getStringExtra("TROPHY");
        String tournames=in.getStringExtra("TOUR");
        String teamscoreinnings_one=in.getStringExtra("TEAMSCOREONE");
        String teamscoreinnings_one_two=in.getStringExtra("TEAMSCOREONE_TEAMTWO");
        String teamscoreinnings_two=in.getStringExtra("TEAMSCORE_OTHER");
        String teamscoreinnings_two_two=in.getStringExtra("TEAMSCORE_OTHER_TWO");
        String teannamesone=in.getStringExtra("TEAMNAMEONE");
        String teannamestwo=in.getStringExtra("TEAMNAMETWO");
        String venues=in.getStringExtra("VENUE");
        String times=in.getStringExtra("TIME");
        String refrees=in.getStringExtra("RREFREE");
        String bestplayers=in.getStringExtra("BESTPLAYER");
        String dates=in.getStringExtra("DATE");
        String tennistotalone=in.getStringExtra("TENNISTEAMONETOTAL");
        String tennistotaltwo=in.getStringExtra("TENNISTEAMTWOTOTAL");
        String tennisteamone_setone=in.getStringExtra("TENNISTEAMONE_SETONE");
        String tennisteamone_settwo=in.getStringExtra("TENNISTEAMONE_SETTWO");
        String tennisteamone_setthree=in.getStringExtra("TENNISTEAMONE_SETTHREE");
        String tennisteamone_setfour=in.getStringExtra("TENNISTEAMONE_SETFOUR");
        String tennisteamone_setfive=in.getStringExtra("TENNISTEAMONE_SETFIVE");

        String tennisteamtwo_setone=in.getStringExtra("TENNISTEAMTWO_SETONE");
        String tennisteamtwo_settwo=in.getStringExtra("TENNISTEAMTWO_SETTWO");
        String tennisteamtwo_setthree=in.getStringExtra("TENNISTEAMTWO_SETTHREE");
        String tennisteamtwo_setfour=in.getStringExtra("TENNISTEAMTWO_SETFOUR");
        String tennisteamtwo_setfive=in.getStringExtra("TENNISTEAMTWO_SETFIVE");


        String footballteamonetotal=in.getStringExtra("FOOTTEAMONETOTAL");
        String footballteamtwototal=in.getStringExtra("FOOTTEAMTWOTOTAL");
        String footballteamone_fhalf=in.getStringExtra("FOOTTEAMONEFHALF");
        String footballteamone_shalf=in.getStringExtra("FOOTTEAMONESHALF");
        String footballteamtwo_fhalf=in.getStringExtra("FOOTTEAMTWOFHALF");
        String footballteamtwo_shalf=in.getStringExtra("FOOTTEAMTWOSHALF");

back=(ImageButton)findViewById(R.id.btn_1);
        main=(ImageButton)findViewById(R.id.btn_3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(TamilSportsScoreDetailPage.this,TamilSports.class);
                startActivity(back);
                finish();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu=new Intent(TamilSportsScoreDetailPage.this,MainPageTamil.class);
                startActivity(menu);
                finish();
            }
        });
        // String images=in.getStringExtra("IMAGE");


       // String ss = images;

        // Toast.makeText(Second.this,ss,Toast.LENGTH_LONG).show();


       // String s = ss;
        // s = s.replace("\"", "'");
        //s = s.replace("\\", "");
       /// galleryImages.loadData(s, "text/html; charset=UTF-8", null);
        // description.setBackgroundColor(0x0a000000);
      ///  galleryImages.setBackgroundColor(Color.TRANSPARENT);

        if(sportsname_category.equals("Cricket")){


        sportsname.setText(sportsname_category);
        trophyname.setText(tournames+"\n"+teannamesone+""+"Vs"+""+teannamestwo+"\n"+dates);
        finalscore.setText("Final");
        finalscoreone.setText(teamscoreinnings_one + "&" + teamscoreinnings_one_two);
        finalscoretwo.setText(teamscoreinnings_two + "&" + teamscoreinnings_two_two);
        scorecard.setText("SCORECARD");
        firstinningsdetail.setText(teamscoreinnings_one);
        secondinningsdetail.setText(teamscoreinnings_one_two);
        teamnameone.setText(teannamesone);
        teamnametwo.setText(teannamestwo);
        teamtwoinnigsone.setText(teamscoreinnings_two);
        teamtwoinningstwo.setText(teamscoreinnings_two_two);
        bestplayer.setText("BEST PLAYER");
        bestplayername.setText(bestplayers);
        gameinformation.setText("Game Information");
        venue.setText("Venue:"+venues);
        time.setText("Time:"+times);
        refree.setText("Refree:"+refrees);
        gallery.setText("GALLERY");
        RelativeLayout foot=(RelativeLayout)findViewById(R.id.layout_tennis);
        foot.setVisibility(View.GONE);
        RelativeLayout tennis=(RelativeLayout)findViewById(R.id.layout_foot);
        tennis.setVisibility(View.GONE);
        RelativeLayout cricket=(RelativeLayout)findViewById(R.id.tables);
        }else if (sportsname_category.equals("Tennis")){
            sportsname.setText(sportsname_category);
            trophyname.setText(tournames+"\n"+teannamesone+""+"Vs"+""+teannamestwo+"\n"+dates);
            finalscore.setText("Final");
            finalscoreone.setText(tennistotalone);
            finalscoretwo.setText(tennistotaltwo);
            scorecard.setText("ScoreCard");
            firstinningsdetail.setText(teamscoreinnings_one);
            secondinningsdetail.setText(teamscoreinnings_one_two);
            teamnameone_tennis.setText(teannamesone);
            teamnametwo_tennis.setText(teannamestwo);
            bestplayer.setText("Best Player");
            bestplayername.setText(bestplayers);
            gameinformation.setText("Game Information");
            venue.setText("Venue:"+venues);
            time.setText("Time:"+times);
            refree.setText("Refree:"+refrees);
            gallery.setText("Gallery");
            teamnameone_tennis_setone.setText(tennisteamone_setone);
            teamnameone_tennis_settwo.setText(tennisteamone_settwo);
            teamnameone_tennis_setthree.setText(tennisteamone_setthree);
            teamnameone_tennis_setfour.setText(tennisteamone_setfour);
            teamnameone_tennis_setfive.setText(tennisteamone_setfive);

            teamnametwo_tennis_setone.setText(tennisteamtwo_setone);
            teamnametwo_tennis_settwo.setText(tennisteamtwo_settwo);
            teamnametwo_tennis_setthree.setText(tennisteamtwo_setthree);
            teamnametwo_tennis_setfour.setText(tennisteamtwo_setfour);
            teamnametwo_tennis_setfive.setText(tennisteamtwo_setfive);

            teamnameone_tennis_settotal.setText(tennistotalone);
            teamnametwo_tennis_settotal.setText(tennistotaltwo);


            RelativeLayout foot=(RelativeLayout)findViewById(R.id.layout_tennis);

            RelativeLayout tennis=(RelativeLayout)findViewById(R.id.layout_foot);
            tennis.setVisibility(View.GONE);
            RelativeLayout cricket=(RelativeLayout)findViewById(R.id.tables);
            cricket.setVisibility(View.GONE);
        }else if(sportsname_category.equals("Football")||sportsname_category.equalsIgnoreCase("Basketball")){
            sportsname.setText(sportsname_category);
            trophyname.setText(tournames+"\n"+teannamesone+""+"Vs"+""+teannamestwo+"\n"+dates);
            finalscore.setText("Final");

            finalscoreone.setText(footballteamonetotal);
            finalscoretwo.setText(footballteamtwototal);
            scorecard.setText("SCORECARD");
            teamname_footone.setText(teannamesone);
            teamname_foottwo.setText(teannamestwo);
            teamone_foot_firsthalf.setText(footballteamone_fhalf);
            teamone_foot_secondhalf.setText(footballteamone_shalf);
            teamtwo_foot_firsthalf.setText(footballteamtwo_fhalf);
            teamtwo_foot_secondhalf.setText(footballteamtwo_shalf);
            teamone_totalgoal.setText(footballteamonetotal);
            teamtwo_totalgoal.setText(footballteamtwototal);
            bestplayer.setText("BEST PLAYER");
            bestplayername.setText(bestplayers);
            gameinformation.setText("GAME INFORMATION");
            venue.setText("Venue:"+venues);
            time.setText("Time:"+times);
            refree.setText("Refree:"+refrees);
            gallery.setText("GALLERY");
            RelativeLayout tennis=(RelativeLayout)findViewById(R.id.layout_tennis);
            tennis.setVisibility(View.GONE);
            RelativeLayout foot =(RelativeLayout)findViewById(R.id.layout_foot);

            RelativeLayout cricket=(RelativeLayout)findViewById(R.id.tables);
            cricket.setVisibility(View.GONE);
        }
    }
}

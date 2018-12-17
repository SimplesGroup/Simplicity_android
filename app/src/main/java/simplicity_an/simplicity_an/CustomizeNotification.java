package simplicity_an.simplicity_an;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by kuppusamy on 3/23/2017.
 */
public class CustomizeNotification extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    String myprofileid;
    TextView title,science_textview,article_textview,sports_textview,music_textview,farming_textview,food_textview,health_textview,travel_textview;
    SwitchCompat scinece_switch,article_switch,sports_switch,music_switch,farming_switch,food_switch,health_switch,travel_switch;

    ImageButton back,happening,search,audiovideo,more;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.customizenotification);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

        back=(ImageButton)findViewById(R.id.btn_back);
        happening=(ImageButton)findViewById(R.id.btn_happening);
        search=(ImageButton)findViewById(R.id.btn_search);
        audiovideo=(ImageButton)findViewById(R.id.btn_audio);
        more=(ImageButton)findViewById(R.id.btn_more);

        title=(TextView)findViewById(R.id.update_title);
        science_textview=(TextView)findViewById(R.id.science_notification);
        article_textview=(TextView)findViewById(R.id.article_notification);
        music_textview=(TextView)findViewById(R.id.music_notification);
        farming_textview=(TextView)findViewById(R.id.farming_notification);
        travel_textview=(TextView)findViewById(R.id.travel_notification);
        sports_textview=(TextView)findViewById(R.id.sports_notification);
        health_textview=(TextView)findViewById(R.id.health_notification);
        food_textview=(TextView)findViewById(R.id.food_notification);

        scinece_switch=(SwitchCompat)findViewById(R.id.science_switch);
        sports_switch=(SwitchCompat)findViewById(R.id.sports_switch);
        article_switch=(SwitchCompat)findViewById(R.id.article_switch);
        music_switch=(SwitchCompat)findViewById(R.id.music_switch);
        farming_switch=(SwitchCompat)findViewById(R.id.farming_switch);
        travel_switch=(SwitchCompat)findViewById(R.id.travel_switch);
        health_switch=(SwitchCompat)findViewById(R.id.health_switch);
        food_switch=(SwitchCompat)findViewById(R.id.food_switch);

        title.setTypeface(tf);
        title.setText("Notification");
        science_textview.setTypeface(tf);
        article_textview.setTypeface(tf);
        music_textview.setTypeface(tf);
        farming_textview.setTypeface(tf);
       travel_textview.setTypeface(tf);
        sports_textview.setTypeface(tf);
        health_textview.setTypeface(tf);
        food_textview.setTypeface(tf);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        happening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent happening=new Intent(getApplicationContext(),Events.class);
                startActivity(happening);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent happening=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(happening);
            }
        });
        audiovideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent happening=new Intent(getApplicationContext(),EntertainmentVersiontwo.class);
                startActivity(happening);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent happening=new Intent(getApplicationContext(),Settings.class);
                startActivity(happening);
            }
        });

        scinece_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    scinece_switch.setText("On");
                } else {
                    scinece_switch.setText("Off");
                }
            }
        });
        sports_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sports_switch.setText("On");
                } else {
                    sports_switch.setText("Off");
                }
            }
        });
        food_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    food_switch.setText("On");

                } else {
                    food_switch.setText("Off");
                }
            }
        });
        health_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    health_switch.setText("On");
                } else {
                    health_switch.setText("Off");
                }
            }
        });
        travel_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    travel_switch.setText("On");
                } else {
                    travel_switch.setText("Off");
                }
            }
        });
        music_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    music_switch.setText("On");
                } else {
                    food_switch.setText("Off");
                }
            }
        });
        article_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    article_switch.setText("On");
                } else {
                    article_switch.setText("Off");
                }
            }
        });
        farming_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    farming_switch.setText("On");
                } else {
                    farming_switch.setText("Off");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

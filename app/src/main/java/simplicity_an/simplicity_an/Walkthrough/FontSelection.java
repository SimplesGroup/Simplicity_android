package simplicity_an.simplicity_an.Walkthrough;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.PrefManager;
import simplicity_an.simplicity_an.R;

public class FontSelection extends AppCompatActivity {
    TextView selectyour_text,style_text;
    TextView classic_textview,neo_textview;
    ImageButton nextpage;
    ImageView tick_classic,tick_neo;
    public static final String FONT= "font";
    public static final String Language = "lamguage";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String backgroundcolor = "color";
    private PrefManager prefManager;
    String language,font;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fontselection_walkthrough);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        selectyour_text=(TextView)findViewById(R.id.font_textview);
        style_text=(TextView)findViewById(R.id.style_textview);
        classic_textview=(TextView)findViewById(R.id.classic_textview);
        neo_textview=(TextView)findViewById(R.id.neo_textview);

        tick_classic=(ImageView)findViewById(R.id.tickmark_classic);
        tick_neo=(ImageView)findViewById(R.id.tickmark_neo);

        nextpage=(ImageButton)findViewById(R.id.next_theme);

        String fontPathbarkendina = "fonts/SystemSanFranciscoDisplayRegular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), fontPathbarkendina);

        String fontbold = "fonts/Oxygen-Bold.ttf";
        Typeface tfbold = Typeface.createFromAsset(getApplicationContext().getAssets(), fontbold);
        String fontplay = "fonts/playfairDisplayRegular.ttf";
        Typeface tfplay = Typeface.createFromAsset(getApplicationContext().getAssets(), fontplay);

        selectyour_text.setTypeface(tf);
        style_text.setTypeface(tfbold);
        classic_textview.setTypeface(tfplay);
        neo_textview.setTypeface(tf);

        classic_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tick_classic.setVisibility(View.VISIBLE);
                tick_classic.setImageResource(R.mipmap.tick);
                tick_neo.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(FONT,"playfair");
                editor.commit();
            }
        });

        neo_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tick_neo.setVisibility(View.VISIBLE);
                tick_neo.setImageResource(R.mipmap.tick);
                tick_classic.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(FONT,"sanfrancisco");
                editor.commit();
            }
        });
        prefManager = new PrefManager(getApplicationContext());


        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setFirstTimeLaunch(false);
font=sharedpreferences.getString(FONT,"");
language=sharedpreferences.getString(Language,"");
                  if(font.equals("")) {
                      Toast.makeText(getApplicationContext()," Select Your Font Style",Toast.LENGTH_LONG).show();

                  }else {

                      if(language!=null) {
                          if (language.equals("English")) {
                              Intent i = new Intent(getApplicationContext(), MainPageEnglish.class);
                              startActivity(i);
                              finish();
                          } else {
                              Intent i = new Intent(getApplicationContext(), MainPageTamil.class);
                              startActivity(i);
                              finish();
                          }
                      }
                  }



            }
        });
    }
}

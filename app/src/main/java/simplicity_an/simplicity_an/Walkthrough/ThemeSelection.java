package simplicity_an.simplicity_an.Walkthrough;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import simplicity_an.simplicity_an.AboutPage;
import simplicity_an.simplicity_an.R;

public class ThemeSelection extends AppCompatActivity{
    TextView selectyour_text,theme_text;
    TextView dark_theme_textview,light_theme_textview;
    ImageButton nextpage;
    ImageView tick_light,dark_tick;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String backgroundcolor = "color";

    String color;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.themeselection_walkthrouth);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        selectyour_text=(TextView)findViewById(R.id.selectyour_textview);
        theme_text=(TextView)findViewById(R.id.theme_textview);
        dark_theme_textview=(TextView)findViewById(R.id.darktheme_textview);
        light_theme_textview=(TextView)findViewById(R.id.whitetheme_textview);

        tick_light=(ImageView)findViewById(R.id.tickmark_theme);
        dark_tick=(ImageView)findViewById(R.id.tickmark_themedark);

        nextpage=(ImageButton)findViewById(R.id.next_theme);

        String fontPathbarkendina = "fonts/SystemSanFranciscoDisplayRegular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), fontPathbarkendina);

        String fontbold = "fonts/Oxygen-Bold.ttf";
        Typeface tfbold = Typeface.createFromAsset(getApplicationContext().getAssets(), fontbold);

        selectyour_text.setTypeface(tf);
        theme_text.setTypeface(tfbold);
        dark_theme_textview.setTypeface(tf);
        light_theme_textview.setTypeface(tf);

        light_theme_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tick_light.setVisibility(View.VISIBLE);
                tick_light.setImageResource(R.mipmap.tickblack);
                dark_tick.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(backgroundcolor, "#FFFFFFFF");
                editor.commit();
            }
        });

        dark_theme_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dark_tick.setVisibility(View.VISIBLE);
                dark_tick.setImageResource(R.mipmap.tick);
                tick_light.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
               editor.putString(backgroundcolor, "#262626");
                editor.commit();
            }
        });



        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color=sharedpreferences.getString(backgroundcolor,"");
                Log.e("theme","start");
                Log.e("theme","sss"+color);
                if(color.equals("")) {
                    Toast.makeText(getApplicationContext()," Select Your Theme",Toast.LENGTH_LONG).show();
                }else {
                    Intent in = new Intent(getApplicationContext(), FontSelection.class);
                    startActivity(in);

                }

            }
        });


    }
}

package simplicity_an.simplicity_an.Walkthrough;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import simplicity_an.simplicity_an.R;

/**
 * Created by user on 8/6/2018.
 */

public class LanguageSelection extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String FONT= "font";
    String fontname;
    public static final String backgroundcolor = "color";
    TextView select,language,tamil,english;
    ImageView btntamil,btnenglish;
    ImageButton next_page;
    Context conxt;
    public static final String Language = "lamguage";
    String lang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.language);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(FONT,"");
        String simplycity_title_sans = "fonts/Oxygen-Regular.ttf";
        final Typeface sansfrancisco = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_sans);

        String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
        final Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);


        select=(TextView)findViewById(R.id.select);
        language=(TextView)findViewById(R.id.language);
        tamil=(TextView)findViewById(R.id.tamil);
        english=(TextView)findViewById(R.id.english);
        next_page=(ImageButton)findViewById(R.id.next_theme);
        btntamil=(ImageView)findViewById(R.id.btn_tamil);
        btnenglish=(ImageView)findViewById(R.id.btn_english);


        Typeface bold=Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/SystemSanFranciscoDisplayBold.ttf");
        Typeface regular=Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/SystemSanFranciscoDisplayRegular.ttf");

        select.setTypeface(sansfrancisco);
        language.setTypeface(sansfrancisco);
        tamil.setTypeface(sansfrancisco);
        english.setTypeface(sansfrancisco);


        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang=sharedpreferences.getString(Language,"");
                if(lang.equals("")){
                    Toast.makeText(getApplicationContext(),"Select Your Language",Toast.LENGTH_LONG).show();
                }else {
                    Intent in = new Intent(getApplicationContext(), ThemeSelection.class);
                    startActivity(in);
                    finish();
                }
            }
        });
        tamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Language, "tamil");
                editor.commit();
                btntamil.setVisibility(View.VISIBLE);
                btntamil.setImageResource(R.mipmap.tick);
                btnenglish.setVisibility(View.GONE);
                tamil.setBackgroundColor(Color.parseColor("#666666"));
                english.setBackgroundColor(Color.parseColor("#000000"));



            }

        });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Language, "English");
                editor.commit();
                btnenglish.setVisibility(View.VISIBLE);
                btnenglish.setImageResource(R.mipmap.tick);
                btntamil.setVisibility(View.GONE);

                /*tamil.setBackgroundColor(Color.parseColor("#000000"));
                english.setBackgroundColor(Color.parseColor("#666666"));*/
            }

        });





    }
}

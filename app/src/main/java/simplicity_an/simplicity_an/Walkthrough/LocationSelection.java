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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import simplicity_an.simplicity_an.R;

/**
 * Created by user on 8/6/2018.
 */

public class LocationSelection extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String FONT= "font";
    String fontname;
    public static final String backgroundcolor = "color";
    TextView select,location,coimbatore,chennai,hyderabad;
    Button btncoimbatore,btnchennai,btnhyderabad;
    ImageButton next_page;
    Context conxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.location);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(FONT,"");
        String simplycity_title_sans = "fonts/Oxygen-Regular.ttf";
        final Typeface sansfrancisco = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_sans);

        String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
        final Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);


        select=(TextView)findViewById(R.id.select);
        location=(TextView)findViewById(R.id.location);
        coimbatore=(TextView)findViewById(R.id.coimbatore);
        chennai=(TextView)findViewById(R.id.chennai);
        hyderabad=(TextView)findViewById(R.id.hyderabad);
        next_page=(ImageButton)findViewById(R.id.next_imagebutton);
        btncoimbatore=(Button)findViewById(R.id.btn_coimbatore);
        btnchennai=(Button)findViewById(R.id.btn_chennai);
        btnhyderabad=(Button)findViewById(R.id.btn_hyderabad);

        Typeface bold=Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/SystemSanFranciscoDisplayBold.ttf");
        Typeface regular=Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/SystemSanFranciscoDisplayRegular.ttf");

        select.setTypeface(sansfrancisco);
        location.setTypeface(sansfrancisco);
        coimbatore.setTypeface(sansfrancisco);
        chennai.setTypeface(sansfrancisco);
        hyderabad.setTypeface(sansfrancisco);

        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), LanguageSelection.class);
                startActivity(in);
                finish();
            }
        });
        coimbatore.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        btncoimbatore.setVisibility(View.VISIBLE);
            btncoimbatore.setBackgroundResource(R.mipmap.tick);
            btnchennai.setVisibility(View.GONE);
            btnhyderabad.setVisibility(View.GONE);
        chennai.setBackgroundColor(Color.parseColor("#000000"));
        coimbatore.setBackgroundColor(Color.parseColor("#666666"));
        hyderabad.setBackgroundColor(Color.parseColor("#000000"));
    }

});
        chennai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnchennai.setVisibility(View.VISIBLE);
                btnchennai.setBackgroundResource(R.mipmap.tick);
                btncoimbatore.setVisibility(View.GONE);
                btnhyderabad.setVisibility(View.GONE);
                chennai.setBackgroundColor(Color.parseColor("#666666"));
                coimbatore.setBackgroundColor(Color.parseColor("#000000"));
                hyderabad.setBackgroundColor(Color.parseColor("#000000"));
            }

        });
        hyderabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnhyderabad.setVisibility(View.VISIBLE);
                btnhyderabad.setBackgroundResource(R.mipmap.tick);
                btnchennai.setVisibility(View.GONE);
                btncoimbatore.setVisibility(View.GONE);
                chennai.setBackgroundColor(Color.parseColor("#000000"));
                coimbatore.setBackgroundColor(Color.parseColor("#000000"));
                hyderabad.setBackgroundColor(Color.parseColor("#666666"));
            }

        });


    }
}

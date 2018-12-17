package simplicity_an.simplicity_an.Explore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import simplicity_an.simplicity_an.Events;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/11/2017.
 */
public class RentalsRecordDetail extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RequestQueue requestQueue;
    ImageButton back,happenning,search_button,entertainment,morepage;
    TextView explore_title_textview,doubt_label_textview,doubtphone_textview;
    EditText name_edit,email_edit,contact_edit;
    Button getstarted_button;
    RelativeLayout mainlayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_rentalrecord_detail);
        Intent get=getIntent();
        String title=get.getStringExtra("TITLE");
        String id=get.getStringExtra("ID");
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        requestQueue= Volley.newRequestQueue(this);
        if(colorcodes.length()==0){

        }else {
            if (colorcodes.equalsIgnoreCase("004")) {
                Log.e("Msg", "hihihi");
            } else {

                int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);
            }
        }

        back=(ImageButton)findViewById(R.id.btn_ex_back);
        happenning=(ImageButton)findViewById(R.id.btn_ex_happening);
        search_button=(ImageButton)findViewById(R.id.btn_ex_search);
        entertainment=(ImageButton)findViewById(R.id.btn_ex_entertainment);
        morepage=(ImageButton)findViewById(R.id.btn_ex_more);
        explore_title_textview = (TextView)findViewById(R.id.title_text) ;
        doubt_label_textview = (TextView)findViewById(R.id.doubts_label) ;
        doubtphone_textview = (TextView)findViewById(R.id.doubts_phone) ;
name_edit=(EditText)findViewById(R.id.explore_rentalrecord_name);
        email_edit=(EditText)findViewById(R.id.explore_rentalrecord_email);
        contact_edit=(EditText)findViewById(R.id.explore_rentalrecord_phone);

        getstarted_button=(Button)findViewById(R.id.explore_rentalrecord_getstarted);
        getstarted_button.setText("Get Started");
        getstarted_button.setTypeface(tf);
        explore_title_textview.setTypeface(tf);
        doubtphone_textview.setTypeface(tf);
        doubt_label_textview.setTypeface(tf);

        doubt_label_textview.setText("Doubts?");
        doubtphone_textview.setText("Call us at +91 - 90807 06050");
        explore_title_textview.setText(title);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        morepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notification_page = new Intent(getApplicationContext(), ExploreSettings.class);
                startActivity(notification_page);
            }
        });
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), simplicity_an.simplicity_an.EntertainmentVersiontwo.class);
                startActivity(entairnment);
            }
        });
        happenning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event_page = new Intent(getApplicationContext(), Events.class);
                startActivity(event_page);
            }
        });
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
}

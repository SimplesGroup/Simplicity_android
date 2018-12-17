package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 7/7/2016.
 */
public class OurTeamPage extends AppCompatActivity {
  //  TextView team_title,founder,creativehead,projectlead,uideveloper,uidesigner,photography,contentteam,audiovideoteam;

   // TextView founder_names,creativeheadteams,projectheadteams,uideveloperteams,uidesingerteams,photographyteam,contentteam_name,audiovideoteam_name;

   TextView about_titles,simplicity_version,copyright,realease_description,descriptionnotes;
    WebView aboutdetail;
    String Url="http://simpli-city.in/request2.php?rtype=team&key=simples";

    ProgressDialog pdialog;
    final String TAG_REQUEST = "MY_TAG";
    RequestQueue requestQueue;
   ImageButton backs,citycenters,home,entertainment,notifications;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ourteampage);
        backs=(ImageButton)findViewById(R.id.btn_articleback) ;
        citycenters=(ImageButton)findViewById(R.id.btn_citycenter) ;
        home=(ImageButton)findViewById(R.id.btn_home) ;
        entertainment=(ImageButton)findViewById(R.id.btn_entertainment);
        notifications=(ImageButton)findViewById(R.id.btn_notifications) ;
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent back_notification=new Intent(getApplicationContext(),Settings.class);
                startActivity(back_notification);
                finish();*/
                onBackPressed();
            }
        });
        citycenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageEnglish.class);
                entairnment.putExtra("ID","3");
                startActivity(entairnment);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageEnglish.class);
                entairnment.putExtra("ID","4");
                startActivity(entairnment);
            }
        });
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageEnglish.class);
                entairnment.putExtra("ID","2");
                startActivity(entairnment);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageEnglish.class);
                entairnment.putExtra("ID","1");
                startActivity(entairnment);
            }
        });

        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        String fontPathbarkendina = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(OurTeamPage.this.getAssets(), fontPathbarkendina);
        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest aboutpage=new JsonObjectRequest(Request.Method.GET, Url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pdialog.dismiss();
                ParseJsonFeed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        aboutpage.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(aboutpage);
        about_titles=(TextView)findViewById(R.id.about_title);
        about_titles.setTypeface(tf);
        aboutdetail=(WebView)findViewById(R.id.about_detail);



       /* team_title=(TextView)findViewById(R.id.team_title);
        founder=(TextView)findViewById(R.id.founder_ceo);
        creativehead=(TextView)findViewById(R.id.creativehead);
        projectlead=(TextView)findViewById(R.id.projecthead);
        uideveloper=(TextView)findViewById(R.id.uideveloper);
        uidesigner=(TextView)findViewById(R.id.uidesigner);
        photography=(TextView)findViewById(R.id.photography);
        contentteam=(TextView)findViewById(R.id.contentteam);
        audiovideoteam=(TextView)findViewById(R.id.audioteam);


        founder_names=(TextView)findViewById(R.id.founder_ceo_data);
        creativeheadteams=(TextView)findViewById(R.id.creativehead_data);
        projectheadteams=(TextView)findViewById(R.id.projecthead_data);
        uideveloperteams=(TextView)findViewById(R.id.uideveloper_data);
        uidesingerteams=(TextView)findViewById(R.id.uidesigner_data);
        photographyteam=(TextView)findViewById(R.id.photography_data);
        contentteam_name=(TextView)findViewById(R.id.contentteam_data);
        audiovideoteam_name=(TextView)findViewById(R.id.audioteam_data);

        team_title.setTypeface(tf);
        founder.setTypeface(tf);
        creativehead.setTypeface(tf);
        projectlead.setTypeface(tf);
        uideveloper.setTypeface(tf);
        uidesigner.setTypeface(tf);
        photography.setTypeface(tf);
        contentteam.setTypeface(tf);
        audiovideoteam.setTypeface(tf);


        founder_names.setTypeface(tf);
        creativeheadteams.setTypeface(tf);
        projectheadteams.setTypeface(tf);
        uideveloperteams.setTypeface(tf);
        uidesingerteams.setTypeface(tf);
        photographyteam.setTypeface(tf);
        contentteam_name.setTypeface(tf);
        audiovideoteam_name.setTypeface(tf);

        team_title.setText("Team");
        founder.setText("Founder & CEO");
        creativehead.setText("Creative Head");
        projectlead.setText("Project Lead");
        uideveloper.setText("UI Developer");
        uidesigner.setText("UI Designer");
        photography.setText("Photography");
        contentteam.setText("Content Team");
        audiovideoteam.setText("Audio Video Team");

        founder_names.setText("Andrew Sam");
        creativeheadteams.setText("Andrew Sam"+"\n"+"Deepa Sam");
        projectheadteams.setText("Mithun Sundaresh");
        uideveloperteams.setText("Chethan Chandrashekar"+"\n"+"K Kuppusamy"+"\n"+"R Vignesh");
        uidesingerteams.setText("Jeyenth Jeyeraman");
        photographyteam.setText("T Mohanraj"+"\n"+"S Mohanraj");
        contentteam_name.setText("Ajay Kumar Jagadeesan"+"\n"+"Deepa Sam"+"\n"+"Ashwathi Ashokan");
        audiovideoteam_name.setText("M Raja"+"\n"+"Achuthan Warrier");*/
    }

    private void ParseJsonFeed(JSONObject response){

        try{
            JSONArray jsonArray=response.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj=jsonArray.getJSONObject(i);
                ItemModel item=new  ItemModel();
                item.setAbout(obj.getString("content"));
                item.setName(obj.getString("name"));
                String descrition = obj.isNull("content") ? null : obj
                        .getString("content");
                about_titles.setText(obj.getString("name"));
                aboutdetail.getSettings().setJavaScriptEnabled(true);

                aboutdetail.loadData(descrition, "text/html; charset=UTF-8", null);


                aboutdetail.getSettings().setAllowContentAccess(true);


                aboutdetail.setBackgroundColor(Color.TRANSPARENT);

            }

        }catch (JSONException e){

        }
    }
    class ItemModel{
        String about,name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }
    }


}

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
public class AboutPage extends AppCompatActivity {
  TextView about_titles,simplicity_version,copyright,realease_description,descriptionnotes;
    WebView aboutdetail;
    String Url="http://simpli-city.in/request2.php?rtype=about&key=simples";
    ImageButton backs,citycenters,home,entertainment,notifications;
    ProgressDialog pdialog;
    final String TAG_REQUEST = "MY_TAG";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aboutpage);
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
        Typeface tf = Typeface.createFromAsset(AboutPage.this.getAssets(), fontPathbarkendina);
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
        /*about_titles=(TextView)findViewById(R.id.about_title);
        simplicity_version=(TextView)findViewById(R.id.simplicity_title);
        copyright=(TextView)findViewById(R.id.copyright_title);
        realease_description=(TextView)findViewById(R.id.realese_notes);
        descriptionnotes=(TextView)findViewById(R.id.description_notes);
        about_titles.setTypeface(tf);
        simplicity_version.setTypeface(tf);
        copyright.setTypeface(tf);
        realease_description.setTypeface(tf);
        descriptionnotes.setTypeface(tf);
        about_titles.setText("About");
        simplicity_version.setText("SimpliCity 1.1.1");
        copyright.setText("Copyright 2016-2017 SimpliCity");
        realease_description.setText("The 2016 releases of SimpliCity exciting new features for news readers and more. Read on for a quick introduction to these features and links to resources offering more information.");
        descriptionnotes.setText(Html.fromHtml("What impacts your daily life.. "+"\n"+"Water in Mars or Water to your home?That's what SimpliCity aims to help you with. SimpliCity is a new age tech-media platform that endeavours to inform, connect and enable the Citycens of a city with valuable real-time information that enriches their lives. Everything that a Citycen requires to make his daily life easier has been meticulously curated and served with a world-class user interface. We have an excellent media and reporting team that works with the soul idea of informing the people of the city with information that is both enriching and important. And this is the information that is generally skipped by the mainstream media.We provide extremely potent information in News, Events, Local Sports, Government Notifications, Jobs, Radio and independent Music and Movies.SimpliCity includes the world's first and one-of-a-kind social network & news crowdsourcing platform, City Center, which has been designed from the ground up to enable people of a City to start talking again with each other and to report news, events and happenings in near real-time.SimpliCity is an app that is available in all three major platforms and we are extremely proud to launch it first in Coimbatore. Madurai & Trichy will follow shortly..."));
*/



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
        }
    }
}

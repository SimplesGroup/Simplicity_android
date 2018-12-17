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
 * Created by kuppusamy on 10/6/2016.
 */
public class Contactpage extends AppCompatActivity {
    TextView about_titles,simplicity_version,copyright,realease_description,descriptionnotes;
    WebView aboutdetail;
    String Url="http://simpli-city.in/request2.php?rtype=contact&key=simples";

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
        Typeface tf = Typeface.createFromAsset(Contactpage.this.getAssets(), fontPathbarkendina);
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

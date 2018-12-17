package simplicity_an.simplicity_an.Explore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.Events;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/10/2017.
 */
public class RentalDetailPage extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    String activity, contentid, colorcodes, myprofileid, cartcounts;
    public static final String MYUSERID = "myprofileid";
    public String CARTCOUNT = "cartcound";
    RelativeLayout mainlayout;

    RequestQueue requestQueue;
    int requestcount = 1;
    String URLPRODUCTLIST = "http://simpli-city.in/explore_request.php?key=smp_explore&rtype=realestate&id=";

    String URLALL, URL;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;
    List<Exploreitem> exploreitemList_product = new ArrayList<>();

    ImageButton back, happenning, search_button, entertainment, morepage;

    LinearLayout mycartlayout;
    NetworkImageView productimageview;
    ImageLoader imageLoader;


    TextView name_textview,location_textview, date_textview,cantactperson_textview,phone_textview;

    TextView pricing_textview,callnow_textview;
ImageButton locationicon;
    WebView rental_description_webview;
          TextView tips_description_webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_rental_detail_page);
        Intent get = getIntent();
        String id = get.getStringExtra("ID");
        Log.e("ID", id + "pdetailpage");
        URL = URLPRODUCTLIST + id;
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        colorcodes = sharedpreferences.getString(backgroundcolor, "");
        cartcounts = sharedpreferences.getString(CARTCOUNT, "");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+", "");
        }
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
        mainlayout = (RelativeLayout) findViewById(R.id.main_layout_explore);
        if (colorcodes.length() == 0) {

        } else {
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
        Log.e("URL", URL);
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    pDialog.dismiss();
                    Log.e("URL", response.toString());
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonreq);
        back = (ImageButton) findViewById(R.id.btn_ex_back);
        happenning = (ImageButton) findViewById(R.id.btn_ex_happening);
        search_button = (ImageButton) findViewById(R.id.btn_ex_search);
        entertainment = (ImageButton) findViewById(R.id.btn_ex_entertainment);
        morepage = (ImageButton) findViewById(R.id.btn_ex_more);
        name_textview = (TextView) findViewById(R.id.explore_rentaldetail_name);
     location_textview = (TextView) findViewById(R.id.explore_rentaldetail_location);
        date_textview = (TextView) findViewById(R.id.explore_rentaldetail_date);
        phone_textview = (TextView) findViewById(R.id.explore_rentaldetail_phone);
       cantactperson_textview = (TextView) findViewById(R.id.explore_rentaldetail_contactperson);
        pricing_textview = (TextView) findViewById(R.id.explore_rental_pricetext);
        callnow_textview= (TextView) findViewById(R.id.explore_rental_callnow);

        locationicon=(ImageButton) findViewById(R.id.location_icon);
        rental_description_webview=(WebView)findViewById(R.id.rental_webview_desc) ;
       tips_description_webview=(TextView) findViewById(R.id.rental_webview_tips) ;
        productimageview = (NetworkImageView) findViewById(R.id.explore_productdetail_productimage);

locationicon.setImageResource(R.mipmap.location);
        name_textview.setTypeface(tf);
        location_textview.setTypeface(tf);
        cantactperson_textview.setTypeface(tf);
        phone_textview.setTypeface(tf);
        date_textview.setTypeface(tf);
        pricing_textview.setTypeface(tf);
        callnow_textview.setTypeface(tf);


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
                Intent entairnment = new Intent(getApplicationContext(), simplicity_an.simplicity_an.EntertainmentVersiontwo.class);
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
        pDialog = new ProgressDialog(this);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progressdialog);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pDialog != null) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            pDialog = null;
        }

    }

    private void parseJsonFeed(JSONObject response) {
        if (imageLoader == null)
            imageLoader = CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
        try {
            JSONArray productlist = response.getJSONArray("result");
            for (int i = 0; i < productlist.length(); i++) {
                Exploreitem items = new Exploreitem();


                JSONObject jsonObject = (JSONObject) productlist.get(i);

                String image = jsonObject.isNull("images") ? null : jsonObject
                        .getString("images");
                items.setImage(image);
                items.setId(jsonObject.getString("id"));
                items.setLocation(jsonObject.getString("location"));
                items.setAddress(jsonObject.getString("Address"));
                items.setConttactperson(jsonObject.getString("contact_person"));
                items.setPhone(jsonObject.getString("phone"));
                items.setTips(jsonObject.getString("tips"));
                items.setPdate(jsonObject.getString("post_date"));
                items.setPrice(jsonObject.getString("price"));
                items.setProductname(jsonObject.getString("name"));
                items.setDescription(jsonObject.getString("description"));
                productimageview.setImageUrl(image, imageLoader);
                String description = jsonObject.getString("description").toString();
//tToast.makeText(getApplicationContext(),description,Toast.LENGTH_SHORT).show();
                name_textview.setText(jsonObject.getString("name"));
                cantactperson_textview.setText("Contact Person : "+jsonObject.getString("contact_person"));
               location_textview.setText(jsonObject.getString("location"));
                phone_textview.setText("Phone :"+jsonObject.getString("phone"));
                date_textview.setText("Posted on :"+jsonObject.getString("post_date"));


                pricing_textview.setText(Html.fromHtml(jsonObject.getString("price")));
                callnow_textview.setText("CALL NOW");
                String s = description;
                // s = s.replace("\"", "'");
              //  s = s.replace("\\", "");


                rental_description_webview.loadData(s, "text/html; charset=UTF-8", null);

                rental_description_webview.setBackgroundColor(Color.TRANSPARENT);

                String tips = jsonObject.getString("tips").toString();
                //tips = tips.replace("\\", "");

String sss="<u>\" + obj.getString(\"publisher_name\") + \"</u>\"";
              // tips_description_webview.loadData(sss, "text/html; charset=UTF-8", null);

               // tips_description_webview.setBackgroundColor(Color.TRANSPARENT);
tips_description_webview.setText(Html.fromHtml(tips));

            }

        } catch (JSONException e) {

        }

    }

    public class Exploreitem {
        String id, productname, image, price, pdate,description,location,phone;
String conttactperson,address,tips;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getConttactperson() {
            return conttactperson;
        }

        public void setConttactperson(String conttactperson) {
            this.conttactperson = conttactperson;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getPdate() {
            return pdate;
        }

        public String getPrice() {
            return price;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }


}

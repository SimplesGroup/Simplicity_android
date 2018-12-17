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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.Events;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 4/6/2017.
 */
public class MobileDetailPage extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RelativeLayout mainlayout;

    RequestQueue requestQueue;
    int requestcount=1;
    String URLPRODUCTLIST="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=product-detail&pid=";
    String ADDTOCARTURL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=addtocart";
    String URLALL,URL;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;
    List<Exploreitem> exploreitemList_product=new ArrayList<>();

    ImageButton back,happenning,search_button,entertainment,morepage;
    TextView explore_producttitle_textview,explore_productprice_textview,mycart_textview,buynow_textview,cartcount_textview,explore_productbuynow_textview;
    LinearLayout mycartlayout;
    NetworkImageView productimageview;
    ImageLoader imageLoader;
    Spinner productprices_spinner;
    List<String> pricenos = new ArrayList<String>();
    List<String> price_detail = new ArrayList<String>();
    List<String> price_ids = new ArrayList<String>();
    String addtocart_pricetypeid,addtocart_productid;
    JSONObject objects;
    TextView mobile_desc_textview,mobile_general_textview,mobile_general_detail_textview,mobile_features_textview,mobile_features_detail_textview,mobile_services_textview,mobile_services_detail_textview;
    TextView mobile_display_textview,mobile_display_detail_textview,mobile_os_textview,mobile_os_detail_textview,mobile_storage_textview,mobile_storage_detail_textview,mobile_camera_textview,mobile_camera_detail_textview;
    TextView mobile_connectivity_textview,mobile_connectivity_detail_textview,mobile_others_textview,mobile_others_detail_textview,mobile_multimedia_textview,mobile_multimedia_detail_textview,mobile_battery_textview,mobile_battery_detail_textview;
    TextView mobile_dimensions_textview,mobile_dimensions_detail_textview,mobile_waranty_textview,mobile_waranty_detail_textview;
    ImageButton mobile_general_expand_button,mobile_features_expand_button,mobile_services_expand_button,mobile_display_expand_button,mobile_os_expand_button,mobile_storage_expand_button,mobile_camera_expand_button,mobile_connectivity_expand_button;
    ImageButton mobile_others_expand_button,mobile_multimedia_display_expand_button,mobile_battery_expand_button,mobile_dimension_expand_button,mobile_waranty_expand_button;
    String mobi_general="yes",mobi_features="yes",mobi_services="yes",mobi_display="yes",mobi_os="yes",mobi_storage="yes",mobi_camera="yes",mobi_connectivity="yes",mobi_others="yes",mobi_multimedia="yes",mobi_battery="yes",mobi_dimensions="yes",mobi_waranty="yes";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_mobile_detail);
        Intent get=getIntent();
        String id=get.getStringExtra("ID");
        Log.e("ID",id+"pdetailpage");
        URL=URLPRODUCTLIST+id;
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
        imageLoader= CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
        mainlayout=(RelativeLayout)findViewById(R.id.main_layout_explore);
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
        Log.e("URL",URL);
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    pDialog.dismiss();
                    Log.e("URL",response.toString());
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
        back=(ImageButton)findViewById(R.id.btn_ex_back);
        happenning=(ImageButton)findViewById(R.id.btn_ex_happening);
        search_button=(ImageButton)findViewById(R.id.btn_ex_search);
        entertainment=(ImageButton)findViewById(R.id.btn_ex_entertainment);
        morepage=(ImageButton)findViewById(R.id.btn_ex_more);
        explore_producttitle_textview = (TextView)findViewById(R.id.explore_productdetail_title) ;
        explore_productprice_textview=(TextView)findViewById(R.id.explore_productdetail_price_text) ;
        explore_productbuynow_textview=(TextView)findViewById(R.id.buynow_textview_detail) ;
        mycart_textview=(TextView)findViewById(R.id.mycart_textview);
        buynow_textview=(TextView)findViewById(R.id.buynow_textview) ;
        mobile_desc_textview=(TextView)findViewById(R.id.mobile_description) ;
        cartcount_textview=(TextView)findViewById(R.id.cart_main_count);
        mycartlayout=(LinearLayout)findViewById(R.id.cart_layout) ;
        productimageview=(NetworkImageView)findViewById(R.id.explore_productdetail_productimage) ;
        productprices_spinner=(Spinner)findViewById(R.id.explore_productdetail_spinner_price);
        mobile_general_textview=(TextView)findViewById(R.id.mobile_general_title) ;
        mobile_general_detail_textview=(TextView)findViewById(R.id.mobile_general_detail);
        mobile_general_expand_button=(ImageButton)findViewById(R.id.expand_mobile);

        mobile_features_textview=(TextView)findViewById(R.id.mobile_features_title) ;
        mobile_features_detail_textview=(TextView)findViewById(R.id.mobile_features_detail);
        mobile_features_expand_button=(ImageButton)findViewById(R.id.expand_mobile_features);

        mobile_services_textview=(TextView)findViewById(R.id.mobile_servicesfeatures_title) ;
        mobile_services_detail_textview=(TextView)findViewById(R.id.mobile_servicesfeatures_detail);
        mobile_services_expand_button=(ImageButton)findViewById(R.id.expand_mobile_servicesfeatures);

        mobile_display_textview=(TextView)findViewById(R.id.mobile_displayfeatures_title) ;
        mobile_display_detail_textview=(TextView)findViewById(R.id.mobile_displayfeatures_detail);
        mobile_display_expand_button=(ImageButton)findViewById(R.id.expand_mobile_displayfeatures);

        mobile_os_textview=(TextView)findViewById(R.id.mobile_osfeatures_title) ;
        mobile_os_detail_textview=(TextView)findViewById(R.id.mobile_osfeatures_detail);
        mobile_os_expand_button=(ImageButton)findViewById(R.id.expand_mobile_osfeatures);

        mobile_storage_textview=(TextView)findViewById(R.id.mobile_storagefeatures_title) ;
        mobile_storage_detail_textview=(TextView)findViewById(R.id.mobile_storagefeatures_detail);
        mobile_storage_expand_button=(ImageButton)findViewById(R.id.expand_mobile_storagefeatures);

        mobile_camera_textview=(TextView)findViewById(R.id.mobile_camerafeatures_title) ;
        mobile_camera_detail_textview=(TextView)findViewById(R.id.mobile_camerafeatures_detail);
        mobile_camera_expand_button=(ImageButton)findViewById(R.id.expand_mobile_camerafeatures);

        mobile_connectivity_textview=(TextView)findViewById(R.id.mobile_connectivity_title) ;
        mobile_connectivity_detail_textview=(TextView)findViewById(R.id.mobile_connectivity_detail);
        mobile_connectivity_expand_button=(ImageButton)findViewById(R.id.expand_mobile_connectivity);
        mobile_others_textview=(TextView)findViewById(R.id.mobile_others_title) ;
        mobile_others_detail_textview=(TextView)findViewById(R.id.mobile_others_detail);
        mobile_others_expand_button=(ImageButton)findViewById(R.id.expand_mobile_others);

        mobile_multimedia_textview=(TextView)findViewById(R.id.mobile_multimedia_title) ;
        mobile_multimedia_detail_textview=(TextView)findViewById(R.id.mobile_multimedia_detail);
        mobile_multimedia_display_expand_button=(ImageButton)findViewById(R.id.expand_mobile_multimedia);

        mobile_battery_textview=(TextView)findViewById(R.id.mobile_battery_title) ;
        mobile_battery_detail_textview=(TextView)findViewById(R.id.mobile_battery_detail);
        mobile_battery_expand_button=(ImageButton)findViewById(R.id.expand_mobile_battery);

        mobile_dimensions_textview=(TextView)findViewById(R.id.mobile_dimension_title) ;
        mobile_dimensions_detail_textview=(TextView)findViewById(R.id.mobile_dimension_detail);
        mobile_dimension_expand_button=(ImageButton)findViewById(R.id.expand_mobile_dimension);

        mobile_waranty_textview=(TextView)findViewById(R.id.mobile_waranty_title) ;
        mobile_waranty_detail_textview=(TextView)findViewById(R.id.mobile_waranty_detail);
        mobile_waranty_expand_button=(ImageButton)findViewById(R.id.expand_mobile_waranty);


        mobile_general_detail_textview.setTypeface(tf);
        mobile_general_textview.setTypeface(tf);
        mobile_os_detail_textview.setTypeface(tf);
        mobile_os_textview.setTypeface(tf);
        mobile_storage_detail_textview.setTypeface(tf);
        mobile_storage_textview.setTypeface(tf);
        mobile_camera_detail_textview.setTypeface(tf);
        mobile_camera_textview.setTypeface(tf);
        mobile_connectivity_detail_textview.setTypeface(tf);
        mobile_connectivity_textview.setTypeface(tf);
        mobile_others_detail_textview.setTypeface(tf);
        mobile_others_textview.setTypeface(tf);
        mobile_multimedia_detail_textview.setTypeface(tf);
        mobile_multimedia_textview.setTypeface(tf);
        mobile_battery_detail_textview.setTypeface(tf);
        mobile_battery_textview.setTypeface(tf);
        mobile_dimensions_detail_textview.setTypeface(tf);
        mobile_dimensions_textview.setTypeface(tf);
        mobile_waranty_detail_textview.setTypeface(tf);
        mobile_waranty_textview.setTypeface(tf);

        mobile_features_detail_textview.setTypeface(tf);
        mobile_features_textview.setTypeface(tf);
        mobile_services_detail_textview.setTypeface(tf);
        mobile_services_textview.setTypeface(tf);
        mobile_display_detail_textview.setTypeface(tf);
        mobile_display_textview.setTypeface(tf);
        mycart_textview.setTypeface(tf);
        cartcount_textview.setTypeface(tf);
        buynow_textview.setTypeface(tf);
        explore_producttitle_textview.setTypeface(tf);
        explore_productprice_textview.setTypeface(tf);
        explore_productbuynow_textview.setTypeface(tf);
        mobile_desc_textview.setTypeface(tf);
        Log.e("CARTCOUNT",cartcounts+"hi");
        if(cartcounts==null||cartcounts.equalsIgnoreCase("")){
            cartcount_textview.setText("0");

        }else {
            cartcount_textview.setText(cartcounts);
        }

        mycartlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart=new Intent(getApplicationContext(),CartListPage.class);
                startActivity(cart);
               /* Toast.makeText(getApplicationContext(),"go to cart page",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.remove(CARTCOUNT);
                editor.apply();
                CartCountincrement();*/
            }
        });
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
        mobile_general_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mobi_general.equals("yes")){
mobile_general_detail_textview.setVisibility(View.VISIBLE);
                    mobi_general="no";
                    mobile_general_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_general_detail_textview.setVisibility(View.GONE);
                    mobi_general="yes";
                    mobile_general_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_features_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_features.equals("yes")){
                    mobile_features_detail_textview.setVisibility(View.VISIBLE);
                    mobi_features="no";
                    mobile_features_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_features_detail_textview.setVisibility(View.GONE);
                    mobi_features="yes";
                    mobile_features_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_services_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_services.equals("yes")){
                    mobile_services_detail_textview.setVisibility(View.VISIBLE);
                    mobi_services="no";
                    mobile_services_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_services_detail_textview.setVisibility(View.GONE);
                    mobi_services="yes";
                    mobile_services_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_display_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_display.equals("yes")){
                    mobile_display_detail_textview.setVisibility(View.VISIBLE);
                    mobi_display="no";
                    mobile_display_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_display_detail_textview.setVisibility(View.GONE);
                    mobi_display="yes";
                    mobile_display_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_os_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_os.equals("yes")){
                    mobile_os_detail_textview.setVisibility(View.VISIBLE);
                    mobi_os="no";
                    mobile_os_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_os_detail_textview.setVisibility(View.GONE);
                    mobi_os="yes";
                    mobile_os_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_storage_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_storage.equals("yes")){
                    mobile_storage_detail_textview.setVisibility(View.VISIBLE);
                    mobi_storage="no";
                    mobile_storage_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_storage_detail_textview.setVisibility(View.GONE);
                    mobi_storage="yes";
                    mobile_storage_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_camera_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_camera.equals("yes")){
                    mobile_camera_detail_textview.setVisibility(View.VISIBLE);
                    mobi_camera="no";
                    mobile_camera_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_camera_detail_textview.setVisibility(View.GONE);
                    mobi_camera="yes";
                    mobile_camera_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_connectivity_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_connectivity.equals("yes")){
                    mobile_connectivity_detail_textview.setVisibility(View.VISIBLE);
                    mobi_connectivity="no";
                    mobile_connectivity_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_connectivity_detail_textview.setVisibility(View.GONE);
                    mobi_connectivity="yes";
                    mobile_connectivity_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_others_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_others.equals("yes")){
                    mobile_others_detail_textview.setVisibility(View.VISIBLE);
                    mobi_others="no";
                    mobile_others_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_others_detail_textview.setVisibility(View.GONE);
                    mobi_others="yes";
                    mobile_others_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_multimedia_display_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_multimedia.equals("yes")){
                    mobile_multimedia_detail_textview.setVisibility(View.VISIBLE);
                    mobi_multimedia="no";
                    mobile_multimedia_display_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_multimedia_detail_textview.setVisibility(View.GONE);
                    mobi_multimedia="yes";
                    mobile_multimedia_display_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_battery_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mobi_battery.equals("yes")){
                    mobile_battery_detail_textview.setVisibility(View.VISIBLE);
                    mobi_battery="no";
                    mobile_battery_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_battery_detail_textview.setVisibility(View.GONE);
                    mobi_battery="yes";
                    mobile_battery_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_dimension_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_dimensions.equals("yes")){
                    mobile_dimensions_detail_textview.setVisibility(View.VISIBLE);
                    mobi_dimensions="no";
                    mobile_dimension_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_dimensions_detail_textview.setVisibility(View.GONE);
                    mobi_dimensions="yes";
                    mobile_dimension_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        mobile_waranty_expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobi_waranty.equals("yes")){
                    mobile_waranty_detail_textview.setVisibility(View.VISIBLE);
                    mobi_waranty="no";
                    mobile_waranty_expand_button.setImageResource(R.mipmap.expandup);
                }else {
                    mobile_waranty_detail_textview.setVisibility(View.GONE);
                    mobi_waranty="yes";
                    mobile_waranty_expand_button.setImageResource(R.mipmap.expanddown);
                }
            }
        });
        pDialog=new ProgressDialog(this);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progressdialog);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        explore_productbuynow_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("CART", addtocart_pricetypeid+","+addtocart_productid);
                StringRequest addtocatr=new StringRequest(Request.Method.POST, ADDTOCARTURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CART", response);
                        String res=response.toString();
                        res = res.replace(" ", "");
                        res = res.trim();
                        if(res.toString().equalsIgnoreCase("added")){
                            String val = cartcount_textview.getText().toString();
                            Log.e("CART", val);
                            int values = 0;
                            if (val != null) {

                                try {
                                    val = val.trim();
                                    int valstring = Integer.parseInt(val);
                                    Log.e("CART", val);
                                    values = valstring + 1;
                                } catch (NumberFormatException e) {

                                }


                            } else {
                                values = 1;
                            }


                            Log.e("CARTfinal", String.valueOf(values));
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(CARTCOUNT, String.valueOf(values));
                            editor.commit();
                            CartCountincrement();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    /**
                     * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
                     * {@link AuthFailureError} as authentication may be required to provide these values.
                     * <p/>
                     * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
                     *
                     * @throws AuthFailureError in the event of auth failure
                     */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new Hashtable<String, String>();
                        params.put("id",addtocart_productid);
                        params.put("price_type",addtocart_pricetypeid);
                        params.put("user_id",myprofileid);
                        return params;
                    }
                };

                addtocatr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(addtocatr);


            }
        });

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
    private void parseJsonFeed(JSONObject response){
        if (imageLoader == null)
            imageLoader = CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
        try {
            JSONArray productlist=response.getJSONArray("result");
            for(int i=0;i<productlist.length();i++){
                Exploreitem items=new Exploreitem();



                final JSONObject jsonObject = (JSONObject) productlist.get(i);

                String image = jsonObject.isNull("image") ? null : jsonObject
                        .getString("image");
                items.setImage(image);
                items.setId(jsonObject.getString("id"));

                items.setProductname(jsonObject.getString("title"));
                items.setProductcode(jsonObject.getString("prod_code"));
                items.setGender(jsonObject.getString("gender"));
                items.setColor(jsonObject.getString("color"));
                items.setModuleid(jsonObject.getString("module_id"));
                items.setMudulename(jsonObject.getString("module_name"));
                items.setCatid(jsonObject.getString("cat_id"));
                items.setCategoryname(jsonObject.getString("category_name"));
                items.setSubcatid(jsonObject.getString("subcat_id"));
                items.setSubcatname(jsonObject.getString("subcat_name"));
                items.setCompanyid(jsonObject.getString("company_id"));
                items.setBrand(jsonObject.getString("brand"));
                //  items.setWishlistcount(jsonObject.getInt("wishlist_count"));
                items.setDescription(jsonObject.getString("description"));
                items.setCompanyname(jsonObject.getString("company_name"));
                items.setSpecification(jsonObject.getString("specification"));
                items.setIngreditients(jsonObject.getString("ingredients"));
                items.setMaxqty(jsonObject.getString("maxqty"));
                items.setAdvanceamount(jsonObject.getString("advance_amount"));

                String title=jsonObject.getString("title");
                Log.e("TEX",image+title+jsonObject.getString("image"));
                productimageview.setImageUrl(image,imageLoader);
                explore_producttitle_textview.setText(title);
                mobile_desc_textview.setText(Html.fromHtml(jsonObject.getString("ingredients")));
                List<Exploreitem.PriceData> price_item_list = new ArrayList<Exploreitem.PriceData>();


                JSONArray pricelist = jsonObject.getJSONArray("price");

                Log.e("Pricesize", String.valueOf(pricelist));
                for (int k = 0; k < pricelist.length(); k++) {
                    objects = (JSONObject) pricelist.get(k);
                    Exploreitem.PriceData priceitem=items.new PriceData();
                    priceitem.setPriceid(objects.getString("id"));
                    priceitem.setPrice(objects.getLong("price"));

                    priceitem.setPricepid(objects.getString("pid"));
                    priceitem.setPriceno(objects.getString("price_no"));
                    priceitem.setNtype(objects.getString("ntype"));
                    priceitem.setNos(objects.getLong("nos"));
                    priceitem.setStock(objects.getString("stock"));
                    price_item_list.add(priceitem);
                    long name=objects.getLong("nos");
                    String ntype=objects.getString("ntype");
                    String price=objects.getString("price");
                    String id=objects.getString("id");
                    price_ids.add(id);
                    pricenos.add(name+""+ntype);
                    price_detail.add(price);
                }
                addtocart_productid=jsonObject.getString("id");

                items.setPhoneList(price_item_list);


                JSONArray phone_detaillist = jsonObject.getJSONArray("mobile_info");
                for (int s = 0; s < phone_detaillist.length(); s++) {
              JSONObject      objects_phone = (JSONObject)phone_detaillist.get(s);
                   Exploreitem itemsphone=new Exploreitem();
itemsphone.setGeneralinfo(objects_phone.getString("general_info"));
                    itemsphone.setFeatures(objects_phone.getString("features"));
                    itemsphone.setServices(objects_phone.getString("services"));
                    itemsphone.setDisplayfeatures(objects_phone.getString("display_features"));
                    itemsphone.setOsfearures(objects_phone.getString("os_processor_features"));
                    itemsphone.setStoragefeatures(objects_phone.getString("memory_storage_features"));
                    itemsphone.setCamerafeatures(objects_phone.getString("camera_features"));
                    itemsphone.setConnectivity(objects_phone.getString("connectivity_features"));
                    itemsphone.setOthers(objects_phone.getString("other_details"));
                    itemsphone.setMultimedia(objects_phone.getString("multimedia_features"));
                    itemsphone.setBatteryfeatutres(objects_phone.getString("battery_power_features"));
                    itemsphone.setDimensions(objects_phone.getString("dimensions"));
                    itemsphone.setWarrnty(objects_phone.getString("warranty"));

    mobile_general_textview.setText("GENERAL INFORMATION");
    mobile_general_detail_textview.setText(Html.fromHtml(objects_phone.getString("general_info")));
    mobile_general_expand_button.setImageResource(R.mipmap.expanddown);
                    mobile_features_textview.setText("FEATURES");
                    mobile_features_detail_textview.setText(Html.fromHtml(objects_phone.getString("features")));
                    mobile_features_expand_button.setImageResource(R.mipmap.expanddown);
                    mobile_services_textview.setText("SERVICES");
                    mobile_services_detail_textview.setText(Html.fromHtml(objects_phone.getString("services")));
                    mobile_services_expand_button.setImageResource(R.mipmap.expanddown);
                    mobile_display_textview.setText("DISPLAY FEATURES");
                    mobile_display_detail_textview.setText(Html.fromHtml(objects_phone.getString("display_features")));
                    mobile_display_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_os_textview.setText("OS FEATURES");
                    mobile_os_detail_textview.setText(Html.fromHtml(objects_phone.getString("os_processor_features")));
                    mobile_os_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_storage_textview.setText("STORAGE FEATURES");
                    mobile_storage_detail_textview.setText(Html.fromHtml(objects_phone.getString("memory_storage_features")));
                    mobile_storage_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_camera_textview.setText("CAMERA FEATURES");
                    mobile_camera_detail_textview.setText(Html.fromHtml(objects_phone.getString("camera_features")));
                    mobile_camera_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_connectivity_textview.setText("CONNECTIVITY");
                    mobile_connectivity_detail_textview.setText(Html.fromHtml(objects_phone.getString("connectivity_features")));
                    mobile_connectivity_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_others_textview.setText("OTHERS");
                    mobile_others_detail_textview.setText(Html.fromHtml(objects_phone.getString("other_details")));
                    mobile_others_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_multimedia_textview.setText("MULTIMEDIA");
                    mobile_multimedia_detail_textview.setText(Html.fromHtml(objects_phone.getString("multimedia_features")));
                    mobile_multimedia_display_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_battery_textview.setText("BATTERY FEATURES");
                    mobile_battery_detail_textview.setText(Html.fromHtml(objects_phone.getString("battery_power_features")));
                    mobile_battery_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_dimensions_textview.setText("DIMENSIONS");
                    mobile_dimensions_detail_textview.setText(Html.fromHtml(objects_phone.getString("dimensions")));
                    mobile_dimension_expand_button.setImageResource(R.mipmap.expanddown);

                    mobile_waranty_textview.setText("WARANTY");
                    mobile_waranty_detail_textview.setText(Html.fromHtml(objects_phone.getString("warranty")));
                    mobile_waranty_expand_button.setImageResource(R.mipmap.expanddown);

                }
                exploreitemList_product.add(items);
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.view_spinner_item,pricenos);
                arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                productprices_spinner.setAdapter(arrayAdapter);
                productprices_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  Exploreitem itemmodel = exploreitemList_product.get(position);
                        if (position == 0) {
                            //   explore_productprice_textview.setText("Rs."+String.valueOf(name));
                            /// explore_productprice_textview.setText("Rs."+String.valueOf(productprices_spinner.getSelectedItem().toString()));
                            String getprice=price_detail.get(position);

                            addtocart_pricetypeid = price_ids.get(position);

                            explore_productprice_textview.setText("Rs."+ String.valueOf(getprice));
                            /*for(int k = 0; k< ((Exploreitem)exploreitemList_product.get(position)).getPhoneList().size(); k++){
                                Exploreitem.PriceData item=((Exploreitem)exploreitemList_product.get(position)).getPhoneList().get(k);
                                if(k==position) {
                                    long name = item.getPrice();

                                    explore_productprice_textview.setText("Rs."+String.valueOf(name));

                                }


                            }*/

                        } else {

                            String getprice=price_detail.get(position);
                            explore_productprice_textview.setText("Rs."+ String.valueOf(getprice));

                            addtocart_pricetypeid = price_ids.get(position);

                            /*for(int k = 0; k< ((Exploreitem)exploreitemList_product.get(position)).getPhoneList().size(); k++){
                                Exploreitem.PriceData item=((Exploreitem)exploreitemList_product.get(position)).getPhoneList().get(k);
                                if(k==position) {
                                    long name = item.getPrice();

                                    explore_productprice_textview.setText("Rs."+String.valueOf(name));

                                }


                            }*/
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

        }catch (JSONException e) {

        }

    }

    public    class Exploreitem {
        String id, productname, image, productcode, gender, color, moduleid, mudulename;
        String catid, categoryname, subcatid, subcatname, companyid, brand, description, companyname;
        String specification, maxqty, ingreditients, advanceamount;
        String features,services,description_mobile,generalinfo,displayfeatures,osfearures;
        String storagefeatures,camerafeatures,connectivity,others,multimedia,batteryfeatutres,dimensions,warrnty;
        List<Exploreitem> pricelist;
        private List<PriceData> phoneList;
        int wishlistcount;


        public String getOthers() {
            return others;
        }

        public void setOthers(String others) {
            this.others = others;
        }

        public String getDimensions() {
            return dimensions;
        }

        public void setDimensions(String dimensions) {
            this.dimensions = dimensions;
        }

        public String getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(String multimedia) {
            this.multimedia = multimedia;
        }

        public String getWarrnty() {
            return warrnty;
        }

        public void setWarrnty(String warrnty) {
            this.warrnty = warrnty;
        }

        public String getStoragefeatures() {
            return storagefeatures;
        }

        public void setStoragefeatures(String storagefeatures) {
            this.storagefeatures = storagefeatures;
        }

        public String getBatteryfeatutres() {
            return batteryfeatutres;
        }

        public void setBatteryfeatutres(String batteryfeatutres) {
            this.batteryfeatutres = batteryfeatutres;
        }

        public String getConnectivity() {
            return connectivity;
        }

        public void setConnectivity(String connectivity) {
            this.connectivity = connectivity;
        }

        public String getCamerafeatures() {
            return camerafeatures;
        }

        public void setCamerafeatures(String camerafeatures) {
            this.camerafeatures = camerafeatures;
        }

        public String getDescription_mobile() {
            return description_mobile;
        }

        public void setDescription_mobile(String description_mobile) {
            this.description_mobile = description_mobile;
        }

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public String getGeneralinfo() {
            return generalinfo;
        }

        public void setGeneralinfo(String generalinfo) {
            this.generalinfo = generalinfo;
        }

        public String getServices() {
            return services;
        }

        public void setServices(String services) {
            this.services = services;
        }

        public String getDisplayfeatures() {
            return displayfeatures;
        }

        public void setDisplayfeatures(String displayfeatures) {
            this.displayfeatures = displayfeatures;
        }

        public String getOsfearures() {
            return osfearures;
        }

        public void setOsfearures(String osfearures) {
            this.osfearures = osfearures;
        }

        public int getWishlistcount() {
            return wishlistcount;
        }

        public void setWishlistcount(int wishlistcount) {
            this.wishlistcount = wishlistcount;
        }

        public List<PriceData> getPhoneList() {
            return phoneList;
        }

        public void setPhoneList(List<PriceData> phoneList) {
            this.phoneList = phoneList;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }


        public List<Exploreitem> getPricelist() {
            return pricelist;
        }

        public void setPricelist(List<Exploreitem> pricelist) {
            this.pricelist = pricelist;
        }

        public String getAdvanceamount() {
            return advanceamount;
        }

        public void setAdvanceamount(String advanceamount) {
            this.advanceamount = advanceamount;
        }

        public String getIngreditients() {
            return ingreditients;
        }

        public void setIngreditients(String ingreditients) {
            this.ingreditients = ingreditients;
        }

        public String getMaxqty() {
            return maxqty;
        }

        public void setMaxqty(String maxqty) {
            this.maxqty = maxqty;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getCompanyid() {
            return companyid;
        }

        public void setCompanyid(String companyid) {
            this.companyid = companyid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSubcatid() {
            return subcatid;
        }

        public void setSubcatid(String subcatid) {
            this.subcatid = subcatid;
        }

        public String getSubcatname() {
            return subcatname;
        }

        public void setSubcatname(String subcatname) {
            this.subcatname = subcatname;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getModuleid() {
            return moduleid;
        }

        public void setModuleid(String moduleid) {
            this.moduleid = moduleid;
        }

        public String getMudulename() {
            return mudulename;
        }

        public void setMudulename(String mudulename) {
            this.mudulename = mudulename;
        }

        public String getProductcode() {
            return productcode;
        }

        public void setProductcode(String productcode) {
            this.productcode = productcode;
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

        public class PriceData {
            String priceid,pricepid,ntype,stock,priceno;
            long nos,price;

            public long getNos() {
                return nos;
            }

            public void setNos(long nos) {
                this.nos = nos;
            }

            public String getNtype() {
                return ntype;
            }

            public void setNtype(String ntype) {
                this.ntype = ntype;
            }

            public long getPrice() {
                return price;
            }

            public void setPrice(long price) {
                this.price = price;
            }

            public String getPriceid() {
                return priceid;
            }

            public void setPriceid(String priceid) {
                this.priceid = priceid;
            }

            public String getPriceno() {
                return priceno;
            }

            public void setPriceno(String priceno) {
                this.priceno = priceno;
            }

            public String getPricepid() {
                return pricepid;
            }

            public void setPricepid(String pricepid) {
                this.pricepid = pricepid;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }
        }

    }
    private void CartCountincrement(){
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        cartcount_textview.setText(cartcounts);

    }
}

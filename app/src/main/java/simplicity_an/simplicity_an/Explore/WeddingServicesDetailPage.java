package simplicity_an.simplicity_an.Explore;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.List;

import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.Events;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/8/2017.
 */
public class WeddingServicesDetailPage extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RelativeLayout mainlayout;

    RequestQueue requestQueue;
    int requestcount=1;
    String URLPRODUCTLIST="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=wedding_services&scat=";

    String URLALL,URL;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;
    List<Exploreitem> exploreitemList_product=new ArrayList<>();

    ImageButton back,happenning,search_button,entertainment,morepage;

    LinearLayout mycartlayout;
    NetworkImageView productimageview;
    ImageLoader imageLoader;


    TextView venuename,venuelocation,venuedescription;
    TextView features_textview,feature_data_textview;
    TextView services_textview,services_data_textview;

    TextView requestqueue_textview;
    EditText name_edit,email_edit,phone_edit,functiontype_edit,message_edit;
    Button checkandavailability_button;
    private static EditText date_edit;
    LinearLayout features_layouts,services_layouts;
    String title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_wedding_services_detail);
        Intent get=getIntent();
        String cat_id=get.getStringExtra("CATID");
        String id=get.getStringExtra("ID");
        title=get.getStringExtra("TITLE");
        Log.e("ID",id+"pdetailpage");
        URL=URLPRODUCTLIST+cat_id+"&id="+id;
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
        features_layouts=(LinearLayout)findViewById(R.id.featues_layout);
        services_layouts=(LinearLayout)findViewById(R.id.services_layout);
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
        venuename = (TextView)findViewById(R.id.explore_weddingvenuedetail_venuename) ;
        venuelocation=(TextView)findViewById(R.id.explore_weddingvenuedetail_venuelocation) ;
        venuedescription=(TextView)findViewById(R.id.explore_weddingvenuedetail_venuedescription) ;
       features_textview=(TextView) findViewById(R.id.explore_weddingvenuedetail_hallfeatures);
       feature_data_textview=(TextView) findViewById(R.id.explore_weddingvenuedetail_hallfeatures_data);
        services_textview=(TextView) findViewById(R.id.explore_weddingvenuedetail_including) ;
        services_data_textview=(TextView) findViewById(R.id.explore_weddingvenuedetail_including_data);



        requestqueue_textview=(TextView) findViewById(R.id.explore_weddingvenuedetail_requestqueue);


        name_edit=(EditText) findViewById(R.id.explore_venue_name);
        email_edit=(EditText) findViewById(R.id.explore_venue_email);
        phone_edit=(EditText) findViewById(R.id.explore_venue_phone);
        date_edit=(EditText) findViewById(R.id.explore_venue_date);
        functiontype_edit=(EditText) findViewById(R.id.explore_venue_funtiontype);
        message_edit=(EditText) findViewById(R.id.explore_venue_message);
        checkandavailability_button=(Button)findViewById(R.id.explore_venue_checkavailability) ;




        checkandavailability_button.setTypeface(tf);
        checkandavailability_button.setText("Check Pricing & Availability");
        productimageview=(NetworkImageView)findViewById(R.id.explore_productdetail_productimage) ;


        venuename.setTypeface(tf);
        venuelocation.setTypeface(tf);
        venuedescription.setTypeface(tf);
        features_textview.setTypeface(tf);
       feature_data_textview.setTypeface(tf);

        services_textview.setTypeface(tf);
        services_data_textview.setTypeface(tf);

        requestqueue_textview.setTypeface(tf);
        requestqueue_textview.setText("Request Quote");



        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
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
        pDialog=new ProgressDialog(this);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progressdialog);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            date_edit.setText(day + "/" + (month + 1) + "/" + year);
        }
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



                JSONObject jsonObject = (JSONObject) productlist.get(i);

                String image = jsonObject.isNull("image") ? null : jsonObject
                        .getString("image");
                items.setImage(image);
                items.setId(jsonObject.getString("id"));
                items.setLocation(jsonObject.getString("location"));
                items.setHallfeatures(jsonObject.getString("specialization"));
                items.setPriceinclude(jsonObject.getString("features"));
                items.setPriceexclude(jsonObject.getString("services"));
                items.setProductname(jsonObject.getString("title"));
                items.setDescription(jsonObject.getString("description"));
                productimageview.setImageUrl(image,imageLoader);
                String description=jsonObject.getString("description").toString();
//tToast.makeText(getApplicationContext(),description,Toast.LENGTH_SHORT).show();
                venuename.setText(jsonObject.getString("title"));
                venuelocation.setText(jsonObject.getString("location"));
                venuedescription.setText(Html.fromHtml(jsonObject.getString("description")));
                if(jsonObject.getString("specialization").equalsIgnoreCase("")&&jsonObject.getString("features").equalsIgnoreCase("")&&jsonObject.getString("services").equalsIgnoreCase("")){
                    features_layouts.setVisibility(View.GONE);
                }else {
                    if (jsonObject.getString("specialization").equalsIgnoreCase("") || jsonObject.getString("specialization") == null) {
                        features_textview.setText("Features");
                        feature_data_textview.setText(Html.fromHtml(jsonObject.getString("features")));

                    } else {
                        features_textview.setText(title + "Specialization");
                        feature_data_textview.setText(Html.fromHtml(jsonObject.getString("specialization")));
                    }
                }
                if(jsonObject.getString("services").equalsIgnoreCase("")){
                    services_layouts.setVisibility(View.GONE);
                }else {
                    services_textview.setText("Services");
                    services_data_textview.setText(Html.fromHtml(jsonObject.getString("services")));

                }


                exploreitemList_product.add(items);

            }

        }catch (JSONException e) {

        }

    }

    public    class Exploreitem {
        String id, productname, image, productcode, gender, color, moduleid, mudulename;
        String catid, categoryname, subcatid, subcatname, companyid, brand, description, companyname;
        String specification, maxqty, ingreditients, advanceamount;
        String priceinclude,priceexclude,hallfeatures,location;
        List<Exploreitem> pricelist;
        private List<PriceData> phoneList;
        private List<Jewellry> jewellrylist;
        int wishlistcount;

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocation() {
            return location;
        }

        public String getHallfeatures() {
            return hallfeatures;
        }

        public String getPriceexclude() {
            return priceexclude;
        }

        public String getPriceinclude() {
            return priceinclude;
        }

        public void setHallfeatures(String hallfeatures) {
            this.hallfeatures = hallfeatures;
        }

        public void setPriceexclude(String priceexclude) {
            this.priceexclude = priceexclude;
        }

        public void setPriceinclude(String priceinclude) {
            this.priceinclude = priceinclude;
        }

        public int getWishlistcount() {
            return wishlistcount;
        }

        public void setWishlistcount(int wishlistcount) {
            this.wishlistcount = wishlistcount;
        }

        public List<Jewellry> getJewellrylist() {
            return jewellrylist;
        }

        public void setJewellrylist(List<Jewellry> jewellrylist) {
            this.jewellrylist = jewellrylist;
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
        public class Jewellry{
            String karagtage,diamondweight,gemsweight,emerald,grossweight,wastage,netweight,makingcharges,metalwidth,metalheight,certificationcharge,stonecharge;

            public String getCertificationcharge() {
                return certificationcharge;
            }

            public void setCertificationcharge(String certificationcharge) {
                this.certificationcharge = certificationcharge;
            }

            public String getDiamondweight() {
                return diamondweight;
            }

            public void setDiamondweight(String diamondweight) {
                this.diamondweight = diamondweight;
            }

            public String getEmerald() {
                return emerald;
            }

            public void setEmerald(String emerald) {
                this.emerald = emerald;
            }

            public String getGemsweight() {
                return gemsweight;
            }

            public void setGemsweight(String gemsweight) {
                this.gemsweight = gemsweight;
            }

            public String getGrossweight() {
                return grossweight;
            }

            public void setGrossweight(String grossweight) {
                this.grossweight = grossweight;
            }

            public String getKaragtage() {
                return karagtage;
            }

            public void setKaragtage(String karagtage) {
                this.karagtage = karagtage;
            }

            public String getMakingcharges() {
                return makingcharges;
            }

            public void setMakingcharges(String makingcharges) {
                this.makingcharges = makingcharges;
            }

            public String getMetalheight() {
                return metalheight;
            }

            public void setMetalheight(String metalheight) {
                this.metalheight = metalheight;
            }

            public String getMetalwidth() {
                return metalwidth;
            }

            public void setMetalwidth(String metalwidth) {
                this.metalwidth = metalwidth;
            }

            public String getNetweight() {
                return netweight;
            }

            public void setNetweight(String netweight) {
                this.netweight = netweight;
            }

            public String getStonecharge() {
                return stonecharge;
            }

            public void setStonecharge(String stonecharge) {
                this.stonecharge = stonecharge;
            }

            public String getWastage() {
                return wastage;
            }

            public void setWastage(String wastage) {
                this.wastage = wastage;
            }
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
}

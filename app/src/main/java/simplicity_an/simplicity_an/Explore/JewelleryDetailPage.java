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
import android.widget.Button;
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
 * Created by kuppusamy on 3/2/2017.
 */
public class JewelleryDetailPage extends AppCompatActivity {
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

    String URLALL,URL;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;
    List<Exploreitem> exploreitemList_product=new ArrayList<>();

    ImageButton back,happenning,search_button,entertainment,morepage;
    TextView mycart_textview,buynow_textview,cartcount_textview,explore_productbuynow_textview;
    LinearLayout mycartlayout;
    NetworkImageView productimageview;
    ImageLoader imageLoader;
    List<String> pricenos = new ArrayList<String>();
    List<String> price_detail = new ArrayList<String>();
    Button buynow_button,appoinment_button;
    TextView jeweldescription_webview;
    LinearLayout specification_layout,features_layout;
    TextView features_TextView_title,specification_TextView_title;
    TextView productcode_textview,producttitle_textview,productprice_textview;
    TextView spec_karatage_textview,spec_grossweight_textview,spec_wastage_textview,spec_netweight_textview,spec_makingcost_textview;
    TextView spec_karatage_value_textview,spec_grossweight_value_textview,spec_wastage_value_textview,spec_netweight_value_textview,spec_makingcost_value_textview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_jewel_detailpage);
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
        productcode_textview = (TextView)findViewById(R.id.explore_jeweldetail_productcode) ;
        producttitle_textview=(TextView)findViewById(R.id.explore_jeweldetail_title) ;
        productprice_textview=(TextView)findViewById(R.id.explore_jeweldetail_price) ;
        features_TextView_title=(TextView) findViewById(R.id.explore_jeweldetail_features);
        specification_TextView_title=(TextView) findViewById(R.id.explore_jeweldetail_specifications);
jeweldescription_webview=(TextView) findViewById(R.id.textView_desc) ;

        spec_karatage_textview=(TextView) findViewById(R.id.explore_jeweldetail_karagtage);
        spec_grossweight_textview=(TextView) findViewById(R.id.explore_jeweldetail_grossweight);
        spec_wastage_textview=(TextView) findViewById(R.id.explore_jeweldetail_wastage);
        spec_netweight_textview=(TextView) findViewById(R.id.explore_jeweldetail_netweight);
        spec_makingcost_textview=(TextView) findViewById(R.id.explore_jeweldetail_makingcharges);

        spec_karatage_value_textview=(TextView) findViewById(R.id.explore_jeweldetail_karagtage_value);
        spec_grossweight_value_textview=(TextView) findViewById(R.id.explore_jeweldetail_grossweight_value);
        spec_wastage_value_textview=(TextView) findViewById(R.id.explore_jeweldetail_wastage_value);
        spec_netweight_value_textview=(TextView) findViewById(R.id.explore_jeweldetail_netweight_value);
        spec_makingcost_value_textview=(TextView) findViewById(R.id.explore_jeweldetail_makingcharges_value);




        mycart_textview=(TextView)findViewById(R.id.mycart_textview);
        buynow_textview=(TextView)findViewById(R.id.buynow_textview) ;
        cartcount_textview=(TextView)findViewById(R.id.cart_main_count);
        mycartlayout=(LinearLayout)findViewById(R.id.cart_layout) ;
        productimageview=(NetworkImageView)findViewById(R.id.explore_productdetail_productimage) ;
specification_layout=(LinearLayout)findViewById(R.id.jewel_specification_layout) ;
        features_layout=(LinearLayout) findViewById(R.id.jewel_feature_layout);
        buynow_button=(Button)findViewById(R.id.buynow_jewel_detail_button) ;
        appoinment_button=(Button) findViewById(R.id.bookappoinment_jewel_detail_button);
        mycart_textview.setTypeface(tf);
        cartcount_textview.setTypeface(tf);
        buynow_textview.setTypeface(tf);
        productcode_textview.setTypeface(tf);
        producttitle_textview.setTypeface(tf);
        productprice_textview.setTypeface(tf);
        features_TextView_title.setTypeface(tf);
        specification_TextView_title.setTypeface(tf);
        buynow_button.setTypeface(tf);

        spec_karatage_textview.setTypeface(tf);
        spec_grossweight_textview.setTypeface(tf);
        spec_wastage_textview.setTypeface(tf);
        spec_netweight_textview.setTypeface(tf);
        spec_makingcost_textview.setTypeface(tf);
        spec_karatage_value_textview.setTypeface(tf);
        spec_grossweight_value_textview.setTypeface(tf);
        spec_wastage_value_textview.setTypeface(tf);
        spec_netweight_value_textview.setTypeface(tf);
        spec_makingcost_value_textview.setTypeface(tf);

        buynow_button.setText("Buy Now");
        appoinment_button.setText("Book An Appointment");
        appoinment_button.setTypeface(tf);



        Log.e("CARTCOUNT",cartcounts+"hi");
        if(cartcounts==null||cartcounts.equalsIgnoreCase("")){
            cartcount_textview.setText("0");

        }else {
            cartcount_textview.setText(cartcounts);
        }

        mycartlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(getApplicationContext(),"go to cart page",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.remove(CARTCOUNT);
                editor.apply();
                CartCountincrement();*/
                Intent cart=new Intent(getApplicationContext(),CartListPage.class);
                startActivity(cart);
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
              // items.setWishlistcount(jsonObject.getInt("wishlist_count"));
             items.setDescription(jsonObject.getString("description"));
                items.setCompanyname(jsonObject.getString("company_name"));
                items.setSpecification(jsonObject.getString("specification"));
                items.setIngreditients(jsonObject.getString("ingredients"));
                items.setMaxqty(jsonObject.getString("maxqty"));
                items.setAdvanceamount(jsonObject.getString("advance_amount"));
               String description=jsonObject.getString("description").toString();
//tToast.makeText(getApplicationContext(),description,Toast.LENGTH_SHORT).show();
                if(description==null||description.equalsIgnoreCase("")){
                    features_layout.setVisibility(View.GONE);
                }else {
                    features_TextView_title.setText("FEATURES");
                    String ss = description;
                    String s = ss;
                    // s = s.replace("\"", "'");
                    s = s.replace("\\", "");

                    String data="<div style = 'width:100%;float:left;color:#fff;'>test here</div>";
                    jeweldescription_webview.setText(Html.fromHtml(jsonObject.getString("description").toString()));
                   // jeweldescription_webview.loadData(description, "text/html; charset=UTF-8", null);
                    // description.setBackgroundColor(0x0a000000);
                  //  jeweldescription_webview.setBackgroundColor(Color.TRANSPARENT);
                }
                String title=jsonObject.getString("title");
                Log.e("TEX",image+title+jsonObject.getString("image")+jsonObject.getString("description"));
                Log.e("Desc",description+","+image);
                productimageview.setImageUrl(image,imageLoader);
                productcode_textview.setText("Product Code: "+jsonObject.getString("prod_code"));
                producttitle_textview.setText(title);

                List<Exploreitem.PriceData> price_item_list = new ArrayList<Exploreitem.PriceData>();

                JSONArray pricelist = jsonObject.getJSONArray("price");
                Log.e("Pricesize", String.valueOf(pricelist));
                for (int k = 0; k < pricelist.length(); k++) {
                    JSONObject objects = (JSONObject) pricelist.get(k);
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
                    pricenos.add(name+""+ntype);

                    price_detail.add(price);
                    if(price.equalsIgnoreCase("0.00")||price.equalsIgnoreCase("")){

                    }else {
                        productprice_textview.setText(objects.getString("price"));
                    }
                }

                items.setPhoneList(price_item_list);
                List<Exploreitem.Jewellry> jewellryitemlist=new ArrayList<>();
                JSONArray jewellrylist = jsonObject.getJSONArray("jewel_info");
                for(int s=0;s<jewellrylist.length();s++){
                    JSONObject objectjewel=(JSONObject)jewellrylist.get(s);
                    Exploreitem.Jewellry jewellryitem=items.new Jewellry();
                    jewellryitem.setKaragtage(objectjewel.getString("karatage"));
                    jewellryitem.setDiamondweight(objectjewel.getString("diamond_weight"));
                    jewellryitem.setGemsweight(objectjewel.getString("gems_weight"));
                    jewellryitem.setEmerald(objectjewel.getString("emerald"));
                    jewellryitem.setGrossweight(objectjewel.getString("gross_weight"));
                    jewellryitem.setWastage(objectjewel.getString("wastage"));
                    jewellryitem.setNetweight(objectjewel.getString("net_weight"));
                    jewellryitem.setMakingcharges(objectjewel.getString("making_charges"));
                    jewellryitem.setMetalwidth(objectjewel.getString("metal_width"));
                    jewellryitem.setMetalheight(objectjewel.getString("metal_height"));
                    jewellryitem.setCertificationcharge(objectjewel.getString("certification_charge"));
                    jewellryitem.setStonecharge(objectjewel.getString("stone_charge"));
                    jewellryitemlist.add(jewellryitem);
                    String karagtage=objectjewel.getString("karatage");
                    String gross=objectjewel.getString("gross_weight");
                    String netwg=objectjewel.getString("net_weight");
                    String wastage=objectjewel.getString("wastage");
                    String makingcharges=objectjewel.getString("making_charges");

if(karagtage.equalsIgnoreCase("")||karagtage.equalsIgnoreCase("0")&&gross.equalsIgnoreCase("")||gross.equalsIgnoreCase("0")&&netwg.equalsIgnoreCase("")||netwg.equalsIgnoreCase("0")
        &&wastage.equalsIgnoreCase("")||wastage.equalsIgnoreCase("0")&&makingcharges.equalsIgnoreCase("")||makingcharges.equalsIgnoreCase("0")){
    specification_layout.setVisibility(View.GONE);
        }else {
    specification_TextView_title.setText("SPECIFICATION");
    if (karagtage.equalsIgnoreCase("") || karagtage.equalsIgnoreCase("0")) {

    } else {
        spec_karatage_textview.setText("Gold Karatage");
        spec_karatage_value_textview.setText(karagtage);
    }
    if (gross.equalsIgnoreCase("") || gross.equalsIgnoreCase("0")) {

    } else {
        spec_grossweight_textview.setText("Gold Gross Weight");
        spec_grossweight_value_textview.setText(gross);
    }

    if (netwg.equalsIgnoreCase("") || netwg.equalsIgnoreCase("0")) {

    } else {
        spec_netweight_textview.setText("Net Weight");
        spec_netweight_value_textview.setText(netwg);
    }

    if (wastage.equalsIgnoreCase("") || wastage.equalsIgnoreCase("0")) {

    } else {
        spec_wastage_textview.setText("Wastage");
        spec_wastage_value_textview.setText(wastage);
    }
    if (makingcharges.equalsIgnoreCase("") || makingcharges.equalsIgnoreCase("0")) {

    } else {
        spec_makingcost_textview.setText("Making Charges");
        spec_makingcost_value_textview.setText(makingcharges);
    }
}

                }
                items.setJewellrylist(jewellryitemlist);
                exploreitemList_product.add(items);

            }

        }catch (JSONException e) {

        }

    }

    public    class Exploreitem {
        String id, productname, image, productcode, gender, color, moduleid, mudulename;
        String catid, categoryname, subcatid, subcatname, companyid, brand, description, companyname;
        String specification, maxqty, ingreditients, advanceamount;
        List<Exploreitem> pricelist;
        private List<PriceData> phoneList;
        private List<Jewellry> jewellrylist;
        int wishlistcount;

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
    private void CartCountincrement(){
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        cartcount_textview.setText(cartcounts);

    }
}

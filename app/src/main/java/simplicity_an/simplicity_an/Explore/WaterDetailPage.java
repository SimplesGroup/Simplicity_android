package simplicity_an.simplicity_an.Explore;

import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by kuppusamy on 3/3/2017.
 */
public class WaterDetailPage extends Activity {
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
    List<String> orderdatelist = new ArrayList<String>();
    List<String> deleverytimelist = new ArrayList<String>();
    List<String> orderqtylist = new ArrayList<String>();
    List<String> deleverytimelist_value = new ArrayList<String>();
    List<String> orderdatelist_value = new ArrayList<String>();
    List<String> orderqtylist_value = new ArrayList<String>();
    Button buynow_button,appoinment_button;
    TextView orderdate_textview,ordertype_textview,deleverytime_textview,orderquantity_textview;
    Spinner orderdate_spinner,deleveritime_spinner,orderqty_spinner;
TextView advanceamount_textview,qtytype_textview,refillprice_textview;
    RadioButton new_radiobutton,refill_radiobutton;
    String radio_button_values;

    String DeliveryURL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=waterinfo&wtype=odate";
    String DeliverytimeURL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=waterinfo&wtype=wtime";
    String OrderqtyURL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=waterinfo&wtype=wqty";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_waterdetail_page);
        orderdatelist.add("Select Order Date");
        deleverytimelist.add("Select Delivery Time");
        orderqtylist.add("Select Order Qty");
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
       orderdate_textview = (TextView)findViewById(R.id.explore_waterdetail_orderdate) ;
        ordertype_textview=(TextView)findViewById(R.id.explore_waterdetail_ortertype) ;
        deleverytime_textview=(TextView)findViewById(R.id.explore_waterdetail_deliverytime) ;
        orderquantity_textview=(TextView) findViewById(R.id.explore_waterdetail_orderqty);

        advanceamount_textview=(TextView)findViewById(R.id.explore_waterdetail_advanceamount);
        qtytype_textview=(TextView)findViewById(R.id.explore_waterdetail_quantity) ;
        refillprice_textview=(TextView) findViewById(R.id.explore_waterdetail_refilprice);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);



        orderdate_spinner=(Spinner) findViewById(R.id.explore_waterdetail_spinner_price);
        deleveritime_spinner=(Spinner) findViewById(R.id.explore_waterdetail_spinner_deliverytime);
        orderqty_spinner=(Spinner) findViewById(R.id.explore_waterdetail_spinner_orderqty);



        mycart_textview=(TextView)findViewById(R.id.mycart_textview);
        buynow_textview=(TextView)findViewById(R.id.buynow_textview) ;
        cartcount_textview=(TextView)findViewById(R.id.cart_main_count);
        mycartlayout=(LinearLayout)findViewById(R.id.cart_layout) ;
        productimageview=(NetworkImageView)findViewById(R.id.explore_productdetail_productimage) ;

        buynow_button=(Button)findViewById(R.id.buynow_jewel_detail_button) ;
        appoinment_button=(Button) findViewById(R.id.bookappoinment_jewel_detail_button);
        mycart_textview.setTypeface(tf);
        cartcount_textview.setTypeface(tf);
        buynow_textview.setTypeface(tf);
        orderquantity_textview.setTypeface(tf);
        orderdate_textview.setTypeface(tf);
        ordertype_textview.setTypeface(tf);
        deleverytime_textview.setTypeface(tf);
        advanceamount_textview.setTypeface(tf);
        qtytype_textview.setTypeface(tf);
        refillprice_textview.setTypeface(tf);

ordertype_textview.setText("Order Type");
        orderdate_textview.setText("Order Date");
        orderquantity_textview.setText("Order Qty");
        deleverytime_textview.setText("Delivery Time");
        buynow_button.setText("Cancel");
        appoinment_button.setText("Submit");
        appoinment_button.setTypeface(tf);


appoinment_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int selected=radioGroup.getCheckedRadioButtonId();
        new_radiobutton=(RadioButton)findViewById(selected);
        radio_button_values=new_radiobutton.getText().toString();

        Toast.makeText(WaterDetailPage.this,radio_button_values, Toast.LENGTH_LONG).show();

    }
});
        Log.e("CARTCOUNT",cartcounts+"hi");
        if(cartcounts==null||cartcounts.equalsIgnoreCase("")){
            cartcount_textview.setText("0");

        }else {
            cartcount_textview.setText(cartcounts);
        }

        mycartlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"go to cart page", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.remove(CARTCOUNT);
                editor.apply();
                CartCountincrement();
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
        ArrayAdapter<String> arrayAdapter_date=new ArrayAdapter<String>(getApplicationContext(), R.layout.view_spinner_explore,orderdatelist);
        arrayAdapter_date.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        orderdate_spinner.setAdapter(arrayAdapter_date);

        ArrayAdapter<String> arrayAdapter_time=new ArrayAdapter<String>(getApplicationContext(), R.layout.view_spinner_explore,deleverytimelist);
        arrayAdapter_time.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
       deleveritime_spinner.setAdapter(arrayAdapter_time);

        ArrayAdapter<String> arrayAdapter_qty=new ArrayAdapter<String>(getApplicationContext(), R.layout.view_spinner_explore,orderqtylist);
        arrayAdapter_qty.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        orderqty_spinner.setAdapter(arrayAdapter_qty);
        getDeliveryDate();
        getDeliverytime();
        getOrderqty();
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
                advanceamount_textview.setText("Advance Amount: "+jsonObject.getString("advance_amount"));
                String title=jsonObject.getString("title");
                Log.e("TEX",image+title+jsonObject.getString("image")+jsonObject.getString("description"));
                Log.e("Desc",description+","+image);
                productimageview.setImageUrl(image,imageLoader);

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

qtytype_textview.setText(String.valueOf(name)+ntype);
                    refillprice_textview.setText("Refill Price: "+price);

                }

                items.setPhoneList(price_item_list);

                exploreitemList_product.add(items);

            }

        }catch (JSONException e) {

        }

    }
private void getDeliveryDate(){
    JsonObjectRequest deliverydate=new JsonObjectRequest(Request.Method.GET, DeliveryURL, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray productlist=response.getJSONArray("result");
                for(int i=0;i<productlist.length();i++) {
                    JSONObject object=(JSONObject)productlist.get(i);
Exploreitem exploreitem=new Exploreitem();
                    exploreitem.setWatertext(object.getString("text"));
                    exploreitem.setWatervalue(object.getString("value"));
                    orderdatelist.add(object.getString("text"));
                    orderdatelist_value.add(object.getString("value"));
                }
                }catch (JSONException e){

            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    deliverydate.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    requestQueue.add(deliverydate);
}
    private void getDeliverytime(){
        JsonObjectRequest deliverytime=new JsonObjectRequest(Request.Method.GET, DeliverytimeURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray productlist=response.getJSONArray("result");
                    for(int i=0;i<productlist.length();i++) {
                        JSONObject object=(JSONObject)productlist.get(i);
                        Exploreitem exploreitem=new Exploreitem();
                        exploreitem.setWatertext(object.getString("text"));
                        exploreitem.setWatervalue(object.getString("value"));
                        deleverytimelist.add(object.getString("text"));
                        deleverytimelist_value.add(object.getString("value"));
                    }
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        deliverytime.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(deliverytime);
    }
    private void getOrderqty(){
        JsonObjectRequest orderqty=new JsonObjectRequest(Request.Method.GET, OrderqtyURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray productlist=response.getJSONArray("result");
                    for(int i=0;i<productlist.length();i++) {
                        JSONObject object=(JSONObject)productlist.get(i);
                        Exploreitem exploreitem=new Exploreitem();
                        exploreitem.setWatertext(object.getString("text"));
                        exploreitem.setWatervalue(object.getString("value"));
                        orderqtylist.add(object.getString("text"));
                        orderqtylist_value.add(object.getString("value"));
                    }
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        orderqty.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(orderqty);
    }
    public    class Exploreitem {
        String watertext,watervalue;
        String id, productname, image, productcode, gender, color, moduleid, mudulename;
        String catid, categoryname, subcatid, subcatname, companyid, brand, description, companyname;
        String specification, maxqty, ingreditients, advanceamount;
        List<Exploreitem> pricelist;
        private List<PriceData> phoneList;
        private List<Jewellry> jewellrylist;
        int wishlistcount;

        public String getWatertext() {
            return watertext;
        }

        public void setWatertext(String watertext) {
            this.watertext = watertext;
        }

        public String getWatervalue() {
            return watervalue;
        }

        public void setWatervalue(String watervalue) {
            this.watervalue = watervalue;
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
    private void CartCountincrement(){
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        cartcount_textview.setText(cartcounts);

    }
}

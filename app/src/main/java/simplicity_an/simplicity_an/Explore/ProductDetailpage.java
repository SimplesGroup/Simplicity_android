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
 * Created by kuppusamy on 3/1/2017.
 */
public class ProductDetailpage extends AppCompatActivity {
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_product_detail);
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
        cartcount_textview=(TextView)findViewById(R.id.cart_main_count);
        mycartlayout=(LinearLayout)findViewById(R.id.cart_layout) ;
productimageview=(NetworkImageView)findViewById(R.id.explore_productdetail_productimage) ;
        productprices_spinner=(Spinner)findViewById(R.id.explore_productdetail_spinner_price);
        mycart_textview.setTypeface(tf);
        cartcount_textview.setTypeface(tf);
        buynow_textview.setTypeface(tf);
        explore_producttitle_textview.setTypeface(tf);
        explore_productprice_textview.setTypeface(tf);
        explore_productbuynow_textview.setTypeface(tf);
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
        List<Exploreitem> pricelist;
        private List<PriceData> phoneList;
        int wishlistcount;

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

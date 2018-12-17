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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;

/**
 * Created by kuppusamy on 3/3/2017.
 */
public class WaterListPage extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RelativeLayout mainlayout;
    SearchView search;
    String search_value;
    RequestQueue requestQueue;
    RecyclerView explore_item_recyclerview;
    int requestcount=1;
    String URLPRODUCTLIST="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=products&";
    String URLFAV="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=wishlist";
    String URLALL,URL;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;
    List<Exploreitem> exploreitemList_product=new ArrayList<>();
    RecylerviewAdapter product_adapter;
    ImageButton back,happenning,search_button,entertainment,morepage;
    TextView explore_title_textview,coimbatore_title_textview,mycart_textview,buynow_textview,cartcount_textview;
    LinearLayout mycartlayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.waterlistpage);
        Intent get=getIntent();
        String title=get.getStringExtra("TITLE");
        String id=get.getStringExtra("ID");
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
        getData();
        back=(ImageButton)findViewById(R.id.btn_ex_back);
        happenning=(ImageButton)findViewById(R.id.btn_ex_happening);
        search_button=(ImageButton)findViewById(R.id.btn_ex_search);
        entertainment=(ImageButton)findViewById(R.id.btn_ex_entertainment);
        morepage=(ImageButton)findViewById(R.id.btn_ex_more);
        explore_title_textview = (TextView)findViewById(R.id.title_text) ;

        mycart_textview=(TextView)findViewById(R.id.mycart_textview);
        buynow_textview=(TextView)findViewById(R.id.buynow_textview) ;
        cartcount_textview=(TextView)findViewById(R.id.cart_main_count);
        mycartlayout=(LinearLayout)findViewById(R.id.cart_layout) ;

        mycart_textview.setTypeface(tf);
        cartcount_textview.setTypeface(tf);
        buynow_textview.setTypeface(tf);
        explore_title_textview.setTypeface(tf);
        explore_title_textview.setText(title);
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

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        explore_item_recyclerview=(RecyclerView)findViewById(R.id.expolre_jewellery_recylerview);
        explore_item_recyclerview.setLayoutManager(layoutManager);
        explore_item_recyclerview.setNestedScrollingEnabled(false);
        pDialog=new ProgressDialog(this);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progressdialog);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        product_adapter = new RecylerviewAdapter(exploreitemList_product,explore_item_recyclerview);
        explore_item_recyclerview.setAdapter(product_adapter);
        product_adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        getData();


                        product_adapter.setLoaded();
                    }
                }, 2000);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData(){
        requestQueue.add(getDatafromServer(requestcount));
        requestcount++;
    }
    private JsonObjectRequest getDatafromServer(int requestcount){

        if(myprofileid!=null){
            URLALL=URL+"&page="+requestcount+"&user_id="+myprofileid;
        }else {
            URLALL=URL+"&page="+requestcount;
        }

        Log.e("URL",URLALL);
        JsonObjectRequest   jsonReq = new JsonObjectRequest(Request.Method.GET,
                URLALL,  new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                if (response != null) {
                    dissmissDialog();
                    parseJsonFeed(response);
                    Log.e("JSON",response.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
            }
        });

        jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonReq);
        return jsonReq;
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
        try {
            JSONArray productlist=response.getJSONArray("result");
            for(int i=0;i<productlist.length();i++){
                Exploreitem items=new Exploreitem();



                JSONObject jsonObject = (JSONObject) productlist.get(i);
                items.setId(jsonObject.getString("id"));
                items.setImage(jsonObject.getString("image"));
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
                items.setWishlistcount(jsonObject.getInt("wishlist_count"));
                items.setDescription(jsonObject.getString("description"));
                items.setCompanyname(jsonObject.getString("company_name"));
                items.setSpecification(jsonObject.getString("specification"));
                items.setIngreditients(jsonObject.getString("ingredients"));
                items.setMaxqty(jsonObject.getString("maxqty"));
                items.setAdvanceamount(jsonObject.getString("advance_amount"));

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
                }

                items.setPhoneList(price_item_list);
                exploreitemList_product.add(items);
            }
            product_adapter.notifyDataSetChanged();
        }catch (JSONException e) {

        }

    }

    public    class Exploreitem{
        String id,productname,image,productcode,gender,color,moduleid,mudulename;
        String catid,categoryname,subcatid,subcatname,companyid,brand,description,companyname;
        String specification,maxqty,ingreditients,advanceamount;
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
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView product_image;
        TextView product_name,product_price,product_advanceamount,product_quantity;

       Button add_tocart,add_to_fav;
        public UserViewHolder(View itemView) {
            super(itemView);
            product_image = (NetworkImageView) itemView.findViewById(R.id.explore_productlist_image);
            product_name=(TextView)itemView.findViewById(R.id.explore_waterlist_title);
            product_price=(TextView)itemView.findViewById(R.id.explore_waterlist_refilprice);
            product_advanceamount=(TextView)itemView.findViewById(R.id.explore_waterlist_advanceamount);
            product_quantity=(TextView)itemView.findViewById(R.id.explore_waterlist_quantity);


            add_tocart=(Button) itemView.findViewById(R.id.explore_waterlist_cartbutton);


        }
    }
    public class RecylerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        ImageLoader mImageLoader;
        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_LOADING = 3;
        OnLoadMoreListener onLoadMoreListener;
        private final int VIEW_TYPE_PHOTOSTORY = 2;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;
        boolean loading;
        SpinnerAdapter spinnerAdapter;
        String pidvalues;
        ArrayAdapter<String> dataAdapter;
        public RecylerviewAdapter(List<Exploreitem> students, RecyclerView recyclerView) {
            exploreitemList_product = students;

            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                        .getLayoutManager();


                recyclerView
                        .addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView,
                                                   int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                totalItemCount = linearLayoutManager.getItemCount();
                                lastVisibleItem = linearLayoutManager
                                        .findLastVisibleItemPosition();
                                if (!loading
                                        && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                    // End has been reached
                                    // Do something
                                    if (onLoadMoreListener != null) {
                                        onLoadMoreListener.onLoadMore();
                                    }
                                    loading = true;
                                }
                            }
                        });
            }
        }

        @Override
        public int getItemCount() {
            return exploreitemList_product.size();
        }
        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }
        public void setLoaded() {
            loading = false;
        }

        /**
         * Return the view type of the item at <code>position</code> for the purposes
         * of view recycling.
         * <p/>
         * <p>The default implementation of this method returns 0, making the assumption of
         * a single view type for the adapter. Unlike ListView adapters, types need not
         * be contiguous. Consider using id resources to uniquely identify item view types.
         *
         * @param position position to query
         * @return integer value identifying the type of the view needed to represent the item at
         * <code>position</code>. Type codes need not be contiguous.
         */
        @Override
        public int getItemViewType(int position) {
            return exploreitemList_product.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from ( parent.getContext () );
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.explore_waterlist_feed, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof UserViewHolder) {
                final UserViewHolder userViewHolder = (UserViewHolder) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                final Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                if (mImageLoader == null)
                    mImageLoader = CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
                final Exploreitem itemmodel = exploreitemList_product.get(position);
                userViewHolder.product_name.setText("Water Type : "+itemmodel.getProductname());
                userViewHolder.product_name.setTypeface(seguiregular);
                userViewHolder.product_price.setTypeface(seguiregular);
                userViewHolder.product_advanceamount.setTypeface(seguiregular);
                userViewHolder.product_advanceamount.setText("Advance Amount: "+itemmodel.getAdvanceamount());
                userViewHolder.product_quantity.setTypeface(seguiregular);
                userViewHolder.product_image.setImageUrl(itemmodel.getImage(), mImageLoader);
                for(int k=0;k<itemmodel.getPhoneList().size();k++){
                    Exploreitem.PriceData item=itemmodel.getPhoneList().get(k);
                    long name=item.getNos();
                    String ntype=item.getNtype();
long refilprice=item.getPrice();
                  userViewHolder.product_quantity.setText(String.valueOf(name)+ntype);
userViewHolder.product_price.setText("Refill Price:"+ String.valueOf(refilprice));
                }
                userViewHolder.add_tocart.setTypeface(seguiregular);
                userViewHolder.add_tocart.setText("BOOK"+"\n"+"NOW");
                userViewHolder.add_tocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myprofileid!=null) {
                            Toast.makeText(getApplicationContext(),"go to cartpage", Toast.LENGTH_SHORT).show();
                            String val=cartcount_textview.getText().toString();
                            Log.e("CART", val);
                            int values=0;
                            if(val!=null) {
                                // val = val.replace(" ", "");
                                try {
                                    val = val.trim();
                                    int valstring = Integer.parseInt(val);
                                    Log.e("CART", val);
                                    values = valstring + 1;
                                }catch (NumberFormatException e){

                                }


                            }else {
                                values=1;
                            }
                            // int values=Integer.valueOf(val)+1;

                            Log.e("CARTfinal", String.valueOf(values));
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(CARTCOUNT, String.valueOf(values));
                            editor.commit();
                            CartCountincrement();
                           Intent detail=new Intent(getApplicationContext(),WaterDetailPage.class);
                            detail.putExtra("ID",itemmodel.getId());
                            startActivity(detail);

                        }else {
                            Intent signin=new Intent(getApplicationContext(),SigninpageActivity.class);
                            startActivity(signin);
                            finish();
                        }
                    }
                });

            }else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
    }
    private void CartCountincrement(){
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        cartcount_textview.setText(cartcounts);

    }
}

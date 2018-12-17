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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.Events;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;

/**
 * Created by kuppusamy on 3/11/2017.
 */
public class CartListPage extends AppCompatActivity {
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
    String URLREMOVE="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=deletefromcart";
    String URLFAV="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=wishlist";
    String URLALL,URL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=viewcart";
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;
    List<Exploreitem> exploreitemList_product=new ArrayList<>();
    RecylerviewAdapter product_adapter;
    ImageButton back,happenning,search_button,entertainment,morepage;
    TextView explore_title_textview,coimbatore_title_textview,mycart_textview;
    LinearLayout mycartlayout;
    TextView totalitemprice_textview,addmoreproducts_textview;
    String id;
    String cart_item_size;
    int cart_total_price;
    int quantity_value,max_quantity_value,price_values,check_shippinadress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_cartlist_page);


        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
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

        mycart_textview=(TextView)findViewById(R.id.mycart_checkout);
        totalitemprice_textview=(TextView)findViewById(R.id.explore_mycart_totalitemsandprice);
        addmoreproducts_textview=(TextView)findViewById(R.id.explore_mycart_addmoreproducts);
        mycartlayout=(LinearLayout)findViewById(R.id.cart_layout) ;

        mycart_textview.setTypeface(tf);
        totalitemprice_textview.setTypeface(tf);
        addmoreproducts_textview.setTypeface(tf);
        explore_title_textview.setTypeface(tf);
        explore_title_textview.setText("My Cart");
addmoreproducts_textview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent detail=new Intent(getApplicationContext(),ExploreMain.class);
        startActivity(detail);
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


        URLALL=URL+"&user_id="+myprofileid+"&page="+requestcount;
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
                JSONObject jsonObject=(JSONObject)productlist.get(i);
                items.setId(jsonObject.getString("id"));
                items.setImage(jsonObject.getString("image"));
                items.setProductname(jsonObject.getString("name"));
                items.setUserid(jsonObject.getString("user_id"));
                items.setNtype(jsonObject.getString("ntype"));
                items.setNos(jsonObject.getString("nos"));
                items.setShippingdate(jsonObject.getString("delivery_days"));
                items.setPrice(jsonObject.getInt("total_pdt_price"));
                items.setQuantity(jsonObject.getInt("qty"));
                items.setShippingcharge(jsonObject.getInt("shipping_charge"));
                items.setMaxqty(jsonObject.getInt("maxqty"));
                items.setPricetypeid(jsonObject.getString("price_type"));
                items.setWishlishcount(jsonObject.getInt("wishlist_count"));
                items.setShippingcheckaddress(jsonObject.getInt("shipping_check"));
                check_shippinadress=jsonObject.getInt("shipping_check");
                exploreitemList_product.add(items);
cart_total_price=cart_total_price+jsonObject.getInt("total_pdt_price")+jsonObject.getInt("shipping_charge");
            }
            if(exploreitemList_product.size()>0){
                totalitemprice_textview.setText(exploreitemList_product.size()+"Items"+" -"+" Rs."+ String.valueOf(cart_total_price));
            }else {
                totalitemprice_textview.setVisibility(View.GONE);
            }
            mycartlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(check_shippinadress>1){
                        Intent in=new Intent(getApplicationContext(),CartContinueUpdateAddress.class);
                        in.putExtra("TOTALPRICE", String.valueOf(cart_total_price).toString());
                        startActivity(in);
                    }else {
                        Intent in=new Intent(getApplicationContext(),UpdateAddress.class);
                        startActivity(in);
                    }
                }
            });
            product_adapter.notifyDataSetChanged();
        }catch (JSONException e){

        }
    }
    class Exploreitem{
        String id,productname,image,heading,userid,shippingdate,ntype,nos,pricetypeid;
int price,quantity,shippingcharge,maxqty,wishlishcount,shippingcheckaddress;


        public int getShippingcheckaddress() {
            return shippingcheckaddress;
        }

        public void setShippingcheckaddress(int shippingcheckaddress) {
            this.shippingcheckaddress = shippingcheckaddress;
        }

        public int getWishlishcount() {
            return wishlishcount;
        }

        public void setWishlishcount(int wishlishcount) {
            this.wishlishcount = wishlishcount;
        }

        public String getPricetypeid() {
            return pricetypeid;
        }

        public void setPricetypeid(String pricetypeid) {
            this.pricetypeid = pricetypeid;
        }

        public String getNos() {
            return nos;
        }

        public void setNos(String nos) {
            this.nos = nos;
        }

        public int getMaxqty() {
            return maxqty;
        }

        public void setMaxqty(int maxqty) {
            this.maxqty = maxqty;
        }

        public int getShippingcharge() {
            return shippingcharge;
        }

        public void setShippingcharge(int shippingcharge) {
            this.shippingcharge = shippingcharge;
        }

        public String getNtype() {
            return ntype;
        }

        public void setNtype(String ntype) {
            this.ntype = ntype;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getShippingdate() {
            return shippingdate;
        }

        public void setShippingdate(String shippingdate) {
            this.shippingdate = shippingdate;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
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
        TextView product_name,product_quantity,product_deliverydate,product_shippingcharge,product_maxqty,product_change;
TextView product_icrementpus,product_minus,product_quantityvalue;
        TextView remove_product,addtowish_product;
        public UserViewHolder(View itemView) {
            super(itemView);
            product_image = (NetworkImageView) itemView.findViewById(R.id.explore_cartlist_image);
            product_name=(TextView)itemView.findViewById(R.id.explore_cartlist_title);
            product_quantity=(TextView)itemView.findViewById(R.id.explore_cartlist_weight);
            product_deliverydate=(TextView)itemView.findViewById(R.id.explore_cartlist_delivery);
            product_shippingcharge=(TextView)itemView.findViewById(R.id.explore_cartlist_shippingcharge);
            product_maxqty=(TextView)itemView.findViewById(R.id.explore_cartlist_maxqty);
            product_change=(TextView)itemView.findViewById(R.id.explore_cartlist_change);
            product_icrementpus=(TextView)itemView.findViewById(R.id.explore_cartlist_plus);
            product_minus=(TextView)itemView.findViewById(R.id.explore_cartlist_minus);
            product_quantityvalue=(TextView)itemView.findViewById(R.id.explore_cartlist_count);

            remove_product=(TextView)itemView.findViewById(R.id.remove_list);
            addtowish_product=(TextView)itemView.findViewById(R.id.remove_addtowishlist);


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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.explore_cart_feed, parent, false);
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
                userViewHolder.product_name.setText(itemmodel.getProductname());
                userViewHolder.product_name.setTypeface(seguiregular);
                userViewHolder.product_quantity.setTypeface(seguiregular);
                userViewHolder.product_deliverydate.setTypeface(seguiregular);
                userViewHolder.product_shippingcharge.setTypeface(seguiregular);
                userViewHolder.product_maxqty.setTypeface(seguiregular);
                userViewHolder.product_change.setTypeface(seguiregular);
                userViewHolder.product_icrementpus.setTypeface(seguiregular);
                userViewHolder.product_minus.setTypeface(seguiregular);
                userViewHolder.product_quantityvalue.setTypeface(seguiregular);
                userViewHolder.remove_product.setTypeface(seguiregular);
                userViewHolder.addtowish_product.setTypeface(seguiregular);
                userViewHolder.product_image.setImageUrl(itemmodel.getImage(), mImageLoader);

                userViewHolder.product_change.setText(Html.fromHtml("<u>" + "change" + "</u>"));
                if(itemmodel.getWishlishcount()==1){
                    userViewHolder.addtowish_product.setText("ADD TO WISHLIST");
                    userViewHolder.addtowish_product.setTextColor(Color.RED);
                }else {
                    userViewHolder.addtowish_product.setText("ADD TO WISHLIST");
                    userViewHolder.addtowish_product.setTextColor(Color.WHITE);
                }
                Calendar calendar = Calendar.getInstance();
                Date today = calendar.getTime();
int date_value= Integer.parseInt(itemmodel.getShippingdate());
                calendar.add(Calendar.DAY_OF_YEAR, date_value);
                Date tomorrow = calendar.getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("EE");

                String todayAsString = dateFormat.format(today);
                String tomorrowAsString = dateFormat.format(tomorrow);
                String days_name=sdf.format(tomorrow);
                System.out.println(todayAsString);
                System.out.println(tomorrowAsString);


                userViewHolder.remove_product.setText("REMOVE");
                userViewHolder.product_minus.setText("-");
                userViewHolder.product_icrementpus.setText("+");
                userViewHolder.product_quantity.setText(itemmodel.getNos()+itemmodel.getNtype()+"-"+"RS."+itemmodel.getPrice());
                userViewHolder.product_deliverydate.setText(Html.fromHtml("Delivery by"+"&nbsp;"+days_name+","+tomorrowAsString));
                userViewHolder.product_shippingcharge.setText("Shipping Charge:"+itemmodel.getShippingcharge());
                String qty= String.valueOf(itemmodel.getQuantity());
                userViewHolder.product_quantityvalue.setText(qty.toString());
                quantity_value=itemmodel.getQuantity();
                max_quantity_value=itemmodel.getMaxqty();
                price_values=itemmodel.getPrice();
                if(quantity_value==itemmodel.getMaxqty()) {
                    userViewHolder.product_maxqty.setText("Max qty is " + itemmodel.getMaxqty());
                }else {
                    userViewHolder.product_maxqty.setVisibility(View.GONE);
                }
userViewHolder.product_icrementpus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
           /* if(quantity_value>=itemmodel.getMaxqty()){
                quantity_value=quantity_value+1;

                userViewHolder.product_quantityvalue.setText(String.valueOf(quantity_value));
            }else {

            }*/



    if(quantity_value<max_quantity_value){
        quantity_value=quantity_value+1;
        int priceresult=price_values*quantity_value;
        userViewHolder.product_quantity.setText(itemmodel.getNos()+itemmodel.getNtype()+"-"+"RS."+priceresult);
        totalitemprice_textview.setText(exploreitemList_product.size()+"Items"+" -"+" Rs."+ String.valueOf(priceresult));

        userViewHolder.product_quantityvalue.setText(String.valueOf(quantity_value));
    }else {
        userViewHolder.product_maxqty.setVisibility(View.VISIBLE);
        userViewHolder.product_maxqty.setText("Max qty is " + itemmodel.getMaxqty());


    }


    }
});
                userViewHolder.product_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                      if(quantity_value>=2){
                          quantity_value=quantity_value-1;
                          userViewHolder.product_quantityvalue.setText(String.valueOf(quantity_value));
                          int priceresult=price_values*quantity_value;
                          userViewHolder.product_quantity.setText(itemmodel.getNos()+itemmodel.getNtype()+"-"+"RS."+priceresult);
                          totalitemprice_textview.setText(exploreitemList_product.size()+"Items"+" -"+" Rs."+ String.valueOf(priceresult));

                          // userViewHolder.product_quantityvalue.setText(String.valueOf(itemmodel.getQuantity()));
                          userViewHolder.product_maxqty.setVisibility(View.GONE);
                        }else {
                          userViewHolder.product_quantityvalue.setText(String.valueOf(itemmodel.getQuantity()));
                        }
                    }
                });
userViewHolder.product_change.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent detail=new Intent(getApplicationContext(),ProductDetailpage.class);
        detail.putExtra("ID",itemmodel.getId());
        startActivity(detail);
    }
});


userViewHolder.remove_product.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(myprofileid!=null) {

            StringRequest delete_request=new StringRequest(Request.Method.POST, URLREMOVE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("RES",response);
                    String res=response.toString();
                    res = res.replace(" ", "");
                    res = res.trim();
                    if(res.equalsIgnoreCase("delete")){
                        final int position = userViewHolder.getAdapterPosition();
                        onItemDismiss(position);
                        String val = cartcounts.toString();
                        Log.e("CART", val);
                        int values = 0;
                        if (val != null) {

                            try {
                                val = val.trim();
                                int valstring = Integer.parseInt(val);
                                Log.e("CART", val);
                                values = valstring - 1;
                            } catch (NumberFormatException e) {

                            }


                        } else {
                            //values = 1;
                        }


                        Log.e("CARTfinal", String.valueOf(values));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(CARTCOUNT, String.valueOf(values));
                        editor.commit();
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
                    String pidvalues=itemmodel.getId();
                    params.put("user_id", myprofileid);
                    params.put("id", pidvalues);
                    params.put("price_type",itemmodel.getPricetypeid());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            delete_request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            //Adding request to the queue
            requestQueue.add(delete_request);
        }else {
            Intent signin=new Intent(getApplicationContext(),SigninpageActivity.class);
            startActivity(signin);
            finish();
        }
    }
});
                userViewHolder.addtowish_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myprofileid!=null) {

                            StringRequest add_to_favourite_request=new StringRequest(Request.Method.POST, URLFAV, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("RES",response);
                                    String res=response.toString();
                                    res = res.replace(" ", "");
                                    res = res.trim();
                                    if(res.equalsIgnoreCase("added")){
                                        userViewHolder.addtowish_product.setTextColor(Color.RED);
                                    }else if(res.equalsIgnoreCase("removed")){
                                        userViewHolder.addtowish_product.setTextColor(Color.WHITE);
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
                              String pidvalues=itemmodel.getId();
                                    params.put("user_id", myprofileid);
                                    params.put("pid", pidvalues);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            add_to_favourite_request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            //Adding request to the queue
                            requestQueue.add(add_to_favourite_request);
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
        public void onItemDismiss(int position) {
            if(position!=-1 && position<exploreitemList_product.size())
            {

               exploreitemList_product.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        }

    }
}

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
import android.widget.ProgressBar;
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
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/15/2017.
 */
public class CartallshippingAddress extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts,my_profilename,my_profileimage;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RelativeLayout mainlayout;
    RequestQueue requestQueue;
    String URL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=viewall_address&user_id=";
    String URLFULL;
    RecyclerView explore_item_recyclerview;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;
    List<UPDATEITEM> exploreitemList_product=new ArrayList<>();
    RecylerviewAdapter product_adapter;
    TextView title_textview,back_textview,your_location_title_textview,change_address_tetview;
    RecyclerView.LayoutManager layoutManagers;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_cart_shippingall_address);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

        String simplycity_title_fontPath_1 = "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath_1);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        if (sharedpreferences.contains(USERNAME)) {

            my_profilename=sharedpreferences.getString(USERNAME,"");

        }
        if (sharedpreferences.contains(USERIMAGE)) {

            my_profileimage=sharedpreferences.getString(USERIMAGE,"");

        }
        URLFULL=URL+myprofileid;
        getData();
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
        title_textview=(TextView)findViewById(R.id.city_title);
        title_textview.setTypeface(tf);

        title_textview.setText("Update Address");
        your_location_title_textview=(TextView)findViewById(R.id.your_delivery_location_title);
        change_address_tetview=(TextView)findViewById(R.id.your_delivery_location_change);
        back_textview=(TextView)findViewById(R.id.explore_cartalladdress_back);
        back_textview.setTypeface(tf);
        your_location_title_textview.setTypeface(tf);
        change_address_tetview.setTypeface(tf);
        back_textview.setText("BACK");
       layoutManagers=new LinearLayoutManager(CartallshippingAddress.this);
        explore_item_recyclerview=(RecyclerView)findViewById(R.id.expolre_category_recylerview);
        explore_item_recyclerview.setLayoutManager(layoutManagers);
        explore_item_recyclerview.setNestedScrollingEnabled(false);
        your_location_title_textview.setText("Your Delivery LocationSelection");
        change_address_tetview.setText("ADD NEW");
        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        change_address_tetview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),UpdateAddress.class);
                startActivity(in);
            }
        });

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
                      //  getData();


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
        JsonObjectRequest edit_request=new JsonObjectRequest(Request.Method.GET, URLFULL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                  pDialog.dismiss();
                    JSONArray reslutarray=response.getJSONArray("result");
                    for (int i=0;i<reslutarray.length();i++){
                        UPDATEITEM updateitem=new UPDATEITEM();
                        JSONObject object=(JSONObject)reslutarray.get(i);
                        updateitem.setId(object.getString("id"));
                        updateitem.setUserid(object.getString("user_id"));
                        updateitem.setName(object.getString("name"));
                        updateitem.setAddressone(object.getString("address1"));
                        updateitem.setAddresstwo(object.getString("address2"));
                        updateitem.setPhone(object.getString("phone"));
                        updateitem.setLocation(object.getString("location"));
                        updateitem.setLanmark(object.getString("landmark"));
                        updateitem.setPincode(object.getString("pincode"));
                        updateitem.setPdate(object.getString("pdate"));

exploreitemList_product.add(updateitem);
                    }
                    product_adapter.notifyDataSetChanged();
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        edit_request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(edit_request);
    }
    public class UPDATEITEM{
        String id,name,userid,phone,addressone,addresstwo,location,lanmark,pincode,pdate;


        public String getLanmark() {
            return lanmark;
        }

        public void setLanmark(String lanmark) {
            this.lanmark = lanmark;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPdate() {
            return pdate;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAddressone() {
            return addressone;
        }

        public void setAddressone(String addressone) {
            this.addressone = addressone;
        }

        public String getAddresstwo() {
            return addresstwo;
        }

        public void setAddresstwo(String addresstwo) {
            this.addresstwo = addresstwo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
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
        TextView name_textview,shippingaddress_textview,shippingaddress_continue_textview;
        TextView edit_address,usethis_address;

        public UserViewHolder(View itemView) {
            super(itemView);

            name_textview=(TextView)itemView.findViewById(R.id.explore_user_delivery_name);
            shippingaddress_textview=(TextView)itemView.findViewById(R.id.explore_user_delivery_address);
            shippingaddress_continue_textview=(TextView)itemView.findViewById(R.id.explore_user_delivery_address_continue);
            edit_address=(TextView)itemView.findViewById(R.id.explore_cartalladdress_edit);
            usethis_address=(TextView)itemView.findViewById(R.id.explore_cartalladdress_usethis);



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

        public RecylerviewAdapter(List<UPDATEITEM> students, RecyclerView recyclerView) {
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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.explore_all_address_feed, parent, false);
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
                final UPDATEITEM itemmodel = exploreitemList_product.get(position);

               userViewHolder. name_textview.setText(itemmodel.getName());
             userViewHolder.   shippingaddress_textview.setText(itemmodel.getAddressone()+"\n"+itemmodel.getAddresstwo()+"\n"+itemmodel.getLocation());
             userViewHolder.   shippingaddress_continue_textview.setText("Coimbatore -"+itemmodel.getPincode()+"\n" +"LandMark:"+itemmodel.getLanmark()+"\n"+itemmodel.getPhone());

          userViewHolder.usethis_address.setText("USE THIS");
                userViewHolder.usethis_address.setTypeface(seguiregular);
                userViewHolder.edit_address.setTypeface(seguiregular);
             userViewHolder.edit_address.setText("EDIT");
                userViewHolder.edit_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getApplicationContext(),UpdateAddress.class);
                        in.putExtra("ID",itemmodel.getId());
                        startActivity(in);
                    }
                });
                userViewHolder.usethis_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getApplicationContext(),CartContinueUpdateAddress.class);
                        in.putExtra("ID",itemmodel.getId());
                        startActivity(in);
                    }
                });

            }else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
}}

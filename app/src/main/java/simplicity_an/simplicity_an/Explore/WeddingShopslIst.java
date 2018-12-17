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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by kuppusamy on 3/8/2017.
 */
public class WeddingShopslIst extends AppCompatActivity {
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
    String URLVENUE="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=wedding_shops&scat=";

    String URLALL,URL;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;
    List<Exploreitem> exploreitemList_product=new ArrayList<>();
    RecylerviewAdapter product_adapter;
    ImageButton back,happenning,search_button,entertainment,morepage;
    TextView explore_title_textview,coimbatore_title_textview,mycart_textview,buynow_textview,cartcount_textview;
    LinearLayout mycartlayout;
    String id_intent,title_intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_wedding_venue);
        Intent get=getIntent();
        title_intent=get.getStringExtra("TITLE");
        id_intent=get.getStringExtra("ID");

        URL=URLVENUE+id_intent;



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



        explore_title_textview.setTypeface(tf);
        explore_title_textview.setText(title_intent);


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

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
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


        URLALL=URL+"&page="+requestcount;
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
                items.setProductname(jsonObject.getString("title"));
               // items.setLocation(jsonObject.getString("location"));
                // items.setHeading(jsonObject.getString("heading"));
                exploreitemList_product.add(items);
            }
            product_adapter.notifyDataSetChanged();
        }catch (JSONException e){

        }
    }
    class Exploreitem{
        String id,productname,image,heading,location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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
        TextView product_name,product_location;
        LinearLayout explore_item_layout;
        public UserViewHolder(View itemView) {
            super(itemView);
            product_image = (NetworkImageView) itemView.findViewById(R.id.product_image);
            product_name=(TextView)itemView.findViewById(R.id.product_title);
            product_location=(TextView)itemView.findViewById(R.id.product_location);
            explore_item_layout=(LinearLayout)itemView.findViewById(R.id.jewelorgrocery_layout);
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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.explore_wedding_venue_feed, parent, false);
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
                userViewHolder.product_location.setTypeface(seguiregular);
                userViewHolder.product_location.setVisibility(View.GONE);
               /* if(itemmodel.getLocation()!=null||itemmodel.getLocation().equalsIgnoreCase("")){

                }else {
                    userViewHolder.product_location.setText(itemmodel.getLocation());
                }*/

                userViewHolder.product_image.setImageUrl(itemmodel.getImage(), mImageLoader);
                userViewHolder.explore_item_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent detail=new Intent(getApplicationContext(),WreddingShopsDetail.class);
                        detail.putExtra("TITLE",itemmodel.getProductname());
                        detail.putExtra("ID",itemmodel.getId());
                        detail.putExtra("CATID",id_intent);
                        startActivity(detail);
                    }
                });

            }else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
    }
}

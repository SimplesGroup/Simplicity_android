package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 7/23/2016.
 */
public class DirectoriesSubitemDetaillist extends AppCompatActivity {
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    String URL="http://simpli-city.in/request2.php?rtype=directory&key=simples&cat_id=";

    ProgressDialog pdialog;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerViewAdapter rcAdapter;
    String bimage;
    RecyclerView recycler;

    TextView Toolbartitle;
    ImageButton menu,back,addnewpost;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    String URLTWO,URLSEARCH;
    String URLtwo;
    SearchView search;
    String search_value,MainCatname,subcatname,catid,myprofileid;
    Typeface tf;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue queues;
RequestQueue requestQueue_directories;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.directoriessubdetailpage);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        queues = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        requestQueue_directories= Volley.newRequestQueue(this);
        Intent get = getIntent();
        URLtwo = get.getStringExtra("ID");
        MainCatname = get.getStringExtra("CATNAME");
        subcatname = get.getStringExtra("CATNAMESUB");
        catid = get.getStringExtra("CATID");
       // URLTWO = URL + catid + "&subcat_id=" + URLtwo;
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        back = (ImageButton) findViewById(R.id.btn_dirmainback);
        menu = (ImageButton) findViewById(R.id.btn_dirmainhome);
        addnewpost=(ImageButton)findViewById(R.id.btn_dirmainfav);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent back = new Intent(getApplicationContext(), Directories.class);
                startActivity(back);
                finish();*/
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(getApplicationContext(), MainPageEnglish.class);
                startActivity(menu);

            }
        });
        addnewpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null){
                    Intent addevent=new Intent(DirectoriesSubitemDetaillist.this,AddnewDirectories.class);
                    startActivity(addevent);


                }else {
                    Intent signin=new Intent(DirectoriesSubitemDetaillist.this,SigninpageActivity.class);
                    startActivity(signin);

                }
            }
        });
        Toolbartitle = (TextView) findViewById(R.id.toolbar_title_sub_detail_directory);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        Toolbartitle.setText(MainCatname + "-" + subcatname);
        Toolbartitle.setTypeface(tf);

        recycler = (RecyclerView) findViewById(R.id.directories_sub_detail_recyclerview);
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.smoothScrollToPosition(0);

        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        pdialog.setCanceledOnTouchOutside(false);
        getData();
        colorcodes=sharedpreferences.getString(backgroundcolor,"");

        mainlayout=(RelativeLayout)findViewById(R.id.version_main_layout);
        if(colorcodes.length()==0){
            int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);

            mainlayout.setBackgroundDrawable(gd);

            SharedPreferences.Editor editor = sharedpreferences.edit();
           editor.putString(backgroundcolor, "#262626");
            editor.commit();
        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi"+colorcodes);
                int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);

                SharedPreferences.Editor editor = sharedpreferences.edit();
               editor.putString(backgroundcolor, "#262626");
                editor.commit();
            }else {

                if(colorcodes!=null){
                    int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);
                }else {
                    int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                   editor.putString(backgroundcolor, "#262626");

                    editor.commit();
                }
            }
        }
       /* Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLTWO);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                   // dissmissDialog();
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLTWO, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                    if (response != null) {
                      //  dissmissDialog();
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppControllers.getInstance().addToRequestQueue(jsonReq);*/
            rcAdapter = new RecyclerViewAdapter(modelList, recycler);
            recycler.setAdapter(rcAdapter);
            rcAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    Log.e("haint", "Load More");
                    // modelList.add(null);
                    // adapt.notifyItemInserted(modelList.size() - 1);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("haint", "Load More 2");

                            //Remove loading item
                            // modelList.remove(modelList.size() - 1);
                            // rcAdapter.notifyItemRemoved(modelList.size());
                            getData();
                            //Load data
                            /*int index = modelList.size();
                            int end = index + 9;

                            try {

                                for (i = index; i < end; i++) {
                                    objtwo = (JSONObject) feedArray.get(i);
                                    ItemModel modelone = new ItemModel();

                                    modelone.setStreet(objtwo.getString("street"));
                                    modelone.setTitle(objtwo.getString("title"));
                                    modelone.setCity(objtwo.getString("city"));
                                    modelone.setState(objtwo.getString("state"));
                                    modelone.setCountry(objtwo.getString("country"));
                                    modelone.setPostcode(objtwo.getString("postcode"));
                                    modelone.setPhone(objtwo.getString("phone"));
                                    modelone.setFax(objtwo.getString("fax"));
                                    modelone.setLandline(objtwo.getString("landline"));
                                    modelone.setEmail(objtwo.getString("email"));
                                    modelone.setWebsite(objtwo.getString("website"));
                                    modelone.setLandmark(objtwo.getString("landmark"));

                                    modelList.add(modelone);

                                }
                                rcAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {

                            }*/

                            rcAdapter.setLoaded();
                        }
                    }, 2000);
                }
            });

        }

    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue_directories.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private
    JsonObjectRequest getDataFromTheServer( int requestCount) {

        URLTWO = URL + catid + "&subcat_id=" + URLtwo+"&page="+requestCount;

Log.e("URL",URLTWO);

        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLTWO);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    pdialog.dismiss();
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLTWO,  new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                    if (response != null) {
                        pdialog.dismiss();
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
                }
            });

            jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //AppControllers.getInstance().addToRequestQueue(jsonReq);
            requestQueue_directories.add(jsonReq);
        }
        return jsonReq;
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            feedArray = response.getJSONArray("result");

                for (ii = 0; ii < feedArray.length(); ii++) {
                    obj = (JSONObject) feedArray.get(ii);

                    ItemModel model = new ItemModel();
                    //FeedItem model=new FeedItem();
                    model.setStreet(obj.getString("street"));

                    model.setTitle(obj.getString("title"));
                    model.setCity(obj.getString("city"));
                    model.setState(obj.getString("state"));
                    model.setCountry(obj.getString("country"));
                    model.setPostcode(obj.getString("postcode"));
                    model.setPhone(obj.getString("phone"));
                    model.setFax(obj.getString("fax"));
                    model.setLandline(obj.getString("landline"));
                    model.setEmail(obj.getString("email"));
                    model.setWebsite(obj.getString("website"));
                    model.setLandmark(obj.getString("landmark"));
                    modelList.add(model);



                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();


            }
        }catch(JSONException e){
            e.printStackTrace();
        }

    }
    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }

    }
    class ItemModel {
        private int typeid;
        private String name;
        private String street;
        private String city;
        private String pdate;
        private String postcode;
        private String title;
        private String state;
        private String country;
        private String phone;
        private String industry_type;
        private String fax;
        private String website;
        private String email;
        private String landline;
        private String landmark;
        private String whours;
        /******** start the Food category names****/

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getEmail() {
            return email;
        }

        public String getFax() {
            return fax;
        }

        public String getIndustry_type() {
            return industry_type;
        }

        public String getLandline() {
            return landline;
        }

        public String getLandmark() {
            return landmark;
        }

        public String getPdate() {
            return pdate;
        }

        public String getPhone() {
            return phone;
        }

        public String getPostcode() {
            return postcode;
        }

        public String getState() {
            return state;
        }

        public String getTitle() {
            return title;
        }

        public String getWebsite() {
            return website;
        }

        public String getWhours() {
            return whours;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public void setIndustry_type(String industry_type) {
            this.industry_type = industry_type;
        }

        public void setLandline(String landline) {
            this.landline = landline;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public void setWhours(String whours) {
            this.whours = whours;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_LOADING = 2;
        private final int VIEW_TYPE_FEATURE = 0;
        private boolean loading;
        private OnLoadMoreListener onLoadMoreListener;

        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;
        public RecyclerViewAdapter(List<ItemModel> students, RecyclerView recyclerView) {
            modelList = students;

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
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(DirectoriesSubitemDetaillist.this).inflate(R.layout.feed_directories_list, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(DirectoriesSubitemDetaillist.this).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof UserViewHolder) {

                UserViewHolder userViewHolder = (UserViewHolder) holder;

                final ItemModel itemmodel = modelList.get(position);
                userViewHolder.title.setText(itemmodel.getTitle());


                userViewHolder.getdirection.setVisibility(View.GONE);
                userViewHolder.call.setVisibility(View.GONE);
                String statedestails=itemmodel.getState();
                String countrydestails=itemmodel.getCountry();
                String postdestails=itemmodel.getPostcode();
                final String phonenumber=itemmodel.getPhone();
                final String landlinenumber=itemmodel.getLandline();
                userViewHolder.state_country_post.setText(statedestails+","+countrydestails+"-"+postdestails);




                if(itemmodel.getStreet()!=null){
                    userViewHolder.street.setText(itemmodel.getStreet());
                }else {
                    userViewHolder.street.setVisibility(View.GONE);
                }
                if (itemmodel.getCity()!=null){
                    userViewHolder.city.setText(itemmodel.getCity());
                }else {
                    userViewHolder.city.setVisibility(View.GONE);
                }
                if (itemmodel.getPhone()!=null){
                    userViewHolder.phone.setText(phonenumber);
                }else {
                    userViewHolder.phone.setVisibility(View.GONE);
                }
                if (itemmodel.getLandline()!=null){
                    userViewHolder.landline.setText(landlinenumber);
                }else {
                    userViewHolder.landline.setVisibility(View.GONE);
                }
                if (itemmodel.getEmail()!=null){
                    userViewHolder.email.setText(itemmodel.getEmail());
                }else {
                    userViewHolder.email.setVisibility(View.GONE);
                }
                if(itemmodel.getWebsite()!=null){
                    userViewHolder.getdirection.setVisibility(View.VISIBLE);
                }else {
                    userViewHolder.getdirection.setVisibility(View.GONE);
                }


                if(phonenumber!=null||landlinenumber!=null){
                    userViewHolder.call.setVisibility(View.VISIBLE);

                }else {
                    userViewHolder.call.setVisibility(View.GONE);
                }
                userViewHolder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(phonenumber!=null){
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:"+phonenumber));
                                startActivity(callIntent);
                            }else {
                               if (landlinenumber!=null){
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:"+landlinenumber));
                                    startActivity(callIntent);

                                }

                            }
                        }catch (Exception e){

                        }
                    }
                });
userViewHolder.getdirection.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(itemmodel.getWebsite()));
        startActivity(i);
    }
});
            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }

        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }



        @Override
        public int getItemViewType(int position) {
            return modelList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }
        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList == null ? 0 : modelList.size();
        }

    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView street;
        public TextView city;
        public TextView state_country_post;
        public TextView phone;
        public TextView landline;
        public TextView email;
        public TextView fax;
        public TextView website;
        public TextView landmark;
        ImageButton call,getdirection;
        public UserViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_directories);
            street = (TextView) itemView.findViewById(R.id.street_directories);
            city = (TextView) itemView.findViewById(R.id.city_directories);
            state_country_post = (TextView) itemView.findViewById(R.id.state_contry_postcode_directories);
            phone = (TextView) itemView.findViewById(R.id.phone_directories);
            landline = (TextView) itemView.findViewById(R.id.landline_directories);

            email = (TextView) itemView.findViewById(R.id.email_directories);

            call=(ImageButton) itemView.findViewById(R.id.call);
            getdirection=(ImageButton) itemView.findViewById(R.id.getdirection);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }


    }


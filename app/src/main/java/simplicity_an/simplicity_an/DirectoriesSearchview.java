package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.NetworkInfo;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 7/23/2016.
 */
public class DirectoriesSearchview extends AppCompatActivity {
    List<ItemModel> modelList=new ArrayList<ItemModel>();

    ProgressDialog pdialog;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerViewAdapter rcAdapter;
    String bimage;
    RecyclerView recycler;
    TextView Toolbartitle;
    ImageButton menu,latestnews,citycenter,enternainment,moreoptions;
    LinearLayoutManager mLayoutManager;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    RequestQueue queue;

    Typeface tf;
    SearchView search;
    String search_value="",search_valueone="";
    String URLTWO,URLSEARCH;
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid;

    ImageButton notification;


    protected Handler handler;
    final String TAG_REQUEST = "MY_TAG";
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid;
    int value;
    String notification_counts;
    static NetworkInfo activeNetwork;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";

    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    public static final String MYACTIVITYSEARCH= "myactivitysearch";
    public static final String MYACTIVITYSEARCHVALUE= "myactivitysearchvalue";
    String searchvalue_event,searchvalue_event_value;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.directoriessearchview);

        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        searchvalue_event=sharedpreferences.getString(MYACTIVITYSEARCH,"");
        searchvalue_event_value=sharedpreferences.getString(MYACTIVITYSEARCHVALUE,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        url_notification_count_valueget=url_noti_count+myprofileid;
        URLPOSTQTYPE=urlpost;
        requestQueue=Volley.newRequestQueue(this);
        if(searchvalue_event.equalsIgnoreCase("dirsearch")){

            search_value=searchvalue_event_value;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove(MYACTIVITYSEARCH);
            editor.remove(MYACTIVITYSEARCHVALUE);
            editor.apply();
            Search();
        }

        notification=(ImageButton)findViewById(R.id.btn_5);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(DirectoriesSearchview.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(DirectoriesSearchview.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        menu=(ImageButton)findViewById(R.id.btn_home);
        queue= MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        Intent get=getIntent();
        search_valueone=get.getStringExtra("IDSEARCH");
        search_value = get.getStringExtra("QUERY");

        if(search_value!=null) {
            Search();

        }else {
            if(search_valueone!=null){
                Search();
            }
        }
        URLSEARCH="http://simpli-city.in/request2.php?rtype=directorysearch&key=simples&q="+search_value;
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homesearch = new Intent(DirectoriesSearchview.this, MainActivity.class);
                startActivity(homesearch);
                finish();
            }
        });

        String simplycity_title_fontPath = "fonts/Barkentina.otf";
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        Toolbartitle=(TextView)findViewById(R.id.toolbar_title_directories);
        Toolbartitle.setTypeface(tf);
        search=(SearchView)findViewById(R.id.search_simplicity_directories);
        Toolbartitle.setText("Simplicity");
        recycler = (RecyclerView)findViewById(R.id.searchview_simplicity_directories_recyclerview);
        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) search.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        magImage.setVisibility(View.GONE);
        int searchPlateId = search.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = search.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
           // searchPlate.setBottom(Color.WHITE);

            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
                searchText.setPadding(70,0,0,0);
                //searchText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

            /*    Toast.makeText(getBaseContext(), query,
                        Toast.LENGTH_SHORT).show();*/
                search_value = query;

                modelList.clear();
                Search();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                //	Toast.makeText(getBaseContext(), newText,
                ///Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recycler.setLayoutManager(new LinearLayoutManager(this));

        rcAdapter = new RecyclerViewAdapter(modelList,recycler);
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
Search();
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
    private void Uloaddataservernotify(){
        //final ProgressDialog loading = ProgressDialog.show(getApplicationContext(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLPOSTQTYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //  loading.dismiss();
                        //Showing toast message of the response
                        if(s.equalsIgnoreCase("error")) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show() ;
                        }else {


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                //Getting Image Name


                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                if(myprofileid!=null) {


                    params.put(KEY_QTYPE, "notify");
                    params.put(KEY_POSTID,"1");
                    params.put(KEY_MYUID, myprofileid);


                }
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private
    JsonObjectRequest getDataFromTheServer( int requestCount) {
        if(search_valueone!=null){
            URLSEARCH="http://simpli-city.in/request2.php?rtype=directorysearch&key=simples&q="+search_valueone+"&page="+requestCount;
        }else {
            URLSEARCH="http://simpli-city.in/request2.php?rtype=directorysearch&key=simples&q="+search_value+"&page="+requestCount;
        }



        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLSEARCH);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    dissmissDialog();
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
                    URLSEARCH,  new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                    if (response != null) {
                        dissmissDialog();
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
            requestQueue.add(jsonReq);
        }
        return jsonReq;
    }

    private void Search(){
        requestQueue.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;
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

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();


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
        String count;

        public void setCount(String count) {
            this.count = count;
        }

        public String getCount() {
            return count;
        }

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
                View view = LayoutInflater.from(DirectoriesSearchview.this).inflate(R.layout.feed_directories_list, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(DirectoriesSearchview.this).inflate(R.layout.layout_loading_item, parent, false);
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
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+phonenumber));
                                startActivity(callIntent);
                            }else {
                                if (landlinenumber!=null){
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
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

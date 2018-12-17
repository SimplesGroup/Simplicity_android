package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
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
 * Created by kuppusamy on 5/12/2016.
 */
public class Articlesearchview extends AppCompatActivity {
    private List<ItemModel> modelList=new ArrayList<ItemModel>();

    private ProgressDialog pdialog;
     CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
  CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerViewAdapter rcAdapter;
    String bimage;
    RecyclerView recycler;
    TextView Toolbartitle;
    ImageButton menu,back;
   LinearLayoutManager mLayoutManager;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    RequestQueue queue;
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    Typeface tf;
    SearchView search;
    String search_value;
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
    String notification_counts,searchvalue_article,searchvalue_article_value;
    static NetworkInfo activeNetwork;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String MYACTIVITYSEARCH= "myactivitysearch";
    public static final String MYACTIVITYSEARCHVALUE= "myactivitysearchvalue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.articlesearchview);
        notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        searchvalue_article=sharedpreferences.getString(MYACTIVITYSEARCH,"");
        searchvalue_article_value=sharedpreferences.getString(MYACTIVITYSEARCHVALUE,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");

        }
        url_notification_count_valueget=url_noti_count+myprofileid;
        URLPOSTQTYPE=urlpost;
            requestQueue=Volley.newRequestQueue(this);
        if(searchvalue_article.equalsIgnoreCase("articlesearch")) {
            search_value = searchvalue_article_value;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove(MYACTIVITYSEARCH);
            editor.remove(MYACTIVITYSEARCHVALUE);
            editor.apply();
            Search();
        }else {

        }
            if(myprofileid!=null) {

            JsonArrayRequest jsonReq = new JsonArrayRequest(url_notification_count_valueget, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    // TODO Auto-generated method stub


                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            ItemModel model = new ItemModel();
                            model.setCount(obj.getString("count"));
                            notification_counts = obj.getString("count");
                            Log.e("unrrad:", notification_counts);
                            value= Integer.parseInt(notification_counts);
                            if(value==0){
                                notification_batge_count.setVisibility(View.GONE);
                            }else {
                                notification_batge_count.setVisibility(View.VISIBLE);
                                notification_batge_count.setText(notification_counts);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    // VolleyLog.d(TAG, "ERROR" + error.getMessage());
                }
            });
            AppControllers.getInstance().addToRequestQueue(jsonReq);
        }else {

        }
        notification=(ImageButton)findViewById(R.id.btn_5);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(Articlesearchview.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(Articlesearchview.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        menu=(ImageButton)findViewById(R.id.btn_3);
        back=(ImageButton)findViewById(R.id.btn_1);
        queue= MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuarticlesearchview = new Intent(getApplicationContext(), MainPageEnglish.class);
                startActivity(menuarticlesearchview);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent backarticlesearchview = new Intent(getApplicationContext(), Articleoftheday.class);
                startActivity(backarticlesearchview);
                finish();*/
                onBackPressed();
            }
        });
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        Toolbartitle=(TextView)findViewById(R.id.toolbar_title_article);
        Toolbartitle.setTypeface(tf);
        search=(SearchView)findViewById(R.id.search_article);
        Toolbartitle.setText("Article");
        recycler = (RecyclerView)findViewById(R.id.searchview_article_recyclerview);
        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) search.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        magImage.setVisibility(View.GONE);
        int searchPlateId = search.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = search.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
            searchPlate.setBottom(Color.WHITE);

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

                        //Load data
                        Search();
                        /*int index = modelList.size();
                        int end = index + 9;

                        try {

                            for (i = index; i < end; i++) {
                                objtwo = (JSONObject) feedArray.get(i);
                                ItemModel modelone = new ItemModel();


                                String image = objtwo.isNull("image") ? null : objtwo
                                        .getString("image");
                                modelone.setImage(image);

                                // modelone.setTypeid(objtwo.getInt("type"));
                                modelone.setPdate(objtwo.getString("pdate"));
                                modelone.setTitle(objtwo.getString("title"));
                                modelone.setId(objtwo.getString("id"));


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

        recycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MYACTIVITYSEARCH, "articlesearch");
                editor.putString(MYACTIVITYSEARCHVALUE, search_value);
                editor.commit();
                Intent i = new Intent(getApplicationContext(), Articledescription.class);
                i.putExtra("ID", bid);
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();

                Intent i = new Intent(getApplicationContext(), Articledescription.class);
                i.putExtra("ID", bid);
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    private void Search(){
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        requestQueue.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;

        /*JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLSEARCH, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                if (response != null) {
                    dissmissDialog();
                    parseJsonFeedTwo(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonreq.setTag(TAG_REQUEST);
        ///jsonreq.add(jsonObjRequest);
        queue.add(jsonreq);*/
    }

    JsonObjectRequest getDataFromTheServer( int requestCount) {

        URLSEARCH="http://simpli-city.in/request2.php?rtype=search&key=simples&qtype=article&q="+search_value+"&page="+requestCount;


        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLSEARCH);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    dissmissDialog();
                    parseJsonFeedTwo(new JSONObject(data));
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
                        parseJsonFeedTwo(response);
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



    private void parseJsonFeedTwo(JSONObject response) {

        if (response != null) {
           // dissmissDialog();
            try {
                feedArray = response.getJSONArray("result");

                    for (ii = 0; ii < feedArray.length(); ii++) {
                        obj = (JSONObject) feedArray.get(ii);

                        ItemModel model = new ItemModel();
                        //FeedItem model=new FeedItem();
                        String image = obj.isNull("image") ? null : obj
                                .getString("image");
                        model.setImage(image);
                        // model.setTypeid(obj.getInt("type"));
                        model.setPdate(obj.getString("pdate"));
                        model.setTitle(obj.getString("title"));
                        model.setId(obj.getString("id"));


                        modelList.add(model);

                    }

                    // notify data changes to list adapater
                    rcAdapter.notifyDataSetChanged();

            }catch(JSONException e){
                e.printStackTrace();
            }

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
        private String image;
        private String bimage;
        private String pdate;
        private String description;
        private String title;
        private String id;
        private String company;
        /******** start the Food category names****/
        private String source;
        private String source_link;
        private String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public String getBimage() {
            return bimage;
        }

        public void setBimage(String bimage) {
            this.bimage = bimage;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPdate() {
            return pdate;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        /******** start the Food category names****/
        public String getSource_link() {
            return source_link;
        }

        public void setSource_link(String source_link) {
            this.source_link = source_link;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date;


        public NetworkImageView imageview;

        public UserViewHolder(View itemView) {
            super(itemView);


            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


            name = (TextView) itemView.findViewById(R.id.title_travel);
            date = (TextView) itemView.findViewById(R.id.date_simplicity);



        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        ImageLoader mImageLoader;
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
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            if(viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_simplicity, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;

        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof UserViewHolder) {

                final UserViewHolder userViewHolder = (UserViewHolder) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                if (mImageLoader == null)
                    mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();


                ItemModel itemmodel = modelList.get(position);



                userViewHolder.name.setText(itemmodel.getTitle());
                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.date.setText(itemmodel.getPdate());
                userViewHolder.imageview.setImageUrl(itemmodel.getImage(), mImageLoader);

            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }

        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }




        public int getItemViewType(int position) {


            return modelList.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

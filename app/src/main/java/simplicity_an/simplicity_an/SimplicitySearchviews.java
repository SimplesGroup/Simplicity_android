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
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.Tamil.Activity.DoitDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.Farmingdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.Govtdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.Healthdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.ScienceandTechnologyDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.TamilEventsDescription;
import simplicity_an.simplicity_an.Tamil.Activity.TamilNewsDescription;
import simplicity_an.simplicity_an.Tamil.Activity.TamilSportsnewsDescription;
import simplicity_an.simplicity_an.Tamil.Activity.TipsDescriptionTamil;
import simplicity_an.simplicity_an.Tamil.Activity.TravelsDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.TamilArticledescription;

/**
 * Created by KuppuSamy on 8/22/2017.
 */

public class SimplicitySearchviews extends AppCompatActivity {
    List<ItemModel> modelListsearch=new ArrayList<ItemModel>();

    ProgressDialog pdialog;

    RecyclerViewAdapter recycler_search_Adapter;
    String bimage;
    RecyclerView recycler;
    TextView Toolbartitle;
    ImageButton search_button,events_button,city_button,enternainment,settings;
    LinearLayoutManager mLayoutManager;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    RequestQueue queue;
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    private final String TAG_REQUEST = "MY_TAG";
    Typeface tf;
    android.support.v7.widget.SearchView search;
    String search_value="",search_valueone="";
    String URLTWO,URLSEARCH;

    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid;

    ImageButton notification;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.simplicitysearchview);
        requestQueue= Volley.newRequestQueue(this);
       /* notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);*/
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
     /*   url_notification_count_valueget=url_noti_count+myprofileid;*/
        URLPOSTQTYPE=urlpost;


        notification=(ImageButton)findViewById(R.id.btn_versiontwonotifications);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page = new Intent(getApplicationContext(), MainPageTamil.class);
                    notification_page.putExtra("ID","1");
                    startActivity(notification_page);
                }else {
                    Intent notification_page = new Intent(getApplicationContext(), MainPageTamil.class);
                    notification_page.putExtra("ID","1");
                    startActivity(notification_page);
                }
            }
        });

        search_button=(ImageButton)findViewById(R.id.btn_versiontwosearch);
        events_button=(ImageButton)findViewById(R.id.btn_versiontwobeyond);
        enternainment=(ImageButton)findViewById(R.id.btn_versiontwoexplore) ;
        city_button=(ImageButton)findViewById(R.id.btn_versiontwocity);
        city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        enternainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","2");
                startActivity(entairnment);
            }
        });


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


        events_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event_page = new Intent(getApplicationContext(), MainPageTamil.class);
                event_page.putExtra("ID","3");
                startActivity(event_page);

            }
        });
        String simplycity_title_fontPath = "fonts/Barkentina.otf";
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        Toolbartitle=(TextView)findViewById(R.id.toolbar_title_simplicity);
        Toolbartitle.setTypeface(tf);
        search=(android.support.v7.widget.SearchView)findViewById(R.id.search);
        search.setActivated(true);
        search.setQueryHint("Search for anything in Coimbatore");
        search.onActionViewExpanded();
        search.setIconified(false);
        search.clearFocus();
        setupSearchView();
        Toolbartitle.setText("Simplicity");
        recycler = (RecyclerView)findViewById(R.id.searchview_simplicity_recyclerview);


        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub


                search_value = query;

                modelListsearch.clear();
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


        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setNestedScrollingEnabled(false);
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        recycler_search_Adapter = new RecyclerViewAdapter( modelListsearch,recycler);
        recycler.setAdapter(recycler_search_Adapter);
        recycler_search_Adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        Search();


                        recycler_search_Adapter.setLoaded();
                    }
                }, 2000);
            }
        });
        recycler.addOnItemTouchListener(new SimplicitySearchview.RecyclerTouchListener(getApplicationContext(), recycler, new SimplicitySearchview.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String bid = ((ItemModel)  modelListsearch.get(position)).getId();
                String type= ((ItemModel)  modelListsearch.get(position)).getQtype();
                if(type.equals("news")) {
                    Intent i = new Intent(getApplicationContext(), TamilNewsDescription.class);
                    i.putExtra("ID", bid);
                    i.putExtra("IDSEARCH",search_value);

                    startActivity(i);
                }else if(type.equals("article")){
                    Intent i = new Intent(getApplicationContext(), TamilArticledescription.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("govt")){
                    Intent i = new Intent(getApplicationContext(), Govtdescriptiontamil.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("events")||type.equals("event")){
                    Intent i = new Intent(getApplicationContext(), TamilEventsDescription.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("sports")){
                    Intent i = new Intent(getApplicationContext(), TamilSportsnewsDescription.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("science")){
                    Intent i = new Intent(getApplicationContext(), ScienceandTechnologyDescriptiontamil.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("health")){
                    Intent i = new Intent(getApplicationContext(), Healthdescriptiontamil.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("farming")){
                    Intent i = new Intent(getApplicationContext(), Farmingdescriptiontamil.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else  if(type.equals("doit")){
                    Intent i = new Intent(getApplicationContext(), DoitDescriptiontamil.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("foodtip")){
                    Intent i = new Intent(getApplicationContext(), TipsDescriptionTamil.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("travels")){
                    Intent i = new Intent(getApplicationContext(), TravelsDescriptiontamil.class);
                    i.putExtra("ID", bid);
                    startActivity(i);
                }else if(type.equals("theatre")){
                    String video = ((ItemModel)  modelListsearch.get(position)).getImage();
                    Intent i = new Intent(getApplicationContext(), YoutubeVideoPlayer.class);
                    i.putExtra("URL", video);
                    startActivity(i);
                }else {

                }

            }

            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupSearchView()
    {
        // search hint


        // background
        View searchPlate = search.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundColor(Color.TRANSPARENT);

        // icon
        ImageView searchIcon = (ImageView) search.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(android.R.drawable.ic_menu_search);

        // clear button
        ImageView searchClose = (ImageView) search.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        //searchClose.setImageResource(android.R.drawable.ic_menu_delete);

        // text color
        AutoCompleteTextView searchText = (AutoCompleteTextView) search.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(getResources().getColor(R.color.white));
        searchText.setHintTextColor(getResources().getColor(R.color.white));
    }

    private void Search(){
        requestQueue.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;



    }
    private
    JsonObjectRequest getDataFromTheServer( int requestCount) {
        if(search_valueone!=null){
            URLSEARCH="http://simpli-city.in/request2.php?rtype=searchall&key=simples&q="+search_valueone+"&page="+requestCount;
        }else {
            URLSEARCH="http://simpli-city.in/request2.php?rtype=searchall&key=simples&q="+search_value+"&page="+requestCount;
        }



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
            //  requestQueue.add(jsonReq);
        }
        return jsonReq;
    }


    private void parseJsonFeedTwo(JSONObject response) {


        try {
            Log.e("SEARCH",response.toString());
            JSONArray  feedArray = response.getJSONArray("result");

            for (i = 0; i < feedArray.length(); i++) {
                JSONObject   obj = (JSONObject) feedArray.get(i);

               ItemModel model = new ItemModel();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);
                model.setQtype(obj.getString("qtype"));
                // model.setTypeid(obj.getInt("type"));
                model.setPdate(obj.getString("pdate"));
                model.setTitle(obj.getString("title"));
                model.setId(obj.getString("id"));


                modelListsearch.add(model);
                Log.e("SEARCH",obj.getString("title"));
            }

            // notify data changes to list adapater
            recycler_search_Adapter.notifyDataSetChanged();

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    class ItemModel{
        private int typeid;
        private String name;
        private String image;

        private String pdate;
        private String description;
        private String title;

        /******** start the Food category names****/
        private String id;
        private String qtype;
        private String count;
        private String video;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }


        public String getQtype() {
            return qtype;
        }

        public void setQtype(String qtype) {
            this.qtype = qtype;
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

        public String getImage() {
            return image;
        }
        public void setImage(String image) {
            this.image = image;
        }
        public String getDescription(){return description;}
        public  void setDescription(String description){
            this.description=description;
        }
        public String getPdate(){return  pdate;}

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }
        public String getTitle(){return  title;}

        public void setTitle(String title) {
            this.title = title;
        }

        /******** start the Food category names****/
        public String getId(){return  id;}

        public void setId(String id) {
            this.id = id;
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date;


        public ImageView imageview;

        public UserViewHolder(View itemView) {
            super(itemView);


            imageview = (ImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


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

        private boolean loading;
        private OnLoadMoreListener onLoadMoreListener;
        private  int currentvisiblecount;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;

        public RecyclerViewAdapter(List<ItemModel> students, RecyclerView recyclerView) {
            modelListsearch = students;

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
                                currentvisiblecount=linearLayoutManager.findLastVisibleItemPosition();
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
        public int getItemCount() {
            return  modelListsearch.size();
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_searchview, parent, false);
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

                String simplycity_title_fontPaths = "fonts/Lora-Regular.ttf";;
                Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPaths);
                if (mImageLoader == null)
                    mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();

                final ItemModel itemmodel =  modelListsearch.get(position);



                userViewHolder.name.setText(itemmodel.getTitle());
                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.date.setText(itemmodel.getQtype()+"|"+itemmodel.getPdate());

                Context context=userViewHolder.imageview.getContext();
                if (itemmodel.getImage() != null) {

                    Picasso.with(context).load(itemmodel.getImage()).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(userViewHolder.imageview);
                    //userViewHolder.profileimage.setImageUrl(URLDecoder.decode(itemmodel.getProfilepic()), mImageLoader);

                } else {
                    //userViewHolder.item_image.setVisibility(View.GONE);
                }

            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }

        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }




        public int getItemViewType(int position) {


            return modelListsearch.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

        }
        public void setLoaded() {
            loading = false;
        }


    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SimplicitySearchview.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SimplicitySearchview.ClickListener clickListener) {
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

package simplicity_an.simplicity_an.Tamil.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.AppControllers;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;


/**
 * Created by kuppusamy on 4/5/2016.
 */
public class RadioActivitytamil extends AppCompatActivity {
    LinearLayoutManager mLinearLayoutManager;


    private String KEY_POSTID = "rid";
    private String KEY_MYID = "user_id";
    String URLFAV="http://simpli-city.in/request2.php?rtype=radiofav&key=simples";
    int favs,fav_list_count;
    ImageButton back,menu,notification,fav_playlist;
    TextView texttitle;
    // Need this to link with the Snackbar
   CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LinearLayoutManager lLayout;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=radio&key=simples";
    private ProgressDialog pdialog;
    //Need this to set the title of the app bar


    RecyclerView recycler;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    String myprofileid,URLTWO;

    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i,myuserinteger;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTIDNoTIFy = "id";
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid;
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget;
    int value;
    String notification_counts;
    private ViewPager mPager;
    private HealthViewPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.radioactivity);
        notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {
            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
            Log.e("MUUSERID:",myprofileid);

        }
        url_notification_count_valueget=url_noti_count+myprofileid;
        URLPOSTQTYPE=urlpost;
        if(myprofileid!=null){
            URLTWO=URL+"&user_id="+myprofileid;
            myprofileid = myprofileid.replaceAll("\\D+","");

            myuserinteger = Integer.parseInt(myprofileid);
        }else {
            URLTWO=URL;
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
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        back=(ImageButton)findViewById(R.id.btn_radioback);
        menu=(ImageButton)findViewById(R.id.btn_radiohome);
        fav_playlist=(ImageButton)findViewById(R.id.btn_radiofav);
                texttitle=(TextView)findViewById(R.id.toolbar_title);
        texttitle.setTypeface(tf);
        texttitle.setText("Radio shows");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent backradio = new Intent(getApplicationContext(), MainPageTamil.class);
                startActivity(backradio);
                finish();*/
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent radiomenu = new Intent(getApplicationContext(), MainPageTamil.class);
                startActivity(radiomenu);
                finish();
            }
        });
        fav_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null){
                    Intent radiofavplaylist=new Intent(RadioActivitytamil.this,RadioPlaylisttamil.class);
                    startActivity(radiofavplaylist);
                    finish();
                }else {
                    Intent signin=new Intent(RadioActivitytamil.this,SigninpageActivity.class);
                    startActivity(signin);
                    finish();
                }
            }
        });
        notification=(ImageButton)findViewById(R.id.btn_radioprofile) ;
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(RadioActivitytamil.this,MainPageTamil.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(RadioActivitytamil.this,MainPageTamil.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mAdapter = new HealthViewPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        //mPager.setAdapter(mAdapter);
        setupViewPager(mPager);
        //Notice how the Tab Layout links with the Pager Adapter
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        //Notice how The Tab Layout adn View Pager object are linked
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

       /* recycler.addOnItemTouchListener(new RadioActivitytamil.RecyclerTouchListener(getApplicationContext(), recycler, new RadioActivitytamil.ClickListener() {
            public void onClick(View view, int position) {

                String bitmap = ((ItemModel) modelList.get(position)).getImage();
                String Link= ((ItemModel) modelList.get(position)).getLink();
                String title= ((ItemModel) modelList.get(position)).getTitle();
                String name= ((ItemModel) modelList.get(position)).getName();
                String part= ((ItemModel) modelList.get(position)).getPart();
                String date= ((ItemModel) modelList.get(position)).getPdate();
                String id= ((ItemModel) modelList.get(position)).getId();
                int favid= ((ItemModel) modelList.get(position)).getFavourite();


                postid = ((ItemModel)modelList.get(position)).getId();

                if(myprofileid!=null){
                    Uloaddataserver();
                    Intent fooddescription = new Intent(getApplicationContext(), Radioplayeractivitytamil.class);
                    fooddescription.putExtra("ID", id);
                    fooddescription.putExtra("IMAGE", bitmap);
                    fooddescription.putExtra("LINK", Link);
                    fooddescription.putExtra("TITLE", title);
                    fooddescription.putExtra("NAME", name);
                    fooddescription.putExtra("PART", part);
                    fooddescription.putExtra("DATE", date);

                    fooddescription.putExtra("FAV", favid);
                    startActivity(fooddescription);
                }else {
                    Intent fooddescription = new Intent(getApplicationContext(), Radioplayeractivitytamil.class);
                    fooddescription.putExtra("ID", id);
                    fooddescription.putExtra("IMAGE", bitmap);
                    fooddescription.putExtra("LINK", Link);
                    fooddescription.putExtra("TITLE", title);
                    fooddescription.putExtra("NAME", name);
                    fooddescription.putExtra("PART", part);
                    fooddescription.putExtra("DATE", date);

                    fooddescription.putExtra("FAV", favid);
                    startActivity(fooddescription);
                }
            }

            public void onLongClick(View view, int position) {

            }
        }));*/



       /* Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLTWO);
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
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLTWO,  new Response.Listener<JSONObject>() {

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

            // Adding request to volley request queue
            AppControllers.getInstance().addToRequestQueue(jsonReq);
        }

        rcAdapter = new RecyclerViewAdapter(modelList,recycler);
        recycler.setAdapter(rcAdapter);
        rcAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");

                        int index = modelList.size();
                        int end = index + 9;

                        try {

                            for (i = index; i < end; i++) {
                                objtwo = (JSONObject) feedArray.get(i);
                                ItemModel modelone = new ItemModel();


                                String image = objtwo.isNull("thumb") ? null : objtwo
                                        .getString("thumb");
                                modelone.setImage(image);

                                modelone.setId(objtwo.getString("id"));
                                modelone.setPdate(objtwo.getString("pdate"));
                                modelone.setTitle(objtwo.getString("title"));
                                String descriptions = objtwo.isNull("description") ? null : objtwo
                                        .getString("description");
                                modelone.setDescription(descriptions);
                                modelone.setLink(objtwo.getString("link"));
                                modelone.setName(objtwo.getString("name"));
                                modelone.setPart(objtwo.getString("part"));
                                modelone.setCatid(objtwo.getString("cat_id"));
                                modelone.setCatnamme(objtwo.getString("category_name"));
                                int  favs = objtwo.isNull("fav") ? null : objtwo
                                        .getInt("fav");
                                modelone.setFavourite(favs);

                                modelList.add(modelone);
                            }
                            rcAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                        }

                        rcAdapter.setLoaded();
                    }
                }, 2000);
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    private void Uloaddataserver(){
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
                    if (postid != null) {

                        params.put(KEY_QTYPE, "radio");
                        params.put(KEY_POSTIDNoTIFy, postid);
                        params.put(KEY_MYUID, myprofileid);
                    }

                }
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private void setupViewPager(ViewPager mPager) {
        HealthViewPagerAdapter adapter = new HealthViewPagerAdapter(getSupportFragmentManager());
        // adapter.addFragment(new FragmentTips(), "Tips");
        adapter.addFragment(new RadioShowstamil(), "Shows");
        adapter.addFragment(new RadioNewstamil(), "News");
        adapter.addFragment(new RadioCitytamil(), "Radio City");
        mPager.setAdapter(adapter);
    }
    class HealthViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public HealthViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
    /*private  void parseJsonFeed(JSONObject response){
        try {
            feedArray = response.getJSONArray("result");
            if (feedArray.length() < 10) {
                for (ii = 0; ii < feedArray.length(); ii++) {
                    obj = (JSONObject) feedArray.get(ii);

                    ItemModel model = new ItemModel();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("thumb") ? null : obj
                            .getString("thumb");
                    model.setImage(image);

                    model.setId(obj.getString("id"));
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("title"));
                    // model.setDescription(obj.getString("description"));
                    model.setLink(obj.getString("link"));
                    model.setName(obj.getString("name"));
                    model.setPart(obj.getString("part"));
                    model.setCatid(obj.getString("cat_id"));
                    model.setCatnamme(obj.getString("category_name"));
                    String descriptions = obj.isNull("description") ? null : obj
                            .getString("description");
                    model.setDescription(descriptions);
                    int  favs = obj.isNull("fav") ? null : obj
                            .getInt("fav");
                    model.setFavourite(favs);
                    modelList.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();
            }else {
                for (ii = 0; ii <= 10; ii++) {
                    obj = (JSONObject) feedArray.get(ii);


                    ItemModel model = new ItemModel();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("thumb") ? null : obj
                            .getString("thumb");
                    model.setImage(image);

                    model.setId(obj.getString("id"));
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("title"));
                    // model.setDescription(obj.getString("description"));
                    model.setLink(obj.getString("link"));
                    model.setName(obj.getString("name"));
                    model.setPart(obj.getString("part"));
                    model.setCatid(obj.getString("cat_id"));
                    model.setCatnamme(obj.getString("category_name"));
                    String descriptions = obj.isNull("description") ? null : obj
                            .getString("description");
                    model.setDescription(descriptions);
                    int  favs = obj.isNull("fav") ? null : obj
                            .getInt("fav");
                    model.setFavourite(favs);

                    modelList.add(model);
                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
    class ItemModel{
        private int typeid;
        private String name;
        private String image;

        private String pdate;
        private String description;
        private String title;
        private String link;
        private String part;
        /******** start the Food category names****/
        private String id;
        private String catid;
        private String catnamme;
        private  int favourite;
        String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
        public int getFavourite() {
            return favourite;
        }

        public void setFavourite(int favourite) {
            this.favourite = favourite;
        }

        public String getCatnamme() {
            return catnamme;
        }

        public void setCatnamme(String catnamme) {
            this.catnamme = catnamme;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getPart() {
            return part;
        }

        public void setPart(String part) {
            this.part = part;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
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
   /* static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView title,date,description;
        public NetworkImageView imageview;
            ImageButton favourite;
        public UserViewHolder(View itemView) {
            super(itemView);



            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


            title = (TextView) itemView.findViewById(R.id.title_radio);
            date = (TextView) itemView.findViewById(R.id.date_radio);
            description = (TextView) itemView.findViewById(R.id.desc_radio);
            favourite=(ImageButton) itemView.findViewById(R.id.addfav_button);

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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_radio, parent, false);
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
                    mImageLoader =  MySingleton.getInstance(getApplicationContext()).getImageLoader();


                final ItemModel itemmodel = modelList.get(position);



                userViewHolder.title.setText(itemmodel.getTitle());
                userViewHolder.title.setTypeface(seguiregular);
                userViewHolder.date.setText(itemmodel.getPdate() );
                userViewHolder.date.setTypeface(seguiregular);
                //  userViewHolder.description.setText(Html.fromHtml(itemmodel.getDescription()));
                // userViewHolder.description.setText(itemmodel.getDescription());
                String simplicity="SimpliCity";
                userViewHolder.description.setTypeface(seguiregular);
                userViewHolder.description.setText(Html.fromHtml("By&nbsp;"+simplicity));
                userViewHolder.imageview.setDefaultImageResId(R.mipmap.ic_launcher);
                userViewHolder.imageview.setErrorImageResId(R.mipmap.ic_launcher);
                userViewHolder.imageview.setAdjustViewBounds(true);
                userViewHolder.imageview.setImageUrl(itemmodel.getImage(), mImageLoader);
                if(favs==1){

                    userViewHolder.favourite.setImageResource(R.drawable.greyhandfriends);
                }else {
                    userViewHolder.favourite.setImageResource(R.drawable.handaddfriend);
                }


                fav_list_count=itemmodel.getFavourite();



                userViewHolder.favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myprofileid != null) {
                            if (fav_list_count == 1) {
                                userViewHolder.favourite.setImageResource(R.drawable.handaddfriend);
                                fav_list_count--;


                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLFAV,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                //Disimissing the progress dialog

                                                //Showing toast message of the response
                                                if (s.equalsIgnoreCase("no")) {
                                                    //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                } else {
                                                    Log.e("response:", s);

                                                }

                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                //Dismissing the progress dialog
                                                //loading.dismiss();

                                                //Showing toast
                                                //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        Map<String, String> params = new Hashtable<String, String>();
                                        String friendid = itemmodel.getId();
                                        //Adding parameters
                                        if (friendid != null) {


                                            params.put(KEY_POSTID, friendid);
                                            params.put(KEY_MYID, myprofileid);
                                        } else {


                                        }


                                        return params;
                                    }
                                };

                                //Creating a Request Queue
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                //Adding request to the queue
                                requestQueue.add(stringRequest);

                            } else {
                                userViewHolder.favourite.setImageResource(R.drawable.greyhandfriends);
                                fav_list_count++;

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLFAV,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                //Disimissing the progress dialog

                                                //Showing toast message of the response
                                                if (s.equalsIgnoreCase("no")) {
                                                    //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                } else {
                                                    Log.e("response:", s);

                                                }

                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                //Dismissing the progress dialog
                                                //loading.dismiss();

                                                //Showing toast
                                                //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        //Converting Bitmap to String

                                        //Getting Image Name

                                        //Creating parameters
                                        Map<String, String> params = new Hashtable<String, String>();
                                          String friendid = itemmodel.getId();;
                                        //Adding parameters
                                        if (friendid != null) {


                                            params.put(KEY_POSTID, friendid);
                                            params.put(KEY_MYID, myprofileid);
                                        } else {


                                        }


                                        return params;
                                    }
                                };

                                //Creating a Request Queue
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                //Adding request to the queue
                                requestQueue.add(stringRequest);
                                //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Intent in=new Intent(RadioActivitytamil.this,SigninpageActivity.class);
                            startActivity(in);
                        }
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



        *//*public int getItemViewType(int position) {
            if (isPositionHeader(position)) {
                return VIEW_TYPE_FEATURE;
            } else if (isPositionFooter(position)) {
                return VIEW_TYPE_LOADING;
            }
            return VIEW_TYPE_ITEM;
        }*//*
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
    }*/

    }

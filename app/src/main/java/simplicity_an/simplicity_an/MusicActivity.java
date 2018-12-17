package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
 * Created by kuppusamy on 5/5/2016.
 */
public class MusicActivity extends AppCompatActivity {
     List<ItemModel> modelList=new ArrayList<ItemModel>();
    List<ItemModel> musiclistall=new ArrayList<ItemModel>();
     ProgressDialog pdialog;
     String URL="http://simpli-city.in/request2.php?rtype=music-release&key=simples";
    ArrayList<String> musiclist = new ArrayList<String>();
     CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
String URLTWO="http://simpli-city.in/request2.php?rtype=music&key=simples";
    String URLTHREE;
  TextView title_newrealease,title_music;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i,myuserinteger;
    RequestQueue queues;
    RecyclerView music_newreleases,musicall;
   final String TAG_REQUEST = "MY_TAG";
    RecyclerViewAdapter rcadap;
    RecyclerViewAdapterArticles rcadapall;
    ImageButton backmusic,menumusic,searchmusic,favmusic;
    RecyclerView.LayoutManager mLayoutManager;
    int favs,fav_list_count;
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    private String KEY_POSTIDs = "mid";
    private String KEY_MYID = "user_id";
    String URLFAV="http://simpli-city.in/request2.php?rtype=musicfav&key=simples";
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid;
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid;
    int value;
    String notification_counts;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";

    ImageButton notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.musicactivityxml);
        notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        requestQueue = Volley.newRequestQueue(this);

        url_notification_count_valueget=url_noti_count+myprofileid;
        URLPOSTQTYPE=urlpost;
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
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


        String fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), fontPath);
        queues = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        title_music=(TextView)findViewById(R.id.toolbar_title_music);
        title_newrealease=(TextView)findViewById(R.id.title_newrealease) ;
        title_newrealease.setText("New Releases");
        title_newrealease.setTypeface(tf);
        title_music.setTypeface(tf);
        title_music.setText("Music");
        backmusic=(ImageButton)findViewById(R.id.btn_musicback);
        menumusic=(ImageButton)findViewById(R.id.btn_musichome);
        searchmusic=(ImageButton)findViewById(R.id.btn_musicsearch);
        favmusic=(ImageButton)findViewById(R.id.btn_musicfav);
        favmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null){
                    Intent backmusic_intent=new Intent(MusicActivity.this,Musicfavlist.class);
                    startActivity(backmusic_intent);
                    finish();
                }else {
                    Intent backmusic=new Intent(MusicActivity.this,SigninpageActivity.class);
                    startActivity(backmusic);
                    finish();
                }

            }
        });
        backmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent backmusic_intent=new Intent(MusicActivity.this,MainActivityVersiontwo.class);
                startActivity(backmusic_intent);
                finish();*/
                onBackPressed();
            }
        });
        menumusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menumusic_intent=new Intent(MusicActivity.this, MainPageEnglish.class);
                startActivity(menumusic_intent);
                finish();
            }
        });
        notification=(ImageButton)findViewById(R.id.btn_musicprofile) ;
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(MusicActivity.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(MusicActivity.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        pdialog.setCanceledOnTouchOutside(false);
        music_newreleases=(RecyclerView)findViewById(R.id.my_recycler_newrelease);
        music_newreleases.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        music_newreleases.setLayoutManager(mLayoutManager);
        musicall=(RecyclerView)findViewById(R.id.musicshow_recyclerview);
        musicall.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
       musicall.setHasFixedSize(true);

       musicall.setLayoutManager(new LinearLayoutManager(this));

        getData();


        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {


                if (response != null) {
dissmissDialog();
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppControllers.getInstance().addToRequestQueue(jsonreq);
        // Adding request to volley request queue
        //requestQueue.add(jsonReq);
        rcadap = new RecyclerViewAdapter(this, modelList);
        music_newreleases.setAdapter(rcadap);

        rcadapall = new RecyclerViewAdapterArticles(musiclistall,musicall);
        musicall.setAdapter(rcadapall);
        rcadapall.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        getData();
                      /*  int index = musiclistall.size();
                        int end = index + 9;

                        try {

                            for (i = index; i < end; i++) {
                                objtwo = (JSONObject) feedArray.get(i);
                                ItemModel model = new ItemModel();
                                //FeedItem model=new FeedItem();
                                String image = obj.isNull("thumb") ? null : obj
                                        .getString("thumb");
                                model.setImage(image);

                                String id_city = obj.isNull("id") ? null : obj
                                        .getString("id");
                                model.setId(id_city);
                                String date_city = obj.isNull("pdate") ? null : obj
                                        .getString("pdate");
                                model.setPdate(date_city);
                                String title_city = obj.isNull("title") ? null : obj
                                        .getString("title");
                                model.setTitle(title_city);
                                String musicdirector= obj.isNull("music_director") ? null : obj
                                        .getString("music_director");
                                model.setMusicdirector(musicdirector);
                                String musiccategory= obj.isNull("music_category") ? null : obj
                                        .getString("music_category");
                                model.setMusiccategory(musiccategory);
                                String musiclanguage= obj.isNull("language") ? null : obj
                                        .getString("language");
                                model.setLanguage(musiclanguage);
                                String musiclink= obj.isNull("link") ? null : obj
                                        .getString("link");
                                model.setLink(musiclink);
                                musiclistall.add(model);
                            }
                            rcadapall.notifyDataSetChanged();

                        } catch (JSONException e) {

                        }*/

                        rcadapall.setLoaded();
                    }
                }, 2000);
            }
        });

        musicall.addOnItemTouchListener(new RecyclerTouchListener(this, musicall, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                int  post = ((ItemModel)musiclistall.get(position)).getType();
                String bid = ((ItemModel)musiclistall.get(position)).getAdurl();
                String id = ((ItemModel)musiclistall.get(position)).getId();
                String ids= String.valueOf(post);
                Log.e("Log:",ids);
                Log.e("Log:",bid);
             if(post==4||post==3) {
                    Intent k = new Intent(Intent.ACTION_VIEW);
                    Log.e("Log:",bid);
                    k.setData(Uri.parse(bid));
                    startActivity(k);

                }else {
                    if(myprofileid!=null){
                       // Uloaddataserver();
                        Intent i = new Intent(getApplicationContext(), Musicplayerpage.class);
                        i.putExtra("ID", id);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getApplicationContext(), Musicplayerpage.class);
                        i.putExtra("ID", id);
                        startActivity(i);
                    }
                }

            }

            public void onLongClick(View view, int position) {

            }
        }));
music_newreleases.addOnItemTouchListener(new RecyclerTouchListener(this,music_newreleases,new ClickListener(){

    public void onClick(View view, int position) {
        String id = ((ItemModel) modelList.get(position)).getId();

        String image = ((ItemModel) modelList.get(position)).getImage();
        String title= ((ItemModel) modelList.get(position)).getTitle();
        String date= ((ItemModel) modelList.get(position)).getPdate();
        Intent i = new Intent(MusicActivity.this, MusicnewRealeaseDetailpage.class);
        i.putExtra("ID", id);
        i.putExtra("IMAGE", image);
        i.putExtra("TITLE", title);
        i.putExtra("DATE", date);
        startActivity(i);
    }

    public void onLongClick(View view, int position) {

    }
}));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;
    }
    private
    JsonObjectRequest getDataFromTheServer( int requestCount) {

        if(myprofileid!=null){
            URLTHREE=URLTWO+"&page="+requestCount+"&userid="+myprofileid;
        }else {
            URLTHREE=URLTWO+"&page="+requestCount;
        }






        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLTHREE);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    dissmissDialog();
                    parseJsonFeedAll(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLTHREE,  new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                    if (response != null) {
                        dissmissDialog();
                        parseJsonFeedAll(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            //requestQueue.add(jsonReq);
        }
        return jsonReq;
    }
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i <feedArray.length(); i++) {
                JSONObject obj = (JSONObject) feedArray.get(i);

                ItemModel model = new ItemModel();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
                model.setImage(image);
                String decription_city = obj.isNull("description") ? null : obj
                        .getString("description");
                model.setDescription(decription_city);
                String id_city = obj.isNull("fid") ? null : obj
                        .getString("fid");
                model.setId(id_city);
                String date_city = obj.isNull("pdate") ? null : obj
                        .getString("pdate");
                model.setPdate(date_city);
                String title_city = obj.isNull("title") ? null : obj
                        .getString("title");
                model.setTitle(title_city);
                String musicdirector= obj.isNull("music_director") ? null : obj
                        .getString("music_director");
                model.setMusicdirector(musicdirector);
                String musiccategory= obj.isNull("music_category") ? null : obj
                        .getString("music_category");
                model.setMusiccategory(musiccategory);
                String musiclanguage= obj.isNull("language") ? null : obj
                        .getString("language");
                model.setLanguage(musiclanguage);
                String musiclink= obj.isNull("link") ? null : obj
                        .getString("link");
                model.setLink(musiclink);

                modelList.add(model);

            }


            rcadap.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJsonFeedAll(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i <feedArray.length(); i++) {
                JSONObject obj = (JSONObject) feedArray.get(i);

                ItemModel model = new ItemModel();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
                model.setImage(image);
                model.setType(obj.getInt("typeid"));
                String id_city = obj.isNull("id") ? null : obj
                        .getString("id");
                model.setId(id_city);
                String date_city = obj.isNull("pdate") ? null : obj
                        .getString("pdate");
                model.setPdate(date_city);
                String title_city = obj.isNull("title") ? null : obj
                        .getString("title");
                model.setTitle(title_city);
                String musicdirector= obj.isNull("music_director") ? null : obj
                        .getString("music_director");
                model.setMusicdirector(musicdirector);
                String musiccategory= obj.isNull("music_category") ? null : obj
                        .getString("music_category");
                model.setMusiccategory(musiccategory);
                String musiclanguage= obj.isNull("language") ? null : obj
                        .getString("language");
                model.setLanguage(musiclanguage);
                String musiclink= obj.isNull("link") ? null : obj
                        .getString("link");
                model.setLink(musiclink);
               String favs = obj.isNull("fav") ? null : obj
                        .getString("fav");
                model.setFavourite(favs);
                String imageurl = obj.isNull("image_url") ? null : obj
                        .getString("image_url");
                model.setImageurl(imageurl);
                String adurl = obj.isNull("ad_url") ? null : obj
                        .getString("ad_url");
                model.setAdurl(adurl);
                musiclistall.add(model);

            }


            rcadapall.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    class ItemModel{
        private int typeid;
        private String name;
        private String image;
        private String bimage;
        private String pdate;
        private String description;
        private String title;
        private String publisher;
        private String company;
        private String qtype;
        private String link;
        private String musiccategory;
        private String musicdirector;
        private String language;
        String count;
        String favourite;
        int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private String adurl,imageurl;

        public String getAdurl() {
            return adurl;
        }

        public void setAdurl(String adurl) {
            this.adurl = adurl;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getFavourite() {
            return favourite;
        }

        public void setFavourite(String favourite) {
            this.favourite = favourite;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCount() {
            return count;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getMusiccategory() {
            return musiccategory;
        }

        public void setMusiccategory(String musiccategory) {
            this.musiccategory = musiccategory;
        }

        public String getMusicdirector() {
            return musicdirector;
        }

        public void setMusicdirector(String musicdirector) {
            this.musicdirector = musicdirector;
        }

        /******** start the Food category names****/
        private String category_name;
        /******** start the Food category names****/
        private String id;

        public String getQtype() {
            return qtype;
        }

        public void setQtype(String qtype) {
            this.qtype = qtype;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
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
        public String getPublisher(){return  publisher;}

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }
        public String getCompany(){return company;}
        public void setCompany(String company){
            this.company=company;
        }
        /******** start the Food category names****/
        public String getCategory_name(){return  category_name;}

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelList=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapter(Context context, List<ItemModel> modelList) {
            this.modelList = modelList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_music, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = MySingleton.getInstance(MusicActivity.this).getImageLoader();
            ItemModel itemmodel=modelList.get(position);
            String types=itemmodel.getQtype();
            //if(types.equals("news")) {
                holder.titles.setText(itemmodel.getTitle());
                holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
                holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
                holder.profileimage.setAdjustViewBounds(true);
                holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
            /*}else {
                holder.profileimage.setVisibility(View.GONE);
                holder.titles.setVisibility(View.GONE);
            }*/
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }
        public class RecyclerViewHolders extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHolders(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date;

        public ImageButton addtoplaylist;

        public NetworkImageView imageview;

        public UserViewHolder(View itemView) {
            super(itemView);

            // im = (ImageView) itemView.findViewById(R.id.imageViewlitle);

            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


            name = (TextView) itemView.findViewById(R.id.title_musicshow);
            date=(TextView)itemView.findViewById(R.id.date_music);
            addtoplaylist=(ImageButton)itemView.findViewById(R.id.addfav_button);


        }
    }
    static class UserViewHoldersmallad extends RecyclerView.ViewHolder {

        public NetworkImageView feed_advertisement_small;

        public UserViewHoldersmallad(View itemView) {
            super(itemView);

            feed_advertisement_small=(NetworkImageView) itemView.findViewById(R.id.ad_small);




        }
    }
    static class UserViewHolderimage extends RecyclerView.ViewHolder {

        public FeedImageView feed_advertisement;

        public UserViewHolderimage(View itemView) {
            super(itemView);

            feed_advertisement=(FeedImageView)itemView.findViewById(R.id.feedImage_advertisement);




        }
    }
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

   public class RecyclerViewAdapterArticles extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        ImageLoader mImageLoader;
        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_LOADING = 2;
        private final int VIEW_TYPE_FEATURE = 0;
       private final int VIEW_TYPE_ITEM_AD = 3;
       private final int VIEW_TYPE_ITEM_AD_SMALL = 4;
        private boolean loading;
        private OnLoadMoreListener onLoadMoreListener;

        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;

        public RecyclerViewAdapterArticles(List<ItemModel> students, RecyclerView recyclerView) {
            musiclistall = students;

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
            switch (viewType){
                case VIEW_TYPE_ITEM:
                    ViewGroup vGroup = (ViewGroup) mInflater.inflate ( R.layout.feed_item_music_vertical, parent, false );
                    UserViewHolder vhGroup = new UserViewHolder ( vGroup );
                    return vhGroup;
                case VIEW_TYPE_ITEM_AD:
                    ViewGroup adview=(ViewGroup)mInflater.inflate(R.layout.feed_item_ar,parent,false);
                    UserViewHolderimage image=new UserViewHolderimage(adview);
                    return image;
                case VIEW_TYPE_ITEM_AD_SMALL:
                    ViewGroup adsmall=(ViewGroup)mInflater.inflate(R.layout.feed_item_small_ad,parent,false);
                    UserViewHoldersmallad small=new UserViewHoldersmallad(adsmall);
                    return  small;
                case VIEW_TYPE_LOADING:
                    ViewGroup viewload=(ViewGroup)mInflater.inflate(R.layout.layout_loading_item,parent,false);
                    LoadingViewHolder load=new LoadingViewHolder(viewload);
                    return load;
            }

            return null;

        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if(holder instanceof UserViewHolderimage){
                final UserViewHolderimage userViewHolderimagess = (UserViewHolderimage) holder;
                ItemModel itemmodel=musiclistall.get(position);
                if(itemmodel.getType()==4){
                    if (mImageLoader == null)
                        mImageLoader = CustomVolleyRequest.getInstance(MusicActivity.this).getImageLoader();
                    // mImageLoader.get(itemmodel.getImage(), ImageLoader.getImageListener(userViewHolder.feedimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    userViewHolderimagess.feed_advertisement.setImageUrl(itemmodel.getImageurl(), mImageLoader);

                }
            }else if(holder instanceof UserViewHoldersmallad){
                final UserViewHoldersmallad user_smallad = (UserViewHoldersmallad) holder;
                ItemModel itemmodel=musiclistall.get(position);
                String ids="http://simpli-city.in/smp_interface/images/ads/480665630VDsmall.jpg";
                if(itemmodel.getType()==3){
                    if (mImageLoader == null)
                        mImageLoader = CustomVolleyRequest.getInstance(MusicActivity.this).getImageLoader();
                    // mImageLoader.get(itemmodel.getImage(), ImageLoader.getImageListener(userViewHolder.feedimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    user_smallad.feed_advertisement_small.setImageUrl(itemmodel.getImageurl(), mImageLoader);

                }
            }
           else if (holder instanceof UserViewHolder) {
               final ItemModel itemmodel = musiclistall.get(position);
                if(itemmodel.getType()==0) {
                    final UserViewHolder userViewHolder = (UserViewHolder) holder;

                    String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                    Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                    if (mImageLoader == null)
                        mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();


                    String favscount = itemmodel.getFavourite();
                    if (favscount != null) {
                        favs = Integer.parseInt(favscount);
                    }

                    if (favscount.equalsIgnoreCase("1")) {

                        userViewHolder.addtoplaylist.setImageResource(R.drawable.greyhandfriends);
                    } else {
                        userViewHolder.addtoplaylist.setImageResource(R.drawable.handaddfriend);
                    }


                    fav_list_count = favs;


                    userViewHolder.addtoplaylist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (myprofileid != null) {
                                if (fav_list_count == 1) {
                                    userViewHolder.addtoplaylist.setImageResource(R.drawable.handaddfriend);
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
                                    userViewHolder.addtoplaylist.setImageResource(R.drawable.greyhandfriends);
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
                                    //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Intent in = new Intent(MusicActivity.this, SigninpageActivity.class);
                                startActivity(in);
                            }
                        }
                    });


                    userViewHolder.name.setTypeface(seguiregular);
                    userViewHolder.date.setText(itemmodel.getPdate());
                    userViewHolder.date.setTypeface(seguiregular);
                    userViewHolder.name.setText(itemmodel.getTitle());
                    userViewHolder.imageview.setDefaultImageResId(R.mipmap.ic_launcher);
                    userViewHolder.imageview.setErrorImageResId(R.mipmap.ic_launcher);
                    userViewHolder.imageview.setAdjustViewBounds(true);
                    userViewHolder.imageview.setImageUrl(itemmodel.getImage(), mImageLoader);
                    // userViewHolder.im.setVisibility(View.VISIBLE);
                }
            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }

        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }



        /*public int getItemViewType(int position) {
            if (isPositionHeader(position)) {
                return VIEW_TYPE_FEATURE;
            } else if (isPositionFooter(position)) {
                return VIEW_TYPE_LOADING;
            }
            return VIEW_TYPE_ITEM;
        }*/
        public int getItemViewType(int position) {
            ItemModel item = musiclistall.get(position);
            if(item.getTypeid()==4){
                return VIEW_TYPE_ITEM_AD;
            }else if(item.getTypeid()==3){
                return VIEW_TYPE_ITEM_AD_SMALL;
            }else {
                return item != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
            }


        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return musiclistall.size();
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


    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }
    }








}

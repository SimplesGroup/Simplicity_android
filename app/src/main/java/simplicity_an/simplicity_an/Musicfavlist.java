package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

import com.android.volley.Cache;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 8/22/2016.
 */
public class Musicfavlist extends AppCompatActivity {
    private LinearLayoutManager lLayout;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=musicplaylist&key=simples&user_id=";
    private String KEY_POSTID = "mid";
    private String KEY_MYID = "user_id";
    String URLFAV="http://simpli-city.in/request2.php?rtype=musicfav&key=simples";
    int favs,fav_list_count;
    private ProgressDialog pdialog;
    View view;
    RecyclerViewAdapter rcAdapter;
    RecyclerView rView;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue queue;
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid,myprofileid;
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTIDS = "id";
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO;
    TextView title;
    ImageButton back,mainpage;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.musicfavlist);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");

        }
        URLTWO=URL+myprofileid;
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        lLayout = new LinearLayoutManager(this);
        queue = MySingleton.getInstance(this).
                getRequestQueue();
        requestQueue= Volley.newRequestQueue(this);
        title=(TextView)findViewById(R.id.radioplay_title) ;
        title.setTypeface(tf);
        title.setText("My Playlist");
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        back=(ImageButton)findViewById(R.id.btn_radioplayback);
        mainpage=(ImageButton)findViewById(R.id.btn_radioplayhome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent radiopage=new Intent(Musicfavlist.this,MusicActivity.class);
                startActivity(radiopage);
                finish();*/
                onBackPressed();
            }
        });
        mainpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent radiopage=new Intent(Musicfavlist.this,MainPageEnglish.class);
                startActivity(radiopage);
                finish();
            }
        });
        rView = (RecyclerView) findViewById(R.id.radioplay_recyclerview);
        rView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rView.setLayoutManager(lLayout);
        pdialog = new ProgressDialog(this);
        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
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
            requestQueue.add(jsonReq);
            //AppControllers.getInstance().addToRequestQueue(jsonReq);
        }
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        rcAdapter = new RecyclerViewAdapter(modelList,rView);
        rView.setAdapter(rcAdapter);
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
                                modelone.setTypeid(objtwo.getInt("id"));
                                String id_city = objtwo.isNull("id") ? null : objtwo
                                        .getString("id");
                                modelone.setId(id_city);
                                String date_city = objtwo.isNull("pdate") ? null : objtwo
                                        .getString("pdate");
                                modelone.setPdate(date_city);
                                String title_city = objtwo.isNull("title") ? null : objtwo
                                        .getString("title");
                                modelone.setTitle(title_city);
                                String musicdirector= objtwo.isNull("music_director") ? null : objtwo
                                        .getString("music_director");
                                modelone.setMusicdirector(musicdirector);
                                String musiccategory= objtwo.isNull("music_category") ? null : objtwo
                                        .getString("music_category");
                                modelone.setMusiccategory(musiccategory);
                                String musiclanguage= objtwo.isNull("language") ? null : objtwo
                                        .getString("language");
                                modelone.setLanguage(musiclanguage);
                                String musiclink= objtwo.isNull("link") ? null : objtwo
                                        .getString("link");
                                modelone.setLink(musiclink);
                                modelList.add(modelone);
                            }
                            rcAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                        }

                        rcAdapter.setLoaded();
                    }
                }, 2000);
            }
        });

        rView.addOnItemTouchListener(new RadioActivity.RecyclerTouchListener(getApplicationContext(), rView, new RadioActivity.ClickListener() {
            public void onClick(View view, int position) {



                String id= ((ItemModel) modelList.get(position)).getId();
                // int favid= ((ItemModel) modelList.get(position)).getFavourite();


                postid = ((ItemModel)modelList.get(position)).getId();

                if(myprofileid!=null){
                    //  Uloaddataserver();
                    Intent fooddescription = new Intent(getApplicationContext(), Musicfavplayerpage.class);
                    fooddescription.putExtra("ID", id);

                    startActivity(fooddescription);
                }else {
                    Intent fooddescription = new Intent(getApplicationContext(), Musicfavplayerpage.class);
                    fooddescription.putExtra("ID", id);

                    startActivity(fooddescription);
                }
            }

            public void onLongClick(View view, int position) {

            }
        }));
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    private  void parseJsonFeed(JSONObject response){
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
                    model.setTypeid(obj.getInt("id"));
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
                    model.setTypeid(obj.getInt("id"));
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

                    modelList.add(model);
                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class ItemModel{

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
        private int typeid;
        int favourite;

        public int getFavourite() {
            return favourite;
        }

        public void setFavourite(int favourite) {
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView title,date,description;
        public NetworkImageView imageview;
        ImageButton favourite;
        public UserViewHolder(View itemView) {
            super(itemView);



            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


            title = (TextView) itemView.findViewById(R.id.title_radio);
            date = (TextView) itemView.findViewById(R.id.date_radio);
            description = (TextView) itemView.findViewById(R.id.desc_radio);
            //  favourite=(ImageButton) itemView.findViewById(R.id.addfav_button);

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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_radio_playlist, parent, false);
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
                /*if(favs==1){

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
                            Intent in=new Intent(RadioPlaylisttamil.this,SigninpageActivity.class);
                            startActivity(in);
                        }
                    }
                });*/



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

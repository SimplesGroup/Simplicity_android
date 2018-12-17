package simplicity_an.simplicity_an;

import android.app.Dialog;
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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
 * Created by kuppusamy on 6/23/2016.
 */
public class PhotoStories extends AppCompatActivity {
    private List<ItemModel> modelList=new ArrayList<ItemModel>();

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    RecyclerViewAdapter rcAdapter;
    LinearLayoutManager mLayoutManager;
    RecyclerView recycler;
    JSONObject obj, objtwo;
    JSONArray feedArray,feedArraygallery;
    static String myprofileid;
    String URLTWO;
    int ii,i,post_likes_count,k;
    String URL="http://simpli-city.in/request2.php?rtype=photostories&key=simples";
    ArrayAdapter<String> adapter;
    private final String TAG_REQUEST = "MY_TAG";
    private String KEY_MYUID = "usrid";
    private String KEY_POSTID = "photo_id";
    private String KEY_MYID = "uid";
    private String KEY_FRIENDID= "fid";
    private String KEY_DELETEID= "cid";
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
TextView title;
    ImageButton back,menu,notification;

    String UPLOAD_URLLIKES="http://simpli-city.in/request2.php?rtype=photostorylikes&key=simples";
    String UPLOAD_URLADDFRIENDS="http://simpli-city.in/request2.php?rtype=addfriend&key=simples";
    String UPLOAD_URLDELETEMYPOST="http://simpli-city.in/request2.php?rtype=deletepost&key=simples";
    String UPLOAD_URLSHAREPOST="http://simpli-city.in/request2.php?rtype=sharepost&key=simples";
    String UPLOAD_URLREPORTPOST="http://simpli-city.in/request2.php?rtype=reportabuse&key=simples";

    private String KEY_QTYPES = "qtype";
    private String KEY_MYUIDS = "user_id";
    private String KEY_POSTIDS = "id";
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid;
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget;
    int value;
    String notification_counts;
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid,activity;
    ProgressDialog pdialog;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.photostory);
        notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        contentid=sharedpreferences.getString(CONTENTID,"");
        activity=sharedpreferences.getString(Activity,"");
        if (sharedpreferences.contains(MYUSERID)) {
            myprofileid=sharedpreferences.getString(MYUSERID,"");
            Log.e("MUUSERID:",myprofileid);
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        if(activity!=null){
        if(activity.equalsIgnoreCase("photoesc")){

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove(Activity);
            editor.remove(CONTENTID);
            editor.apply();

        }
        }
        requestQueue = Volley.newRequestQueue(this);
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
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

        String fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(PhotoStories.this.getAssets(), fontPath);
        title=(TextView)findViewById(R.id.toolbar_title_photostory);
        title.setTypeface(tf);
        title.setText("Photo Stories");
        menu=(ImageButton)findViewById(R.id.btn_photohome);
        back=(ImageButton)findViewById(R.id.btn_photoback);

        if(colorcodes.equals("#FFFFFFFF")){
            title.setTextColor(Color.WHITE);
        }
        else{
            title.setTextColor(Color.BLACK);
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menucity = new Intent(PhotoStories.this, MainPageEnglish.class);
                startActivity(menucity);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent backcity = new Intent(PhotoStories.this, MainActivityVersiontwo.class);
                startActivity(backcity);
                finish();*/
                onBackPressed();
            }
        });
        notification=(ImageButton)findViewById(R.id.btn_photoprofile) ;
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(PhotoStories.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(PhotoStories.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });
        recycler = (RecyclerView)findViewById(R.id.photostories_recyclerview);
        recycler.addItemDecoration(new DividerItemDecoration(PhotoStories.this, LinearLayoutManager.VERTICAL));
        recycler.setHasFixedSize(true);

        recycler.setLayoutManager(new LinearLayoutManager(PhotoStories.this));
        pdialog = new ProgressDialog(PhotoStories.this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdialog.setCanceledOnTouchOutside(false);

        getData();
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
                        getData();
                        //Load data



                        rcAdapter.setLoaded();
                    }
                }, 2000);
            }
        });

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
            URLTWO=URL+"&user_id="+myprofileid+"&page="+requestCount;
        }else {
            URLTWO=URL+"&page="+requestCount;
        }

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
            jsonReq = new JsonObjectRequest(Request.Method.GET,
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

            jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS *2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonReq);
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
                    String image = obj.isNull("image") ? null : obj
                            .getString("image");
                    model.setImage(image);
                    String decription_city = obj.isNull("title") ? null : obj
                            .getString("title");
                    model.setDescription(decription_city);
                    String id_city = obj.isNull("id") ? null : obj
                            .getString("id");
                    model.setId(id_city);
                    String date_city = obj.isNull("pdate") ? null : obj
                            .getString("pdate");
                    model.setPdate(date_city);

                    String userid_city = obj.isNull("cid") ? null : obj
                            .getString("cid");
                    model.setUid(userid_city);
                    int useridinteger_city = obj.isNull("cid") ? null : obj
                            .getInt("cid");
                    model.setUserids(useridinteger_city);

                    int likecount_city = obj.isNull("like_count") ? null : obj
                            .getInt("like_count");
                    model.setLikecount(likecount_city);
                    int mylike_city = obj.isNull("like") ? null : obj
                            .getInt("like");
                    model.setPostmylikes(mylike_city);
                    String likecount_cityss = obj.isNull("like_count") ? null : obj
                            .getString("like_count");
                    model.setLikescounts(likecount_cityss);
                    int typevalue = obj.isNull("album_count") ? null : obj
                            .getInt("album_count");
                    model.setType(typevalue);
                    feedArraygallery = obj.getJSONArray("album");
                    ArrayList<String> album = new ArrayList<String>();
                    for ( k= 0; k < feedArraygallery.length(); k++) {

                        album.add(((String) feedArraygallery.get(k))+"\n");
                    }
                    model.setAlbum(album);
                    modelList.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();

                  } catch (JSONException e) {
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

    class ItemModel{
        private int typeid;
        private String name;
        private String image;
        private String profilepic;
        private String pdate;
        private String description;
        private String title;
        private int likecount;
        private String commentcount;
        /******** start the Food category names****/
        private String id;
        /******** start the Food category names****/
        private String uid;
        private int postmylikes,type;
        private  int  checkmyfriends;

        int userids;
        String likescounts;
        String count;
        private ArrayList<String> album;

        public ArrayList<String> getAlbum() {
            return album;
        }

        public void setAlbum(ArrayList<String> album) {
            this.album = album;
        }


        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
        public String getLikescounts() {
            return likescounts;
        }

        public void setLikescounts(String likescounts) {
            this.likescounts = likescounts;
        }

        public int getUserids() {
            return userids;
        }

        public void setUserids(int userids) {
            this.userids = userids;
        }

        public int getCheckmyfriends() {
            return checkmyfriends;
        }

        public void setCheckmyfriends(int checkmyfriends) {
            this.checkmyfriends = checkmyfriends;
        }

        public int getPostmylikes() {
            return postmylikes;
        }

        public void setPostmylikes(int postmylikes) {
            this.postmylikes = postmylikes;
        }

        public String getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(String commentcount) {
            this.commentcount = commentcount;
        }

        public int getLikecount() {
            return likecount;
        }

        public void setLikecount(int likecount) {
            this.likecount = likecount;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getProfilepic() {
            return profilepic;
        }

        public void setProfilepic(String profilepic) {
            this.profilepic = profilepic;
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


                    params.put(KEY_QTYPES, "notify");
                    params.put(KEY_POSTIDS,"1");
                    params.put(KEY_MYUIDS, myprofileid);


                }
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name,likescount;


        public Button commentsbut,likes,sharepost;
        ImageButton heartbuttton_like;
        NetworkImageView   feedImageView;

        NetworkImageView   feedImageView_typetwo_one,feedImageView_typetwo_two;
        NetworkImageView   feed_typethree_ones,feed_typethree_twos,feed_typethree_threes;
        NetworkImageView   feedImageView_typefour_one,feedImageView_typefour_two,feedImageView_typefour_three,feedImageView_typefour_four;

        public UserViewHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.title_photo);


            feedImageView = (NetworkImageView) itemView
                    .findViewById(R.id.feedImage_photostory);

            feedImageView_typetwo_one=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_two_one) ;
            feedImageView_typetwo_two=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_two_two) ;

            feed_typethree_ones=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_three_one) ;
            feed_typethree_twos=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_three_two) ;
            feed_typethree_threes=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_three_three) ;


            feedImageView_typefour_one=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_four_one) ;
            feedImageView_typefour_two=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_four_two) ;
            feedImageView_typefour_three=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_four_three) ;
            feedImageView_typefour_four=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_four_four) ;
          commentsbut = (Button) itemView.findViewById(R.id.photo_comment);
            heartbuttton_like = (ImageButton) itemView.findViewById(R.id.citycenter_main_heartlike);
            likescount = (TextView) itemView.findViewById(R.id.likes_photo);
           sharepost = (Button) itemView.findViewById(R.id.city_sharepost);
           likes = (Button) itemView.findViewById(R.id.photo_likes);
commentsbut.setText("Comment");
            sharepost.setText("Share");
            likes.setText("Like");
commentsbut.setTransformationMethod(null);
            sharepost.setTransformationMethod(null);
            likes.setTransformationMethod(null);

           if(backgroundcolor.equals("#FFFFFFFF")){
               commentsbut.setTextColor(Color.WHITE);
               name.setTextColor(Color.WHITE);
           }
           else{
               commentsbut.setTextColor(Color.BLACK);
               name.setTextColor(Color.BLACK);
           }

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

            if(viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(PhotoStories.this).inflate(R.layout.feed_item_photostory, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(PhotoStories.this).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;

        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof UserViewHolder) {

                final UserViewHolder userViewHolder = (UserViewHolder) holder;
                final ItemModel itemmodel = modelList.get(position);
                if (mImageLoader == null)
                    mImageLoader = AppControllers.getInstance().getImageLoader();

                userViewHolder. name.setText(itemmodel.getDescription());

                // Converting timestamp into x ago format
               /* CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(itemmodel.getPdate()),
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);*/
                userViewHolder.likescount.setText(itemmodel.getLikescounts());
                String powers = "";
                String powerstwo = "";
                // Chcek for empty status message
                if (itemmodel.getType()==0) {

                    userViewHolder.feedImageView.setImageUrl(itemmodel.getImage(), mImageLoader);
                    userViewHolder.feedImageView.setDefaultImageResId(R.mipmap.ic_launcher);

                    userViewHolder.feedImageView.setVisibility(View.VISIBLE);
                       /* userViewHolder.feedImageView
                                .setResponseObserver(new FeedImageView.ResponseObserver() {
                                    @Override
                                    public void onError() {
                                    }

                                    @Override
                                    public void onSuccess() {
                                    }
                                });*/
                    userViewHolder.feedImageView_typetwo_one.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typetwo_two.setVisibility(View.GONE);
                    userViewHolder.feed_typethree_ones.setVisibility(View.GONE);
                    userViewHolder.feed_typethree_twos.setVisibility(View.GONE);
                    userViewHolder.feed_typethree_threes.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typefour_one.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typefour_two.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typefour_three.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typefour_four.setVisibility(View.GONE);
                }else if(itemmodel.getType()==2){
                    int j;
                       /* for(int i = 0; i<itemmodel.getAlbum().size(); i++){
                            powers= itemmodel.getAlbum().get(i);
                        }
                        //Log.e("image:",powers);
                        ArrayList<String> powersitem = new ArrayList<String>();
                        powersitem.add(powers);*/

                    for( j=0;j<itemmodel.getAlbum().size();j++) {
                        powerstwo = itemmodel.getAlbum().get(j);
                        if(j==0) {
                            System.out.println("two1 -->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typetwo_one.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typetwo_one.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typetwo_one.setVisibility(View.VISIBLE);
                               /* userViewHolder.feedImageView_typetwo_one
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        }else if(j==1) {
                            System.out.println(" two2-->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typetwo_two.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typetwo_two.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typetwo_two.setVisibility(View.VISIBLE);
                               /* userViewHolder.feedImageView_typetwo_two
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        }
                        userViewHolder.feedImageView.setVisibility(View.GONE);

                        userViewHolder.feed_typethree_ones.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_twos.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_threes.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_one.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_two.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_three.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_four.setVisibility(View.GONE);
                    }
                }else if(itemmodel.getType()==3){
                    int j;
                    for( j=0;j<itemmodel.getAlbum().size();j++) {
                        powerstwo = itemmodel.getAlbum().get(j);
                        // holder.textViewPowers_one.setText(powerstwo);
                        // holder.textViewPowers_two.setText(powerstwo);
                        // holder.textViewPowers_three.setText(powerstwo);
                        // holder.textViewPowers_four.setText(powerstwo);
                        if(j==0) {
                            System.out.println("three -->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feed_typethree_ones.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feed_typethree_ones.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feed_typethree_ones.setVisibility(View.VISIBLE);
                                /*userViewHolder.feedImageView_typethree_one
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        }else if(j==1) {
                            System.out.println(" three2-->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feed_typethree_twos.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feed_typethree_twos.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feed_typethree_twos.setVisibility(View.VISIBLE);
                               /* userViewHolder.feedImageView_typethree_two
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        }else if (j==2) {
                            System.out.println("three3 -->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feed_typethree_threes.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feed_typethree_threes.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feed_typethree_threes.setVisibility(View.VISIBLE);
                               /* userViewHolder.feedImageView_typethree_three
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        }
                        userViewHolder.feedImageView_typetwo_one.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typetwo_two.setVisibility(View.GONE);
                        userViewHolder.feedImageView.setVisibility(View.GONE);

                        userViewHolder.feedImageView_typefour_one.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_two.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_three.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_four.setVisibility(View.GONE);
                    }
                }else {
                    int j;
                    for (j = 0; j < itemmodel.getAlbum().size(); j++) {
                        powerstwo = itemmodel.getAlbum().get(j);
                        // holder.textViewPowers_one.setText(powerstwo);
                        // holder.textViewPowers_two.setText(powerstwo);
                        // holder.textViewPowers_three.setText(powerstwo);
                        // holder.textViewPowers_four.setText(powerstwo);
                        if (j == 0) {
                            System.out.println("four -->" + itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typefour_one.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typefour_one.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typefour_one.setVisibility(View.VISIBLE);
                                /*userViewHolder.feedImageView_typefour_one
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        } else if (j == 1) {
                            System.out.println("four2 -->" + itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typefour_two.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typefour_two.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typefour_two.setVisibility(View.VISIBLE);
                               /* userViewHolder.feedImageView_typefour_two
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        } else if (j == 2) {
                            System.out.println("four3 -->" + itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typefour_three.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typefour_three.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typefour_three.setVisibility(View.VISIBLE);
                                /*userViewHolder.feedImageView_typefour_three
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        } else if (j == 3) {
                            System.out.println("four4 -->" + itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typefour_four.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typefour_four.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typefour_four.setVisibility(View.VISIBLE);
                                /*userViewHolder.feedImageView_typefour_four
                                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                                            @Override
                                            public void onError() {
                                            }

                                            @Override
                                            public void onSuccess() {
                                            }
                                        });*/
                        }
                        userViewHolder.feedImageView_typetwo_one.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typetwo_two.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_ones.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_twos.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_threes.setVisibility(View.GONE);
                        userViewHolder.feedImageView.setVisibility(View.GONE);


                    }


                    userViewHolder.feedImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getImage());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragment frag;
                            frag = new MyDialogFragment();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getImage());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    userViewHolder.feedImageView_typetwo_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    userViewHolder.feedImageView_typetwo_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });

                    userViewHolder.feedImageView_typefour_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    userViewHolder.feedImageView_typefour_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    userViewHolder.feedImageView_typefour_three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    userViewHolder.feedImageView_typefour_four.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });

                    userViewHolder.feed_typethree_ones.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    userViewHolder.feed_typethree_twos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    userViewHolder.feed_typethree_threes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("IMAGE:", itemmodel.getId());


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyDialogFragmentmultiple frag;
                            frag = new MyDialogFragmentmultiple();
                            Bundle args = new Bundle();
                            args.putString("Image", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    post_likes_count = itemmodel.getPostmylikes();
                    if (itemmodel.getPostmylikes() == 1) {
                        userViewHolder.heartbuttton_like.setImageResource(R.drawable.heartcityred);

                    } else {
                        userViewHolder.heartbuttton_like.setImageResource(R.drawable.heartcity);

                    }

                    userViewHolder.heartbuttton_like.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            MyLIKEFragment frag;
                            frag = new MyLIKEFragment();
                            Bundle args = new Bundle();
                            args.putString("ID", itemmodel.getId());
                            frag.setArguments(args);
                            frag.show(ft, "txn_tag");
                        }
                    });
                    userViewHolder.likes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(myprofileid!=null) {

                                if (post_likes_count == 1) {
                                    userViewHolder.heartbuttton_like.setImageResource(R.drawable.heartcity);
                                    post_likes_count--;
                                    String f = itemmodel.getLikescounts().toString();

                                    int i = Integer.parseInt(f);
                                    String s = "1";
                                    int j = Integer.parseInt(s);

                                    Integer result = i - j;
                                    String res = result.toString();


                                    userViewHolder.likescount.setText(itemmodel.getLikescounts());

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URLLIKES,
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
                                            String postid = itemmodel.getId();
                                            //Adding parameters
                                            if (postid != null) {


                                                params.put(KEY_POSTID, postid);
                                                params.put(KEY_MYUID, myprofileid);
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
                                    userViewHolder.heartbuttton_like.setImageResource(R.drawable.heartcityred);
                                    post_likes_count++;


                                    String f = itemmodel.getLikescounts().toString();
                                    int i = Integer.parseInt(f);
                                    String s = "1";
                                    int j = Integer.parseInt(s);

                                    Integer result = i + j;
                                    String res = result.toString();
                                    userViewHolder.likescount.setText(res);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URLLIKES,
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
                                            String postid = itemmodel.getId();
                                            //Adding parameters
                                            if (postid != null) {


                                                params.put(KEY_POSTID, postid);
                                                params.put(KEY_MYUID, myprofileid);
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
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Activity, "photoesc");
                                editor.putString(CONTENTID, "0");
                                editor.commit();
                                Intent ins = new Intent(getApplicationContext(), SigninpageActivity.class);

                                startActivity(ins);
                            }
                        }
                    });


                    userViewHolder.commentsbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (myprofileid != null) {
                                   /* Intent commentpage = new Intent(getApplicationContext(), CityCenterCommentPage.class);
                                    commentpage.putExtra("ID", itemmodel.getId());
                                    commentpage.putExtra("ACTIVITY", "citycenter");
                                    startActivity(commentpage);*/
                                } else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Activity, "photoesc");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent ins = new Intent(getApplicationContext(), SigninpageActivity.class);

                                    startActivity(ins);
                                }

                            } catch (Exception e) {

                            }


                        }
                    });


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


            return modelList.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }

    }
    public static class MyDialogFragment extends DialogFragment {
        NetworkImageView post_view_image_big;
        ImageLoader mImageLoader;
        ImageButton closedialog;
        String images_to_load;
        public MyDialogFragment() {

        }
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d!=null){
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.my_fragment, container, false);
            post_view_image_big=(NetworkImageView)root.findViewById(R.id.citycenter_post_view_image);
            closedialog=(ImageButton)root.findViewById(R.id.closebigimage);
           images_to_load = getArguments().getString("Image");
            if (mImageLoader == null)
                mImageLoader =  MySingleton.getInstance(getActivity()).getImageLoader();
            post_view_image_big.setDefaultImageResId(R.drawable.ic_launcher);
            post_view_image_big.setErrorImageResId(R.drawable.ic_launcher);
            post_view_image_big.setAdjustViewBounds(true);
            post_view_image_big.setImageUrl(images_to_load, mImageLoader);
            closedialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post_view_image_big.setVisibility(View.GONE);

                    MyDialogFragment.this.dismiss();

                }
            });
            return root;
        }

    }
    public static class MyDialogFragmentmultiple extends DialogFragment {
        NetworkImageView post_view_image_big;

        String images_to_load;
        private List<ItemModel> modelListlikes=new ArrayList<ItemModel>();
        private String URL="http://simpli-city.in/request2.php?rtype=photostories&key=simples&pid=";
        private ListView listview;
        private ProgressDialog pdialog;
        CoordinatorLayout mCoordinator;
        //Need this to set the title of the app bar
        CollapsingToolbarLayout mCollapsingToolbarLayout;
        RecyclerViewAdapter rcAdapter;
        private String URLTWO;
        String bimage;
        RecyclerView recycler;
        private Toolbar mToolbar;
        TextView Toolbartitle;
        ImageButton menu,back,search;
        LinearLayoutManager mLayoutManager;
        JSONObject obj, objtwo;
        JSONArray feedArray;
        int ii,i;
        protected Handler handler;
        private final String TAG_REQUEST = "MY_TAG";
        RequestQueue queues;

        ImageLoader mImageLoader;
        ImageButton closedialog;

        String likes_to_load;
        public MyDialogFragmentmultiple() {

        }
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d!=null){
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.photostoriesmultipleimagesshow, container, false);

            closedialog=(ImageButton)root.findViewById(R.id.closebigimage);
            likes_to_load = getArguments().getString("Image");
            URLTWO=URL+likes_to_load;
            mLayoutManager = new LinearLayoutManager(getActivity());
            mCoordinator = (CoordinatorLayout)root. findViewById(R.id.root_coordinator);
            mCollapsingToolbarLayout = (CollapsingToolbarLayout)root. findViewById(R.id.collapsing_toolbar_layout);
            closedialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    MyDialogFragmentmultiple.this.dismiss();

                }
            });
            recycler = (RecyclerView)root.findViewById(R.id.likes_recyclerview);
            recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            pdialog = new ProgressDialog(getActivity());
            pdialog.show();
            pdialog.setContentView(R.layout.custom_progressdialog);
            pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


                public void onResponse(JSONObject response) {


                    if (response != null) {
                        dissmissDialog();
                        try {
                            feedArray = response.getJSONArray("palbum");
                            if (feedArray.length() < 10) {
                                for (ii = 0; ii < feedArray.length(); ii++) {
                                    obj = (JSONObject) feedArray.get(ii);

                                    ItemModel model = new ItemModel();

                                        String image = obj.isNull("image") ? null : obj
                                                .getString("image");
                                        model.setImage(image);



                                  //  model.setName(obj.getString("name"));

                                   // model.setId(obj.getString("id"));

                                    modelListlikes.add(model);

                                }

                                // notify data changes to list adapater
                                rcAdapter.notifyDataSetChanged();
                            }else {
                                for (ii = 0; ii <= 10; ii++) {
                                    obj = (JSONObject) feedArray.get(ii);


                                    ItemModel model = new ItemModel();

                                    String image = obj.isNull("image") ? null : obj
                                            .getString("image");
                                    model.setImage(image);


                                    modelListlikes.add(model);
                                }

                                // notify data changes to list adapater
                                rcAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonreq.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonreq.setTag(TAG_REQUEST);

            MySingleton.getInstance(getActivity()).addToRequestQueue(jsonreq);

            rcAdapter = new RecyclerViewAdapter(modelListlikes,recycler);
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
                            int index = modelListlikes.size();
                            int end = index + 9;

                            try {

                                for (i = index; i < end; i++) {
                                    objtwo = (JSONObject) feedArray.get(i);
                                    ItemModel modelone = new ItemModel();
                                    String image = objtwo.isNull("image") ? null : objtwo
                                            .getString("image");
                                    modelone.setImage(image);
                                   /* JSONArray albumimages=objtwo.getJSONArray("palbum");
                                    for(int j=0;j<albumimages.length();j++){
                                        JSONObject album=(JSONObject)albumimages.get(j);
                                        String image = album.isNull("image") ? null : album
                                                .getString("image");
                                        modelone.setImage(image);

                                    }*/



                                    modelListlikes.add(modelone);
                                }
                                rcAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {

                            }

                            rcAdapter.setLoaded();
                        }
                    }, 2000);
                }
            });

            return root;
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
            public TextView name,locations;



            public NetworkImageView imageview;

            public UserViewHolder(View itemView) {
                super(itemView);



                imageview = (NetworkImageView) itemView.findViewById(R.id.photostories_multiple_view);







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
                modelListlikes = students;

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
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_multipleimagesview_photostory, parent, false);
                    return new UserViewHolder(view);
                } else if (viewType == VIEW_TYPE_LOADING) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading_item, parent, false);
                    return new LoadingViewHolder(view);
                }
                return null;

            }



            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                if (holder instanceof UserViewHolder) {

                    final UserViewHolder userViewHolder = (UserViewHolder) holder;

                    String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                    Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                    if (mImageLoader == null)
                        mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();


                    ItemModel itemmodel = modelListlikes.get(position);





                    userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                    userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

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


                return modelListlikes.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;

            }

            public void setLoaded() {
                loading = false;
            }

            public int getItemCount() {
                return modelListlikes.size();
            }
        }


    }

    public static class MyLIKEFragment extends DialogFragment {
        private List<ItemModel> modelListlikes=new ArrayList<ItemModel>();
        private String URL="http://simpli-city.in/request2.php?rtype=photostorylikeview&key=simples&photo_id=";
        private ListView listview;
        private ProgressDialog pdialog;
         CoordinatorLayout mCoordinator;
        //Need this to set the title of the app bar
        CollapsingToolbarLayout mCollapsingToolbarLayout;
        RecyclerViewAdapter rcAdapter;
        private String URLTWO;
        String bimage;
        RecyclerView recycler;
        private Toolbar mToolbar;
        TextView Toolbartitle;
        ImageButton menu,back,search;
     LinearLayoutManager mLayoutManager;
        JSONObject obj, objtwo;
        JSONArray feedArray;
        int ii,i;
        protected Handler handler;
        private final String TAG_REQUEST = "MY_TAG";
        RequestQueue queues;

        ImageLoader mImageLoader;
        ImageButton closedialog;

        String likes_to_load;
        public MyLIKEFragment() {

        }
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d!=null){
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.likespage, container, false);
            queues = MySingleton.getInstance(this.getActivity()).
                    getRequestQueue();

            closedialog=(ImageButton)root.findViewById(R.id.closebigimage);
            likes_to_load = getArguments().getString("ID");
            URLTWO=URL+likes_to_load;
            mLayoutManager = new LinearLayoutManager(getActivity());
            mCoordinator = (CoordinatorLayout)root. findViewById(R.id.root_coordinator);
            mCollapsingToolbarLayout = (CollapsingToolbarLayout)root. findViewById(R.id.collapsing_toolbar_layout);
            closedialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyLIKEFragment.this.dismiss();

                }
            });
            String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;

            Toolbartitle=(TextView)root.findViewById(R.id.toolbar_title);

            Toolbartitle.setText("LIKES");

            recycler = (RecyclerView)root.findViewById(R.id.likes_recyclerview);
            recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            pdialog = new ProgressDialog(getActivity());
            pdialog.show();
            pdialog.setContentView(R.layout.custom_progressdialog);
            pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


                public void onResponse(JSONObject response) {


                    if (response != null) {
                        dissmissDialog();
                        try {
                            feedArray = response.getJSONArray("result");
                            if (feedArray.length() < 10) {
                                for (ii = 0; ii < feedArray.length(); ii++) {
                                    obj = (JSONObject) feedArray.get(ii);

                                    ItemModel model = new ItemModel();

                                    String image = obj.isNull("image") ? null : obj
                                            .getString("image");
                                    model.setImage(image);

                                    model.setName(obj.getString("name"));

                                    model.setId(obj.getString("id"));

                                    modelListlikes.add(model);

                                }

                                // notify data changes to list adapater
                                rcAdapter.notifyDataSetChanged();
                            }else {
                                for (ii = 0; ii <= 10; ii++) {
                                    obj = (JSONObject) feedArray.get(ii);


                                    ItemModel model = new ItemModel();

                                    String image = obj.isNull("image") ? null : obj
                                            .getString("image");
                                    model.setImage(image);
                                    model.setName(obj.getString("name"));


                                    model.setId(obj.getString("id"));


                                    modelListlikes.add(model);
                                }

                                // notify data changes to list adapater
                                rcAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonreq.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonreq.setTag(TAG_REQUEST);

            MySingleton.getInstance(getActivity()).addToRequestQueue(jsonreq);

            rcAdapter = new RecyclerViewAdapter(modelListlikes,recycler);
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
                            int index = modelListlikes.size();
                            int end = index + 9;

                            try {

                                for (i = index; i < end; i++) {
                                    objtwo = (JSONObject) feedArray.get(i);
                                    ItemModel modelone = new ItemModel();


                                    String image = objtwo.isNull("image") ? null : objtwo
                                            .getString("image");
                                    modelone.setImage(image);
                                    modelone.setName(objtwo.getString("name"));

                                    modelone.setId(objtwo.getString("id"));


                                    modelListlikes.add(modelone);
                                }
                                rcAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {

                            }

                            rcAdapter.setLoaded();
                        }
                    }, 2000);
                }
            });

            return root;
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
            public TextView name,locations;



            public NetworkImageView imageview;

            public UserViewHolder(View itemView) {
                super(itemView);



                imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


                name = (TextView) itemView.findViewById(R.id.name);




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
                modelListlikes = students;

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
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_city_friendslist, parent, false);
                    return new UserViewHolder(view);
                } else if (viewType == VIEW_TYPE_LOADING) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading_item, parent, false);
                    return new LoadingViewHolder(view);
                }
                return null;

            }



            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                if (holder instanceof UserViewHolder) {

                    final UserViewHolder userViewHolder = (UserViewHolder) holder;

                    String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                    Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                    if (mImageLoader == null)
                        mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();


                    ItemModel itemmodel = modelListlikes.get(position);



                    userViewHolder.name.setText(itemmodel.getName());
                    userViewHolder.name.setTypeface(seguiregular);

                    userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                    userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

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


                return modelListlikes.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;

            }

            public void setLoaded() {
                loading = false;
            }

            public int getItemCount() {
                return modelListlikes.size();
            }
        }

    }

}

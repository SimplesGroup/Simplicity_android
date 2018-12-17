package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
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
 * Created by kuppusamy on 8/20/2016.
 */
public class MusicnewRealeaseDetailpage extends AppCompatActivity {
    String getid,gettitle,getdate,getimage;
FeedImageView imageView;
    ImageLoader imageLoader;
    TextView title,date;
    ImageButton backs,fav,home,searchs,notifications;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    RecyclerView music_newreleases,musicall;
    JSONObject obj, objtwo;
    String URLTWO;
    JSONArray feedArray;
    int ii,i,myuserinteger;
    RecyclerViewAdapterArticles rcadapall;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    ProgressDialog pdialog;
    JsonObjectRequest jsonReq;
    RequestQueue requestQueue;
    final String TAG_REQUEST = "MY_TAG";
    String URL="http://simpli-city.in/request2.php?rtype=music-release&key=simples&fid=";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.musicnewrelaesedetail);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        Intent get=getIntent();
        getid=get.getStringExtra("ID");
        gettitle=get.getStringExtra("TITLE");
        getdate=get.getStringExtra("DATE");
        getimage=get.getStringExtra("IMAGE");
        URLTWO=URL+getid;
        Log.e("URl:",URLTWO);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        imageView=(FeedImageView)findViewById(R.id.feedImage1);
        title=(TextView)findViewById(R.id.viewitem_typetwo_title);
        date=(TextView)findViewById(R.id.viewitem_typetwo_date);
        title.setTypeface(tf);
        date.setTypeface(tf);
        title.setText(gettitle);
        date.setText(getdate);
        imageLoader= CustomVolleyRequest.getInstance(MusicnewRealeaseDetailpage.this).getImageLoader();
        imageView.setDefaultImageResId(R.mipmap.ic_launcher);
        imageView.setErrorImageResId(R.mipmap.ic_launcher);
        imageView.setAdjustViewBounds(true);
        imageView.setImageUrl(getimage,imageLoader);
        backs=(ImageButton)findViewById(R.id.btn_musicnewback);
        home=(ImageButton)findViewById(R.id.btn_musicnewhome);
        notifications=(ImageButton)findViewById(R.id.btn_musicnewnotification);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent back=new Intent(MusicnewRealeaseDetailpage.this,MusicActivity.class);
                startActivity(back);
                finish();*/
                onBackPressed();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(MusicnewRealeaseDetailpage.this,MainPageEnglish.class);
                startActivity(back);
                finish();
            }
        });
        requestQueue= Volley.newRequestQueue(this);
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
requestQueue.add(jsonReq);
            //AppControllers.getInstance().addToRequestQueue(jsonReq);
        }
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        pdialog.setCanceledOnTouchOutside(false);
        musicall=(RecyclerView)findViewById(R.id.musicshow_recyclerview);
        musicall.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        musicall.setHasFixedSize(true);

        musicall.setLayoutManager(new LinearLayoutManager(this));
        rcadapall = new RecyclerViewAdapterArticles(modelList,musicall);
        musicall.setAdapter(rcadapall);
        musicall.addOnItemTouchListener(new MusicActivity.RecyclerTouchListener(this, musicall, new MusicActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String id= ((ItemModel) modelList.get(position)).getId();
                String fid= ((ItemModel) modelList.get(position)).getFid();

                Intent i = new Intent(MusicnewRealeaseDetailpage.this, MusicnewReleaseplayer.class);
                i.putExtra("ID", id);
                i.putExtra("FID", fid);
                i.putExtra("TITLE", gettitle);
                i.putExtra("DATE", getdate);
                i.putExtra("IMAGE", getimage);

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
                String decription_city = obj.isNull("duration") ? null : obj
                        .getString("duration");
                model.setDescription(decription_city);
                String id_city = obj.isNull("id") ? null : obj
                        .getString("id");
                model.setId(id_city);
                String fid_city = obj.isNull("fid") ? null : obj
                        .getString("fid");
                model.setFid(fid_city);
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


            rcadapall.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        private String fid;

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
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
    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }

    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name,duration;

        public ImageButton addtoplay;



        public UserViewHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.songsnames);
            duration=(TextView)itemView.findViewById(R.id.duration_time);
            addtoplay=(ImageButton)itemView.findViewById(R.id.play_music_newrelaese);


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
        private boolean loading;
        private OnLoadMoreListener onLoadMoreListener;

        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;

        public RecyclerViewAdapterArticles(List<ItemModel> students, RecyclerView recyclerView) {
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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_music_newrelease, parent, false);
                return new UserViewHolder(view);
            } /*else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }*/

            return null;

        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof UserViewHolder) {

                final UserViewHolder userViewHolder = (UserViewHolder) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);


                ItemModel itemmodel = modelList.get(position);




                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.duration.setText(itemmodel.getDescription());
                userViewHolder.duration.setTypeface(seguiregular);
                userViewHolder.name.setText(itemmodel.getTitle());



            } /*else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
*/
        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }




        public int getItemViewType(int position) {


           // return modelList.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;
            return  VIEW_TYPE_ITEM ;

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }
    }
}

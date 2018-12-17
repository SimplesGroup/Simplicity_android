package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuppusamy on 4/6/2016.
 */
public class RadioprogramsInnerpage extends AppCompatActivity {
   LinearLayoutManager lLayout;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    String URL="http://simpli-city.in/request2.php?rtype=radiopgmdetail&key=simples&cat=";

    View view;

    RequestQueue queue;

    ProgressDialog pdialog;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
  CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerViewAdapter rcAdapter;
    RecyclerView recycler;
    LinearLayoutManager mLayoutManager;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";
    String Urls;
    TextView cattitle,authername;
    NetworkImageView imageView;
    String image,catname,links,authname;
int position;
    ItemModel itemmodel;
    ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.radioprogramsinnerpage);
        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
       // ItemModel itemModel=modelList.get(position);
        lLayout = new LinearLayoutManager(getApplicationContext());
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        Intent get=getIntent();
        String imagesurl=get.getStringExtra("IMAGE");
        String catid=get.getStringExtra("ID");
        Urls=URL+catid;
        cattitle=(TextView)findViewById(R.id.catname_radioproginner);
        authername=(TextView)findViewById(R.id.titlename_radioproginner);
        imageView=(NetworkImageView)findViewById(R.id.thumbnailone);
        recycler = (RecyclerView)findViewById(R.id.radioproginner_recyclerview);
        recycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recycler.setLayoutManager(lLayout);
        pdialog = new ProgressDialog(this);
       // pdialog.show();
        imageView.setImageUrl(imagesurl, mImageLoader);

        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, Urls, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                  //  dissmissDialog();
                    try {
                        feedArray = response.getJSONArray("result");
                        if (feedArray.length() < 10) {
                            for (ii = 0; ii < feedArray.length(); ii++) {
                                obj = (JSONObject) feedArray.get(ii);

                                ItemModel model = new ItemModel();
                                //FeedItem model=new FeedItem();
                                 image = obj.isNull("thumb") ? null : obj
                                        .getString("thumb");
                                model.setImage(image);

                                //model.setId(obj.getString("id"));
                                model.setPdate(obj.getString("pdate"));
                                model.setTitle(obj.getString("title"));
                                //model.setDescription(obj.getString("description"));
                               // model.setLink(obj.getString("link"));
                               // model.setName(obj.getString("source"));
                                model.setPart(obj.getString("part"));
                                model.setCatid(obj.getString("cat_id"));
                                //model.setCatnamme(obj.getString("cat_name"));
                                catname = obj.isNull("cat_name") ? null : obj
                                        .getString("cat_name");
                                model.setCatnamme(catname);
                                links = obj.isNull("link") ? null : obj
                                        .getString("link");
                                model.setLink(links);
                                authname = obj.isNull("source") ? null : obj
                                        .getString("source");
                                model.setName(authname);
                                modelList.add(model);

                            }

                            // notify data changes to list adapater
                            rcAdapter.notifyDataSetChanged();
                        }else {
                            for (ii = 0; ii <= 10; ii++) {
                                obj = (JSONObject) feedArray.get(ii);


                                ItemModel model = new ItemModel();
                                //FeedItem model=new FeedItem();
                                 image = obj.isNull("thumb") ? null : obj
                                        .getString("thumb");
                                model.setImage(image);

                                // model.setId(obj.getString("id"));
                                model.setPdate(obj.getString("pdate"));
                                model.setTitle(obj.getString("title"));
                                //model.setDescription(obj.getString("description"));
                                //  model.setLink(obj.getString("link"));
                               // model.setName(obj.getString("source"));
                                model.setPart(obj.getString("part"));
                                model.setCatid(obj.getString("cat_id"));
                                // model.setCatnamme(obj.getString("cat_name"));
                                catname = obj.isNull("cat_name") ? null : obj
                                        .getString("cat_name");
                                model.setCatnamme(catname);
                                links = obj.isNull("link") ? null : obj
                                        .getString("link");
                                model.setLink(links);
                                authname = obj.isNull("source") ? null : obj
                                        .getString("source");
                                model.setName(authname);

                                modelList.add(model);
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

        MySingleton.getInstance(this).addToRequestQueue(jsonreq);

        rcAdapter = new RecyclerViewAdapter(modelList, recycler);
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


                                image = objtwo.isNull("thumb") ? null : objtwo
                                        .getString("thumb");
                                modelone.setImage(image);

                                // modelone.setId(objtwo.getString("id"));
                                modelone.setPdate(objtwo.getString("pdate"));
                                modelone.setTitle(objtwo.getString("title"));
                                // modelone.setDescription(objtwo.getString("description"));
                                //modelone.setLink(objtwo.getString("link"));
                                //modelone.setName(objtwo.getString("source"));
                                modelone.setPart(objtwo.getString("part"));
                                modelone.setCatid(objtwo.getString("cat_id"));
                                catname = objtwo.isNull("cat_name") ? null : objtwo
                                        .getString("cat_name");
                                modelone.setCatnamme(catname);
                                links = objtwo.isNull("link") ? null : objtwo
                                        .getString("link");
                                modelone.setLink(links);
                                authname = objtwo.isNull("source") ? null : objtwo
                                        .getString("source");
                                modelone.setName(authname);
                                //modelone.setCatnamme(objtwo.getString("cat_name"));


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
        recycler.addOnItemTouchListener(new RadioActivity.RecyclerTouchListener(getApplicationContext(), recycler, new RadioActivity.ClickListener() {
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
                String bitmap = ((ItemModel) modelList.get(position)).getImage();
                String Link = ((ItemModel) modelList.get(position)).getLink();
                String title = ((ItemModel) modelList.get(position)).getTitle();
                String name = ((ItemModel) modelList.get(position)).getName();
                String part = ((ItemModel) modelList.get(position)).getPart();
                String date = ((ItemModel) modelList.get(position)).getPdate();
                String catname= ((ItemModel) modelList.get(position)).getCatnamme();
                String catid = ((ItemModel) modelList.get(position)).getCatid();

                Intent fooddescription = new Intent(getApplicationContext(), RadioPlayerPage.class);
                fooddescription.putExtra("ID", catid);
                fooddescription.putExtra("IMAGE", bitmap);
                fooddescription.putExtra("LINK", Link);
                fooddescription.putExtra("TITLE", title);
                fooddescription.putExtra("NAME", name);
                fooddescription.putExtra("PART", part);
                fooddescription.putExtra("DATE", date);
                fooddescription.putExtra("CATNAME", catname);

                startActivity(fooddescription);
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
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView part_title,title_sub,songduration;
        public ImageView play;

        public UserViewHolder(View itemView) {
            super(itemView);




            part_title = (TextView) itemView.findViewById(R.id.partname_programs_innerpage);
            title_sub = (TextView) itemView.findViewById(R.id.name);
            songduration = (TextView) itemView.findViewById(R.id.timestamp);
            play=(ImageView)itemView.findViewById(R.id.imageViewlitle);

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
MediaPlayer mediaPlayer;
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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_radioprograms_innerpage, parent, false);
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
                mediaPlayer=new MediaPlayer();

                 itemmodel = modelList.get(position);

                cattitle.setText(itemmodel.getPart());
                cattitle.setTypeface(seguiregular);
                authername.setTypeface(seguiregular);
                authername.setText("By" + "" + itemmodel.getName());

                userViewHolder.part_title.setText(itemmodel.getPart());
                userViewHolder.title_sub.setText(itemmodel.getTitle());
                userViewHolder.title_sub.setTypeface(seguiregular);
                userViewHolder.part_title.setTypeface(seguiregular);
              /*  try {
                    mediaPlayer.setDataSource(itemmodel.getLink()); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    mediaPlayer.prepare();

                    // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finalTime = mediaPlayer.getDuration();
                Toast.makeText(getApplicationContext(),finalTime,Toast.LENGTH_SHORT).show();*/
               // userViewHolder.songduration.setText("finalTime");
                userViewHolder.songduration.setTypeface(seguiregular);

                userViewHolder.play.setVisibility(View.VISIBLE);


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


            return modelList.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }
    }

}

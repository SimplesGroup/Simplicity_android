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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
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

/**
 * Created by kuppusamy on 7/4/2016.
 */
public class Notificationcitycenter extends Fragment {
    private LinearLayoutManager lLayout;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=citycenternotification&key=simples&user_id=";
    View view;

    RequestQueue requestQueue;

    private ProgressDialog pdialog;

    RecyclerViewAdapter rcAdapter;
    RecyclerView recycler;

    TextView Toolbartitle;

    RequestQueue requestQueuecity;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID = "myprofileid";
    String myprofileid,URLTWO;
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";

    String contentid,activity;
    public Notificationcitycenter(){

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notificationcitycenter, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");

        }
if(myprofileid==null){
    SharedPreferences.Editor editor = sharedpreferences.edit();
    editor.putString(Activity, "notification");
    editor.putString(CONTENTID, "0");
    editor.commit();
    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
    startActivity(signin);
}else {
    lLayout = new LinearLayoutManager(getActivity());
    requestQueuecity= Volley.newRequestQueue(getActivity());
URLTWO=URL+myprofileid;
    recycler = (RecyclerView) view.findViewById(R.id.recycler_view_citycenter);
    recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    recycler.setLayoutManager(lLayout);
    pdialog = new ProgressDialog(getActivity());

    pdialog.show();
    pdialog.setContentView(R.layout.custom_progressdialog);
    pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppControllers.getInstance().addToRequestQueue(jsonReq);
        requestQueuecity.add(jsonReq);
        // Adding request to volley request queue
        //AppControllers.getInstance().addToRequestQueue(jsonReq);
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


                            String image = objtwo.isNull("image") ? null : objtwo
                                    .getString("image");
                            modelone.setImage(image);

                            modelone.setId(objtwo.getString("cid"));
                            modelone.setPdate(objtwo.getString("pdate"));
                            modelone.setTitle(objtwo.getString("comment"));
                            modelone.setQtype(objtwo.getString("qtype"));
                            modelone.setUserids(objtwo.getString("user_id"));
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
}
        return view;

    }
    private void parseJsonFeed(JSONObject response){
        try {
            feedArray = response.getJSONArray("result");
            if (feedArray.length() < 10) {
                for (ii = 0; ii < feedArray.length(); ii++) {
                    obj = (JSONObject) feedArray.get(ii);

                    ItemModel model = new ItemModel();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("image") ? null : obj
                            .getString("image");
                    model.setImage(image);

                    model.setId(obj.getString("cid"));
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("comment"));
                    model.setQtype(obj.getString("qtype"));
                    model.setUserids(obj.getString("user_id"));
                    modelList.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();
            }else {
                for (ii = 0; ii <= 10; ii++) {
                    obj = (JSONObject) feedArray.get(ii);


                    ItemModel model = new ItemModel();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("image") ? null : obj
                            .getString("image");
                    model.setImage(image);

                    model.setId(obj.getString("cid"));
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("comment"));
                    model.setQtype(obj.getString("qtype"));
                    model.setUserids(obj.getString("user_id"));

                    modelList.add(model);
                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();
            }
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

        private String pdate;
        private String description;
        private String title;

        /******** start the Food category names****/
        private String id;
        /******** start the Food category names****/
        private String qtype;
        String userids;

        public String getUserids() {
            return userids;
        }

        public void setUserids(String userids) {
            this.userids = userids;
        }

        public String getQtype() {
            return qtype;
        }

        public void setQtype(String qtype) {
            this.qtype = qtype;
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

            // im = (ImageView) itemView.findViewById(R.id.imageViewlitle);

            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnail_missednews);


            name = (TextView) itemView.findViewById(R.id.title_missednews);
            date = (TextView) itemView.findViewById(R.id.date_missednews);



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
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_missednews, parent, false);
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


                ItemModel itemmodel = modelList.get(position);



                userViewHolder.name.setText(itemmodel.getTitle());
                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.date.setTypeface(seguiregular);
                userViewHolder.imageview.setImageUrl(itemmodel.getImage(), mImageLoader);
                userViewHolder.date.setText(Html.fromHtml(itemmodel.getPdate()));
                // userViewHolder.im.setVisibility(View.VISIBLE);

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
}

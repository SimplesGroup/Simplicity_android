package simplicity_an.simplicity_an.Tamil.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import simplicity_an.simplicity_an.AppControllers;
import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.DividerItemDecoration;
import simplicity_an.simplicity_an.FeedImageView;
import simplicity_an.simplicity_an.NewsActivity;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 6/15/2016.
 */
public class TamilFragmentInternationalNews extends Fragment {
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    ProgressDialog pdialog;
    RecyclerViewAdapter rcAdapter;
    LinearLayoutManager mLayoutManager;
    RecyclerView recycler;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;

    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO;
    String URL="http://simpli-city.in/request2.php?rtype=news&ntype=2&key=simples&language=2&page=";
    ArrayAdapter<String> adapter;
    private final String TAG_REQUEST = "MY_TAG";
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid,myprofileid;
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";


    private boolean isFragmentLoaded=false;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public TamilFragmentInternationalNews() {

    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded ) {
            isFragmentLoaded = true;
            Log.e("TAB:","INTERNEWS");
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news_international, container, false);
        sharedpreferences =  getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        URLPOSTQTYPE=urlpost;
        recycler = (RecyclerView)view.findViewById(R.id.news_recyclerview_international);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getActivity());

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        pdialog = new ProgressDialog(getActivity());
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdialog.setCanceledOnTouchOutside(false);
        rcAdapter = new RecyclerViewAdapter(modelList,recycler);
        recycler.setAdapter(rcAdapter);
        getData();
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
                       /* int index = modelList.size();
                        int end = index + 9;

                        try {

                            for (i = index; i < end; i++) {
                                objtwo = (JSONObject) feedArray.get(i);
                                ItemModel modelone = new ItemModel();


                                String image = objtwo.isNull("image") ? null : objtwo
                                        .getString("image");
                                modelone.setImage(image);

                                modelone.setTypeid(objtwo.getInt("type"));
                                modelone.setPdate(objtwo.getString("pdate"));
                                modelone.setTitle(objtwo.getString("title"));
                                modelone.setId(objtwo.getString("id"));


                                modelList.add(modelone);
                            }
                            rcAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                        }
*/
                        rcAdapter.setLoaded();
                    }
                }, 2000);
            }
        });

        recycler.addOnItemTouchListener(new NewsActivity.RecyclerTouchListener(getActivity(), recycler, new NewsActivity.ClickListener() {
            public void onClick(View view, int position) {

                int  post = ((ItemModel)modelList.get(position)).getTypeid();
                String bid = ((ItemModel)modelList.get(position)).getAdurl();
                String id = ((ItemModel)modelList.get(position)).getId();
                if(post==4||post==3) {
                    Intent k = new Intent(Intent.ACTION_VIEW);
                    k.setData(Uri.parse(bid));
                    startActivity(k);

                }else {
                    if(myprofileid!=null){
                        Uloaddataserver();
                        Intent i = new Intent(getActivity(), TamilNewsDescription.class);
                        i.putExtra("ID", id);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getActivity(), TamilNewsDescription.class);
                        i.putExtra("ID", id);
                        startActivity(i);
                    }
                }
            }

            public void onLongClick(View view, int position) {

            }
        }));
        return view;
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

    public void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
        }
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

        URLTWO=URL+requestCount;


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

            // Adding request to volley request queue
            jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonReq);
        }
        return jsonReq;
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
                            //  Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
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

                        params.put(KEY_QTYPE, "news");
                        params.put(KEY_POSTID, postid);
                        params.put(KEY_MYUID, myprofileid);
                    }

                }
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
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
                    model.setTypeid(obj.getInt("type"));
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("title"));
                    model.setId(obj.getString("id"));
                    String imageurl = obj.isNull("image_url") ? null : obj
                            .getString("image_url");
                    model.setImageurl(imageurl);
                    String adurl = obj.isNull("ad_url") ? null : obj
                            .getString("ad_url");
                    model.setAdurl(adurl);

                    modelList.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnectivityAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    class ItemModel {
        private int typeid;
        private String name;
        private String image;
        private String bimage;
        private String pdate;
        private String description;
        private String title;
        private String publisher;
        private String company;
        /******** start the Food category names****/
        private String category_name;
        private String source;
        private String source_link;
        private String id;

        private String count,adurl,imageurl;

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

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSource_link() {
            return source_link;
        }

        public void setSource_link(String source_link) {
            this.source_link = source_link;
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

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        /******** start the Food category names****/
        public String getCategory_name() {
            return category_name;
        }

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
        public TextView name;

        public TextView timestamp;

        public NetworkImageView imageview,advertisementimageview;


        public UserViewHolder(View itemView) {
            super(itemView);

            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);

            name = (TextView) itemView.findViewById(R.id.name);
            timestamp = (TextView) itemView
                    .findViewById(R.id.timestamp);



        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
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
    static class ViewitemTwoholder extends RecyclerView.ViewHolder {
        TextView type_two_title,type_two_date;
        FeedImageView feedimage,feed_advertisement;
        ImageView fav_type_two;
        public ViewitemTwoholder(View v) {
            super(v);
            type_two_title = (TextView) itemView.findViewById(R.id.viewitem_typetwo_title);
            type_two_date = (TextView) itemView.findViewById(R.id.viewitem_typetwo_date);
            feedimage=(FeedImageView)itemView.findViewById(R.id.feedImage1);
            // fav_type_two=(ImageView)itemView.findViewById(R.id.imageView_typetwo);


        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
            switch (viewType) {

                case VIEW_TYPE_FEATURE:
                    ViewGroup vImage = (ViewGroup) mInflater.inflate ( R.layout.feed_item_feature, parent, false );
                    ViewitemTwoholder vhImage = new ViewitemTwoholder ( vImage );
                    return vhImage;
                case VIEW_TYPE_ITEM_AD:
                    ViewGroup vGroupone= (ViewGroup) mInflater.inflate(R.layout.feed_item_ar, parent, false);
                    UserViewHolderimage vhGroupone = new UserViewHolderimage(vGroupone);
                    return vhGroupone;
                case VIEW_TYPE_ITEM_AD_SMALL:
                    ViewGroup smallad= (ViewGroup) mInflater.inflate(R.layout.feed_item_small_ad, parent, false);
                    UserViewHoldersmallad small_ad = new UserViewHoldersmallad(smallad);
                    return small_ad;
                case VIEW_TYPE_ITEM:
                    ViewGroup vGroup = (ViewGroup) mInflater.inflate ( R.layout.feed_item, parent, false );
                    UserViewHolder vhGroup = new UserViewHolder ( vGroup );
                    return vhGroup;
                case VIEW_TYPE_LOADING:
                    ViewGroup vGroup0 = (ViewGroup) mInflater.inflate ( R.layout.layout_loading_item, parent, false );
                    LoadingViewHolder vhGroup0 = new LoadingViewHolder ( vGroup0 );
                    return vhGroup0;
            }
return null;
        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if(holder instanceof ViewitemTwoholder){
              /*  if(mImageLoader==null)
                    mImageLoader = VolleySingleton.getInstance().getImageLoader();*/
                mImageLoader = CustomVolleyRequest.getInstance(getActivity()).getImageLoader();
                ItemModel itemmodel=modelList.get(position);
                final ViewitemTwoholder userViewHolder = (ViewitemTwoholder) holder;
                if(itemmodel.getTypeid()==1) {

                    userViewHolder.type_two_title.setText(Html.fromHtml(itemmodel.getTitle()));
                    userViewHolder.type_two_date.setText(itemmodel.getPdate());

                    //Loading Image from URL
                              /*      Picasso.with(NewsActivityTamil.this)
                            .load(itemmodel.getImage())
                            .placeholder(R.drawable.iconlogo)   // optional
                            .error(R.drawable.iconlogo)      // optional
                                             // optional
                            .into(userViewHolder.feedimage);*/
                    userViewHolder.feedimage.setDefaultImageResId(R.mipmap.ic_launcher);
                    userViewHolder.feedimage.setErrorImageResId(R.mipmap.ic_launcher);
                    userViewHolder.feedimage.setAdjustViewBounds(true);
                    userViewHolder.feedimage.setImageUrl(itemmodel.getImage(), mImageLoader);

                    //  userViewHolder.fav_type_two.setVisibility(View.VISIBLE);

                    userViewHolder.feedimage
                            .setResponseObserver(new FeedImageView.ResponseObserver() {
                                @Override
                                public void onError() {
                                }

                                @Override
                                public void onSuccess() {

                                }
                            });
                }

            }else if(holder instanceof UserViewHolderimage){
                final UserViewHolderimage userViewHolderimagess = (UserViewHolderimage) holder;
                ItemModel itemmodel=modelList.get(position);
                if(itemmodel.getTypeid()==4){
                    if (mImageLoader == null)
                        mImageLoader = CustomVolleyRequest.getInstance(getActivity()).getImageLoader();
                    // mImageLoader.get(itemmodel.getImage(), ImageLoader.getImageListener(userViewHolder.feedimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    userViewHolderimagess.feed_advertisement.setImageUrl(itemmodel.getImageurl(), mImageLoader);

                }
            }
            else  if(holder instanceof UserViewHoldersmallad){
                final UserViewHoldersmallad user_smallad = (UserViewHoldersmallad) holder;
                ItemModel itemmodel=modelList.get(position);
                String ids="http://simpli-city.in/smp_interface/images/ads/480665630VDsmall.jpg";
                if(itemmodel.getTypeid()==3){
                    if (mImageLoader == null)
                        mImageLoader = CustomVolleyRequest.getInstance(getActivity()).getImageLoader();
                    // mImageLoader.get(itemmodel.getImage(), ImageLoader.getImageListener(userViewHolder.feedimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    user_smallad.feed_advertisement_small.setImageUrl(itemmodel.getImageurl(), mImageLoader);

                }
            }
          else  if (holder instanceof UserViewHolder) {
                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);

                UserViewHolder userViewHolder = (UserViewHolder) holder;
                mImageLoader = CustomVolleyRequest.getInstance(getActivity()).getImageLoader();

                ItemModel itemmodel = modelList.get(position);



                    userViewHolder.name.setText(Html.fromHtml(itemmodel.getTitle()));
                    userViewHolder.name.setTypeface(seguiregular);
                    userViewHolder.timestamp.setText(itemmodel.getPdate());
                    userViewHolder.timestamp.setTypeface(seguiregular);
                    userViewHolder.imageview.setImageUrl(itemmodel.getImage(),mImageLoader);
                    userViewHolder.imageview.setErrorImageResId(R.mipmap.ic_launcher);
                    userViewHolder.imageview.setDefaultImageResId(R.mipmap.ic_launcher);
                    userViewHolder.imageview.setAdjustViewBounds(true);

                    userViewHolder.name.setVisibility(View.VISIBLE);
                    userViewHolder.timestamp.setVisibility(View.VISIBLE);


                /*if(mImageLoader==null)
                    mImageLoader = VolleySingleton.getInstance().getImageLoader();
*/



                /*userViewHolder.name.setText(itemmodel.getTitle());
                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.timestamp.setText(itemmodel.getPdate());
                userViewHolder.timestamp.setTypeface(seguiregular);
                userViewHolder.imageview.setImageUrl(itemmodel.getImage(),mImageLoader);
                userViewHolder.imageview.setErrorImageResId(R.mipmap.ic_launcher);
                userViewHolder.imageview.setDefaultImageResId(R.mipmap.ic_launcher);
                userViewHolder.imageview.setAdjustViewBounds(true);

                userViewHolder.name.setVisibility(View.VISIBLE);
                userViewHolder.timestamp.setVisibility(View.VISIBLE);*/









            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }




        public int getItemViewType(int position) {


            ItemModel item = modelList.get(position);
            if(item.getTypeid()==1)
            {
                return VIEW_TYPE_FEATURE;
            } else if(item.getTypeid()==4){
                return VIEW_TYPE_FEATURE;
                //  return VIEW_TYPE_ITEM;
            }else if(item.getTypeid()==3){
                return VIEW_TYPE_ITEM_AD_SMALL;
            }
            else {
                return modelList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
            }

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }
    }
}

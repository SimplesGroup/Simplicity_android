package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
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

/**
 * Created by kuppusamy on 2/18/2017.
 */
public class RadioJockey extends Fragment {
    private LinearLayoutManager lLayout;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=radiojockey&key=simples";
    private String KEY_POSTID = "rid";
    private String KEY_MYID = "user_id";
    String URLFAV="http://simpli-city.in/request2.php?rtype=radiofav&key=simples";
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

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public RadioJockey() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.radioshows, container, false);
        sharedpreferences =  getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        URLPOSTQTYPE=urlpost;
        requestQueue = Volley.newRequestQueue(getActivity());
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        lLayout = new LinearLayoutManager(getActivity());
        queue = MySingleton.getInstance(getActivity()).
                getRequestQueue();

        rView = (RecyclerView) view.findViewById(R.id.recycler_view_radioshows);
       //rView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rView.setLayoutManager(layoutManager);
        pdialog = new ProgressDialog(getActivity());


        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getData();
        rView.addOnItemTouchListener(new RadioActivity.RecyclerTouchListener(getActivity(), rView, new RadioActivity.ClickListener() {
            public void onClick(View view, int position) {

                String bitmap = ((ItemModel) modelList.get(position)).getImage();
                String Link= ((ItemModel) modelList.get(position)).getLink();
                String title= ((ItemModel) modelList.get(position)).getTitle();
                String name= ((ItemModel) modelList.get(position)).getName();
                String part= ((ItemModel) modelList.get(position)).getPart();
                String date= ((ItemModel) modelList.get(position)).getPdate();
                String id= ((ItemModel) modelList.get(position)).getId();
                String favid= ((ItemModel) modelList.get(position)).getFavourite();


                postid = ((ItemModel)modelList.get(position)).getId();
                int  post = ((ItemModel)modelList.get(position)).getTypeid();
                String bid = ((ItemModel)modelList.get(position)).getAdurl();
Intent jockey=new Intent(getActivity(),RadioJockeyDetailpage.class);
                jockey.putExtra("ID",postid);
                jockey.putExtra("TITLE",name);
                jockey.putExtra("IMAGE",bitmap);

                startActivity(jockey);
               /* if(post==4||post==3) {
                    Intent k = new Intent(Intent.ACTION_VIEW);
                    k.setData(Uri.parse(bid));
                    startActivity(k);

                }else {
                    if(myprofileid!=null){
                        Uloaddataserver();
                        Intent fooddescription = new Intent(getActivity(), Radioplayeractivity.class);
                        fooddescription.putExtra("ID", id);
                        fooddescription.putExtra("IMAGE", bitmap);
                        fooddescription.putExtra("LINK", Link);
                        fooddescription.putExtra("TITLE", title);
                        fooddescription.putExtra("NAME", name);
                        fooddescription.putExtra("PART", part);
                        fooddescription.putExtra("DATE", date);
                        fooddescription.putExtra("TYPEVALUE",2);
                        fooddescription.putExtra("FAV", favid);
                        startActivity(fooddescription);
                    }else {
                        Intent fooddescription = new Intent(getActivity(), Radioplayeractivity.class);
                        fooddescription.putExtra("ID", id);
                        fooddescription.putExtra("IMAGE", bitmap);
                        fooddescription.putExtra("LINK", Link);
                        fooddescription.putExtra("TITLE", title);
                        fooddescription.putExtra("NAME", name);
                        fooddescription.putExtra("PART", part);
                        fooddescription.putExtra("DATE", date);
                        fooddescription.putExtra("TYPEVALUE",2);
                        fooddescription.putExtra("FAV", favid);
                        startActivity(fooddescription);
                    }
                }*/

            }

            public void onLongClick(View view, int position) {

            }
        }));
        rcAdapter = new RecyclerViewAdapter(modelList,rView);
        rView.setAdapter(rcAdapter);
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

                        getData();


                        rcAdapter.setLoaded();
                    }
                }, 2000);
            }
        });

        return view;
    }
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;
    }

    private JsonObjectRequest getDataFromTheServer(int requestCount) {



        if(myprofileid!=null){
            URLTWO=URL+"&page="+requestCount+"&user_id="+myprofileid;

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

            // Adding request to volley request queue
            requestQueue.add(jsonReq);
        }
        return jsonReq;
    }
    private void parseJsonFeed(JSONObject response) {
        try {
            feedArray = response.getJSONArray("result");

            // if (feedArray.length() < 10) {
            for (ii = 0; ii < feedArray.length(); ii++) {
                obj = (JSONObject) feedArray.get(ii);

                ItemModel model = new ItemModel();
                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);
               // model.setTypeid(obj.getInt("type"));
                model.setId(obj.getString("id"));
              /*  model.setPdate(obj.getString("pdate"));
                model.setTitle(obj.getString("title"));
                // model.setDescription(obj.getString("description"));
                model.setLink(obj.getString("link"));*/
                model.setName(obj.getString("name"));
               /* model.setPart(obj.getString("part"));
                model.setCatid(obj.getString("cat_id"));
                model.setCatnamme(obj.getString("category_name"));
                String descriptions = obj.isNull("description") ? null : obj
                        .getString("description");
                model.setDescription(descriptions);
                String  favs = obj.isNull("fav") ? null : obj
                        .getString("fav");
                model.setFavourite(favs);
                String imageurl = obj.isNull("image_url") ? null : obj
                        .getString("image_url");
                model.setImageurl(imageurl);
                String adurl = obj.isNull("ad_url") ? null : obj
                        .getString("ad_url");
                model.setAdurl(adurl);
*/                modelList.add(model);

                // }

                // notify data changes to list adapater


            }
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

                        params.put(KEY_QTYPE, "radio");
                        params.put(KEY_POSTIDS, postid);
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
        private String favourite;
        String count,type;
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
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getFavourite() {
            return favourite;
        }

        public void setFavourite(String favourite) {
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
    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView type_two_title,type_two_date;
        NetworkImageView feedimage;
        ImageView fav_type_two;
        public UserViewHolder(View itemView) {
            super(itemView);
            type_two_title = (TextView) itemView.findViewById(R.id.viewitem_typetwo_title);
            type_two_date = (TextView) itemView.findViewById(R.id.viewitem_typetwo_date);
            feedimage=(NetworkImageView) itemView.findViewById(R.id.feedImage1);

/*

            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


            title = (TextView) itemView.findViewById(R.id.title_radio);*/
            //date = (TextView) itemView.findViewById(R.id.date_radio);
           // description = (TextView) itemView.findViewById(R.id.desc_radio);
           // favourite=(ImageButton) itemView.findViewById(R.id.addfav_button);

        }
    }

  /*  static class UserViewHoldersmallad extends RecyclerView.ViewHolder {

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
    }*/

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
   /* static class ViewitemTwoholder extends RecyclerView.ViewHolder {
        TextView type_two_title,type_two_date;
        FeedImageView feedimage;
        ImageView fav_type_two;
        public ViewitemTwoholder(View v) {
            super(v);
            type_two_title = (TextView) itemView.findViewById(R.id.viewitem_typetwo_title);
            type_two_date = (TextView) itemView.findViewById(R.id.viewitem_typetwo_date);
            feedimage=(FeedImageView)itemView.findViewById(R.id.feedImage1);
            // fav_type_two=(ImageView)itemView.findViewById(R.id.imageView_typetwo);
        }
    }*/
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
            switch (viewType) {


                case VIEW_TYPE_ITEM:
                    ViewGroup vGroup = (ViewGroup) mInflater.inflate(R.layout.feed_iem_radiojockey, parent, false);
                    UserViewHolder vhGroup = new UserViewHolder(vGroup);
                    return vhGroup;
               /* case VIEW_TYPE_ITEM_AD:
                    ViewGroup ad=(ViewGroup)mInflater.inflate(R.layout.feed_item_ar,parent,false);
                    UserViewHolderimage adbig=new UserViewHolderimage(ad);
                    return adbig;
                case VIEW_TYPE_ITEM_AD_SMALL:
                    ViewGroup adsmall=(ViewGroup)mInflater.inflate(R.layout.feed_item_small_ad,parent,false);
                    UserViewHoldersmallad ad_small=new UserViewHoldersmallad(adsmall);
                    return ad_small;*/
                case VIEW_TYPE_LOADING:
                    ViewGroup vGroup0 = (ViewGroup) mInflater.inflate(R.layout.layout_loading_item, parent, false);
                    LoadingViewHolder vhGroup0 = new LoadingViewHolder(vGroup0);
                    return vhGroup0;
            }
            return  null;
        }

        private ItemModel getItem(int position) {
            return modelList.get(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
          if(holder instanceof UserViewHolder){
              if (mImageLoader == null)
                  mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
              ItemModel itemmodel = modelList.get(position);
              final UserViewHolder userViewHolder = (UserViewHolder) holder;
userViewHolder.type_two_title.setText(itemmodel.getName());
                userViewHolder.feedimage.setImageUrl(itemmodel.getImage(),mImageLoader);


            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }

        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }


        @Override
        public int getItemViewType(int position) {
            ItemModel item = modelList.get(position);
           /* if(item.getTypeid()==1)
            {
                return VIEW_TYPE_FEATURE;
            } else if(item.getTypeid()==4){
                return VIEW_TYPE_ITEM_AD;
                //  return VIEW_TYPE_ITEM;
            }else if(item.getTypeid()==3){
                return VIEW_TYPE_ITEM_AD_SMALL;
            } else {*/
                return modelList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
            //}

        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        private boolean isPositionFooter(int position) {
            return position == modelList.size() + 1;
        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }
    }
}

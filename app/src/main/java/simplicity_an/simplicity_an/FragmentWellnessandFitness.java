package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuppusamy on 2/3/2016.
 */
public class FragmentWellnessandFitness extends Fragment {
    private LinearLayoutManager lLayout;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=healthliving&key=simples&htype=2";
    private List<ItemModel> citycatmodelList=new ArrayList<ItemModel>();
    View view;

    RequestQueue queue;
    private ListView listview;
    private ProgressDialog pdialog;

    RecyclerViewAdapter rcAdapter;
    RecyclerView recycler;

    TextView Toolbartitle;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    ArrayAdapter<String> SpinnerAdapter;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    String URLTWO,myprofileid;
    int ii,i;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String imageprofile;
    String id_city_category;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    Spinner spin;
    private ViewPager mPager;
    ArrayList<String> citycategorylist = new ArrayList<String>();
    String CITY_CAT_URL="http://simpli-city.in/request2.php?rtype=healthcategory&key=simples";
    public FragmentWellnessandFitness() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdoctorszone, container, false);
        mCoordinator = (CoordinatorLayout)getActivity(). findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_layout);

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {
            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
            Log.e("MUUSERID:",myprofileid);

        }
        requestQueue = Volley.newRequestQueue(getActivity());
        final String fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);

        mPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
        spin=(Spinner)getActivity().findViewById(R.id.my_spinner);
        citycategorylist.add("Healthy Living");
        getData();
        JsonArrayRequest jsonReq = new JsonArrayRequest(CITY_CAT_URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        ItemModel model = new ItemModel();

                        model.setId(obj.getString("id"));
                        citycatmodelList.add(model);

                        citycategorylist.add(obj.getString("category_name"));

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
        SpinnerAdapter =new ArrayAdapter<String>(
                getActivity(),
                R.layout.view_spinner_item,
                citycategorylist
        ){
            //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,
                        parent);

                GradientDrawable gd = new GradientDrawable();


                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(14);
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setPadding(50, 0, 10, 0);
                ((TextView) v).setCompoundDrawables(null,null,null,null);
                return v;
            }
        };
        // spin.setAdapter(adapter);
        //SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(SpinnerAdapter);  // Set Adapter in the spinner

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    // Toast.makeText(getActivity(),"wworked",Toast.LENGTH_LONG).show();
                    id_city_category="";
                    mPager.setCurrentItem(0);
                    requestCount=1;
                    modelList.clear();
                    getData();

                    mPager.setCurrentItem(0);
                } else {
                    int pos=position-1;
                    id_city_category = ((ItemModel)citycatmodelList.get(pos)).getId();
                    mPager.setCurrentItem(0);
                    requestCount=1;
                    modelList.clear();
                    getData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        lLayout = new LinearLayoutManager(getActivity());
        queue =  MySingleton.getInstance(getActivity()).
                getRequestQueue();

        recycler = (RecyclerView) view.findViewById(R.id.recycler_view_doctorszone);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setLayoutManager(lLayout);
        pdialog = new ProgressDialog(getActivity());


        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        recycler.addOnItemTouchListener(new HealthAndLiving.RecyclerTouchListener(getActivity(),recycler,new HealthAndLiving.ClickListener() {
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
                        //  Uloaddataserver();
                        Intent i = new Intent(getActivity(), Healthylivingdescription.class);
                        i.putExtra("ID", id);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getActivity(), Healthylivingdescription.class);
                        i.putExtra("ID", id);
                        startActivity(i);
                    }
                }
            }
            public void onLongClick(View view, int position) {

            }
        }));
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


    private
    JsonObjectRequest getDataFromTheServer( int requestCount) {

            if (id_city_category!=null){
                URLTWO=URL+"&page="+requestCount+"&hid="+id_city_category;
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

            jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
                //FeedItem model=new FeedItem();
                String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
                model.setImage(image);

                model.setId(obj.getString("id"));
                model.setTypeid(obj.getInt("type"));
                model.setPdate(obj.getString("pdate"));
                model.setTitle(obj.getString("title"));
                String imageurl = obj.isNull("image_url") ? null : obj
                        .getString("image_url");
                model.setImageurl(imageurl);
                String adurl = obj.isNull("ad_url") ? null : obj
                        .getString("ad_url");
                model.setAdurl(adurl);
                modelList.add(model);

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
        /******** start the Food category names****/
        private String id;
        /******** start the Food category names****/
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
        public TextView name;
        public TextView date;
        public TextView article;
        public TextView timestamp;
        public TextView publishername;
        public TextView description;
        public TextView publish;
        public TextView company;
        public ImageView im;
        public ImageView im1;

        public NetworkImageView imageview;
        public FeedImageView feedImageView;
        public UserViewHolder(View itemView) {
            super(itemView);

            // im = (ImageView) itemView.findViewById(R.id.imageView);
            // im1 = (ImageView)itemView.findViewById(R.id.imageViewlitle);
            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
            //feedImageView = (FeedImageView) itemView.findViewById(R.id.feedImage1);

            //article=(TextView)itemView.findViewById(R.id.food_name);
            name = (TextView) itemView.findViewById(R.id.name);
            timestamp = (TextView)itemView
                    .findViewById(R.id.timestamp);
            //date=(TextView)itemView.findViewById(R.id.releaseYear);
            // date.setPadding(7, 7, 7, 7);


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
    static class ViewitemTwoholder extends RecyclerView.ViewHolder {
        TextView type_two_title,type_two_date;
        FeedImageView feedimage;
        ImageView fav_type_two;
        public ViewitemTwoholder(View v) {
            super(v);
            type_two_title = (TextView) itemView.findViewById(R.id.viewitem_typetwo_title);
            type_two_date = (TextView) itemView.findViewById(R.id.viewitem_typetwo_date);
            feedimage=(FeedImageView)itemView.findViewById(R.id.feedImage1);
            //fav_type_two=(ImageView)itemView.findViewById(R.id.imageView_typetwo);
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

                case VIEW_TYPE_FEATURE:
                    ViewGroup vImage = (ViewGroup) mInflater.inflate(R.layout.feed_item_feature, parent, false);
                    ViewitemTwoholder vhImage = new ViewitemTwoholder(vImage);
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
                    ViewGroup vGroup = (ViewGroup) mInflater.inflate(R.layout.feed_item, parent, false);
                    UserViewHolder vhGroup = new UserViewHolder(vGroup);
                    return vhGroup;
                case VIEW_TYPE_LOADING:
                    ViewGroup vGroup0 = (ViewGroup) mInflater.inflate(R.layout.layout_loading_item, parent, false);
                    LoadingViewHolder vhGroup0 = new LoadingViewHolder(vGroup0);
                    return vhGroup0;
            }
            return null;
        }

        private ItemModel getItem(int position) {
            return modelList.get(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewitemTwoholder) {
                if (mImageLoader == null)
                    mImageLoader =  MySingleton.getInstance(getActivity()).getImageLoader();
                ItemModel itemmodel = modelList.get(position);
                final ViewitemTwoholder userViewHolder = (ViewitemTwoholder) holder;
                if (itemmodel.getTypeid() == 1) {

                    userViewHolder.type_two_title.setText(itemmodel.getTitle());
                    userViewHolder.type_two_date.setText(itemmodel.getPdate());
                    userViewHolder.feedimage.setDefaultImageResId(R.drawable.iconlogo);
                    userViewHolder.feedimage.setErrorImageResId(R.drawable.iconlogo);
                    userViewHolder.feedimage.setAdjustViewBounds(true);
                    userViewHolder.feedimage.setImageUrl(itemmodel.getImage(), mImageLoader);
                    // userViewHolder.fav_type_two.setVisibility(View.VISIBLE);

                    userViewHolder.feedimage
                            .setResponseObserver(new FeedImageView.ResponseObserver() {
                                @Override
                                public void onError() {
                                }

                                @Override
                                public void onSuccess() {

                                }
                            });
                } else {
                    userViewHolder.type_two_title.setVisibility(View.GONE);
                    userViewHolder.type_two_date.setVisibility(View.GONE);
                    userViewHolder.feedimage.setVisibility(View.GONE);
                    // userViewHolder.fav_type_two.setVisibility(View.GONE);

                }
            } else if(holder instanceof UserViewHolderimage){
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
            }else if (holder instanceof UserViewHolder) {

                final UserViewHolder userViewHolder = (UserViewHolder) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                if (mImageLoader == null)
                    mImageLoader =  MySingleton.getInstance(getActivity()).getImageLoader();


                ItemModel itemmodel = modelList.get(position);



                userViewHolder.name.setText(itemmodel.getTitle());
                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.timestamp.setText(itemmodel.getPdate());
                userViewHolder.timestamp.setTypeface(seguiregular);
                userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);
                userViewHolder.imageview.setAdjustViewBounds(true);
                userViewHolder.imageview.setImageUrl(itemmodel.getImage(), mImageLoader);

                // userViewHolder.im1.setVisibility(View.VISIBLE);

                // userViewHolder.im.setVisibility(View.GONE);
                // userViewHolder.article.setVisibility(View.GONE);
                // userViewHolder.date.setVisibility(View.GONE);


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
            if(item.getTypeid()==1)
            {
                return VIEW_TYPE_FEATURE;
            } else if(item.getTypeid()==4){
                return VIEW_TYPE_ITEM_AD;
                //  return VIEW_TYPE_ITEM;
            }else if(item.getTypeid()==3){
                return VIEW_TYPE_ITEM_AD_SMALL;
            } else {
                return modelList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
            }
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

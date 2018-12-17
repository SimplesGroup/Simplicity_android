
package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;


public class DoitYourself extends AppCompatActivity {
    ArrayAdapter<String> SpinnerAdapter;
    private Spinner spin;
    ArrayList<String> worldlist = new ArrayList<String>();
    private LinearLayoutManager lLayout;
     List<ItemModel> modelList=new ArrayList<ItemModel>();
    List<ItemModel> modelListspin=new ArrayList<ItemModel>();
     ProgressDialog pdialog;
    View view;
    RecyclerViewAdapter rcAdapter;
    private String URL="http://simpli-city.in/request2.php?rtype=foodnew&key=simples&fid=All";
    private String URLTW="http://simpli-city.in/request2.php?rtype=doitcategory&key=simples";
    private String URLTHREE;
    private String URLFOUR;
    String id_food;
    ImageButton back,menu,search,notification;
    Toolbar mToolbar;
    RequestQueue queue;

    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
   CollapsingToolbarLayout mCollapsingToolbarLayout;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";

    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid;
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid;
    int value;
    String notification_counts;
    static NetworkInfo activeNetwork;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.doityourself);
        notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");

        }
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

        spin=(Spinner)findViewById(R.id.my_spinnerdoityourself);
        worldlist.add("Do it yourself");
       queue= MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        notification=(ImageButton)findViewById(R.id.btn_doitprofile) ;
        menu=(ImageButton)findViewById(R.id.btn_doithome);
        back=(ImageButton)findViewById(R.id.btn_doitback);
        search=(ImageButton)findViewById(R.id.btn_doitsearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent search_intent=new Intent(getApplicationContext(),DoitSearchview.class);
               startActivity(search_intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent backdoitmain=new Intent(getApplicationContext(),MainActivityVersiontwo.class);
                startActivity(backdoitmain);
                finish();*/
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menudoitmain=new Intent(getApplicationContext(), MainPageEnglish.class);
                startActivity(menudoitmain);
                finish();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(DoitYourself.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(DoitYourself.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });

        lLayout = new LinearLayoutManager(this);

        RecyclerView rView = (RecyclerView) findViewById(R.id.pick_itemdoityouself);
        rView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rView.setLayoutManager(lLayout);


        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdialog.setCanceledOnTouchOutside(false);

        rView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rView, new ClickListener() {
            public void onClick(View view, int position) {
                String bid = ((ItemModel) modelList.get(position)).getId();


                postid = ((ItemModel)modelList.get(position)).getId();

                if(myprofileid!=null){
                    Uloaddataserver();
                    Intent fooddescription = new Intent(getApplicationContext(), DoitDescription.class);
                    fooddescription.putExtra("ID", bid);


                    startActivity(fooddescription);
                }else {
                    Intent fooddescription = new Intent(getApplicationContext(), DoitDescription.class);
                    fooddescription.putExtra("ID", bid);


                    startActivity(fooddescription);
                }

            }

            public void onLongClick(View view, int position) {

            }
        }));

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    int pos = position - 1;
                    id_food = ((ItemModel) modelListspin.get(pos)).getId();
                    URLFOUR = "http://simpli-city.in/request2.php?rtype=doityourself&key=simples&did=" + id_food;
                    modelList.clear();
                    jsontwo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        URLTHREE="http://simpli-city.in/request2.php?rtype=doityourself&key=simples";
        json();
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        JsonArrayRequest arrayReq=new JsonArrayRequest(URLTW, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub

                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj=response.getJSONObject(i);
                        ItemModel model=new ItemModel();
                        model.setId(obj.getString("id"));
                        modelListspin.add(model);
                        worldlist.add(obj.getString("category_name"));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                SpinnerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                // VolleyLog.d(TAG, "ERROR" + error.getMessage());
            }
        });
        arrayReq.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        arrayReq.setTag(TAG_REQUEST);

        MySingleton.getInstance(this).addToRequestQueue(arrayReq);


        SpinnerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.view_spinner_item,
                worldlist
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
                ((TextView) v).setCompoundDrawables(null, null, null, null);
                return v;
            }
        };
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(SpinnerAdapter);

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

                        //Remove loading item
                        // modelList.remove(modelList.size() - 1);
                        // rcAdapter.notifyItemRemoved(modelList.size());

                        //Load data
                        int index = modelList.size();
                        int end = index + 9;

                        try {

                            for (i = index; i < end; i++) {
                                objtwo = (JSONObject) feedArray.get(i);
                                ItemModel modelone = new ItemModel();


                                String image = objtwo.isNull("thumb") ? null : objtwo
                                        .getString("thumb");
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

    private void json(){
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTHREE, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
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
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonreq.setTag(TAG_REQUEST);

        MySingleton.getInstance(this).addToRequestQueue(jsonreq);
        //AppControllers.getInstance().addToRequestQueue(jsonreq);

    }
    private void jsontwo(){
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLFOUR, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
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
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonreq.setTag(TAG_REQUEST);

        MySingleton.getInstance(this).addToRequestQueue(jsonreq);

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

                        params.put(KEY_QTYPE, "doit");
                        params.put(KEY_POSTID, postid);
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

    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }

    }
    private void parseJsonFeed(JSONObject response) {
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

                    model.setTypeid(obj.getInt("type"));
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("title"));
                    model.setId(obj.getString("id"));

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

                    model.setTypeid(obj.getInt("type"));
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("title"));
                    model.setId(obj.getString("id"));


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
        /******** start the Food category names****/
        private String id;
        String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
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
            //im1 = (ImageView)itemView.findViewById(R.id.imageViewlitle);
            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
           // feedImageView = (FeedImageView) itemView.findViewById(R.id.feedImage1);


            name = (TextView) itemView.findViewById(R.id.name);
            timestamp = (TextView)itemView
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
    static class ViewitemTwoholder extends RecyclerView.ViewHolder {
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
    }
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        ImageLoader  mImageLoader;
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
            LayoutInflater mInflater = LayoutInflater.from ( parent.getContext () );
            switch ( viewType ) {

                case VIEW_TYPE_FEATURE:
                    ViewGroup vImage = (ViewGroup) mInflater.inflate ( R.layout.feed_item_feature, parent, false );
                    ViewitemTwoholder vhImage = new ViewitemTwoholder ( vImage );
                    return vhImage;
                case VIEW_TYPE_ITEM:
                    ViewGroup vGroup = (ViewGroup) mInflater.inflate ( R.layout.feed_item, parent, false );
                    UserViewHolder vhGroup = new UserViewHolder ( vGroup );
                    return vhGroup;
                default:
                    ViewGroup vGroup0 = (ViewGroup) mInflater.inflate ( R.layout.layout_loading_item, parent, false );
                    LoadingViewHolder vhGroup0 = new LoadingViewHolder ( vGroup0 );
                    return vhGroup0;
            }
        }
        private ItemModel getItem (int position) {
            return modelList.get (position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ViewitemTwoholder){
                if(mImageLoader==null)
                    mImageLoader = MySingleton.getInstance(DoitYourself.this).getImageLoader();
                ItemModel itemmodel=modelList.get(position);
                final ViewitemTwoholder userViewHolder = (ViewitemTwoholder) holder;
                if(itemmodel.getTypeid()==1) {

                    userViewHolder.type_two_title.setText(itemmodel.getTitle());
                    userViewHolder.type_two_date.setText(itemmodel.getPdate());
                    userViewHolder.feedimage.setImageUrl(itemmodel.getImage(), mImageLoader);
                  //  userViewHolder.fav_type_two.setVisibility(View.VISIBLE);
                    userViewHolder.feedimage.setDefaultImageResId(R.mipmap.ic_launcher);
                    userViewHolder.feedimage.setErrorImageResId(R.mipmap.ic_launcher);
                    userViewHolder.feedimage
                            .setResponseObserver(new FeedImageView.ResponseObserver() {
                                @Override
                                public void onError() {
                                }

                                @Override
                                public void onSuccess() {

                                }
                            });
                }else {
                    userViewHolder.type_two_title.setVisibility(View.GONE);
                    userViewHolder.type_two_date.setVisibility(View.GONE);
                    userViewHolder.feedimage.setVisibility(View.GONE);
                   // userViewHolder.fav_type_two.setVisibility(View.GONE);

                }
            }
            else if (holder instanceof UserViewHolder) {

                final UserViewHolder userViewHolder = (UserViewHolder) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                if(mImageLoader==null)
                    mImageLoader = MySingleton.getInstance(DoitYourself.this).getImageLoader();


                ItemModel itemmodel=modelList.get(position);

                userViewHolder.name.setText(itemmodel.getTitle());
                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.timestamp.setText(itemmodel.getPdate());
                userViewHolder.timestamp.setTypeface(seguiregular);
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


        @Override
        public int getItemViewType (int position) {
            if(isPositionHeader (position)) {
                return VIEW_TYPE_FEATURE;
            } else if(isPositionFooter (position)) {
                return VIEW_TYPE_LOADING;
            }
            return VIEW_TYPE_ITEM;
        }

        private boolean isPositionHeader (int position) {
            return position == 0;
        }

        private boolean isPositionFooter (int position) {
            return position == modelList.size () + 1;
        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return  modelList.size();
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


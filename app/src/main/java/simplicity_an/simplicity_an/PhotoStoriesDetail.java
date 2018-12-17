package simplicity_an.simplicity_an;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.Utils.Configurl;
import simplicity_an.simplicity_an.Utils.Fonts;

/**
 * Created by KuppuSamy on 8/10/2017.
 */

public class PhotoStoriesDetail extends AppCompatActivity {
    NetworkImageView post_view_image_big;

    String images_to_load;
    private List<ItemModel> modelListlikes = new ArrayList<ItemModel>();
    private String URL = "http://simpli-city.in/request2.php?rtype=photostories&key=simples&pid=";
    private ListView listview;
    private ProgressDialog pdialog;

    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
   RecyclerViewAdapter rcAdapter;
    private String URLTWO;
    String bimage;
    RecyclerView recycler;
    private Toolbar mToolbar;
    TextView Toolbartitle;
    ImageButton  search;
    LinearLayoutManager mLayoutManager;

    protected Handler handler;

    RequestQueue queues;
    TextView photostory_title, photostory_date;
    ImageLoader mImageLoader;
    ImageButton closedialog;
    RequestQueue requestQueue;
    String likes_to_load, date, title;
    ImageButton comment,share,menu,back,favourite;
    String URLFAV="http://simpli-city.in/request2.php?rtype=addfav&key=simples";

    SharedPreferences sharedpreferences,shredpreferencemyactivity;
    public static final String mypreference = "mypref";
    public static final String myACTIVITY = "myactivity";
    public static final String MYUSERID= "myprofileid";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid,activity,searchnonitiid,searchactivity_article;
    public static final String MYACTIVITYSEARCH= "myactivitysearch";
    public static final String MYACTIVITYSEARCHVALUE= "myactivitysearchvalue";

    LinearLayout commentbox;
    TextView comment_title,loadmore_title;
    EditText commentbox_editext;
    Button post;
    RecyclerView recycler_comment;
    private String KEY_QTYPE="qtype";
    private String KEY_COMMENT = "comment";
    private String KEY_TYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=photostories&id=";
    String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";
    String URLLIKES="http://simpli-city.in/request2.php?rtype=articlelikes&key=simples";
    public static final String USERID="user_id";
    public static final String QID="qid";
    public static final String QTYPE="qtype";
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii, i;
    TextView titles;
    CoordinatorLayout mCoordinator;
    private final String TAG_REQUEST = "MY_TAG";
    List<ItemModels> commentlist = new ArrayList<ItemModels>();
    int post_likes_count;
    String sourcelink,myprofileid,my_profilename,my_profileimage;
    RecyclerViewAdaptercomment adaptercomment;
    ScrollView scrollView;
    String description_comment, URLTWO_comment;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String colorcodes;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    LinearLayout commentlayout;
    String fontname;
    Typeface tf_play;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.photostoriesmultipleimagesshow);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        searchactivity_article=sharedpreferences.getString(MYACTIVITYSEARCH,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
            contentid=sharedpreferences.getString(CONTENTID,"");
            activity=sharedpreferences.getString(Activity,"");
        }
        if (sharedpreferences.contains(USERNAME)) {

            my_profilename=sharedpreferences.getString(USERNAME,"");

        }
        if (sharedpreferences.contains(USERIMAGE)) {

            my_profileimage=sharedpreferences.getString(USERIMAGE,"");

        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        Log.e("Colorcodes","photo"+colorcodes);
        mainlayout=(RelativeLayout)findViewById(R.id.main_layout_explore);
        commentlayout=(LinearLayout) findViewById(R.id.commentbox_city);
        scrollView=(ScrollView)findViewById(R.id.scroll);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        String simplycity_title_fontPathone = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPathone);


        share = (ImageButton) findViewById(R.id.btn_share);
        back = (ImageButton) findViewById(R.id.btn_back);
        favourite = (ImageButton) findViewById(R.id.btn_like);
        commentbox=(LinearLayout)findViewById(R.id.comments_versiontwo) ;
        comment_title=(TextView)findViewById(R.id.comments_title);
        loadmore_title=(TextView)findViewById(R.id.loadmore);
        commentbox_editext=(EditText)findViewById(R.id.comment_description);
        post=(Button)findViewById(R.id.post_button) ;
        recycler_comment=(RecyclerView)findViewById(R.id.commentpagelist_recyclerview) ;
        recycler_comment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        comment_title.setTypeface(tf);
        loadmore_title.setTypeface(tf);
        post.setTypeface(tf);
        commentbox_editext.setHint("Comments Here");
        closedialog = (ImageButton) findViewById(R.id.closebigimage);
        Intent get=getIntent();
        likes_to_load = get.getStringExtra("Image");
        date = get.getStringExtra("DATE");
        title = get.getStringExtra("TITLE");
        URLTWO = URL + likes_to_load;
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
       back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(colorcodes.equals("#FFFFFFFF")){
            commentlayout.setBackgroundColor(Color.WHITE);
            back.setImageResource(R.mipmap.backtamilone);
        }
        else{
            commentlayout.setBackgroundColor(Color.BLACK);
            back.setImageResource(R.mipmap.back);
        }
        if (colorcodes.length() == 0) {
            int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);

            mainlayout.setBackgroundDrawable(gd);

            SharedPreferences.Editor editor = sharedpreferences.edit();
           editor.putString(backgroundcolor, "#262626");
            editor.commit();
        } else {
            if (colorcodes.equalsIgnoreCase("004")) {
                Log.e("Msg", "hihihi" + colorcodes);
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);

                SharedPreferences.Editor editor = sharedpreferences.edit();
               editor.putString(backgroundcolor, "#262626");
                editor.commit();
            } else {

                if (colorcodes != null) {
                    if(colorcodes.equals("#FFFFFFFF")){
                        int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FFFFFFFF")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(backgroundcolor, "#FFFFFFFF");
                        editor.commit();
                    } else {
                        int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                       editor.putString(backgroundcolor, "#262626");

                        editor.commit();
                    }
                } else {
                    int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                   editor.putString(backgroundcolor, "#262626");

                    editor.commit();
                }
            }
        }




        if(myprofileid!=null) {
          // URLTWO_comment=URLCOMMENT+notifiid;
          //comment_title.setText("Post your Comment Here - ");
          post.setText("Post");
          loadmore_title.setText("Load More");
          commentbox.setVisibility(View.VISIBLE);

          post.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                  imm.hideSoftInputFromWindow(commentbox_editext.getWindowToken(), 0);
                  /*pdialog = new ProgressDialog(getApplicationContext());
                  pdialog.show();
                  pdialog.setContentView(R.layout.custom_progressdialog);
                  pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
                  if(myprofileid!=null) {

                      try {

                          StringRequest comment_post_request = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                              @Override
                              public void onResponse(String response) {
                                  Log.e("Res", response.toString().trim());
                                    //pdialog.dismiss();
                                  if (response.equalsIgnoreCase("error")) {
                                      Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                  } else {

                                      commentbox_editext.setText("");
                                      AddnewCommnent();
                                      scrollView.post(new Runnable() {
                                          public void run() {
                                              scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                          }
                                      });

                                  }
                              }
                          }, new Response.ErrorListener() {
                              @Override
                              public void onErrorResponse(VolleyError error) {

                              }
                          }) {
                              @Override
                              protected Map<String, String> getParams() throws AuthFailureError {
                                  description_comment = commentbox_editext.getText().toString().trim();


                                  Map<String, String> param = new Hashtable<String, String>();
                                  String keytepe = "article";
                                  Log.e("qty", keytepe);
                                  param.put("Key", "Simplicity");
                                  param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                                  param.put("rtype", "comment");
                                  param.put("language", "1");
                                  param.put("id", likes_to_load);
                                  param.put("user_id", myprofileid);
                                  param.put("comment", description_comment);
                                  param.put("qtype", "photostories");
                                  return param;
                              }
                          };
                          RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                          requestQueue.add(comment_post_request);
                      } catch (Exception e) {

                      }
                  }else {
                      Intent signin=new Intent(getApplicationContext(),SigninpageActivity.class);
                      startActivity(signin);
                      finish();
                  }





              }

          });

          loadmore_title.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                  MyDialogFragment frag;
                  frag = new MyDialogFragment();
                  Bundle args = new Bundle();
                  args.putString("POSTID", likes_to_load);
                  args.putString("USERID", myprofileid);
                  frag.setArguments(args);
                  frag.show(ft, "txn_tag");
              }
          });

      }else {
          commentbox.setVisibility(View.GONE);
      }

            photostory_title = (TextView) findViewById(R.id.title);
            photostory_date = (TextView) findViewById(R.id.date);


        if(fontname.equals("sanfrancisco")){
            String playfair ="fonts/Oxygen-Bold.ttf";
            tf_play = Typeface.createFromAsset(getApplicationContext().getAssets(), playfair);

            photostory_title.setTextSize(20);
            photostory_title.setLineSpacing(0,1f);
        }else {
            String playfair = "fonts/playfairDisplayRegular.ttf";
            tf_play = Typeface.createFromAsset(getApplicationContext().getAssets(), playfair);

        }
        photostory_date.setTypeface(tf);
        photostory_title.setTypeface(tf_play);
        if(colorcodes.equals( "#FFFFFFFF")){
            photostory_date.setTextColor(Color.BLACK);
            photostory_title.setTextColor(Color.BLACK);
            commentbox.setBackgroundColor(Color.WHITE);
            post.setTextColor(Color.BLACK);
            comment_title.setTextColor(Color.BLACK);
            back.setImageResource(R.mipmap.backtamilone);
            commentlayout.setBackgroundColor(Color.WHITE);

        }
        else{
            photostory_date.setTextColor(Color.WHITE);
            photostory_title.setTextColor(Color.WHITE);
            commentbox.setBackgroundColor(Color.BLACK);
            post.setTextColor(Color.WHITE);
            comment_title.setTextColor(Color.WHITE);
            back.setImageResource(R.mipmap.back);
            commentlayout.setBackgroundColor(Color.BLACK);
        }
          /*  if (photostory_title != null) {
                photostory_title.setText(Html.fromHtml(title));
            } else {

            }
            if (photostory_date != null) {
                photostory_date.setText(Html.fromHtml(date));
            } else {

            }*/
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recycler = (RecyclerView) findViewById(R.id.likes_recyclerview);
            recycler.setNestedScrollingEnabled(false);

            recycler.setLayoutManager(layoutManager);
        /*pdialog = new ProgressDialog(getApplicationContext());
//        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
           StringRequest jsonreq = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {


                public void onResponse(String response) {


                    if (response != null) {
                        Log.e("RES", response.toString());
                        dissmissDialog();
                       try {
                           JSONObject object1=new JSONObject(response.toString());
                           JSONArray array=object1.getJSONArray("result");
                           String data=array.optString(1);
                           JSONArray jsonArray=new JSONArray(data.toString());
                           JSONObject object=null;
                           // JSONArray array = response.getJSONArray("general");
                            for (int k = 0; k < jsonArray.length(); k++) {
                                 object = (JSONObject) jsonArray.get(k);
                                photostory_title.setText(Html.fromHtml(object.getString("title")));
                                photostory_title.setTypeface(tf_play);
                                photostory_date.setText(Html.fromHtml(object.getString("photo_credit")) +"\n"+Html.fromHtml(object.getString("date")));



                           feedArray = object.getJSONArray("album");
                                for (ii = 0; ii < feedArray.length(); ii++) {
                                  //  obj = (JSONObject) feedArray.get(ii);

                                    ItemModel model = new ItemModel();



                                  String images=feedArray.optString(ii);
                                  Log.e("IMG",images.toString());

                                    model.setImage(images);
                                    modelListlikes.add(model);



                                // notify data changes to list adapater
                               /* rcAdapter.notifyDataSetChanged();
                            } else {
                                for (ii = 0; ii <= 10; ii++) {
                                    obj = (JSONObject) feedArray.get(ii);


                                    ItemModel model = new ItemModel();


                                    String images=feedArray.optString(ii);
                                    Log.e("IMG",images.toString());

                                    model.setImage(images);

                                    modelListlikes.add(model);
                                }*/
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
            }){
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String>param=new HashMap<>();
                   param.put("Key","Simplicity");
                   param.put("Token","8d83cef3923ec6e4468db1b287ad3fa7");
                   param.put("language","1");
                   param.put("rtype","photostories");
                   param.put("id",likes_to_load);
                   if(myprofileid!=null){
                       param.put("user_id",myprofileid);
                   }else {

                   }
                   return param;
               }
           };
            jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonreq);
            rcAdapter = new RecyclerViewAdapter(modelListlikes, recycler);
            recycler.setAdapter(rcAdapter);
            rcAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    Log.e("haint", "Load More");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("haint", "Load More 2");


                           /* //Load data
                            int index = modelListlikes.size();
                            int end = index + 9;

                            try {

                                for (i = index; i < end; i++) {
                                    objtwo = (JSONObject) feedArray.get(i);
                                    ItemModel modelone = new ItemModel();

                                    String images=feedArray.optString(ii);
                                    Log.e("IMG",images.toString());

                                    modelone.setImage(images);

                                    modelListlikes.add(modelone);
                                }
                                rcAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {

                            }

                            rcAdapter.setLoaded();*/
                        }
                    }, 2000);


                }
            });
        getData();
        adaptercomment = new RecyclerViewAdaptercomment(commentlist, recycler_comment);
        recycler_comment.setAdapter(adaptercomment);
            adaptercomment.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    Log.e("haint", "Load More");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("haint", "Load More 2");


                            getData();


                            adaptercomment.setLoaded();
                        }
                    }, 2000);
                }
            });





    }
    public void onBackPressed() {
        super.onBackPressed();
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

    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
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
        public TextView name, locations;


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
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_multipleimagesview_photostory, parent, false);
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
                    mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();


                ItemModel itemmodel = modelListlikes.get(position);


                userViewHolder.imageview.setDefaultImageResId(R.drawable.ic_launcher);
                userViewHolder.imageview.setErrorImageResId(R.drawable.ic_launcher);

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


            return modelListlikes.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelListlikes.size();
        }
    }









    static class UserViewHoldercomment extends RecyclerView.ViewHolder {
        public TextView name, locations,commentsdecription;


        public NetworkImageView imageview;

        public UserViewHoldercomment(View itemView) {
            super(itemView);



            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


            name = (TextView) itemView.findViewById(R.id.name);
            locations = (TextView) itemView.findViewById(R.id.location);

            commentsdecription = (TextView) itemView.findViewById(R.id.all_page_descriptions);
        }
    }

    static class LoadingViewHoldercomment extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHoldercomment(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }


    private void getData() {
        StringRequest request=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Res",response.toString());
                try {
                    JSONObject object = new JSONObject(response.toString());
                    JSONArray array = object.getJSONArray("result");
                    String data = array.optString(1);
                    JSONArray     jsonArray = new JSONArray(data.toString());
                    parseJsonFeedTwo(jsonArray);
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>param=new HashMap<>();
                param.put("Key", "Simplicity");
                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("rtype", "viewcomment");
                param.put("language","1");
                param.put("qtype","photostories");
                param.put("id",likes_to_load);

                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }

    private void parseJsonFeedTwo(JSONArray response) {
        try {



            for (ii = 0; ii < response.length(); ii++) {
                obj = (JSONObject) response.get(ii);

                ItemModels model = new ItemModels();

                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setProfilepic(image);
                model.setComment(obj.getString("comment"));
                //model.setPadate(obj.getString("date"));
                model.setName(obj.getString("name"));
                model.setId(obj.getString("user_id"));
                if(response.length()==0){

                    recycler_comment.setVisibility(View.GONE);
                }else {
                    recycler_comment.setVisibility(View.VISIBLE);

                }
                if(response.length()==0){
                    loadmore_title.setVisibility(View.GONE);
                }else {
                    if(response.length()>4){
                        loadmore_title.setText("Load More");
                    }else {
                        loadmore_title.setVisibility(View.GONE);
                    }
                }

                commentlist.add(model);

            }

            // notify data changes to list adapater
            rcAdapter.notifyDataSetChanged();




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class  ItemModels{
        private String id,comment,padate,name,profilepic;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPadate() {
            return padate;
        }

        public void setPadate(String padate) {
            this.padate = padate;
        }

        public String getProfilepic() {
            return profilepic;
        }

        public void setProfilepic(String profilepic) {
            this.profilepic = profilepic;
        }

    }

    class RecyclerViewAdaptercomment extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        ImageLoader mImageLoader;
        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_LOADING = 2;

        private boolean loading;
        private OnLoadMoreListener onLoadMoreListener;

        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;

        public RecyclerViewAdaptercomment(List<ItemModels> students, RecyclerView recyclerView) {
            commentlist = students;

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
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_comment_all, parent, false);
                return new UserViewHoldercomment(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHoldercomment(view);
            }
            return null;

        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof UserViewHoldercomment) {

                final UserViewHoldercomment userViewHolder = (UserViewHoldercomment) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                if (mImageLoader == null)
                    mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();


               ItemModels itemmodel = commentlist.get(position);


                userViewHolder.name.setText(itemmodel.getName());
                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.commentsdecription.setText(itemmodel.getComment());
                userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

                userViewHolder.imageview.setImageUrl(itemmodel.getProfilepic(), mImageLoader);
                // userViewHolder.im.setVisibility(View.VISIBLE);

            } else if (holder instanceof LoadingViewHoldercomment) {
                LoadingViewHoldercomment loadingViewHolder = (LoadingViewHoldercomment) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }

        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }


        public int getItemViewType(int position) {


            return commentlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

        }

        public void setLoaded() {
            loading = false;
        }
        public void onItemDismiss(int position) {
            if(position!=-1 && position<commentlist.size())
            {
                commentlist.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        }

        public int getItemCount() {
            return commentlist.size();
        }
    }

    public void AddnewCommnent(){
        int curSize =adaptercomment.getItemCount();
       ItemModels models=new ItemModels();
        models.setName(my_profilename);
        models.setProfilepic(my_profileimage);
        models.setComment(description_comment);
        commentlist.add(models);
        recycler_comment.setVisibility(View.VISIBLE);
        adaptercomment.notifyDataSetChanged();
        adaptercomment.notifyItemRangeInserted(curSize, commentlist.size());
    }



    public static class MyDialogFragment extends DialogFragment {
        private String KEY_COMMENT = "comment";
        private String KEY_TYPE = "qtype";
        private String KEY_MYUID = "user_id";
        private String KEY_POSTID = "id";
        String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=article&id=";
        String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";
        EditText commentbox;
        Button post_review;
        ImageButton close_back;

        JSONObject obj, objtwo;
        JSONArray feedArray;
        int ii, i;
        TextView titles;
        CoordinatorLayout mCoordinator;
        private final String TAG_REQUEST = "MY_TAG";
        List<ItemModels> commentlist = new ArrayList<ItemModels>();
        //Need this to set the title of the app bar
        CollapsingToolbarLayout mCollapsingToolbarLayout;
        RecyclerViewAdapter rcAdapter;
        ProgressDialog pdialog;
        RequestQueue requestQueue;
        private int requestCount = 1;
        JsonObjectRequest jsonReq;
        String URLTWO;
        String bimage;
        RecyclerView recycler;
        LinearLayoutManager mLayoutManager;
        String postid, myuserid;

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
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.commentsreview, container, false);
            titles = (TextView) root.findViewById(R.id.comments_title);
            requestQueue = Volley.newRequestQueue(getActivity());
            postid = getArguments().getString("POSTID");
            myuserid = getArguments().getString("USERID");
            String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
            commentbox = (EditText) root.findViewById(R.id.comment_description);
            post_review = (Button) root.findViewById(R.id.post_button);
            close_back = (ImageButton) root.findViewById(R.id.btn_back_comment_review);
            // mCoordinator = (CoordinatorLayout) root.findViewById(R.id.root_coordinator);             mCollapsingToolbarLayout = (CollapsingToolbarLayout) root.findViewById(R.id.collapsing_toolbar_layout);
            recycler = (RecyclerView) root.findViewById(R.id.commentpagelist_recyclerview);
            recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

            pdialog = new ProgressDialog(getActivity());
            pdialog.show();
            pdialog.setContentView(R.layout.custom_progressdialog);
            pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            titles.setTypeface(tf);
            titles.setText("Comments");
            post_review.setTypeface(tf);
            post_review.setText("POST");
            close_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialogFragment.this.dismiss();
                }
            });
            post_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        StringRequest comment_post_request = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Res", response.toString().trim());

                                if (response.equalsIgnoreCase("error")) {
                                    Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                                } else {

                                        /*commentbox_editext.setText("");
                                        AddnewCommnent();
                                        scrollView.post(new Runnable() {
                                            public void run() {
                                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                            }
                                        });*/

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                String      description_comment = commentbox.getText().toString().trim();


                                Map<String, String> param = new Hashtable<String, String>();
                                String keytepe = "article";
                                Log.e("qty", keytepe);
                                param.put("Key", "Simplicity");
                                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                                param.put("rtype", "comment");
                                param.put("language", "1");
                                param.put("id", postid);
                                param.put("user_id", myuserid);
                                param.put("comment", description_comment);
                                param.put("qtype", "photostories");
                                return param;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(comment_post_request);
                    } catch (Exception e) {

                    }
                }

            });

            rcAdapter = new RecyclerViewAdapter(commentlist,recycler);
            recycler.setAdapter(rcAdapter);
            getData();
            rcAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    Log.e("haint", "Load More");
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

        public void onDestroy() {
            super.onDestroy();
            dissmissDialog();
        }

        static class UserViewHolder extends RecyclerView.ViewHolder {
            public TextView name, locations,commentsdecription;


            public NetworkImageView imageview;

            public UserViewHolder(View itemView) {
                super(itemView);

                // im = (ImageView) itemView.findViewById(R.id.imageViewlitle);

                imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


                name = (TextView) itemView.findViewById(R.id.name);
                locations = (TextView) itemView.findViewById(R.id.location);

                commentsdecription = (TextView) itemView.findViewById(R.id.all_page_descriptions);
            }
        }

        static class LoadingViewHolder extends RecyclerView.ViewHolder {
            public ProgressBar progressBar;

            public LoadingViewHolder(View itemView) {
                super(itemView);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
            }
        }

        private void getData() {
            StringRequest request=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("Res",response.toString());
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        JSONArray array = object.getJSONArray("result");
                        String data = array.optString(1);
                        JSONArray     jsonArray = new JSONArray(data.toString());
                        parseJsonFeed(jsonArray);
                    }catch (JSONException e){

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String>param=new HashMap<>();
                    param.put("Key", "Simplicity");
                    param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                    param.put("rtype", "viewcomment");
                    param.put("language","1");
                    param.put("qtype","photostories");
                    param.put("id",postid);

                    return param;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);

        }


        private void parseJsonFeed(JSONArray response) {
            try {
                //  feedArray = response.getJSONArray("result");


                for (ii = 0; ii < response.length(); ii++) {
                    obj = (JSONObject) response.get(ii);

                    ItemModels model = new ItemModels();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("image") ? null : obj
                            .getString("image");
                    model.setProfilepic(image);
                    model.setComment(obj.getString("comment"));
                    model.setPadate(obj.getString("date"));
                    model.setName(obj.getString("name"));
                    model.setId(obj.getString("user_id"));


                    commentlist.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();

                // notify data changes to list adapater


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        class  ItemModels{
            private String id,comment,padate,name,profilepic;

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPadate() {
                return padate;
            }

            public void setPadate(String padate) {
                this.padate = padate;
            }

            public String getProfilepic() {
                return profilepic;
            }

            public void setProfilepic(String profilepic) {
                this.profilepic = profilepic;
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

            public RecyclerViewAdapter(List<ItemModels> students, RecyclerView recyclerView) {
                commentlist = students;

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
                if (viewType == VIEW_TYPE_ITEM) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_comment_all, parent, false);
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


                   ItemModels itemmodel = commentlist.get(position);


                    userViewHolder.name.setText(itemmodel.getName());
                    userViewHolder.name.setTypeface(seguiregular);
                    userViewHolder.commentsdecription.setText(itemmodel.getComment());
                    userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                    userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

                    userViewHolder.imageview.setImageUrl(itemmodel.getProfilepic(), mImageLoader);
                    // userViewHolder.im.setVisibility(View.VISIBLE);

                } else if (holder instanceof Articledescription.MyDialogFragment.LoadingViewHolder) {
                    Articledescription.MyDialogFragment.LoadingViewHolder loadingViewHolder = (Articledescription.MyDialogFragment.LoadingViewHolder) holder;
                    loadingViewHolder.progressBar.setIndeterminate(true);
                }

            }

            public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
                this.onLoadMoreListener = onLoadMoreListener;
            }


            public int getItemViewType(int position) {


                return commentlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

            }

            public void setLoaded() {
                loading = false;
            }

            public int getItemCount() {
                return commentlist.size();
            }
        }
    }


}










package simplicity_an.simplicity_an;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

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
 * Created by kuppusamy on 4/2/2016.
 */
public class TravelsDescription extends AppCompatActivity {
    TextView title,date,sourcelinknews,sourcelinksimplicity,textview_date;
    NetworkImageView image;
    WebView description;

    ImageView reporter_profile_image;
    TextView source_reporter_name,sourcereprterdivider,hashtags_title,image_description,short_description,title_category;
    ImageButton comment,share,menu,back,favourite;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=travels&key=simples&tid=";
    String URLTWO;

    String titl;
    RequestQueue queue,requestQueue;
    String s;
    private final String TAG_REQUEST = "MY_TAG";
    String notifiid,myprofileid,shareurl,sharetitle;
    String URLFAV="http://simpli-city.in/request2.php?rtype=addfav&key=simples";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    private String KEY_QTYPE= "qtype";
    int post_likes_count,favcount;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid,activity;
    ProgressDialog pdialog;
    public static final String MYACTIVITYSEARCH= "myactivitysearch";
    String searchnonitiid,searchactivity_travel;

    List<ItemModels> commentlist = new ArrayList<ItemModels>();
    RecyclerViewAdapter rcAdapter;

    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO_comment;
    String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=travel&id=";
    String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";

    RecyclerView recycler;
    LinearLayoutManager mLayoutManager;
    String postid, myuserid;
    LinearLayout commentbox;
    TextView comment_title,loadmore_title;
    EditText commentbox_editext;
    Button post;
    RecyclerView recycler_comment;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii, i;
    private String KEY_COMMENT = "comment";
    private String KEY_TYPE = "qtype";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    String description_comment,my_profilename,my_profileimage;

    String URLLIKES="http://simpli-city.in/request2.php?rtype=articlelikes&key=simples";
    public static final String USERID="user_id";
    public static final String QID="qid";
    public static final String QTYPE="qtype";
    ScrollView scrollView;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    LinearLayout commentboxlayout;
    String fontname;
    Typeface tf_play,tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.newsdescription);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        searchactivity_travel=sharedpreferences.getString(MYACTIVITYSEARCH,"");
        contentid=sharedpreferences.getString(CONTENTID,"");
        activity=sharedpreferences.getString(Activity,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        if (sharedpreferences.contains(USERNAME)) {

            my_profilename=sharedpreferences.getString(USERNAME,"");

        }
        if (sharedpreferences.contains(USERIMAGE)) {

            my_profileimage=sharedpreferences.getString(USERIMAGE,"");

        }

        colorcodes=sharedpreferences.getString(backgroundcolor,"");

        mainlayout=(RelativeLayout)findViewById(R.id.version_main_layout);

        commentboxlayout = (LinearLayout)findViewById(R.id.commentbox_city);
        back = (ImageButton)findViewById(R.id.btn_back);
        if(colorcodes.equals("#FFFFFFFF")){
            commentboxlayout.setBackgroundColor(Color.WHITE);
            back.setImageResource(R.mipmap.backtamilone);
        }
        else{
            commentboxlayout.setBackgroundColor(Color.BLACK);
            back.setImageResource(R.mipmap.back);
        }

        if(colorcodes.length()==0){
            int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);

            mainlayout.setBackgroundDrawable(gd);

            SharedPreferences.Editor editor = sharedpreferences.edit();
           editor.putString(backgroundcolor, "#262626");
            editor.commit();
        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi"+colorcodes);
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);

                SharedPreferences.Editor editor = sharedpreferences.edit();
               editor.putString(backgroundcolor, "#262626");
                editor.commit();
            }else {

                if(colorcodes!=null){
                    if(colorcodes.equals("#FFFFFFFF")){
                        int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FFFFFFFF")};

                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors);
                        gd.setCornerRadius(0f);

                        mainlayout.setBackgroundDrawable(gd);
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
                }else {
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
        sourcelinknews = (TextView) findViewById(R.id.sourcelink);
        sourcelinksimplicity = (TextView) findViewById(R.id.sourcelinkredsimplicity);
        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        requestQueue=Volley.newRequestQueue(this);
       /* Intent getnotifi=getIntent();
        notifiid=getnotifi.getStringExtra("ID");*/
        if(activity==null){
            Intent getnotifi=getIntent();
            notifiid=getnotifi.getStringExtra("ID");
            searchnonitiid = getnotifi.getStringExtra("IDSEARCH");
            notifiid = notifiid.replaceAll("\\D+","");
            Log.e("ID",notifiid);

        } else {
            if(activity.equalsIgnoreCase("traveldesc")){
                notifiid=contentid;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(Activity);
                editor.remove(CONTENTID);
                editor.apply();

            }else {
                Intent getnotifi=getIntent();
                notifiid=getnotifi.getStringExtra("ID");
                searchnonitiid = getnotifi.getStringExtra("IDSEARCH");
                notifiid = notifiid.replaceAll("\\D+","");
                Log.e("ID",notifiid);
            }
        }
        if(myprofileid!=null){
            URLTWO=URL+notifiid+"&user_id="+myprofileid;
        }else {
            URLTWO=URL+notifiid;
        }
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        commentbox=(LinearLayout)findViewById(R.id.comments_versiontwo) ;
        comment_title=(TextView)findViewById(R.id.comments_title);
        loadmore_title=(TextView)findViewById(R.id.loadmore);
        commentbox_editext=(EditText)findViewById(R.id.comment_description);
        post=(Button)findViewById(R.id.post_button) ;
        recycler_comment=(RecyclerView)findViewById(R.id.commentpagelist_recyclerview) ;

      //  recycler_comment.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        recycler_comment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        scrollView=(ScrollView)findViewById(R.id.scroll);
        title=(TextView)findViewById(R.id.textView_titlename);
        date=(TextView)findViewById(R.id.textView_date);
        textview_date=(TextView) findViewById(R.id.textView_datenew);
        source_reporter_name=(TextView)findViewById(R.id.textView_sourcename) ;
        sourcereprterdivider=(TextView)findViewById(R.id.centerdivider);
        hashtags_title=(TextView) findViewById(R.id.textView_hashtags);
        reporter_profile_image=(ImageView)findViewById(R.id.profile_reporter);
        image_description=(TextView)findViewById(R.id.textView_photodescription);
        short_description=(TextView)findViewById(R.id.textView_shortdescription);
        title_category=(TextView)findViewById(R.id.textView_qtypename) ;

        comment=(ImageButton)findViewById(R.id.btn_4);
        share=(ImageButton)findViewById(R.id.btn_share);
        menu=(ImageButton)findViewById(R.id.btn_3);
        back=(ImageButton)findViewById(R.id.btn_back);
        favourite=(ImageButton)findViewById(R.id.btn_like);
        description=(WebView)findViewById(R.id.textView_desc);
        description.getSettings().setLoadsImagesAutomatically(true);
        description.getSettings().setPluginState(WebSettings.PluginState.ON);
        description.getSettings().setAllowFileAccess(true);
        description.getSettings().setJavaScriptEnabled(true);


        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
       /* Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        String simplycity_title_bold= "fonts/robotoSlabBold.ttf";
        Typeface tf_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);*/
        if(fontname.equals("sanfrancisco")){
            String playfair ="fonts/Oxygen-Bold.ttf";
            tf_play = Typeface.createFromAsset(getApplicationContext().getAssets(), playfair);
            String simplycity_title_bold = "fonts/SystemSanFranciscoDisplayRegular.ttf";
            tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);
            title.setTextSize(20);
            title.setLineSpacing(0,1f);
        }else {
            String playfair = "fonts/playfairDisplayRegular.ttf";
            tf_play = Typeface.createFromAsset(getApplicationContext().getAssets(), playfair);
            String simplycity_title_bold = "fonts/Lora-Regular.ttf";
            tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);
        }
        title.setTypeface(tf_play);
        textview_date.setTypeface(tf);
        sourcelinknews.setTypeface(tf);
        sourcelinksimplicity.setTypeface(tf);
        date.setTypeface(tf);
        commentbox_editext.setHint("Commnets Here");
        comment_title.setTypeface(tf);
        loadmore_title.setTypeface(tf);
        post.setTypeface(tf);
        image=(NetworkImageView)findViewById(R.id.thumbnailone);

        if(colorcodes.equals("#FFFFFFFF")){
            title.setTextColor(Color.BLACK);
            post.setTextColor(Color.BLACK);
            comment_title.setTextColor(Color.BLACK);
            loadmore_title.setTextColor(Color.BLACK);
            source_reporter_name.setTextColor(Color.BLACK);
            sourcelinknews.setTextColor(Color.BLACK);
            sourcelinksimplicity.setTextColor(Color.BLACK);
            image_description.setTextColor(Color.BLACK);
            short_description.setTextColor(Color.BLACK);
            date.setTextColor(Color.BLACK);
            textview_date.setTextColor(Color.BLACK);
            commentbox_editext.setBackgroundResource(R.drawable.editextboxwhite);
            commentbox_editext.setTextColor(getResources().getColor(android.R.color.black));
            comment_title.setBackgroundResource(R.drawable.editextboxwhite);

        }
        else{
            title.setTextColor(Color.WHITE);
            post.setTextColor(Color.WHITE);
            comment_title.setTextColor(Color.WHITE);
            loadmore_title.setTextColor(Color.WHITE);
            source_reporter_name.setTextColor(Color.WHITE);
            sourcelinknews.setTextColor(Color.WHITE);
            sourcelinksimplicity.setTextColor(Color.WHITE);
            image_description.setTextColor(Color.WHITE);
            short_description.setTextColor(Color.WHITE);
            date.setTextColor(Color.WHITE);
            textview_date.setTextColor(Color.WHITE);
        }

        if(notifiid!=null) {
            StringRequest jsonreq = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {


                public void onResponse(String response) {
                    Log.e("Response",response.toString());
                    try{
                        JSONObject object=new JSONObject(response.toString());
                        JSONArray array=object.getJSONArray("result");
                        String data=array.optString(1);
                        JSONArray jsonArray=new JSONArray(data.toString());
                        Log.e("Response",data.toString());
                        if (response != null) {
                            pdialog.dismiss();
                            parseJsonFeed(jsonArray);
                        }
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
                    param.put("Key","Simplicity");
                    param.put("Token","8d83cef3923ec6e4468db1b287ad3fa7");
                    param.put("language","1");
                    param.put("rtype","travels");
                    param.put("id",notifiid);
                    if(myprofileid!=null){
                        param.put("user_id",myprofileid);
                    }else {

                    }
                    return param;
                }
            };

            jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonreq);

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(searchactivity_travel.equalsIgnoreCase("travelsearch")){
                    Intent searchnotification = new Intent(getApplicationContext(), TravelSearchview.class);
                    startActivity(searchnotification);
                }
                else if (searchnonitiid != null) {
                    Intent searchnotification = new Intent(getApplicationContext(), SimplicitySearchview.class);
                    searchnotification.putExtra("IDSEARCH", searchnonitiid);
                    startActivity(searchnotification);
                    finish();
                } else {
                    Intent backtrveldesc = new Intent(TravelsDescription.this, MainActivityVersiontwo.class);
                    startActivity(backtrveldesc);
                    finish();
                }*/
onBackPressed();

            }
        });


        if(myprofileid!=null){
            // URLTWO_comment=URLCOMMENT+notifiid;
            //comment_title.setText("Post your Comment Here - ");
            post.setText("Post");

            commentbox.setVisibility(View.VISIBLE);

            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(commentbox_editext.getWindowToken(), 0);
                   /* pdialog = new ProgressDialog(getApplicationContext());
                    pdialog.show();
                    pdialog.setContentView(R.layout.custom_progressdialog);
                    pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
                    if(myprofileid!=null) {

                        try {

                            StringRequest comment_post_request = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Res", response.toString().trim());
                                   // pdialog.dismiss();
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
                                    param.put("id", notifiid);
                                    param.put("user_id", myprofileid);
                                    param.put("comment", description_comment);
                                    param.put("qtype", "travels");
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
            rcAdapter = new RecyclerViewAdapter(commentlist,recycler_comment);
            recycler_comment.setAdapter(rcAdapter);
            getData();
            rcAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    Log.e("haint", "Load More");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("haint", "Load More 2");


                            //getData();


                            rcAdapter.setLoaded();
                        }
                    }, 2000);
                }
            });

            loadmore_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    MyDialogFragment frag;
                    frag = new MyDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("POSTID", notifiid);
                    args.putString("USERID", myprofileid);
                    frag.setArguments(args);
                    frag.show(ft, "txn_tag");
                }
            });

        }else {
            commentbox.setVisibility(View.GONE);
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menutraveldesc = new Intent(TravelsDescription.this, MainActivityVersiontwo.class);
                startActivity(menutraveldesc);
                finish();
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myprofileid != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    MyDialogFragment frag;
                    frag = new MyDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("POSTID", notifiid);
                    args.putString("USERID", myprofileid);
                    frag.setArguments(args);
                    frag.show(ft, "txn_tag");
                } else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "traveldesc");
                    editor.putString(CONTENTID, notifiid);
                    editor.commit();
                    Intent signin = new Intent(TravelsDescription.this, SigninpageActivity.class);
                    startActivity(signin);
                    finish();
                }
            }
        });
    }

  /*  protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
        }
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void AddnewCommnent(){
        int curSize = rcAdapter.getItemCount();
        ItemModels models=new ItemModels();
        models.setName(my_profilename);
        models.setProfilepic(my_profileimage);
        models.setComment(description_comment);
        commentlist.add(models);
        recycler_comment.setVisibility(View.VISIBLE);
        rcAdapter.notifyDataSetChanged();
        rcAdapter.notifyItemRangeInserted(curSize, commentlist.size());
    }

    private void parseJsonFeed(JSONArray response) {
        ImageLoader
                mImageLoader = MySingleton.getInstance(TravelsDescription.this).getImageLoader();
        try {
            //JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < response.length(); i++) {
                final JSONObject obj = (JSONObject) response.get(i);


                ItemModel model=new ItemModel();
                //FeedItem model=new FeedItem();
                String images = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(images);
                model.setDescription(obj.getString("description"));
                model.setPdate(obj.getString("date"));
                model.setTitle(obj.getString("title"));

                 title.setText(Html.fromHtml(obj.getString("title")));
                image.setImageUrl(images, mImageLoader);
                model.setShortdescription(obj.getString("short_description"));
               // model.setReporterid(obj.getString("reporter_id"));
                model.setReportername(obj.getString("reporter_name"));
                model.setReporterimage(obj.getString("reporter_image"));
             //   model.setReporterurl(obj.getString("reporter_url"));
               // model.setPhotocreditid(obj.getString("photo_credits_id"));
                model.setPhotocreditimage(obj.getString("photo_credit_image"));
                model.setPhotocreditname(obj.getString("photo_credit_name"));
             //   model.setPhotocrediturl(obj.getString("photo_credits_url"));
                String descrition = obj.isNull("description") ? null : obj
                        .getString("description");
                String ss = descrition;
                model.setSource(obj.getString("source"));
                model.setSource_link(obj.getString("source_link"));
                // Toast.makeText(Second.this,ss,Toast.LENGTH_LONG).show();
                image_description.setText("");
                if(short_description!=null){
                    short_description.setText(obj.getString("short_description"));
                }else {
                    short_description.setVisibility(View.GONE);
                }
                hashtags_title.setText("");
                //title_category.setText(obj.getString("qtype"));
                String reporterimage=obj.getString("reporter_image");
                if(reporterimage.equals("null")||reporterimage.equals("")){

                }else {
                    Picasso.with(getApplicationContext())
                            .load(reporterimage)
                            .centerCrop()
                            .resize(40, 40)
                            .into(reporter_profile_image);
                }
                String by = "By&nbsp;";
               if (obj.getString("reporter_name").equals("") || obj.getString("reporter_name").equals("null")) {                     source_reporter_name.setText(Html.fromHtml(obj.getString("source")));                 } else {                     if(obj.getString("source").equals("")){                         source_reporter_name.setText(Html.fromHtml(obj.getString("reporter_name")+"&nbsp;"));                     }else {                         source_reporter_name.setText(Html.fromHtml(obj.getString("reporter_name") + "&nbsp;"+"|"+"&nbsp;"+obj.getString("source")));                     }                  }
                date.setText(Html.fromHtml( obj.getString("source")));
                textview_date.setText(obj.getString("date"));

                s = ss;
                // s = s.replace("\"", "'");
                s = s.replace("\\", "");

                // description.setText(Html.fromHtml(s));

                //description.loadData(String.format(descrip), "text/html", "utf-8");
                //description.loadData(descrip,"text/html","utf-8");
               // String fonts="<html>\n" +         "\t<head>\n" +         "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +         "\t\t<style>\n" +         "\t\t@font-face {\n" +         "  font-family: 'segeoui-light';\n" +         " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "  font-family: 'segeoui-regular';\n" +         "src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "  font-family: 'segeoui-sbold';\n" +         " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Bold';\n" +         "   src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Light';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Regular';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Thin';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "\t\t</style>\n" +         "\t</head>"; description.loadDataWithBaseURL("", fonts+descrition+"</head>", "text/html", "utf-8", "");
                // description.setBackgroundColor(0x7A4100);
                description.setBackgroundColor(Color.TRANSPARENT);

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                String fonts="";
                if(fontname.equals("sanfrancisco")){
                    fonts = "<html>\n" +
                            "\t<head>\n" +
                            "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +
                            "\t\t<style>\n" +
                            "\t\t@font-face {\n" +
                            "  font-family: 'segeoui-light';\n" +
                            " src: url('file:///android_asset/fonts/SystemSanFranciscoDisplayRegular');\n" +
                            "  font-style: normal;\n" +
                            "}\n" +
                            "\n" +
                            "@font-face {\n" +
                            "  font-family: 'segeoui-regular';\n" +
                            " src: url('file:///android_asset/fonts/SystemSanFranciscoDisplayRegular');\n" +
                            "  font-style: normal;\n" +
                            "}\n" +
                            "\n" +
                            "@font-face {\n" +
                            "  font-family: 'segeoui-sbold';\n" +
                            " src: url('file:///android_asset/fonts/SystemSanFranciscoDisplayRegular');\n" +
                            "  font-style: normal;\n" +
                            "}\n" +
                            "\n" +
                            "@font-face {\n" +
                            "    font-family: 'RobotoSlab-Bold';\n" +
                            "   src: url('file:///android_asset/fonts/SystemSanFranciscoDisplayRegular');\n" +
                            "    font-style: normal;\n" +
                            "}\n" +
                            "@font-face {\n" +
                            "    font-family: 'RobotoSlab-Light';\n" +
                            "    src: url('file:///android_asset/fonts/SystemSanFranciscoDisplayRegular');\n" +
                            "    font-style: normal;\n" +
                            "}\n" +
                            "@font-face {\n" +
                            "    font-family: 'RobotoSlab-Regular';\n" +
                            "    src: url('file:///android_asset/fonts/SystemSanFranciscoDisplayRegular');\n" +
                            "    font-style: normal;\n" +
                            "}\n" +
                            "@font-face {\n" +
                            "    font-family: 'RobotoSlab-Thin';\n" +
                            "    src: url('file:///android_asset/fonts/SystemSanFranciscoDisplayRegular');\n" +
                            "    font-style: normal;\n" +
                            "}\n" +
                            "\t\t</style>\n" +
                            "\t</head>";
                }else {
                    fonts = "<html>\n" +
                            "\t<head>\n" +
                            "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +
                            "\t\t<style>\n" +
                            "\t\t@font-face {\n" +
                            "  font-family: 'segeoui-light';\n" +
                            " src: url('file:///android_asset/fonts/Lora-Regular.ttf');\n" +
                            "  font-style: normal;\n" +
                            "}\n" +
                            "\n" +
                            "@font-face {\n" +
                            "  font-family: 'segeoui-regular';\n" +
                            " src: url('file:///android_asset/fonts/Lora-Regular.ttf');\n" +
                            "  font-style: normal;\n" +
                            "}\n" +
                            "\n" +
                            "@font-face {\n" +
                            "  font-family: 'segeoui-sbold';\n" +
                            " src: url('file:///android_asset/fonts/Lora-Regular.ttf');\n" +
                            "  font-style: normal;\n" +
                            "}\n" +
                            "\n" +
                            "@font-face {\n" +
                            "    font-family: 'RobotoSlab-Bold';\n" +
                            "   src: url('file:///android_asset/fonts/Lora-Regular.ttf');\n" +
                            "    font-style: normal;\n" +
                            "}\n" +
                            "@font-face {\n" +
                            "    font-family: 'RobotoSlab-Light';\n" +
                            "    src: url('file:///android_asset/fonts/Lora-Regular.ttf');\n" +
                            "    font-style: normal;\n" +
                            "}\n" +
                            "@font-face {\n" +
                            "    font-family: 'RobotoSlab-Regular';\n" +
                            "    src: url('file:///android_asset/fonts/Lora-Regular.ttf');\n" +
                            "    font-style: normal;\n" +
                            "}\n" +
                            "@font-face {\n" +
                            "    font-family: 'RobotoSlab-Thin';\n" +
                            "    src: url('file:///android_asset/fonts/Lora-Regular.ttf');\n" +
                            "    font-style: normal;\n" +
                            "}\n" +
                            "\t\t</style>\n" +
                            "\t</head>";
                }

                String rep = String.valueOf(descrition);
                rep =  rep.replaceAll("color:#fff","color:#000");
                String date = "<p><font color=\"white\">" + obj.getString("date") + "</font></p>";
                if(colorcodes.equals("#FFFFFFFF")) {
                    description.loadDataWithBaseURL("", fonts + rep + "</head>", "text/html", "utf-8", "");
                }else{
                    description.loadDataWithBaseURL("", fonts + descrition + "</head>", "text/html", "utf-8", "");
                }
                description.setWebViewClient(new MyBrowser());
                description.setBackgroundColor(Color.TRANSPARENT);
               /* description.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String s) {
                        super.onPageFinished(view, s);
                       // view.loadUrl(s);
                        view.loadData(s,"text/image",null);
                    }

                    @Override
                    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                        return super.shouldInterceptRequest(view, request);
                    }

                });*/


                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, sharetitle+ "\n" + shareurl+"\n"+"\n"+"\n"+"Receive instant updates by installing Simplicity for iPhone/iPad,Android and Windows 10(desktop & Mobile)(http://goo.gl/Sv3vfc)");

                        sendIntent.setType("text/plain");
                        startActivity(Intent.createChooser(sendIntent, "Share using"));

                    }
                });
                 if (favcount == 1) {                     favourite.setImageResource(R.mipmap.likered);                     favourite.setTag("heartfullred");                 } else {                    favourite.setImageResource(R.mipmap.like);                     favourite.setTag("heart");                 }
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myprofileid!=null) {
                            String backgroundImageName = String.valueOf(favourite.getTag());
                            Log.e("RUN","with"+backgroundImageName);
                            if(backgroundImageName.equals("heart")){
                                favourite.setImageResource(R.mipmap.likered);
                                favourite.setTag("heartfullred");
                            }else if(backgroundImageName.equals("heartfullred")) {
                                favourite.setImageResource(R.mipmap.like);
                                favourite.setTag("heart");
                            }else {

                            }
                            StringRequest likes=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String res;
                                    Log.e("RES",response.toString());
                                    try {
                                        Log.e("RES", "START");

                                        JSONObject object=new JSONObject(response.toString());
                                        JSONArray array=object.getJSONArray("result");
                                        String data=array.optString(1);
                                        JSONArray jsonArray=new JSONArray(data.toString());


                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject obj = (JSONObject) jsonArray.get(i);
                                            String dirs = obj.getString("like_type");

                                            Log.d("RES", dirs);
                                            res=object.getString("like_type");

                                            Log.e("RES",res.toString());


                                            if(res.equals("Liked")){

                                                favourite.setImageResource(R.mipmap.heartfullred);
                                                favourite.setTag("heartfullred");
                                            }else if(res.equals("Like")){



                                                favourite.setImageResource(R.mipmap.heart);
                                                favourite.setTag("heart");
                                            }





                                        }

                                    }catch (JSONException e){

                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                protected Map<String,String> getParams()throws AuthFailureError{
                                    Map<String,String> param=new Hashtable<String, String>();
                                    String type=null;
                                    try {
                                        type=obj.getString("qtypemain");
                                    }catch (JSONException e){

                                    }

                                    param.put("Key","Simplicity");
                                    param.put("Token","8d83cef3923ec6e4468db1b287ad3fa7");
                                    param.put("rtype","like");
                                    param.put("id", notifiid);
                                    param.put("user_id", myprofileid);
                                    param.put("qtype", type);
                                    return param;
                                }
                            };
                            RequestQueue likesqueue=Volley.newRequestQueue(getApplicationContext());
                            likes.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            likesqueue.add(likes);
                        }else {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Activity, "mainversion");
                            editor.putString(CONTENTID, "0");
                            editor.commit();
                            Intent sign=new Intent(getApplicationContext(),SigninpageActivity.class);
                            startActivity(sign);

                        }

                    }
                });
                modelList.add(model);
            }

            // notify data changes to list adapater
            //rcAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // ((EditText) getActionBar().getCustomView().findViewById(R.id.editText)).setText(url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("http://simpli")){
                Intent ads=new Intent(getApplicationContext(),AdvertisementPage.class);
                ads.putExtra("ID",url);
                startActivity(ads);
            }else {
                Intent ads=new Intent(getApplicationContext(),AdvertisementPage.class);                 ads.putExtra("ID",url);                 startActivity(ads);
                // view.loadUrl(url);
            }
            //

            return true;
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
        private int favcount;
        private String shareurl;
        private String source;
        private String source_link;
        String shortdescription,reporterid,reportername,reporterurl,reporterimage;
        String photodescription,photocreditid,photocreditname,photocreditimage,photocrediturl;

        public String getShortdescription() {
            return shortdescription;
        }

        public void setShortdescription(String shortdescription) {
            this.shortdescription = shortdescription;
        }

        public String getReporterid() {
            return reporterid;
        }

        public void setReporterid(String reporterid) {
            this.reporterid = reporterid;
        }

        public String getReportername() {
            return reportername;
        }

        public void setReportername(String reportername) {
            this.reportername = reportername;
        }

        public String getReporterimage() {
            return reporterimage;
        }

        public void setReporterimage(String reporterimage) {
            this.reporterimage = reporterimage;
        }

        public String getReporterurl() {
            return reporterurl;
        }

        public void setReporterurl(String reporterurl) {
            this.reporterurl = reporterurl;
        }

        public String getPhotocreditid() {
            return photocreditid;
        }

        public void setPhotocreditid(String photocreditid) {
            this.photocreditid = photocreditid;
        }

        public String getPhotocreditimage() {
            return photocreditimage;
        }

        public void setPhotocreditimage(String photocreditimage) {
            this.photocreditimage = photocreditimage;
        }

        public String getPhotocreditname() {
            return photocreditname;
        }

        public void setPhotocreditname(String photocreditname) {
            this.photocreditname = photocreditname;
        }

        public String getPhotocrediturl() {
            return photocrediturl;
        }

        public void setPhotocrediturl(String photocrediturl) {
            this.photocrediturl = photocrediturl;
        }

        public String getPhotodescription() {
            return photodescription;
        }

        public void setPhotodescription(String photodescription) {
            this.photodescription = photodescription;
        }

        public void setSource_link(String source_link) {
            this.source_link = source_link;
        }

        public String getSource_link() {
            return source_link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setFavcount(int favcount) {
            this.favcount = favcount;
        }

        public int getFavcount() {
            return favcount;
        }

        public String getShareurl() {
            return shareurl;
        }



        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
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
                param.put("qtype","travels");
                param.put("id",notifiid);

                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }

    private void parseJsonFeedTwo(JSONArray response) {
        try {
            //feedArray = response.getJSONArray("result");


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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_comment_all, parent, false);
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


                ItemModels itemmodel = commentlist.get(position);

                if(colorcodes.equals("#FFFFFFFF")){
                    userViewHolder.name.setText(itemmodel.getName());
                    userViewHolder.name.setTextColor(getResources().getColor(android.R.color.black));

                    userViewHolder.commentsdecription.setText(itemmodel.getComment());
                    userViewHolder.commentsdecription.setTextColor(getResources().getColor(android.R.color.black));

                }else {

                    userViewHolder.name.setText(itemmodel.getName());

                    userViewHolder.commentsdecription.setText(itemmodel.getComment());
                }

                userViewHolder.name.setTypeface(seguiregular);

                userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

                userViewHolder.imageview.setImageUrl(itemmodel.getProfilepic(), mImageLoader);
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


            return commentlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return commentlist.size();
        }
    }
    public static class MyDialogFragment extends DialogFragment {
        private String KEY_COMMENT = "comment";
        private String KEY_TYPE = "qtype";
        private String KEY_MYUID = "user_id";
        private String KEY_POSTID = "id";
        String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=travel&id=";
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

        SharedPreferences sharedPreferences;
        public static final String mypreference = "mypref";
        String colorcodes;
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
            View root = inflater.inflate(R.layout.commenttravelfragment, container, false);

            sharedPreferences = getActivity(). getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE);

            colorcodes=sharedPreferences.getString(backgroundcolor,"");
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
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycler.setNestedScrollingEnabled(false);
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
                                param.put("qtype", "travels");
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
                    param.put("qtype","travels");
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


                    if(colorcodes.equals("#FFFFFFFF")){
                        userViewHolder.name.setText(itemmodel.getName());
                        userViewHolder.name.setTextColor(getResources().getColor(android.R.color.black));

                        userViewHolder.commentsdecription.setText(itemmodel.getComment());
                        userViewHolder.commentsdecription.setTextColor(getResources().getColor(android.R.color.black));

                    }else {

                        userViewHolder.name.setText(itemmodel.getName());

                        userViewHolder.commentsdecription.setText(itemmodel.getComment());
                    }
                    userViewHolder.name.setTypeface(seguiregular);

                    userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                    userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

                    userViewHolder.imageview.setImageUrl(itemmodel.getProfilepic(), mImageLoader);
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
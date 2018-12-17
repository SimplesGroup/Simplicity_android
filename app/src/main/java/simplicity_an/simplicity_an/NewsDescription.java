package simplicity_an.simplicity_an;

import android.annotation.SuppressLint;
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
 * Created by kuppusamy on 2/3/2016.
 */
public class NewsDescription extends AppCompatActivity {
    TextView tv, articleoftheday, sourcelinknews, pdate, sourcelinksimplicity, textview_date;
    NetworkImageView thump;
    WebView description;

    ImageView reporter_profile_image;
    TextView source_reporter_name, sourcereprterdivider, hashtags_title, image_description, short_description, title_category;
    String notifiid, searchnonitiid, shareurl, sharetitle;
    String bimage;
    ImageButton comment, share, menu, back, favourite;
    List<ItemModel> modelList = new ArrayList<ItemModel>();
    String URL = "http://simpli-city.in/request2.php?rtype=news&key=simples&nid=";
    String URLTWO;
    String URLFAV = "http://simpli-city.in/request2.php?rtype=addfav&key=simples";

    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "qid";
    private String KEY_QTYPE = "qtype";


    int post_likes_count, favcount;
    String titl, name;
    RequestQueue queue, requestQueue;
    String s;
    String sourcelink, myprofileid;
    final String TAG_REQUEST = "MY_TAG";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID = "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    public static final String MYACTIVITYSEARCH = "myactivitysearch";
    String activity, contentid, searchactivity_news;
    ProgressDialog pdialog;

    List<ItemModels> commentlist = new ArrayList<ItemModels>();
    RecyclerViewAdapter rcAdapter;
    public static final String USERID = "user_id";
    public static final String QID = "qid";
    public static final String QTYPE = "qtype";

    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO_comment;
    String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=news&id=";
    String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";
    String URLLIKES = "http://simpli-city.in/request2.php?rtype=articlelikes&key=simples";
    RecyclerView recycler;
    LinearLayoutManager mLayoutManager;
    String postid, myuserid;
    LinearLayout commentbox;
    TextView comment_title, loadmore_title;
    EditText commentbox_editext;
    Button post;
    RecyclerView recycler_comment;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii, i;
    private String KEY_COMMENT = "comment";
    private String KEY_TYPE = "qtype";
    public static final String USERNAME = "myprofilename";
    public static final String USERIMAGE = "myprofileimage";
    String description_comment, my_profilename, my_profileimage;
    ScrollView scrollView;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    LinearLayout commentboxlayout;
    String fontname;
    Typeface tf_play;
    Typeface tf_bold;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.newsdescription);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        // searchactivity_news=sharedpreferences.getString(MYACTIVITYSEARCH,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+", "");
            contentid = sharedpreferences.getString(CONTENTID, "");
            activity = sharedpreferences.getString(Activity, "");
        }
        if (sharedpreferences.contains(USERNAME)) {

            my_profilename = sharedpreferences.getString(USERNAME, "");

        }
        if (sharedpreferences.contains(USERIMAGE)) {

            my_profileimage = sharedpreferences.getString(USERIMAGE, "");

        }
        requestQueue = Volley.newRequestQueue(this);
        colorcodes = sharedpreferences.getString(backgroundcolor, "");
        Log.e("ColorCodeNews",colorcodes);
        mainlayout = (RelativeLayout) findViewById(R.id.version_main_layout);

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


        Intent getnotifi = getIntent();
        searchnonitiid = getnotifi.getStringExtra("IDSEARCH");
        notifiid = getnotifi.getStringExtra("ID");
        Log.e("ID", notifiid);

        if (myprofileid != null) {
            URLTWO = URL + notifiid + "&user_id=" + myprofileid;
            Log.e("URL", URLTWO);
        } else {
            URLTWO = URL + notifiid;
            Log.e("URL", URLTWO);
        }

        commentbox = (LinearLayout) findViewById(R.id.comments_versiontwo);
        comment_title = (TextView) findViewById(R.id.comments_title);
        loadmore_title = (TextView) findViewById(R.id.loadmore);
        commentbox_editext = (EditText) findViewById(R.id.comment_description);
        post = (Button) findViewById(R.id.post_button);
        recycler_comment = (RecyclerView) findViewById(R.id.commentpagelist_recyclerview);
        recycler_comment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        scrollView = (ScrollView) findViewById(R.id.scroll);

        tv = (TextView) findViewById(R.id.textView_titlename);
        pdate = (TextView) findViewById(R.id.textView_date);
        textview_date = (TextView) findViewById(R.id.textView_datenew);
        sourcelinknews = (TextView) findViewById(R.id.sourcelink);
        sourcelinksimplicity = (TextView) findViewById(R.id.sourcelinkredsimplicity);
        source_reporter_name = (TextView) findViewById(R.id.textView_sourcename);
        sourcereprterdivider = (TextView) findViewById(R.id.centerdivider);
        hashtags_title = (TextView) findViewById(R.id.textView_hashtags);
        reporter_profile_image = (ImageView) findViewById(R.id.profile_reporter);
        image_description = (TextView) findViewById(R.id.textView_photodescription);
        short_description = (TextView) findViewById(R.id.textView_shortdescription);
        title_category = (TextView) findViewById(R.id.textView_qtypename);
        comment = (ImageButton) findViewById(R.id.btn_4);
        share = (ImageButton) findViewById(R.id.btn_share);
        menu = (ImageButton) findViewById(R.id.btn_3);
        favourite = (ImageButton) findViewById(R.id.btn_like);
        description = (WebView) findViewById(R.id.textView_desc);
        description.getSettings().setLoadsImagesAutomatically(true);
        description.getSettings().setPluginState(WebSettings.PluginState.ON);
        description.getSettings().setAllowFileAccess(true);

        description.getSettings().setJavaScriptEnabled(true);

        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);


        if(fontname.equals("sanfrancisco")){
            String playfair ="fonts/Oxygen-Bold.ttf";
            tf_play = Typeface.createFromAsset(getApplicationContext().getAssets(), playfair);
            String simplycity_title_bold = "fonts/SystemSanFranciscoDisplayRegular.ttf";
            tf_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);
            tv.setTextSize(20);
            tv.setLineSpacing(0,1f);
        }else {
            String playfair = "fonts/playfairDisplayRegular.ttf";
            tf_play = Typeface.createFromAsset(getApplicationContext().getAssets(), playfair);
            String simplycity_title_bold = "fonts/Lora-Regular.ttf";
            tf_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);
        }
        if(colorcodes.equals("#FFFFFFFF")){
            tv.setTextColor(Color.BLACK);
            comment_title.setTextColor(Color.BLACK);
            loadmore_title.setTextColor(Color.BLACK);
            post.setTextColor(Color.BLACK);
            source_reporter_name.setTextColor(Color.BLACK);
            sourcelinknews.setTextColor(Color.BLACK);
            sourcelinksimplicity.setTextColor(Color.BLACK);
            image_description.setTextColor(Color.BLACK);
            short_description.setTextColor(Color.BLACK);
            commentbox_editext.setBackgroundResource(R.drawable.editextboxwhite);
            commentbox_editext.setTextColor(getResources().getColor(android.R.color.black));
            comment_title.setBackgroundResource(R.drawable.editextboxwhite);

        }
        else{
            tv.setTextColor(Color.WHITE);
            comment_title.setTextColor(Color.WHITE);
            loadmore_title.setTextColor(Color.WHITE);
            post.setTextColor(Color.WHITE);
            source_reporter_name.setTextColor(Color.WHITE);
            sourcelinknews.setTextColor(Color.WHITE);
            sourcelinksimplicity.setTextColor(Color.WHITE);
            image_description.setTextColor(Color.WHITE);
            short_description.setTextColor(Color.WHITE);
        }

        tv.setTypeface(tf_play);
        textview_date.setTypeface(tf_bold);
        comment_title.setTypeface(tf_bold);
        loadmore_title.setTypeface(tf_bold);
        post.setTypeface(tf_bold);
        sourcelinknews.setTypeface(tf_bold);
        sourcelinksimplicity.setTypeface(tf_bold);
        source_reporter_name.setTypeface(tf_bold);
        image_description.setTypeface(tf_bold);
        short_description.setTypeface(tf_bold);
        title_category.setTypeface(tf_bold);
        commentbox_editext.setHint("Post your Comments Here");
        pdate.setTypeface(tf_bold);
        thump = (NetworkImageView) findViewById(R.id.thumbnailone);
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (notifiid != null) {
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
                    param.put("rtype","news");
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

        } else {


        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MusicplayerBottom.mediaPlayer.isPlaying()) {
                    Log.e("NEWSSONG", "ISPLAYING");
                    MusicplayerBottom.mediaPlayer.pause();
                } else {
                    Log.e("NEWSSONG", "NO");
                }
                Intent menudesc = new Intent(getApplicationContext(), MainActivityVersiontwo.class);
                startActivity(menudesc);
                finish();
            }
        });
        if (myprofileid != null) {

            post.setText("Post");

            commentbox.setVisibility(View.VISIBLE);

            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://simpli-city.in/request.php?rtype=comments2&key=simples";
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
                                    param.put("qtype", "news");
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
            rcAdapter = new RecyclerViewAdapter(commentlist, recycler_comment);
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


                        //    getData();


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

        } else {
            commentbox.setVisibility(View.GONE);
        }
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
                    editor.putString(Activity, "newsdesc");
                    editor.putString(CONTENTID, notifiid);
                    editor.commit();
                    Intent signin = new Intent(NewsDescription.this, SigninpageActivity.class);
                    startActivity(signin);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void AddnewCommnent() {
        int curSize = rcAdapter.getItemCount();
        ItemModels models = new ItemModels();
        models.setName(my_profilename);
        models.setProfilepic(my_profileimage);
        models.setComment(description_comment);
        commentlist.add(models);
        recycler_comment.setVisibility(View.VISIBLE);
        rcAdapter.notifyDataSetChanged();
        rcAdapter.notifyItemRangeInserted(curSize, commentlist.size());
    }

    private void parseJsonFeed(JSONArray response) {
        ImageLoader mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
        try {
           // JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < response.length(); i++) {
                final JSONObject obj = (JSONObject) response.get(i);


                ItemModel model = new ItemModel();

                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);

                model.setDescription(obj.getString("description"));
                //model.setTypeid(obj.getInt("type"));
                model.setPdate(obj.getString("date"));
                model.setTitle(obj.getString("title"));

                model.setSource(obj.getString("source"));
                tv.setText(Html.fromHtml(obj.getString("title")));
                model.setFavcount(obj.getInt("like_type"));
                model.setShareurl(obj.getString("sharingurl"));
                model.setShortdescription(obj.getString("short_description"));
//                model.setReporterid(obj.getString("reporter_id"));
                model.setReportername(obj.getString("reporter_name"));
                model.setReporterimage(obj.getString("reporter_image"));
//                model.setReporterurl(obj.getString("reporter_url"));
//                model.setPhotocreditid(obj.getString("photo_credit_id"));
                model.setPhotocreditimage(obj.getString("photo_credit_image"));
                model.setPhotocreditname(obj.getString("photo_credit_name"));
//                model.setPhotocrediturl(obj.getString("photo_credit_url"));


                favcount = obj.getInt("like_type");
                post_likes_count = obj.getInt("like_type");
                shareurl = obj.getString("sharingurl");
                sharetitle = obj.getString("title");

                thump.setDefaultImageResId(R.mipmap.ic_launcher);
                thump.setErrorImageResId(R.drawable.iconlogo);
                thump.setImageUrl(image, mImageLoader);
                image_description.setText("");
                if (short_description != null) {
                    short_description.setText(obj.getString("short_description").trim());
                } else {
                    short_description.setVisibility(View.GONE);
                }

                hashtags_title.setText("");
                title_category.setText(obj.getString("qtype"));
                String reporterimage = obj.getString("reporter_image");
                if (reporterimage.equals("null") || reporterimage.equals("")) {

                } else {
                    Picasso.with(getApplicationContext())
                            .load(reporterimage)
                            .centerCrop()
                            .resize(40, 40)
                            .into(reporter_profile_image);
                }
                String by = "By&nbsp;";
                if (obj.getString("reporter_name").equals("") || obj.getString("reporter_name").equals("null")) {
                    source_reporter_name.setText(Html.fromHtml(obj.getString("source")));
                } else {
                    if(obj.getString("source").equals("")){
                        source_reporter_name.setText(Html.fromHtml(obj.getString("reporter_name")+"&nbsp;"));
                    }else {
                        source_reporter_name.setText(Html.fromHtml(obj.getString("reporter_name") + "&nbsp;"+"|"+"&nbsp;"+obj.getString("source")));
                    }

                }
                pdate.setText(Html.fromHtml("&nbsp;"+obj.getString("source")));
                textview_date.setText(obj.getString("date"));
                if(obj.getString("photo_credit_name").equals("")){

                }else {
                    image_description.setVisibility(View.VISIBLE);
                   image_description.setText("Photography by:"+obj.getString("photo_credit_name"));
                }
                String descrition = obj.isNull("description") ? null : obj
                        .getString("description");
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

                Log.e("DESC", descrition.toString());
                Log.e("DESC", rep.toString());
                Log.e("DESC", description.toString());
                if (obj.getString("source") != null) {
                    //sourcelinknews.setText(Html.fromHtml("Source:"));
                    // sourcelinksimplicity.setText(Html.fromHtml("<u>" + obj.getString("source") + "</u>"));
                } else {

                }
              //  final String sourcelink = obj.getString("source_link");
                sourcelinksimplicity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sourcelink.equals("") || sourcelink.equals("null")) {

                        } else {
                            Intent ads = new Intent(getApplicationContext(), AdvertisementPage.class);
                            ads.putExtra("ID", sourcelink);
                            startActivity(ads);
                        }
                    }
                });
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

                        if (myprofileid != null) {
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

                        } else {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Activity, "mainversion");
                            editor.putString(CONTENTID, "0");
                            editor.commit();
                            Intent sign = new Intent(getApplicationContext(), SigninpageActivity.class);
                            startActivity(sign);

                        }


                    }
                });

                modelList.add(model);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http://simpli")) {
                Intent ads = new Intent(getApplicationContext(), AdvertisementPage.class);
                ads.putExtra("ID", url);
                startActivity(ads);
            } else {
                Intent ads = new Intent(getApplicationContext(), AdvertisementPage.class);
                ads.putExtra("ID", url);
                startActivity(ads);

            }


            return true;
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
        private String publisher;
        private String company;

        private String category_name;
        private String source;
        private String source_link;
        private String id;
        private int favcount;
        private String shareurl;

        String shortdescription, reporterid, reportername, reporterurl, reporterimage;
        String photodescription, photocreditid, photocreditname, photocreditimage, photocrediturl;

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


        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name, locations, commentsdecription;


        public NetworkImageView imageview;

        public UserViewHolder(View itemView) {
            super(itemView);


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
                param.put("qtype","news");
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
    class ItemModels {
        private String id, comment, padate, name, profilepic;

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
        String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=news&id=";
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
            View root = inflater.inflate(R.layout.commentnewsfragment, container, false);
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
                                param.put("qtype", "news");
                                return param;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(comment_post_request);
                    } catch (Exception e) {

                    }
                }

            });

            rcAdapter = new RecyclerViewAdapter(commentlist, recycler);
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
            public TextView name, locations, commentsdecription;


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
                    param.put("qtype","news");
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
        class ItemModels {
            private String id, comment, padate, name, profilepic;

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


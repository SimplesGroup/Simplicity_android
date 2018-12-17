package simplicity_an.simplicity_an.Tamil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.AppControllers;
import simplicity_an.simplicity_an.DividerItemDecoration;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.Utils.Fonts;

/**
 * Created by kuppusamy on 2/1/2016.
 */
public class TamilArticledescription extends AppCompatActivity {
    TextView tv,articleoftheday,authorname,pdate,sourcelinknews,sourcelinksimplicity;//description;
    NetworkImageView thump;
    WebView description;
    ImageView reporter_profile_image;
    TextView source_reporter_name,sourcereprterdivider,hashtags_title,image_description,short_description,title_category,textview_date;

    ImageButton comment,share,menu,back,favourite;
 String notifiid,shareurl,sharetitle;
int favcount;
    RequestQueue queue;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
     String URL="http://simpli-city.in/request2.php?rtype=article&key=simples&aid=";
    String URLTWO;
    String URLFAV="http://simpli-city.in/request2.php?rtype=addfav&key=simples";


    int post_likes_count;
     String sourcelink,myprofileid;

    SharedPreferences sharedpreferences,shredpreferencemyactivity;
    public static final String mypreference = "mypref";
    public static final String myACTIVITY = "myactivity";
    public static final String MYUSERID= "myprofileid";
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
    String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=article&id=";
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
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerViewAdapter rcAdapter;
    ProgressDialog pdialog;
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO_comment;
    String bimage;
    RecyclerView recycler;
    LinearLayoutManager mLayoutManager;
    String postid, myuserid;
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    String description_comment,my_profilename,my_profileimage;
ScrollView scrollView;
    LinearLayout commentboxlayout;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    String fontname;
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.newsdescriptiontamil);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        searchactivity_article=sharedpreferences.getString(MYACTIVITYSEARCH,"");

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

        mainlayout=(RelativeLayout)findViewById(R.id.version_main_layout);
        fontname=sharedpreferences.getString(Fonts.FONT,"");
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
            int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};
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
                int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};
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
                        int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

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
                    int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

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
        requestQueue = Volley.newRequestQueue(this);
if(activity==null){
    Intent getnotifi=getIntent();
    notifiid=getnotifi.getStringExtra("ID");
    searchnonitiid = getnotifi.getStringExtra("IDSEARCH");
    notifiid = notifiid.replaceAll("\\D+","");
    Log.e("ID",notifiid);

} else {
    if(activity.equalsIgnoreCase("articledesctamil")){
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

        tv = (TextView) findViewById(R.id.textView_titlename);
        sourcelinknews = (TextView) findViewById(R.id.sourcelink);
        sourcelinksimplicity = (TextView) findViewById(R.id.sourcelinkredsimplicity);
        source_reporter_name=(TextView)findViewById(R.id.textView_sourcename) ;
        sourcereprterdivider=(TextView)findViewById(R.id.centerdivider);
        hashtags_title=(TextView) findViewById(R.id.textView_hashtags);
        reporter_profile_image=(ImageView)findViewById(R.id.profile_reporter);
        image_description=(TextView)findViewById(R.id.textView_photodescription);
        short_description=(TextView)findViewById(R.id.textView_shortdescription);
        title_category=(TextView)findViewById(R.id.textView_qtypename) ;
        pdate = (TextView) findViewById(R.id.textView_date);
        textview_date=(TextView) findViewById(R.id.textView_datenew);
        share = (ImageButton) findViewById(R.id.btn_share);
        menu = (ImageButton) findViewById(R.id.btn_3);
        back = (ImageButton) findViewById(R.id.btn_back);
        favourite = (ImageButton) findViewById(R.id.btn_like);
        description = (WebView) findViewById(R.id.textView_desc);
        description.getSettings().setLoadsImagesAutomatically(true);
        description.getSettings().setPluginState(WebSettings.PluginState.ON);
        description.getSettings().setAllowFileAccess(true);
        description.getSettings().setJavaScriptEnabled(true);

        /*Resources res = getResources();
        float  fontSize = res.getDimension(R.dimen.txtSize);
        description.getSettings().setDefaultFontSize((int)fontSize);*/

        commentbox=(LinearLayout)findViewById(R.id.comments_versiontwo) ;
        comment_title=(TextView)findViewById(R.id.comments_title);
        loadmore_title=(TextView)findViewById(R.id.loadmore);
        commentbox_editext=(EditText)findViewById(R.id.comment_description);
        post=(Button)findViewById(R.id.post_button) ;
        recycler_comment=(RecyclerView)findViewById(R.id.commentpagelist_recyclerview) ;
        scrollView=(ScrollView)findViewById(R.id.scroll);
      //  recycler_comment.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        recycler_comment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String simplycity_title_fontPath = "fonts/TAU_Elango_Madhavi.TTF";;
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        String simplycity_title_fontPathone = "fonts/TAU_Elango_Madhavi.TTF";;
        Typeface tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPathone);
        if(fontname.equals("playfair")){
            String playfa ="fonts/TAU_Elango_Madhavi.TTF";
            tf = Typeface.createFromAsset(getApplicationContext().getAssets(), playfa);
            tv.setTypeface(tf);
            tv.setTextSize(27);

        }else {
            String play = "fonts/MUKTAMALAR-BOLD.TTF";
            tf= Typeface.createFromAsset(getApplicationContext().getAssets(), play);
            tv.setTypeface(tf);
            tv.setTextSize(22);
        }

        sourcelinknews.setTypeface(tf);
        sourcelinksimplicity.setTypeface(tf);
        comment_title.setTypeface(tf);
        loadmore_title.setTypeface(tf);
        post.setTypeface(tf);


        hashtags_title.setTypeface(tf_regular);
        source_reporter_name.setTypeface(tf_regular);
        pdate.setTypeface(tf);

        if(colorcodes.equals("#FFFFFFFF")){
            tv.setTextColor(Color.BLACK);
            comment_title.setTextColor(Color.BLACK);
            loadmore_title.setTextColor(Color.BLACK);
            source_reporter_name.setTextColor(Color.BLACK);
            sourcelinknews.setTextColor(Color.BLACK);
            sourcelinksimplicity.setTextColor(Color.BLACK);
            pdate.setTextColor(Color.BLACK);
            post.setTextColor(Color.BLACK);
            back.setImageResource(R.mipmap.backtamilone);
            commentboxlayout.setBackgroundColor(Color.WHITE);
            commentbox_editext.setBackgroundResource(R.drawable.editextboxwhite);
            commentbox_editext.setTextColor(getResources().getColor(android.R.color.black));
            comment_title.setBackgroundResource(R.drawable.editextboxwhite);

        }
        else{
            tv.setTextColor(Color.WHITE);
            comment_title.setTextColor(Color.WHITE);
            loadmore_title.setTextColor(Color.WHITE);
            source_reporter_name.setTextColor(Color.WHITE);
            sourcelinknews.setTextColor(Color.WHITE);
            sourcelinksimplicity.setTextColor(Color.WHITE);
            pdate.setTextColor(Color.WHITE);
            post.setTextColor(Color.WHITE);
            back.setImageResource(R.mipmap.backtamil);
            commentboxlayout.setBackgroundColor(Color.BLACK);
            commentbox_editext.setBackgroundResource(R.drawable.editextbox);
            comment_title.setBackgroundResource(R.drawable.editextbox);
        }

        thump = (NetworkImageView) findViewById(R.id.thumbnailone);
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //if(notifiid!=null) {
            JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


                public void onResponse(JSONObject response) {

                    //VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        pdialog.dismiss();
                        //dissmissDialog();
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
           requestQueue.add(jsonreq);


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBackPressed();
                }
            });
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent menu_article = new Intent(getApplicationContext(), MainPageTamil.class);
                    startActivity(menu_article);
                    finish();
                }
            });
if(myprofileid!=null){
   // URLTWO_comment=URLCOMMENT+notifiid;
   // comment_title.setText("Post your Comment Here - ");
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
            //Showing the progress dialog
            //final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlpost,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                          //  pdialog.dismiss();
                            //Disimissing the progress dialog
                            //  loading.dismiss();
                            //Showing toast message of the response
                            if (s.equalsIgnoreCase("error")) {
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            } else {
                                //MyDialogFragment.this.dismiss();

                                commentbox_editext.setText("");
                                AddnewCommnent();
                                scrollView.post(new Runnable() {
                                    public void run() {
                                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                    }
                                });
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //Dismissing the progress dialog
                            //  loading.dismiss();

                            //Showing toast
                            // Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Converting Bitmap to String


                    //Getting Image Name
                    description_comment = commentbox_editext.getText().toString().trim();

                    //Creating parameters
                    Map<String, String> params = new Hashtable<String, String>();

                    //Adding parameters

                            params.put(KEY_COMMENT, description_comment);
                            params.put(KEY_TYPE, "article");
                            params.put(KEY_POSTID,notifiid);
                            params.put(KEY_MYUID, myprofileid);

                    return params;
                }
            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            //Adding request to the queue
            requestQueue.add(stringRequest);
            //queue.add(stringRequest);
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


                    getData();


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

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    MyDialogFragment frag;
                    frag = new MyDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("POSTID", notifiid);
                    args.putString("USERID", myprofileid);
                    frag.setArguments(args);
                    frag.show(ft, "txn_tag");
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "articledesctamil");
                    editor.putString(CONTENTID, notifiid);
                    editor.commit();
                    Intent signin=new Intent(TamilArticledescription.this,SigninpageActivity.class);
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
    private void parseJsonFeed(JSONObject response) {
        ImageLoader  mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                final JSONObject obj = (JSONObject) feedArray.get(i);


                ItemModel model = new ItemModel();

                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);


                model.setName(obj.getString("publisher_name"));
                model.setDescription(obj.getString("description"));
                model.setTypeid(obj.getInt("type"));
                model.setPdate(obj.getString("pdate"));
                model.setTitle(obj.getString("title"));
                model.setPublisher(obj.getString("pub_designation"));
                model.setCompany(obj.getString("company"));
                model.setSource(obj.getString("source"));
                model.setSource_link(obj.getString("source_link"));
                model.setId(obj.getString("id"));
               tv.setText(Html.fromHtml(obj.getString("title")));
                model.setFavcount(obj.getInt("fav_count"));
                model.setShareurl(obj.getString("sharingurl"));
                favcount=obj.getInt("fav_count");
                post_likes_count=obj.getInt("fav_count");
                shareurl=obj.getString("sharingurl");
                sharetitle=obj.getString("title");
                //articleoftheday.setText(obj.getString("qtype"));
                thump.setDefaultImageResId(R.mipmap.ic_launcher);
                thump.setErrorImageResId(R.mipmap.ic_launcher);
                thump.setImageUrl(image, mImageLoader);
                //thump.setImageUrl(image, imageLoader);
               // hashtags_title.setText("#Article of the day");
                String by = "By&nbsp;";

                String descrition = obj.isNull("description") ? null : obj
                        .getString("description");
                String ss = descrition;

                image_description.setText("");
                if(short_description!=null){
                    short_description.setText(obj.getString("short_description"));
                }else {
                    short_description.setVisibility(View.GONE);
                }
                hashtags_title.setText("");
                title_category.setText(obj.getString("qtype"));
                String reporterimage=obj.getString("reporter_image");
                if(reporterimage.equals("null")||reporterimage.equals("")){

                }else {
                    Picasso.with(getApplicationContext())
                            .load(reporterimage)
                            .centerCrop()
                            .resize(40, 40)
                            .into(reporter_profile_image);
                }

                if (obj.getString("reporter_name").equals("") || obj.getString("reporter_name").equals("null")) {                     source_reporter_name.setText(Html.fromHtml(obj.getString("source")));                 } else {                     if(obj.getString("source").equals("")){                         source_reporter_name.setText(Html.fromHtml(obj.getString("reporter_name")+"&nbsp;"));                     }else {                         source_reporter_name.setText(Html.fromHtml(obj.getString("reporter_name") + "&nbsp;"+"|"+"&nbsp;"+obj.getString("source")));                     }                  }
                pdate.setText(Html.fromHtml( obj.getString("source")));
                textview_date.setText(obj.getString("pdate"));

                String s = ss;
                // s = s.replace("\"", "'");
                s = s.replace("\\", "");


              // String fonts="<html>\n" +         "\t<head>\n" +         "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +         "\t\t<style>\n" +         "\t\t@font-face {\n" +         "  font-family: 'segeoui-light';\n" +         " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "  font-family: 'segeoui-regular';\n" +         "src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "  font-family: 'segeoui-sbold';\n" +         " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Bold';\n" +         "   src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Light';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Regular';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Thin';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "\t\t</style>\n" +         "\t</head>"; description.loadDataWithBaseURL("", fonts+descrition+"</head>", "text/html", "utf-8", "");
                // description.setBackgroundColor(0x0a000000);
                description.setBackgroundColor(Color.TRANSPARENT);

                String simplycity_title_fontPath = "fonts/TAU_Elango_Madhavi.TTF";;
                Typeface tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                String fonts="<html>\n" +
                        "\t<head>\n" +
                        "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +
                        "\t\t<style>\n" +
                        "\t\t@font-face {\n" +
                        "  font-family: 'segeoui-light';\n" +
                        " src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "  font-family: 'segeoui-regular';\n" +
                        "src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "  font-family: 'segeoui-sbold';\n" +
                        " src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Bold';\n" +
                        "   src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Light';\n" +
                        "    src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Regular';\n" +
                        "    src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Thin';\n" +
                        "    src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "\t\t</style>\n" +
                        "\t</head>";
                String rep = String.valueOf(descrition);
                rep =  rep.replaceAll("color:#fff","color:#000");
                if(colorcodes.equals("#FFFFFFFF")) {
                    description.loadDataWithBaseURL("", fonts + rep + "</head>", "text/html", "utf-8", "");
                }else{
                    description.loadDataWithBaseURL("", fonts + descrition + "</head>", "text/html", "utf-8", "");
                }
                description.setWebViewClient(new MyBrowser());
                // description.loadUrl(fonts+descrition+"</head>");

                description.setBackgroundColor(Color.TRANSPARENT);
                modelList.add(model);
                if (favcount == 1) {
                    favourite.setImageResource(R.mipmap.likered);
                    favourite.setTag("heartfullred");
                } else {
                    favourite.setImageResource(R.mipmap.like);
                    favourite.setTag("heart");
                }
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
                            StringRequest likes=new StringRequest(Request.Method.POST, URLLIKES, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String res;                                     try {                                                                 Log.e("RES", "START");                                           JSONObject data = new JSONObject(response.toString());                                            String dir = data.getString("result");                                           Log.d("RES", dir);                                                            JSONObject object=new JSONObject(dir);                                           String dir2=object.getString("message");                                            Log.d("RES", dir2);                                                        for (int i = 0; i < object.length(); i++) {                                                   String dirs = object.getString("message");                                                 Log.d("RES", dirs);                                                        res=object.getString("message");                                                                                            if(res.equals("Liked")){                                                       favourite.setImageResource(R.mipmap.likered);                                                  favourite.setTag("heartfullred");                                                 }else if(res.equals("Like")){                                                    favourite.setImageResource(R.mipmap.like);                                                  favourite.setTag("heart");                                                }                                                }                                             }catch (JSONException e){                                                                                  }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                protected Map<String,String> getParams()throws AuthFailureError{
                                    Map<String,String> param=new Hashtable<String, String>();

                                    String postid = notifiid;
                                    //Adding parameters
                                    param.put(QID, postid);
                                    param.put(USERID, myprofileid);
                                    param.put(QTYPE, "article");
                                   /* if (postid != null) {


                                        param.put(QID, ids);
                                        param.put(USERID, myprofileid);
                                        param.put(QTYPE, itemmodel.getQtypemain());
                                    } else {


                                    }*/
                                    return param;
                                }
                            };
                            RequestQueue likesqueue=Volley.newRequestQueue(getApplicationContext());
                            likes.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            likesqueue.add(likes);

                        }else {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Activity, "articledesctamil");
                            editor.putString(CONTENTID, notifiid);
                            editor.commit();
                            Intent signin=new Intent(TamilArticledescription.this,SigninpageActivity.class);
                            startActivity(signin);
                            finish();
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
            }

            // notify data changes to list adapater
           // rcAdapter.notifyDataSetChanged();
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
                Intent ads=new Intent(getApplicationContext(),AdvertisementPage.class);
                ads.putExtra("ID",url);
                startActivity(ads);
                // view.loadUrl(url);
            }
            //

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
        /******** start the Food category names****/
        private String source;
        private String source_link;
        String id;
        private int favcount;
        private String shareurl;

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
        public String getSource_link() {
            return source_link;
        }

        public void setSource_link(String source_link) {
            this.source_link = source_link;
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
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;
    }

    private JsonObjectRequest getDataFromTheServer(int requestCount) {

        URLTWO_comment = URLCOMMENT +notifiid+"&page="+ requestCount;


        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLTWO_comment);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    pdialog.dismiss();
                    // dissmissDialog();
                    parseJsonFeedTwo(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLTWO_comment, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                    if (response != null) {
                        pdialog.dismiss();
                        //   dissmissDialog();
                        parseJsonFeedTwo(response);
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
    private void parseJsonFeedTwo(JSONObject response) {
        try {
            feedArray = response.getJSONArray("result");


            for (ii = 0; ii < feedArray.length(); ii++) {
                obj = (JSONObject) feedArray.get(ii);

                ItemModels model = new ItemModels();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
                model.setProfilepic(image);
                model.setComment(obj.getString("comment"));
                model.setPadate(obj.getString("pdate"));
                model.setName(obj.getString("name"));
                model.setId(obj.getString("id"));
                if(feedArray.length()==0){

                    recycler_comment.setVisibility(View.GONE);
                }else {
                    recycler_comment.setVisibility(View.VISIBLE);

                }
                if(feedArray.length()==0){
                    loadmore_title.setVisibility(View.GONE);
                }else {
                    if(feedArray.length()>4){
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
            View root = inflater.inflate(R.layout.commentsreview, container, false);

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

                    //Showing the progress dialog
                    //final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlpost,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    //Disimissing the progress dialog
                                    //  loading.dismiss();
                                    //Showing toast message of the response
                                    if (s.equalsIgnoreCase("error")) {
                                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                                    } else {
                                        MyDialogFragment.this.dismiss();

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    //Dismissing the progress dialog
                                    //  loading.dismiss();

                                    //Showing toast
                                    // Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //Converting Bitmap to String


                            //Getting Image Name
                            String description = commentbox.getText().toString().trim();

                            //Creating parameters
                            Map<String, String> params = new Hashtable<String, String>();

                            //Adding parameters
                            if (postid != null) {
                                if (description != null) {
                                    params.put(KEY_COMMENT, description);
                                    params.put(KEY_TYPE, "article");
                                    params.put(KEY_POSTID, postid);
                                    params.put(KEY_MYUID, myuserid);
                                }

                            }
                            return params;
                        }
                    };

                    //Creating a Request Queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    //Adding request to the queue
                    requestQueue.add(stringRequest);
                    //queue.add(stringRequest);
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
            //Adding the method to the queue by calling the method getDataFromServer
            requestQueue.add(getDataFromTheServer(requestCount));
            // getDataFromTheServer();
            //Incrementing the request counter
            requestCount++;
        }

        private JsonObjectRequest getDataFromTheServer(int requestCount) {

            URLTWO = URLCOMMENT +postid+"&page="+ requestCount;


            Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(URLTWO);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                        // dissmissDialog();
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
                        URLTWO, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                        if (response != null) {
                            //   dissmissDialog();
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

        private void parseJsonFeed(JSONObject response) {
            try {
                feedArray = response.getJSONArray("result");


                for (ii = 0; ii < feedArray.length(); ii++) {
                    obj = (JSONObject) feedArray.get(ii);

                    ItemModels model = new ItemModels();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("thumb") ? null : obj
                            .getString("thumb");
                    model.setProfilepic(image);
                    model.setComment(obj.getString("comment"));
                    model.setPadate(obj.getString("pdate"));
                    model.setName(obj.getString("name"));
                    model.setId(obj.getString("id"));


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
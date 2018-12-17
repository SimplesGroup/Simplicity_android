package simplicity_an.simplicity_an.Tamil.Activity;

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
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.AppControllers;
import simplicity_an.simplicity_an.DividerItemDecoration;
import simplicity_an.simplicity_an.JobApplyPage;
import simplicity_an.simplicity_an.JobsDetailPage;
import simplicity_an.simplicity_an.MainActivityVersiontwo;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.Utils.Configurl;
import simplicity_an.simplicity_an.Utils.Fonts;


/**
 * Created by kuppusamy on 1/23/2016.
 */
public class JobsDetailPagetamil extends AppCompatActivity {
    Button applyjob;
    String titl;

    String URLLIKES="http://simpli-city.in/request2.php?rtype=articlelikes&key=simples";
    public static final String USERID="user_id";
    public static final String QID="qid";
    public static final String QTYPE="qtype";

    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;


    NetworkImageView thump;
    TextView title,title_qype;
    TextView jobtypelabel,jobtype,salarylabel,salaryamt,candidateprofile,Candidateprofiledetails,walininterviewlabel,interviewdate,interviewdate_data,interviewtim_label,interviewtiming;
    TextView venue,emailabel,Candidateprofileeducation,Candidateprofileskills;
    WebView jobdescrion_web,job_address_web;
    ProgressDialog pdialog;

    RequestQueue jobrequest;
    final String TAG_REQUEST = "MY_TAG";
    // List<ItemModel> modelList=new ArrayList<ItemModel>();
    String URL="http://simpli-city.in/request2.php?rtype=job&key=simples&jid=";
    String URLFULL;

    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    private String KEY_QTYPE= "qtype";
    String company,myprofileid,notifiid;
    ImageButton menu,back,comment,share,favourite;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    RequestQueue requestQueue;
    JsonObjectRequest jsonReq;
    String URLTWO_comment;
    String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=job&id=";
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
    WebView infodescrtion,infoaddress;
    TextView phonenumber_textview;
    LinearLayout commentboxlayout;
    String fontname;
    Typeface tf;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.jobsactivitydetailpage);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
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
        Intent getnotifi=getIntent();
        notifiid=getnotifi.getStringExtra("ID");
        titl=getnotifi.getStringExtra("TITLE");
        company=getnotifi.getStringExtra("COMPANY");
        String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        String simplycity_title_bold = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);

        applyjob=(Button)findViewById(R.id.applynow_button);
        share = (ImageButton) findViewById(R.id.btn_share);
        back = (ImageButton) findViewById(R.id.btn_back);
        favourite = (ImageButton) findViewById(R.id.btn_like);

        title = (TextView) findViewById(R.id.textView_titlename);
        title_qype=(TextView)findViewById(R.id.textView_qtypename) ;
        thump = (NetworkImageView) findViewById(R.id.photo_image);

        sharedpreferences =getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
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
        ;
        requestQueue=Volley.newRequestQueue(getApplicationContext());




        infodescrtion=(WebView)findViewById(R.id.webView_info_employee);
        infoaddress=(WebView)findViewById(R.id.webView_info_venue);
        phonenumber_textview=(TextView)findViewById(R.id.phonenumber);
        jobtypelabel=(TextView)findViewById(R.id.jobtype_label);
        jobtype=(TextView)findViewById(R.id.jobcategory_label);
        salarylabel=(TextView)findViewById(R.id.salary_label);
        salaryamt=(TextView)findViewById(R.id.salary_amount);
        candidateprofile=(TextView)findViewById(R.id.candidate_profile);
        Candidateprofiledetails=(TextView)findViewById(R.id.candidate_profiledetails);
        Candidateprofileeducation=(TextView)findViewById(R.id.candidate_profile_education);
        Candidateprofileskills=(TextView)findViewById(R.id.candidate_profile_skillls);
        walininterviewlabel=(TextView)findViewById(R.id.Walkininterview_label_i);
        interviewdate=(TextView)findViewById(R.id.inertviewdate);
        interviewdate_data=(TextView)findViewById(R.id.inertviewdate_data);
        interviewtim_label=(TextView)findViewById(R.id.interviewtiming);
        interviewtiming=(TextView)findViewById(R.id.interviewtiming_data);
        venue=(TextView)findViewById(R.id.venue);
        emailabel=(TextView)findViewById(R.id.emaillabel);
        jobdescrion_web=(WebView)findViewById(R.id.webView_jobdescription);
        jobdescrion_web.getSettings().setLoadsImagesAutomatically(true);
        jobdescrion_web.getSettings().setPluginState(WebSettings.PluginState.ON);
        jobdescrion_web.getSettings().setAllowFileAccess(true);
        jobdescrion_web.getSettings().setJavaScriptEnabled(true);
        job_address_web=(WebView)findViewById(R.id.webView_venuedetails);
        job_address_web.getSettings().setLoadsImagesAutomatically(true);
        job_address_web.getSettings().setPluginState(WebSettings.PluginState.ON);
        job_address_web.getSettings().setAllowFileAccess(true);
        job_address_web.getSettings().setJavaScriptEnabled(true);
        jobtypelabel.setTypeface(tf);
        jobtype.setTypeface(tf);
        salarylabel.setTypeface(tf);
        salaryamt.setTypeface(tf);
        candidateprofile.setTypeface(tf);
        Candidateprofileskills.setTypeface(tf);
        Candidateprofileeducation.setTypeface(tf);
        Candidateprofiledetails.setTypeface(tf);
        walininterviewlabel.setTypeface(tf);
        interviewdate.setTypeface(tf);
        interviewdate_data.setTypeface(tf);
        interviewtim_label.setTypeface(tf);
        interviewtiming.setTypeface(tf);
        venue.setTypeface(tf);
        emailabel.setTypeface(tf);
        phonenumber_textview.setTypeface(tf);
        title.setTypeface(tf_bold);
        title_qype.setTypeface(tf);

        jobrequest= Volley.newRequestQueue(getApplicationContext());

        if(colorcodes.equals("#FFFFFFFF")){

            //comment_title.setTextColor(Color.BLACK);
            //loadmore_title.setTextColor(Color.BLACK);
            jobtypelabel.setTextColor(Color.BLACK);
            jobtype.setTextColor(Color.BLACK);
            salarylabel.setTextColor(Color.BLACK);
            salaryamt.setTextColor(Color.BLACK);
            candidateprofile.setTextColor(Color.BLACK);
            Candidateprofileskills.setTextColor(Color.BLACK);
            Candidateprofileeducation.setTextColor(Color.BLACK);
            Candidateprofiledetails.setTextColor(Color.BLACK);
            walininterviewlabel.setTextColor(Color.BLACK);
            interviewdate.setTextColor(Color.BLACK);
            interviewdate_data.setTextColor(Color.BLACK);
            interviewtim_label.setTextColor(Color.BLACK);
            interviewtiming.setTextColor(Color.BLACK);
            venue.setTextColor(Color.BLACK);
            emailabel.setTextColor(Color.BLACK);
            phonenumber_textview.setTextColor(Color.BLACK);
            title.setTextColor(Color.BLACK);
            title_qype.setTextColor(Color.BLACK);
            applyjob.setTextColor(Color.BLACK);

            interviewdate.setBackgroundResource(R.drawable.whiteback);
            interviewdate_data.setBackgroundResource(R.drawable.whiteback);
            jobtype.setBackgroundResource(R.drawable.whiteback);
            jobtypelabel.setBackgroundResource(R.drawable.whiteback);
            salarylabel.setBackgroundResource(R.drawable.whiteback);
            salaryamt.setBackgroundResource(R.drawable.whiteback);
            candidateprofile.setBackgroundResource(R.drawable.whiteback);
            interviewtim_label.setBackgroundResource(R.drawable.whiteback);
            interviewtiming.setBackgroundResource(R.drawable.whiteback);
            venue.setBackgroundResource(R.drawable.whiteback);
        }
        else{
//            comment_title.setTextColor(Color.WHITE);
            //loadmore_title.setTextColor(Color.WHITE);
            jobtypelabel.setTextColor(Color.WHITE);
            jobtype.setTextColor(Color.WHITE);
            salarylabel.setTextColor(Color.WHITE);
            salaryamt.setTextColor(Color.WHITE);
            candidateprofile.setTextColor(Color.WHITE);
            Candidateprofileskills.setTextColor(Color.WHITE);
            Candidateprofileeducation.setTextColor(Color.WHITE);
            Candidateprofiledetails.setTextColor(Color.WHITE);
            walininterviewlabel.setTextColor(Color.WHITE);
            interviewdate.setTextColor(Color.WHITE);
            interviewdate_data.setTextColor(Color.WHITE);
            interviewtim_label.setTextColor(Color.WHITE);
            interviewtiming.setTextColor(Color.WHITE);
            venue.setTextColor(Color.WHITE);
            emailabel.setTextColor(Color.WHITE);
            phonenumber_textview.setTextColor(Color.WHITE);
            title.setTextColor(Color.WHITE);
            title_qype.setTextColor(Color.WHITE);
        }

        /*pdialog = new ProgressDialog(getApplicationContext());
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/



        notifiid=getnotifi.getStringExtra("ID");
        if(notifiid!=null) {
            StringRequest jsonreq = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {


                public void onResponse(String response) {
                    Log.e("Response", response.toString());
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        JSONArray array = object.getJSONArray("result");
                        String data = array.optString(1);
                        JSONArray jsonArray = new JSONArray(data.toString());
                        Log.e("Response", data.toString());
                        if (response != null) {
                            //pdialog.dismiss();
                            parseJsonFeed(jsonArray);
                        }
                    } catch (JSONException e) {

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("Key", "Simplicity");
                    param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                    param.put("language", "2");
                    param.put("rtype", "job");
                    param.put("id", notifiid);
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
                onBackPressed();
            }
        });

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







        applyjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent applypage=new Intent(getApplicationContext(),JobApplyPagetamil.class);
                applypage.putExtra("TITLE",titl);
                applypage.putExtra("COMPANY",company);
                startActivity(applypage);
            }
        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void parseJsonFeed(JSONArray response) {
        ImageLoader mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
        try {
            // JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = (JSONObject) response.get(i);

                ItemModel model=new ItemModel();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);
                String timin = obj.isNull("time") ? null : obj
                        .getString("time");
                model.setTiming(timin);
                //bimage=obj.isNull("bimage")?null:obj.getString("bimage");
                String id = obj.isNull("id") ? null : obj
                        .getString("id");
                model.setId(id);
                //  model.setTiming(obj.getString("timing"));
                String tformat = obj.isNull("tformat") ? null : obj
                        .getString("tformat");
                model.setTformat(tformat);
                // model.setTformat(obj.getString("tformat"));
                String location = obj.isNull("location") ? null : obj
                        .getString("location");
                model.setLocation(location);
                // model.setLocation(obj.getString("location"));
              /*  int types = obj.isNull("type") ? null : obj
                        .getInt("type");
                model.setTypeid(types);*/
                // model.setTypeid(obj.getInt("type"));
                String jobdates = obj.isNull("job_date") ? null : obj
                        .getString("job_date");
                model.setJob_date(jobdates);
                // model.setJob_date(obj.getString("job_date"));
                model.setTitle(obj.getString("title"));
                String jobdesc = obj.isNull("description") ? null : obj
                        .getString("description");
                model.setJob_description(jobdesc);
                // model.setJob_description(obj.getString("job_description"));

                String jobtypes = obj.isNull("job_type") ? null : obj
                        .getString("job_type");
                model.setJob_category(jobtypes);
                //model.setJob_category(obj.getString("job_type"));
                String jobcomp = obj.isNull("company_name") ? null : obj
                        .getString("company_name");
                model.setCompany(jobcomp);
                // model.setCompany(obj.getString("company_name"));
                String jobcompabout = obj.isNull("about_us") ? null : obj
                        .getString("about_us");
                model.setAbout_company(jobcompabout);
                // model.setAbout_company(obj.getString("about_company"));
                String jobcompadd = obj.isNull("address") ? null : obj
                        .getString("address");
                model.setAddress(jobcompadd);
                // model.setAddress(obj.getString("address"));
                String jobcompphone = obj.isNull("phone") ? null : obj
                        .getString("phone");
                model.setPhone(jobcompphone);
                //model.setPhone(obj.getString("phone"));
                String jobcompweb = obj.isNull("website") ? null : obj
                        .getString("website");
                model.setWebsite(jobcompweb);
                // model.setWebsite(obj.getString("website"));
                String jobcompexp = obj.isNull("experience") ? null : obj
                        .getString("experience");
                model.setExperience(jobcompexp);
                //model.setExperience(obj.getString("experience"));
                String jobcompedu = obj.isNull("education") ? null : obj
                        .getString("education");
                model.setEducation(jobcompedu);
                // model.setEducation(obj.getString("education"));
                String jobsalary = obj.isNull("salary") ? null : obj
                        .getString("salary");
                model.setSalary(jobsalary);
                // model.setSalary(obj.getString("salary"));
                String jobkeyskill = obj.isNull("key_skills") ? null : obj
                        .getString("key_skills");
                model.setKey_skills(jobkeyskill);
                // model.setKey_skills(obj.getString("key_skills"));
                String imageurl = obj.isNull("image_url") ? null : obj
                        .getString("image_url");
                model.setImageurl(imageurl);
                String adurl = obj.isNull("url") ? null : obj
                        .getString("url");
                model.setAdurl(adurl);
                thump.setImageUrl(image, mImageLoader);
                //  Picasso.with(getApplicationContext()).load(obj.getString("image")).into(thump);
                title.setText(Html.fromHtml(obj.getString("title")));
                jobtype.setText(jobtypes);
                salaryamt.setText(jobsalary);
                if(jobcompedu!=null){
                    Candidateprofiledetails.setText("Education: "+jobcompedu);
                }else {
                    Candidateprofiledetails.setVisibility(View.GONE);
                }
                if(jobcompexp!=null){
                    Candidateprofileeducation.setText("Experience: "+jobcompexp);
                }else {
                    Candidateprofileeducation.setVisibility(View.GONE);
                }
                if (jobkeyskill!=null){
                    Candidateprofileskills.setText("Skill set: "+jobkeyskill);
                }
                interviewdate_data.setText(jobdates);
                interviewtiming.setText(timin);
                String ss=jobdesc ;
                String s=ss;
                // s = s.replace("\"", "'");
//        s = s.replace("\\", "");
                String fonts="<html>\n" +
                        "\t<head>\n" +
                        "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +
                        "\t\t<style>\n" +
                        "\t\t@font-face {\n" +
                        "  font-family: 'segeoui-light';\n" +
                        " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "  font-family: 'segeoui-regular';\n" +
                        "src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "  font-family: 'segeoui-sbold';\n" +
                        " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Bold';\n" +
                        "   src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Light';\n" +
                        "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Regular';\n" +
                        "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Thin';\n" +
                        "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "\t\t</style>\n" +
                        "\t</head>";
                String rep = String.valueOf(s);
                rep =  rep.replaceAll("color:#fff","color:#000");
                if(colorcodes.equals("#FFFFFFFF")) {
                    jobdescrion_web.loadDataWithBaseURL("", fonts + rep + "</head>", "text/html", "utf-8", "");
                }else{
                    jobdescrion_web.loadDataWithBaseURL("", fonts + s + "</head>", "text/html", "utf-8", "");
                }
                jobdescrion_web.setWebViewClient(new MyBrowser());
                jobdescrion_web.setBackgroundColor(Color.TRANSPARENT);
                String adds=jobcompadd;
                String sad=adds;
                // s = s.replace("\"", "'");
                //sad = sad.replace("\\", "");

                String reps = sad;
                reps =  rep.replaceAll("color:#fff","color:#000");
                if(colorcodes.equals("#FFFFFFFF")) {
                    job_address_web.loadDataWithBaseURL("", fonts + reps + "</head>", "text/html", "utf-8", "");
                }else{
                    job_address_web.loadDataWithBaseURL("", fonts + sad + "</head>", "text/html", "utf-8", "");
                }

                job_address_web.setWebViewClient(new MyBrowser());
                job_address_web.setBackgroundColor(Color.TRANSPARENT);

                String ssinfo=jobcompabout;
                String sinfo=ssinfo;
                // s = s.replace("\"", "'");
//        s = s.replace("\\", "");
                String repss = sinfo;
                repss =  rep.replaceAll("color:#fff","color:#000");
                if(colorcodes.equals("#FFFFFFFF")) {
                    infodescrtion.loadDataWithBaseURL("", fonts + repss + "</head>", "text/html", "utf-8", "");
                }else{
                    infodescrtion.loadDataWithBaseURL("", fonts + sinfo + "</head>", "text/html", "utf-8", "");
                }
                infodescrtion.setBackgroundColor(Color.TRANSPARENT);
                String addsinfo=jobcompadd;
                String sadinfo=adds;
                // s = s.replace("\"", "'");
                //sad = sad.replace("\\", "");
              /*  infoaddress.loadData(sadinfo,"text/html; charset=UTF-8",null);
                infoaddress.setBackgroundColor(Color.TRANSPARENT);*/
                if (jobcompphone == null || jobcompphone == "") {

                }else {

                    phonenumber_textview.setText("Phone:"+jobcompphone);
                }



                //modelList.add(model);
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
                Intent ads=new Intent(getApplicationContext(),AdvertisementPage.class);                 ads.putExtra("ID",url);                 startActivity(ads);
                // view.loadUrl(url);
            }
            //

            return true;
        }
    }
    class ItemModel{
        private int typeid;
        private String timing;
        private String image;
        private String bimage;
        private String job_date;
        private String job_description;
        private String title;
        private String location;
        private String company;
        private String job_category;
        private String about_company;
        private String address;
        private String phone;
        private String website;
        private String experience;
        private String education;
        private String salary;
        private String key_skills;
        private String tformat,count;
        private String adurl,imageurl;
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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
        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getTformat(){return tformat;}
        public void setTformat(String tformat){this.tformat=tformat;}

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }
        public String getTiming() {
            return timing;
        }
        public void setTiming(String timing) {
            this.timing = timing;
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
        public String getjob_description(){return job_description;}
        public  void setJob_description(String job_description){
            this.job_description=job_description;
        }
        public String getJob_date(){return  job_date;}

        public void setJob_date(String job_date) {
            this.job_date = job_date;
        }
        public String getTitle(){return  title;}

        public void setTitle(String title) {
            this.title = title;
        }
        public String getLocation(){return  location;}

        public void setLocation(String location) {
            this.location = location;
        }
        public String getCompany(){return company;}
        public void setCompany(String company){
            this.company=company;
        }
        /******** start the Food category names****/
        public String getJob_category(){return  job_category;}

        public void setJob_category(String job_category) {
            this.job_category = job_category;
        }
        public String getAbout_company(){return about_company;}
        public void setAbout_company(String about_company){
            this.about_company=about_company;
        }
        public String getAddress(){return address;}
        public void setAddress(String address){this.address=address;}
        public String getPhone(){return phone;}
        public void setPhone(String phone){this.phone=phone;}
        public String getWebsite(){return website;}
        public void setWebsite(String website){this.website=website;}
        public String getExperience(){return experience;}
        public void setExperience(String experience){this.experience=experience;}
        public String getEducation(){return education;}
        public  void  setEducation(String education){this.education=education;}
        public String getSalary(){return salary;}
        public void setSalary(String salary){this.salary=salary;}
        public String getKey_skills(){return key_skills;}
        public void setKey_skills(String key_skills){this.key_skills=key_skills;}
    }


}

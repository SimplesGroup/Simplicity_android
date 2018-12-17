package simplicity_an.simplicity_an.Tamil.Activity;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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
import com.android.volley.toolbox.JsonArrayRequest;
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
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;


/**
 * Created by kuppusamy on 1/21/2016.
 */
public class JobsActivitytamil extends AppCompatActivity {
  LinearLayoutManager lLayout;
     List<ItemModel> modelList=new ArrayList<ItemModel>();
     String URL="http://simpli-city.in/request2.php?key=simples&rtype=job&page=";
    ListView listview;
   ProgressDialog pdialog;
    View view;
    RecyclerViewAdapter rcAdapter;
    String bimage;
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO;
    ImageButton comment,share,menu,back,searchbutton;


    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
  CollapsingToolbarLayout mCollapsingToolbarLayout;
    TextView nameslabel_coimbatore;
    JSONObject response;
    SearchView jobs_search;
    String search_value;
    String URLSEARCH;
    final String TAG_REQUEST = "MY_TAG";
    RequestQueue queue;
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid;
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid;
    int value;
    String notification_counts;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    private String URLTW="http://simpli-city.in/request2.php?rtype=healthcategory&key=simples";
    ImageButton search,notification;

    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.jobsactivitys);
        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        requestQueue = Volley.newRequestQueue(this);

        notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }

        colorcodes=sharedpreferences.getString(backgroundcolor,"");

        mainlayout=(RelativeLayout)findViewById(R.id.version_main_layout);
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
                    int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);
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
        getData();
        nameslabel_coimbatore=(TextView)findViewById(R.id.namelabel_jobscoimbatore);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        nameslabel_coimbatore.setTypeface(tf);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
nameslabel_coimbatore.setTypeface(tf);
        nameslabel_coimbatore.setText("Jobs");
        // mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mToolbarone = (Toolbar) findViewById(R.id.toolbarone);
        menu=(ImageButton)findViewById(R.id.btn_jobbhome);
        back=(ImageButton)findViewById(R.id.btn_jobback);
        searchbutton=(ImageButton)findViewById(R.id.btn_jobsearch);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_intent = new Intent(getApplicationContext(), JobsSearchviewtamil.class);
                startActivity(search_intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent back_intent = new Intent(getApplicationContext(), MainPageTamil.class);
                startActivity(back_intent);
                finish();*/
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu_Intent = new Intent(getApplicationContext(), MainPageTamil.class);
                startActivity(menu_Intent);
                finish();
            }
        });
        notification=(ImageButton)findViewById(R.id.btn_jobprofile) ;
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(JobsActivitytamil.this,MainPageTamil.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(JobsActivitytamil.this,MainPageTamil.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });
        lLayout = new LinearLayoutManager(this);
       jobs_search=(SearchView)findViewById(R.id.searchjobs_list);
        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) jobs_search.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        magImage.setVisibility(View.GONE);
        int searchPlateId = jobs_search.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = jobs_search.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
            //searchPlate.setBottom(Color.WHITE);

            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.WHITE);

                searchText.setHintTextColor(getResources().getColor(R.color.white));
                searchText.setPadding(70, 0, 0, 0);
                //searchText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
        jobs_search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


            }
        });

        //*** setOnQueryTextListener ***
        jobs_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub


                search_value = query;

                Intent simplicity=new Intent(getApplicationContext(),JobsSearchviewtamil.class);
                simplicity.putExtra("QUERY", search_value);
                startActivity(simplicity);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                //	Toast.makeText(getBaseContext(), newText,
                ///Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        RecyclerView rView = (RecyclerView)findViewById(R.id.pick_item);
        rView.setLayoutManager(new LinearLayoutManager(this));

        /*rView.setOnScrollListener(new MyscrollListenertwo(this) {
            @Override
            public void onMoved(int distance) {
                mToolbarone.setTranslationY(-distance);
            }
        });*/
        rView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        pdialog = new ProgressDialog(this);
        //pdialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //pdialog.setMessage("Loading data..");

        //pdialog.setContentView(R.layout.progressdialog);
        pdialog.show();

        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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



        rView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
              String s= String.valueOf(position);

                String title = ((ItemModel)modelList.get(position)).getTitle();
                String experience = ((ItemModel)modelList.get(position)).getExperience();

                String company = ((ItemModel)modelList.get(position)).getCompany();
                String job_description = ((ItemModel)modelList.get(position)).getjob_description();
                String job_type = ((ItemModel)modelList.get(position)).getJob_category();
                String salary = ((ItemModel)modelList.get(position)).getSalary();
                String profile = ((ItemModel)modelList.get(position)).getEducation();
                String interviewdate = ((ItemModel)modelList.get(position)).getJob_date();
                String interviewtiming = ((ItemModel)modelList.get(position)).getTiming();
                String keyskills = ((ItemModel)modelList.get(position)).getKey_skills();
                String address = ((ItemModel)modelList.get(position)).getAddress();
              String aboutcomp = ((ItemModel)modelList.get(position)).getAbout_company();

                int  post = ((ItemModel)modelList.get(position)).getTypeid();
                String bid = ((ItemModel)modelList.get(position)).getAdurl();
                String id = ((ItemModel)modelList.get(position)).getId();
                if(post==4||post==3) {
                    Intent k = new Intent(Intent.ACTION_VIEW);
                    k.setData(Uri.parse(bid));
                    startActivity(k);

                }else {
                    try {
                        Intent ii=new Intent(getApplicationContext(),JobsDetailPagetamil.class);
                        ii.putExtra("JOBDESCRITION", job_description);
                        //   ii.putExtra("IMAGE", bitmap);
                        ii.putExtra("TITLE", title);
                        ii.putExtra("DATE", interviewdate);
                        ii.putExtra("JOBTYPE", job_type);
                        ii.putExtra("SALARY", salary);
                        ii.putExtra("COMPANY", company);
                        ii.putExtra("PROFILE", profile);
                        ii.putExtra("TIMING", interviewtiming);
                        ii.putExtra("KEYSKILLS", keyskills);
                        ii.putExtra("ADDRESS", address);
                        ii.putExtra("EXPERIENCE", experience);
                        ii.putExtra("ABOUT_COMPANY",aboutcomp);
                        startActivity(ii);
                    }catch (Exception e){

                    }
                }



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
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
                    parseJsonFeedtwo(new JSONObject(data));
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
                        parseJsonFeedtwo(response);
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
            requestQueue.add(jsonReq);
        }
        return jsonReq;
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
    private void Search(){
        URLSEARCH="http://simpli-cityimage.in/request2.php?rtype=search&key=simples&qtype=job&q="+search_value;
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLSEARCH, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                if (response != null) {
                    dissmissDialog();
                    parseJsonFeedtwo(response);
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
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject obj = (JSONObject) feedArray.get(i);


                ItemModel model=new ItemModel();
                //FeedItem model=new FeedItem();
               /* String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);
                bimage=obj.isNull("bimage")?null:obj.getString("bimage");
                model.setBimage(bimage);*/
                model.setTiming(obj.getString("timing"));
                model.setTformat(obj.getString("tformat"));
                model.setLocation(obj.getString("location"));
                model.setTypeid(obj.getInt("type"));
                model.setJob_date(obj.getString("job_date"));
                model.setTitle(obj.getString("title"));
                model.setJob_description(obj.getString("job_description"));
                model.setJob_category(obj.getString("job_type"));
                model.setCompany(obj.getString("company_name"));
                model.setAbout_company(obj.getString("about_company"));
                model.setAddress(obj.getString("address"));
                model.setPhone(obj.getString("phone"));
                model.setWebsite(obj.getString("website"));
                model.setExperience(obj.getString("experience"));
                model.setEducation(obj.getString("education"));
                model.setSalary(obj.getString("salary"));
                model.setKey_skills(obj.getString("key_skills"));
                modelList.add(model);
            }

            // notify data changes to list adapater
            rcAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseJsonFeedtwo(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject obj = (JSONObject) feedArray.get(i);


                ItemModel model=new ItemModel();
                //FeedItem model=new FeedItem();
               String image = obj.isNull("timing") ? null : obj
                        .getString("timing");
                model.setTiming(image);
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
               int types = obj.isNull("type") ? null : obj
                        .getInt("type");
                model.setTypeid(types);
               // model.setTypeid(obj.getInt("type"));
                String jobdates = obj.isNull("job_date") ? null : obj
                        .getString("job_date");
                model.setJob_date(jobdates);
               // model.setJob_date(obj.getString("job_date"));
                model.setTitle(obj.getString("title"));
                String jobdesc = obj.isNull("job_description") ? null : obj
                        .getString("job_description");
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
                String jobcompabout = obj.isNull("about_company") ? null : obj
                        .getString("about_company");
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
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;
        private boolean loading;
        private OnLoadMoreListener onLoadMoreListener;
        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_LOADING = 2;
        private final int VIEW_TYPE_ITEM_AD = 3;
        private final int VIEW_TYPE_ITEM_AD_SMALL = 4;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;

        ImageLoader mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
        private List<ItemModel> modelList = new ArrayList<ItemModel>();
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
            switch (viewType){
                case VIEW_TYPE_ITEM:
                    ViewGroup item=(ViewGroup)mInflater.inflate(R.layout.feed_itemtwo,parent,false);
                    RecyclerViewHolders hold=new RecyclerViewHolders(item);
                    return hold;
                case VIEW_TYPE_LOADING:
                    ViewGroup load=(ViewGroup)mInflater.inflate(R.layout.layout_loading_item,parent,false);
                    LoadingViewHolder loads=new LoadingViewHolder(load);
                    return  loads;
                case VIEW_TYPE_ITEM_AD:
                    ViewGroup bigad=(ViewGroup)mInflater.inflate(R.layout.feed_item_ar,parent,false);
                    UserViewHolderimage big=new UserViewHolderimage(bigad);
                    return big;
                case VIEW_TYPE_ITEM_AD_SMALL:
                    ViewGroup smallad=(ViewGroup)mInflater.inflate(R.layout.feed_item_small_ad,parent,false);
                    UserViewHoldersmallad small=new UserViewHoldersmallad(smallad);
                    return small;
            }

            return null;
        }


        public void onBindViewHolder(RecyclerView.ViewHolder holders, int position) {

            if(holders instanceof UserViewHolderimage){
                final UserViewHolderimage userViewHolderimagess = (UserViewHolderimage) holders;
                ItemModel itemmodel=modelList.get(position);
                if(itemmodel.getTypeid()==4){
                    if (mImageLoader == null)
                        mImageLoader = CustomVolleyRequest.getInstance(JobsActivitytamil.this).getImageLoader();
                    // mImageLoader.get(itemmodel.getImage(), ImageLoader.getImageListener(userViewHolder.feedimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    userViewHolderimagess.feed_advertisement.setImageUrl(itemmodel.getImageurl(), mImageLoader);

                }
            }
            else  if(holders instanceof UserViewHoldersmallad){
                final UserViewHoldersmallad user_smallad = (UserViewHoldersmallad) holders;
                ItemModel itemmodel=modelList.get(position);
                String ids="http://simpli-city.in/smp_interface/images/ads/480665630VDsmall.jpg";
                if(itemmodel.getTypeid()==3){
                    if (mImageLoader == null)
                        mImageLoader = CustomVolleyRequest.getInstance(JobsActivitytamil.this).getImageLoader();
                    // mImageLoader.get(itemmodel.getImage(), ImageLoader.getImageListener(userViewHolder.feedimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    user_smallad.feed_advertisement_small.setImageUrl(itemmodel.getImageurl(), mImageLoader);

                }
            }else     if (holders instanceof RecyclerViewHolders) {

                final RecyclerViewHolders holder = (RecyclerViewHolders) holders;
                ItemModel itemmodel = modelList.get(position);
                // holder.articlenew.setText(itemmodel.getTitle());
                String dat = itemmodel.getJob_date();
                holder.jobdescription.setText(itemmodel.getjob_description());
                holder.aboutcompany.setText(itemmodel.getAbout_company());
                holder.address.setText(itemmodel.getAddress());
                holder.jobtype.setText(itemmodel.getJob_category());
                holder.education.setText(itemmodel.getEducation());
                holder.timing.setText(itemmodel.getTiming());
                holder.salary.setText(itemmodel.getSalary());
                holder.keyskills.setText(itemmodel.getKey_skills());
                holder.datesecondpage.setText(itemmodel.getJob_date());
                if (itemmodel.getTypeid() == 0) {
                    holder.location.setText("LocationSelection" + "-" + itemmodel.getLocation());
                    holder.postname.setText(itemmodel.getTitle());
                    holder.postcompany.setText(itemmodel.getCompany());
                    holder.myJobdateone.setText(itemmodel.getTformat());
                    holder.myJobdateone.setVisibility(View.VISIBLE);
                    if(itemmodel.getExperience()!=null){
                        holder.experience.setText("Experience" + "-" + itemmodel.getExperience());
                        holder.experience.setVisibility(View.VISIBLE);
                    }else {
                        holder.experience.setVisibility(View.GONE);
                    }

                    // holder.imageview.setImageUrl(itemmodel.getImage(), imageLoader);
                    holder.im1.setVisibility(View.VISIBLE);
                    holder.im2.setVisibility(View.VISIBLE);
                    holder.postname.setVisibility(View.VISIBLE);
                    holder.postcompany.setVisibility(View.VISIBLE);
                    holder.location.setVisibility(View.VISIBLE);
                    holder.feedImageView.setVisibility(View.GONE);
                    holder.im.setVisibility(View.GONE);
                    holder.imone.setVisibility(View.GONE);
                    holder.postnametwo.setVisibility(View.GONE);
                    holder.postcompanytwo.setVisibility(View.GONE);
                    holder.jobdate.setVisibility(View.GONE);

                } else {
                    // Feed image
                    if (itemmodel.getTypeid() == 1) {

                        holder.im.setVisibility(View.VISIBLE);
                        holder.experience.setVisibility(View.GONE);
                        holder.location.setVisibility(View.GONE);
                        holder.myJobdateone.setVisibility(View.GONE);
                        holder.postcompany.setVisibility(View.GONE);
                        holder.im1.setVisibility(View.GONE);
                        holder.im2.setVisibility(View.GONE);
                        holder.postname.setVisibility(View.GONE);
                        //holder.imageview.setVisibility(View.GONE);
                        holder.feedImageView.setImageUrl(itemmodel.getImage(), mImageLoader);

                        holder.imone.setVisibility(View.VISIBLE);
                        holder.postcompanytwo.setText(itemmodel.getCompany());

                        holder.jobdate.setText(itemmodel.getTformat());
                        holder.jobdate.setVisibility(View.VISIBLE);
                        holder.postnametwo.setText(itemmodel.getTitle());
                        holder.postnametwo.setVisibility(View.VISIBLE);
                        holder.feedImageView.setVisibility(View.VISIBLE);
                        holder.feedImageView
                                .setResponseObserver(new FeedImageView.ResponseObserver() {
                                    @Override
                                    public void onError() {
                                    }

                                    @Override
                                    public void onSuccess() {
                                    }
                                });
                    } else {
                        holder.feedImageView.setVisibility(View.GONE);
                        holder.postnametwo.setVisibility(View.GONE);
                        holder.postcompanytwo.setVisibility(View.GONE);
                        holder.im.setVisibility(View.GONE);
                        holder.imone.setVisibility(View.GONE);
                        holder.jobdate.setVisibility(View.GONE);

                    }
                }

            }else if (holders instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holders;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }




        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }


        public int getItemViewType(int position) {
            ItemModel item = modelList.get(position);
           if(item.getTypeid()==4){
                return VIEW_TYPE_ITEM_AD;
                //  return VIEW_TYPE_ITEM;
            }else if(item.getTypeid()==3){
                return VIEW_TYPE_ITEM_AD_SMALL;
            } else {
                return modelList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
            }

          // return modelList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }

        public class RecyclerViewHolders extends RecyclerView.ViewHolder{

            public TextView postname;
            public TextView postcompany;
            public TextView jobdate,myJobdateone;
            public TextView postnametwo;
            public TextView postcompanytwo;

            public TextView location;
            public TextView experience;
            public TextView description;
            public TextView publish;
            public TextView company;
            public TextView jobdescription;
            public TextView aboutcompany;
            public TextView address;
            public TextView jobtype;
            public TextView education;
            public TextView timing;
            public TextView salary;
            public TextView keyskills;
            public TextView datesecondpage;
            public ImageView im,imone;
            public ImageView im1,im2;
           // public  NetworkImageView imageview;
            public FeedImageView feedImageView;
            public RecyclerViewHolders(View itemView) {
                super(itemView);
                im = (ImageView) itemView.findViewById(R.id.imageView);
                imone = (ImageView) itemView.findViewById(R.id.imageViewone);
                im1 = (ImageView)itemView.findViewById(R.id.imageViewlitle);
                im2 = (ImageView)itemView.findViewById(R.id.imageViewlitleone);
               // imageview=(NetworkImageView)itemView.findViewById(R.id.thumbnailfoodcategory);
                feedImageView = (FeedImageView) itemView
                        .findViewById(R.id.feedImage1);

                postnametwo=(TextView)itemView.findViewById(R.id.food_name);
                postname = (TextView) itemView.findViewById(R.id.name);
                postcompany = (TextView)itemView
                        .findViewById(R.id.timestamp);
                postcompanytwo=(TextView)itemView.findViewById(R.id.releaseYear);
                jobdate=(TextView)itemView.findViewById(R.id.jobdate);
                myJobdateone=(TextView)itemView.findViewById(R.id.jobdateweeks);
                // date.setPadding(7, 7, 7, 7);

                description=(TextView)itemView.findViewById(R.id.description);
                publish=(TextView)itemView.findViewById(R.id.publisherdesg);
                company=(TextView)itemView.findViewById(R.id.company);
                location=(TextView)itemView.findViewById(R.id.location);
                experience=(TextView)itemView.findViewById(R.id.experience);
                jobdescription=(TextView)itemView.findViewById(R.id.job_description);
                aboutcompany=(TextView)itemView.findViewById(R.id.about_company);
               address=(TextView)itemView.findViewById(R.id.address);

               jobtype= (TextView)itemView.findViewById(R.id.job_type);
               education = (TextView)itemView.findViewById(R.id.education);
                timing = (TextView)itemView.findViewById(R.id.timing);
                salary = (TextView)itemView.findViewById(R.id.salary);
                keyskills = (TextView)itemView.findViewById(R.id.keyskills);
                datesecondpage = (TextView)itemView.findViewById(R.id.datesecondpage);

            }
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
    /*public void onPause() {
        super.onPause();
        parseJsonFeed(response);
    }

  protected void onResume() {
        super.onResume();
       parseJsonFeed(response);
    }*/
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
}

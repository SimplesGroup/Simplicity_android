package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
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

/**
 * Created by kuppusamy on 5/30/2016.
 */
public class JobsSearchview extends AppCompatActivity {
    LinearLayoutManager lLayout;
     List<ItemModel> modelList=new ArrayList<ItemModel>();
    String URL="http://simpli-city.in/request2.php?rtype=search&key=simples&qtype=job&q=";

     ProgressDialog pdialog;
    View view;
    RecyclerViewAdapter rcAdapter;



    ImageButton comment,share,menu,back;

   ArrayAdapter<String> listAdapter ;
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
     CollapsingToolbarLayout mCollapsingToolbarLayout;
    TextView nameslabel_coimbatore;
    JSONObject response;
    SearchView jobs_search;
    String search_value;
    String URLSEARCH,URLTW;
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

    ImageButton search,notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.jobsactivitys);
        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
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
        Intent get=getIntent();
        String qury=get.getStringExtra("QUERY");
        if(qury!=null){
            search_value=qury;
           URLTW=URL+search_value;
        }

        nameslabel_coimbatore=(TextView)findViewById(R.id.namelabel_jobscoimbatore);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        nameslabel_coimbatore.setTypeface(tf);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        nameslabel_coimbatore.setTypeface(tf);
        // mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mToolbarone = (Toolbar) findViewById(R.id.toolbarone);
        menu=(ImageButton)findViewById(R.id.btn_jobbhome);
        back=(ImageButton)findViewById(R.id.btn_jobback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent backjobsearch = new Intent(getApplicationContext(), JobsActivity.class);
                startActivity(backjobsearch);
                finish();*/
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujobsearch = new Intent(getApplicationContext(), MainActivityVersiontwo.class);
                startActivity(menujobsearch);
                finish();
            }
        });
        notification=(ImageButton)findViewById(R.id.btn_jobprofile) ;
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(JobsSearchview.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(JobsSearchview.this, MainPageEnglish.class);
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
            searchPlate.setBottom(Color.WHITE);

            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
                searchText.setPadding(70,0,0,0);
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

                Toast.makeText(getBaseContext(), query,
                        Toast.LENGTH_SHORT).show();
                search_value = query;

                modelList.clear();
                Search();
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
        rcAdapter = new RecyclerViewAdapter(this,modelList);
        rView.setAdapter(rcAdapter);

      JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTW, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
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


        rView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String s= String.valueOf(position);

                String title = ((TextView) view.findViewById(R.id.food_name))
                        .getText().toString();
                String experience = ((ItemModel)modelList.get(position)).getExperience();

                String company = ((TextView) view.findViewById(R.id.releaseYear))
                        .getText().toString();
                String job_description = ((TextView) view.findViewById(R.id.job_description))
                        .getText().toString();
                String job_type = ((TextView) view.findViewById(R.id.job_type))
                        .getText().toString();
                String salary = ((TextView) view.findViewById(R.id.salary))
                        .getText().toString();
                String profile = ((TextView) view.findViewById(R.id.education))
                        .getText().toString();
                String interviewdate = ((TextView) view.findViewById(R.id.datesecondpage))
                        .getText().toString();
                String interviewtiming = ((TextView) view.findViewById(R.id.timing))
                        .getText().toString();
                String keyskills = ((TextView) view.findViewById(R.id.keyskills))
                        .getText().toString();
                String address = ((TextView) view.findViewById(R.id.address))
                        .getText().toString();
                String aboutcomp = ((TextView) view.findViewById(R.id.about_company))
                        .getText().toString();


                try {
                    Intent ii=new Intent(getApplicationContext(),JobsDetailPage.class);
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

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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

    @Override
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
    private void Search(){
        URLSEARCH="http://simpli-city.in/request2.php?rtype=search&key=simples&qtype=job&q="+search_value;
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
                model.setImage(image);
                //bimage=obj.isNull("bimage")?null:obj.getString("bimage");
                //model.setBimage(bimage);
                //  model.setTiming(obj.getString("timing"));
                String tformat = obj.isNull("tformat") ? null : obj
                        .getString("tformat");
                model.setImage(tformat);
                // model.setTformat(obj.getString("tformat"));
                String location = obj.isNull("location") ? null : obj
                        .getString("location");
                model.setImage(location);
                // model.setLocation(obj.getString("location"));
                String types = obj.isNull("type") ? null : obj
                        .getString("type");
                model.setImage(types);
                // model.setTypeid(obj.getInt("type"));
                String jobdates = obj.isNull("job_date") ? null : obj
                        .getString("job_date");
                model.setImage(jobdates);
                // model.setJob_date(obj.getString("job_date"));
                model.setTitle(obj.getString("title"));
                String jobdesc = obj.isNull("job_description") ? null : obj
                        .getString("job_description");
                model.setImage(jobdesc);
                // model.setJob_description(obj.getString("job_description"));

                String jobtypes = obj.isNull("job_type") ? null : obj
                        .getString("job_type");
                model.setImage(jobtypes);
                //model.setJob_category(obj.getString("job_type"));
                String jobcomp = obj.isNull("company_name") ? null : obj
                        .getString("company_name");
                model.setImage(jobcomp);
                // model.setCompany(obj.getString("company_name"));
                String jobcompabout = obj.isNull("about_company") ? null : obj
                        .getString("about_company");
                model.setImage(jobcompabout);
                // model.setAbout_company(obj.getString("about_company"));
                String jobcompadd = obj.isNull("address") ? null : obj
                        .getString("address");
                model.setImage(jobcompadd);
                // model.setAddress(obj.getString("address"));
                String jobcompphone = obj.isNull("phone") ? null : obj
                        .getString("phone");
                model.setImage(jobcompphone);
                //model.setPhone(obj.getString("phone"));
                String jobcompweb = obj.isNull("website") ? null : obj
                        .getString("website");
                model.setImage(jobcompweb);
                // model.setWebsite(obj.getString("website"));
                String jobcompexp = obj.isNull("experience") ? null : obj
                        .getString("experience");
                model.setImage(jobcompexp);
                //model.setExperience(obj.getString("experience"));
                String jobcompedu = obj.isNull("education") ? null : obj
                        .getString("education");
                model.setImage(jobcompedu);
                // model.setEducation(obj.getString("education"));
                String jobsalary = obj.isNull("salary") ? null : obj
                        .getString("salary");
                model.setImage(jobsalary);
                // model.setSalary(obj.getString("salary"));
                String jobkeyskill = obj.isNull("key_skills") ? null : obj
                        .getString("key_skills");
                model.setImage(jobkeyskill);
                // model.setKey_skills(obj.getString("key_skills"));
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
        private String tformat;
         String count;

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


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader =  MySingleton.getInstance(getApplicationContext()).getImageLoader();
        private List<ItemModel> modelList=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapter(Context context, List<ItemModel> modelList) {
            this.modelList = modelList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_itemtwo, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {

            ItemModel itemmodel=modelList.get(position);
            // holder.articlenew.setText(itemmodel.getTitle());
            String dat=itemmodel.getJob_date();
            holder. jobdescription.setText(itemmodel.getjob_description());
            holder.aboutcompany.setText(itemmodel.getAbout_company());
            holder.address.setText(itemmodel.getAddress());
            holder.jobtype.setText(itemmodel.getJob_category());
            holder.education.setText(itemmodel.getEducation());
            holder.timing.setText(itemmodel.getTiming());
            holder.salary.setText(itemmodel.getSalary());
            holder.keyskills.setText(itemmodel.getKey_skills());
            holder.datesecondpage.setText(itemmodel.getJob_date());
            if (itemmodel.getTypeid() == 0) {
                holder.location.setText("LocationSelection"+"-"+itemmodel.getLocation());
                holder.postname.setText(itemmodel.getTitle());
                holder.postcompany.setText(itemmodel.getCompany());
                holder.myJobdateone.setText(itemmodel.getTformat());
                holder.myJobdateone.setVisibility(View.VISIBLE);
                holder.experience.setText("Experience" + "-" + itemmodel.getExperience());
                holder.experience.setVisibility(View.VISIBLE);
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

            }
            else  {
                // Feed image
                if (itemmodel.getTypeid() == 1) {

                    holder.im.setVisibility(View.VISIBLE);
                    holder.experience.setVisibility(View.GONE);
                    holder.location.setVisibility(View.GONE);
                    holder.myJobdateone.setVisibility(View.GONE);
                    holder.postcompany.setVisibility(View.GONE);
                    holder. im1.setVisibility(View.GONE);
                    holder.im2.setVisibility(View.GONE);
                    holder. postname.setVisibility(View.GONE);
                    //holder.imageview.setVisibility(View.GONE);
                    holder.feedImageView.setImageUrl(itemmodel.getImage(), mImageLoader);

                    holder.imone.setVisibility(View.VISIBLE);
                    holder.postcompanytwo.setText(itemmodel.getCompany());

                    holder.jobdate.setText(itemmodel.getTformat());
                    holder.jobdate.setVisibility(View.VISIBLE);
                    holder.postnametwo.setText(itemmodel.getTitle());
                    holder. postnametwo.setVisibility(View.VISIBLE);
                    holder. feedImageView.setVisibility(View.VISIBLE);
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
                    holder. feedImageView.setVisibility(View.GONE);
                    holder.postnametwo.setVisibility(View.GONE);
                    holder.postcompanytwo.setVisibility(View.GONE);
                    holder.im.setVisibility(View.GONE);
                    holder.imone.setVisibility(View.GONE);
                    holder.jobdate.setVisibility(View.GONE);

                }
            }

        }

        @Override
        public int getItemCount() {
            return this.modelList.size();
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

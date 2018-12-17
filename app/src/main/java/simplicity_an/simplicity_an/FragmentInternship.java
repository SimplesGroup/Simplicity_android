package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
 * Created by kuppusamy on 8/2/2016.
 */
public class FragmentInternship extends Fragment {
            List<ItemModel> modelList=new ArrayList<ItemModel>();
            ProgressDialog pdialog;
            RecyclerViewAdapter rcAdapter;
            LinearLayoutManager mLayoutManager;
            RecyclerView recycler;
            JSONObject obj, objtwo;
            JSONArray feedArray;
            int ii,i;
            String URL="http://simpli-city.in/request2.php?rtype=internship&key=simples";
            ArrayAdapter<String> adapter;
            private final String TAG_REQUEST = "MY_TAG";
            String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
            String URLPOSTQTYPE,postid,myprofileid;
            private String KEY_QTYPE = "qtype";
            private String KEY_MYUID = "user_id";
            private String KEY_POSTID = "id";
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO;


            SharedPreferences sharedpreferences;
            public static final String mypreference = "mypref";
            public static final String MYUSERID= "myprofileid";
            public FragmentInternship() {

            }

            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.frag_news_city, container, false);
                sharedpreferences =  getActivity().getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                if (sharedpreferences.contains(MYUSERID)) {

                    myprofileid=sharedpreferences.getString(MYUSERID,"");
                    myprofileid = myprofileid.replaceAll("\\D+","");
                }
                URLPOSTQTYPE=urlpost;
                requestQueue = Volley.newRequestQueue(getActivity());

                recycler = (RecyclerView)view.findViewById(R.id.news_recyclerview);
                recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                recycler.setHasFixedSize(true);
                // recycler.smoothScrollToPosition(0);
                recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                pdialog = new ProgressDialog(getActivity());
                pdialog.show();
                pdialog.setContentView(R.layout.custom_progressdialog);
                pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pdialog.setCanceledOnTouchOutside(false);
                rcAdapter = new RecyclerViewAdapter(modelList,recycler);
                recycler.setAdapter(rcAdapter);
                getData();
                /*Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
                Cache.Entry entry = cache.get(URL);
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
                    JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                            URL,  new Response.Listener<JSONObject>() {

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
                    AppControllers.getInstance().addToRequestQueue(jsonReq);
                }*/
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
                                //Remove loading item
                                // modelList.remove(modelList.size() - 1);
                                // rcAdapter.notifyItemRemoved(modelList.size());

                                //Load data
                                /*int index = modelList.size();
                                int end = index + 9;

                                try {

                                    for (i = index; i < end; i++) {
                                        objtwo = (JSONObject) feedArray.get(i);
                                        ItemModel modelone = new ItemModel();

 *//* String image = obj.isNull("timing") ? null : obj
                                                .getString("timing");
                                        model.setImage(image);*//*

                                        //  model.setTiming(obj.getString("timing"));
                                        String tformat = objtwo.isNull("tformat") ? null : objtwo
                                                .getString("tformat");
                                        modelone.setTformat(tformat);
                                        // model.setTformat(obj.getString("tformat"));
                                        String location = objtwo.isNull("location") ? null : objtwo
                                                .getString("location");
                                        modelone.setLocation(location);
                                        // model.setLocation(obj.getString("location"));
                                        *//*String types = obj.isNull("type") ? null : obj
                                                .getString("type");
                                        model.setImage(types);*//*
                                        // model.setTypeid(obj.getInt("type"));
                                        String jobdates = objtwo.isNull("job_date") ? null : objtwo
                                                .getString("job_date");
                                        modelone.setJob_date(jobdates);
                                        // model.setJob_date(obj.getString("job_date"));
                                        modelone.setTitle(objtwo.getString("title"));
                                        String jobdesc = objtwo.isNull("job_description") ? null : objtwo
                                                .getString("job_description");
                                        modelone.setJob_description(jobdesc);
                                        // model.setJob_description(obj.getString("job_description"));

                                        String jobtypes = objtwo.isNull("job_type") ? null : objtwo
                                                .getString("job_type");
                                        modelone.setJob_category(jobtypes);
                                        //model.setJob_category(obj.getString("job_type"));
                                        String jobcomp = objtwo.isNull("company_name") ? null : objtwo
                                                .getString("company_name");
                                        modelone.setCompany(jobcomp);
                                        // model.setCompany(obj.getString("company_name"));
                                        String jobcompabout = objtwo.isNull("about_company") ? null : objtwo
                                                .getString("about_company");
                                        modelone.setAbout_company(jobcompabout);
                                        // model.setAbout_company(obj.getString("about_company"));
                                        String jobcompadd = objtwo.isNull("address") ? null : objtwo
                                                .getString("address");
                                        modelone.setAddress(jobcompadd);
                                        // model.setAddress(obj.getString("address"));
                                        String jobcompphone = objtwo.isNull("phone") ? null : objtwo
                                                .getString("phone");
                                        modelone.setPhone(jobcompphone);
                                        //model.setPhone(obj.getString("phone"));
                                        String jobcompweb = objtwo.isNull("website") ? null : objtwo
                                                .getString("website");
                                        modelone.setWebsite(jobcompweb);
                                        // model.setWebsite(obj.getString("website"));
                                        String jobcompexp = objtwo.isNull("experience") ? null : objtwo
                                                .getString("experience");
                                        modelone.setExperience(jobcompexp);
                                        //model.setExperience(obj.getString("experience"));
                                        String jobcompedu = objtwo.isNull("education") ? null : objtwo
                                                .getString("education");
                                        modelone.setEducation(jobcompedu);
                                        // model.setEducation(obj.getString("education"));
                                        String jobsalary = objtwo.isNull("salary") ? null : objtwo
                                                .getString("salary");
                                        modelone.setSalary(jobsalary);
                                        // model.setSalary(obj.getString("salary"));
                                        String jobkeyskill = objtwo.isNull("key_skills") ? null : objtwo
                                                .getString("key_skills");
                                        modelone.setKey_skills(jobkeyskill);

                                        modelone.setId(objtwo.getString("id"));
                                        modelList.add(modelone);
                                    }
                                    rcAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {

                                }*/

                                rcAdapter.setLoaded();
                            }
                        }, 2000);
                    }
                });

                recycler.addOnItemTouchListener(new NewsActivity.RecyclerTouchListener(getActivity(), recycler, new NewsActivity.ClickListener() {
                    public void onClick(View view, int position) {
                        int  post = ((ItemModel)modelList.get(position)).getTypeid();
                        String bids = ((ItemModel)modelList.get(position)).getAdurl();
                        String id = ((ItemModel)modelList.get(position)).getId();

                        String bid = ((ItemModel) modelList.get(position)).getId();
                        String title = ((ItemModel) modelList.get(position)).getTitle();
                        String company = ((ItemModel) modelList.get(position)).getCompany();
                        postid = ((ItemModel)modelList.get(position)).getId();
                        if(post==4||post==3) {
                            Intent k = new Intent(Intent.ACTION_VIEW);
                            k.setData(Uri.parse(bids));
                            startActivity(k);

                        }else {
                            if (myprofileid != null) {
                                Uloaddataserver();
                                Intent fooddescription = new Intent(getActivity(), EducationInternshipDescription.class);
                                fooddescription.putExtra("ID", bid);
                                fooddescription.putExtra("TITLE", title);
                                fooddescription.putExtra("COMPANY", company);
                                startActivity(fooddescription);
                            } else {
                                Intent fooddescription = new Intent(getActivity(), EducationInternshipDescription.class);
                                fooddescription.putExtra("ID", bid);
                                fooddescription.putExtra("TITLE", title);
                                fooddescription.putExtra("COMPANY", company);
                                startActivity(fooddescription);
                            }
                        }
                    }

                    public void onLongClick(View view, int position) {

                    }
                }));
                return view;
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



        URLTWO=URL+"&page="+requestCount;




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

                                params.put(KEY_QTYPE, "internship");
                                params.put(KEY_POSTID, postid);
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
            private void parseJsonFeed(JSONObject response) {
                try {
                    feedArray = response.getJSONArray("result");


                        for (ii = 0; ii < feedArray.length(); ii++) {
                            obj = (JSONObject) feedArray.get(ii);

                            ItemModel model = new ItemModel();
                            //FeedItem model=new FeedItem();
                            /* String image = obj.isNull("timing") ? null : obj
                                                .getString("timing");
                                        model.setImage(image);*/

                            //  model.setTiming(obj.getString("timing"));
                            String tformat = obj.isNull("tformat") ? null : obj
                                    .getString("tformat");
                            model.setTformat(tformat);
                            // model.setTformat(obj.getString("tformat"));
                            String location = obj.isNull("location") ? null : obj
                                    .getString("location");
                            model.setLocation(location);
                            // model.setLocation(obj.getString("location"));
                                        /*String types = obj.isNull("type") ? null : obj
                                                .getString("type");
                                        model.setImage(types);*/
                            model.setTypeid(obj.getInt("type"));
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
                            model.setId(obj.getString("id"));
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

            public static boolean isConnectivityAvailable(Context context) {
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
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
        private String id;
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
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
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


    @Override
            public void onDestroy() {
                super.onDestroy();
                dissmissDialog();
            }
            static class UserViewHolder extends RecyclerView.ViewHolder {
                public TextView name;
                public TextView postdate;

                public TextView timestamp;


                public UserViewHolder(View itemView) {
                    super(itemView);


                    name = (TextView) itemView.findViewById(R.id.name);
                    timestamp = (TextView) itemView
                            .findViewById(R.id.timestamp);
                    postdate=(TextView)itemView.findViewById(R.id.description);

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
                    ViewGroup vImageone = (ViewGroup) mInflater.inflate(R.layout.feeditem_education_internship, parent, false);
                    UserViewHolder vhImageon= new UserViewHolder(vImageone);
                    return vhImageon;
                case VIEW_TYPE_ITEM_AD:
                    ViewGroup vGroupone = (ViewGroup) mInflater.inflate(R.layout.feed_item_ar, parent, false);
                    UserViewHolderimage vhGroupone = new UserViewHolderimage(vGroupone);
                    return vhGroupone;
                case VIEW_TYPE_ITEM_AD_SMALL:
                    ViewGroup smallad = (ViewGroup) mInflater.inflate(R.layout.feed_item_small_ad, parent, false);
                    UserViewHoldersmallad small_ad = new UserViewHoldersmallad(smallad);
                    return small_ad;
                case VIEW_TYPE_LOADING:
                    ViewGroup vGroup0 = (ViewGroup) mInflater.inflate(R.layout.layout_loading_item, parent, false);
                    LoadingViewHolder vhGroup0 = new LoadingViewHolder(vGroup0);
                    return vhGroup0;
            }
            return null;

        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof UserViewHolder) {

                final UserViewHolder userViewHolder = (UserViewHolder) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);


                ItemModel itemmodel = modelList.get(position);



                userViewHolder.name.setText(itemmodel.getTitle());
                userViewHolder.name.setTypeface(seguiregular);
                userViewHolder.timestamp.setText("LocationSelection:"+itemmodel.getLocation());
                userViewHolder.postdate.setText(itemmodel.getTformat());
                userViewHolder.timestamp.setTypeface(seguiregular);
                userViewHolder.postdate.setTypeface(seguiregular);


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
            }else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
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

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }
    }        }


package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 3/2/2016.
 */
public class DirectoriesItemListActivity extends AppCompatActivity {
     List<ItemModel> modelList=new ArrayList<ItemModel>();
    String URL="http://simpli-city.in/request2.php?rtype=industry&key=simples&id=";

    ProgressDialog pdialog;
   CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerViewAdapter rcAdapter;
    String bimage;
    RecyclerView recycler;

    TextView Toolbartitle;
    ImageButton menu,back;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    String URLTWO,URLSEARCH;
    String URLtwo;
    SearchView search;
    String search_value;
    Typeface tf;

    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue queues;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.directories_item_lispage);
        Intent get=getIntent();
        URLtwo=get.getStringExtra("ID");

        URLTWO=URL+URLtwo;
        queues = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        menu=(ImageButton)findViewById(R.id.btn_3);
        back=(ImageButton)findViewById(R.id.btn_1);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menudirectories = new Intent(DirectoriesItemListActivity.this, MainPageEnglish.class);
                startActivity(menudirectories);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent backdirectories = new Intent(DirectoriesItemListActivity.this, Directories.class);
                startActivity(backdirectories);
                finish();*/
                onBackPressed();
            }
        });

        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        Toolbartitle=(TextView)findViewById(R.id.toolbar_title_list);
        Toolbartitle.setTypeface(tf);
        search=(SearchView)findViewById(R.id.searchdirectories_list);
        Toolbartitle.setText("Directories Services");
        recycler = (RecyclerView)findViewById(R.id.directories_list_recyclerview);
        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) search.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        magImage.setVisibility(View.GONE);
        int searchPlateId = search.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = search.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
           // searchPlate.setBottom(Color.WHITE);

            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
                searchText.setPadding(70,0,0,0);
                //searchText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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

        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recycler.setLayoutManager(new LinearLayoutManager(this));
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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

                        //Load data
                        int index = modelList.size();
                        int end = index + 9;

                        try {

                            for (i = index; i < end; i++) {
                                objtwo = (JSONObject) feedArray.get(i);
                                ItemModel modelone = new ItemModel();

                                modelone.setStreet(objtwo.getString("street"));
                                modelone.setTitle(objtwo.getString("title"));
                                modelone.setCity(objtwo.getString("city"));
                                modelone.setState(objtwo.getString("state"));
                                modelone.setCountry(objtwo.getString("country"));
                                modelone.setPostcode(objtwo.getString("postcode"));
                                modelone.setPhone(objtwo.getString("phone"));
                                modelone.setFax(objtwo.getString("fax"));
                                modelone.setLandline(objtwo.getString("landline"));
                                modelone.setEmail(objtwo.getString("email"));
                                modelone.setWebsite(objtwo.getString("website"));
                                modelone.setLandmark(objtwo.getString("landmark"));

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


        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


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
    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            feedArray = response.getJSONArray("result");
            if (feedArray.length() < 10) {
                for (ii = 0; ii < feedArray.length(); ii++) {
                    obj = (JSONObject) feedArray.get(ii);

                    ItemModel model = new ItemModel();
                    //FeedItem model=new FeedItem();
                    model.setStreet(obj.getString("street"));

                    model.setTitle(obj.getString("title"));
                  model.setCity(obj.getString("city"));
                    model.setState(obj.getString("state"));
                    model.setCountry(obj.getString("country"));
                    model.setPostcode(obj.getString("postcode"));
                    model.setPhone(obj.getString("phone"));
                    model.setFax(obj.getString("fax"));
                    model.setLandline(obj.getString("landline"));
                    model.setEmail(obj.getString("email"));
                    model.setWebsite(obj.getString("website"));
                    model.setLandmark(obj.getString("landmark"));
                    modelList.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();

            } else {
                for (ii = 0; ii <= 10; ii++) {
                    obj = (JSONObject) feedArray.get(ii);

                    ItemModel model = new ItemModel();
                    //FeedItem model=new FeedItem();
                    model.setStreet(obj.getString("street"));
                    model.setTitle(obj.getString("title"));
                  model.setCity(obj.getString("city"));
                    model.setState(obj.getString("state"));
                    model.setCountry(obj.getString("country"));
                    model.setPostcode(obj.getString("postcode"));
                    model.setPhone(obj.getString("phone"));
                    model.setFax(obj.getString("fax"));
                    model.setLandline(obj.getString("landline"));
                    model.setEmail(obj.getString("email"));
                    model.setWebsite(obj.getString("website"));
                    model.setLandmark(obj.getString("landmark"));
                    modelList.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    class ItemModel {
        private int typeid;
        private String name;
        private String street;
        private String city;
        private String pdate;
        private String postcode;
        private String title;
        private String state;
        private String country;
        private String phone;
        private String industry_type;
        private String fax;
        private String website;
        private String email;
        private String landline;
        private String landmark;
        private String whours;
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

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getEmail() {
            return email;
        }

        public String getFax() {
            return fax;
        }

        public String getIndustry_type() {
            return industry_type;
        }

        public String getLandline() {
            return landline;
        }

        public String getLandmark() {
            return landmark;
        }

        public String getPdate() {
            return pdate;
        }

        public String getPhone() {
            return phone;
        }

        public String getPostcode() {
            return postcode;
        }

        public String getState() {
            return state;
        }

        public String getTitle() {
            return title;
        }

        public String getWebsite() {
            return website;
        }

        public String getWhours() {
            return whours;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public void setIndustry_type(String industry_type) {
            this.industry_type = industry_type;
        }

        public void setLandline(String landline) {
            this.landline = landline;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public void setWhours(String whours) {
            this.whours = whours;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

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

            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(DirectoriesItemListActivity.this).inflate(R.layout.feed_directories_list, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(DirectoriesItemListActivity.this).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof UserViewHolder) {

                UserViewHolder userViewHolder = (UserViewHolder) holder;

                ItemModel itemmodel = modelList.get(position);
                userViewHolder.title.setText(itemmodel.getTitle());
                userViewHolder.street.setText(itemmodel.getStreet());
                userViewHolder.city.setText(itemmodel.getCity());
                userViewHolder.getdirection.setVisibility(View.VISIBLE);
                userViewHolder.getdirection.setTransformationMethod(null);
                userViewHolder.getdirection.setText("Get direction");
                userViewHolder.getdirection.setTypeface(tf);
                String statedestails=itemmodel.getState();
                String countrydestails=itemmodel.getCountry();
                String postdestails=itemmodel.getPostcode();
                final String phonenumber=itemmodel.getPhone();
                final String landlinenumber=itemmodel.getLandline();
                userViewHolder.state_country_post.setText(statedestails+","+countrydestails+"-"+postdestails);
                userViewHolder.phone.setText("Phone:"+phonenumber);
                userViewHolder.landline.setText("landline:"+landlinenumber);
                userViewHolder.fax.setText("Fax:"+itemmodel.getFax());
                userViewHolder.email.setText("Email:"+itemmodel.getEmail());
                userViewHolder.website.setText("Website:"+itemmodel.getWebsite());
                userViewHolder.landmark.setText("Landmark:" + itemmodel.getLandmark());
                if(phonenumber!=null||landlinenumber!=null){
                    userViewHolder.call.setVisibility(View.VISIBLE);
                    userViewHolder.call.setTransformationMethod(null);
                    userViewHolder.call.setText("Call");
                    userViewHolder.call.setTypeface(tf);
                }else {
                    userViewHolder.call.setVisibility(View.GONE);
                }
                userViewHolder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(phonenumber!=null){
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+phonenumber));
                                startActivity(callIntent);
                            }/*else {
                               if (landlinenumber!=null){
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse(landlinenumber));
                                    startActivity(callIntent);

                                }

                            }*/
                        }catch (Exception e){

                        }
                    }
                });

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
            return modelList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }
        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList == null ? 0 : modelList.size();
        }

    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView street;
        public TextView city;
        public TextView state_country_post;
        public TextView phone;
        public TextView landline;
        public TextView email;
        public TextView fax;
        public TextView website;
        public TextView landmark;
        Button call,getdirection;
        public UserViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_directories);
            street = (TextView) itemView.findViewById(R.id.street_directories);
            city = (TextView) itemView.findViewById(R.id.city_directories);
            state_country_post = (TextView) itemView.findViewById(R.id.state_contry_postcode_directories);
            phone = (TextView) itemView.findViewById(R.id.phone_directories);
            landline = (TextView) itemView.findViewById(R.id.landline_directories);

            email = (TextView) itemView.findViewById(R.id.email_directories);

            /*call=(Button)itemView.findViewById(R.id.call);
            getdirection=(Button)itemView.findViewById(R.id.getdirection);*/
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
    private void Search(){
        URLSEARCH="http://simpli-city.in/request2.php?rtype=directorysearch&key=simples&q="+search_value;
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLSEARCH, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                if (response != null) {
                    dissmissDialog();
                    parseJsonFeedTwo(response);
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
    private void parseJsonFeedTwo(JSONObject response) {

        try {
            feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject objsearch = (JSONObject) feedArray.get(i);

                ItemModel modelsearch = new ItemModel();
                modelsearch.setStreet(objsearch.getString("street"));
                modelsearch.setTitle(objsearch.getString("title"));
                modelsearch.setCity(objsearch.getString("cityimage"));
                modelsearch.setState(objsearch.getString("state"));
                modelsearch.setCountry(objsearch.getString("country"));
                modelsearch.setPostcode(objsearch.getString("postcode"));
                modelsearch.setPhone(objsearch.getString("phone"));
                modelsearch.setFax(objsearch.getString("fax"));
                modelsearch.setLandline(objsearch.getString("landline"));
                modelsearch.setEmail(objsearch.getString("email"));
                modelsearch.setWebsite(objsearch.getString("website"));
                modelsearch.setLandmark(objsearch.getString("landmark"));
                modelList.add(modelsearch);

            }

            // notify data changes to list adapater
            rcAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}

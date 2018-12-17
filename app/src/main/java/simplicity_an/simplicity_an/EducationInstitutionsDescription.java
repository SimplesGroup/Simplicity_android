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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 8/6/2016.
 */
public class EducationInstitutionsDescription extends AppCompatActivity {
    TextView about_title_label,about_description;
    TextView facility_label,auditorium_label,auditorium_detail,bank_label,bank_detail,book_label,book_detail,cafeteria_label,cafeteria_detail;
    TextView infrastructure_label,infrastructure_detail,mandapam_label,mandapam_detail,gym_label,gym_detail,hostel_label,hostel_detail,library_label,library_detail;
    TextView address_label,address_detail;
    NetworkImageView thump;
    WebView description;
    String bimage;
    ImageButton comment,share,menu,back,favourite;
    String notifiid;

    RequestQueue queue,requestQueue;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    List<ItemModel> modelListsecond=new ArrayList<ItemModel>();
    List<ItemModel> modelListgovt=new ArrayList<ItemModel>();
    List<ItemModel> modelListevent=new ArrayList<ItemModel>();
    String URL="http://simpli-city.in/request2.php?rtype=instituitions&key=simples&id=";
    String URLTWO;
    String sourcelink,myprofileid;
    final String TAG_REQUEST = "MY_TAG";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    RecyclerViewAdapter rcadap;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    String URLFAV="http://simpli-city.in/request2.php?rtype=addfav&key=simples";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    private String KEY_QTYPE= "qtype";
    int post_likes_count;

    String shareurl,sharetitle;
    int favcount;
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid,activity;
    ProgressDialog pdialog;
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    String description_comment,my_profilename,my_profileimage;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.educationinstitutiondescription);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        contentid=sharedpreferences.getString(CONTENTID,"");
        activity=sharedpreferences.getString(Activity,"");
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
        // RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        requestQueue=Volley.newRequestQueue(this);
        /*Intent getnotifi=getIntent();
        notifiid=getnotifi.getStringExtra("ID");*/
        if(activity==null){
            Intent getnotifi=getIntent();
            notifiid=getnotifi.getStringExtra("ID");
            notifiid = notifiid.replaceAll("\\D+","");
            Log.e("ID",notifiid);

        } else {
            if(activity.equalsIgnoreCase("eduintsdesc")){
                notifiid=contentid;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(Activity);
                editor.remove(CONTENTID);
                editor.apply();

            }else {
                Intent getnotifi=getIntent();
                notifiid=getnotifi.getStringExtra("ID");
                notifiid = notifiid.replaceAll("\\D+","");
                Log.e("ID",notifiid);
            }
        }

        if(myprofileid!=null){
            URLTWO=URL+notifiid+"&user_id="+myprofileid;
        }else {
            URLTWO=URL+notifiid;
        }

        about_title_label = (TextView) findViewById(R.id.about_education);
        about_description = (TextView) findViewById(R.id.about_education_description);

        facility_label=(TextView) findViewById(R.id.title_facilities);
        auditorium_label=(TextView) findViewById(R.id.title_autitorim_label);
        auditorium_detail=(TextView) findViewById(R.id.title_autitorim_detail);

        bank_label=(TextView) findViewById(R.id.title_bank_label);
        bank_detail=(TextView) findViewById(R.id.title_bank_detail);
        book_label=(TextView) findViewById(R.id.title_bookstore_label);
        book_detail=(TextView) findViewById(R.id.title_bookstore_detail);
        cafeteria_label=(TextView) findViewById(R.id.title_cafeteria_label);
        cafeteria_detail=(TextView) findViewById(R.id.title_cafeteria_detail);
        infrastructure_label=(TextView) findViewById(R.id.title_infrastructure_label);
        infrastructure_detail=(TextView) findViewById(R.id.title_infrastructure_detail);
        mandapam_label=(TextView) findViewById(R.id.title_mandapam_label);
        mandapam_detail=(TextView) findViewById(R.id.title_mandapam_detail);
        gym_label=(TextView) findViewById(R.id.title_gym_label);
        gym_detail=(TextView) findViewById(R.id.title_gym_detail);
        hostel_label=(TextView) findViewById(R.id.title_hostel_label);
        hostel_detail=(TextView) findViewById(R.id.title_hostel_detail);
        library_label=(TextView) findViewById(R.id.title_library_label);
        library_detail=(TextView) findViewById(R.id.title_library_detail);
        address_label=(TextView) findViewById(R.id.title_address);
        address_detail=(TextView) findViewById(R.id.title_address_detail);



        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));






        comment = (ImageButton) findViewById(R.id.btn_4);
        share = (ImageButton) findViewById(R.id.btn_5);
        menu = (ImageButton) findViewById(R.id.btn_3);
        back = (ImageButton) findViewById(R.id.btn_1);
        favourite = (ImageButton) findViewById(R.id.btn_2);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        String simplycity_title_fontPathone = "fonts/Lora-Regular.ttf";;
        Typeface tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPathone);
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdialog.setCanceledOnTouchOutside(false);


        about_description.setTypeface(tf_regular);
        about_title_label.setTypeface(tf_regular);
        auditorium_label.setTypeface(tf_regular);
        auditorium_detail.setTypeface(tf_regular);
        bank_label.setTypeface(tf_regular);
        bank_detail.setTypeface(tf_regular);
        book_label.setTypeface(tf_regular);
        book_detail.setTypeface(tf_regular);
        cafeteria_label.setTypeface(tf_regular);
        cafeteria_detail.setTypeface(tf_regular);
        infrastructure_label.setTypeface(tf_regular);
        infrastructure_detail.setTypeface(tf_regular);
        mandapam_label.setTypeface(tf_regular);
        mandapam_detail.setTypeface(tf_regular);
        gym_label.setTypeface(tf_regular);
        gym_detail.setTypeface(tf_regular);
        hostel_label.setTypeface(tf_regular);
        hostel_detail.setTypeface(tf_regular);
        address_label.setTypeface(tf_regular);
        address_detail.setTypeface(tf_regular);

        thump = (NetworkImageView) findViewById(R.id.thumbnailone);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_afffairs);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        rcadap = new RecyclerViewAdapter(this, modelListsecond);
        mRecyclerView.setAdapter(rcadap);
        about_title_label.setText("About Us");
        address_label.setText("Address");
        facility_label.setText("Facilities");
        if(notifiid!=null) {
            JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


                public void onResponse(JSONObject response) {

                    //VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                       pdialog.dismiss();
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
           // AppControllers.getInstance().addToRequestQueue(jsonreq);
            requestQueue.add(jsonreq);
        }else {

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent back_article = new Intent(getApplicationContext(), MainActivityVersiontwo.class);
                startActivity(back_article);
                finish();*/
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu_article = new Intent(getApplicationContext(), MainPageEnglish.class);
                startActivity(menu_article);
                finish();
            }
        });

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
                   editor.putString(Activity, "eduintsdesc");
                   editor.putString(CONTENTID, notifiid);
                   editor.commit();
                    Intent signin=new Intent(EducationInstitutionsDescription.this,SigninpageActivity.class);
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

    private void parseJsonFeed(JSONObject response) {
        String personal="personal";
        String managementpeople="team";
        String facilities="facilities";

        ImageLoader mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
        try {


            if(personal.equals("personal")) {
                JSONArray feedArray = response.getJSONArray("personal");
                // JSONArray feedArraytwo = response.getJSONArray("article");

                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject obj = (JSONObject) feedArray.get(i);


                    ItemModel model = new ItemModel();
                    String image = obj.isNull("image") ? null : obj
                            .getString("image");
                    model.setImage(image);
                    model.setAbout(obj.getString("about"));
                    model.setTitle(obj.getString("name"));
                    model.setCity(obj.getString("city"));
                    model.setStreet(obj.getString("street"));
                    model.setPhonenumber(obj.getString("phone"));
                    model.setPincode(obj.getString("pincode"));

                    model.setId(obj.getString("id"));
                    model.setFavcount(obj.getInt("fav_count"));
                    model.setShareurl(obj.getString("sharingurl"));
                    favcount=obj.getInt("fav_count");
                    post_likes_count=obj.getInt("fav_count");
                    shareurl=obj.getString("sharingurl");
                    sharetitle=obj.getString("title");
                    about_description.setText(obj.getString("about"));
                    thump.setDefaultImageResId(R.mipmap.ic_launcher);
                    thump.setErrorImageResId(R.mipmap.ic_launcher);
                    thump.setImageUrl(image, mImageLoader);
                    address_detail.setText(obj.getString("name")+","+obj.getString("street")+","+obj.getString("city")+"-"+obj.getString("pincode")+"."+"Phone:"+obj.getString("phone"));
                    modelList.add(model);
                    if (favcount == 1) {
                        favourite.setImageResource(R.drawable.favred);
                    } else {
                        favourite.setImageResource(R.drawable.favtwo);
                    }
                    favourite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(myprofileid!=null) {

                                if (post_likes_count == 1) {
                                    favourite.setImageResource(R.drawable.favtwo);
                                    post_likes_count--;





                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLFAV,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String s) {
                                                    //Disimissing the progress dialog

                                                    //Showing toast message of the response
                                                    if (s.equalsIgnoreCase("no")) {
                                                        //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                    } else {
                                                        Log.e("response:", s);

                                                    }

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {
                                                    //Dismissing the progress dialog
                                                    //loading.dismiss();

                                                    //Showing toast
                                                    //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                }
                                            }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> params = new Hashtable<String, String>();
                                            String postid = notifiid;
                                            //Adding parameters
                                            if (postid != null) {


                                                params.put(KEY_POSTID, postid);
                                                params.put(KEY_MYUID, myprofileid);
                                                params.put(KEY_QTYPE, "article");
                                            } else {


                                            }


                                            return params;
                                        }
                                    };

                                    //Creating a Request Queue
                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                    //Adding request to the queue
                                    requestQueue.add(stringRequest);

                                } else {
                                    favourite.setImageResource(R.drawable.favred);
                                    post_likes_count++;



                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLFAV,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String s) {
                                                    //Disimissing the progress dialog

                                                    //Showing toast message of the response
                                                    if (s.equalsIgnoreCase("no")) {
                                                        //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                    } else {
                                                        Log.e("response:", s);


                                                    }

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {
                                                    //Dismissing the progress dialog
                                                    //loading.dismiss();

                                                    //Showing toast
                                                    //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                }
                                            }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            //Converting Bitmap to String

                                            //Getting Image Name

                                            //Creating parameters
                                            Map<String, String> params = new Hashtable<String, String>();
                                            String postid = notifiid;
                                            //Adding parameters
                                            if (postid != null) {


                                                params.put(KEY_POSTID, postid);
                                                params.put(KEY_MYUID, myprofileid);
                                                params.put(KEY_QTYPE, "learning");
                                            } else {


                                            }


                                            return params;
                                        }
                                    };

                                    //Creating a Request Queue
                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                    //Adding request to the queue
                                    requestQueue.add(stringRequest);
                                    //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();
                                }
                            }else {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Activity, "eduintsdesc");
                                editor.putString(CONTENTID, notifiid);
                                editor.commit();
                                Intent signin=new Intent(EducationInstitutionsDescription.this,SigninpageActivity.class);
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
                            sendIntent.putExtra(Intent.EXTRA_TEXT, sharetitle+ "\n" + shareurl);
                            sendIntent.setType("text/plain");
                            startActivity(Intent.createChooser(sendIntent, "Share using"));

                        }
                    });

                    // modelListsecond.add(model);
                }
            }
          if(managementpeople.equals("team")) {
             // dissmissDialog();
                JSONArray feedArraytwo = response.getJSONArray("team");

                for (int i = 0; i < feedArraytwo.length(); i++) {
                    JSONObject objt = (JSONObject) feedArraytwo.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("image") ? null : objt
                            .getString("image");
                    model.setImage(image);
                   model.setAdministrativename(objt.getString("name"));
                    model.setChairmainposition(objt.getString("position"));


                    // modelList.add(model);
                    modelListsecond.add(model);
                }
            }
            if(facilities.equals("facilities")) {
                JSONArray feedArraygovt = response.getJSONArray("facilities");

                for (int i = 0; i < feedArraygovt.length(); i++) {
                    JSONObject objt = (JSONObject) feedArraygovt.get(i);


                    ItemModel model = new ItemModel();
                    model.setAutitorium(objt.getString("Auditorium"));
                    model.setBank(objt.getString("Bank"));
                    model.setBookstore(objt.getString("Book Store"));
                    model.setCafeteria(objt.getString("Cafeteria"));
                    model.setInfrastructure(objt.getString("Infrastructure"));
                    model.setMandapam(objt.getString("Mandapam"));
                    model.setGym(objt.getString("Gym"));
                    model.setHostels(objt.getString("Hostels"));
                    model.setLibrary(objt.getString("Library"));
auditorium_label.setText("Auditorium");
                    bank_label.setText("Bank");
                    book_label.setText("Book Store");
                    cafeteria_label.setText("Cafeteria");
                    infrastructure_label.setText("Computing Infrastructure");
                    mandapam_label.setText("Dhyana Mandapam");
                    gym_label.setText("Gym");
                    hostel_label.setText("Hostels");
                    library_label.setText("Library");

                    auditorium_detail.setText(objt.getString("Auditorium"));
                    bank_detail.setText(objt.getString("Bank"));
                    book_detail.setText(objt.getString("Book Store"));
                    cafeteria_detail.setText(objt.getString("Cafeteria"));
                    infrastructure_detail.setText(objt.getString("Infrastructure"));
                    mandapam_detail.setText(objt.getString("Mandapam"));
                    gym_detail.setText(objt.getString("Gym"));
                    hostel_detail.setText(objt.getString("Hostels"));
                    library_detail.setText(objt.getString("Library"));
                    // modelList.add(model);
                    modelListgovt.add(model);
                }
            }

            // notify data changes to list adapater
            // rcAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
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
        String autitorium,bank,bookstore,cafeteria,infrastructure,mandapam,gym,hostels,library;
        /******** start the Food category names****/
        private String source,administrativename,chairmainposition;

        String id,about,phonenumber,pincode;
        private String source_link,street,city;
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

        public String getAutitorium() {
            return autitorium;
        }

        public void setAutitorium(String autitorium) {
            this.autitorium = autitorium;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBookstore() {
            return bookstore;
        }

        public String getAdministrativename() {
            return administrativename;
        }

        public void setBookstore(String bookstore) {
            this.bookstore = bookstore;
        }

        public String getCafeteria() {
            return cafeteria;
        }

        public void setCafeteria(String cafeteria) {
            this.cafeteria = cafeteria;
        }

        public String getGym() {
            return gym;
        }

        public void setGym(String gym) {
            this.gym = gym;
        }

        public String getHostels() {
            return hostels;
        }

        public void setHostels(String hostels) {
            this.hostels = hostels;
        }

        public String getMandapam() {
            return mandapam;
        }

        public void setMandapam(String mandapam) {
            this.mandapam = mandapam;
        }

        public String getInfrastructure() {
            return infrastructure;
        }

        public void setInfrastructure(String infrastructure) {
            this.infrastructure = infrastructure;
        }

        public String getLibrary() {
            return library;
        }

        public void setLibrary(String library) {
            this.library = library;
        }

        public String getChairmainposition() {
            return chairmainposition;
        }

        public void setAdministrativename(String administrativename) {
            this.administrativename = administrativename;
        }

        public void setChairmainposition(String chairmainposition) {
            this.chairmainposition = chairmainposition;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getCity() {
            return city;
        }

        public String getStreet() {
            return street;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setStreet(String street) {
            this.street = street;
        }
        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
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

    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelListsecond=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapter(Context context, List<ItemModel> modelList) {
            this.modelListsecond = modelList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_education_institutions, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = MySingleton.getInstance(EducationInstitutionsDescription.this).getImageLoader();
            ItemModel itemmodel=modelListsecond.get(position);

            // if(types.equals("news")) {
            holder.titles.setText(itemmodel.getAdministrativename()+"\n"+itemmodel.getChairmainposition());
            holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
            holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
            holder.profileimage.setAdjustViewBounds(true);
            holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
           /* }else {
                holder.profileimage.setVisibility(View.GONE);
                holder.titles.setVisibility(View.GONE);
            }*/
        }

        @Override
        public int getItemCount() {
            return modelListsecond.size();
        }
        public class RecyclerViewHolders extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHolders(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }


    public static class MyDialogFragment extends DialogFragment {
        private String KEY_COMMENT = "comment";
        private String KEY_TYPE = "qtype";
        private String KEY_MYUID = "user_id";
        private String KEY_POSTID = "id";
        String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=instituition&id=";
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
            View root = inflater.inflate(R.layout.commenteducationfragment, container, false);
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
                                    params.put(KEY_TYPE, "instituition");
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


                    userViewHolder.name.setText(itemmodel.getName());
                    userViewHolder.name.setTypeface(seguiregular);
                    userViewHolder.commentsdecription.setText(itemmodel.getComment());
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

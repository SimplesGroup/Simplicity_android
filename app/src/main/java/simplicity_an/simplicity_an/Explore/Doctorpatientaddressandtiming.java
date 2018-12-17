package simplicity_an.simplicity_an.Explore;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;

/**
 * Created by KuppuSamy on 8/29/2017.
 */

public class Doctorpatientaddressandtiming extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    RequestQueue requestQueue;
    public static final String backgroundcolor = "color";
    String colorcodes,myprofileid;
    RelativeLayout mainlayout;
    TextView patient_title_textview,doctors_timing_textview,mylocation_textview;
    TextView gender_textview;
    CheckBox gender_male,gender_female;
    EditText patient_name_editext,patient_age_editext,patient_email_editext,patient_mobile_editext,patient_address_editext;
    Button next_button;
    LinearLayout detectmy_location_layout;
    ImageButton back,main,settings;
    RecyclerView illnesslist_recylerview;
    List<DoctorItem> timinglist=new ArrayList<>();
   Recyclerdoctorcalladapter recyclerdoctorcalladapter;
    ProgressDialog pDialog;
    String URLALL,URL;
    private final String TAG_REQUEST = "MY_TAG";
    int requestcount=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_doctor_patient_addressandtiming);
        String simplycity_title_reugular= "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_reugular);
        requestQueue= Volley.newRequestQueue(this);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        mainlayout=(RelativeLayout)findViewById(R.id.main_layout_explore);
        if(colorcodes.length()==0){

        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi");
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

        DoctorItem item1=new DoctorItem();
        item1.setId("1");
        item1.setTiming("06:00Am to 09:00 Am");
        timinglist.add(item1);

        DoctorItem item2=new DoctorItem();
        item2.setId("1");
        item2.setTiming("09:00 Am to 12:00 Am");
        timinglist.add(item2);

        DoctorItem item3=new DoctorItem();
        item3.setId("1");
        item3.setTiming("12:00 Am to 15:00 pm");
        timinglist.add(item3);
        patient_title_textview =(TextView)findViewById(R.id.patient_details);
        gender_textview =(TextView)findViewById(R.id.patient_gender);
        doctors_timing_textview =(TextView)findViewById(R.id.choose_prefered_time);
        mylocation_textview =(TextView)findViewById(R.id.detect_my_locaion);
        detectmy_location_layout=(LinearLayout)findViewById(R.id.location);

        gender_male=(CheckBox)findViewById(R.id.gender_male);
        gender_female=(CheckBox)findViewById(R.id.gender_female);

        patient_age_editext=(EditText)findViewById(R.id.patient_age);
        patient_name_editext=(EditText)findViewById(R.id.patient_name);
        patient_email_editext=(EditText)findViewById(R.id.patient_email);
        patient_mobile_editext=(EditText)findViewById(R.id.patient_mobile);
        patient_address_editext=(EditText)findViewById(R.id.patient_address_edit);

        next_button=(Button)findViewById(R.id.Pay_fees_button);


        patient_title_textview.setTypeface(tf1);
        gender_textview .setTypeface(tf1);
        doctors_timing_textview.setTypeface(tf1);
        mylocation_textview.setTypeface(tf1);

        patient_age_editext.setTypeface(tf1);
        patient_name_editext.setTypeface(tf1);
        patient_email_editext.setTypeface(tf1);
        patient_mobile_editext.setTypeface(tf1);
        patient_address_editext.setTypeface(tf1);
        next_button.setTypeface(tf1);

        patient_title_textview.setText("Patient Details");
        gender_textview.setText("Gender");
        doctors_timing_textview.setText("Choose Preferred Time");
        patient_address_editext.setHint("fill your address");
        next_button.setText("Next");
        mylocation_textview.setText("Detect my location");
                back=(ImageButton)findViewById(R.id.btn_ex_back) ;
        main=(ImageButton)findViewById(R.id.btn_ex_search);
        settings=(ImageButton)findViewById(R.id.btn_ex_more);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),ExploreMain.class);
                startActivity(in);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notification_page = new Intent(getApplicationContext(), ExploreSettings.class);
                startActivity(notification_page);
            }
        });

        illnesslist_recylerview=(RecyclerView)findViewById(R.id.doctoroncall_recylerview);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);

        illnesslist_recylerview.setNestedScrollingEnabled(false);
        illnesslist_recylerview.setLayoutManager(layoutManager);

        pDialog=new ProgressDialog(this);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progressdialog);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerdoctorcalladapter = new Recyclerdoctorcalladapter(timinglist,illnesslist_recylerview);
        illnesslist_recylerview.setAdapter(recyclerdoctorcalladapter);

        recyclerdoctorcalladapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        //getData();


                        recyclerdoctorcalladapter.setLoaded();
                    }
                }, 2000);
            }
        });
//getData();


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gender_male.isChecked()&&gender_female.isChecked()){
                    Toast.makeText(getApplicationContext(),"Please select one category in gender",Toast.LENGTH_SHORT).show();
                }else {
                    Intent nextpages=new Intent(getApplicationContext(),DoctorPatientDetails.class);
                    startActivity(nextpages);
                }

            }
        });
    }



    private void getData(){
        requestQueue.add(getDatafromServer(requestcount));
        requestcount++;
    }
    private JsonObjectRequest getDatafromServer(int requestcount){

        if(myprofileid!=null){
            URLALL=URL+"&page="+requestcount+"&user_id="+myprofileid;
        }else {
            URLALL=URL+"&page="+requestcount;
        }

        Log.e("URL",URLALL);
        JsonObjectRequest   jsonReq = new JsonObjectRequest(Request.Method.GET,
                URLALL,  new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                if (response != null) {
                    dissmissDialog();
                    parseJsonFeed(response);
                    Log.e("JSON",response.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
            }
        });

        jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonReq);
        return jsonReq;
    }
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray productlist = response.getJSONArray("result");
            for (int i = 0; i < productlist.length(); i++) {
                DoctorItem items = new DoctorItem();
                JSONObject jsonObject = (JSONObject) productlist.get(i);
            }
        } catch (JSONException e) {

        }
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pDialog != null) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            pDialog = null;
        }

    }
    class DoctorItem{
        String timing,id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTiming() {
            return timing;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }
    }
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
    static  class UserviewHolder extends RecyclerView.ViewHolder{
        TextView illness_title;
        CheckBox checkBox_select;
        public UserviewHolder(View itemView) {
            super(itemView);
            illness_title=(TextView)itemView.findViewById(R.id.illness_names);
            checkBox_select=(CheckBox)itemView.findViewById(R.id.checkbox_illness);


        }
    }
    public  class  Recyclerdoctorcalladapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        ImageLoader mImageLoader;
        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_LOADING = 3;
        OnLoadMoreListener onLoadMoreListener;
        private final int VIEW_TYPE_PHOTOSTORY = 2;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;
        boolean loading;
        SpinnerAdapter spinnerAdapter;
        String pidvalues;
        ArrayAdapter<String> dataAdapter;
        public Recyclerdoctorcalladapter(List<DoctorItem> students, RecyclerView recyclerView) {
            timinglist = students;

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
        public int getItemCount() {
            return timinglist.size();
        }
        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }
        public void setLoaded() {
            loading = false;
        }
        public int getItemViewType(int position) {
            return timinglist.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from ( parent.getContext () );
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.explore_doctoroncall_main_feed, parent, false);
                return new UserviewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof UserviewHolder) {
                UserviewHolder userViewHolder = (UserviewHolder) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                final Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

                DoctorItem doctorItem=timinglist.get(position);
                userViewHolder.illness_title.setText(doctorItem.getTiming());
                userViewHolder.illness_title.setTypeface(seguiregular);


            } else if (holder instanceof LoadingViewHolder) {
               LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
    }

}

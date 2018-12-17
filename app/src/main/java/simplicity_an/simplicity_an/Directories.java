package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
 * Created by kuppusamy on 2/22/2016.
 */
public class Directories extends AppCompatActivity {
    LinearLayoutManager lLayout;
  List<ItemModel> modelList=new ArrayList<ItemModel>();
     ProgressDialog pdialog;

    // Need this to link with the Snackbar
     CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
   CollapsingToolbarLayout mCollapsingToolbarLayout;

    private String URLTW="http://simpli-city.in/request2.php?rtype=directory_cat&key=simples";
    public String URLTHREE;
    private static final String TAG = "LocalBroadcastDemo";
    ImageButton back,menu,notification,addnewpost;
    SearchView search;
    TextView title;
    RecyclerViewAdapter rcAdapter;
    String bimage;
    RecyclerView recycler;
    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue queues;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    String URLPOSTQTYPE,postid;
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid;
    int value;
    String notification_counts;
    RequestQueue requestQueue_directories;
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.directoriesactivity);
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
        requestQueue_directories=Volley.newRequestQueue(this);
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
        queues = simplicity_an.simplicity_an.MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        back=(ImageButton)findViewById(R.id.btn_dirmainback);
        notification=(ImageButton)findViewById(R.id.btn_dirmainprofile);
        addnewpost=(ImageButton)findViewById(R.id.btn_dirmainfav);
        menu=(ImageButton)findViewById(R.id.btn_dirmainhome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /* Intent back=new Intent(getApplicationContext(),MainActivityVersiontwo.class);
                startActivity(back);
                finish();*/
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu=new Intent(getApplicationContext(), MainPageEnglish.class);
                startActivity(menu);
                finish();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value==0){
                    Intent notification_page=new Intent(Directories.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }else {
                    Uloaddataservernotify();
                    Intent notification_page=new Intent(Directories.this, MainPageEnglish.class);
                    startActivity(notification_page);
                    finish();
                }
            }
        });
        addnewpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null){
                    Intent addevent=new Intent(Directories.this,AddnewDirectories.class);
                    startActivity(addevent);
                    finish();

                }else {
                    Intent signin=new Intent(Directories.this,SigninpageActivity.class);
                    startActivity(signin);
                    finish();

                }
            }
        });
        search=(SearchView)findViewById(R.id.searchdirectories);
        title=(TextView)findViewById(R.id.toolbar_title);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        title.setText("Directory Services");
        title.setTypeface(tf);
        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) search.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        magImage.setVisibility(View.GONE);
        int searchPlateId = search.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = search.findViewById(searchPlateId);
        recycler = (RecyclerView)findViewById(R.id.directories_recyclerview);

        recycler.addItemDecoration(new MarginDecoration(this));
       // recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
       //search.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
         rcAdapter = new RecyclerViewAdapter(this, modelList);
        recycler.setAdapter(rcAdapter);
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
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                /*Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

               /* Toast.makeText(getBaseContext(), query,
                        Toast.LENGTH_SHORT).show();*/
Intent serchviewpaage=new Intent(Directories.this, simplicity_an.simplicity_an.DirectoriesSearchview.class);
                serchviewpaage.putExtra("QUERY",query);
                startActivity(serchviewpaage);
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
        recycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String id = ((ItemModel) modelList.get(position)).getId();
                String catname = ((ItemModel) modelList.get(position)).getCategory_name();
                Intent i = new Intent(Directories.this, simplicity_an.simplicity_an.DirectoriesServiceItemSubCategory.class);
                i.putExtra("ID", id);
                i.putExtra("CATNAME",catname);
                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        JsonArrayRequest arrayReq=new JsonArrayRequest(URLTW, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub
                dissmissDialog();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj=response.getJSONObject(i);
                        ItemModel model=new ItemModel();
                        model.setId(obj.getString("id"));
                        model.setCategory_name(obj.getString("category"));
                       // modelList.add(obj.getString("category_name"));
                        modelList.add(model);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
               rcAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                // VolleyLog.d(TAG, "ERROR" + error.getMessage());
            }
        });

        arrayReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppControllers.getInstance().addToRequestQueue(jsonReq);
        requestQueue_directories.add(arrayReq);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    class ItemModel{
        private String category_name;
        private String id;

        String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        /******** start the Food category names****/
        public String getCategory_name(){return  category_name;}

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {
        private LayoutInflater inflater;

        private List<ItemModel> modelList=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapter(Context context, List<ItemModel> modelList) {
            this.modelList = modelList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_directories, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {


            ItemModel itemmodel=modelList.get(position);
            holder.list_name.setText(itemmodel.getCategory_name());

        }

        public int getItemCount() {
            return this.modelList == null ? 0 :this.modelList.size();
        }
        public class RecyclerViewHolders extends RecyclerView.ViewHolder{

            public TextView list_name;

            public RecyclerViewHolders(View itemView) {
                super(itemView);

                list_name=(TextView)itemView.findViewById(R.id.categories_list_name);


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
}



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
import android.support.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 7/23/2016.
 */
public class  DirectoriesServiceItemSubCategory extends AppCompatActivity {
    LinearLayoutManager lLayout;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    ProgressDialog pdialog;

    // Need this to link with the Snackbar
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    RequestQueue requestQueue_directories;
    private String URL="http://simpli-city.in/request2.php?rtype=directory_sub&key=simples&cat_id=";
    public String URLTHREE;
    private static final String TAG = "LocalBroadcastDemo";
    ImageButton back,menu,addnewpost;
    SearchView search;
    TextView title;
    RecyclerViewAdapter rcAdapter;
    String bimage;
    RecyclerView recycler;
    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue queues;
    String URLTWO,URLSEARCH;
    String URLtwo,MainCatname,myprofileid;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.directoriessubcategoryitem);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        queues = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        requestQueue_directories= Volley.newRequestQueue(this);
        Intent get=getIntent();
        URLtwo=get.getStringExtra("ID");
        MainCatname=get.getStringExtra("CATNAME");
        URLTWO=URL+URLtwo;
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        back=(ImageButton)findViewById(R.id.btn_dirmainback);
        menu=(ImageButton)findViewById(R.id.btn_dirmainhome);
        addnewpost=(ImageButton)findViewById(R.id.btn_dirmainfav);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent back=new Intent(getApplicationContext(),Directories.class);
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

            }
        });
        addnewpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null){
                    Intent addevent=new Intent(DirectoriesServiceItemSubCategory.this,AddnewDirectories.class);
                    startActivity(addevent);


                }else {
                    Intent signin=new Intent(DirectoriesServiceItemSubCategory.this,SigninpageActivity.class);
                    startActivity(signin);


                }
            }
        });
        title=(TextView)findViewById(R.id.toolbar_title_sub_directory);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        title.setText(MainCatname);
        title.setTypeface(tf);

        recycler = (RecyclerView)findViewById(R.id.directories_sub_recyclerview);

        recycler.addItemDecoration(new MarginDecoration(this));
        // recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        //search.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        rcAdapter = new RecyclerViewAdapter(this, modelList);
        recycler.setAdapter(rcAdapter);
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
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        recycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String id = ((ItemModel) modelList.get(position)).getId();
                String catid = ((ItemModel) modelList.get(position)).getCatmainid();
                String catsub_name = ((ItemModel) modelList.get(position)).getCategory_name();
                Intent i = new Intent(DirectoriesServiceItemSubCategory.this, DirectoriesSubitemDetaillist.class);
                i.putExtra("ID", id);
                i.putExtra("CATID",catid);
                i.putExtra("CATNAME",MainCatname);
                i.putExtra("CATNAMESUB",catsub_name);
                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        JsonArrayRequest arrayReq=new JsonArrayRequest(URLTWO, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub
                dissmissDialog();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj=response.getJSONObject(i);
                        ItemModel model=new ItemModel();
                        model.setId(obj.getString("id"));
                        model.setCategory_name(obj.getString("sname"));
                        model.setCatmainid(obj.getString("cat_id"));
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
        //AppController.getInstance().addToRequestQueue(jsonReq);
        requestQueue_directories.add(arrayReq);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    class ItemModel{
        private String category_name;
        private String id;
        private String catmainid;


        public String getCatmainid() {
            return catmainid;
        }

        public void setCatmainid(String catmainid) {
            this.catmainid = catmainid;
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

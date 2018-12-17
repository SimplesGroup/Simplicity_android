package simplicity_an.simplicity_an.Tamil.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import simplicity_an.simplicity_an.AddNewEvent;
import simplicity_an.simplicity_an.AppControllers;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.Tamil.FragPoojaTamil;
import simplicity_an.simplicity_an.Tamil.FragmenteventTomorrowTamil;

/**
 * Created by kuppusamy on 2/11/2016.
 */
public class TamilEvents extends AppCompatActivity {
    LinearLayoutManager lLayout;
     List<ItemModel> modelList=new ArrayList<ItemModel>();
     ProgressDialog pdialog;
     String URL="http://simpli-city.in//request2.php?rtype=foodnew&key=simples&fid=All";


    public String spinpostwo="";

    ArrayList<String> worldlist = new ArrayList<String>();

    // Need this to link with the Snackbar
   CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
   CollapsingToolbarLayout mCollapsingToolbarLayout;

    private ViewPager mPager;
    private MYEventViewPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    ArrayAdapter<String> SpinnerAdapter;
    Spinner spin;

     String URLTW="http://simpli-city.in//request2.php?rtype=eventcategory&key=simples";


    ImageButton back,menu,search,notification,addnewevent;
    String urlpost="http://simpli-city.in/request2.php?key=simples&rtype=user_history";
    String URLPOSTQTYPE,postid;
    private String KEY_QTYPE = "qtype";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    TextView toolbartitle,notification_batge_count;
    String url_noti_count="http://simpli-city.in/request2.php?rtype=notificationcount&key=simples&user_id=";
    String url_notification_count_valueget,myprofileid;
    int value;
    String notification_counts,colorcodes;
    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue   queues;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.eventsactivity);
        notification_batge_count=(TextView)findViewById(R.id.text_batchvalue_main);
        notification_batge_count.setVisibility(View.GONE);
           queues = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        Log.e("coloR",colorcodes);
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
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
back=(ImageButton)findViewById(R.id.btn_eventback);
        menu=(ImageButton)findViewById(R.id.btn_eventhome);
        addnewevent=(ImageButton)findViewById(R.id.btn_eventfav);
        search=(ImageButton)findViewById(R.id.btn_eventsearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","2");
                startActivity(entairnment);
                overridePendingTransition(R.anim.righttoleft, R.anim.righttoleft);
            }
        });

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       /* Intent backevent = new Intent(getApplicationContext(), MainPageTamil.class);
        startActivity(backevent);
        finish();*/
        onBackPressed();
        overridePendingTransition(R.anim.lefttoright, R.anim.lefttoright);
    }
});
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","4");
                startActivity(entairnment);
                overridePendingTransition(R.anim.righttoleft, R.anim.righttoleft);
            }
        });
        notification=(ImageButton)findViewById(R.id.btn_eventprofile) ;
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","1");
                startActivity(entairnment);
                overridePendingTransition(R.anim.righttoleft, R.anim.righttoleft);

            }
        });
addnewevent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(myprofileid!=null){
            Intent addevent=new Intent(TamilEvents.this,AddNewEvent.class);
            startActivity(addevent);

            overridePendingTransition(R.anim.righttoleft, R.anim.righttoleft);
        }else {
            Intent signin=new Intent(TamilEvents.this,SigninpageActivity.class);
            startActivity(signin);
            overridePendingTransition(R.anim.righttoleft, R.anim.righttoleft);

        }
    }
});
       /* back.setImageResource(R.mipmap.citytamil);
        addnewevent.setImageResource(R.mipmap.happeninigtamil);
        menu.setImageResource(R.mipmap.exploretamil);
        search.setImageResource(R.mipmap.audiotamil);
        notification.setImageResource(R.mipmap.moretamil);*/

        if(colorcodes.equalsIgnoreCase("#262626")){
            addnewevent.setBackgroundResource(R.color.theme1button);
        }else if(colorcodes.equalsIgnoreCase("#59247c")){
            addnewevent.setBackgroundResource(R.color.theme2);
        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
            addnewevent.setBackgroundResource(R.color.theme3);
        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
            addnewevent.setBackgroundResource(R.color.theme4);
        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
            addnewevent.setBackgroundResource(R.color.theme5);
        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
            addnewevent.setBackgroundResource(R.color.theme6);
        }else if(colorcodes.equalsIgnoreCase("#185546")){
            addnewevent.setBackgroundResource(R.color.theme7);
        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
            addnewevent.setBackgroundResource(R.color.theme8);
        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
            addnewevent.setBackgroundResource(R.color.theme9);
        }else if(colorcodes.equalsIgnoreCase("#339900")){
            addnewevent.setBackgroundResource(R.color.theme10);
        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
            addnewevent.setBackgroundResource(R.color.theme11);
        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
            addnewevent.setBackgroundResource(R.color.theme12);
        }

      /*  spin=(Spinner)findViewById(R.id.my_spinner);
        worldlist.add("All Events");
        JsonArrayRequest arrayReq=new JsonArrayRequest(URLTW, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub

                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj=response.getJSONObject(i);
                        ItemModel model=new ItemModel();
                        model.setId(obj.getString("id"));
                        //model.setImage(obj.getString("image"));
                        //model.setName(obj.getString("name"));
                        modelList.add(model);
                        worldlist.add(obj.getString("category_name"));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                SpinnerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                // VolleyLog.d(TAG, "ERROR" + error.getMessage());
            }
        });
        arrayReq.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        arrayReq.setTag(TAG_REQUEST);
        MySingleton.getInstance(this).addToRequestQueue(arrayReq);
        SpinnerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.view_spinner_item,
                worldlist
        ){
            //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,
                        parent);

                GradientDrawable gd = new GradientDrawable();


                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(14);
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setPadding(50, 0, 10, 0);
                ((TextView) v).setCompoundDrawables(null, null, null, null);
                return v;
            }
        };
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(SpinnerAdapter);  // Set Adapter in the spinner

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinpostwo=spin.getSelectedItem().toString();
                //s=position;
                if (position == 0) {

                } else {
                    int pos=position-1;
                    String id_food = ((ItemModel)modelList.get(pos)).getId();



                    //Toast.makeText(getApplicationContext(),id_food,Toast.LENGTH_LONG).show();
                    Intent in=new Intent(getApplicationContext(),EventCategorypage.class);
                    in.putExtra("ID",id_food);
                    startActivity(in);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
       /* mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();*/

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mAdapter = new MYEventViewPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        //mPager.setAdapter(mAdapter);
        setupViewPager(mPager);
        //Notice how the Tab Layout links with the Pager Adapter
        mTabLayout.setTabsFromPagerAdapter(mAdapter);



        //Notice how The Tab Layout adn View Pager object are linked
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupViewPager(ViewPager mPager) {
    MYEventViewPagerAdapter adapter = new MYEventViewPagerAdapter(getSupportFragmentManager());

    adapter.addFragment(new FragAllEventtamil(), "அனைத்தும்");
    adapter.addFragment(new FragTodaytamil(), "இன்று");
        adapter.addFragment(new FragmenteventTomorrowTamil(),"நாளை");
        adapter.addFragment(new FragPoojaTamil(),"பூஜை");
    mPager.setAdapter(adapter);
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
    private int typeid;
    private String name;
    private String image;
    private String bimage;
    private String pdate;
    private String description;
    private String title;
    private String publisher;
    private String company,count;
    /******** start the Food category names****/
    private String category_name;
    /******** start the Food category names****/
    private String id;

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
    public String getDescription(){return description;}
    public  void setDescription(String description){
        this.description=description;
    }
    public String getPdate(){return  pdate;}

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }
    public String getTitle(){return  title;}

    public void setTitle(String title) {
        this.title = title;
    }
    public String getPublisher(){return  publisher;}

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getCompany(){return company;}
    public void setCompany(String company){
        this.company=company;
    }
    /******** start the Food category names****/
    public String getCategory_name(){return  category_name;}

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
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


    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }
    }



}


class MYEventViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public MYEventViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


}


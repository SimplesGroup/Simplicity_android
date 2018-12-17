package simplicity_an.simplicity_an;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 7/8/2016.
 */
public class Enternainment extends Activity {
    RecyclerView recycler_radio,recycler_music,recycler_theatre;
    LinearLayoutManager mLayoutManager_radio,layoutManager_music,layoutManager_theatre;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    List<ItemModel> modelListsecond=new ArrayList<ItemModel>();
    List<ItemModel> modelListgovt=new ArrayList<ItemModel>();

    RecyclerViewAdapter rcadapradio;
    RecyclerViewAdaptermusic rcadap_music;
    RecyclerViewAdaptertheatre rcadap_theatre;
    private final String TAG_REQUEST = "MY_TAG";
    ImageButton homepage,latestnews,citycenter,entertainment,notification;
    int cDay,cMonth,cYear,cHour,cMinute,cSecond;
    String selectedMonth,selectedYear;
    ImageButton move_radio_page,move_music_page,move_theatre_page;
    String URL="http://simpli-city.in/request2.php?rtype=entertainment&key=simples";
    TextView title_coimbatore,date_current,titleradio,titlemusic,titletheatre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
              setContentView(R.layout.entertainment);
        Calendar calander = Calendar.getInstance();
        cDay = calander.get(Calendar.DAY_OF_MONTH);
        cMonth = calander.get(Calendar.MONTH) + 1;
        cYear = calander.get(Calendar.YEAR);
        selectedMonth = "" + cMonth;
        selectedYear = "" + cYear;
        cHour = calander.get(Calendar.HOUR);
        cMinute = calander.get(Calendar.MINUTE);
        cSecond = calander.get(Calendar.SECOND);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM");
        Date d1 = new Date();
        String sMonthName =  sdf1.format(d1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 1);
        int mon=calander.get(Calendar.DAY_OF_MONTH);
        Log.e("DAY_OF_MONTH: ", "DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH)+sMonthName);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
       title_coimbatore=(TextView)findViewById(R.id.title_coimbatore) ;
     date_current=(TextView)findViewById(R.id.weather) ;
        titleradio=(TextView)findViewById(R.id.title_radioshow) ;
        titlemusic=(TextView)findViewById(R.id.title_musicshow) ;
        titletheatre=(TextView)findViewById(R.id.title_theatreshow) ;

        title_coimbatore.setTypeface(tf);
      date_current.setTypeface(tf);
        titleradio.setTypeface(tf);
        titlemusic.setTypeface(tf);
        titletheatre.setTypeface(tf);

        title_coimbatore.setText("Coimbatore");
      date_current.setText(Html.fromHtml(dayOfTheWeek+","+""+sMonthName+""+calendar.get(Calendar.DAY_OF_MONTH)+""+"|"));
        titleradio.setText("Radio Shows");
        titlemusic.setText("Music");
        titletheatre.setText("Theatre");

        homepage=(ImageButton)findViewById(R.id.btn_3);
        latestnews=(ImageButton)findViewById(R.id.btn_1) ;
        citycenter=(ImageButton)findViewById(R.id.btn_2) ;
        entertainment=(ImageButton)findViewById(R.id.btn_4) ;
        notification=(ImageButton)findViewById(R.id.btn_5) ;
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main=new Intent(Enternainment.this, MainPageEnglish.class);
                startActivity(main);
                finish();
            }
        });
        citycenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent city=new Intent(Enternainment.this,CityCenter.class);
                startActivity(city);
                finish();*/
            }
        });
        latestnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent latestnewss=new Intent(Enternainment.this,LatestNews.class);
                startActivity(latestnewss);
                finish();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notification=new Intent(Enternainment.this, MainPageEnglish.class);
                startActivity(notification);
                finish();
            }
        });



        mLayoutManager_radio =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager_music = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager_theatre= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recycler_radio= (RecyclerView) findViewById(R.id.my_recycler_view_radio);
        recycler_music=(RecyclerView)findViewById(R.id.my_recycler_view_music);
        recycler_theatre=(RecyclerView)findViewById(R.id.my_recycler_view_theatre);


        recycler_radio.setLayoutManager(mLayoutManager_radio);
        recycler_music.setLayoutManager(layoutManager_music);
        recycler_theatre.setLayoutManager(layoutManager_theatre);

        move_radio_page=(ImageButton)findViewById(R.id.move_to_radio);
        move_music_page=(ImageButton)findViewById(R.id.move_to_music);
        move_theatre_page=(ImageButton)findViewById(R.id.move_to_theatre);

        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {

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
            rcadapradio = new RecyclerViewAdapter(this, modelList);
            rcadap_music=new RecyclerViewAdaptermusic(this,modelListsecond);
            rcadap_theatre=new RecyclerViewAdaptertheatre(this,modelListgovt);
            recycler_radio.setAdapter(rcadapradio);
            recycler_music.setAdapter(rcadap_music);
            recycler_theatre.setAdapter(rcadap_theatre);
            recycler_radio.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_radio, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    String bid = ((ItemModel) modelList.get(position)).getId();

                    Intent i = new Intent(getApplicationContext(), NewsDescription.class);
                    i.putExtra("ID", bid);

                    startActivity(i);
                }

                public void onLongClick(View view, int position) {

                }
            }));
            recycler_music.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_music, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    String bid = ((ItemModel) modelList.get(position)).getId();

                    Intent i = new Intent(getApplicationContext(), Articledescription.class);
                    i.putExtra("ID", bid);

                    startActivity(i);
                }

                public void onLongClick(View view, int position) {

                }
            }));
            recycler_theatre.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_theatre, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    String bid = ((ItemModel) modelList.get(position)).getId();

                    Intent i = new Intent(getApplicationContext(), GovernmentnotificationsDescriptions.class);
                    i.putExtra("ID", bid);

                    startActivity(i);
                }

                public void onLongClick(View view, int position) {

                }
            }));

            move_radio_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newspage = new Intent(Enternainment.this, RadioActivity.class);
                    startActivity(newspage);
                }
            });
            move_music_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent articlepage = new Intent(Enternainment.this, MusicActivity.class);
                    startActivity(articlepage);
                }
            });
            move_theatre_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent govtpage = new Intent(Enternainment.this, Theatre.class);
                    startActivity(govtpage);
                }
            });
        }


    }
    private void parseJsonFeed(JSONObject response) {
        try {
            //JSONArray feedArray = response.getJSONArray("result");
            String radioarray="radio";
            String musicarray="music";
            String theatrearray="theatre";

            if(radioarray.equals("radio")) {
                JSONArray feedArray = response.getJSONArray("radio");
                // JSONArray feedArraytwo = response.getJSONArray("article");

                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject obj = (JSONObject) feedArray.get(i);


                    ItemModel model = new ItemModel();
                    String image = obj.isNull("thumb") ? null : obj
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(obj.getString("pdate"));
                    model.setTitle(obj.getString("title"));
                    model.setQtype(obj.getString("qtype"));
                    model.setId(obj.getString("id"));
                    modelList.add(model);
                    // modelListsecond.add(model);
                }
            }
            if(musicarray.equals("music")) {
                JSONArray feedArraytwo = response.getJSONArray("music");

                for (int i = 0; i < feedArraytwo.length(); i++) {
                    JSONObject objt = (JSONObject) feedArraytwo.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListsecond.add(model);
                }
            }
            if(theatrearray.equals("theatre")) {
                JSONArray feedArraygovt = response.getJSONArray("theatre");

                for (int i = 0; i < feedArraygovt.length(); i++) {
                    JSONObject objt = (JSONObject) feedArraygovt.get(i);


                    ItemModel model = new ItemModel();
                    String image = objt.isNull("thumb") ? null : objt
                            .getString("thumb");
                    model.setImage(image);
                    model.setPdate(objt.getString("pdate"));
                    model.setTitle(objt.getString("title"));
                    model.setQtype(objt.getString("qtype"));
                    model.setId(objt.getString("id"));
                    // modelList.add(model);
                    modelListgovt.add(model);
                }
            }



            rcadapradio.notifyDataSetChanged();
            rcadap_music.notifyDataSetChanged();
            rcadap_theatre.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class ItemModel{
        private int typeid;
        private String name;
        private String image;
        private String pdate;
        private String description;
        private String title;
        private String qtype;
        private String id;



        public String getQtype() {
            return qtype;
        }

        public void setQtype(String qtype) {
            this.qtype = qtype;
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

        public String getImage() {
            return image;
        }
        public void setImage(String image) {
            this.image = image;
        }

        public String getPdate(){return  pdate;}
        public void setPdate(String pdate) {
            this.pdate = pdate;
        }
        public String getTitle(){return  title;}

        public void setTitle(String title) {
            this.title = title;
        }
        public String getId(){return  id;}

        public void setId(String id) {
            this.id = id;
        }


    }
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelList=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapter(Context context, List<ItemModel> modelList) {
            this.modelList = modelList;
            this.context = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_entertainment, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = MySingleton.getInstance(Enternainment.this).getImageLoader();
            ItemModel itemmodel=modelList.get(position);
            String types=itemmodel.getQtype();
            // if(types.equals("news")) {
            holder.titles.setText(itemmodel.getTitle());
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
            return modelList.size();
        }
        public class RecyclerViewHolders extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHolders(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory_entertainment);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }
    public class RecyclerViewAdaptermusic extends RecyclerView.Adapter<RecyclerViewAdaptermusic.RecyclerViewHoldersArticles> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelListsecond=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdaptermusic(Context context, List<ItemModel> modelListsecond) {
            this. modelListsecond =  modelListsecond;
            this.context = context;
        }

        @Override
        public RecyclerViewHoldersArticles onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_entertainment, null);
            RecyclerViewHoldersArticles rcv = new RecyclerViewHoldersArticles(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHoldersArticles holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = MySingleton.getInstance(Enternainment.this).getImageLoader();
            ItemModel itemmodel= modelListsecond.get(position);
            String types=itemmodel.getQtype();
            // holder.titles.setText("atricle news ");
            //if(types.equals("article")) {
            holder.titles.setText(itemmodel.getTitle());
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
            return  modelListsecond.size();
        }
        public class RecyclerViewHoldersArticles extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHoldersArticles(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory_entertainment);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }
    public class RecyclerViewAdaptertheatre extends RecyclerView.Adapter<RecyclerViewAdaptertheatre.RecyclerViewHoldersgovts> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> modelListgovt=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdaptertheatre(Context context, List<ItemModel> modelListgovt) {
            this.modelListgovt = modelListgovt;
            this.context = context;
        }

        @Override
        public RecyclerViewHoldersgovts onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_entertainment, null);
            RecyclerViewHoldersgovts rcv = new RecyclerViewHoldersgovts(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHoldersgovts holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = MySingleton.getInstance(Enternainment.this).getImageLoader();
            ItemModel itemmodel=modelListgovt.get(position);
            String types=itemmodel.getQtype();
            //  if(types.equals("govt")) {
            holder.titles.setText(itemmodel.getTitle());
            holder.profileimage.setDefaultImageResId(R.mipmap.ic_launcher);
            holder.profileimage.setErrorImageResId(R.drawable.iconlogo);
            holder.profileimage.setAdjustViewBounds(true);
            holder.profileimage.setImageUrl(itemmodel.getImage(), mImageLoader);
            /*}else {
                holder.profileimage.setVisibility(View.GONE);
                holder.titles.setVisibility(View.GONE);
            }*/
        }

        @Override
        public int getItemCount() {
            return modelListgovt.size();
        }
        public class RecyclerViewHoldersgovts extends RecyclerView.ViewHolder{
            public TextView titles;
            public NetworkImageView profileimage;


            public RecyclerViewHoldersgovts(View itemView) {
                super(itemView);
                profileimage = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory_entertainment);
                titles = (TextView) itemView.findViewById(R.id.name);


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

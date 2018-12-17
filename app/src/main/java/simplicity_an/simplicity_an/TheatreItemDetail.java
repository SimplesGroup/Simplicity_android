package simplicity_an.simplicity_an;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
 * Created by kuppusamy on 5/3/2016.
 */
public class TheatreItemDetail extends AppCompatActivity {
    ImageButton play;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    List<ItemModel> castimagelist=new ArrayList<ItemModel>();
     List<ItemModel> galleryimagelist=new ArrayList<ItemModel>();
     String URL="http://simpli-city.in/request2.php?rtype=theatre&key=simples&tid=";
    String Urlid;
    String TAG = "simplicity_an.simplicity_an";

    Typeface tf;
    private final String TAG_REQUEST = "MY_TAG";

    RequestQueue queues,requestQueue;
  RecyclerViewAdapter rcAdapter;
    RecyclerViewAdapterGallery rcAdaptergallery;
    String bimage;
    RecyclerView recyclerView,recyclergallery;
    String link_video,myprofileid,URLTWO,notifiid;
    String URLFAV="http://simpli-city.in/request2.php?rtype=addfav&key=simples";
    LinearLayoutManager mLayoutManager,gallerylayoutmanager;
    TextView title_movie,title_desc,duration_time,description_movie,views,casttheatre;
    TextView director,screenplay,story,actor,cinematographer,editor;
    NetworkImageView movie_image;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    public static final String LINK = "link";
    String contentid,activity,link;
    ProgressDialog pdialog;
    ImageButton back,menu,favourite,comment,notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.theatre_item_detail);
        play=(ImageButton)findViewById(R.id.play_buttonss);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
            contentid=sharedpreferences.getString(CONTENTID,"");
            activity=sharedpreferences.getString(Activity,"");
            link=sharedpreferences.getString(LINK,"");
        }
        title_movie=(TextView)findViewById(R.id.title_movie);
        title_desc=(TextView)findViewById(R.id.title_description);
requestQueue=Volley.newRequestQueue(this);
        comment=(ImageButton)findViewById(R.id.btn_4);
        notification=(ImageButton)findViewById(R.id.btn_5);
        menu=(ImageButton)findViewById(R.id.btn_3);
        back=(ImageButton)findViewById(R.id.btn_1);
        favourite=(ImageButton)findViewById(R.id.btn_2);
        casttheatre=(TextView)findViewById(R.id.cast_theatre_movie);

        director=(TextView)findViewById(R.id.theatre_director_name);
        screenplay=(TextView)findViewById(R.id.theatre_storyandscreenplay_name);
        story=(TextView)findViewById(R.id.theatre_storywritter_name);
        actor=(TextView)findViewById(R.id.theatre_actor_name);
        cinematographer=(TextView)findViewById(R.id.theatre_cinematographer_name);
        editor=(TextView)findViewById(R.id.theatre_editor_name);



       // Intent get=getIntent();

       // notifiid=get.getStringExtra("ID");
        if(activity==null){
            Intent getnotifi=getIntent();
            notifiid=getnotifi.getStringExtra("ID");
            link_video=getnotifi.getStringExtra("LINK");
            notifiid = notifiid.replaceAll("\\D+","");
            Log.e("ID",notifiid);

        } else {
            if(activity.equalsIgnoreCase("theatre")){
                notifiid=contentid;
                link_video=link;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(Activity);
                editor.remove(CONTENTID);
                editor.remove(LINK);
                editor.apply();

            }else {
                Intent getnotifi=getIntent();
                notifiid=getnotifi.getStringExtra("ID");
                notifiid = notifiid.replaceAll("\\D+","");
                link_video=getnotifi.getStringExtra("LINK");
                Log.e("ID",notifiid);
            }
        }
        if(myprofileid!=null){
            URLTWO=URL+notifiid+"&user_id="+myprofileid;
        }else {
            URLTWO=URL+notifiid;
        }
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        director.setTypeface(tf);
        screenplay.setTypeface(tf);
        story.setTypeface(tf);
        actor.setTypeface(tf);
        cinematographer.setTypeface(tf);
        editor.setTypeface(tf);
        queues = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView  = (RecyclerView)findViewById(R.id.cast_recycler_theatre);
     // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        gallerylayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclergallery = (RecyclerView)findViewById(R.id.gallery_recycler_theatre);
        recyclergallery.setLayoutManager(gallerylayoutmanager);
        recyclergallery.setHasFixedSize(true);
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
        //  AppControllers.getInstance().addToRequestQueue(jsonreq);
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // AppControllers.getInstance().addToRequestQueue(jsonreq);
        requestQueue.add(jsonreq);
        rcAdapter = new RecyclerViewAdapter(this, modelList);
        recyclerView.setAdapter(rcAdapter);
        rcAdaptergallery = new RecyclerViewAdapterGallery(this, galleryimagelist);
        recyclergallery.setAdapter(rcAdaptergallery);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent backeventdesc = new Intent(getApplicationContext(), Events.class);
                startActivity(backeventdesc);
                finish();*/
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menueventdesc = new Intent(getApplicationContext(), MainPageEnglish.class);
                startActivity(menueventdesc);
                finish();
            }
        });
favourite.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(myprofileid!=null) {
            Intent notify=new Intent(TheatreItemDetail.this,NotificationSettingsactivity.class);
            startActivity(notify);
            finish();
        }else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Activity, "theatre");
            editor.putString(CONTENTID, notifiid);
            editor.commit();
            Intent signin=new Intent(TheatreItemDetail.this,SigninpageActivity.class);
            startActivity(signin);
            finish();
        }
    }
});
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null) {
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    MyDialogFragment frag;
                    frag = new MyDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("POSTID", notifiid);
                    args.putString("USERID", myprofileid);
                    frag.setArguments(args);
                    frag.show(ft, "txn_tag");
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "theatre");
                    editor.putString(CONTENTID, notifiid);
                    editor.commit();
                    Intent signin=new Intent(TheatreItemDetail.this,SigninpageActivity.class);
                    startActivity(signin);
                    finish();
                }
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null) {
                    Intent notify=new Intent(TheatreItemDetail.this,NotificationSettingsactivity.class);
                    startActivity(notify);
                    finish();
                }else {
                    Intent signin=new Intent(TheatreItemDetail.this,SigninpageActivity.class);
                    startActivity(signin);
                    finish();
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Videoplaycall();
            }
        });
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
   /* public void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
        }
    }*/
    private void parseJsonFeed(JSONObject response) {
        ImageLoader mImageLoader= MySingleton.getInstance(TheatreItemDetail.this).getImageLoader();
        try {
           JSONArray feedArray = response.getJSONArray("general");

          for (int j = 0; j < feedArray.length(); j++) {
            JSONObject obj = (JSONObject) feedArray.get(j);
              ItemModel model = new ItemModel();


                //FeedItem model=new FeedItem();
                 String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
                model.setImage(image);

              model.setName(obj.getString("director"));
              model.setDescription(obj.getString("description"));
              model.setActor(obj.getString("actor"));
              model.setCinematographer(obj.getString("cg"));
              model.setScreenplay(obj.getString("story"));
              model.setStorywriter(obj.getString("writers"));
              model.setEditor(obj.getString("editor"));
              model.setPdate(obj.getString("pdate"));
              model.setTitle(obj.getString("title"));

              model.setSource(obj.getString("time"));
              model.setSource_link(obj.getString("link"));

              title_movie.setText(obj.getString("title"));
              title_desc.setText(obj.getString("description"));
              link_video=obj.getString("link");
              director.setText("Director :"+obj.getString("director"));
              screenplay.setText("Story & Screenplay :"+obj.getString("story"));
              story.setText("Writer :"+obj.getString("writers"));
              cinematographer.setText("Actor :"+obj.getString("cg"));
              actor.setText("Cinematographer :"+obj.getString("actor"));
              editor.setText("Editor :"+obj.getString("editor"));


            castimagelist.add(model);
            }

                    JSONArray feedArraydoit = response.getJSONArray("casting");
if(feedArraydoit!=null) {
    recyclerView.setVisibility(View.VISIBLE);
    for (int i = 0; i < feedArraydoit.length(); i++) {
        JSONObject objt = (JSONObject) feedArraydoit.get(i);

        ItemModel modelone = new ItemModel();


        String images = objt.isNull("image") ? null : objt
                .getString("image");
        modelone.setCas_image(images);
        modelList.add(modelone);
    }
}else {
    recyclerView.setVisibility(View.GONE);
}
            JSONArray feedArraygallery = response.getJSONArray("gallery");
if(feedArraygallery!=null) {
    recyclergallery.setVisibility(View.VISIBLE);
    for (int k = 0; k < feedArraygallery.length(); k++) {
        JSONObject objt = (JSONObject) feedArraygallery.get(k);

        ItemModel modelgalllery = new ItemModel();


        String images = objt.isNull("image") ? null : objt
                .getString("image");
        modelgalllery.setGalleryimage(images);
        galleryimagelist.add(modelgalllery);
    }

}else {
    recyclergallery.setVisibility(View.GONE);
}
            // notify data changes to list adapater
            rcAdapter.notifyDataSetChanged();
            rcAdaptergallery.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Videoplaycall(){
        if(link_video!=null) {
            dissmissDialog();
            final VideoView videoView =
                    (VideoView) findViewById(R.id.video_view);

            videoView.setVideoPath(link_video);
            MediaController mediaController = new
                    MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.setOnPreparedListener(new
                                                    MediaPlayer.OnPreparedListener() {
                                                        @Override
                                                        public void onPrepared(MediaPlayer mp) {
                                                            Log.i(TAG, "Duration = " +
                                                                    videoView.getDuration());
                                                        }
                                                    });
            play.setVisibility(View.GONE);
            videoView.start();
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
        private String source;
        private String source_link;
        private String cas_image;
        private String screenplay;
        private String storywriter;
        private String cinematographer;
        private String editor;
        private String actor;



        private String galleryimage;


        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getCinematographer() {
            return cinematographer;
        }

        public void setCinematographer(String cinematographer) {
            this.cinematographer = cinematographer;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getScreenplay() {
            return screenplay;
        }

        public void setScreenplay(String screenplay) {
            this.screenplay = screenplay;
        }

        public String getStorywriter() {
            return storywriter;
        }

        public void setStorywriter(String storywriter) {
            this.storywriter = storywriter;
        }

        public String getCas_image() {
            return cas_image;
        }

        public void setCas_image(String cas_image) {
            this.cas_image = cas_image;
        }


        public void setGalleryimage(String galleryimage) {
            this.galleryimage = galleryimage;
        }

        public String getGalleryimage() {
            return galleryimage;
        }

        /******** start the Food category names****/


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

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
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

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_itemtheatre_detail, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = MySingleton.getInstance(TheatreItemDetail.this).getImageLoader();
            ItemModel itemmodel=modelList.get(position);
         holder.profileimage.setDefaultImageResId(R.drawable.ic_launcher);
            holder.profileimage.setErrorImageResId(R.drawable.ic_launcher);
            if(itemmodel.getCas_image()==null){
                holder.profileimage.setVisibility(View.GONE);
            }else {
                holder.profileimage.setImageUrl(itemmodel.getCas_image(),mImageLoader);

            }

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
                profileimage = (NetworkImageView) itemView.findViewById(R.id.cast_image);
                titles = (TextView) itemView.findViewById(R.id.name);


            }
        }
    }
    public class RecyclerViewAdapterGallery extends RecyclerView.Adapter<RecyclerViewAdapterGallery.RecyclerViewHoldersgallery> {
        private LayoutInflater inflater;
        ImageLoader mImageLoader;
        private List<ItemModel> galleryimagelist=new ArrayList<ItemModel>();
        private Context context;

        public RecyclerViewAdapterGallery(Context context, List<ItemModel> galleryimagelist) {
            this.galleryimagelist = galleryimagelist;
            this.context = context;
        }

        @Override
        public RecyclerViewHoldersgallery onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_itemtheatre_detail, null);
            RecyclerViewHoldersgallery rcv = new RecyclerViewHoldersgallery(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHoldersgallery holder, int position) {

            //if (mImageLoader == null)
            mImageLoader = MySingleton.getInstance(TheatreItemDetail.this).getImageLoader();
            ItemModel itemmodel=galleryimagelist.get(position);
            holder.galleryimage.setDefaultImageResId(R.drawable.ic_launcher);
            holder.galleryimage.setErrorImageResId(R.drawable.ic_launcher);
            if(itemmodel.getGalleryimage()==null){
                holder.galleryimage.setVisibility(View.GONE);
            }
            holder.galleryimage.setImageUrl(itemmodel.getGalleryimage(),mImageLoader);

        }

        @Override
        public int getItemCount() {
            return galleryimagelist.size();
        }
        public class RecyclerViewHoldersgallery extends RecyclerView.ViewHolder{

            public NetworkImageView galleryimage;


            public RecyclerViewHoldersgallery(View itemView) {
                super(itemView);
                galleryimage = (NetworkImageView) itemView.findViewById(R.id.cast_image);



            }
        }
    }



    public static class MyDialogFragment extends DialogFragment {
        private String KEY_COMMENT = "comment";
        private String KEY_TYPE = "qtype";
        private String KEY_MYUID = "user_id";
        private String KEY_POSTID = "id";
        String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=theatre&id=";
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
            View root = inflater.inflate(R.layout.commenttheatrefragment, container, false);
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
                                    params.put(KEY_TYPE, "theatre");
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

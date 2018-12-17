package simplicity_an.simplicity_an;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

/**
 * Created by kuppusamy on 7/5/2016.
 */
public class Myprofilepost extends Fragment {
    private static final int MAX_LINES =2;
    TextView mResizableTextView;
    LinearLayoutManager lLayout;
    List<ItemModel> modelList=new ArrayList<ItemModel>();

    String URL="http://simpli-city.in/request2.php?rtype=citycenter_user&key=simples&user_id=";
    RequestQueue queue;
    ProgressDialog pdialog;
    RecyclerViewAdapter rcAdapter;
    RecyclerView recycler;

    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    protected Handler handler;
    int myuserinteger;
    String Urltwo,URLTHREE;

    TextView profilename_page,profileaddress,profilemypost;
    Context context;
    ImageView profileimage_page;
    Button addfriend,friedslist;
    String userid="";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    private final String TAG_REQUEST = "MY_TAG";
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    private String KEY_MYUID = "usrid";
    private String KEY_POSTID = "qid";
    private String KEY_MYID = "uid";
    private String KEY_FRIENDID= "fid";
    private String KEY_DELETEID= "cid";

    String myprofileid;
    String UPLOAD_URLLIKES="http://simpli-city.in/request2.php?rtype=citycenterlikes&key=simples";
    String UPLOAD_URLADDFRIENDS="http://simpli-city.in/request2.php?rtype=addfriend&key=simples";
    String UPLOAD_URLDELETEMYPOST="http://simpli-city.in/request2.php?rtype=deletepost&key=simples";
    String UPLOAD_URLSHAREPOST="http://simpli-city.in/request2.php?rtype=sharepost&key=simples";
    String UPLOAD_URLREPORTPOST="http://simpli-city.in/request2.php?rtype=reportabuse&key=simples";
    String ProfileImagetopost;
    int userids;
    int countsun,resultun;
    int post_likes_count,friend_list_count;

    String friendid;
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid,activity;
    public Myprofilepost(){

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myprofilepost, container, false);
        sharedpreferences =  getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            contentid=sharedpreferences.getString(CONTENTID,"");
            activity=sharedpreferences.getString(Activity,"");
        }
        requestQueue = Volley.newRequestQueue(getActivity());
        getData();
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
        lLayout = new LinearLayoutManager(getActivity());
        recycler = (RecyclerView)view.findViewById(R.id.recycler_view_myprofilepost_id);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setLayoutManager(lLayout);

        rcAdapter = new RecyclerViewAdapter(modelList,recycler);
        recycler.setAdapter(rcAdapter);
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

        myprofileid = myprofileid.replaceAll("\\D+","");

        myuserinteger = Integer.parseInt(myprofileid);
           // URLTHREE=URL+myprofileid+"&page="+requestCount;
URLTHREE=URL+myprofileid+"&page="+requestCount;

Log.e("myprofileurl",URLTHREE);




        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLTHREE);
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

            jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLTHREE,  new Response.Listener<JSONObject>() {

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
           // AppControllers.getInstance().addToRequestQueue(jsonReq);
            jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //AppControllers.getInstance().addToRequestQueue(jsonReq);
            requestQueue.add(jsonReq);
        }
        return jsonReq;
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            feedArray = response.getJSONArray("post");

            // if (feedArray.length() < 10) {
            for (ii = 0; ii < feedArray.length(); ii++) {
                obj = (JSONObject) feedArray.get(ii);

                ItemModel model = new ItemModel();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);
                model.setDescription(obj.getString("description"));
                model.setId(obj.getString("id"));
                model.setPdate(obj.getString("pdate"));
                model.setTitle(obj.getString("uname"));
                String profileimage = obj.isNull("pic") ? null : obj
                        .getString("pic");
                model.setProfilepic(profileimage);
                model.setUid(obj.getString("uid"));
                int useridinteger_city = obj.isNull("uid") ? null : obj
                        .getInt("uid");
                model.setUserids(useridinteger_city);
                String commentcount_city = obj.isNull("comment_count") ? null : obj
                        .getString("comment_count");
                model.setCommentcount(commentcount_city);
                int likecount_city = obj.isNull("like_count") ? null : obj
                        .getInt("like_count");
                model.setLikecount(likecount_city);
                int mylike_citys = obj.isNull("like") ? null : obj
                        .getInt("like");
                model.setPostmylikes(mylike_citys);
                int myfriedornot_city = obj.isNull("friend") ? null : obj
                        .getInt("friend");
                model.setCheckmyfriends(myfriedornot_city);
                String taggedusers = obj.isNull("taggedusers") ? null : obj
                        .getString("taggedusers");
                model.setTaggedusers(taggedusers);
                String totaltaggedusers = obj.isNull("total_tagged") ? null : obj
                        .getString("total_tagged");
                model.setTotaltaggedusers(totaltaggedusers);
                modelList.add(model);

                // }

                // notify data changes to list adapater


            }
            rcAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    class ItemModel{
        private int typeid;
        private String name;
        private String image;
        private String profilepic;
        private String pdate;
        private String description;
        private String title;
        private int likecount;
        private String commentcount;
        /******** start the Food category names****/
        private String id;
        /******** start the Food category names****/
        private String uid;
        private int postmylikes;
        private  int  checkmyfriends;

        int userids;
        private String friendlist;
        String likescounts;
        String taggedusers;
        String totaltaggedusers;


        public String getTotaltaggedusers() {
            return totaltaggedusers;
        }

        public void setTotaltaggedusers(String totaltaggedusers) {
            this.totaltaggedusers = totaltaggedusers;
        }

        public String getTaggedusers() {
            return taggedusers;
        }

        public void setTaggedusers(String taggedusers) {
            this.taggedusers = taggedusers;
        }


        public String getLikescounts() {
            return likescounts;
        }

        public void setLikescounts(String likescounts) {
            this.likescounts = likescounts;
        }


        public String getFriendlist() {
            return friendlist;
        }

        public void setFriendlist(String friendlist) {
            this.friendlist = friendlist;
        }

        public int getUserids() {
            return userids;
        }

        public void setUserids(int userids) {
            this.userids = userids;
        }

        public int getCheckmyfriends() {
            return checkmyfriends;
        }

        public void setCheckmyfriends(int checkmyfriends) {
            this.checkmyfriends = checkmyfriends;
        }

        public int getPostmylikes() {
            return postmylikes;
        }

        public void setPostmylikes(int postmylikes) {
            this.postmylikes = postmylikes;
        }

        public String getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(String commentcount) {
            this.commentcount = commentcount;
        }

        public int getLikecount() {
            return likecount;
        }

        public void setLikecount(int likecount) {
            this.likecount = likecount;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        /******** start the Food category names****/
        public String getId(){return  id;}

        public void setId(String id) {
            this.id = id;
        }

        public String getProfilepic() {
            return profilepic;
        }

        public void setProfilepic(String profilepic) {
            this.profilepic = profilepic;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name,timestamp,statusMsg,url,taggedusernames,readmoretext;
        NetworkImageView profilePic;
        public TextView profile_name,description_image,date_cityposted,comment_counts,hearts_likes_counts;

        public ImageButton commentsbut,heartbuttton_like,drop_promote,addfriend,sharepost,delete_my_post;

        FeedImageView feedImageView;
        public UserViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            timestamp = (TextView) itemView
                    .findViewById(R.id.timestamp);
            statusMsg = (TextView) itemView
                    .findViewById(R.id.txtStatusMsg);
            readmoretext = (TextView) itemView
                    .findViewById(R.id.description_readmore);
            profilePic = (NetworkImageView) itemView
                    .findViewById(R.id.profilePic);
            feedImageView = (FeedImageView) itemView
                    .findViewById(R.id.feedImage1);
            commentsbut = (ImageButton) itemView.findViewById(R.id.comment_button);
            heartbuttton_like = (ImageButton) itemView.findViewById(R.id.citycenter_main_heartlike);
            drop_promote = (ImageButton) itemView.findViewById(R.id.imageView_promote);
            taggedusernames=(TextView) itemView.findViewById(R.id.tagged_names);

            comment_counts= (TextView) itemView.findViewById(R.id.comments_count);
            hearts_likes_counts = (TextView) itemView.findViewById(R.id.heartslike_count);
            sharepost = (ImageButton) itemView.findViewById(R.id.city_sharepost);
            delete_my_post = (ImageButton) itemView.findViewById(R.id.city_deletepost);



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

            if(viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_citycenter, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;

        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof UserViewHolder) {

                final UserViewHolder userViewHolder = (UserViewHolder) holder;
                final ItemModel itemmodel = modelList.get(position);
                if (mImageLoader == null)
                    mImageLoader = AppControllers.getInstance().getImageLoader();

                userViewHolder. name.setText(itemmodel.getTitle());

                // Converting timestamp into x ago format
               /* CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(itemmodel.getPdate()),
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);*/
                userViewHolder.timestamp.setText(itemmodel.getPdate());

                // Chcek for empty status message

                if(itemmodel.getDescription().equalsIgnoreCase("")) {
                    userViewHolder.statusMsg.setVisibility(View.GONE);
                }else {
                    //  userViewHolder.statusMsg.setText(itemmodel.getDescription());
                    //  ResizableCustomView.doResizeTextView(userViewHolder.statusMsg, MAX_LINES, "View More", true);
                    userViewHolder.statusMsg.setMaxLines(3);
                    userViewHolder.statusMsg.setEllipsize(TextUtils.TruncateAt.END);
                    userViewHolder.statusMsg.setText(itemmodel.getDescription());
                    userViewHolder.statusMsg.post(new Runnable() {
                        @Override
                        public void run() {
                            int lineCounts = userViewHolder.statusMsg.getLineCount();
                            // Use lineCount here
                            //idss=String.valueOf(lineCounts);

                            //Log.e("NEWL:",idss);
                            if(lineCounts==2) {
                                userViewHolder.readmoretext.setText("Read more");
                                userViewHolder.readmoretext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!userViewHolder.readmoretext.getText().toString().trim().equalsIgnoreCase("Read more")){
                                            System.out.println("Hide button");
                                            // hide.setVisibility(View.INVISIBLE);
                                            // show.setVisibility(View.VISIBLE);
                                            userViewHolder.statusMsg.setMaxLines(3);
                                            userViewHolder.readmoretext.setText("Read more");
                                        }else {

                                            //show.setVisibility(View.INVISIBLE);
                                            //hide.setVisibility(View.VISIBLE);
                                            userViewHolder.statusMsg.setMaxLines(Integer.MAX_VALUE);
                                            userViewHolder.readmoretext.setText("Read less");
                                        }
                                    }
                                });

                                    /*show.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            System.out.println("Show button");
                                            show.setVisibility(View.INVISIBLE);
                                            hide.setVisibility(View.VISIBLE);
                                            descText.setMaxLines(Integer.MAX_VALUE);
                                            readmore.setText("read less");
                                        }
                                    });
                                    // hide = (ImageButton) findViewById(R.id.hide);
                                    hide.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            System.out.println("Hide button");
                                            hide.setVisibility(View.INVISIBLE);
                                            show.setVisibility(View.VISIBLE);
                                            descText.setMaxLines(2);
                                            readmore.setText("read more");
                                        }
                                    });*/

                            }else {
                                // show.setVisibility(View.GONE);
                                //hide.setVisibility(View.GONE);
                                userViewHolder.readmoretext.setVisibility(View.GONE);
                            }
                        }
                    });
                }

                if(itemmodel.getTaggedusers()!=null) {
                    userViewHolder.taggedusernames.setText(itemmodel.getTaggedusers());

                }else {
                    userViewHolder.taggedusernames.setVisibility(View.GONE);
                }

                // Checking for null feed url
               /* if (item.getUrl() != null) {
                    url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                            + item.getUrl() + "</a> "));

                    // Making url clickable
                    url.setMovementMethod(LinkMovementMethod.getInstance());
                    url.setVisibility(View.VISIBLE);
                } else {
                    // url is null, remove from the view
                    url.setVisibility(View.GONE);
                }
*/
                // user profile pic
                userViewHolder. profilePic.setImageUrl(itemmodel.getProfilepic(), mImageLoader);

                // Feed image
                if (itemmodel.getImage().equals("")) {
                    userViewHolder.feedImageView.setVisibility(View.GONE);

                } else {
                    userViewHolder.feedImageView.setImageUrl(itemmodel.getImage(), mImageLoader);
                    userViewHolder.feedImageView.setVisibility(View.VISIBLE);
                    userViewHolder.feedImageView
                            .setResponseObserver(new FeedImageView.ResponseObserver() {
                                @Override
                                public void onError() {
                                }

                                @Override
                                public void onSuccess() {
                                }
                            });
                }

               /* userViewHolder.  feedImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("IMAGE:",itemmodel.getImage());


                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        MyDialogFragment frag;
                        frag = new MyDialogFragment();
                        Bundle args = new Bundle();
                        args.putString("Image",itemmodel.getImage());
                        frag.setArguments(args);
                        frag.show(ft, "txn_tag");
                    }
                });*/
                post_likes_count=itemmodel.getPostmylikes();
                if(itemmodel.getPostmylikes()==1){
                    userViewHolder.heartbuttton_like.setImageResource(R.drawable.heartcityred);
                    userViewHolder.hearts_likes_counts.setText(itemmodel.getLikecount()+"likes");
                }else {
                    userViewHolder.heartbuttton_like.setImageResource(R.drawable.heartcity);
                    userViewHolder.hearts_likes_counts.setText(itemmodel.getLikecount() + "likes");
                }
userViewHolder.hearts_likes_counts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        MyLIKEFragment frag;
        frag = new MyLIKEFragment();
        Bundle args = new Bundle();
        args.putString("ID", itemmodel.getId());
        frag.setArguments(args);
        frag.show(ft, "txn_tag");
    }
});
                userViewHolder.heartbuttton_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(myprofileid!=null) {
                            if (post_likes_count == 1) {
                                userViewHolder.heartbuttton_like.setImageResource(R.drawable.heartcity);
                                post_likes_count--;
                                String f = itemmodel.getLikescounts().toString();

                                int i = Integer.parseInt(f);
                                String s = "1";
                                int j = Integer.parseInt(s);

                                Integer result = i - j;
                                String res = result.toString();
                                if (result > 0) {
                                    userViewHolder.hearts_likes_counts.setText(res + "likes");
                                } else {
                                    userViewHolder.hearts_likes_counts.setText(itemmodel.getLikescounts() + "likes");
                                }
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URLLIKES,
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
                                        String postid = itemmodel.getId();
                                        //Adding parameters
                                        if (postid != null) {


                                            params.put(KEY_POSTID, postid);
                                            params.put(KEY_MYUID, myprofileid);
                                        } else {


                                        }


                                        return params;
                                    }
                                };

                                //Creating a Request Queue
                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                //Adding request to the queue
                                requestQueue.add(stringRequest);

                            } else {
                                userViewHolder.heartbuttton_like.setImageResource(R.drawable.heartcityred);
                                post_likes_count++;


                                String f = itemmodel.getLikescounts().toString();
                                int i = Integer.parseInt(f);
                                String s = "1";
                                int j = Integer.parseInt(s);

                                Integer result = i + j;
                                String res = result.toString();
                                userViewHolder.hearts_likes_counts.setText(res + "likes");
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URLLIKES,
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
                                        String postid = itemmodel.getId();
                                        //Adding parameters
                                        if (postid != null) {


                                            params.put(KEY_POSTID, postid);
                                            params.put(KEY_MYUID, myprofileid);
                                        } else {


                                        }


                                        return params;
                                    }
                                };

                                //Creating a Request Queue
                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                //Adding request to the queue
                                requestQueue.add(stringRequest);
                                //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();
                            }


                        }else {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Activity, "notification");
                            editor.putString(CONTENTID, "0");
                            editor.commit();
                            Intent sign=new Intent(getActivity(),SigninpageActivity.class);
                            startActivity(sign);
                        }
                    }
                });

                userViewHolder.drop_promote.setVisibility(View.VISIBLE);
                userViewHolder.commentsbut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (myprofileid != null) {
                                /*Intent commentpage = new Intent(getActivity(), CityCenterCommentPage.class);
                                commentpage.putExtra("ID", itemmodel.getId());
                               // commentpage.putExtra("ACTIVITY", "citycenter");
                                startActivity(commentpage);*/
                            } else {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Activity, "notification");
                                editor.putString(CONTENTID, "0");
                                editor.commit();
                                Intent sign=new Intent(getActivity(),SigninpageActivity.class);
                                startActivity(sign);
                            }

                        } catch (Exception e) {

                        }


                    }
                });



                friend_list_count=itemmodel.getCheckmyfriends();



               /* if(itemmodel.getUserids()==myuserinteger) {
                    userViewHolder.addfriend.setVisibility(View.GONE);
                }

                userViewHolder.addfriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(friend_list_count==1){
                            userViewHolder.addfriend.setImageResource(R.drawable.handaddfriend);
                            friend_list_count--;


                            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URLADDFRIENDS,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {
                                            //Disimissing the progress dialog

                                            //Showing toast message of the response
                                            if (s.equalsIgnoreCase("no")) {
                                                //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                            } else {
                                                Log.e("response:",s);

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
                                    String friendid = itemmodel.getUid();
                                    //Adding parameters
                                    if (friendid != null) {


                                        params.put(KEY_FRIENDID, friendid);
                                        params.put(KEY_MYID, myprofileid);
                                    } else {


                                    }


                                    return params;
                                }
                            };

                            //Creating a Request Queue
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                            //Adding request to the queue
                            requestQueue.add(stringRequest);

                        }else {
                            userViewHolder.addfriend.setImageResource(R.drawable.greyhandfriends);
                            friend_list_count++;

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URLADDFRIENDS,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {
                                            //Disimissing the progress dialog

                                            //Showing toast message of the response
                                            if (s.equalsIgnoreCase("no")) {
                                                //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                            } else {
                                                Log.e("response:",s);

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
                                    String friendid = itemmodel.getUid();
                                    //Adding parameters
                                    if (friendid != null) {


                                        params.put(KEY_FRIENDID, friendid);
                                        params.put(KEY_MYID, myprofileid);
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

                    }
                });*/
                if(myprofileid!=null){
                    if(itemmodel.getUserids()==myuserinteger){
                        userViewHolder.delete_my_post.setVisibility(View.VISIBLE);
                        userViewHolder.delete_my_post.setImageResource(R.drawable.deletepost);
                    }else {
                        userViewHolder.delete_my_post.setVisibility(View.GONE);
                    }
                }else {
                    userViewHolder.delete_my_post.setVisibility(View.GONE);
                }

                userViewHolder.delete_my_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                        // Setting Dialog Title
                        alertDialog.setTitle("Delete Post");

                        // Setting Dialog Message
                        alertDialog.setMessage("Are you sure you want delete this POST?");

                        // Setting Icon to Dialog

                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke NO event

                                dialog.cancel();
                            }
                        });
                        // Setting Positive "Yes" Button
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URLDELETEMYPOST,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                //Disimissing the progress dialog

                                                //Showing toast message of the response
                                                if (s.equalsIgnoreCase("no")) {
                                                    //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                } else {
                                                    Log.e("response:",s);
                                                    Toast.makeText(getActivity(),"Your post Successfully deleted", Toast.LENGTH_LONG).show();
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
                                        String deletepostid = itemmodel.getId();
                                        //Adding parameters
                                        if (deletepostid != null) {


                                            params.put(KEY_DELETEID, deletepostid);

                                        } else {


                                        }


                                        return params;
                                    }
                                };

                                //Creating a Request Queue
                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                //Adding request to the queue
                                requestQueue.add(stringRequest);
                                //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();

                            }
                        });

                        // Setting Negative "NO" Button


                        // Showing Alert Message
                        alertDialog.show();



                    }
                });

                userViewHolder.sharepost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URLSHAREPOST,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        //Disimissing the progress dialog

                                        //Showing toast message of the response
                                        if (s.equalsIgnoreCase("no")) {

                                        } else {
                                            Log.e("response:",s);
                                            Toast.makeText(getActivity(), "Success  fully shared", Toast.LENGTH_LONG).show() ;
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
                                String share_postid = itemmodel.getUid();
                                //Adding parameters
                                if (share_postid != null) {


                                    params.put(KEY_DELETEID, share_postid);
                                    params.put(KEY_MYID, myprofileid);
                                } else {


                                }


                                return params;
                            }
                        };

                        //Creating a Request Queue
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                        //Adding request to the queue
                        requestQueue.add(stringRequest);
                        //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();

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



        /*public int getItemViewType(int position) {
            if (isPositionHeader(position)) {
                return VIEW_TYPE_FEATURE;
            } else if (isPositionFooter(position)) {
                return VIEW_TYPE_LOADING;
            }
            return VIEW_TYPE_ITEM;
        }*/
        public int getItemViewType(int position) {


            return modelList.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;

        }

        public void setLoaded() {
            loading = false;
        }

        public int getItemCount() {
            return modelList.size();
        }

    }
    public static class MyDialogFragment extends DialogFragment {
        NetworkImageView post_view_image_big;
        ImageLoader mImageLoader;
        ImageButton closedialog;
        String images_to_load;
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
            if (d!=null){
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.my_fragment, container, false);
            post_view_image_big=(NetworkImageView)root.findViewById(R.id.citycenter_post_view_image);
            closedialog=(ImageButton)root.findViewById(R.id.closebigimage);
            images_to_load = getArguments().getString("Image");
            if (mImageLoader == null)
                mImageLoader =  MySingleton.getInstance(getActivity()).getImageLoader();
            post_view_image_big.setDefaultImageResId(R.drawable.ic_launcher);
            post_view_image_big.setErrorImageResId(R.drawable.ic_launcher);
            post_view_image_big.setAdjustViewBounds(true);
            post_view_image_big.setImageUrl(images_to_load, mImageLoader);
            closedialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post_view_image_big.setVisibility(View.GONE);

                    MyDialogFragment.this.dismiss();

                }
            });
            return root;
        }

    }
    public static class MyLIKEFragment extends DialogFragment {
        private List<ItemModel> modelListlikes=new ArrayList<ItemModel>();
        private String URL="http://simpli-city.in/request2.php?rtype=postlikes&key=simples&qid=";
        private ListView listview;
        private ProgressDialog pdialog;
        CoordinatorLayout mCoordinator;
        //Need this to set the title of the app bar
        CollapsingToolbarLayout mCollapsingToolbarLayout;
        RecyclerViewAdapter rcAdapter;
        private String URLTWO;
        String bimage;
        RecyclerView recycler;
        private Toolbar mToolbar;
        TextView Toolbartitle;
        ImageButton menu,back,search;
        LinearLayoutManager mLayoutManager;
        JSONObject obj, objtwo;
        JSONArray feedArray;
        int ii,i;
        protected Handler handler;
        private final String TAG_REQUEST = "MY_TAG";
        RequestQueue queues;

        ImageLoader mImageLoader;
        ImageButton closedialog;

        String likes_to_load;
        public MyLIKEFragment() {

        }
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d!=null){
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.likespage, container, false);
            queues = MySingleton.getInstance(this.getActivity()).
                    getRequestQueue();

            closedialog=(ImageButton)root.findViewById(R.id.closebigimage);
            likes_to_load = getArguments().getString("ID");
            URLTWO=URL+likes_to_load;
            mLayoutManager = new LinearLayoutManager(getActivity());
            mCoordinator = (CoordinatorLayout)root. findViewById(R.id.root_coordinator);
            mCollapsingToolbarLayout = (CollapsingToolbarLayout)root. findViewById(R.id.collapsing_toolbar_layout);
            closedialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyLIKEFragment.this.dismiss();

                }
            });
            String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;

            Toolbartitle=(TextView)root.findViewById(R.id.toolbar_title);

            Toolbartitle.setText("LIKES");

            recycler = (RecyclerView)root.findViewById(R.id.likes_recyclerview);
            recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            pdialog = new ProgressDialog(getActivity());
            pdialog.show();
            pdialog.setContentView(R.layout.custom_progressdialog);
            pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


                public void onResponse(JSONObject response) {


                    if (response != null) {
                        dissmissDialog();
                        try {
                            feedArray = response.getJSONArray("result");
                            if (feedArray.length() < 10) {
                                for (ii = 0; ii < feedArray.length(); ii++) {
                                    obj = (JSONObject) feedArray.get(ii);

                                    ItemModel model = new ItemModel();

                                    String image = obj.isNull("image") ? null : obj
                                            .getString("image");
                                    model.setImage(image);

                                    model.setName(obj.getString("name"));

                                    model.setId(obj.getString("id"));

                                    modelListlikes.add(model);

                                }

                                // notify data changes to list adapater
                                rcAdapter.notifyDataSetChanged();
                            }else {
                                for (ii = 0; ii <= 10; ii++) {
                                    obj = (JSONObject) feedArray.get(ii);


                                    ItemModel model = new ItemModel();

                                    String image = obj.isNull("image") ? null : obj
                                            .getString("image");
                                    model.setImage(image);
                                    model.setName(obj.getString("name"));


                                    model.setId(obj.getString("id"));


                                    modelListlikes.add(model);
                                }

                                // notify data changes to list adapater
                                rcAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonreq.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonreq.setTag(TAG_REQUEST);

            MySingleton.getInstance(getActivity()).addToRequestQueue(jsonreq);

            rcAdapter = new RecyclerViewAdapter(modelListlikes,recycler);
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
                            int index = modelListlikes.size();
                            int end = index + 9;

                            try {

                                for (i = index; i < end; i++) {
                                    objtwo = (JSONObject) feedArray.get(i);
                                    ItemModel modelone = new ItemModel();


                                    String image = objtwo.isNull("image") ? null : objtwo
                                            .getString("image");
                                    modelone.setImage(image);
                                    modelone.setName(objtwo.getString("name"));

                                    modelone.setId(objtwo.getString("id"));


                                    modelListlikes.add(modelone);
                                }
                                rcAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {

                            }

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


        class ItemModel {
            private int typeid;
            private String name;
            private String image;
            private String bimage;
            private String pdate;
            private String description;
            private String title;
            private String id;
            private String company;
            /******** start the Food category names****/
            private String source;
            private String source_link;

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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
        static class UserViewHolder extends RecyclerView.ViewHolder {
            public TextView name,locations;



            public NetworkImageView imageview;

            public UserViewHolder(View itemView) {
                super(itemView);



                imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


                name = (TextView) itemView.findViewById(R.id.name);




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

            private boolean loading;
            private OnLoadMoreListener onLoadMoreListener;

            private int visibleThreshold = 5;
            private int lastVisibleItem, totalItemCount;
            Context context;

            public RecyclerViewAdapter(List<ItemModel> students, RecyclerView recyclerView) {
                modelListlikes = students;

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
                if(viewType == VIEW_TYPE_ITEM) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_city_friendslist, parent, false);
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


                    ItemModel itemmodel = modelListlikes.get(position);



                    userViewHolder.name.setText(itemmodel.getName());
                    userViewHolder.name.setTypeface(seguiregular);

                    userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                    userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

                    userViewHolder.imageview.setImageUrl(itemmodel.getImage(), mImageLoader);

                } else if (holder instanceof LoadingViewHolder) {
                    LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                    loadingViewHolder.progressBar.setIndeterminate(true);
                }

            }

            public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
                this.onLoadMoreListener = onLoadMoreListener;
            }




            public int getItemViewType(int position) {


                return modelListlikes.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;

            }

            public void setLoaded() {
                loading = false;
            }

            public int getItemCount() {
                return modelListlikes.size();
            }
        }

    }
}

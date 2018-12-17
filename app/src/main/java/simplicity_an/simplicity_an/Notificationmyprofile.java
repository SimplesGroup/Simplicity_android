package simplicity_an.simplicity_an;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by kuppusamy on 7/4/2016.
 */
public class Notificationmyprofile extends Fragment {
    ImageView profile_image_notifications;
    List<ItemModel> modelListprofile = new ArrayList<ItemModel>();
    String URL = "http://simpli-city.in/request2.php?rtype=citycenter_user&key=simples&user_id=";
    RequestQueue queue;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii, i;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID = "myprofileid";
    private final String TAG_REQUEST = "MY_TAG";
    String myprofileid, userid;
    String URLTWO;
    TextView name_profiles;
    private TabLayout tabLayout;
    private ViewPager viewPager;
RequestQueue requestQueue;

    public Notificationmyprofile() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_myprofile, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");

        }

        URLTWO = URL + myprofileid;
        viewPager = (ViewPager)view. findViewById(R.id.viewpager);
        setupViewPager(viewPager);
requestQueue= Volley.newRequestQueue(getActivity());
        tabLayout = (TabLayout)view. findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
        profile_image_notifications = (ImageView) view.findViewById(R.id.profile_image_notification);
        name_profiles = (TextView) view.findViewById(R.id.name_profile);
        name_profiles.setTypeface(tf);
        JsonObjectRequest jsonreqprofile = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                ImageLoader mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();

                //VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    // dissmissDialog();
                    try {
                        feedArray = response.getJSONArray("personal");

                        for (ii = 0; ii < feedArray.length(); ii++) {
                            obj = (JSONObject) feedArray.get(ii);

                            ItemModel model = new ItemModel();
                            //FeedItem model=new FeedItem();
                            String image = obj.isNull("image") ? null : obj
                                    .getString("image");
                            model.setImage(image);
                            model.setDescription(obj.getString("loc"));
                            model.setId(obj.getString("id"));
                            model.setFriendlist(obj.getString("friends"));
                            model.setTitle(obj.getString("name"));
                            name_profiles.setText(obj.getString("name"));
                            modelListprofile.add(model);
                            userid = obj.getString("id");
                            Context context = profile_image_notifications.getContext();
                           /* Picasso.with(context).load(obj.getString("image"))
                                    .placeholder(context.getResources().getDrawable(R.drawable.photoprofile))

                                    .into(profile_image_notifications);*/


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
        jsonreqprofile.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppController.getInstance().addToRequestQueue(jsonReq);
        requestQueue.add(jsonreqprofile);



        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Myprofilepost(), "Post");
     adapter.addFragment(new Myprofilefriendsfollowing(), "Following");
      adapter.addFragment(new MyFollwers(), "Followers");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        private String id;

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
}

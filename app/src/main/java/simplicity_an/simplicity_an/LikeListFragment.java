package simplicity_an.simplicity_an;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.http.RequestQueue;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.Utils.Configurl;

/**
 * Created by kuppusamy on 5/24/2017.
 */


    public  class LikeListFragment extends android.support.v4.app.DialogFragment {
        private List<ItemModel> modelListlikes=new ArrayList<>();
        private String URL="http://simpli-city.in/request2.php?rtype=likeusers&key=simples&qtype=";
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
        protected android.os.Handler handler;
        private final String TAG_REQUEST = "MY_TAG";
        RequestQueue queues;
com.android.volley.RequestQueue requestQueue;
        ImageLoader mImageLoader;
        ImageButton closedialog;
    JSONArray jsonArray;
        String likes_to_load,likes_qtype;
        public LikeListFragment() {

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
            requestQueue= Volley.newRequestQueue(getActivity());

            closedialog=(ImageButton)root.findViewById(R.id.closebigimage);
            likes_to_load = getArguments().getString("ID");
            likes_qtype=getArguments().getString("QTYPE");
            URLTWO=URL+likes_qtype+"&id="+likes_to_load;
            mLayoutManager = new LinearLayoutManager(getActivity());
         /*   mCoordinator = (CoordinatorLayout)root. findViewById(R.id.root_coordinator);
            mCollapsingToolbarLayout = (CollapsingToolbarLayout)root. findViewById(R.id.collapsing_toolbar_layout);*/
            closedialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LikeListFragment.this.dismiss();

                }
            });
            String simplycity_title_fontPath = "fonts/segoeui.ttf";

            Toolbartitle=(TextView)root.findViewById(R.id.toolbar_title);

            Toolbartitle.setText("Likes");

            recycler = (RecyclerView)root.findViewById(R.id.likes_recyclerview);
            recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
           recycler.setNestedScrollingEnabled(false);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            pdialog = new ProgressDialog(getActivity());
            pdialog.show();
            pdialog.setContentView(R.layout.custom_progressdialog);
            pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           StringRequest jsonreq = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {


                public void onResponse(String response) {

                        Log.e("Response","likes"+response.toString());
                       try {
                           dissmissDialog();
                            JSONObject object=new JSONObject(response.toString());
                            JSONArray array=object.getJSONArray("result");
                            String data=array.optString(1);
                             jsonArray=new JSONArray(data.toString());
                            if (jsonArray.length() < 10) {
                                for (ii = 0; ii < jsonArray.length(); ii++) {
                                    obj = (JSONObject) jsonArray.get(ii);

                                    ItemModel model = new ItemModel();

                                    String image = obj.isNull("image") ? null : obj
                                            .getString("image");
                                    model.setImage(image);

                                    model.setName(obj.getString("name"));

                                    model.setId(obj.getString("user_id"));

                                    modelListlikes.add(model);

                                }

                                // notify data changes to list adapater
                                rcAdapter.notifyDataSetChanged();
                            }else {
                                for (ii = 0; ii <= 10; ii++) {
                                    obj = (JSONObject) jsonArray.get(ii);


                                    ItemModel model = new ItemModel();

                                    String image = obj.isNull("image") ? null : obj
                                            .getString("image");
                                    model.setImage(image);
                                    model.setName(obj.getString("name"));


                                    model.setId(obj.getString("user_id"));


                                    modelListlikes.add(model);
                                }

                                // notify data changes to list adapater
                                rcAdapter.notifyDataSetChanged();
                            }
                        }catch (JSONException e){

                        }

                    /*if (response != null) {
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
                    }*/
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String>params=new HashMap<>();

                   params.put("Key", "Simplicity");
                   params.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                   params.put("rtype", "viewlike");
                   params.put("language","1");
                   params.put("qtype",likes_qtype);
                   params.put("id",likes_to_load);


                   return params;
               }
           };
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

                    new android.os.Handler().postDelayed(new Runnable() {
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
                                    objtwo = (JSONObject) jsonArray.get(i);
                                    ItemModel modelone = new ItemModel();


                                    String image = objtwo.isNull("image") ? null : objtwo
                                            .getString("image");
                                    modelone.setImage(image);
                                    modelone.setName(objtwo.getString("name"));

                                    modelone.setId(objtwo.getString("user_id"));


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

                    String simplycity_title_fontPath = "fonts/segoeui.ttf";
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

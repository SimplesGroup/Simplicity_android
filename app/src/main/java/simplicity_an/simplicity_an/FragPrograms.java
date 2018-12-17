package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuppusamy on 4/5/2016.
 */
public class FragPrograms extends Fragment {
    private LinearLayoutManager lLayout;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=radiocategory&key=simples";

    View view;

    RequestQueue queue;
    private ListView listview;
    private ProgressDialog pdialog;
    private CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerViewAdapter rcAdapter;
    RecyclerView recycler;
    private Toolbar mToolbar;
    TextView Toolbartitle;

    private LinearLayoutManager mLayoutManager;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii,i;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";
    public FragPrograms() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragprograms, container, false);
        lLayout = new LinearLayoutManager(getActivity());
        queue =  MySingleton.getInstance(getActivity()).
                getRequestQueue();

        recycler = (RecyclerView) view.findViewById(R.id.recycler_view_radio_programs);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setLayoutManager(lLayout);
        pdialog = new ProgressDialog(getActivity());

        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
 recycler.addOnItemTouchListener(new RadioActivity.RecyclerTouchListener(getActivity(), recycler, new RadioActivity.ClickListener() {
            public void onClick(View view, int position) {

                String bid = ((ItemModel) modelList.get(position)).getId();
                String bitmap = ((ItemModel) modelList.get(position)).getImage();
                Toast.makeText(getActivity(),bid, Toast.LENGTH_SHORT).show();
                Intent fooddescription = new Intent(getActivity(), RadioprogramsInnerpage.class);
                fooddescription.putExtra("ID", bid);
                fooddescription.putExtra("IMAGE", bitmap);

                startActivity(fooddescription);
            }

            public void onLongClick(View view, int position) {

            }
        }));
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    dissmissDialog();
                    try {
                        feedArray = response.getJSONArray("result");
                        if (feedArray.length() < 10) {
                            for (ii = 0; ii < feedArray.length(); ii++) {
                                obj = (JSONObject) feedArray.get(ii);

                                ItemModel model = new ItemModel();
                                //FeedItem model=new FeedItem();
                                String image = obj.isNull("thumb") ? null : obj
                                        .getString("thumb");
                                model.setImage(image);
                               // model.setLink(obj.getString("link"));
                                model.setId(obj.getString("id"));
                                //model.setPdate(obj.getString("pdate"));
                                model.setTitle(obj.getString("category_name"));
                               // model.setDescription(obj.getString("description"));
                                model.setName(obj.getString("source"));

                                modelList.add(model);

                            }

                            // notify data changes to list adapater
                            rcAdapter.notifyDataSetChanged();
                        }else {
                            for (ii = 0; ii <= 10; ii++) {
                                obj = (JSONObject) feedArray.get(ii);


                                ItemModel model = new ItemModel();
                                //FeedItem model=new FeedItem();
                                String image = obj.isNull("thumb") ? null : obj
                                        .getString("thumb");
                                model.setImage(image);
                               // model.setLink(obj.getString("link"));
                                model.setId(obj.getString("id"));
                               // model.setPdate(obj.getString("pdate"));
                                model.setTitle(obj.getString("category_name"));
                                // model.setDescription(obj.getString("description"));
                                model.setName(obj.getString("source"));

                                modelList.add(model);
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

                        int index = modelList.size();
                        int end = index + 9;

                        try {

                            for (i = index; i < end; i++) {
                                objtwo = (JSONObject) feedArray.get(i);
                                ItemModel modelone = new ItemModel();


                                String image = objtwo.isNull("thumb") ? null : objtwo
                                        .getString("thumb");
                                modelone.setImage(image);
                                //modelone.setLink(objtwo.getString("link"));
                                modelone.setId(objtwo.getString("id"));
                               // modelone.setPdate(objtwo.getString("pdate"));
                                modelone.setTitle(objtwo.getString("category_name"));
                                // model.setDescription(obj.getString("description"));
                                modelone.setName(objtwo.getString("source"));



                                modelList.add(modelone);
                            }
                            rcAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                        }

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

    class ItemModel{
        private int typeid;
        private String name;
        private String image;

        private String pdate;
        private String description;
        private String title;

        private String link;

        /******** start the Food category names****/
        private String id;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView title,date,description;
        public NetworkImageView imageview;

        public UserViewHolder(View itemView) {
            super(itemView);



            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


            title = (TextView) itemView.findViewById(R.id.title_radio);
            date = (TextView) itemView.findViewById(R.id.date_radio);
            description = (TextView) itemView.findViewById(R.id.desc_radio);


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
        private final int VIEW_TYPE_FEATURE = 0;
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
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            if(viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_radio, parent, false);
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


                ItemModel itemmodel = modelList.get(position);



                userViewHolder.title.setText(itemmodel.getTitle());
                userViewHolder.title.setTypeface(seguiregular);
                userViewHolder.date.setText("By" + "" + itemmodel.getName());
                userViewHolder.date.setTypeface(seguiregular);
                userViewHolder.description.setVisibility(View.GONE);
              //  userViewHolder.description.setText(Html.fromHtml(itemmodel.getDescription()));
                //userViewHolder.description.setText(itemmodel.getDescription());
                userViewHolder.description.setTypeface(seguiregular);
                userViewHolder.imageview.setImageUrl(itemmodel.getImage(), mImageLoader);


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
}

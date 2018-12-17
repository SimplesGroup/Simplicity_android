package simplicity_an.simplicity_an.MainEnglish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SimplicitySearchview;
import simplicity_an.simplicity_an.Tab_new_news;
import simplicity_an.simplicity_an.Tabarticle;
import simplicity_an.simplicity_an.Utils.Configurl;
import simplicity_an.simplicity_an.Utils.Fonts;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopFragment extends Fragment {
   private TextView title_shop;
   private RecyclerView recyclerView;
   private SwipeRefreshLayout swipeRefreshLayout;
   List<ShopData> shopDataList=new ArrayList<>();
   private int requestCount=1;
   private RequestQueue requestQueue;
   ShopAdapter shopAdapter;
    android.support.v7.widget.SearchView search;

  private   SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    String myprofileid,colorcodes,fontname;
    RelativeLayout mainlayout;
    ProgressDialog pdialog;
    private String search_value;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.shop_fragment,container,false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        requestQueue= Volley.newRequestQueue(getActivity());
       title_shop=(TextView)view.findViewById(R.id.title_shop_textview);


        mainlayout=(RelativeLayout)view.findViewById(R.id.shop_layout);
       search=(android.support.v7.widget.SearchView)view.findViewById(R.id.searchview_main);
        search.setActivated(true);
        search.setQueryHint("Search for products/brands in Coimbatore");
        search.onActionViewExpanded();
        search.setIconified(false);
        search.clearFocus();
       setupSearchView();
       shopDataList.clear();
        getData();
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


            }
        });



        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub


                search_value = query;

               /* Intent simplicity = new Intent(getActivity(), SimplicitySearchview.class);
                simplicity.putExtra("QUERY", search_value);
                startActivity(simplicity);*/

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        });

        if(colorcodes!=null) {
            if (colorcodes.equals("#FFFFFFFF")) {
                int[] colors = {Color.parseColor(colorcodes),  Color.parseColor("#FFFFFFFF")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);


            } else {
                int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);
                // city.setBackgroundColor(getResources().getColor(R.color.theme1button));
               /* fabplus.setBackgroundResource(R.color.theme1button);
                fabinnerplus.setBackgroundResource(R.color.theme1button);
                fabsearch.setBackgroundResource(R.color.theme1button);*/
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(backgroundcolor, "#262626");

                editor.commit();

            }
        }else{
            int[] colors = {Color.parseColor("#262626"),  Color.parseColor("#00000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);

            mainlayout.setBackgroundDrawable(gd);

            /*fabplus.setBackgroundResource(R.color.theme1button);
            fabinnerplus.setBackgroundResource(R.color.theme1button);
            fabsearch.setBackgroundResource(R.color.theme1button);*/
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }
        title_shop.setText("Shop Essentials");
        if(colorcodes.equals("#FFFFFFFF"))
        {
            title_shop.setTextColor(Color.BLACK);

        }
        else
        {
            title_shop.setTextColor(Color.WHITE);
        }
        String simplycity_title= "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);
        if(fontname.equals("playfair")){
            title_shop.setTypeface(tf_pala);

        }else {
            Typeface sanf=Typeface.createFromAsset(getActivity().getAssets(),Fonts.sanfranciscobold);
            title_shop.setTypeface(sanf);


        }

        pdialog = new ProgressDialog(getActivity());
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_shop);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_shop);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        shopAdapter=new ShopAdapter(shopDataList,recyclerView);
        recyclerView.setAdapter(shopAdapter);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                shopDataList.clear();
                shopAdapter.notifyDataSetChanged();
                requestCount=1;
                getData();

                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                    }                 }, 3000);

            }
        });



        return view;
    }

    private void getData(){
        requestQueue.add(getDataFromTheServer(requestCount));
        requestCount++;
    }

    StringRequest getDataFromTheServer(final int requestCount){


        StringRequest request=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","shop"+response.toString());
               try{
                    JSONObject object=new JSONObject(response.toString());
                    JSONArray array=object.getJSONArray("result");
                    String data=array.optString(1);
                    JSONArray jsonArray=new JSONArray(data.toString());
                    Log.e("Response","shop"+data.toString());
                    if (response != null) {
                        dissmissDialog();
                        parseJsonFeed(jsonArray);
                    }
                }catch (JSONException e){

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();
                param.put("Key", "Simplicity");
                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("language", "1");
                param.put("rtype", "explore");

                param.put("page", String.valueOf(requestCount));
                /*if(myprofileid!=null){
                    param.put("user_id",myprofileid);
                }else {

                }*/
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

        return request;







    }

    private void parseJsonFeed(JSONArray response) {
        try {


            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = (JSONObject) response.get(i);
                ShopData model=new ShopData();
                String imageeve = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(imageeve);
                model.setTitle(obj.getString("title"));
                model.setUrl(obj.getString("url"));
                shopDataList.add(model);
            }
            shopAdapter.notifyDataSetChanged();

        } catch (JSONException e) {

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

    private void setupSearchView()
    {
        // search hint
        search.setQueryHint(getString(R.string.queryhintforshopsearch));

        // background
        View searchPlate = search.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundColor(Color.TRANSPARENT);

        // icon
        ImageView searchIcon = (ImageView) search.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(android.R.drawable.ic_menu_search);

        // clear button
        ImageView searchClose = (ImageView) search.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        //searchClose.setImageResource(android.R.drawable.ic_menu_delete);

        // text color
        AutoCompleteTextView searchText = (AutoCompleteTextView) search.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(getResources().getColor(R.color.white));
        searchText.setHintTextColor(getResources().getColor(R.color.white));
    }


    public class ShopData{

private String id;
private String title;
        private String image;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.Shopmodelview>{


        ImageLoader mImageLoader;
        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_LOADING = 3;

        boolean loading;
        OnLoadMoreListener onLoadMoreListener;
        private final int VIEW_TYPE_PHOTOSTORY = 2;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;
        private  int currentvisiblecount;
        List<ShopData>shopdataList=new ArrayList<>();
        public ShopAdapter(List<ShopData> students, RecyclerView recyclerView){
            shopdataList = students;

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
                                currentvisiblecount=linearLayoutManager.findLastVisibleItemPosition();
                                if(lastVisibleItem>=10){
                                   // fabnews.setVisibility(View.VISIBLE);
                                }else {
                                   // fabnews.setVisibility(View.GONE);
                                }
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

        @NonNull
        @Override
        public Shopmodelview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_shopsfrag,parent,false);

            return new Shopmodelview(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Shopmodelview holder, int position) {
            sharedpreferences = getActivity(). getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE);
            fontname=sharedpreferences.getString(Fonts.FONT,"");
            colorcodes=sharedpreferences.getString(backgroundcolor,"");

            if (mImageLoader == null)
                mImageLoader = CustomVolleyRequest.getInstance(getActivity()).getImageLoader();
            final ShopData data=shopDataList.get(position);

            if(colorcodes.equals("#FFFFFFFF"))
            {
                holder.title_category.setTextColor(Color.BLACK);

            }
            else
            {
                holder.title_category.setTextColor(Color.WHITE);
            }
            String simplycity_title= "fonts/playfairDisplayRegular.ttf";
            Typeface tf_pala = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);
            if(fontname.equals("playfair")){
               holder.title_category.setTypeface(tf_pala);

            }else {
                Typeface sanf=Typeface.createFromAsset(getActivity().getAssets(),Fonts.sanfranciscobold);
                holder.title_category.setTypeface(sanf);


            }
            holder.title_category.setText(data.getTitle());
            Picasso.with(getActivity()).load(data.getImage()).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.category_image);
            holder.category_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AdvertisementPage.class);
                    intent.putExtra("ID", data.getUrl());
                    startActivity(intent);
                }
            });
        }
        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }
        public void setLoaded() {
            loading = false;
        }
        @Override
        public int getItemCount() {
            return shopdataList.size();
        }

        public class Shopmodelview extends RecyclerView.ViewHolder{
            private TextView title_category;
            private ImageView category_image;
            public Shopmodelview(View itemView) {
                super(itemView);
                title_category=(TextView)itemView.findViewById(R.id.shop_title_category);

                category_image=(ImageView)itemView.findViewById(R.id.shop_image_category);
            }
        }
    }
}

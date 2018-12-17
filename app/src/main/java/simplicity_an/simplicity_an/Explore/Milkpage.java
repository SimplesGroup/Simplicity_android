package simplicity_an.simplicity_an.Explore;

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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.Events;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/2/2017.
 */
public class Milkpage extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RelativeLayout mainlayout;

    String URL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=milk_location";

    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pDialog;

 RequestQueue requestQueue;
    ImageButton back,happenning,search_button,entertainment,morepage;
    TextView explore_milktitle_textview,explore_milktitle_below_textview,mycart_textview,buynow_textview,cartcount_textview;
    LinearLayout mycartlayout;
    List<String> milklocations=new ArrayList<>();
    List<String> milkids=new ArrayList<>();
    Spinner productprices_spinner;
    String title,spinnerid;
    Button button_continue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_milk_age);
        Intent get=getIntent();
         title=get.getStringExtra("TITLE");
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        requestQueue= Volley.newRequestQueue(this);
        mainlayout=(RelativeLayout)findViewById(R.id.main_layout_explore);
        if(colorcodes.length()==0){

        }else {
            if (colorcodes.equalsIgnoreCase("004")) {
                Log.e("Msg", "hihihi");
            } else {

                int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);
            }
        }
        JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {


            public void onResponse(JSONObject response) {

                //VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    pDialog.dismiss();
                    Log.e("URL",response.toString());
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonreq);
        back=(ImageButton)findViewById(R.id.btn_ex_back);
        happenning=(ImageButton)findViewById(R.id.btn_ex_happening);
        search_button=(ImageButton)findViewById(R.id.btn_ex_search);
        entertainment=(ImageButton)findViewById(R.id.btn_ex_entertainment);
        morepage=(ImageButton)findViewById(R.id.btn_ex_more);
        explore_milktitle_textview= (TextView)findViewById(R.id.explore_milk_title) ;
        explore_milktitle_below_textview=(TextView) findViewById(R.id.explore_milktwo_title);
        mycart_textview=(TextView)findViewById(R.id.mycart_textview);
        buynow_textview=(TextView)findViewById(R.id.buynow_textview) ;
        cartcount_textview=(TextView)findViewById(R.id.cart_main_count);
        mycartlayout=(LinearLayout)findViewById(R.id.cart_layout) ;
        button_continue=(Button)findViewById(R.id.continue_button_milk);
        productprices_spinner=(Spinner)findViewById(R.id.explore_productdetail_spinner_price);
        mycart_textview.setTypeface(tf);
        cartcount_textview.setTypeface(tf);
        buynow_textview.setTypeface(tf);
        explore_milktitle_textview.setTypeface(tf);
        explore_milktitle_below_textview.setTypeface(tf);
        explore_milktitle_textview.setText(title);
        explore_milktitle_below_textview.setText("Get fresh milk daily");
        Log.e("CARTCOUNT",cartcounts+"hi");
        if(cartcounts==null||cartcounts.equalsIgnoreCase("")){
            cartcount_textview.setText("0");

        }else {
            cartcount_textview.setText(cartcounts);
        }
        mycartlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"go to cart page", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.remove(CARTCOUNT);
                editor.apply();
                CartCountincrement();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        morepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notification_page = new Intent(getApplicationContext(), ExploreSettings.class);
                startActivity(notification_page);
            }
        });
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), simplicity_an.simplicity_an.EntertainmentVersiontwo.class);
                startActivity(entairnment);
            }
        });
        happenning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event_page = new Intent(getApplicationContext(), Events.class);
                startActivity(event_page);
            }
        });
        pDialog=new ProgressDialog(this);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progressdialog);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        milklocations.add("Choose location");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.view_spinner_explore,milklocations);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        productprices_spinner.setAdapter(arrayAdapter);
        productprices_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Exploreitem itemmodel = exploreitemList_product.get(position);
                if (position == 0) {

                } else {
                    spinnerid=milkids.get(position-1);
                    Log.e("ID",spinnerid);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerid!=null) {
                    Intent intent = new Intent(getApplicationContext(),ProductListPage.class);
                    intent.putExtra("TITLE",title);
                    intent.putExtra("ID","location="+spinnerid);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Please Select the location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void parseJsonFeed(JSONObject response){
        try {
            JSONArray productlist=response.getJSONArray("result");
            for(int i=0;i<productlist.length();i++){
                Exploreitem items=new Exploreitem();
                JSONObject jsonObject=(JSONObject)productlist.get(i);
                items.setId(jsonObject.getString("id"));

                items.setLocation(jsonObject.getString("location"));
milklocations.add(jsonObject.getString("location"));
                milkids.add(jsonObject.getString("id"));

            }

        }catch (JSONException e){

        }
    }
    class Exploreitem{
        String id,location;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
    private void CartCountincrement(){
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        cartcount_textview.setText(cartcounts);

    }
}

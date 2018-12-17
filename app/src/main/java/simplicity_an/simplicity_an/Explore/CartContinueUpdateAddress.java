package simplicity_an.simplicity_an.Explore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/14/2017.
 */
public class CartContinueUpdateAddress extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    public static final String USEREMAIL= "myprofileemail";
    public static final String USERMAILID= "myprofileemail";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts,my_profilename,my_profileimage,my_profileemail;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RelativeLayout mainlayout;
    RequestQueue requestQueue;
    TextView title_textview,your_location_title_textview,change_address_tetview,name_textview,shippingaddress_textview,addmoreproducts_textview;
    TextView back_tocart_textview,totalprice_textview,continue_textview,shippingaddress_continue_textview;
    String URL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=viewshipping&user_id=";
    String URLFULL;
    String addressid,address_data,billingname,billingaddress,billingpincode,billingcity,billingstate,billingphone,billingemail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cartcontinueupdatepage);
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

        String simplycity_title_fontPath_1 = "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath_1);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }

        final Intent get=getIntent();
        final String price=get.getStringExtra("TOTALPRICE");
        String id=get.getStringExtra("ID");

        if (sharedpreferences.contains(USERNAME)) {

            my_profilename=sharedpreferences.getString(USERNAME,"");

        }
        if (sharedpreferences.contains(USERIMAGE)) {

            my_profileimage=sharedpreferences.getString(USERIMAGE,"");

        }

        Log.e("Msg", "hihihi"+price);
            my_profileemail=sharedpreferences.getString(USERMAILID,"");
            Log.e("Email","emailss"+my_profileemail.toString());

        if(id!=null){
            URLFULL=URL+myprofileid+"&id="+id;

        }else {
            URLFULL=URL+myprofileid;
        }

        getData();
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
        title_textview=(TextView)findViewById(R.id.city_title);
        title_textview.setTypeface(tf);
        title_textview.setText("Update Address");
        your_location_title_textview=(TextView)findViewById(R.id.your_delivery_location_title);
        change_address_tetview=(TextView)findViewById(R.id.your_delivery_location_change);
        name_textview=(TextView)findViewById(R.id.explore_user_delivery_name);
        shippingaddress_textview=(TextView)findViewById(R.id.explore_user_delivery_address);
        shippingaddress_continue_textview=(TextView)findViewById(R.id.explore_user_delivery_address_continue);
        addmoreproducts_textview=(TextView)findViewById(R.id.your_delivery_location_addmoreproducts);
        back_tocart_textview=(TextView)findViewById(R.id.your_delivery_location_backtocart);
       totalprice_textview=(TextView)findViewById(R.id.mycart_textview);
        continue_textview=(TextView)findViewById(R.id.buynow_textview);

        your_location_title_textview.setTypeface(tf);
        change_address_tetview.setTypeface(tf);
        name_textview.setTypeface(tf1);
        shippingaddress_textview.setTypeface(tf);
        addmoreproducts_textview.setTypeface(tf);
        back_tocart_textview.setTypeface(tf);
        totalprice_textview.setTypeface(tf);
        continue_textview.setTypeface(tf);
        shippingaddress_continue_textview.setTypeface(tf);

        addmoreproducts_textview.setText("ADD MORE PRODUCTS");
        back_tocart_textview.setText("BACK TO CART");
        your_location_title_textview.setText("Your Delivery LocationSelection");
        change_address_tetview.setText("CHANGE");
        continue_textview.setText("CONTINUE");
        if (price!=null)
        totalprice_textview.setText("Total:Rs."+price);

change_address_tetview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in=new Intent(getApplicationContext(),CartallshippingAddress.class);
        startActivity(in);
    }
});

        continue_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),SelectPaymentPage.class);
                in.putExtra("ID",addressid);
                in.putExtra("NAME",billingname);
                in.putExtra("ADDRESS",billingaddress);
                in.putExtra("PIN",billingpincode);
                in.putExtra("CITY",billingcity);
                in.putExtra("STATE",billingstate);
                in.putExtra("PHONE",billingphone);
                in.putExtra("TOTALPRICE",price);
                in.putExtra("EMAIL",my_profileemail);
                startActivity(in);
            }
        });
    }
    private void getData(){
        JsonObjectRequest edit_request=new JsonObjectRequest(Request.Method.GET, URLFULL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray reslutarray=response.getJSONArray("result");
                    for (int i=0;i<reslutarray.length();i++){
                        UPDATEITEM updateitem=new UPDATEITEM();
                        JSONObject object=(JSONObject)reslutarray.get(i);
                        updateitem.setId(object.getString("id"));
                        updateitem.setUserid(object.getString("user_id"));
                        updateitem.setName(object.getString("name"));
                        updateitem.setAddressone(object.getString("address1"));
                        updateitem.setAddresstwo(object.getString("address2"));
                        updateitem.setPhone(object.getString("phone"));
                        updateitem.setLocation(object.getString("location"));
                        updateitem.setLanmark(object.getString("landmark"));
                        updateitem.setPincode(object.getString("pincode"));
                        updateitem.setPdate(object.getString("pdate"));
addressid=object.getString("id");
                       name_textview.setText(object.getString("name"));
                        shippingaddress_textview.setText(object.getString("address1")+"\n"+object.getString("address2")+"\n"+object.getString("location"));
                        shippingaddress_continue_textview.setText("Coimbatore -"+object.getString("pincode")+"\n" +"LandMark:"+object.getString("landmark")+"\n"+object.getString("phone"));
address_data=object.getString("name")+"\n"+object.getString("address1")+"\n"+object.getString("address2")+"\n"+object.getString("location")+"\n"+
        "Coimbatore -"+object.getString("pincode")+"\n" +"LandMark:"+object.getString("landmark")+"\n"+object.getString("phone");
                        billingname=object.getString("name");
                        billingaddress=object.getString("address1")+"\n"+object.getString("address2")+"\n"+object.getString("location");
                        billingcity="Coimbatore";
                       billingphone=object.getString("phone");
                        billingstate="Tamilnadu";
                        billingpincode=object.getString("pincode");
                    }
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        edit_request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(edit_request);
    }
    public class UPDATEITEM{
        String id,name,userid,phone,addressone,addresstwo,location,lanmark,pincode,pdate;


        public String getLanmark() {
            return lanmark;
        }

        public void setLanmark(String lanmark) {
            this.lanmark = lanmark;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPdate() {
            return pdate;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAddressone() {
            return addressone;
        }

        public void setAddressone(String addressone) {
            this.addressone = addressone;
        }

        public String getAddresstwo() {
            return addresstwo;
        }

        public void setAddresstwo(String addresstwo) {
            this.addresstwo = addresstwo;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

    }
}

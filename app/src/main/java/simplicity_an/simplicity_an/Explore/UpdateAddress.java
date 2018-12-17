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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/14/2017.
 */
public class UpdateAddress extends AppCompatActivity {
    public static final String backgroundcolor = "color";
    public static final String mypreference = "mypref";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    SharedPreferences sharedpreferences;
    String activity,contentid,colorcodes,myprofileid,cartcounts,my_profilename,my_profileimage;
    public static final String MYUSERID= "myprofileid";
    public String CARTCOUNT= "cartcound";
    RelativeLayout mainlayout;
    RequestQueue requestQueue;
String UPDATEURL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=addshippings";
String EDITURL="http://simpli-city.in/explore_request.php?key=smp_explore&rtype=viewshipping&user_id=";
    String EDITFULLURL;
    TextView title_textview;
    EditText name_edit,phone_edit,address_one_edit,address_two_edit,location_edit,pincode_edit,landmark_edit;
    Button back_button,save_button;
    String sid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profileupdate);



        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        cartcounts=sharedpreferences.getString(CARTCOUNT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }

        final Intent get=getIntent();
         sid=get.getStringExtra("ID");
        if(sid!=null){
            EDITFULLURL=EDITURL+myprofileid+"sid="+sid;
            getData();
        }
        if (sharedpreferences.contains(USERNAME)) {

            my_profilename=sharedpreferences.getString(USERNAME,"");

        }
        if (sharedpreferences.contains(USERIMAGE)) {

            my_profileimage=sharedpreferences.getString(USERIMAGE,"");

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
title_textview=(TextView)findViewById(R.id.city_title);
        title_textview.setTypeface(tf);
        title_textview.setText("Update Address");
        name_edit=(EditText)findViewById(R.id.username);
name_edit.setText(my_profilename);
        location_edit=(EditText)findViewById(R.id.location);
       phone_edit=(EditText)findViewById(R.id.phone);
        pincode_edit=(EditText)findViewById(R.id.pincode);
        address_one_edit=(EditText)findViewById(R.id.addresslineone);
        address_two_edit=(EditText)findViewById(R.id.addresslinetwo);
        landmark_edit=(EditText)findViewById(R.id.lanmark);
        back_button=(Button)findViewById(R.id.back_button_explore);
        save_button=(Button)findViewById(R.id.save_button_explore);
        back_button.setTypeface(tf);
        save_button.setTypeface(tf);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name=name_edit.getText().toString();
                final String phone=phone_edit.getText().toString();
                final String address1=address_one_edit.getText().toString();
                final String address2=address_two_edit.getText().toString();
                final String locations=location_edit.getText().toString();
                final String pincodes=pincode_edit.getText().toString();
                final String landmark=landmark_edit.getText().toString();

                Toast.makeText(getApplicationContext(),"sgsgsg",Toast.LENGTH_LONG).show();



                StringRequest save_request=new StringRequest(Request.Method.POST, UPDATEURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
Log.e("RES",response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    /**
                     * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
                     * {@link AuthFailureError} as authentication may be required to provide these values.
                     * <p/>
                     * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
                     *
                     * @throws AuthFailureError in the event of auth failure
                     */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new Hashtable<String, String>();
                        params.put("user_id",myprofileid);
                        params.put("name",name);
                        params.put("phone",phone);
                        params.put("address1",address1);
                        params.put("address2",address2);
                        params.put("pincode",pincodes);
                        params.put("landmark",landmark);
                        params.put("location",locations);

                        if(sid==null||sid==""){

                        }else {
                            params.put("sid",sid);
                        }

                        return params;
                    }
                };
                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                save_request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(save_request);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData(){
        JsonObjectRequest edit_request=new JsonObjectRequest(Request.Method.GET, EDITFULLURL, new Response.Listener<JSONObject>() {
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

        name_edit.setText(object.getString("name"));
        phone_edit.setText(object.getString("phone"));
        address_one_edit.setText(object.getString("address1"));
        address_two_edit.setText(object.getString("address2"));
        location_edit.setText(object.getString("location"));
        pincode_edit.setText(object.getString("pincode"));
        landmark_edit.setText(object.getString("landmark"));

    }
}catch (JSONException e){

}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        edit_request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(edit_request);
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

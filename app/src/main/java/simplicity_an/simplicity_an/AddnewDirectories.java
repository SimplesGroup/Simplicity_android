package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 9/8/2016.
 */
public class AddnewDirectories extends AppCompatActivity {
    TextView addposttitle;
    Button getimagepath,postevent;
    EditText category_addpost,address_addpost,area_addpost,contactphone,contactemail,website_addpost,contactlandline,name;
    private String KEY_NAME="cname";
    private String KEY_MYUSERID="user_id";
    private String KEY_EMAIL= "email";
    private String KEY_PHONE = "phone";
    private String KEY_ADDRESS = "address";
    private String KEY_WEB = "website";
    private String KEY_CATLIST= "category";
    private String KEY_AREA= "area";
    private String KEY_LANDLINE= "altphone";



    String UPLOAD_URL ="http://simpli-city.in/request2.php?rtype=postbusiness&key=simples";
    String text="";

    String encodedImage,myprofileid;
    ImageButton menus,back,addnewevent;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.addnewdirectories);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        back=(ImageButton)findViewById(R.id.btn_1);
        menus=(ImageButton)findViewById(R.id.btn_3);
        addnewevent=(ImageButton)findViewById(R.id.btn_2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent back=new Intent(AddnewDirectories.this,Directories.class);
                startActivity(back);
                finish();*/
                onBackPressed();
            }
        });
        menus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu=new Intent(AddnewDirectories.this, MainPageEnglish.class);
                startActivity(menu);
                finish();
            }
        });
        addposttitle=(TextView)findViewById(R.id.add_post_your_newbusiness);

        postevent=(Button)findViewById(R.id.add_newevent_postevent);
        name=(EditText)findViewById(R.id.add_newbusiness_name);
        category_addpost=(EditText)findViewById(R.id.add_newbusiness_category);
        address_addpost=(EditText)findViewById(R.id.add_newbusiness_address);
        area_addpost=(EditText)findViewById(R.id.add_newbusiness_area);
        contactphone=(EditText)findViewById(R.id.add_newbusiness_phone);
        contactlandline=(EditText)findViewById(R.id.add_newbusiness_landline);
        contactemail=(EditText)findViewById(R.id.add_newbusiness_contactemail);
        website_addpost=(EditText)findViewById(R.id.add_newbusiness_contactwebsite);



        addposttitle.setTypeface(tf);
        addposttitle.setText("POST YOUR BUSINESS HERE");

        postevent.setTypeface(tf);
        postevent.setText("POST BUSINESS");

        name.setTypeface(tf);
        category_addpost.setTypeface(tf);
        address_addpost.setTypeface(tf);
        area_addpost.setTypeface(tf);
        contactlandline.setTypeface(tf);
        contactphone.setTypeface(tf);
        contactemail.setTypeface(tf);
        website_addpost.setTypeface(tf);
postevent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uploaddata();
    }
});
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void Uploaddata(){
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //  loading.dismiss();
                        //Showing toast message of the response
                        if(s.equalsIgnoreCase("error")) {
                            Toast.makeText(AddnewDirectories.this, s, Toast.LENGTH_LONG).show() ;
                        }else {
                            Intent citycenter_intent=new Intent(AddnewDirectories.this,Directories.class);
                            startActivity(citycenter_intent);
                            finish();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        // Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                //Getting Image Name

                String titles = name.getText().toString().trim();
                String venue = address_addpost.getText().toString().trim();
                String location = area_addpost.getText().toString().trim();
                String category = category_addpost.getText().toString().trim();
                String cphone = contactphone.getText().toString().trim();
                String cemail = contactemail.getText().toString();
                String website = website_addpost.getText().toString();
                String landline = contactlandline.getText().toString();



                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                if (cemail!= null||landline!=null||website!=null) {

                    params.put(KEY_NAME, titles);
                    params.put(KEY_MYUSERID, myprofileid);
                    params.put(KEY_ADDRESS, venue);
                    params.put(KEY_AREA, location);
                    params.put(KEY_PHONE, cphone);
                    params.put(KEY_CATLIST, category);

                    if(cemail!=null){
                        params.put(KEY_EMAIL, cemail);
                    }else if(website!=null){
                        params.put(KEY_WEB, website);
                    }else if(landline!=null){
                        params.put(KEY_LANDLINE, landline);
                    }

                }else {
                    params.put(KEY_NAME, titles);
                    params.put(KEY_MYUSERID, myprofileid);
                    params.put(KEY_ADDRESS, venue);
                    params.put(KEY_AREA, location);
                    params.put(KEY_PHONE, cphone);
                    params.put(KEY_CATLIST, category);


                }


                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        //queue.add(stringRequest);
    }
}

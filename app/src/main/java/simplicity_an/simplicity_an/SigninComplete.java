package simplicity_an.simplicity_an;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by kuppusamy on 5/27/2016.
 */
public class  SigninComplete extends Activity {

    ProgressDialog pdialog;
    String URLZIP="http://simpli-city.in/request2.php?rtype=zipcode&key=simples";
    String URLPROFFESSION="http://simpli-city.in/request2.php?rtype=profession&key=simples";


    String UPLOAD_URL="http://simpli-city.in/request2.php?rtype=login&key=simples";
    EditText username,user_email,user_phone,user_area;
    Spinner genderitem,arealiving,zipcode_area,proffesion;
    TextView simplicityname,justfewsteps;
    RadioButton terms_condition;
    Button submit_data_button,cancel;
    ArrayList<String> genderlist = new ArrayList<String>();
    ArrayList<String> citieslist = new ArrayList<String>();
    ArrayList<String> ziplist=new ArrayList<String>();
    ArrayList<String> professionlist=new ArrayList<String>();
    ArrayAdapter<String> SpinnerAdaptergender,SpinnerAdaptercities,SpinnerAdapterzipcodes,SpinnerAdapterprofession;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String GcmId = "gcmid";
String username_edit,email_edit,phone_edit,area_edit,gender_detail,location_detail,gcmids,pagefrom_activity,activity_name,profession_data;

    private String KEY_GCM = "gcm_id";
    private String KEY_NAME = "name";
    private String KEY_EMAIL= "email";
    private String KEY_PHONE = "phone";
    private String KEY_AREA = "zipcode";
    private String KEY_GENDER = "gender";
    private String KEY_LOCATION = "resident";
    private String KEY_PROFILEIMAGE = "image";
    private String KEY_PROFESSION = "profession";

    private final String TAG_REQUEST = "MY_TAG";
    String facebookimageurl;
    public static final String MYUSERID= "myprofileid";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    String namefromfacebok,emailfromfacebook,profilepicturefromfacebook;

    Boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signincompletepage);

        Intent get=getIntent();
        activity_name=get.getStringExtra("ACTIVITY");
       // Toast.makeText(SigninComplete.this, activity_name, Toast.LENGTH_LONG).show();

        citieslist.add("cities");
        citieslist.add("Coimbatore");
        citieslist.add("Madurai");
        citieslist.add("Trichy");
        citieslist.add("NRI");
        citieslist.add("Other cities");
        Intent gets=getIntent();
        pagefrom_activity=gets.getStringExtra("FACEACTIVITY");

        namefromfacebok=gets.getStringExtra("NAME");
        emailfromfacebook=gets.getStringExtra("EMAIL");
        profilepicturefromfacebook=gets.getStringExtra("IMAGEURL");
        facebookimageurl=gets.getStringExtra("IMAGEURL");
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(GcmId)) {

            gcmids=sharedpreferences.getString(GcmId,"");
            Log.e("GCMID:",gcmids);
            Log.e("IMAGEURL:",profilepicturefromfacebook);

        }
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
      final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        terms_condition=(RadioButton)findViewById(R.id.radio_accept_terms);
       cancel=(Button) findViewById(R.id.cancel_button_signin);
        user_phone=(EditText)findViewById(R.id.phonesignin);
        user_email=(EditText)findViewById(R.id.emailsignin);
        username=(EditText)findViewById(R.id.usernamesignin);
        zipcode_area=(Spinner)findViewById(R.id.area_zipcode);
        proffesion=(Spinner)findViewById(R.id.profession_signin);
     genderitem=(Spinner)findViewById(R.id.gender);
        arealiving=(Spinner)findViewById(R.id.location);
        simplicityname=(TextView)findViewById(R.id.simplicityname_signin);
        justfewsteps=(TextView)findViewById(R.id.justfewsteps_signin);
        submit_data_button = (Button)findViewById(R.id.submit_button_signin);
        simplicityname.setTypeface(tf);
        justfewsteps.setTypeface(tf);
        submit_data_button.setTypeface(tf);
        submit_data_button.setTransformationMethod(null);
        simplicityname.setText("Simplicity");
        justfewsteps.setText("The following information are needed to notify you about your areas happening,shopping offers etc. ");
        submit_data_button.setText("Submit");
        ziplist.add("-Area-");
        professionlist.add("-Profession-");
        genderlist.add("Select");
        genderlist.add("Male");
        genderlist.add("Female");
        genderlist.add("Trans Gender");
        username.setText(namefromfacebok);
        user_email.setText(emailfromfacebook);
        JsonArrayRequest arrayReq=new JsonArrayRequest(URLZIP, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub

                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj=response.getJSONObject(i);
                        ItemModel model=new ItemModel();

                        model.setZipcode(obj.getString("zipcode"));
                        //model.setName(obj.getString("name"));

                        ziplist.add(obj.getString("zipcode"));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                SpinnerAdapterzipcodes.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                // VolleyLog.d(TAG, "ERROR" + error.getMessage());
            }
        });
        arrayReq.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        arrayReq.setTag(TAG_REQUEST);
        MySingleton.getInstance(this).addToRequestQueue(arrayReq);
        JsonArrayRequest arrayReqs=new JsonArrayRequest(URLPROFFESSION, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub

                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj=response.getJSONObject(i);
                        ItemModel model=new ItemModel();
                        model.setIds(obj.getString("id"));
                        model.setProfession(obj.getString("profession"));
                        //model.setName(obj.getString("name"));

                        professionlist.add(obj.getString("profession"));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                SpinnerAdapterprofession.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                // VolleyLog.d(TAG, "ERROR" + error.getMessage());
            }
        });
        arrayReqs.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        arrayReqs.setTag(TAG_REQUEST);
        MySingleton.getInstance(this).addToRequestQueue(arrayReqs);
        SpinnerAdaptercities = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, citieslist){
            public View getView(int position, View convertView,
                                ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
            ((TextView) v).setTextSize(15);
            ((TextView) v).setTypeface(tf);

            ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.droparrows, 0);
            ((TextView) v).setCompoundDrawablePadding(12);

            return v;
        }

        // By using this method we will define how the listview appears after clicking a spinner.
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View v = super.getDropDownView(position, convertView,
                parent);
        GradientDrawable gd = new GradientDrawable();


        ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
        ((TextView) v).setTextSize(14);
        ((TextView) v).setTypeface(tf);
        ((TextView) v).setPadding(50, 0, 10, 0);
        return v;
    }
};
SpinnerAdaptercities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arealiving.setAdapter(SpinnerAdaptercities);

        SpinnerAdaptergender = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, genderlist){
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(15);
                ((TextView) v).setTypeface(tf);
                //((TextView) v).setCompoundDrawablesWithIntrinsicBounds(R.drawable.droparrows, 0, 0, 0);
                ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.droparrows, 0);
                ((TextView) v).setCompoundDrawablePadding(12);

                return v;
            }

            // By using this method we will define how the listview appears after clicking a spinner.
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,
                        parent);
                GradientDrawable gd = new GradientDrawable();


                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(14);
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setPadding(50, 0, 10, 0);
                return v;
            }
        };
        SpinnerAdaptergender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderitem.setAdapter(SpinnerAdaptergender);
        SpinnerAdapterzipcodes = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, ziplist){
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(15);
                ((TextView) v).setTypeface(tf);

                ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.droparrows, 0);
                ((TextView) v).setCompoundDrawablePadding(12);

                return v;
            }

            // By using this method we will define how the listview appears after clicking a spinner.
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,
                        parent);
                GradientDrawable gd = new GradientDrawable();


                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(14);
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setPadding(50, 0, 10, 0);
                return v;
            }
        };
        SpinnerAdapterzipcodes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zipcode_area.setAdapter(SpinnerAdapterzipcodes);


        SpinnerAdapterprofession = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, professionlist){
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(15);
                ((TextView) v).setTypeface(tf);

                ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.droparrows, 0);
                ((TextView) v).setCompoundDrawablePadding(12);

                return v;
            }

            // By using this method we will define how the listview appears after clicking a spinner.
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,
                        parent);
                GradientDrawable gd = new GradientDrawable();


                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setTextSize(14);
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setPadding(50, 0, 10, 0);
                return v;
            }
        };
        SpinnerAdapterprofession.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proffesion.setAdapter(SpinnerAdapterprofession);



        genderitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                } else {
                    gender_detail = genderitem.getSelectedItem().toString().trim();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        arealiving.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                } else if(position==1){

                    location_detail = "1";
                }else if(position==2){
                    location_detail = "2";
                }else if(position==3){
                    location_detail = "3";
                }else if(position==4){
                    location_detail = "4";
                }else if(position==5){
                    location_detail = "5";
                }
//Toast.makeText(SigninComplete.this,location_detail,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        zipcode_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                } else {
                    area_edit = zipcode_area.getSelectedItem().toString().trim();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        proffesion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                } else {
                    profession_data = proffesion.getSelectedItem().toString().trim();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

terms_condition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            terms_condition.setChecked(false);
        }else {
            terms_condition.setChecked(true);
        }
    }
});

        submit_data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(terms_condition.isChecked())
                {
                    uploadImage();
                }
                else
                {
                   Toast.makeText(getApplicationContext(),"please Accept the terms and privacy policy", Toast.LENGTH_LONG).show();
                }

            }
        });


cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent back=new Intent(getApplicationContext(),MainActivityVersiontwo.class);
        startActivity(back);
        finish();
    }
});





    }
    private void uploadImage(){

        username_edit = username.getText().toString();
        email_edit = user_email.getText().toString().trim();
        phone_edit = user_phone.getText().toString().trim();
        //area_edit = user_area.getText().toString();
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {




                         if (s.equalsIgnoreCase("phone") || s.equalsIgnoreCase("email") || s.equalsIgnoreCase("name")) {
                             pdialog.dismiss();
                                Toast.makeText(SigninComplete.this, s + " Already Exist", Toast.LENGTH_LONG).show();
                            } else {
                             pdialog.dismiss();
                                Toast.makeText(SigninComplete.this, "Registration Success", Toast.LENGTH_LONG).show();
                             Log.e("UserID", s);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(MYUSERID, s);
                             editor.putString(USERNAME,username_edit);
                            // editor.putString(KEY_PROFILEIMAGE, profilepicturefromfacebook);
                               if (pagefrom_activity!=null) {
                                     profilepicturefromfacebook = "https://graph.facebook.com/" + facebookimageurl + "/picture";
                                    editor.putString(KEY_PROFILEIMAGE, profilepicturefromfacebook);
                                   editor.putString(USERIMAGE,profilepicturefromfacebook);
                                } else {
                                    editor.putString(KEY_PROFILEIMAGE, profilepicturefromfacebook);
                                   editor.putString(USERIMAGE,profilepicturefromfacebook);
                                }
                                editor.commit();
                                Log.e("UserID", s);
                             if (activity_name.equals("citycenter")) {

                                   /* Intent news = new Intent(SigninComplete.this, CityCenter.class);
                                    startActivity(news);
                                    finish();*/
                                }


                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //Dismissing the progress dialog
                            // loading.dismiss();

                            //Showing toast
                            Toast.makeText(SigninComplete.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    username_edit = username.getText().toString();
                    email_edit = user_email.getText().toString().trim();
                    phone_edit = user_phone.getText().toString().trim();
                    //area_edit = user_area.getText().toString();

                    Map<String, String> params = new Hashtable<String, String>();
if(pagefrom_activity!=null){
    params.put(KEY_GCM, gcmids);
    params.put(KEY_NAME, username_edit);
    params.put(KEY_EMAIL, email_edit);
    params.put(KEY_PHONE, phone_edit);
    params.put(KEY_AREA, area_edit);
    params.put(KEY_PROFESSION,profession_data);
    params.put(KEY_GENDER, gender_detail);
    params.put(KEY_LOCATION, location_detail);
    params.put(KEY_PROFILEIMAGE, profilepicturefromfacebook);
}else {
    params.put(KEY_GCM, gcmids);
    params.put(KEY_NAME, username_edit);
    params.put(KEY_EMAIL, email_edit);
    params.put(KEY_PHONE, phone_edit);
    params.put(KEY_PROFESSION,profession_data);
    params.put(KEY_AREA, area_edit);
    params.put(KEY_GENDER, gender_detail);
    params.put(KEY_LOCATION, location_detail);
    params.put(KEY_PROFILEIMAGE, facebookimageurl);
}
                    //Adding parameters

                    // params.put(KEY_UID,userid);
                    //returning parameters
                    return params;
                }
            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(SigninComplete.this);


            //Adding request to the queue
            requestQueue.add(stringRequest);


    }
private  void Activitiescall(){
    if(activity_name.equals("citycenter")){
        Toast.makeText(SigninComplete.this, "Registration Success", Toast.LENGTH_LONG).show();
       ///startActivity(city);
      //  finish();
    }/*else if (activity_name.equals("news")){
        Intent news=new Intent(getApplicationContext(),NewsActivityTamil.class);
        startActivity(news);
        finish();
    }else if(activity_name.equals("article")){
        Intent art=new Intent(getApplicationContext(),TamilArticleoftheday.class);
        startActivity(art);
        finish();
    }else if(activity_name.equals("events")){
        Intent event=new Intent(getApplicationContext(),Events.class);
        startActivity(event);
        finish();
    }else if (activity_name.equals("sports")){
        Intent sports=new Intent(getApplicationContext(),Sports.class);
        startActivity(sports);
        finish();
    }else if(activity_name.equals("govt")){
        Intent govt_notifications=new Intent(getApplicationContext(),GovernmentNotificationstamil.class);
        startActivity(govt_notifications);
        finish();
    }else if (activity_name.equals("jobs")){
        Intent job=new Intent(getApplicationContext(),JobsActivitytamil.class);
        startActivity(job);
        finish();
    }else if (activity_name.equals("science")){
        Intent sciencetech=new Intent(getApplicationContext(),ScienceandTechnologytamil.class);
        startActivity(sciencetech);
        finish();
    }else if (activity_name.equals("radio")){
        Intent radioplayer=new Intent(getApplicationContext(),RadioActivitytamil.class);
        startActivity(radioplayer);
        finish();
    }else if (activity_name.equals("music")){
        Intent music=new Intent(getApplicationContext(),MusicActivity.class);
        startActivity(music);
        finish();
    }else if (activity_name.equals("theatre")){
        Intent theatre=new Intent(getApplicationContext(),Theatre.class);
        startActivity(theatre);
        finish();
    }else if (activity_name.equals("agriculture")){
        Intent agri=new Intent(getApplicationContext(),FarmingAgricultureActivitytamil.class);
        startActivity(agri);
        finish();
    }else if (activity_name.equals("doit")){
        Intent doityourself=new Intent(getApplicationContext(),DoitYourself.class);
        startActivity(doityourself);
        finish();
    }else if (activity_name.equals("travel")){
        Intent travels=new Intent(getApplicationContext(),TravelsActivitytamil.class);
        startActivity(travels);
        finish();
    }else if (activity_name.equals("directories")){
        Intent directory=new Intent(getApplicationContext(),Directories.class);
        startActivity(directory);
        finish();
    }*/
}
    class  ItemModel {
        String ids,zipcode,profession;

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
    }

}

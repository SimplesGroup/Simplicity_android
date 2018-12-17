package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.Utils.Configurl;

/**
 * Created by kuppusamy on 4/7/2016.
 */
public class FaceBooklogin extends android.app.Activity {
    TextView textitem;
    CallbackManager callbackManager;
    SharedPreferences sharedpreferences;
    private String EMAILID_CHECK="email";
    private String KEY_GCM = "player_id";
    public static final String GcmId = "gcmid";
    public static final String mypreference = "mypref";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid;
    String UPLOAD_CHECK_USER="http://simpli-city.in/request2.php?rtype=checkuser&key=simples";
ProgressDialog pdialog;
    public static final String MYUSERID= "myprofileid";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    public static final String USEREMAIL= "myprofileemail";
    public static final String USERMAILID= "myprofileemail";
    public static final String Language = "lamguage";
    String text,emaildata,profileimage,activity,gcmids,gendervalue,language_data;

    String UPLOAD_URL=" http://simpli-city.in/request2.php?rtype=checkusertest&key=simples";

    private String KEY_NAME = "name";
    private String KEY_EMAIL= "email";
    private String KEY_GENDER = "gender";
    RequestQueue requestQueue;
    private String KEY_PROFILEIMAGE = "picture_link";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.facebooklogin);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        requestQueue=Volley.newRequestQueue(this);
        gcmids=sharedpreferences.getString(GcmId,"");
        language_data=sharedpreferences.getString(Language,"");
        if (sharedpreferences.contains(Activity)) {
            activity=sharedpreferences.getString(Activity,"");
            Log.e("GCMID:",activity);
            // Log.e("IMAGEURL:",profilepicturefromfacebook);

        }
        textitem = (TextView) findViewById(R.id.textView);
        callbackManager = CallbackManager.Factory.create();
        Intent getactivity=getIntent();


        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
           // share.setVisibility(View.VISIBLE);
           // details.setVisibility(View.VISIBLE);
        }
        LoginManager.getInstance().logInWithReadPermissions( this ,
        Arrays.asList("public_profile", "user_friends","email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        if (AccessToken.getCurrentAccessToken() != null) {
                            RequestData();
                            //share.setVisibility(View.VISIBLE);
                            // details.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){
                         text = json.getString("name");
                        //details_txt.setText(Html.fromHtml(text));
                        profileimage=json.getString("id");
                      // profile.setProfileId(json.getString("id"));
                        emaildata=json.getString("email");
                       // gendervalue=json.getString("gender");
                       // Toast.makeText(getApplicationContext(),text+":"+emaildata,Toast.LENGTH_SHORT).show();
                        pdialog = new ProgressDialog(FaceBooklogin.this);
                        pdialog.show();
                        pdialog.setContentView(R.layout.custom_progressdialog);
                        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                       /* StringRequest facesigin=new StringRequest(Request.Method.POST, UPLOAD_CHECK_USER, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.toString().trim().equalsIgnoreCase("no")){
                                    pdialog.dismiss();

                                    StringRequest upload=new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.toString().trim().equalsIgnoreCase("no")) {
                                                Log.e("UserID","no cancel");
                                            }else {
                                                if(response==""||response==null){
                                                    Log.e("UserID","empty");
                                                }else {
                                                    Log.e("UserID", response);
                                                    String[] array = response.split(",");

                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                    editor.putString(MYUSERID,array[0]);
                                                    editor.putString(USERNAME,array[1]);
                                                    editor.putString(USERIMAGE,array[2]);
                                                    String emails=array[3].toString();
                                                    editor.putString(USERMAILID,array[3].toString().trim());
                                                    editor.commit();
                                                    if(language_data.equals("English")) {
                                                        Intent main = new Intent(getApplicationContext(), MainPageEnglish.class);
                                                        startActivity(main);
                                                    }else {
                                                        Intent main = new Intent(getApplicationContext(), MainPageTamil.class);
                                                        startActivity(main);
                                                    }
                                                }



                                                Intent main=new Intent(getApplicationContext(),MainPageEnglish.class);
                                                startActivity(main);

                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        protected Map<String ,String> getParams()throws AuthFailureError{
                                            Map<String,String> params=new Hashtable<String, String>();

                                            params.put(KEY_EMAIL,emaildata);
                                            params.put(KEY_NAME,text);
                                            params.put(KEY_PROFILEIMAGE,"https://graph.facebook.com/" + profileimage + "/picture");
                                           // params.put(KEY_GENDER,gendervalue);
                                            params.put(KEY_GCM,gcmids);
                                            return  params;
                                        }
                                    };
                                    upload.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    requestQueue.add(upload);




                                }else {
                                    pdialog.dismiss();
                                  //  Log.e("Activity",activity);
                                    if(response==""||response==null){
                                        Log.e("UserID","empty");
                                    }else {
                                        Log.e("UserID", response);
                                        String[] array = response.split(",");

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(MYUSERID,array[0]);
                                        editor.putString(USERNAME,array[1]);
                                        editor.putString(USERIMAGE,array[2]);
                                        String emails=array[3].toString();
                                        editor.putString(USERMAILID,array[3].toString().trim());
                                        editor.commit();
                                        if(language_data.equals("English")) {
                                            Intent main = new Intent(getApplicationContext(), MainPageEnglish.class);
                                            startActivity(main);
                                        }else {
                                            Intent main = new Intent(getApplicationContext(), MainPageTamil.class);
                                            startActivity(main);
                                        }
                                    }

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            protected Map<String,String> getParams()throws AuthFailureError{
                                Map<String,String > params=new HashMap<String, String>();
                                params.put(EMAILID_CHECK,emaildata);
                                params.put(KEY_GCM,gcmids);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(FaceBooklogin.this);


                        //Adding request to the queue
                        requestQueue.add(facesigin);*/


                        StringRequest upload=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.toString().trim().equalsIgnoreCase("no")) {
                                    Log.e("UserID","no cancel");
                                }else {
                                    if(response==""||response==null){
                                        Log.e("UserID","empty");
                                    }else {
                                        Log.e("UserID", response);
                                        String[] array = response.split(",");

                                        try {
                                            JSONObject data = new JSONObject(response);
                                            String dir = data.getString("result");
                                            JSONArray object=new JSONArray(dir);
                                            String dir2=object.optString(1);
                                            JSONArray jsonArray=new JSONArray(dir2.toString());
                                            String name=null;
                                            String userid=null;
                                            String userimage=null;
                                            String usermailid=null;
                                            for (int i=0;i<jsonArray.length();i++){
                                                JSONObject obj=jsonArray.getJSONObject(i);
                                                name=obj.getString("name");
                                                userid=obj.getString("user_id");
                                                userimage=obj.getString("picture_link");
                                                usermailid=obj.getString("email");
                                            }
                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString(MYUSERID,userid);
                                            editor.putString(USERNAME,name);
                                            editor.putString(USERIMAGE,userimage);
                                            editor.putString(USERMAILID,usermailid);
                                            Log.e("EMAIL,",usermailid);
                                            editor.commit();
                                        }catch (JSONException e){

                                        }
                                        if(language_data.equals("English")) {
                                            Intent main = new Intent(getApplicationContext(), MainPageEnglish.class);
                                            startActivity(main);
                                        }else {
                                            Intent main = new Intent(getApplicationContext(), MainPageTamil.class);
                                            startActivity(main);
                                        }
                                    }



                                    Intent main=new Intent(getApplicationContext(),MainPageEnglish.class);
                                    startActivity(main);

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            protected Map<String ,String> getParams()throws AuthFailureError{
                                Map<String,String> params=new Hashtable<String, String>();
                                params.put("Key", "Simplicity");
                                params.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                                params.put("rtype", "user_login");
                                params.put(KEY_EMAIL,emaildata);
                                params.put(KEY_NAME,text);
                                params.put(KEY_PROFILEIMAGE,"https://graph.facebook.com/" + profileimage + "/picture");
                                // params.put(KEY_GENDER,gendervalue);
                                params.put(KEY_GCM,gcmids);
                                return  params;
                            }
                        };
                        upload.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        requestQueue.add(upload);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


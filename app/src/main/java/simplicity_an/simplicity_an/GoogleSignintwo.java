package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.Utils.Configurl;

public class GoogleSignintwo extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {




    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;
    SharedPreferences sharedpreferences;
    private String EMAILID_CHECK="email";

    public static final String GcmId = "gcmid";
    public static final String mypreference = "mypref";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String UPLOAD_URL="http://simpli-city.in/request2.php?rtype=checkusertest&key=simples";
    private String KEY_GCM = "player_id";
    private String KEY_NAME = "name";
    private String KEY_EMAIL= "email";
    private String KEY_GENDER = "gender";
    public static final String Language = "lamguage";
    private String KEY_PROFILEIMAGE = "picture_link";
    String contentid;
String UPLOAD_CHECK_USER="http://simpli-city.in/request2.php?rtype=checkuser&key=simples";
String activity,gcmids;
    public static final String MYUSERID= "myprofileid";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    public static final String USERMAILID= "myprofileemail";
    public static final String USEREMAIL= "myprofileemail";
ProgressDialog pdialog;
    String username,userimage,user_email,language_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.googlesinintw);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        gcmids=sharedpreferences.getString(GcmId,"");
        language_data=sharedpreferences.getString(Language,"");
        Log.e("GCMID:",gcmids);
        if (sharedpreferences.contains(Activity)) {

            activity=sharedpreferences.getString(Activity,"");
            gcmids=sharedpreferences.getString(GcmId,"");
            Log.e("GCMID:",activity);
            // Log.e("IMAGEURL:",profilepicturefromfacebook);

        }

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {

       final GoogleSignInAccount acct = result.getSignInAccount();

            pdialog = new ProgressDialog(this);
            pdialog.show();
            pdialog.setContentView(R.layout.custom_progressdialog);
            pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

           /* final StringRequest signintwo=new StringRequest(Request.Method.POST, UPLOAD_CHECK_USER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
Log.e("UserID","empstart"+response.toString());
                if(response.toString().trim().equalsIgnoreCase("no")){
                    pdialog.dismiss();
                    StringRequest upload=new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String res) {
                            if(res.toString().trim().equalsIgnoreCase("no")){
                                Log.e("UserID","no cancel");
                            }else {
                                Log.e("UserIDnew", res);
                                if(res==""||res==null||res.equalsIgnoreCase("no")){
                                    Log.e("UserID","empty"+res);
                                }else {
                                    Log.e("UserIDnew", res);
                                    String[] array = res.split(",");

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(MYUSERID,array[0]);
                                    editor.putString(USERNAME,array[1]);
                                    editor.putString(USERIMAGE,array[2]);
                                    editor.putString(USERMAILID,array[3].toString().trim());
                                    Log.e("EMAIL,",array[3].toString());
                                    editor.commit();
                                    if (sharedpreferences.contains(USERNAME)) {
                                        // name.setText(sharedpreferences.getString(Name, ""));
                                        username=sharedpreferences.getString(USERNAME,"");
                                        // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
                                    }
                                    if (sharedpreferences.contains(USERIMAGE)) {
                                        // name.setText(sharedpreferences.getString(Name, ""));
                                        userimage=sharedpreferences.getString(USERIMAGE,"");
                                        // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
                                    }
                                    if (sharedpreferences.contains(USERMAILID)) {
                                        // name.setText(sharedpreferences.getString(Name, ""));
                                        user_email=sharedpreferences.getString(USERMAILID,"");
                                        // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
                                    }
                                    Log.e("EMAIL,","data"+user_email+userimage+username);
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
                        protected Map<String ,String> getParams()throws AuthFailureError{
                          Map<String,String> params=new Hashtable<String, String>();

                            params.put(KEY_EMAIL,acct.getEmail());
                            params.put(KEY_NAME,acct.getDisplayName());
                            params.put(KEY_PROFILEIMAGE,acct.getPhotoUrl().toString());

                            params.put(KEY_GCM,gcmids);
                            return  params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(GoogleSignintwo.this);


                    //Adding request to the queue
                    requestQueue.add(upload);

                }else {
                    pdialog.dismiss();
                    if(response==""||response==null){
                        Log.e("UserID","emptycheck"+response);
                    }else {
                        Log.e("UserIDcheck","no cancel");
                        Log.e("UserIDcheck", response);
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
                protected Map<String,String> getParams() throws AuthFailureError{
                    Map<String ,String> params=new Hashtable<String, String>();
Log.e("Email",gcmids);
                    params.put(EMAILID_CHECK,acct.getEmail());
                    params.put(KEY_GCM,gcmids);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(GoogleSignintwo.this);


            //Adding request to the queue
            requestQueue.add(signintwo);
*/
            StringRequest upload=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String res) {
                    if(res.toString().trim().equalsIgnoreCase("no")){
                        Log.e("UserID","no cancel");
                    }else {
                        Log.e("UserIDnew", res);
                        if(res==""||res==null||res.equalsIgnoreCase("no")){
                            Log.e("UserID","empty"+res);
                        }else {
                            Log.e("UserIDnew", res);
                            String[] array = res.split(",");
                            try {
                                JSONObject data = new JSONObject(res);
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


                            if (sharedpreferences.contains(USERNAME)) {
                                // name.setText(sharedpreferences.getString(Name, ""));
                                username=sharedpreferences.getString(USERNAME,"");
                                // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
                            }
                            if (sharedpreferences.contains(USERIMAGE)) {
                                // name.setText(sharedpreferences.getString(Name, ""));
                                userimage=sharedpreferences.getString(USERIMAGE,"");
                                // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
                            }
                            if (sharedpreferences.contains(USERMAILID)) {
                                // name.setText(sharedpreferences.getString(Name, ""));
                                user_email=sharedpreferences.getString(USERMAILID,"");
                                // Toast.makeText(SigninComplete.this, sharedpreferences.getString(GcmId,""), Toast.LENGTH_SHORT).show();
                            }
                            Log.e("EMAIL,","data"+user_email+userimage+username);
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
                protected Map<String ,String> getParams()throws AuthFailureError{
                    Map<String,String> params=new Hashtable<String, String>();
                    params.put("Key", "Simplicity");
                    params.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                    params.put("rtype", "user_login");
                    params.put(KEY_EMAIL,acct.getEmail());
                    params.put(KEY_NAME,acct.getDisplayName());
                    params.put(KEY_PROFILEIMAGE,acct.getPhotoUrl().toString());
                    params.put(KEY_GCM,gcmids);
                    Log.e("Email",acct.getEmail()+acct.getDisplayName()+acct.getPhotoUrl());
                    return  params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(GoogleSignintwo.this);


            //Adding request to the queue
            requestQueue.add(upload);


        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


}

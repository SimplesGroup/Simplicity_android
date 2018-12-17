package simplicity_an.simplicity_an;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.onesignal.OneSignal;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.Walkthrough.Aboutapp;


/**
 * Created by kuppusamy on 12/2/2015.
 */
public class SplashScreen extends Activity{

  PackageInfo info;
    SharedPreferences sharedpreferences;
    private PrefManager prefManager;
    public static final String GcmId = "gcmid";
    public static final String MYUSERID= "myprofileid";
    public static final String FONT= "font";
    private static int SPLASH_TIME_OUT = 3000;
    public static final String backgroundcolor = "color";
    public static final String Language = "lamguage";
    public static final String mypreference = "mypref";
    String colorcodes,language_data,radio_button_values;
    String url_change_lang="http://simpli-city.in/request2.php?rtype=updatelanguage&key=simples";
    String playerid;
    Dialog dialog;
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
         /*try {
            info = getPackageManager().getPackageInfo("simplicity_an.simplicity_an", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }*/
    requestWindowFeature(Window.FEATURE_NO_TITLE);
     //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreentwo);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        language_data=sharedpreferences.getString(Language,"");
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                Log.e("ColorCodes",userId);
                SharedPreferences.Editor gcmidseditor=sharedpreferences.edit();
                gcmidseditor.putString(GcmId,userId);
                gcmidseditor.commit();
                playerid=userId;
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

            }
        });
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                try {
                    prefManager = new PrefManager(getApplicationContext());
                    if (!prefManager.isFirstTimeLaunch()) {
                        Log.e("RES","hi");
                        if(language_data!=null){
                            if(language_data.equals("English")) {
                               Intent i = new Intent(SplashScreen.this, MainPageEnglish.class);
                                startActivity(i);
                                finish();
                            }else {
                                Intent i = new Intent(SplashScreen.this, MainPageTamil.class);
                                startActivity(i);
                                finish();
                            }
                        }


                    }else {

                        Intent i = new Intent(SplashScreen.this, Aboutapp.class);
                        startActivity(i);
                        finish();


                       /* Log.e("RES","hid");
                         dialog = new Dialog(SplashScreen.this);
                        dialog.setContentView(R.layout.themeandlaguageselect);

                        ImageButton colorone = (ImageButton) dialog.findViewById(R.id.color);
                        ImageButton colortwo = (ImageButton) dialog.findViewById(R.id.color2);
                        ImageButton colorthree = (ImageButton) dialog.findViewById(R.id.color3);
                        ImageButton colorfour = (ImageButton) dialog.findViewById(R.id.color4);
                        ImageButton colorfive = (ImageButton) dialog.findViewById(R.id.color5);
                        ImageButton colorsix = (ImageButton) dialog.findViewById(R.id.color6);
                        ImageButton colorseven = (ImageButton) dialog.findViewById(R.id.color7);
                        ImageButton coloreight= (ImageButton) dialog.findViewById(R.id.color8);
                        ImageButton colornine = (ImageButton) dialog.findViewById(R.id.color9);
                        ImageButton colorten = (ImageButton) dialog.findViewById(R.id.color10);
                        ImageButton coloreleven = (ImageButton) dialog.findViewById(R.id.color11);
                        ImageButton colortwelve = (ImageButton) dialog.findViewById(R.id.color12);
                        ImageButton colorthirteen = (ImageButton) dialog.findViewById(R.id.color13);
                        Button closebutton=(Button)dialog.findViewById(R.id.close_button);
                        Button continue_button=(Button)dialog.findViewById(R.id.continue_button);
                        final RadioGroup radioGroup = (RadioGroup)dialog. findViewById(R.id.radiogroup);
                        colorone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                               editor.putString(backgroundcolor, "#262626");
                                editor.commit();

                            }
                        });
                        colortwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#59247c"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#59247c");


                                editor.commit();

                            }
                        });
                        colorthree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#1d487a"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#1d487a");
                                editor.commit();

                            }
                        });
                        colorfour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#7A4100"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#7A4100");
                                editor.commit();

                            }
                        });
                        colorfive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#6E0138"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#6E0138");

                                editor.commit();
                            }
                        });
                        colorsix.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#00BFD4"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#00BFD4");

                                editor.commit();
                            }
                        });
                        colorseven.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#185546"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#185546");

                                editor.commit();
                            }
                        });
                        coloreight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#D0A06F"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#D0A06F");

                                editor.commit();
                            }
                        });
                        colornine.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#82C6E6"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#82C6E6");

                                editor.commit();
                            }
                        });
                        colorten.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#339900"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#339900");

                                editor.commit();
                            }
                        });
                        coloreleven.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#CC9C00"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#CC9C00");

                                editor.commit();
                            }
                        });
                        colortwelve.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#00B09B"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};
                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#00B09B");

                                editor.commit();
                            }
                        });
                        colorthirteen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int[] colors = {Color.parseColor("#FFFFFFFF"), Color.parseColor("#FFFAF6F6")};
                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        colors);
                                gd.setCornerRadius(0f);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(backgroundcolor, "#FFFFFFFF");

                                editor.commit();
                            }
                        });


                        closebutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prefManager.setFirstTimeLaunch(false);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Language, "English");

                                editor.commit();
                                if(playerid!=null){
                                    StringRequest language=new StringRequest(Request.Method.POST, url_change_lang, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.e("CHANGE LAMG",response.toString());
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String,String>params=new Hashtable<String, String>();
                                            params.put("playerid",playerid);
                                            params.put("language","1");
                                            return params;
                                        }
                                    };
                                    RequestQueue likesqueue= Volley.newRequestQueue(getApplicationContext());
                                    language.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    likesqueue.add(language);
                                }else {

                                }
                                dialog.dismiss();
                               Intent i = new Intent(SplashScreen.this, MainPageEnglish.class);
                                startActivity(i);
                                finish();
                            }
                        });
                        continue_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prefManager.setFirstTimeLaunch(false);
                                int selected=radioGroup.getCheckedRadioButtonId();
                                RadioButton new_radiobutton=(RadioButton)dialog.findViewById(selected);
                                radio_button_values=new_radiobutton.getText().toString();
                                //Toast.makeText(getApplicationContext(),radio_button_values, Toast.LENGTH_LONG).show();
                              if(radio_button_values.equals("Tamil")){
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Language, "Tamil");

                                    editor.commit();
                                  if(playerid!=null){
                                      StringRequest language=new StringRequest(Request.Method.POST, url_change_lang, new Response.Listener<String>() {
                                          @Override
                                          public void onResponse(String response) {
                                              Log.e("CHANGE LAMG",response.toString());
                                          }
                                      }, new Response.ErrorListener() {
                                          @Override
                                          public void onErrorResponse(VolleyError error) {

                                          }
                                      }){
                                          @Override
                                          protected Map<String, String> getParams() throws AuthFailureError {

                                              Map<String,String>params=new Hashtable<String, String>();
                                              params.put("playerid",playerid);
                                              params.put("language","2");
                                              return params;
                                          }
                                      };
                                      RequestQueue likesqueue= Volley.newRequestQueue(getApplicationContext());
                                      language.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                      likesqueue.add(language);
                                  }
                                   Intent i = new Intent(SplashScreen.this, MainPageTamil.class);
                                    startActivity(i);
                                    finish();
                                }else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Language, "English");

                                    editor.commit();
                                  if(playerid!=null){
                                      StringRequest language=new StringRequest(Request.Method.POST, url_change_lang, new Response.Listener<String>() {
                                          @Override
                                          public void onResponse(String response) {
                                              Log.e("CHANGE LAMG",response.toString());
                                          }
                                      }, new Response.ErrorListener() {
                                          @Override
                                          public void onErrorResponse(VolleyError error) {

                                          }
                                      }){
                                          @Override
                                          protected Map<String, String> getParams() throws AuthFailureError {

                                              Map<String,String>params=new Hashtable<String, String>();
                                              params.put("playerid",playerid);
                                              params.put("language","1");
                                              return params;
                                          }
                                      };
                                      RequestQueue likesqueue= Volley.newRequestQueue(getApplicationContext());
                                      language.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                      likesqueue.add(language);
                                  }
                                   Intent i = new Intent(SplashScreen.this, MainPageEnglish.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });

                        dialog.show();*/

                    }

                } catch (Exception e) {

                }
            }
        }, SPLASH_TIME_OUT);
    }



}
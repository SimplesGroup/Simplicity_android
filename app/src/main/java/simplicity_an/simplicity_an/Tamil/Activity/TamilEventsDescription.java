package simplicity_an.simplicity_an.Tamil.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.AppControllers;
import simplicity_an.simplicity_an.DividerItemDecoration;
import simplicity_an.simplicity_an.EventsDescription;
import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.Utils.Configurl;


/**
 * Created by kuppusamy on 2/12/2016.
 */
public class TamilEventsDescription extends AppCompatActivity {
    TextView title,title_qype,eventdetail,eventdetaildata,venueandcontact;
    TextView venuedetails,timing,timingdetails,date,datedetails;
    TextView contactname,contactnamedetails,phone,phonenumberdetails,email,emaildetails,venue_text,location_text,website_text,location_details,website_details;
    NetworkImageView thump;
    WebView description;
    Button booknow;
    ImageButton comment,share,menu,back,favourite,remindme;
    String titl,bitmap,venue,locations;
    String entrytype,entryfees;
ProgressDialog pdialog;
    long startTime,endTime;
    Calendar cal;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=event&key=simples&id=";
    String URLTWO;
    private final String TAG_REQUEST = "MY_TAG";
    String event_title,event_place,event_startdate,event_enddate;
    String URLFAV="http://simpli-city.in/request2.php?rtype=addfav&key=simples";
    String notifiid,myprofileid,shareurl,sharetitle;
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "id";
    private String KEY_QTYPE= "qtype";

    int post_likes_count,favcount;
    RequestQueue queue,requestQueue;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    public static final String MYACTIVITYSEARCH= "myactivitysearch";
    String contentid,activity,searchnonitiid,searchactivity_event;


    List<ItemModels> commentlist = new ArrayList<ItemModels>();


    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO_comment;
    String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=event&id=";
    String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";

    RecyclerView recycler;
    LinearLayoutManager mLayoutManager;
    String postid, myuserid;
    LinearLayout commentbox;
    TextView comment_title,loadmore_title,textview_organizedby;
    EditText commentbox_editext;
    Button post;
    RecyclerView recycler_comment;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii, i;
    private String KEY_COMMENT = "comment";
    private String KEY_TYPE = "qtype";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    String description_comment,my_profilename,my_profileimage;

    String URLLIKES="http://simpli-city.in/request2.php?rtype=articlelikes&key=simples";
    public static final String USERID="user_id";
    public static final String QID="qid";
    public static final String QTYPE="qtype";
    ScrollView scrollView;

    public static final String backgroundcolor = "color";
    RelativeLayout mainlayout;
    String colorcodes;
    LinearLayout commentboxlayout;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.eventsdesc);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        searchactivity_event=sharedpreferences.getString(MYACTIVITYSEARCH,"");
        contentid=sharedpreferences.getString(CONTENTID,"");
        activity=sharedpreferences.getString(Activity,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        if (sharedpreferences.contains(USERNAME)) {

            my_profilename=sharedpreferences.getString(USERNAME,"");

        }
        if (sharedpreferences.contains(USERIMAGE)) {

            my_profileimage=sharedpreferences.getString(USERIMAGE,"");

        }

        colorcodes=sharedpreferences.getString(backgroundcolor,"");

        mainlayout=(RelativeLayout)findViewById(R.id.version_main_layout);

        commentboxlayout = (LinearLayout)findViewById(R.id.commentbox_city);
        back = (ImageButton)findViewById(R.id.btn_back);
        if(colorcodes.equals("#FFFFFFFF")){
            commentboxlayout.setBackgroundColor(Color.WHITE);
            back.setImageResource(R.mipmap.backtamilone);
        }
        else{
            commentboxlayout.setBackgroundColor(Color.BLACK);
            back.setImageResource(R.mipmap.back);
        }


        if(colorcodes.length()==0){
            int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);

            mainlayout.setBackgroundDrawable(gd);

            SharedPreferences.Editor editor = sharedpreferences.edit();
           editor.putString(backgroundcolor, "#262626");
            editor.commit();
        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi"+colorcodes);
                int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);

                SharedPreferences.Editor editor = sharedpreferences.edit();
               editor.putString(backgroundcolor, "#262626");
                editor.commit();
            }else {

                if(colorcodes!=null){
                    if(colorcodes.equals("#FFFFFFFF")){
                        int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FFFFFFFF")};

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

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                       editor.putString(backgroundcolor, "#262626");

                        editor.commit();
                    }
                }else {
                    int[] colors = {Color.parseColor("#262626"),Color.parseColor("#00000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                   editor.putString(backgroundcolor, "#262626");

                    editor.commit();
                }
            }
        }

        requestQueue=Volley.newRequestQueue(this);

        if(activity==null){
            Intent getnotifi=getIntent();
            notifiid=getnotifi.getStringExtra("ID");
            searchnonitiid = getnotifi.getStringExtra("IDSEARCH");
            notifiid = notifiid.replaceAll("\\D+","");
            Log.e("ID",notifiid);

        } else {
            if(activity.equalsIgnoreCase("eventdesctamil")){
                notifiid=contentid;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(Activity);
                editor.remove(CONTENTID);
                editor.apply();

            }else {
                Intent getnotifi=getIntent();
                notifiid=getnotifi.getStringExtra("ID");
                searchnonitiid = getnotifi.getStringExtra("IDSEARCH");
                notifiid = notifiid.replaceAll("\\D+","");
                Log.e("ID",notifiid);
            }
        }

        if(myprofileid!=null){
            URLTWO=URL+notifiid+"&user_id="+myprofileid;
        }else {
            URLTWO=URL+notifiid;
        }

        booknow = (Button) findViewById(R.id.booknow_button);
booknow.setText("பதிவு செய்ய");

         queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        commentbox=(LinearLayout)findViewById(R.id.comments_versiontwo) ;

        scrollView=(ScrollView)findViewById(R.id.scroll);



        title = (TextView) findViewById(R.id.textView_titlename);
        title_qype=(TextView)findViewById(R.id.textView_qtypename) ;

        textview_organizedby=(TextView)findViewById(R.id.text_org);

        eventdetaildata=(TextView)findViewById(R.id.eventdetaildata);
        description=(WebView)findViewById(R.id.webview_eventdescription);
        description.getSettings().setLoadsImagesAutomatically(true);
        description.getSettings().setPluginState(WebSettings.PluginState.ON);
        description.getSettings().setAllowFileAccess(true);
        description.getSettings().setJavaScriptEnabled(true);
      venue_text=(TextView)findViewById(R.id.text_eventvenue);
        location_text=(TextView)findViewById(R.id.text_eventlocation);
        website_text=(TextView)findViewById(R.id.text_eventwebsite);
        location_details=(TextView)findViewById(R.id.text_eventlocation_details);
        website_details=(TextView)findViewById(R.id.text_eventwebsite_details);
        venueandcontact=(TextView)findViewById(R.id.eventtitle);
        venueandcontact.setText("இடம்/தொடர்புக்கான விவரம்");
        eventdetaildata=(TextView)findViewById(R.id.eventdetaildata);
        venuedetails=(TextView)findViewById(R.id.text_eventvenue_details);
        timing=(TextView)findViewById(R.id.text_timing_event);
        timingdetails=(TextView)findViewById(R.id.text_timing_event_data);
        date=(TextView)findViewById(R.id.text_date_event);
        datedetails=(TextView)findViewById(R.id.text_date_event_data);
        contactname=(TextView)findViewById(R.id.text_eventontactname);
        remindme=(ImageButton)findViewById(R.id.btn_remider);

        contactnamedetails=(TextView)findViewById(R.id.text_eventontactname_details);
        phone=(TextView)findViewById(R.id.text_eventphonenumber);
        phonenumberdetails=(TextView)findViewById(R.id.text_eventphonenumber_details);
       email=(TextView)findViewById(R.id.text_eventemail);
        emaildetails=(TextView)findViewById(R.id.text_eventemail_details);

        description=(WebView)findViewById(R.id.webview_eventdescription);
        description.getSettings().setLoadsImagesAutomatically(true);
        description.getSettings().setPluginState(WebSettings.PluginState.ON);
        description.getSettings().setAllowFileAccess(true);
        description.getSettings().setJavaScriptEnabled(true);

       /* Resources res = getResources();
        float  fontSize = res.getDimension(R.dimen.txtSize);
        description.getSettings().setDefaultFontSize((int)fontSize);*/
        comment=(ImageButton)findViewById(R.id.btn_4);
        share=(ImageButton)findViewById(R.id.btn_share);
        menu=(ImageButton)findViewById(R.id.btn_3);
        back=(ImageButton)findViewById(R.id.btn_back);
        favourite=(ImageButton)findViewById(R.id.btn_like);
        String simplycity_title_fontPath = "fonts/TAU_Elango_Madhavi.TTF";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        String simplycity_title_bold = "fonts/TAU_Elango_Madhavi.TTF";
        Typeface tf_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_bold);

        venue_text.setTypeface(tf);
        location_text.setTypeface(tf);
        website_text.setTypeface(tf);

        if(colorcodes.equals("#FFFFFFFF")){
            venue_text.setTextColor(Color.BLACK);
//            comment_title.setTextColor(Color.BLACK);
            // loadmore_title.setTextColor(Color.BLACK);
            // post.setTextColor(Color.BLACK);
            location_text.setTextColor(Color.BLACK);
            website_text.setTextColor(Color.BLACK);
            venue_text.setTextColor(Color.BLACK);
            title.setTextColor(Color.BLACK);
            title_qype.setTextColor(Color.BLACK);
            eventdetaildata.setTextColor(Color.BLACK);
            venuedetails.setTextColor(Color.BLACK);
            timing.setTextColor(Color.BLACK);
            timingdetails.setTextColor(Color.BLACK);
            date.setTextColor(Color.BLACK);
            date.setTextColor(Color.BLACK);
            datedetails.setTextColor(Color.BLACK);
            contactname.setTextColor(Color.BLACK);
            booknow.setTextColor(Color.BLACK);
            contactnamedetails.setTextColor(Color.BLACK);
            email.setTextColor(Color.BLACK);
            emaildetails.setTextColor(Color.BLACK);
            phone.setTextColor(Color.BLACK);
            phonenumberdetails.setTextColor(Color.BLACK);
            location_details.setTextColor(Color.BLACK);
            website_details.setTextColor(Color.BLACK);
            venueandcontact.setTextColor(Color.BLACK);

            date.setBackgroundResource(R.drawable.whiteback);
            datedetails.setBackgroundResource(R.drawable.whiteback);
            timing.setBackgroundResource(R.drawable.whiteback);
            timingdetails.setBackgroundResource(R.drawable.whiteback);
            venueandcontact.setBackgroundResource(R.drawable.whiteback);
            textview_organizedby.setTextColor(Color.BLACK);
        }
        else{
            venue_text.setTextColor(Color.WHITE);
            //comment_title.setTextColor(Color.WHITE);
            //loadmore_title.setTextColor(Color.WHITE);
            // post.setTextColor(Color.WHITE);
            location_text.setTextColor(Color.WHITE);
            website_text.setTextColor(Color.WHITE);
            venue_text.setTextColor(Color.WHITE);
            title.setTextColor(Color.WHITE);
            title_qype.setTextColor(Color.WHITE);
            eventdetaildata.setTextColor(Color.WHITE);
            venuedetails.setTextColor(Color.WHITE);
            timing.setTextColor(Color.WHITE);
            timingdetails.setTextColor(Color.WHITE);
            date.setTextColor(Color.WHITE);
            date.setTextColor(Color.WHITE);
            datedetails.setTextColor(Color.WHITE);
            contactname.setTextColor(Color.WHITE);
            booknow.setTextColor(Color.WHITE);
            contactnamedetails.setTextColor(Color.WHITE);
            email.setTextColor(Color.WHITE);
            emaildetails.setTextColor(Color.WHITE);
            phone.setTextColor(Color.WHITE);
            phonenumberdetails.setTextColor(Color.WHITE);
            location_details.setTextColor(Color.WHITE);
            website_details.setTextColor(Color.WHITE);
            venueandcontact.setTextColor(Color.WHITE);
           // venueandcontact.setBackgroundColor(R.color.eventcolor);
            textview_organizedby.setTextColor(Color.WHITE);
        }

        title.setTypeface(tf_bold);
        textview_organizedby.setTypeface(tf);
        eventdetaildata.setTypeface(tf);
       venuedetails.setTypeface(tf);
       timing.setTypeface(tf);
        timing.setText("நேரம்");
       timingdetails.setTypeface(tf);
      date.setTypeface(tf);
        date.setText("தேதி");
      datedetails.setTypeface(tf);
        contactname.setTypeface(tf);
        booknow.setTypeface(tf);

        contactnamedetails.setTypeface(tf);
        email.setTypeface(tf);

        emaildetails.setTypeface(tf);
        phone.setTypeface(tf);

        location_details.setTypeface(tf);
        website_details.setTypeface(tf);
        phonenumberdetails.setTypeface(tf);
        thump=(NetworkImageView)findViewById(R.id.photo_image);
        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(notifiid!=null) {
            StringRequest jsonreq = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {


                public void onResponse(String response) {
                    Log.e("Response",response.toString());
                    try{
                        JSONObject object=new JSONObject(response.toString());
                        JSONArray array=object.getJSONArray("result");
                        String data=array.optString(1);
                        JSONArray jsonArray=new JSONArray(data.toString());
                        Log.e("Response",data.toString());
                        if (response != null) {
                            pdialog.dismiss();
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
                    param.put("Key","Simplicity");
                    param.put("Token","8d83cef3923ec6e4468db1b287ad3fa7");
                    param.put("language","2");
                    param.put("rtype","event");
                    param.put("id",notifiid);
                    if(myprofileid!=null){
                        param.put("user_id",myprofileid);
                    }else {

                    }
                    return param;
                }
            };

            jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonreq);
        }
        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getApplicationContext(), R.style.MyDialogTheme);

                // set title
                // alertDialogBuilder.setTitle("Your Title");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Booking not Available for this event")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        }).create();

                // create alert dialog
               // AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialogBuilder.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menueventdesc = new Intent(getApplicationContext(), MainPageTamil.class);
                startActivity(menueventdesc);
                finish();
            }
        });




        remindme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                cal = Calendar.getInstance();
                try {
                    Date date = new SimpleDateFormat("MMMM dd, yyyy").parse(event_startdate);
                    startTime=date.getTime();
                    //startTime=date.setHours(4);


                }
                catch(Exception e){ }
                try {
                    Date date = new SimpleDateFormat("MMMM dd, yyyy").parse(event_enddate);
                    endTime=date.getTime();
                }
                catch(Exception e){ }

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);

                intent.putExtra(CalendarContract.Events.TITLE, event_title);
                //intent.putExtra(CalendarContract.Events.DESCRIPTION,  "This is a sample description");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, event_place);
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                startActivity(intent);
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void AddnewCommnent(){

        ItemModels models=new ItemModels();
        models.setName(my_profilename);
        models.setProfilepic(my_profileimage);
        models.setComment(description_comment);
        commentlist.add(models);

    }
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
        }
    }
    private void parseJsonFeed(JSONArray response) {
        ImageLoader mImageLoader;

        mImageLoader = simplicity_an.simplicity_an.MySingleton.getInstance(getApplicationContext()).getImageLoader();
        try {
            //  JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < response.length(); i++) {
                final JSONObject obj = (JSONObject) response.get(i);
                ItemModel model=new ItemModel();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);
                String venue = obj.isNull("venue") ? null : obj
                        .getString("venue");
                model.setVenue(venue);
                String locations = obj.isNull("location") ? null : obj
                        .getString("location");
                model.setLocation(locations);
                String contactwebsite = obj.isNull("contact_website") ? null : obj
                        .getString("contact_website");
                model.setContactwebsite(contactwebsite);
                String organizedby = obj.isNull("organized_by") ? null : obj
                        .getString("organized_by");
                model.setOrganizedby(organizedby);
              /*  String entrytype = obj.isNull("etype") ? null : obj
                        .getString("etype");
                model.setEtype(entrytype);
                String entryfees = obj.isNull("emt") ? null : obj
                        .getString("emt");
                model.setEmt(entryfees);*/
                // bimage=obj.isNull("bimage")?null:obj.getString("bimage");
                // model.setBimage(bimage);
                // model.setName(obj.getString("pub_by"));
                model.setDescription(obj.getString("description"));
                // model.setTypeid(obj.getInt("type"));
                // model.setPdate(obj.getString("date"));
                model.setTitle(obj.getString("title"));
                model.setEventstartdate(obj.getString("event_start_date"));
                model.setEventenddate(obj.getString("event_end_date"));
                model.setContactname(obj.getString("contact_name"));
                model.setVenue(obj.getString("venue"));
                model.setLocation(obj.getString("location"));
                model.setTiming(obj.getString("timing"));
                // model.setOrganizedby(obj.getString("organized_by"));
                //  model.setEtype(obj.getString("etype"));
                //  model.setEmt(obj.getString("emt"));
                model.setContactphone(obj.getString("contact_phone"));
                model.setContactemail(obj.getString("contact_email"));
               // model.setContactwebsite(obj.getString("contact_website"));
                JSONArray array=obj.getJSONArray("amt");
                JSONObject object=null;
                for(int n = 0; n < array.length(); n++)
                {
                    object = array.getJSONObject(n);
                    // do some stuff....
                }

                thump.setImageUrl(image, mImageLoader);
                //  Picasso.with(getApplicationContext()).load(obj.getString("image")).into(thump);
                title.setText(Html.fromHtml(obj.getString("title")));
                event_title = obj.getString("title");
                event_place = obj.getString("venue");
                event_startdate = obj.getString("event_start_date");
                event_enddate = obj.getString("event_end_date");
                textview_organizedby.setText("Organized by:"+obj
                        .getString("organized_by"));
                if (organizedby.equals("")||organizedby.equals("null")) {


                    if (object.getString("amount").contentEquals("0")) {
                        eventdetaildata.setText(Html.fromHtml("Entry Type"+"&nbsp;"+":" +"&nbsp;"+ "Free"));
                    } else {
                        eventdetaildata.setText(Html.fromHtml("Entry Type"+"&nbsp;"+ ":"+ "&nbsp;"+"Paid" + "\n" + "Entry Fee" + object.getString("amount")));

                    }

                } else {
                    eventdetaildata.setVisibility(View.VISIBLE);
                    if (object.getString("amount").contentEquals("0")) {
                        eventdetaildata.setText(Html.fromHtml("Entry Type"+"&nbsp;"+":" +"&nbsp;"+ "Free"));
                    } else {
                        eventdetaildata.setText(Html.fromHtml("Entry Type"+"&nbsp;"+ ":"+ "&nbsp;"+"Paid" + "\n" + "Entry Fee" + object.getString("amount")));

                    }
                   /* if (entrytype != null) {
                        if (obj.getString("etype").contentEquals("paid")) {
                            eventdetaildata.setText(Html.fromHtml("Organized by"+"&nbsp;"+":"+"&nbsp;" + organizedby+"\n"+"Entry Type"+"&nbsp;"+":"+"&nbsp;"  + entrytype + "\n" + "Entry Fee"+"&nbsp;"+":"+"&nbsp;"  + obj.getString("emt")));
                        } else {
                            eventdetaildata.setText(Html.fromHtml("Organized by"+"&nbsp;"+":"+"&nbsp;"  + organizedby+"\n"+"Entry Type"+"&nbsp;"+":"+"&nbsp;"  + entrytype));
                        }
                    } else {
                        eventdetaildata.setVisibility(View.GONE);
                    }*/

                }
                textview_organizedby.setText("ஒருங்கிணைப்புக் குழு:"+obj
                        .getString("organized_by"));
                String descrition = obj.isNull("description") ? null : obj
                        .getString("description");
                String ss = descrition;
                String s = ss;
                // s = s.replace("\"", "'");
                s = s.replace("\\", "");

                // description.setText(Html.fromHtml(s));

                //description.loadData(String.format(descrip), "text/html", "utf-8");
                //description.loadData(descrip,"text/html","utf-8");
              // String fonts="<html>\n" +         "\t<head>\n" +         "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +         "\t\t<style>\n" +         "\t\t@font-face {\n" +         "  font-family: 'segeoui-light';\n" +         " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "  font-family: 'segeoui-regular';\n" +         "src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "  font-family: 'segeoui-sbold';\n" +         " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Bold';\n" +         "   src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Light';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Regular';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Thin';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "\t\t</style>\n" +         "\t</head>"; description.loadDataWithBaseURL("", fonts+descrition+"</head>", "text/html", "utf-8", "");
                // description.setBackgroundColor(0x0a000000);
                description.setBackgroundColor(Color.TRANSPARENT);

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                Typeface tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                String fonts="<html>\n" +
                        "\t<head>\n" +
                        "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +
                        "\t\t<style>\n" +
                        "\t\t@font-face {\n" +
                        "  font-family: 'segeoui-light';\n" +
                        " src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "  font-family: 'segeoui-regular';\n" +
                        "src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "  font-family: 'segeoui-sbold';\n" +
                        " src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "  font-style: normal;\n" +
                        "}\n" +
                        "\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Bold';\n" +
                        "   src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Light';\n" +
                        "    src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Regular';\n" +
                        "    src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "@font-face {\n" +
                        "    font-family: 'RobotoSlab-Thin';\n" +
                        "    src: url('file:///android_asset/fonts/robotoSlabRegular.ttf');\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "\t\t</style>\n" +
                        "\t</head>";
                String rep = String.valueOf(descrition);
                rep =  rep.replaceAll("color:#fff","color:#000");

                if(colorcodes.equals("#FFFFFFFF")) {
                    description.loadDataWithBaseURL("", fonts + rep + "</head>", "text/html", "utf-8", "");
                }else{
                    description.loadDataWithBaseURL("", fonts + descrition + "</head>", "text/html", "utf-8", "");
                }                // description.setBackgroundColor(0x0a000000);
                description.setBackgroundColor(Color.TRANSPARENT);
                description.setWebViewClient(new MyBrowser());
                venuedetails.setText(venue);
                location_details.setText(obj.getString("location"));
                website_details.setText(obj.getString("contact_website"));
                timing.setText("Timing");
                date.setText("Date");
                timingdetails.setText(obj.getString("timing"));
                datedetails.setText(event_startdate + "-" + event_enddate);
                if (obj.getString("contact_name").equals("") || obj.getString("contact_name").equals("null")) {
                    contactname.setVisibility(View.GONE);
                    contactnamedetails.setVisibility(View.GONE);
                } else {
                    contactname.setText("Name:");
                    contactnamedetails.setText(obj.getString("contact_name"));
                }
                if (obj.getString("contact_email").equals("") || obj.getString("contact_email").equals("null")) {
                    emaildetails.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                } else {
                    email.setText("Email:");
                    emaildetails.setText(obj.getString("contact_email"));
                }

                if (obj.getString("contact_phone").equals("") || obj.getString("contact_phone").equals("null")) {
                    phonenumberdetails.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                } else {
                    phone.setText("Phone:");
                    phonenumberdetails.setText(obj.getString("contact_phone"));
                }
                if (obj.getString("venue").equals("") || obj.getString("venue").equals("null")) {
                    venue_text.setVisibility(View.GONE);
                    venuedetails.setVisibility(View.GONE);
                } else {
                    venue_text.setText("Venue:");
                    venuedetails.setText(obj.getString("venue"));
                }
                if (obj.getString("contact_website").equals("") || obj.getString("contact_website").equals("null")) {
                    website_text.setVisibility(View.GONE);
                    website_details.setVisibility(View.GONE);
                } else {
                    website_text.setText("Website:");
                    website_details.setText(obj.getString("contact_website"));
                }
                if (obj.getString("location").equals("") || obj.getString("location").equals("null")) {
                    location_text.setVisibility(View.GONE);
                    location_details.setVisibility(View.GONE);
                } else {
                    location_text.setText("LocationSelection:");
                    location_details.setText(obj.getString("location"));
                }


                model.setFavcount(obj.getInt("like_type"));
                model.setShareurl(obj.getString("sharingurl"));
                favcount = obj.getInt("like_type");
                post_likes_count = obj.getInt("like_type");
                shareurl = obj.getString("sharingurl");
                sharetitle = obj.getString("title");


                 if (favcount == 1) {                     favourite.setImageResource(R.mipmap.likered);                     favourite.setTag("heartfullred");                 } else {                    favourite.setImageResource(R.mipmap.like);                     favourite.setTag("heart");                 }
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(myprofileid!=null) {
                            String backgroundImageName = String.valueOf(favourite.getTag());
                            Log.e("RUN","with"+backgroundImageName);
                            if(backgroundImageName.equals("heart")){
                                favourite.setImageResource(R.mipmap.likered);
                                favourite.setTag("heartfullred");
                            }else if(backgroundImageName.equals("heartfullred")) {
                                favourite.setImageResource(R.mipmap.like);
                                favourite.setTag("heart");
                            }else {

                            }
                            StringRequest likes=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String res;
                                    Log.e("RES",response.toString());
                                    try {
                                        Log.e("RES", "START");

                                        JSONObject object=new JSONObject(response.toString());
                                        JSONArray array=object.getJSONArray("result");
                                        String data=array.optString(1);
                                        JSONArray jsonArray=new JSONArray(data.toString());


                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject obj = (JSONObject) jsonArray.get(i);
                                            String dirs = obj.getString("like_type");

                                            Log.d("RES", dirs);
                                            res=object.getString("like_type");

                                            Log.e("RES",res.toString());


                                            if(res.equals("Liked")){

                                                favourite.setImageResource(R.mipmap.heartfullred);
                                                favourite.setTag("heartfullred");
                                            }else if(res.equals("Like")){



                                                favourite.setImageResource(R.mipmap.heart);
                                                favourite.setTag("heart");
                                            }





                                        }

                                    }catch (JSONException e){

                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                protected Map<String,String> getParams()throws AuthFailureError{
                                    Map<String,String> param=new Hashtable<String, String>();
                                    String type=null;
                                    try {
                                        type=obj.getString("qtypemain");
                                    }catch (JSONException e){

                                    }

                                    param.put("Key","Simplicity");
                                    param.put("Token","8d83cef3923ec6e4468db1b287ad3fa7");
                                    param.put("rtype","like");
                                    param.put("id", notifiid);
                                    param.put("user_id", myprofileid);
                                    param.put("qtype", type);
                                    return param;
                                }
                            };
                            RequestQueue likesqueue=Volley.newRequestQueue(getApplicationContext());
                            likes.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            likesqueue.add(likes);

                        }else {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Activity, "eventdesctamil");
                            editor.putString(CONTENTID, notifiid);
                            editor.commit();
                            Intent signin=new Intent(TamilEventsDescription.this,SigninpageActivity.class);
                            startActivity(signin);
                            finish();
                        }
                    }
                });
                final String number = obj.getString("contact_phone");
                phonenumberdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" +number));
                        startActivity(intent);
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, sharetitle+ "\n" + shareurl+"\n"+"\n"+"\n"+"Receive instant updates by installing Simplicity for iPhone/iPad,Android and Windows 10(desktop & Mobile)(http://goo.gl/Sv3vfc)");

                        sendIntent.setType("text/plain");
                        startActivity(Intent.createChooser(sendIntent, "Share using"));

                    }
                });


                modelList.add(model);





            }

            // notify data changes to list adapater
            //rcAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // ((EditText) getActionBar().getCustomView().findViewById(R.id.editText)).setText(url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("http://simpli")){
                Intent ads=new Intent(getApplicationContext(),AdvertisementPage.class);
                ads.putExtra("ID",url);
                startActivity(ads);
            }else {
                Intent ads=new Intent(getApplicationContext(),AdvertisementPage.class);                 ads.putExtra("ID",url);                 startActivity(ads);
                // view.loadUrl(url);
            }
            //

            return true;
        }
    }


    class ItemModel{
        private int typeid;
        private String name;
        private String image;
        private String bimage;
        private String pdate;
        private String description;
        private String title;
        private String eventstartdate;
        private String eventenddate;
        private String venue;
        private String location;
        private String timing;
        private String contactname;
        private String contactemail;
        private String contactwebsite;
        private String contactphone;
        private String etype;
        private String emt;
        private String organizedby;
        private int favcount;
        private String shareurl;

        public void setFavcount(int favcount) {
            this.favcount = favcount;
        }

        public int getFavcount() {
            return favcount;
        }

        public String getShareurl() {
            return shareurl;
        }



        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }

        public String getOrganizedby(){return organizedby;}
        public void setOrganizedby(String organizedby){this.organizedby=organizedby;}
        public String getEmt(){return emt;}
        public void setEmt(String emt){this.emt=emt;}
        public String getEtype(){return etype;}
        public void setEtype(String etype){this.etype=etype;}
        public String getVenue(){return venue;}
        public void setVenue(String venue){this.venue=venue;}
        public String getLocation(){return location;}
        public void setLocation(String location){this.location=location;}
        public String getTiming(){return timing;}
        public void setTiming(String timing){this.timing=timing;}
        public String getContactname(){return contactname;}
        public void setContactname(String contactname){this.contactname=contactname;}
        public String getContactemail(){return contactemail;}
        public void setContactemail(String contactemail){this.contactemail=contactemail;}
        public String getContactwebsite(){return contactwebsite;}
        public void setContactwebsite(String contactwebsite){this.contactwebsite=contactwebsite;}
        public String getContactphone(){return contactphone;}
        public void setContactphone(String contactphone){this.contactphone=contactphone;}
        /******** start the Food category names****/
        private String category_name;
        /******** start the Food category names****/

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
        public String getBimage() {
            return bimage;
        }
        public void setBimage(String bimage) {
            this.bimage = bimage;
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
        public String getEventstartdate(){return  eventstartdate;}

        public void setEventstartdate(String eventstartdate) {
            this.eventstartdate = eventstartdate;
        }
        public String getEventenddate(){return eventenddate;}
        public void setEventenddate(String eventenddate){
            this.eventenddate=eventenddate;
        }
        /******** start the Food category names****/
        public String getCategory_name(){return  category_name;}

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }


    class  ItemModels{
        private String id,comment,padate,name,profilepic;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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

        public String getPadate() {
            return padate;
        }

        public void setPadate(String padate) {
            this.padate = padate;
        }

        public String getProfilepic() {
            return profilepic;
        }

        public void setProfilepic(String profilepic) {
            this.profilepic = profilepic;
        }

    }


}

package simplicity_an.simplicity_an;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.MainEnglish.MainPageEnglish;

/**
 * Created by kuppusamy on 9/7/2016.
 */
public class AddNewEvent extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    TextView addposttitle;
    Button getimagepath,postevent;
    EditText title_addpost,category_addpost,venue_addpost,location_addpost,timing_addpost,organizedby,contactname,contactphone,contactemail,website_addpost,description_addpost,entrytype,feeamount;
        private static EditText eventstartdate,eventenddate;
    private String KEY_TITLE = "title";
    private String KEY_NAME="contact_name";
    private String KEY_MYUSERID="user_id";
    private String KEY_EMAIL= "contact_email";
    private String KEY_PHONE = "contact_phone";
    private String KEY_ADDRESS = "address";
    private String KEY_WEB = "website";
    private String KEY_DESCRIPTION = "description";
    private String KEY_ENTRYTYPE = "etype";
    private String KEY_STARTDATE= "sdate";
    private String KEY_ENDDATE= "edate";
    private String KEY_CATLIST= "category";
    private String KEY_LOCATION= "location";
    private String KEY_VENUE= "venue";
    private String KEY_TIMING= "timing";
    private String KEY_ORGANIZEDBY= "organized_by";
    private String KEY_FEE= "fee_amount";
    private String KEY_IMAGELOC="imageloc";
    ArrayList<String> eventlist = new ArrayList<String>();
    ArrayList<String> entrytypelist = new ArrayList<String>();
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    Dialog dialogs,dialog_additional;
    String UPLOAD_URL ="http://simpli-city.in/request2.php?rtype=postevent&key=simples";
    String text="";
String entrytypevalue="";
    Bitmap bitmap,bitmapcamera;
    String ba1,image;
    String encodedImage,myprofileid;
ImageButton menus,back,addnewevent;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";

    ArrayList<String> citycategorylist = new ArrayList<String>();
    String CITY_CAT_URL="http://simpli-city.in//request2.php?rtype=eventcategory&key=simples";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.addnewevent);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

        entrytypelist.add("Paid");
        entrytypelist.add("Free");
        back=(ImageButton)findViewById(R.id.btn_1);
        menus=(ImageButton)findViewById(R.id.btn_3);
        addnewevent=(ImageButton)findViewById(R.id.btn_2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent back=new Intent(AddNewEvent.this,MainActivityVersiontwo.class);
                startActivity(back);
                finish();*/
                onBackPressed();
            }
        });
        menus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu=new Intent(AddNewEvent.this, MainPageEnglish.class);
                startActivity(menu);
                finish();
            }
        });
        JsonArrayRequest jsonReq = new JsonArrayRequest(CITY_CAT_URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        ItemModel model = new ItemModel();

                        model.setId(obj.getString("id"));
                        modelList.add(model);

                        citycategorylist.add(obj.getString("category_name"));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                // VolleyLog.d(TAG, "ERROR" + error.getMessage());
            }
        });
        AppControllers.getInstance().addToRequestQueue(jsonReq);

        addposttitle=(TextView)findViewById(R.id.add_post_your_newevent);
        getimagepath=(Button)findViewById(R.id.add_newevent_chooseimagefile);
        postevent=(Button)findViewById(R.id.add_newevent_postevent);
        title_addpost=(EditText)findViewById(R.id.add_newevent_title);
        category_addpost=(EditText)findViewById(R.id.add_newevent_category);
        venue_addpost=(EditText)findViewById(R.id.add_newevent_venue);
        location_addpost=(EditText)findViewById(R.id.add_newevent_location);
        eventstartdate=(EditText)findViewById(R.id.add_newevent_startdate);
        eventenddate=(EditText)findViewById(R.id.add_newevent_enddate);
        timing_addpost=(EditText)findViewById(R.id.add_newevent_Timing);
        organizedby=(EditText)findViewById(R.id.add_newevent_organizedby);
        contactname=(EditText)findViewById(R.id.add_newevent_contactname);
        contactemail=(EditText)findViewById(R.id.add_newevent_contactemail);
        contactphone=(EditText)findViewById(R.id.add_newevent_contactphone);
        website_addpost=(EditText)findViewById(R.id.add_newevent_contactwebsite);
        description_addpost=(EditText)findViewById(R.id.add_newevent_description);
        entrytype=(EditText)findViewById(R.id.add_newevent_entrytype);
        feeamount=(EditText)findViewById(R.id.add_newevent_feeamount);



        addposttitle.setTypeface(tf);
        addposttitle.setText("POST YOUR EVENTS"+"\n"+"HERE");
        getimagepath.setTypeface(tf);
        getimagepath.setText("Choose File");
        postevent.setTypeface(tf);
        postevent.setText("POST");

        title_addpost.setTypeface(tf);
        category_addpost.setTypeface(tf);
        venue_addpost.setTypeface(tf);
        location_addpost.setTypeface(tf);
        eventstartdate.setTypeface(tf);
        eventenddate.setTypeface(tf);
        timing_addpost.setTypeface(tf);
        organizedby.setTypeface(tf);
        contactname.setTypeface(tf);
        contactphone.setTypeface(tf);
        contactemail.setTypeface(tf);
        website_addpost.setTypeface(tf);
        description_addpost.setTypeface(tf);
        feeamount.setTypeface(tf);
        entrytype.setTypeface(tf);
postevent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Postnewevent();
    }
});
        getimagepath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        eventstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        eventenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragmentend();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        category_addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_additional = onCreateDialogSingleChoice();
                dialog_additional.show();

            }
        });
        entrytype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs = onCreateDialogSingleChoiceyes();
                dialogs.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public String getStringImage(Bitmap bmp){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        }catch (NullPointerException e){

        }
        return encodedImage;
    }




    private void showFileChooser() {

        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }




    private void Postnewevent(){
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //  loading.dismiss();
                        //Showing toast message of the response
                        if(s.equalsIgnoreCase("error")) {
                            Toast.makeText(AddNewEvent.this, s, Toast.LENGTH_LONG).show() ;
                        }else {
                            Intent citycenter_intent=new Intent(AddNewEvent.this,Events.class);
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

                if(getStringImage(bitmap)!=null){
                    image = getStringImage(bitmap);
                }else{
                    image = ba1;
                }
                //Getting Image Name
                String description = description_addpost.getText().toString().trim();
                String titles = title_addpost.getText().toString().trim();
                String venue = venue_addpost.getText().toString().trim();
                String location = location_addpost.getText().toString().trim();
                String sdate = eventstartdate.getText().toString().trim();
                String edate = eventenddate.getText().toString().trim();
                String timing = timing_addpost.getText().toString().trim();
                String organize = organizedby.getText().toString().trim();
                String cname = contactname.getText().toString().trim();
                String cphone = contactphone.getText().toString().trim();
                String cemail = contactemail.getText().toString().trim();
                String website = website_addpost.getText().toString().trim();


                String feeamt = feeamount.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                    if (feeamt != null) {
                        params.put(KEY_IMAGELOC, image);
                        params.put(KEY_NAME, cname);
                        params.put(KEY_TITLE,titles);
                        params.put(KEY_MYUSERID, myprofileid);
                        params.put(KEY_VENUE, venue);
                        params.put(KEY_LOCATION, location);
                        params.put(KEY_STARTDATE,sdate);
                        params.put(KEY_ENDDATE, edate);
                        params.put(KEY_TIMING, timing);
                        params.put(KEY_ORGANIZEDBY,organize);
                        params.put(KEY_PHONE, cphone);
                        params.put(KEY_EMAIL, cemail);
                        params.put(KEY_WEB,website);
                        params.put(KEY_ENTRYTYPE, entrytypevalue);
                        params.put(KEY_CATLIST, text);
                        params.put(KEY_FEE,feeamt);
                    }else {
                        params.put(KEY_IMAGELOC, image);
                        params.put(KEY_NAME, cname);
                        params.put(KEY_TITLE,titles);
                        params.put(KEY_MYUSERID, myprofileid);
                        params.put(KEY_VENUE, venue);
                        params.put(KEY_LOCATION, location);
                        params.put(KEY_STARTDATE,sdate);
                        params.put(KEY_ENDDATE, edate);
                        params.put(KEY_TIMING, timing);
                        params.put(KEY_ORGANIZEDBY,organize);
                        params.put(KEY_PHONE, cphone);
                        params.put(KEY_EMAIL, cemail);
                        params.put(KEY_WEB,website);
                        params.put(KEY_ENTRYTYPE, entrytypevalue);
                        params.put(KEY_CATLIST, text);
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            eventstartdate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }
    public static class DatePickerFragmentend extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            eventenddate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }
    public Dialog onCreateDialogSingleChoice() {

        final String id="";
//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//Source of the data in the DIalog
        final String[] catlistevent = citycategorylist.toArray(new String[citycategorylist.size()]);

        int selected = -1;
// Set the dialog title
        builder.setTitle("Select Type")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(catlistevent, selected, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String idss=catlistevent[which].trim();


                        String id = ((ItemModel)modelList.get(which)).getId();
                        String catnname= ((ItemModel)modelList.get(which)).getCatname();
                        text=id;
                        if(idss.equalsIgnoreCase(catnname)){

                        }
                       // Log.e("TEXTVALUE",text);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog_additional.dismiss();
                    }
                })
// Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog
                        // Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG) .show();
                        if(text!=null){
                            dialog_additional.dismiss();
                        }else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Please Select the Crime", Toast.LENGTH_SHORT);
// Set the Gravity to Top and Left
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                            toast.show();
                            dialog_additional = onCreateDialogSingleChoice();
                            dialog_additional.show();
                        }

                    }
                });



        return builder.create();
    }

    public Dialog onCreateDialogSingleChoiceyes() {

        final String id="";
//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//Source of the data in the DIalog
        final String[] catlist = entrytypelist.toArray(new String[entrytypelist.size()]);

        int selected = -1;
// Set the dialog title
        builder.setTitle("Select Type")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(catlist, selected, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String id=catlist[which].trim();

entrytypevalue=id;

                        Log.e("TEXTVALUE",entrytypevalue);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
// Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog
                        // Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG) .show();
                        if(text!=null){
                            dialogs.dismiss();
                        }else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Please Select the Crime", Toast.LENGTH_SHORT);
// Set the Gravity to Top and Left
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                            toast.show();
                            dialogs = onCreateDialogSingleChoiceyes();
                            dialogs.show();
                        }

                    }
                });



        return builder.create();
    }

    class ItemModel{
        String id;
        String catname;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getCatname() {
            return catname;
        }

        public void setCatname(String catname) {
            this.catname = catname;
        }
    }
}

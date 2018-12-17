package simplicity_an.simplicity_an.Tamil.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.R;

/**
 * Created by kuppusamy on 3/24/2017.
 */
public class ReportNewsOrComplaintsTamil extends AppCompatActivity {
    SharedPreferences sharedpreferences;

    String myprofileid,username,userimage;
    ImageButton city,happening,search,audiovideo,more;

    TextView title,username_textview;
    NetworkImageView user_image;
    ImageView user_selected_image;
    ImageLoader mImageLoader;
    EditText news_edit;
    ImageButton camera,send;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    Uri fileUri;
    Bitmap bitmap_camera_uri;

    Bitmap bitmap_gallery_uri;
    private int PICK_IMAGE_REQUEST = 1;
    String cameraimage_path,galleryimage_path,edittext_description_aboutpost;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    private String KEY_IMAGE = "imageloc";
    private String KEY_NAME = "post";
    private String KEY_MYUID = "uid";
    private String KEY_POSTID = "pid";
    String Url_upload_data="http://simpli-city.in/request2.php?rtype=citycenterpost&key=simples";
    String postids;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.reportnewsorcomplaint);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
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
        String simplycity_title_fontPath = "fonts/robotoSlabLight.ttf";
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
mImageLoader= MySingleton.getInstance(getApplicationContext()).getImageLoader();
        city=(ImageButton)findViewById(R.id.btn_back);
        happening=(ImageButton)findViewById(R.id.btn_happening);
        search=(ImageButton)findViewById(R.id.btn_search);
        audiovideo=(ImageButton)findViewById(R.id.btn_audio);
        more=(ImageButton)findViewById(R.id.btn_more);
        title=(TextView)findViewById(R.id.title_report_news);
        username_textview=(TextView)findViewById(R.id.user_profile_name);
        user_selected_image=(ImageView)findViewById(R.id.selected_image) ;
        news_edit=(EditText)findViewById(R.id.edit_getcontent_from_user) ;
        camera=(ImageButton)findViewById(R.id.post_cameraimage);
        send=(ImageButton)findViewById(R.id.post_image);
        user_image=(NetworkImageView)findViewById(R.id.profile_user_image) ;
        title.setTypeface(tf);
        username_textview.setTypeface(tf);
        username_textview.setText(username);
        user_image.setImageUrl(userimage,mImageLoader);
city.setImageResource(R.mipmap.citytamil);
        happening.setImageResource(R.mipmap.happeninigtamil);
        search.setImageResource(R.mipmap.searchtamil);
        audiovideo.setImageResource(R.mipmap.audiotamil);
        more.setImageResource(R.mipmap.moretamil);

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","5");
                startActivity(entairnment);
            }
        });
        happening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","3");
                startActivity(entairnment);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","4");
                startActivity(entairnment);
            }
        });
        audiovideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","2");
                startActivity(entairnment);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entairnment=new Intent(getApplicationContext(), MainPageTamil.class);
                entairnment.putExtra("ID","1");
                startActivity(entairnment);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ReportNewsOrComplaintsTamil.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},0);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void showFileChooser() {

        final CharSequence[] options = { "Camera", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ReportNewsOrComplaintsTamil.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera"))
                {
                    Intent cameara=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri= Uri.fromFile(getOutputMediaFile());
                    cameara.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
                    startActivityForResult(cameara,100);
                }
                else if (options[item].equals("Choose from Gallery"))
                {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
       /* Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Source"), SELECT_PICTURE);*/
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){

            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode== Activity.RESULT_OK){
                String stringuri=fileUri.toString();

                Log.e("Uri",stringuri);

                String url =fileUri.getPath().toString();
                url=url.replace("file:///","/");
                Picasso.with(getApplicationContext()).load(url)

                        .into(user_selected_image);
                user_selected_image.setImageURI(Uri.parse(url));
                try {
                    bitmap_camera_uri = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);

                    Bitmap bm= BitmapFactory.decodeFile(fileUri.getPath());
                    ByteArrayOutputStream bao=new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG,50,bao);
                    byte [] ba=bao.toByteArray();
                    cameraimage_path= Base64.encodeToString(ba,Base64.DEFAULT);
                    Log.e("ENCODECAMERA",cameraimage_path);

                }catch (IOException e){

                }

            }
        }else if(requestCode==PICK_IMAGE_REQUEST&&resultCode== Activity.RESULT_OK&& data != null && data.getData() != null){
            Uri filegallery=data.getData();
            Log.e("GAL",filegallery.toString());

            try {
                bitmap_gallery_uri=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap_gallery_uri.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                byte[] imageBytes = baos.toByteArray();
                galleryimage_path = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Log.e("ENCODEGALLERY",galleryimage_path);

            }catch (IOException e){

            }
            user_selected_image.setImageURI(filegallery);

        }
    }
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "SimpliCity");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }    String timestamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath()+File.separator+"CITY_IMG_"+timestamp+".jpg");
    }
    private void Uploadpost(){
        final StringRequest postdataupload=new StringRequest(Request.Method.POST, Url_upload_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("error")){
                    Log.e("Error",response);
                }else {
                    Log.e("Success",response);
                    Toast.makeText(getApplicationContext(),"Post Succeed",Toast.LENGTH_SHORT).show();
                   // CommentReplyFragment.this.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                edittext_description_aboutpost=news_edit.getText().toString();
                String uploadimage=null;
                if(cameraimage_path!=null){
                    uploadimage=cameraimage_path;
                }else if(galleryimage_path!=null){
                    uploadimage=galleryimage_path;
                }
                Map<String,String>param=new Hashtable<String, String>();
                   /*param.put("imageloc",uploadimage);
                   param.put("post",edittext_description_aboutpost);
                   param.put("uid",myprofileid);*/



                    if (uploadimage != null) {
                        param.put(KEY_IMAGE, uploadimage);
                        param.put(KEY_NAME, edittext_description_aboutpost);

                        param.put(KEY_MYUID, myprofileid);
                    }else {
                        param.put(KEY_NAME, edittext_description_aboutpost);

                        param.put(KEY_MYUID, myprofileid);
                    }






                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        postdataupload.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(postdataupload);

    }


}

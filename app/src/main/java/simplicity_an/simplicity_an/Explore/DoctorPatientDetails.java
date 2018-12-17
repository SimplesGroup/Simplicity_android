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
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import simplicity_an.simplicity_an.R;

/**
 * Created by KuppuSamy on 8/29/2017.
 */

public class DoctorPatientDetails extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    RequestQueue requestQueue;
    public static final String backgroundcolor = "color";
    String colorcodes,myprofileid;
    RelativeLayout mainlayout;

    TextView patient_title_textview,payment_details_textview,consultation_fees_title_textview,subtotal_textview,grand__textview,consultation_fees__textview,subtotal_fees__textview,grand_totalfees__textview;
    TextView gender_textview,termsandcondition_textview;
    CheckBox gender_male,gender_female;
    EditText patient_name_editext,patient_age_editext,patient_email_editext;
    Button bill_pay_button;
    ImageButton back,main,settings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_doctor_patient_details);
        String simplycity_title_reugular= "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_reugular);
        requestQueue= Volley.newRequestQueue(this);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        mainlayout=(RelativeLayout)findViewById(R.id.main_layout_explore);
        if(colorcodes.length()==0){

        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi");
            }else {

                if(colorcodes!=null){
                    int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);
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


       /* patient_title_textview =(TextView)findViewById(R.id.patient_details);
        gender_textview =(TextView)findViewById(R.id.patient_gender);*/
        payment_details_textview =(TextView)findViewById(R.id.patient_payment_details);
        consultation_fees_title_textview =(TextView)findViewById(R.id.consultation_fees);
        consultation_fees__textview =(TextView)findViewById(R.id.consultation_fees_detail);
        subtotal_textview =(TextView)findViewById(R.id.subtotal_fees);
        subtotal_fees__textview =(TextView)findViewById(R.id.subtotal_fees_detail);
        grand__textview =(TextView)findViewById(R.id.grandtotal_fees);
        grand_totalfees__textview=(TextView)findViewById(R.id.grandtotal_fees_detail);
        termsandcondition_textview =(TextView)findViewById(R.id.patient_licences_details);

      /*  gender_male=(CheckBox)findViewById(R.id.gender_male);
        gender_female=(CheckBox)findViewById(R.id.gender_female);

        patient_age_editext=(EditText)findViewById(R.id.patient_age);
        patient_name_editext=(EditText)findViewById(R.id.patient_name);
        patient_email_editext=(EditText)findViewById(R.id.patient_email);
*/
        bill_pay_button=(Button)findViewById(R.id.Pay_fees_button);


       /* patient_title_textview.setTypeface(tf1);
        gender_textview .setTypeface(tf1);*/
        payment_details_textview.setTypeface(tf1);
        consultation_fees_title_textview.setTypeface(tf1);
        consultation_fees__textview.setTypeface(tf1);
        subtotal_textview.setTypeface(tf1);
        subtotal_fees__textview.setTypeface(tf1);
        grand__textview.setTypeface(tf1);
        grand_totalfees__textview.setTypeface(tf1);
        termsandcondition_textview.setTypeface(tf1);
       /* patient_age_editext.setTypeface(tf1);
        patient_name_editext.setTypeface(tf1);
        patient_email_editext.setTypeface(tf1);*/
        bill_pay_button.setTypeface(tf1);

       /* patient_title_textview.setText("Patient Details");
        gender_textview.setText("Gender");*/
        payment_details_textview.setText("Payment Details");
        consultation_fees_title_textview.setText("Consultation fees");
        subtotal_textview.setText("Sub Total");
        grand__textview.setText("Grand Total");

        termsandcondition_textview.setText(Html.fromHtml("<p>By cliking on pay & start you agree our</p>"+"<p style=\"color:red;\">Terms & Conditions</p>"));

bill_pay_button.setText("PAY ");



bill_pay_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            Intent next=new Intent(getApplicationContext(),Doctorthankingpage.class);
            startActivity(next);

    }
});

        back=(ImageButton)findViewById(R.id.btn_ex_back) ;
        main=(ImageButton)findViewById(R.id.btn_ex_search);
        settings=(ImageButton)findViewById(R.id.btn_ex_more);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),ExploreMain.class);
                startActivity(in);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notification_page = new Intent(getApplicationContext(), ExploreSettings.class);
                startActivity(notification_page);
            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

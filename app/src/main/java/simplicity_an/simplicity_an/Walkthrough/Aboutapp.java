package simplicity_an.simplicity_an.Walkthrough;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import simplicity_an.simplicity_an.R;

public class Aboutapp extends AppCompatActivity {

    TextView what_textview,simplicity_textview,description_textview,welcome_textview;
    ImageButton next_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aboutapp_walkthrough);
        what_textview=(TextView)findViewById(R.id.whatis_textview);
        simplicity_textview=(TextView)findViewById(R.id.simplicity_textview);
        description_textview=(TextView)findViewById(R.id.description_textview);
        welcome_textview=(TextView)findViewById(R.id.welcome_textview);
        next_page=(ImageButton)findViewById(R.id.next_imagebutton);

        Typeface bold=Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/SystemSanFranciscoDisplayBold.ttf");
        Typeface regular=Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/SystemSanFranciscoDisplayRegular.ttf");

        what_textview.setTypeface(regular);
        simplicity_textview.setTypeface(bold);
        description_textview.setTypeface(regular);
        welcome_textview.setTypeface(regular);


        String descriptions="SimpliCity is a vision. A vision that was brought to light 2000 years ago by famed poet Kaniyan Poongundranaar through his legendary poems of Tamil in Puranaanuru. He said “யாதும் ஊரே யாவரும் கேளிர்” “yaadhum oorae yaavarum kaelir”. Every city is our own and all it’s citizens are our kin and kith. A grand democratic vision that was millennia ahead of it’s time. \n" +
                "\n" +
                "To make every city our own, to know what’s happening in our city in great depth and instantaneously, we need a top-class tech media platform that informs, connects and engages me in realtime while providing authenticated news and information that can be trusted.\n" +
                "\n" +
                "SimpliCity is the best source for realtime city news provided by a top class journalist team, has the largest events platform, great updates on traffic, water status and other government notifications, a free job portal and exclusive videos about places to eat, people to cherish, talents to watch out for and much more. \n" +
                "\n" +
                "We hope this will be the realisation of a grand vision that was set in motion nearly 2000 years ago. ";



        what_textview.setText("WHAT IS");
        simplicity_textview.setText("SIMPLICITY");
        welcome_textview.setText("Welcome to SimpliCity");

        description_textview.setText(Html.fromHtml("<html>"+descriptions+  "</html>"));


        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), LocationSelection.class);
                startActivity(in);
                finish();
            }
        });



    }
}

package simplicity_an.simplicity_an.Tamil.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.MainTamil.MainPageTamil;
import simplicity_an.simplicity_an.R;


/**
 * Created by kuppusamy on 8/5/2016.
 */
public class EducationInternshipDescriptiontamil extends AppCompatActivity {
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    ViewPager mPager;
    MyViewPagerAdapter mAdapter;
    TabLayout mTabLayout;
    TextView posttitle,job_company_name;
    Button applyjob;
    String titl;
    String company;
    ImageButton menu,back,favourite,comment;
    String URLFAV="http://simpli-city.in/request2.php?rtype=addfav&key=simples";
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "qid";
    private String KEY_QTYPE= "qtype";
    int post_likes_count;
    RequestQueue requestQueue;
    String shareurl,sharetitle;
    int favcount;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid,activity;
    ProgressDialog pdialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.educationinternshipdescription);

        String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);

        menu=(ImageButton)findViewById(R.id.btn_3);
        back=(ImageButton)findViewById(R.id.btn_1);
        menu=(ImageButton)findViewById(R.id.btn_2);
        favourite=(ImageButton)findViewById(R.id.btn_4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                /*Intent backjobdetail=new Intent(getApplicationContext(),MainPageTamil.class);
                startActivity(backjobdetail);
                finish();*/
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujobdeatail=new Intent(getApplicationContext(),MainPageTamil.class);
                startActivity(menujobdeatail);
                finish();
            }
        });
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        posttitle=(TextView)findViewById(R.id.titlelabel);
        job_company_name=(TextView)findViewById(R.id.education_job_company);
        posttitle.setTypeface(tf);
        job_company_name.setTypeface(tf);
        Intent in=getIntent();
        titl=in.getStringExtra("TITLE");
        String id=in.getStringExtra("ID");
        company=in.getStringExtra("COMPANY");
        posttitle.setText(titl);
        job_company_name.setText(company);
        //getActionBar().hide();
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        //mPager.setAdapter(mAdapter);
        setupViewPager(mPager);
        //Notice how the Tab Layout links with the Pager Adapter
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        //Notice how The Tab Layout adn View Pager object are linked
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupViewPager(ViewPager mPager) {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        // adapter.addFragment(new FragmentTips(), "Tips");
        adapter.addFragment(new FragmentEducationJobDescriptiontamil(), "Job Description");
        adapter.addFragment(new FragmentEducationEmployerinfotamil(), "Employee Info");
        mPager.setAdapter(adapter);
    }
    class MyViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

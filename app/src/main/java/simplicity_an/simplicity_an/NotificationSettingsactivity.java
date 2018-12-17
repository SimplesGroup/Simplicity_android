package simplicity_an.simplicity_an;

import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuppusamy on 7/4/2016.
 */
public class NotificationSettingsactivity extends AppCompatActivity {
    // Need this to link with the Snackbar
    CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private ViewPager mPager;
    private HealthViewPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    TextView title_notification;
ImageButton back,home;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid,activity,myprofileid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.notificationandsettings);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(MYUSERID)) {
            contentid=sharedpreferences.getString(CONTENTID,"");
            activity=sharedpreferences.getString(Activity,"");
            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+", "");
        }
        final String fontPath = "fonts/Lora-Regular.ttf";;
if(activity!=null){
    if(activity.equalsIgnoreCase("notification")){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(Activity);
        editor.remove(CONTENTID);
        editor.apply();

    }
}
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), fontPath);
        title_notification=(TextView)findViewById(R.id.toolbar_title_notification);
        title_notification.setText("Notification & Settings");
        title_notification.setTypeface(tf);
        back=(ImageButton)findViewById(R.id.btn_musicback);
        home=(ImageButton)findViewById(R.id.btn_musichome) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent back_home=new Intent(NotificationSettingsactivity.this,MainActivity.class);
                startActivity(back_home);
                finish();*/
                onBackPressed();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(NotificationSettingsactivity.this,MainActivityVersiontwo.class);
                startActivity(home);
                finish();
            }
        });
        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mAdapter = new HealthViewPagerAdapter(getSupportFragmentManager());
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
        HealthViewPagerAdapter adapter = new HealthViewPagerAdapter(getSupportFragmentManager());
        // adapter.addFragment(new FragmentTips(), "Tips");
        //adapter.addFragment(new Notificationmissednews(), "Missed News");
       // adapter.addFragment(new Notificationcitycenter(), "Citycenter");
        adapter.addFragment(new Notificationfavourites(), "Saved Article");
       // adapter.addFragment(new Notificationmyprofile(), "My Profile");
        adapter.addFragment(new Notificationsettingmy(), "Settings");
        mPager.setAdapter(adapter);
    }
    class HealthViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public HealthViewPagerAdapter(FragmentManager manager) {
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

package app.jibon.spider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import app.jibon.spider.Fragments.Home;
import app.jibon.spider.Fragments.Messages;
import app.jibon.spider.Fragments.Notification;
import app.jibon.spider.Fragments.Profile;

public class MainActivity extends FragmentActivity {
    @Override
    public void onBackPressed() {
        (new Settings(this)).exitApp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            new NavigationDrawerSettings(this, R.id.nav_drawer);

            ViewPager viewPager1 = findViewById(R.id.MainActivityViewPager1);
            TabLayout tabLayout = findViewById(R.id.MainActivityViewPager1Tablayout);
            ImageView MainActivityBackButton = findViewById(R.id.MainActivityBackButton);
            TextView MainActivityTitleText = findViewById(R.id.MainActivityTitleText);
            ImageView MainActivityMoreButton = findViewById(R.id.MainActivityMoreButton);

            MainActivityViewPagerAdapter mainActivityViewPagerAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
            viewPager1.setAdapter(mainActivityViewPagerAdapter);
            MainActivityBackButton.setOnClickListener(v->{
                viewPager1.setCurrentItem(0);
            });
            MainActivityMoreButton.setOnClickListener(v->{
                (new Timer()).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((DrawerLayout) findViewById(R.id.Drawer)).openDrawer(GravityCompat.END);
                    }
                }, 100);
            });

            Drawable mainActivityBackButtonImage = MainActivityBackButton.getDrawable();
            MainActivityBackButton.setImageDrawable(null);
            tabLayout.setupWithViewPager(viewPager1);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_outline_account_circle_24);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_message_24);
            tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_notifications_none_24);
            viewPager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (viewPager1.getCurrentItem() == 0){
                        MainActivityBackButton.setImageDrawable(null);
                    }else{
                        MainActivityBackButton.setImageDrawable(mainActivityBackButtonImage);
                    }
                }@Override
                public void onPageSelected(int position) {}

                @Override
                public void onPageScrollStateChanged(int state) {}
            });


        }catch (Exception e){
            Log.e("errnos", e.toString());
        }


    }
}




class MainActivityViewPagerAdapter extends FragmentStatePagerAdapter{

    public MainActivityViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0){
            fragment = new Home();
        }else if (position == 1){
            fragment = new Profile();
        }else if (position == 2){
            fragment = new Messages();
        }else if (position == 3){
            fragment = new Notification();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return "";
    }
}
package app.jibon.spider;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import app.jibon.spider.Fragments.Home;
import app.jibon.spider.Fragments.Messages;
import app.jibon.spider.Fragments.Notification;
import app.jibon.spider.Fragments.Profile;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try{

        // first open setup
            (new SaveImageFromLink(this, "https://i.pinimg.com/736x/91/75/1f/91751f67c7ee60fc7742ee2e13c657e4.jpg", "profile.png")).execute();
            new NavigationDrawerSettings(this, R.id.nav_drawer);

        // find view by id
            TabLayout tabLayout = findViewById(R.id.MainActivityViewPager1Tablayout);
            ImageView MainActivityBackButton = findViewById(R.id.MainActivityBackButton);
            TextView MainActivityTitleText = findViewById(R.id.MainActivityTitleText);
            ImageView MainActivityMoreButton = findViewById(R.id.MainActivityMoreButton);
            View mainActivityFragment1 = findViewById(R.id.MainActivityViewFragment1);

        // option menu button
            MainActivityMoreButton.setOnClickListener(v->{
                (new Timer()).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((DrawerLayout) findViewById(R.id.Drawer)).openDrawer(GravityCompat.END);
                    }
                }, 100);
            });

        // back button manage
            MainActivityBackButton.setOnClickListener(v->new CustomTools(this).toast("Next version, wait.", R.drawable.ic_baseline_warning_24));
            Drawable mainActivityBackButtonImage = MainActivityBackButton.getDrawable();
            MainActivityBackButton.setImageDrawable(null);

        //tabs buttons manage with fragments
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Fragment fragment1 = null;
                    MainActivityBackButton.setImageDrawable(mainActivityBackButtonImage);
                    if (tab.getPosition() == 0){
                        fragment1 = new app.jibon.spider.Fragments.Home();
                        MainActivityBackButton.setImageDrawable(null);
                    }else if (tab.getPosition() == 1){
                        fragment1 = new app.jibon.spider.Fragments.Notification();
                    }else if (tab.getPosition() == 1){
                        fragment1 = new app.jibon.spider.Fragments.Settings();
                    }
                    if (fragment1 != null){
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.MainActivityViewFragment1, fragment1);
                        fragmentTransaction1.commit();
                    }
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}
                @Override
                public void onTabReselected(TabLayout.Tab tab) {}
            });


        }catch (Exception e){
            Log.e("errnos", e.toString());
        }


    }
}





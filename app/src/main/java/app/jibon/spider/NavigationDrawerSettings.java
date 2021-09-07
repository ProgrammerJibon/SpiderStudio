package app.jibon.spider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("Registered")
public class NavigationDrawerSettings{
    public Activity activity;

    public NavigationDrawerSettings(Activity parentActivityIntent, int nav_drawer) {
        this.activity = parentActivityIntent;
        // get the nav drawer
        NavigationView navigationView = activity.findViewById(nav_drawer);
        DrawerLayout drawerLayout = activity.findViewById(R.id.Drawer);
        navigationView.setVisibility(View.VISIBLE);
        // get the menus of nav drawer
        Menu nav_menus = navigationView.getMenu();
        nav_menus.findItem(R.id.nav_login).setTitle("Login"); //sample test
        // get the header of nav drawer
        View header_layout = (activity.getLayoutInflater()).inflate(R.layout.header_navigation_menus, activity.findViewById(R.id.nav_profile_view), false);
        File profile_pic_from_storage = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            profile_pic_from_storage = new File(Environment.getStorageDirectory(), ".ProgrammerJibon");
        }else{
            profile_pic_from_storage = new File(Environment.getExternalStorageDirectory(), ".ProgrammerJibon");
        }
        profile_pic_from_storage = new File(profile_pic_from_storage, "profile.png");
        if (profile_pic_from_storage.exists()) {
            ((ImageView) header_layout.findViewById(R.id.nav_profile_pic)).setImageBitmap(BitmapFactory.decodeFile(profile_pic_from_storage.getAbsolutePath()));
        } else {
            (new OpenImageFromLink("https://i.pinimg.com/736x/91/75/1f/91751f67c7ee60fc7742ee2e13c657e4.jpg",  header_layout.findViewById(R.id.nav_profile_pic))).execute();
        }
        ((TextView) header_layout.findViewById(R.id.nav_profile_name)).setText("MD JIBON HOWLADER"); // sample
        ((TextView) header_layout.findViewById(R.id.nav_profile_email)).setText("UserName@DOMAIN.COM"); // sample
        navigationView.addHeaderView(header_layout);
        // any item clicked of nav drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_exit){
                (new Settings(activity)).exitApp();
            }else if (item.getItemId() == R.id.nav_home){
                // wait bro
            }else if (item.getItemId() == R.id.profile){
                // wait bro
            }else if (item.getItemId() == R.id.nav_messages){
                // wait bro
            }else if (item.getItemId() == R.id.nav_notification){
                // wait bro
            }else if (item.getItemId() == R.id.nav_ui_mode){
                new CustomTools(activity).toast("Beta version users only", R.drawable.ic_baseline_warning_24);
            }
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
            }, 100);
            return false;
        });

    }
}

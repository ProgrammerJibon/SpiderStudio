package app.jibon.spider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

@SuppressLint("Registered")
public class NavigationDrawerSettings{
    public Activity activity;

    public NavigationDrawerSettings(Activity parentActivityIntent, int nav_drawer) {
        this.activity = parentActivityIntent;
        // get the nav drawer
        NavigationView navigationView = activity.findViewById(nav_drawer);
        navigationView.setVisibility(View.VISIBLE);
        // get the menus of nav drawer
        Menu nav_menus = navigationView.getMenu();
        nav_menus.findItem(R.id.nav_login).setTitle("Login"); //sample test
        // get the header of nav drawer
        View header_layout = (activity.getLayoutInflater()).inflate(R.layout.header_navigation_menus, activity.findViewById(R.id.nav_profile_view), false);
        File profile_pic_from_storage = new File(Environment.getExternalStorageDirectory(), ".programmerjibon");
        profile_pic_from_storage = new File(String.valueOf(profile_pic_from_storage.getAbsoluteFile()), "profile.png");
        if (profile_pic_from_storage.exists()) {
            ((ImageView) header_layout.findViewById(R.id.nav_profile_pic)).setImageBitmap(BitmapFactory.decodeFile(profile_pic_from_storage.getAbsolutePath()));
        } else {
            URL urlx = null;
            try {
                urlx = new URL("https://i.pinimg.com/736x/91/75/1f/91751f67c7ee60fc7742ee2e13c657e4.jpg");
                URLConnection connection = urlx.openConnection();
                Bitmap bitmap =  BitmapFactory.decodeStream(connection.getInputStream());
                ((ImageView) header_layout.findViewById(R.id.nav_profile_pic)).setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((ImageView) header_layout.findViewById(R.id.nav_profile_pic)).setImageResource(R.drawable.ic_outline_account_circle_24);
            new SaveImage(activity, "https://i.pinimg.com/736x/91/75/1f/91751f67c7ee60fc7742ee2e13c657e4.jpg", "profile.png");
        }
        ((TextView) header_layout.findViewById(R.id.nav_profile_name)).setText("MD JIBON HOWLADER"); // sample
        ((TextView) header_layout.findViewById(R.id.nav_profile_email)).setText("UserName@DOMAIN.COM"); // sample
        navigationView.addHeaderView(header_layout);
        // any item clicked of nav drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_exit){
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Are you sure to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Exit", (dialog, which) -> {
                            activity.finish();
                            activity.onBackPressed();
                        })
                        .setNegativeButton("Later", (dialog, which) -> {
                            dialog.cancel();
                        });
                builder.create().show();
            }else if (item.getItemId() == R.id.nav_home){
                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                activity.startActivity(intent);
            }else if (item.getItemId() == R.id.nav_ui_mode){
                new Settings(activity).VisualModeSettings();
            }
            return false;
        });
    }
}

package app.jibon.spider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

@SuppressLint("Registered")
public class NavigationDrawerSettings{
    public Activity activity;

    public NavigationDrawerSettings(Activity parentActivityIntent, int nav_drawer) {
        this.activity = parentActivityIntent;
        // get the nav drawer
        NavigationView navigationView = activity.findViewById(nav_drawer);
        navigationView.setVisibility(View.VISIBLE);
        navigationView.findViewById(R.id.nav_profile_view).setPadding(0,200,0,0);
        // get the menus of nav drawer
        Menu nav_menus = navigationView.getMenu();
        nav_menus.findItem(R.id.nav_login).setTitle("Login"); //sample test
        // get the header of nav drawer
        View header_layout = (activity.getLayoutInflater()).inflate(R.layout.header_navigation_menus, activity.findViewById(R.id.nav_profile_view), false);
        ((TextView) header_layout.findViewById(R.id.nav_profile_name)).setText("MD Jibon howlader"); // sample
        ((TextView) header_layout.findViewById(R.id.nav_profile_email)).setText("ProgrammerJibon@gmail.com"); // sample
        ((ImageView) header_layout.findViewById(R.id.nav_profile_pic)).setImageBitmap(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.developer)); // sample
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

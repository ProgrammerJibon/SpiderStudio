package app.jibon.spider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.material.navigation.NavigationView;

import java.io.File;

@SuppressLint("Registered")
public class NavigationDrawerSettings{
    protected Activity activity;

    public NavigationDrawerSettings(Activity parentActivityIntent, int nav_drawer) {
        this.activity = parentActivityIntent;
        NavigationView navigationView = activity.findViewById(nav_drawer);
        navigationView.setVisibility(View.VISIBLE);
        Menu nav_menus = navigationView.getMenu();
        nav_menus.findItem(R.id.nav_login).setTitle("Login"); //sample test
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_share){
                try {
                    if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        ApplicationInfo app = activity.getApplicationContext().getApplicationInfo();
                        String filePath = app.sourceDir;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("*/*");
                        Uri uri = Uri.fromFile(new File(filePath));
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        activity.startActivity(Intent.createChooser(intent, "Share app via"));
                    }else{
                        new CustomToast(activity, "Please permit to read Storage", R.drawable.ic_baseline_warning_24);
                    }


                }catch (Exception error){
                    new CustomToast(activity, error.toString(), R.drawable.ic_baseline_error_24);
                }
            }else if (item.getItemId() == R.id.nav_exit){
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
                ((AlertDialog)builder.create()).show();
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

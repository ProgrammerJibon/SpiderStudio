package app.jibon.spider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatDelegate;

public class Settings  {
    public SharedPreferences preferences;
    public SharedPreferences.Editor preferencesEditor;
    public Activity activity;
    public Settings(Activity applicationContext) {
        this.activity = applicationContext;
        this.preferences = activity.getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
    }

    public Boolean visualModeSettings(){
        try {
            preferencesEditor= preferences.edit();
            if (preferences.getString("nightMode", "exception").contains("true")){
                preferencesEditor.putString("nightMode", "false");
            }else if (preferences.getString("nightMode", "exception").contains("false")){
                preferencesEditor.putString("nightMode", "true");
            }else{
                preferencesEditor.putString("nightMode", "true");
            }
            preferencesEditor.apply();
            return setVisualMode();
        }catch (Exception e){
            Log.e("errnos_settings_a", e.toString());
            return false;
        }

    }
    public Boolean setVisualMode(){
        try {
            if (preferences.getString("nightMode", "").contains("true")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                return true;
            }else if (preferences.getString("nightMode", "").contains("false")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            }else{
                return visualModeSettings();
            }
        }catch (Exception e){
            Log.e("errnos_settings_b", e.toString());
            return false;
        }

    }
    public void exitApp(Boolean... showAlertBox){
        if (showAlertBox[0] == null || showAlertBox[0]){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Are you sure to exit?")
                    .setCancelable(true)
                    .setPositiveButton("Exit", (dialog, which) -> {
                        new CustomTools(activity).toast("Thanks for using our app", R.drawable.ic_baseline_stars_24);
                        Process.killProcess(Process.myPid());
                        activity.finish();
                        System.exit(0);
                    })
                    .setNegativeButton("Later", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .setIcon(R.drawable.ic_baseline_exit_to_app_24);
            builder.create().show();
        }else{
            new CustomTools(activity).toast("Thanks for using our app", R.drawable.ic_baseline_stars_24);
            Process.killProcess(Process.myPid());
            activity.finish();
            System.exit(0);
        }

    }


    public boolean restartApp(String cause){
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(cause)
                    .setCancelable(true)
                    .setPositiveButton("Restart", (dialog, which) -> {
                        new CustomTools(activity).toast("Restarting app", R.drawable.ic_baseline_notification_important_24);
                        new CustomTools(activity).vibrate(1000);
                        activity.startActivity(new Intent(activity, SplashScreen.class));
                        android.os.Process.killProcess(Process.myPid());
                    })
                    .setNegativeButton("Later", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .setIcon(R.drawable.ic_baseline_exit_to_app_24);
            builder.create().show();
        }catch (Exception e){
            Log.e("errnos_settings_c", e.toString());
        }
        return true;
    }


    public String storedCookie(String cookies){
        String cookie = "";
        if (cookies != null){
            preferencesEditor = preferences.edit();
            preferencesEditor.putString("cookie", cookies);
            preferencesEditor.commit();
        }
        if (!preferences.getString("cookie", "null").equals("null")){
            cookie = preferences.getString("cookie", "null");
        }else{
            preferencesEditor = preferences.edit();
            preferencesEditor.putString("cookie", "null");
            preferencesEditor.commit();
        }
        return cookie;
    }

    public String userId(String user) {
        String user_id = "";
        if (user != null){
            preferencesEditor = preferences.edit();
            preferencesEditor.putString("user_id", user);
            preferencesEditor.commit();
        }
        if (!preferences.getString("user_id", "null").equals("null")){
            user_id = preferences.getString("user_id", "null");
        }else{
            preferencesEditor = preferences.edit();
            preferencesEditor.putString("user_id", "null");
            preferencesEditor.commit();
        }
        return user_id;
    }
}

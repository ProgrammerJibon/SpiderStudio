package app.jibon.spider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class Settings  {
    public SharedPreferences preferences;
    public SharedPreferences.Editor preferencesEditor;
    public Activity activity;
    public Settings(Activity applicationContext) {
        this.activity = applicationContext;
    }

    public Boolean visualModeSettings(){
        try {
            preferences= PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
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
            Log.e("errno", e.toString());
            return false;
        }

    }
    public Boolean setVisualMode(){
        try {
            preferences= PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            Log.e("errnos", preferences.getString("nightMode", ""));
            if (preferences.getString("nightMode", "").contains("true")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Log.e("errnos", "Night Mode setted");
                return true;
            }else if (preferences.getString("nightMode", "").contains("false")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Log.e("errnos", "Night Mode removed");
                return true;
            }else{
                return visualModeSettings();
            }
        }catch (Exception e){
            Log.e("errnos", e.toString());
            return false;
        }

    }
    public void exitApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure to exit?")
                .setCancelable(true)
                .setPositiveButton("Exit", (dialog, which) -> {
                    android.os.Process.killProcess(Process.myPid());
                })
                .setNegativeButton("Later", (dialog, which) -> {
                    dialog.cancel();
                })
                .setIcon(R.drawable.ic_baseline_exit_to_app_24);
        builder.create().show();
    }


    public boolean restartApp(String cause){
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(cause)
                    .setCancelable(true)
                    .setPositiveButton("Restart", (dialog, which) -> {
                        activity.startActivity(new Intent(activity, SplashScreen.class));
                        android.os.Process.killProcess(Process.myPid());
                    })
                    .setNegativeButton("Later", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .setIcon(R.drawable.ic_baseline_exit_to_app_24);
            builder.create().show();
        }catch (Exception e){
            Log.e("errnos", e.toString());
        }
        return true;
    }
}

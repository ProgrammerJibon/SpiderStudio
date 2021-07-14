package app.jibon.spider;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Settings  {
    public SharedPreferences preferences;
    public SharedPreferences.Editor preferencesEditor;
    public Activity activity;
    public Settings(Activity applicationContext) {
        this.activity = applicationContext;
    }

    public Boolean VisualModeSettings(){
        try {
            preferences= PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            preferencesEditor= preferences.edit();
            if (preferences.getString("nightMode", "exception").equals("true")){
                preferencesEditor.putString("nightMode", "false");
            }else if (preferences.getString("nightMode", "exception").equals("false")){
                preferencesEditor.putString("nightMode", "true");
            }else{
                preferencesEditor.putString("nightMode", "true");
            }
            preferencesEditor.apply();
            SetVisualMode();
            return true;
        }catch (Exception e){
            Log.e("errno", e.toString());
            return false;
        }

    }
    public Boolean SetVisualMode(){
        try {
            preferences= (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            if (preferences.getString("nightMode", "exception").equals("true")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else if (preferences.getString("nightMode", "exception").equals("false")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            return true;
        }catch (Exception e){
            new CustomToast(activity, e.toString(), R.drawable.ic_baseline_error_24);
            return false;
        }

    }
}

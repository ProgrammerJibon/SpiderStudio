package app.jibon.spider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Settings  {
    public SharedPreferences preferences;
    public SharedPreferences.Editor preferencesEditor;
    public Context context;
    public Settings(Context applicationContext) {
        this.context = applicationContext;
    }

    public Boolean VisualModeSettings(){
        try {
            preferences= PreferenceManager.getDefaultSharedPreferences(context);
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
            preferences= PreferenceManager.getDefaultSharedPreferences(context);
            preferencesEditor = preferences.edit();
            if (preferences.getString("nightMode", "exception").equals("true")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else if (preferences.getString("nightMode", "exception").equals("false")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else{
                return false;
            }
            preferencesEditor.apply();
            return true;
        }catch (Exception e){
            Log.e("errno", e.toString());
            return false;
        }

    }
}

package app.jibon.spider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Parameter;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_WORLD_READABLE;

public class MainActivity extends AppCompatActivity {
    DrawerLayout nav_drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav_drawer = findViewById(R.id.MainActivity);
        navigationView = findViewById(R.id.nav_drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, nav_drawer, toolbar, 0,0);
        nav_drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_night_mode){
                if (new Settings(getApplicationContext()).VisualModeSettings()){
                    new CustomToast(getApplicationContext(), "Visual Mode Changed");
                }else{
                    new CustomToast(getApplicationContext(), "Something went wrong");
                }
            }
            if (id == R.id.nav_exit){
                finish();
            }
            navigationView.clearFocus();
            nav_drawer.closeDrawers();
            return false;
        });
    }
    public class CustomToast {
        Context context;
        String text;
        public CustomToast(Context context, String s) {
            this.context = context;
            this.text = String.valueOf(s);View view = getLayoutInflater().inflate(R.layout.toast, findViewById(R.id.Custom_toast), false);
            ((TextView) view.findViewById(R.id.Custom_toast_text)).setText(text);
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(view);
            toast.show();
            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
        }

    }

}
package app.jibon.spider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Parameter;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_WORLD_READABLE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button MainActivitySetMode = findViewById(R.id.nav_night_mode);
        MainActivitySetMode.setOnClickListener(view->{
            if (new Settings(getApplicationContext()).VisualModeSettings()){
                new CustomToast(getApplicationContext(), "Visual Mode Changed");
            }else{
                new CustomToast(getApplicationContext(), "Something went wrong");
            }
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
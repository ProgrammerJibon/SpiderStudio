package app.jibon.spider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new Settings(this).SetVisualMode()){
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }, 1000);
        }else{
            new CustomToast(this, "Something went wrong", R.drawable.ic_baseline_error_24);
            finish();
        }

    }
}

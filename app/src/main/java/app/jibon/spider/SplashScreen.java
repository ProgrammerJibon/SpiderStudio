package app.jibon.spider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity{
    Activity main = this;
    Context context = this;
    public Data DATA;
    public ProgressBar SplashScreenProgressBar;
    CheckUserSigned checkUserSigned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        DATA = new Data(main);
        SplashScreenProgressBar = findViewById(R.id.splash_screen_progress_bar);
        checkUserSigned = new CheckUserSigned(main, DATA.linkForJson("sign_states=1"), SplashScreenProgressBar);
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.VIBRATE
        };
        int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 255;
        if (hasPermission(this, permissions)){
            checkUserSigned.execute();
        }else{
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }

    }

    public void finishSplash(){
        this.finish();
    }

    public boolean hasPermission(Context context, String[] permissionx) {
        if (context != null && permissionx != null) {
            for (String permission : permissionx) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean results = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                results = false;
                break;
            }
        }
        if (results){
            checkUserSigned.execute();
        }else{
            new CustomTools(main).toast("permission not granted", R.drawable.ic_baseline_error_24);
            finish();
        }
    }
}


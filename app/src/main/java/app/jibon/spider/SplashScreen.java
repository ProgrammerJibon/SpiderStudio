package app.jibon.spider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity{
    Activity main = this;
    Context context = this;
    Data DATA = new Data();
    public ProgressBar SplashScreenProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.VIBRATE
        };
        int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 255;
        if (hasPermission(this, permissions)){
            runMainThread(main);
        }else{
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
        SplashScreenProgressBar = findViewById(R.id.splash_screen_progress_bar);

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

    private void runMainThread(Activity activity){
        final GetJsonURL[] getJsonURL = {null};
        final Boolean[] looper = {false};
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!looper[0]){
                        Looper.prepare();
                        looper[0] = true;
                    }
                    if (getJsonURL[0] == null) {
                        getJsonURL[0] = new GetJsonURL(activity, DATA.getJSONLINK("?sign_states=1"), SplashScreenProgressBar);
                        getJsonURL[0].execute();
                    }
                    if (!(new File(Environment.getExternalStorageDirectory() + "/.ProgrammerJibon")).exists()) {
                        if (!(new File(Environment.getExternalStorageDirectory() + "/.ProgrammerJibon")).mkdir()) {
                            new CustomTools(activity).toast("Unable to make folder", R.drawable.ic_baseline_error_24);
                        }
                    }
                    if (getJsonURL[0].done) {
                        this.cancel();
                        if (getJsonURL[0].result != null) {
                            JSONObject json = getJsonURL[0].result;
                            Log.e("errnos", json.toString());
                            if (json.has("signed")){
                                if (!json.getBoolean("signed")){
                                    startActivity(new Intent(activity, LoginActivity.class));
                                }else{
                                    startActivity(new Intent(activity, MainActivity.class));
                                }
                                Process.killProcess(Process.myPid());
                                finish();
                            }
                        } else {
                            Toast.makeText(activity, "Can't connect to server", Toast.LENGTH_LONG).show();
                            android.os.Process.killProcess(Process.myPid());
                        }
                    }
                } catch (Exception e) {
                    Log.e("errnos", e.toString());
                    this.cancel();
                    Toast.makeText(activity, "Can't connect to server", Toast.LENGTH_LONG).show();
                    android.os.Process.killProcess(Process.myPid());
                }

            }
        }, 1, 100);
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
            runMainThread(main);
        }else{
            new CustomTools(main).toast("permission not granted", R.drawable.ic_baseline_error_24);
            finish();
        }
    }
}

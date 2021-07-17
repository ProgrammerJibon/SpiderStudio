package app.jibon.spider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    Activity main = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 255;
        if (hasPermission(this, permissions)){
            runMainThread(this);
        }else{
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }


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
        new SaveImage(activity, "https://i.pinimg.com/736x/91/75/1f/91751f67c7ee60fc7742ee2e13c657e4.jpg", "profile.png");
        if (new Settings(activity).SetVisualMode()){
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(activity, MainActivity.class));
                    finish();
                }
            }, 3000);
        }else{
            new CustomToast(activity, "Something went wrong", R.drawable.ic_baseline_error_24);
            finish();
        }
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
            new CustomToast(main, "permission not granted", R.drawable.ic_baseline_error_24);
            finish();
        }
    }
}

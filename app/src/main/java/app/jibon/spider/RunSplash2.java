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

public class RunSplash2 extends AppCompatActivity {
    Activity activity = this;
    private final String[] permissionx = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.VIBRATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.CALL_PHONE};
    protected int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        if(hasPermissions(getApplicationContext(), permissionx)){
            runBaseActivity();
        }else{
            ActivityCompat.requestPermissions(this, permissionx, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            runBaseActivity();
        }

    }

    private void runBaseActivity() {
        try {
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    new CustomToast(activity, "TARAKHA", R.drawable.ic_baseline_done_24);
                    startActivity(new Intent(activity, MainActivity.class));
                    finish();
                }
            }, 4000);
        }catch (Exception e){
            new CustomToast(activity, e.toString(), R.drawable.ic_baseline_error_24);
        }

    }

    public boolean hasPermissions(Context context, String[] permissionx) {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean denied = false;
        for (int xxx = 0; xxx < permissions.length; xxx++) {
            String permission = permissions[xxx];
            int grantResult = grantResults[xxx];
            for (String s : permissions) {
                if (permission.equals(s)) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED){
                        denied = true;
                    }
                }
            }
        }
        if (denied){
            new CustomToast(activity, "Some Permissions Are Denieted", R.drawable.ic_baseline_error_24);

        }else{
            runBaseActivity();
        }
    }
}

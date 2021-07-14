package app.jibon.spider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RunSplash2 extends AppCompatActivity {
    private String[] permissionx = new String[]{
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
        }

    }

    private void runBaseActivity() {
        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 4000);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
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
        ArrayList<String> denied = new ArrayList();
        for (int xxx = 0; xxx < permissions.length; xxx++) {
            String permission = permissions[xxx];
            int grantResult = grantResults[xxx];
            for (String s : permissions) {
                if (permission.equals(s)) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED){
                        denied.add(s);
                    }
                }
            }
        }
        if (denied.size() > 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Activity activity = this.getParent();
                for(int xxy = 0; xxy < denied.size(); xxy++){
                    String denieds = denied.get(xxy);
                    (new Timer()).schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new CustomToast(activity, "Please Grant " + denieds, R.drawable.ic_baseline_warning_24);
                        }
                    }, xxy*300);

                }
                (new Timer()).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new CustomToast(activity, "Some permission is missing", R.drawable.ic_baseline_error_24);
                    }
                }, denied.size()*300);
            }

        }else{
            runBaseActivity();
        }
    }
}

package app.jibon.spider;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            new CustomToast(this, "Welcome sir!", R.drawable.ic_baseline_notifications_none_24);
            new NavigationDrawerSettings(this, R.id.nav_drawer);
        }catch (Exception e){
            e.toString();
        }

    }


}
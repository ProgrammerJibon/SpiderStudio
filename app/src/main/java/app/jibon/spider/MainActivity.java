package app.jibon.spider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            new CustomToast(this, "Welcome sir!", R.drawable.ic_baseline_notifications_none_24);
            new NavigationDrawerSettings(this, R.id.nav_drawer);
            try {
                URL urlx = new URL("https://i.pinimg.com/736x/91/75/1f/91751f67c7ee60fc7742ee2e13c657e4.jpg");
                URLConnection connection = urlx.openConnection();
                Bitmap bitmap =  BitmapFactory.decodeStream(connection.getInputStream());
                ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
            }catch (Exception e){
                new CustomToast(this, e.toString(), R.drawable.ic_baseline_warning_24);
            }

        }catch (Exception e){
            new CustomToast(this, e.toString(), R.drawable.ic_baseline_warning_24);
        }

    }


}
package app.jibon.spider;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);

            new OpenImageLink("https://i.pinimg.com/736x/91/75/1f/91751f67c7ee60fc7742ee2e13c657e4.jpg", findViewById(R.id.image));
        }catch (Exception e){
            new CustomToast(this, e.toString(), R.drawable.ic_baseline_warning_24);
        }

    }


}
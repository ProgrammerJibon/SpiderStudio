package app.jibon.spider;

import android.app.AlertDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NavigationDrawerSettings(this, R.id.nav_drawer);
        EditText pasteFileLink = findViewById(R.id.pasteLinkOfFile);
        pasteFileLink.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                if (!(pasteFileLink.getText()).equals("")){
                    String pastedLink = String.valueOf(pasteFileLink.getText());
                    try{
                        (new SaveImage(this, pastedLink, null)).execute().get();
                        File filePic = new File(Environment.getExternalStorageDirectory(), ".ProgrammerJibon");
                        filePic = new File(String.valueOf(filePic.getAbsoluteFile()), (new File(pastedLink).getName())+".jpg");
                        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(BitmapFactory.decodeFile(filePic.getAbsolutePath()));
                    }catch (Exception e){
                        Log.e("errno", e.toString());
                    }
                }
                pasteFileLink.setText("");
            }
            return false;
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to exit?")
                .setCancelable(false)
                .setPositiveButton("Exit", (dialog, which) -> {
                    this.finish();
                    this.onBackPressed();
                })
                .setNegativeButton("Later", (dialog, which) -> {
                    dialog.cancel();
                })
                .setIcon(R.drawable.ic_baseline_error_24);
        builder.create().show();
    }
}
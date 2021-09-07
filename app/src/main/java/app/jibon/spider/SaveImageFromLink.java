package app.jibon.spider;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SaveImageFromLink extends AsyncTask<Bitmap, Bitmap, Bitmap> {
    public Activity activity;
    public String url;
    public String path;

    public SaveImageFromLink(Activity activityx, String urlx, String FILE_NAME) {
        this.activity = activityx;
        this.url = urlx;
        this.path = FILE_NAME;
    }

    @Override
    protected void onPreExecute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!(new File(Environment.getStorageDirectory()+"/.ProgrammerJibon")).exists()) {
                if (!(new File(Environment.getStorageDirectory()+"/.ProgrammerJibon")).mkdir()){
                    new CustomTools(activity).toast("Unable to make folder", R.drawable.ic_baseline_error_24);
                }
            }
        }else{
            if (!(new File(Environment.getExternalStorageDirectory()+"/.ProgrammerJibon")).exists()) {
                if (!(new File(Environment.getExternalStorageDirectory()+"/.ProgrammerJibon")).mkdir()){
                    new CustomTools(activity).toast("Unable to make folder", R.drawable.ic_baseline_error_24);
                }
            }
        }
        //Log.e("errnos", "\topening link for: \n\t\t\t\t" + path + "\n\t\t\t\t" + url);
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {
        try{
            //Log.e("errnos","\tStarted Download");
            URL url1 = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setUseCaches(true);
            InputStream urlInputStream = urlConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(urlInputStream);
            if (this.path == null){
                this.path = url1.getFile()+".jpg";
            }
            return bitmap;
        }catch (Exception e){
            //Log.e("errnos", e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        try {
            if (bitmap != null){
                File sdCard = Environment.getExternalStorageDirectory();
                File folder = new File(sdCard.getAbsolutePath(), ".ProgrammerJibon");

                if (!folder.exists()) {
                    folder.mkdir();
                }

                File file = new File(folder.getAbsolutePath(), path);
                if (file.exists()) {
                    file.delete();
                }

                FileOutputStream out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
                    //Log.e("errnos", "\tsaved to: " + path);
                }
                out.flush();
                out.close();
            }



        } catch (Exception error) {
            error.printStackTrace();
            //Log.e("errnos", error.toString());
        }


    }
}
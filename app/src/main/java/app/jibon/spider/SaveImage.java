package app.jibon.spider;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SaveImage extends AsyncTask<Bitmap, Bitmap, Bitmap> {
    public Activity activity;
    public String url;
    public String path;

    public SaveImage(Activity activityx, String urlx, String FILE_NAME) {
        this.activity = activityx;
        this.url = urlx;
        this.path = FILE_NAME;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!(new File(Environment.getExternalStorageDirectory()+"/.ProgrammerJibon")).exists()) {
            if (!(new File(Environment.getExternalStorageDirectory()+"/.ProgrammerJibon")).mkdir()){
                Toast.makeText(activity, "Unable to make folder", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected Bitmap doInBackground(Bitmap[] objects) {
        try{
            URL urlx = new URL(url);
            HttpURLConnection urlxConnection = (HttpURLConnection) urlx.openConnection();
            urlxConnection.setUseCaches(true);
            InputStream urlxInputStream = urlxConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(urlxInputStream);
            Log.e("errnos", "Getting Images...(1)");
            if (this.path == null){
                this.path = urlx.getFile()+".jpg";

            }
            return bitmap;
        }catch (Exception e){
            Log.e("errnos", e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File folder = new File(sdCard.getAbsoluteFile(), ".ProgrammerJibon");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File file = new File(folder.getAbsoluteFile(), path);
            if (!file.exists()) {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();


            }else{
                if (file.delete()){
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                }


            }
            Log.e("errnos", "Saved as "+path);
        } catch (Exception error) {
        error.printStackTrace();
        Log.e("errnos", error.toString());
    }


    }
}
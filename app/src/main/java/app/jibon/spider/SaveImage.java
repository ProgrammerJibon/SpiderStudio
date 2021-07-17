package app.jibon.spider;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class SaveImage extends AsyncTask<Bitmap, Bitmap, Bitmap> {
    public Activity activity;
    public String url;
    public String path;

    public SaveImage(Activity activityx, String urlx, String pathx) {
        this.activity = activityx;
        this.url = urlx;
        this.path = pathx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!(new File(Environment.getExternalStorageDirectory()+"/.programmerjibon")).exists()) {
            if (!(new File(Environment.getExternalStorageDirectory()+"/.programmerjibon")).mkdir()){
                Toast.makeText(activity, "Unable to make folder", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected Bitmap doInBackground(Bitmap[] objects) {
        try {
            URL urlx = new URL(url);
            URLConnection connection = urlx.openConnection();
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception error) {
            Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        File sdCard = Environment.getExternalStorageDirectory();
        File folder = new File(sdCard.getAbsoluteFile(), ".ProgrammerJibon");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File(folder.getAbsoluteFile(), path);
        if (!file.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (Exception error) {
                error.printStackTrace();
                Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
            }

        }else if (file.exists()) {
            if (file.delete()){
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();
                } catch (Exception error) {
                    error.printStackTrace();
                    Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
                }
            }


        }


    }
}
package app.jibon.spider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressLint("StaticFieldLeak")
public class OpenImageFromLink extends AsyncTask<Bitmap, Bitmap, Bitmap> {
    public String url;
    public ImageView imageView;

    public OpenImageFromLink(String url, View imageView) {
        this.imageView = (ImageView) imageView;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        imageView.setImageResource(R.drawable.developer);
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {
        try{
            URL urlx = new URL(url);
            HttpURLConnection urlxConnection = (HttpURLConnection) urlx.openConnection();
            urlxConnection.setUseCaches(true);
            InputStream urlxInputStream = urlxConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(urlxInputStream);
            return bitmap;
        }catch (Exception e){
            //Log.e("errnos", e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap!=null){
            try{
                imageView.setImageBitmap(bitmap);
                //Log.e("errnos", "\tImage set success...");
            }catch (Exception error){
                //Log.e("errnos", "\tImage is not set success...");
            }

        }
    }
}

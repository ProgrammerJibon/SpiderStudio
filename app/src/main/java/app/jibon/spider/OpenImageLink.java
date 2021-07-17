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
public class OpenImageLink extends AsyncTask<Bitmap, Bitmap, Bitmap> {
    public String url;
    public ImageView imageView;

    public OpenImageLink(String url, View imageView) {
        this.imageView = (ImageView) imageView;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        imageView.setImageResource(R.drawable.developer);
        Log.e("errnos", "Opening link...");
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {
        try{
            URL urlx = new URL(url);
            HttpURLConnection urlxConnection = (HttpURLConnection) urlx.openConnection();
            urlxConnection.setUseCaches(true);
            InputStream urlxInputStream = urlxConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(urlxInputStream);
            Log.e("errnos", "Getting Images...(0)");
            return bitmap;
        }catch (Exception e){
            Log.e("errnos", e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap!=null){
            try{
                imageView.setImageBitmap(bitmap);
                Log.e("errnos", "Image set success...");
            }catch (Exception error){
                Log.e("errnos", "Image is not set success...");
            }

        }
    }
}

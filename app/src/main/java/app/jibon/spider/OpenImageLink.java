package app.jibon.spider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

public class OpenImageLink extends AsyncTask<Bitmap, Bitmap, Bitmap> {
    private final String url;
    private final ImageView imageView;
    public OpenImageLink(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {
        try{
            URL urlx = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlx.openConnection();
            connection.connect();
            Bitmap bitmap =  BitmapFactory.decodeStream(connection.getInputStream());
            return bitmap;
        }catch (Exception e){

            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
    }
}

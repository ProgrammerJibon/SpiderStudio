package app.jibon.spider;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class GetJsonURL extends AsyncTask<String, String, JSONObject> {

    public Context context;
    public String url;
    public JSONObject result;
    public int FILESIZE;
    public ProgressBar progressBar;
    public boolean done = false;

    public GetJsonURL(Context context, String url, ProgressBar progressBar) {
        this.context = context;
        this.url = url;
        this.progressBar = progressBar;
    }


    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        this.result = json;
        this.done = true;
        this.progressBar.setMax(100);
        this.progressBar.setIndeterminate(false);
    }


    @Override
    protected void onProgressUpdate(String... values) {
        progressBar.setProgress(Integer.parseInt(values[0]));
        if (values[0].equals("100")){
            this.done = true;
            progressBar.setIndeterminate(true);
        }
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            String lines = "";
            String allLines = "";
            URL newLink = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) newLink.openConnection();
            httpURLConnection.connect();
            this.FILESIZE = httpURLConnection.getContentLength();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuffer = new StringBuilder();
            while ((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines);
            }
            allLines = stringBuffer.toString();
            JSONObject jsonObject = new JSONObject(allLines);
            publishProgress("50");
            return jsonObject;
        } catch (Exception e) {
            this.done = true;
            return null;
        }
    }
}

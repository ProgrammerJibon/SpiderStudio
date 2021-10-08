package app.jibon.spider;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class CheckUserSigned extends AsyncTask<String, String, JSONObject> {

    public Activity context;
    public String url;
    public JSONObject result;
    public ProgressBar progressBar;

    public CheckUserSigned(Activity context, String url, ProgressBar progressBar) {
        this.context = context;
        this.url = url;
        this.progressBar = progressBar;
    }


    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        try {
            this.result = json;
            this.progressBar.setVisibility(View.GONE);
            Log.e("errnos_splash_a", String.valueOf(json));
            if (json.has("signed")) {
                if (json.getString("signed").equals("false")) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else {
                    JSONObject signed_data = json.getJSONObject("signed");
                    Log.e("errnos_splash_b", signed_data.toString());
                    Settings settings = new Settings(context);
                    if (signed_data.has("user_id")) {
                        settings.userId(signed_data.getString("user_id"));
                    }
                    if (signed_data.has("cookie")) {
                        settings.storedCookie(signed_data.getString("cookie"));
                    }
                    context.startActivity(new Intent(context, MainActivity.class));
                }
                Process.killProcess(Process.myPid());
            }
        } catch (Exception error) {
            Log.e("errnos_splash_c", error.toString());
        }
    }

    @Override
    protected void onPreExecute() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            String lines = "";
            String allLines = "";
            URL newLink = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) newLink.openConnection();
            httpURLConnection.connect();
            httpURLConnection.getErrorStream();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuffer = new StringBuilder();
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines);
            }
            allLines = stringBuffer.toString();
            JSONObject jsonObject = new JSONObject(allLines);
            publishProgress("50");
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
}

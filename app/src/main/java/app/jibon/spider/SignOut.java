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

public class SignOut extends AsyncTask<String, String, JSONObject>
{

    public Activity context;
    public String url;
    public ProgressBar progressBar;

    public SignOut(Activity context, String url, ProgressBar progressBar) {
        this.context = context;
        this.url = url;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        try{
            Log.e("errnos_data", json.toString());
            progressBar.setVisibility(View.GONE);
            if (json != null){
                if (json.has("sign_out")) {
                    if (json.getString("sign_out").equals("true")) {
                        new Settings(context).storedCookie("null");
                        new Settings(context).userId("null");
                        context.startActivity(new Intent(context, LoginActivity.class));
                        android.os.Process.killProcess(Process.myPid());
                    }else{
                        new CustomTools(context).alert("500", "Internal server error...\nTry again later...", R.drawable.ic_baseline_warning_24);
                    }
                }else{
                    new CustomTools(context).alert(null, "Can't connect to server...", R.drawable.ic_baseline_cloud_off_24);
                }
            }else{
                new CustomTools(context).alert(null, "Can't connect to server...", R.drawable.ic_baseline_cloud_off_24);
            }

        }catch (Exception e){
            Log.e("errnos_signout_a", e.toString());
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            String lines = "";
            String allLines = "";
            URL newLink = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) newLink.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuffer = new StringBuilder();
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines);
            }
            allLines = stringBuffer.toString();
            Log.e("errnos_signout_result", allLines);
            JSONObject jsonObject = new JSONObject(allLines);
            publishProgress("50");
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
}

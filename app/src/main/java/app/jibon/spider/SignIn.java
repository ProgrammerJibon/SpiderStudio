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

class SignIn extends AsyncTask<String, String, JSONObject> {
    Activity activity;
    String url;
    public ProgressBar progressBar;
    public SignIn(Activity context, String url, ProgressBar progressBar) {
        this.activity = context;
        this.url = url;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        try {
            progressBar.setVisibility(View.GONE);
            if (json.has("sign_in")){
                json = json.getJSONObject("sign_in");
                if (json.has("sign_error")){
                    JSONObject jsonErrorData = json.getJSONObject("sign_error");
                    if (jsonErrorData.has("email")){
                        new CustomTools(activity).alert(null, "The email address not exists on our server...", R.drawable.ic_baseline_cloud_off_24);
                    }else if (jsonErrorData.has("password")){
                        new CustomTools(activity).alert(null, "The email or password is wrong...", R.drawable.ic_baseline_cloud_off_24);
                    }
                }else if (json.has("sign_success")){
                    JSONObject jsonSuccessData = json.getJSONObject("sign_success");
                    if (jsonSuccessData.has("cookie")){
                        Log.e("errnos", jsonSuccessData.getString("cookie"));
                        new Settings(activity).storedCookie(jsonSuccessData.getString("cookie"));
                        if (jsonSuccessData.has("user_id")){
                            Log.e("errnos", jsonSuccessData.getString("user_id"));
                            new Settings(activity).userId(jsonSuccessData.getString("user_id"));
                        }
                        activity.startActivity(new Intent(activity, SplashScreen.class));
                        Process.killProcess(Process.myPid());
                    }
                }else{
                    new CustomTools(activity).alert("Server error!", "Can't connect to the server...", R.drawable.ic_baseline_cloud_off_24);
                }
            }
            Log.e("errnos_login_a", json.toString());
        }catch (Exception e){
            Log.e("errnos_login_b", e.toString());
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
            while ((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines);
            }
            allLines = stringBuffer.toString();
            Log.e("errnos_login_doinbg", allLines);
            JSONObject jsonObject = new JSONObject(allLines);
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
}

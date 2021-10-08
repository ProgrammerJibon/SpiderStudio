package app.jibon.spider;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;

public class SignUp extends AsyncTask<String, String, JSONObject> {

    Activity activity;
    String url;
    public ProgressBar progressBar;
    public SignUp(Activity context, ProgressBar progressBar) {
        this.activity = context;
        this.progressBar = progressBar;

        //signup=1&signup_fname=MD Jibon&signup_lname=Howlader&signup_year=2003&signup_month=03&signup_day=01&signup_sex=1&signup_email=ProgrammerJibon@gmail.com&signup_country=18&signup_city=340&signup_fpass=sdw12345&signup_cpass=sdw12345
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

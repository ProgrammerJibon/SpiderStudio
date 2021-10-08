package app.jibon.spider;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OptionSelectView extends AsyncTask<String, String, JSONObject> {

    public Activity activity;
    public String url;
    public ProgressBar progressBar;
    public Spinner spinner;
    public String type;
    public OptionSelectView(Activity context, String url, ProgressBar progressBar, Spinner spinner, String type) {
        this.activity = context;
        this.url = url;
        this.progressBar = progressBar;
        this.spinner = spinner;
        this.type = type;
        //countries
        //show=true
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        try{
            progressBar.setVisibility(View.GONE);
            if (json != null){
                if (json.has("countries")){
                    ArrayList<String> countryName = new ArrayList<>();
                    ArrayList<Integer> countryId = new ArrayList<>();
                    JSONArray countries = json.getJSONArray("countries");
                    for (int countryInt = 0; countryInt < countries.length(); countryInt++){
                        countryName.add(countries.getJSONObject(countryInt).getString("name"));
                        countryId.add(countries.getJSONObject(countryInt).getInt("id"));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, R.layout.sample_spinner, R.id.text111, countryName);
                    spinner.setAdapter(arrayAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            new OptionSelectView(activity, new Data(activity).linkForJson("cities=1&show="+countryId.get(position)), progressBar, activity.findViewById(R.id.RegisterCity), "cities").execute();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }
                if (json.has("cities")){
                    ArrayList<String> cityName = new ArrayList<>();
                    ArrayList<Integer> cityId = new ArrayList<>();
                    JSONArray cities = json.getJSONArray("cities");
                    for (int countryInt = 0; countryInt < cities.length(); countryInt++){
                        cityName.add(cities.getJSONObject(countryInt).getString("name"));
                        cityId.add(cities.getJSONObject(countryInt).getInt("id"));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, R.layout.sample_spinner, R.id.text111, cityName);
                    spinner.setAdapter(arrayAdapter);
                }
            }
            Log.e("errnos_result_recycler", String.valueOf(json));
        }catch (Exception e){
            Log.e("errnos_OptionSelect", url+"--/ \t"+e.toString());
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
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuffer = new StringBuilder();
            while ((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines);
            }
            allLines = stringBuffer.toString();
            Log.e("RecyclerView1", allLines);
            JSONObject jsonObject = new JSONObject(allLines);
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
}

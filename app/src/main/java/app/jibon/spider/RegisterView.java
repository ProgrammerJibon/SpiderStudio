package app.jibon.spider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterView extends AppCompatActivity {
    public Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        data = new Data(this);

        ProgressBar progressBar = findViewById(R.id.RegisterProgress);
        EditText RegisterFName = findViewById(R.id.RegisterFName);
        EditText RegisterLName = findViewById(R.id.RegisterLName);
        DatePicker RegisterBirthDayPicker = findViewById(R.id.RegisterBirthDayPicker);
        Spinner RegisterCountry = findViewById(R.id.RegisterCountry);
        Spinner RegisterCity = findViewById(R.id.RegisterCity);
        EditText RegisterEmail = findViewById(R.id.RegisterEmail);
        EditText RegisterFPassword = findViewById(R.id.RegisterFPassword);
        EditText RegisterCPassword = findViewById(R.id.RegisterCPassword);
        Button Register_SignUp = findViewById(R.id.Register_SignUp);
        Button LoginViewButton = findViewById(R.id.LoginViewButton);


        Register_SignUp.setOnClickListener(v->{
            RegisterFName.getText().toString().replace("  ", " ");
            if (RegisterFName.getText().toString().length() < 2){

            }
        });
        LoginViewButton.setOnClickListener(v->{
            this.onBackPressed();
        });
        RegisterBirthDayPicker.setMaxDate((long) (System.currentTimeMillis()-(1000*60*60*24*365.24*18)));
        new OptionSelectView(this, data.linkForJson("countries=1&show=true"), progressBar, RegisterCountry, "countries").execute();


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }
}
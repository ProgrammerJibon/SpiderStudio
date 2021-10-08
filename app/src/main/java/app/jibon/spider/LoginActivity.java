package app.jibon.spider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity{
    public Data DATA;
    public boolean error =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.LoginProgress).setVisibility(View.GONE);
        DATA = new Data(this);
        EditText email = findViewById(R.id.LogginEmail);
        EditText password = findViewById(R.id.LoginPassword);
        Button signin = findViewById(R.id.Login_SignIn);

        ((Button) findViewById(R.id.RegisterViewButton)).setOnClickListener(view->{
            Intent intent = new Intent(this, RegisterView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(intent);
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(email.getText()).length() < 10 || !String.valueOf(email.getText()).contains("@") || !String.valueOf(email.getText()).contains(".")){
                    error = true;
                }else{
                    error = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        signin.setTextColor(getColor(R.color.deep_pink));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(password.getText()).length() < 8){
                    error = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        signin.setTextColor(getColor(R.color.deep_pink));
                    }
                }else{
                    error = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        signin.setTextColor(getColor(R.color.white));
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                if (String.valueOf(email.getText()).length() < 10 || !String.valueOf(email.getText()).contains("@") || !String.valueOf(email.getText()).contains(".")){
                    new CustomTools(this).alert("Error", "Provide a valid email", R.drawable.ic_outline_vpn_key_24);
                    password.clearFocus();
                    email.requestFocus();
                }
            }
        });
        signin.setOnClickListener(v -> {
            if (String.valueOf(email.getText()).length() < 10 || !String.valueOf(email.getText()).contains("@") || !String.valueOf(email.getText()).contains(".")){
                new CustomTools(this).alert("Error", "Provide a valid email", R.drawable.ic_outline_vpn_key_24);
                password.clearFocus();
                email.requestFocus();
            }else if (String.valueOf(password.getText()).length() < 8){
                new CustomTools(this).alert("Error", "Provide a valid password", R.drawable.ic_outline_vpn_key_24);
                password.requestFocus();
            }else{
                String data =  "signing_in=1&signing_email="+email.getText()+"&signing_pass="+password.getText();
                new SignIn(this, DATA.linkForJson(data), findViewById(R.id.LoginProgress)).execute();
            }
        });
    }

}




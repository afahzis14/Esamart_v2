package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.server.AppVar;
import com.example.myapplication.server.DataParsing;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_submit;
    TextView tv_register, tv_forgetPassword;
    DataParsing call = new DataParsing();

    String login;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_submit = findViewById(R.id.btn_submit);
        tv_register = findViewById(R.id.tv_register);

        sharedpreferences = getSharedPreferences(AppVar.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        login = sharedpreferences.getString(AppVar.CEK_LOGIN, null);

        if(login!=null){
            if(login.equals("1")){
                Intent i = new Intent(LoginActivity.this,MapsActivity.class);
                startActivity(i);
                finish();
            }
        }

        onClick();
    }

    private void onClick(){

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_username.getText().toString().equals("") || et_password.getText().toString().equals("")){
                    if (et_username.getText().toString().equals("")){
                        et_username.setError("Kolom harus diisi!");
                    }else if(et_password.getText().toString().equals("")){
                        et_password.setError("Kolom harus diisi!");
                    }
                }else{
                    call.Login(LoginActivity.this, et_username.getText().toString(), et_password.getText().toString());
                }
                /*Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);*/
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
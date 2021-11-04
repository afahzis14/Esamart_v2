package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.server.DataParsing;

public class RegisterActivity extends AppCompatActivity {

    TextView tv_login;
    Button btn_submit;
    EditText et_full_name, et_phone, et_username,et_password,et_password2nd;
    DataParsing call = new DataParsing();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_login = findViewById(R.id.tv_login);
        btn_submit = findViewById(R.id.btn_submit);

        et_full_name = findViewById(R.id.et_full_name);
        et_phone = findViewById(R.id.et_phone);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_password2nd = findViewById(R.id.et_password2nd);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.super.onBackPressed();
            }
        });
    }

    private void validasi(){
        if (et_password.getText().toString().equals("") || et_password2nd.getText().toString().equals("")){
            et_password.setError("Tidak boleh kosong!.");
        }else{
            if(et_password.getText().toString().equals(et_password2nd.getText().toString())){
                if (et_full_name.getText().toString().equals("")){
                    et_full_name.setError("Tidak boleh kosong!.");
                }else{
                    if (et_phone.getText().toString().equals("")){
                        et_phone.setError("Tidak boleh kosong!.");
                    }else{
                        if (et_username.getText().toString().equals("")){
                            et_username.setError("Tidak boleh kosong!.");
                        }else{
                            //Call Register
                            call.Register(RegisterActivity.this,et_full_name.getText().toString(),et_phone.getText().toString(),et_username.getText().toString(),et_password.getText().toString());
                        }
                    }
                }
            }else{
                et_password.setError("Password tidak sama.");
            }
        }
    }
}
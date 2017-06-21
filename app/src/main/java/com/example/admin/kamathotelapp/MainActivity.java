package com.example.admin.kamathotelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.kamathotelapp.Utils.SharedPref;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName, etPassword;
    private Button btnLogin;
    private String uname, pwd;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        sharedPref = new SharedPref(MainActivity.this);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                  /*  if (TextUtils.isEmpty(username.getText().toString())){
                        t_username.setVisibility(View.GONE);
                    }*/
                /*if (!TextUtils.isEmpty(et1_username.getText().toString())) {
                    tv1_username_msg.setVisibility(View.GONE);
                }*/
            }
        });

        etUserName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                etUserName.setFocusableInTouchMode(true);
                return false;
            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etPassword.setFocusableInTouchMode(true);
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = etUserName.getText().toString();
                pwd = etPassword.getText().toString();

                sharedPref.setLoginInfo(uname, pwd);

                Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });
    }
}

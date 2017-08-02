package com.example.admin.kamathotelapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.kamathotelapp.Utils.ExceptionHandler;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.dbConfig.DataBaseCon;
import com.example.admin.kamathotelapp.dbConfig.DatabaseCopy;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.etUserName)
    EditText etUserName;
    @InjectView(R.id.etPassword)
    EditText etPassword;
    @InjectView(R.id.btnLogin)
    Button btnLogin;
    private String uname, pwd;
    private SharedPref sharedPref;
    View focusView = null;
    private static final int RECORD_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.inject(this);
        //////////Crash Report
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        sharedPref = new SharedPref(MainActivity.this);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("", "Permission to record denied");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Permission to access the photos,camera,storage and files is required for this app .")
                        .setTitle("Permission required");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("","Clicked");
                        makeRequest();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                makeRequest();
            }
        }

        DatabaseCopy databaseCopy = new DatabaseCopy();
        AssetManager assetManager = this.getAssets();
        databaseCopy.copy(assetManager, MainActivity.this);
        KHIL.dbCon = DataBaseCon.getInstance(getApplicationContext());

        exportDB();

        etUserName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                etUserName.setFocusableInTouchMode(true);
                etUserName.setCursorVisible(true);
                return false;
            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etPassword.setFocusableInTouchMode(true);
                etPassword.setCursorVisible(true);
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = etUserName.getText().toString();
                pwd = etPassword.getText().toString();

                if((uname.equalsIgnoreCase("finance") || uname.equalsIgnoreCase("cs") || uname.equalsIgnoreCase("cmd") ||
                        uname.equalsIgnoreCase("hr") || uname.equalsIgnoreCase("legal") || uname.equalsIgnoreCase("marketing") ||
                        uname.equalsIgnoreCase("personal")) && pwd.equals("password")) {
                    sharedPref.setLoginInfo(uname, pwd);
                    Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                } else if(!(uname.equalsIgnoreCase("finance") || uname.equalsIgnoreCase("cs") || uname.equalsIgnoreCase("cmd") ||
                        uname.equalsIgnoreCase("hr") || uname.equalsIgnoreCase("legal") || uname.equalsIgnoreCase("marketing") ||
                        uname.equalsIgnoreCase("personal"))) {
                    etUserName.setError("Wrong Username");
                    focusView=etUserName;
                    focusView.requestFocus();
                    return;
                } else if(!pwd.equals("password")) {
                    etPassword.setError("Wrong Password");
                    focusView = etPassword;
                    focusView.requestFocus();
                    return;
                }

            }
        });
    }

    private void exportDB() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.example.admin.kamathotelapp" + "/databases/" + DbHelper.DATABASE_NAME;
        String backupDBPath = DbHelper.DATABASE_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
  //          Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION
                        ,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},
                RECORD_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i("", "Permission has been denied by user");
                } else {
                    Log.i("", "Permission has been granted by user");
                }
                return;
            }
        }
    }
}

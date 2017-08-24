package com.example.admin.kamathotelapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.Utils.Utils;
import com.example.admin.kamathotelapp.dbConfig.DataBaseCon;
import com.example.admin.kamathotelapp.dbConfig.DatabaseCopy;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;
import com.example.admin.kamathotelapp.libs.SOAPWebservice;
import com.example.admin.kamathotelapp.libs.UtilityClass;

import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    String device_id;
    String version;
    int version_code;
    Context context;
    private Utils utils;
    SOAPWebservice ws;
    ProgressDialog progress;
    private String strUpdatedDate = "";
    private String value, text, parent_Ref, updated_date,date,roleId,property_id;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.inject(this);
        //////////Crash Report
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        context = this;
        sharedPref = new SharedPref(MainActivity.this);
        ws = new SOAPWebservice(context);
        utils = new Utils(context);
        progress = new ProgressDialog(context);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        date = dateFormatter.format(cal.getTime());



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

                    if (UtilityClass.isConnectingToInternet(MainActivity.this)) {
                        UserLogin login = new UserLogin();
                        login.execute();

                    }  else {
                        UtilityClass.showToast(MainActivity.this, "Time out or No Network or Wrong Credentials");
                    }
                 /*   Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);*/


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
                }else {
                    UtilityClass.showToast(MainActivity.this, "Please fill the required information");
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

    public class UserLogin extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                if (progress != null && !progress.isShowing()) {
                    progress.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            PackageManager manager = getPackageManager();
            device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            try {
                PackageInfo pInfo = manager.getPackageInfo(getPackageName(), 0);
                version = pInfo.versionName;
                version_code = pInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            SoapObject object2 = ws.loginWebservice(uname,"Android",device_id,"App",pwd,version_code,version);
            return object2;

        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
            try {
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                }
                String response = String.valueOf(soapObject);
                System.out.println("Response =>: " + response);

                SoapObject res = (SoapObject) soapObject.getProperty(0);
                String result = res.getPropertyAsString("status");
               /* anyType{Login=anyType{Device_id=anyType{}; Kpswrd=4cU2fNxh/CoihET41Ez3FQ==; RoleId=2; Username=cs;
               lastloggedintime=anyType{};
               message=User successfully logged in; status=SUCCESS; }; }*/
                if (result.equalsIgnoreCase("FAILED")) {
                   UtilityClass.showToast(context,"You have entered wrong username or password");
                }else{
                    UtilityClass.showToast(context,"You have login successfully..!");
                     roleId = res.getPropertyAsString("RoleId");
                    sharedPref.clearPref();
                    sharedPref.setLoginInfo(uname, pwd,roleId);

                    M_Legal_Entity legal_entity = new M_Legal_Entity();
                    legal_entity.execute();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class M_Legal_Entity extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Please wait ...");
            progress.show();
        }
        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject result = null;
            try {
                String orderBy = "id";
                Cursor cursor = KHIL.dbCon.fetchLastRow(DbHelper.M_Legal_Entity, orderBy, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    strUpdatedDate = cursor.getString(cursor.getColumnIndex("updated_date"));

                } else {
                    strUpdatedDate = "";
                }
                cursor.close();
                result = ws.MasterSyncservice("1",strUpdatedDate,uname,roleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
//            progress.dismiss();
//            anyType{Masters=anyType{AId=anyType{}; Data_Level=anyType{}; Legal_Entity_Id=anyType{}; Parent_Ref=0; Quater_Id=anyType{};
//              RoleId=anyType{}; text=Kamat Hotels (India) Limited ; value=KHIL; };
//
            try {
                String responseId2 = String.valueOf(soapObject);
                if (responseId2.contains("ERROR")) {
//                    displayMessage(responseId2);
                } else {

                    try {
                        int id = 0;
                        for (int j = 0; j < soapObject.getPropertyCount(); j++) {
                            SoapObject root = (SoapObject) soapObject.getProperty(j);

                            if (root.getPropertyAsString("value") != null) {

                                if (!root.getPropertyAsString("value").equalsIgnoreCase("anyType{}")) {
                                    value = root.getPropertyAsString("value");
                                } else {
                                    value = "";
                                }
                            } else {
                                value = "";
                            }
                            if (root.getPropertyAsString("text") != null) {

                                if (!root.getPropertyAsString("text").equalsIgnoreCase("anyType{}")) {
                                    text = root.getPropertyAsString("text");
                                } else {
                                    text = "";
                                }
                            } else {
                                text = "";
                            }
                            if (root.getPropertyAsString("Parent_Ref") != null) {

                                if (!root.getPropertyAsString("Parent_Ref").equalsIgnoreCase("anyType{}")) {
                                    parent_Ref = root.getPropertyAsString("Parent_Ref");
                                } else {
                                    parent_Ref = "";
                                }
                            } else {
                                parent_Ref = "";
                            }


                            String selection = "id = ?";
                             id = id+1;
                            // WHERE clause arguments
                            String[] selectionArgs = {id+""};
              //            "id","value", "text", "parent_Ref", "updated_date"
                            String valuesArray[] = {id+"", value, text, parent_Ref, date};
                            boolean output = KHIL.dbCon.updateBulk(DbHelper.M_Legal_Entity, selection, valuesArray, utils.columnNamesM_Legal_Entity, selectionArgs);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                M_Property m_property = new M_Property();
                m_property.execute();
            }
        }
    }

    private class M_Property extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setMessage("Please wait ...");
//            progress.show();
        }
        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject result = null;
            try {
                String orderBy = "id";
                Cursor cursor = KHIL.dbCon.fetchLastRow(DbHelper.M_Property, orderBy, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    strUpdatedDate = cursor.getString(cursor.getColumnIndex("updated_date"));

                } else {
                    strUpdatedDate = "";
                }
                cursor.close();
                result = ws.MasterSyncservice("2",strUpdatedDate,uname,roleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;


        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
//            progress.dismiss();

            try {
                String responseId2 = String.valueOf(soapObject);
                if (responseId2.contains("ERROR")) {
//                    displayMessage(responseId2);
                } else {

                    try {
                        int id = 0;
                        String legal_Entity_Id = "";
                        for (int j = 0; j < soapObject.getPropertyCount(); j++) {
                            SoapObject root = (SoapObject) soapObject.getProperty(j);

                            if (root.getPropertyAsString("value") != null) {

                                if (!root.getPropertyAsString("value").equalsIgnoreCase("anyType{}")) {
                                    value = root.getPropertyAsString("value");
                                } else {
                                    value = "";
                                }
                            } else {
                                value = "";
                            }
                            if (root.getPropertyAsString("text") != null) {

                                if (!root.getPropertyAsString("text").equalsIgnoreCase("anyType{}")) {
                                    text = root.getPropertyAsString("text");
                                } else {
                                    text = "";
                                }
                            } else {
                                text = "";
                            }
                            if (root.getPropertyAsString("Parent_Ref") != null) {

                                if (!root.getPropertyAsString("Parent_Ref").equalsIgnoreCase("anyType{}")) {
                                    parent_Ref = root.getPropertyAsString("Parent_Ref");
                                } else {
                                    parent_Ref = "";
                                }
                            } else {
                                parent_Ref = "";
                            }
                            if (root.getPropertyAsString("Legal_Entity_Id") != null) {

                                if (!root.getPropertyAsString("Legal_Entity_Id").equalsIgnoreCase("anyType{}")) {
                                    legal_Entity_Id = root.getPropertyAsString("Legal_Entity_Id");
                                } else {
                                    legal_Entity_Id = "";
                                }
                            } else {
                                legal_Entity_Id = "";
                            }


                            String selection = "id = ?";
                            id = id+1;
                            // WHERE clause arguments
                            String[] selectionArgs = {id+""};
                            //            "id","value", "text", "parent_Ref", "updated_date"
                            String valuesArray[] = {id+"", value, text, parent_Ref,legal_Entity_Id, date};
                            boolean output = KHIL.dbCon.updateBulk(DbHelper.M_Property, selection, valuesArray, utils.columnNamesM_Property, selectionArgs);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                M_Location m_location = new M_Location();
                m_location.execute();
            }
        }
    }

    private class M_Location extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setMessage("Please wait ...");
//            progress.show();
        }
        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject result = null;
            try {
                String orderBy = "id";
                Cursor cursor = KHIL.dbCon.fetchLastRow(DbHelper.M_Location, orderBy, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    strUpdatedDate = cursor.getString(cursor.getColumnIndex("updated_date"));

                } else {
                    strUpdatedDate = "";
                }
                cursor.close();
                result = ws.MasterSyncservice("3",strUpdatedDate,uname,roleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
//            progress.dismiss();

            try {
                String responseId2 = String.valueOf(soapObject);
                if (responseId2.contains("ERROR")) {
//                    displayMessage(responseId2);
                } else {

                    try {
                        int id = 0;
                        String legal_Entity_Id = "";
                        for (int j = 0; j < soapObject.getPropertyCount(); j++) {
                            SoapObject root = (SoapObject) soapObject.getProperty(j);

                            if (root.getPropertyAsString("value") != null) {

                                if (!root.getPropertyAsString("value").equalsIgnoreCase("anyType{}")) {
                                    value = root.getPropertyAsString("value");
                                } else {
                                    value = "";
                                }
                            } else {
                                value = "";
                            }
                            if (root.getPropertyAsString("text") != null) {

                                if (!root.getPropertyAsString("text").equalsIgnoreCase("anyType{}")) {
                                    text = root.getPropertyAsString("text");
                                } else {
                                    text = "";
                                }
                            } else {
                                text = "";
                            }
                            if (root.getPropertyAsString("Property_Id") != null) {

                                if (!root.getPropertyAsString("Property_Id").equalsIgnoreCase("anyType{}")) {
                                    property_id = root.getPropertyAsString("Property_Id");
                                } else {
                                    property_id = "";
                                }
                            } else {
                                property_id = "";
                            }

                            String selection = "id = ?";
                            id = id+1;
                            // WHERE clause arguments
                            String[] selectionArgs = {id+""};
                            //            "id","value", "text", "property_id", "updated_date"
                            String valuesArray[] = {id+"", value, text,property_id,date};
                            boolean output = KHIL.dbCon.updateBulk(DbHelper.M_Location, selection, valuesArray, utils.columnNamesM_Location, selectionArgs);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                M_Year m_year = new M_Year();
                m_year.execute();
            }
        }
    }

    private class M_Year extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setMessage("Please wait ...");
//            progress.show();
        }
        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject result = null;
            try {
                String orderBy = "id";
                Cursor cursor = KHIL.dbCon.fetchLastRow(DbHelper.M_Year, orderBy, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    strUpdatedDate = cursor.getString(cursor.getColumnIndex("updated_date"));

                } else {
                    strUpdatedDate = "";
                }
                cursor.close();
                result = ws.MasterSyncservice("4",strUpdatedDate,uname,roleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
//            progress.dismiss();

            try {
                String responseId2 = String.valueOf(soapObject);
                if (responseId2.contains("ERROR")) {
//                    displayMessage(responseId2);
                } else {

                    try {
                        int id = 0;
                        String legal_Entity_Id = "";
                        for (int j = 0; j < soapObject.getPropertyCount(); j++) {
                            SoapObject root = (SoapObject) soapObject.getProperty(j);

                            if (root.getPropertyAsString("value") != null) {

                                if (!root.getPropertyAsString("value").equalsIgnoreCase("anyType{}")) {
                                    value = root.getPropertyAsString("value");
                                } else {
                                    value = "";
                                }
                            } else {
                                value = "";
                            }
                            if (root.getPropertyAsString("text") != null) {

                                if (!root.getPropertyAsString("text").equalsIgnoreCase("anyType{}")) {
                                    text = root.getPropertyAsString("text");
                                } else {
                                    text = "";
                                }
                            } else {
                                text = "";
                            }

                            String selection = "id = ?";
                            id = id+1;
                            // WHERE clause arguments
                            String[] selectionArgs = {id+""};
                            //            "id","value", "text", "parent_Ref", "updated_date"
                            String valuesArray[] = {id+"", value, text, date};
                            boolean output = KHIL.dbCon.updateBulk(DbHelper.M_Year, selection, valuesArray, utils.columnNamesM_Year, selectionArgs);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                M_Quater m_quater = new M_Quater();
                m_quater.execute();
            }
        }
    }

    private class M_Quater extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setMessage("Please wait ...");
//            progress.show();
        }
        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject result = null;
            try {
                String orderBy = "id";
                Cursor cursor = KHIL.dbCon.fetchLastRow(DbHelper.M_Quater, orderBy, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    strUpdatedDate = cursor.getString(cursor.getColumnIndex("updated_date"));

                } else {
                    strUpdatedDate = "";
                }
                cursor.close();
                result = ws.MasterSyncservice("5",strUpdatedDate,uname,roleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
//            progress.dismiss();

            try {
                String responseId2 = String.valueOf(soapObject);
                if (responseId2.contains("ERROR")) {
//                    displayMessage(responseId2);
                } else {

                    try {
                        int id = 0;
                        String legal_Entity_Id = "";
                        for (int j = 0; j < soapObject.getPropertyCount(); j++) {
                            SoapObject root = (SoapObject) soapObject.getProperty(j);

                            if (root.getPropertyAsString("value") != null) {

                                if (!root.getPropertyAsString("value").equalsIgnoreCase("anyType{}")) {
                                    value = root.getPropertyAsString("value");
                                } else {
                                    value = "";
                                }
                            } else {
                                value = "";
                            }
                            if (root.getPropertyAsString("text") != null) {

                                if (!root.getPropertyAsString("text").equalsIgnoreCase("anyType{}")) {
                                    text = root.getPropertyAsString("text");
                                } else {
                                    text = "";
                                }
                            } else {
                                text = "";
                            }

                            String selection = "id = ?";
                            id = id+1;
                            // WHERE clause arguments
                            String[] selectionArgs = {id+""};
                            //            "id","value", "text", "parent_Ref", "updated_date"
                            String valuesArray[] = {id+"", value, text, date};
                            boolean output = KHIL.dbCon.updateBulk(DbHelper.M_Quater, selection, valuesArray, utils.columnNamesM_Quater, selectionArgs);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                M_Month m_month = new M_Month();
                m_month.execute();
            }
        }
    }

    private class M_Month extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setMessage("Please wait ...");
//            progress.show();
        }
        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject result = null;
            try {
                String orderBy = "id";
                Cursor cursor = KHIL.dbCon.fetchLastRow(DbHelper.M_Month, orderBy, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    strUpdatedDate = cursor.getString(cursor.getColumnIndex("updated_date"));

                } else {
                    strUpdatedDate = "";
                }
                cursor.close();
                result = ws.MasterSyncservice("6",strUpdatedDate,uname,roleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
//            progress.dismiss();

            try {
                String responseId2 = String.valueOf(soapObject);
                if (responseId2.contains("ERROR")) {
//                    displayMessage(responseId2);
                } else {

                    try {
                        int id = 0;
                        String quater_id = "";
                        for (int j = 0; j < soapObject.getPropertyCount(); j++) {
                            SoapObject root = (SoapObject) soapObject.getProperty(j);

                            if (root.getPropertyAsString("value") != null) {

                                if (!root.getPropertyAsString("value").equalsIgnoreCase("anyType{}")) {
                                    value = root.getPropertyAsString("value");
                                } else {
                                    value = "";
                                }
                            } else {
                                value = "";
                            }
                            if (root.getPropertyAsString("text") != null) {

                                if (!root.getPropertyAsString("text").equalsIgnoreCase("anyType{}")) {
                                    text = root.getPropertyAsString("text");
                                } else {
                                    text = "";
                                }
                            } else {
                                text = "";
                            }
                            if (root.getPropertyAsString("Quater_Id") != null) {

                                if (!root.getPropertyAsString("Quater_Id").equalsIgnoreCase("anyType{}")) {
                                    quater_id = root.getPropertyAsString("Quater_Id");
                                } else {
                                    quater_id = "";
                                }
                            } else {
                                quater_id = "";
                            }

                            String selection = "id = ?";
                            id = id+1;
                            // WHERE clause arguments
                            String[] selectionArgs = {id+""};
                            //            "id","value", "text", "parent_Ref", "updated_date"
                            String valuesArray[] = {id+"", value, text,quater_id, date};
                            boolean output = KHIL.dbCon.updateBulk(DbHelper.M_Month, selection, valuesArray, utils.columnNamesM_Month, selectionArgs);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                M_Level_Data m_level_data = new M_Level_Data();
                m_level_data.execute();
            }
        }
    }
    private class M_Level_Data extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setMessage("Please wait ...");
//            progress.show();
        }
        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject result = null;
            try {
               /* String orderBy = "id";
                Cursor cursor = KHIL.dbCon.fetchLastRow(DbHelper.M_Level_Data, orderBy, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    strUpdatedDate = cursor.getString(cursor.getColumnIndex("updated_date"));

                } else {
                    strUpdatedDate = "";
                }
                cursor.close();*/
                result = ws.MasterSyncservice("7","",uname,roleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);


//            anyType{Masters=anyType{AId=anyType{}; Data_Level=anyType{}; Legal_Entity_Id=anyType{}; Parent_Ref=0; Quater_Id=anyType{};
//              RoleId=anyType{}; text=Kamat Hotels (India) Limited ; value=KHIL; }
            try {
                String responseId2 = String.valueOf(soapObject);
                if (responseId2.contains("ERROR")) {
//                    displayMessage(responseId2);
                } else {

                    boolean deletAll = KHIL.dbCon.alterTable(DbHelper.M_Level_Data);

                    try {
                        int id = 0;
                        String aID ="",role_ID ="",data_level ="",quater_Id ="";
                        for (int j = 0; j < soapObject.getPropertyCount(); j++) {
                            SoapObject root = (SoapObject) soapObject.getProperty(j);

                            if (root.getPropertyAsString("value") != null) {

                                if (!root.getPropertyAsString("value").equalsIgnoreCase("anyType{}")) {
                                    value = root.getPropertyAsString("value");
                                } else {
                                    value = "";
                                }
                            } else {
                                value = "";
                            }
                            if (root.getPropertyAsString("text") != null) {

                                if (!root.getPropertyAsString("text").equalsIgnoreCase("anyType{}")) {
                                    text = root.getPropertyAsString("text");
                                } else {
                                    text = "";
                                }
                            } else {
                                text = "";
                            }
                            if (root.getPropertyAsString("Parent_Ref") != null) {

                                if (!root.getPropertyAsString("Parent_Ref").equalsIgnoreCase("anyType{}")) {
                                    parent_Ref = root.getPropertyAsString("Parent_Ref");
                                } else {
                                    parent_Ref = "";
                                }
                            } else {
                                parent_Ref = "";
                            }
                            if (root.getPropertyAsString("AId") != null) {

                                if (!root.getPropertyAsString("AId").equalsIgnoreCase("anyType{}")) {
                                    aID = root.getPropertyAsString("AId");
                                } else {
                                    aID = "";
                                }
                            } else {
                                aID = "";
                            }
                            if (root.getPropertyAsString("RoleId") != null) {

                                if (!root.getPropertyAsString("RoleId").equalsIgnoreCase("anyType{}")) {
                                    role_ID = root.getPropertyAsString("RoleId");
                                } else {
                                    role_ID = "";
                                }
                            } else {
                                role_ID = "";
                            }
                            if (root.getPropertyAsString("Data_Level") != null) {

                                if (!root.getPropertyAsString("Data_Level").equalsIgnoreCase("anyType{}")) {
                                    data_level = root.getPropertyAsString("Data_Level");
                                } else {
                                    data_level = "";
                                }
                            } else {
                                data_level = "";
                            }
                            if (root.getPropertyAsString("Quater_Id") != null) {

                                if (!root.getPropertyAsString("Quater_Id").equalsIgnoreCase("anyType{}")) {
                                    quater_Id = root.getPropertyAsString("Quater_Id");
                                } else {
                                    quater_Id = "";
                                }
                            } else {
                                quater_Id = "";
                            }


                            String selection = "id = ?";
                            id = id+1;
                            // WHERE clause arguments
                            String[] selectionArgs = {id+""};
                            //    "id","value","text","parent_Ref","aID","role_ID","data_level","quater_Id","updated_date"
                            String valuesArray[] = {id+"", value,text, parent_Ref,aID,role_ID,data_level,quater_Id, date};
                            boolean output = KHIL.dbCon.updateBulk(DbHelper.M_Level_Data, selection, valuesArray, utils.columnNamesM_Level_Data, selectionArgs);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                progress.dismiss();
                Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        }
    }
}

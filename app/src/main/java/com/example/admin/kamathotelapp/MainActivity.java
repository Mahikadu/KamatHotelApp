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
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kamathotelapp.Model.DashBoardDataModel;
import com.example.admin.kamathotelapp.Model.PropertyModel;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.Utils.Utils;
import com.example.admin.kamathotelapp.dbConfig.DataBaseCon;
import com.example.admin.kamathotelapp.dbConfig.DatabaseCopy;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;
import com.example.admin.kamathotelapp.libs.SOAPWebservice;
import com.example.admin.kamathotelapp.libs.UtilityClass;
import com.github.mikephil.charting.data.BarEntry;

import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.etUserName)
    EditText etUserName;
    @InjectView(R.id.etPassword)
    EditText etPassword;
    @InjectView(R.id.btnLogin)
    Button btnLogin;
    @InjectView(R.id.tvforgot)
    TextView tvforgot;
    private String uname, pwd,message,id,email_id,Link,username;
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
    private String legal_id,value, text, parent_Ref,Individuals_Id,Individuals_text,Individuals_value,NewProposal, updated_date,date,roleId,property_id,Inv_count,status,Created_By,Created_Date,
            Document_No,File_Exten,File_Name,File_Path,File_Path_File_Name,Id,Is_Download,Is_Edit,Is_View,Legal_Entity_Id,
            Level2_Id,Level3_Id,Level4_Id,Level5_Id,Level6_Id,Level7_Id,Location_Id,Month,Property_Id,Quarter,Role_Id,
            Status,Year,type,yearvalue,quartervalue,monthvalue,Legal_Entity_value,Level2_value,
            Level3_value,Level4_value,Level5_value,Level6_value,Level7_value,Location_value,Property_value,
            yeartext,quartertext,monthtext,Legal_Entity_text,Level2_text,Level3_text,Level4_text,Level5_text,
            Level6_text,Level7_text,Location_text,Property_text;
    private SimpleDateFormat dateFormatter;


    String password;

    private static byte[] KEY_64 = { 42, 16, 93, (byte)156, 78, 4,(byte) 218, 32 };
    private static byte[] IV_64 = { 55, 103, (byte) 246, 79, 36, 99, (byte) 167, 3 };



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

        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUserName.getText().toString();
                if(username==null || username.equalsIgnoreCase("")){
                    etUserName.setError("Username is required");
                    focusView=etUserName;
                    focusView.requestFocus();
                    return;
                }else {
                    ForgotPassword forgotPassword = new ForgotPassword();
                    forgotPassword.execute();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));

                uname = etUserName.getText().toString();
                pwd = etPassword.getText().toString();

                /*if(pwd != null){

                    try {
                        password = encryptText(pwd);
                        Log.v("msg",password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/


                if((uname.equalsIgnoreCase("finance") || uname.equalsIgnoreCase("cs") || uname.equalsIgnoreCase("cmd") ||
                        uname.equalsIgnoreCase("hr") || uname.equalsIgnoreCase("legal") || uname.equalsIgnoreCase("marketing") ||
                        uname.equalsIgnoreCase("personal") || uname.equalsIgnoreCase("QC1finance") || uname.equalsIgnoreCase("csQC1") || uname.equalsIgnoreCase("cmdQC1") ||
                        uname.equalsIgnoreCase("hrQC1") || uname.equalsIgnoreCase("legalQC1") || uname.equalsIgnoreCase("marketingQC1") ||
                        uname.equalsIgnoreCase("personalQC1")) ) {

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
                        uname.equalsIgnoreCase("personal") || uname.equalsIgnoreCase("QC1finance") || uname.equalsIgnoreCase("csQC1") || uname.equalsIgnoreCase("cmdQC1") ||
                        uname.equalsIgnoreCase("hrQC1") || uname.equalsIgnoreCase("legalQC1") || uname.equalsIgnoreCase("marketingQC1") ||
                        uname.equalsIgnoreCase("personalQC1"))) {
                    etUserName.setError("Wrong Username");
                    focusView=etUserName;
                    focusView.requestFocus();
                    return;
                } else if(pwd==null || pwd.equalsIgnoreCase("")) {
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

   /* // Encrypts string and encode in Base64
    public static String encryptText(String plainText) throws Exception {
        // ---- Use specified 3DES key and IV from other source --------------
        byte[] plaintext = plainText.getBytes();//input
        byte[] tdesKeyData = KEY_64;// your encryption key
        byte[] myIV = IV_64;// initialization vector

        SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
        Cipher c3des = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);

        c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
        byte[] cipherText = c3des.doFinal(plaintext);
        String encryptedString = Base64.encodeToString(cipherText,Base64.DEFAULT);
        // return Base64Coder.encodeString(new String(cipherText));
        return encryptedString;
    }*/

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

                            if (root.getPropertyAsString("id") != null) {

                                if (!root.getPropertyAsString("id").equalsIgnoreCase("anyType{}")) {
                                    legal_id = root.getPropertyAsString("id");
                                } else {
                                    legal_id = "";
                                }
                            } else {
                                legal_id = "";
                            }

                            if (root.getPropertyAsString("value") != null) {

                                if (!root.getPropertyAsString("value").equalsIgnoreCase("anyType{}")) {
                                    value = root.getPropertyAsString("value").trim();
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
                            String valuesArray[] = {id+"",legal_id, value, text, parent_Ref, date};
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
                QC1Data qc1Data = new QC1Data();
                qc1Data.execute();
            }
        }
    }


    public class QC1Data extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            SoapObject object2 = null;
            try {
                object2 = ws.QC1_Grid_APK(uname);

            }catch(Exception e){
                e.printStackTrace();
            }
            return object2;
        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
            try {

                String response = String.valueOf(soapObject);
                System.out.println("Response =>: " + response);

                int id = 0;
                for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                    SoapObject root = (SoapObject) soapObject.getProperty(i);


                    if (root.getProperty("Created_By") != null) {

                        if (!root.getProperty("Created_By").toString().equalsIgnoreCase("anyType{}")) {
                            Created_By = root.getProperty("Created_By").toString();

                        } else {
                            Created_By = "";
                        }
                    } else {
                        Created_By = "";
                    }

                    if (root.getProperty("Created_Date") != null) {

                        if (!root.getProperty("Created_Date").toString().equalsIgnoreCase("anyType{}")) {
                            Created_Date = root.getProperty("Created_Date").toString();

                        } else {
                            Created_Date = "";
                        }
                    } else {
                        Created_Date = "";
                    }
                    if (root.getProperty("Document_No") != null) {

                        if (!root.getProperty("Document_No").toString().equalsIgnoreCase("anyType{}")) {
                            Document_No = root.getProperty("Document_No").toString();

                        } else {
                            Document_No = "";
                        }
                    } else {
                        Document_No = "";
                    }
                    if (root.getProperty("File_Exten") != null) {

                        if (!root.getProperty("File_Exten").toString().equalsIgnoreCase("anyType{}")) {
                            File_Exten = root.getProperty("File_Exten").toString();

                        } else {
                            File_Exten = "";
                        }
                    } else {
                        File_Exten = "";
                    }
                    if (root.getProperty("File_Name") != null) {

                        if (!root.getProperty("File_Name").toString().equalsIgnoreCase("anyType{}")) {
                            File_Name = root.getProperty("File_Name").toString();

                        } else {
                            File_Name = "";
                        }
                    } else {
                        File_Name = "";
                    }
                    if (root.getProperty("File_Path") != null) {

                        if (!root.getProperty("File_Path").toString().equalsIgnoreCase("anyType{}")) {
                            File_Path = root.getProperty("File_Path").toString();

                        } else {
                            File_Path = "";
                        }
                    } else {
                        File_Path = "";
                    }
                    if (root.getProperty("File_Path_File_Name") != null) {

                        if (!root.getProperty("File_Path_File_Name").toString().equalsIgnoreCase("anyType{}")) {
                            File_Path_File_Name = root.getProperty("File_Path_File_Name").toString();

                        } else {
                            File_Path_File_Name = "";
                        }
                    } else {
                        File_Path_File_Name = "";
                    }
                    if (root.getProperty("Id") != null) {

                        if (!root.getProperty("Id").toString().equalsIgnoreCase("anyType{}")) {
                            Id = root.getProperty("Id").toString();

                        } else {
                            Id = "";
                        }
                    } else {
                        Id = "";
                    }

                    if (root.getProperty("Individuals_Id") != null) {

                        if (!root.getProperty("Individuals_Id").toString().equalsIgnoreCase("anyType{}")) {
                            Individuals_Id = root.getProperty("Individuals_Id").toString();

                        } else {
                            Individuals_Id = "";
                        }
                    } else {
                        Individuals_Id = "";
                    }
                    try {
                        Cursor cursorindividual = null;
                        String where = " where legal_id = '" + Individuals_Id + "'";
                        cursorindividual = KHIL.dbCon.fetchFromSelect(DbHelper.M_Legal_Entity, where);
                        if (cursorindividual != null && cursorindividual.getCount() > 0) {
                            cursorindividual.moveToFirst();
                            do {
                                Individuals_Id = cursorindividual.getString(cursorindividual.getColumnIndex("legal_id"));
                                Individuals_text = cursorindividual.getString(cursorindividual.getColumnIndex("text"));
                                Individuals_value = cursorindividual.getString(cursorindividual.getColumnIndex("value"));
                            } while (cursorindividual.moveToNext());
                            cursorindividual.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Is_Download") != null) {

                        if (!root.getProperty("Is_Download").toString().equalsIgnoreCase("anyType{}")) {
                            Is_Download = root.getProperty("Is_Download").toString();

                        } else {
                            Is_Download = "";
                        }
                    } else {
                        Is_Download = "";
                    }
                    if (root.getProperty("Is_Edit") != null) {

                        if (!root.getProperty("Is_Edit").toString().equalsIgnoreCase("anyType{}")) {
                            Is_Edit = root.getProperty("Is_Edit").toString();

                        } else {
                            Is_Edit = "";
                        }
                    } else {
                        Is_Edit = "";
                    }
                    if (root.getProperty("Is_View") != null) {

                        if (!root.getProperty("Is_View").toString().equalsIgnoreCase("anyType{}")) {
                            Is_View = root.getProperty("Is_View").toString();

                        } else {
                            Is_View = "";
                        }
                    } else {
                        Is_View = "";
                    }
                    if (root.getProperty("Legal_Entity_Id") != null) {

                        if (!root.getProperty("Legal_Entity_Id").toString().equalsIgnoreCase("anyType{}")) {
                            Legal_Entity_Id = root.getProperty("Legal_Entity_Id").toString();

                        } else {
                            Legal_Entity_Id = "";
                        }
                    } else {
                        Legal_Entity_Id = "";
                    }
                    try {
                        Cursor cursorlegal = null;
                        String where = " where legal_id = '" + Legal_Entity_Id + "'";
                        cursorlegal = KHIL.dbCon.fetchFromSelect(DbHelper.M_Legal_Entity, where);
                        if (cursorlegal != null && cursorlegal.getCount() > 0) {
                            cursorlegal.moveToFirst();
                            do {
                                Legal_Entity_Id = cursorlegal.getString(cursorlegal.getColumnIndex("legal_id"));
                                Legal_Entity_text = cursorlegal.getString(cursorlegal.getColumnIndex("text"));
                                Legal_Entity_value = cursorlegal.getString(cursorlegal.getColumnIndex("value"));
                            } while (cursorlegal.moveToNext());
                            cursorlegal.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Level2_Id") != null) {

                        if (!root.getProperty("Level2_Id").toString().equalsIgnoreCase("anyType{}")&&
                                !root.getProperty("Level2_Id").toString().equalsIgnoreCase("0")) {
                            Level2_Id = root.getProperty("Level2_Id").toString();

                        } else {
                            Level2_Id = "";
                        }
                    } else {
                        Level2_Id = "";
                    }
                    try {
                        Cursor cursorlevel2 = null;
                        String where = " where aID = '" + Level2_Id + "'";
                        cursorlevel2 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data, where);
                        if (cursorlevel2 != null && cursorlevel2.getCount() > 0) {
                            cursorlevel2.moveToFirst();
                            do {
                                Level2_Id = cursorlevel2.getString(cursorlevel2.getColumnIndex("id"));
                                Level2_text = cursorlevel2.getString(cursorlevel2.getColumnIndex("text"));
                                Level2_value = cursorlevel2.getString(cursorlevel2.getColumnIndex("value"));
                            } while (cursorlevel2.moveToNext());
                            cursorlevel2.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Level3_Id") != null) {

                        if (!root.getProperty("Level3_Id").toString().equalsIgnoreCase("anyType{}")&&
                                !root.getProperty("Level3_Id").toString().equalsIgnoreCase("0")) {
                            Level3_Id = root.getProperty("Level3_Id").toString();

                        } else {
                            Level3_Id = "";
                        }
                    } else {
                        Level3_Id = "";
                    }
                    try {
                        Cursor cursorlevel3 = null;
                        String where = " where aID = '" + Level3_Id + "'";
                        cursorlevel3 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data, where);
                        if (cursorlevel3 != null && cursorlevel3.getCount() > 0) {
                            cursorlevel3.moveToFirst();
                            do {
                                Level3_Id = cursorlevel3.getString(cursorlevel3.getColumnIndex("id"));
                                Level3_text = cursorlevel3.getString(cursorlevel3.getColumnIndex("text"));
                                Level3_value = cursorlevel3.getString(cursorlevel3.getColumnIndex("value"));
                            } while (cursorlevel3.moveToNext());
                            cursorlevel3.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Level4_Id") != null) {

                        if (!root.getProperty("Level4_Id").toString().equalsIgnoreCase("anyType{}")&&
                                !root.getProperty("Level4_Id").toString().equalsIgnoreCase("0")) {
                            Level4_Id = root.getProperty("Level4_Id").toString();

                        } else {
                            Level4_Id = "";
                        }
                    } else {
                        Level4_Id = "";
                    }
                    try {
                        Cursor cursorlevel4 = null;
                        String where = " where aID= '" + Level4_Id + "'";
                        cursorlevel4 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data, where);
                        if (cursorlevel4 != null && cursorlevel4.getCount() > 0) {
                            cursorlevel4.moveToFirst();
                            do {
                                Level4_Id = cursorlevel4.getString(cursorlevel4.getColumnIndex("id"));
                                Level4_text = cursorlevel4.getString(cursorlevel4.getColumnIndex("text"));
                                Level4_value = cursorlevel4.getString(cursorlevel4.getColumnIndex("value"));
                            } while (cursorlevel4.moveToNext());
                            cursorlevel4.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Level5_Id") != null) {

                        if (!root.getProperty("Level5_Id").toString().equalsIgnoreCase("anyType{}")&&
                                !root.getProperty("Level5_Id").toString().equalsIgnoreCase("0")) {
                            Level5_Id = root.getProperty("Level5_Id").toString();

                        } else {
                            Level5_Id = "";
                        }
                    } else {
                        Level5_Id = "";
                    }
                    try {
                        Cursor cursorlevel5 = null;
                        String where = " where aID = '" + Level5_Id + "'";
                        cursorlevel5 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data, where);
                        if (cursorlevel5 != null && cursorlevel5.getCount() > 0) {
                            cursorlevel5.moveToFirst();
                            do {
                                Level5_Id = cursorlevel5.getString(cursorlevel5.getColumnIndex("id"));
                                Level5_text = cursorlevel5.getString(cursorlevel5.getColumnIndex("text"));
                                Level5_value = cursorlevel5.getString(cursorlevel5.getColumnIndex("value"));
                            } while (cursorlevel5.moveToNext());
                            cursorlevel5.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Level6_Id") != null) {

                        if (!root.getProperty("Level6_Id").toString().equalsIgnoreCase("anyType{}")&&
                                !root.getProperty("Level6_Id").toString().equalsIgnoreCase("0")) {
                            Level6_Id = root.getProperty("Level6_Id").toString();

                        } else {
                            Level6_Id = "";
                        }
                    } else {
                        Level6_Id = "";
                    }
                    try {
                        Cursor cursorlevel6 = null;
                        String where = " where aID= '" + Level6_Id + "'";
                        cursorlevel6 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data, where);
                        if (cursorlevel6 != null && cursorlevel6.getCount() > 0) {
                            cursorlevel6.moveToFirst();
                            do {
                                Level6_Id = cursorlevel6.getString(cursorlevel6.getColumnIndex("id"));
                                Level6_text = cursorlevel6.getString(cursorlevel6.getColumnIndex("text"));
                                Level6_value = cursorlevel6.getString(cursorlevel6.getColumnIndex("value"));
                            } while (cursorlevel6.moveToNext());
                            cursorlevel6.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Level7_Id") != null) {

                        if (!root.getProperty("Level7_Id").toString().equalsIgnoreCase("anyType{}")&&
                                !root.getProperty("Level7_Id").toString().equalsIgnoreCase("0")) {
                            Level7_Id = root.getProperty("Level7_Id").toString();

                        } else {
                            Level7_Id = "";
                        }
                    } else {
                        Level7_Id = "";
                    }
                    try {
                        Cursor cursorlevel7 = null;
                        String where = " where aID = '" + Level7_Id + "'";
                        cursorlevel7 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data, where);
                        if (cursorlevel7 != null && cursorlevel7.getCount() > 0) {
                            cursorlevel7.moveToFirst();
                            do {
                                Level7_Id = cursorlevel7.getString(cursorlevel7.getColumnIndex("id"));
                                Level7_text = cursorlevel7.getString(cursorlevel7.getColumnIndex("text"));
                                Level7_value = cursorlevel7.getString(cursorlevel7.getColumnIndex("value"));
                            } while (cursorlevel7.moveToNext());
                            cursorlevel7.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Location_Id") != null) {

                        if (!root.getProperty("Location_Id").toString().equalsIgnoreCase("anyType{}")) {
                            Location_Id = root.getProperty("Location_Id").toString();

                        } else {
                            Location_Id = "";
                        }
                    } else {
                        Location_Id = "";
                    }
                    try {
                        Cursor cursorloc = null;
                        String where = " where id = '" + Location_Id + "'";
                        cursorloc = KHIL.dbCon.fetchFromSelect(DbHelper.M_Location, where);
                        if (cursorloc != null && cursorloc.getCount() > 0) {
                            cursorloc.moveToFirst();
                            do {
                                Location_Id = cursorloc.getString(cursorloc.getColumnIndex("id"));
                                Location_text = cursorloc.getString(cursorloc.getColumnIndex("text"));
                                Location_value = cursorloc.getString(cursorloc.getColumnIndex("value"));
                            } while (cursorloc.moveToNext());
                            cursorloc.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Month") != null) {

                        if (!root.getProperty("Month").toString().equalsIgnoreCase("anyType{}")) {
                            Month = root.getProperty("Month").toString();

                        } else {
                            Month = "";
                        }
                    } else {
                        Month = "";
                    }
                    try {
                        Cursor cursorMonth = null;
                        String where = " where id = '" + Month + "'";
                        cursorMonth = KHIL.dbCon.fetchFromSelect(DbHelper.M_Month, where);
                        if (cursorMonth != null && cursorMonth.getCount() > 0) {
                            cursorMonth.moveToFirst();
                            do {
                                Month = cursorMonth.getString(cursorMonth.getColumnIndex("id"));
                                monthtext = cursorMonth.getString(cursorMonth.getColumnIndex("text"));
                                monthvalue = cursorMonth.getString(cursorMonth.getColumnIndex("value"));
                            } while (cursorMonth.moveToNext());
                            cursorMonth.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (root.getProperty("NewProposal") != null) {

                        if (!root.getProperty("NewProposal").toString().equalsIgnoreCase("anyType{}")) {
                            NewProposal = root.getProperty("NewProposal").toString();

                        } else {
                            NewProposal = "";
                        }
                    } else {
                        NewProposal = "";
                    }
                    if (root.getProperty("Property_Id") != null) {

                        if (!root.getProperty("Property_Id").toString().equalsIgnoreCase("anyType{}")) {
                            Property_Id = root.getProperty("Property_Id").toString();

                        } else {
                            Property_Id = "";
                        }
                    } else {
                        Property_Id = "";
                    }
                    try {
                        Cursor cursorpro = null;
                        String where = " where id = '" + Property_Id + "'";
                        cursorpro = KHIL.dbCon.fetchFromSelect(DbHelper.M_Property, where);
                        if (cursorpro != null && cursorpro.getCount() > 0) {
                            cursorpro.moveToFirst();
                            do {
                                Property_Id = cursorpro.getString(cursorpro.getColumnIndex("id"));
                                Property_text = cursorpro.getString(cursorpro.getColumnIndex("text"));
                                Property_value = cursorpro.getString(cursorpro.getColumnIndex("value"));
                            } while (cursorpro.moveToNext());
                            cursorpro.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("Quarter") != null) {

                        if (!root.getProperty("Quarter").toString().equalsIgnoreCase("anyType{}")) {
                            Quarter = root.getProperty("Quarter").toString();

                        } else {
                            Quarter = "";
                        }
                    } else {
                        Quarter = "";
                    }
                    try {
                        Cursor cursorQuter = null;
                        String where = " where id = '" + Quarter + "'";
                        cursorQuter = KHIL.dbCon.fetchFromSelect(DbHelper.M_Quater, where);
                        if (cursorQuter != null && cursorQuter.getCount() > 0) {
                            cursorQuter.moveToFirst();
                            do {
                                Quarter = cursorQuter.getString(cursorQuter.getColumnIndex("id"));
                                quartertext = cursorQuter.getString(cursorQuter.getColumnIndex("text"));
                                quartervalue = cursorQuter.getString(cursorQuter.getColumnIndex("value"));
                            } while (cursorQuter.moveToNext());
                            cursorQuter.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (root.getProperty("Role_Id") != null) {

                        if (!root.getProperty("Role_Id").toString().equalsIgnoreCase("anyType{}")) {
                            Role_Id = root.getProperty("Role_Id").toString();

                        } else {
                            Role_Id = "";
                        }
                    } else {
                        Role_Id = "";
                    }
                    if (root.getProperty("Status") != null) {

                        if (!root.getProperty("Status").toString().equalsIgnoreCase("anyType{}")) {
                            Status = root.getProperty("Status").toString();

                        } else {
                            Status = "";
                        }
                    } else {
                        Status = "";
                    }
                    if (root.getProperty("Year") != null) {

                        if (!root.getProperty("Year").toString().equalsIgnoreCase("anyType{}")) {
                            Year = root.getProperty("Year").toString();

                        } else {
                            Year = "";
                        }
                    } else {
                        Year = "";
                    }
                    try {
                        Cursor cursoryear = null;
                        String where = " where value = '" + Year + "'";
                        cursoryear = KHIL.dbCon.fetchFromSelect(DbHelper.M_Year, where);
                        if (cursoryear != null && cursoryear.getCount() > 0) {
                            cursoryear.moveToFirst();
                            do {
                                Year = cursoryear.getString(cursoryear.getColumnIndex("id"));
                                yeartext = cursoryear.getString(cursoryear.getColumnIndex("text"));
                                yearvalue = cursoryear.getString(cursoryear.getColumnIndex("value"));
                            } while (cursoryear.moveToNext());
                            cursoryear.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (root.getProperty("type") != null) {

                        if (!root.getProperty("type").toString().equalsIgnoreCase("anyType{}")) {
                            type = root.getProperty("type").toString();

                        } else {
                            type = "";
                        }
                    } else {
                        type = "";
                    }

                    String strLastSync = "0";
                    String selection = "id = ?";
                    id = id+1;
                    // WHERE clause arguments
                    String[] selectionArgs = {id+""};

                    String valuesArray[] = {id+"",Created_By,Created_Date,Document_No,File_Exten,File_Name,File_Path,File_Path_File_Name,
                            Id,Is_Download,Is_Edit,Is_View,Legal_Entity_Id,Level2_Id,Level3_Id,Level4_Id,Level5_Id,Level6_Id,Level7_Id,
                            Location_Id,Month,Property_Id,Quarter,Role_Id,Status,Year,type,strLastSync,yearvalue,
                            quartervalue,monthvalue,Legal_Entity_value,Level2_value,
                            Level3_value,Level4_value,Level5_value,Level6_value,Level7_value,Location_value,Property_value,
                            yeartext,quartertext,monthtext,Legal_Entity_text,Level2_text,Level3_text,Level4_text,Level5_text,
                            Level6_text,Level7_text,Location_text,Property_text,Individuals_Id,Individuals_text,Individuals_value,NewProposal};
                    boolean output = KHIL.dbCon.updateBulk(DbHelper.QC1_DATA, selection, valuesArray, utils.columnNames_QC1_Data, selectionArgs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                progress.dismiss();
                Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);

            }
        }
    }

    public class ForgotPassword extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            SoapObject object2 = null;
            try {
                object2 = ws.getEmailID(username);

            }catch(Exception e){
                e.printStackTrace();
            }
            return object2;
        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
            try {

                String response = String.valueOf(soapObject);
                System.out.println("Response =>: " + response);

                for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                    SoapObject root = (SoapObject) soapObject.getProperty(i);


                    if (root.getProperty("email_id") != null) {

                        if (!root.getProperty("email_id").toString().equalsIgnoreCase("anyType{}")) {
                            email_id = root.getProperty("email_id").toString();

                        } else {
                            email_id = "";
                        }
                    } else {
                        email_id = "";
                    }


                    if (root.getProperty("id") != null) {

                        if (!root.getProperty("id").toString().equalsIgnoreCase("anyType{}")) {
                            id = root.getProperty("id").toString();

                        } else {
                            id = "";
                        }
                    } else {
                        id = "";
                    }
                    if (root.getProperty("message") != null) {

                        if (!root.getProperty("message").toString().equalsIgnoreCase("anyType{}")) {
                            message = root.getProperty("message").toString();

                        } else {
                            message = "";
                        }
                    } else {
                        message = "";
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

           if(message.equalsIgnoreCase("Mail Sent")){

               new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                       .setTitleText(sharedPref.getLoginId())
                       .setContentText("Email will be sent to register Email-Id to Reset Password")
                       .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sDialog) {

                               sDialog.dismissWithAnimation();
                           }
                       })
                       .show();
           }
        }
    }
}

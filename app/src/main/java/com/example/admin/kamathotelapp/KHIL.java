package com.example.admin.kamathotelapp;

import android.app.Application;

import com.example.admin.kamathotelapp.dbConfig.DataBaseCon;


public class KHIL extends Application {

    public static DataBaseCon dbCon = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            if (dbCon != null) {
                dbCon.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

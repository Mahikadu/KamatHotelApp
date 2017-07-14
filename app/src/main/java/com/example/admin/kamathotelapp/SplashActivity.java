package com.example.admin.kamathotelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.Utils.Utils;



public class SplashActivity extends AbstractActivity implements Runnable {

    private Handler threadRunnable;
    private int time = 2000;
    private Context mContext;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        try {
            mContext = SplashActivity.this;

            utils = new Utils(mContext);
            threadRunnable = new Handler();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        SharedPref sharedPref = new SharedPref(mContext);
        String result = sharedPref.getLoginResult();
        if (result.equalsIgnoreCase("FALSE")) {
            pushActivity(SplashActivity.this, MainActivity.class, null, true);
        } else {
            sharedPref.setKeyNodata(true);
            pushActivity(SplashActivity.this, NavigationDrawerActivity.class, null, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (threadRunnable != null) {
                threadRunnable.postDelayed(this, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            if (threadRunnable != null) {
                threadRunnable.removeCallbacks(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

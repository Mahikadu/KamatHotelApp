package com.example.admin.kamathotelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.admin.kamathotelapp.Fragments.ChangePassFragement;
import com.example.admin.kamathotelapp.Fragments.DashboardFragment;
import com.example.admin.kamathotelapp.Fragments.QC1;
import com.example.admin.kamathotelapp.Fragments.ScanFragment;
import com.example.admin.kamathotelapp.Fragments.UploadFragment;
import com.example.admin.kamathotelapp.Utils.ExceptionHandler;
import com.example.admin.kamathotelapp.Utils.SharedPref;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICKFILE_RESULT_CODE = 1;
    SharedPref sharedPref;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPref = new SharedPref(this);
        logout = (ImageView) findViewById(R.id.imgLogout);
        //////////Crash Report
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            selectFragmentAsDefault();
        }

        String userloged = sharedPref.getLoginId();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(userloged.equalsIgnoreCase("QC1finance") || userloged.equalsIgnoreCase("csQC1") ||
                userloged.equalsIgnoreCase("cmdQC1") || userloged.equalsIgnoreCase("hrQC1") ||
                userloged.equalsIgnoreCase("legalQC1") || userloged.equalsIgnoreCase("marketingQC1") ||
                userloged.equalsIgnoreCase("personalQC1")){

            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.qc1).setVisible(true);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(NavigationDrawerActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure want to logout?")
                       /* .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")*/
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                sDialog.setTitleText("Logout Sucessfully!")
                                       /* .setContentText("Your imaginary file has been deleted!")*/
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                sharedPref.clearPref();
                Intent intent = new Intent(NavigationDrawerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_content);
        if (currentFragment instanceof DashboardFragment) {//the fragment on which you want to handle your back press
            Log.i("BACK PRESSED", "BACK PRESSED");
        }else if (currentFragment instanceof UploadFragment) {
            Log.i("BACK PRESSED", "BACK PRESSED");
        }else if (currentFragment instanceof ScanFragment) {
            Log.i("BACK PRESSED", "BACK PRESSED");
        }else if (currentFragment instanceof QC1) {
            Log.i("BACK PRESSED", "BACK PRESSED");
        }else if (currentFragment instanceof ChangePassFragement) {
            Log.i("BACK PRESSED", "BACK PRESSED");
        }else{
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            getSupportActionBar().setTitle("Dashboard");
            DashboardFragment fragment = new DashboardFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_upload) {
            getSupportActionBar().setTitle("Upload");
            UploadFragment fragment = new UploadFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_scan) {
            getSupportActionBar().setTitle("Document Scan");
            ScanFragment fragment = new ScanFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.qc1) {
            getSupportActionBar().setTitle("QC1");
            QC1 fragment = new QC1();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,fragment);
            fragmentTransaction.commit();
        }else if (id == R.id.changepass) {
            getSupportActionBar().setTitle("Change Password");
            ChangePassFragement fragment = new ChangePassFragement();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,fragment);
            fragmentTransaction.commit();
        } else if(id == R.id.logout2) {
            sharedPref.clearPref();
            Intent intent = new Intent(NavigationDrawerActivity.this, MainActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectFragmentAsDefault() {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        getSupportActionBar().setTitle("Dashboard");
        tx.replace(R.id.frame_content, new DashboardFragment());
        tx.commit();
    }
}

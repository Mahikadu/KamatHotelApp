package com.example.admin.kamathotelapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.kamathotelapp.MainActivity;
import com.example.admin.kamathotelapp.NavigationDrawerActivity;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.Utils.Utils;
import com.example.admin.kamathotelapp.libs.SOAPWebservice;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 05-10-2017.
 */

public class ChangePassFragement extends Fragment {

    @InjectView(R.id.etpass)
    EditText pass;
    @InjectView(R.id.etconfirmpass)
    EditText confirmpass;
    @InjectView(R.id.btnsubmit)
    Button submit;
    @InjectView(R.id.errormsg)
    TextView errormsg;
    SOAPWebservice ws;

    private Utils utils;
    private SharedPref sharedPref;
    String Pass,confirmPass;
    View focusView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().setTitle("Change Password");

        //////////Crash Report
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_changepass, container, false);
        ButterKnife.inject(this, view);


        sharedPref = new SharedPref(getActivity());
        ws = new SOAPWebservice(getActivity());
        utils = new Utils(getActivity());

        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pass.setFocusableInTouchMode(true);
                pass.setCursorVisible(true);
                errormsg.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        confirmpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                confirmpass.setFocusableInTouchMode(true);
                confirmpass.setCursorVisible(true);
                errormsg.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pass = pass.getText().toString();
                confirmPass = confirmpass.getText().toString();

                if(!Pass.contains(confirmPass)){
                    errormsg.setVisibility(View.VISIBLE);
                }else {
                    ChangePassword changePassword = new ChangePassword();
                    changePassword.execute();
                }
            }
        });

        return view;
    }

    public class ChangePassword extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            SoapObject object2 = null;
            try {
                object2 = ws.changepassword(sharedPref.getRoleID(),confirmPass,sharedPref.getLoginId());

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

               if(!response.equalsIgnoreCase("Not Updated")){
                   pass.setText("");
                   confirmpass.setText("");
                   new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                           .setTitleText(sharedPref.getLoginId())
                           .setContentText("Password Changed Sucessfully.")
                           .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                               @Override
                               public void onClick(SweetAlertDialog sDialog) {
                                   Intent intent = new Intent(getContext(),NavigationDrawerActivity.class);
                                   startActivity(intent);

                                   sDialog.dismissWithAnimation();
                               }
                           })
                           .show();
               }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

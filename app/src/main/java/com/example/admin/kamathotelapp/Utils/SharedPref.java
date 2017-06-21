package com.example.admin.kamathotelapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 6/19/2017.
 */
public class SharedPref {

    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String KEY_LoginId = "key_loginid";
    private static final String KEY_PWD = "key_password";

    public SharedPref(Context _ctx) {
        context = _ctx;
        sharedPref = _ctx.getSharedPreferences("SHARED_PREF", context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setLoginInfo(String loginId, String password) {
        editor.putString(KEY_LoginId, loginId);
        editor.putString(KEY_PWD, password);
        editor.commit();
    }

    public String getLoginId() {
        String loginId = sharedPref.getString(KEY_LoginId, "");
        return loginId;
    }

    public String getPassword() {
        String pass = sharedPref.getString(KEY_PWD, "");
        return pass;
    }

    public void clearPref() {
        try {
            editor.remove(KEY_LoginId);
            editor.clear();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
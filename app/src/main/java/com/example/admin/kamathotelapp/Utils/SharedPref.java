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
    private static final String KEY_RoleId = "key_roleId";
    private static final String KEY_Result = "key_result";
    private static final String KEY_NODATA = "key_nodata";

    public SharedPref(Context _ctx) {
        context = _ctx;
        sharedPref = _ctx.getSharedPreferences("SHARED_PREF", context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setLoginInfo(String loginId, String password,String roleID) {
        editor.putString(KEY_LoginId, loginId);
        editor.putString(KEY_PWD, password);
        editor.putString(KEY_RoleId, roleID);
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

    public String getRoleID() {
        String roleId = sharedPref.getString(KEY_RoleId, "");
        return roleId;
    }
    public String getLoginResult() {
        return sharedPref.getString(KEY_Result, "false");
    }

    public void setKeyNodata(boolean isData) {
        editor.putBoolean(KEY_NODATA, isData);
        editor.commit();
    }

    public void clearPref() {
        try {
            editor.remove(KEY_LoginId);
            editor.remove(KEY_PWD);
            editor.remove(KEY_RoleId);
            editor.clear();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.example.admin.kamathotelapp.Utils;

import android.content.Context;

import java.util.Arrays;

/**
 * Created by Admin on 7/6/2017.
 */
public class Utils {
    public String[] columnNamesUpload = new String[50];
    private Context mContext;
    public Utils(Context mContext) {
        this.mContext = mContext;
        String[] uploadArray = {"id","user_id", "legal_entity", "property", "col_year", "quarter", "col_month", "location", "fileName",
                "level2", "level3", "level4", "level5", "level6", "level7"};
        columnNamesUpload = Arrays.copyOf(uploadArray, uploadArray.length);
    }
}

package com.example.admin.kamathotelapp.Utils;

import android.content.Context;

import java.util.Arrays;

/**
 * Created by Admin on 7/6/2017.
 */
public class Utils {
    public String[] columnNamesUpload = new String[50];
    public String[] columnNamesM_Legal_Entity = new String[50];
    public String[] columnNamesM_Property = new String[50];
    public String[] columnNamesM_Location = new String[50];
    public String[] columnNamesM_Year = new String[50];
    public String[] columnNamesM_Quater = new String[50];
    public String[] columnNamesM_Month = new String[50];
    public String[] columnNamesM_Level_Data = new String[50];
    public String[] columnNames_Dashboard_Data = new String[50];

    private Context mContext;

    public Utils(Context mContext) {
        this.mContext = mContext;
        String[] uploadArray = {"id","user_id", "legal_entity","individuals","new_proposal", "property", "col_year", "quarter", "col_month", "location", "fileName",
                "level2", "level3", "level4", "level5", "level6", "level7","level2Id", "level3Id", "level4Id", "level5Id", "level6Id", "level7Id","role_Id",
                "legalEntityID","propertyID","individualID","locationID","doc_no","file","file_path","file_extension","trans_id","updated_date","last_sync"};
        columnNamesUpload = Arrays.copyOf(uploadArray, uploadArray.length);


        String[] master1Array = {"id","value", "text", "parent_Ref", "updated_date"};
        columnNamesM_Legal_Entity = Arrays.copyOf(master1Array, master1Array.length);

        String[] master2Array = {"id","value", "text", "parent_Ref","legal_Entity_Id","updated_date"};
        columnNamesM_Property = Arrays.copyOf(master2Array, master2Array.length);

        String[] master3Array = {"id","value", "text","property_id","updated_date"};
        columnNamesM_Location = Arrays.copyOf(master3Array, master3Array.length);

        String[] master4Array = {"id","value", "text","updated_date"};
        columnNamesM_Year = Arrays.copyOf(master4Array, master4Array.length);

        String[] master5Array = {"id","value", "text","updated_date"};
        columnNamesM_Quater = Arrays.copyOf(master5Array, master5Array.length);

        String[] master6Array = {"id","value", "text", "quater_id", "updated_date"};
        columnNamesM_Month = Arrays.copyOf(master6Array, master6Array.length);

        String[] master7Array = {"id","value","text","parent_Ref","aID","role_ID","data_level","quater_Id","updated_date"};
        columnNamesM_Level_Data = Arrays.copyOf(master7Array, master7Array.length);

        String[] master8Array = {"id","inv_count", "status"};
        columnNames_Dashboard_Data = Arrays.copyOf(master8Array, master8Array.length);
    }
}

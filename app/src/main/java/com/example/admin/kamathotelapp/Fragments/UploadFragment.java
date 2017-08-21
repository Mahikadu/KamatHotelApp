package com.example.admin.kamathotelapp.Fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kamathotelapp.Adapters.UploadAdapter;
import com.example.admin.kamathotelapp.KHIL;
import com.example.admin.kamathotelapp.Model.LegalEntityModel;
import com.example.admin.kamathotelapp.Model.LeveldataModel;
import com.example.admin.kamathotelapp.Model.LocationModel;
import com.example.admin.kamathotelapp.Model.MonthModel;
import com.example.admin.kamathotelapp.Model.PropertyModel;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.ExceptionHandler;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.Utils.Utils;
import com.example.admin.kamathotelapp.dbConfig.DataBaseCon;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;
import com.example.admin.kamathotelapp.libs.SOAPWebservice;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UploadFragment extends Fragment {

    //private TextView autoLegalEntity, autoLocation;
    //private Spinner spinLegalEntity, spinProperty, spinMonth, spinYear, spinQuarter, spinLocation;
    private Spinner spinLevel3, spinLevel4, spinLevel5, spinLevel6;
    ArrayAdapter<String> dataAdapter;
    private SharedPref sharedPref;
    List<String> listL2, listL3, listL4, listL5, listL6;
    public static Button btnUpload;
    @InjectView(R.id.btnFile)
    Button  btnAttach;
    @InjectView(R.id.btnUpdate)
    Button btnUpdate;
    @InjectView(R.id.btnCancel)
    Button btnCancel;
    @InjectView(R.id.btnSubmit)
    Button btnSubmit;
    public static ListView listUpload;
    private String loginId, password,roleID;
    List<UploadModel> uploadModelList;
    UploadModel uploadModel;
    private UploadAdapter adapter;
    private String valLevel2="", valLevel3="", valLevel4="", valLevel5="", valLevel6="", valLevel7="";
    @InjectView(R.id.spinLegalEntity)
    AutoCompleteTextView autoLegalEntity;
    @InjectView(R.id.spinIndividuals)
    AutoCompleteTextView autoIndividuals;
    @InjectView(R.id.etNewProposal)
    EditText etNewProposal;
    @InjectView(R.id.spinProperty)
    AutoCompleteTextView autoProperty;
    @InjectView(R.id.spinMonth)
    AutoCompleteTextView autoMonth;
    @InjectView(R.id.spinYear)
    AutoCompleteTextView autoYear;
    @InjectView(R.id.spinQuarter)
    AutoCompleteTextView autoQuarter;
    @InjectView(R.id.spinLoc)
    AutoCompleteTextView autoLoc;
    public static AutoCompleteTextView txtL2,txtL3,txtL4,txtL5,txtL6,txtL7;
    public String txtL2Id,txtL3Id,txtL4Id,txtL5Id,txtL6Id,txtL7Id,monthID;
    public String legalEntityID,propertyID,individualID,locationID;
    public String legalEntityValue,propertyValue,individualValue,locationValue;
    String[] strLegalArray = null;
    String[] strPropertyArray = null;
    String[] strMonthArray = null;
    String[] strYearArray = null;
    String[] strQuarterArray = null;
    String[] strLocArray = null;
    String[] strLevel2Array = null;
    String[] strLevel3Array = null;
    String[] strLevel4Array = null;
    String[] strLevel5Array = null;
    String[] strLevel6Array = null;
    String[] strLevel4ArrayHR = null;
    String[] strLevel5ArrayHR = null;
    String[] strLevel6ArrayHR = null;
    String[] strLevel4ArrayCMD = null;
    String[] strLevel5ArrayCMD = null;
    String[] strLevel6ArrayCMD = null;
    String[] strLevel7ArrayCMD = null;
    String[] strLevel2ArrayCS = null;
    String[] strLevel3ArrayCS = null;
    String[] strLevel4ArrayCS = null;
    String[] strLevel4ArrayMAR = null;
    String[] strLevel5ArrayMAR = null;
    String[] strLevel6ArrayMAR = null;
    String[] strLevel7ArrayMAR = null;
    String[] strLevel3ArrayPer = null;
    String[] strLevel4ArrayPer = null;
    String[] strLevel5ArrayPer = null;
    String[] strLevel4ArrayLEGAL = null;
    String[] strLevel5ArrayLEGAL = null;
    String[] strLevel6ArrayLEGAL = null;
    String[] strLevel7ArrayLEGAL = null;
    private String strLegalEntity,strProperty,strMonth,strYear,strQuarter,strLoc;
    private String legalEntityString,propertyString,monthString,yearString,quarterString="",locString;
    private List<String> level2list,level3list,level4list,level5list,level6list;
    private List<String> level4listHR, level5listHR, level6listHR;
    private List<String> level4listCMD, level5listCMD, level6listCMD, level7listCMD;
    private List<String> level2listCS,level3listCS,level4listCS;
    private List<String> level4listMAR, level5listMAR, level6listMAR, level7listMAR;
    private List<String> level3listPER, level4listPER, level5listPER;
    private List<String> level4listLEGAL, level5listLEGAL, level6listLEGAL, level7listLEGAL;
    String chk = "";
    String manufactures = android.os.Build.MANUFACTURER;
    String filePath;
    public static CardView cardlevel2, cardlevel3,cardlevel4,cardlevel5,cardlevel6,cardlevel7,cardNewProposal,cardIndividuals,cardProperty;
    public static TextInputLayout level2txtlayout, level3txtlayout,level4txtlayout,level5txtlayout,level6txtlayout,level7txtlayout;
    public TextView headLev2, headLev3, headLev4, headLev5, headLev6, headLev7, viewFile;
    public static TextView txtNoFile;
    public static TextView no_files;
    private static final int PICKFILE_RESULT_CODE = 1;
    public String fileName="";
    private Utils utils;
    private String legalEntity,individuals = "",newProposal = "",property, year, quarter, month, location, file="", level2="", level3="", level4="", level5="", level6="", level7="";
    public static LinearLayout layout_edit;
    public static int ID = 0;
    private ArrayList<String> listIndividuals,listYear,listQuarter;
    LegalEntityModel legalEntityModel;
    private ArrayList<LegalEntityModel> listLegal,listLegalAll;
    private ArrayList<LeveldataModel> listLevelData;
    LeveldataModel leveldataModel;
    private ArrayList<PropertyModel> listProperty;
    PropertyModel propertyModel;
    private ArrayList<LocationModel> listLoc;
    LocationModel locationModel;
    private ArrayList<MonthModel> listMonth;
    MonthModel monthModel;
    private SimpleDateFormat dateFormatter;
    private String date;
    public String fileExtension = "";
    private ArrayList<UploadModel> submitDatalist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().setTitle("Upload");
        //////////Crash Report
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        ButterKnife.inject(this, view);
        layout_edit = (LinearLayout) view.findViewById(R.id.layout_edit);
        btnUpload = (Button) view.findViewById(R.id.btnUpload);
        initView(view);
        return view;
    }

    void initView(View view) {
        sharedPref = new SharedPref(getContext());
        utils = new Utils(getActivity());
        loginId = sharedPref.getLoginId();
        password = sharedPref.getPassword();
        roleID = sharedPref.getRoleID();

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        date = dateFormatter.format(cal.getTime());
        //................Auto Complete Text View......
        txtL2 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel2);
        txtL3 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel3);
        txtL4 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel4);
        txtL5 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel5);
        txtL6 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel6);
        txtL7 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel7);
        txtNoFile = (TextView) view.findViewById(R.id.txtNofile);
        no_files = (TextView) view.findViewById(R.id.no_files);

        cardNewProposal = (CardView) view.findViewById(R.id.newProposalcardview);
        cardIndividuals = (CardView) view.findViewById(R.id.individualscardview);
        cardProperty = (CardView) view.findViewById(R.id.propertycardview);
        cardlevel2 = (CardView) view.findViewById(R.id.level2cardview);
        cardlevel3 = (CardView) view.findViewById(R.id.level3cardview);
        cardlevel4 = (CardView) view.findViewById(R.id.level4cardview);
        cardlevel5 = (CardView) view.findViewById(R.id.level5cardview);
        cardlevel6 = (CardView) view.findViewById(R.id.level6cardview);
        cardlevel7 = (CardView) view.findViewById(R.id.level7cardview);

        level2txtlayout = (TextInputLayout)view.findViewById(R.id.level2txtlayout); 
        level3txtlayout = (TextInputLayout)view.findViewById(R.id.level3txtlayout);
        level4txtlayout = (TextInputLayout)view.findViewById(R.id.level4txtlayout);
        level5txtlayout = (TextInputLayout)view.findViewById(R.id.level5txtlayout);
        level6txtlayout = (TextInputLayout)view.findViewById(R.id.level6txtlayout);
        level7txtlayout = (TextInputLayout)view.findViewById(R.id.level7txtlayout);

        headLev2 = (TextView) view.findViewById(R.id.level2);
        headLev3 = (TextView) view.findViewById(R.id.level3);
        headLev4 = (TextView) view.findViewById(R.id.level4);
        headLev5 = (TextView) view.findViewById(R.id.level5);
        headLev6 = (TextView) view.findViewById(R.id.level6);
        headLev7 = (TextView) view.findViewById(R.id.level7);
        viewFile = (TextView) view.findViewById(R.id.txtViewFile);

        listUpload = (ListView) view.findViewById(R.id.listViewUpload);


       // autoLegalEntity = (TextView) view.findViewById(R.id.autoLegalEntity);
        //autoLegalEntity.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Legal Entity</font>" + "<font color=\"red\">*</font>\n"));
       // spinLegalEntity = (Spinner) view.findViewById(R.id.spinLegalEntity);
        //autoProperty = (TextView) view.findViewById(R.id.autoProperty);
       // autoProperty.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Property</font>" + "<font color=\"red\">*</font>\n"));
       // spinProperty = (Spinner) view.findViewById(R.id.spinProperty);
       // autoMonth = (TextView) view.findViewById(R.id.autoMonth);
        //autoMonth.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Month</font>" + "<font color=\"red\">*</font>\n"));
        //spinMonth = (Spinner) view.findViewById(R.id.spinMonth);
        //autoYear = (TextView) view.findViewById(R.id.autoYear);
        //autoYear.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Year</font>" + "<font color=\"red\">*</font>\n"));
        //spinYear = (Spinner) view.findViewById(R.id.spinYear);
        //autoQuarter = (TextView) view.findViewById(R.id.autoQuarter);
        //autoQuarter.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Quarter</font>" + "<font color=\"red\">*</font>\n"));
        //spinQuarter = (Spinner) view.findViewById(R.id.spinQuarter);
        //autoLocation = (TextView) view.findViewById(R.id.autoLoc);
        //autoLocation.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Location</font>" + "<font color=\"red\">*</font>\n"));
        //spinLocation = (Spinner) view.findViewById(R.id.spinLoc);

       // txtL2 = (TextView) view.findViewById(R.id.txtLevel2);
        //spinLevel2 = (Spinner) view.findViewById(R.id.spinLevel2);
        //txtL3 = (TextView) view.findViewById(R.id.txtLevel3);
       // spinLevel3 =(Spinner) view.findViewById(R.id.spinLevel3);
       // txtL4 = (TextView) view.findViewById(R.id.txtLevel4);
        //spinLevel4 = (Spinner) view.findViewById(R.id.spinLevel4);
        //txtL5 = (TextView) view.findViewById(R.id.txtLevel5);
        //spinLevel5 = (Spinner) view.findViewById(R.id.spinLevel5);
        //txtL6 = (TextView) view.findViewById(R.id.txtLevel6);
        //spinLevel6 = (Spinner) view.findViewById(R.id.spinLevel6);
        uploadModelList = new ArrayList<>();
        uploadModel = new UploadModel();

             fetchLegalEntity();
             FetchLeveldata();
        /////////Details of legal Entity
        if (listLegal.size() > 0) {
            strLegalArray = new String[listLegal.size()];
            for (int i = 0; i < listLegal.size(); i++) {
                legalEntityModel = listLegal.get(i);
                String text = legalEntityModel.getText();
                strLegalArray[i] = text;
            }
        }
        if (listLegal != null && listLegal.size() > 0) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLegalArray) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = null;
                    // If this is the initial dummy entry, make it hidden
                    if (position == 0) {
                        TextView tv = new TextView(getContext());
                        tv.setHeight(0);
                        tv.setVisibility(View.GONE);
                        v = tv;
                    } else {
                        // Pass convertView as null to prevent reuse of special case views
                        v = super.getDropDownView(position, null, parent);
                    }
                    // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                    parent.setVerticalScrollBarEnabled(false);
                    return v;
                }
            };

            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autoLegalEntity.setAdapter(adapter1);
        }

        autoLegalEntity.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoLegalEntity.showDropDown();
                return false;
            }
        });

        autoLegalEntity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strLegalArray != null && strLegalArray.length > 0) {
                    String ID = "";
                    strLegalEntity = autoLegalEntity.getText().toString();
                    legalEntityString = parent.getItemAtPosition(position).toString();
                    for(int i=0; i<listLegal.size(); i++) {
                        legalEntityModel = listLegal.get(i);
                        String entity = legalEntityModel.getText();
                        if (entity.equalsIgnoreCase(legalEntityString)) {
                            ID = legalEntityModel.getId();
                            legalEntityID = ID;
                            legalEntityValue = legalEntityModel.getValue();
                        }
                    }
                    autoProperty.setText("");
                    autoIndividuals.setText("");
                    autoLoc.setText("");
//                    long positionId = id+1;
                    if(strLegalEntity.equalsIgnoreCase("Individuals")){

                        cardIndividuals.setVisibility(View.VISIBLE);
                        cardNewProposal.setVisibility(View.GONE);
                        cardProperty.setVisibility(View.VISIBLE);

                        listIndividuals = new ArrayList<>();
                        String individuals = "";
                        String where1 = " where parent_Ref = " + "'" + legalEntityID + "'";
                        String text = "text";
                        Cursor cursor = KHIL.dbCon.fetchFromSelectDistinctWhere(text,DbHelper.M_Legal_Entity, where1);
                        if (cursor != null && cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            do {
                                individuals = cursor.getString(cursor.getColumnIndex("text"));
                                listIndividuals.add(individuals);
                            } while (cursor.moveToNext());
                            cursor.close();
                        }


                        if (listIndividuals != null && listIndividuals.size() > 0) {

                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listIndividuals) {
                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    View v = null;
                                    // If this is the initial dummy entry, make it hidden
                                    if (position == 0) {
                                        TextView tv = new TextView(getContext());
                                        tv.setHeight(0);
                                        tv.setVisibility(View.GONE);
                                        v = tv;
                                    } else {
                                        // Pass convertView as null to prevent reuse of special case views
                                        v = super.getDropDownView(position, null, parent);
                                    }
                                    // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                                    parent.setVerticalScrollBarEnabled(false);
                                    return v;
                                }
                            };

                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            autoIndividuals.setAdapter(adapter1);
                        }

                        autoIndividuals.setOnTouchListener(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                autoIndividuals.showDropDown();
                                return false;
                            }
                        });
                        autoIndividuals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            String eid ="",parentref;
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String individual = parent.getItemAtPosition(position).toString();
                                autoProperty.setText("");
                                autoLoc.setText("");

                                for(int i=0; i<listLegalAll.size(); i++){
                                    legalEntityModel = listLegalAll.get(i);
                                    String text = legalEntityModel.getText();
                                    if (text.equalsIgnoreCase(individual)){
                                         eid = legalEntityModel.getId();
                                         parentref = legalEntityModel.getParent_Ref();

                                          individualID = eid;
                                        individualValue = legalEntityModel.getValue();

                                        listProperty = new ArrayList<>();
                                        String property = "";
                                        String where1 = " where legal_Entity_Id = " + "'" + eid + "'";
                                        String text1 = "text";
                                        Cursor cursor = KHIL.dbCon.fetchFromSelect(DbHelper.M_Property, where1);
                                        if (cursor != null && cursor.getCount() > 0) {
                                            cursor.moveToFirst();
                                            do {
                                                propertyModel = new PropertyModel();
                                                propertyModel.setId(cursor.getString(cursor.getColumnIndex("id")));
                                                propertyModel.setText(cursor.getString(cursor.getColumnIndex("text")));
                                                propertyModel.setValue(cursor.getString(cursor.getColumnIndex("value")));
                                                propertyModel.setLegalEntityId(cursor.getString(cursor.getColumnIndex("legal_Entity_Id")));
                                                listProperty.add(propertyModel);
                                            } while (cursor.moveToNext());
                                            cursor.close();
                                        }
                                        ///////////////Details of property
                                        if (listProperty.size() > 0) {
                                            strPropertyArray = new String[listProperty.size()];
                                            for (int j = 0; j < listProperty.size(); j++) {
                                                propertyModel = listProperty.get(j);
                                                strPropertyArray[j] = propertyModel.getText();
                                            }
                                        }
                                        if (listProperty != null && listProperty.size() > 0) {
                                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strPropertyArray) {
                                                @Override
                                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                                    View v = null;
                                                    // If this is the initial dummy entry, make it hidden
                                                    if (position == 0) {
                                                        TextView tv = new TextView(getContext());
                                                        tv.setHeight(0);
                                                        tv.setVisibility(View.GONE);
                                                        v = tv;
                                                    } else {
                                                        // Pass convertView as null to prevent reuse of special case views
                                                        v = super.getDropDownView(position, null, parent);
                                                    }
                                                    // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                                                    parent.setVerticalScrollBarEnabled(false);
                                                    return v;
                                                }
                                            };

                                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            autoProperty.setAdapter(adapter1);
                                        }

                                        autoProperty.setOnTouchListener(new View.OnTouchListener() {

                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                autoProperty.showDropDown();
                                                return false;
                                            }
                                        });

                                        autoProperty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                if (strPropertyArray != null && strPropertyArray.length > 0) {
                                                    strProperty = autoProperty.getText().toString();
                                                    propertyString = parent.getItemAtPosition(position).toString();

                                                    for (int i = 0; i < listProperty.size(); i++) {
                                                        propertyModel = listProperty.get(i);
                                                        String text = propertyModel.getText();
                                                        if (text.equalsIgnoreCase(propertyString)) {
                                                           String propid = propertyModel.getId();
                                                            propertyID = propid;
                                                            propertyValue = propertyModel.getValue();

                                                            listLoc = new ArrayList<>();
                                                            String location = "";
                                                            String where1 = " where property_id = " + "'" + propertyID + "'";
                                                            Cursor cursor4 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Location,where1);
                                                            if (cursor4 != null && cursor4.getCount() > 0) {
                                                                cursor4.moveToFirst();
                                                                do {
                                                                    locationModel = new LocationModel();
                                                                    locationModel.setId(cursor4.getString(cursor4.getColumnIndex("id")));
                                                                    locationModel.setText(cursor4.getString(cursor4.getColumnIndex("text")));
                                                                    locationModel.setValue(cursor4.getString(cursor4.getColumnIndex("value")));
                                                                    locationModel.setProperty_id(cursor4.getString(cursor4.getColumnIndex("property_id")));
                                                                    listLoc.add(locationModel);
                                                                } while (cursor4.moveToNext());
                                                                cursor4.close();
                                                            }
                                                            ///////////////////////////////
                                                            ///////////////Details of Location
                                                            if (listLoc.size() > 0) {
                                                                strLocArray = new String[listLoc.size()];
                                                                //   strLeadArray[0] = "Select Source Lead";
                                                                for (int m = 0; m < listLoc.size(); m++) {
                                                                    locationModel = listLoc.get(m);
                                                                    strLocArray[m] = locationModel.getText();
                                                                }
                                                            }
                                                            if (listLoc != null && listLoc.size() > 0) {
                                                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLocArray) {
                                                                    @Override
                                                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                                                        View v = null;
                                                                        // If this is the initial dummy entry, make it hidden
                                                                        if (position == 0) {
                                                                            TextView tv = new TextView(getContext());
                                                                            tv.setHeight(0);
                                                                            tv.setVisibility(View.GONE);
                                                                            v = tv;
                                                                        } else {
                                                                            // Pass convertView as null to prevent reuse of special case views
                                                                            v = super.getDropDownView(position, null, parent);
                                                                        }
                                                                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                                                                        parent.setVerticalScrollBarEnabled(false);
                                                                        return v;
                                                                    }
                                                                };

                                                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                autoLoc.setAdapter(adapter1);
                                                            }

                                                            autoLoc.setOnTouchListener(new View.OnTouchListener() {
                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    autoLoc.showDropDown();
                                                                    return false;
                                                                }
                                                            });

                                                            autoLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                @Override
                                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                    if (strLocArray != null && strLocArray.length > 0) {
                                                                        strLoc = autoLoc.getText().toString();
                                                                        locString = parent.getItemAtPosition(position).toString();

                                                                        for (int i = 0; i < listLoc.size(); i++) {
                                                                            locationModel = listLoc.get(i);
                                                                            String text = locationModel.getText();
                                                                            if (text.equalsIgnoreCase(locString)) {
                                                                                locationID  = locationModel.getId();
                                                                                locationValue = locationModel.getValue();
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            });

                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }


                            }
                        });

                    }else if(strLegalEntity.equalsIgnoreCase("New Proposal")){
                        cardNewProposal.setVisibility(View.VISIBLE);
                        cardIndividuals.setVisibility(View.GONE);
                        cardProperty.setVisibility(View.GONE);
                    }else {
                        cardIndividuals.setVisibility(View.GONE);
                        cardNewProposal.setVisibility(View.GONE);
                        cardProperty.setVisibility(View.VISIBLE);

                        listProperty = new ArrayList<>();
                        String property = "";
                        String where1 = " where legal_Entity_Id = " + "'" + legalEntityID + "'";
                        Cursor cursor = KHIL.dbCon.fetchFromSelect(DbHelper.M_Property, where1);
                        if (cursor != null && cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            do {
                                propertyModel = new PropertyModel();
                                propertyModel.setId(cursor.getString(cursor.getColumnIndex("id")));
                                propertyModel.setText(cursor.getString(cursor.getColumnIndex("text")));
                                propertyModel.setValue(cursor.getString(cursor.getColumnIndex("value")));
                                propertyModel.setLegalEntityId(cursor.getString(cursor.getColumnIndex("legal_Entity_Id")));
                                listProperty.add(propertyModel);
                            } while (cursor.moveToNext());
                            cursor.close();
                        }
                        ///////////////Details of property
                        if (listProperty.size() > 0) {
                            strPropertyArray = new String[listProperty.size()];
                            for (int j = 0; j < listProperty.size(); j++) {
                                propertyModel = listProperty.get(j);
                                strPropertyArray[j] = propertyModel.getText();
                            }
                        }

                        if (listProperty != null && listProperty.size() > 0) {
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strPropertyArray) {
                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                    View v = null;
                                    // If this is the initial dummy entry, make it hidden
                                    if (position == 0) {
                                        TextView tv = new TextView(getContext());
                                        tv.setHeight(0);
                                        tv.setVisibility(View.GONE);
                                        v = tv;
                                    } else {
                                        // Pass convertView as null to prevent reuse of special case views
                                        v = super.getDropDownView(position, null, parent);
                                    }
                                    // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                                    parent.setVerticalScrollBarEnabled(false);
                                    return v;
                                }
                            };

                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            autoProperty.setAdapter(adapter1);
                        }

                        autoProperty.setOnTouchListener(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                autoProperty.showDropDown();
                                return false;
                            }
                        });

                        autoProperty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (strPropertyArray != null && strPropertyArray.length > 0) {
                                    strProperty = autoProperty.getText().toString();
                                    propertyString = parent.getItemAtPosition(position).toString();

                                    for (int i = 0; i < listProperty.size(); i++) {
                                        propertyModel = listProperty.get(i);
                                        String text = propertyModel.getText();
                                        if (text.equalsIgnoreCase(propertyString)) {
                                            String propid = propertyModel.getId();
                                            propertyID = propid;
                                            propertyValue = propertyModel.getValue();


                                            listLoc = new ArrayList<>();
                                            String location = "";
                                            String where1 = " where property_id = " + "'" + propertyID + "'";
                                            Cursor cursor4 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Location,where1);
                                            if (cursor4 != null && cursor4.getCount() > 0) {
                                                cursor4.moveToFirst();
                                                do {
                                                    locationModel = new LocationModel();
                                                    locationModel.setId(cursor4.getString(cursor4.getColumnIndex("id")));
                                                    locationModel.setText(cursor4.getString(cursor4.getColumnIndex("text")));
                                                    locationModel.setValue(cursor4.getString(cursor4.getColumnIndex("value")));
                                                    locationModel.setProperty_id(cursor4.getString(cursor4.getColumnIndex("property_id")));
                                                    listLoc.add(locationModel);
                                                } while (cursor4.moveToNext());
                                                cursor4.close();
                                            }
                                            ///////////////////////////////
                                            ///////////////Details of Location
                                            if (listLoc.size() > 0) {
                                                strLocArray = new String[listLoc.size()];
                                                //   strLeadArray[0] = "Select Source Lead";
                                                for (int m = 0; m < listLoc.size(); m++) {
                                                    locationModel = listLoc.get(m);
                                                    strLocArray[m] = locationModel.getText();
                                                }
                                            }
                                            if (listLoc != null && listLoc.size() > 0) {
                                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLocArray) {
                                                    @Override
                                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                                        View v = null;
                                                        // If this is the initial dummy entry, make it hidden
                                                        if (position == 0) {
                                                            TextView tv = new TextView(getContext());
                                                            tv.setHeight(0);
                                                            tv.setVisibility(View.GONE);
                                                            v = tv;
                                                        } else {
                                                            // Pass convertView as null to prevent reuse of special case views
                                                            v = super.getDropDownView(position, null, parent);
                                                        }
                                                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                                                        parent.setVerticalScrollBarEnabled(false);
                                                        return v;
                                                    }
                                                };

                                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                autoLoc.setAdapter(adapter1);
                                            }

                                            autoLoc.setOnTouchListener(new View.OnTouchListener() {
                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    autoLoc.showDropDown();
                                                    return false;
                                                }
                                            });

                                            autoLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    if (strLocArray != null && strLocArray.length > 0) {
                                                        strLoc = autoLoc.getText().toString();
                                                        locString = parent.getItemAtPosition(position).toString();

                                                        for (int i = 0; i < listLoc.size(); i++) {
                                                            locationModel = listLoc.get(i);
                                                            String text = locationModel.getText();
                                                            if (text.equalsIgnoreCase(locString)) {
                                                                locationID  = locationModel.getId();
                                                                locationValue = locationModel.getValue();
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    }

                }
            }
        });




        if (listYear.size() > 0) {
            strYearArray = new String[listYear.size()];
            //   strLeadArray[0] = "Select Source Lead";
            for (int i = 0; i < listYear.size(); i++) {
                strYearArray[i] = listYear.get(i);
            }
        }
        if (listYear != null && listYear.size() > 0) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strYearArray) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = null;
                    // If this is the initial dummy entry, make it hidden
                    if (position == 0) {
                        TextView tv = new TextView(getContext());
                        tv.setHeight(0);
                        tv.setVisibility(View.GONE);
                        v = tv;
                    } else {
                        // Pass convertView as null to prevent reuse of special case views
                        v = super.getDropDownView(position, null, parent);
                    }
                    // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                    parent.setVerticalScrollBarEnabled(false);
                    return v;
                }
            };

            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autoYear.setAdapter(adapter1);
        }

        autoYear.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoYear.showDropDown();
                return false;
            }
        });

        autoYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                autoQuarter.setText("");
                autoMonth.setText("");
                if (strYearArray != null && strYearArray.length > 0) {
                    strYear = autoYear.getText().toString();
                    yearString = parent.getItemAtPosition(position).toString();
                }
            }
        });
        ///////////////////////////////
        ///////////////Details of Quarter
        if (listQuarter.size() > 0) {
            strQuarterArray = new String[listQuarter.size()];
            for (int i = 0; i < listQuarter.size(); i++) {
                strQuarterArray[i] = listQuarter.get(i);
            }
        }
        if (listQuarter != null && listQuarter.size() > 0) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strQuarterArray) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = null;
                    // If this is the initial dummy entry, make it hidden
                    if (position == 0) {
                        TextView tv = new TextView(getContext());
                        tv.setHeight(0);
                        tv.setVisibility(View.GONE);
                        v = tv;
                    } else {
                        // Pass convertView as null to prevent reuse of special case views
                        v = super.getDropDownView(position, null, parent);
                    }
                    // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                    parent.setVerticalScrollBarEnabled(false);
                    return v;
                }
            };

            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autoQuarter.setAdapter(adapter1);
        }

        autoQuarter.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoQuarter.showDropDown();
                return false;
            }
        });

        autoQuarter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoMonth.setText("");
                if (strQuarterArray != null && strQuarterArray.length > 0) {
                    strQuarter = autoQuarter.getText().toString();
                    quarterString = parent.getItemAtPosition(position).toString();
                }
                listMonth = new ArrayList<>();
                String month = "";
                String where = " where quater_id = " + "'" + quarterString + "'";
                Cursor cursor3 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Month,where);
                if (cursor3 != null && cursor3.getCount() > 0) {
                    cursor3.moveToFirst();
                    do {
                        monthModel = new MonthModel();
                        monthModel.setId(cursor3.getString(cursor3.getColumnIndex("id")));
                        monthModel.setText(cursor3.getString(cursor3.getColumnIndex("text")));
                        monthModel.setValue(cursor3.getString(cursor3.getColumnIndex("value")));
                        monthModel.setQuarter_id(cursor3.getString(cursor3.getColumnIndex("quater_id")));
                        listMonth.add(monthModel);
                    } while (cursor3.moveToNext());
                    cursor3.close();
                }

                if (listMonth != null) {
                    if (listMonth.size() > 0) {
                        strMonthArray = new String[listMonth.size()];
                        for (int i = 0; i < listMonth.size(); i++) {
                            monthModel  = listMonth.get(i);
                            strMonthArray[i] = monthModel.getText();
                        }
                    }
                }
                if (listMonth != null && listMonth.size() > 0) {
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strMonthArray) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = null;
                            // If this is the initial dummy entry, make it hidden
                            if (position == 0) {
                                TextView tv = new TextView(getContext());
                                tv.setHeight(0);
                                tv.setVisibility(View.GONE);
                                v = tv;
                            } else {
                                // Pass convertView as null to prevent reuse of special case views
                                v = super.getDropDownView(position, null, parent);
                            }
                            // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                            parent.setVerticalScrollBarEnabled(false);
                            return v;
                        }
                    };

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    autoMonth.setAdapter(adapter1);
                }

                autoMonth.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        autoMonth.showDropDown();
                        return false;
                    }
                });

                autoMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (strMonthArray != null && strMonthArray.length > 0) {
                            monthString = parent.getItemAtPosition(position).toString();

                            for (int i = 0; i < listMonth.size(); i++) {
                                monthModel = listMonth.get(i);
                                String text = monthModel.getText();
                                if (text.equalsIgnoreCase(monthString)) {
                                    monthID = monthModel.getValue();
                                }
                            }
                        }
                    }
                });

            }
        });


        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(manufactures.equalsIgnoreCase("samsung")) {
                    intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
                    intent.putExtra("CONTENT_TYPE", "*/*");
                } else {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                }
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });

        if(loginId.equalsIgnoreCase("finance") && password.equalsIgnoreCase("password")) {

            headLev2.setText("Type (Level 2)");
            headLev3.setText("TypeDesc (Level 3)");
            headLev4.setText("Level 4");
            headLev5.setText("Level 5");
            headLev6.setText("Level 6");
//            headLev7.setVisibility(View.GONE);
            headLev7.setText("Level 7");
            txtL2.setHint("Financial Type");
            fetchLevel2dataFin();

            txtL2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel3.setVisibility(View.GONE);
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);

                    txtL3.setHint("");
                    txtL3.setText("");
                    txtL4.setText("");
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel3 = "";
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    txtL2.showDropDown();
                    return false;
                }
            });

            txtL2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level2txtlayout.setHint("Financial Type");
                    if (strLevel2Array != null && strLevel2Array.length > 0) {
                        String aid = "",parentref = "";
                        valLevel2 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel2)) {
                                aid = leveldataModel.getaID();
                                txtL2Id = leveldataModel.getValue();

                            }
                        }
                        if (valLevel2 != null && valLevel2.length() > 0) {
                            fetchLevel3dataFin(aid);
                            level3txtlayout.setHint(valLevel2);
                            cardlevel3.setVisibility(View.VISIBLE);
                            txtL3.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            ///////////////////////////////////////
            //////////////////Level 3 details

            txtL3.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.setText("");
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    txtL3.showDropDown();
                    return false;
                }
            });

            txtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel3Array != null && strLevel3Array.length > 0) {
                        String aid = "",parentref = "";
                        valLevel3 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel3)) {
                                aid = leveldataModel.getaID();
                                txtL3Id = leveldataModel.getValue();
                            }
                        }
                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4data(aid);
                            if(chk.equals("no")) {
                                cardlevel4.setVisibility(View.GONE);
                            } else {
                                cardlevel4.setVisibility(View.VISIBLE);
                                level4txtlayout.setHint(valLevel3);
                            }
                        }
                    }
                }
            });

            ///////////////////////////////////////

            //////////////////Level 4 details

            txtL4.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    txtL4.showDropDown();
                    return false;
                }
            });

            txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel4Array != null && strLevel4Array.length > 0) {
                        String aid = "",parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = leveldataModel.getValue();
                            }
                        }
                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5data(aid);
                            if(chk.equals("no")) {
                                cardlevel5.setVisibility(View.GONE);
                            } else if(chk.equals("yes")) {
                                level5txtlayout.setHint(valLevel4);
                                cardlevel5.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                }
            });

            ///////////////////////////////////////
            //////////////////Level 5 details

            txtL5.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5Array != null && strLevel5Array.length > 0) {
                        String aid = "",parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = leveldataModel.getValue();
                            }
                        }
                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6data(aid);
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel6.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            ///////////////////////////////////////

            //////////////////Level 6 details

            txtL6.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                    valLevel7 = "";
                    txtL6.showDropDown();
                    return false;
                }
            });

            txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6Array != null && strLevel6Array.length > 0) {
                        String aid;
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        } else if(loginId.equalsIgnoreCase("hr") && password.equalsIgnoreCase("password")) {

//            headLev2.setVisibility(View.GONE);
//            headLev3.setVisibility(View.GONE);
            headLev2.setText("Level 2");
            headLev3.setText("Level 3");
            headLev4.setText("Type (Level 4)");
            headLev5.setText("TypeDesc (Level 5)");
            headLev6.setText("Level 6");
            headLev7.setText("Level7");
//            headLev7.setVisibility(View.GONE);
            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);
            cardlevel4.setVisibility(View.VISIBLE);
            txtL4.setHint("HR");

            fetchLevel4dataHR();

            txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel5="";
                    valLevel6="";
                    valLevel7="";
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.showDropDown();
                    return false;
                }
            });

            txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level4txtlayout.setHint("HR");
                    if (strLevel4ArrayHR != null && strLevel4ArrayHR.length > 0) {
                        String aid = "",parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                         for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = leveldataModel.getValue();
                            }
                        }
                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataHR(aid);
                            level5txtlayout.setHint(valLevel4);
                            cardlevel5.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel6="";
                    valLevel7="";
                    cardlevel7.setVisibility(View.GONE);
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayHR != null && strLevel5ArrayHR.length > 0) {
                        String aid = "",parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = leveldataModel.getValue();
                            }
                        }
                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataHR(aid);
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel6.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                    valLevel7="";
                    txtL6.showDropDown();
                    return false;
                }
            });

            txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayHR != null && strLevel6ArrayHR.length > 0) {
                        String aid;
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        } else if(loginId.equalsIgnoreCase("cmd") && password.equalsIgnoreCase("password")) {

//            headLev2.setVisibility(View.GONE);
//            headLev3.setVisibility(View.GONE);
            headLev2.setText("Level 2");
            headLev3.setText("Level 3");
            headLev4.setText("Type (Level 4)");
            headLev5.setText("TypeDesc (Level 5)");
            headLev6.setText("Level 6");
            headLev7.setText("Level 7");

            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            cardlevel4.setVisibility(View.VISIBLE);
            txtL4.setHint("Type");

            fetchLevel4dataCMD();

            txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel5="";
                    valLevel6="";
                    valLevel7="";
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.showDropDown();
                    return false;
                }
            });

            txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level4txtlayout.setHint("CMD");
                    if (strLevel4ArrayCMD != null && strLevel4ArrayCMD.length > 0) {
                       String aid = "",parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataCMD(aid);

                            if(chk.equals("yes")) {
                                level5txtlayout.setHint(valLevel4);
                                cardlevel5.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel5.setVisibility(View.GONE);
                            }

                        }
                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel6="";
                    valLevel7="";
                    cardlevel7.setVisibility(View.GONE);
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayCMD != null && strLevel5ArrayCMD.length > 0) {
                        String aid = "",parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = leveldataModel.getValue();
                            }
                        }
                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataCMD(aid);
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel6.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                    valLevel7="";
                    txtL6.showDropDown();
                    return false;
                }
            });

            txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayCMD != null && strLevel6ArrayCMD.length > 0) {
                        String aid = "",parentref = "";
                        valLevel6 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                               txtL6Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataCMD(aid);
                            if(chk.equals("yes")) {
                                level7txtlayout.setHint(valLevel5);
                                cardlevel7.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel7.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL7.showDropDown();
                    return false;
                }
            });

            txtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel7ArrayCMD != null && strLevel7ArrayCMD.length > 0) {
                        String aid = "";
                        valLevel7 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel7)) {
                                aid = leveldataModel.getaID();
                                txtL7Id = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        } else if(loginId.equalsIgnoreCase("cs") && password.equalsIgnoreCase("password")) {

            headLev2.setText("Type (Level 2)");
            headLev3.setText("TypeDesc (Level 3)");
            headLev4.setText("Level 4");

//            headLev5.setVisibility(View.GONE);
//            headLev6.setVisibility(View.GONE);
//            headLev7.setVisibility(View.GONE);
            headLev5.setText("Level 5");
            headLev6.setText("Level 6");
            headLev7.setText("Level 7");

            fetchLevel2dataCS();
            txtL2.setHint("Type");

            txtL2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel3.setVisibility(View.GONE);
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL3.setText("");
                    txtL4.setText("");
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel3 = "";
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    txtL2.showDropDown();
                    return false;
                }
            });

            txtL2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level2txtlayout.setHint("Type");
                    if (strLevel2ArrayCS != null && strLevel2ArrayCS.length > 0) {
                        String aid = "",parentref = "";
                        valLevel2 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel2)) {
                                aid = leveldataModel.getaID();
                                txtL2Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel2 != null && valLevel2.length() > 0) {
                            fetchLevel3dataCS(aid);
                            level3txtlayout.setHint(valLevel2);
                            cardlevel3.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            txtL3.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.setText("");
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    txtL3.showDropDown();
                    return false;
                }
            });

            txtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel3ArrayCS != null && strLevel3ArrayCS.length > 0) {
                        String aid = "",parentref = "";
                        valLevel3 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel3)) {
                                aid = leveldataModel.getaID();
                                txtL3Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4dataCS(aid);
                            if(chk.equals("no")) {
                                cardlevel4.setVisibility(View.GONE);
                            } else {
                                cardlevel4.setVisibility(View.VISIBLE);
                                level4txtlayout.setHint(valLevel3);
                            }
                        }
                    }
                }
            });

            txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL4.showDropDown();
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    return false;
                }
            });

            txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel4ArrayCS != null && strLevel4ArrayCS.length > 0) {
                        String aid = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = leveldataModel.getValue();
                            }
                        }

                    }
                }
            });
        } else if(loginId.equalsIgnoreCase("marketing") && password.equalsIgnoreCase("password")) {

            headLev2.setText("Level 2");
            headLev3.setText("Level 3");
//            headLev2.setVisibility(View.GONE);
//            headLev3.setVisibility(View.GONE);
            headLev4.setText("Type (Level 4)");
            headLev5.setText("TypeDesc (Level 5)");
            headLev6.setText("Level 6");
            headLev7.setText("Level 7");
            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            cardlevel4.setVisibility(View.VISIBLE);
            txtL4.setHint("Occupancy");
            fetchLevel4dataMAR();

            txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.showDropDown();
                    return false;
                }
            });

            txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level4txtlayout.setHint("Occupancy");
                    if (strLevel4ArrayMAR != null && strLevel4ArrayMAR.length > 0) {
                        String aid = "",parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataMAR(aid);
                            if(chk.equals("yes")) {
                                level5txtlayout.setHint(valLevel4);
                                cardlevel5.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel5.setVisibility(View.GONE);
                            }

                        }
                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayMAR != null && strLevel5ArrayMAR.length > 0) {
                        String aid = "",parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataMAR(aid);
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel6.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                    valLevel7 = "";
                    txtL6.showDropDown();
                    return false;
                }
            });

            txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayMAR != null && strLevel6ArrayMAR.length > 0) {
                        String aid = "",parentref  ="";
                        valLevel6 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataMAR(aid);
                            if(chk.equals("yes")) {
                                level7txtlayout.setHint(valLevel6);
                                cardlevel7.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel7.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL7.showDropDown();
                    return false;
                }
            });

            txtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel7ArrayMAR != null && strLevel7ArrayMAR.length > 0) {
                        String aid = "";
                        valLevel7 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel7)) {
                                aid = leveldataModel.getaID();
                                txtL7Id = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });

        } else if(loginId.equalsIgnoreCase("Personal") && password.equalsIgnoreCase("password")) {
            headLev2.setText("Level 2");
//            headLev2.setVisibility(View.GONE);
            headLev3.setText("Type (Level 3)");
            headLev4.setText("TypeDesc (Level 4)");
            headLev5.setText("Level 5");
            headLev6.setText("Level 6");
            headLev7.setText("Level 7");
            /*headLev6.setVisibility(View.GONE);
            headLev7.setVisibility(View.GONE);*/

            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.VISIBLE);
            txtL3.setHint("Type");

            fetchLevel3dataPER();

            txtL3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.setText("");
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    txtL3.showDropDown();
                    return false;
                }
            });

            txtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level3txtlayout.setHint("Type");
                    if (strLevel3ArrayPer != null && strLevel3ArrayPer.length > 0) {
                        String aid ="",parentref = "";
                        valLevel3 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel3)) {
                                aid = leveldataModel.getaID();
                                txtL3Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4dataPER(aid);
                            if(chk.equals("no")) {
                                cardlevel4.setVisibility(View.GONE);
                            } else {
                                cardlevel4.setVisibility(View.VISIBLE);
                                level4txtlayout.setHint(valLevel3);
                            }
                        }
                    }
                }
            });

            txtL4.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    txtL4.showDropDown();
                    return false;
                }
            });

            txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel4ArrayPer != null && strLevel4ArrayPer.length > 0) {
                        String aid = "",parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                               txtL4Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataPER(aid);
                            if(chk.equals("no")) {
                                cardlevel5.setVisibility(View.GONE);
                            } else if(chk.equals("yes")) {
                                level5txtlayout.setHint(valLevel4);
                                cardlevel5.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL7.setText("");
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayPer != null && strLevel5ArrayPer.length > 0) {
                        String aid = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        } else if(loginId.equalsIgnoreCase("legal") && password.equalsIgnoreCase("password")) {
            headLev2.setText("Level 2");
            headLev3.setText("Level 3");
//            headLev2.setVisibility(View.GONE);
//            headLev3.setVisibility(View.GONE);
            headLev4.setText("Type (Level 4)");
            headLev5.setText("TypeDesc (Level 5)");
            headLev6.setText("Level 6");
            headLev7.setText("Level 7");

            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            cardlevel4.setVisibility(View.VISIBLE);

            txtL4.setHint("Legal");
            fetchLevel4dataLEGAL();

            txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setText("");
                    txtL7.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.showDropDown();
                    return false;
                }
            });

            txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level4txtlayout.setHint("Legal");
                    if (strLevel4ArrayLEGAL != null && strLevel4ArrayLEGAL.length > 0) {
                        String aid = "",parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataLEGAL(aid);
                            level5txtlayout.setHint(valLevel4);
                            cardlevel5.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL7.setText("");
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayLEGAL!= null && strLevel5ArrayLEGAL.length > 0) {
                        String aid = "",parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataLEGAL(aid);
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel6.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                    txtL6.showDropDown();
                    return false;
                }
            });

            txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayLEGAL!= null && strLevel6ArrayLEGAL.length > 0) {
                        String aid = "",parentref = "";
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = leveldataModel.getValue();
                            }
                        }

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataLEGAL(aid);
                            if(chk.equals("yes")) {
                                level7txtlayout.setHint(valLevel6);
                                cardlevel7.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                cardlevel7.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL7.showDropDown();
                    return false;
                }
            });

            txtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel7ArrayLEGAL != null && strLevel7ArrayLEGAL.length > 0) {
                        String aid = "";
                        valLevel7 = parent.getItemAtPosition(position).toString();

                        for(int i=0; i<listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel7)) {
                                aid = leveldataModel.getaID();
                                txtL7Id = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        }
        ///////////////////////////////////////

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNoFile.getText().toString().equalsIgnoreCase("No file chosen")) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(
                            getActivity()).create();

                    // Setting Dialog Title
                    alertDialog.setTitle(loginId);

                    // Setting Dialog Message
                    alertDialog.setMessage("Please attach the file");

                    // Setting Icon to Dialog
//                    alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            alertDialog.dismiss();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                    return;
                } else {
                    file = txtNoFile.getText().toString();
                }
                txtNoFile.setText("No file chosen");
                no_files.setVisibility(View.GONE);
                listUpload.setVisibility(View.VISIBLE);
                
                legalEntity = autoLegalEntity.getText().toString();
                individuals = autoIndividuals.getText().toString();
                newProposal = etNewProposal.getText().toString();
                property = autoProperty.getText().toString();
                year = autoYear.getText().toString();
                quarter = autoQuarter.getText().toString();
                month = autoMonth.getText().toString();
                location = autoLoc.getText().toString();
                level2 = txtL2.getText().toString();
                level3 = txtL3.getText().toString();
                level4 = txtL4.getText().toString();
                level5 = txtL5.getText().toString();
                level6 = txtL6.getText().toString();
                level7 = txtL7.getText().toString();

                insertIntoUploadDb(1);

                if(loginId.equalsIgnoreCase("cmd") || loginId.equalsIgnoreCase("hr") ||
                        loginId.equalsIgnoreCase("legal") || loginId.equalsIgnoreCase("marketing")) {
                    txtL4.setText("");
                    txtL4.setHint("");

                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                } else if(loginId.equalsIgnoreCase("cs") || loginId.equalsIgnoreCase("finance")) {
                    txtL2.setText("");
                    txtL2.setHint("");

                    cardlevel3.setVisibility(View.GONE);
                    txtL3.setText("");
                    cardlevel4.setVisibility(View.GONE);
                    txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                }  else if(loginId.equalsIgnoreCase("Personal")) {
                    txtL3.setText("");
                    txtL3.setHint("");

                    cardlevel4.setVisibility(View.GONE);
                    txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file = txtNoFile.getText().toString();
                txtNoFile.setText("No file chosen");
                layout_edit.setVisibility(View.GONE);
                btnUpload.setVisibility(View.VISIBLE);

                legalEntity = autoLegalEntity.getText().toString();
                individuals = autoIndividuals.getText().toString();
                newProposal = etNewProposal.getText().toString();
                property = autoProperty.getText().toString();
                year = autoYear.getText().toString();
                quarter = autoQuarter.getText().toString();
                month = autoMonth.getText().toString();
                location = autoLoc.getText().toString();
                level2 = txtL2.getText().toString();
                level3 = txtL3.getText().toString();
                level4 = txtL4.getText().toString();
                level5 = txtL5.getText().toString();
                level6 = txtL6.getText().toString();
                level7 = txtL7.getText().toString();

                insertIntoUploadDb(2);

                if(loginId.equalsIgnoreCase("cmd") || loginId.equalsIgnoreCase("hr") ||
                        loginId.equalsIgnoreCase("legal") || loginId.equalsIgnoreCase("marketing")) {
                    txtL4.setText("");
                    txtL4.setHint("");

                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                } else if(loginId.equalsIgnoreCase("cs") || loginId.equalsIgnoreCase("finance")) {
                    txtL2.setText("");
                    txtL2.setHint("");

                    cardlevel3.setVisibility(View.GONE);
                    txtL3.setText("");
                    cardlevel4.setVisibility(View.GONE);
                    txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                }  else if(loginId.equalsIgnoreCase("Personal")) {
                    txtL3.setText("");
                    txtL3.setHint("");

                    cardlevel4.setVisibility(View.GONE);
                    txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNoFile.setText("No file chosen");
                btnUpload.setVisibility(View.VISIBLE);
                layout_edit.setVisibility(View.GONE);

                if(loginId.equalsIgnoreCase("cmd") || loginId.equalsIgnoreCase("hr") ||
                        loginId.equalsIgnoreCase("legal") || loginId.equalsIgnoreCase("marketing")) {
                    txtL4.setText("");
                    txtL4.setHint("");

                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                } else if(loginId.equalsIgnoreCase("cs") || loginId.equalsIgnoreCase("finance")) {
                    txtL2.setText("");
                    txtL2.setHint("");

                    cardlevel3.setVisibility(View.GONE);
                    txtL3.setText("");
                    cardlevel4.setVisibility(View.GONE);
                    txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                }  else if(loginId.equalsIgnoreCase("Personal")) {
                    txtL3.setText("");
                    txtL3.setHint("");

                    cardlevel4.setVisibility(View.GONE);
                    txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                }
            }
        });

        viewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openFile(getContext(),filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = getAllSubmitData();
                if (size > 0) {
                    new SaveInsertUpdate().execute();
                } else {
                    Toast.makeText(getActivity(), "No data for uploading ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case PICKFILE_RESULT_CODE:
                if(resultCode == getActivity().RESULT_OK) {
                    filePath = data.getData().getPath();

                    if(filePath.contains("/external/images/media/")) {
                        Uri selectedImageURI = data.getData();
                        File imageFile = new File(getRealPathFromURI(selectedImageURI));
                        filePath = imageFile.getAbsolutePath();
                    }

                    fileName = filePath.substring(filePath.lastIndexOf("/")+1);
                    txtNoFile.setText(fileName);
                    String filename11 = fileName;
                    String filenameArray[] = filename11.split("\\.");
                    fileExtension = filenameArray[filenameArray.length-1];
                    viewFile.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void insertIntoUploadDb(int flag) {

        String Doc_no = roleID+"_"+legalEntityValue+"_"+propertyValue+"_"+locationValue+"_"+year+"_"+quarter+"_"+monthID;
        String fileUp = Doc_no;
        if (txtL2Id != null && txtL2Id.length()>0){
            fileUp = fileUp+"_"+txtL2Id;
        }if (txtL3Id != null && txtL3Id.length()>0){
            fileUp = fileUp+"_"+txtL3Id;
        }if (txtL4Id != null && txtL4Id.length()>0){
            fileUp = fileUp+"_"+txtL4Id;
        }if (txtL5Id != null && txtL5Id.length()>0){
            fileUp = fileUp+"_"+txtL5Id;
        }if (txtL6Id != null && txtL6Id.length()>0){
            fileUp = fileUp+"_"+txtL6Id;
        }if (txtL7Id != null && txtL7Id.length()>0){
            fileUp = fileUp+"_"+txtL7Id;
        }

        fileUp = fileUp+fileName;
        String strLastSync = "0";
        String selection = "id = ?";
        String id = "";

        if(flag == 1) {
            String[] args = {loginId};
            ID = KHIL.dbCon.getCountOfRows(DbHelper.TABLE_UPLOAD," user_id = ?", args) + 1;
        }
        id = String.valueOf(ID);
        String[] selectionArgs = {id};
        String valuesArray[] = {id, loginId, legalEntity,individuals,newProposal,property, year, quarter, month,
                location, file, level2, level3, level4, level5, level6, level7,txtL2Id,txtL3Id,txtL4Id,txtL5Id,txtL6Id,txtL7Id,
                roleID,legalEntityID,propertyID,individualID,locationID,Doc_no,fileUp,filePath,fileExtension,"",date,strLastSync};

        boolean result = KHIL.dbCon.updateBulk(DbHelper.TABLE_UPLOAD, selection, valuesArray, utils.columnNamesUpload, selectionArgs);

        if (result) {
            System.out.println("Upload details are added");
           /* SaveInsertUpdate saveInsertUpdate = new SaveInsertUpdate();
            saveInsertUpdate.execute();*/
            getUploadData();
        } else {
            System.out.println("FAILURE..!!");
        }
    }

    public void getUploadData() {
        Cursor cursor = null;
        uploadModelList.clear();
        uploadModel = new UploadModel();

        String where = " where user_id = '" + loginId + "'";
        cursor = KHIL.dbCon.fetchFromSelect(DbHelper.TABLE_UPLOAD, where);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                uploadModel = createUploadModel(cursor);
                uploadModelList.add(uploadModel);
                setValues(uploadModelList);
            } while(cursor.moveToNext());
            cursor.close();
        }
    }

    public void openFile(Context context, String url) throws IOException {
        // Create URI
        File file = new File(url);
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.contains(".doc") || url.contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if(url.contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri,"application/pdf");
        } else if(url.contains(".ppt") || url.contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if(url.contains(".xls") || url.contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if(url.contains(".zip") || url.contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/zip");
        } else if(url.contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if(url.contains(".wav") || url.contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if(url.contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if(url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
            // JPG file
            showCustomDialog(uri);
//            intent.setDataAndType(uri, "image/jpeg");
            return;
        } else if(url.contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if(url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent1 = Intent.createChooser(intent, "Open File");
        try {
            context.startActivity(intent1);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }

        /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/
    }

    private void showCustomDialog(Uri uri) {

        final android.support.v7.app.AlertDialog.Builder DialogMaster = new android.support.v7.app.AlertDialog.Builder(getActivity());

        @SuppressLint("WrongConstant")
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogViewMaster = li.inflate(R.layout.image_dailog, null);
        DialogMaster.setView(dialogViewMaster);

        final android.support.v7.app.AlertDialog showMaster = DialogMaster.show();

        Button btnDismissMaster = (Button) showMaster.findViewById(R.id.buttonClose);
        ImageView image = (ImageView) showMaster.findViewById(R.id.imageviewDialog);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDismissMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMaster.dismiss();
            }
        });
        showMaster.setCancelable(false);

    }

    private void showAttachDialog() {

        final android.support.v7.app.AlertDialog.Builder DialogMaster = new android.support.v7.app.AlertDialog.Builder(getActivity());

        @SuppressLint("WrongConstant")
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogViewMaster = li.inflate(R.layout.attachment_dialog, null);
        DialogMaster.setView(dialogViewMaster);

        final android.support.v7.app.AlertDialog showMaster = DialogMaster.show();

        Button btnImg = (Button) showMaster.findViewById(R.id.btnImg);
        Button btnDoc = (Button) showMaster.findViewById(R.id.btnDoc);
        Button btnCancel = (Button) showMaster.findViewById(R.id.btnCancel);

        /*btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityPermissionsDispatcher.onPickPhotoWithCheck(this);
            }
        });
*/
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMaster.dismiss();
            }
        });
        showMaster.setCancelable(false);

    }

    private UploadModel createUploadModel(Cursor cursor) {
        uploadModel = new UploadModel();
        try {
            uploadModel.setId(cursor.getString(cursor.getColumnIndex("id")));
            uploadModel.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
            uploadModel.setLevel2(cursor.getString(cursor.getColumnIndex("level2")));
            uploadModel.setLevel3(cursor.getString(cursor.getColumnIndex("level3")));
            uploadModel.setLevel4(cursor.getString(cursor.getColumnIndex("level4")));
            uploadModel.setLevel5(cursor.getString(cursor.getColumnIndex("level5")));
            uploadModel.setLevel6(cursor.getString(cursor.getColumnIndex("level6")));
            uploadModel.setLevel7(cursor.getString(cursor.getColumnIndex("level7")));
            uploadModel.setRoleID(cursor.getString(cursor.getColumnIndex("role_Id")));
            uploadModel.setLegalEntityID(cursor.getString(cursor.getColumnIndex("legalEntityID")));
            uploadModel.setPropertyID(cursor.getString(cursor.getColumnIndex("propertyID")));
            uploadModel.setLocationID(cursor.getString(cursor.getColumnIndex("locationID")));
            uploadModel.setDoc_no(cursor.getString(cursor.getColumnIndex("doc_no")));
            uploadModel.setLoginId(cursor.getString(cursor.getColumnIndex("user_id")));
            uploadModel.setDate(cursor.getString(cursor.getColumnIndex("updated_date")));
            uploadModel.setIndividualID(cursor.getString(cursor.getColumnIndex("individualID")));
            uploadModel.setNewProposal(cursor.getString(cursor.getColumnIndex("new_proposal")));
            uploadModel.setTxtL2Id(cursor.getString(cursor.getColumnIndex("level2Id")));
            uploadModel.setTxtL3Id(cursor.getString(cursor.getColumnIndex("level3Id")));
            uploadModel.setTxtL4Id(cursor.getString(cursor.getColumnIndex("level4Id")));
            uploadModel.setTxtL5Id(cursor.getString(cursor.getColumnIndex("level5Id")));
            uploadModel.setTxtL6Id(cursor.getString(cursor.getColumnIndex("level6Id")));
            uploadModel.setTxtL7Id(cursor.getString(cursor.getColumnIndex("level7Id")));
            uploadModel.setYear(cursor.getString(cursor.getColumnIndex("col_year")));
            uploadModel.setQuarter(cursor.getString(cursor.getColumnIndex("quarter")));
            uploadModel.setMonth(cursor.getString(cursor.getColumnIndex("col_month")));
            uploadModel.setFileUp(cursor.getString(cursor.getColumnIndex("file")));
            uploadModel.setFilePath(cursor.getString(cursor.getColumnIndex("file_path")));
            uploadModel.setFileExtension(cursor.getString(cursor.getColumnIndex("file_extension")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return uploadModel;
    }

    private void setValues(List<UploadModel> uploadModelList) {
        if(uploadModelList != null && uploadModelList.size() > 0) {
            try {
                adapter = new UploadAdapter(getActivity(), uploadModelList);
                listUpload.setAdapter(adapter);
                setListViewHeightBasedOnItems(listUpload);
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

     public void fetchLevel2dataFin() {
        try {
            level2list = new ArrayList<>();
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "1" + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String level2Fin = "";
                    level2Fin = cursor1.getString(cursor1.getColumnIndex("text"));
                    level2list.add(level2Fin);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level2list);
            if (level2list.size() > 0) {
                strLevel2Array = new String[level2list.size()];
                for (int i = 0; i < level2list.size(); i++) {
                    strLevel2Array[i] = level2list.get(i);
                }
            }
            if (level2list != null && level2list.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel2Array) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL2.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel3dataFin(String aID) {
        try {

            level3list = new ArrayList<>();
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "2" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level3list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level3list);
            if (level3list.size() > 0) {
                strLevel3Array = new String[level3list.size()];
                for (int i = 0; i < level3list.size(); i++) {
                    strLevel3Array[i] = level3list.get(i);
                }
            }
            if (level3list != null && level3list.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel3Array) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4data(String aID) {
        try {
            level4list = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "3" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }

            Collections.sort(level4list);
            if (level4list.size() > 0) {
                chk = "yes";
                strLevel4Array = new String[level4list.size()];
                for (int i = 0; i < level4list.size(); i++) {
                    strLevel4Array[i] = level4list.get(i);
                }
            }
            if (level4list != null && level4list.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel4Array) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5data(String aID) {
        try {

            level5list = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "4" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level5list);
            if (level5list.size() > 0) {
                strLevel5Array = new String[level5list.size()];
                for (int i = 0; i < level5list.size(); i++) {
                    strLevel5Array[i] = level5list.get(i);
                }
            }
            if (level5list != null && level5list.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel5Array) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6data(String aID) {
        try {

            level6list = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "5" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6list);
            if (level6list.size() > 0) {
                strLevel6Array = new String[level6list.size()];
                for (int i = 0; i < level6list.size(); i++) {
                    strLevel6Array[i] = level6list.get(i);
                }
            }
            if (level6list != null && level6list.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel6Array) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataHR() {
        try {
            level4listHR = new ArrayList<>();
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "3" + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String hr = "";
                    hr = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4listHR.add(hr);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level4listHR);
            if (level4listHR.size() > 0) {
                strLevel4ArrayHR = new String[level4listHR.size()];
                for (int i = 0; i < level4listHR.size(); i++) {
                    strLevel4ArrayHR[i] = level4listHR.get(i);
                }
            }
            if (level4listHR != null && level4listHR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel4ArrayHR) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataHR(String aID) {
        try {

            level5listHR = new ArrayList<>();
            String level5 = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "4" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5listHR.add(level5);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            Collections.sort(level5listHR);
            if (level5listHR.size() > 0) {
                strLevel5ArrayHR = new String[level5listHR.size()];
                for (int i = 0; i < level5listHR.size(); i++) {
                    strLevel5ArrayHR[i] = level5listHR.get(i);
                }
            }
            if (level5listHR != null && level5listHR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel5ArrayHR) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataHR(String aID) {
        try {

            level6listHR = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "5" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6listHR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6listHR);
            if (level6listHR.size() > 0) {
                strLevel6ArrayHR = new String[level6listHR.size()];
                for (int i = 0; i < level6listHR.size(); i++) {
                    strLevel6ArrayHR[i] = level6listHR.get(i);
                }
            }
            if (level6listHR != null && level6listHR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel6ArrayHR) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataCMD() {
        try {
            level4listCMD = new ArrayList<>();

            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "4" + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String cmd = "";
                    cmd = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4listCMD.add(cmd);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level4listCMD);
            if (level4listCMD.size() > 0) {
                strLevel4ArrayCMD = new String[level4listCMD.size()];
                for (int i = 0; i < level4listCMD.size(); i++) {
                    strLevel4ArrayCMD[i] = level4listCMD.get(i);
                }
            }
            if (level4listCMD != null && level4listCMD.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel4ArrayCMD) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataCMD(String aID) {
        try {
            level5listCMD = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "5" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5listCMD.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(level5.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }

            Collections.sort(level5listCMD);
            if (level5listCMD.size() > 0) {
                strLevel5ArrayCMD = new String[level5listCMD.size()];
                for (int i = 0; i < level5listCMD.size(); i++) {
                    strLevel5ArrayCMD[i] = level5listCMD.get(i);
                }
            }
            if (level5listCMD != null && level5listCMD.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel5ArrayCMD) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataCMD(String aID) {
        try {

            level6listCMD = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "6" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6listCMD.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6listCMD);
            if (level6listCMD.size() > 0) {
                strLevel6ArrayCMD = new String[level6listCMD.size()];
                for (int i = 0; i < level6listCMD.size(); i++) {
                    strLevel6ArrayCMD[i] = level6listCMD.get(i);
                }
            }
            if (level6listCMD != null && level6listCMD.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel6ArrayCMD) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataCMD(String aID) {
        try {

            level7listCMD = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "7" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level7listCMD.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level7listCMD);
            if (level7listCMD.size() > 0) {
                strLevel7ArrayCMD = new String[level7listCMD.size()];
                for (int i = 0; i < level7listCMD.size(); i++) {
                    strLevel7ArrayCMD[i] = level7listCMD.get(i);
                }
            }
            if (level7listCMD != null && level7listCMD.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel7ArrayCMD) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel2dataCS() {
        try {
            level2listCS = new ArrayList<>();

            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "1" + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level2listCS.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level2listCS);
            if (level2listCS.size() > 0) {
                strLevel2ArrayCS = new String[level2listCS.size()];
                for (int i = 0; i < level2listCS.size(); i++) {
                    strLevel2ArrayCS[i] = level2listCS.get(i);
                }
            }
            if (level2listCS != null && level2listCS.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel2ArrayCS) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL2.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel3dataCS(String aID) {
        try {
            level3listCS = new ArrayList<>();

            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "2" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level3listCS.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level3listCS);
            if (level3listCS.size() > 0) {
                strLevel3ArrayCS = new String[level3listCS.size()];
                for (int i = 0; i < level3listCS.size(); i++) {
                    strLevel3ArrayCS[i] = level3listCS.get(i);
                }
            }
            if (level3listCS != null && level3listCS.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel3ArrayCS) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataCS(String aID) {
        try {
            level4listCS = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "3" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4listCS.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }

            Collections.sort(level4listCS);
            if (level4listCS.size() > 0) {
                chk = "yes";
                strLevel4ArrayCS = new String[level4listCS.size()];
                for (int i = 0; i < level4listCS.size(); i++) {
                    strLevel4ArrayCS[i] = level4listCS.get(i);
                }
            }
            if (level4listCS != null && level4listCS.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel4ArrayCS) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataMAR() {
        try {
            level4listMAR = new ArrayList<>();

            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "3" + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String mar = "";
                    mar = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4listMAR.add(mar);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level4listMAR);
            if (level4listMAR.size() > 0) {
                strLevel4ArrayMAR = new String[level4listMAR.size()];
                for (int i = 0; i < level4listMAR.size(); i++) {
                    strLevel4ArrayMAR[i] = level4listMAR.get(i);
                }
            }
            if (level4listMAR != null && level4listMAR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel4ArrayMAR) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataMAR(String aID) {
        try {

            level5listMAR = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "4" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5listMAR.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(level5.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }

            Collections.sort(level5listMAR);
            if (level5listMAR.size() > 0) {
                strLevel5ArrayMAR= new String[level5listMAR.size()];
                for (int i = 0; i < level5listMAR.size(); i++) {
                    strLevel5ArrayMAR[i] = level5listMAR.get(i);
                }
            }
            if (level5listMAR != null && level5listMAR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel5ArrayMAR) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataMAR(String aID) {
        try {
            level6listMAR = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "5" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6listMAR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6listMAR);
            if (level6listMAR.size() > 0) {
                strLevel6ArrayMAR = new String[level6listMAR.size()];
                for (int i = 0; i < level6listMAR.size(); i++) {
                    strLevel6ArrayMAR[i] = level6listMAR.get(i);
                }
            }
            if (level6listMAR != null && level6listMAR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel6ArrayMAR) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataMAR(String aID) {
        try {

            level7listMAR = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "6" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level7listMAR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level7listMAR);
            if (level7listMAR.size() > 0) {
                strLevel7ArrayMAR = new String[level7listMAR.size()];
                for (int i = 0; i < level7listMAR.size(); i++) {
                    strLevel7ArrayMAR[i] = level7listMAR.get(i);
                }
            }
            if (level7listMAR != null && level7listMAR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel7ArrayMAR) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel3dataPER() {
        try {
            level3listPER = new ArrayList<>();

            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "2" + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level3listPER.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level3listPER);
            if (level3listPER.size() > 0) {
                strLevel3ArrayPer = new String[level3listPER.size()];
                for (int i = 0; i < level3listPER.size(); i++) {
                    strLevel3ArrayPer[i] = level3listPER.get(i);
                }
            }
            if (level3listPER != null && level3listPER.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel3ArrayPer) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataPER(String aID) {
        try {
            level4listPER = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "4" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4listPER.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }

            Collections.sort(level4listPER);
            if (level4listPER.size() > 0) {
                chk = "yes";
                strLevel4ArrayPer = new String[level4listPER.size()];
                for (int i = 0; i < level4listPER.size(); i++) {
                    strLevel4ArrayPer[i] = level4listPER.get(i);
                }
            }
            if (level4listPER != null && level4listPER.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel4ArrayPer) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataPER(String aID) {
        try {
            level5listPER = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "5" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5listPER.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level5listPER);
            if (level5listPER.size() > 0) {
                strLevel5ArrayPer = new String[level5listPER.size()];
                for (int i = 0; i < level5listPER.size(); i++) {
                    strLevel5ArrayPer[i] = level5listPER.get(i);
                }
            }
            if (level5listPER != null && level5listPER.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel5ArrayPer) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataLEGAL() {
        try {
            level4listLEGAL = new ArrayList<>();

            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "3" + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String legal = "";
                    legal = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4listLEGAL.add(legal);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            Collections.sort(level4listLEGAL);
            if (level4listLEGAL.size() > 0) {
                strLevel4ArrayLEGAL = new String[level4listLEGAL.size()];
                for (int i = 0; i < level4listLEGAL.size(); i++) {
                    strLevel4ArrayLEGAL[i] = level4listLEGAL.get(i);
                }
            }
            if (level4listLEGAL != null && level4listLEGAL.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel4ArrayLEGAL) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataLEGAL(String aID) {
        try {

            level5listLEGAL = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "4" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5listLEGAL.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            Collections.sort(level5listLEGAL);
            if (level5listLEGAL.size() > 0) {
                strLevel5ArrayLEGAL= new String[level5listLEGAL.size()];
                for (int i = 0; i < level5listLEGAL.size(); i++) {
                    strLevel5ArrayLEGAL[i] = level5listLEGAL.get(i);
                }
            }
            if (level5listLEGAL != null && level5listLEGAL.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel5ArrayLEGAL) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataLEGAL(String aID) {
        try {
            level6listLEGAL = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "5" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6listLEGAL.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(temp.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6listLEGAL);
            if (level6listLEGAL.size() > 0) {
                strLevel6ArrayLEGAL = new String[level6listLEGAL.size()];
                for (int i = 0; i < level6listLEGAL.size(); i++) {
                    strLevel6ArrayLEGAL[i] = level6listLEGAL.get(i);
                }
            }
            if (level6listLEGAL != null && level6listLEGAL.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel6ArrayLEGAL) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataLEGAL(String aID) {
        try {
            level7listLEGAL = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and data_level = "+ "'" + "6" + "'"+ "and parent_Ref = "+ "'" + aID + "'";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level7listLEGAL.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(temp.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level7listLEGAL);
            if (level7listLEGAL.size() > 0) {
                strLevel7ArrayLEGAL = new String[level7listLEGAL.size()];
                for (int i = 0; i < level7listLEGAL.size(); i++) {
                    strLevel7ArrayLEGAL[i] = level7listLEGAL.get(i);
                }
            }
            if (level7listLEGAL != null && level7listLEGAL.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLevel7ArrayLEGAL) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = null;
                        // If this is the initial dummy entry, make it hidden
                        if (position == 0) {
                            TextView tv = new TextView(getContext());
                            tv.setHeight(0);
                            tv.setVisibility(View.GONE);
                            v = tv;
                        } else {
                            // Pass convertView as null to prevent reuse of special case views
                            v = super.getDropDownView(position, null, parent);
                        }
                        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                        parent.setVerticalScrollBarEnabled(false);
                        return v;
                    }
                };

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private List<UploadModel> setUploadModelList() {
        List<UploadModel> uploadModels = new ArrayList<>();
        uploadModel = getUploadModel();
        uploadModels.add(uploadModel);
        return uploadModels;
    }

    private UploadModel getUploadModel() {
        uploadModel.setLevel2(txtL2.getText().toString());
        uploadModel.setLevel3(txtL3.getText().toString());
        uploadModel.setLevel4(txtL4.getText().toString());
        uploadModel.setLevel5(txtL5.getText().toString());
        uploadModel.setLevel6(txtL6.getText().toString());
        uploadModel.setLevel7(txtL7.getText().toString());
        uploadModel.setFileName(fileName);
        valLevel2 = "";
        valLevel3 = "";
        valLevel4 = "";
        valLevel5 = "";
        valLevel6 = "";
        valLevel7 = "";
        return uploadModel;
    }*/

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }

    private void fetchLegalEntity() {
        listLegal = new ArrayList<>();
        String where = " where parent_Ref = " + "'" + "0" + "'";
        String text = "text";
        Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Legal_Entity, where);
        if (cursor1 != null && cursor1.getCount() > 0) {
            cursor1.moveToFirst();
            do {
                legalEntityModel = new LegalEntityModel();
                legalEntityModel.setId(cursor1.getString(cursor1.getColumnIndex("id")));
                legalEntityModel.setText(cursor1.getString(cursor1.getColumnIndex("text")));
                legalEntityModel.setValue(cursor1.getString(cursor1.getColumnIndex("value")));
                legalEntityModel.setParent_Ref(cursor1.getString(cursor1.getColumnIndex("parent_Ref")));
                listLegal.add(legalEntityModel);
            } while (cursor1.moveToNext());
            cursor1.close();
        }

        listLegalAll = new ArrayList<>();
        Cursor cursorAll = KHIL.dbCon.fetchAlldata(DbHelper.M_Legal_Entity);
        if (cursorAll != null && cursorAll.getCount() > 0) {
            cursorAll.moveToFirst();
            do {
                legalEntityModel = new LegalEntityModel();
                legalEntityModel.setId(cursorAll.getString(cursorAll.getColumnIndex("id")));
                legalEntityModel.setText(cursorAll.getString(cursorAll.getColumnIndex("text")));
                legalEntityModel.setValue(cursorAll.getString(cursorAll.getColumnIndex("value")));
                legalEntityModel.setParent_Ref(cursorAll.getString(cursorAll.getColumnIndex("parent_Ref")));

                listLegalAll.add(legalEntityModel);
            } while (cursorAll.moveToNext());
            cursorAll.close();
        }



        listYear = new ArrayList<>();
        String year = "";
        Cursor cursor2 = KHIL.dbCon.fetchFromSelectDistinct(text,DbHelper.M_Year);
        if (cursor2 != null && cursor2.getCount() > 0) {
            cursor2.moveToFirst();
            do {
                year = cursor2.getString(cursor2.getColumnIndex("text"));
                listYear.add(year);
            } while (cursor2.moveToNext());
            cursor2.close();
        }

        listQuarter = new ArrayList<>();
        String quater = "";
        Cursor cursor3 = KHIL.dbCon.fetchFromSelectDistinct(text,DbHelper.M_Quater);
        if (cursor3 != null && cursor3.getCount() > 0) {
            cursor3.moveToFirst();
            do {
                quater = cursor3.getString(cursor3.getColumnIndex("text"));
                listQuarter.add(quater);
            } while (cursor3.moveToNext());
            cursor3.close();
        }



    }

    private void FetchLeveldata(){
        listLevelData = new ArrayList<>();
        String where = " where role_ID = " + "'" + roleID + "'";
        Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data, where);
        if (cursor1 != null && cursor1.getCount() > 0) {
            cursor1.moveToFirst();
            do {
                leveldataModel = new LeveldataModel();
                leveldataModel.setId(cursor1.getString(cursor1.getColumnIndex("id")));
                leveldataModel.setValue(cursor1.getString(cursor1.getColumnIndex("value")));
                leveldataModel.setText(cursor1.getString(cursor1.getColumnIndex("text")));
                leveldataModel.setParentRef(cursor1.getString(cursor1.getColumnIndex("parent_Ref")));
                leveldataModel.setaID(cursor1.getString(cursor1.getColumnIndex("aID")));
                leveldataModel.setRoleID(cursor1.getString(cursor1.getColumnIndex("role_ID")));
                leveldataModel.setDataLevel(cursor1.getString(cursor1.getColumnIndex("data_level")));
                leveldataModel.setQuaterId(cursor1.getString(cursor1.getColumnIndex("quater_Id")));
                listLevelData.add(leveldataModel);
            } while (cursor1.moveToNext());
            cursor1.close();
        }
    }

    public int getAllSubmitData() {
        Cursor cursor = null;
        int size = 0;

        submitDatalist = new ArrayList<>();
        submitDatalist.clear();
        try {
            // WHERE clause
            String where = " where last_sync = '0'"+" and user_id = '" + loginId + "'";

            cursor = KHIL.dbCon.fetchFromSelect(DbHelper.TABLE_UPLOAD, where);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    uploadModel = createUploadModel(cursor);
                    submitDatalist.add(uploadModel);
                } while(cursor.moveToNext());
                cursor.close();
                size = submitDatalist.size();
            } else {
                size = 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    private SoapPrimitive SyncAllData(){
        SoapPrimitive objLead = null;
        SoapPrimitive object = null;

        SOAPWebservice webService = new SOAPWebservice(getActivity());
        for (int k =0;k<submitDatalist.size();k++){
            uploadModel = submitDatalist.get(k);
            objLead = webService.Insert_upload(uploadModel.getRoleID(),uploadModel.getLegalEntityID(),uploadModel.getPropertyID(),
                    uploadModel.getLocationID(),uploadModel.getDoc_no(),uploadModel.getLoginId(), uploadModel.getLoginId(),uploadModel.getDate(),uploadModel.getDate(),
                    uploadModel.getIndividualID(),uploadModel.getNewProposal(),"",uploadModel.getTxtL2Id(),uploadModel.getTxtL3Id(),
                    uploadModel.getTxtL4Id(),uploadModel.getTxtL5Id(),uploadModel.getTxtL6Id(),uploadModel.getTxtL7Id(),
                    uploadModel.getYear(),uploadModel.getQuarter(),uploadModel.getMonth(),uploadModel.getFileUp(),uploadModel.getFilePath(),
                    uploadModel.getFilePath()+uploadModel.getFileUp(), uploadModel.getFileExtension());
            String id =  String.valueOf(objLead);
            if (id.equalsIgnoreCase("null") || id.equals(null) || id.contains("ERROR")){
                String strLastSync = "0";
                String selection = "file = ?";
                String[] selectionArgs = {uploadModel.getFileUp()};
                String valuesArray[] = {strLastSync};
                String[] columnNames = {"last_sync"};
                boolean result = KHIL.dbCon.updateBulk(DbHelper.TABLE_UPLOAD, selection, valuesArray, columnNames, selectionArgs);
                if (result) {
                    System.out.println("Upload details are added");
                } else {
                    System.out.println("FAILURE..!!");
                }
            }else {
                String strLastSync = "1";
                String selection = "file = ?";

                String[] selectionArgs = {uploadModel.getFileUp()};
                String valuesArray[] = {id,strLastSync};
                String[] columnNames = {"trans_id","last_sync"};
                boolean result = KHIL.dbCon.updateBulk(DbHelper.TABLE_UPLOAD, selection, valuesArray, columnNames, selectionArgs);
                if (result) {
                    System.out.println("Upload details are added");
                } else {
                    System.out.println("FAILURE..!!");
                }
            }
        }


        return objLead;
    }
    private class SaveInsertUpdate extends AsyncTask<Void, Void, SoapPrimitive> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress.setMessage("Please wait ...");
//            progress.show();
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            SoapPrimitive soapObject = null;

            SoapPrimitive object = SyncAllData();

            return soapObject;
        }
        @Override
        protected void onPostExecute(SoapPrimitive soapObject) {
            super.onPostExecute(soapObject);
//            progress.dismiss();


        }
    }
}

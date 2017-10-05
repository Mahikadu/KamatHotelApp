package com.example.admin.kamathotelapp.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.kamathotelapp.Adapters.DashboardAdapter;
import com.example.admin.kamathotelapp.Adapters.QC1Adapter;
import com.example.admin.kamathotelapp.Adapters.QC1UploadAdapter;
import com.example.admin.kamathotelapp.Adapters.UploadAdapter;
import com.example.admin.kamathotelapp.KHIL;
import com.example.admin.kamathotelapp.MainActivity;
import com.example.admin.kamathotelapp.Model.LegalEntityModel;
import com.example.admin.kamathotelapp.Model.LeveldataModel;
import com.example.admin.kamathotelapp.Model.LocationModel;
import com.example.admin.kamathotelapp.Model.MonthModel;
import com.example.admin.kamathotelapp.Model.PropertyModel;
import com.example.admin.kamathotelapp.Model.QC1Model;
import com.example.admin.kamathotelapp.Model.QuaterModel;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.Model.YearModel;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.Utils.Utils;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;
import com.example.admin.kamathotelapp.libs.SOAPWebservice;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import org.ksoap2.serialization.SoapPrimitive;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.gauriinfotech.commons.Commons;

import static com.example.admin.kamathotelapp.Fragments.UploadFragment.setListViewHeightBasedOnItems;


public class QC1 extends Fragment {

    public String legalEntityID,propertyID,individualID,locationID;
    public String legalEntityValue,propertyValue,individualValue,locationValue;

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    List<String> imagesUri;
    String imageFilename, imagePath;
    Image pickimage;
    ProgressDialog progress;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;

    private boolean qc1lev2 = false;
    private boolean qc1lev3 = false;
    private boolean qc1lev4 = false;
    private boolean qc1lev5 = false;
    private boolean qc1lev6 = false;
    private boolean qc1lev7 = false;
    private boolean qc1legalEntity = false;
    private boolean qc1property = false;
    private boolean qc1month = false;
    private boolean qc1quarter = false;
    private boolean qc1year = false;
    private boolean qc1loc = false;


    private String value, text, parent_Ref, updated_date,date,roleId,property_id,Inv_count,status,Created_By,Created_Date,
            Document_No,File_Exten,File_Name,File_Path,File_Path_File_Name,Id,Is_Download,Is_Edit,Is_View,Legal_Entity_Id,
            Level2_Id,Level3_Id,Level4_Id,Level5_Id,Level6_Id,Level7_Id,Location_Id,Month,Property_Id,Quarter,Role_Id,
            Status,Year,type,yearvalue,quartervalue,monthvalue,Legal_Entity_value,Level2_value,
            Level3_value,Level4_value,Level5_value,Level6_value,Level7_value,Location_value,Property_value,
            yeartext,quartertext,monthtext,Legal_Entity_text,Level2_text,Level3_text,Level4_text,Level5_text,
            Level6_text,Level7_text,Location_text,Property_text,TransID="";

    private Utils utils;

    String filePath;
    public String fileName="";
    public String fileExtension = "";
    private String strLegalEntity,strProperty,strMonth,strYear,strQuarter,strLoc;
    private String legalEntityString,propertyString,monthString,yearString,quarterString="",locString;
    public static CardView cardlevel2, cardlevel3,cardlevel4,cardlevel5,cardlevel6,cardlevel7,cardNewProposal,cardIndividuals,cardProperty;
    public static TextInputLayout level2txtlayout, level3txtlayout,level4txtlayout,level5txtlayout,level6txtlayout,level7txtlayout;
    public TextView headLev2, headLev3, headLev4, headLev5, headLev6, viewFile;// headLev7,
    private String valLevel2="", valLevel3="", valLevel4="", valLevel5="", valLevel6="", valLevel7="";
    public String txtL2Id,txtL3Id,txtL4Id,txtL5Id,txtL6Id,txtL7Id,monthID,yearID,quaterID;
    public String valuel2,valuel3,valuel4,valuel5,valuel6,valuel7;

    private String legalEntity, individuals = "", newProposal = "", property, year, quarter, month, location, file = "", level2 = "", level3 = "", level4 = "", level5 = "", level6 = "", level7 = "";


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

    private List<String> level2list,level3list,level4list,level5list,level6list;
    private List<String> level4listHR, level5listHR, level6listHR;
    private List<String> level4listCMD, level5listCMD, level6listCMD, level7listCMD;
    private List<String> level2listCS,level3listCS,level4listCS;
    private List<String> level4listMAR, level5listMAR, level6listMAR, level7listMAR;
    private List<String> level3listPER, level4listPER, level5listPER;
    private List<String> level4listLEGAL, level5listLEGAL, level6listLEGAL, level7listLEGAL;

    String manufactures = android.os.Build.MANUFACTURER;
    private static final int PICKFILE_RESULT_CODE = 1;
    private ArrayList<String> listIndividuals;
    private ArrayList<YearModel> listYear;
    YearModel yearModel;
    private ArrayList<QuaterModel> listQuarter;
    QuaterModel quaterModel;
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
    public static LinearLayout qc1layout_edit,choosefilelayout;



    ListView listqc;
    QC1Adapter qc1Adapter;
    QC1Model qc1Model;
    private ArrayList<QC1Model> listQC1;
    private SharedPref sharedPref;
    public String loginId, password,roleID;
    LinearLayout qc1datalayout,qclayout;
    boolean clickflag = false;
    Integer recordnumber;

    @InjectView(R.id.qc1LegalEntity)
    AutoCompleteTextView autoLegalEntity;
    @InjectView(R.id.qc1Individuals)
    AutoCompleteTextView autoIndividuals;
    @InjectView(R.id.qc1etNewProposal)
    EditText etNewProposal;
    @InjectView(R.id.qc1Property)
    AutoCompleteTextView autoProperty;
    @InjectView(R.id.qc1Month)
    AutoCompleteTextView autoMonth;
    @InjectView(R.id.qc1Year)
    AutoCompleteTextView autoYear;
    @InjectView(R.id.qc1Quarter)
    AutoCompleteTextView autoQuarter;
    @InjectView(R.id.qc1Loc)
    AutoCompleteTextView autoLoc;
    @InjectView(R.id.qc1btnFile)
    Button btnAttach;
    @InjectView(R.id.qc1btnUpdate)
    Button btnUpdate;
    @InjectView(R.id.qc1btnCancel)
    Button btnCancel;
    @InjectView(R.id.qc1btnApprove)
    Button btnApprove;
    @InjectView(R.id.qcbtnReject)
    Button btnReject;

    public static Button btnAdd;
    public static int QID = 0;
    public static int ID = 0;

    public static AutoCompleteTextView qc1txtL2,qc1txtL3,qc1txtL4,qc1txtL5,qc1txtL6,qc1txtL7;
    public static TextView qc1txtNoFile;
    public static TextView qc1no_files;
    public static ListView qc1listUpload;

    List<QC1Model> qc1uploadModelList;
    private QC1UploadAdapter adapter;

    public static QC1 newInstance() {
        QC1 fragment = new QC1();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getActivity().setTitle("QC1");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qc1, container, false);
        ButterKnife.inject(this, view);
        initView(view);
        progress = new ProgressDialog(getContext());
        return view;
    }

    public void initView(View view) {
        utils = new Utils(getContext());
        sharedPref = new SharedPref(getContext());
        roleID = sharedPref.getRoleID();
        loginId = sharedPref.getLoginId();
        password = sharedPref.getPassword();
        listqc = (ListView) view.findViewById(R.id.listqc);
        qc1datalayout = (LinearLayout) view.findViewById(R.id.qc1datalayout);
        qclayout = (LinearLayout) view.findViewById(R.id.qclayout);
        btnAdd = (Button) view.findViewById(R.id.qc1btnAdd);
        qc1layout_edit = (LinearLayout) view.findViewById(R.id.qc1layout_edit);

        //////Level data textview
        qc1txtL2 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level2);
        qc1txtL3 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level3);
        qc1txtL4 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level4);
        qc1txtL5 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level5);
        qc1txtL6 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level6);
        qc1txtL7 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level7);
        choosefilelayout = (LinearLayout)view.findViewById(R.id.choosefilelayout);
        qc1txtNoFile = (TextView) view.findViewById(R.id.qc1txtNofile);
        qc1no_files = (TextView) view.findViewById(R.id.qc1no_files);

        cardNewProposal = (CardView) view.findViewById(R.id.qc1newProposalcardview);
        cardIndividuals = (CardView) view.findViewById(R.id.qc1individualscardview);
        cardProperty = (CardView) view.findViewById(R.id.qc1propertycardview);
        cardlevel2 = (CardView) view.findViewById(R.id.qc1level2cardview);
        cardlevel3 = (CardView) view.findViewById(R.id.qc1level3cardview);
        cardlevel4 = (CardView) view.findViewById(R.id.qc1level4cardview);
        cardlevel5 = (CardView) view.findViewById(R.id.qc1level5cardview);
        cardlevel6 = (CardView) view.findViewById(R.id.qc1level6cardview);
        cardlevel7 = (CardView) view.findViewById(R.id.qc1level7cardview);


        level2txtlayout = (TextInputLayout) view.findViewById(R.id.qc1level2txtlayout);
        level3txtlayout = (TextInputLayout) view.findViewById(R.id.qc1level3txtlayout);
        level4txtlayout = (TextInputLayout) view.findViewById(R.id.qc1level4txtlayout);
        level5txtlayout = (TextInputLayout) view.findViewById(R.id.qc1level5txtlayout);
        level6txtlayout = (TextInputLayout) view.findViewById(R.id.qc1level6txtlayout);
        level7txtlayout = (TextInputLayout) view.findViewById(R.id.qc1level7txtlayout);

        headLev2 = (TextView) view.findViewById(R.id.qc1level2);
        headLev3 = (TextView) view.findViewById(R.id.qc1level3);
        headLev4 = (TextView) view.findViewById(R.id.qc1level4);
        headLev5 = (TextView) view.findViewById(R.id.qc1level5);
        headLev6 = (TextView) view.findViewById(R.id.qc1level6);
        viewFile = (TextView) view.findViewById(R.id.qc1txtViewFile);

        qc1listUpload = (ListView) view.findViewById(R.id.qc1listViewUpload);
        qc1Model = new QC1Model();

        fetchQC1Details(roleID);
        qc1uploadModelList = new ArrayList<>();
        Bundle bundle = getArguments();

        if (bundle != null) {
            try {
                clickflag = bundle.getBoolean("Click");
//            qc1Model = (QC1Model) bundle.getSerializable("QC1");
                recordnumber = bundle.getInt("position");
                qc1uploadModelList = bundle.getParcelableArrayList("QC1_QIDlist");

                qc1Model = qc1uploadModelList.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (clickflag) {

            listqc.setVisibility(View.GONE);
            qclayout.setVisibility(View.GONE);
            qc1datalayout.setVisibility(View.VISIBLE);

            autoLegalEntity.setText(qc1Model.getLegal_Entity_text());
            autoProperty.setText(qc1Model.getProperty_text());
            autoMonth.setText(qc1Model.getMonthtext());
            autoYear.setText(qc1Model.getYeartext());
            autoQuarter.setText(qc1Model.getQuartertext());
            autoLoc.setText(qc1Model.getLocation_text());

            qc1no_files.setVisibility(View.GONE);
            qc1listUpload.setVisibility(View.VISIBLE);

            //qc1uploadModelList.add(qc1Model);
            setValues(qc1uploadModelList);
        }


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
                    for (int i = 0; i < listLegal.size(); i++) {
                        legalEntityModel = listLegal.get(i);
                        String entity = legalEntityModel.getText();
                        if (entity.equalsIgnoreCase(legalEntityString)) {
                            ID = legalEntityModel.getId();
                            legalEntityID = ID;
                            legalEntityValue = legalEntityModel.getValue();
                            qc1legalEntity = true;
                        }
                    }
                    autoProperty.setText("");
                    autoIndividuals.setText("");
                    autoLoc.setText("");
//                    long positionId = id+1;
                    if (strLegalEntity.equalsIgnoreCase("Individuals")) {

                        cardIndividuals.setVisibility(View.VISIBLE);
                        cardNewProposal.setVisibility(View.GONE);
                        cardProperty.setVisibility(View.VISIBLE);

                        listIndividuals = new ArrayList<>();
                        String individuals = "";
                        String where1 = " where parent_Ref = " + "'" + legalEntityID + "'";
                        String text = "text";
                        Cursor cursor = KHIL.dbCon.fetchFromSelectDistinctWhere(text, DbHelper.M_Legal_Entity, where1);
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
                            String eid = "", parentref;

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String individual = parent.getItemAtPosition(position).toString();
                                autoProperty.setText("");
                                autoLoc.setText("");

                                for (int i = 0; i < listLegalAll.size(); i++) {
                                    legalEntityModel = listLegalAll.get(i);
                                    String text = legalEntityModel.getText();
                                    if (text.equalsIgnoreCase(individual)) {
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
                                                            qc1property = true;

                                                            listLoc = new ArrayList<>();
                                                            String location = "";
                                                            String where1 = " where property_id = " + "'" + propertyID + "'";
                                                            Cursor cursor4 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Location, where1);
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
                                                                                locationID = locationModel.getId();
                                                                                locationValue = locationModel.getValue();
                                                                                qc1loc = true;
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

                    } else if (strLegalEntity.equalsIgnoreCase("New Proposal")) {
                        cardNewProposal.setVisibility(View.VISIBLE);
                        cardIndividuals.setVisibility(View.GONE);
                        cardProperty.setVisibility(View.VISIBLE);
                    } else {
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
                                            qc1property = true;


                                            listLoc = new ArrayList<>();
                                            String location = "";
                                            String where1 = " where property_id = " + "'" + propertyID + "'";
                                            Cursor cursor4 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Location, where1);
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
                                                                locationID = locationModel.getId();
                                                                locationValue = locationModel.getValue();
                                                                qc1loc = true;
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


        if (listYear != null) {
            if (listYear.size() > 0) {
                strYearArray = new String[listYear.size()];
                //   strLeadArray[0] = "Select Source Lead";
                for (int i = 0; i < listYear.size(); i++) {
                    yearModel = listYear.get(i);
                    strYearArray[i] = yearModel.getText();
                }
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
                    for (int i = 0; i < listYear.size(); i++) {
                        yearModel = listYear.get(i);
                        String text = yearModel.getText();
                        if (text.equalsIgnoreCase(yearString)) {
                            yearID = yearModel.getValue();
                            qc1year = true;
                        }
                    }
                }

            }
        });
        ///////////////////////////////
        ///////////////Details of Quarter
        if (listQuarter.size() > 0) {
            strQuarterArray = new String[listQuarter.size()];
            for (int i = 0; i < listQuarter.size(); i++) {
                quaterModel = listQuarter.get(i);
                strQuarterArray[i] = quaterModel.getText();
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
                    for (int i = 0; i < listQuarter.size(); i++) {
                        quaterModel = listQuarter.get(i);
                        String text = quaterModel.getText();
                        if (text.equalsIgnoreCase(quarterString)) {
                            quaterID = quaterModel.getValue();
                            qc1quarter = true;
                        }
                    }
                }
                listMonth = new ArrayList<>();
                String month = "";
                String where = " where quater_id = " + "'" + quarterString + "'";
                Cursor cursor3 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Month, where);
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
                            monthModel = listMonth.get(i);
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
                                    qc1month = true;
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
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                Intent intent;
                if (manufactures.equalsIgnoreCase("samsung")) {
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


        ///////////////////////// Level data set to Autocompletetextview

        if (loginId.equalsIgnoreCase("finance") || loginId.equalsIgnoreCase("financeqc1") && password.equalsIgnoreCase("password")) {
            qc1txtL2.setHint("Financial Type");
            fetchLevel2dataFin();

            qc1txtL2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel3.setVisibility(View.GONE);
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);

                    qc1txtL3.setHint("");
                    qc1txtL3.setText("");
                    qc1txtL4.setText("");
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel3 = "";
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    qc1txtL2.showDropDown();
                    return false;
                }
            });

            qc1txtL2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level2txtlayout.setHint("Financial Type");
                    if (strLevel2Array != null && strLevel2Array.length > 0) {
                        String aid = "", parentref = "";
                        valLevel2 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel2)) {
                                aid = leveldataModel.getaID();
                                txtL2Id = aid;
                                valuel2 = leveldataModel.getValue();

                            }
                        }
                        if (valLevel2 != null && valLevel2.length() > 0) {
                            fetchLevel3dataFin(aid);
                            level3txtlayout.setHint(valLevel2);
                            cardlevel3.setVisibility(View.VISIBLE);
                            qc1txtL3.setVisibility(View.VISIBLE);
                            qc1lev3 = true;
                        }else {
                            cardlevel3.setVisibility(View.GONE);
                            qc1lev3 = false;
                        }
                    }
                }
            });

            ///////////////////////////////////////
            //////////////////Level 3 details

            qc1txtL3.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    qc1txtL3.showDropDown();
                    return false;
                }
            });

            qc1txtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel3Array != null && strLevel3Array.length > 0) {
                        String aid = "", parentref = "";
                        valLevel3 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel3)) {
                                aid = leveldataModel.getaID();
                                txtL3Id = aid;
                                valuel3 = leveldataModel.getValue();
                            }
                        }
                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4data(aid);
                            if (level4list.size() > 0) {
                                cardlevel4.setVisibility(View.VISIBLE);
                                level4txtlayout.setHint(valLevel3);
                                qc1lev4 = true;
                            } else {
                                cardlevel4.setVisibility(View.GONE);
                                qc1lev4 = false;
                            }
                        }
                    }
                }
            });

            ///////////////////////////////////////

            //////////////////Level 4 details

            qc1txtL4.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    qc1txtL4.showDropDown();
                    return false;
                }
            });

            qc1txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel4Array != null && strLevel4Array.length > 0) {
                        String aid = "", parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = aid;
                                valuel4 = leveldataModel.getValue();
                            }
                        }
                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5data(aid);
                            if (level5list.size() > 0) {
                                level5txtlayout.setHint(valLevel4);
                                cardlevel5.setVisibility(View.VISIBLE);
                                qc1lev5 = true;
                            } else {
                                cardlevel5.setVisibility(View.GONE);
                                qc1lev5 = false;
                            }
                        }

                    }
                }
            });

            ///////////////////////////////////////
            //////////////////Level 5 details

            qc1txtL5.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    qc1txtL5.showDropDown();
                    return false;
                }
            });

            qc1txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5Array != null && strLevel5Array.length > 0) {
                        String aid = "", parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = aid;
                                valuel5 = leveldataModel.getValue();
                            }
                        }
                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6data(aid);
                            if (level6list.size() > 0) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                                qc1lev6 = true;
                            } else {
                                cardlevel6.setVisibility(View.GONE);
                                qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            ///////////////////////////////////////

            //////////////////Level 6 details

            qc1txtL6.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                    valLevel7 = "";
                    qc1txtL6.showDropDown();
                    return false;
                }
            });

            qc1txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6Array != null && strLevel6Array.length > 0) {
                        String aid = "";
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = aid;
                                valuel6 = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        } else if (loginId.equalsIgnoreCase("hr") || loginId.equalsIgnoreCase("hrqc1") && password.equalsIgnoreCase("password")) {

            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);
            cardlevel4.setVisibility(View.VISIBLE);
            qc1txtL4.setHint("HR");

            fetchLevel4dataHR();

            qc1txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL4.showDropDown();
                    return false;
                }
            });

            qc1txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level4txtlayout.setHint("HR");
                    if (strLevel4ArrayHR != null && strLevel4ArrayHR.length > 0) {
                        String aid = "", parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = aid;
                                valuel4 = leveldataModel.getValue();
                            }
                        }
                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataHR(aid);
                            level5txtlayout.setHint(valLevel4);
                            cardlevel5.setVisibility(View.VISIBLE);
                            qc1lev5 = true;
                        }else {
                            cardlevel5.setVisibility(View.GONE);
                            qc1lev5 = false;
                        }
                    }
                }
            });

            qc1txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL5.showDropDown();
                    return false;
                }
            });

            qc1txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayHR != null && strLevel5ArrayHR.length > 0) {
                        String aid = "", parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = aid;
                                valuel5 = leveldataModel.getValue();
                            }
                        }
                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataHR(aid);
                            if (level6listHR.size() > 0) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                                qc1lev6 = true;
                            } else {
                                cardlevel6.setVisibility(View.GONE);
                                qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                    valLevel7 = "";
                    qc1txtL6.showDropDown();
                    return false;
                }
            });

            qc1txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayHR != null && strLevel6ArrayHR.length > 0) {
                        String aid;
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = aid;
                                valuel6 = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        } else if (loginId.equalsIgnoreCase("cmd") || loginId.equalsIgnoreCase("cmdqc1") && password.equalsIgnoreCase("password")) {

            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            cardlevel4.setVisibility(View.VISIBLE);
            qc1txtL4.setHint("Type");

            fetchLevel4dataCMD();

            qc1txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL4.showDropDown();
                    return false;
                }
            });

            qc1txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level4txtlayout.setHint("CMD");
                    if (strLevel4ArrayCMD != null && strLevel4ArrayCMD.length > 0) {
                        String aid = "", parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = aid;
                                valuel4 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataCMD(aid);
                            if (level5listCMD.size() > 0) {
                                level5txtlayout.setHint(valLevel4);
                                cardlevel5.setVisibility(View.VISIBLE);
                                qc1lev5 = true;
                            } else {
                                cardlevel5.setVisibility(View.GONE);
                                qc1lev5 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL5.showDropDown();
                    return false;
                }
            });

            qc1txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayCMD != null && strLevel5ArrayCMD.length > 0) {
                        String aid = "", parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = aid;
                                valuel5 = leveldataModel.getValue();
                            }
                        }
                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataCMD(aid);
                            if (level6listCMD.size() > 0) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                                qc1lev6 = true;
                            } else {
                                cardlevel6.setVisibility(View.GONE);
                                qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                    valLevel7 = "";
                    qc1txtL6.showDropDown();
                    return false;
                }
            });

            qc1txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayCMD != null && strLevel6ArrayCMD.length > 0) {
                        String aid = "", parentref = "";
                        valLevel6 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = aid;
                                valuel6 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataCMD(aid);
                            if (level7listCMD.size() > 0) {
                                level7txtlayout.setHint(valLevel5);
                                cardlevel7.setVisibility(View.VISIBLE);
                                qc1lev7 = true;
                            } else {
                                cardlevel7.setVisibility(View.GONE);
                                qc1lev7 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    qc1txtL7.showDropDown();
                    return false;
                }
            });

            qc1txtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel7ArrayCMD != null && strLevel7ArrayCMD.length > 0) {
                        String aid = "";
                        valLevel7 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel7)) {
                                aid = leveldataModel.getaID();
                                txtL7Id = aid;
                                valuel7 = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        } else if (loginId.equalsIgnoreCase("cs") || loginId.equalsIgnoreCase("csqc1") && password.equalsIgnoreCase("password")) {

            fetchLevel2dataCS();
            qc1txtL2.setHint("Type");

            qc1txtL2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel3.setVisibility(View.GONE);
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL3.setText("");
                    qc1txtL4.setText("");
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel3 = "";
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    qc1txtL2.showDropDown();
                    return false;
                }
            });

            qc1txtL2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level2txtlayout.setHint("Type");
                    if (strLevel2ArrayCS != null && strLevel2ArrayCS.length > 0) {
                        String aid = "", parentref = "";
                        valLevel2 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel2)) {
                                aid = leveldataModel.getaID();
                                txtL2Id = aid;
                                valuel2 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel2 != null && valLevel2.length() > 0) {
                            fetchLevel3dataCS(aid);
                            level3txtlayout.setHint(valLevel2);
                            cardlevel3.setVisibility(View.VISIBLE);
                            qc1lev3 = true;
                        }else {
                            cardlevel3.setVisibility(View.GONE);
                            qc1lev3 = false;
                        }
                    }
                }
            });

            qc1txtL3.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    qc1txtL3.showDropDown();
                    return false;
                }
            });

            qc1txtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel3ArrayCS != null && strLevel3ArrayCS.length > 0) {
                        String aid = "", parentref = "";
                        valLevel3 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel3)) {
                                aid = leveldataModel.getaID();
                                txtL3Id = aid;
                                valuel3 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4dataCS(aid);
                            if (level4listCS.size() > 0) {
                                cardlevel4.setVisibility(View.VISIBLE);
                                level4txtlayout.setHint(valLevel3);
                                qc1lev4 = true;
                            } else {
                                cardlevel4.setVisibility(View.GONE);
                                qc1lev4 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    qc1txtL4.showDropDown();
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    return false;
                }
            });

            qc1txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel4ArrayCS != null && strLevel4ArrayCS.length > 0) {
                        String aid = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = aid;
                                valuel4 = leveldataModel.getValue();
                            }
                        }

                    }
                }
            });
        } else if (loginId.equalsIgnoreCase("marketing") || loginId.equalsIgnoreCase("marketingqc1") && password.equalsIgnoreCase("password")) {

            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            cardlevel4.setVisibility(View.VISIBLE);
            qc1txtL4.setHint("Occupancy");
            fetchLevel4dataMAR();

            qc1txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL4.showDropDown();
                    return false;
                }
            });

            qc1txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level4txtlayout.setHint("Occupancy");
                    if (strLevel4ArrayMAR != null && strLevel4ArrayMAR.length > 0) {
                        String aid = "", parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = aid;
                                valuel4 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataMAR(aid);
                            if (level5listMAR.size() > 0) {
                                level5txtlayout.setHint(valLevel4);
                                cardlevel5.setVisibility(View.VISIBLE);
                                qc1lev5 = true;
                            } else {
                                cardlevel5.setVisibility(View.GONE);
                                qc1lev5 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    qc1txtL5.showDropDown();
                    return false;
                }
            });

            qc1txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayMAR != null && strLevel5ArrayMAR.length > 0) {
                        String aid = "", parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = aid;
                                valuel5 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataMAR(aid);
                            if (level6listMAR.size() > 0) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                                qc1lev6 = true;
                            } else {
                                cardlevel6.setVisibility(View.GONE);
                                qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                    valLevel7 = "";
                    qc1txtL6.showDropDown();
                    return false;
                }
            });

            qc1txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayMAR != null && strLevel6ArrayMAR.length > 0) {
                        String aid = "", parentref = "";
                        valLevel6 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = aid;
                                valuel6 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataMAR(aid);
                            if (level7listMAR.size() > 0) {
                                level7txtlayout.setHint(valLevel6);
                                cardlevel7.setVisibility(View.VISIBLE);
                                qc1lev7 = true;
                            } else {
                                cardlevel7.setVisibility(View.GONE);
                                qc1lev7 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    qc1txtL7.showDropDown();
                    return false;
                }
            });

            qc1txtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel7ArrayMAR != null && strLevel7ArrayMAR.length > 0) {
                        String aid = "";
                        valLevel7 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel7)) {
                                aid = leveldataModel.getaID();
                                txtL7Id = aid;
                                valuel7 = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });

        } else if (loginId.equalsIgnoreCase("Personal") || loginId.equalsIgnoreCase("Personalqc1") && password.equalsIgnoreCase("password")) {

            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.VISIBLE);
            qc1txtL3.setHint("Type");

            fetchLevel3dataPER();

            qc1txtL3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel4.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    qc1txtL3.showDropDown();
                    return false;
                }
            });

            qc1txtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level3txtlayout.setHint("Type");
                    if (strLevel3ArrayPer != null && strLevel3ArrayPer.length > 0) {
                        String aid = "", parentref = "";
                        valLevel3 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel3)) {
                                aid = leveldataModel.getaID();
                                txtL3Id = aid;
                                valuel3 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4dataPER(aid);
                            if (level4listPER.size() > 0) {
                                cardlevel4.setVisibility(View.VISIBLE);
                                level4txtlayout.setHint(valLevel3);
                                qc1lev4 = true;
                            } else {
                                cardlevel4.setVisibility(View.GONE);
                                qc1lev4 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL4.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    qc1txtL4.showDropDown();
                    return false;
                }
            });

            qc1txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel4ArrayPer != null && strLevel4ArrayPer.length > 0) {
                        String aid = "", parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = aid;
                                valuel4 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataPER(aid);
                            if (level5listPER.size() > 0) {
                                level5txtlayout.setHint(valLevel4);
                                cardlevel5.setVisibility(View.VISIBLE);
                                qc1lev5 = true;
                            } else {
                                cardlevel5.setVisibility(View.GONE);
                                qc1lev5 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    qc1txtL5.showDropDown();
                    return false;
                }
            });

            qc1txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayPer != null && strLevel5ArrayPer.length > 0) {
                        String aid = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = aid;
                                valuel5 = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        } else if (loginId.equalsIgnoreCase("legal") || loginId.equalsIgnoreCase("legalqc1") && password.equalsIgnoreCase("password")) {

            cardlevel2.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            cardlevel4.setVisibility(View.VISIBLE);

            qc1txtL4.setHint("Legal");
            fetchLevel4dataLEGAL();

            qc1txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL4.showDropDown();
                    return false;
                }
            });

            qc1txtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    level4txtlayout.setHint("Legal");
                    if (strLevel4ArrayLEGAL != null && strLevel4ArrayLEGAL.length > 0) {
                        String aid = "", parentref = "";
                        valLevel4 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel4)) {
                                aid = leveldataModel.getaID();
                                txtL4Id = aid;
                                valuel4 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataLEGAL(aid);
                            level5txtlayout.setHint(valLevel4);
                            cardlevel5.setVisibility(View.VISIBLE);
                            qc1lev5 = true;
                        }else {
                            cardlevel6.setVisibility(View.GONE);
                            qc1lev5 = false;
                        }
                    }
                }
            });

            qc1txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel6.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    qc1txtL7.setText("");
                    qc1txtL5.showDropDown();
                    return false;
                }
            });

            qc1txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayLEGAL != null && strLevel5ArrayLEGAL.length > 0) {
                        String aid = "", parentref = "";
                        valLevel5 = parent.getItemAtPosition(position).toString();
                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel5)) {
                                aid = leveldataModel.getaID();
                                txtL5Id = aid;
                                valuel5 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataLEGAL(aid);
                            if (level6listLEGAL.size() > 0) {
                                level6txtlayout.setHint(valLevel5);
                                cardlevel6.setVisibility(View.VISIBLE);
                                qc1lev6 = true;
                            } else {
                                cardlevel6.setVisibility(View.GONE);
                                qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                    qc1txtL6.showDropDown();
                    return false;
                }
            });

            qc1txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayLEGAL != null && strLevel6ArrayLEGAL.length > 0) {
                        String aid = "", parentref = "";
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel6)) {
                                aid = leveldataModel.getaID();
                                txtL6Id = aid;
                                valuel6 = leveldataModel.getValue();
                            }
                        }

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataLEGAL(aid);
                            if (level7listLEGAL.size() > 0) {
                                level7txtlayout.setHint(valLevel6);
                                cardlevel7.setVisibility(View.VISIBLE);
                                qc1lev7 = true;
                            } else {
                                cardlevel7.setVisibility(View.GONE);
                                qc1lev7 = false;
                            }
                        }
                    }
                }
            });

            qc1txtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    qc1txtL7.showDropDown();
                    return false;
                }
            });

            qc1txtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel7ArrayLEGAL != null && strLevel7ArrayLEGAL.length > 0) {
                        String aid = "";
                        valLevel7 = parent.getItemAtPosition(position).toString();

                        for (int i = 0; i < listLevelData.size(); i++) {
                            leveldataModel = listLevelData.get(i);
                            String text = leveldataModel.getText();
                            if (text.equalsIgnoreCase(valLevel7)) {
                                aid = leveldataModel.getaID();
                                txtL7Id = aid;
                                valuel7 = leveldataModel.getValue();
                            }
                        }
                    }
                }
            });
        }

        ///////////////////////////////////Finish////////////////////


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                View focusView = null;
                autoLegalEntity.setError(null);
                autoProperty.setError(null);
                autoQuarter.setError(null);
                autoMonth.setError(null);
                autoLoc.setError(null);
                qc1txtL2.setError(null);
                qc1txtL3.setError(null);
                qc1txtL4.setError(null);
                qc1txtL5.setError(null);
                qc1txtL6.setError(null);
                qc1txtL7.setError(null);


                if (TextUtils.isEmpty(autoLegalEntity.getText().toString())) {
                    autoLegalEntity.setError("This field is required");
                    focusView = autoLegalEntity;
                    focusView.requestFocus();
                    autoLegalEntity.setFocusable(true);
                    autoLegalEntity.requestFocusFromTouch();
                    return;
                }
                if (TextUtils.isEmpty(autoProperty.getText().toString())) {
                    autoProperty.setError("This field is required");
                    focusView = autoProperty;
                    focusView.requestFocus();
                    autoProperty.setFocusable(true);
                    autoProperty.requestFocusFromTouch();
                    return;
                }
                if (TextUtils.isEmpty(autoYear.getText().toString())) {
                    autoYear.setError("This field is required");
                    focusView = autoYear;
                    focusView.requestFocus();
                    autoYear.setFocusable(true);
                    autoYear.requestFocusFromTouch();
                    return;
                }
                if (TextUtils.isEmpty(autoQuarter.getText().toString())) {
                    autoQuarter.setError("This field is required");
                    focusView = autoQuarter;
                    focusView.requestFocus();
                    autoQuarter.setFocusable(true);
                    autoQuarter.requestFocusFromTouch();
                    return;
                }
                if (TextUtils.isEmpty(autoMonth.getText().toString())) {
                    autoMonth.setError("This field is required");
                    focusView = autoMonth;
                    focusView.requestFocus();
                    autoMonth.setFocusable(true);
                    autoMonth.requestFocusFromTouch();
                    return;
                }
                if (TextUtils.isEmpty(autoLoc.getText().toString())) {
                    autoLoc.setError("This field is required");
                    focusView = autoLoc;
                    focusView.requestFocus();
                    autoLoc.setFocusable(true);
                    autoLoc.requestFocusFromTouch();
                    return;
                }
                if (!loginId.equalsIgnoreCase("cmd") && !loginId.equalsIgnoreCase("hr") &&
                        !loginId.equalsIgnoreCase("legal") && !loginId.equalsIgnoreCase("marketing") &&
                        !loginId.equalsIgnoreCase("personal" ) && !loginId.equalsIgnoreCase("cmdqc1") &&
                        !loginId.equalsIgnoreCase("hrqc1") && !loginId.equalsIgnoreCase("legalqc1") &&
                        loginId.equalsIgnoreCase("marketingqc1") && !loginId.equalsIgnoreCase("personalqc1")) {

                    if (TextUtils.isEmpty(qc1txtL2.getText().toString())) {
                        qc1txtL2.setFocusable(true);
                        qc1txtL2.setError("This field is required");
                        focusView = qc1txtL2;
                        focusView.requestFocus();
                        qc1txtL2.requestFocusFromTouch();
                        return;

                    }

                }
                if (!loginId.equalsIgnoreCase("cmd") && !loginId.equalsIgnoreCase("hr") &&
                        !loginId.equalsIgnoreCase("legal") && !loginId.equalsIgnoreCase("marketing") &&
                        !loginId.equalsIgnoreCase("cmdqc1") && !loginId.equalsIgnoreCase("hrqc1") &&
                        !loginId.equalsIgnoreCase("legalqc1") && !loginId.equalsIgnoreCase("marketingqc1")){

                    if (TextUtils.isEmpty(qc1txtL3.getText().toString())) {
                        qc1txtL3.setFocusable(true);
                        qc1txtL3.setError("This field is required");
                        focusView = qc1txtL3;
                        focusView.requestFocus();
                        qc1txtL3.requestFocusFromTouch();
                        return;
                    }

                }
                if (TextUtils.isEmpty(qc1txtL4.getText().toString())) {
                    if (qc1lev4) {
                        qc1txtL4.setFocusable(true);
                        qc1txtL4.setError("This field is required");
                        focusView = qc1txtL4;
                        focusView.requestFocus();
                        qc1txtL4.requestFocusFromTouch();
                        return;
                    }

                }
                if (TextUtils.isEmpty(qc1txtL5.getText().toString())) {
                    if (qc1lev5) {
                        qc1txtL5.setFocusable(true);
                        focusView = qc1txtL5;
                        focusView.requestFocus();
                        qc1txtL5.setError("This field is required");
                        qc1txtL5.requestFocusFromTouch();
                        return;
                    }

                }
                if (TextUtils.isEmpty(qc1txtL6.getText().toString())) {
                    if (qc1lev6) {
                        qc1txtL6.setFocusable(true);
                        focusView = qc1txtL6;
                        focusView.requestFocus();
                        qc1txtL6.setError("This field is required");
                        qc1txtL6.requestFocusFromTouch();
                        return;
                    }
                }
                if (TextUtils.isEmpty(qc1txtL7.getText().toString())) {
                    if (qc1lev7) {
                        qc1txtL7.setFocusable(true);
                        qc1txtL7.setError("This field is required");
                        focusView = qc1txtL7;
                        focusView.requestFocus();
                        qc1txtL7.requestFocusFromTouch();
                        return;
                    }
                }
                if (qc1txtNoFile.getText().toString().equalsIgnoreCase("No file chosen")) {
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
                    file = qc1txtNoFile.getText().toString();
                }
                qc1txtNoFile.setText("No file chosen");
                qc1no_files.setVisibility(View.GONE);
                qc1listUpload.setVisibility(View.VISIBLE);

                Created_By = sharedPref.getLoginId();
                Role_Id = sharedPref.getRoleID();
                Created_Date = qc1Model.getCreatedDate();
                TransID = qc1Model.getQ_Id();
                Is_Download = "True";
                Is_Edit = "True";
                Is_View = "True";

                if(!qc1legalEntity){
                    Legal_Entity_Id = qc1Model.getLegal_Entity_Id();
                    Legal_Entity_text = qc1Model.getLegal_Entity_text();
                    Legal_Entity_value = qc1Model.getLegal_Entity_value();
                }else {
                    Legal_Entity_Id = legalEntityID;
                    Legal_Entity_text = autoLegalEntity.getText().toString();
                    Legal_Entity_value = legalEntityValue;
                }

                if(!qc1property){
                    Property_Id = qc1Model.getProperty_Id();
                    Property_text = qc1Model.getProperty_text();
                    Property_value = qc1Model.getProperty_value();
                }else {
                    Property_Id = propertyID;
                    Property_text = autoProperty.getText().toString();
                    Property_value = propertyValue;
                }

                if(!qc1year){
                    Year = qc1Model.getYear();
                    yeartext = qc1Model.getYeartext();
                    yearvalue = qc1Model.getYearvalue();
                }else {
                    Year = yearID;
                    yeartext = autoYear.getText().toString();
                    yearvalue = yearID;
                }

                if(!qc1quarter){
                    Quarter = qc1Model.getQuarter();
                    quartertext = qc1Model.getQuartertext();
                    quartervalue = qc1Model.getQuartervalue();
                }else {
                    Quarter = quaterID;
                    quartertext = autoQuarter.getText().toString();
                    quartervalue = quaterID;
                }

                if(!qc1month){
                    Month = qc1Model.getMonth();
                    monthtext = qc1Model.getMonthtext();
                    monthvalue = qc1Model.getMonthvalue();
                }else {
                    Month = monthID;
                    monthtext = autoMonth.getText().toString();
                    monthvalue = monthID;
                }

                if(!qc1loc){
                    Location_Id = qc1Model.getLocation_Id();
                    Location_text = qc1Model.getLocation_text();
                    Location_value = qc1Model.getLocation_value();
                }else {
                    Location_Id = locationID;
                    Location_text = autoLoc.getText().toString();
                    Location_value = locationValue;
                }

//                individuals = autoIndividuals.getText().toString();
//                newProposal = etNewProposal.getText().toString();

                Level2_Id = txtL2Id;
                Level3_Id = txtL3Id;
                Level4_Id = txtL4Id;
                Level5_Id = txtL5Id;
                Level6_Id = txtL6Id;
                Level7_Id = txtL7Id;

                Level2_text = qc1txtL2.getText().toString();
                Level3_text = qc1txtL3.getText().toString();
                Level4_text = qc1txtL4.getText().toString();
                Level5_text = qc1txtL5.getText().toString();
                Level6_text = qc1txtL6.getText().toString();
                Level7_text = qc1txtL7.getText().toString();

                Level2_value = valuel2;
                Level3_value = valuel3;
                Level4_value = valuel4;
                Level5_value = valuel5;
                Level6_value = valuel6;
                Level7_value = valuel7;
                Status = "QC1Pending";
                type = "1";

                insertIntoUploadDb(1);

                if (loginId.equalsIgnoreCase("cmd") || loginId.equalsIgnoreCase("hr") ||
                        loginId.equalsIgnoreCase("legal") || loginId.equalsIgnoreCase("marketing") ||
                        loginId.equalsIgnoreCase("cmdqc1") || loginId.equalsIgnoreCase("hrqc1") ||
                        loginId.equalsIgnoreCase("legalqc1") || loginId.equalsIgnoreCase("marketingqc1")) {
                    qc1txtL4.setText("");
                    qc1txtL4.setHint("");

                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                } else if (loginId.equalsIgnoreCase("cs") || loginId.equalsIgnoreCase("finance") ||
                        loginId.equalsIgnoreCase("csqc1") || loginId.equalsIgnoreCase("financeqc1")) {
                    qc1txtL2.setText("");
                    qc1txtL2.setHint("");

                    cardlevel3.setVisibility(View.GONE);
                    qc1txtL3.setText("");
                    cardlevel4.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                } else if (loginId.equalsIgnoreCase("Personal")||
                        loginId.equalsIgnoreCase("Personalqc1")) {
                    qc1txtL3.setText("");
                    qc1txtL3.setHint("");

                    cardlevel4.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                file = qc1txtNoFile.getText().toString();
                choosefilelayout.setVisibility(View.VISIBLE);
                qc1txtNoFile.setText("No file chosen");
                qc1layout_edit.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);

                Created_By = sharedPref.getLoginId();
                Role_Id = sharedPref.getRoleID();
                Created_Date = qc1Model.getCreatedDate();
                TransID = qc1Model.getQ_Id();
                Is_Download = "True";
                Is_Edit = "True";
                Is_View = "True";
                if(!qc1legalEntity){
                    Legal_Entity_Id = qc1Model.getLegal_Entity_Id();
                    Legal_Entity_text = qc1Model.getLegal_Entity_text();
                    Legal_Entity_value = qc1Model.getLegal_Entity_value();
                }else {
                    Legal_Entity_Id = legalEntityID;
                    Legal_Entity_text = autoLegalEntity.getText().toString();
                    Legal_Entity_value = legalEntityValue;
                }

                if(!qc1property){
                    Property_Id = qc1Model.getProperty_Id();
                    Property_text = qc1Model.getProperty_text();
                    Property_value = qc1Model.getProperty_value();
                }else {
                    Property_Id = propertyID;
                    Property_text = autoProperty.getText().toString();
                    Property_value = propertyValue;
                }

                if(!qc1year){
                    Year = qc1Model.getYear();
                    yeartext = qc1Model.getYeartext();
                    yearvalue = qc1Model.getYearvalue();
                }else {
                    Year = yearID;
                    yeartext = autoYear.getText().toString();
                    yearvalue = yearID;
                }

                if(!qc1quarter){
                    Quarter = qc1Model.getQuarter();
                    quartertext = qc1Model.getQuartertext();
                    quartervalue = qc1Model.getQuartervalue();
                }else {
                    Quarter = quaterID;
                    quartertext = autoQuarter.getText().toString();
                    quartervalue = quaterID;
                }

                if(!qc1month){
                    Month = qc1Model.getMonth();
                    monthtext = qc1Model.getMonthtext();
                    monthvalue = qc1Model.getMonthvalue();
                }else {
                    Month = monthID;
                    monthtext = autoMonth.getText().toString();
                    monthvalue = monthID;
                }

                if(!qc1loc){
                    Location_Id = qc1Model.getLocation_Id();
                    Location_text = qc1Model.getLocation_text();
                    Location_value = qc1Model.getLocation_value();
                }else {
                    Location_Id = locationID;
                    Location_text = autoLoc.getText().toString();
                    Location_value = locationValue;
                }

//                individuals = autoIndividuals.getText().toString();
//                newProposal = etNewProposal.getText().toString();

                Level2_Id = txtL2Id;
                Level3_Id = txtL3Id;
                Level4_Id = txtL4Id;
                Level5_Id = txtL5Id;
                Level6_Id = txtL6Id;
                Level7_Id = txtL7Id;

                Level2_text = qc1txtL2.getText().toString();
                Level3_text = qc1txtL3.getText().toString();
                Level4_text = qc1txtL4.getText().toString();
                Level5_text = qc1txtL5.getText().toString();
                Level6_text = qc1txtL6.getText().toString();
                Level7_text = qc1txtL7.getText().toString();

                Level2_value = valuel2;
                Level3_value = valuel3;
                Level4_value = valuel4;
                Level5_value = valuel5;
                Level6_value = valuel6;
                Level7_value = valuel7;
                Status = "QC1Pending";
                type = "1";

                insertIntoUploadDb(2);

                if (loginId.equalsIgnoreCase("cmd") || loginId.equalsIgnoreCase("hr") ||
                        loginId.equalsIgnoreCase("legal") || loginId.equalsIgnoreCase("marketing") ||
                        loginId.equalsIgnoreCase("cmdqc1") || loginId.equalsIgnoreCase("hrqc1") ||
                        loginId.equalsIgnoreCase("legalqc1") || loginId.equalsIgnoreCase("marketingqc1")) {
                    qc1txtL4.setText("");
                    qc1txtL4.setHint("");

                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                } else if (loginId.equalsIgnoreCase("cs") || loginId.equalsIgnoreCase("finance") ||
                        loginId.equalsIgnoreCase("csqc1") || loginId.equalsIgnoreCase("financeqc1")) {
                    qc1txtL2.setText("");
                    qc1txtL2.setHint("");

                    cardlevel3.setVisibility(View.GONE);
                    qc1txtL3.setText("");
                    cardlevel4.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                } else if (loginId.equalsIgnoreCase("Personal") ||
                        loginId.equalsIgnoreCase("Personalqc1")) {
                    qc1txtL3.setText("");
                    qc1txtL3.setHint("");

                    cardlevel4.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                choosefilelayout.setVisibility(View.VISIBLE);
                qc1txtNoFile.setText("No file chosen");
                btnAdd.setVisibility(View.VISIBLE);
                qc1layout_edit.setVisibility(View.GONE);

                if (loginId.equalsIgnoreCase("cmd") || loginId.equalsIgnoreCase("hr") ||
                        loginId.equalsIgnoreCase("legal") || loginId.equalsIgnoreCase("marketing") ||
                        loginId.equalsIgnoreCase("cmdqc1") || loginId.equalsIgnoreCase("hrqc1") ||
                        loginId.equalsIgnoreCase("legalqc1") || loginId.equalsIgnoreCase("marketingqc1")) {
                    qc1txtL4.setText("");
                    qc1txtL4.setHint("");

                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                } else if (loginId.equalsIgnoreCase("cs") || loginId.equalsIgnoreCase("finance")||
                        loginId.equalsIgnoreCase("csqc1") || loginId.equalsIgnoreCase("financeqc1")) {
                    qc1txtL2.setText("");
                    qc1txtL2.setHint("");

                    cardlevel3.setVisibility(View.GONE);
                    qc1txtL3.setText("");
                    cardlevel4.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                } else if (loginId.equalsIgnoreCase("Personal")||
                        loginId.equalsIgnoreCase("Personalqc1")) {
                    qc1txtL3.setText("");
                    qc1txtL3.setHint("");

                    cardlevel4.setVisibility(View.GONE);
                    qc1txtL4.setText("");
                    cardlevel5.setVisibility(View.GONE);
                    qc1txtL5.setText("");
                    cardlevel6.setVisibility(View.GONE);
                    qc1txtL6.setText("");
                    cardlevel7.setVisibility(View.GONE);
                    qc1txtL7.setText("");
                }
            }
        });

        viewFile.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                try {
                    openFile(getContext(), filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                UpdateTrans updateTrans = new UpdateTrans();
                updateTrans.execute();

            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                UpdateTransReject updateTransReject = new UpdateTransReject();
                updateTransReject.execute();
            }
        });

    }



    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == getActivity().RESULT_OK) {
//                    filePath = data.getData().getPath();
                    Uri uri = data.getData();
                    filePath = Commons.getPath(uri, getContext());
                    if (filePath.contains("/external/images/media/")) {
                        Uri selectedImageURI = data.getData();
                        File imageFile = new File(getRealPathFromURI(selectedImageURI));
                        filePath = imageFile.getAbsolutePath();
                    }

                    fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                    qc1txtNoFile.setText(fileName);
                    String filename11 = fileName;
                    String filenameArray[] = filename11.split("\\.");
                    fileExtension = filenameArray[filenameArray.length - 1];
                    viewFile.setVisibility(View.VISIBLE);
                }
                break;
            case INTENT_REQUEST_GET_IMAGES:
                if (resultCode == Activity.RESULT_OK) {

                    ArrayList<Uri> image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                    for (int i = 0; i < image_uris.size(); i++) {
                        imagesUri.add(image_uris.get(i).getPath());
                    }
                    Toast.makeText(getContext(), "Images added", Toast.LENGTH_LONG).show();
//            morphToSquare(createPdf, integer(R.integer.mb_animation));
                    createPdf();


                }
        }
    }

    void startAddingImages() {
        // Check if permissions are granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT);
            } else {
                selectImages();
            }
        } else {
            selectImages();
        }
    }

    public void selectImages() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    void createPdf() {
        if (imagesUri.size() == 0) {
            Toast.makeText(getContext(), "No Images selected", Toast.LENGTH_LONG).show();
        } else {
            new MaterialDialog.Builder(getContext())
                    .title("Creating PDF")
                    .content("Enter file name")
                    .input("Example : abc", null, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            if (input == null) {
                                Toast.makeText(getContext(), "Name cannot be blank", Toast.LENGTH_LONG).show();
                            } else {
                                imageFilename = input.toString();

                                new creatingPDF().execute();

                            }
                        }
                    })
                    .show();
        }
    }

    public class creatingPDF extends AsyncTask<String, String, String> {

        // Progress dialog
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext())
                .title("Please Wait")
                .content("Creating PDF. This may take a while.")
                .cancelable(false)
                .progress(true, 0);
        MaterialDialog dialog = builder.build();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDFfiles/");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }


            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDFfiles/";

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDFfiles/");

            filePath = filePath + imageFilename + ".pdf";

            Log.v("stage 1", "store the pdf in sd card");

            Document document = new Document(PageSize.A4, 38, 38, 50, 38);

            Log.v("stage 2", "Document Created");

            Rectangle documentRect = document.getPageSize();


            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

                Log.v("Stage 3", "Pdf writer");

                document.open();

                Log.v("Stage 4", "Document opened");

                for (int i = 0; i < imagesUri.size(); i++) {


                    Bitmap bmp = BitmapFactory.decodeFile(imagesUri.get(i));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 70, stream);


                    pickimage = Image.getInstance(imagesUri.get(i));


                    if (bmp.getWidth() > documentRect.getWidth() || bmp.getHeight() > documentRect.getHeight()) {
                        //bitmap is larger than page,so set bitmap's size similar to the whole page
                        pickimage.scaleAbsolute(documentRect.getWidth(), documentRect.getHeight());
                    } else {
                        //bitmap is smaller than page, so add bitmap simply.[note: if you want to fill page by stretching image, you may set size similar to page as above]
                        pickimage.scaleAbsolute(bmp.getWidth(), bmp.getHeight());
                    }


                    Log.v("Stage 6", "Image path adding");

                    pickimage.setAbsolutePosition((documentRect.getWidth() - pickimage.getScaledWidth()) / 2, (documentRect.getHeight() - pickimage.getScaledHeight()) / 2);
                    Log.v("Stage 7", "Image Alignments");

                    pickimage.setBorder(Image.BOX);

                    pickimage.setBorderWidth(15);

                    document.add(pickimage);

                    document.newPage();
                }

                Log.v("Stage 8", "Image adding");

                document.close();

                Log.v("Stage 7", "Document Closed" + filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            document.close();
            imagesUri.clear();

            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            fileName = s.substring(s.lastIndexOf("/") + 1);
            qc1txtNoFile.setText(fileName);
            viewFile.setVisibility(View.VISIBLE);
        }
    }


    private void insertIntoUploadDb(int flag) {

        File_Exten = fileExtension;
        if(filePath!=null && filePath.length() > 0){
            File_Path = filePath;
        }else{
            File_Path = qc1Model.getFile_Path();
        }

        if(flag ==1) {
            Id = String.valueOf(qc1Model.getQ_Id());
            Document_No = roleID + "_" + Legal_Entity_value + "_" + Property_value + "_" + Location_value + "_" + yeartext + "_" + Quarter + "_" + Month;
            File_Name = Document_No;
            if (Level2_value != null && Level2_value.length() > 0) {
                File_Name = File_Name + "_" + Level2_value;
            }
            if (Level3_value != null && Level3_value.length() > 0) {
                File_Name = File_Name + "_" + Level3_value;
            }
            if (Level4_value != null && Level4_value.length() > 0) {
                File_Name = File_Name + "_" + Level4_value;
            }
            if (Level5_value != null && Level5_value.length() > 0) {
                File_Name = File_Name + "_" + Level5_value;
            }
            if (Level6_value != null && Level6_value.length() > 0) {
                File_Name = File_Name + "_" + Level6_value;
            }
            if (Level7_value != null && Level7_value.length() > 0) {
                File_Name = File_Name + "_" + Level7_value;
            }
        }else{
            Document_No = qc1Model.getDocNo();
            File_Name = Document_No;
            if (Level2_value != null && Level2_value.length() > 0) {
                File_Name = File_Name + "_" + Level2_value;
            }
            if (Level3_value != null && Level3_value.length() > 0) {
                File_Name = File_Name + "_" + Level3_value;
            }
            if (Level4_value != null && Level4_value.length() > 0) {
                File_Name = File_Name + "_" + Level4_value;
            }
            if (Level5_value != null && Level5_value.length() > 0) {
                File_Name = File_Name + "_" + Level5_value;
            }
            if (Level6_value != null && Level6_value.length() > 0) {
                File_Name = File_Name + "_" + Level6_value;
            }
            if (Level7_value != null && Level7_value.length() > 0) {
                File_Name = File_Name + "_" + Level7_value;
            }
            Id = String.valueOf(QID);
        }

        File_Name = File_Name + "_" + fileName;
        File_Path_File_Name = File_Path + File_Name;
        String strLastSync = "0";
        String selection = "id = ?";
        String id = "";

        if (flag == 1) {
            //String[] args = {qc1Model.getFile_Name()};
            ID = KHIL.dbCon.getCountOfRows(DbHelper.QC1_DATA) + 1;
        }else{
            ID = Integer.valueOf(qc1Model.getId());
        }
        id = String.valueOf(ID);
        String[] selectionArgs = {id};
        String valuesArray[] = {id,Created_By,Created_Date,Document_No,File_Exten,File_Name,File_Path,File_Path_File_Name,
                Id,Is_Download,Is_Edit,Is_View,Legal_Entity_Id,Level2_Id,Level3_Id,Level4_Id,Level5_Id,Level6_Id,Level7_Id,
                Location_Id,Month,Property_Id,Quarter,Role_Id,Status,Year,type,strLastSync,
                yearvalue,quartervalue,monthvalue,Legal_Entity_value,Level2_value,
                Level3_value,Level4_value,Level5_value,Level6_value,Level7_value,Location_value,Property_value,
                yeartext,quartertext,monthtext,Legal_Entity_text,Level2_text,Level3_text,Level4_text,Level5_text,
                Level6_text,Level7_text,Location_text,Property_text};
        boolean result = KHIL.dbCon.updateBulk(DbHelper.QC1_DATA, selection, valuesArray, utils.columnNames_QC1_Data, selectionArgs);

        if (result) {
            if (flag == 1) {
                System.out.println("Upload details are added");
                SaveTransDetails saveTransDetails = new SaveTransDetails();
                saveTransDetails.execute();
            }else if (flag == 2){
                Edit_Details_QC1 edit_details = new Edit_Details_QC1();
                edit_details.execute();
            }

          /* if(flag ==1) {
               String base64 = getBase64Image(File_Path);

               final File dwldsPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDFfiles/" + fileName + ".pdf");
               byte[] pdfAsBytes = Base64.decode(base64, 0);
               FileOutputStream os;
               try {
                   os = new FileOutputStream(dwldsPath, false);
                   os.write(pdfAsBytes);
                   os.flush();
                   os.close();
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }*/
           /* if(flag == 1) {
                getUploadData(1);
            }else{
                getUploadData(2);
            }*/

            getUploadData();
        } else {
            System.out.println("FAILURE..!!");
        }
    }

    public void getUploadData() {
        Cursor cursor = null;
        qc1uploadModelList.clear();
        qc1Model = new QC1Model();
        String strLastSynctable = "0";


        String where = " where Q_id = '" + Id + "'" + "and last_sync = '" + strLastSynctable +"'";
        cursor = KHIL.dbCon.fetchFromSelect(DbHelper.QC1_DATA, where);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                qc1Model = createQC1Model(cursor);
                qc1uploadModelList.add(qc1Model);
                setValues(qc1uploadModelList);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void getStatusData(String id) {
        Cursor cursor = null;
        qc1uploadModelList.clear();
        qc1Model = new QC1Model();
        String strLastSynctable = "1";

        String selection = "approve_sync"+ " = ?";
        // WHERE clause arguments
        String[] selectionArgs = {strLastSynctable};
       boolean result = KHIL.dbCon.delete(DbHelper.QC1_DATA, selection, selectionArgs);
        if (result) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(loginId)
                    .setContentText("Document Number Approved Successfully")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            QC1 fragment2 = new QC1();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_content, fragment2);
                            fragmentTransaction.commit();
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
            adapter = new QC1UploadAdapter(getActivity(), qc1uploadModelList);
            qc1listUpload.setAdapter(adapter);
            setListViewHeightBasedOnItems(qc1listUpload);
            adapter.notifyDataSetChanged();
        }
    }
    public void getStatusDataReject(String id) {
        Cursor cursor = null;
        qc1uploadModelList.clear();
        qc1Model = new QC1Model();
        String strLastSynctable = "1";

        String selection = "reject_Sync"+ " = ?";
        // WHERE clause arguments
        String[] selectionArgs = {strLastSynctable};
        boolean result = KHIL.dbCon.delete(DbHelper.QC1_DATA, selection, selectionArgs);
        if (result) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(loginId)
                    .setContentText("Document Number Rejected Successfully")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            QC1 fragment2 = new QC1();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_content, fragment2);
                            fragmentTransaction.commit();
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
            adapter = new QC1UploadAdapter(getActivity(), qc1uploadModelList);
            qc1listUpload.setAdapter(adapter);
            setListViewHeightBasedOnItems(qc1listUpload);
            adapter.notifyDataSetChanged();
        }
    }

    private String getBase64Image(String imgPath) {
        String base64Img = "";
        InputStream inputStream = null;//You can get an inputStream using any IO API

        try {
            inputStream = new FileInputStream(imgPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        base64Img = output.toString();
        return base64Img;
    }

    private void setValues(List<QC1Model> qc1uploadModelList) {
        if(qc1uploadModelList != null && qc1uploadModelList.size() > 0) {
            try {
                adapter = new QC1UploadAdapter(getActivity(), qc1uploadModelList);
                qc1listUpload.setAdapter(adapter);
                setListViewHeightBasedOnItems(qc1listUpload);
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private QC1Model createQC1Model(Cursor cursor) {
        qc1Model = new QC1Model();
        try {
            qc1Model.setId(cursor.getString(cursor.getColumnIndex("id")));
            qc1Model.setDept(cursor.getString(cursor.getColumnIndex("Created_By")));
            qc1Model.setCreatedBy(cursor.getString(cursor.getColumnIndex("Created_By")));
            qc1Model.setCreatedDate(cursor.getString(cursor.getColumnIndex("Created_Date")));
            qc1Model.setDocNo(cursor.getString(cursor.getColumnIndex("Document_No")));
            qc1Model.setFile_Exten(cursor.getString(cursor.getColumnIndex("File_Exten")));
            qc1Model.setFile_Name(cursor.getString(cursor.getColumnIndex("File_Name")));
            qc1Model.setFile_Path(cursor.getString(cursor.getColumnIndex("File_Path")));
            qc1Model.setFile_Path_File_Name(cursor.getString(cursor.getColumnIndex("File_Path_File_Name")));
            qc1Model.setQ_Id(cursor.getString(cursor.getColumnIndex("Q_Id")));
            qc1Model.setIs_Download(cursor.getString(cursor.getColumnIndex("Is_Download")));
            qc1Model.setIs_Edit(cursor.getString(cursor.getColumnIndex("Is_Edit")));
            qc1Model.setIs_View(cursor.getString(cursor.getColumnIndex("Is_View")));
            qc1Model.setLegal_Entity_Id(cursor.getString(cursor.getColumnIndex("Legal_Entity_Id")));
            qc1Model.setLevel2_Id(cursor.getString(cursor.getColumnIndex("Level2_Id")));
            qc1Model.setLevel3_Id(cursor.getString(cursor.getColumnIndex("Level3_Id")));
            qc1Model.setLevel4_Id(cursor.getString(cursor.getColumnIndex("Level4_Id")));
            qc1Model.setLevel5_Id(cursor.getString(cursor.getColumnIndex("Level5_Id")));
            qc1Model.setLevel6_Id(cursor.getString(cursor.getColumnIndex("Level6_Id")));
            qc1Model.setLevel7_Id(cursor.getString(cursor.getColumnIndex("Level7_Id")));
            qc1Model.setLocation_Id(cursor.getString(cursor.getColumnIndex("Location_Id")));
            qc1Model.setMonth(cursor.getString(cursor.getColumnIndex("Month")));
            qc1Model.setProperty_Id(cursor.getString(cursor.getColumnIndex("Property_Id")));
            qc1Model.setQuarter(cursor.getString(cursor.getColumnIndex("Quarter")));
            qc1Model.setRole_Id(cursor.getString(cursor.getColumnIndex("Role_Id")));
            qc1Model.setStatus(cursor.getString(cursor.getColumnIndex("Status")));
            qc1Model.setYear(cursor.getString(cursor.getColumnIndex("Year")));
            qc1Model.setType(cursor.getString(cursor.getColumnIndex("type")));


            qc1Model.setYearvalue(cursor.getString(cursor.getColumnIndex("yearvalue")));
            qc1Model.setQuartervalue(cursor.getString(cursor.getColumnIndex("quartervalue")));
            qc1Model.setMonthvalue(cursor.getString(cursor.getColumnIndex("monthvalue")));
            qc1Model.setLegal_Entity_value(cursor.getString(cursor.getColumnIndex("Legal_Entity_value")));
            qc1Model.setLevel2_value(cursor.getString(cursor.getColumnIndex("Level2_value")));
            qc1Model.setLevel3_value(cursor.getString(cursor.getColumnIndex("Level3_value")));
            qc1Model.setLevel4_value(cursor.getString(cursor.getColumnIndex("Level4_value")));
            qc1Model.setLevel5_value(cursor.getString(cursor.getColumnIndex("Level5_value")));
            qc1Model.setLevel6_value(cursor.getString(cursor.getColumnIndex("Level6_value")));
            qc1Model.setLevel7_value(cursor.getString(cursor.getColumnIndex("Level7_value")));
            qc1Model.setLocation_value(cursor.getString(cursor.getColumnIndex("Location_value")));
            qc1Model.setProperty_value(cursor.getString(cursor.getColumnIndex("Property_value")));
            qc1Model.setYeartext(cursor.getString(cursor.getColumnIndex("yeartext")));
            qc1Model.setQuartertext(cursor.getString(cursor.getColumnIndex("quartertext")));
            qc1Model.setMonthtext(cursor.getString(cursor.getColumnIndex("monthtext")));
            qc1Model.setLegal_Entity_text(cursor.getString(cursor.getColumnIndex("Legal_Entity_text")));
            qc1Model.setLevel2_text(cursor.getString(cursor.getColumnIndex("Level2_text")));
            qc1Model.setLevel3_text(cursor.getString(cursor.getColumnIndex("Level3_text")));
            qc1Model.setLevel4_text(cursor.getString(cursor.getColumnIndex("Level4_text")));
            qc1Model.setLevel5_text(cursor.getString(cursor.getColumnIndex("Level5_text")));
            qc1Model.setLevel6_text(cursor.getString(cursor.getColumnIndex("Level6_text")));
            qc1Model.setLevel7_text(cursor.getString(cursor.getColumnIndex("Level7_text")));
            qc1Model.setLocation_text(cursor.getString(cursor.getColumnIndex("Location_text")));
            qc1Model.setProperty_text(cursor.getString(cursor.getColumnIndex("Property_text")));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return qc1Model;
    }

    public void fetchQC1Details(String roleID){

        try {
            listQC1 = new ArrayList<>();
            Cursor cursor = null;
            String where = " where Role_Id = '" + roleID + "'";
            cursor = KHIL.dbCon.fetchFromSelect(DbHelper.QC1_DATA, where);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    qc1Model = createQC1Model(cursor);
                    listQC1.add(qc1Model);
                } while (cursor.moveToNext());
                cursor.close();
            }

            qc1Adapter = new QC1Adapter(getContext(), listQC1);
            listqc.setAdapter(qc1Adapter);
        }catch (Exception e){
            e.printStackTrace();
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
        Cursor cursor2 = KHIL.dbCon.fetchAlldata(DbHelper.M_Year);
        if (cursor2 != null && cursor2.getCount() > 0) {
            cursor2.moveToFirst();
            do {
//                year = cursor2.getString(cursor2.getColumnIndex("text"));
                yearModel = new YearModel();
                yearModel.setId(cursor2.getString(cursor2.getColumnIndex("id")));
                yearModel.setText(cursor2.getString(cursor2.getColumnIndex("text")));
                yearModel.setValue(cursor2.getString(cursor2.getColumnIndex("value")));
                listYear.add(yearModel);
            } while (cursor2.moveToNext());
            cursor2.close();
        }

        listQuarter = new ArrayList<>();
        String quater = "";
        Cursor cursor3 = KHIL.dbCon.fetchAlldata(DbHelper.M_Quater);
        if (cursor3 != null && cursor3.getCount() > 0) {
            cursor3.moveToFirst();
            do {
//                quater = cursor3.getString(cursor3.getColumnIndex("text"));
                quaterModel = new QuaterModel();
                quaterModel.setId(cursor3.getString(cursor3.getColumnIndex("id")));
                quaterModel.setText(cursor3.getString(cursor3.getColumnIndex("text")));
                quaterModel.setValue(cursor3.getString(cursor3.getColumnIndex("value")));

                listQuarter.add(quaterModel);
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

    /**
     * Fetching All Level Data User vise
     */

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
                qc1txtL2.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel3dataFin(String aID) {
        try {

            level3list = new ArrayList<>();
            String where = " where role_ID = " + "'" + roleID +"'"+ "and parent_Ref = "+ "'" + aID + "'";// "'" + "and data_level = "+ "'" + "2" +
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
                qc1txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4data(String aID) {
        try {
            level4list = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'"+ "and parent_Ref = "+ "'" + aID + "'";// "'" + "and data_level = "+ "'" + "3" +
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }


            Collections.sort(level4list);
            if (level4list.size() > 0) {
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
                qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5data(String aID) {
        try {

            level5list = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID +"'"+ "and parent_Ref = "+ "'" + aID + "'";// "'" + "and data_level = "+ "'" + "4" +
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6data(String aID) {
        try {

            level6list = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'"+ "and parent_Ref = "+ "'" + aID + "'";//"'" + "and data_level = "+ "'" + "5" +
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL6.setAdapter(adapter1);
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
                qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataHR(String aID) {
        try {

            level5listHR = new ArrayList<>();
            String level5 = "";
            String where = " where role_ID = " + "'" + roleID + "'"+ "and parent_Ref = "+ "'" + aID + "'";// "'" + "and data_level = "+ "'" + "4" +
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
                qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataHR(String aID) {
        try {

            level6listHR = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'"+ "and parent_Ref = "+ "'" + aID + "'";//"'" + "and data_level = "+ "'" + "5" +
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6listHR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL6.setAdapter(adapter1);
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
                qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataCMD(String aID) {
        try {
            level5listCMD = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'"+ "and parent_Ref = "+ "'" + aID + "'";// "'" + "and data_level = "+ "'" + "5" +
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5listCMD.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataCMD(String aID) {
        try {

            level6listCMD = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID +"'"+ "and parent_Ref = "+ "'" + aID + "'";// "'" + "and data_level = "+ "'" + "6" +
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6listCMD.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataCMD(String aID) {
        try {

            level7listCMD = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID +"'"+ "and parent_Ref = "+ "'" + aID + "'";// "'" + "and data_level = "+ "'" + "7" +
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level7listCMD.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL7.setAdapter(adapter1);
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
                qc1txtL2.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel3dataCS(String aID) {
        try {
            level3listCS = new ArrayList<>();

            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//"and data_level = "+ "'" + "2" +
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
                qc1txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataCS(String aID) {
        try {
            level4listCS = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "3"
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4listCS.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            Collections.sort(level4listCS);
            if (level4listCS.size() > 0) {
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
                qc1txtL4.setAdapter(adapter1);
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
                qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataMAR(String aID) {
        try {

            level5listMAR = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "4" + "'"
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5listMAR.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataMAR(String aID) {
        try {
            level6listMAR = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "5" + "'"
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6listMAR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataMAR(String aID) {
        try {

            level7listMAR = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "6" + "'"
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level7listMAR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL7.setAdapter(adapter1);
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
                qc1txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataPER(String aID) {
        try {
            level4listPER = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "4" + "'"
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level4listPER.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            Collections.sort(level4listPER);
            if (level4listPER.size() > 0) {
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
                qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataPER(String aID) {
        try {
            level5listPER = new ArrayList<>();
            String branch = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "5" + "'"
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);

            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("text"));
                    level5listPER.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL5.setAdapter(adapter1);
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
                qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataLEGAL(String aID) {
        try {

            level5listLEGAL = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "4" + "'"
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
                qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataLEGAL(String aID) {
        try {
            level6listLEGAL = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "5" + "'"
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level6listLEGAL.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataLEGAL(String aID) {
        try {
            level7listLEGAL = new ArrayList<>();
            String temp = "";
            String where = " where role_ID = " + "'" + roleID + "'" + "and parent_Ref = "+ "'" + aID + "'";//+ "and data_level = "+ "'" + "6" + "'"
            Cursor cursor1 = KHIL.dbCon.fetchFromSelect(DbHelper.M_Level_Data,where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    temp = cursor1.getString(cursor1.getColumnIndex("text"));
                    level7listLEGAL.add(temp);
                } while (cursor1.moveToNext());
                cursor1.close();
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
                qc1txtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SaveTransDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Please wait ...");
            progress.show();
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            SoapPrimitive soapObject = null;
            SOAPWebservice webService = new SOAPWebservice(getActivity());
            String Updated_By = Created_By;
            String Updated_Date = Created_Date;
            // TODO: 9/22/2017  Individuals_Id = "",NewProposal="",NewDocument_No="";
            soapObject = webService.Save_Trans_Dtls(Role_Id,Legal_Entity_Id,Property_Id,Location_Id,Document_No,Created_By,Updated_By,Created_Date,Updated_Date,
                   "","",TransID,Level2_Id,Level3_Id,Level4_Id,Level5_Id,Level6_Id,Level7_Id,Year,Quarter,Month,File_Name ,File_Path,File_Path_File_Name,
                    File_Exten,Status,"");
            return soapObject;
        }

        @Override
        protected void onPostExecute(SoapPrimitive soapObject) {
            super.onPostExecute(soapObject);
            progress.dismiss();

        }
    }

    private class Edit_Details_QC1 extends AsyncTask<Void, Void, SoapPrimitive> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Please wait ...");
            progress.show();
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            SoapPrimitive soapObject = null;
            SOAPWebservice webService = new SOAPWebservice(getActivity());
            String Updated_By = Created_By;
            String Updated_Date = Created_Date;
            // TODO: 9/22/2017  Individuals_Id = "",NewProposal="",NewDocument_No="";

            soapObject = webService.Edit_Details_QC1(Role_Id,Legal_Entity_Id,Property_Id,Location_Id,Document_No,Created_By,Updated_By,Created_Date,Updated_Date,
                    "","",TransID,Level2_Id,Level3_Id,Level4_Id,Level5_Id,Level6_Id,Level7_Id,Year,Quarter,Month,File_Name ,File_Path,File_Path_File_Name,
                    File_Exten,Status,"");
            return soapObject;
        }

        @Override
        protected void onPostExecute(SoapPrimitive soapObject) {
            super.onPostExecute(soapObject);
            progress.dismiss();

        }
    }
    private class UpdateTrans extends AsyncTask<Void, Void, SoapPrimitive> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Please wait ...");
            progress.show();
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {

            SoapPrimitive object = Approve();

            return object;
        }

        @Override
        protected void onPostExecute(SoapPrimitive soapObject) {
            super.onPostExecute(soapObject);
            progress.dismiss();
            getStatusData(qc1Model.getQ_Id());
        }
    }

    private SoapPrimitive Approve() {
        SoapPrimitive objLead = null;
        SOAPWebservice webService = new SOAPWebservice(getActivity());
        if (qc1uploadModelList.size()>0){
            for (int k = 0; k < qc1uploadModelList.size(); k++) {
                qc1Model = qc1uploadModelList.get(k);

                // TODO: 9/22/2017  Individuals_Id = "",NewProposal="",NewDocument_No="";
                objLead = webService.Update_Trans(qc1Model.getRole_Id(),qc1Model.getLegal_Entity_Id(),qc1Model.getProperty_Id(),
                        qc1Model.getLocation_Id(),qc1Model.getDocNo(),qc1Model.getCreatedBy(),qc1Model.getCreatedBy(),qc1Model.getCreatedDate(),
                        qc1Model.getCreatedDate(),"","",qc1Model.getQ_Id(),qc1Model.getYear(),qc1Model.getQuarter(),qc1Model.getMonth(),
                        "QC1Approved","");
                String id = String.valueOf(objLead);
                if (id.equalsIgnoreCase("null") || id.equals(null) || id.equals("0")) {
                    String strLastSync = "0";
                    String selection = "File_Name = ?";
                    String[] selectionArgs = {qc1Model.getFile_Name()};
                    String valuesArray[] = {strLastSync};
                    String[] columnNames = {"approve_sync"};
                    boolean result = KHIL.dbCon.updateBulk(DbHelper.QC1_DATA, selection, valuesArray, columnNames, selectionArgs);
                    if (result) {
                        System.out.println("Document Reject");
                    } else {
                        System.out.println("FAILURE..!!");
                    }

                }else {
                    String strLastSync = "1";
                    String selection = "File_Name = ?";
                    String[] selectionArgs = {qc1Model.getFile_Name()};
                    String valuesArray[] = {strLastSync};
                    String[] columnNames = {"approve_sync"};
                    boolean result = KHIL.dbCon.updateBulk(DbHelper.QC1_DATA, selection, valuesArray, columnNames, selectionArgs);
                    if (result) {
                        System.out.println("Document Approve");
                    } else {
                        System.out.println("FAILURE..!!");
                    }

                }

            }
        }
        return objLead;
    }
    private class UpdateTransReject extends AsyncTask<Void, Void, SoapPrimitive> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Please wait ...");
            progress.show();
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {

            SoapPrimitive object = Reject();

            return object;
        }

        @Override
        protected void onPostExecute(SoapPrimitive soapObject) {
            super.onPostExecute(soapObject);
            progress.dismiss();
            getStatusDataReject(qc1Model.getQ_Id());
        }
    }
    private SoapPrimitive Reject(){
        SoapPrimitive objLead = null;
        SOAPWebservice webService = new SOAPWebservice(getActivity());
        if (qc1uploadModelList.size()>0){
            for (int k = 0; k < qc1uploadModelList.size(); k++) {
                qc1Model = qc1uploadModelList.get(k);

                // TODO: 9/22/2017  Individuals_Id = "",NewProposal="",NewDocument_No="";
                objLead = webService.Update_Trans(qc1Model.getRole_Id(),qc1Model.getLegal_Entity_Id(),qc1Model.getProperty_Id(),
                        qc1Model.getLocation_Id(),qc1Model.getDocNo(),qc1Model.getCreatedBy(),qc1Model.getCreatedBy(),qc1Model.getCreatedDate(),
                        qc1Model.getCreatedDate(),"","",qc1Model.getQ_Id(),qc1Model.getYear(),qc1Model.getQuarter(),qc1Model.getMonth(),
                        "QC1Rejected","");
                String id = String.valueOf(objLead);
                if (id.equalsIgnoreCase("null") || id.equals(null) || id.equals("0")) {
                    String strLastSync = "0";
                    String selection = "File_Name = ?";
                    String[] selectionArgs = {qc1Model.getFile_Name()};
                    String valuesArray[] = {strLastSync};
                    String[] columnNames = {"reject_Sync"};
                    boolean result = KHIL.dbCon.updateBulk(DbHelper.QC1_DATA, selection, valuesArray, columnNames, selectionArgs);
                    if (result) {
                        System.out.println("Document Rejected");
                    } else {
                        System.out.println("FAILURE..!!");
                    }

                }else {
                    String strLastSync = "1";
                    String selection = "File_Name = ?";
                    String[] selectionArgs = {qc1Model.getFile_Name()};
                    String valuesArray[] = {strLastSync};
                    String[] columnNames = {"reject_Sync"};
                    boolean result = KHIL.dbCon.updateBulk(DbHelper.QC1_DATA, selection, valuesArray, columnNames, selectionArgs);
                    if (result) {
                        System.out.println("Document Rejected");
                    } else {
                        System.out.println("FAILURE..!!");
                    }

                }

            }
        }
        return objLead;
    }
}

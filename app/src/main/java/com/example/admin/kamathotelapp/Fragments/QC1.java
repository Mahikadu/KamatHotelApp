package com.example.admin.kamathotelapp.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.gauriinfotech.commons.Commons;

import static com.example.admin.kamathotelapp.Fragments.UploadFragment.setListViewHeightBasedOnItems;


public class QC1 extends Fragment {

    public String monthID,yearID,quaterID;
    public String legalEntityID,propertyID,individualID,locationID;
    public String legalEntityValue,propertyValue,individualValue,locationValue;
    String[] strLegalArray = null;
    String[] strPropertyArray = null;
    String[] strMonthArray = null;
    String[] strYearArray = null;
    String[] strQuarterArray = null;
    String[] strLocArray = null;
    String filePath;
    public String fileName="";
    public String fileExtension = "";
    private String strLegalEntity,strProperty,strMonth,strYear,strQuarter,strLoc;
    private String legalEntityString,propertyString,monthString,yearString,quarterString="",locString;
    public static CardView cardlevel2, cardlevel3,cardlevel4,cardlevel5,cardlevel6,cardlevel7,cardNewProposal,cardIndividuals,cardProperty;
    public static TextInputLayout level2txtlayout, level3txtlayout,level4txtlayout,level5txtlayout,level6txtlayout,level7txtlayout;
    public TextView headLev2, headLev3, headLev4, headLev5, headLev6, viewFile;// headLev7,

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



    ListView listqc;
    QC1Adapter qc1Adapter;
    QC1Model qc1Model;
    private ArrayList<QC1Model> listQC1;
    private SharedPref sharedPref;
    String roleID;
    LinearLayout qc1datalayout,qclayout;
    boolean clickflag = false;

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

    public static Button btnUpload;

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
        return view;
    }

    public void initView(View view){

        sharedPref = new SharedPref(getContext());
        roleID = sharedPref.getRoleID();
        listqc = (ListView) view.findViewById(R.id.listqc);
        qc1datalayout = (LinearLayout)view.findViewById(R.id.qc1datalayout);
        qclayout = (LinearLayout)view.findViewById(R.id.qclayout);
        btnUpload = (Button) view.findViewById(R.id.btnUpload);

        //////Level data textview
        qc1txtL2 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level2);
        qc1txtL3 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level3);
        qc1txtL4 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level4);
        qc1txtL5 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level5);
        qc1txtL6 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level6);
        qc1txtL7 = (AutoCompleteTextView) view.findViewById(R.id.qc1Level7);
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

        level2txtlayout = (TextInputLayout)view.findViewById(R.id.qc1level2txtlayout);
        level3txtlayout = (TextInputLayout)view.findViewById(R.id.qc1level3txtlayout);
        level4txtlayout = (TextInputLayout)view.findViewById(R.id.qc1level4txtlayout);
        level5txtlayout = (TextInputLayout)view.findViewById(R.id.qc1level5txtlayout);
        level6txtlayout = (TextInputLayout)view.findViewById(R.id.qc1level6txtlayout);
        level7txtlayout = (TextInputLayout)view.findViewById(R.id.qc1level7txtlayout);

        headLev2 = (TextView) view.findViewById(R.id.qc1level2);
        headLev3 = (TextView) view.findViewById(R.id.qc1level3);
        headLev4 = (TextView) view.findViewById(R.id.qc1level4);
        headLev5 = (TextView) view.findViewById(R.id.qc1level5);
        headLev6 = (TextView) view.findViewById(R.id.qc1level6);
        viewFile = (TextView) view.findViewById(R.id.qc1txtViewFile);

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

        qc1listUpload = (ListView) view.findViewById(R.id.qc1listViewUpload);
        qc1Model = new QC1Model();

        Bundle bundle = getArguments();
        if (bundle != null) {
            clickflag = bundle.getBoolean("Click");
            qc1Model = (QC1Model) bundle.getSerializable("QC1");
        }
        if(clickflag){
            qc1uploadModelList = new ArrayList<>();
            listqc.setVisibility(View.GONE);
            qclayout.setVisibility(View.GONE);
            qc1datalayout.setVisibility(View.VISIBLE);

            autoLegalEntity.setText(qc1Model.getLegal_Entity_Id());
            autoProperty.setText(qc1Model.getProperty_Id());
            autoMonth.setText(qc1Model.getMonth());
            autoYear.setText(qc1Model.getYear());
            autoQuarter.setText(qc1Model.getQuarter());
            autoLoc.setText(qc1Model.getLocation_Id());

            qc1no_files.setVisibility(View.GONE);
            qc1listUpload.setVisibility(View.VISIBLE);

            qc1uploadModelList.add(qc1Model);
            setValues(qc1uploadModelList);
        }

        fetchQC1Details(roleID);
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


        if (listYear != null) {
            if (listYear.size() > 0) {
                strYearArray = new String[listYear.size()];
                //   strLeadArray[0] = "Select Source Lead";
                for (int i = 0; i < listYear.size(); i++) {
                    yearModel  = listYear.get(i);
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
                        }
                    }
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
    public void fetchQC1Details(String roleID){

        try {
            listQC1 = new ArrayList<>();
            Cursor cursor = null;
            String where = " where Role_Id = '" + roleID + "'";
            cursor = KHIL.dbCon.fetchFromSelect(DbHelper.QC1_DATA, where);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    qc1Model = new QC1Model();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case PICKFILE_RESULT_CODE:
                if(resultCode == getActivity().RESULT_OK) {
//                    filePath = data.getData().getPath();
                    Uri uri = data.getData();
                    filePath = Commons.getPath(uri, getContext());
                    if(filePath.contains("/external/images/media/")) {
                        Uri selectedImageURI = data.getData();
                        File imageFile = new File(getRealPathFromURI(selectedImageURI));
                        filePath = imageFile.getAbsolutePath();
                    }

                    fileName = filePath.substring(filePath.lastIndexOf("/")+1);
                    qc1txtNoFile.setText(fileName);
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

}

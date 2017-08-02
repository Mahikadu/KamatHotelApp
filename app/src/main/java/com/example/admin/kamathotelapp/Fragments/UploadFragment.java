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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kamathotelapp.Adapters.UploadAdapter;
import com.example.admin.kamathotelapp.KHIL;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.ExceptionHandler;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.Utils.Utils;
import com.example.admin.kamathotelapp.dbConfig.DataBaseCon;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public static ListView listUpload;
    private String loginId, password;
    List<UploadModel> uploadModelList;
    UploadModel uploadModel;
    List<String> listMonth;
    private UploadAdapter adapter;
    private String valLevel2="", valLevel3="", valLevel4="", valLevel5="", valLevel6="", valLevel7="";
    @InjectView(R.id.spinLegalEntity)
    AutoCompleteTextView autoLegalEntity;
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
    public static CardView cardlevel2, cardlevel3,cardlevel4,cardlevel5,cardlevel6,cardlevel7;
    public static TextInputLayout level2txtlayout, level3txtlayout,level4txtlayout,level5txtlayout,level6txtlayout,level7txtlayout;
    public TextView headLev2, headLev3, headLev4, headLev5, headLev6, headLev7, viewFile;
    public static TextView txtNoFile;
    public static TextView no_files;
    private static final int PICKFILE_RESULT_CODE = 1;
    public String fileName="";
    private Utils utils;
    private String legalEntity, property, year, quarter, month, location, file="", level2="", level3="", level4="", level5="", level6="", level7="";
    public static LinearLayout layout_edit;
    public static int ID = 0;

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
        //................Auto Complete Text View......
        txtL2 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel2);
        txtL3 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel3);
        txtL4 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel4);
        txtL5 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel5);
        txtL6 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel6);
        txtL7 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel7);
        txtNoFile = (TextView) view.findViewById(R.id.txtNofile);
        no_files = (TextView) view.findViewById(R.id.no_files);

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

        List<String> listLegal = Arrays.asList(getResources().getStringArray(R.array.legal_entity));
        List<String> listProperty = Arrays.asList(getResources().getStringArray(R.array.property));

        List<String> listYear = Arrays.asList(getResources().getStringArray(R.array.year));
        List<String> listQuarter = Arrays.asList(getResources().getStringArray(R.array.quarter));
        List<String> listLoc = Arrays.asList(getResources().getStringArray(R.array.location));

        /////////Details of legal Entity
        if (listLegal.size() > 0) {
            strLegalArray = new String[listLegal.size()];
            for (int i = 0; i < listLegal.size(); i++) {
                strLegalArray[i] = listLegal.get(i);
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
                    strLegalEntity = autoLegalEntity.getText().toString();
                    legalEntityString = parent.getItemAtPosition(position).toString();
                }
            }
        });

        ///////////////Details of property
        if (listProperty.size() > 0) {
            strPropertyArray = new String[listProperty.size()];
            for (int i = 0; i < listProperty.size(); i++) {
                strPropertyArray[i] = listProperty.get(i);
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

                if(quarterString.equalsIgnoreCase("Quarter 1")) {
                    listMonth = Arrays.asList(getResources().getStringArray(R.array.month_q1));
                } else if(quarterString.equalsIgnoreCase("Quarter 2")) {
                    listMonth = Arrays.asList(getResources().getStringArray(R.array.month_q2));
                } else if(quarterString.equalsIgnoreCase("Quarter 3")) {
                    listMonth = Arrays.asList(getResources().getStringArray(R.array.month_q3));
                } else if(quarterString.equalsIgnoreCase("Quarter 4")) {
                    listMonth = Arrays.asList(getResources().getStringArray(R.array.month_q4));
                }

                if (listMonth != null) {
                    if (listMonth.size() > 0) {
                        strMonthArray = new String[listMonth.size()];
                        for (int i = 0; i < listMonth.size(); i++) {
                            strMonthArray[i] = listMonth.get(i);
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
                            strMonth = autoMonth.getText().toString();
                            monthString = parent.getItemAtPosition(position).toString();
                        }
                    }
                });

            }
        });
        ///////////////////////////////
        ///////////////Details of Location
        if (listLoc.size() > 0) {
            strLocArray = new String[listLoc.size()];
            //   strLeadArray[0] = "Select Source Lead";
            for (int i = 0; i < listLoc.size(); i++) {
                strLocArray[i] = listLoc.get(i);
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
                }
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

                        valLevel2 = parent.getItemAtPosition(position).toString();

                        if (valLevel2 != null && valLevel2.length() > 0) {
                            fetchLevel3dataFin();
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
                        valLevel3 = parent.getItemAtPosition(position).toString();

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4data();
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
                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5data();
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
                        valLevel5 = parent.getItemAtPosition(position).toString();

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6data();
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
                        valLevel6 = parent.getItemAtPosition(position).toString();
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
                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataHR();
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
                        valLevel5 = parent.getItemAtPosition(position).toString();

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataHR();
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
                        valLevel6 = parent.getItemAtPosition(position).toString();
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

                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataCMD();

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
                        valLevel5 = parent.getItemAtPosition(position).toString();

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataCMD();
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
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataCMD();
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
                        valLevel7 = parent.getItemAtPosition(position).toString();
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
                        valLevel2 = parent.getItemAtPosition(position).toString();

                        if (valLevel2 != null && valLevel2.length() > 0) {
                            fetchLevel3dataCS();
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
                        valLevel3 = parent.getItemAtPosition(position).toString();

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4dataCS();
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
                        valLevel4 = parent.getItemAtPosition(position).toString();
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
                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataMAR();
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
                        valLevel5 = parent.getItemAtPosition(position).toString();

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataMAR();
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
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataMAR();
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
                        valLevel7 = parent.getItemAtPosition(position).toString();
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
                        valLevel3 = parent.getItemAtPosition(position).toString();

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4dataPER();
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
                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataPER();
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
                        valLevel5 = parent.getItemAtPosition(position).toString();
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

                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataLEGAL();
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
                        valLevel5 = parent.getItemAtPosition(position).toString();

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataLEGAL();
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
                        valLevel6 = parent.getItemAtPosition(position).toString();

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataLEGAL();
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
                        valLevel7 = parent.getItemAtPosition(position).toString();
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
        String selection = "id = ?";
        String id = "";

        if(flag == 1) {
            String[] args = {loginId};
            ID = KHIL.dbCon.getCountOfRows(DbHelper.TABLE_UPLOAD," user_id = ?", args) + 1;
        }
        id = String.valueOf(ID);
        String[] selectionArgs = {id};

        String valuesArray[] = {id, loginId, legalEntity, property, year, quarter, month, location, file, level2, level3, level4, level5, level6, level7};

        boolean result = KHIL.dbCon.updateBulk(DbHelper.TABLE_UPLOAD, selection, valuesArray, utils.columnNamesUpload, selectionArgs);

        if (result) {
            System.out.println("Upload details are added");
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
            String Level2 = "level2";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level2,DbHelper.TABLE_FINANCE);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level2"));
                    level2list.add(branch);
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

    private void fetchLevel3dataFin() {
        try {

            level3list = new ArrayList<>();

            String where = " where level2 like " + "'" + valLevel2 + "'";
            String Level3 = "level3";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level3,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level3"));
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

    private void fetchLevel4data() {
        try {
            level4list = new ArrayList<>();

            String where = " where level3 like " + "'" + valLevel3 + "'";
            String Level4 = "level4";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level4,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level4"));
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

    private void fetchLevel5data() {
        try {

            level5list = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level5"));
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

    private void fetchLevel6data() {
        try {

            level6list = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
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

            String Level4 = "level4";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level4,DbHelper.TABLE_HR);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String hr = "";
                    hr = cursor1.getString(cursor1.getColumnIndex("level4"));
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

    private void fetchLevel5dataHR() {
        try {

            level5listHR = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String level5 = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_HR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("level5"));
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

    private void fetchLevel6dataHR() {
        try {

            level6listHR = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_HR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
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

            String Level4 = "level4";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level4,DbHelper.TABLE_CMD);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String cmd = "";
                    cmd = cursor1.getString(cursor1.getColumnIndex("level4"));
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

    private void fetchLevel5dataCMD() {
        try {
            level5listCMD = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String level5 = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_CMD, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("level5"));
                    level5listCMD.add(level5);
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

    private void fetchLevel6dataCMD() {
        try {

            level6listCMD = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_CMD, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
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

    private void fetchLevel7dataCMD() {
        try {

            level7listCMD = new ArrayList<>();
            String branch = "";
            String where = " where level6 like " + "'" + valLevel6 + "'";
            String Level7 = "level7";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level7,DbHelper.TABLE_CMD, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level7"));
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

            String Level2 = "level2";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level2,DbHelper.TABLE_CS);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level2"));
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

    private void fetchLevel3dataCS() {
        try {
            level3listCS = new ArrayList<>();

            String where = " where level2 like " + "'" + valLevel2 + "'";
            String Level3 = "level3";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level3,DbHelper.TABLE_CS, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level3"));
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

    private void fetchLevel4dataCS() {
        try {
            level4listCS = new ArrayList<>();

            String where = " where level3 like " + "'" + valLevel3 + "'";
            String Level4 = "level4";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level4,DbHelper.TABLE_CS, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level4"));
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

            String Level4 = "level4";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level4,DbHelper.TABLE_MAR);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String mar = "";
                    mar = cursor1.getString(cursor1.getColumnIndex("level4"));
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

    private void fetchLevel5dataMAR() {
        try {

            level5listMAR = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String level5 = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_MAR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("level5"));
                    level5listMAR.add(level5);
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

    private void fetchLevel6dataMAR() {
        try {
            level6listMAR = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_MAR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
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

    private void fetchLevel7dataMAR() {
        try {

            level7listMAR = new ArrayList<>();
            String branch = "";
            String where = " where level6 like " + "'" + valLevel6 + "'";
            String Level7 = "level7";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level7,DbHelper.TABLE_MAR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level7"));
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

            String Level3 = "level3";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level3,DbHelper.TABLE_PER);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level3"));
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

    private void fetchLevel4dataPER() {
        try {
            level4listPER = new ArrayList<>();

            String where = " where level3 like " + "'" + valLevel3 + "'";
            String Level4 = "level4";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level4,DbHelper.TABLE_PER, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level4"));
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

    private void fetchLevel5dataPER() {
        try {
            level5listPER = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_PER, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level5"));
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

            String Level4 = "level4";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level4,DbHelper.TABLE_LEGAL);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String legal = "";
                    legal = cursor1.getString(cursor1.getColumnIndex("level4"));
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

    private void fetchLevel5dataLEGAL() {
        try {

            level5listLEGAL = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String level5 = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_LEGAL, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("level5"));
                    level5listLEGAL.add(level5);
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

    private void fetchLevel6dataLEGAL() {
        try {
            level6listLEGAL = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_LEGAL, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
                    level6listLEGAL.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
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

    private void fetchLevel7dataLEGAL() {
        try {
            level7listLEGAL = new ArrayList<>();
            String branch = "";
            String where = " where level6 like " + "'" + valLevel6 + "'";
            String Level7 = "level7";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level7,DbHelper.TABLE_LEGAL, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level7"));
                    level7listLEGAL.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
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
}

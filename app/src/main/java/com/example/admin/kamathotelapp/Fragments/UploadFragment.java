package com.example.admin.kamathotelapp.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Adapters.UploadAdapter;
import com.example.admin.kamathotelapp.KHIL;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UploadFragment extends Fragment {

    //private TextView txtLegalEntity, txtLocation;
    //private Spinner spinLegalEntity, spinProperty, spinMonth, spinYear, spinQuarter, spinLocation;
    private Spinner spinLevel3, spinLevel4, spinLevel5, spinLevel6;
    ArrayAdapter<String> dataAdapter;
    private SharedPref sharedPref;
    List<String> listL2, listL3, listL4, listL5, listL6;
    Button btnUpload;
    ListView listUpload;
    List<UploadModel> uploadModelList;
    UploadModel uploadModel;
    private UploadAdapter adapter;
    private String valLevel2, valLevel3, valLevel4, valLevel5, valLevel6;
    private AutoCompleteTextView txtlegalEntity,txtProperty,txtMonth,txtYear,txtQuarter,txtLoc,txtL2,txtL3,txtL4,txtL5,txtL6;
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
    private String strLegalEntity,strProperty,strMonth,strYear,strQuarter,strLoc,strlevel2,strlevel3,strlevel4,strlevel5,strlevel6;
    private String legalEntityString,propertyString,monthString,yearString,quarterString,locString;
    private List<String> level2list,level3list,level4list,level5list,level6list;
    private CardView cardlevel3,cardlevel4,cardlevel5,cardlevel6;
    private TextInputLayout level3txtlayout,level4txtlayout,level5txtlayout,level6txtlayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        initView(view);
        return view;
    }

    void initView(View view) {
        sharedPref = new SharedPref(getContext());

        //................Auto Complete Text View......
        txtlegalEntity = (AutoCompleteTextView) view.findViewById(R.id.spinLegalEntity);
        txtProperty = (AutoCompleteTextView) view.findViewById(R.id.spinProperty);
        txtMonth = (AutoCompleteTextView) view.findViewById(R.id.spinMonth);
        txtYear = (AutoCompleteTextView) view.findViewById(R.id.spinYear);
        txtQuarter = (AutoCompleteTextView) view.findViewById(R.id.spinQuarter);
        txtLoc = (AutoCompleteTextView) view.findViewById(R.id.spinLoc);
        txtL2 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel2);
        txtL3 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel3);
        txtL4 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel4);
        txtL5 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel5);
        txtL6 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel6);

        cardlevel3 = (CardView) view.findViewById(R.id.level3cardview);
        cardlevel4 = (CardView) view.findViewById(R.id.level4cardview);
        cardlevel5 = (CardView) view.findViewById(R.id.level5cardview);
        cardlevel6 = (CardView) view.findViewById(R.id.level6cardview);

        level3txtlayout = (TextInputLayout)view.findViewById(R.id.level3txtlayout);
        level4txtlayout = (TextInputLayout)view.findViewById(R.id.level4txtlayout);
        level5txtlayout = (TextInputLayout)view.findViewById(R.id.level5txtlayout);
        level6txtlayout = (TextInputLayout)view.findViewById(R.id.level6txtlayout);

        fetchLevel2data();


       // txtLegalEntity = (TextView) view.findViewById(R.id.txtLegalEntity);
        //txtLegalEntity.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Legal Entity</font>" + "<font color=\"red\">*</font>\n"));
       // spinLegalEntity = (Spinner) view.findViewById(R.id.spinLegalEntity);
        //txtProperty = (TextView) view.findViewById(R.id.txtProperty);
       // txtProperty.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Property</font>" + "<font color=\"red\">*</font>\n"));
       // spinProperty = (Spinner) view.findViewById(R.id.spinProperty);
       // txtMonth = (TextView) view.findViewById(R.id.txtMonth);
        //txtMonth.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Month</font>" + "<font color=\"red\">*</font>\n"));
        //spinMonth = (Spinner) view.findViewById(R.id.spinMonth);
        //txtYear = (TextView) view.findViewById(R.id.txtYear);
        //txtYear.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Year</font>" + "<font color=\"red\">*</font>\n"));
        //spinYear = (Spinner) view.findViewById(R.id.spinYear);
        //txtQuarter = (TextView) view.findViewById(R.id.txtQuarter);
        //txtQuarter.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Quarter</font>" + "<font color=\"red\">*</font>\n"));
        //spinQuarter = (Spinner) view.findViewById(R.id.spinQuarter);
        //txtLocation = (TextView) view.findViewById(R.id.txtLoc);
        //txtLocation.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Location</font>" + "<font color=\"red\">*</font>\n"));
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
        listUpload = (ListView) view.findViewById(R.id.listViewUpload);
        uploadModelList = new ArrayList<>();
        uploadModel = new UploadModel();
        btnUpload = (Button) view.findViewById(R.id.btnUpload);

        List<String> listLegal = Arrays.asList(getResources().getStringArray(R.array.legal_entity));
        List<String> listProperty = Arrays.asList(getResources().getStringArray(R.array.property));
        List<String> listMonth = Arrays.asList(getResources().getStringArray(R.array.month));
        List<String> listYear = Arrays.asList(getResources().getStringArray(R.array.year));
        List<String> listQuarter = Arrays.asList(getResources().getStringArray(R.array.quarter));
        List<String> listLoc = Arrays.asList(getResources().getStringArray(R.array.location));

        if(sharedPref.getLoginId().equalsIgnoreCase("finance") && sharedPref.getPassword().equalsIgnoreCase("password")) {
          /*  listL2 = Arrays.asList(getResources().getStringArray(R.array.finance_type));

            dataAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_bg, listL2);
            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
            spinLevel2.setAdapter(dataAdapter);*/

        /*    spinLevel2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    valLevel2 = parent.getItemAtPosition(position).toString();

                    txtL4.setVisibility(View.GONE);
                    spinLevel4.setVisibility(View.GONE);
                    txtL5.setVisibility(View.GONE);
                    spinLevel5.setVisibility(View.GONE);
                    txtL6.setVisibility(View.GONE);
                    spinLevel6.setVisibility(View.GONE);

                    if(valLevel2.contains("Direct Taxation")) {
                        txtL3.setVisibility(View.VISIBLE);
                        spinLevel3.setVisibility(View.VISIBLE);

                        txtL3.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Direct Taxation</font>" + "<font color=\"red\">*</font>\n"));

                        listL3 = Arrays.asList(getResources().getStringArray(R.array.direct_taxation));

                        dataAdapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_bg, listL3);
                        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                        spinLevel3.setAdapter(dataAdapter);

                        spinLevel3.setFocusable(true);
                        spinLevel3.requestFocusFromTouch();
                    } else if(valLevel2.contains("IDTX")) {
                        txtL3.setVisibility(View.VISIBLE);
                        spinLevel3.setVisibility(View.VISIBLE);

                        txtL3.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">In-Direct Taxation</font>" + "<font color=\"red\">*</font>\n"));

                        listL3 = Arrays.asList(getResources().getStringArray(R.array.indirect_taxation));

                        dataAdapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_bg, listL3);
                        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                        spinLevel3.setAdapter(dataAdapter);

                        spinLevel3.setFocusable(true);
                        spinLevel3.requestFocusFromTouch();
                    } else if(valLevel2.contains("FNC")) {
                        txtL3.setVisibility(View.VISIBLE);
                        spinLevel3.setVisibility(View.VISIBLE);

                        txtL3.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Finance</font>" + "<font color=\"red\">*</font>\n"));
                        listL3 = Arrays.asList(getResources().getStringArray(R.array.finance));

                        dataAdapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_bg, listL3);
                        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                        spinLevel3.setAdapter(dataAdapter);

                        spinLevel3.setFocusable(true);
                        spinLevel3.requestFocusFromTouch();
                    } else if(valLevel2.contains("ACNTS")) {
                        txtL3.setVisibility(View.VISIBLE);
                        spinLevel3.setVisibility(View.VISIBLE);

                        txtL3.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Accounts</font>" + "<font color=\"red\">*</font>\n"));
                        listL3 = Arrays.asList(getResources().getStringArray(R.array.accounts));

                        dataAdapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_bg, listL3);
                        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                        spinLevel3.setAdapter(dataAdapter);

                        spinLevel3.setFocusable(true);
                        spinLevel3.requestFocusFromTouch();
                    } else if(valLevel2.contains("BNKG")) {
                        txtL3.setVisibility(View.VISIBLE);
                        spinLevel3.setVisibility(View.VISIBLE);

                        txtL3.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Banking</font>" + "<font color=\"red\">*</font>\n"));
                        listL3 = Arrays.asList(getResources().getStringArray(R.array.banking));

                        dataAdapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_bg, listL3);
                        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                        spinLevel3.setAdapter(dataAdapter);

                        spinLevel3.setFocusable(true);
                        spinLevel3.requestFocusFromTouch();
                    } else if(valLevel2.contains("AUDIT")) {
                        txtL3.setVisibility(View.VISIBLE);
                        spinLevel3.setVisibility(View.VISIBLE);

                        txtL3.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Audit</font>" + "<font color=\"red\">*</font>\n"));
                        listL3 = Arrays.asList(getResources().getStringArray(R.array.audit));

                        dataAdapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_bg, listL3);
                        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                        spinLevel3.setAdapter(dataAdapter);

                        spinLevel3.setFocusable(true);
                        spinLevel3.requestFocusFromTouch();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
*/


         /*   spinLevel3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    valLevel3 = parent.getItemAtPosition(position).toString();
                    txtL5.setVisibility(View.GONE);
                    spinLevel5.setVisibility(View.GONE);
                    txtL6.setVisibility(View.GONE);
                    spinLevel6.setVisibility(View.GONE);


                    if(txtL3.getText().toString().contains("Direct Taxation") && !txtL3.getText().toString().contains("In-Direct Taxation")) {
                        if (valLevel3.contains("ITR")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Income Tax Return</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.income_tax_return));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        } else if (valLevel3.contains("ASMT")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Assessment</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.assessment));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        } else if (valLevel3.contains("APL")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Appeal</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.assessment));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        } else if (valLevel3.contains("ETDR(NON)")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">E-tds Return</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.e_tds_non_salaries));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        } else if (valLevel3.contains("ETDR(S)")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">E-tds Return</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.e_tds_salaries));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        }
                    } else if(txtL3.getText().toString().contains("In-Direct Taxation")) {
                        if (valLevel3.contains("LOPU")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Location/Property/Unit</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.location_pro_unit));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        } if (valLevel3.contains("STE")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">State</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.state));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        }
                    } else if(txtL3.getText().toString().contains("Finance")) {
                        if (valLevel3.contains("LDL")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Lender List</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.lender_list));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        } else if (valLevel3.contains("ARCL")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">ARC List</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.arc_list));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        }
                    } else if(txtL3.getText().toString().contains("Accounts")) {
                        if (valLevel3.contains("RCVABL")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Receivable</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.receivable));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        } else if (valLevel3.contains("PYBL")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Payable</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.payable));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        } else if (valLevel3.contains("FXASTS")) {
                            txtL4.setVisibility(View.VISIBLE);
                            spinLevel4.setVisibility(View.VISIBLE);

                            txtL4.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Fixed Assets</font>" + "<font color=\"red\">*</font>\n"));

                            listL4 = Arrays.asList(getResources().getStringArray(R.array.fixed_assets));
                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL4);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel4.setAdapter(dataAdapter);

                            spinLevel4.setFocusable(true);
                            spinLevel4.requestFocusFromTouch();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

         /*   spinLevel4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    valLevel4 = parent.getItemAtPosition(position).toString();

                    txtL6.setVisibility(View.GONE);
                    spinLevel6.setVisibility(View.GONE);

                    if(txtL4.getText().toString().contains("Assessment")){
                        if (valLevel4.contains("SRT")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Scrutiny</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.scrutiny));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("CIT-A")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">CIT-A</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.cit_a));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("ITAT")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">ITAT</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.itat));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("HCRT")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">High Court</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.high_court));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("SPRMCT")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Supreme Court</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.supreme_court));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        }
                    } else if(txtL4.getText().toString().contains("Location/Property/Unit")) {
                        if (valLevel4.contains("FRMTNC")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Foreign Remittances</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.foreign_remittances));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("EMPTDS")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Employee TDS</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.employee_tds));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("SRTX")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Service Tax</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.service_tax));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        }
                    } else if(txtL4.getText().toString().contains("State")) {
                        if (valLevel4.contains("CTREX")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Central Excise</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.central_excise));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("VAT")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">VAT</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.vat));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("LXRTX")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Luxury Tax</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.luxury_tax));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("PFSTX")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Profession Tax</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.profession_tax));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("LCLBDTX")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Local Body Tax</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.local_body_tax));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("GST")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">GST</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.gst));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        } else if (valLevel4.contains("CUTMS")) {
                            txtL5.setVisibility(View.VISIBLE);
                            spinLevel5.setVisibility(View.VISIBLE);

                            txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Customs</font>" + "<font color=\"red\">*</font>\n"));

                            listL5 = Arrays.asList(getResources().getStringArray(R.array.customs));

                            dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.spinner_bg, listL5);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                            spinLevel5.setAdapter(dataAdapter);

                            spinLevel5.setFocusable(true);
                            spinLevel5.requestFocusFromTouch();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

           /* spinLevel5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    valLevel5 = parent.getItemAtPosition(position).toString();
                    if (valLevel5.contains("LISNS")) {
                        txtL6.setVisibility(View.VISIBLE);
                        spinLevel6.setVisibility(View.VISIBLE);

                        txtL5.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Licenses</font>" + "<font color=\"red\">*</font>\n"));

                        listL6 = Arrays.asList(getResources().getStringArray(R.array.licenses));

                        dataAdapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_bg, listL6);
                        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
                        spinLevel6.setAdapter(dataAdapter);

                        spinLevel6.setFocusable(true);
                        spinLevel6.requestFocusFromTouch();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
        }


        /////AutoCompletetextview set values

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
            txtlegalEntity.setAdapter(adapter1);
        }

        txtlegalEntity.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtlegalEntity.showDropDown();
                return false;
            }
        });

        txtlegalEntity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strLegalArray != null && strLegalArray.length > 0) {
                    strLegalEntity = txtlegalEntity.getText().toString();
                    legalEntityString = parent.getItemAtPosition(position).toString();

                }

            }
        });
        //////////////////////////////
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
            txtProperty.setAdapter(adapter1);
        }

        txtProperty.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtProperty.showDropDown();
                return false;
            }
        });

        txtProperty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strPropertyArray != null && strPropertyArray.length > 0) {
                    strProperty = txtProperty.getText().toString();
                    propertyString = parent.getItemAtPosition(position).toString();

                }

            }
        });
        ///////////////////////////////

        ///////////////Details of Month
        if (listMonth.size() > 0) {
            strMonthArray = new String[listMonth.size()];
            for (int i = 0; i < listMonth.size(); i++) {
                strMonthArray[i] = listMonth.get(i);
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
            txtMonth.setAdapter(adapter1);
        }

        txtMonth.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtMonth.showDropDown();
                return false;
            }
        });

        txtMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strMonthArray != null && strMonthArray.length > 0) {
                    strMonth = txtMonth.getText().toString();
                    monthString = parent.getItemAtPosition(position).toString();

                }

            }
        });
        ///////////////////////////////
        ///////////////Details of Year
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
            txtYear.setAdapter(adapter1);
        }

        txtYear.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtYear.showDropDown();
                return false;
            }
        });

        txtYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strYearArray != null && strYearArray.length > 0) {
                    strYear = txtYear.getText().toString();
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
            txtQuarter.setAdapter(adapter1);
        }

        txtQuarter.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtQuarter.showDropDown();
                return false;
            }
        });

        txtQuarter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strQuarterArray != null && strQuarterArray.length > 0) {
                    strQuarter = txtQuarter.getText().toString();
                    quarterString = parent.getItemAtPosition(position).toString();

                }

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
            txtLoc.setAdapter(adapter1);
        }

        txtLoc.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtLoc.showDropDown();
                return false;
            }
        });

        txtLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strLocArray != null && strLocArray.length > 0) {
                    strLoc = txtLoc.getText().toString();
                    locString = parent.getItemAtPosition(position).toString();

                }

            }
        });
        ///////////////////////////////
        //////////////////Level 2 details

        txtL2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtL3.setVisibility(View.GONE);
                cardlevel3.setVisibility(View.GONE);
                txtL4.setVisibility(View.GONE);
                cardlevel4.setVisibility(View.GONE);
                txtL5.setVisibility(View.GONE);
                cardlevel5.setVisibility(View.GONE);
                txtL6.setVisibility(View.GONE);
                cardlevel6.setVisibility(View.GONE);
                txtL3.setText("");
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
                if (strLevel2Array != null && strLevel2Array.length > 0) {
                    strlevel2 = txtL2.getText().toString();

                    valLevel2 = parent.getItemAtPosition(position).toString();

                    if (valLevel2 != null && valLevel2.length() > 0) {
                        if (valLevel2.equalsIgnoreCase("Direct Taxation")||
                                valLevel2.equalsIgnoreCase("In-Direct Taxation")||
                                valLevel2.equalsIgnoreCase("Finance")||
                                valLevel2.equalsIgnoreCase("Accounts")||
                                valLevel2.equalsIgnoreCase("Banking")||
                                valLevel2.equalsIgnoreCase("Audit")) {
                            fetchLevel3data();

                            level3txtlayout.setHint(valLevel2);
                            txtL3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    level3txtlayout.setHint(valLevel2);
                                }
                            });
                            txtL3.setVisibility(View.VISIBLE);
                            cardlevel3.setVisibility(View.VISIBLE);
                        }
                    }

                    }
            }
        });

        ///////////////////////////////////////
        //////////////////Level 3 details

        txtL3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtL4.setVisibility(View.GONE);
                cardlevel4.setVisibility(View.GONE);
                txtL5.setVisibility(View.GONE);
                cardlevel5.setVisibility(View.GONE);
                txtL6.setVisibility(View.GONE);
                cardlevel6.setVisibility(View.GONE);
                txtL4.setText("");
                valLevel4 = "";
                valLevel5 = "";
                valLevel6 = "";
                txtL3.showDropDown();
                return false;
            }
        });

        txtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strLevel3Array != null && strLevel3Array.length > 0) {
                    strlevel3 = txtL3.getText().toString();
                    valLevel3 = parent.getItemAtPosition(position).toString();

                    if (valLevel3 != null && valLevel3.length() > 0) {
                        if( valLevel2.equalsIgnoreCase("Banking")||
                                valLevel2.equalsIgnoreCase("Audit")) {
                            if (valLevel3.equalsIgnoreCase("Daily Finance Memo") ||
                                    valLevel3.equalsIgnoreCase("Monthly CashFlow") ||
                                    valLevel3.equalsIgnoreCase("Investments")||
                                    valLevel3.equalsIgnoreCase("Statutory Audit") ||
                                    valLevel3.equalsIgnoreCase("Internal Audit") ||
                                    valLevel3.equalsIgnoreCase("Concurrent Audit")) {

                                txtL4.setVisibility(View.GONE);
                                cardlevel4.setVisibility(View.GONE);
                            }
                        }else{
                            fetchLevel4data();

                            level4txtlayout.setHint(valLevel3);
                            txtL4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    level4txtlayout.setHint(valLevel3);
                                }
                            });
                            txtL4.setVisibility(View.VISIBLE);
                            cardlevel4.setVisibility(View.VISIBLE);
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
                txtL5.setVisibility(View.GONE);
                cardlevel5.setVisibility(View.GONE);
                txtL6.setVisibility(View.GONE);
                cardlevel6.setVisibility(View.GONE);
                txtL5.setText("");
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
                    strlevel4 = txtL4.getText().toString();
                    valLevel4 = parent.getItemAtPosition(position).toString();

                    if (valLevel4 != null && valLevel4.length() > 0) {
                        if (valLevel3.equalsIgnoreCase("Income Tax Return") ||valLevel3.equalsIgnoreCase("Assessment")||
                                valLevel3.equalsIgnoreCase("E-tds Return (Non-salary)")||valLevel3.equalsIgnoreCase("E-tds Return (Salaries)")||
                                valLevel3.equalsIgnoreCase("Lender List")||valLevel3.equalsIgnoreCase("ARC List")||
                                valLevel3.equalsIgnoreCase("Receivable")||valLevel3.equalsIgnoreCase("Payable")||
                                valLevel3.equalsIgnoreCase("Fixed Assets")) {
                            if (valLevel4.equalsIgnoreCase("Acknowledgement") ||valLevel4.equalsIgnoreCase("ITR") ||
                                    valLevel4.equalsIgnoreCase("Advance tax Payment") ||valLevel4.equalsIgnoreCase("Self Assessment Tax Payment") ||
                                    valLevel4.equalsIgnoreCase("Dividend Tax Payment") ||valLevel4.equalsIgnoreCase("TAN Details") ||
                                    valLevel4.equalsIgnoreCase("Lower deduction") ||valLevel4.equalsIgnoreCase("TDS Challan payment receipt monthly") ||
                                    valLevel4.equalsIgnoreCase("Form 16") ||valLevel4.equalsIgnoreCase("Quarterly Return") ||
                                    valLevel4.equalsIgnoreCase("FVV File") ||valLevel4.equalsIgnoreCase("Revised Return") ||
                                    valLevel4.equalsIgnoreCase("Notice") ||valLevel4.equalsIgnoreCase("Application / Proposals") ||
                                    valLevel4.equalsIgnoreCase("Valuations") ||valLevel4.equalsIgnoreCase("Sanction letter") ||
                                    valLevel4.equalsIgnoreCase("Loan agreements") ||valLevel4.equalsIgnoreCase("Disb requests") ||
                                    valLevel4.equalsIgnoreCase("security creation") ||valLevel4.equalsIgnoreCase("Registration") ||
                                    valLevel4.equalsIgnoreCase("Charge creation / satisfaction") ||valLevel4.equalsIgnoreCase("Restructuring") ||
                                    valLevel4.equalsIgnoreCase("Payments") ||valLevel4.equalsIgnoreCase("Disputes") ||
                                    valLevel4.equalsIgnoreCase("OTS") ||valLevel4.equalsIgnoreCase("Assignment") ||
                                    valLevel4.equalsIgnoreCase("Offer") ||valLevel4.equalsIgnoreCase("Settlement") ||
                                    valLevel4.equalsIgnoreCase("Prepayment") ||valLevel4.equalsIgnoreCase("Rent") ||
                                    valLevel4.equalsIgnoreCase("Interest") ||valLevel4.equalsIgnoreCase("DSR") ||
                                    valLevel4.equalsIgnoreCase("Royalty fee") ||valLevel4.equalsIgnoreCase("Franchisee fee") ||
                                    valLevel4.equalsIgnoreCase("Management fee") ||valLevel4.equalsIgnoreCase("AMC") ||
                                    valLevel4.equalsIgnoreCase("Dividends") ||valLevel4.equalsIgnoreCase("Others") ||
                                    valLevel4.equalsIgnoreCase("Contractors") ||valLevel4.equalsIgnoreCase("PO") ||
                                    valLevel4.equalsIgnoreCase("Retainer") ||valLevel4.equalsIgnoreCase("Paysheet") ||
                                    valLevel4.equalsIgnoreCase("Interest") ||valLevel4.equalsIgnoreCase("Capex") ||
                                    valLevel4.equalsIgnoreCase("Purchase bills") ||valLevel4.equalsIgnoreCase("Sale Bills") ||
                                    valLevel4.equalsIgnoreCase("Depreciation") ||valLevel4.equalsIgnoreCase("Physical Verification")
                                    ) {

                                txtL5.setVisibility(View.GONE);
                                cardlevel5.setVisibility(View.GONE);
                            }
                        }else{
                            fetchLevel5data();

                            level5txtlayout.setHint(valLevel4);
                            txtL5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    level5txtlayout.setHint(valLevel4);
                                }
                            });
                            txtL5.setVisibility(View.VISIBLE);
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
                txtL6.setVisibility(View.GONE);
                cardlevel6.setVisibility(View.GONE);
                txtL6.setText("");
                valLevel6 = "";
                txtL5.showDropDown();
                return false;
            }
        });

        txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strLevel5Array != null && strLevel5Array.length > 0) {
                    strlevel5 = txtL5.getText().toString();
                    valLevel5 = parent.getItemAtPosition(position).toString();

                    if (valLevel5 != null && valLevel5.length() > 0) {
                        if (valLevel4.equalsIgnoreCase("Customs")&&
                                valLevel5.equalsIgnoreCase("Licenses")) {

                                fetchLevel6data();

                                level6txtlayout.setHint(valLevel5);
                                txtL6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                    @Override
                                    public void onFocusChange(View v, boolean hasFocus) {
                                        level6txtlayout.setHint(valLevel5);
                                    }
                                });
                                txtL6.setVisibility(View.VISIBLE);
                                cardlevel6.setVisibility(View.VISIBLE);
                            }else{
                            txtL6.setVisibility(View.GONE);
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
                txtL6.showDropDown();
                return false;
            }
        });

        txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strLevel6Array != null && strLevel6Array.length > 0) {
                    strlevel6 = txtL6.getText().toString();
                    valLevel6 = parent.getItemAtPosition(position).toString();
                }
            }
        });

        ///////////////////////////////////////


      /*  dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listLegal);
        //android.R.layout.simple_spinner_dropdown_item
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinLegalEntity.setAdapter(dataAdapter);*/

       /* dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listProperty);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinProperty.setAdapter(dataAdapter);*/

        /*dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listMonth);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinMonth.setAdapter(dataAdapter);*/

       /* dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listYear);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinYear.setAdapter(dataAdapter);*/

       /* dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listQuarter);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinQuarter.setAdapter(dataAdapter);*/

       /* dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listLoc);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinLocation.setAdapter(dataAdapter);*/




        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uploadModelList = setUploadModelList();
                    adapter = new UploadAdapter(getActivity(), uploadModelList);
                    listUpload.setAdapter(adapter);
                    setListViewHeightBasedOnItems(listUpload);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void fetchLevel2data() {
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

    private void fetchLevel3data() {
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
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level4,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level4"));
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
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level5"));
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
                txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6data() {
        try {

            level6list = new ArrayList<>();

            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
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
                txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<UploadModel> setUploadModelList() {
        List<UploadModel> uploadModels = new ArrayList<>();
        uploadModel = getUploadModel();
        uploadModels.add(uploadModel);
        return uploadModels;
    }

    private UploadModel getUploadModel() {
        uploadModel.setLevel2(valLevel2);
        uploadModel.setLevel3(valLevel3);
        uploadModel.setLevel4(valLevel4);
        uploadModel.setLevel5(valLevel5);
        uploadModel.setLevel6(valLevel6);
        return uploadModel;
    }

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
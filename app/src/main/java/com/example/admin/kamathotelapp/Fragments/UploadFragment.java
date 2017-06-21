package com.example.admin.kamathotelapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Adapters.UploadAdapter;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.SharedPref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UploadFragment extends Fragment {

    private TextView txtLegalEntity, txtProperty, txtMonth, txtYear, txtQuarter, txtLocation, txtType;
    private Spinner spinLegalEntity, spinProperty, spinMonth, spinYear, spinQuarter, spinLocation, spinType;
    private Spinner spinLevel2, spinLevel3, spinLevel4, spinLevel5, spinLevel6;
    ArrayAdapter<String> dataAdapter;
    private SharedPref sharedPref;
    List<String> listL2, listL3, listL4, listL5, listL6;
    Button btnUpload;
    ListView listUpload;
    List<UploadModel> uploadModelList;
    UploadModel uploadModel;
    private UploadAdapter adapter;
    private String valLevel2, valLevel3, valLevel4, valLevel5, valLevel6;
    private TextView txtL2, txtL3, txtL4, txtL5, txtL6;

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
        txtLegalEntity = (TextView) view.findViewById(R.id.txtLegalEntity);
        txtLegalEntity.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Legal Entity</font>" + "<font color=\"red\">*</font>\n"));
        spinLegalEntity = (Spinner) view.findViewById(R.id.spinLegalEntity);
        txtProperty = (TextView) view.findViewById(R.id.txtProperty);
        txtProperty.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Property</font>" + "<font color=\"red\">*</font>\n"));
        spinProperty = (Spinner) view.findViewById(R.id.spinProperty);
        txtMonth = (TextView) view.findViewById(R.id.txtMonth);
        txtMonth.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Month</font>" + "<font color=\"red\">*</font>\n"));
        spinMonth = (Spinner) view.findViewById(R.id.spinMonth);
        txtYear = (TextView) view.findViewById(R.id.txtYear);
        txtYear.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Year</font>" + "<font color=\"red\">*</font>\n"));
        spinYear = (Spinner) view.findViewById(R.id.spinYear);
        txtQuarter = (TextView) view.findViewById(R.id.txtQuarter);
        txtQuarter.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Quarter</font>" + "<font color=\"red\">*</font>\n"));
        spinQuarter = (Spinner) view.findViewById(R.id.spinQuarter);
        txtLocation = (TextView) view.findViewById(R.id.txtLoc);
        txtLocation.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Location</font>" + "<font color=\"red\">*</font>\n"));
        spinLocation = (Spinner) view.findViewById(R.id.spinLoc);
        txtType = (TextView) view.findViewById(R.id.txtType);
        txtType.setText(Html.fromHtml("<font color=\"@color/colorPrimaryDark\">Type</font>" + "<font color=\"red\">*</font>\n"));
        spinType = (Spinner) view.findViewById(R.id.spinType);
        txtL2 = (TextView) view.findViewById(R.id.txtLevel2);
        spinLevel2 = (Spinner) view.findViewById(R.id.spinLevel2);
        txtL3 = (TextView) view.findViewById(R.id.txtLevel3);
        spinLevel3 =(Spinner) view.findViewById(R.id.spinLevel3);
        txtL4 = (TextView) view.findViewById(R.id.txtLevel4);
        spinLevel4 = (Spinner) view.findViewById(R.id.spinLevel4);
        txtL5 = (TextView) view.findViewById(R.id.txtLevel5);
        spinLevel5 = (Spinner) view.findViewById(R.id.spinLevel5);
        txtL6 = (TextView) view.findViewById(R.id.txtLevel6);
        spinLevel6 = (Spinner) view.findViewById(R.id.spinLevel6);
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
        List<String> listType = Arrays.asList(getResources().getStringArray(R.array.type));

        if(sharedPref.getLoginId().equalsIgnoreCase("finance") && sharedPref.getPassword().equalsIgnoreCase("password")) {
            listL2 = Arrays.asList(getResources().getStringArray(R.array.finance_type));

            dataAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_bg, listL2);
            dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
            spinLevel2.setAdapter(dataAdapter);

            spinLevel2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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



            spinLevel3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            });

            spinLevel4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            });

            spinLevel5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            });
        }




        dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listLegal);
        //android.R.layout.simple_spinner_dropdown_item
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinLegalEntity.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listProperty);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinProperty.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listMonth);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinMonth.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listYear);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinYear.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listQuarter);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinQuarter.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listLoc);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinLocation.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_bg, listType);
        dataAdapter.setDropDownViewResource(R.layout.spinner_bg);
        spinType.setAdapter(dataAdapter);







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

    private List<UploadModel> setUploadModelList() {
        List<UploadModel> uploadModels = new ArrayList<>();
        uploadModel = getUploadModel();
        uploadModels.add(uploadModel);
        return uploadModels;
    }

    private UploadModel getUploadModel() {
        uploadModel.setLevel2(spinLevel2.getSelectedItem().toString());
        uploadModel.setLevel3(spinLevel3.getSelectedItem().toString());
        uploadModel.setLevel4(spinLevel4.getSelectedItem().toString());
        uploadModel.setLevel5("No data");
        uploadModel.setLevel6("No data");
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

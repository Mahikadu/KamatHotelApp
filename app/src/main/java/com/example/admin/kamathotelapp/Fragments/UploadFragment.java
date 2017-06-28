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
    List<String> listMonth;
    private UploadAdapter adapter;
    private String valLevel2, valLevel3, valLevel4, valLevel5, valLevel6, valLevel7;
    private AutoCompleteTextView txtlegalEntity,txtProperty,txtMonth,txtYear,txtQuarter,txtLoc,txtL2,txtL3,txtL4,txtL5,txtL6,txtL7;
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
    private String strLegalEntity,strProperty,strMonth,strYear,strQuarter,strLoc,strlevel2,strlevel3,strlevel4,strlevel5,strlevel6,strlevel7;
    private String legalEntityString,propertyString,monthString,yearString,quarterString="",locString;
    private List<String> level2list,level3list,level4list,level5list,level6list;
    private List<String> level4listHR, level5listHR, level6listHR;
    private List<String> level4listCMD, level5listCMD, level6listCMD, level7listCMD;
    private List<String> level2listCS,level3listCS,level4listCS;
    private List<String> level4listMAR, level5listMAR, level6listMAR, level7listMAR;
    private List<String> level3listPER, level4listPER, level5listPER;
    String chk = "";
    private CardView cardlevel2, cardlevel3,cardlevel4,cardlevel5,cardlevel6,cardlevel7;
    private TextInputLayout level2txtlayout, level3txtlayout,level4txtlayout,level5txtlayout,level6txtlayout,level7txtlayout;

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
        txtL7 = (AutoCompleteTextView) view.findViewById(R.id.spinLevel7);

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



        if(sharedPref.getLoginId().equalsIgnoreCase("finance") && sharedPref.getPassword().equalsIgnoreCase("password")) {
            fetchLevel2dataFin();
            txtL2.setHint("Financial Type");

            txtL2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL4.setVisibility(View.GONE);
                    cardlevel4.setVisibility(View.GONE);
                    txtL5.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
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
                    level2txtlayout.setHint("Financial Type");
                    if (strLevel2Array != null && strLevel2Array.length > 0) {
                        strlevel2 = txtL2.getText().toString();

                        valLevel2 = parent.getItemAtPosition(position).toString();

                        if (valLevel2 != null && valLevel2.length() > 0) {
                            fetchLevel3dataFin();
                            level3txtlayout.setHint(valLevel2);
                            txtL3.setVisibility(View.VISIBLE);
                            cardlevel3.setVisibility(View.VISIBLE);
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
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
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
                            fetchLevel4data();
                            if(chk.equals("no")) {
                                txtL4.setVisibility(View.GONE);
                                cardlevel4.setVisibility(View.GONE);
                            } else {
                                txtL4.setVisibility(View.VISIBLE);
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
                    txtL5.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
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
                            fetchLevel5data();
                            if(chk.equals("no")) {
                                txtL5.setVisibility(View.GONE);
                                cardlevel5.setVisibility(View.GONE);
                            } else if(chk.equals("yes")) {
                                level5txtlayout.setHint(valLevel4);
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
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
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
                            fetchLevel6data();
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                txtL6.setVisibility(View.VISIBLE);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
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
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
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
        } else if(sharedPref.getLoginId().equalsIgnoreCase("hr") && sharedPref.getPassword().equalsIgnoreCase("password")) {
            txtL2.setVisibility(View.GONE);
            cardlevel2.setVisibility(View.GONE);
            txtL3.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            txtL4.setVisibility(View.VISIBLE);
            cardlevel4.setVisibility(View.VISIBLE);
            txtL4.setHint("HR");

            fetchLevel4dataHR();

            txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL5.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
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
                        strlevel4 = txtL4.getText().toString();

                        valLevel4 = parent.getItemAtPosition(position).toString();


                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataHR();
                            level5txtlayout.setHint(valLevel4);
                            txtL5.setVisibility(View.VISIBLE);
                            cardlevel5.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayHR != null && strLevel5ArrayHR.length > 0) {
                        strlevel5 = txtL5.getText().toString();

                        valLevel5 = parent.getItemAtPosition(position).toString();


                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataHR();
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                txtL6.setVisibility(View.VISIBLE);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                txtL6.setVisibility(View.GONE);
                                cardlevel6.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                    txtL6.showDropDown();
                    return false;
                }
            });
        } else if(sharedPref.getLoginId().equalsIgnoreCase("cmd") && sharedPref.getPassword().equalsIgnoreCase("password")) {
            txtL2.setVisibility(View.GONE);
            cardlevel2.setVisibility(View.GONE);
            txtL3.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            txtL4.setVisibility(View.VISIBLE);
            cardlevel4.setVisibility(View.VISIBLE);
            txtL4.setHint("Type");

            fetchLevel4dataCMD();

            txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL5.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
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
                        strlevel4 = txtL4.getText().toString();

                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataCMD();
                            level5txtlayout.setHint(valLevel4);
                            txtL5.setVisibility(View.VISIBLE);
                            cardlevel5.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayCMD != null && strLevel5ArrayCMD.length > 0) {
                        strlevel5 = txtL5.getText().toString();

                        valLevel5 = parent.getItemAtPosition(position).toString();


                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataCMD();
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                txtL6.setVisibility(View.VISIBLE);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                txtL6.setVisibility(View.GONE);
                                cardlevel6.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                    txtL6.showDropDown();
                    return false;
                }
            });

            txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayCMD != null && strLevel6ArrayCMD.length > 0) {
                        strlevel6 = txtL6.getText().toString();

                        valLevel6 = parent.getItemAtPosition(position).toString();

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataCMD();
                            if(chk.equals("yes")) {
                                level7txtlayout.setHint(valLevel5);
                                txtL7.setVisibility(View.VISIBLE);
                                cardlevel7.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                txtL7.setVisibility(View.GONE);
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
        } else if(sharedPref.getLoginId().equalsIgnoreCase("cs") && sharedPref.getPassword().equalsIgnoreCase("password")) {
            fetchLevel2dataCS();
            txtL2.setHint("Type");

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
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL3.setText("");
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
                        strlevel2 = txtL2.getText().toString();

                        valLevel2 = parent.getItemAtPosition(position).toString();

                        if (valLevel2 != null && valLevel2.length() > 0) {
                            fetchLevel3dataCS();
                            level3txtlayout.setHint(valLevel2);
                            txtL3.setVisibility(View.VISIBLE);
                            cardlevel3.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            txtL3.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL4.setVisibility(View.GONE);
                    cardlevel4.setVisibility(View.GONE);
                    txtL5.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.setText("");
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
                        strlevel3 = txtL3.getText().toString();
                        valLevel3 = parent.getItemAtPosition(position).toString();

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4dataCS();
                            if(chk.equals("no")) {
                                txtL4.setVisibility(View.GONE);
                                cardlevel4.setVisibility(View.GONE);
                            } else {
                                txtL4.setVisibility(View.VISIBLE);
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
                    return false;
                }
            });
        } else if(sharedPref.getLoginId().equalsIgnoreCase("marketing") && sharedPref.getPassword().equalsIgnoreCase("password")) {
            txtL2.setVisibility(View.GONE);
            cardlevel2.setVisibility(View.GONE);
            txtL3.setVisibility(View.GONE);
            cardlevel3.setVisibility(View.GONE);

            txtL4.setVisibility(View.VISIBLE);
            cardlevel4.setVisibility(View.VISIBLE);
            txtL4.setHint("Occupancy");
            fetchLevel4dataMAR();

            txtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL5.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    txtL5.setText("");
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
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
                        strlevel4 = txtL4.getText().toString();

                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataMAR();
                            level5txtlayout.setHint(valLevel4);
                            txtL5.setVisibility(View.VISIBLE);
                            cardlevel5.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL5.showDropDown();
                    return false;
                }
            });

            txtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel5ArrayMAR != null && strLevel5ArrayMAR.length > 0) {
                        strlevel5 = txtL5.getText().toString();

                        valLevel5 = parent.getItemAtPosition(position).toString();

                        if (valLevel5 != null && valLevel5.length() > 0) {
                            fetchLevel6dataMAR();
                            if(chk.equals("yes")) {
                                level6txtlayout.setHint(valLevel5);
                                txtL6.setVisibility(View.VISIBLE);
                                cardlevel6.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                txtL6.setVisibility(View.GONE);
                                cardlevel6.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            txtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL7.setText("");
                    txtL6.showDropDown();
                    return false;
                }
            });

            txtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (strLevel6ArrayMAR != null && strLevel6ArrayMAR.length > 0) {
                        strlevel6 = txtL6.getText().toString();

                        valLevel6 = parent.getItemAtPosition(position).toString();

                        if (valLevel6 != null && valLevel6.length() > 0) {
                            fetchLevel7dataMAR();
                            if(chk.equals("yes")) {
                                level7txtlayout.setHint(valLevel6);
                                txtL7.setVisibility(View.VISIBLE);
                                cardlevel7.setVisibility(View.VISIBLE);
                            } else if(chk.equals("no")) {
                                txtL7.setVisibility(View.GONE);
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

        } else if(sharedPref.getLoginId().equalsIgnoreCase("Personal") && sharedPref.getPassword().equalsIgnoreCase("password")) {
            txtL2.setVisibility(View.GONE);
            cardlevel2.setVisibility(View.GONE);

            txtL3.setVisibility(View.VISIBLE);
            cardlevel3.setVisibility(View.VISIBLE);
            txtL3.setHint("Type");

            fetchLevel3dataPER();

            txtL3.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL4.setVisibility(View.GONE);
                    cardlevel4.setVisibility(View.GONE);
                    txtL5.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL4.setText("");
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
                        strlevel3 = txtL3.getText().toString();
                        valLevel3 = parent.getItemAtPosition(position).toString();

                        if (valLevel3 != null && valLevel3.length() > 0) {
                            fetchLevel4dataPER();
                            if(chk.equals("no")) {
                                txtL4.setVisibility(View.GONE);
                                cardlevel4.setVisibility(View.GONE);
                            } else {
                                txtL4.setVisibility(View.VISIBLE);
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
                    txtL5.setVisibility(View.GONE);
                    cardlevel5.setVisibility(View.GONE);
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL5.setText("");
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
                        strlevel4 = txtL4.getText().toString();
                        valLevel4 = parent.getItemAtPosition(position).toString();

                        if (valLevel4 != null && valLevel4.length() > 0) {
                            fetchLevel5dataPER();
                            if(chk.equals("no")) {
                                txtL5.setVisibility(View.GONE);
                                cardlevel5.setVisibility(View.GONE);
                            } else if(chk.equals("yes")) {
                                level5txtlayout.setHint(valLevel4);
                                txtL5.setVisibility(View.VISIBLE);
                                cardlevel5.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                }
            });

            txtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtL6.setVisibility(View.GONE);
                    cardlevel6.setVisibility(View.GONE);
                    txtL7.setVisibility(View.GONE);
                    cardlevel7.setVisibility(View.GONE);
                    txtL6.setText("");
                    txtL5.showDropDown();
                    return false;
                }
            });
        }
        ///////////////////////////////////////

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

    private void fetchLevel2dataFin() {
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

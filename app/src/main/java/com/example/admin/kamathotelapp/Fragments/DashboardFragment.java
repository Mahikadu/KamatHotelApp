package com.example.admin.kamathotelapp.Fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Adapters.DashboardAdapter;
import com.example.admin.kamathotelapp.Model.DashboardModel;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {

    private AutoCompleteTextView autoLegal, autoProp, autoLoc, autoYr, autoQuarter, autoMonth;
    private LinearLayout detailsLayout;
    DashboardModel dashboardModel;
    List<DashboardModel> dashboardModelList;
    DashboardAdapter dashboardAdapter;
    private Button search,loadRecord;
    private TableLayout tableLayout;
    private String strQuarter="";
    String[] strLegalArray = null;
    String[] strPropertyArray = null;
    String[] strMonthArray = null;
    String[] strYearArray = null;
    String[] strQuarterArray = null;
    String[] strLocArray = null;
    String[] strLevel2Array = null;
    String[] strLevel3Array = null;
    List<String> listMonth;
    ListView listDashboard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getActivity().setTitle("Dashboard");
        //////////Crash Report
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView(view);
        return view;
    }

    void initView(View view) {
        tableLayout = (TableLayout) view.findViewById(R.id.tableLaout_lead);
        detailsLayout = (LinearLayout)view.findViewById(R.id.detailsLayout);
        search = (Button)view.findViewById(R.id.btnSearch);
        loadRecord = (Button)view.findViewById(R.id.btnLoad);
        autoLegal = (AutoCompleteTextView)view.findViewById(R.id.autoLegalEntity);
        autoProp = (AutoCompleteTextView)view.findViewById(R.id.autoProperty);
        autoLoc = (AutoCompleteTextView)view.findViewById(R.id.autoLocation);
        autoYr = (AutoCompleteTextView)view.findViewById(R.id.autoYear);
        autoQuarter = (AutoCompleteTextView)view.findViewById(R.id.autoQuarter);
        autoMonth = (AutoCompleteTextView)view.findViewById(R.id.autoMonth);
        listDashboard = (ListView) view.findViewById(R.id.listDashboard);

        dashboardModel  = new DashboardModel();
        dashboardModelList = new ArrayList<>();
        dashboardModel.setRole("HR");
        dashboardModel.setDocNo("5_6/1/2017 1:59:43 PM_366d6bb9-b186-43ce-9b2c-682e5deb81bd");
        dashboardModel.setFileName("cd7524eb-4929-42e9-8706-34c2433e84f2_Penguins.jpg");
        dashboardModel.setCreatedBy("hr");
        dashboardModel.setCreatedDate("2017-06-02");
        dashboardModel.setYear("2017");
        dashboardModel.setMonth("June");
        dashboardModel.setStatus("Approved");
        dashboardModelList.add(dashboardModel);
        dashboardAdapter = new DashboardAdapter(getContext(), dashboardModelList);
        listDashboard.setAdapter(dashboardAdapter);

        List<String> listLegal = Arrays.asList(getResources().getStringArray(R.array.legal_entity));
        List<String> listProperty = Arrays.asList(getResources().getStringArray(R.array.property));
        List<String> listYear = Arrays.asList(getResources().getStringArray(R.array.year));
        List<String> listQuarter = Arrays.asList(getResources().getStringArray(R.array.quarter));
        List<String> listLoc = Arrays.asList(getResources().getStringArray(R.array.location));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsLayout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
        });
        loadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsLayout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }
        });

        if (listLegal.size() > 0) {
            strLegalArray = new String[listLegal.size()];
            //   strLeadArray[0] = "Select Source Lead";
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
            autoLegal.setAdapter(adapter1);
        }

        autoLegal.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoLegal.showDropDown();
                return false;
            }
        });

        if (listProperty.size() > 0) {
            strPropertyArray = new String[listProperty.size()];
            //   strLeadArray[0] = "Select Source Lead";
            for (int i = 0; i < listProperty.size(); i++) {
                strPropertyArray[i] = listProperty.get(i);
            }
        }

        if (listProperty != null && listProperty.size() > 0) {

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strPropertyArray) {
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

            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autoProp.setAdapter(adapter2);
        }

        autoProp.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoProp.showDropDown();
                return false;
            }
        });

        if (listLoc.size() > 0) {
            strLocArray = new String[listLoc.size()];
            //   strLeadArray[0] = "Select Source Lead";
            for (int i = 0; i < listLoc.size(); i++) {
                strLocArray[i] = listLoc.get(i);
            }
        }

        if (listLoc != null && listLoc.size() > 0) {

            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strLocArray) {
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

            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autoLoc.setAdapter(adapter3);
        }

        autoLoc.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoLoc.showDropDown();
                return false;
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

            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strYearArray) {
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

            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autoYr.setAdapter(adapter4);
        }

        autoYr.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoYr.showDropDown();
                return false;
            }
        });

        if (listQuarter.size() > 0) {
            strQuarterArray = new String[listQuarter.size()];
            //   strLeadArray[0] = "Select Source Lead";
            for (int i = 0; i < listQuarter.size(); i++) {
                strQuarterArray[i] = listQuarter.get(i);
            }
        }

        if (listQuarter != null && listQuarter.size() > 0) {

            ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strQuarterArray) {
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

            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autoQuarter.setAdapter(adapter5);
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
                if (strQuarterArray != null && strQuarterArray.length > 0) {

                    strQuarter = parent.getItemAtPosition(position).toString();

                    if(strQuarter.equalsIgnoreCase("Quarter 1")) {
                        listMonth = Arrays.asList(getResources().getStringArray(R.array.month_q1));
                    } else if(strQuarter.equalsIgnoreCase("Quarter 2")) {
                        listMonth = Arrays.asList(getResources().getStringArray(R.array.month_q2));
                    } else if(strQuarter.equalsIgnoreCase("Quarter 3")) {
                        listMonth = Arrays.asList(getResources().getStringArray(R.array.month_q3));
                    } else if(strQuarter.equalsIgnoreCase("Quarter 4")) {
                        listMonth = Arrays.asList(getResources().getStringArray(R.array.month_q4));
                    }

                    if (listMonth != null) {
                        if (listMonth.size() > 0) {
                            strMonthArray = new String[listMonth.size()];
                            //   strLeadArray[0] = "Select Source Lead";
                            for (int i = 0; i < listMonth.size(); i++) {
                                strMonthArray[i] = listMonth.get(i);
                            }
                        }
                    }

                    if (listMonth != null && listMonth.size() > 0) {

                        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strMonthArray) {
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

                        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        autoMonth.setAdapter(adapter6);
                    }


                    autoMonth.setOnTouchListener(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            autoMonth.showDropDown();
                            return false;
                        }
                    });
                }
            }
        });


    }
}

package com.example.admin.kamathotelapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kamathotelapp.Adapters.ChartAdapter;
import com.example.admin.kamathotelapp.Adapters.DashboardAdapter;
import com.example.admin.kamathotelapp.KHIL;
import com.example.admin.kamathotelapp.MainActivity;
import com.example.admin.kamathotelapp.Model.ChartDataModel;
import com.example.admin.kamathotelapp.Model.DashBoardDataModel;
import com.example.admin.kamathotelapp.Model.DashboardModel;
import com.example.admin.kamathotelapp.Model.LocationModel;
import com.example.admin.kamathotelapp.Model.PropertyModel;
import com.example.admin.kamathotelapp.NavigationDrawerActivity;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.ExceptionHandler;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.Utils.Utils;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;
import com.example.admin.kamathotelapp.libs.SOAPWebservice;
import com.example.admin.kamathotelapp.libs.UtilityClass;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DashboardFragment extends Fragment {

    private AutoCompleteTextView autoLegal, autoProp, autoLoc, autoYr, autoQuarter, autoMonth;
    private LinearLayout detailsLayout,dashboardlayout,chartdatalayout,barchartlayout,searchlayout;
    DashboardModel dashboardModel;
    List<DashboardModel> dashboardModelList;
    List<ChartDataModel>chartModelList;
    DashboardAdapter dashboardAdapter;
    ChartAdapter chartAdapter;
    private Button search,loadRecord,Searchbtn,btnAdvSearch;
    EditText etsearchdoc;
    private TableLayout tableLayout,tableLaout_chart;
    private String strQuarter="",Inv_count,status,Created_By,LegalEntity,type_of_document,
            selected_document,supporting_document,annexure,Location,Property;
    String[] strLegalArray = null;
    String[] strPropertyArray = null;
    String[] strMonthArray = null;
    String[] strYearArray = null;
    String[] strQuarterArray = null;
    String[] strLocArray = null;
    String[] strLevel2Array = null;
    String[] strLevel3Array = null;
    List<String> listMonth;
    ListView listDashboard,listDashchart;
    HorizontalBarChart barChart;
    private SharedPref sharedPref;
    SOAPWebservice ws;
    ProgressDialog progress;

    @InjectView(R.id.dashIndividuals)
    AutoCompleteTextView autoIndividuals;
    @InjectView(R.id.dashetNewProposal)
    EditText etNewProposal;

    ArrayList<String> lables;
    ArrayList<BarEntry> barEntries;
    ArrayList<DashBoardDataModel> dashBoardDataModelArrayList;

    DashBoardDataModel dashBoardDataModel;
    ChartDataModel chartDataModel;

    public static AutoCompleteTextView dashtxtL2, dashtxtL3, dashtxtL4, dashtxtL5, dashtxtL6, dashtxtL7;
    public static CardView dashcardlevel2, dashcardlevel3, dashcardlevel4, dashcardlevel5, dashcardlevel6,
            dashcardlevel7, dashcardNewProposal, dashcardIndividuals, dashcardProperty;
    public static TextInputLayout dashlevel2txtlayout, dashlevel3txtlayout, dashlevel4txtlayout,
            dashlevel5txtlayout, dashlevel6txtlayout, dashlevel7txtlayout;

    private String strLegalEntity;
    private String legalEntityString, propertyString, monthString, yearString, quarterString = "", locString;




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
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.inject(this, view);
        initView(view);
        return view;
    }

    void initView(View view) {
        barChart = (HorizontalBarChart) view.findViewById(R.id.bargraph);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLaout_lead);
       // tableLaout_chart = (TableLayout) view.findViewById(R.id.tableLaout_chart);
        detailsLayout = (LinearLayout)view.findViewById(R.id.detailsLayout);
        dashboardlayout = (LinearLayout) view.findViewById(R.id.dashboardlayout);
        chartdatalayout = (LinearLayout) view.findViewById(R.id.chartdatalayout);
        barchartlayout = (LinearLayout) view.findViewById(R.id.barchartlayout);
        searchlayout = (LinearLayout) view.findViewById(R.id.searchlayout);
        search = (Button)view.findViewById(R.id.btnSearch);
        loadRecord = (Button)view.findViewById(R.id.btnLoad);
        autoLegal = (AutoCompleteTextView)view.findViewById(R.id.autoLegalEntity);
        autoProp = (AutoCompleteTextView)view.findViewById(R.id.autoProperty);
        autoLoc = (AutoCompleteTextView)view.findViewById(R.id.autoLocation);
        autoYr = (AutoCompleteTextView)view.findViewById(R.id.autoYear);
        autoQuarter = (AutoCompleteTextView)view.findViewById(R.id.autoQuarter);
        autoMonth = (AutoCompleteTextView)view.findViewById(R.id.autoMonth);
        listDashboard = (ListView) view.findViewById(R.id.listDashboard);
        listDashchart = (ListView) view.findViewById(R.id.listDashchart);

        //................Auto Complete Text View......
        dashtxtL2 = (AutoCompleteTextView) view.findViewById(R.id.dashLevel2);
        dashtxtL3 = (AutoCompleteTextView) view.findViewById(R.id.dashLevel3);
        dashtxtL4 = (AutoCompleteTextView) view.findViewById(R.id.dashLevel4);
        dashtxtL5 = (AutoCompleteTextView) view.findViewById(R.id.dashLevel5);
        dashtxtL6 = (AutoCompleteTextView) view.findViewById(R.id.dashLevel6);
        dashtxtL7 = (AutoCompleteTextView) view.findViewById(R.id.dashLevel7);

        //      cardview
        dashcardNewProposal = (CardView) view.findViewById(R.id.dashnewProposalcardview);
        dashcardIndividuals = (CardView) view.findViewById(R.id.dashindividualscardview);
        dashcardlevel2 = (CardView) view.findViewById(R.id.dashlevel2cardview);
        dashcardlevel3 = (CardView) view.findViewById(R.id.dashlevel3cardview);
        dashcardlevel4 = (CardView) view.findViewById(R.id.dashlevel4cardview);
        dashcardlevel5 = (CardView) view.findViewById(R.id.dashlevel5cardview);
        dashcardlevel6 = (CardView) view.findViewById(R.id.dashlevel6cardview);
        dashcardlevel7 = (CardView) view.findViewById(R.id.dashlevel7cardview);

        dashlevel2txtlayout = (TextInputLayout) view.findViewById(R.id.dashlevel2txtlayout);
        dashlevel3txtlayout = (TextInputLayout) view.findViewById(R.id.dashlevel3txtlayout);
        dashlevel4txtlayout = (TextInputLayout) view.findViewById(R.id.dashlevel4txtlayout);
        dashlevel5txtlayout = (TextInputLayout) view.findViewById(R.id.dashlevel5txtlayout);
        dashlevel6txtlayout = (TextInputLayout) view.findViewById(R.id.dashlevel6txtlayout);
        dashlevel7txtlayout = (TextInputLayout) view.findViewById(R.id.dashlevel7txtlayout);

        Searchbtn = (Button)view.findViewById(R.id.Searchbtn);
        btnAdvSearch = (Button)view.findViewById(R.id.btnAdvSearch);
        etsearchdoc = (EditText)view.findViewById(R.id.etsearchdoc);


        listDashchart.setOnTouchListener(new ListView.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        listDashchart.setClickable(true);

        sharedPref = new SharedPref(getActivity());
        ws = new SOAPWebservice(getActivity());
        progress = new ProgressDialog(getActivity());


//        dashBoardDataModel = new DashBoardDataModel();
//        dashBoardDataModelArrayList.add(dashBoardDataModel);

        getDashboardData();


        //////////////Bar chart Details//////////////////////////

       /* barEntries.add(new BarEntry(50f,0));
        barEntries.add(new BarEntry(40f,1));
        barEntries.add(new BarEntry(20f,2));*/
       try {
           if (barEntries.size() > 0) {
               BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
               int[] Colors = {Color.rgb(193, 37, 82),
                       Color.rgb(255, 102, 0),
                       Color.rgb(245, 199, 0),};
               barDataSet.setColors(Colors);
               //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

               barDataSet.setValueFormatter(new ValueFormatter() {
                   @Override
                   public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                       return Math.round(value) + "";
                   }
               });
               barDataSet.setDrawValues(true);//above heading in bar




        /*lables.add("QC1Approved");
        lables.add("QC1Pending");
        lables.add("QC1Rejected");*/

               BarData theData = new BarData(lables, barDataSet);
               barChart.setData(theData);
               barChart.setTouchEnabled(true);
               barChart.setDragEnabled(true);
               barChart.setScaleEnabled(true);// when long tuch to screen it zooms
               barChart.animateY(3000);
               barChart.getData().setHighlightEnabled(false);   // when click on bar chart high light is false

               YAxis y = barChart.getAxisLeft();
               y.setAxisMaxValue(100);
               y.setAxisMinValue(0);
               y.setDrawAxisLine(true);

               XAxis xAxis = barChart.getXAxis();
               xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

               YAxis Right = barChart.getAxisRight();
               Right.setEnabled(false);

               Legend legend = barChart.getLegend();
               legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
               legend.setWordWrapEnabled(true);  ///wrap the leget
               legend.setCustom(Colors, new String[]{"Set1", "Set2", "set3",});
               legend.setTextSize(10);

               barChart.getXAxis().setDrawGridLines(false);  //vertical grid lines visibility false
               barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                   @Override
                   public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                       if (e.getXIndex() == 0) {
                           String approved = lables.get(0);
                           if(approved.equalsIgnoreCase("QC1Approved")) {
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(approved);
                           }else if(approved.equalsIgnoreCase("QC1Pending")){
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(approved);
                           }else if(approved.equalsIgnoreCase("QC1Rejected")){
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(approved);
                           }
                       } else if (e.getXIndex() == 1) {
                           String pending = lables.get(1);
                           if(pending.equalsIgnoreCase("QC1Approved")) {
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(pending);
                           }else if(pending.equalsIgnoreCase("QC1Pending")){
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(pending);
                           }else if(pending.equalsIgnoreCase("QC1Rejected")){
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(pending);
                           }
                       } else if (e.getXIndex() == 2) {
                           String rejected = lables.get(2);
                           if(rejected.equalsIgnoreCase("QC1Approved")) {
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(rejected);
                           }else if(rejected.equalsIgnoreCase("QC1Pending")){
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(rejected);
                           }else if(rejected.equalsIgnoreCase("QC1Rejected")){
                               chartdatalayout.setVisibility(View.VISIBLE);
                               ChartData chartData = new ChartData();
                               chartData.execute(rejected);
                           }

                       }
                   }

                   @Override
                   public void onNothingSelected() {

                   }
               });

           }
       }catch (Exception e){
           e.printStackTrace();
       }
        ////////////////////////////////////////////

       /* dashboardModel  = new DashboardModel();
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
        listDashboard.setAdapter(dashboardAdapter);*/

        List<String> listLegal = Arrays.asList(getResources().getStringArray(R.array.legal_entity));
        List<String> listProperty = Arrays.asList(getResources().getStringArray(R.array.property));
        List<String> listYear = Arrays.asList(getResources().getStringArray(R.array.year));
        List<String> listQuarter = Arrays.asList(getResources().getStringArray(R.array.quarter));
        List<String> listLoc = Arrays.asList(getResources().getStringArray(R.array.location));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
               // detailsLayout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                searchlayout.setVisibility(View.VISIBLE);
                dashboardlayout.setVisibility(View.VISIBLE);
                barchartlayout.setVisibility(View.GONE);
            }
        });

        btnAdvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                searchlayout.setVisibility(View.GONE);
                dashboardlayout.setVisibility(View.GONE);
                detailsLayout.setVisibility(View.VISIBLE);
            }
        });

        loadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                detailsLayout.setVisibility(View.GONE);
 //               search.setVisibility(View.VISIBLE);
                dashboardlayout.setVisibility(View.VISIBLE);
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

       /* autoLegal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strLegalArray != null && strLegalArray.length > 0) {
                    String ID = "";
                    strLegalEntity = autoLegal.getText().toString();
                    legalEntityString = parent.getItemAtPosition(position).toString();
                    for (int i = 0; i < listLegal.size(); i++) {
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
                    if (strLegalEntity.equalsIgnoreCase("Individuals")) {

                        dashcardIndividuals.setVisibility(View.VISIBLE);
                        dashcardNewProposal.setVisibility(View.GONE);
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
        });*/

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

    public void getDashboardData() {

        dashBoardDataModelArrayList = new ArrayList<>();
        try {
            Cursor cursor = null;
            String where = " where roleId = '" + sharedPref.getRoleID() + "'";
            cursor = KHIL.dbCon.fetchFromSelect(DbHelper.DASHBOARD_DATA, where);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    dashBoardDataModel = createDashboardDataModel(cursor);
                    dashBoardDataModelArrayList.add(dashBoardDataModel);

                } while (cursor.moveToNext());
                cursor.close();

            }
            setDashboardDataValue();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public DashBoardDataModel createDashboardDataModel(Cursor cursor) {
        dashBoardDataModel = new DashBoardDataModel();
        try {
            dashBoardDataModel.setInv_count(cursor.getString(cursor.getColumnIndex("inv_count")));
            dashBoardDataModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dashBoardDataModel;

    }

    private void setDashboardDataValue() {
        lables = new ArrayList<>();
        barEntries = new ArrayList<>();
        try {
            if (dashBoardDataModelArrayList.size() > 0) {
                for (int i = 0; i < dashBoardDataModelArrayList.size(); i++) {
                    final DashBoardDataModel clientDetailsModel = dashBoardDataModelArrayList.get(i);
                     Inv_count = clientDetailsModel.getInv_count();
                     status = clientDetailsModel.getStatus();

                    lables.add(status);
                    barEntries.add(new BarEntry(Float.valueOf(Inv_count),i));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ChartData extends AsyncTask<String, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                if (progress != null && !progress.isShowing()) {
                    progress.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected SoapObject doInBackground(String... params) {
            SoapObject object2 = null;
            String status = params[0];
            try {
                object2 = ws.chartdata(sharedPref.getLoginId(),status);

            }catch(Exception e){
                e.printStackTrace();
            }
            return object2;
        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);

            try {
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                }
                String response = String.valueOf(soapObject);
                System.out.println("Response =>: " + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                int id = 0;
                chartModelList = new ArrayList<>();
                for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                    SoapObject root = (SoapObject) soapObject.getProperty(i);


                    if (root.getProperty("Created_By") != null) {

                        if (!root.getProperty("Created_By").toString().equalsIgnoreCase("anyType{}")) {
                            Created_By = root.getProperty("Created_By").toString();

                        } else {
                            Created_By = "";
                        }
                    } else {
                        Created_By = "";
                    }

                    if (root.getProperty("LegalEntity") != null) {

                        if (!root.getProperty("LegalEntity").toString().equalsIgnoreCase("anyType{}")) {
                            LegalEntity = root.getProperty("LegalEntity").toString();

                        } else {
                            LegalEntity = "";
                        }
                    } else {
                        LegalEntity = "";
                    }

                    if (root.getProperty("Level4") != null) {

                        if (!root.getProperty("Level4").toString().equalsIgnoreCase("anyType{}")) {
                            type_of_document = root.getProperty("Level4").toString();

                        } else {
                            type_of_document = "";
                        }
                    } else {
                        type_of_document = "";
                    }

                    if (root.getProperty("Level5") != null) {

                        if (!root.getProperty("Level5").toString().equalsIgnoreCase("anyType{}")) {
                            selected_document = root.getProperty("Level5").toString();

                        } else {
                            selected_document = "";
                        }
                    } else {
                        selected_document = "";
                    }

                    if (root.getProperty("Level6") != null) {

                        if (!root.getProperty("Level6").toString().equalsIgnoreCase("anyType{}")) {
                            supporting_document = root.getProperty("Level6").toString();

                        } else {
                            supporting_document = "";
                        }
                    } else {
                        supporting_document = "";
                    }

                    if (root.getProperty("Level7") != null) {

                        if (!root.getProperty("Level7").toString().equalsIgnoreCase("anyType{}")) {
                            annexure = root.getProperty("Level7").toString();

                        } else {
                            annexure = "";
                        }
                    } else {
                        annexure = "";
                    }

                    if (root.getProperty("Location") != null) {

                        if (!root.getProperty("Location").toString().equalsIgnoreCase("anyType{}")) {
                            Location = root.getProperty("Location").toString();

                        } else {
                            Location = "";
                        }
                    } else {
                        Location = "";
                    }

                    if (root.getProperty("Property") != null) {

                        if (!root.getProperty("Property").toString().equalsIgnoreCase("anyType{}")) {
                            Property = root.getProperty("Property").toString();

                        } else {
                            Property = "";
                        }
                    } else {
                        Property = "";
                    }
                    chartDataModel = new ChartDataModel();
                    chartDataModel.setCreated_By(Created_By);
                    chartDataModel.setLegalEntity(LegalEntity);
                    chartDataModel.setType_Of_Document(type_of_document);
                    chartDataModel.setSelected_Document(selected_document);
                    chartDataModel.setSupporting_Document(supporting_document);
                    chartDataModel.setAnnexure(annexure);
                    chartDataModel.setLocation(Location);
                    chartDataModel.setProperty(Property);
                    chartModelList.add(chartDataModel);


                }

                chartAdapter = new ChartAdapter(getContext(), chartModelList);
                listDashchart.setAdapter(chartAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

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
import com.example.admin.kamathotelapp.Model.LegalEntityModel;
import com.example.admin.kamathotelapp.Model.LeveldataModel;
import com.example.admin.kamathotelapp.Model.LocationModel;
import com.example.admin.kamathotelapp.Model.MonthModel;
import com.example.admin.kamathotelapp.Model.PropertyModel;
import com.example.admin.kamathotelapp.Model.QuaterModel;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.Model.YearModel;
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

    private LinearLayout detailsLayout,dashboardlayout,chartdatalayout,barchartlayout,searchlayout;
    DashboardModel dashboardModel;
    List<DashboardModel> dashboardModelList;
    List<ChartDataModel>chartModelList;
    DashboardAdapter dashboardAdapter;
    ChartAdapter chartAdapter;
    private Button loadRecord,Searchbtn,btnAdvSearch;
    AutoCompleteTextView etsearchdoc;
    private TableLayout tableLayout,tableLaout_chart;
    private String Inv_count,status,Created_By,LegalEntity,type_of_document,
            selected_document,supporting_document,annexure,Location,Property;

    ListView listDashboard,listDashchart;
    HorizontalBarChart barChart;
    SOAPWebservice ws;
    ProgressDialog progress;

    DashBoardDataModel dashboarddatamodel;
    private Utils utils;

    @InjectView(R.id.dashLegalEntity)
    AutoCompleteTextView autoLegalEntity;
    @InjectView(R.id.dashIndividuals)
    AutoCompleteTextView autoIndividuals;
    @InjectView(R.id.dashetNewProposal)
    EditText etNewProposal;
    @InjectView(R.id.dashProperty)
    AutoCompleteTextView autoProperty;
    @InjectView(R.id.dashMonth)
    AutoCompleteTextView autoMonth;
    @InjectView(R.id.dashYear)
    AutoCompleteTextView autoYear;
    @InjectView(R.id.dashQuarter)
    AutoCompleteTextView autoQuarter;
    @InjectView(R.id.dashLoc)
    AutoCompleteTextView autoLoc;

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

    private String legalEntityString, propertyString, monthString, yearString, quarterString = "", locString;
    private String strLegalEntity, strProperty, strMonth, strYear, strQuarter, strLoc;

    private ArrayList<LegalEntityModel> listLegal, listLegalAll;
    List<UploadModel> uploadModelList;
    UploadModel uploadModel;
    LegalEntityModel legalEntityModel;
    public String legalEntityID, propertyID, individualID, locationID;
    public String legalEntityValue, propertyValue, individualValue, locationValue;
    private ArrayList<String> listIndividuals;
    private ArrayList<YearModel> listYear;
    YearModel yearModel;
    private ArrayList<QuaterModel> listQuarter;
    QuaterModel quaterModel;
    private ArrayList<LeveldataModel> listLevelData;
    LeveldataModel leveldataModel;
    private ArrayList<PropertyModel> listProperty;
    PropertyModel propertyModel;
    private ArrayList<LocationModel> listLoc;
    LocationModel locationModel;
    private ArrayList<MonthModel> listMonth;
    MonthModel monthModel;
    public String txtL2Id, txtL3Id, txtL4Id, txtL5Id, txtL6Id, txtL7Id, monthID, yearID, quaterID;
    private SharedPref sharedPref;
    public String loginId, password,roleID;
    private String valLevel2="", valLevel3="", valLevel4="", valLevel5="", valLevel6="", valLevel7="";
    public String valuel2,valuel3,valuel4,valuel5,valuel6,valuel7;

    private List<String> level2list,level3list,level4list,level5list,level6list;
    private List<String> level4listHR, level5listHR, level6listHR;
    private List<String> level4listCMD, level5listCMD, level6listCMD, level7listCMD;
    private List<String> level2listCS,level3listCS,level4listCS;
    private List<String> level4listMAR, level5listMAR, level6listMAR, level7listMAR;
    private List<String> level3listPER, level4listPER, level5listPER;
    private List<String> level4listLEGAL, level5listLEGAL, level6listLEGAL, level7listLEGAL;

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


    String[] countryNameList = {"India", "China", "Australia", "New Zealand", "England", "Pakistan"};


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

        sharedPref = new SharedPref(getContext());
        roleID = sharedPref.getRoleID();
        loginId = sharedPref.getLoginId();
        password = sharedPref.getPassword();
        utils = new Utils(getContext());

        barChart = (HorizontalBarChart) view.findViewById(R.id.bargraph);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLaout_lead);
       // tableLaout_chart = (TableLayout) view.findViewById(R.id.tableLaout_chart);
        detailsLayout = (LinearLayout)view.findViewById(R.id.detailsLayout);
        dashboardlayout = (LinearLayout) view.findViewById(R.id.dashboardlayout);
        chartdatalayout = (LinearLayout) view.findViewById(R.id.chartdatalayout);
        barchartlayout = (LinearLayout) view.findViewById(R.id.barchartlayout);
        searchlayout = (LinearLayout) view.findViewById(R.id.searchlayout);
        //search = (Button)view.findViewById(R.id.btnSearch);
        loadRecord = (Button)view.findViewById(R.id.btnLoad);

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
        dashcardProperty = (CardView) view.findViewById(R.id.dashpropertycardview);
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
        etsearchdoc = (AutoCompleteTextView)view.findViewById(R.id.etsearchdoc);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, countryNameList);

        etsearchdoc.setAdapter(adapter);
        etsearchdoc.setThreshold(1);//start searching from 1 character
        etsearchdoc.setAdapter(adapter);   //set the adapter for displaying country name list

        // call Dashoboarddata AsynTask
        DashboardData dashboardData = new DashboardData();
        dashboardData.execute();


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

      /*  search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
               // detailsLayout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                searchlayout.setVisibility(View.VISIBLE);
                dashboardlayout.setVisibility(View.VISIBLE);
                barchartlayout.setVisibility(View.GONE);
                chartdatalayout.setVisibility(View.GONE);
            }
        });*/

        Searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_click));
                dashboardlayout.setVisibility(View.VISIBLE);
                barchartlayout.setVisibility(View.GONE);
                chartdatalayout.setVisibility(View.GONE);
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
                searchlayout.setVisibility(View.VISIBLE);
                dashboardlayout.setVisibility(View.VISIBLE);
            }
        });


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
                    for (int i = 0; i < listLegal.size(); i++) {
                        legalEntityModel = listLegal.get(i);
                        String entity = legalEntityModel.getText();
                        if (entity.equalsIgnoreCase(legalEntityString)) {
                            ID = legalEntityModel.getLegal_id();
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
                        dashcardProperty.setVisibility(View.VISIBLE);

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
                                        eid = legalEntityModel.getLegal_id();
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
                        dashcardNewProposal.setVisibility(View.VISIBLE);
                        dashcardIndividuals.setVisibility(View.GONE);
                        dashcardProperty.setVisibility(View.VISIBLE);
                    } else {
                        dashcardIndividuals.setVisibility(View.GONE);
                        dashcardNewProposal.setVisibility(View.GONE);
                        dashcardProperty.setVisibility(View.VISIBLE);

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
                                }
                            }
                        }
                    }
                });

            }
        });


        ///////////////////////// Level data set to Autocompletetextview

        if (loginId.equalsIgnoreCase("finance") || loginId.equalsIgnoreCase("financeqc1") ) {
            dashtxtL2.setHint("Financial Type");
            fetchLevel2dataFin();

            dashtxtL2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel3.setVisibility(View.GONE);
                    dashcardlevel4.setVisibility(View.GONE);
                    dashcardlevel5.setVisibility(View.GONE);
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);

                    dashtxtL3.setHint("");
                    dashtxtL3.setText("");
                    dashtxtL4.setText("");
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel3 = "";
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    dashtxtL2.showDropDown();
                    return false;
                }
            });

            dashtxtL2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dashlevel2txtlayout.setHint("Financial Type");
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
                            dashlevel3txtlayout.setHint(valLevel2);
                            dashcardlevel3.setVisibility(View.VISIBLE);
                            dashtxtL3.setVisibility(View.VISIBLE);
                            //qc1lev3 = true;
                        }else {
                            dashcardlevel3.setVisibility(View.GONE);
                            //qc1lev3 = false;
                        }
                    }
                }
            });

            ///////////////////////////////////////
            //////////////////Level 3 details

            dashtxtL3.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel4.setVisibility(View.GONE);
                    dashcardlevel5.setVisibility(View.GONE);
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL4.setText("");
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    dashtxtL3.showDropDown();
                    return false;
                }
            });

            dashtxtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashcardlevel4.setVisibility(View.VISIBLE);
                                dashlevel4txtlayout.setHint(valLevel3);
                                //qc1lev4 = true;
                            } else {
                                dashcardlevel4.setVisibility(View.GONE);
                                //qc1lev4 = false;
                            }
                        }
                    }
                }
            });

            ///////////////////////////////////////

            //////////////////Level 4 details

            dashtxtL4.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel5.setVisibility(View.GONE);
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    dashtxtL4.showDropDown();
                    return false;
                }
            });

            dashtxtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel5txtlayout.setHint(valLevel4);
                                dashcardlevel5.setVisibility(View.VISIBLE);
                               // qc1lev5 = true;
                            } else {
                                dashcardlevel5.setVisibility(View.GONE);
                               // qc1lev5 = false;
                            }
                        }

                    }
                }
            });

            ///////////////////////////////////////
            //////////////////Level 5 details

            dashtxtL5.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    dashtxtL5.showDropDown();
                    return false;
                }
            });

            dashtxtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel6txtlayout.setHint(valLevel5);
                                dashcardlevel6.setVisibility(View.VISIBLE);
                                //qc1lev6 = true;
                            } else {
                                dashcardlevel6.setVisibility(View.GONE);
                                //qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            ///////////////////////////////////////

            //////////////////Level 6 details

            dashtxtL6.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL7.setText("");
                    valLevel7 = "";
                    dashtxtL6.showDropDown();
                    return false;
                }
            });

            dashtxtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        } else if (loginId.equalsIgnoreCase("hr") || loginId.equalsIgnoreCase("hrqc1") ) {

            dashcardlevel2.setVisibility(View.GONE);
            dashcardlevel3.setVisibility(View.GONE);
            dashcardlevel4.setVisibility(View.VISIBLE);
            dashtxtL4.setHint("HR");

            fetchLevel4dataHR();

            dashtxtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel5.setVisibility(View.GONE);
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL4.showDropDown();
                    return false;
                }
            });

            dashtxtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dashlevel4txtlayout.setHint("HR");
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
                            dashlevel5txtlayout.setHint(valLevel4);
                            dashcardlevel5.setVisibility(View.VISIBLE);
                            //qc1lev5 = true;
                        }else {
                            dashcardlevel5.setVisibility(View.GONE);
                            //qc1lev5 = false;
                        }
                    }
                }
            });

            dashtxtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel6.setVisibility(View.GONE);
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL5.showDropDown();
                    return false;
                }
            });

            dashtxtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel6txtlayout.setHint(valLevel5);
                                dashcardlevel6.setVisibility(View.VISIBLE);
                                //qc1lev6 = true;
                            } else {
                                dashcardlevel6.setVisibility(View.GONE);
                                //qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL7.setText("");
                    valLevel7 = "";
                    dashtxtL6.showDropDown();
                    return false;
                }
            });

            dashtxtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        } else if (loginId.equalsIgnoreCase("cmd") || loginId.equalsIgnoreCase("cmdqc1") ) {

            dashcardlevel2.setVisibility(View.GONE);
            dashcardlevel3.setVisibility(View.GONE);

            dashcardlevel4.setVisibility(View.VISIBLE);
            dashtxtL4.setHint("Type");

            fetchLevel4dataCMD();

            dashtxtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel5.setVisibility(View.GONE);
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL4.showDropDown();
                    return false;
                }
            });

            dashtxtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dashlevel4txtlayout.setHint("CMD");
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
                                dashlevel5txtlayout.setHint(valLevel4);
                                dashcardlevel5.setVisibility(View.VISIBLE);
                                //qc1lev5 = true;
                            } else {
                                dashcardlevel5.setVisibility(View.GONE);
                               // qc1lev5 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel6.setVisibility(View.GONE);
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL5.showDropDown();
                    return false;
                }
            });

            dashtxtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel6txtlayout.setHint(valLevel5);
                                dashcardlevel6.setVisibility(View.VISIBLE);
                                //qc1lev6 = true;
                            } else {
                                dashcardlevel6.setVisibility(View.GONE);
                                //qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL7.setText("");
                    valLevel7 = "";
                    dashtxtL6.showDropDown();
                    return false;
                }
            });

            dashtxtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel7txtlayout.setHint(valLevel5);
                                dashcardlevel7.setVisibility(View.VISIBLE);
                                //qc1lev7 = true;
                            } else {
                                dashcardlevel7.setVisibility(View.GONE);
                                //qc1lev7 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashtxtL7.showDropDown();
                    return false;
                }
            });

            dashtxtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        } else if (loginId.equalsIgnoreCase("cs") || loginId.equalsIgnoreCase("csqc1") ) {

            fetchLevel2dataCS();
            dashtxtL2.setHint("Type");

            dashtxtL2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel3.setVisibility(View.GONE);
                    dashcardlevel4.setVisibility(View.GONE);
                    dashcardlevel5.setVisibility(View.GONE);
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL3.setText("");
                    dashtxtL4.setText("");
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel3 = "";
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    dashtxtL2.showDropDown();
                    return false;
                }
            });

            dashtxtL2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dashlevel2txtlayout.setHint("Type");
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
                            dashlevel3txtlayout.setHint(valLevel2);
                            dashcardlevel3.setVisibility(View.VISIBLE);
                            //qc1lev3 = true;
                        }else {
                            dashcardlevel3.setVisibility(View.GONE);
                            //qc1lev3 = false;
                        }
                    }
                }
            });

            dashtxtL3.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel4.setVisibility(View.GONE);
                    dashcardlevel5.setVisibility(View.GONE);
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL4.setText("");
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    dashtxtL3.showDropDown();
                    return false;
                }
            });

            dashtxtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashcardlevel4.setVisibility(View.VISIBLE);
                                dashlevel4txtlayout.setHint(valLevel3);
                                //qc1lev4 = true;
                            } else {
                                dashcardlevel4.setVisibility(View.GONE);
                                //qc1lev4 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashtxtL4.showDropDown();
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    return false;
                }
            });

            dashtxtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        } else if (loginId.equalsIgnoreCase("marketing") || loginId.equalsIgnoreCase("marketingqc1") ) {

            dashcardlevel2.setVisibility(View.GONE);
            dashcardlevel3.setVisibility(View.GONE);

            dashcardlevel4.setVisibility(View.VISIBLE);
            dashtxtL4.setHint("Occupancy");
            fetchLevel4dataMAR();

            dashtxtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel5.setVisibility(View.GONE);
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL4.showDropDown();
                    return false;
                }
            });

            dashtxtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dashlevel4txtlayout.setHint("Occupancy");
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
                                dashlevel5txtlayout.setHint(valLevel4);
                                dashcardlevel5.setVisibility(View.VISIBLE);
                                //qc1lev5 = true;
                            } else {
                                dashcardlevel5.setVisibility(View.GONE);
                                //qc1lev5 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel6 = "";
                    valLevel7 = "";
                    dashtxtL5.showDropDown();
                    return false;
                }
            });

            dashtxtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel6txtlayout.setHint(valLevel5);
                                dashcardlevel6.setVisibility(View.VISIBLE);
                                //qc1lev6 = true;
                            } else {
                                dashcardlevel6.setVisibility(View.GONE);
                                //qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL7.setText("");
                    valLevel7 = "";
                    dashtxtL6.showDropDown();
                    return false;
                }
            });

            dashtxtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel7txtlayout.setHint(valLevel6);
                                dashcardlevel7.setVisibility(View.VISIBLE);
                                //qc1lev7 = true;
                            } else {
                                dashcardlevel7.setVisibility(View.GONE);
                                //qc1lev7 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashtxtL7.showDropDown();
                    return false;
                }
            });

            dashtxtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        } else if (loginId.equalsIgnoreCase("Personal") || loginId.equalsIgnoreCase("Personalqc1") ) {

            dashcardlevel2.setVisibility(View.GONE);
            dashcardlevel3.setVisibility(View.VISIBLE);
            dashtxtL3.setHint("Type");

            fetchLevel3dataPER();

            dashtxtL3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel4.setVisibility(View.GONE);
                    dashcardlevel5.setVisibility(View.GONE);
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL4.setText("");
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel4 = "";
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    dashtxtL3.showDropDown();
                    return false;
                }
            });

            dashtxtL3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dashlevel3txtlayout.setHint("Type");
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
                                dashcardlevel4.setVisibility(View.VISIBLE);
                                dashlevel4txtlayout.setHint(valLevel3);
                                //qc1lev4 = true;
                            } else {
                                dashcardlevel4.setVisibility(View.GONE);
                                //qc1lev4 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL4.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel5.setVisibility(View.GONE);
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    valLevel5 = "";
                    valLevel6 = "";
                    valLevel7 = "";
                    dashtxtL4.showDropDown();
                    return false;
                }
            });

            dashtxtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel5txtlayout.setHint(valLevel4);
                                dashcardlevel5.setVisibility(View.VISIBLE);
                                //qc1lev5 = true;
                            } else {
                                dashcardlevel5.setVisibility(View.GONE);
                                //qc1lev5 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    dashtxtL5.showDropDown();
                    return false;
                }
            });

            dashtxtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        } else if (loginId.equalsIgnoreCase("legal") || loginId.equalsIgnoreCase("legalqc1") ) {

            dashcardlevel2.setVisibility(View.GONE);
            dashcardlevel3.setVisibility(View.GONE);

            dashcardlevel4.setVisibility(View.VISIBLE);

            dashtxtL4.setHint("Legal");
            fetchLevel4dataLEGAL();

            dashtxtL4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel5.setVisibility(View.GONE);
                    dashtxtL5.setText("");
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL4.showDropDown();
                    return false;
                }
            });

            dashtxtL4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dashlevel4txtlayout.setHint("Legal");
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
                            dashlevel5txtlayout.setHint(valLevel4);
                            dashcardlevel5.setVisibility(View.VISIBLE);
                            //qc1lev5 = true;
                        }else {
                            dashcardlevel6.setVisibility(View.GONE);
                            //qc1lev5 = false;
                        }
                    }
                }
            });

            dashtxtL5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel6.setVisibility(View.GONE);
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL6.setText("");
                    dashtxtL7.setText("");
                    dashtxtL5.showDropDown();
                    return false;
                }
            });

            dashtxtL5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel6txtlayout.setHint(valLevel5);
                                dashcardlevel6.setVisibility(View.VISIBLE);
                                //qc1lev6 = true;
                            } else {
                                dashcardlevel6.setVisibility(View.GONE);
                                //qc1lev6 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashcardlevel7.setVisibility(View.GONE);
                    dashtxtL7.setText("");
                    dashtxtL6.showDropDown();
                    return false;
                }
            });

            dashtxtL6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                dashlevel7txtlayout.setHint(valLevel6);
                                dashcardlevel7.setVisibility(View.VISIBLE);
                                //qc1lev7 = true;
                            } else {
                                dashcardlevel7.setVisibility(View.GONE);
                                //qc1lev7 = false;
                            }
                        }
                    }
                }
            });

            dashtxtL7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dashtxtL7.showDropDown();
                    return false;
                }
            });

            dashtxtL7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


    }

    public class DashboardData extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            SoapObject object2 = null;
            try {
                object2 = ws.Dashboarddata(loginId,"2");

            }catch(Exception e){
                e.printStackTrace();
            }
            return object2;
        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
            try {

                String response = String.valueOf(soapObject);
                System.out.println("Response =>: " + response);

                int id = 0;
                dashboarddatamodel = new DashBoardDataModel();
                for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                    SoapObject root = (SoapObject) soapObject.getProperty(i);


                    if (root.getProperty("Inv_count") != null) {

                        if (!root.getProperty("Inv_count").toString().equalsIgnoreCase("anyType{}")) {
                            Inv_count = root.getProperty("Inv_count").toString();

                        } else {
                            Inv_count = "";
                        }
                    } else {
                        Inv_count = "";
                    }
                    //barEntries.add(new BarEntry(Float.valueOf(Inv_count),i));

                    if (root.getProperty("status") != null) {

                        if (!root.getProperty("status").toString().equalsIgnoreCase("anyType{}")) {
                            status = root.getProperty("status").toString();

                        } else {
                            status = "";
                        }
                    } else {
                        status = "";
                    }

                    String selection = "id = ?";
                    id = id+1;
                    // WHERE clause arguments
                    String[] selectionArgs = {id+""};
                    //            "id","value", "text", "parent_Ref", "updated_date"
                    String valuesArray[] = {id+"", Inv_count, status, roleID};
                    boolean output = KHIL.dbCon.updateBulk(DbHelper.DASHBOARD_DATA, selection, valuesArray, utils.columnNames_Dashboard_Data, selectionArgs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    /*public class SearchData extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            SoapObject object2 = null;
            try {
                object2 = ws.SearchData(text);

            }catch(Exception e){
                e.printStackTrace();
            }
            return object2;
        }

        @Override
        protected void onPostExecute(SoapObject soapObject) {
            super.onPostExecute(soapObject);
            try {

                String response = String.valueOf(soapObject);
                System.out.println("Response =>: " + response);


                dashboardModel = new DashboardModel();
                for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                    SoapObject root = (SoapObject) soapObject.getProperty(i);


                    if (root.getProperty("Document_No") != null) {

                        if (!root.getProperty("Document_No").toString().equalsIgnoreCase("anyType{}")) {
                            Document_No = root.getProperty("Document_No").toString();

                        } else {
                            Document_No = "";
                        }
                    } else {
                        Document_No = "";
                    }

                    if (root.getProperty("File_Name") != null) {

                        if (!root.getProperty("File_Name").toString().equalsIgnoreCase("anyType{}")) {
                            File_Name = root.getProperty("File_Name").toString();

                        } else {
                            File_Name = "";
                        }
                    } else {
                        File_Name = "";
                    }

                    if (root.getProperty("Is_Download") != null) {

                        if (!root.getProperty("Is_Download").toString().equalsIgnoreCase("anyType{}")) {
                            Is_Download = root.getProperty("Is_Download").toString();

                        } else {
                            Is_Download = "";
                        }
                    } else {
                        Is_Download = "";
                    }

                    if (root.getProperty("Is_Edit") != null) {

                        if (!root.getProperty("Is_Edit").toString().equalsIgnoreCase("anyType{}")) {
                            Is_Edit = root.getProperty("Is_Edit").toString();

                        } else {
                            Is_Edit = "";
                        }
                    } else {
                        Is_Edit = "";
                    }

                    if (root.getProperty("Is_View") != null) {

                        if (!root.getProperty("Is_View").toString().equalsIgnoreCase("anyType{}")) {
                            Is_View = root.getProperty("Is_View").toString();

                        } else {
                            Is_View = "";
                        }
                    } else {
                        Is_View = "";
                    }

                    if (root.getProperty("Legal_Entity") != null) {

                        if (!root.getProperty("Legal_Entity").toString().equalsIgnoreCase("anyType{}")) {
                            Legal_Entity = root.getProperty("Legal_Entity").toString();

                        } else {
                            Legal_Entity = "";
                        }
                    } else {
                        Legal_Entity = "";
                    }

                    if (root.getProperty("Level2_id") != null) {

                        if (!root.getProperty("Level2_id").toString().equalsIgnoreCase("anyType{}")) {
                            Level2_id = root.getProperty("Level2_id").toString();

                        } else {
                            Level2_id = "";
                        }
                    } else {
                        Level2_id = "";
                    }

                    if (root.getProperty("Level3_id") != null) {

                        if (!root.getProperty("Level3_id").toString().equalsIgnoreCase("anyType{}")) {
                            Level3_id = root.getProperty("Level3_id").toString();

                        } else {
                            Level3_id = "";
                        }
                    } else {
                        Level3_id = "";
                    }

                    if (root.getProperty("Level4_id") != null) {

                        if (!root.getProperty("Level4_id").toString().equalsIgnoreCase("anyType{}")) {
                            Level4_id = root.getProperty("Level4_id").toString();

                        } else {
                            Level4_id = "";
                        }
                    } else {
                        Level4_id = "";
                    }

                    if (root.getProperty("Level5_id") != null) {

                        if (!root.getProperty("Level5_id").toString().equalsIgnoreCase("anyType{}")) {
                            Level5_id = root.getProperty("Level5_id").toString();

                        } else {
                            Level5_id = "";
                        }
                    } else {
                        Level5_id = "";
                    }

                    if (root.getProperty("Level6_id") != null) {

                        if (!root.getProperty("Level6_id").toString().equalsIgnoreCase("anyType{}")) {
                            Level6_id = root.getProperty("Level6_id").toString();

                        } else {
                            Level6_id = "";
                        }
                    } else {
                        Level6_id = "";
                    }

                    if (root.getProperty("Level7_id") != null) {

                        if (!root.getProperty("Level7_id").toString().equalsIgnoreCase("anyType{}")) {
                            Level7_id = root.getProperty("Level7_id").toString();

                        } else {
                            Level7_id = "";
                        }
                    } else {
                        Level7_id = "";
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

                    if (root.getProperty("RoleName") != null) {

                        if (!root.getProperty("RoleName").toString().equalsIgnoreCase("anyType{}")) {
                            RoleName = root.getProperty("RoleName").toString();

                        } else {
                            RoleName = "";
                        }
                    } else {
                        RoleName = "";
                    }

                    if (root.getProperty("Status") != null) {

                        if (!root.getProperty("Status").toString().equalsIgnoreCase("anyType{}")) {
                            Status = root.getProperty("Status").toString();

                        } else {
                            Status = "";
                        }
                    } else {
                        Status = "";
                    }

                    if (root.getProperty("Trans_Dtl_Id") != null) {

                        if (!root.getProperty("Trans_Dtl_Id").toString().equalsIgnoreCase("anyType{}")) {
                            Trans_Dtl_Id = root.getProperty("Trans_Dtl_Id").toString();

                        } else {
                            Trans_Dtl_Id = "";
                        }
                    } else {
                        Trans_Dtl_Id = "";
                    }

                    if (root.getProperty("message") != null) {

                        if (!root.getProperty("message").toString().equalsIgnoreCase("anyType{}")) {
                            message = root.getProperty("message").toString();

                        } else {
                            message = "";
                        }
                    } else {
                        message = "";
                    }

                    if (root.getProperty("type") != null) {

                        if (!root.getProperty("type").toString().equalsIgnoreCase("anyType{}")) {
                            type = root.getProperty("type").toString();

                        } else {
                            type = "";
                        }
                    } else {
                        type = "";
                    }

                    String selection = "id = ?";
                    id = id+1;
                    // WHERE clause arguments
                    String[] selectionArgs = {id+""};
                    //            "id","value", "text", "parent_Ref", "updated_date"
                    String valuesArray[] = {id+"", Inv_count, status, roleID};
                    boolean output = KHIL.dbCon.updateBulk(DbHelper.DASHBOARD_DATA, selection, valuesArray, utils.columnNames_Dashboard_Data, selectionArgs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

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
                legalEntityModel.setLegal_id(cursor1.getString(cursor1.getColumnIndex("legal_id")));
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
                legalEntityModel.setLegal_id(cursorAll.getString(cursorAll.getColumnIndex("legal_id")));
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

    private void FetchLeveldata() {
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
                dashtxtL2.setAdapter(adapter1);
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
                dashtxtL3.setAdapter(adapter1);
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
                dashtxtL4.setAdapter(adapter1);
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
                dashtxtL5.setAdapter(adapter1);
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
                dashtxtL6.setAdapter(adapter1);
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
                dashtxtL4.setAdapter(adapter1);
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
                dashtxtL5.setAdapter(adapter1);
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
                dashtxtL6.setAdapter(adapter1);
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
                dashtxtL4.setAdapter(adapter1);
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
                dashtxtL5.setAdapter(adapter1);
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
                dashtxtL6.setAdapter(adapter1);
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
                dashtxtL7.setAdapter(adapter1);
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
                dashtxtL2.setAdapter(adapter1);
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
                dashtxtL3.setAdapter(adapter1);
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
                dashtxtL4.setAdapter(adapter1);
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
                dashtxtL4.setAdapter(adapter1);
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
                dashtxtL5.setAdapter(adapter1);
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
                dashtxtL6.setAdapter(adapter1);
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
                dashtxtL7.setAdapter(adapter1);
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
                dashtxtL3.setAdapter(adapter1);
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
                dashtxtL4.setAdapter(adapter1);
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
                dashtxtL5.setAdapter(adapter1);
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
                dashtxtL4.setAdapter(adapter1);
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
                dashtxtL5.setAdapter(adapter1);
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
                dashtxtL6.setAdapter(adapter1);
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
                dashtxtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /////////////////END

}

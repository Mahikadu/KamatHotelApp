package com.example.admin.kamathotelapp.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Fragments.QC1;
import com.example.admin.kamathotelapp.KHIL;
import com.example.admin.kamathotelapp.Model.DashboardModel;
import com.example.admin.kamathotelapp.Model.QC1Model;
import com.example.admin.kamathotelapp.NavigationDrawerActivity;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11-09-2017.
 */

public class QC1Adapter extends BaseAdapter {

    private Context mContext;
    private List<QC1Model> qc1ModelList;
    public List<QC1Model> qc1QIDModelList;
    private static LayoutInflater inflater = null;
    QC1Adapter adapter;
    private QC1Model qc1Model;
    boolean click = false;

    public QC1Adapter(Context mContext, List<QC1Model> qc1Modellist) {
        this.mContext = mContext;
        this.qc1ModelList = qc1Modellist;

    }

    @Override
    public int getCount() {
        return qc1ModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return qc1ModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final QC1Adapter.ViewHolder viewHolder;

        if (inflater == null)
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new QC1Adapter.ViewHolder();
            convertView = inflater.inflate(R.layout.qc1_adapter_item, null);
            viewHolder.txtDept = (TextView) convertView.findViewById(R.id.txtDept);
            viewHolder.txtDocumentNo = (TextView) convertView.findViewById(R.id.txtDocumentNo);
            viewHolder.txtCreby = (TextView) convertView.findViewById(R.id.txtCreby);
            viewHolder.txtCredt = (TextView) convertView.findViewById(R.id.txtCredt);
            viewHolder.txtYear = (TextView) convertView.findViewById(R.id.txtYear);
            viewHolder.txtQuater = (TextView) convertView.findViewById(R.id.txtQuater);
            viewHolder.txtMonth = (TextView) convertView.findViewById(R.id.txtMonth);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            viewHolder.txtDetails = (TextView) convertView.findViewById(R.id.txtDetails);
            viewHolder.txtQC1 = (TextView) convertView.findViewById(R.id.txtQC1);
            viewHolder.qc1_detailslayout = (LinearLayout) convertView.findViewById(R.id.qc1_detailslayout);
            viewHolder.document_no = (TextView) convertView.findViewById(R.id.document_no);
            viewHolder.filename_txt = (TextView) convertView.findViewById(R.id.filename_txt);
            viewHolder.qc1_txt = (TextView) convertView.findViewById(R.id.qc1_txt);
            viewHolder.view_txt = (TextView) convertView.findViewById(R.id.view_txt);
            viewHolder.download_txt = (TextView) convertView.findViewById(R.id.download_txt);
            viewHolder.closebtn = (TextView) convertView.findViewById(R.id.closebtn);

            viewHolder.txtQC1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* */
                    qc1QIDModelList = new ArrayList<>();
                    click = true;

                    qc1Model = qc1ModelList.get(position);
                    Cursor cursor = null;
                    String strLastSynctable = "0";
                    String where = " where Q_id = '" + qc1Model.getQ_Id() + "'" + "and last_sync = '" + strLastSynctable +"'";
                    cursor = KHIL.dbCon.fetchFromSelect(DbHelper.QC1_DATA, where);
                    if (cursor != null && cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            qc1Model = createQC1Model(cursor);
                            qc1QIDModelList.add(qc1Model);
                        } while (cursor.moveToNext());
                        cursor.close();
                    }

                    QC1 fragment = new QC1();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = ((NavigationDrawerActivity)mContext).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content,fragment);
                    fragmentTransaction.commit();

                    Bundle args = new Bundle();
                    args.putBoolean("Click",click);
                    args.putInt("position",position);
                    args.putSerializable("QC1_QIDlist", (Serializable) qc1QIDModelList);
                    fragment.setArguments(args);
                }
            });

            viewHolder.txtDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    qc1QIDModelList = new ArrayList<>();
                    qc1Model = qc1ModelList.get(position);

                    viewHolder.qc1_detailslayout.setVisibility(View.VISIBLE);
                    viewHolder.document_no.setText(qc1Model.getDocNo());
                    viewHolder.filename_txt.setText(qc1Model.getFile_Name());
                    viewHolder.qc1_txt.setText("QC1");
                    viewHolder.view_txt.setText("View");
                    viewHolder.download_txt.setText("Download");
                }
            });

            viewHolder.closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.qc1_detailslayout.setVisibility(View.GONE);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (QC1Adapter.ViewHolder) convertView.getTag();
        }
        qc1Model = qc1ModelList.get(position);
        viewHolder.txtDept.setText(qc1Model.getDept());
        viewHolder.txtDocumentNo.setText(qc1Model.getDocNo());
        viewHolder.txtCreby.setText(qc1Model.getCreatedBy());
        viewHolder.txtCredt.setText(qc1Model.getCreatedDate());
        viewHolder.txtYear.setText(qc1Model.getYeartext());
        viewHolder.txtQuater.setText(qc1Model.getQuartertext());
        viewHolder.txtMonth.setText(qc1Model.getMonthtext());
        viewHolder.txtStatus.setText(qc1Model.getStatus());


        return convertView;
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

    public class ViewHolder {
        private TextView txtDept, txtDocumentNo, txtCreby, txtCredt, txtYear, txtQuater, txtMonth, txtStatus,txtDetails,
                txtQC1,document_no,filename_txt,qc1_txt,view_txt,download_txt,closebtn;
        private LinearLayout qc1_detailslayout;
    }
}

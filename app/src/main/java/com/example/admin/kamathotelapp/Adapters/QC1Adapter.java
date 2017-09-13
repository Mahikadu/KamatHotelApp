package com.example.admin.kamathotelapp.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Fragments.QC1;
import com.example.admin.kamathotelapp.Model.DashboardModel;
import com.example.admin.kamathotelapp.Model.QC1Model;
import com.example.admin.kamathotelapp.NavigationDrawerActivity;
import com.example.admin.kamathotelapp.R;

import java.util.List;

/**
 * Created by Admin on 11-09-2017.
 */

public class QC1Adapter extends BaseAdapter {

    private Context mContext;
    private List<QC1Model> qc1ModelList;
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
//            viewHolder.txtDetails = (TextView) convertView.findViewById(R.id.txtDetails);
            viewHolder.txtQC1 = (TextView) convertView.findViewById(R.id.txtQC1);

            viewHolder.txtQC1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = true;
                    qc1Model = qc1ModelList.get(position);
                    QC1 fragment = new QC1();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = ((NavigationDrawerActivity)mContext).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content,fragment);
                    fragmentTransaction.commit();

                    Bundle args = new Bundle();
                    args.putBoolean("Click",click);
                    args.putSerializable("QC1", qc1Model);
                    fragment.setArguments(args);
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
        viewHolder.txtYear.setText(qc1Model.getYear());
        viewHolder.txtQuater.setText(qc1Model.getQuarter());
        viewHolder.txtMonth.setText(qc1Model.getMonth());
        viewHolder.txtStatus.setText(qc1Model.getStatus());
//        viewHolder.txtDetails.setText(qc1Model.getd());
//        viewHolder.txtQC1.setText(qc1Model.getq());

        return convertView;
    }

    public class ViewHolder {
        private TextView txtDept, txtDocumentNo, txtCreby, txtCredt, txtYear, txtQuater, txtMonth, txtStatus,txtQC1;
    }
}

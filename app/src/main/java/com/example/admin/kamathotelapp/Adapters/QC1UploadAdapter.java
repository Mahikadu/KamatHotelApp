package com.example.admin.kamathotelapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Fragments.QC1;
import com.example.admin.kamathotelapp.Model.QC1Model;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.NavigationDrawerActivity;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.SharedPref;

import java.util.List;

/**
 * Created by Admin on 13-09-2017.
 */

public class QC1UploadAdapter extends BaseAdapter {

    private Context mContext;
    private List<QC1Model> qc1uploadModelList;
    private SharedPref sharedPref;
    private QC1Model qc1Model;
    private static LayoutInflater inflater = null;

    public QC1UploadAdapter(Context mContext, List<QC1Model> qc1uploadModelList) {
        this.mContext = mContext;
        sharedPref = new SharedPref(this.mContext);
        this.qc1uploadModelList = qc1uploadModelList;
    }

    @Override
    public int getCount() {
        return qc1uploadModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return qc1uploadModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final QC1UploadAdapter.ViewHolder viewHolder;

        if (inflater == null)
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new QC1UploadAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.qc1_upload_item, null);
            viewHolder.txtSrNoqc1 = (TextView) convertView.findViewById(R.id.txtSrNoqc1);
            viewHolder.txtLevel2qc1 = (TextView) convertView.findViewById(R.id.txtLevel2qc1);
            viewHolder.txtLevel3qc1 = (TextView) convertView.findViewById(R.id.txtLevel3qc1);
            viewHolder.txtLevel4qc1 = (TextView) convertView.findViewById(R.id.txtLevel4qc1);
            viewHolder.txtLevel5qc1 = (TextView) convertView.findViewById(R.id.txtLevel5qc1);
            viewHolder.txtLevel6qc1 = (TextView) convertView.findViewById(R.id.txtLevel6qc1);
            viewHolder.txtFilenNameqc1 = (TextView) convertView.findViewById(R.id.txtFilenNameqc1);
            viewHolder.txtEditqc1 = (TextView) convertView.findViewById(R.id.txtEditqc1);
            viewHolder.txtViewqc1 = (TextView) convertView.findViewById(R.id.txtViewqc1);

           /* viewHolder.txtQC1.setOnClickListener(new View.OnClickListener() {
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
            });*/

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (QC1UploadAdapter.ViewHolder) convertView.getTag();
        }
        qc1Model = qc1uploadModelList.get(position);
        viewHolder.txtSrNoqc1.setText(String.valueOf(position+1));
        viewHolder.txtLevel2qc1.setText(qc1Model.getLevel2_Id());
        viewHolder.txtLevel3qc1.setText(qc1Model.getLevel3_Id());
        viewHolder.txtLevel4qc1.setText(qc1Model.getLevel4_Id());
        viewHolder.txtLevel5qc1.setText(qc1Model.getLevel5_Id());
        viewHolder.txtLevel6qc1.setText(qc1Model.getLevel6_Id());
        viewHolder.txtFilenNameqc1.setText(qc1Model.getFile_Name());


        return convertView;
    }

    public class ViewHolder {
        private TextView txtSrNoqc1, txtLevel2qc1, txtLevel3qc1, txtLevel4qc1, txtLevel5qc1, txtLevel6qc1, txtFilenNameqc1, txtEditqc1,txtViewqc1;
    }
}

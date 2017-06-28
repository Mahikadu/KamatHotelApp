package com.example.admin.kamathotelapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.R;

import java.util.List;

import me.grantland.widget.AutofitHelper;

/**
 * Created by Admin on 6/17/2017.
 */
public class UploadAdapter extends BaseAdapter {

    private Context mContext;
    private List<UploadModel> uploadModelList;
    private UploadModel uploadModel;
    boolean result = false;

    private static LayoutInflater inflater = null;

    public UploadAdapter(Context mContext, List<UploadModel> uploadModelList) {
        this.mContext = mContext;
        this.uploadModelList = uploadModelList;
    }

    @Override
    public int getCount() {
        return uploadModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return uploadModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (inflater == null)
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.upload_adapter_item, null);
            viewHolder = new ViewHolder();

            viewHolder.txtSrNo = (TextView) convertView.findViewById(R.id.txtSrNo);
            AutofitHelper.create(viewHolder.txtSrNo);
            viewHolder.txtLevel2 = (TextView) convertView.findViewById(R.id.txtLevel2);
            AutofitHelper.create(viewHolder.txtLevel2);
            viewHolder.txtLevel3 = (TextView) convertView.findViewById(R.id.txtLevel3);
            AutofitHelper.create(viewHolder.txtLevel3);
            viewHolder.txtLevel4 = (TextView) convertView.findViewById(R.id.txtLevel4);
            AutofitHelper.create(viewHolder.txtLevel4);
            viewHolder.txtLevel5 = (TextView) convertView.findViewById(R.id.txtLevel5);
            AutofitHelper.create(viewHolder.txtLevel5);
            viewHolder.txtLevel6 = (TextView) convertView.findViewById(R.id.txtLevel6);
            AutofitHelper.create(viewHolder.txtLevel6);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        uploadModel = uploadModelList.get(position);

        viewHolder.txtSrNo.setText("1");
        viewHolder.txtLevel2.setText(uploadModel.getLevel2());
        viewHolder.txtLevel2.setTag(position);
        viewHolder.txtLevel3.setText(uploadModel.getLevel3());
        viewHolder.txtLevel3.setTag(position);
        viewHolder.txtLevel4.setText(uploadModel.getLevel4());
        viewHolder.txtLevel4.setTag(position);
        viewHolder.txtLevel5.setText(uploadModel.getLevel5());
        viewHolder.txtLevel5.setTag(position);
        viewHolder.txtLevel6.setText(uploadModel.getLevel6());
        viewHolder.txtLevel6.setTag(position);

        /*viewHolder.plan_view.setTag(position);
        viewHolder.plan_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                show_Plan_Detail_Dialog(planDetailsModelList.get(pos));
            }
        });
        viewHolder.deletePlanDetail.setTag(position);*/


        return convertView;
    }

    public class ViewHolder {
        private TextView txtSrNo, txtLevel2, txtLevel3, txtLevel4, txtLevel5, txtLevel6, txtFileName;
        private ImageView actionDelete, actionEdit;
    }
}

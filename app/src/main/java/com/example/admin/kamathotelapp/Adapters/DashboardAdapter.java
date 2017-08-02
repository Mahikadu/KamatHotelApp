package com.example.admin.kamathotelapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Model.DashboardModel;
import com.example.admin.kamathotelapp.R;

import java.util.List;

/**
 * Created by Admin on 7/28/2017.
 */
public class DashboardAdapter extends BaseAdapter {

    private Context mContext;
    private List<DashboardModel> dashboardModelList;
    private static LayoutInflater inflater = null;
    DashboardAdapter adapter;
    private DashboardModel dashboardModel;

    public DashboardAdapter(Context mContext, List<DashboardModel> dashboardModels) {
        this.mContext = mContext;
        this.dashboardModelList = dashboardModels;
    }

    @Override
    public int getCount() {
        return dashboardModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return dashboardModelList.get(position);
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
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.dashboard_adapter_item, null);
            viewHolder.txtRole = (TextView) convertView.findViewById(R.id.txtRole);
            viewHolder.txtDocNo = (TextView) convertView.findViewById(R.id.txtDocNo);
            viewHolder.txtFile = (TextView) convertView.findViewById(R.id.txtFile);
            viewHolder.txtCreBy = (TextView) convertView.findViewById(R.id.txtCreBy);
            viewHolder.txtCreDate = (TextView) convertView.findViewById(R.id.txtCreDate);
            viewHolder.txtYear = (TextView) convertView.findViewById(R.id.txtYear);
            viewHolder.txtQuarter = (TextView) convertView.findViewById(R.id.txtQuarter);
            viewHolder.txtMonth = (TextView) convertView.findViewById(R.id.txtMonth);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            viewHolder.txtDetails = (TextView) convertView.findViewById(R.id.txtDetails);
            viewHolder.txtView = (TextView) convertView.findViewById(R.id.txtView);
            viewHolder.txtDownload = (TextView) convertView.findViewById(R.id.txtDownload);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        dashboardModel = dashboardModelList.get(position);
        viewHolder.txtRole.setText(dashboardModel.getRole());
        viewHolder.txtDocNo.setText(dashboardModel.getDocNo());
        viewHolder.txtFile.setText(dashboardModel.getFileName());
        viewHolder.txtCreBy.setText(dashboardModel.getCreatedBy());
        viewHolder.txtCreDate.setText(dashboardModel.getCreatedDate());
        viewHolder.txtYear.setText(dashboardModel.getYear());
        viewHolder.txtQuarter.setText(dashboardModel.getQuarter());
        viewHolder.txtMonth.setText(dashboardModel.getMonth());
        viewHolder.txtStatus.setText(dashboardModel.getStatus());

        return convertView;
    }

    public class ViewHolder {
        private TextView txtRole, txtDocNo, txtFile, txtCreBy, txtCreDate, txtYear, txtQuarter, txtMonth, txtStatus,
                txtDetails, txtView, txtDownload;
    }
}

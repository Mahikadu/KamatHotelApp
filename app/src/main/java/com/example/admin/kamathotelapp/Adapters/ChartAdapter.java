package com.example.admin.kamathotelapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Model.ChartDataModel;
import com.example.admin.kamathotelapp.Model.DashboardModel;
import com.example.admin.kamathotelapp.R;

import java.util.List;

/**
 * Created by Admin on 28-08-2017.
 */

public class ChartAdapter extends BaseAdapter {

    private Context mContext;
    private List<ChartDataModel> chartModelList;
    //private static LayoutInflater inflater = null;
    // private ChartDataModel chartDataModel;

    public ChartAdapter(Context mContext, List<ChartDataModel> chartDataModel) {
        this.mContext = mContext;
        this.chartModelList = chartDataModel;
    }

    @Override
    public int getCount() {
        return chartModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return chartModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (inflater == null)
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.chart_adpter_item, null);
            viewHolder.txtLegal_Entity = (TextView) convertView.findViewById(R.id.txtLegal_Entity);
            viewHolder.txtProperty = (TextView) convertView.findViewById(R.id.txtProperty);
            viewHolder.txtLocation = (TextView) convertView.findViewById(R.id.txtLocation);
            viewHolder.txtType_Of_Document = (TextView) convertView.findViewById(R.id.txtType_Of_Document);
            viewHolder.txtSelected_Document = (TextView) convertView.findViewById(R.id.txtSelected_Document);
            viewHolder.txtSupporting_Document = (TextView) convertView.findViewById(R.id.txtSupporting_Document);
            viewHolder.txtAnnexure = (TextView) convertView.findViewById(R.id.txtAnnexure);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChartDataModel chartDataModel = chartModelList.get(position);
        viewHolder.txtLegal_Entity.setText(chartDataModel.getLegalEntity());
        viewHolder.txtProperty.setText(chartDataModel.getProperty());
        viewHolder.txtLocation.setText(chartDataModel.getLocation());
        viewHolder.txtType_Of_Document.setText(chartDataModel.getType_Of_Document());
        viewHolder.txtSelected_Document.setText(chartDataModel.getSelected_Document());
        viewHolder.txtSupporting_Document.setText(chartDataModel.getSupporting_Document());
        viewHolder.txtAnnexure.setText(chartDataModel.getAnnexure());


        return convertView;
    }*/

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        convertView = null;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.chart_adpter_item, null);
            TextView txtLegal_Entity = (TextView) convertView.findViewById(R.id.txtLegal_Entity);
            TextView txtProperty = (TextView) convertView.findViewById(R.id.txtProperty);
            TextView txtLocation = (TextView) convertView.findViewById(R.id.txtLocation);
            TextView txtType_Of_Document = (TextView) convertView.findViewById(R.id.txtType_Of_Document);
            TextView txtSelected_Document = (TextView) convertView.findViewById(R.id.txtSelected_Document);
            TextView txtSupporting_Document = (TextView) convertView.findViewById(R.id.txtSupporting_Document);
            TextView txtAnnexure = (TextView) convertView.findViewById(R.id.txtAnnexure);
            TextView txtView = (TextView) convertView.findViewById(R.id.txtView);
            TextView txtDownload = (TextView) convertView.findViewById(R.id.txtDownload);
            TextView txtdelete = (TextView) convertView.findViewById(R.id.txtdelete);


            ChartDataModel chartDataModel = chartModelList.get(position);

            txtLegal_Entity.setText(chartDataModel.getLegalEntity());
            txtProperty.setText(chartDataModel.getProperty());
            txtLocation.setText(chartDataModel.getLocation());
            txtType_Of_Document.setText(chartDataModel.getType_Of_Document());
            txtSelected_Document.setText(chartDataModel.getSelected_Document());
            txtSupporting_Document.setText(chartDataModel.getSupporting_Document());
            txtAnnexure.setText(chartDataModel.getAnnexure());


        }
        return convertView;
    }

   /* public class ViewHolder {
        private TextView txtLegal_Entity, txtProperty, txtLocation, txtType_Of_Document, txtSelected_Document, txtSupporting_Document, txtAnnexure;
    }*/
}

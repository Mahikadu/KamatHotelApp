package com.example.admin.kamathotelapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.kamathotelapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 14-07-2017.
 */

public class GridDataAdapter extends RecyclerView.Adapter<GridDataAdapter.ViewHolder> {

    private ArrayList<String> android;
    private Context context;

    public GridDataAdapter(Context context,ArrayList<String> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public GridDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridDataAdapter.ViewHolder viewHolder, int position) {

        String imagepath = android.get(position);
        Bitmap photoBitmap = BitmapFactory.decodeFile(imagepath);
        if(photoBitmap!=null)
        {
            photoBitmap = Bitmap.createScaledBitmap (photoBitmap, 240 , 200 , false);

            viewHolder.img_android.setImageBitmap(photoBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }


}

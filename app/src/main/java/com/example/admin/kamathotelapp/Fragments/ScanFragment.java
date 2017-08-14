package com.example.admin.kamathotelapp.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.admin.kamathotelapp.Adapters.GridDataAdapter;

import com.example.admin.kamathotelapp.MyRecyclerItemClickListener;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.ExceptionHandler;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




public class ScanFragment extends Fragment {

    private static final int REQUEST_CODE = 99;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,camera,gallary;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private GridView gridView;
    private GridDataAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar mProgressBar;

    private ArrayList<String> imageItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().setTitle("Document Scan");
        //////////Crash Report
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        init(view);
        return view;
    }

    @SuppressLint("WrongConstant")
    private void init(View view) {
        /////Recycle Gridview

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.VISIBLE);

        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/scanCrop/";
        File targetDirector = new File(targetPath);
        try {
            if(targetDirector.exists()) {
                new LoadImage().execute();
            }else{
                mProgressBar.setVisibility(View.GONE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        recyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);



        recyclerView.addOnItemTouchListener(
                new MyRecyclerItemClickListener(getActivity(), new MyRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showCustomDialog(view,position);
                    }
                })
        );


        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        camera = (FloatingActionButton)view.findViewById(R.id.camera);
        gallary = (FloatingActionButton)view.findViewById(R.id.gallary);
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });
        camera.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA));
        gallary.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_MEDIA));
    }


    private void showCustomDialog(View v, int position) {

        final AlertDialog.Builder DialogMaster = new AlertDialog.Builder(getActivity());

        @SuppressLint("WrongConstant")
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogViewMaster = li.inflate(R.layout.image_dailog, null);
        DialogMaster.setView(dialogViewMaster);

        final AlertDialog showMaster = DialogMaster.show();

        Button btnDismissMaster = (Button) showMaster.findViewById(R.id.buttonClose);
        ImageView image = (ImageView) showMaster.findViewById(R.id.imageviewDialog);

        try {
            v.setDrawingCacheEnabled(true);
            v.buildDrawingCache();
            Bitmap bitmap = v.getDrawingCache();
            image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDismissMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMaster.dismiss();
            }
        });
        showMaster.setCancelable(false);

    }

    public class LoadImage extends AsyncTask<Object,Void,Object>{

        @Override
        protected Object doInBackground(Object... params) {

            String ExternalStorageDirectoryPath = Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath();

            String targetPath = ExternalStorageDirectoryPath + "/scanCrop/";
            File targetDirector = new File(targetPath);

            File[] files = targetDirector.listFiles();
            imageItems = new ArrayList<>();
            for (File file : files) {
                imageItems.add(file.getAbsolutePath());
            }

            return imageItems;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(Object o) {
            if(imageItems.size()>0){
                mProgressBar.setVisibility(View.GONE);

                adapter = new GridDataAdapter(getActivity(),imageItems);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            camera.startAnimation(fab_close);
            gallary.startAnimation(fab_close);
            camera.setClickable(false);
            gallary.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            camera.startAnimation(fab_open);
            gallary.startAnimation(fab_open);
            camera.setClickable(true);
            gallary.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    private class ScanButtonClickListener implements View.OnClickListener {

        private int preference;

        public ScanButtonClickListener(int preference) {

            this.preference = preference;
        }


        @Override
        public void onClick(View v) {

            startScan(preference);
        }
    }

    protected void startScan(int preference) {
        Intent intent = new Intent(getActivity(), ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                if(isFabOpen){

                    fab.startAnimation(rotate_backward);
                    camera.startAnimation(fab_close);
                    gallary.startAnimation(fab_close);
                    camera.setClickable(false);
                    gallary.setClickable(false);
                    isFabOpen = false;
                    Log.d("Raj", "close");

                }
                String pathToImage = getRealPathFromURI(uri);;
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                getActivity().getContentResolver().delete(uri, null, null);

                ////set image on gridview
                String ExternalStorageDirectoryPath = Environment
                        .getExternalStorageDirectory()
                        .getAbsolutePath();

                String targetPath = ExternalStorageDirectoryPath + "/scanCrop/";
                File targetDirector = new File(targetPath);

                File[] files = targetDirector.listFiles();
                imageItems = new ArrayList<>();
                for (File file : files) {
                    imageItems.add(file.getAbsolutePath());
                }

                adapter = new GridDataAdapter(getActivity(),imageItems);
                recyclerView.setAdapter(adapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}

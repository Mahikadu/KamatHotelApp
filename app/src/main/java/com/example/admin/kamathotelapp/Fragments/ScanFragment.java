package com.example.admin.kamathotelapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.ExceptionHandler;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.IOException;


public class ScanFragment extends Fragment {

    private static final int REQUEST_CODE = 99;
    private Button cameraButton;
    private Button mediaButton;
    private ImageView scannedImageView;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,camera,gallary;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //////////Crash Report
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
//        cameraButton = (Button) view.findViewById(R.id.cameraButton);
//        cameraButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA));
//        mediaButton = (Button) view.findViewById(R.id.mediaButton);
//        mediaButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_MEDIA));
        scannedImageView = (ImageView) view.findViewById(R.id.scannedImage);

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
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                getActivity().getContentResolver().delete(uri, null, null);
                scannedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

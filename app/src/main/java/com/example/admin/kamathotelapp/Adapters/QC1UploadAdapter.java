package com.example.admin.kamathotelapp.Adapters;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.kamathotelapp.Fragments.QC1;

import com.example.admin.kamathotelapp.KHIL;
import com.example.admin.kamathotelapp.Model.QC1Model;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

    boolean result = false;
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
    String chk = "";
    private List<String> level2list,level3list,level4list,level5list,level6list;
    private List<String> level4listHR, level5listHR, level6listHR;
    private List<String> level4listCMD, level5listCMD, level6listCMD, level7listCMD;
    private List<String> level2listCS,level3listCS,level4listCS;
    private List<String> level4listMAR, level5listMAR, level6listMAR, level7listMAR;
    private List<String> level3listPER, level4listPER, level5listPER;
    private List<String> level4listLEGAL, level5listLEGAL, level6listLEGAL, level7listLEGAL;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

            viewHolder.txtViewqc1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    qc1Model = qc1uploadModelList.get(position);
                    String filePath = qc1Model.getFile_Path();
                    try {
                        openFile(mContext, filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            viewHolder.txtEditqc1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QC1.btnAdd.setVisibility(View.GONE);
                    QC1.qc1layout_edit.setVisibility(View.VISIBLE);
                    QC1.choosefilelayout.setVisibility(View.GONE);

                    qc1Model = qc1uploadModelList.get(position);
                    QC1.QID = Integer.parseInt(qc1Model.getQ_Id());

                   /* if(!qc1Model.getFile_Name().equalsIgnoreCase("")) {
                        QC1.qc1txtNoFile.setText(qc1Model.getFile_Name());
                    }*/
                    if(sharedPref.getLoginId().equalsIgnoreCase("finance") ||
                            sharedPref.getLoginId().equalsIgnoreCase("financeQC1")) {

                        QC1.cardlevel4.setVisibility(View.GONE);
                        QC1.qc1txtL4.setVisibility(View.GONE);
                        QC1.qc1txtL4.setText("");
                        QC1.cardlevel5.setVisibility(View.GONE);
                        QC1.qc1txtL5.setVisibility(View.GONE);
                        QC1.qc1txtL5.setText("");
                        QC1.cardlevel6.setVisibility(View.GONE);
                        QC1.qc1txtL6.setVisibility(View.GONE);
                        QC1.qc1txtL6.setText("");

                        QC1.qc1txtL2.setText(qc1Model.getLevel2_Id());
                        fetchLevel2dataFin();
                        QC1.cardlevel3.setVisibility(View.VISIBLE);
                        QC1.qc1txtL3.setVisibility(View.VISIBLE);
                        QC1.qc1txtL3.setText(qc1Model.getLevel3_Id());

                        if(!qc1Model.getLevel4_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel4.setVisibility(View.VISIBLE);
                            QC1.qc1txtL4.setVisibility(View.VISIBLE);
                            QC1.qc1txtL4.setText(qc1Model.getLevel4_Id());
                        }
                        if(!qc1Model.getLevel5_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel5.setVisibility(View.VISIBLE);
                            QC1.qc1txtL5.setVisibility(View.VISIBLE);
                            QC1.qc1txtL5.setText(qc1Model.getLevel5_Id());
                        }
                        if(!qc1Model.getLevel6_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setText(qc1Model.getLevel6_Id());
                        }

                        fetchLevel3dataFin(qc1Model.getLevel2_Id());
                        fetchLevel4datafin(qc1Model.getLevel3_Id());
                        fetchLevel5datafin(qc1Model.getLevel4_Id());
                        fetchLevel6datafin(qc1Model.getLevel5_Id());
                    } else if(sharedPref.getLoginId().equalsIgnoreCase("cs") ||
                            sharedPref.getLoginId().equalsIgnoreCase("csQC1")) {

                        QC1.cardlevel4.setVisibility(View.GONE);
                        QC1.qc1txtL4.setVisibility(View.GONE);
                        QC1.qc1txtL4.setText("");

                        QC1.qc1txtL2.setText(qc1Model.getLevel2_Id());
                        fetchLevel2dataCS();
                        QC1.cardlevel3.setVisibility(View.VISIBLE);
                        QC1.qc1txtL3.setVisibility(View.VISIBLE);
                        QC1.qc1txtL3.setText(qc1Model.getLevel3_Id());

                        fetchLevel3dataCS(qc1Model.getLevel2_Id());

                        if(!qc1Model.getLevel4_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel4.setVisibility(View.VISIBLE);
                            QC1.qc1txtL4.setVisibility(View.VISIBLE);
                            QC1.qc1txtL4.setText(qc1Model.getLevel4_Id());
                        }

                        fetchLevel4dataCS(qc1Model.getLevel3_Id());

                    } else if(sharedPref.getLoginId().equalsIgnoreCase("cmd")||
                            sharedPref.getLoginId().equalsIgnoreCase("cmdQC1")) {

                        QC1.cardlevel6.setVisibility(View.GONE);
                        QC1.qc1txtL7.setVisibility(View.GONE);
                        QC1.qc1txtL7.setText("");

                        QC1.qc1txtL4.setText(qc1Model.getLevel4_Id());
                        fetchLevel4dataCMD();
                        QC1.cardlevel5.setVisibility(View.VISIBLE);
                        QC1.qc1txtL5.setVisibility(View.VISIBLE);
                        QC1.qc1txtL5.setText(qc1Model.getLevel5_Id());
                        QC1.qc1txtL6.setVisibility(View.GONE);
                        QC1.cardlevel7.setVisibility(View.GONE);

                        if(!qc1Model.getLevel6_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setText(qc1Model.getLevel6_Id());
                        }
                        if(!qc1Model.getLevel7_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel7.setVisibility(View.VISIBLE);
                            QC1.qc1txtL7.setVisibility(View.VISIBLE);
                            QC1.qc1txtL7.setText(qc1Model.getLevel7_Id());
                        }
                        fetchLevel5dataCMD(qc1Model.getLevel4_Id());
                        fetchLevel6dataCMD(qc1Model.getLevel5_Id());
                        fetchLevel7dataCMD(qc1Model.getLevel6_Id());

                    } else if(sharedPref.getLoginId().equalsIgnoreCase("legal")||
                            sharedPref.getLoginId().equalsIgnoreCase("legalQC1")) {

                        QC1.cardlevel6.setVisibility(View.GONE);
                        QC1.qc1txtL6.setVisibility(View.GONE);
                        QC1.qc1txtL6.setText("");
                        QC1.cardlevel7.setVisibility(View.GONE);
                        QC1.qc1txtL7.setVisibility(View.GONE);
                        QC1.qc1txtL7.setText("");

                        QC1.qc1txtL4.setText(qc1Model.getLevel4_Id());
                        fetchLevel4dataLEGAL();
                        QC1.cardlevel5.setVisibility(View.VISIBLE);
                        QC1.qc1txtL5.setVisibility(View.VISIBLE);
                        QC1.qc1txtL5.setText(qc1Model.getLevel5_Id());

                        fetchLevel5dataLEGAL(qc1Model.getLevel4_Id());

                        if(!qc1Model.getLevel6_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setText(qc1Model.getLevel6_Id());
                        }
                        if(!qc1Model.getLevel7_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel7.setVisibility(View.VISIBLE);
                            QC1.qc1txtL7.setVisibility(View.VISIBLE);
                            QC1.qc1txtL7.setText(qc1Model.getLevel7_Id());
                        }
                        fetchLevel6dataLEGAL(qc1Model.getLevel5_Id());
                        fetchLevel7dataLEGAL(qc1Model.getLevel6_Id());

                    } else if(sharedPref.getLoginId().equalsIgnoreCase("marketing")||
                            sharedPref.getLoginId().equalsIgnoreCase("marketingQC1")) {
                        QC1.cardlevel6.setVisibility(View.GONE);
                        QC1.qc1txtL6.setVisibility(View.GONE);
                        QC1.qc1txtL6.setText("");
                        QC1.cardlevel7.setVisibility(View.GONE);
                        QC1.qc1txtL7.setVisibility(View.GONE);
                        QC1.qc1txtL7.setText("");

                        QC1.qc1txtL4.setText(qc1Model.getLevel4_Id());
                        fetchLevel4dataMAR();
                        QC1.cardlevel5.setVisibility(View.VISIBLE);
                        QC1.qc1txtL5.setVisibility(View.VISIBLE);
                        QC1.qc1txtL5.setText(qc1Model.getLevel5_Id());

                        fetchLevel5dataMAR(qc1Model.getLevel4_Id());

                        if(!qc1Model.getLevel6_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setText(qc1Model.getLevel6_Id());
                        }
                        if(!qc1Model.getLevel7_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel7.setVisibility(View.VISIBLE);
                            QC1.qc1txtL7.setVisibility(View.VISIBLE);
                            QC1.qc1txtL7.setText(qc1Model.getLevel7_Id());
                        }

                        fetchLevel6dataMAR(qc1Model.getLevel5_Id());
                        fetchLevel7dataMAR(qc1Model.getLevel6_Id());


                    } else if(sharedPref.getLoginId().equalsIgnoreCase("hr")||
                            sharedPref.getLoginId().equalsIgnoreCase("hrQC1")) {

                        QC1.cardlevel6.setVisibility(View.GONE);
                        QC1.qc1txtL6.setVisibility(View.GONE);
                        QC1.qc1txtL6.setText("");

                        QC1.qc1txtL4.setText(qc1Model.getLevel4_Id());
                        fetchLevel4dataHR();
                        QC1.cardlevel5.setVisibility(View.VISIBLE);
                        QC1.qc1txtL5.setVisibility(View.VISIBLE);
                        QC1.qc1txtL5.setText(qc1Model.getLevel5_Id());

                        fetchLevel5dataHR(qc1Model.getLevel4_Id());

                        if(!qc1Model.getLevel6_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setVisibility(View.VISIBLE);
                            QC1.qc1txtL6.setText(qc1Model.getLevel6_Id());
                        }
                        fetchLevel6dataHR(qc1Model.getLevel5_Id());
                    } else if(sharedPref.getLoginId().equalsIgnoreCase("Personal")||
                            sharedPref.getLoginId().equalsIgnoreCase("PersonalQC1")) {

                        QC1.cardlevel5.setVisibility(View.GONE);
                        QC1.qc1txtL5.setVisibility(View.GONE);
                        QC1.qc1txtL5.setText("");

                        QC1.qc1txtL3.setText(qc1Model.getLevel3_Id());
                        fetchLevel3dataPER();
                        QC1.cardlevel4.setVisibility(View.VISIBLE);
                        QC1.qc1txtL4.setVisibility(View.VISIBLE);
                        QC1.qc1txtL4.setText(qc1Model.getLevel4_Id());

                        fetchLevel4dataPER(qc1Model.getLevel3_Id());

                        if(!qc1Model.getLevel5_Id().equalsIgnoreCase("")) {
                            QC1.cardlevel5.setVisibility(View.VISIBLE);
                            QC1.qc1txtL5.setVisibility(View.VISIBLE);
                            QC1.qc1txtL5.setText(qc1Model.getLevel5_Id());
                        }
                        fetchLevel5dataPER(qc1Model.getLevel4_Id());
                    }
                }
            });

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

        if(sharedPref.getLoginId().equalsIgnoreCase("finance") ||
                sharedPref.getLoginId().equalsIgnoreCase("financeQC1")) {

            viewHolder.txtLevel2qc1.setText(qc1Model.getLevel2_Id());
            viewHolder.txtLevel2qc1.setTag(position);
            viewHolder.txtLevel3qc1.setText(qc1Model.getLevel3_Id());
            viewHolder.txtLevel3qc1.setTag(position);
            viewHolder.txtLevel4qc1.setText(qc1Model.getLevel4_Id());
            viewHolder.txtLevel4qc1.setTag(position);
            viewHolder.txtLevel5qc1.setText(qc1Model.getLevel5_Id());
            viewHolder.txtLevel5qc1.setTag(position);
            viewHolder.txtLevel6qc1.setText(qc1Model.getLevel6_Id());
            viewHolder.txtLevel6qc1.setTag(position);
        } else if(sharedPref.getLoginId().equalsIgnoreCase("hr")||
                sharedPref.getLoginId().equalsIgnoreCase("hrQC1")) {

            viewHolder.txtLevel2qc1.setText(qc1Model.getLevel4_Id());
            viewHolder.txtLevel2qc1.setTag(position);
            viewHolder.txtLevel3qc1.setText(qc1Model.getLevel5_Id());
            viewHolder.txtLevel3qc1.setTag(position);
            viewHolder.txtLevel4qc1.setText(qc1Model.getLevel6_Id());
            viewHolder.txtLevel4qc1.setTag(position);
            viewHolder.txtLevel5qc1.setText("");
            viewHolder.txtLevel5qc1.setTag(position);
            viewHolder.txtLevel6qc1.setText("");
            viewHolder.txtLevel6qc1.setTag(position);

        } else if (sharedPref.getLoginId().equalsIgnoreCase("cmd") ||
                sharedPref.getLoginId().equalsIgnoreCase("legal") ||
                sharedPref.getLoginId().equalsIgnoreCase("marketing")||
                sharedPref.getLoginId().equalsIgnoreCase("cmdQC1") ||
                sharedPref.getLoginId().equalsIgnoreCase("legalQC1") ||
                sharedPref.getLoginId().equalsIgnoreCase("marketingQC1")) {

            viewHolder.txtLevel2qc1.setText(qc1Model.getLevel4_Id());
            viewHolder.txtLevel2qc1.setTag(position);
            viewHolder.txtLevel3qc1.setText(qc1Model.getLevel5_Id());
            viewHolder.txtLevel3qc1.setTag(position);
            viewHolder.txtLevel4qc1.setText(qc1Model.getLevel6_Id());
            viewHolder.txtLevel4qc1.setTag(position);
            viewHolder.txtLevel5qc1.setText("");
            viewHolder.txtLevel5qc1.setTag(position);
            viewHolder.txtLevel6qc1.setText("");
            viewHolder.txtLevel6qc1.setTag(position);

        } else if (sharedPref.getLoginId().equalsIgnoreCase("cs")||
                sharedPref.getLoginId().equalsIgnoreCase("csQC1")) {
            viewHolder.txtLevel2qc1.setText(qc1Model.getLevel2_Id());
            viewHolder.txtLevel2qc1.setTag(position);
            viewHolder.txtLevel3qc1.setText(qc1Model.getLevel3_Id());
            viewHolder.txtLevel3qc1.setTag(position);
            viewHolder.txtLevel4qc1.setText(qc1Model.getLevel4_Id());
            viewHolder.txtLevel4qc1.setTag(position);
            viewHolder.txtLevel5qc1.setText("");
            viewHolder.txtLevel5qc1.setTag(position);
            viewHolder.txtLevel6qc1.setText("");
            viewHolder.txtLevel6qc1.setTag(position);

        } else if (sharedPref.getLoginId().equalsIgnoreCase("Personal")||
                sharedPref.getLoginId().equalsIgnoreCase("PersonalQC1")) {
            viewHolder.txtLevel2qc1.setText(qc1Model.getLevel3_Id());
            viewHolder.txtLevel2qc1.setTag(position);
            viewHolder.txtLevel3qc1.setText(qc1Model.getLevel4_Id());
            viewHolder.txtLevel3qc1.setTag(position);
            viewHolder.txtLevel4qc1.setText(qc1Model.getLevel5_Id());
            viewHolder.txtLevel4qc1.setTag(position);
            viewHolder.txtLevel5qc1.setText("");
            viewHolder.txtLevel5qc1.setTag(position);
            viewHolder.txtLevel6qc1.setText("");
            viewHolder.txtLevel6qc1.setTag(position);

        }


        return convertView;
    }

    public void openFile(Context context, String url) throws IOException {
        // Create URI
        File file = new File(url);
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.contains(".doc") || url.contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (url.contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.contains(".ppt") || url.contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.contains(".xls") || url.contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.contains(".zip") || url.contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/zip");
        } else if (url.contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (url.contains(".wav") || url.contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (url.contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
            // JPG file
            showCustomDialog(uri);
//            intent.setDataAndType(uri, "image/jpeg");
            return;
        } else if (url.contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent1 = Intent.createChooser(intent, "Open File");
        try {
            context.startActivity(intent1);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }

        /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/
    }

    private void showCustomDialog(Uri uri) {

        final android.support.v7.app.AlertDialog.Builder DialogMaster = new android.support.v7.app.AlertDialog.Builder(mContext);

        @SuppressLint("WrongConstant")
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogViewMaster = li.inflate(R.layout.image_dailog, null);
        DialogMaster.setView(dialogViewMaster);

        final android.support.v7.app.AlertDialog showMaster = DialogMaster.show();

        Button btnDismissMaster = (Button) showMaster.findViewById(R.id.buttonClose);
        ImageView image = (ImageView) showMaster.findViewById(R.id.imageviewDialog);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
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

    public void fetchLevel2dataFin() {
        try {
            level2list = new ArrayList<>();
            String Level2 = "level2";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level2, DbHelper.TABLE_FINANCE);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level2"));
                    level2list.add(branch);
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel2Array) {
                    @SuppressLint("WrongConstant")
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
                QC1.qc1txtL2.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel3dataFin(String valL2) {
        try {
            level3list = new ArrayList<>();
            String where = " where level2 like " + "'" + valL2 + "'";
            String Level3 = "level3";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level3,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level3"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel3Array) {
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
                QC1.qc1txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4datafin(String valLevel3) {
        try {
            level4list = new ArrayList<>();

            String where = " where level3 like " + "'" + valLevel3 + "'";
            String Level4 = "level4";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level4,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level4"));
                    level4list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }

            Collections.sort(level4list);
            if (level4list.size() > 0) {
                chk = "yes";
                strLevel4Array = new String[level4list.size()];
                for (int i = 0; i < level4list.size(); i++) {
                    strLevel4Array[i] = level4list.get(i);
                }
            }
            if (level4list != null && level4list.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel4Array) {
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
                QC1.qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5datafin(String valLevel4) {
        try {

            level5list = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level5"));
                    level5list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level5list);
            if (level5list.size() > 0) {
                strLevel5Array = new String[level5list.size()];
                for (int i = 0; i < level5list.size(); i++) {
                    strLevel5Array[i] = level5list.get(i);
                }
            }
            if (level5list != null && level5list.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel5Array) {
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
                QC1.qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6datafin(String valLevel5) {
        try {

            level6list = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_FINANCE, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
                    level6list.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6list);
            if (level6list.size() > 0) {
                strLevel6Array = new String[level6list.size()];
                for (int i = 0; i < level6list.size(); i++) {
                    strLevel6Array[i] = level6list.get(i);
                }
            }
            if (level6list != null && level6list.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel6Array) {
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
                QC1.qc1txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel2dataCS() {
        try {
            level2listCS = new ArrayList<>();

            String Level2 = "level2";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level2,DbHelper.TABLE_CS);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level2"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel2ArrayCS) {
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
                QC1.qc1txtL2.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel3dataCS(String valLevel2) {
        try {
            level3listCS = new ArrayList<>();

            String where = " where level2 like " + "'" + valLevel2 + "'";
            String Level3 = "level3";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level3,DbHelper.TABLE_CS, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level3"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel3ArrayCS) {
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
                QC1.qc1txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataCS(String valLevel3) {
        try {
            level4listCS = new ArrayList<>();

            String where = " where level3 like " + "'" + valLevel3 + "'";
            String Level4 = "level4";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level4,DbHelper.TABLE_CS, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level4"));
                    level4listCS.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }

            Collections.sort(level4listCS);
            if (level4listCS.size() > 0) {
                chk = "yes";
                strLevel4ArrayCS = new String[level4listCS.size()];
                for (int i = 0; i < level4listCS.size(); i++) {
                    strLevel4ArrayCS[i] = level4listCS.get(i);
                }
            }
            if (level4listCS != null && level4listCS.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel4ArrayCS) {
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
                QC1.qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataCMD() {
        try {
            level4listCMD = new ArrayList<>();

            String Level4 = "level4";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level4,DbHelper.TABLE_CMD);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String cmd = "";
                    cmd = cursor1.getString(cursor1.getColumnIndex("level4"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel4ArrayCMD) {
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
                QC1.qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataCMD(String valLevel4) {
        try {
            level5listCMD = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String level5 = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_CMD, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("level5"));
                    level5listCMD.add(level5);
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel5ArrayCMD) {
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
                QC1.qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataCMD(String valLevel5) {
        try {

            level6listCMD = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_CMD, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
                    level6listCMD.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6listCMD);
            if (level6listCMD.size() > 0) {
                strLevel6ArrayCMD = new String[level6listCMD.size()];
                for (int i = 0; i < level6listCMD.size(); i++) {
                    strLevel6ArrayCMD[i] = level6listCMD.get(i);
                }
            }
            if (level6listCMD != null && level6listCMD.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel6ArrayCMD) {
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
                QC1.qc1txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataCMD(String valLevel6) {
        try {

            level7listCMD = new ArrayList<>();
            String branch = "";
            String where = " where level6 like " + "'" + valLevel6 + "'";
            String Level7 = "level7";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level7,DbHelper.TABLE_CMD, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level7"));
                    level7listCMD.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level7listCMD);
            if (level7listCMD.size() > 0) {
                strLevel7ArrayCMD = new String[level7listCMD.size()];
                for (int i = 0; i < level7listCMD.size(); i++) {
                    strLevel7ArrayCMD[i] = level7listCMD.get(i);
                }
            }
            if (level7listCMD != null && level7listCMD.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel7ArrayCMD) {
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
                QC1.qc1txtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataLEGAL() {
        try {
            level4listLEGAL = new ArrayList<>();

            String Level4 = "level4";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level4,DbHelper.TABLE_LEGAL);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String legal = "";
                    legal = cursor1.getString(cursor1.getColumnIndex("level4"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel4ArrayLEGAL) {
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
                QC1.qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataLEGAL(String valLevel4) {
        try {

            level5listLEGAL = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String level5 = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_LEGAL, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("level5"));
                    level5listLEGAL.add(level5);
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel5ArrayLEGAL) {
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
                QC1.qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataLEGAL(String valLevel5) {
        try {
            level6listLEGAL = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_LEGAL, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
                    level6listLEGAL.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6listLEGAL);
            if (level6listLEGAL.size() > 0) {
                strLevel6ArrayLEGAL = new String[level6listLEGAL.size()];
                for (int i = 0; i < level6listLEGAL.size(); i++) {
                    strLevel6ArrayLEGAL[i] = level6listLEGAL.get(i);
                }
            }
            if (level6listLEGAL != null && level6listLEGAL.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel6ArrayLEGAL) {
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
                QC1.qc1txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataLEGAL(String valLevel6) {
        try {
            level7listLEGAL = new ArrayList<>();
            String branch = "";
            String where = " where level6 like " + "'" + valLevel6 + "'";
            String Level7 = "level7";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level7,DbHelper.TABLE_LEGAL, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level7"));
                    level7listLEGAL.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level7listLEGAL);
            if (level7listLEGAL.size() > 0) {
                strLevel7ArrayLEGAL = new String[level7listLEGAL.size()];
                for (int i = 0; i < level7listLEGAL.size(); i++) {
                    strLevel7ArrayLEGAL[i] = level7listLEGAL.get(i);
                }
            }
            if (level7listLEGAL != null && level7listLEGAL.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel7ArrayLEGAL) {
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
                QC1.qc1txtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataMAR() {
        try {
            level4listMAR = new ArrayList<>();

            String Level4 = "level4";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level4,DbHelper.TABLE_MAR);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String mar = "";
                    mar = cursor1.getString(cursor1.getColumnIndex("level4"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel4ArrayMAR) {
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
                QC1.qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataMAR(String valLevel4) {
        try {

            level5listMAR = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String level5 = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_MAR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("level5"));
                    level5listMAR.add(level5);
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel5ArrayMAR) {
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
                QC1.qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataMAR(String valLevel5) {
        try {
            level6listMAR = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_MAR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
                    level6listMAR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6listMAR);
            if (level6listMAR.size() > 0) {
                strLevel6ArrayMAR = new String[level6listMAR.size()];
                for (int i = 0; i < level6listMAR.size(); i++) {
                    strLevel6ArrayMAR[i] = level6listMAR.get(i);
                }
            }
            if (level6listMAR != null && level6listMAR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel6ArrayMAR) {
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
                QC1.qc1txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel7dataMAR(String valLevel6) {
        try {

            level7listMAR = new ArrayList<>();
            String branch = "";
            String where = " where level6 like " + "'" + valLevel6 + "'";
            String Level7 = "level7";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level7,DbHelper.TABLE_MAR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level7"));
                    level7listMAR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level7listMAR);
            if (level7listMAR.size() > 0) {
                strLevel7ArrayMAR = new String[level7listMAR.size()];
                for (int i = 0; i < level7listMAR.size(); i++) {
                    strLevel7ArrayMAR[i] = level7listMAR.get(i);
                }
            }
            if (level7listMAR != null && level7listMAR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel7ArrayMAR) {
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
                QC1.qc1txtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataHR() {
        try {
            level4listHR = new ArrayList<>();

            String Level4 = "level4";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level4,DbHelper.TABLE_HR);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String hr = "";
                    hr = cursor1.getString(cursor1.getColumnIndex("level4"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel4ArrayHR) {
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
                QC1.qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataHR(String valLevel4) {
        try {

            level5listHR = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String level5 = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_HR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    level5 = cursor1.getString(cursor1.getColumnIndex("level5"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel5ArrayHR) {
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
                QC1.qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6dataHR(String valLevel5) {
        try {
            level6listHR = new ArrayList<>();
            String branch = "";
            String where = " where level5 like " + "'" + valLevel5 + "'";
            String Level6 = "level6";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level6,DbHelper.TABLE_HR, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level6"));
                    level6listHR.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level6listHR);
            if (level6listHR.size() > 0) {
                strLevel6ArrayHR = new String[level6listHR.size()];
                for (int i = 0; i < level6listHR.size(); i++) {
                    strLevel6ArrayHR[i] = level6listHR.get(i);
                }
            }
            if (level6listHR != null && level6listHR.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel6ArrayHR) {
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
                QC1.qc1txtL6.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel3dataPER() {
        try {
            level3listPER = new ArrayList<>();

            String Level3 = "level3";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinct(Level3,DbHelper.TABLE_PER);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    String branch = "";
                    branch = cursor1.getString(cursor1.getColumnIndex("level3"));
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
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel3ArrayPer) {
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
                QC1.qc1txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4dataPER(String valLevel3) {
        try {
            level4listPER = new ArrayList<>();

            String where = " where level3 like " + "'" + valLevel3 + "'";
            String Level4 = "level4";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level4,DbHelper.TABLE_PER, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level4"));
                    level4listPER.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }

            Collections.sort(level4listPER);
            if (level4listPER.size() > 0) {
                chk = "yes";
                strLevel4ArrayPer = new String[level4listPER.size()];
                for (int i = 0; i < level4listPER.size(); i++) {
                    strLevel4ArrayPer[i] = level4listPER.get(i);
                }
            }
            if (level4listPER != null && level4listPER.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel4ArrayPer) {
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
                QC1.qc1txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5dataPER(String valLevel4) {
        try {
            level5listPER = new ArrayList<>();

            String where = " where level4 like " + "'" + valLevel4 + "'";
            String Level5 = "level5";
            String branch = "";
            Cursor cursor1 = KHIL.dbCon.fetchFromSelectDistinctWhere(Level5,DbHelper.TABLE_PER, where);
            if (cursor1 != null && cursor1.getCount() > 0) {
                cursor1.moveToFirst();
                do {
                    branch = cursor1.getString(cursor1.getColumnIndex("level5"));
                    level5listPER.add(branch);
                } while (cursor1.moveToNext());
                cursor1.close();
            }

            if(branch.equals("null")) {
                chk = "no";
                return;
            } else {
                chk = "yes";
            }
            Collections.sort(level5listPER);
            if (level5listPER.size() > 0) {
                strLevel5ArrayPer = new String[level5listPER.size()];
                for (int i = 0; i < level5listPER.size(); i++) {
                    strLevel5ArrayPer[i] = level5listPER.get(i);
                }
            }
            if (level5listPER != null && level5listPER.size() > 0) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strLevel5ArrayPer) {
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
                QC1.qc1txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder {
        private TextView txtSrNoqc1, txtLevel2qc1, txtLevel3qc1, txtLevel4qc1, txtLevel5qc1, txtLevel6qc1, txtFilenNameqc1, txtEditqc1,txtViewqc1;
    }
}

package com.example.admin.kamathotelapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.kamathotelapp.Fragments.UploadFragment;
import com.example.admin.kamathotelapp.KHIL;
import com.example.admin.kamathotelapp.Model.UploadModel;
import com.example.admin.kamathotelapp.R;
import com.example.admin.kamathotelapp.Utils.SharedPref;
import com.example.admin.kamathotelapp.dbConfig.DbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.grantland.widget.AutofitHelper;

/**
 * Created by Admin on 6/17/2017.
 */
public class UploadAdapter extends BaseAdapter {

    private Context mContext;
    private List<UploadModel> uploadModelList;
    private SharedPref sharedPref;
    private UploadModel uploadModel;
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

    private static LayoutInflater inflater = null;
    UploadAdapter adapter;

    public UploadAdapter(Context mContext, List<UploadModel> uploadModelList) {
        this.mContext = mContext;
        sharedPref = new SharedPref(this.mContext);
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

    @SuppressLint("WrongConstant")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (inflater == null)
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.upload_adapter_item, null);

            viewHolder.txtSrNo = (TextView) convertView.findViewById(R.id.txtSrNo);
//            AutofitHelper.create(viewHolder.txtSrNo);
            viewHolder.txtLevel2 = (TextView) convertView.findViewById(R.id.txtLevel2);
//            AutofitHelper.create(viewHolder.txtLevel2);
            viewHolder.txtLevel3 = (TextView) convertView.findViewById(R.id.txtLevel3);
//            AutofitHelper.create(viewHolder.txtLevel3);
            viewHolder.txtLevel4 = (TextView) convertView.findViewById(R.id.txtLevel4);
//            AutofitHelper.create(viewHolder.txtLevel4);
            viewHolder.txtLevel5 = (TextView) convertView.findViewById(R.id.txtLevel5);
//            AutofitHelper.create(viewHolder.txtLevel5);
            viewHolder.txtLevel6 = (TextView) convertView.findViewById(R.id.txtLevel6);
//            AutofitHelper.create(viewHolder.txtLevel6);
            viewHolder.txtLevel7 = (TextView) convertView.findViewById(R.id.txtLevel7);
//            AutofitHelper.create(viewHolder.txtLevel7);
            viewHolder.txtFileName = (TextView) convertView.findViewById(R.id.txtFilenName);
            viewHolder.txtDelete = (TextView) convertView.findViewById(R.id.txtDelete);
            viewHolder.txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (uploadModelList != null && uploadModelList.size() > 0) {
                        deleteFromDb(uploadModelList.get(pos).getId());
                        /*uploadModelList.remove(pos);
                        UploadAdapter.this.notifyDataSetChanged();*/
                    }
                }
            });

            viewHolder.txtEdit = (TextView)convertView.findViewById(R.id.txtEdit);
            viewHolder.txtEdit.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    UploadFragment.btnUpload.setVisibility(View.GONE);
                    UploadFragment.layout_edit.setVisibility(View.VISIBLE);

                    uploadModel = uploadModelList.get(position);
                    UploadFragment.ID = Integer.parseInt(uploadModel.getId());

                    if(!uploadModel.getFileName().equalsIgnoreCase("")) {
                        UploadFragment.txtNoFile.setText(uploadModel.getFileName());
                    }

                    if(sharedPref.getLoginId().equalsIgnoreCase("finance")) {

                        UploadFragment.cardlevel4.setVisibility(View.GONE);
                        UploadFragment.txtL4.setVisibility(View.GONE);
                        UploadFragment.txtL4.setText("");
                        UploadFragment.cardlevel5.setVisibility(View.GONE);
                        UploadFragment.txtL5.setVisibility(View.GONE);
                        UploadFragment.txtL5.setText("");
                        UploadFragment.cardlevel6.setVisibility(View.GONE);
                        UploadFragment.txtL6.setVisibility(View.GONE);
                        UploadFragment.txtL6.setText("");

                        UploadFragment.txtL2.setText(uploadModel.getLevel2());
                        fetchLevel2dataFin();
                        UploadFragment.cardlevel3.setVisibility(View.VISIBLE);
                        UploadFragment.txtL3.setVisibility(View.VISIBLE);
                        UploadFragment.txtL3.setText(uploadModel.getLevel3());

                        if(!uploadModel.getLevel4().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel4.setVisibility(View.VISIBLE);
                            UploadFragment.txtL4.setVisibility(View.VISIBLE);
                            UploadFragment.txtL4.setText(uploadModel.getLevel4());
                        }
                        if(!uploadModel.getLevel5().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel5.setVisibility(View.VISIBLE);
                            UploadFragment.txtL5.setVisibility(View.VISIBLE);
                            UploadFragment.txtL5.setText(uploadModel.getLevel5());
                        }
                        if(!uploadModel.getLevel6().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setText(uploadModel.getLevel6());
                        }

                        fetchLevel3dataFin(uploadModel.getLevel2());
                        fetchLevel4data(uploadModel.getLevel3());
                        fetchLevel5data(uploadModel.getLevel4());
                        fetchLevel6data(uploadModel.getLevel5());


                    } else if(sharedPref.getLoginId().equalsIgnoreCase("cs")) {

                        UploadFragment.cardlevel4.setVisibility(View.GONE);
                        UploadFragment.txtL4.setVisibility(View.GONE);
                        UploadFragment.txtL4.setText("");

                        UploadFragment.txtL2.setText(uploadModel.getLevel2());
                        fetchLevel2dataCS();
                        UploadFragment.cardlevel3.setVisibility(View.VISIBLE);
                        UploadFragment.txtL3.setVisibility(View.VISIBLE);
                        UploadFragment.txtL3.setText(uploadModel.getLevel3());

                        fetchLevel3dataCS(uploadModel.getLevel2());

                        if(!uploadModel.getLevel4().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel4.setVisibility(View.VISIBLE);
                            UploadFragment.txtL4.setVisibility(View.VISIBLE);
                            UploadFragment.txtL4.setText(uploadModel.getLevel4());
                        }

                        fetchLevel4dataCS(uploadModel.getLevel3());

                    } else if(sharedPref.getLoginId().equalsIgnoreCase("cmd")) {

                        UploadFragment.cardlevel6.setVisibility(View.GONE);
                        UploadFragment.txtL7.setVisibility(View.GONE);
                        UploadFragment.txtL7.setText("");

                        UploadFragment.txtL4.setText(uploadModel.getLevel4());
                        fetchLevel4dataCMD();
                        UploadFragment.cardlevel5.setVisibility(View.VISIBLE);
                        UploadFragment.txtL5.setVisibility(View.VISIBLE);
                        UploadFragment.txtL5.setText(uploadModel.getLevel5());
                        UploadFragment.txtL6.setVisibility(View.GONE);
                        UploadFragment.cardlevel7.setVisibility(View.GONE);

                        if(!uploadModel.getLevel6().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setText(uploadModel.getLevel6());
                        }
                        if(!uploadModel.getLevel7().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel7.setVisibility(View.VISIBLE);
                            UploadFragment.txtL7.setVisibility(View.VISIBLE);
                            UploadFragment.txtL7.setText(uploadModel.getLevel7());
                        }
                        fetchLevel5dataCMD(uploadModel.getLevel4());
                        fetchLevel6dataCMD(uploadModel.getLevel5());
                        fetchLevel7dataCMD(uploadModel.getLevel6());

                    } else if(sharedPref.getLoginId().equalsIgnoreCase("legal")) {

                        UploadFragment.cardlevel6.setVisibility(View.GONE);
                        UploadFragment.txtL6.setVisibility(View.GONE);
                        UploadFragment.txtL6.setText("");
                        UploadFragment.cardlevel7.setVisibility(View.GONE);
                        UploadFragment.txtL7.setVisibility(View.GONE);
                        UploadFragment.txtL7.setText("");

                        UploadFragment.txtL4.setText(uploadModel.getLevel4());
                        fetchLevel4dataLEGAL();
                        UploadFragment.cardlevel5.setVisibility(View.VISIBLE);
                        UploadFragment.txtL5.setVisibility(View.VISIBLE);
                        UploadFragment.txtL5.setText(uploadModel.getLevel5());

                        fetchLevel5dataLEGAL(uploadModel.getLevel4());

                        if(!uploadModel.getLevel6().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setText(uploadModel.getLevel6());
                        }
                        if(!uploadModel.getLevel7().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel7.setVisibility(View.VISIBLE);
                            UploadFragment.txtL7.setVisibility(View.VISIBLE);
                            UploadFragment.txtL7.setText(uploadModel.getLevel7());
                        }
                        fetchLevel6dataLEGAL(uploadModel.getLevel5());
                        fetchLevel7dataLEGAL(uploadModel.getLevel6());

                    } else if(sharedPref.getLoginId().equalsIgnoreCase("marketing")) {
                        UploadFragment.cardlevel6.setVisibility(View.GONE);
                        UploadFragment.txtL6.setVisibility(View.GONE);
                        UploadFragment.txtL6.setText("");
                        UploadFragment.cardlevel7.setVisibility(View.GONE);
                        UploadFragment.txtL7.setVisibility(View.GONE);
                        UploadFragment.txtL7.setText("");

                        UploadFragment.txtL4.setText(uploadModel.getLevel4());
                        fetchLevel4dataMAR();
                        UploadFragment.cardlevel5.setVisibility(View.VISIBLE);
                        UploadFragment.txtL5.setVisibility(View.VISIBLE);
                        UploadFragment.txtL5.setText(uploadModel.getLevel5());

                        fetchLevel5dataMAR(uploadModel.getLevel4());

                        if(!uploadModel.getLevel6().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setText(uploadModel.getLevel6());
                        }
                        if(!uploadModel.getLevel7().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel7.setVisibility(View.VISIBLE);
                            UploadFragment.txtL7.setVisibility(View.VISIBLE);
                            UploadFragment.txtL7.setText(uploadModel.getLevel7());
                        }

                        fetchLevel6dataMAR(uploadModel.getLevel5());
                        fetchLevel7dataMAR(uploadModel.getLevel6());


                    } else if(sharedPref.getLoginId().equalsIgnoreCase("hr")) {

                        UploadFragment.cardlevel6.setVisibility(View.GONE);
                        UploadFragment.txtL6.setVisibility(View.GONE);
                        UploadFragment.txtL6.setText("");

                        UploadFragment.txtL4.setText(uploadModel.getLevel4());
                        fetchLevel4dataHR();
                        UploadFragment.cardlevel5.setVisibility(View.VISIBLE);
                        UploadFragment.txtL5.setVisibility(View.VISIBLE);
                        UploadFragment.txtL5.setText(uploadModel.getLevel5());

                        fetchLevel5dataHR(uploadModel.getLevel4());

                        if(!uploadModel.getLevel6().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setVisibility(View.VISIBLE);
                            UploadFragment.txtL6.setText(uploadModel.getLevel6());
                        }
                        fetchLevel6dataHR(uploadModel.getLevel5());
                    } else if(sharedPref.getLoginId().equalsIgnoreCase("Personal")) {

                        UploadFragment.cardlevel5.setVisibility(View.GONE);
                        UploadFragment.txtL5.setVisibility(View.GONE);
                        UploadFragment.txtL5.setText("");

                        UploadFragment.txtL3.setText(uploadModel.getLevel3());
                        fetchLevel3dataPER();
                        UploadFragment.cardlevel4.setVisibility(View.VISIBLE);
                        UploadFragment.txtL4.setVisibility(View.VISIBLE);
                        UploadFragment.txtL4.setText(uploadModel.getLevel4());

                        fetchLevel4dataPER(uploadModel.getLevel3());

                        if(!uploadModel.getLevel5().equalsIgnoreCase("")) {
                            UploadFragment.cardlevel5.setVisibility(View.VISIBLE);
                            UploadFragment.txtL5.setVisibility(View.VISIBLE);
                            UploadFragment.txtL5.setText(uploadModel.getLevel5());
                        }
                        fetchLevel5dataPER(uploadModel.getLevel4());
                    }
                }
            });
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        uploadModel = uploadModelList.get(position);
        viewHolder.txtSrNo.setText(uploadModel.getId());
        viewHolder.txtLevel4.setText(uploadModel.getLevel4());
        viewHolder.txtLevel4.setTag(position);
        viewHolder.txtFileName.setText(uploadModel.getFileName());
        viewHolder.txtFileName.setTag(position);
        viewHolder.txtDelete.setTag(position);
        viewHolder.txtEdit.setTag(position);

        if (sharedPref.getLoginId().equalsIgnoreCase("finance")) {
//            viewHolder.txtLevel7.setVisibility(View.GONE);
            viewHolder.txtLevel7.setText("");
            viewHolder.txtLevel7.setTag(position);
            viewHolder.txtLevel2.setText(uploadModel.getLevel2());
            viewHolder.txtLevel2.setTag(position);
            viewHolder.txtLevel3.setText(uploadModel.getLevel3());
            viewHolder.txtLevel3.setTag(position);
            viewHolder.txtLevel5.setText(uploadModel.getLevel5());
            viewHolder.txtLevel5.setTag(position);
            viewHolder.txtLevel6.setText(uploadModel.getLevel6());
            viewHolder.txtLevel6.setTag(position);
        } else if (sharedPref.getLoginId().equalsIgnoreCase("hr")) {
//            viewHolder.txtLevel2.setVisibility(View.GONE);
//            viewHolder.txtLevel3.setVisibility(View.GONE);
//            viewHolder.txtLevel7.setVisibility(View.GONE);
            viewHolder.txtLevel2.setText("");
            viewHolder.txtLevel2.setTag(position);
            viewHolder.txtLevel3.setText("");
            viewHolder.txtLevel3.setTag(position);
            viewHolder.txtLevel7.setText("");
            viewHolder.txtLevel7.setTag(position);
            viewHolder.txtLevel5.setText(uploadModel.getLevel5());
            viewHolder.txtLevel5.setTag(position);
            viewHolder.txtLevel6.setText(uploadModel.getLevel6());
            viewHolder.txtLevel6.setTag(position);
        } else if (sharedPref.getLoginId().equalsIgnoreCase("cmd") ||
                sharedPref.getLoginId().equalsIgnoreCase("legal") ||
                sharedPref.getLoginId().equalsIgnoreCase("marketing")) {
//            viewHolder.txtLevel2.setVisibility(View.GONE);
//            viewHolder.txtLevel3.setVisibility(View.GONE);
            viewHolder.txtLevel2.setText("");
            viewHolder.txtLevel2.setTag(position);
            viewHolder.txtLevel3.setText("");
            viewHolder.txtLevel3.setTag(position);
            viewHolder.txtLevel5.setText(uploadModel.getLevel5());
            viewHolder.txtLevel5.setTag(position);
            viewHolder.txtLevel6.setText(uploadModel.getLevel6());
            viewHolder.txtLevel6.setTag(position);
            viewHolder.txtLevel7.setText(uploadModel.getLevel7());
            viewHolder.txtLevel7.setTag(position);
        } else if (sharedPref.getLoginId().equalsIgnoreCase("cs")) {
//            viewHolder.txtLevel5.setVisibility(View.GONE);
//            viewHolder.txtLevel6.setVisibility(View.GONE);
//            viewHolder.txtLevel7.setVisibility(View.GONE);
            viewHolder.txtLevel5.setText("");
            viewHolder.txtLevel5.setTag(position);
            viewHolder.txtLevel6.setText("");
            viewHolder.txtLevel6.setTag(position);
            viewHolder.txtLevel7.setText("");
            viewHolder.txtLevel7.setTag(position);
            viewHolder.txtLevel2.setText(uploadModel.getLevel2());
            viewHolder.txtLevel2.setTag(position);
            viewHolder.txtLevel3.setText(uploadModel.getLevel3());
            viewHolder.txtLevel3.setTag(position);

        } else if (sharedPref.getLoginId().equalsIgnoreCase("Personal")) {
//            viewHolder.txtLevel2.setVisibility(View.GONE);
//            viewHolder.txtLevel6.setVisibility(View.GONE);
//            viewHolder.txtLevel7.setVisibility(View.GONE);
            viewHolder.txtLevel2.setText("");
            viewHolder.txtLevel2.setTag(position);
            viewHolder.txtLevel6.setText("");
            viewHolder.txtLevel6.setTag(position);
            viewHolder.txtLevel7.setText("");
            viewHolder.txtLevel7.setTag(position);
            viewHolder.txtLevel5.setText(uploadModel.getLevel5());
            viewHolder.txtLevel5.setTag(position);
            viewHolder.txtLevel3.setText(uploadModel.getLevel3());
            viewHolder.txtLevel3.setTag(position);
        }
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

    public void getUploadData() {
        Cursor cursor = null;
        uploadModelList.clear();
        uploadModel = new UploadModel();

        String where = " where user_id = '" + sharedPref.getLoginId() + "'";
        cursor = KHIL.dbCon.fetchFromSelect(DbHelper.TABLE_UPLOAD, where);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                uploadModel = createUploadModel(cursor);
                uploadModelList.add(uploadModel);
            } while(cursor.moveToNext());
            cursor.close();
        }
        setValues(uploadModelList);

    }

    private UploadModel createUploadModel(Cursor cursor) {
        uploadModel = new UploadModel();
        try {
            uploadModel.setId(cursor.getString(cursor.getColumnIndex("id")));
            uploadModel.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
            uploadModel.setLevel2(cursor.getString(cursor.getColumnIndex("level2")));
            uploadModel.setLevel3(cursor.getString(cursor.getColumnIndex("level3")));
            uploadModel.setLevel4(cursor.getString(cursor.getColumnIndex("level4")));
            uploadModel.setLevel5(cursor.getString(cursor.getColumnIndex("level5")));
            uploadModel.setLevel6(cursor.getString(cursor.getColumnIndex("level6")));
            uploadModel.setLevel7(cursor.getString(cursor.getColumnIndex("level7")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return uploadModel;
    }

    private void setValues(List<UploadModel> uploadModelList) {
        if(uploadModelList != null && uploadModelList.size() > 0) {
            try {
                adapter = new UploadAdapter(mContext, uploadModelList);
                UploadFragment.listUpload.setAdapter(adapter);
                UploadFragment.setListViewHeightBasedOnItems(UploadFragment.listUpload);
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            UploadAdapter.this.notifyDataSetChanged();
            UploadFragment.listUpload.setVisibility(View.GONE);
            UploadFragment.no_files.setVisibility(View.VISIBLE);
        }
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
                UploadFragment.txtL2.setAdapter(adapter1);
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
                UploadFragment.txtL3.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel4data(String valLevel3) {
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
                UploadFragment.txtL4.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel5data(String valLevel4) {
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
                UploadFragment.txtL5.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLevel6data(String valLevel5) {
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
                UploadFragment.txtL6.setAdapter(adapter1);
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
                UploadFragment.txtL4.setAdapter(adapter1);
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
                UploadFragment.txtL5.setAdapter(adapter1);
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
                UploadFragment.txtL6.setAdapter(adapter1);
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
                UploadFragment.txtL4.setAdapter(adapter1);
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
                UploadFragment.txtL5.setAdapter(adapter1);
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
                UploadFragment.txtL6.setAdapter(adapter1);
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
                UploadFragment.txtL7.setAdapter(adapter1);
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
                UploadFragment.txtL2.setAdapter(adapter1);
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
                UploadFragment.txtL3.setAdapter(adapter1);
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
                UploadFragment.txtL4.setAdapter(adapter1);
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
                UploadFragment.txtL4.setAdapter(adapter1);
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
                UploadFragment.txtL5.setAdapter(adapter1);
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
                UploadFragment.txtL6.setAdapter(adapter1);
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
                UploadFragment.txtL7.setAdapter(adapter1);
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
                UploadFragment.txtL3.setAdapter(adapter1);
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
                UploadFragment.txtL4.setAdapter(adapter1);
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
                UploadFragment.txtL5.setAdapter(adapter1);
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
                UploadFragment.txtL4.setAdapter(adapter1);
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
                UploadFragment.txtL5.setAdapter(adapter1);
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
                UploadFragment.txtL6.setAdapter(adapter1);
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
                UploadFragment.txtL7.setAdapter(adapter1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFromDb(String id) {
        result = false;
        try {
            String selection = "id"+ " = ?";
            // WHERE clause arguments
            String[] selectionArgs = {id};
            result = KHIL.dbCon.delete(DbHelper.TABLE_UPLOAD, selection, selectionArgs);
            if (result){
                Toast.makeText(mContext, "Record has been deleted..!", Toast.LENGTH_SHORT).show();
                updateTable(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTable(String id) {
        result = false;
        try {
            KHIL.dbCon.updateIds(id);
            getUploadData();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder {
        private TextView txtSrNo, txtLevel2, txtLevel3, txtLevel4, txtLevel5, txtLevel6, txtLevel7, txtFileName, txtDelete, txtEdit;
        private ImageView actionDelete, actionEdit;
    }
}

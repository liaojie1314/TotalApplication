package com.example.totalapplication.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.APKVersionCodeUtil;
import com.example.totalapplication.config.BasicConfig;
import com.example.totalapplication.domain.entities.APKUpdate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class APKUpdateController {
    private Activity mActivity;
    private boolean mIsCancel;
    private ProgressBar mProgressBar;
    private AlertDialog mDownloadDialog;
    private TextView progressTextView;
    private String mSavePath;
    private String mAPKFileName;
    private int mProgress;
    private int count;
    private int length;

    public APKUpdateController(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void checkNewVersion(final OnAPKUpdateListener onAPKUpdateListener) {
        BmobQuery<APKUpdate> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereGreaterThan("version", APKVersionCodeUtil.getVerName(mActivity))
                .order("-version");
        bmobQuery.findObjects(new FindListener<APKUpdate>() {
            @Override
            public void done(List<APKUpdate> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        onAPKUpdateListener.onQueryDone(list.get(0), true);
                    } else {
                        onAPKUpdateListener.onQueryDone(null, false);
                    }
                } else {
                    onAPKUpdateListener.onQueryError(e);
                }
            }
        });
    }

    public void showNewVersionDialog(final APKUpdate apkUpdate) {
        AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setIcon(R.drawable.icon)
                .setTitle("??????????????????")
                .setMessage(apkUpdate.getUpdateMessage())
                //????????????????????????
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIsCancel = false;
                        //???????????????
                        showDownloadDialog(apkUpdate);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void showDownloadDialog(APKUpdate apkUpdate) {
        length = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("?????????");
        View view = LayoutInflater.from(mActivity).inflate(R.layout.view_progress_bar, null);
        mProgressBar = view.findViewById(R.id.view_progressbar);
        progressTextView = view.findViewById(R.id.view_dialog_text);
        builder.setView(view);
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ?????????????????????
                dialog.dismiss();
                // ???????????????????????????
                mIsCancel = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        mAPKFileName = "comic-version.apk";
        // ??????APK
        downloadAPK(apkUpdate.getAPKUrl());
    }

    private void downloadAPK(final String fileUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      ??????????????????
                        mSavePath = sdPath + "comic/Download/Update";
                        File dir = new File(mSavePath);
                        if (!dir.exists()){
                            dir.mkdirs();
                        }
                        // ????????????
                        HttpURLConnection conn = (HttpURLConnection) new URL(fileUrl).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        length = conn.getContentLength();

                        File apkFile = new File(mSavePath, mAPKFileName);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel){
                            int numRead = is.read(buffer);
                            count += numRead;
                            // ??????????????????????????????
                            mProgress  = (int) (((float) count / length) * 100);
                            // ???????????????
                            mUpdateProgressHandler.sendEmptyMessage(1);
                            // ????????????
                            if (numRead < 0){
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numRead);
                        }
                        fos.close();
                        is.close();
                    }
                }catch(Exception e){
                    Log.d(BasicConfig.TAG, "run: " + e.toString());
                }
            }
        }).start();
    }

    /**
     * ????????????
     */
    private Handler mUpdateProgressHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    // ???????????????
                    mProgressBar.setProgress(mProgress);
                    progressTextView.setText(count + " / " + length);
                    break;
                case 2:
                    // ???????????????????????????
                    mDownloadDialog.dismiss();
                    // ?????? APK ??????
                    installAPK();
            }
        };
    };

    /*
     * ??????????????????????????????
     */
    protected void installAPK() {
        File apkFile = new File(mSavePath, mAPKFileName);
        if (!apkFile.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mActivity, ".fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        mActivity.startActivity(intent);
    }

    public interface OnAPKUpdateListener {
        void onQueryDone(APKUpdate update, boolean newVersion);
        void onQueryError(Exception e);
    }
}



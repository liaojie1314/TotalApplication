package com.example.totalapplication.activities.recoder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.PermissionUtils;
import com.example.totalapplication.Utils.StartSystemPageUtils;
import com.example.totalapplication.activities.audio.AudioListActivity;
import com.example.totalapplication.databinding.ActivityRecorderBinding;
import com.example.totalapplication.services.RecorderService;

public class RecorderActivity extends AppCompatActivity {

    ActivityRecorderBinding mBinding;
    private RecorderService mRecorderService;

    RecorderService.OnRefreshUIThreadListener mRefreshUIThreadListener = new RecorderService.OnRefreshUIThreadListener() {
        @Override
        public void OnRefresh(int fb, String time) {
            mBinding.voiceLine.setVolume(fb);
            mBinding.tvDuration.setText(time);
        }
    };

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RecorderService.RecorderBinder binder = (RecorderService.RecorderBinder) service;
            mRecorderService = binder.getService();
            mRecorderService.startRecorder();
            mRecorderService.setOnRefreshUIThreadListener(mRefreshUIThreadListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRecorderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_RECORD_AUDIO, listener);
        Intent intent = new Intent(this, RecorderService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void recorderClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                StartSystemPageUtils.goToHomePage(this);
                break;
            case R.id.iv_stop:
                mRecorderService.stopRecorder();
                Intent intent = new Intent(this, AudioListActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    PermissionUtils.PermissionGrant listener = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定服务
        if (mConnection != null) {
            unbindService(mConnection);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断点击了返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            StartSystemPageUtils.goToHomePage(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
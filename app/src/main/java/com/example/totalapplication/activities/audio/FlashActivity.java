package com.example.totalapplication.activities.audio;

import static com.example.totalapplication.Utils.PermissionUtils.CODE_READ_EXTERNAL_STORAGE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.totalapplication.Utils.PermissionUtils;
import com.example.totalapplication.Utils.SDCardUtils;
import com.example.totalapplication.base.Contacts;
import com.example.totalapplication.databinding.ActivityFlashBinding;
import com.example.totalapplication.interfaces.IFileInterface;

import java.io.File;

public class FlashActivity extends AppCompatActivity {
    private ActivityFlashBinding binding;
    private int time = 3;
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                time--;
                if (time == 0) {
                    startActivity(new Intent(FlashActivity.this, AudioListActivity.class));
                    finish();
                } else {
                    binding.flashTV.setText(time + "");
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.flashTV.setText(time + "");
        PermissionUtils.requestPermission(this, CODE_READ_EXTERNAL_STORAGE, listener);
    }

    PermissionUtils.PermissionGrant listener = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            //判断是否有引用文件夹 没有则创建
            createAppDir();
            //倒计时进入录音界面
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    private void createAppDir() {
        File recoderDir = SDCardUtils.getInstance().createAppFetchDir(IFileInterface.FETCH_DIR_AUDIO);
        Contacts.PATH_FETCH_DIR_RECORD = recoderDir.getAbsolutePath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }
}
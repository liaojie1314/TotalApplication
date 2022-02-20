package com.example.totalapplication.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.totalapplication.R;

import static android.os.Build.VERSION_CODES.M;

public class LoginActivity extends AppCompatActivity {

    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    private void checkPermission() {
        //1.判断当前手机系统是否大于或等于6.0
        if (Build.VERSION.SDK_INT >= M) {
            //2.如果大于6.0,则检查是否有权限
            // 用于检查是否有某种权限
            int isPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (isPermission == PackageManager.PERMISSION_GRANTED) {
                //4.如果有则直接拨打电话
                call("");
            } else {
                //3.如果没有则去申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
            }
        } else {
            //5.如果小于6.0直接拨打电话
            call("");
        }
    }

    /**
     * 此函数为申请权限的回调函数,无论成功失败都会调用这个函数
     *
     * @param requestCode  请求码
     * @param permissions  申请的权限
     * @param grantResults 申请的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults != null && grantResults.length > 0) {
                //判断用户是否授予了这个权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call("");
                } else {
                    Toast.makeText(this, "你拒绝了拨打电话的权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void call(String phoneNumber) {
        //拨打电话方法
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + phoneNumber);
        intent.setData(uri);
        startActivity(intent);
    }
}
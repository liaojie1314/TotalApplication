package com.example.totalapplication.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请权限的工具类
 */
public class PermissionUtils {
    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    public static final int CODE_WRITE_CONTACTS = 2;
    public static final int CODE_READ_CONTACTS = 3;
    public static final int CODE_READ_PHONE_STATE = 4;
    public static final int CODE_CALL_PHONE = 5;
    public static final int CODE_READ_CALL_LOG = 6;
    public static final int CODE_WRITE_CALL_LOG = 7;
    public static final int CODE_USE_SIP = 8;
    public static final int CODE_PROCESS_OUTGOING_CALLS = 9;
    public static final int CODE_ADD_VOICEMAIL = 10;
    public static final int CODE_CAMERA = 11;
    public static final int CODE_ACCESS_FINE_LOCATION = 12;
    public static final int CODE_ACCESS_COARSE_LOCATION = 13;
    public static final int CODE_READ_EXTERNAL_STORAGE = 14;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 15;
    public static final int CODE_READ_CALENDAR = 16;
    public static final int CODE_WRITE_CALENDAR = 17;


    //申请多个权限的请求码
    public static final int CODE_MULTI_PERMISSION = 100;

    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static final String PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String PERMISSION_WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String PERMISSION_USE_SIP = Manifest.permission.USE_SIP;
    public static final String PERMISSION_PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    public static final String PERMISSION_ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERMISSION_READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String PERMISSION_WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;

    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_WRITE_CONTACTS,
            PERMISSION_READ_CONTACTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_READ_CALL_LOG,
            PERMISSION_WRITE_CALL_LOG,
            PERMISSION_USE_SIP,
            PERMISSION_PROCESS_OUTGOING_CALLS,
            PERMISSION_ADD_VOICEMAIL,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_READ_CALENDAR,
            PERMISSION_WRITE_CALENDAR
    };

    //授权成功的接口
    public interface PermissionGranted {
        void onPermissionGranted(int requestCode);
    }

    //封装请求权限函数
    public static void requestPermissions(Activity activity, int requestCode, PermissionGranted permissionGranted) {
        if (activity == null) {
            return;
        }
        //排除不存在请求码
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            return;
        }
        String requestPermission = requestPermissions[requestCode];
        //小于6.0默认授权状态
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        } catch (Exception e) {
            Toast.makeText(activity, "请打开权限：" + requestPermission, Toast.LENGTH_SHORT).show();
            return;
        }
        //判断是否授权
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            //没有授权,需申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                shouldShowRationale(activity, requestCode, requestPermission);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            }
        } else {
            //用户授权了,调用相关功能
            //Toast.makeText(activity, "opened:" + requestPermission, Toast.LENGTH_SHORT).show();
            permissionGranted.onPermissionGranted(requestCode);
        }
    }

    private static void shouldShowRationale(Activity activity, int requestCode, String requestPermission) {
        showMessageOkCancel(activity, "Rationle:" + requestPermission, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            }
        });
    }

    /**
     * 申请权限结果的方法
     *
     * @param activity          申请权限的activity
     * @param requestCode       请求码
     * @param permissions       申请的权限
     * @param grantResults      申请的结果
     * @param permissionGranted 回调接口
     */
    public static void requestPermissionResult(Activity activity, int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults, PermissionGranted permissionGranted) {
        if (activity == null) {
            return;
        }
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return;
        }
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //授权成功
            permissionGranted.onPermissionGranted(requestCode);
        } else {
            //授权失败
            String permissionError = permissions[requestCode];
            openSettingActivity(activity, "Result:" + permissionError);
        }
    }

    //获取申请打开多个权限的结果
    public static void requestMultiResult(Activity activity, @NonNull String[] permissions,
                                          @NonNull int[] grantResults, PermissionGranted permissionGranted) {
        if (activity == null) {
            return;
        }
        Map<String, Integer> perms = new HashMap<>();
        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }
        if (notGranted.size() == 0) {
            Toast.makeText(activity, "all permissions granted success", Toast.LENGTH_SHORT).show();
            permissionGranted.onPermissionGranted(CODE_MULTI_PERMISSION);
        } else {
            openSettingActivity(activity, "those permissions need to be granted");
        }
    }

    //打开设置界面
    private static void openSettingActivity(Activity activity, String msg) {
        showMessageOkCancel(activity, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        });

    }

    //弹出是否打开设置界面的对话框
    private static void showMessageOkCancel(Activity activity, String msg, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(msg)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("cancel", null)
                .create().show();
    }

    //一次申请多个权限
    public static void requestMultiPermissions(Activity activity, PermissionGranted granted) {
        //获取没有授权的权限
        ArrayList<String> permissionList = getNoGrantedPermission(activity, false);
        ArrayList<String> shouldRationlePermissionList = getNoGrantedPermission(activity, true);
        if (permissionList == null || shouldRationlePermissionList == null) {
            return;
        }
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]),
                    CODE_MULTI_PERMISSION);
        } else if (shouldRationlePermissionList.size() > 0) {
            showMessageOkCancel(activity, "please open those permissions", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity,
                            shouldRationlePermissionList.toArray(new String[shouldRationlePermissionList.size()]),
                            CODE_MULTI_PERMISSION);
                }
            });
        } else {
            granted.onPermissionGranted(CODE_MULTI_PERMISSION);
        }
    }

    //获取没有被授权的权限列表
    private static ArrayList<String> getNoGrantedPermission(Activity activity, Boolean isShouldRationle) {
        ArrayList<String> permissions = new ArrayList<>();
        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (Exception e) {
                Toast.makeText(activity, "please open those permissions", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                //没有被授权,需要申请
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    if (isShouldRationle) {
                        permissions.add(requestPermission);
                    }
                } else {
                    if (!isShouldRationle) {
                        permissions.add(requestPermission);
                    }
                }
            }
        }
        return permissions;
    }
}

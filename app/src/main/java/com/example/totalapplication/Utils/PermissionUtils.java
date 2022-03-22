package com.example.totalapplication.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    // 表示授权成功的接口
    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);
    }

    /*
     * 封装请求权限的函数
     * */
    public static void requestPermission(Activity activity, int requestCode, PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }
        //排除不存在的请求码
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
            ToastUtils.shortToast(activity, "请打开这个权限：" + requestPermission);
            return;
        }
        //判断是否被授权了
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            // 没有被授权，需要进行申请
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                shouldShowRationale(activity, requestCode, requestPermission);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            }
        } else {
            //用户授权了，可以直接调用相关功能
            ToastUtils.shortToast(activity, "opened：" + requestPermission);
            permissionGrant.onPermissionGranted(requestCode);
        }
    }

    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        showMessageOKCancel(activity, "Rationale:" + requestPermission, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            }
        });
    }

    /*
     * 申请权限结果的方法
     * */
    public static void requestPermissionsResult(Activity activity, int requestCode,
                                                @NonNull String[] permissions, @NonNull int[] grantResults, PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            ToastUtils.shortToast(activity, "illegal requestCode:" + requestCode);
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //授权成功了
            permissionGrant.onPermissionGranted(requestCode);
        } else {
            String permissionError = permissions[requestCode];
            openSettingActivity(activity, "Result:" + permissionError);
        }
    }

    /*
     * 获取申请多个权限的结果
     * */
    public static void requestMultiResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults,
                                          PermissionGrant permissionGrant) {
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
            ToastUtils.shortToast(activity, "all permission succewss");
            permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
        } else {
            openSettingActivity(activity, "those permission need granted!");
        }

    }

    /*打开设置界面*/
    private static void openSettingActivity(final Activity activity, String msg) {
        showMessageOKCancel(activity, msg, new DialogInterface.OnClickListener() {
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

    /* 弹出是否打开的对话框*/
    private static void showMessageOKCancel(Activity activity, String msg, DialogInterface.OnClickListener oklistener) {
        new AlertDialog.Builder(activity)
                .setMessage(msg)
                .setPositiveButton("OK", oklistener)
                .setNegativeButton("Cancel", null)
                .create().show();
    }

    /* 一次申请多个权限*/
    public static void requestMultiPermissions(final Activity activity, PermissionGrant grant) {
        //获取没有被授权的权限
        ArrayList<String> permissionList = getNoGrantedPermission(activity, false);
        final ArrayList<String> shouldRationalePermissionList = getNoGrantedPermission(activity, true);
        if (permissionList == null || shouldRationalePermissionList == null) {
            return;
        }

        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]),
                    CODE_MULTI_PERMISSION);
        } else if (shouldRationalePermissionList.size() > 0) {
            showMessageOKCancel(activity, "should open those permissions", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity,
                            shouldRationalePermissionList.toArray(new String[shouldRationalePermissionList.size()]), CODE_MULTI_PERMISSION);
                }
            });
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
        }
    }

    /*
     * 获取没有被授权的权限列表
     * */
    private static ArrayList<String> getNoGrantedPermission(Activity activity, boolean isShouldRationale) {
        ArrayList<String> permissions = new ArrayList<>();
        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (Exception e) {
                ToastUtils.shortToast(activity, "please open those permission");
                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                //没有被授权需要去申请
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                } else {
                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                }
            }
        }
        return permissions;
    }
}

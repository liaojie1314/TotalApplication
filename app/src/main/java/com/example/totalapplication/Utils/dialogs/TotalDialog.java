package com.example.totalapplication.Utils.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class TotalDialog {
    /**
     * 普通dialog
     */
    private void showAlterDialog(Context context, Drawable icon, String title, String message, String selectPositive, String selectNegative, String selectNeutral) {
        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(context);
        alterDiaglog.setIcon(icon);//图标
        alterDiaglog.setTitle(title);//文字
        alterDiaglog.setMessage(message);//提示消息
        //积极的选择
        alterDiaglog.setPositiveButton(selectPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "点击了" + selectPositive, Toast.LENGTH_SHORT).show();
            }
        });
        //消极的选择
        alterDiaglog.setNegativeButton(selectNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "点击了" + selectNegative, Toast.LENGTH_SHORT).show();
            }
        });
        //中立的选择
        alterDiaglog.setNeutralButton(selectNeutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "点击了" + selectNeutral, Toast.LENGTH_SHORT).show();
            }
        });
        //显示
        alterDiaglog.show();
    }

    /**
     * 列表Dialog
     */
    private void showListDialog(Context context, Drawable icon, String[] items, String title) {
        AlertDialog.Builder listDialog = new AlertDialog.Builder(context);
        listDialog.setIcon(icon);//图标
        listDialog.setTitle(title);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "点击了" + items[which], Toast.LENGTH_SHORT).show();
            }
        });
        listDialog.show();
    }

    /**
     * 单选Dialog
     */
    int choice;

    private void showSingDialog(Context context, String[] items, Drawable icon, String title) {
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(context);
        singleChoiceDialog.setIcon(icon);
        singleChoiceDialog.setTitle(title);
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = which;
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice != -1) {
                    Toast.makeText(context,
                            "你选择了" + items[choice],
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        singleChoiceDialog.show();
    }

    /**
     * 多选对话框
     */
    ArrayList<Integer> choices = new ArrayList<>();

    private void showMultiChoiceDialog(Context context, String[] items, Drawable icon, String title, boolean[] initChoices) {
        //设置默认选择都是false
        initChoices = new boolean[]{false, false, false};
        choices.clear();
        AlertDialog.Builder multChoiceDialog = new AlertDialog.Builder(context);
        multChoiceDialog.setIcon(icon);
        multChoiceDialog.setTitle(title);
        multChoiceDialog.setMultiChoiceItems(items, initChoices, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    choices.add(which);
                } else {
                    choices.remove(which);
                }
            }
        });
        multChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = choices.size();
                String str = "";
                for (int i = 0; i < size; i++) {
                    str += items[choices.get(i)] + "";
                }
                Toast.makeText(context,
                        "你选中了" + str,
                        Toast.LENGTH_SHORT).show();
            }
        });
        multChoiceDialog.show();
    }

    /**
     * 等待对话框
     */
    private void showProgressDialog(Context context, String title, String message) {
        final int MAX = 100;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);//setCancelable 为使屏幕不可点击，设置为不可取消(false)　
        progressDialog.show();
    }

    /**
     * 进度条Dialog
     */
    private void showWhiteDialog(Context context, Drawable icon, String title) {
        /* @setProgress 设置初始进度
         * @setProgressStyle 设置样式（水平进度条）
         * @setMax 设置进度最大值
         */
        final int Max = 100;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgress(0);
        progressDialog.setIcon(icon);
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(Max);
        progressDialog.show();
        /**
         * 开个线程
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                int p = 0;
                while (p < Max) {
                    try {
                        Thread.sleep(100);
                        p++;
                        progressDialog.setProgress(p);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.cancel();//达到最大就消失
            }
        }).start();
    }

    /**
     * 自定义1 控制普通的dialog的位置，大小，透明度
     * 在普通的dialog.show下面添加东西
     */
    private void DiyDialog1(Context context, Drawable icon, String title, String message, String selectPositive, String selectNegative, String selectNeutral) {
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(context);
        alterDiaglog.setIcon(icon);//图标
        alterDiaglog.setTitle(title);//文字
        alterDiaglog.setMessage(message);//提示消息
        //积极的选择
        alterDiaglog.setPositiveButton(selectPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "点击了" + selectPositive, Toast.LENGTH_SHORT).show();
            }
        });
        //消极的选择
        alterDiaglog.setNegativeButton(selectNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "点击了" + selectNegative, Toast.LENGTH_SHORT).show();
            }
        });

        alterDiaglog.setNeutralButton(selectNeutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "点击了" + selectNeutral, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alterDiaglog.create();
        //显示
        dialog.show();
        //自定义的东西
        //放在show()之后，不然有些属性是没有效果的，比如height和width
        Window dialogWindow = dialog.getWindow();
        Activity activity = (Activity) context;
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置高度和宽度
        p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.65
        p.gravity = Gravity.TOP;//设置位置
        p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
    }

    /**
     * 自定义dialog2 简单自定义布局
     */
    private void DiyDialog2(Context context, int layoutId, int styleId) {
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(context, styleId);
        alterDiaglog.setView(layoutId);//加载进去
        AlertDialog dialog = alterDiaglog.create();
        //显示
        dialog.show();
        //自定义的东西
    }
}

package com.example.totalapplication.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class PermissionDialogUtils {

    public interface onLeftClickListener {
        public void OnLeftClick();
    }

    public interface onRightClickListener {
        public void OnRightClick();
    }

    public static void showNormalDialog(Context context, String title, String msg,
                                        String leftBtn, onLeftClickListener leftClickListener,
                                        String rightBtn, onRightClickListener rightClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg);
        builder.setNegativeButton(leftBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (leftClickListener != null) {
                    leftClickListener.OnLeftClick();
                    dialog.cancel();
                }
            }
        });
        builder.setPositiveButton(rightBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rightClickListener!=null) {
                    rightClickListener.OnRightClick();
                    dialog.cancel();
                }
            }
        });
        builder.create().show();
    }
}

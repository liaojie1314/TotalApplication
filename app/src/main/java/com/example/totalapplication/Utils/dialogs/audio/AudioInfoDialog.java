package com.example.totalapplication.Utils.dialogs.audio;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.totalapplication.databinding.DialogAudioInfoBinding;
import com.example.totalapplication.domain.AudioBean;

import java.text.DecimalFormat;

public class AudioInfoDialog extends Dialog {
    private DialogAudioInfoBinding mBinding;

    public AudioInfoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DialogAudioInfoBinding.inflate(getLayoutInflater());
        CardView root = mBinding.getRoot();
        setContentView(root);
        mBinding.tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    /**
     * 设置对话框宽度和屏幕宽度一致
     */
    public void setDialogWidth(){
        //获取当前屏幕窗口对象
        Window window=getWindow();
        //获取窗口的信息参数
        WindowManager.LayoutParams wlp = window.getAttributes();
        //获取屏幕的宽度
        Display display = window.getWindowManager().getDefaultDisplay();
        wlp.width = display.getWidth()-30;
        wlp.gravity = Gravity.BOTTOM;
        //设置窗口背景透明
        window.setBackgroundDrawableResource(android.R.color.transparent);
        //设置窗口参数
        window.setAttributes(wlp);
    }

    public void setFileInfo(AudioBean bean) {
        mBinding.tvTime.setText(bean.getTime());
        mBinding.tvTitle.setText(bean.getTitle());
        mBinding.tvPath.setText(bean.getPath());
        String size = calFileSize(bean.getFileLength());
        mBinding.tvSize.setText(size);
    }

    private String calFileSize(long fileLength) {
        DecimalFormat format = new DecimalFormat("#.00");
        if (fileLength >= 1024 * 1024) {
            return format.format(fileLength * 1.0 / (1024 * 1024)) + "MB";
        }
        if (fileLength >= 1024) {
            return format.format(fileLength * 1.0 / 1024) + "KB";
        }
        if (fileLength < 1024) {
            return fileLength + "B";
        }
        return "0KB";
    }

}

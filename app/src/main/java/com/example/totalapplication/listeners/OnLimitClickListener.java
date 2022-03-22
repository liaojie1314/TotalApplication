package com.example.totalapplication.listeners;

import android.view.View;

import com.example.totalapplication.interfaces.OnLimitClickInterface;

import java.util.Calendar;

/**
 * 限制快速点击的ClickListenerHelper
 */

public class OnLimitClickListener implements View.OnClickListener {
    public static final int LIMIT_TIME = 300;
    private long lastClickTime = 0;
    private OnLimitClickInterface onLimitClickInterface = null;

    public OnLimitClickListener(OnLimitClickInterface onLimitClickInterface) {
        this.onLimitClickInterface = onLimitClickInterface;
    }

    @Override
    public void onClick(View view) {
        long curTime = Calendar.getInstance().getTimeInMillis();
        if (curTime - lastClickTime > LIMIT_TIME) {
            lastClickTime = curTime;
            if(onLimitClickInterface != null){
                onLimitClickInterface.onClick(view);
            }
        }
    }
}

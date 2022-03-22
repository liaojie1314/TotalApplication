package com.example.totalapplication.Utils.dialogs.time;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.totalapplication.R;

public class TimePickerDialog extends Dialog implements View.OnClickListener {
    private android.widget.TimePicker mTimePicker;
    private OnTimeClick mClicker;
    private TextView mCancelButton, mOkButton;
    private Context mContext;
    private int mHourOfDay;
    private int mMinute;
    private boolean mIs24HourView;

    public TimePickerDialog(@NonNull Context context, OnTimeClick callBack, int hourOfDay, int minute, boolean is24HourView) {
        super(context);
        this.mClicker = callBack;
        this.mContext = context;
        this.mHourOfDay = hourOfDay;
        this.mMinute = minute;
        this.mIs24HourView = is24HourView;
        init();
    }

    private void init() {
        //用来取消标题栏 在setcontentview之前调用 否则会报错
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getWindow();
        window.setContentView(R.layout.view_time_picker_dialog);
        View view = this.getWindow().getDecorView();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.75); // 宽度设置为屏幕的0.75
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        mTimePicker = (android.widget.TimePicker) view.findViewById(R.id.timePicker);
        mTimePicker.setCurrentHour(mHourOfDay);
        mTimePicker.setCurrentMinute(mMinute);
        mTimePicker.setIs24HourView(mIs24HourView);
        mCancelButton = (TextView) view.findViewById(R.id.btn_cancel);
        mOkButton = (TextView) view.findViewById(R.id.btn_ok);
        mCancelButton.setOnClickListener(this);
        mOkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                onOkButtonClick();
                dismiss();
                break;
        }
    }

    /**
     * 确认 按钮 点击 事件
     */
    @SuppressLint("NewApi")
    private void onOkButtonClick() {
        if (mClicker != null) {
            mClicker.onTimeSelect(mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
        }
    }

    public android.widget.TimePicker getDatePicker() {
        return mTimePicker;
    }

    public interface OnTimeClick {
        void onTimeSelect(int hour, int minute);
    }
}
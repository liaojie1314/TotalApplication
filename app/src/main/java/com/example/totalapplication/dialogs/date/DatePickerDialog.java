package com.example.totalapplication.dialogs.date;

import android.app.Dialog;
import android.content.Context;
import android.media.midi.MidiOutputPort;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.totalapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/*使用
    private void selectDate(){
        new DatePickerDialog(getContext(), new OnDateClick() {
            @Override
            public void onDataSelect(Date date) {
                int y = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
                int m = Integer.parseInt(new SimpleDateFormat("MM").format(date));
                int d = Integer.parseInt(new SimpleDateFormat("dd").format(date));
                String year= String.valueOf(y);
                String month= String.valueOf(m);
                String day= String.valueOf(d);
                //更新UI
                xxxTv.setText(year+"-"+month+"-"+day);
                xxxIv.setVisibility(View.GONE);
            }
        },year,month,day);
        dialog.getDatePicker().setCalendarViewShown(false);
        dialog.show();
    }
 */
public class DatePickerDialog extends Dialog implements View.OnClickListener {
    private DatePicker mDatePicker;
    private OnDateClick mClicker;
    private TextView mCancelButton, mOkButton;
    private Context mContext;
    private int mYear;
    private int mMonthOfYear;
    private int mDayOfMonth;

    public DatePickerDialog(@NonNull Context context, OnDateClick callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context);
        this.mClicker = callBack;
        this.mContext = context;
        this.mYear = year;
        this.mMonthOfYear = monthOfYear;
        this.mDayOfMonth = dayOfMonth;
        init();
    }

    private void init() {
        //用来取消标题栏 在setcontentview之前调用 否则会报错 android.util.AndroidRuntimeException:requestFeature() must be called before adding content
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getWindow();
        window.setContentView(R.layout.view_date_picker_dialog);
        View view = this.getWindow().getDecorView();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.75); // 宽度设置为屏幕的0.75
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mDatePicker.init(mYear, mMonthOfYear, mDayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            }
        });
        mCancelButton = (TextView) view.findViewById(R.id.cancelButton);
        mOkButton = (TextView) view.findViewById(R.id.okButton);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());
        mCancelButton.setOnClickListener(this);
        mOkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelButton:
                dismiss();
                break;
            case R.id.okButton:
                onOkButtonClick();
                dismiss();
                break;
        }
    }

    /**
     * 确认 按钮 点击 事件
     */
    private void onOkButtonClick() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mDatePicker.getYear());
        int month = mDatePicker.getMonth();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
        calendar.getTime();
        if (mClicker != null) {
            mClicker.onDataSelect(calendar.getTime());
        }
    }

    public DatePicker getDatePicker() {
        return mDatePicker;
    }

    public interface OnDateClick {
        void onDataSelect(Date date);
    }
}
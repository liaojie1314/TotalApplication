package com.example.totalapplication.Utils.dialogs.audio;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.example.totalapplication.R;
import com.example.totalapplication.databinding.DialogRenameBinding;

public class RenameDialog extends Dialog implements View.OnClickListener {
    private DialogRenameBinding mBinding;

    //创建点击确定执行的接口函数
    public interface OnEnsureListener{
        public void OnEnSure(String msg);
    }
    private OnEnsureListener mOnEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.mOnEnsureListener = onEnsureListener;
    }

    public RenameDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DialogRenameBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.dialogRenameBtnCancel.setOnClickListener(this);
        mBinding.dialogRenameBtnEnsure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_rename_btn_ensure:
                if(mOnEnsureListener!=null){
                    String msg = mBinding.dialogRenameEt.getText().toString().trim();
                    mOnEnsureListener.OnEnSure(msg);
                }
                cancel();
                break;
            case R.id.dialog_rename_btn_cancel:
                cancel();
                break;
        }
    }

    /**
     * 设置EditText显示原来的标题名称
     */
    public void setTipText(String oldText){
        mBinding.dialogRenameEt.setText(oldText);
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
        wlp.width = display.getWidth();
        wlp.gravity = Gravity.BOTTOM;
        //设置窗口背景透明
        window.setBackgroundDrawableResource(android.R.color.transparent);
        //设置窗口参数
        window.setAttributes(wlp);
        //自动弹出软键盘
        mHandler.sendEmptyMessageDelayed(1,100);
    }
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
            return false;
        }
    });
}

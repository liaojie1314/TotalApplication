package com.example.totalapplication.activities.cimoc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.totalapplication.R;
import com.example.totalapplication.Utils.UserUtil;
import com.example.totalapplication.base.BaseActivity;
import com.example.totalapplication.config.BasicConfig;
import com.example.totalapplication.controllers.LoadingViewController;
import com.example.totalapplication.domain.entities.MyUser;
import com.example.totalapplication.managers.QQLoginManager;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements QQLoginManager.QQLoginListener, View.OnClickListener {

    private QQLoginManager qqLoginManager;
    private Button loginButton;
    private TextView cancelTextView;
    private ConstraintLayout containerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        qqLoginManager = new QQLoginManager("1108104187", this);
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        qqLoginManager.onActivityResultData(requestCode, resultCode, data);
    }

    private void initData() {
        loginButton = findViewById(R.id.a_login_qq_bitton);
        cancelTextView = findViewById(R.id.a_login_cancel);
        containerView = findViewById(R.id.a_login_container);
        loginButton.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a_login_qq_bitton:
                LoadingViewController.with(this)
                        .setLoadingViewContainer(containerView)
                        .setTouchOutsideToDismiss(false)
                        .setOutsideAlpha(0.7f)
                        .setHintTextSize(15)
                        .setIndicator("BallClipRotatePulseIndicator")
                        .setHintText("正在认证")
                        .build();
                loginButton.setText("正在认证...");
                qqLoginManager.launchQQLogin();
                break;
            case R.id.a_login_cancel:
                finishActivity(BasicConfig.RESULT_CODE_CANCEL, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onQQLoginSuccess(JSONObject jsonObject) {
        LoadingViewController.dismiss();
        loginButton.setText("认证成功");
        MyUser myUser = parseUserData(jsonObject);
        UserUtil.myUser = myUser;
        UserUtil.saveUserCache(this, myUser);
        finishActivity(BasicConfig.RESULT_CODE_SUCCESS, myUser);
        showToast("登录成功");
    }

    @Override
    public void onQQLoginCancel() {
        LoadingViewController.dismiss();
        loginButton.setText("QQ登录");
        showToast("登录取消");
    }

    @Override
    public void onQQLoginError(UiError uiError) {
        LoadingViewController.dismiss();
        loginButton.setText("QQ登录");
        showToast("登录出错：" + uiError.errorMessage);
    }

    @Override
    public void onQQLoginWarning() {

    }

    private MyUser parseUserData(JSONObject jsonObject) {
        try {
            MyUser user = new MyUser();
            user.setUserOpenId(jsonObject.getString("open_id"));
            user.setUserNickname(jsonObject.getString("nickname"));
            user.setUserHeadingUrl(jsonObject.getString("figureurl_qq_2"));
            return user;
        } catch (JSONException e) {
            return null;
        }
    }

    private void finishActivity(int resultCode, Parcelable parcelable) {
        if (parcelable != null) {
            Intent intent = new Intent();
            intent.putExtra(BasicConfig.LOGIN_INTENT_KEY, parcelable);
            setResult(resultCode, intent);
        } else {
            setResult(resultCode, null);
        }
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

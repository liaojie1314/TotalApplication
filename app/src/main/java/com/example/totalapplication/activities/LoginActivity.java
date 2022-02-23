package com.example.totalapplication.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.totalapplication.R;
import com.example.totalapplication.api.AndroidScheduler;
import com.example.totalapplication.api.Api;
import com.example.totalapplication.api.ApiService;
import com.example.totalapplication.api.NetWorkModule;
import com.example.totalapplication.api.exception.ApiException;
import com.example.totalapplication.api.exception.ErrorConsumer;
import com.example.totalapplication.domain.PhoneLoginBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static android.os.Build.VERSION_CODES.M;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    int REQUEST_CODE = 1;
    private TextView mAccountTv;
    private TextView mPasswordTv;
    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private TextView mForgetPasswordTv;
    private TextView mSignWithMessageTv;
    private Button mLoginBtn;

    //手机号规则
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initListener() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerLoginEvent(v);
            }
        });
    }

    private void initView() {
        mAccountTv = findViewById(R.id.accountTv);
        mPasswordTv = findViewById(R.id.passwordTv);
        mAccountEditText = findViewById(R.id.accountEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mForgetPasswordTv = findViewById(R.id.forgetPasswordTv);
        mSignWithMessageTv = findViewById(R.id.signWithMessageTv);
        mLoginBtn = findViewById(R.id.loginBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        File filesDir=this.getFilesDir();
//        File saveFile=new File(filesDir,"info.txt");
        try {
            FileInputStream fileInputStream = this.openFileInput("info.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String info = bufferedReader.readLine();
//            fos.write((accountText+"***"+passwordText).getBytes());
//             上面是之前保存的形式，对数据进行切割
            String[] splits = info.split("\\*\\*\\*");
            String account = splits[0];
            String password = splits[1];
            //回显数据
            mAccountEditText.setText(account);
            mPasswordEditText.setText(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlerLoginEvent(View v) {
        String accountText = mAccountEditText.getText().toString();
        String passwordText = mPasswordEditText.getText().toString();
        //对账号密码进行合法性检查 这里只对其进行判空
        if (TextUtils.isEmpty(accountText)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!accountText.matches(REGEX_MOBILE_EXACT)) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //发送请求
        send(accountText, passwordText);
        //保存
        saveUserInfo(accountText, passwordText);
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    private void send(String accountText, String passwordText) {
        NetWorkModule netWorkModule = new NetWorkModule();
        OkHttpClient okHttpClient = netWorkModule.providerOkHttpClient();
        Retrofit retrofit = netWorkModule.providerRetrofit(okHttpClient, Api.NETEASE_MUSIC_BASE_URL);
        ApiService apiService = netWorkModule.providerApiService(retrofit);
        apiService.loginCellPhone(accountText, passwordText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new Consumer<PhoneLoginBean>() {
                    @Override
                    public void accept(PhoneLoginBean phoneLoginBean) throws Exception {
                        Log.i(TAG, "====================="+phoneLoginBean.getCode());
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {
                        Log.i(TAG, "error:" + e);
                    }
                });
    }

    private void saveUserInfo(String accountText, String passwordText) {
        //缓存路径 getCacheDir
        File cacheDir = this.getCacheDir();
        //获取保存路径getFilesDir
        File filesDir = this.getFilesDir();
        File saveFile = new File(filesDir, "info.txt");
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(saveFile);
            //以特定的格式存储
            fos.write((accountText + "***" + passwordText).getBytes());
            fos.close();
            //Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermission() {
        //1.判断当前手机系统是否大于或等于6.0
        if (Build.VERSION.SDK_INT >= M) {
            //2.如果大于6.0,则检查是否有权限
            // 用于检查是否有某种权限
            int isPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (isPermission == PackageManager.PERMISSION_GRANTED) {
                //4.如果有则直接拨打电话
            } else {
                //3.如果没有则去申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
            }
        } else {
            //5.如果小于6.0直接拨打电话
        }
    }

    /**
     * 此函数为申请权限的回调函数,无论成功失败都会调用这个函数
     *
     * @param requestCode  请求码
     * @param permissions  申请的权限
     * @param grantResults 申请的结果
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults != null && grantResults.length > 0) {
//                //判断用户是否授予了这个权限
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    call("");
//                } else {
//                    Toast.makeText(this, "你拒绝了拨打电话的权限", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    private void call(String phoneNumber) {
//        //拨打电话方法
//        Intent intent = new Intent(Intent.ACTION_CALL);
//        Uri uri = Uri.parse("tel:" + phoneNumber);
//        intent.setData(uri);
//        startActivity(intent);
//    }
    //sharePreference
    /*
    public class PreferenceDemoActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "PreferenceDemoActivity";
    private Switch mAllow;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perference_demo);
        mAllow = (Switch) this.findViewById(R.id.is_allow_unknown_app_sources_switch);
        mAllow.setOnCheckedChangeListener(this);
        mSharedPreferences = this.getSharedPreferences("settings_info", MODE_PRIVATE);
        boolean state = mSharedPreferences.getBoolean("state", false);
        mAllow.setChecked(state);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "current state==" + isChecked);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean("state", isChecked);
        edit.commit();//保存数据
    }
}
     */
}
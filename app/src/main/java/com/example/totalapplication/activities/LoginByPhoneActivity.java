package com.example.totalapplication.activities;

import static android.os.Build.VERSION_CODES.M;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.DateTimeUtils;
import com.example.totalapplication.Utils.RSAUtils;
import com.example.totalapplication.Utils.ToastUtils;
import com.example.totalapplication.api.AndroidScheduler;
import com.example.totalapplication.api.Api;
import com.example.totalapplication.api.ApiService;
import com.example.totalapplication.api.NetWorkModule;
import com.example.totalapplication.api.exception.ApiException;
import com.example.totalapplication.api.exception.ErrorConsumer;
import com.example.totalapplication.domain.PhoneLoginBean;
import com.example.totalapplication.listeners.OnSwipeTouchListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class LoginByPhoneActivity extends AppCompatActivity {

    @Inject
    ApiService mApiService;

    private static final String TAG = "LoginByPhoneActivity";
    int REQUEST_CODE = 1;
    int count = 0;
    private EditText mAccountText;
    private EditText mPasswordText;
    private TextView mForgetPasswordTv;
    private Button mLoginBtn;

    //???????????????
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    private Button mRegisterBtn;
    private ImageView mImageView;
    private TextView mTextView;
    private Map<Integer, String> mRsaKeyMap;
    private long mId;
    private int mCode;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_by_phone);
        initView();
        initListener();
        mImageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    mImageView.setImageResource(R.drawable.good_night_img);
                    mTextView.setText("Night");
                    count = 1;
                } else {
                    mImageView.setImageResource(R.drawable.good_morning_img);
                    mTextView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    mImageView.setImageResource(R.drawable.good_night_img);
                    mTextView.setText("Night");
                    count = 1;
                } else {
                    mImageView.setImageResource(R.drawable.good_morning_img);
                    mTextView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void initListener() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerLoginEvent(v);
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginByPhoneActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        mForgetPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetPasswordIntent = new Intent(LoginByPhoneActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetPasswordIntent);
            }
        });
    }

    private void initView() {
        mAccountText = findViewById(R.id.accountTv);
        mImageView = findViewById(R.id.imageView);
        mTextView = findViewById(R.id.textView);
        mPasswordText = findViewById(R.id.passwordTv);
        mForgetPasswordTv = findViewById(R.id.forgetPasswordTv);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegisterBtn = findViewById(R.id.registerBtn);
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
//             ??????????????????????????????????????????????????????
            String[] splits = info.split("\\*\\*\\*");
            String account = splits[0];
            String password = splits[1];
            //RSA??????
            String decryptAccount = RSAUtils.decrypt(account, mRsaKeyMap.get(1));
            String decryptPassword = RSAUtils.decrypt(password, mRsaKeyMap.get(1));
            //????????????
            mAccountText.setText(decryptAccount);
            mPasswordText.setText(decryptPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlerLoginEvent(View v) {
        String accountText = mAccountText.getText().toString();
        String passwordText = mPasswordText.getText().toString();
        //???????????????????????????????????? ???????????????????????????
        if (TextUtils.isEmpty(accountText)) {
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!accountText.matches(REGEX_MOBILE_EXACT)) {
            Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        //????????????
        sendByPhone(accountText, passwordText);
        if (mCode == 200) {
            //????????????
            //??????
            try {
                // ?????????????????????
                mRsaKeyMap = RSAUtils.genKeyPair();
                //RSA??????
                String encryptAccount = RSAUtils.encrypt(accountText, mRsaKeyMap.get(0));
                String encryptPassword = RSAUtils.encrypt(passwordText, mRsaKeyMap.get(0));
                //??????
                saveUserInfo(encryptAccount, encryptPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(LoginByPhoneActivity.this, MainActivity.class);

            intent.putExtra("id", mId);
            this.startActivity(intent);
        } else if (mCode == 502) {
            ToastUtils.shortToast(this, "????????????");
        } else {
            ToastUtils.shortToast(this, "????????????");
        }
    }


    private void sendByPhone(String accountText, String passwordText) {
        mApiService.loginCellPhone(accountText, passwordText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new Consumer<PhoneLoginBean>() {
                    @Override
                    public void accept(PhoneLoginBean phoneLoginBean) throws Exception {
                        mId = phoneLoginBean.getAccount().getId();
                        mCode = phoneLoginBean.getCode();
                        Log.i(TAG, "accept: =======>"+phoneLoginBean);
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {
                        Log.i(TAG, "error:" + e);
                    }
                });
    }

    private void sendByEmail(String emailText, String passwordText) {
        mApiService.loginEmail(emailText, passwordText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {

                    }
                });
    }

    private void saveUserInfo(String accountText, String passwordText) {
        //???????????? getCacheDir
        File cacheDir = this.getCacheDir();
        //??????????????????getFilesDir
        File filesDir = this.getFilesDir();
        File saveFile = new File(filesDir, "info.txt");
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(saveFile);
            //????????????????????????
            fos.write((accountText + "***" + passwordText).getBytes());
            fos.close();
            //Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermission() {
        //1.?????????????????????????????????????????????6.0
        if (Build.VERSION.SDK_INT >= M) {
            //2.????????????6.0,????????????????????????
            // ?????????????????????????????????
            int isPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (isPermission == PackageManager.PERMISSION_GRANTED) {
                //4.??????????????????????????????
            } else {
                //3.??????????????????????????????
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
            }
        } else {
            //5.????????????6.0??????????????????
        }
    }

    /**
     * ???????????????????????????????????????,??????????????????????????????????????????
     * requestCode  ?????????
     * permissions  ???????????????
     * grantResults ???????????????
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults != null && grantResults.length > 0) {
//                //???????????????????????????????????????
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    call("");
//                } else {
//                    Toast.makeText(this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    private void call(String phoneNumber) {
//        //??????????????????
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
        edit.commit();//????????????
    }
}
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
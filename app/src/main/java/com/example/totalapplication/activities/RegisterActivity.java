package com.example.totalapplication.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.totalapplication.R;
import com.example.totalapplication.api.AndroidScheduler;
import com.example.totalapplication.api.Api;
import com.example.totalapplication.api.ApiService;
import com.example.totalapplication.api.NetWorkModule;
import com.example.totalapplication.api.exception.ApiException;
import com.example.totalapplication.api.exception.ErrorConsumer;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity {

    @Inject
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /**
     * 获取验证码
     * @param phoneNumber 手机号
     * @param ctcode 国家区号
     */
    private void getCaptcha(String phoneNumber, String ctcode) {
        //发送验证码
        mApiService.captchaSent(phoneNumber,ctcode)
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

    /**
     * 验证验证码
     *
     * @param phoneNumber 手机号
     * @param captcha 验证码
     */
    private void verifyCaptcha(String phoneNumber,String captcha){
        mApiService.captchaVerify(phoneNumber, captcha)
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

    /**
     * 注册(修改密码)
     * @param phoneNumber 手机号
     * @param captcha 验证码
     * @param password 密码
     * @param nickname 昵称
     */

    private void registerByPhone(String phoneNumber,String captcha,String password,String nickname){
        mApiService.registerCellphone(phoneNumber, captcha, password, nickname)
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

    /**
     * 检测手机号码是否已注册
     * @param phoneNumber 手机号
     */

    private void checkPhoneIsRegisterOrNot(String phoneNumber){
        mApiService.cellphoneExistenceCheck(phoneNumber)
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

    /**
     * 检查昵称是否重复
     * @param nickName 昵称
     */

    private void checkNickname(String nickName){
        mApiService.nicknameCheck(nickName)
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
}
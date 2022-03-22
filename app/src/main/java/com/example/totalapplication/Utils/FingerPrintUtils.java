package com.example.totalapplication.Utils;

import android.app.Activity;
import android.content.Context;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class FingerPrintUtils {

    /*使用
    BiometricPrompt.PromptInfo mPromptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Please Verify")
                .setDescription("User Authentication is required to proceed")
                .setNegativeButtonText("Cancel")
                .build();
        getPrompt(context).authenticate(mPromptInfo);
     */

    public BiometricPrompt getPrompt(Context context) {
        Executor executor = ContextCompat.getMainExecutor(context);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                ToastUtils.shortToast(context, errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                ToastUtils.shortToast(context, "Authentication Succeeded");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                ToastUtils.shortToast(context, "Authentication Failed");
            }
        };
        FragmentActivity fragmentActivity = (FragmentActivity) context;
        BiometricPrompt biometricPrompt = new BiometricPrompt(fragmentActivity, executor, callback);
        return biometricPrompt;
    }
}

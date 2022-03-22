package com.example.totalapplication.base;

import android.app.Application;

import com.example.totalapplication.api.MyApplication;
import com.example.totalapplication.api.imomoeAPI.ImomoeSearch;
import com.tencent.bugly.Bugly;
import java.util.ArrayList;


public class MyImomoeApplication extends Application {

    private static MyImomoeApplication INSTANCE = null;

    public static ArrayList<ImomoeSearch> arrayList = new ArrayList<>();

    public static Application getInstance(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        arrayList.add(new ImomoeSearch());
        INSTANCE = this;
        Bugly.init(this, "244ca6b730", false);
    }
}

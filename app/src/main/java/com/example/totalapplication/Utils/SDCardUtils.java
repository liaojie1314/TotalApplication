package com.example.totalapplication.Utils;

import android.os.Environment;

import com.example.totalapplication.base.Contacts;
import com.example.totalapplication.interfaces.IFileInterface;

import java.io.File;

public class SDCardUtils {
    private static SDCardUtils sSDCardUtils;
    private SDCardUtils(){}
    public static  SDCardUtils getInstance(){
        if (sSDCardUtils==null) {
            synchronized (SDCardUtils.class){
                if (sSDCardUtils==null) {
                    sSDCardUtils = new SDCardUtils();
                }
            }
        }
        return sSDCardUtils;
    }
    /**
     * 判断手机是否有内存卡
     */
    public boolean isHaveSDCard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    /**
     * 创建项目的公共目录
     */
    public File createAppPublicDir(){
        if(isHaveSDCard()){
            File sdDir = Environment.getExternalStorageDirectory();
            File appDir = new File(sdDir, IFileInterface.APP_DIR);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            Contacts.PATH_APP_DIR = appDir.getAbsolutePath();
            return appDir;
        }
        return null;
    }
    /**
     * 创建项目分支目录
     */
    public File createAppFetchDir(String dir){
        File publicDir= createAppPublicDir();
        if (publicDir!=null){
            File fetchDir = new File(publicDir, dir);
            if (!fetchDir.exists()) {
                fetchDir.mkdirs();
            }
            return fetchDir;
        }
        return null;
    }
}

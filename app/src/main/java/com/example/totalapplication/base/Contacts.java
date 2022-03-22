package com.example.totalapplication.base;

import com.example.totalapplication.domain.AudioBean;

import java.util.List;

public class Contacts {
    public static String PATH_APP_DIR;
    public static String PATH_FETCH_DIR_RECORD;

    private static List<AudioBean>sAudioBeanList;
    public static void setAudioBeanList(List<AudioBean>audioList){
        if (audioList!=null){
            Contacts.sAudioBeanList=audioList;
        }
    }
    public static List<AudioBean>getAudioBeanList(){
        return sAudioBeanList;
    }
}


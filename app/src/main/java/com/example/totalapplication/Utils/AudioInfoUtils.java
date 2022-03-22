package com.example.totalapplication.Utils;

import android.media.MediaMetadataRetriever;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 多媒体音频文件音频数据获取工具类
 */

public class AudioInfoUtils {
    //获取音频文件相关内容的工具类
    private MediaMetadataRetriever mMediaMetadataRetriever;

    private AudioInfoUtils() {
    }

    private static AudioInfoUtils sAudioInfoUtils;

    public static AudioInfoUtils getInstance() {
        if (sAudioInfoUtils == null) {
            synchronized (AudioInfoUtils.class) {
                if (sAudioInfoUtils == null) {
                    sAudioInfoUtils = new AudioInfoUtils();
                }
            }
        }
        return sAudioInfoUtils;
    }

    public long getAudioFileDuration(String filePath) {
        long duration = 0;
        if (mMediaMetadataRetriever == null) {
            mMediaMetadataRetriever = new MediaMetadataRetriever();
        }
        mMediaMetadataRetriever.setDataSource(filePath);
        String s = mMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        duration = Long.parseLong(s);
        return duration;
    }

    public String getAudioFileFormatDuration(String format, long durLong) {
        durLong -= 8 * 3600 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(durLong));
    }
    /*
    转换为固定类型的时长 HH:mm:ss
     */

    public String getAudioFileFormatDuration(long durLong) {
        return getAudioFileFormatDuration("HH:mm:ss", durLong);
    }

    /**
     * 获取多媒体文件的艺术家
     */
    public String getAudioFileArtist(String filePath) {
        if (mMediaMetadataRetriever == null) {
            mMediaMetadataRetriever = new MediaMetadataRetriever();
        }
        mMediaMetadataRetriever.setDataSource(filePath);
        String artist = mMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        return artist;
    }

    public void releaseRetriever() {
        if (mMediaMetadataRetriever != null) {
            mMediaMetadataRetriever.release();
            mMediaMetadataRetriever = null;
        }
    }
}

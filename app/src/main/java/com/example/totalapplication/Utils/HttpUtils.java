package com.example.totalapplication.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
    public static String getJsonContent(String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            byte[] buf = new byte[1024];
            int hasRead = 0;
            while ((hasRead=is.read(buf))!=-1){
                baos.write(buf,0,hasRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toString();
    }
}

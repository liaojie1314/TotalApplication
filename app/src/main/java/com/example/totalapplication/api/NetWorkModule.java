package com.example.totalapplication.api;


import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//@InstallIn(ApplicationComponent.class)
//@Module
public class NetWorkModule {
    //获取httpClient实例
    //@Singleton//指定为单例(不需要创建多次)
    //@Provides
    public OkHttpClient providerOkHttpClient() {
        //拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("TAG", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    //@Singleton
    //@Provides
    public Retrofit providerRetrofit(OkHttpClient client,String url) {

        //传入参数为body时
        //.addConverterFactory(ScalarsConverterFactory.create())
        //需修改为.addConverterFactory(GsonConverterFactory.create())

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    //@Singleton
    //@Provides
    public ApiService providerApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}

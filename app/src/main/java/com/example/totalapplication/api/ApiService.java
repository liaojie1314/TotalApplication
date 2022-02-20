package com.example.totalapplication.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    // https://xxxxxxx/post  xxx=value
    @POST("post")
    @FormUrlEncoded
    Call<ResponseBody> post(@Field("username") String userName, @Field("password") String pwd);

    // https://xxxxxxx/get
    @GET("get")
    Call<ResponseBody> get(@Query("username") String userName, @Query("password") String pwd);

//    @GET("test")
//    Call<String>test(@Query("test")String str);
//
//    @POST("test")
//    @FormUrlEncoded
////    Call<String> uploadBody(@Body RequestBody body);
//    Call<String> uploadBody(@Body VideoBean body);
//    //上传文件
//    @Multipart
//    @POST("upload")
//    //String 可根据返回的json数据修改
//    Call<ResponseData<String>>uploadFile(@Part MultipartBody.Part part);

    //test api
    @GET("test")
    Observable<String> test(@Query("test") String str);

    @POST("test")
    @FormUrlEncoded
    Observable<ResponseData<String>> uploadBody(@Field("test") String str);

    @Multipart
    @POST("upload")
    Observable<ResponseData<String>> uploadFile(@Part MultipartBody.Part part);

    //OPEN_API_BASE_URL
    //获取图片
    @POST("getImages")
    @FormUrlEncoded
    Observable<String> getPic(@Field("page") int page, @Field("count") int count);

    //获取网易新闻
    @POST("getWangYiNews")
    @FormUrlEncoded
    Observable<String> getNews();

    //用户更新
    /*
    apikey 	string 	是 	开发者key 	d0058f79caae5300dcba65f128e52855
    name 	string 	是 	用户名 	peakchao
    passwd 	string 	是 	密码 	123456
    nikeName 	string 	否 	昵称-非必填 	飞翔的水牛
    headerImg 	string 	否 	头像-非必填 	https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_86d58ae1.png
    phone 	string 	否 	手机-非必填 	13888888888
    email 	string 	否 	邮箱-非必填 	123456@qq.com
    vipGrade 	string 	否 	vip等级-非必填 	6
    autograph 	string 	否 	签名-非必填 	没有个性，没有签名
    remarks 	string 	否 	备注-非必填 	这是一个备注，啦啦啦啦~
     */
    @POST("updateUserInfo")
    @FormUrlEncoded
    Observable<String> updateUserInfo(@Field("apikey") String apiKey, @Field("name") String name, @Field("password") String password);

    //用户反馈
    /*
    apikey 	string 	是 	开发者key 	9648872f9aa08da137ce45fe1dda8279
    text 	string 	是 	反馈内容 	我觉得应该再增加个版本更新功能。
    email 	string 	是 	反馈者联系方式 	135@qq.com
     */
    @POST("userFeedback")
    @FormUrlEncoded
    Observable<String>userFeedback(@Field("apikey")String apiKey,@Field("text")String text,@Field("email")String email);

    //用户登陆
    /*
    apikey 	string 	是 	开发者key 	d0058f79caae5300dcba65f128e52855
    name 	string 	是 	用户名 	peakchao
    passwd 	string 	是 	密码 	123456
     */
    @POST("loginUser")
    @FormUrlEncoded
    Observable<String>loginUser(@Field("apikey") String apiKey, @Field("name") String name, @Field("password") String password);

    //用户注册
    /*
    apikey 	string 	是 	开发者key 	d0058f79caae5300dcba65f128e52855
    name 	string 	是 	用户名 	peakchao
    passwd 	string 	是 	密码 	123456
    nikeName 	string 	否 	昵称-非必填 	飞翔的水牛
    headerImg 	string 	否 	头像-非必填 	https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_86d58ae1.png
    phone 	string 	否 	手机-非必填 	13888888888
    email 	string 	否 	邮箱-非必填 	123456@qq.com
    vipGrade 	string 	否 	vip等级-非必填 	6
    autograph 	string 	否 	签名-非必填 	没有个性，没有签名
    remarks 	string 	否 	备注-非必填 	这是一个备注，啦啦啦啦~
     */
    @POST("registerUser")
    @FormUrlEncoded
    Observable<String>registerUser(@Field("apikey") String apiKey, @Field("name") String name, @Field("password") String password);
    //开发者
    //删除反馈
    /*
    apikey 	string 	是 	开发者KEY 	9648872f9aa08da137ce45fe1dda8279
    id 	string 	是 	要删除的反馈ID 	1
     */
    @POST("deleteFeedback")
    @FormUrlEncoded
    Observable<String>deleteFeedback(@Field("apikey")String apiKey,@Field("id")String id);
    //查看反馈
    /*
    apikey 	string 	是 	开发者key 	9648872f9aa08da137ce45fe1dda8279
    page 	string 	否 	页码 	1
    count 	string 	否 	每页返回数量 	10
     */
    @POST("getFeedback")
    @FormUrlEncoded
    Observable<String>getFeedback(@Field("apikey")String apiKey);
    //更新开发者KEY-会清除开发者下面所有用户
    /*
    name 	string 	是 	用户名 	peakchao
    passwd 	string 	是 	密码 	123456
     */
    @POST("developerUpdateKey")
    @FormUrlEncoded
    Observable<String>developerUpdateKey(@Field("name")String name,@Field("passwd")String password);
    //开发者登陆
    /*
    name 	string 	是 	用户名 	peakchao
    passwd 	string 	是 	密码 	123456
     */
    @POST("developerLogin")
    @FormUrlEncoded
    Observable<String>developerLogin(@Field("name")String name,@Field("passwd")String password);
    //开发者注册
    /*
    name 	string 	是 	用户名 	peakchao
    passwd 	string 	是 	密码 	123456
    email 	string 	是 	邮箱，用户反馈相关会邮件通知。 	309324904@qq.com
     */
    @POST("developerRegister")
    @FormUrlEncoded
    Observable<String>developerRegister(@Field("name")String name,@Field("passwd")String password,@Field("email")String email);
}


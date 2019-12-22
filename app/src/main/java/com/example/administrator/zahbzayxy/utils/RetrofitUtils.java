package com.example.administrator.zahbzayxy.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/10/25.
 */
public class RetrofitUtils{
//宁波微信screat
//817b1ce692d74eb257432a445af8aa0b

    //烟花爆竹微信screat
    //b2502c37d791bab55eb2d8235355e63d


    //登录友盟appkey:	59e5aab76e27a41290001478
    //线下内网
  // private static String BASE_URL = "http://192.168.2.233";
    //线下外网
   // private final static String BASE_URL = "http://124.193.134.226:9100/";
    //线上
//   private  static String BASE_URL = "http://app1.zayxy.com/";
    //中安云测试
//   private  static String BASE_URL = "http://120.55.73.36:8181/" ;

    //新的线上地址
   // private  static String BASE_URL = "http://192.168.2.125:8084/" ;
   //
     //private  static String BASE_URL = "http://192.168.2.135:8089/" ;
   // private  static String BASE_URL = "http://120.26.212.51:28080/" ;

    //线上测试地址
//    private  static String BASE_URL = "http://app.test.zayxy.com";
   private  static String BASE_URL = "http://app1.zayxy.com";
    //    private  static String BASE_URL = "http://192.168.120.239";
    private static RetrofitUtils mInstance;
    private static Retrofit mRetrofit;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    private int eventNum=0;
    private RetrofitUtils(){
        Retrofit.Builder builder = new Retrofit.Builder();
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("RetrofitLog", "retrofitBack =========" + message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor)
                .addInterceptor(new Interceptor(){
                                    @Override
                                    public Response intercept(Chain chain) throws IOException {
                                        Request request = chain.request()
                                                .newBuilder()
                                                .addHeader("User-Agent", "Zayxy Android")
                                                .build();
                                        return chain.proceed(request);
                                    }
                                    }).build();
        builder.client(httpClient);
        mRetrofit = builder.baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
            .build();
}

    // 单例获取
    public static RetrofitUtils getInstance(){
        if (mInstance == null){
            mInstance = new RetrofitUtils();
        }
        return mInstance;
    }
    public <T> T createClass(Class<T> tClass){
         return (T) mRetrofit.create(tClass);
    }
}

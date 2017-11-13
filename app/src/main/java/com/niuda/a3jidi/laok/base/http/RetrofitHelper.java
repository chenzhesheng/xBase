package com.niuda.a3jidi.laok.base.http;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * (￣▽￣)"
 *
 * @version V1.0 : Retrofit 实例层
 * @author: Administrator
 * @date: 2017-11-01 17:51
 */

public class RetrofitHelper {
    private final static String Url = "https://api.douban.com/v2/";

    private Context context;
    private static  RetrofitHelper sHelper = null;
    private GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private Retrofit mRetrofit = null;

    // okhttp初始化
    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {// 添加通用的Header
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
//                    builder.addHeader("token", "123");
                    return chain.proceed(builder.build());
                }
            }).build();



    public static RetrofitHelper getInstance(Context context){
        if(sHelper == null){
            sHelper = new RetrofitHelper(context);
        }
        return sHelper;
    }

    /**
     *  构造方法
     * @param context
     */
    public RetrofitHelper(Context context) {
        this.context = context;
        init();
    }


    /**
     *  初始化
     */
    private void init() {
        resetApp();
    }


    /**
     *  Retrofit的创建
     */
    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Url)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    /**
     *  获取RetrofitServer的实体类
     * @return
     */
    public RetrofitService getService(){
        return mRetrofit.create(RetrofitService.class);
    }
}

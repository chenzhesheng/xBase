package com.niuda.a3jidi.laok.service;

import android.content.Context;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
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
    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private Retrofit mRetrofit = null;


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

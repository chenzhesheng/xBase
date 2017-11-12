package com.niuda.a3jidi.laok.service.manager;

import android.content.Context;

import com.niuda.a3jidi.laok.service.RetrofitHelper;
import com.niuda.a3jidi.laok.service.RetrofitService;
import com.niuda.a3jidi.laok.service.entity.Book;

import io.reactivex.Observable;

/**
 * (￣▽￣)"
 *
 * @version V1.0 :数据管理层
 * @author: Administrator
 * @date: 2017-11-11 14:07
 */

public class DataManager {
    private RetrofitService mRetrofitService;

    /**
     *  获取单例Retrofit
     * @param context
     */
    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getService();
    }

    public Observable<Book> getSearchBook(String name, String tag, int start, int end){
        return mRetrofitService.getSearchBook(name, tag, start, end);
    }
}

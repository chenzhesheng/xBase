package com.niuda.a3jidi.laok.booklook.manager;

import android.content.Context;

import com.niuda.a3jidi.laok.booklook.model.Book;
import com.niuda.a3jidi.lib_base.base.http.RetrofitHelper;
import com.niuda.a3jidi.lib_base.base.http.RetrofitService;

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

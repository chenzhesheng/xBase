package com.niuda.a3jidi.lib_base.base.http;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * (￣▽￣)"
 *
 * @version V1.0 : 接口层
 * @author: Administrator
 * @date: 2017-10-18 10:04
 */

public interface RetrofitService<T> {
    String Url = "https://api.douban.com/v2/";


    @GET("book/search")
    Observable<T> getSearchBook(@Query("q") String name,
                                @Query("tag") String tag,
                                @Query("start") int start,
                                @Query("count") int count);
}

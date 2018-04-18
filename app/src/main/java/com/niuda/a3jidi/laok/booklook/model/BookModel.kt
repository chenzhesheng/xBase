package com.niuda.a3jidi.laok.booklook.model

import android.widget.Toast
import com.google.gson.Gson
import com.niuda.a3jidi.laok.booklook.contract.API
import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.laok.booklook.model.pojo.Book
import com.niuda.a3jidi.lib_base.base.base.BaseView
import com.niuda.a3jidi.lib_base.base.http.RxUtils
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 作者: created by chenzhesheng on 2018/4/18 16:09
 */

@Module
class BookModel @Inject constructor(val baseView: BaseView, val api: API){

    @Inject lateinit var mGson : Gson

    @Provides
    @Singleton
    fun getBook(){
        RxUtils.get(baseView.getViewContext()).simpleRequest(api.getBook(),{
            val bookView = baseView as BookContract.BookView
            val mBook = mGson.fromJson<Book>(it.toString(), Book::class.java)
            bookView.Success(mBook)
        },{
            Toast.makeText(baseView.getViewContext(), "獲取失敗", Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(baseView.getViewContext(), "获取成功", Toast.LENGTH_SHORT).show()
        })
    }

}

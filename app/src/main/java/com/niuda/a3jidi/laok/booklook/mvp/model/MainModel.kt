package com.niuda.a3jidi.laok.booklook.mvp.model

import android.widget.Toast
import com.niuda.a3jidi.laok.booklook.mvp.contract.API
import com.niuda.a3jidi.laok.booklook.mvp.contract.BookContract
import com.niuda.a3jidi.laok.booklook.mvp.model.entity.Book
import com.niuda.a3jidi.lib_base.base.http.RxUtils

/**
* 作者: created by chenzhesheng on 2017/6/16 10:19
*/

class MainModel : BaseModel() {

    private lateinit var api: API

    fun getBook(baseView: BaseView){
        RxUtils.get(baseView.getViewContext()).simpleRequest(api.getBook(),"正在获取数据。。",{
            val bookView = baseView as BookContract.BookView
            val mBook = mGson.fromJson<Book>(it.toString(), Book::class.java)
            bookView.Success(mBook)
        },{
            Toast.makeText(baseView.getViewContext(), "获取失败", Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(baseView.getViewContext(), "获取成功", Toast.LENGTH_SHORT).show()
        })
    }

md



    fun getInfo(callBack: BookContract.CallBack) {
        callBack.data(Book())
    }

}
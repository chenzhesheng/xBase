package com.niuda.a3jidi.laok.booklook.model

import android.widget.Toast
import com.niuda.a3jidi.laok.booklook.contract.API
import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.laok.booklook.model.pojo.Book
import com.niuda.a3jidi.lib_base.base.base.BaseView
import com.niuda.a3jidi.lib_base.base.di.modules.BaseModel
import com.niuda.a3jidi.lib_base.base.http.RxUtils

/**
* 作者: created by chenzhesheng on 2017/6/16 10:19
*/

class MainModel: BaseModel() {

    fun getBook(baseView: BaseView, api: API){
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

    interface CallBack {
        fun data(info: Book)
    }

    fun getInfo(callBack: CallBack) {
        callBack.data(Book())
    }

}
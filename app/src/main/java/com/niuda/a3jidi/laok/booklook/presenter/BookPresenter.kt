package com.niuda.a3jidi.laok.booklook.presenter

import android.widget.Toast
import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.lib_base.base.base.BaseView
import com.niuda.a3jidi.lib_base.base.http.RxUtils


/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:45
 */

class BookPresenter constructor(val baseView: BaseView,val api: BookAPI): BookContract.BookPresenterIml{


    override fun getBookView() {
        RxUtils.get(baseView.getViewContext()).simpleRequest(api.getBook(),{
            val bookView = baseView as BookContract.BookView
            bookView.Success(it)
        },{
            Toast.makeText(baseView.getViewContext(), "獲取失敗", Toast.LENGTH_SHORT).show()
        })
    }

}


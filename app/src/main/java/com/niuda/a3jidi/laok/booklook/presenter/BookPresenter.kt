package com.niuda.a3jidi.laok.booklook.presenter

import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.laok.booklook.model.MainModel
import com.niuda.a3jidi.lib_base.base.base.BaseView


/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:45
 */

class BookPresenter: BookContract.BookPresenterIml{


    var mBookModel = MainModel()

    override fun getBookView(baseView: BaseView) {
        mBookModel.getBook(baseView)
    }

}


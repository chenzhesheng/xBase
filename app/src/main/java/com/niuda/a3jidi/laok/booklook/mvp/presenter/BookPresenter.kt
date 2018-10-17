package com.niuda.a3jidi.laok.booklook.mvp.presenter

import com.niuda.a3jidi.laok.booklook.mvp.contract.BookContract
import com.niuda.a3jidi.laok.booklook.mvp.model.MainModel


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

package com.niuda.a3jidi.laok.booklook.presenter

import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.laok.booklook.model.BookModel
import javax.inject.Inject


/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:45
 */


class BookPresenter @Inject constructor(): BookContract.BookPresenterIml{

    @Inject lateinit var mBookModel: BookModel

    override fun getBookView() {
        mBookModel.getBook()
    }

}


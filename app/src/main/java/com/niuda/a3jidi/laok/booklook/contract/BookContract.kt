package com.niuda.a3jidi.laok.booklook.contract

import com.niuda.a3jidi.laok.booklook.model.pojo.Book
import com.niuda.a3jidi.lib_base.base.base.BasePresenter
import com.niuda.a3jidi.lib_base.base.base.BaseView

/**
 * 作者: created by chenzhesheng on 2018/4/18 09:59
 */
interface BookContract {
    interface BookView: BaseView{
        fun Success(it: Book)
    }

    interface BookPresenterIml: BasePresenter{
        fun getBookView()
    }
}
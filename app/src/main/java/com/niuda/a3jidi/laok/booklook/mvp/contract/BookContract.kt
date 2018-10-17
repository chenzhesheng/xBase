package com.niuda.a3jidi.laok.booklook.mvp.contract

import com.niuda.a3jidi.laok.booklook.mvp.model.entity.Book

/**
 * 作者: created by chenzhesheng on 2018/4/18 09:59
 */
interface BookContract {
    interface BookView: BaseView{
        fun Success(it: Book)
    }

    interface BookPresenterIml: BasePresenter{
        fun getBookView(baseView: BaseView)
    }

    interface CallBack {
        fun data(info: Book)
    }
}
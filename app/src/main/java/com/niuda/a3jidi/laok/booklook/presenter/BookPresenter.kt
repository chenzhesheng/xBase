package com.niuda.a3jidi.laok.booklook.presenter

import android.content.Context
import com.niuda.a3jidi.laok.booklook.manager.DataManager
import com.niuda.a3jidi.laok.booklook.model.Book
import com.niuda.a3jidi.laok.booklook.model.IBookView
import com.niuda.a3jidi.lib_base.base.base.IBasePresenter
import com.niuda.a3jidi.lib_base.base.base.IBaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:45
 */

class BookPresenter(private val mContext: Context) : IBasePresenter {

    private var mManager: DataManager<*>? = null
    private var mBookView: IBookView? = null
    private var mBook: Book? = null

    override fun onCreate() {
        mManager = DataManager<Book>(context = mContext)
    }

    override fun onStop() {
        //        if (mCompositeSubscription.hasSubscriptions()){
        //            mCompositeSubscription.unsubscribe();
        //        }
    }

    override fun attachView(baseView: IBaseView) {
        mBookView = baseView as IBookView
    }

    fun getSearchBook(name: String, tag: String, start: Int, count: Int) {
        mManager!!.getSearchBook(name, tag, start, count)
                .subscribeOn(Schedulers.io())       //IO线程进行请求
//                .observeOn(AndroidSchedulers.mainThread())  //主线程回调
                .subscribe(object : Observer<Book> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(value: Book) {      // 请求成功1
                        mBook = value
                    }

                    override fun onError(e: Throwable) {      //请求失败-1
                        e.printStackTrace()
                        mBookView!!.onError("请求失败！")
                    }

                    override fun onComplete() {      // 返回成功2
                        if (mBook != null) {
                            mBookView!!.onSuccess(mBook!!)
                        }
                    }
                })
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }
}

private fun Any.subscribe(observer: Observer<Book>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

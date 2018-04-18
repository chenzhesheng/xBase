package com.niuda.a3jidi.lib_base.base.http

import android.content.Context
import android.text.TextUtils
import android.util.Log

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.lang.ref.WeakReference


/**
 * Created by mac on 2017/8/30.
 * 网络加载帮助工具类
 */
class RxUtils {
    object INSTANCE {
        val instance = RxUtils()
    }

    private var mContext: WeakReference<Context>? = null

    companion object {
        fun get(mContext: Context): RxUtils {
            val apiUtils = INSTANCE.instance
            apiUtils.mContext = WeakReference(mContext)
            return apiUtils
        }

        fun get(): RxUtils {
            val apiUtils = INSTANCE.instance
            apiUtils.mContext = null
            return apiUtils
        }
    }

    /**
     * 文件上传预处理,无进度监听
     */
    fun <T> upload(observable: Observable<T>,
                   resultFun: (t: T?) -> Unit,
                   errorFun: ((e: Throwable?) -> Unit)?) {
        if (mContext == null) {
            throw Exception("需要先设置mContext mContext =" + mContext)
        }
        val loadstate = SimpleLoadState(mContext, "正在上传，请稍候...")
        execute(observable, loadstate, resultFun, errorFun, null)
    }

    /**
     * 文件上传预处理，有进度监听
     */
    fun <T> upload(observable: Observable<T>,
                   originUploadSimpleProgressLoadState: SimpleProgressLoadState?,
                   resultFun: (t: T?) -> Unit,
                   errorFun: ((e: Throwable?) -> Unit)?,
                   completeFun: (() -> Unit)?) {
        var tempState = originUploadSimpleProgressLoadState
        if (tempState == null && mContext != null)
            tempState = SimpleProgressLoadState(mContext, false)
        val downloadState = tempState
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    downloadState?.onLoading()
                }.subscribe(object : Subscriber<T>() {
            override fun onNext(t: T?) {
                resultFun(t)
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                downloadState?.onLoadFailure(e, errorFun)
            }

            override fun onCompleted() {
                downloadState?.onLoadComplete()
                if (!this.isUnsubscribed) {
                    this.unsubscribe()
                }
                if (completeFun != null) {
                    completeFun()
                }
            }

        })
    }

    /**
     * 多文件上传
     */
    fun <T, R> upload(tList: List<T>,
                      originUploadSimpleProgressLoadState: SimpleProgressLoadState?,
                      handleFun: (t: T?) -> Observable<R>,
                      errorFun: ((e: Throwable?) -> Unit)?,
                      completeFun: (() -> Unit)?) {
        upload(tList, originUploadSimpleProgressLoadState, handleFun, null, errorFun, completeFun)
    }

    /**
     * 多文件上传
     */
    fun <T, R> upload(tList: List<T>,
                      originUploadSimpleProgressLoadState: SimpleProgressLoadState?,
                      handleFun: (t: T?) -> Observable<R>,
                      resultFun: ((r: R?) -> Unit)?,
                      errorFun: ((e: Throwable?) -> Unit)?,
                      completeFun: (() -> Unit)?) {
        var tempState = originUploadSimpleProgressLoadState
        if (tempState == null && mContext != null)
            tempState = SimpleProgressLoadState(mContext, false)
        val downloadState = tempState
        Observable.from(tList)
                .flatMap { handleFun(it) }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    downloadState?.onLoading()
                }.subscribe(object : Subscriber<R>() {
            override fun onNext(r: R?) {
                if (resultFun != null)
                    resultFun(r)
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                downloadState?.onLoadFailure(e, errorFun)
            }

            override fun onCompleted() {
                downloadState?.onLoadComplete()
                if (!this.isUnsubscribed) {
                    this.unsubscribe()
                }
                if (completeFun != null) {
                    completeFun()
                }
            }
        })
    }

    /**
     * 文件下载预处理,无进度监听
     */
    fun <T> download(observable: Observable<T>,
                     resultFun: (t: T?) -> Unit,
                     errorFun: ((e: Throwable?) -> Unit)?) {
        if (mContext == null) {
            throw Exception("需要先设置mContext mContext =" + mContext)
        }
        val loadstate = SimpleLoadState(mContext, "正在下载，请稍候...")
        execute(observable, loadstate, resultFun, errorFun, null)
    }

    /**
     * 文件下载预处理，有进度监听
     */
    fun <T> download(observable: Observable<T>,
                     originDownloadSimpleProgressLoadState: SimpleProgressLoadState?,
                     resultFun: (t: T?) -> Unit,
                     errorFun: ((e: Throwable?) -> Unit)?,
                     completeFun: (() -> Unit)?) {
        var tempState = originDownloadSimpleProgressLoadState
        if (tempState == null && mContext != null)
            tempState = SimpleProgressLoadState(mContext, false)
        val downloadState = tempState
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    downloadState?.onLoading()
                }.subscribe(object : Subscriber<T>() {
            override fun onNext(t: T?) {
                resultFun(t)
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                downloadState?.onLoadFailure(e, errorFun)
            }

            override fun onCompleted() {
                downloadState?.onLoadComplete()
                if (!this.isUnsubscribed) {
                    this.unsubscribe()
                }
                if (completeFun != null) {
                    completeFun()
                }
            }

        })
    }

    /**
     * 一般性Observable预处理
     */
    fun <T> execute(observable: Observable<T>,
                    nextFun: (t: T?) -> Unit,
                    errorFun: ((e: Throwable?) -> Unit)?) {
        execute(observable, nextFun, errorFun, null)
    }

    /**
     * 一般性Observable预处理
     */
    fun <T> execute(observable: Observable<T>,
                    nextFun: (t: T?) -> Unit,
                    errorFun: ((e: Throwable?) -> Unit)?,
                    completeFun: (() -> Unit)?) {
        execute(observable, "处理中，请稍候", nextFun, errorFun, completeFun)
    }

    /**
     * 一般性Observable预处理
     */
    fun <T> execute(observable: Observable<T>,
                    showMessage: String?,
                    nextFun: (t: T?) -> Unit,
                    errorFun: ((e: Throwable?) -> Unit)?,
                    completeFun: (() -> Unit)?) {
        if (mContext == null) {
            throw Exception("需要先设置mContext mContext =" + mContext)
        }
        val loadstate = SimpleLoadState(mContext, showMessage ?: "处理中，请稍候")
        execute(observable, loadstate, nextFun, errorFun, completeFun)
    }

    /**
     * 一般性Observable预处理
     */
    fun <T> execute(observable: Observable<T>,
                    loadState: LoadState?,
                    nextFun: (t: T?) -> Unit,
                    errorFun: ((e: Throwable?) -> Unit)?,
                    completeFun: (() -> Unit)?) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (loadState != null) {
                        loadState.onLoading()
                    }
                }.subscribe(object : Subscriber<T>() {
            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                loadState?.onLoadComplete()
                loadState?.onLoadFailure(e, errorFun)
            }

            override fun onNext(t: T?) {
                nextFun(t)
            }

            override fun onCompleted() {
                loadState?.onLoadComplete()
                if (!this.isUnsubscribed) {
                    this.unsubscribe()
                }
                if (completeFun != null) {
                    completeFun()
                }
            }

        })
    }

    fun <T> realmQuery(observable: Observable<T>,
                       nextFun: (t: T?) -> Unit,
                       errorFun: ((e: Throwable?) -> Unit)?) {
        if (mContext == null) {
            throw Exception("需要先设置mContext mContext =" + mContext)
        }
        val loadstate = SimpleLoadState(mContext, "处理中，请稍候")
        realmQuery(observable, loadstate, nextFun, errorFun, null)
    }

    fun <T> realmQuery(observable: Observable<T>,
                       nextFun: (t: T?) -> Unit,
                       errorFun: ((e: Throwable?) -> Unit)?,
                       completeFun: (() -> Unit)?) {
        if (mContext == null) {
            throw Exception("需要先设置mContext mContext =" + mContext)
        }
        val loadstate = SimpleLoadState(mContext, "处理中，请稍候")
        realmQuery(observable, loadstate, nextFun, errorFun, completeFun)
    }

    fun <T> realmQuery(observable: Observable<T>,
                       showMessage: String?,
                       nextFun: (t: T?) -> Unit,
                       errorFun: ((e: Throwable?) -> Unit)?) {
        if (mContext == null) {
            throw Exception("需要先设置mContext mContext =" + mContext)
        }
        val loadstate = SimpleLoadState(mContext, showMessage ?: "处理中，请稍候")
        realmQuery(observable, loadstate, nextFun, errorFun, null)
    }

    fun <T> realmQuery(observable: Observable<T>,
                       showMessage: String?,
                       nextFun: (t: T?) -> Unit,
                       errorFun: ((e: Throwable?) -> Unit)?,
                       completeFun: (() -> Unit)?) {
        if (mContext == null) {
            throw Exception("需要先设置mContext mContext =" + mContext)
        }
        val loadstate = SimpleLoadState(mContext, showMessage ?: "处理中，请稍候")
        realmQuery(observable, loadstate, nextFun, errorFun, completeFun)
    }

    /**
     * mRealm query操作
     * 查询的结果只能在同一线程使用
     */
    fun <T> realmQuery(observable: Observable<T>,
                       loadState: LoadState?,
                       nextFun: (t: T?) -> Unit,
                       errorFun: ((e: Throwable?) -> Unit)?,
                       completeFun: (() -> Unit)?) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (loadState != null) {
                        loadState.onLoading()
                    }
                }.subscribe(object : Subscriber<T>() {
            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                loadState?.onLoadComplete()
                loadState?.onLoadFailure(e, errorFun)
            }

            override fun onNext(t: T?) {
                nextFun(t)
            }

            override fun onCompleted() {
                loadState?.onLoadComplete()
                if (!this.isUnsubscribed) {
                    this.unsubscribe()
                }
                if (completeFun != null) {
                    completeFun()
                }
            }

        })
    }

    /**
     * 通用网络加载预处理
     */
    fun request(observable: Observable<HttpJsonResult>,
                loadState: LoadState?,
                resultFun: (t: String?) -> Unit,
                errorFun: ((e: Throwable?) -> Unit)?,
                completeFun: (() -> Unit)?,
                isLogin: Boolean) {
                observable.unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (loadState != null) {
                        loadState.onLoading()
                    }
                }.subscribe(object : Subscriber<HttpJsonResult>() {
            override fun onCompleted() {
                loadState?.onLoadComplete()
                if (!this.isUnsubscribed) {
                    this.unsubscribe()
                }
                if (completeFun != null) {
                    completeFun()
                }
            }

            override fun onNext(t: HttpJsonResult?) {
                commonRequest(t, resultFun, errorFun)
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                loadState?.onLoadComplete()
                loadState?.onLoadFailure(e, errorFun)
            }
        })
    }

    /**
     * 通用网络加载预处理
     */
    fun requestTwo(observable: Observable<HttpJsonResult>,
                loadState: LoadState?,
                resultFun: (t: String?) -> Unit,
                errorFun: ((e: Throwable?) -> Unit)?,
                completeFun: (() -> Unit)?,
                isLogin: Boolean) {
        observable.unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnSubscribe {
                    if (loadState != null) {
                        loadState.onLoading()
                    }
                }.subscribe(object : Subscriber<HttpJsonResult>() {
            override fun onCompleted() {
                Log.v( "rx--subscribe " , Thread.currentThread().getName() ) ;
                loadState?.onLoadComplete()
                if (!this.isUnsubscribed) {
                    this.unsubscribe()
                }
                if (completeFun != null) {
                    completeFun()
                }
            }

            override fun onNext(t: HttpJsonResult?) {
                commonRequest(t, resultFun, errorFun)
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                loadState?.onLoadComplete()
                loadState?.onLoadFailure(e, errorFun)
            }

        })
    }
    /**
     * 一般请求
     */
    fun request(observable: Observable<HttpJsonResult>,
                loadState: LoadState?,
                function: (t: String?) -> Unit,
                errorFun: (e: Throwable?) -> Unit,
                completeFun: (() -> Unit)?) {
        request(observable, loadState, function, errorFun, completeFun, false)
    }

    /**
     * 一般请求
     */
    fun request(observable: Observable<HttpJsonResult>, loadState: LoadState?,
                function: (t: String?) -> Unit, errorFun: (e: Throwable?) -> Unit) {
        request(observable, loadState, function, errorFun, null, false)
    }

    /**
     * 一般请求
     */
    fun requestTwo(observable: Observable<HttpJsonResult>, loadState: LoadState?,
                function: (t: String?) -> Unit, errorFun: (e: Throwable?) -> Unit) {
        requestTwo(observable, loadState, function, errorFun, null, false)
    }

    /**
     * 一般请求
     */
    fun request(observable: Observable<HttpJsonResult>, loadState: LoadState?,
                function: (t: String?) -> Unit) {
        request(observable, loadState, function, null, null, false)
    }

    /**
     * 一般请求
     */
    fun simpleRequest(observable: Observable<HttpJsonResult>,
                      function: (t: String?) -> Unit) {
        simpleRequest(observable, null, function = function)
    }

    /**
     * 一般请求
     */
    fun simpleRequest(observable: Observable<HttpJsonResult>,
                      message: String?,
                      function: (t: String?) -> Unit) {
        simpleRequest(observable, message, function = function, errorFun = null)
    }

    /**
     * 一般请求
     */
    fun simpleRequest(observable: Observable<HttpJsonResult>,
                      function: (t: String?) -> Unit,
                      errorFun: ((e: Throwable?) -> Unit)?) {
        simpleRequest(observable, null, function, errorFun)
    }

    /**
     * 一般请求
     */
    fun simpleRequest(observable: Observable<HttpJsonResult>,
                      message: String?,
                      function: (t: String?) -> Unit,
                      errorFun: ((e: Throwable?) -> Unit)?) {
        simpleRequest(observable, message, function, errorFun, null)
    }

    /**
     * 一般请求
     */
    fun simpleRequest(observable: Observable<HttpJsonResult>,
                      function: (t: String?) -> Unit,
                      errorFun: ((e: Throwable?) -> Unit)?,
                      completeFun: (() -> Unit)?
    ) {
        simpleRequest(observable, null, function, errorFun, completeFun)
    }

    /**
     * 一般请求
     */
    fun simpleRequest(observable: Observable<HttpJsonResult>,
                      message: String?,
                      function: (t: String?) -> Unit,
                      errorFun: ((e: Throwable?) -> Unit)?,
                      completeFun: (() -> Unit)?
    ) {
        if (mContext == null) {
            throw Exception("需要先设置mContext mContext =" + mContext)
        }
        val loadstate = SimpleLoadState(mContext, message ?: "加载中，请稍候...")
        simpleRequestWithState(observable, loadstate, function, errorFun, completeFun)
    }

    /**
     * 一般请求
     */
    fun simpleRequestWithState(observable: Observable<HttpJsonResult>,
                               loadState: LoadState?,
                               function: (t: String?) -> Unit,
                               errorFun: ((e: Throwable?) -> Unit)?,
                               completeFun: (() -> Unit)?) {
        var realLoadState: LoadState? = loadState
        if (realLoadState == null) {
            realLoadState = NoStateProgressLoadState()
        }
        request(observable, realLoadState, function, errorFun, completeFun, false)
    }

        /**
     * 一般网络请求预处理
     */
    private fun commonRequest(t: HttpJsonResult?,
                              resultFun: (t: String?) -> Unit,
                              errorFun: ((e: Throwable?) -> Unit)?) {
        if (t == null) {
            if (errorFun != null)
                errorFun(HttpException(100))
            return
        }
        if (200 == t.state) {
            if (!TextUtils.isEmpty(t.content)) {
                resultFun(t.content)
            } else {
                resultFun(t.content)
            }
        } else {
            val mHttpException = HttpException(t.message!!)
            if (errorFun != null)
                errorFun(mHttpException)
        }
    }
}
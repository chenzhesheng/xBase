/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chenzhesheng.xBase.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chenzhesheng.xBase.base.delegete.IFragment
import com.chenzhesheng.xBase.integration.cache.Cache
import com.chenzhesheng.xBase.integration.cache.CacheType
import com.chenzhesheng.xBase.integration.lifecycle.FragmentLifecycleable
import com.chenzhesheng.xBase.mvp.IPresenter
import com.chenzhesheng.xBase.utils.XBaseUtils
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

/**
 * ================================================
 * 因为 Java 只能单继承, 所以如果要用到需要继承特定 @[Fragment] 的三方库, 那你就需要自己自定义 @[Fragment]
 * 继承于这个特定的 @[Fragment], 然后再按照 [BaseFragment] 的格式, 将代码复制过去, 记住一定要实现[IFragment]
 *
 * @see [请配合官方 Wiki 文档学习本框架](https://github.com/JessYanCoding/MVPArms/wiki)
 *
 * @see [更新日志, 升级必看!](https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog)
 *
 * @see [常见 Issues, 踩坑必看!](https://github.com/JessYanCoding/MVPArms/wiki/Issues)
 *
 * @see [MVPArms 官方组件化方案 ArmsComponent, 进阶指南!](https://github.com/JessYanCoding/ArmsComponent/wiki)
 * Created by JessYan on 22/03/2016
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
abstract class BaseFragment<P : IPresenter> : Fragment(), IFragment, FragmentLifecycleable {
    protected val TAG = this.javaClass.simpleName
    private val mLifecycleSubject = BehaviorSubject.create<FragmentEvent>()
    private var mCache: Cache<*, *>? = null
    protected var mContext: Context? = null
    @Inject
    @JvmField
    var mPresenter: P? = null//如果当前页面逻辑简单, Presenter 可以为 null

    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = XBaseUtils.obtainAppComponentFromContext(activity!!).cacheFactory().build(CacheType.FRAGMENT_CACHE)
        }
        return mCache as Cache<String, Any>
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent> {
        return mLifecycleSubject
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) mPresenter!!.onDestroy()//释放资源
        this.mPresenter = null
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

}

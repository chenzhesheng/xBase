package com.niuda.a3jidi.lib_base.base.base

import android.support.v4.app.Fragment

/**
 * 若把初始化内容放到initData实现,就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 * -
 * -注1: 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 * ------可以调用mViewPager.setOffscreenPageLimit(size),若设置了该属性 则viewpager会缓存指定数量的Fragment
 * -注2: 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * -注3: 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 */

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-11 14:07
 * ================================================
 */
abstract class BaseFragment : Fragment() {
//
//    var inflater: LayoutInflater? = null
//    var isFirstLoad: Boolean? = null
//    var isPrepared: Boolean? = null
//    var fragmentTitle: String? = null
//    var view: View = null
//
//    var title: String
//        get() = if (TextUtils.isEmpty(fragmentTitle)) "" else fragmentTitle
//        set(title) {
//            fragmentTitle = title
//        }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        this.inflater = inflater
//        isFirstLoad = true
//        view = inflater.inflate(initLayout(), container, false)
//        isPrepared = true
//        lazyLoad(savedInstanceState)
//        return view
//    }
//
//    /** 如果是与ViewPager一起使用，调用的是setUserVisibleHint  */
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (userVisibleHint) {
//            isVisible = true
//            onVisible()
//        } else {
//            isVisible = false
//            onInvisible()
//        }
//    }
//
//    /**
//     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
//     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
//     */
//    override fun onHiddenChanged(hidden: Boolean) {
//        super.onHiddenChanged(hidden)
//        if (!hidden) {
//            isVisible = true
//            onVisible()
//        } else {
//            isVisible = false
//            onInvisible()
//        }
//    }
//
//
//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        this.context = context
//    }
//
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        viewStateRetored()
//    }
//
//
//    protected fun onVisible() {
//        lazyLoad(savedInstanceState)
//    }
//
//    protected fun onInvisible() {}
//
//    protected fun lazyLoad(savedInstanceState: Bundle?) {
//        if (!isPrepared || !isVisible || !isFirstLoad) {
//            return
//        }
//        isFirstLoad = false
//        savedInstanceState(savedInstanceState)
//    }
//
//    protected abstract fun initLayout(): Int
//
//    protected abstract fun savedInstanceState(savedInstanceState: Bundle?)
//
//    protected abstract fun viewStateRetored()

}

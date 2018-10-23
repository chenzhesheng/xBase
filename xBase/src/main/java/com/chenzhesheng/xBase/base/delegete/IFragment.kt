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
package com.chenzhesheng.xBase.base.delegete

import android.app.Activity
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.chenzhesheng.xBase.di.component.AppComponent
import com.chenzhesheng.xBase.integration.cache.Cache

/**
 * ================================================
 * 框架要求框架中的每个 [Fragment] 都需要实现此类,以满足规范
 *
 * Created by JessYan on 29/04/2017 14:31
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface IFragment {

    /**
     * 提供在 [Fragment] 生命周期内的缓存容器, 可向此 [Fragment] 存取一些必要的数据
     * 此缓存容器和 [Fragment] 的生命周期绑定, 如果 [Fragment] 在屏幕旋转或者配置更改的情况下
     * 重新创建, 那此缓存容器中的数据也会被清空, 如果你想避免此种情况请使用 [LifecycleModel](https://github.com/JessYanCoding/LifecycleModel)
     *
     * @return like [LruCache]
     */
    fun provideCache(): Cache<String, Any>

    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent
     */
    fun setupFragmentComponent(appComponent: AppComponent)

    /**
     * 初始化 View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    fun initData(savedInstanceState: Bundle?)

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 [Message], 通过 what 字段来区分不同的方法, 在 [.setData]
     * 方法中就可以 `switch` 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     *
     *
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 [.setData] 方法时 [Fragment.onCreate] 还没执行
     * 但在 [.setData] 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 [Fragment.onCreate] 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 [.setData], 在 [.initData] 中初始化就可以了
     *
     *
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     * if (data != null && data instanceof Message) {
     * switch (((Message) data).what) {
     * case 0:
     * loadData(((Message) data).arg1);
     * break;
     * case 1:
     * refreshUI();
     * break;
     * default:
     * //do something
     * break;
     * }
     * }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
    </pre> *
     *
     * [.setData] 框架是不会调用的, 是拿给开发者自己去调用的, 让 [Activity] 或者其他类可以和 [Fragment] 通信,
     * 并且因为 [.setData] 是 [IFragment] 的方法, 所以你可以通过多态, 持有父类,
     * 不持有具体子类的方式就可以和子类 [Fragment] 通信, 这样如果需要替换子类, 就不会影响到其他地方,
     * 并且 [.setData] 可以通过传入 [Message] 作为参数, 使外部统一调用 [.setData],
     * 方法内部再通过 `switch(message.what)` 的方式, 从而在外部调用方式不变的情况下, 却可以扩展更多的方法,
     * 让方法扩展更多的参数, 这样不管 [Fragment] 子类怎么变, 它内部的方法以及方法的参数怎么变, 却不会影响到外部调用的任何一行代码
     *
     * @param data 当不需要参数时 `data` 可以为 `null`
     */
    fun setData(data: Any?)
}

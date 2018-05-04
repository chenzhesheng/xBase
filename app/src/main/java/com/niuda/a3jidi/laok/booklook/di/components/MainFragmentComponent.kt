package com.niuda.a3jidi.laok.booklook.di.components

import com.niuda.a3jidi.laok.booklook.di.scopes.PerActivity
import com.niuda.a3jidi.laok.booklook.view.fragment.MainFragment
import dagger.Subcomponent

/**
 * 作者: created by chenzhesheng on 2018/5/4 16:55
 */

@PerActivity
@Subcomponent
interface MainFragmentComponent {
    fun inject(mainFragment : MainFragment)
}
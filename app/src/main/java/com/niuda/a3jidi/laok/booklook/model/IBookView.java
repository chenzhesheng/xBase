package com.niuda.a3jidi.laok.booklook.model;

import com.niuda.a3jidi.lib_base.base.base.IBaseView;

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:40
 */

public interface IBookView extends IBaseView {
    void onSuccess(Book book);
    void onError(String result);
}

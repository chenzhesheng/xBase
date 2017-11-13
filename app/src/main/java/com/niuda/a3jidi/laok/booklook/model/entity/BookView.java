package com.niuda.a3jidi.laok.booklook.model.entity;

import com.niuda.a3jidi.laok.base.view.View;

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:40
 */

public interface BookView extends View {
    void onSuccess(Book book);
    void onError(String result);
}

package com.niuda.a3jidi.lib_base.base.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mac on 2017/6/29.
 */

public class ProgressResponseInterceptor implements Interceptor {

	private ProgressResponseListener listener;

	public ProgressResponseInterceptor(ProgressResponseListener listener) {
		this.listener = listener;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
//		boolean state = BaseApp.baseApp.getAppState();
//		if (state) {
//			BaseApp.baseApp.setAppState(false);
			return chain.proceed(request);
//		} else {
//			Response originalResponse = chain.proceed(chain.request());
//
//			return originalResponse.newBuilder()
//					.body(new ProgressResponseBody(originalResponse.body(), listener))
//					.build();
//		}
	}
}

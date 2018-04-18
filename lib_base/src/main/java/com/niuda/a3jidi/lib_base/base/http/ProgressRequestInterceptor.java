package com.niuda.a3jidi.lib_base.base.http;

import android.support.annotation.NonNull;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mac on 2017/6/29.
 */

public class ProgressRequestInterceptor implements Interceptor {

	private ProgressRequestListener listener;
	private RequestBody requestBody;

	public ProgressRequestInterceptor(RequestBody requestBody,ProgressRequestListener listener) {
		this.listener = listener;
		this.requestBody= requestBody;
	}
	public ProgressRequestInterceptor(ProgressRequestListener listener) {
		this.listener = listener;
	}
	@Override
	public Response intercept(@NonNull Chain chain) throws IOException {
		Request request = chain.request();
//		boolean state = BaseApp.baseApp.getAppState();
//		if (state) {
//			BaseApp.baseApp.setAppState(false);
//			return chain.proceed(request);
//		}else{
//			//参数加密
//			String token = TAppTokenModel.INSTANCE.getCurrentToken();
//			String privateKey = TAppTokenModel.INSTANCE.getPrivateKey();
//			String sPublicKey = TAppTokenModel.INSTANCE.getPublicKeyForServer();
//			String content = request.url().queryParameter("content");
//			if (content == null || content.equals("")) {
//				content = "content";
//			}
//			String encodedData = CodeUtil.encryptByPublicForLong(content, sPublicKey);
//			String sign = CodeUtil.newSign(encodedData, privateKey);
//			// 添加新的参数
//			HttpUrl.Builder authorizedUrlBuilder = request.url()
//					.newBuilder()
//					.scheme(request.url().scheme())
//					.host(request.url().host())
//					.addQueryParameter("sign", sign)
//					.addQueryParameter("token", token)
//					.setQueryParameter("content", encodedData);
//			RequestBody newReqpestBody = null;
//			if (requestBody == null) {
//				newReqpestBody = new ProgressRequestBody(request.body(), listener);
//			}else {
//				newReqpestBody = new ProgressRequestBody(requestBody, listener);
//			}
//			request = request.newBuilder()
//					.url(authorizedUrlBuilder.build())
//					.post(newReqpestBody)
//					.build();
			return chain.proceed(request);
//		}
	}
}

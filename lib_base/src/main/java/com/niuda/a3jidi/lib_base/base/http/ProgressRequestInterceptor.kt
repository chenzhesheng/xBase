package com.niuda.a3jidi.lib_base.base.http


import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

/**
 * Created by mac on 2017/6/29.
 */

class ProgressRequestInterceptor : Interceptor {

    private var listener: ProgressRequestListener? = null
    lateinit var requestBody: RequestBody

    constructor(requestBody: RequestBody, listener: ProgressRequestListener) {
        this.listener = listener
        this.requestBody = requestBody
    }

    constructor(listener: ProgressRequestListener) {
        this.listener = listener
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
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
        return chain.proceed(request)
        //		}
    }
}

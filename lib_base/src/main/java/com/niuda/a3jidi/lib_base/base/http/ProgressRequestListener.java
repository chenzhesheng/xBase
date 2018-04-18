package com.niuda.a3jidi.lib_base.base.http;

/**
 * Created by mac on 2017/6/29.
 */

public interface ProgressRequestListener {
	void update(long bytesRead, long contentLength, boolean done);
}

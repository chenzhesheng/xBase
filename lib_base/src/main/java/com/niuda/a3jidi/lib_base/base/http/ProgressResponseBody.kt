package com.niuda.a3jidi.lib_base.base.http

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * Created by mac on 2017/6/29.
 */

class ProgressResponseBody(private val responseBody: ResponseBody,
                           private val progressListener: ProgressResponseListener?) : ResponseBody() {
    var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead: Long = super.read(sink, byteCount)
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += if (bytesRead.toInt() != -1) bytesRead else 0

                progressListener?.update(totalBytesRead, responseBody.contentLength(), bytesRead.toInt() == -1)
                return bytesRead
            }
        }

    }
}

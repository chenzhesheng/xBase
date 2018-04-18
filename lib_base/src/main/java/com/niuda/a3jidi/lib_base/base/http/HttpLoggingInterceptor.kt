package com.niuda.a3jidi.lib_base.base.http

import com.elvishew.xlog.XLog
import okhttp3.*
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/12
 * 描    述：OkHttp拦截器，主要用于打印日志
 * 修订历史：
 * ================================================
 */
class HttpLoggingInterceptor(level: Level) : Interceptor {

    @Volatile
    private var printLevel = Level.NONE

    enum class Level {
        NONE, //不打印log
        BASIC, //只打印 请求首行 和 响应首行
        HEADERS, //打印请求和响应的所有 Header
        BODY        //所有数据全部打印
    }

    init {
        setPrintLevel(level)
    }

    fun setPrintLevel(level: Level) {
        printLevel = level
    }

    fun log(message: String) {
        XLog.d(message)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (printLevel == Level.NONE) {
            return chain.proceed(request)
        }

        //请求日志拦截
        logForRequest(request, chain.connection())

        //执行请求，计算请求时间
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            log("<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        //响应日志拦截
        return logForResponse(response, tookMs)
    }

    @Throws(IOException::class)
    private fun logForRequest(request: Request, connection: Connection?) {
        val logBody = printLevel == Level.BODY
        val logHeaders = printLevel == Level.HEADERS
        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1

        try {
            val requestStartMessage = "--> " + request.method() + ' '.toString() + request.url() + ' '.toString() + protocol
            log(requestStartMessage)

            if (logHeaders) {
                val headers = request.headers()
                var i = 0
                val count = headers.size()
                while (i < count) {
                    log("\t" + headers.name(i) + ": " + headers.value(i))
                    i++
                }
            }
            if (logBody) {
                log(" ")
                if (hasRequestBody) {
                    if (isPlaintext(requestBody!!.contentType())) {
                        bodyToString(request)
                    } else {
                        log("\tbody: maybe [file part] , too large too print , ignored!")
                    }
                }
            }
        } catch (e: Exception) {
            XLog.e(e.message, e)
        } finally {
            log("--> END " + request.method())
        }
    }

    private fun logForResponse(response: Response, tookMs: Long): Response {
        val builder = response.newBuilder()
        val clone = builder.build()
        var responseBody = clone.body()
        val logBody = printLevel == Level.BODY
        val logHeaders = printLevel == Level.BODY || printLevel == Level.HEADERS

        try {
            log("<-- " + clone.code() + ' '.toString() + clone.message() + ' '.toString() + clone.request().url() + " (" + tookMs + "ms）")
            if (logHeaders) {
                val headers = clone.headers()
                var i = 0
                val count = headers.size()
                while (i < count) {
                    log("\t" + headers.name(i) + ": " + headers.value(i))
                    i++
                }
                log(" ")
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (isPlaintext(responseBody!!.contentType())) {
                        val body = responseBody.string()
                        log("\tbody:")
                        if (body.startsWith("<xml")) {
                            XLog.xml(body)
                        } else if (body.startsWith("{") && body.endsWith("}")) {
                            XLog.json(body)
                        } else {
                            XLog.d(body)
                        }
                        responseBody = ResponseBody.create(responseBody.contentType(), body)
                        return response.newBuilder().body(responseBody).build()
                    } else {
                        log("\tbody: maybe [file part] , too large too print , ignored!")
                    }
                }
            }
        } catch (e: Exception) {
            XLog.e(e.message, e)
        } finally {
            log("<-- END HTTP")
        }
        return response
    }

    private fun bodyToString(request: Request) {
        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            var charset: Charset? = UTF8
            val contentType = copy.body()!!.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            log("\tbody:" + buffer.readString(charset!!))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {

        private val UTF8 = Charset.forName("UTF-8")

        /**
         * Returns true if the body in question probably contains human readable text. Uses TAppFjxxbModel small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        private fun isPlaintext(mediaType: MediaType?): Boolean {
            if (mediaType == null) return false
            if (mediaType.type() != null && mediaType.type() == "text") {
                return true
            }
            var subtype: String? = mediaType.subtype()
            if (subtype != null) {
                subtype = subtype.toLowerCase()
                if (subtype.contains("x-www-form-urlencoded") ||
                        subtype.contains("json") ||
                        subtype.contains("xml") ||
                        subtype.contains("html"))
                //
                    return true
            }
            return false
        }
    }
}
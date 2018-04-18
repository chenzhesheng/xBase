package com.niuda.a3jidi.lib_base.base.http

class HttpException(detailMessage: String) : RuntimeException(detailMessage) {

    constructor(resultCode: Int) : this(getExceptionMessage(resultCode)) {}

    companion object {

        /**
         * 序列化
         */
        private val serialVersionUID = 1L
        val USER_NOT_EXIST = 100
        val WRONG_PASSWORD = 101

        /**
         * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
         * 需要根据错误码对错误信息进行一个转换，在显示给用户
         * @param code
         * @return
         */
        private fun getExceptionMessage(code: Int): String {
            var message = ""
            when (code) {
                USER_NOT_EXIST -> message = "该用户不存在"
                WRONG_PASSWORD -> message = "密码错误"
                else -> message = "未知错误"
            }
            return message
        }
    }
}


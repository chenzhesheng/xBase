package com.niuda.a3jidi.lib_base.base.http

/**
 * Created by mac on 2017/8/30.
 */
/**
 * 基础数据模型
 */
data class HttpJsonResult(var state: Int?,
                          var message: String?,
                          var publicKey: String?,
                          var sign: String?,
                          var token: String?,
                          var content: String?,
                          var datetime: String?){
    constructor():this(null,null,null,null,null,null,null)
}



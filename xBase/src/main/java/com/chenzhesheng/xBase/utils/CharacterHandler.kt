/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chenzhesheng.xBase.utils

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import java.util.regex.Pattern
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * ================================================
 * 处理字符串的工具类
 *
 *
 * Created by JessYan on 2016/3/16
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class CharacterHandler private constructor() {

    init {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {

        val emojiFilter: InputFilter = object : InputFilter {//emoji过滤器

            internal var emoji = Pattern.compile(
                    "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE)

            override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int,
                                dend: Int): CharSequence? {

                val emojiMatcher = emoji.matcher(source)
                return if (emojiMatcher.find()) {
                    ""
                } else null

            }
        }


        /**
         * 字符串转换成十六进制字符串
         *
         * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
         */
        fun str2HexStr(str: String): String {

            val chars = "0123456789ABCDEF".toCharArray()
            val sb = StringBuilder("")
            val bs = str.toByteArray()
            var bit: Int

            for (i in bs.indices) {
                bit = bs[i].toInt() and 0x0f0 shr 4
                sb.append(chars[bit])
                bit = bs[i].toInt() and 0x0f
                sb.append(chars[bit])
            }
            return sb.toString().trim { it <= ' ' }
        }


        /**
         * json 格式化
         *
         * @param json
         * @return
         */
        fun jsonFormat(json: String): String {
            var json = json
            if (TextUtils.isEmpty(json)) {
                return "Empty/Null json content"
            }
            var message: String
            try {
                json = json.trim { it <= ' ' }
                if (json.startsWith("{")) {
                    val jsonObject = JSONObject(json)
                    message = jsonObject.toString(4)
                } else if (json.startsWith("[")) {
                    val jsonArray = JSONArray(json)
                    message = jsonArray.toString(4)
                } else {
                    message = json
                }
            } catch (e: JSONException) {
                message = json
            }

            return message
        }


        /**
         * xml 格式化
         *
         * @param xml
         * @return
         */
        fun xmlFormat(xml: String): String {
            if (TextUtils.isEmpty(xml)) {
                return "Empty/Null xml content"
            }
            var message: String
            try {
                val xmlInput = StreamSource(StringReader(xml))
                val xmlOutput = StreamResult(StringWriter())
                val transformer = TransformerFactory.newInstance().newTransformer()
                transformer.setOutputProperty(OutputKeys.INDENT, "yes")
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
                transformer.transform(xmlInput, xmlOutput)
                message = xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
            } catch (e: TransformerException) {
                message = xml
            }

            return message
        }
    }
}

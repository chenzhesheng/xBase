package com.chenzhesheng.xBase.demo.mvp.model.entity

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-10-18 10:05
 */

class Book {

    /**
     * count : 1
     * start : 0
     * total : 545
     * books : [{"rating":{"max":10,"numRaters":4296,"average":"8.6","min":0},"subtitle":"张竹坡批评第一奇书","author":["兰陵笑笑生"],"pubdate":"1991","tags":[{"count":2052,"name":"金瓶梅","title":"金瓶梅"},{"count":1189,"name":"古典文学","title":"古典文学"},{"count":867,"name":"兰陵笑笑生","title":"兰陵笑笑生"},{"count":760,"name":"小说","title":"小说"},{"count":652,"name":"中国古典文学","title":"中国古典文学"},{"count":430,"name":"中国文学","title":"中国文学"},{"count":375,"name":"古典","title":"古典"},{"count":361,"name":"中国","title":"中国"}],"origin_title":"（明）兰陵笑笑生","image":"https://img1.doubanio.com/mpic/s10069398.jpg","binding":"","translator":[],"catalog":"\n      ","pages":"","images":{"small":"https://img1.doubanio.com/spic/s10069398.jpg","large":"https://img1.doubanio.com/lpic/s10069398.jpg","medium":"https://img1.doubanio.com/mpic/s10069398.jpg"},"alt":"https://book.douban.com/subject/1456692/","id":"1456692","publisher":"齐鲁出版社","isbn10":"7533300815","isbn13":"9787533300814","title":"金瓶梅","url":"https://api.douban.com/v2/book/1456692","alt_title":"（明）兰陵笑笑生","author_intro":"","summary":"本书由王汝梅与李昭恂、于凤树校点。","series":{"id":"4279","title":"明代四大奇书"},"price":"268.00元"}]
     */

    var count: Int = 0
    var start: Int = 0
    var total: Int = 0
    var books: List<BooksBean>? = null

    class BooksBean {
        /**
         * rating : {"max":10,"numRaters":4296,"average":"8.6","min":0}
         * subtitle : 张竹坡批评第一奇书
         * author : ["兰陵笑笑生"]
         * pubdate : 1991
         * tags : [{"count":2052,"name":"金瓶梅","title":"金瓶梅"},{"count":1189,"name":"古典文学","title":"古典文学"},{"count":867,"name":"兰陵笑笑生","title":"兰陵笑笑生"},{"count":760,"name":"小说","title":"小说"},{"count":652,"name":"中国古典文学","title":"中国古典文学"},{"count":430,"name":"中国文学","title":"中国文学"},{"count":375,"name":"古典","title":"古典"},{"count":361,"name":"中国","title":"中国"}]
         * origin_title : （明）兰陵笑笑生
         * image : https://img1.doubanio.com/mpic/s10069398.jpg
         * binding :
         * translator : []
         * catalog :
         *
         * pages :
         * images : {"small":"https://img1.doubanio.com/spic/s10069398.jpg","large":"https://img1.doubanio.com/lpic/s10069398.jpg","medium":"https://img1.doubanio.com/mpic/s10069398.jpg"}
         * alt : https://book.douban.com/subject/1456692/
         * id : 1456692
         * publisher : 齐鲁出版社
         * isbn10 : 7533300815
         * isbn13 : 9787533300814
         * title : 金瓶梅
         * url : https://api.douban.com/v2/book/1456692
         * alt_title : （明）兰陵笑笑生
         * author_intro :
         * summary : 本书由王汝梅与李昭恂、于凤树校点。
         * series : {"id":"4279","title":"明代四大奇书"}
         * price : 268.00元
         */

        var rating: RatingBean? = null
        var subtitle: String? = null
        var pubdate: String? = null
        var origin_title: String? = null
        var image: String? = null
        var binding: String? = null
        var catalog: String? = null
        var pages: String? = null
        var images: ImagesBean? = null
        var alt: String? = null
        var id: String? = null
        var publisher: String? = null
        var isbn10: String? = null
        var isbn13: String? = null
        var title: String? = null
        var url: String? = null
        var alt_title: String? = null
        var author_intro: String? = null
        var summary: String? = null
        var series: SeriesBean? = null
        var price: String? = null
        var author: List<String>? = null
        var tags: List<TagsBean>? = null
        var translator: List<*>? = null

        override fun toString(): String {
            return "BooksBean{" +
                    "rating=" + rating +
                    ", subtitle='" + subtitle + '\''.toString() +
                    ", pubdate='" + pubdate + '\''.toString() +
                    ", origin_title='" + origin_title + '\''.toString() +
                    ", image='" + image + '\''.toString() +
                    ", binding='" + binding + '\''.toString() +
                    ", catalog='" + catalog + '\''.toString() +
                    ", pages='" + pages + '\''.toString() +
                    ", images=" + images +
                    ", alt='" + alt + '\''.toString() +
                    ", id='" + id + '\''.toString() +
                    ", publisher='" + publisher + '\''.toString() +
                    ", isbn10='" + isbn10 + '\''.toString() +
                    ", isbn13='" + isbn13 + '\''.toString() +
                    ", title='" + title + '\''.toString() +
                    ", url='" + url + '\''.toString() +
                    ", alt_title='" + alt_title + '\''.toString() +
                    ", author_intro='" + author_intro + '\''.toString() +
                    ", summary='" + summary + '\''.toString() +
                    ", series=" + series +
                    ", price='" + price + '\''.toString() +
                    ", author=" + author +
                    ", tags=" + tags +
                    ", translator=" + translator +
                    '}'.toString()
        }

        class RatingBean {
            /**
             * max : 10
             * numRaters : 4296
             * average : 8.6
             * min : 0
             */

            var max: Int = 0
            var numRaters: Int = 0
            var average: String? = null
            var min: Int = 0

            override fun toString(): String {
                return "RatingBean{" +
                        "max=" + max +
                        ", numRaters=" + numRaters +
                        ", average='" + average + '\''.toString() +
                        ", min=" + min +
                        '}'.toString()
            }
        }

        class ImagesBean {
            /**
             * small : https://img1.doubanio.com/spic/s10069398.jpg
             * large : https://img1.doubanio.com/lpic/s10069398.jpg
             * medium : https://img1.doubanio.com/mpic/s10069398.jpg
             */

            var small: String? = null
            var large: String? = null
            var medium: String? = null

            override fun toString(): String {
                return "ImagesBean{" +
                        "small='" + small + '\''.toString() +
                        ", large='" + large + '\''.toString() +
                        ", medium='" + medium + '\''.toString() +
                        '}'.toString()
            }
        }

        class SeriesBean {
            /**
             * id : 4279
             * title : 明代四大奇书
             */

            var id: String? = null
            var title: String? = null

            override fun toString(): String {
                return "SeriesBean{" +
                        "id='" + id + '\''.toString() +
                        ", title='" + title + '\''.toString() +
                        '}'.toString()
            }
        }

        class TagsBean {
            /**
             * count : 2052
             * name : 金瓶梅
             * title : 金瓶梅
             */

            var count: Int = 0
            var name: String? = null
            var title: String? = null

            override fun toString(): String {
                return "TagsBean{" +
                        "count=" + count +
                        ", name='" + name + '\''.toString() +
                        ", title='" + title + '\''.toString() +
                        '}'.toString()
            }
        }
    }

    override fun toString(): String {
        return "Book{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", books=" + books +
                '}'.toString()
    }
}

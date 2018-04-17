package com.niuda.a3jidi.lib_base.base.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import java.util.*

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-11 14:07
 */
abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {

    protected var mContext: Context
    protected var mDatas: MutableList<T>? = null
    protected var inflater: LayoutInflater

    constructor(context: Context) {
        this.mContext = context
        this.mDatas = ArrayList()
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    constructor(context: Context, datas: MutableList<T>?) {
        var datas = datas
        if (datas == null) datas = ArrayList()
        this.mContext = context
        this.mDatas = datas
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    constructor(context: Context, datas: Array<T>) {
        this.mContext = context
        this.mDatas = ArrayList()
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        Collections.addAll(mDatas!!, *datas)
    }

    override fun getItemCount(): Int {
        return if (mDatas == null) 0 else mDatas!!.size
    }

    /** 更新数据，替换原有数据  */
    fun updateItems(items: MutableList<T>) {
        mDatas = items
        notifyDataSetChanged()
    }

    /** 插入一条数据  */
    fun addItem(item: T) {
        mDatas!!.add(0, item)
        notifyItemInserted(0)
    }

    /** 插入一条数据  */
    fun addItem(item: T, position: Int) {
        var position = position
        position = Math.min(position, mDatas!!.size)
        mDatas!!.add(position, item)
        notifyItemInserted(position)
    }

    /** 在列表尾添加一串数据  */
    fun addItems(items: List<T>) {
        val start = mDatas!!.size
        mDatas!!.addAll(items)
        notifyItemRangeChanged(start, items.size)
    }

    /** 移除一条数据  */
    fun removeItem(position: Int) {
        if (position > mDatas!!.size - 1) {
            return
        }
        mDatas!!.removeAt(position)
        notifyItemRemoved(position)
    }

    /** 移除一条数据  */
    fun removeItem(item: T) {
        var position = 0
        val iterator = mDatas!!.listIterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next === item) {
                iterator.remove()
                notifyItemRemoved(position)
            }
            position++
        }
    }

    /** 清除所有数据  */
    fun removeAllItems() {
        mDatas!!.clear()
        notifyDataSetChanged()
    }
}

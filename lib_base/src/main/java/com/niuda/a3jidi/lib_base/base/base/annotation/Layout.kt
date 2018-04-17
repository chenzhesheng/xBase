package com.gdth.bank.base.annotation

/**
 * Created by mac on 2017/5/20.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class Layout(val value: Int = 0)
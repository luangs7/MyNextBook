package com.lgdevs.mynextbook.observerkmm.model

fun interface Observer<T> {
    fun update(data: T)
}
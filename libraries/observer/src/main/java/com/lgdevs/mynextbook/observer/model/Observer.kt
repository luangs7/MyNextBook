package com.lgdevs.mynextbook.observer.model

fun interface Observer<T> {
    fun update(data: T)
}
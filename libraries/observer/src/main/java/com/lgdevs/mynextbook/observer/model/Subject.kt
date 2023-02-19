package com.lgdevs.mynextbook.observer.model

interface Subject <T> {
    fun observer(o: Observer<T>)
    fun invoke(data: T)
    fun removeObserver(o: Observer<T>)
    fun notifyObservers()
}
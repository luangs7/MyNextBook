package com.lgdevs.mynextbook.observerkmm

import com.lgdevs.mynextbook.observerkmm.model.Observer
import com.lgdevs.mynextbook.observerkmm.model.Subject

class KObserver<T> : Subject<T> {

    private var data: T? = null
    private val observers: MutableList<Observer<T>> = mutableListOf()

    override fun observer(o: Observer<T>) {
        observers.add(o)
    }

    override fun removeObserver(o: Observer<T>) {
        observers.remove(o)
    }

    override fun invoke(data: T) {
        this.data = data
        notifyObservers()
    }

    override fun notifyObservers() {
        observers.forEach {
            data?.let { value -> it.update(value) }
        }
    }

}

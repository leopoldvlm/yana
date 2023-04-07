package fr.leopoldvlm.yana.model

@Suppress("unused")
open class Observable {
    private val attached: Set<Observer> = emptySet()

    fun attach(observer: Observer) {
        attached.plus(observer)
        observer.update(this)
    }

    fun detach(observer: Observer) {
        attached.minus(observer)
    }

    fun notifyObservers() {
        attached.forEach() {observer -> observer.update(this) }
    }

    fun notifyObservers(data: Any) {
        attached.forEach() {observer -> observer.update(this, data) }
    }
}
package fr.leopoldvlm.yana.model

interface Observer  {
    fun update(observable: Observable)
    fun update(observable: Observable, data: Any)
}
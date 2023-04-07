package fr.leopoldvlm.yana.model.note

import com.google.firebase.Timestamp
import java.util.*

data class Note(
    val content: String,
    val createdAt: Timestamp,
    val title: String,
    val lastModified: Timestamp?
) {
    @Suppress("unused")
    constructor(): this(
        "",
        Timestamp(Date()),
        "",
        null
    )
}

package fr.leopoldvlm.yana.notes

import com.google.firebase.Timestamp
import java.util.*

data class DocumentNotes(
    val id: String,
    val notes: List<Note>
) {
    // no argument constructor -> for Firebase deserialisation!
    @Suppress("unused")
    constructor(): this("", ArrayList<Note>())
}

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


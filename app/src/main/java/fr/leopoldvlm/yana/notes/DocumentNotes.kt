package fr.leopoldvlm.yana.notes

import com.google.firebase.Timestamp

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
    val title: String
) {
    @Suppress("unused")
    constructor(): this("", Timestamp(java.util.Date()), "")
}


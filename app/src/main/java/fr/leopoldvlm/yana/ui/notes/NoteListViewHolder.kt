package fr.leopoldvlm.yana.ui.notes

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import fr.leopoldvlm.yana.databinding.NoteListBinding
import fr.leopoldvlm.yana.model.note.Note
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class NoteListViewHolder(private val binding: NoteListBinding) : RecyclerView.ViewHolder(binding.root) {
    private val dateFormatter = android.text.format.DateFormat.getDateFormat(binding.root.context)
    private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm a")

    @SuppressLint("SetTextI18n")
    fun setStuff(note: Note) {
        binding.noteCardTitle.text = note.title
        binding.noteCardDetails.text = note.content
        val date = note.lastModified?.toDate()
        val hour = note.lastModified?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime()
        binding.noteCardLastmod.text = "Last modified on ${date?.let { dateFormatter.format(it) }} at ${hourFormatter.format(hour)}"
    }

}
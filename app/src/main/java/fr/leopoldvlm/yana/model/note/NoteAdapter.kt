package fr.leopoldvlm.yana.model.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.leopoldvlm.yana.databinding.NoteListBinding
import fr.leopoldvlm.yana.ui.notes.NoteListViewHolder

class NoteAdapter(private val notes: List<Note>): RecyclerView.Adapter<NoteListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val binding = NoteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.setStuff(notes[position])
    }

}
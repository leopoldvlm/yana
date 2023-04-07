package fr.leopoldvlm.yana.ui.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import fr.leopoldvlm.yana.MainActivity
import fr.leopoldvlm.yana.R
import fr.leopoldvlm.yana.databinding.ActivityNoteListBinding
import fr.leopoldvlm.yana.model.YanaApplication
import fr.leopoldvlm.yana.model.note.NoteAdapter
import fr.leopoldvlm.yana.ui.auth.LoginActivity


class NoteListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = (application as YanaApplication).auth
        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setText()
        setTopBarListeners()
    }

    private fun setText() {
        binding.progressBar.visibility = View.VISIBLE

        (application as YanaApplication).notes.observe(this) { notes ->
            val recyclerView = binding.notelistRecyclerview
            val adapter = NoteAdapter(notes)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            val lm = LinearLayoutManager(this)
            recyclerView.layoutManager = lm
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun setTopBarListeners() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_signout -> logout()
                else -> false
            }
        }
    }

    private fun logout(): Boolean {
        if (auth.currentUser == null) {
            return false
        }
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }


}
package fr.leopoldvlm.yana

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import fr.leopoldvlm.yana.auth.LoginActivity
import fr.leopoldvlm.yana.databinding.ActivityNoteBinding
import fr.leopoldvlm.yana.notes.DocumentNotes
import fr.leopoldvlm.yana.notes.Note
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NoteListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setText()
    }

    private fun setText() {
        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        binding.who.text = resources.getString(R.string.welcome, auth.currentUser?.email)
        val text = StringBuilder()

        lifecycleScope.launch {
            val notes = retrieveNotes()
            for (note in notes) {
                val localDate = note.createdAt.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                text.append("${note.title} (created on ${localDate.format(dateFormatter)}" +
                        " at ${localDate.format(hourFormatter)})\n")
            }
            binding.notes.text = text
        }
    }

    private suspend fun retrieveNotes(): List<Note> {
        return try {
            val ref = db.collection("notes").document(auth.currentUser!!.uid)
            val snapshot = ref.get().await()
            if (snapshot.exists()) {
                snapshot.toObject(DocumentNotes::class.java)!!.notes
            } else {
                ArrayList()
            }
        } catch (e: NullPointerException) {
            ArrayList()
        }
    }
}
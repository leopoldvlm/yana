package fr.leopoldvlm.yana.ui.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import fr.leopoldvlm.yana.YanaApplication
import fr.leopoldvlm.yana.databinding.ActivityNoteListBinding
import fr.leopoldvlm.yana.ui.auth.LoginActivity
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class NoteListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding
    private lateinit var auth: FirebaseAuth

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm")

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
    }

    private fun setText() {
        val text = StringBuilder()
        binding.progressBar.visibility = View.VISIBLE

        (application as YanaApplication).notes.observe(this) { notes ->
            for (note in notes) {
                val localDate = note.createdAt.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                text.append("${note.title} (created on ${localDate.format(dateFormatter)}" +
                        " at ${localDate.format(hourFormatter)})\n")
            }
            for(i in 0..100) {
                text.append("much text! $i\n")
            }
            binding.text.text = text
            binding.progressBar.visibility = View.INVISIBLE
        }

//        lifecycleScope.launch {
//            val notes = (application as YanaApplication).getNotes()
//            for (note in notes) {
//                val localDate = note.createdAt.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
//                text.append("${note.title} (created on ${localDate.format(dateFormatter)}" +
//                        " at ${localDate.format(hourFormatter)})\n")
//            }
//            for(i in 0..100) {
//                text.append("much text! $i\n")
//            }
//            binding.text.text = text
//            binding.progressBar.visibility = View.INVISIBLE
//        }
    }


}
package fr.leopoldvlm.yana.ui.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import fr.leopoldvlm.yana.MainActivity
import fr.leopoldvlm.yana.R
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
        setTopBarListeners()
    }

    private fun setText() {
        val text = StringBuilder()
        binding.progressBar.visibility = View.VISIBLE

        (application as YanaApplication).notes.observe(this) { notes ->
            for (note in notes) {
                val localDate = note.createdAt.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                val ok = note.lastModified?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime()
                val txt = "${note.title} (created on ${localDate.format(dateFormatter)}" +
                        " mod at $ok\n"
                text.append(txt)
                Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
            }
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
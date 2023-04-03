package fr.leopoldvlm.yana.ui.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import fr.leopoldvlm.yana.R
import fr.leopoldvlm.yana.databinding.ActivityNoteListBinding
import java.time.format.DateTimeFormatter


class NoteListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        setBar()

//        setText()
        supportActionBar?.show()
    }

    private fun setBar() {
//        val bar = binding.topbar
//        val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
//
//        bar.subtitle = auth.currentUser?.email
//        bar.setOnMenuItemClickListener { menuItem ->
//            when(menuItem.itemId) {
//                R.id.edit -> {
//                    toast.setText("edit")
//                    toast.show()
//                    true
//                }
//                else -> false
//            }
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notelist_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Handle search bar item here
                val searchView = item.actionView
                // Set up search view listener and other properties as needed
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun setText() {
//        if (auth.currentUser == null) {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//            return
//        }
//        val text = StringBuilder()
//        binding.noteLoadingbar.visibility = View.VISIBLE
//
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                Thread.sleep(2000)
//            }
//            val notes = retrieveNotes()
//            for (note in notes) {
//                val localDate = note.createdAt.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
//                text.append("${note.title} (created on ${localDate.format(dateFormatter)}" +
//                        " at ${localDate.format(hourFormatter)})\n")
//            }
//            binding.notes.text = text
//            binding.noteLoadingbar.visibility = View.INVISIBLE
//        }
//    }

//    private suspend fun retrieveNotes(): List<Note> {
//        return try {
//            val ref = db.collection("notes").document(auth.currentUser!!.uid)
//            val snapshot = ref.get().await()
//            if (snapshot.exists()) {
//                snapshot.toObject(DocumentNotes::class.java)!!.notes
//            } else {
//                ArrayList()
//            }
//        } catch (e: NullPointerException) {
//            ArrayList()
//        }
//    }
}
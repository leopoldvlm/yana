package fr.leopoldvlm.yana.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.leopoldvlm.yana.model.note.Note

class YanaApplication() : Application() {

    lateinit var auth: FirebaseAuth
    private val notesLiveData = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>>
        get() = notesLiveData


    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                retrieveNotes {notes ->
                    notesLiveData.postValue(notes)
                }
            }
        }

    }

    private fun retrieveNotes(callback: (List<Note>?) -> Unit) {
        val db = Firebase.firestore
        val list = mutableListOf<Note>()
        val ref = db.collection("users").document(auth.currentUser!!.uid)
            .collection("notes")
        ref.get()
            .addOnCompleteListener { documents ->
                documents.result.forEach { document ->
                    val thing = document.toObject(Note::class.java)
                    list.add(thing)
                }
                callback(list)
            }
            .addOnFailureListener {
                callback(null)
            }
    }
}
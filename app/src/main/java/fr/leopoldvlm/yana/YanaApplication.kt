package fr.leopoldvlm.yana

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.leopoldvlm.yana.notes.DocumentNotes
import fr.leopoldvlm.yana.notes.Note
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

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
                runBlocking { // idk i tried to do better for hours, it'll be this for now
                    val notes = retrieveNotes()
                    notesLiveData.postValue(notes)
                }
            }
        }

    }

    private suspend fun retrieveNotes(): List<Note> {
        val db = Firebase.firestore
        return try {
            val ref = db.collection("notes").document(auth.currentUser!!.uid)
            val snapshot = ref.get().await()
            if (snapshot.exists()) {
                snapshot.toObject(DocumentNotes::class.java)!!.notes
            } else {
                emptyList()
            }
        } catch (e: NullPointerException) {
            emptyList()
        }
    }


}
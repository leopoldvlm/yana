package fr.leopoldvlm.yana

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class YanaApplication : Application() {

    lateinit var auth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
    }


}
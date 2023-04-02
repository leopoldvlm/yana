package fr.leopoldvlm.yana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.leopoldvlm.yana.auth.LoginActivity
import fr.leopoldvlm.yana.auth.SigninActivity
import fr.leopoldvlm.yana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        handleAuth()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvents()

        authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            Log.i("allo?", "allo?")
            if (result.resultCode == RESULT_OK) {
                nextActivity()
            }
        }
    }

    private fun setEvents() {
        binding.login.setOnClickListener {
            authLauncher.launch(Intent(this, LoginActivity::class.java))
        }

        binding.signin.setOnClickListener {
            authLauncher.launch(Intent(this, SigninActivity::class.java))
        }
    }

    private fun handleAuth() {
        if (auth.currentUser != null) {
            nextActivity()
        }
    }

    private fun nextActivity() {
        Log.i("allo?", "allo?")
        startActivity(Intent(this, NoteListActivity::class.java))
        finish()
    }
}
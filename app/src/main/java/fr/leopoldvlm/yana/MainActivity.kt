package fr.leopoldvlm.yana

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import fr.leopoldvlm.yana.databinding.ActivityMainBinding
import fr.leopoldvlm.yana.databinding.ActivityMainLoadingBinding
import fr.leopoldvlm.yana.ui.auth.LoginActivity
import fr.leopoldvlm.yana.ui.auth.SigninActivity
import fr.leopoldvlm.yana.ui.notes.NoteListActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = (application as YanaApplication).auth
        setContentView(ActivityMainLoadingBinding.inflate(layoutInflater).root)
        handleAuth()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEvents()

        authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
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
        startActivity(Intent(this, NoteListActivity::class.java))
        finish()
    }
}
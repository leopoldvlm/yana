package fr.leopoldvlm.yana.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.leopoldvlm.yana.R
import fr.leopoldvlm.yana.databinding.ActivityAuthBinding

abstract class AuthActivity : AppCompatActivity() {
    protected lateinit var auth: FirebaseAuth
    protected lateinit var binding: ActivityAuthBinding
    protected val tag = "FirebaseAuth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val button = binding.auth

        button.setOnClickListener {
            val email = binding.email.text.toString()
            if (email == "") {
                Toast.makeText(baseContext, "Enter an email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val password = binding.password.text.toString()
            if (password == "") {
                Toast.makeText(baseContext, "Enter a password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            handleAuth(email, password)
        }

    }

    override fun onStart() {
        super.onStart()
        // check if the user is already signed in!
        val currentUser = auth.currentUser
        if (currentUser != null) {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.loggedin))
                .setMessage(resources.getString(R.string.already_loggedin, currentUser.email))
                .setIcon(R.drawable.baseline_check_24)
                .setPositiveButton(resources.getText(R.string.logout)) { _, _ ->
                    auth.signOut()
                }
                .setNegativeButton(resources.getText(R.string.stay_loggedin)) { _, _ ->
                    finishActivity(currentUser)
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun handleAuth(email: String, password: String) {
        binding.auth.visibility = View.INVISIBLE
        binding.prog.visibility = View.VISIBLE
        auth(email, password) { result ->
            if (result) {
            finishActivity(auth.currentUser)
            } else {
                binding.auth.visibility = View.VISIBLE
                binding.prog.visibility = View.INVISIBLE
            }
        }
    }

    protected abstract fun auth(email: String, password: String, callback: (Boolean) -> Unit)

    protected abstract fun getAuthContext(): CharSequence

    private fun finishActivity(user: FirebaseUser?) {
        if (user == null) {
            setResult(RESULT_CANCELED)
            finish()
        } else {
            setResult(RESULT_OK, Intent().putExtra("user", user))
            finish()
        }
    }
}
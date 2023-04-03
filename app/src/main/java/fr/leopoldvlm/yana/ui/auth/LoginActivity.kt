package fr.leopoldvlm.yana.ui.auth

import android.os.Bundle
import android.widget.Toast
import fr.leopoldvlm.yana.R


class LoginActivity : AuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.auth.text = getAuthContext()
        supportActionBar?.title = "Welcome back to Yana!"
    }

    override fun auth(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    Toast.makeText(baseContext, "Auth failed", Toast.LENGTH_SHORT).show()
                    binding.password.setText("")
                    callback(false)
                }
            }
    }

    override fun getAuthContext(): CharSequence {
        return resources.getText(R.string.login)
    }
}
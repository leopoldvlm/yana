package fr.leopoldvlm.yana.ui.auth

import android.os.Bundle
import android.widget.Toast
import fr.leopoldvlm.yana.R

class SigninActivity : AuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.auth.text = getAuthContext()
        supportActionBar?.title = "Welcome to Yana!"
    }

    override fun auth(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    Toast.makeText(this, "Could not sign you in", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }
    }

    override fun getAuthContext(): CharSequence {
        return resources.getText(R.string.signin)
    }
}
package com.nomadapps.watchlist.view.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nomadapps.watchlist.view.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var pass: String
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth
        button.setOnClickListener {
            email = editTextEmail.text.toString()
            pass = editTextPassword.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                createAccount(email, pass)
            } else {
                Toast.makeText(
                    baseContext, "Email or Password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        textView3.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        baseContext, "Created Succesfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val i = Intent(this, MovieActivity::class.java)
                    startActivity(i)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

                } else {
                    // If sign in fails, display a message to the user.
                    if (!email.isValidEmail()) {
                        Toast.makeText(
                            baseContext, "Email is not valid",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (password.length < 6) {
                        Toast.makeText(
                            baseContext, "Password length must be at least 6 characters",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext, "Email Already in Use",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.e("ahah", "createUserWithEmail:failure", task.exception)

                }
            }

    }

    fun String.isValidEmail() =
        isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()


}
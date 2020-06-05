package com.giyadabi.appat_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email: String = email_edittext_login.text.toString()
        val password: String = password_edittext_login.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,"harap mengisi email dan password",Toast.LENGTH_SHORT).show()
            return
//            mau ke firebasenya kak?
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener
                startActivity(Intent(this,TotalReportActivty::class.java))
                Log.d("Login","succesfully login in : ${it.result?.user?.uid}")
            }
            .addOnFailureListener{
                Toast.makeText(this,"Failed log in: ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }

    val users = FirebaseAuth.getInstance().currentUser
    override fun onStart() {
        super.onStart()
        if (users != null) {
            val intent = Intent(Intent(this, TotalReportActivty::class.java))
            startActivity(intent)
        } else {

        }
    }
}

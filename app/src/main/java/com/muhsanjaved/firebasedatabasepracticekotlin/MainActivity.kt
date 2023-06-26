package com.muhsanjaved.firebasedatabasepracticekotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhsanjaved.firebasedatabasepracticekotlin.activities.HomeActivity
import com.muhsanjaved.firebasedatabasepracticekotlin.databinding.ActivityMainBinding
import kotlin.math.sin

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.LoginButton.setOnClickListener {

            auth.createUserWithEmailAndPassword(binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success")
                        Toast.makeText(this,task.result.toString(), Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
//                        updateUI(null)
                    }
                }


        }


        binding.SignUpWithGoogle.setOnClickListener {

            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)

        }

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        result->

        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            if (task.isSuccessful){
                val account : GoogleSignInAccount?=task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                    else{Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()}
                }
            }

        }else{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStart() {
        super.onStart()

//        if (FirebaseAuth.getInstance().currentUser!= null){
//        if (auth.currentUser!= null){
//            startActivity(Intent(this, HomeActivity::class.java))
//        }
    }
}
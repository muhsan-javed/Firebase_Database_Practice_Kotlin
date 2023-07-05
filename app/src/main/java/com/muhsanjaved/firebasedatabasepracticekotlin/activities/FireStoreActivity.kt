package com.muhsanjaved.firebasedatabasepracticekotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhsanjaved.firebasedatabasepracticekotlin.R
import com.muhsanjaved.firebasedatabasepracticekotlin.databinding.ActivityFirestoreBinding

class FireStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirestoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        binding.Login.setOnClickListener {
            // Create a new user with a first and last name
            val user = hashMapOf(
                "email" to binding.edEmail.text.toString(),
                "pass" to binding.edPass.text.toString(),
            )

            // Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
//                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(
                        this,
                        "DocumentSnapshot added with ID: ${documentReference.id}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
//                Log.w("TAG", "Error adding document", e)
                    Toast.makeText(
                        this,
                        "Error adding document\", ${e.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }


        }
    }
}
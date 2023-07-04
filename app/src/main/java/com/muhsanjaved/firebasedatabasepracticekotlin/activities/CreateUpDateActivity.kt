package com.muhsanjaved.firebasedatabasepracticekotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.muhsanjaved.firebasedatabasepracticekotlin.R
import com.muhsanjaved.firebasedatabasepracticekotlin.databinding.ActivityCreateUpDateBinding
import com.muhsanjaved.firebasedatabasepracticekotlin.databinding.ActivityHomeBinding
import com.muhsanjaved.firebasedatabasepracticekotlin.models.Data

class CreateUpDateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateUpDateBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUpDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        binding.btnSave.setOnClickListener {
            val  key = database.child("Notes").push().key
            database.child("Notes").child(key!!).setValue(Data(binding.editTextText.text.toString(), key)).addOnCompleteListener {
                task->

                if (task.isSuccessful){
                    Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT,).show()
                    startActivity(Intent(this@CreateUpDateActivity,HomeActivity::class.java))
                }else{
                    Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_SHORT,).show()
                }

            }
        }

    }
}
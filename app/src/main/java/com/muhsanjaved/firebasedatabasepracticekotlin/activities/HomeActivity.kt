package com.muhsanjaved.firebasedatabasepracticekotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.muhsanjaved.firebasedatabasepracticekotlin.MainActivity
import com.muhsanjaved.firebasedatabasepracticekotlin.R
import com.muhsanjaved.firebasedatabasepracticekotlin.adapters.NoteAdapter
import com.muhsanjaved.firebasedatabasepracticekotlin.databinding.ActivityHomeBinding
import com.muhsanjaved.firebasedatabasepracticekotlin.models.Data

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private var list = ArrayList<Data>()
//    private lateinit var noteAdapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val dBRF = Firebase.database.getReference("Notes")
        binding.recyclerViewData.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(list,this)
        binding.recyclerViewData.adapter=noteAdapter

        dBRF.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (dataSnap in snapshot.children){
                    val data = dataSnap.getValue(Data::class.java)
                    list.add(data!!)
                    noteAdapter.notifyDataSetChanged()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        binding.create.setOnClickListener {
            startActivity(Intent(this@HomeActivity, CreateUpDateActivity::class.java).putExtra("MODE","CREATE"))
        }


        /*val DataList = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue<Data>()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)*/

//        binding.recyclerViewData.apply {
//            layoutManager = LinearLayoutManager(this@HomeActivity)
//            adapter = NoteAdapter(list , this@HomeActivity)
//        }

        binding.button.setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build()

//            GoogleSignIn = GoogleSignIn.getClient(this@MainActivity, gso)
            GoogleSignIn.getClient(this,gso).signOut()

            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}
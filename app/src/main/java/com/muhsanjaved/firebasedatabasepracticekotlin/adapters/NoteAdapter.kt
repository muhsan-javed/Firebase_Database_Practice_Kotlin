package com.muhsanjaved.firebasedatabasepracticekotlin.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.muhsanjaved.firebasedatabasepracticekotlin.R
import com.muhsanjaved.firebasedatabasepracticekotlin.activities.CreateUpDateActivity
import com.muhsanjaved.firebasedatabasepracticekotlin.models.Data

class NoteAdapter(private val dataList:ArrayList<Data>, private val context:Context) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.textViewData.text = dataList[position].data
        holder.textViewData.setText(dataList.get(position).data)

        holder.deleteButton.setOnClickListener {
            val key = dataList.get(position).key
            val dbRef = Firebase.database.getReference("Notes").child(key!!)
            dbRef.removeValue().addOnCompleteListener {
                if (it.isSuccessful){
                    notifyDataSetChanged()
                    Toast.makeText(context, "Note Delete",Toast.LENGTH_SHORT).show()
                }

            }
        }

        holder.updateButton.setOnClickListener {
            val intent = Intent(context, CreateUpDateActivity::class.java)
            intent.putExtra("MODE","UPDATE")
            intent.putExtra("KEY",dataList.get(position).key)
            intent.putExtra("DATA",dataList.get(position).data)
            context.startActivity(intent)

        }
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textViewData: TextView = itemView.findViewById(R.id.showNoteTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val updateButton: Button = itemView.findViewById(R.id.updateButton)
    }

}
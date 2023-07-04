package com.muhsanjaved.firebasedatabasepracticekotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muhsanjaved.firebasedatabasepracticekotlin.R
import com.muhsanjaved.firebasedatabasepracticekotlin.models.Data

class NoteAdapter(val dataList:ArrayList<Data> , val context:Context) :
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
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textViewData = itemView.findViewById<TextView>(R.id.showNoteTextView)
    }

}
package com.example.androidtest.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.R

class UserAdapter(private var userList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_analytics, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(newList: List<User>) {
        userList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.id)
        private val nameTextView: TextView = itemView.findViewById(R.id.ime)
        private val timeTextView: TextView = itemView.findViewById(R.id.vrijeme)
        private val stepsTextView: TextView = itemView.findViewById(R.id.broj_koraka)

        fun bind(user: User) {
            idTextView.text = user.id.toString()
            nameTextView.text = user.name
            timeTextView.text = user.time
            stepsTextView.text = "(" + user.steps.toString() + ")"
        }
    }
}
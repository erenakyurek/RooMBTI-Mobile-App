package com.example.roombti

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roombti.databinding.UserLayoutBinding

class UserAdapter(val context: Context, val userList: ArrayList<UserData>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)
    }

    class UserViewHolder(private val binding: UserLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserData) {
            binding.txtName.text = user.name

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DMPageActivity::class.java)
                intent.putExtra("name", user.name)
                intent.putExtra("uid", user.id)
                binding.root.context.startActivity(intent)
            }
        }
    }
}
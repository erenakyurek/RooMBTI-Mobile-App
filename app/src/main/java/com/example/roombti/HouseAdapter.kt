package com.example.roombti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roombti.databinding.ItemHouseBinding

class HouseAdapter(
    private val userList: List<UserData>,
    private val onItemClick: (UserData) -> Unit
) : RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    class HouseViewHolder(private val binding: ItemHouseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserData, onItemClick: (UserData) -> Unit) {
            // Fotoğraf (ilk fotoğraf varsa)
            val photoResId = user.photos?.firstOrNull()?.toIntOrNull() ?: R.drawable.room1
            binding.imageViewHouse.setImageResource(photoResId)
            binding.textViewLocation.text = user.location ?: "-"
            binding.textViewPersonCount.text = "${user.currentHousemates ?: 0}/${user.totalHousemates ?: 0}"
            binding.textViewPrice.text = user.rentPerPerson?.let { String.format("%,d", it) } ?: "-"
            binding.root.setOnClickListener { onItemClick(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val binding = ItemHouseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HouseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        holder.bind(userList[position], onItemClick)
    }

    override fun getItemCount() = userList.size
} 
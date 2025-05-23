package com.example.roombti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HouseAdapter(
    private val userList: List<UserData>,
    private val onItemClick: (UserData) -> Unit
) : RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    class HouseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewHouse: ImageView = itemView.findViewById(R.id.imageViewHouse)
        val textViewLocation: TextView = itemView.findViewById(R.id.textViewLocation)
        val textViewPersonCount: TextView = itemView.findViewById(R.id.textViewPersonCount)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_house, parent, false)
        return HouseViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val user = userList[position]
        // Fotoğraf (ilk fotoğraf varsa)
        val photoResId = user.photos?.firstOrNull()?.toIntOrNull() ?: R.drawable.room1
        holder.imageViewHouse.setImageResource(photoResId)
        holder.textViewLocation.text = user.location ?: "-"
        holder.textViewPersonCount.text = "${user.currentHousemates ?: 0}/${user.totalHousemates ?: 0}"
        holder.textViewPrice.text = user.rentPerPerson?.let { String.format("%,d", it) } ?: "-"
        holder.itemView.setOnClickListener { onItemClick(user) }
    }

    override fun getItemCount() = userList.size
} 
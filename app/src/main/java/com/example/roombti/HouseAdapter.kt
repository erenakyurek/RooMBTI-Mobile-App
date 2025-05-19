package com.example.roombti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HouseAdapter(
    private val houseList: List<House>,
    private val onItemClick: (House) -> Unit
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
        val house = houseList[position]
        holder.imageViewHouse.setImageResource(house.imageResId)
        holder.textViewLocation.text = house.location
        holder.textViewPersonCount.text = "${house.currentPersonCount}/${house.capacity}"
        holder.textViewPrice.text = String.format("%,d", house.price)
        holder.itemView.setOnClickListener { onItemClick(house) }
    }

    override fun getItemCount() = houseList.size
} 
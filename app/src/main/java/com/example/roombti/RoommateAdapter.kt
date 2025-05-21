package com.example.roombti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoommateAdapter(private val userList: List<UserData>, private val onItemClick: (UserData) -> Unit) :
    RecyclerView.Adapter<RoommateAdapter.RoommateViewHolder>() {

    class RoommateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewMbti: TextView = itemView.findViewById(R.id.textViewMbti)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoommateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_roommate, parent, false)
        return RoommateViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoommateViewHolder, position: Int) {
        val user = userList[position]
        holder.textViewName.text = user.name ?: "-"
        holder.textViewMbti.text = user.mbti ?: "-"
        // Arka plan rengi/drawable (örnek: MBTI'ye göre)
        val profileBar = holder.itemView.findViewById<View>(R.id.profile_bar)
        when (user.mbti) {
            "ESTJ", "ISFJ" -> profileBar.setBackgroundResource(R.drawable.profile_bar_blue)
            "INTP", "ENTJ" -> profileBar.setBackgroundResource(R.drawable.profile_bar_purple)
            "ESTP", "ISFP" -> profileBar.setBackgroundResource(R.drawable.profile_bar_yellow)
            "INFJ", "ENFJ" -> profileBar.setBackgroundResource(R.drawable.profile_bar_green)
            else -> profileBar.setBackgroundResource(R.drawable.profile_bar_blue)
        }
        // Cinsiyet simgesi
        val genderIcon = holder.itemView.findViewById<View>(R.id.gender_icon)
        genderIcon.setBackgroundResource(
            if (user.gender == "female") R.drawable.female_icon else R.drawable.male_icon
        )
        // Ok işareti sabit
        val arrowIcon = holder.itemView.findViewById<View>(R.id.arrow_icon)
        arrowIcon.setBackgroundResource(R.drawable.right_arrow)
        // Profil fotoğrafı sabit
        val photo = holder.itemView.findViewById<View>(R.id.photo)
        photo.setBackgroundResource(R.drawable.bar_photo)
        holder.itemView.setOnClickListener {
            onItemClick(user)
        }
    }

    override fun getItemCount() = userList.size
} 
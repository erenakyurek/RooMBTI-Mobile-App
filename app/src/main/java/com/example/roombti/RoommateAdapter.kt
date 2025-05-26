package com.example.roombti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roombti.databinding.ItemRoommateBinding

class RoommateAdapter(
    private val userList: List<UserData>, 
    private val onItemClick: (UserData) -> Unit
) : RecyclerView.Adapter<RoommateAdapter.RoommateViewHolder>() {

    class RoommateViewHolder(private val binding: ItemRoommateBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(user: UserData, onItemClick: (UserData) -> Unit) {
            binding.textViewName.text = user.name ?: "-"
            binding.textViewMbti.text = user.mbti ?: "-"
            
            // Arka plan rengi/drawable (MBTI'ye göre)
            when (user.mbti) {
                "ESTJ", "ISFJ", "ISTJ", "ESFJ" -> binding.profileBar.setBackgroundResource(R.drawable.profile_bar_blue)
                "INTP", "ENTJ", "INTJ", "ENTP" -> binding.profileBar.setBackgroundResource(R.drawable.profile_bar_purple)
                "ESTP", "ISFP", "ISTP", "ESFP" -> binding.profileBar.setBackgroundResource(R.drawable.profile_bar_yellow)
                "INFJ", "ENFJ", "INFP", "ENFP" -> binding.profileBar.setBackgroundResource(R.drawable.profile_bar_green)
            }
            
            // Cinsiyet simgesi
            binding.genderIcon.setBackgroundResource(
                if (user.gender == "Female") R.drawable.female_icon else R.drawable.male_icon
            )
            
            // Ok işareti sabit
            binding.arrowIcon.setBackgroundResource(R.drawable.right_arrow)
            
            // Profil fotoğrafı sabit
            binding.photo.setBackgroundResource(R.drawable.bar_photo)
            
            binding.root.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoommateViewHolder {
        val binding = ItemRoommateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoommateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoommateViewHolder, position: Int) {
        holder.bind(userList[position], onItemClick)
    }

    override fun getItemCount() = userList.size
} 
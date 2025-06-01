package com.example.roombti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MatchingAdapter : ListAdapter<MatchResult, MatchingAdapter.MatchViewHolder>(MatchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = getItem(position)
        holder.bind(match)
    }

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.match_name)
        private val scoreTextView: TextView = itemView.findViewById(R.id.match_score)
        private val detailsTextView: TextView = itemView.findViewById(R.id.match_details)

        fun bind(match: MatchResult) {
            val user = match.userData
            nameTextView.text = "${user.name} ${user.surname}"
            scoreTextView.text = "Match Score: ${(match.score * 100).toInt()}%"
            
            val details = buildString {
                append("MBTI: ${user.mbti ?: "Not specified"}\n")
                append("Location: ${user.location ?: "Not specified"}\n")
                if (user.userType == "homeseeker") {
                    append("Max Budget: ${user.maxBudget ?: "Not specified"}\n")
                    append("Min Budget: ${user.minBudget ?: "Not specified"}\n")
                } else {
                    append("Rent per Person: ${user.rentPerPerson ?: "Not specified"}\n")
                    append("Current Housemates: ${user.currentHousemates ?: 0}/${user.totalHousemates ?: 0}\n")
                }
                append("Smoking: ${if (user.allowSmoking == true) "Allowed" else "Not Allowed"}\n")
                append("Pets: ${if (user.allowPets == true) "Allowed" else "Not Allowed"}")
            }
            detailsTextView.text = details
        }
    }

    private class MatchDiffCallback : DiffUtil.ItemCallback<MatchResult>() {
        override fun areItemsTheSame(oldItem: MatchResult, newItem: MatchResult): Boolean {
            return oldItem.userData.id == newItem.userData.id
        }

        override fun areContentsTheSame(oldItem: MatchResult, newItem: MatchResult): Boolean {
            return oldItem == newItem
        }
    }
} 
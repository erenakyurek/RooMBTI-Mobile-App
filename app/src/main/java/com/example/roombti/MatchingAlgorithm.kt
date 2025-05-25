package com.example.roombti

import android.content.Context
import java.io.BufferedReader
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class MatchResult(val score: Float, val userData: UserData)

class MatchingAlgorithm {
    companion object {
        // Ağırlıklar
        private const val MBTI_WEIGHT = 0.3f
        private const val LOCATION_WEIGHT = 0.2f
        private const val BUDGET_WEIGHT = 0.15f
        private const val SMOKING_WEIGHT = 0.1f
        private const val PET_WEIGHT = 0.1f
        private const val GENDER_WEIGHT = 0.1f
        private const val CAPACITY_WEIGHT = 0.05f

        // MBTI uyumluluk matrisi
        private var mbtiCompatibility: Map<String, Map<String, Float>> = mapOf()

        fun initializeMBTIMatrix(context: Context) {
            val compatibilityMap = mutableMapOf<String, MutableMap<String, Float>>()            
            try {
                context.assets.open("mbti.csv").bufferedReader().use { reader ->
                    reader.forEachLine { line ->
                        val (type1, type2, score) = line.split(",")
                        val normalizedScore = score.toFloat() / 5f // 5 üzerinden puanı 0-1 aralığına normalize et
                        compatibilityMap.getOrPut(type1) { mutableMapOf() }[type2] = normalizedScore
                    }
                }
                mbtiCompatibility = compatibilityMap
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        suspend fun getMatchingUsers(currentUserId: String, context: Context): List<MatchResult> = withContext(Dispatchers.IO) {
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.reference.child("user")
            
            // Get current user's data
            val currentUserSnapshot = usersRef.child(currentUserId).get().await()
            val currentUser = currentUserSnapshot.getValue(UserData::class.java)
            
            if (currentUser == null) {
                return@withContext emptyList()
            }

            // Get all users and filter by type in memory
            val allUsersSnapshot = usersRef.get().await()
            val targetUserType = if (currentUser.userType == "houserenter") "homeseeker" else "houserenter"
            
            val matchResults = mutableListOf<MatchResult>()
            
            for (userSnapshot in allUsersSnapshot.children) {
                val potentialMatch = userSnapshot.getValue(UserData::class.java)
                if (potentialMatch != null && potentialMatch.userType == targetUserType) {
                    val score = if (currentUser.userType == "houserenter") {
                        calculateMatchScore(potentialMatch, currentUser)
                    } else {
                        calculateMatchScore(currentUser, potentialMatch)
                    }
                    matchResults.add(MatchResult(score, potentialMatch))
                }
            }
            
            // Sort by score in descending order
            matchResults.sortedByDescending { it.score }
        }

        fun calculateMatchScore(
            seeker: UserData, // userType = "homeseeker"
            lister: UserData, // userType = "houserenter"
            preferredGender: String? = null
        ): Float {
            var totalScore = 0f

            // MBTI uyumluluğu
            val mbtiScore = calculateMBTIScore(seeker.mbti ?: "", lister.mbti ?: "")
            totalScore += mbtiScore * MBTI_WEIGHT

            // Konum eşleşmesi
            val locationScore = if (seeker.location == lister.location) 1f else 0f
            totalScore += locationScore * LOCATION_WEIGHT

            // Bütçe uyumu
            if (lister.rentPerPerson != null && seeker.maxBudget != null) {
                val budgetScore = calculateBudgetScore(lister.rentPerPerson, seeker.maxBudget)
                totalScore += budgetScore * BUDGET_WEIGHT
            }

            // Sigara kullanımı uyumu
            val smokingScore = if (seeker.allowSmoking == lister.allowSmoking) 1f else 0f
            totalScore += smokingScore * SMOKING_WEIGHT

            // Evcil hayvan uyumu
            val petScore = if (seeker.allowPets == lister.allowPets) 1f else 0f
            totalScore += petScore * PET_WEIGHT

            // Cinsiyet uyumu
            if (preferredGender != null) {
                val genderScore = if (lister.gender == preferredGender) 1f else 0f
                totalScore += genderScore * GENDER_WEIGHT
            }

            // Kapasite uyumu
            if (lister.totalHousemates != null && lister.currentHousemates != null) {
                val capacityScore = calculateCapacityScore(lister.totalHousemates, lister.currentHousemates)
                totalScore += capacityScore * CAPACITY_WEIGHT
            }

            return totalScore
        }

        private fun calculateMBTIScore(mbti1: String, mbti2: String): Float {
            return mbtiCompatibility[mbti1]?.get(mbti2) ?: 0.5f
        }

        private fun calculateBudgetScore(price: Int, maxBudget: Int): Float {
            return if (price <= maxBudget) {
                1f - (price.toFloat() / maxBudget.toFloat())
            } else {
                0f
            }
        }

        private fun calculateCapacityScore(capacity: Int, currentCount: Int): Float {
            return if (currentCount < capacity) {
                1f - (currentCount.toFloat() / capacity.toFloat())
            } else {
                0f
            }
        }
    }
}
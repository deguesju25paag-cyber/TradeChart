package com.example.tradechart.domain.repository

import com.example.tradechart.domain.model.User

interface UserRepository {
    suspend fun registerUser(user: User)
    suspend fun login(username: String, passwordHash: String): User?
    suspend fun updateUser(user: User)
}

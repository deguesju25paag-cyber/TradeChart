package com.example.tradechart.data.repository

import com.example.tradechart.data.local.UserDao
import com.example.tradechart.domain.model.User
import com.example.tradechart.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun login(username: String, passwordHash: String): User? {
        val user = userDao.getUserByUsername(username)
        return if (user?.passwordHash == passwordHash) user else null
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}

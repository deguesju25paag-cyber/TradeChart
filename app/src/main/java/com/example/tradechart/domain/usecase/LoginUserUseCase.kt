package com.example.tradechart.domain.usecase

import com.example.tradechart.domain.model.User
import com.example.tradechart.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, passwordHash: String): User? {
        return userRepository.login(username, passwordHash)
    }
}

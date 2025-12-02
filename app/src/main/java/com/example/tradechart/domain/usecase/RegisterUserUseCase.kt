package com.example.tradechart.domain.usecase

import com.example.tradechart.domain.model.User
import com.example.tradechart.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.registerUser(user)
    }
}

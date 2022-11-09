package com.example.demo.domain

import com.example.demo.data.UserEntity
import com.example.demo.data.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun create(name: String, age: Int, gender: Gender): User {
        return this.userRepository.save(
            UserEntity(
                name = name,
                age = age,
                gender = gender
            )
        ).toModel()
    }
}

data class User(
    val name: String,
    val age: Int,
    val gender: Gender
)

private fun UserEntity.toModel() = User(
    this.name,
    this.age,
    this.gender
)

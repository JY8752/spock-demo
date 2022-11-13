package com.example.demo.application

import com.example.demo.domain.Gender
import com.example.demo.domain.User
import com.example.demo.domain.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("/user")
    fun create(@RequestBody request: CreateUserRequest): User {
        return this.userService.create(request.name, request.age, request.gender)
    }
}

data class CreateUserRequest(val name: String, val age: Int, val gender: Gender)
package com.example.demo.data

import com.example.demo.domain.Gender
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserEntity(
    @Id val id: Long? = null,
    val name: String,
    val age: Int,
    val gender: Gender
)

package com.example.demo.repository

import com.example.demo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByUsernameAndPassword(username: String, password: String): User?
    override fun findById(id: Long): Optional<User>
    override fun findAll(): List<User>
    fun save(user: User): User
    override fun deleteById(id: Long)
}

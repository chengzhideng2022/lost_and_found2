package com.example.demo.service

import com.example.demo.entity.LostItem
import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import org.hibernate.Hibernate
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun register(user: User): User {
        if (userRepository.findByUsername(user.username) != null) {
            throw IllegalArgumentException("A user with the same username already exists")
        }
        return userRepository.save(user)
    }

    fun login(username: String, password: String): User {
        val user = userRepository.findByUsername(username)
        if ((user == null) || (user.password != password)) {
            throw IllegalArgumentException("Invalid username or password")
        }
        return user
    }

    fun getUserByUsername(username: String): User {
        val user = userRepository.findByUsername(username) ?: throw IllegalArgumentException("User not found")
        Hibernate.initialize(user.lostItems)
        return user
    }

    fun getLostItemsByUsername(username: String): List<LostItem> {
        val user = userRepository.findByUsername(username)
        return user?.lostItems ?: emptyList()
    }

    fun updateUserInfo(username: String, email: String) {
        val user = userRepository.findByUsername(username) ?: throw IllegalArgumentException("User not found")
        user.email = email
        userRepository.save(user)
    }

    // Add other methods as needed
}


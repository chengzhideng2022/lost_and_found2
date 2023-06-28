package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@CrossOrigin(origins = ["http://localhost:8081"], allowCredentials = "true")
@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    data class LoginRequest(val username: String, val password: String)

    @PostMapping("/api/login")
    fun login(@RequestBody request: LoginRequest, session: HttpSession): ResponseEntity<Any> {
        return try {
            val user = userService.login(request.username, request.password)
            println("User ${user.username} logged in")  // Log the username of the logged in user
            session.setAttribute("username", user.username)  // Save the username in the session
            val testUsername = session.getAttribute("username")  // Try to get the username from the session
            println("Test username: $testUsername")  // Log the test username
            println("Session ID during login: ${session.id}")  // Log the session ID during login
            ResponseEntity.ok(user)
        } catch (e: Exception) {
            println("Login failed: ${e.message}")  // Log the error message
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to e.message))
        }
    }

    @GetMapping("/api/me")
    fun getCurrentUser(session: HttpSession): ResponseEntity<User> {
        val username = session.getAttribute("username")
        println("Getting current user: $username")  // Log the username of the current user
        println("Session ID when getting current user: ${session.id}")  // Log the session ID when getting current user
        if (username is String) {
            val user = userService.getUserByUsername(username)
            if (user != null) {
                return ResponseEntity(user, HttpStatus.OK)
            }
        }
        println("No current user")  // Log when there is no current user
        return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }



    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(): String {
        return "redirect:/error"
    }

    @PostMapping("/api/register")
    fun register(@RequestBody user: User): ResponseEntity<Any> {
        return try {
            val newUser = userService.register(user)
            ResponseEntity(newUser, HttpStatus.CREATED)
        } catch (e: IllegalArgumentException) {
            val error = mapOf("error" to "Username is already in use")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }
    // Add other methods as needed
}


package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import com.example.demo.service.LostItemService
import com.example.demo.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
@CrossOrigin(origins = ["http://localhost:8081"], allowCredentials = "true")
@RestController
@RequestMapping("/users")
class UserViewController(private val userRepository: UserRepository, private val userService: UserService, private val lostItemService: LostItemService) {

    @GetMapping("/login")
    fun showLoginForm(): String {
        return "login"
    }

    @PostMapping("/login")
    fun login(@RequestParam username: String, @RequestParam password: String): ResponseEntity<User> {
        try {
            val user = userService.login(username, password)
            return ResponseEntity(user, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }


    @GetMapping("/user-home")
    fun showUserHome(model: Model, request: HttpServletRequest): String {
        val username = request.session.getAttribute("username")
        if (username !is String) {
            return "redirect:/users/login"
        }
        val user = userService.getUserByUsername(username)
        val items = userService.getLostItemsByUsername(username)
        model.addAttribute("user", user)
        model.addAttribute("items", items)
        return "user-home"
    }

    @GetMapping("/error")
    fun showErrorPage(req: HttpServletRequest, model: Model): String {
        val exception = req.getAttribute("exception") as Exception
        println("Error message: ${exception.message}") // 添加这一行
        model.addAttribute("errorMessage", exception.message)
        return "error"
    }


    @GetMapping("/showRegister")
    fun showRegistrationForm(): String {
        return "register"
    }

    @PostMapping("/register")
    fun register(@RequestParam username: String, @RequestParam password: String, @RequestParam email: String, redirectAttributes: RedirectAttributes): String {
        val user = User(username = username, password = password, email = email)
        val existingUser: User? = userRepository.findByUsername(username)
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "A user with the same username already exists")
            return "redirect:/error"
        }
        userService.register(user)
        return "redirect:/users/login"
    }


    // Add other methods as needed
}


package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
@CrossOrigin(origins = ["http://localhost:8081"], allowCredentials = "true")
@Controller
class VueController {
    @RequestMapping("/", "/users/login", "/users/showRegister", "/lostitems/post")
    fun index(): String {
        return "forward:/index.html"
    }
}

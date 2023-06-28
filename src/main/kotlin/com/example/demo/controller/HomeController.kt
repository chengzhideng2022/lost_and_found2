package com.example.demo.controller

import com.example.demo.entity.LostItem
import com.example.demo.service.LostItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8081"], allowCredentials = "true")
@RestController
@RequestMapping("/api")
class HomeController(private val lostItemService: LostItemService) {

    @GetMapping("/")
    fun index(): List<LostItem> {
        return lostItemService.getAllLostItems()
    }

    @GetMapping("/search/title")
    fun search(@RequestParam title: String): List<LostItem> {
        return lostItemService.searchByTitle(title)
    }
}


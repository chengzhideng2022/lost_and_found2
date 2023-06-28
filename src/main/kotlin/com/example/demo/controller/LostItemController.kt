package com.example.demo.controller

import com.example.demo.entity.LostItem
import com.example.demo.repository.LostItemRepository
import com.example.demo.service.LostItemService
import com.example.demo.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@CrossOrigin(origins = ["http://localhost:8081"], allowCredentials = "true")
@RestController
@RequestMapping("/lostitems")
class LostItemController(private val lostItemService: LostItemService,private val userService: UserService) {

    @Autowired
    private val lostItemRepository: LostItemRepository? = null

    @GetMapping("/api/{id}")
    fun getLostItem(@PathVariable id: Long): ResponseEntity<LostItem> {
        val item = lostItemService.getLostItemById(id)
        return ResponseEntity(item, HttpStatus.OK)
    }

    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨执行
    fun deleteOldLostItems() {
        val sevenDaysAgo = LocalDateTime.now().minusDays(7)
        lostItemRepository?.deleteByDateTimeBefore(sevenDaysAgo)
    }

    @GetMapping("/api/me")
    fun getMyLostItems(session: HttpSession): ResponseEntity<List<LostItem>> {
        val username = session.getAttribute("username")
        if (username is String) {
            val user = userService.getUserByUsername(username)
            val myLostItems = lostItemService.getLostItemsByUser(user.id)
            return ResponseEntity(myLostItems, HttpStatus.OK)
        }
        // Handle the case where there is no current user
        return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }


    @GetMapping("/{id}")
    fun viewLostItem(@PathVariable id: Long): ResponseEntity<LostItem> {
        val lostItem = lostItemService.getLostItem(id)
        return ResponseEntity(lostItem, HttpStatus.OK)
    }

    @PostMapping
    fun postLostItem(@RequestParam title: String, @RequestParam description: String, @RequestParam location: String, @RequestParam dateTime: LocalDateTime, session: HttpSession): ResponseEntity<LostItem> {
        val username = session.getAttribute("username") as? String
        val user = if (username != null) userService.getUserByUsername(username) else null
        if (user != null) {
            val lostItem = LostItem(title = title, description = description, location = location, dateTime = dateTime, user = user)
            val postedItem = lostItemService.postLostItem(lostItem)
            return ResponseEntity(postedItem, HttpStatus.CREATED)
        }
        return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }




    @GetMapping
    fun getAllLostItems(): ResponseEntity<List<LostItem>> {
        val lostItems = lostItemService.getAllLostItems()
        return ResponseEntity(lostItems, HttpStatus.OK)
    }

    @DeleteMapping("/api/{id}")
    fun deleteLostItem(@PathVariable id: Long): ResponseEntity<Unit> {
        lostItemService.deleteLostItem(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PutMapping("/{id}")
    fun updateLostItem(@PathVariable id: Long, @RequestBody updatedLostItem: LostItem): ResponseEntity<LostItem> {
        val updatedItem = lostItemService.updateLostItem(id, updatedLostItem)
        return ResponseEntity(updatedItem, HttpStatus.OK)
    }

    // Add other methods as needed
}

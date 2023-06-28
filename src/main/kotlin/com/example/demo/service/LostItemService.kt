package com.example.demo.service

import com.example.demo.entity.LostItem
import com.example.demo.entity.User
import com.example.demo.repository.LostItemRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LostItemService(private val lostItemRepository: LostItemRepository) {
    fun getLostItemById(id: Long): LostItem {
        return lostItemRepository.findById(id).orElseThrow { NoSuchElementException("No lost item found with ID $id") }
    }


    fun searchByTitle(title: String): List<LostItem> {
        return lostItemRepository.findByTitleContaining(title)
    }


    fun postLostItem(lostItem: LostItem): LostItem {
        return lostItemRepository.save(lostItem)
    }

    fun getAllLostItems(): List<LostItem> {
        return lostItemRepository.findAll()
    }

    fun getLostItemsByUser(userId: Long): List<LostItem> {
        return lostItemRepository.findByUserId(userId)
    }

    fun findByUserId(user: Long): List<LostItem> {
        return lostItemRepository.findByUserId(user)
    }

    fun deleteLostItem(id: Long) {
        lostItemRepository.deleteById(id)
    }
    fun getLostItem(id: Long): LostItem {
        return lostItemRepository.findById(id).orElseThrow {
            NoSuchElementException("No LostItem found with id $id")
        }
    }

    fun updateLostItem(id: Long, updatedLostItem: LostItem): LostItem {
        val existingItem = lostItemRepository.findById(id).orElseThrow { throw ChangeSetPersister.NotFoundException() }

    existingItem.title = updatedLostItem.title
    existingItem.description = updatedLostItem.description
    existingItem.location = updatedLostItem.location
    existingItem.dateTime = updatedLostItem.dateTime

    return lostItemRepository.save(existingItem)
}

@Scheduled(cron = "0 0 0 * * ?") // 每天凌晨执行一次
fun cleanupOldLostItems() {
    val currentDate = LocalDateTime.now()
    val oldItems = lostItemRepository.findByDateTimeBefore(currentDate.minusDays(7))
    oldItems.forEach { lostItemRepository.delete(it) }
}

    // Add other methods as needed
}

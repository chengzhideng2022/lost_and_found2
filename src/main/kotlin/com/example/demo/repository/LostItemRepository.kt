package com.example.demo.repository

import com.example.demo.entity.LostItem
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface LostItemRepository : JpaRepository<LostItem, Long> {
    fun findByUserId(userId: Long): List<LostItem>
    fun findByDateTimeBefore(dateTime: LocalDateTime): List<LostItem>
    fun findByTitleContaining(title: String): List<LostItem> // 新增的方法
    fun deleteByDateTimeBefore(dateTime: LocalDateTime?)
}


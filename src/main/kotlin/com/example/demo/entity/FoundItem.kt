package com.example.demo.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class FoundItem(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lost_item_id", nullable = false)
    val lostItem: LostItem,

    @Column(nullable = false)
    var foundTime: LocalDateTime,

    @Column(nullable = false)
    var foundLocation: String
)

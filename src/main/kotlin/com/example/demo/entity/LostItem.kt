package com.example.demo.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Entity
data class LostItem(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var location: String,

    @Column(nullable = false)
    var dateTime: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    val user: User
)
{
    fun getDateTimeAsDate(): Date {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
    }

    override fun toString(): String {
        return "LostItem(id=$id, title='$title', description='$description', location='$location', dateTime=$dateTime)"
    }
}

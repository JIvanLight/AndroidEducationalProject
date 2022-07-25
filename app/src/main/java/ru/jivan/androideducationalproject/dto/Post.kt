package ru.jivan.androideducationalproject.dto

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    val share: Int,
    val likes: Int = 0,
    val likedByMe: Boolean = false
)

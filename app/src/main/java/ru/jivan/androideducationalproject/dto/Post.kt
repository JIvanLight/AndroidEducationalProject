package ru.jivan.androideducationalproject.dto

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    val share: Int = 0,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val linkVideo: String? = null
)
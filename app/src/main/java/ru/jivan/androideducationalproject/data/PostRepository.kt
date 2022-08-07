package ru.jivan.androideducationalproject.data

import androidx.lifecycle.LiveData
import ru.jivan.androideducationalproject.dto.Post

interface PostRepository {

    val data: LiveData<List<Post>>

    fun like(postId: Int)

    fun share(postId: Int)
}
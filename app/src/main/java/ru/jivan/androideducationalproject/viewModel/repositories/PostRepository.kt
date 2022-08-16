package ru.jivan.androideducationalproject.viewModel.repositories

import androidx.lifecycle.LiveData
import ru.jivan.androideducationalproject.dto.Post

interface PostRepository {

    val data: LiveData<List<Post>>

    fun like(postId: Int)

    fun share(postId: Int)

    fun remove(postId: Int)

    fun save(post: Post)

    companion object {
        const val NEW_POST_ID = 0
    }
}
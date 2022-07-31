package ru.jivan.androideducationalproject.data

import androidx.lifecycle.LiveData
import ru.jivan.androideducationalproject.dto.Post

interface PostRepository {

    val data: LiveData<Post>

    fun like()

    fun share()
}
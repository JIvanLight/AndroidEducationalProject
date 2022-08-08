package ru.jivan.androideducationalproject.viewModel

import androidx.lifecycle.ViewModel
import ru.jivan.androideducationalproject.data.InMemoryPostRepository
import ru.jivan.androideducationalproject.data.PostRepository
import ru.jivan.androideducationalproject.dto.Post

class PostViewModel : ViewModel() {

    val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeCliked(post: Post) = repository.like(post.id)

    fun onShareCliked(post: Post) = repository.share(post.id)
}
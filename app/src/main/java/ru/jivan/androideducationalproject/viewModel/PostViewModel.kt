package ru.jivan.androideducationalproject.viewModel

import androidx.lifecycle.ViewModel
import ru.jivan.androideducationalproject.data.InMemoryPostRepository
import ru.jivan.androideducationalproject.data.PostRepository
import ru.jivan.androideducationalproject.dto.Post

class PostViewModel : ViewModel(), PostInteractiveListener {

    val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    override fun onLikeCliked(post: Post) = repository.like(post.id)

    override fun onShareCliked(post: Post) = repository.share(post.id)

    override fun onRemoveClicked(post: Post) = repository.remove(post.id)
}
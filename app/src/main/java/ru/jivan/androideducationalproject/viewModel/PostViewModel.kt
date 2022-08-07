package ru.jivan.androideducationalproject.viewModel

import androidx.lifecycle.ViewModel
import ru.jivan.androideducationalproject.data.InMemoryPostRepository
import ru.jivan.androideducationalproject.data.PostRepository

class PostViewModel : ViewModel() {

    val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeCliked() = repository.like()

    fun onShareCliked() = repository.share()
}
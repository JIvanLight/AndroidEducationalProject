package ru.jivan.androideducationalproject.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.jivan.androideducationalproject.MainActivity
import ru.jivan.androideducationalproject.viewModel.repositories.InMemoryPostRepository
import ru.jivan.androideducationalproject.viewModel.repositories.PostRepository
import ru.jivan.androideducationalproject.dto.Post

class PostViewModel : ViewModel(), PostInteractiveListener {

    val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    val flagFocusEditText = MutableLiveData<Boolean>(false)

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) = repository.share(post.id)

    override fun onRemoveClicked(post: Post) = repository.remove(post.id)

    override fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(content = content) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today",
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }
}
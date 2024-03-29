package ru.jivan.androideducationalproject.viewModel

import android.app.Application
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.jivan.androideducationalproject.viewModel.repositories.InMemoryPostRepository
import ru.jivan.androideducationalproject.viewModel.repositories.PostRepository
import ru.jivan.androideducationalproject.dto.Post
import ru.jivan.androideducationalproject.utill.Intents
import ru.jivan.androideducationalproject.utill.Keys
import ru.jivan.androideducationalproject.utill.SingleLiveEvent
import ru.jivan.androideducationalproject.viewModel.repositories.FilePostRepository

class PostViewModel(application: Application) : AndroidViewModel(application), PostInteractiveListener {

    val repository: PostRepository = FilePostRepository(application)

    val data by repository::data

    val navigateToActionPostScreenEvent = SingleLiveEvent<Bundle>()

    val navigateToPlayLinkVideoEvent = SingleLiveEvent<String?>()

    val currentPost = MutableLiveData<Post?>(null)

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) = repository.share(post.id)

    override fun onRemoveClicked(post: Post) = repository.remove(post.id)

    override fun onSaveButtonClicked(content: Bundle) {

        val action = content.getString(Keys.ACTION_KEY)
        val postContent = content.getString(Keys.CONTENT_KEY)

        if (postContent.isNullOrBlank()) return

        if (action == Intents.ACTION_EDIT_POST){
            val post = checkNotNull(currentPost.value?.copy(content = postContent))
            repository.save(post)
        } else {
            val post = Post(
                id = PostRepository.NEW_POST_ID,
                author = "Me",
                content = postContent,
                published = "Today",
            )
            repository.save(post)
        }
        currentPost.value = null
    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        val postContent = post.content
        val bundle = bundleOf()
        bundle.putString(Keys.CONTENT_KEY, postContent)
        bundle.putString(Keys.ACTION_KEY, Intents.ACTION_EDIT_POST)
        navigateToActionPostScreenEvent.value = bundle
    }

    override fun onPlayVideoClicked(post: Post) {
        navigateToPlayLinkVideoEvent.value = post.linkVideo
    }
}
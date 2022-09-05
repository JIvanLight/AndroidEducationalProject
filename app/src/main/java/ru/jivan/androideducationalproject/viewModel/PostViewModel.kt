package ru.jivan.androideducationalproject.viewModel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.jivan.androideducationalproject.PostContentActionsFragment.Companion.actionArg
import ru.jivan.androideducationalproject.PostContentActionsFragment.Companion.textArg
import ru.jivan.androideducationalproject.dto.Post
import ru.jivan.androideducationalproject.utill.Actions
import ru.jivan.androideducationalproject.utill.SingleLiveEvent
import ru.jivan.androideducationalproject.viewModel.repositories.FilePostRepository
import ru.jivan.androideducationalproject.viewModel.repositories.PostRepository

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractiveListener {

    private val repository: PostRepository = FilePostRepository(application)

    val data by repository::data

    val navigateToActionPostScreenEvent = SingleLiveEvent<Bundle>()

    val navigateToPlayLinkVideoEvent = SingleLiveEvent<String?>()

    val navigateToPostScreenEvent = SingleLiveEvent<Int>()

    internal val currentPost = MutableLiveData<Post?>(null)

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.share(post.id)
        val bundle = Bundle().apply {
            textArg = post.content
            actionArg = Actions.ACTION_SHARE_POST
        }
        navigateToActionPostScreenEvent.value = bundle
    }

    override fun onRemoveClicked(post: Post) = repository.remove(post.id)

    override fun onSaveButtonClicked(content: Bundle) {

        val postContent = content.textArg
        val action = content.actionArg

        if (postContent.isNullOrBlank()) return

        if (action == Actions.ACTION_EDIT_POST) {
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
        val bundle = Bundle().apply {
            textArg = post.content
            actionArg = Actions.ACTION_EDIT_POST
        }
        navigateToActionPostScreenEvent.value = bundle
    }

    override fun onPlayVideoClicked(post: Post) {
        navigateToPlayLinkVideoEvent.value = post.linkVideo
    }

    override fun onSelectPost(post: Post) {
        currentPost.value = post
        navigateToPostScreenEvent.value = post.id

    }
}
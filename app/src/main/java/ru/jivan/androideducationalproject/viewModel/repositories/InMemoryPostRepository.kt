package ru.jivan.androideducationalproject.viewModel.repositories

import androidx.lifecycle.MutableLiveData
import ru.jivan.androideducationalproject.dto.Post

class InMemoryPostRepository : PostRepository {

    private var nextId = 10

    override val data = MutableLiveData(
        List(10) {
            Post(
                id = 1 + it,
                author = "Иван",
                content = "Здесь могла быть Ваша РЕКЛАМА! №${1 + it}",
                published = "6 июня 2022 в 12:30",
                likes = 1_999_999,
                share = 990
            )
        }
    )

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override fun like(postId: Int) {
        data.value = posts.map {
            if (it.id == postId) {
                if (!it.likedByMe) {
                    val increasedLikes = it.likes + 1
                    val newlikedByMe = !it.likedByMe
                    it.copy(likes = increasedLikes, likedByMe = newlikedByMe)
                } else {
                    val decreasedLikes = it.likes - 1
                    val newlikedByMe = !it.likedByMe
                    it.copy(likes = decreasedLikes, likedByMe = newlikedByMe)
                }
            } else it
        }
    }

    override fun share(postId: Int) {
        data.value = posts.map {
            if (it.id == postId) {
                val increaseShare = it.share + 1
                it.copy(share = increaseShare)
            } else it
        }
    }

    override fun remove(postId: Int) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (post.id == it.id) post else it
        }
    }
}
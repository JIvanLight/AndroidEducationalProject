package ru.jivan.androideducationalproject.viewModel.repositories

import androidx.lifecycle.MutableLiveData
import ru.jivan.androideducationalproject.dto.Post
import java.util.*

class InMemoryPostRepository : PostRepository {

    private var nextId = 10

    override val data = MutableLiveData(
        (mutableListOf(Post(
            id = 1,
            author = "Иван",
            content = "Здесь могла быть Ваша РЕКЛАМА! №1",
            published = "6 июня 2022 в 12:30",
            likes = 1_999_999,
            share = 990,
            linkVideo = "https://www.youtube.com/watch?v=uN6lwGwn9aw&list=PLxAu73S7-QXCNbYUCR-tlDGeeQC1V1UCd&index=6"
        )) + (MutableList(10) {
            Post(
                id = 2 + it,
                author = "Иван",
                content = "Здесь могла быть Ваша РЕКЛАМА! №${2 + it}",
                published = "6 июня 2022 в 12:30",
                likes = 1_999_999,
                share = 990
            )
        })).toMutableList()
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
        }.toMutableList()
    }

    override fun share(postId: Int) {
        data.value = posts.map {
            if (it.id == postId) {
                val increaseShare = it.share + 1
                it.copy(share = increaseShare)
            } else it
        }.toMutableList()
    }

    override fun remove(postId: Int) {
        data.value = posts.filter { it.id != postId }.toMutableList()
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = (listOf(
            post.copy(id = ++nextId)
        ) + posts).toMutableList()
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (post.id == it.id) post else it
        }.toMutableList()
    }
}
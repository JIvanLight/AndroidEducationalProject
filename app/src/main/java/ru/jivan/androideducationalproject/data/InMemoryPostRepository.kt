package ru.jivan.androideducationalproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jivan.androideducationalproject.dto.Post

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        List(10) {
            Post(
                id = it,
                author = "Иван",
                content = "Здесь могла быть Ваша РЕКЛАМА! №$it",
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
}
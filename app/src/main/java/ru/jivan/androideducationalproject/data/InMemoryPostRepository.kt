package ru.jivan.androideducationalproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.jivan.androideducationalproject.dto.Post

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        Post(
            id = 0,
            author = "Иван",
            content = "Здесь могла быть Ваша РЕКЛАМА!",
            published = "6 июня 2022 в 12:30",
            likes = 1_999_999,
            share = 990
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        if (!currentPost.likedByMe) {
            val increasedLikes = currentPost.likes + 1
            val newlikedByMe = !currentPost.likedByMe
            data.value = currentPost.copy(likes = increasedLikes, likedByMe = newlikedByMe)
        } else {
            val decreasedLikes = currentPost.likes - 1
            val newlikedByMe = !currentPost.likedByMe
            data.value = currentPost.copy(likes = decreasedLikes, likedByMe = newlikedByMe)
        }
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val increaseShare = currentPost.share + 1
        data.value = currentPost.copy(share = increaseShare)
    }
}
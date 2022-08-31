package ru.jivan.androideducationalproject.viewModel.repositories

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.jivan.androideducationalproject.dto.Post
import kotlin.properties.Delegates

class FilePostRepository(
    val application: Application
): PostRepository {

    private val gson = Gson()

    val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private val prefs = application.getSharedPreferences(
        DIR_SHARED_PREF_POST_REPOSITORY, Context.MODE_PRIVATE
    )

    private var nextId by Delegates.observable(
        prefs.getInt(NEXT_ID_PFEFS_KEY, 0)
    ) { _, _, newValue ->
        prefs.edit {
            putInt(NEXT_ID_PFEFS_KEY, newValue)
        }
    }

    override val data: MutableLiveData <MutableList<Post>>

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME_POST_REPOSITORY)

        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME_POST_REPOSITORY)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()

        data = MutableLiveData(posts.toMutableList())
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value){
            application.openFileOutput(FILE_NAME_POST_REPOSITORY, Context.MODE_PRIVATE).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }


    override fun like(postId: Int) {
        posts = posts.map {
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
        posts = posts.map {
            if (it.id == postId) {
                val increaseShare = it.share + 1
                it.copy(share = increaseShare)
            } else it
        }.toMutableList()
    }

    override fun remove(postId: Int) {
        posts = posts.filter { it.id != postId }.toMutableList()
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        posts = (listOf(
            post.copy(id = ++nextId)
        ) + posts).toMutableList()
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (post.id == it.id) post else it
        }.toMutableList()
    }

    companion object {
        private const val DIR_SHARED_PREF_POST_REPOSITORY = "SharedPrefPostRepository"
        private const val NEXT_ID_PFEFS_KEY = "nextId"
        private const val FILE_NAME_POST_REPOSITORY = "FilePostRepository"
    }
}
package ru.jivan.androideducationalproject.viewModel

import android.os.Bundle
import ru.jivan.androideducationalproject.dto.Post

interface PostInteractiveListener {

    fun onLikeClicked(post: Post)

    fun onShareClicked(post: Post)

    fun onRemoveClicked(post: Post)

    fun onSaveButtonClicked(content: Bundle)

    fun onEditClicked(post: Post)
}
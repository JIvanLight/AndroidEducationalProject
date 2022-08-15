package ru.jivan.androideducationalproject.viewModel

import ru.jivan.androideducationalproject.dto.Post

interface PostInteractiveListener {

    fun onLikeClicked(post: Post)

    fun onShareClicked(post: Post)

    fun onRemoveClicked(post: Post)

    fun onSaveButtonClicked(content: String)

    fun onEditClicked(post: Post)
}
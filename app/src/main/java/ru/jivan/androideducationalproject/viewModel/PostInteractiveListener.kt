package ru.jivan.androideducationalproject.viewModel

import ru.jivan.androideducationalproject.dto.Post

interface PostInteractiveListener {

    fun onLikeCliked(post: Post)

    fun onShareCliked(post: Post)

    fun onRemoveClicked(post: Post)
}
package ru.jivan.androideducationalproject.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.jivan.androideducationalproject.R
import ru.jivan.androideducationalproject.databinding.PostBinding
import ru.jivan.androideducationalproject.dto.Post

internal class PostAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: PostBinding) : RecyclerView.ViewHolder(binding.root) {

        lateinit var post: Post

        init {
            binding.likes.setOnClickListener { onLikeClicked(post) }
            binding.share.setOnClickListener { onShareClicked(post) }
        }

        fun bind(post: Post) {

            this.post = post

            with(binding) {
                author.text = post.author
                content.text = post.content
                date.text = post.published
                shareValue.text = convertThousandsToText(post.share)
                likeValue.text = convertThousandsToText(post.likes)
                likes.setImageResource(getLikeIconResId(post.likedByMe))
            }
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_liked_24 else R.drawable.ic_likes_24dp

        private fun convertThousandsToText(likes: Int): String {
            when {
                likes in 1000..9999 -> {
                    val thousands = (likes / 1000.0).toInt()
                    val hundreds = (((likes / 1000.0) - thousands) * 10).toInt()
                    return if (hundreds == 0) "${thousands}K" else "$thousands.${hundreds}K"

                }
                likes in 10_000..999_999 -> {
                    val thousands = (likes / 1000.0).toInt()
                    return "${thousands}K"
                }
                likes >= 1_000_000 -> {
                    val millions = (likes / 1_000_000.0).toInt()
                    val hundredsOfThousands = (((likes / 1_000_000.0) - millions) * 10).toInt()
                    return if (hundredsOfThousands == 0) "${millions}M" else "$millions.${hundredsOfThousands}M"
                }
                else -> return likes.toString()
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem
    }
}
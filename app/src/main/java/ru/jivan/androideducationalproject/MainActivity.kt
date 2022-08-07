package ru.jivan.androideducationalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.jivan.androideducationalproject.databinding.ActivityMainBinding
import ru.jivan.androideducationalproject.dto.Post
import ru.jivan.androideducationalproject.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.likes.setOnClickListener {
            viewModel.onLikeCliked()
        }

        binding.share.setOnClickListener {
            viewModel.onShareCliked()
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        author.text = post.author
        content.text = post.content
        date.text = post.published
        shareValue.text = convertThousandsToText(post.share)
        likeValue.text = convertThousandsToText(post.likes)
        likes.setImageResource(getLikeIconResId(post.likedByMe))
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
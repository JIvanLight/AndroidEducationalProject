package ru.jivan.androideducationalproject

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.jivan.androideducationalproject.databinding.ActivityMainBinding
import ru.jivan.androideducationalproject.dto.Post


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var post = Post(
            id = 0,
            author = "Иван",
            content = "Здесь могла быть Ваша РЕКЛАМА!",
            published = "6 июня 2022 в 12:30",
            likes = 1_999_999,
            share = 990
        )

        binding.render(post)
        binding.likes.setOnClickListener {
            if (!post.likedByMe) {
                val increasedLikes = post.likes + 1
                val newlikedByMe = !post.likedByMe
                post = post.copy(likes = increasedLikes, likedByMe = newlikedByMe)
                binding.likeValue.text = convertThousandsToText(post.likes)
                binding.likes.setImageResource(R.drawable.ic_liked_24)
            } else {
                val decreasedLikes = post.likes - 1
                val newlikedByMe = !post.likedByMe
                post = post.copy(likes = decreasedLikes, likedByMe = newlikedByMe)
                binding.likeValue.text = convertThousandsToText(post.likes)
                binding.likes.setImageResource(R.drawable.ic_likes_24dp)
            }
        }

        binding.share.setOnClickListener {
            val increaseShare = post.share + 1
            post = post.copy(share = increaseShare)
            binding.shareValue.text = convertThousandsToText(post.share)
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
package ru.jivan.androideducationalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.jivan.androideducationalproject.data.PostAdapter
import ru.jivan.androideducationalproject.databinding.ActivityMainBinding
import ru.jivan.androideducationalproject.dto.Post
import ru.jivan.androideducationalproject.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            viewModel::onLikeCliked,
            viewModel::onShareCliked
        )

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) {
            adapter.submitList(it)
        }
    }
}
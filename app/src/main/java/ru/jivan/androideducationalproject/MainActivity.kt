package ru.jivan.androideducationalproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import ru.jivan.androideducationalproject.viewModel.adapters.PostAdapter
import ru.jivan.androideducationalproject.databinding.ActivityMainBinding
import ru.jivan.androideducationalproject.utill.Intents
import ru.jivan.androideducationalproject.utill.Keys
import ru.jivan.androideducationalproject.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) {
            adapter.submitList(it)
        }

        val postContentActionsActivityLauncher = registerForActivityResult(
            PostContentActionsActivity.ResultContract()
        ) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveButtonClicked(postContent)
        }

        viewModel.navigateToActionPostScreenEvent.observe(this) {
            postContentActionsActivityLauncher.launch(it)
        }

        binding.addFloatingActionButton.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString(Keys.ACTION_KEY, Intents.ACTION_ADD_POST)
            bundle.putString(Keys.CONTENT_KEY, null)
            postContentActionsActivityLauncher.launch(bundle)
        }

        viewModel.navigateToPlayLinkVideoEvent.observe(this) {
            if (it != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                val chooser = Intent.createChooser(intent, getString(R.string.chooser_video))
                startActivity(chooser)
            }
        }
    }
}
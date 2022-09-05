package ru.jivan.androideducationalproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.jivan.androideducationalproject.PostContentActionsFragment.Companion.actionArg
import ru.jivan.androideducationalproject.PostContentActionsFragment.Companion.idArg
import ru.jivan.androideducationalproject.PostContentActionsFragment.Companion.textArg
import ru.jivan.androideducationalproject.databinding.FragmentPostBinding
import ru.jivan.androideducationalproject.dto.Post
import ru.jivan.androideducationalproject.utill.Actions
import ru.jivan.androideducationalproject.viewModel.PostViewModel
import ru.jivan.androideducationalproject.viewModel.adapters.PostAdapter

class PostFragment : Fragment() {

    var post: Post? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPostBinding.inflate(layoutInflater, container, false)

        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )

        val postId = checkNotNull(arguments?.idArg)

        val viewHolder = PostAdapter.ViewHolder(binding.fragmentPost, viewModel)


        viewModel.data.observe(viewLifecycleOwner) {
            post = viewModel.data.value?.find { it.id == postId }
            if (post == null) findNavController().navigateUp()
            else viewHolder.bind(checkNotNull(post) { "Post should not be null" })
        }

        viewModel.navigateToActionPostScreenEvent.observe(viewLifecycleOwner) {
            if (it.actionArg == Actions.ACTION_SHARE_POST) {
                val intent = Intent().apply {
                    setAction(Intent.ACTION_SEND)
                    addCategory(Intent.CATEGORY_DEFAULT)
                    setType("text/plain")
                    putExtra(Intent.EXTRA_TEXT, it.textArg)
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            } else findNavController().navigate(
                R.id.action_postFragment_to_postContentActionsFragment,
                it
            )
        }

        viewModel.navigateToPlayLinkVideoEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                val chooser = Intent.createChooser(intent, getString(R.string.chooser_video))
                startActivity(chooser)
            }
        }

        return binding.root
    }
}
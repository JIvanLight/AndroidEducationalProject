package ru.jivan.androideducationalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.jivan.androideducationalproject.databinding.FragmentPostContentActionBinding
import ru.jivan.androideducationalproject.utill.Actions
import ru.jivan.androideducationalproject.utill.IntArg
import ru.jivan.androideducationalproject.utill.StringArg
import ru.jivan.androideducationalproject.utill.hideKeyboard
import ru.jivan.androideducationalproject.viewModel.PostViewModel

class PostContentActionsFragment : Fragment() {

    internal companion object {
        var Bundle.textArg: String? by StringArg()

        var Bundle.actionArg: String? by StringArg()

        var Bundle.idArg: Int by IntArg()
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPostContentActionBinding.inflate(layoutInflater, container, false)

        when (arguments?.actionArg) {

            Actions.ACTION_EDIT_POST -> {
                arguments?.textArg?.let(binding.editTextEdit::setText)
                binding.editTextEdit.requestFocus()
                binding.okFloatingActionButton.setOnClickListener {
                    val textAfterEdit = binding.editTextEdit.text
                    if (textAfterEdit.isNullOrBlank()) {
                        findNavController().navigateUp()
                    } else {
                        val bundle = Bundle().apply {
                            textArg = textAfterEdit.toString()
                            actionArg = Actions.ACTION_EDIT_POST
                        }
                        viewModel.onSaveButtonClicked(bundle)
                    }
                    binding.editTextEdit.hideKeyboard()
                    findNavController().navigateUp()
                }
            }

            Actions.ACTION_ADD_POST -> {
                binding.editTextEdit.requestFocus()
                binding.okFloatingActionButton.setOnClickListener {
                    val textToAdd = binding.editTextEdit.text
                    if (textToAdd.isNullOrBlank()) {
                        binding.editTextEdit.hideKeyboard()
                        findNavController().navigateUp()
                    } else {
                        val bundle = Bundle().apply {
                            textArg = textToAdd.toString()
                            actionArg = Actions.ACTION_ADD_POST

                        }
                        viewModel.onSaveButtonClicked(bundle)
                    }
                    binding.editTextEdit.hideKeyboard()
                    findNavController().navigateUp()
                }
            }

            Actions.ACTION_SHARE_POST -> {
                arguments?.textArg?.let(binding.editTextEdit::setText)
                binding.editTextEdit.requestFocus()
                binding.okFloatingActionButton.setOnClickListener {
                    val textToAdd = binding.editTextEdit.text
                    if (textToAdd.isNullOrBlank()) {
                        binding.editTextEdit.hideKeyboard()
                        findNavController().navigateUp()
                    } else {
                        val bundle = Bundle().apply {
                            textArg = textToAdd.toString()
                            actionArg = Actions.ACTION_ADD_POST

                        }
                        viewModel.onSaveButtonClicked(bundle)
                    }
                    binding.editTextEdit.hideKeyboard()
                    findNavController().navigateUp()
                }
            }
        }
        return binding.root
    }
}
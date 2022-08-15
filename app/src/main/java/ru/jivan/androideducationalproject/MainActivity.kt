package ru.jivan.androideducationalproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import ru.jivan.androideducationalproject.viewModel.adapters.PostAdapter
import ru.jivan.androideducationalproject.databinding.ActivityMainBinding
import ru.jivan.androideducationalproject.utill.*
import ru.jivan.androideducationalproject.viewModel.PostViewModel
import androidx.constraintlayout.widget.Group


class MainActivity : AppCompatActivity() {
//This is an attempt to defocus (1)
//    private lateinit var _viewModel: PostViewModel
//    override fun onBackPressed() {
//        val flag = checkNotNull(_viewModel.flagFocusEditText.value)
//        if (flag) findViewById<EditText>(R.id.contentEditText).clearFocus()
//        super.onBackPressed()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.contentEditText.requestFocus()

        val viewModel: PostViewModel by viewModels()
//This is an attempt to defocus (2)
        //_viewModel = viewModel
        val adapter = PostAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) {
            adapter.submitList(it)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                if (content != null) {
                    setText(content)
                    requestFocus()
                    showKeyboard()
                    binding.redactEditText.setText(
                        binding.redactEditText.context.getString(
                            R.string.redact_text,
                            content
                        )
                    )
                    binding.group.visibility = Group.VISIBLE
                } else {
                    hideKeyboard()
                }
            }
        }

        binding.save.setOnClickListener {
            val content = binding.contentEditText.text.toString()
            viewModel.onSaveButtonClicked(content)
            binding.contentEditText.text.clear()
            binding.group.visibility = Group.GONE
        }

        binding.cancelEdit.setOnClickListener {
            viewModel.currentPost.value = null
            binding.contentEditText.text.clear()
            binding.group.visibility = Group.GONE
        }
    }
}
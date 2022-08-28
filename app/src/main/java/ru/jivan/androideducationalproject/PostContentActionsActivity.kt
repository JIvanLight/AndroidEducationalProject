package ru.jivan.androideducationalproject

import ru.jivan.androideducationalproject.utill.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.os.bundleOf
import ru.jivan.androideducationalproject.databinding.ActivityEditPostBinding
import ru.jivan.androideducationalproject.utill.Intents
import ru.jivan.androideducationalproject.utill.Keys

class PostContentActionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mIntent = intent ?: return

        val action = mIntent.action

        if (action == Intents.ACTION_EDIT_POST) {
            val text = mIntent.getStringExtra(Intent.EXTRA_TEXT)
            binding.editTextEdit.setText(text)
            binding.editTextEdit.requestFocus()
            binding.okFloatingActionButton.setOnClickListener {
                val intentOut = Intent()
                val textOut = binding.editTextEdit.text
                if (textOut.isNullOrBlank()) {
                    setResult(Activity.RESULT_CANCELED, mIntent)
                } else {
                    val textOutString = textOut.toString()
                    intentOut.putExtra(Intent.EXTRA_TEXT, textOutString)
                        .setAction(Intents.ACTION_EDIT_POST)
                    setResult(Activity.RESULT_OK, intentOut)
                }
                binding.editTextEdit.hideKeyboard()
                finish()
            }
        } else {
            binding.editTextEdit.requestFocus()
            binding.okFloatingActionButton.setOnClickListener {
                val intentOut = Intent()
                val textOut = binding.editTextEdit.text
                if (textOut.isNullOrBlank()) {
                    setResult(Activity.RESULT_CANCELED, mIntent)
                } else {
                    val textOutString = textOut.toString()
                    intentOut.putExtra(Intent.EXTRA_TEXT, textOutString)
                    setResult(Activity.RESULT_OK, intentOut)
                }
                binding.editTextEdit.hideKeyboard()
                finish()
            }
        }
    }

    class ResultContract : ActivityResultContract<Bundle, Bundle?>() {
        override fun createIntent(context: Context, input: Bundle): Intent {
            val postContent = input.getString(Keys.CONTENT_KEY)
            val customAction = checkNotNull(input.getString(Keys.ACTION_KEY))
            val intent = Intent(context, PostContentActionsActivity::class.java)
            if (customAction == Intents.ACTION_EDIT_POST) {
                intent.putExtra(Intent.EXTRA_TEXT, postContent).setAction(Intents.ACTION_EDIT_POST)
            }
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Bundle? =
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val action = intent?.action
                    val postContent = intent?.getStringExtra(Intent.EXTRA_TEXT)
                    val bundle = bundleOf()
                    bundle.putString(Keys.ACTION_KEY, action)
                    bundle.putString(Keys.CONTENT_KEY, postContent)
                    bundle
                }
                else -> null
            }
    }
}
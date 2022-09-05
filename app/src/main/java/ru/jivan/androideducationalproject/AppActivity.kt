package ru.jivan.androideducationalproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import ru.jivan.androideducationalproject.PostContentActionsFragment.Companion.actionArg
import ru.jivan.androideducationalproject.PostContentActionsFragment.Companion.textArg
import ru.jivan.androideducationalproject.databinding.ActivityAppBinding
import ru.jivan.androideducationalproject.utill.Actions

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun handleIntent(intent: Intent?) {

        intent?.let {

            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, "Content can't be empty", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        finish()
                    }.show()
                return@let
            }

            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment
            intent.removeExtra(Intent.EXTRA_TEXT)
            fragment.navController.navigate(
                R.id.action_feedFragment_to_postContentActionsFragment,
                Bundle().apply {
                    textArg = text
                    actionArg = Actions.ACTION_SHARE_POST
                }
            )
        }
    }
}
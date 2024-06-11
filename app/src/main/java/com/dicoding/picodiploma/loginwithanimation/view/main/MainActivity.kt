package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.Result
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity
import com.dicoding.picodiploma.loginwithanimation.view.maps.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.upload.AddStoryActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // observe user session
        observeSession()
        // set screen on recycler view
        setupView()
        // set list user stories
        setupAction()
    }

    private fun observeSession() {
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                navigateToLogin()
            }
        }
    }

    private fun setupView() {
        binding.rvListStory.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.maps_activity -> startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            R.id.language_setting -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.logout_setting -> {
                mainViewModel.logout()
                navigateToLogin()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupAction() {
        // observe view model
        lifecycleScope.launch {
            mainViewModel.getStories().observe(this@MainActivity) { story ->
                when (story) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> handleSuccess()
                    is Result.Error -> handleError(story.error)
                }
            }
        }

        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun handleSuccess() {
        showLoading(false)
        // set paging data
        val adapter = ListStoryAdapter()
        binding.rvListStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.storiesPaging.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun handleError(error: String?) {
        showLoading(false)
        showSnackbar(getString(R.string.error_load_story_message) + " " + error)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
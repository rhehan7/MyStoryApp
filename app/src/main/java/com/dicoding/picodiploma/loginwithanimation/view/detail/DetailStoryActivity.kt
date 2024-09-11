package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding

@Suppress("DEPRECATION")
class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailStory = intent.getParcelableExtra<ListStoryItem>(EXTRA_DETAIL_STORY) as ListStoryItem

        setupView()
        setupAction(detailStory)
        //
        setTopAppBarColor()
    }

    private fun setTopAppBarColor() {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        val color = typedValue.data

        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    }

    private fun setupView() {
        supportActionBar?.show()
        supportActionBar?.title = getString(R.string.detail_story_title)
    }

    private fun setupAction(detailStory: ListStoryItem){
        Glide.with(applicationContext)
            .load(detailStory.photoUrl)
            .into(binding.ivStory)
        binding.tvUser.text = detailStory.name
        binding.tvDescription.text = detailStory.description
    }

    companion object {
        const val EXTRA_DETAIL_STORY = "EXTRA_DETAIL_STORY"
    }
}
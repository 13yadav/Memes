package com.strangecoder.memes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.strangecoder.memes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: ActivityViewModel by lazy {
        ViewModelProvider(this).get(ActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val memeImage = binding.memeImage

        viewModel.meme.observe(this, Observer {
            Log.d("JJJ", "ImageUrl:==== ${it.url}")
            it.url?.let {
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                Glide.with(memeImage.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                    )
                    .into(memeImage)
            }
        })
    }
}
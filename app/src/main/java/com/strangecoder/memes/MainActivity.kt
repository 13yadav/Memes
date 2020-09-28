package com.strangecoder.memes

import android.content.Intent
import android.os.Bundle
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
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val memeImage = binding.memeImage

        viewModel.memeUrl.observe(this, Observer {
            it.let {
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                Glide.with(memeImage.context)
                    .load(imgUri)
                    .apply(RequestOptions().placeholder(R.drawable.loading_animation))
                    .into(memeImage)
            }
        })

        binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, "Hey checkout this meme on reddit: ${viewModel.memeUrl.value}")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share link using..."))
        }
    }
}

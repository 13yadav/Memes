package com.strangecoder.memes

data class Meme(
    val author: String,
    val nsfw: Boolean,
    val postLink: String,
    val spoiler: Boolean,
    val subreddit: String,
    val title: String,
    val ups: Int,
    val url: String
)
package com.strangecoder.memes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class ActivityViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getMemes()
    }

    private val _meme = MutableLiveData<Meme>()
    val meme: LiveData<Meme>
        get() = _meme

    private val _memeUrl = MutableLiveData<String>()
    val memeUrl: LiveData<String>
        get() = _memeUrl

    fun getMemes() {
        coroutineScope.launch(Dispatchers.IO) {
            val result = MemesApi.retrofitService.getMemeAsync().await()
            Log.d("JJJ", "Success: $result")
            withContext(Dispatchers.Main) {
                _memeUrl.value = result.url
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
package com.strangecoder.memes

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://meme-api.herokuapp.com/"

interface MemesApiService {

    @GET("gimme")
    fun getMemeAsync(): Deferred<Meme>

}

object MemesApi {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitService: MemesApiService by lazy {
        retrofit.create(MemesApiService::class.java)
    }
}
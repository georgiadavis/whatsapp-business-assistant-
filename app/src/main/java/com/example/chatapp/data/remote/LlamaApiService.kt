package com.example.chatapp.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LlamaApiService {
    @POST("v1/chat/completions")
    suspend fun chatCompletion(
        @Body request: ChatCompletionRequest,
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "application/json"
    ): ChatCompletionResponse
}


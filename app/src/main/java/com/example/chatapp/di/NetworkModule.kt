package com.example.chatapp.di

import android.content.Context
import com.example.chatapp.R
import com.example.chatapp.data.remote.LlamaApiService
import com.example.chatapp.data.repository.LlamaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val LLAMA_API_BASE_URL = "https://api.llama.com/"

    @Provides
    @Singleton
    fun provideJsonSerializer(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideLlamaApiService(
        okHttpClient: OkHttpClient,
        json: Json
    ): LlamaApiService = Retrofit.Builder()
        .baseUrl(LLAMA_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(LlamaApiService::class.java)

    @Provides
    @Singleton
    fun provideLlamaApiKey(@ApplicationContext context: Context): String {
        // Get API key from secrets.xml
        return try {
            context.getString(R.string.llama_api_key)
        } catch (e: Exception) {
            android.util.Log.e("NetworkModule", "Failed to load API key from secrets.xml", e)
            ""
        }
    }

    @Provides
    @Singleton
    fun provideLlamaRepository(
        llamaApiService: LlamaApiService,
        apiKey: String
    ): LlamaRepository = LlamaRepository(llamaApiService, apiKey)
}


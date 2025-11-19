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
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LlamaApiKey

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LlamaApiBaseUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @LlamaApiBaseUrl
    fun provideLlamaApiBaseUrl(@ApplicationContext context: Context): String {
        return try {
            context.getString(R.string.llama_api_base_url)
        } catch (e: Exception) {
            android.util.Log.e("NetworkModule", "Failed to load API base URL from secrets.xml", e)
            "https://api.llama.com/"
        }
    }

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
        json: Json,
        @LlamaApiBaseUrl baseUrl: String
    ): LlamaApiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(LlamaApiService::class.java)

    @Provides
    @Singleton
    @LlamaApiKey
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
        @LlamaApiKey apiKey: String
    ): LlamaRepository = LlamaRepository(llamaApiService, apiKey)
}


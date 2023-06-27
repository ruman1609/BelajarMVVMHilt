package com.rudyrachman16.belajarmvvmhilt.core.di

import com.rudyrachman16.belajarmvvmhilt.core.BuildConfig
import com.rudyrachman16.belajarmvvmhilt.core.api.MealService
import com.rudyrachman16.belajarmvvmhilt.core.api.RemoteDataGetter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().readTimeout(16, TimeUnit.SECONDS)
            .connectTimeout(16, TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, factory: GsonConverterFactory): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client).addConverterFactory(factory)
            .build()

    @Singleton
    @Provides
    fun provideMealService(retrofit: Retrofit): MealService =
        retrofit.create(MealService::class.java)
}
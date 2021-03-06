package com.saitow.di.module

import com.google.gson.GsonBuilder
import com.saitow.BuildConfig
import com.saitow.data.api.ApiHelper
import com.saitow.data.api.ApiHelperImpl
import com.saitow.data.api.ApiService
import com.saitow.data.api.BasicAuthInterceptor
import com.saitow.di.BaseUrl
import com.saitow.di.Password
import com.saitow.di.UserName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Created by Mostafa Anter on 9/5/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {
    @BaseUrl
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @UserName
    @Provides
    fun provideUserName() = BuildConfig.USER_NAME

    @Password
    @Provides
    fun providePassword() = BuildConfig.PASSWORD

    @Provides
    @Singleton
    fun provideOkHttpClient(basicAuthInterceptor: BasicAuthInterceptor) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(basicAuthInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .addNetworkInterceptor(basicAuthInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @BaseUrl BASE_URL: String,
        factory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create(
        GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
    )

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper





}
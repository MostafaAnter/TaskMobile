package com.saitow.di

import javax.inject.Qualifier

/**
 * Created by Mostafa Anter on 9/5/20.
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Password
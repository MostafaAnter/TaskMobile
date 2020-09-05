package com.saitow.data.api

import com.saitow.di.Password
import com.saitow.di.UserName
import okhttp3.Credentials
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mostafa Anter on 9/5/20.
 */
@Singleton
class BasicAuthInterceptor @Inject constructor(@UserName username: String, @Password password: String): Interceptor {
    private var credentials: String = Credentials.basic(username, password)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()

        return chain.proceed(request)
    }
}
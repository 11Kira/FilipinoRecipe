package com.kira.android.filipinorecipe.di

import android.content.Context
import com.kira.android.filipinorecipe.features.account.auth.AuthService
import com.kira.android.filipinorecipe.features.account.auth.token.AuthInterceptor
import com.kira.android.filipinorecipe.features.account.auth.token.TokenManager
import com.kira.android.filipinorecipe.utils.Constants
import com.kira.android.filipinorecipe.utils.Constants.BASE_URL
import com.kira.android.filipinorecipe.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenManager: TokenManager,
        authServiceProvider: Provider<AuthService>
    ): AuthInterceptor {
        return AuthInterceptor(tokenManager, authServiceProvider)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}
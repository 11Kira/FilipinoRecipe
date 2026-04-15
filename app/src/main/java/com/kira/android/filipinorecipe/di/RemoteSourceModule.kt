package com.kira.android.filipinorecipe.di

import com.kira.android.filipinorecipe.features.account.auth.AuthRemoteSource
import com.kira.android.filipinorecipe.features.account.auth.AuthService
import com.kira.android.filipinorecipe.features.recipes.RecipeRemoteSource
import com.kira.android.filipinorecipe.features.recipes.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteSourceModule {
    @Provides
    @Singleton
    fun provideRecipeRemoteSource(
        recipeService: RecipeService
    ) = RecipeRemoteSource(recipeService = recipeService)

    @Provides
    @Singleton
    fun provideAuthRemoteSource(
        authService: AuthService
    ) = AuthRemoteSource(authService = authService)
}
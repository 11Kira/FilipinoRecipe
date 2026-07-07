package com.kira.android.filipinorecipe.di

import com.kira.android.filipinorecipe.data.local.recipe.RecipeDao
import com.kira.android.filipinorecipe.data.local.user.UserDao
import com.kira.android.filipinorecipe.features.account.auth.AuthRemoteSource
import com.kira.android.filipinorecipe.features.account.auth.AuthRepository
import com.kira.android.filipinorecipe.features.account.user.UserRemoteSource
import com.kira.android.filipinorecipe.features.account.user.UserRepository
import com.kira.android.filipinorecipe.features.recipes.RecipeRemoteSource
import com.kira.android.filipinorecipe.features.recipes.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeRemoteSource: RecipeRemoteSource,
        recipeDao: RecipeDao
    ) = RecipeRepository(recipeRemoteSource = recipeRemoteSource, recipeDao = recipeDao)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteSource: AuthRemoteSource
    ) = AuthRepository(remoteSource = authRemoteSource)

    @Provides
    @Singleton
    fun provideUserRepository(
        userRemoteSource: UserRemoteSource,
        userDao: UserDao
    ) = UserRepository(userRemoteSource = userRemoteSource, userDao = userDao)
}
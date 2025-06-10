package com.kira.android.filipinorecipe.di

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
        recipeRemoteSource: RecipeRemoteSource
    ) = RecipeRepository(recipeRemoteSource = recipeRemoteSource)
}
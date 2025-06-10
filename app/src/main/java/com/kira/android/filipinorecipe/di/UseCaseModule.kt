package com.kira.android.filipinorecipe.di

import com.kira.android.filipinorecipe.features.recipes.RecipeRepository
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideRecipeUseCase(
        repository: RecipeRepository
    ) = RecipeUseCase(repository)
}
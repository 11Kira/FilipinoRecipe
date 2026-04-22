package com.kira.android.filipinorecipe.di

import com.kira.android.filipinorecipe.features.account.auth.AuthRepository
import com.kira.android.filipinorecipe.features.account.auth.AuthUseCase
import com.kira.android.filipinorecipe.features.account.user.UserRepository
import com.kira.android.filipinorecipe.features.account.user.UserUseCase
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

    @Provides
    @ViewModelScoped
    fun provideAuthUseCase(
        repository: AuthRepository
    ) = AuthUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideUserUseCase(
        repository: UserRepository
    ) = UserUseCase(repository)
}
package com.kira.android.filipinorecipe.di

import android.content.Context
import androidx.room.Room
import com.kira.android.filipinorecipe.data.local.AppDatabase
import com.kira.android.filipinorecipe.data.local.recipe.RecipeDao
import com.kira.android.filipinorecipe.data.local.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "filipino_recipe.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideRecipeDao(database: AppDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}

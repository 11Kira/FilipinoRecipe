package com.kira.android.filipinorecipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kira.android.filipinorecipe.data.local.recipe.RecipeDao
import com.kira.android.filipinorecipe.data.local.recipe.RecipeEntity
import com.kira.android.filipinorecipe.data.local.user.UserDao
import com.kira.android.filipinorecipe.data.local.user.UserEntity

@Database(entities = [RecipeEntity::class, UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun userDao(): UserDao
}

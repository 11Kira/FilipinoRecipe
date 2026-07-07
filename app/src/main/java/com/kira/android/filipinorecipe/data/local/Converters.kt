package com.kira.android.filipinorecipe.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kira.android.filipinorecipe.model.Ingredients

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIngredients(ingredients: Ingredients?): String? {
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun toIngredients(json: String?): Ingredients? {
        val type = object : TypeToken<Ingredients>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }
}

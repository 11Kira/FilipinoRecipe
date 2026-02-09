package com.kira.android.filipinorecipe.features.recipes.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("image")
    val image: String,
    @SerializedName("estimatedMinutes")
    val estimatedMinutes: Int,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("protein")
    val protein: String,
    @SerializedName("mealTime")
    val mealTime: String,
    @SerializedName("ingredients")
    val ingredients: Ingredients,
    @SerializedName("steps")
    val steps: List<String>,
    @SerializedName("cookingTips")
    val cookingTips: List<String> = emptyList(),
    @SerializedName("variations")
    val variations: List<String> = emptyList(),
    @SerializedName("servingSuggestions")
    val servingSuggestions: List<String> = emptyList(),
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("published")
    val published: Boolean,
)

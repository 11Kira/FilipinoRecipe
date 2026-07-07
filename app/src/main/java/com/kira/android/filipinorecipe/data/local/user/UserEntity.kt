package com.kira.android.filipinorecipe.data.local.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kira.android.filipinorecipe.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val email: String,
    val role: String
)

fun UserEntity.toDomain() = User(id = id, username = username, email = email, role = role)
fun User.toEntity() = UserEntity(id = id, username = username, email = email, role = role)

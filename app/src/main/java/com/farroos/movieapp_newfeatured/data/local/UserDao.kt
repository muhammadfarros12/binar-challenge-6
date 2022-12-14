@file:Suppress("unused")

package com.farroos.movieapp_newfeatured.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Suppress("unused")
@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password")
    suspend fun login(email: String, password: String): User

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Int): User

    @Update(onConflict = REPLACE)
    suspend fun update(user: User)
}
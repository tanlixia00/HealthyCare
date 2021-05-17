package com.example.projectuas_timxd_healthycare.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user:User)

    @Query("SELECT * from user")
    suspend fun selectAllUser(): List<User>
}
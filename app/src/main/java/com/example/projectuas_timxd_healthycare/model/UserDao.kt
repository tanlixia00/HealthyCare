package com.example.projectuas_timxd_healthycare.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: User)

    @Query("SELECT * from user")
    suspend fun selectAllUser(): User

    @Query("UPDATE user SET status=:status where uuid=:uuid")
    suspend fun updateStatus(status: String, uuid: Int)

    @Transaction
    @Query("SELECT * FROM user")
    suspend fun selectUserFoods(): List<UserWithFoods>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(vararg food: Food)

    @Query("UPDATE user set sumCal = sumCal + :cal where uuid= :uuid")
    suspend fun updateUserCal(cal: Int, uuid: Int)

    @Query("UPDATE user set sumCal = 0 where uuid= :uuid")
    suspend fun resetUserSumCal(uuid: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserReport(vararg report: Report)

    @Query("SELECT * from report")
    suspend fun selectAllReport(): List<Report>
}

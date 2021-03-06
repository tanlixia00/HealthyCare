package com.example.projectuas_timxd_healthycare.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectuas_timxd_healthycare.util.MIGRATION_1_2
import com.example.projectuas_timxd_healthycare.util.MIGRATION_2_3
import com.example.projectuas_timxd_healthycare.util.MIGRATION_3_4
import com.example.projectuas_timxd_healthycare.util.MIGRATION_4_5

@Database(entities = arrayOf(User::class, Food::class, Report::class), version = 5)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "newuserdb"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                .build()

        operator fun invoke(context: Context) {
            if (instance != null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}
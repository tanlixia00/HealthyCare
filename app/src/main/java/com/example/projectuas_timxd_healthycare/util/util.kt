package com.example.projectuas_timxd_healthycare.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projectuas_timxd_healthycare.model.UserDatabase

val DB_NAME = "newuserdb"
fun buildDb(context: Context): UserDatabase {
    val db = Room.databaseBuilder(context, UserDatabase::class.java, DB_NAME)
        .addMigrations(MIGRATION_1_2)
        .build()
    return db
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE user ADD COLUMN maxCal INTEGER DEFAULT 0 not null")
    }
}
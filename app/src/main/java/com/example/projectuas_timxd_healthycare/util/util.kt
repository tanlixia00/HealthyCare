package com.example.projectuas_timxd_healthycare.util

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projectuas_timxd_healthycare.model.Food
import com.example.projectuas_timxd_healthycare.model.UserDatabase
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

val DB_NAME = "newuserdb"
fun buildDb(context: Context): UserDatabase {
    val db = Room.databaseBuilder(context, UserDatabase::class.java, DB_NAME)
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
        .build()
    return db
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE user ADD COLUMN maxCal INTEGER DEFAULT 0 not null"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE user ADD COLUMN sumCal INTEGER DEFAULT 0 not null"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE user ADD COLUMN status STRING DEFAULT '' not null"
        )
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE food ADD COLUMN date LONG DEFAULT 0 not null"
        )
    }
}


@BindingAdapter("android:sum", "android:max")
fun progressBarPercent(v: ProgressBar?, sumCal: Int?, maxCal: Int?) {
    v?.progress = ((sumCal!! * 1.0) / (maxCal!! * 1.0) * 100).toInt()
}

@BindingAdapter("android:date", "android:jenis")
fun reportDate(v: TextView?, date: Long?, jenis:String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    var formatter = DateTimeFormatter.ofPattern("")
        if (jenis == "lengkap") {
            formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        }
        else if (jenis =="bulan"){
            formatter = DateTimeFormatter.ofPattern("MMM yyyy")
        }
        val instant = Instant.ofEpochMilli(date!! * 1000L)
        val dates = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val stringDate = formatter.format(dates)
        Log.d("calendar", stringDate)
        Log.d("tanggalutil", date!!.toString())
        v?.text = stringDate
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}


fun hideKeyboard(view: View) {
    val hide = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    hide.hideSoftInputFromWindow(view.windowToken, 0)
}
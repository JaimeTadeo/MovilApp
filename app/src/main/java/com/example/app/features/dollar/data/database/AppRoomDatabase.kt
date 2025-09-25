package com.example.app.features.dollar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app.features.dollar.data.database.dao.IDollarDao
import com.example.app.features.dollar.data.database.entity.DollarEntity

@Database(entities = [DollarEntity::class], version = 2)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun dollarDao(): IDollarDao

    companion object {
        @Volatile
        private var Instance: AppRoomDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE dollars ADD COLUMN usdt TEXT")
                database.execSQL("ALTER TABLE dollars ADD COLUMN usdc TEXT")
                database.execSQL("ALTER TABLE dollars ADD COLUMN fecha_actualizacion TEXT")
            }
        }

        fun getDatabase(context: Context): AppRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDatabase::class.java, "dollar_db")
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

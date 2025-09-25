package com.example.app.features.dollar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app.features.dollar.data.database.dao.IDollarDao
import com.example.app.features.dollar.data.database.entity.DollarEntity
import com.example.app.features.movies.data.database.entity.MovieEntity
import com.example.app.features.movies.data.database.dao.IMovieDao

@Database(entities = [DollarEntity::class, MovieEntity::class], version = 3)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun dollarDao(): IDollarDao
    abstract fun movieDao(): IMovieDao

    companion object {
        @Volatile
        private var Instance: AppRoomDatabase? = null

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS movies (
                        id INTEGER PRIMARY KEY NOT NULL,
                        title TEXT NOT NULL,
                        overview TEXT,
                        posterPath TEXT,
                        backdropPath TEXT,
                        releaseDate TEXT
                    )
                """)
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE dollars ADD COLUMN usdt TEXT")
                db.execSQL("ALTER TABLE dollars ADD COLUMN usdc TEXT")
                db.execSQL("ALTER TABLE dollars ADD COLUMN fecha_actualizacion TEXT")
            }
        }

        fun getDatabase(context: Context): AppRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDatabase::class.java, "dollar_db")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

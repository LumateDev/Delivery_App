package com.example.delivery_service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.delivery_service.models.Courier
import com.example.delivery_service.models.DeliveryDepartment



@Database(
    entities = [DeliveryDepartment::class, Courier::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(TypeConverter::class)
abstract  class InternalDB : RoomDatabase() {

    abstract fun dao(): DeliveryAppDAO

    companion object {
        @Volatile
        private var INSTANCE: InternalDB? = null

        fun getDatabase(context: Context): InternalDB {
            return INSTANCE ?: synchronized(this) {
                buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            InternalDB::class.java,
            "delivery_app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
package uz.gita.dimaa.mymaxway.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.dimaa.mymaxway.data.local.room.dao.FoodDao
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity

@Database(entities = [FoodEntity::class], version = 2)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun addFood(): FoodDao
}
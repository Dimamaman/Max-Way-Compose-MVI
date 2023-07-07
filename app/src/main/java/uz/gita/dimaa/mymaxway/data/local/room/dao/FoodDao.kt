package uz.gita.dimaa.mymaxway.data.local.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(foodEntity: FoodEntity)

    @Query("SELECT * FROM food")
    fun getAllFoods(): Flow<List<FoodEntity>>

    @Update
    fun update(foodEntity: FoodEntity)

    @Query("DELETE  FROM food")
    fun clearData()
}
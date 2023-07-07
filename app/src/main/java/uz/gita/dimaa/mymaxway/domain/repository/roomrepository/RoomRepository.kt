package uz.gita.dimaa.mymaxway.domain.repository.roomrepository

import kotlinx.coroutines.flow.Flow
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.domain.model.FoodData

interface RoomRepository {
    fun add(food: FoodData, count: Int)
    fun getFoods(): Flow<List<FoodEntity>>
    fun updateFood(foodEntity: FoodEntity, count: Int)
    fun clearData(): Flow<Unit>
}
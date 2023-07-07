package uz.gita.dimaa.mymaxway.domain.repository.roomrepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.dimaa.mymaxway.data.local.room.dao.FoodDao
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.domain.model.FoodData
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao
) : RoomRepository {
    override fun add(food: FoodData, count: Int) {
        foodDao.add(food.toEntity(count))
    }

    override fun getFoods(): Flow<List<FoodEntity>> {
        return foodDao.getAllFoods()
    }

    override fun updateFood(foodEntity: FoodEntity, count: Int) {
        foodDao.update(FoodEntity(
            id = foodEntity.id,
            name = foodEntity.name,
            price = foodEntity.price,
            count = count,
            imageUrl = foodEntity.imageUrl,
            description = foodEntity.description
        ))
    }

    override fun clearData(): Flow<Unit> = flow {
        foodDao.clearData()
        emit(Unit)
    }.flowOn(Dispatchers.IO)
}
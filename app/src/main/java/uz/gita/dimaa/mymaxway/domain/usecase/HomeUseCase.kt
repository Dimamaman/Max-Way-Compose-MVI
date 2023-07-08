package uz.gita.dimaa.mymaxway.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.data.model.OrderData
import uz.gita.dimaa.mymaxway.domain.model.FoodData

interface HomeUseCase {
    fun getCategories(): Flow<Result<List<String>>>
     fun addOrders(orderData: OrderData): Flow<Result<String>>
    suspend fun getFoods(): Result<List<FoodData>>
    fun getFoodsFromRoom(): Flow<List<FoodEntity>>
    fun updateFood(foodEntity: FoodEntity, count: Int)
    fun getOrderedFoods(userId: String): Flow<Result<List<OrderData>>>
    fun searchFood(search: String): Flow<Result<List<FoodData>>>
}
package uz.gita.dimaa.mymaxway.domain.repository.firebase

import kotlinx.coroutines.flow.Flow
import uz.gita.dimaa.mymaxway.data.model.Category
import uz.gita.dimaa.mymaxway.data.model.OrderData
import uz.gita.dimaa.mymaxway.domain.model.FoodData

interface FirebaseRepository {
    suspend fun getAllCategory(): Result<List<Category>>
    suspend fun getAllFoods(): Result<List<FoodData>>
    fun addOrders(orderData: OrderData): Flow<Result<String>>
    fun getOrderedFoods(userId: String): Flow<Result<List<OrderData>>>
    fun searchFood(search: String): Flow<Result<List<FoodData>>>
    fun searchFoodByCategory(search: String): Flow<Result<List<Category>>>
}
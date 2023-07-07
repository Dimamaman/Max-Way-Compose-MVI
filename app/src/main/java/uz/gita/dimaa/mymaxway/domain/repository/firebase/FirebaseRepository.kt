package uz.gita.dimaa.mymaxway.domain.repository.firebase

import kotlinx.coroutines.flow.Flow
import uz.gita.dimaa.mymaxway.data.model.Category
import uz.gita.dimaa.mymaxway.data.model.OrderData
import uz.gita.dimaa.mymaxway.domain.model.FoodData

interface FirebaseRepository {
    suspend fun getAllCategory(): Result<List<Category>>
    suspend fun getAllFoods(): Result<List<FoodData>>
     fun addOrders(orderData: OrderData): Flow<Result<String>>
}
package uz.gita.dimaa.mymaxway.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.data.model.OrderData
import uz.gita.dimaa.mymaxway.domain.model.FoodData
import uz.gita.dimaa.mymaxway.domain.repository.firebase.FirebaseRepository
import uz.gita.dimaa.mymaxway.domain.repository.roomrepository.RoomRepository
import uz.gita.dimaa.mymaxway.domain.usecase.HomeUseCase
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val repository: FirebaseRepository,
    private val roomRepository: RoomRepository
) : HomeUseCase {
    override fun getCategories(): Flow<Result<List<String>>> = flow<Result<List<String>>> {
        val resultList = arrayListOf<String>()
        repository.getAllCategory().onSuccess {
            it.forEach { category ->
                resultList.add(category.title)
            }
        }.onFailure {
            emit(Result.failure(it))
        }
        emit(Result.success(resultList))
    }.flowOn(Dispatchers.IO)

    override  fun addOrders(orderData: OrderData): Flow<Result<String>> {
        return repository.addOrders(orderData)
    }

    override suspend fun getFoods(): Result<List<FoodData>> {
        return repository.getAllFoods()
    }

    override fun getFoodsFromRoom(): Flow<List<FoodEntity>> {
        return roomRepository.getFoods()
    }

    override fun updateFood(foodEntity: FoodEntity, count: Int) {
        roomRepository.updateFood(foodEntity, count)
    }

    override fun getOrderedFoods(userId: String): Flow<Result<List<OrderData>>> {
        return repository.getOrderedFoods(userId)
    }

    override fun searchFood(search: String): Flow<Result<List<FoodData>>> {
        return repository.searchFood(search)
    }
}
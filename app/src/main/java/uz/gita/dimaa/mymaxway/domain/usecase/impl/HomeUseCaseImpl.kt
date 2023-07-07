package uz.gita.dimaa.mymaxway.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.dimaa.mymaxway.data.model.Category
import uz.gita.dimaa.mymaxway.data.model.Food
import uz.gita.dimaa.mymaxway.domain.repository.firebase.FirebaseRepository
import uz.gita.dimaa.mymaxway.domain.usecase.HomeUseCase
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val repository: FirebaseRepository
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

    override suspend fun getFoods(): Result<List<Food>> {
        return repository.getAllFoods()
    }
}
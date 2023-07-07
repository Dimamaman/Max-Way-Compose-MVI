package uz.gita.dimaa.mymaxway.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.dimaa.mymaxway.data.model.Food

interface HomeUseCase {
    fun getCategories(): Flow<Result<List<String>>>
    suspend fun getFoods(): Result<List<Food>>
}
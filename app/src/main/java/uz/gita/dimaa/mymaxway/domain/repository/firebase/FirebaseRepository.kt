package uz.gita.dimaa.mymaxway.domain.repository.firebase

import uz.gita.dimaa.mymaxway.data.model.Category
import uz.gita.dimaa.mymaxway.data.model.Food

interface FirebaseRepository {
    suspend fun getAllCategory(): Result<List<Category>>
    suspend fun getAllFoods(): Result<List<Food>>
}
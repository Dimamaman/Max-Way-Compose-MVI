package uz.gita.dimaa.mymaxway.domain.repository.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import uz.gita.dimaa.mymaxway.data.model.Category
import uz.gita.dimaa.mymaxway.data.model.Food
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(): FirebaseRepository {
    private val db = Firebase.firestore

    override suspend fun getAllCategory(): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = db.collection("MaxWay")
                .get()
                .await()

            val resultList = arrayListOf<Category>()

            querySnapshot.documents.forEach {
                val foodList = arrayListOf<Food>()
                val subCollection = it.reference.collection("list")
                    .get()
                    .await()

                subCollection.forEach {
                    foodList.add(it.toObject(Food::class.java))
                }
                resultList.add(
                    Category(
                        id = it.get("id") as Long,
                        title = it.get("title") as String,
                        listFood = foodList
                    )
                )
            }
            return@withContext Result.success(resultList)
        }catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    override suspend fun getAllFoods(): Result<List<Food>>  {
        try {
            val querySnapshot = db.collection("MaxWay")
                .get()
                .await()

            val foodList = arrayListOf<Food>()
            querySnapshot.documents.forEach {
                val subCollection = it.reference.collection("list")
                    .get()
                    .await()

                subCollection.forEach {
                    foodList.add(it.toObject(Food::class.java))
                }
            }
            return Result.success(foodList)
        }catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
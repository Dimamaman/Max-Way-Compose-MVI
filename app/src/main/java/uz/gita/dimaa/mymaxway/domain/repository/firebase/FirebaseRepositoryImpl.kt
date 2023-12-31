package uz.gita.dimaa.mymaxway.domain.repository.firebase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import uz.gita.dimaa.mymaxway.data.model.Category
import uz.gita.dimaa.mymaxway.data.model.Document
import uz.gita.dimaa.mymaxway.data.model.OrderData
import uz.gita.dimaa.mymaxway.domain.model.FoodData
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor() : FirebaseRepository {
    private val db = Firebase.firestore

    override suspend fun getAllCategory(): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = db.collection("MaxWay")
                .get()
                .await()

            val resultList = arrayListOf<Category>()

            querySnapshot.documents.forEach {
                val foodList = arrayListOf<FoodData>()
                val subCollection = it.reference.collection("list")
                    .get()
                    .await()

                subCollection.forEach {
                    foodList.add(it.toObject(FoodData::class.java))
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
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    override suspend fun getAllFoods(): Result<List<FoodData>> {
        try {
            val querySnapshot = db.collection("MaxWay")
                .get()
                .await()

            val foodList = arrayListOf<FoodData>()
            querySnapshot.documents.forEach {
                val subCollection = it.reference.collection("list")
                    .get()
                    .await()

                subCollection.forEach {
                    foodList.add(it.toObject(FoodData::class.java))
                }
            }
            return Result.success(foodList)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override  fun addOrders(
        orderData: OrderData
    ): Flow<Result<String>> = callbackFlow {
        var documentId: String
        val a = db.collection("Orders")
            .get()
            .await()

        db.collection("orders")
            .add(
                Document(
                    userId = orderData.userId,
                    comment = orderData.comment,
                    allPrice = orderData.allPrice
                )
            ).addOnSuccessListener {
                documentId = it.id
                orderData.list.forEach { foodEntity ->
                    Log.d("MMM","Repo -> $foodEntity")
                    db.collection("orders")
                        .document(documentId)
                        .collection("foods")
                        .add(foodEntity)
                }
            }.await()

        a.documents.forEach { documentSnapshot ->
            if (orderData.userId == documentSnapshot.get("userid")) {
                orderData.list.forEach {
                    documentSnapshot.reference.collection("foods")
                        .add(it)
                        .await()
                }
            }
        }
        trySend(Result.success("Buyurtmangiz qabul qilindi"))
        awaitClose()
    }.flowOn(Dispatchers.IO)
}
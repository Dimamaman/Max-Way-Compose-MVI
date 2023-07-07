package uz.gita.dimaa.mymaxway.domain.model

import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity

data class FoodData(
    val id: Long = 0,
    val name: String = "",
    val price: Long = 0,
    val imageUrl: String = "",
    val description: String = ""
) {
    fun toEntity(count: Int): FoodEntity {
        return FoodEntity(id, name, count = count, price, imageUrl, description)
    }
}

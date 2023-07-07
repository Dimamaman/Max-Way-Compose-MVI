package uz.gita.dimaa.mymaxway.data.model

import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity

data class OrderData(
    val list: List<FoodEntity>,
    val userId: String,
    val comment: String,
    val allPrice: Long
)
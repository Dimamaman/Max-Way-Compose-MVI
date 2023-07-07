package uz.gita.dimaa.mymaxway.data.model

import uz.gita.dimaa.mymaxway.domain.model.FoodData

data class Category(
    val id: Long = 0,
    val title: String,
    val listFood: List<FoodData>
)

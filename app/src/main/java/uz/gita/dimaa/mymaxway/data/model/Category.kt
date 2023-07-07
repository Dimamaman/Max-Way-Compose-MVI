package uz.gita.dimaa.mymaxway.data.model

data class Category(
    val id: Long = 0,
    val title: String,
    val listFood: List<Food>
)

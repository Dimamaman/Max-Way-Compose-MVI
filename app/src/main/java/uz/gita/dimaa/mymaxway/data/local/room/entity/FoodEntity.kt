package uz.gita.dimaa.mymaxway.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.dimaa.mymaxway.domain.model.FoodData

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val count: Int = 0,
    val price: Long = 0,
    val imageUrl: String = "",
    val description: String = ""
) {
    fun toData(): FoodData {
        return FoodData(id, name, price, imageUrl, description)
    }
}

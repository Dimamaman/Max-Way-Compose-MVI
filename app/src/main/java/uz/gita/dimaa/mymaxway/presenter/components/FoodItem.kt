package uz.gita.dimaa.mymaxway.presenter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uz.gita.dimaa.mymaxway.data.model.Food

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FoodItem(
    food: Food,
    onClick: (Food) -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(10.dp)

    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                GlideImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(100.dp)
                        .padding(8.dp),
                    model = food.imageUrl,
                    contentDescription = ""
                )
            }

            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = food.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = food.price.toString() + " swm"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { onClick.invoke(food) }) {
                    Text(text = "Savatchaga")
                }
            }
        }
    }
}
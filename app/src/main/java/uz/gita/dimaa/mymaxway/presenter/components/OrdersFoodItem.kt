package uz.gita.dimaa.mymaxway.presenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uz.gita.dimaa.mymaxway.R
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.presenter.screens.busket.BasketContract

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun OrdersFoodItem(
    foodEntity: FoodEntity,
    onEventDispatcher: (BasketContract.Intent) -> Unit,
    change:(Int) -> Unit
) {
    var count by remember { mutableStateOf(foodEntity.count) }

    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            GlideImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(100.dp)
                    .padding(8.dp),
                model = foodEntity.imageUrl,
                contentDescription = ""
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = foodEntity.name,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "${foodEntity.price * count} swm",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Column(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(
                                bounded = false,
                                radius = 30.dp,
                                color = Color.Black
                            ),
                            onClick = {
                                if (count > 1) {
                                    count--
                                    change.invoke(count)
                                }
                            }
                        ),
                    painter = painterResource(id = R.drawable.ic_down),
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "$count",
                )

                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(
                                bounded = false,
                                radius = 30.dp,
                                color = Color.Black
                            ),
                            onClick = {
                                count++
                                change.invoke(count)
                            }
                        ),
                    painter = painterResource(id = R.drawable.ic_up),
                    contentDescription = ""
                )
            }
        }
    }
}
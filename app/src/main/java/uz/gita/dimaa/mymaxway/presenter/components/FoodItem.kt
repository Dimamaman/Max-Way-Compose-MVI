package uz.gita.dimaa.mymaxway.presenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
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
import uz.gita.dimaa.mymaxway.domain.model.FoodData
import uz.gita.dimaa.mymaxway.presenter.page.home.HomeContract

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FoodItem(
    food: FoodData,
    onEventDispatcher: (HomeContract.Intent) -> Unit,
    onClick: (Int) -> Unit
) {
    var count by remember { mutableStateOf(0) }
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

            if (count == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        count++
                        onClick.invoke(count)
                    }) {
                        Text(text = "+ Add")
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                                            onEventDispatcher.invoke(HomeContract.Intent.Change(food.toEntity(count),count))
                                            onClick.invoke(count)
                                        } else {
                                            count = 0
                                            onEventDispatcher.invoke(HomeContract.Intent.Delete(food))
                                            onClick.invoke(count)
                                        }
                                    }
                                ),
                            painter = painterResource(id = R.drawable.ic_down),
                            contentDescription = ""
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = count.toString(),
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
                                        onEventDispatcher.invoke(HomeContract.Intent.Add(food,count))
                                        count++
                                        onClick.invoke(count)
                                        onEventDispatcher.invoke(HomeContract.Intent.Change(food.toEntity(count),count))

                                    }
                                ),
                            painter = painterResource(id = R.drawable.ic_up),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        DismissDirection.EndToStart -> Color(0xFFFF1744)
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart)  Icon(
            Icons.Default.Delete,
            contentDescription = "Edit"
        )
    }
}
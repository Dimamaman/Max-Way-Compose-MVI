package uz.gita.dimaa.mymaxway.presenter.screens.busket

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.dimaa.mymaxway.R
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.presenter.components.OrdersFoodItem
import uz.gita.dimaa.mymaxway.theme.LightGrayColor

class BasketScreen : Tab, AndroidScreen() {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Orders"
            val icon = painterResource(id = R.drawable.ic_buy)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: BasketContract.ViewModel = getViewModel<BasketScreenViewModel>()
        val uiState = viewModel.uiState.collectAsState().value
        val context = LocalContext.current
        viewModel.collectSideEffect {
            when(it) {
                is BasketContract.SideEffect.HasError -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        BasketScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@Composable
fun BasketScreenContent(
    uiState: BasketContract.UIState,
    onEventDispatcher: (BasketContract.Intent) -> Unit
) {

    onEventDispatcher.invoke(BasketContract.Intent.Loading)
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (uiState.foods.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.empty_box), contentDescription = "")
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Busket is empty",
                        fontSize = 35.sp
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {

                items(uiState.foods) { foodEntity ->

                    OrdersFoodItem(foodEntity = foodEntity) {
                        onEventDispatcher.invoke(BasketContract.Intent.Change(foodEntity, it))
                    }
                }
                item {
                    if (uiState.foods.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 10.dp)
                                .clip(RoundedCornerShape(8.dp))


                        ) {

                            TextField(
                                modifier = Modifier.fillMaxSize(),
                                value = comment,
                                onValueChange = { newValue -> comment = newValue },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = Color(0xFF050505)
                                ),
                                placeholder = {
                                    Text(
                                        text = "Izoh...",
                                        color = Color.Gray
                                    )
                                }
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp), contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        onEventDispatcher.invoke(
                            BasketContract.Intent.Comment(
                                comment,
                                getAllPrice(uiState.foods)
                            )
                        )
                    },
                    enabled = uiState.foods.isNotEmpty()
                ) {
                    Text(text = "Rasmiylashtirish")
                }
            }
        }
    }
}

private fun getAllPrice(list: List<FoodEntity>): Long {
    var p = 0L
    list.forEach {
        p += it.price * it.count
    }
    return p
}

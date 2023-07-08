package uz.gita.dimaa.mymaxway.presenter.page.orders

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.dimaa.mymaxway.R
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.data.model.OrderData
import uz.gita.dimaa.mymaxway.theme.LightGrayColor

class OrdersPage : Tab, AndroidScreen() {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Orders"
            val icon = painterResource(id = R.drawable.history_one)
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
        val viewModel: OrdersContract.ViewModel = getViewModel<OrdersPageViewModel>()
        val uiState = viewModel.collectAsState()

        OrderPageContent(uiState, viewModel::onEventDispatcher)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderPageContent(
    uiState: State<OrdersContract.UIState>,
    onEventDispatcher: (OrdersContract.Intent) -> Unit
) {
    onEventDispatcher.invoke(OrdersContract.Intent.Loading)
    var orderList = arrayListOf<OrderData>()

    when (uiState.value) {
        is OrdersContract.UIState.Loading -> {

        }

        is OrdersContract.UIState.Orders -> {
            orderList =
                (uiState.value as OrdersContract.UIState.Orders).list as ArrayList<OrderData>
        }
    }

    Scaffold(topBar = { TopAppBar() }) {
        Surface(
            Modifier
                .padding(it)
                .background(Color(0xFFF4F4F4))
                .fillMaxSize()

        ) {

            if (orderList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.empty_box),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                        )
                        Text(
                            text = "Buyurtma mavjud emas",
                            modifier = Modifier,
                            color = Color.Gray,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    Modifier
                        .padding(top = 8.dp)
                ) {
                    items(orderList) { orderData ->
                        Surface(
                            shadowElevation = 8.dp,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp)

                        ) {
                            Column(
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White)

                            ) {
                                orderData.list.forEach {

                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    ) {
                                        Text(
                                            text = it.name,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                        Text(
                                            text = it.count.toString() + " ta",
                                            modifier = Modifier.padding(start = 16.dp)
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(0.dp)
                                                .weight(1f)
                                        )
                                        Text(text = (it.count * it.price).toString())
                                    }
                                }

                                if (orderData.comment.isNotEmpty()) {
                                    Text(
                                        text = "Izoh: ${orderData.comment}",
                                        modifier = Modifier
                                            .align(Alignment.Start)
                                            .padding(top = 8.dp)
                                            .padding(horizontal = 16.dp)
                                    )
                                }
                                Button(
                                    onClick = { },
                                    modifier = Modifier.padding(
                                        bottom = 8.dp,
                                        end = 8.dp,
                                        top = 8.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(Color(0xFF50267D))
                                ) {
                                    Text(
                                        text = "${orderData.allPrice} so'm",
                                        Modifier,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                    }
                }

                if (orderList.isEmpty()) {

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(45.dp),
                            strokeWidth = 5.dp,
                            color = Color(0xFFF80358)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar() {
    Surface(shadowElevation = 8.dp, modifier = Modifier.height(56.dp)) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Orders",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
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

package uz.gita.dimaa.mymaxway.presenter.page.home

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import uz.gita.dimaa.mymaxway.R
import uz.gita.dimaa.mymaxway.app.App
import uz.gita.dimaa.mymaxway.connection.ConnectivityObserver
import uz.gita.dimaa.mymaxway.connection.NetworkConnectivityObserver
import uz.gita.dimaa.mymaxway.domain.model.FoodData
import uz.gita.dimaa.mymaxway.presenter.components.CustomSearchView
import uz.gita.dimaa.mymaxway.presenter.components.FoodItem
import uz.gita.dimaa.mymaxway.theme.CategoryBackGround
import uz.gita.dimaa.mymaxway.theme.LightGrayColor
import uz.gita.dimaa.mymaxway.theme.PurpleGrey80
import uz.gita.dimaa.mymaxway.theme.dark_grey

class HomePage : Tab, AndroidScreen() {
    override val options: TabOptions
        @Composable get() {
            val title = "Home"

            val icon = painterResource(id = R.drawable.ic_home)
            return remember {
                TabOptions(
                    index = 0u, title = title, icon = icon
                )
            }
        }


    private lateinit var connectivityObserver: ConnectivityObserver

    @Composable
    override fun Content() {

        connectivityObserver = NetworkConnectivityObserver(App.instance.applicationContext)

        val status by connectivityObserver.observe()
            .collectAsState(initial = ConnectivityObserver.Status.Unavailable)

        when(status) {
            ConnectivityObserver.Status.Unavailable -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "No Internet Connection")
                }
            }

            ConnectivityObserver.Status.Available -> {
                val viewModel: HomeContract.ViewModel = getViewModel<HomePageViewModel>()
                val uiState = viewModel.uiState.collectAsState()

                HomePageContent(uiState, viewModel::onEventDispatcher)
            }

            ConnectivityObserver.Status.Lost -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "No Internet Connection")
                }
            }
            ConnectivityObserver.Status.Losing -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "No Internet Connection")
                }
            }
        }
    }
}

@Composable
fun HomePageContent(
    uiState: State<HomeContract.UIState>, onEventDispatcher: (HomeContract.Intent) -> Unit
) {
    val context = LocalContext.current
    var search by remember { mutableStateOf("") }
    var count by remember { mutableStateOf(0) }
    var foodData by remember { mutableStateOf(FoodData()) }
    var dialogState by remember { mutableStateOf(false) }
    val isLoading by remember { mutableStateOf(uiState.value.isRefreshing) }
    var loading by remember { mutableStateOf(uiState.value.loading) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    var categoryName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            CustomSearchView(modifier = Modifier.weight(1f), search = search) {
                search = it
                onEventDispatcher.invoke(HomeContract.Intent.Search(search))
            }

            Image(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(30.dp)
                    .clickable(interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(
                            bounded = false, radius = 25.dp, color = Color.Black
                        ),
                        onClick = {
                            onEventDispatcher.invoke(HomeContract.Intent.OpenOrderScreen)
                        }),
                painter = painterResource(id = R.drawable.ic_buy),
                contentDescription = ""
            )

        }

        LazyRow(modifier = Modifier.padding(top = 2.dp)) {
            item { Spacer(modifier = Modifier.width(10.dp)) }
            items(uiState.value.categories.size) {
                var categoryButtonColor by remember { mutableStateOf(false) }
                Button(
                    onClick = {
                        categoryButtonColor = !categoryButtonColor
                        onEventDispatcher.invoke(HomeContract.Intent.SearchByCategory(uiState.value.categories[it]))
                        categoryName = uiState.value.categories[it]
                        if (!categoryButtonColor) {
                            onEventDispatcher.invoke(HomeContract.Intent.Loading)
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(1.dp)),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(if (categoryButtonColor && categoryName == uiState.value.categories[it]) PurpleGrey80 else CategoryBackGround)
                ) {
                    Text(text = uiState.value.categories[it])
                }
            }
        }

        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            onEventDispatcher.invoke(HomeContract.Intent.Loading)
        }) {

            if (uiState.value.isInternetAvailable) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "No Internet")
                }
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                    items(uiState.value.foods) { food ->
                        FoodItem(food = food, onEventDispatcher) {
                            dialogState = true
                            foodData = food
                            count = it
                        }
                    }
                })
            }
        }
    }

    if (uiState.value.loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(45.dp),
                strokeWidth = 5.dp,
                color = dark_grey
            )
        }
    }

    if (uiState.value.isEmpty) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Not Found",
                    fontSize = 25.sp
                )
            }
        }
    }

    if (count > 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            BottomSheet(foodData = foodData, count = count, onEventDispatcher)
        }
    }

}

@Composable
fun BottomSheet(
    foodData: FoodData,
    count: Int,
    onEventDispatcher: (HomeContract.Intent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Yellow), contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = "25 - 30 min",
                fontWeight = FontWeight.Bold
            )

            Button(modifier = Modifier.weight(1f), onClick = {
                onEventDispatcher.invoke(HomeContract.Intent.Add(foodData, count))
                onEventDispatcher.invoke(HomeContract.Intent.OpenOrderScreen)
            }) {
                Text(text = "Order")
            }

            Text(
                modifier = Modifier.weight(1f),
                text = "${foodData.price * count} swm",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
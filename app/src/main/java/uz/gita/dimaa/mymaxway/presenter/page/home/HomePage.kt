package uz.gita.dimaa.mymaxway.presenter.page.home

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.dimaa.mymaxway.R
import uz.gita.dimaa.mymaxway.domain.model.FoodData
import uz.gita.dimaa.mymaxway.presenter.components.CustomSearchView
import uz.gita.dimaa.mymaxway.presenter.components.FoodItem
import uz.gita.dimaa.mymaxway.theme.LightGrayColor

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

    @Composable
    override fun Content() {
        val viewModel: HomeContract.ViewModel = getViewModel<HomePageViewModel>()
        val uiState = viewModel.uiState.collectAsState().value

        HomePageContent(uiState, viewModel::onEventDispatcher)
    }
}

var allCount = 0

@Composable
fun HomePageContent(
    uiState: HomeContract.UIState, onEventDispatcher: (HomeContract.Intent) -> Unit
) {

    var search by remember { mutableStateOf("") }
    var count by remember { mutableStateOf(0) }
    var foodData by remember { mutableStateOf(FoodData()) }
    var dialogState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomSearchView(modifier = Modifier.weight(1f), search = search) {
                search = it
//                    onEventDispatcher(HomeContact.Intent.Search((search)))
            }

            Image(
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp)
                    .size(30.dp)
                    .clickable(interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(
                            bounded = false, radius = 25.dp, color = Color.Black
                        ),
                        onClick = {

                        }),
                painter = painterResource(id = R.drawable.history),
                contentDescription = ""
            )

        }

        LazyRow(modifier = Modifier.padding(top = 8.dp)) {
            item { Spacer(modifier = Modifier.width(10.dp)) }
            items(uiState.categories.size) {
                Button(
                    onClick = {
//                        if (selectedCategories.contains(uiState.categories[it])) {
//                            selectedCategories.remove(uiState.categories[it])
//                        } else {
//                            selectedCategories.add(uiState.categories[it])
//                        }

//                        onEventDispatcher(
//                            HomeContact.Intent.SelectCategories(selectedCategories)
//                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(1.dp)),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(text = uiState.categories[it])
                }
            }
        }

        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(uiState.foods) { food ->
                FoodItem(food = food) {
                    dialogState = true
                    foodData = food
                    count = it
                }
            }
        })
    }

    Log.d("KKKK", "Count -> $count")
    if (count > 0) {
        Log.d("KKKK", "Count If -> $count")
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            BottomSheet(foodData = foodData, count = count, onEventDispatcher)
        }
    }

}

@Composable
fun BottomSheet(foodData: FoodData, count: Int, onEventDispatcher: (HomeContract.Intent) -> Unit) {
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
            )

            Button(modifier = Modifier.weight(1f), onClick = {
                onEventDispatcher.invoke(HomeContract.Intent.Add(foodData, count))
            }) {
                Text(text = "Order")
            }

            Text(
                modifier = Modifier.weight(1f),
                text = "${foodData.price * count} swm",
                textAlign = TextAlign.Center
            )
        }
    }
}
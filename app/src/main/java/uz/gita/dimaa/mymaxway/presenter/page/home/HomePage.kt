package uz.gita.dimaa.mymaxway.presenter.page.home

import android.util.Log
import android.widget.ImageButton
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.dimaa.mymaxway.R
import uz.gita.dimaa.mymaxway.data.model.Food
import uz.gita.dimaa.mymaxway.presenter.components.CustomSearchView
import uz.gita.dimaa.mymaxway.presenter.components.FoodItem
import uz.gita.dimaa.mymaxway.theme.DialogColor
import uz.gita.dimaa.mymaxway.theme.LightGrayColor

class HomePage : Tab, AndroidScreen() {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"

            val icon = painterResource(id = R.drawable.ic_home)
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
        val viewModel: HomeContract.ViewModel = getViewModel<HomePageViewModel>()
        val uiState = viewModel.uiState.collectAsState().value

        HomePageContent(uiState, viewModel::onEventDispatcher)
    }
}

@Composable
fun HomePageContent(
    uiState: HomeContract.UIState,
    onEventDispatcher: (HomeContract.Intent) -> Unit
) {

    var search by remember { mutableStateOf("") }
    var count by remember { mutableStateOf(0) }
    var foodData by remember { mutableStateOf(Food()) }
    var dialogState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayColor)
    ) {
        CustomSearchView(search = search) {
            search = it
            Log.d("HHH", "Search -> $it")
//                    onEventDispatcher(HomeContact.Intent.Search((search)))
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            content = {
                items(uiState.foods) { food ->
                    FoodItem(food = food) {
                        dialogState = true
                        foodData = it
                    }
                }
            })


        if (dialogState) {
            count = 1
            Dialog(onDismissRequest = { dialogState = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(DialogColor)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = foodData.name,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = foodData.description,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Text(text = "Summa: ${foodData.price * count} swm")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

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
                                            count++
                                        }
                                    ),
                                painter = painterResource(id = R.drawable.ic_up),
                                contentDescription = ""
                            )

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Button(onClick = {
                                    dialogState = false
                                }) {
                                    Text(text = "Ok")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
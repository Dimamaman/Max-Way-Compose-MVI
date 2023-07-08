package uz.gita.dimaa.mymaxway.presenter.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.presenter.screens.busket.BasketContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemOne(
    foodEntity: FoodEntity,
    onEventDispatcher: (BasketContract.Intent) -> Unit,
    onRemove: () -> Unit,
    change:(Int) -> Unit,
) {
    val show by remember { mutableStateOf(true) }

    val dismissState = rememberDismissState(
        confirmValueChange = {
            when (it) {
                DismissValue.DismissedToStart -> {
                    onRemove()
                    false
                }

                DismissValue.DismissedToEnd -> {
                    onRemove()
                    true
                }

                else -> false
            }
        }
    )

    AnimatedVisibility(
        show, exit = fadeOut(spring()),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                OrdersFoodItem(foodEntity = foodEntity, change = change, onEventDispatcher = onEventDispatcher)
            }
        )
    }
}
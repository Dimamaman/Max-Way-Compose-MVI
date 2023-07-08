package uz.gita.dimaa.mymaxway.presenter.screens.busket

import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.domain.model.FoodData
import uz.gita.dimaa.mymaxway.presenter.page.home.HomeContract

interface BasketContract {
    sealed interface Intent {
        object Loading: Intent
        data class Change(val foodEntity: FoodEntity, val count: Int): Intent
        data class Comment(val message: String, val allPrice:Long): Intent
        data class Delete(val food: FoodData): Intent
    }

    data class UIState(val foods: List<FoodEntity> = emptyList())

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    interface Direction {
        suspend fun back()
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }
}
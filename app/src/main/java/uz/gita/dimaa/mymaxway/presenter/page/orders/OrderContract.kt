package uz.gita.dimaa.mymaxway.presenter.page.orders

import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity

interface OrderContract {
    sealed interface Intent {
        object Loading: Intent
        data class Change(val foodEntity: FoodEntity, val count: Int): Intent
        data class Comment(val message: String, val allPrice:Long): Intent
    }

    data class UIState(val foods: List<FoodEntity> = emptyList())

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }
}
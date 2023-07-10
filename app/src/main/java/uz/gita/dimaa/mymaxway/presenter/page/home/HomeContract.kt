package uz.gita.dimaa.mymaxway.presenter.page.home

import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.domain.model.FoodData

interface HomeContract {
    sealed interface Intent {
        object Loading: Intent
        object OpenOrderScreen: Intent
        data class Search(val search: String): Intent
        data class SearchByCategory(val search: String): Intent
        data class Add(val food: FoodData, val count: Int): Intent
        data class Change(val foodEntity: FoodEntity, val count: Int): Intent
        data class Delete(val food: FoodData): Intent

    }

    data class UIState(
        val loading: Boolean = true,
        val isEmpty: Boolean = false,
        val foods: List<FoodData> = emptyList(),
        val categories: List<String> = emptyList(),
        val isRefreshing: Boolean = true,
        val isInternetAvailable: Boolean
    )

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    interface Direction {
        suspend fun goOrderScreen()
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }
}
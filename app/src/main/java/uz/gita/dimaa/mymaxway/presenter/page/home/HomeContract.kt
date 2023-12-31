package uz.gita.dimaa.mymaxway.presenter.page.home

import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import uz.gita.dimaa.mymaxway.domain.model.FoodData

interface HomeContract {
    sealed interface Intent {
        object Loading: Intent
        data class Search(val search: String): Intent
        data class Add(val food: FoodData, val count: Int): Intent
    }

    data class UIState(
        val foods: List<FoodData> = emptyList(),
        val categories: List<String> = emptyList()
    )

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    interface Direction {
        suspend fun goOrderPage()
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }
}
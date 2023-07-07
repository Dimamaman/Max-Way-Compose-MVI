package uz.gita.dimaa.mymaxway.presenter.page.home

import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import uz.gita.dimaa.mymaxway.data.model.Food

interface HomeContract {
    sealed interface Intent {
        object Loading: Intent
        data class Search(val search: String): Intent
        data class OpenDetailsDialog(val food: Food): Intent
    }

    data class UIState(
        val foods: List<Food> = emptyList(),
        val categories: List<String> = emptyList()
    )

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    interface Direction {
       suspend fun openDialog(food: Food)
    }
}
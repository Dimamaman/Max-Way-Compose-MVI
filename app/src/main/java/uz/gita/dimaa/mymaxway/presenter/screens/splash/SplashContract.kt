package uz.gita.dimaa.mymaxway.presenter.screens.splash

import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost

interface SplashContract {

    sealed interface Intent {
        object Loading: Intent
    }

    data class UIState(val isInternetAvailable: Boolean)

    sealed interface SideEffect {
        data class HasError(val message: String)
    }

    interface ViewModel: ContainerHost<UIState,SideEffect> {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }
}
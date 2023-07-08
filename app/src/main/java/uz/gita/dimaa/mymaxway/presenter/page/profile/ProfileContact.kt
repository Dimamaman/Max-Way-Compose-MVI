package uz.gita.dimaa.mymaxway.presenter.page.profile

import kotlinx.coroutines.flow.StateFlow

interface ProfileContact {
    sealed interface Intent {
        object Load : Intent
        object OpenEditProfileScreen : Intent
    }

    data class UiState(
        val name: String = "",
        val phoneNumber: String = "",
    )

    interface ViewModel {
        val uiState: StateFlow<UiState>
        fun onEventDispatcher(intent: Intent)
    }
    interface Directions{
        suspend fun openEditProfileScreen()
    }
}
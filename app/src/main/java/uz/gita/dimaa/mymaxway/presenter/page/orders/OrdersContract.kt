package uz.gita.dimaa.mymaxway.presenter.page.orders

import org.orbitmvi.orbit.ContainerHost
import uz.gita.dimaa.mymaxway.data.model.OrderData

interface OrdersContract {

    sealed interface Intent {
        object Loading: Intent
    }

    sealed interface UIState {
        object Loading: UIState
        data class Orders(val list: List<OrderData>): UIState
    }

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }
}
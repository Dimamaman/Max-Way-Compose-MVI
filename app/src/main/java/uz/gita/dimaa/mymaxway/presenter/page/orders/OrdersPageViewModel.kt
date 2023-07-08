package uz.gita.dimaa.mymaxway.presenter.page.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.dimaa.mymaxway.data.local.sharedPref.SharedPref
import uz.gita.dimaa.mymaxway.domain.usecase.HomeUseCase
import javax.inject.Inject

@HiltViewModel
class OrdersPageViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val sharedPref: SharedPref
) : ViewModel(), OrdersContract.ViewModel {

    override val container = container<OrdersContract.UIState, OrdersContract.SideEffect>(OrdersContract.UIState.Loading)

    init {
        onEventDispatcher(OrdersContract.Intent.Loading)
    }

    override fun onEventDispatcher(intent: OrdersContract.Intent) {
        when (intent) {

            is OrdersContract.Intent.Loading -> {
                homeUseCase.getOrderedFoods(sharedPref.phone).onEach {
                    it.onSuccess { list ->
                        intent {
                            reduce {
                                OrdersContract.UIState.Orders(list)
                            }
                        }
                    }

                    it.onFailure {

                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
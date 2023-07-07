package uz.gita.dimaa.mymaxway.presenter.page.orders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.dimaa.mymaxway.data.local.room.entity.FoodEntity
import uz.gita.dimaa.mymaxway.data.local.sharedPref.SharedPref
import uz.gita.dimaa.mymaxway.data.model.OrderData
import uz.gita.dimaa.mymaxway.domain.repository.roomrepository.RoomRepository
import uz.gita.dimaa.mymaxway.domain.usecase.HomeUseCase
import javax.inject.Inject

@HiltViewModel
class OrderPageViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val roomRepository: RoomRepository,
    private val sharedPref: SharedPref
) : ViewModel(), OrderContract.ViewModel {
    override val container =
        container<OrderContract.UIState, OrderContract.SideEffect>(OrderContract.UIState())
    override val uiState = MutableStateFlow(OrderContract.UIState())

    override fun onEventDispatcher(intent: OrderContract.Intent) {
        when (intent) {
            is OrderContract.Intent.Loading -> {
                homeUseCase.getFoodsFromRoom().onEach { foods ->
                    uiState.update {
                        it.copy(foods = foods)
                    }
                }.launchIn(viewModelScope)
            }

            is OrderContract.Intent.Change -> {
                homeUseCase.updateFood(intent.foodEntity, intent.count)
            }

            is OrderContract.Intent.Comment -> {
                val list = ArrayList<FoodEntity>()
                roomRepository.getFoods().onEach {
                    list.addAll(it)

                }.launchIn(viewModelScope)

                val orderData = OrderData(
                    list = list,
                    userId = sharedPref.phone,
                    comment = intent.message,
                    allPrice = intent.allPrice
                )

                homeUseCase.addOrders(orderData).onEach {
                    it.onSuccess { message ->
                        intent {
                            postSideEffect(OrderContract.SideEffect.HasError(message))
                        }
                        list.clear()
                        roomRepository.clearData().onEach {

                        }.launchIn(viewModelScope)
                    }

                    it.onFailure { message ->
                        intent {
                            postSideEffect(OrderContract.SideEffect.HasError(message.message!!))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
    private fun getAllPrice(it:List<FoodEntity>):Long{
        var allPrice = 0L
        it.forEach { foodEntity ->
            Log.d("JJJ","Price -> ${foodEntity.price}")
            allPrice += foodEntity.price
        }
        return allPrice
    }
}
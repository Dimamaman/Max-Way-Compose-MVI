package uz.gita.dimaa.mymaxway.presenter.screens.busket

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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
class BasketScreenViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val roomRepository: RoomRepository,
    private val sharedPref: SharedPref,
    private val direction: BasketContract.Direction
) : ViewModel(), BasketContract.ViewModel {
    override val container =
        container<BasketContract.UIState, BasketContract.SideEffect>(BasketContract.UIState())
    override val uiState = MutableStateFlow(BasketContract.UIState())

    override fun onEventDispatcher(intent: BasketContract.Intent) {
        when (intent) {
            is BasketContract.Intent.Loading -> {
                homeUseCase.getFoodsFromRoom().onEach { foods ->
                    uiState.update {
                        it.copy(foods = foods)
                    }
                }.launchIn(viewModelScope)
            }

            is BasketContract.Intent.Change -> {
                homeUseCase.updateFood(intent.foodEntity, intent.count)
            }

            is BasketContract.Intent.Comment -> {
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
                            postSideEffect(BasketContract.SideEffect.HasError(message))
                        }
                        list.clear()
                        roomRepository.clearData().onEach {

                        }.launchIn(viewModelScope)

                        direction.back()

                    }

                    it.onFailure { message ->
                        intent {
                            postSideEffect(BasketContract.SideEffect.HasError(message.message!!))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
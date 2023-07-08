package uz.gita.dimaa.mymaxway.presenter.page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.dimaa.mymaxway.data.local.sharedPref.SharedPref
import uz.gita.dimaa.mymaxway.domain.repository.roomrepository.RoomRepository
import uz.gita.dimaa.mymaxway.domain.usecase.HomeUseCase
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val direction: HomeContract.Direction,
    private val roomRepository: RoomRepository,
    private val sharedPref: SharedPref
) : HomeContract.ViewModel, ViewModel() {
    override val container =
        container<HomeContract.UIState, HomeContract.SideEffect>(HomeContract.UIState())

    override val uiState = MutableStateFlow(HomeContract.UIState())

    init {
        onEventDispatcher(HomeContract.Intent.Loading)
    }

    override fun onEventDispatcher(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.Loading -> {
                viewModelScope.launch {
                    homeUseCase.getFoods().onSuccess { list ->
                        uiState.update {
                            it.copy(foods = list)
                        }
                    }.onFailure {

                    }
                }

                homeUseCase.getCategories().onEach {
                    it.onSuccess { categoryName ->
                        uiState.update {
                            it.copy(categories = categoryName)
                        }
                    }
                    it.onFailure {

                    }
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.Search -> {
                if (intent.search.isEmpty()) {
                    onEventDispatcher(HomeContract.Intent.Loading)
                } else {
                    homeUseCase.searchFood(intent.search).debounce(300).onEach {
                        it.onSuccess { list ->
                            uiState.update {
                                it.copy(foods = list)
                            }
                        }

                        it.onFailure {

                        }
                    }.launchIn(viewModelScope)
                }
            }

            is HomeContract.Intent.Add -> {
                roomRepository.add(intent.food, intent.count)
            }

            is HomeContract.Intent.OpenOrderScreen -> {
                viewModelScope.launch {
                    delay(500L)
                    direction.goOrderScreen()
                }
            }

            is HomeContract.Intent.Change -> {
                roomRepository.updateFood(intent.foodEntity, intent.count)
            }

            is HomeContract.Intent.Delete -> {
                roomRepository.delete(intent.food.toEntity(0))
            }
        }
    }
}
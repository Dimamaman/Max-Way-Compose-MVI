package uz.gita.dimaa.mymaxway.presenter.page.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.dimaa.mymaxway.domain.usecase.HomeUseCase
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val direction: HomeDirection
): HomeContract.ViewModel, ViewModel(){
    override val container = container<HomeContract.UIState, HomeContract.SideEffect>(HomeContract.UIState())

    override val uiState = MutableStateFlow(HomeContract.UIState())

    init {
        onEventDispatcher(HomeContract.Intent.Loading)
    }

    override fun onEventDispatcher(intent: HomeContract.Intent) {
        when(intent) {
            is HomeContract.Intent.Loading -> {

                viewModelScope.launch {
                    homeUseCase.getFoods().onSuccess { list ->
                        uiState.update {
                            it.copy(foods = list)
                        }
                    }.onFailure {
                        Log.d("RRR","Error -> ${it.message}")
                    }
                }
                homeUseCase.getCategories().onEach {
                    it.onSuccess {  categoryName ->
                        Log.d("TTT","Category List Name -> $it")
                        uiState.update {
                            it.copy(categories = categoryName)
                        }
                    }
                    it.onFailure {
                        Log.d("TTT","Category List Name -> ${it.message}")
                    }
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.Search -> {

            }

            is HomeContract.Intent.Add -> {
                Log.d("LLL","Awqat -> ${intent.food}\n ${intent.count}")
                homeUseCase.add(intent.food, intent.count)

                viewModelScope.launch {
                    delay(1000L)
                    direction.goOrderPage()
                }
            }
        }
    }
}
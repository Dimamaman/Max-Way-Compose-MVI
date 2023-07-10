package uz.gita.dimaa.mymaxway.presenter.screens.splash

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.dimaa.mymaxway.data.local.sharedPref.SharedPref
import uz.gita.dimaa.mymaxway.presenter.screens.busket.BasketContract
import uz.gita.dimaa.mymaxway.util.hasConnection
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val direction: SplashScreenDirection,
    private val sharedPref: SharedPref
) : ViewModel(), SplashContract.ViewModel {

//    init {
//        findScreen()
//        onEventDispatcher(SplashContract.Intent.Loading)
//    }

    /*private fun findScreen() {
        if (hasConnection()) {

            if (sharedPref.hasToken) {
                Log.d(
                    "HHH", "Phone -> ${sharedPref.phone}\n" +
                            "Name -> ${sharedPref.name}\n" +
                            "Token -> ${sharedPref.hasToken}\n" +
                            "Sms Verification -> ${sharedPref.smsVerification}"
                )

                viewModelScope.launch {
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModelScope.launch {
                            direction.openMainScreen()
                        }
                    }, 2000)
                }
            } else {
                viewModelScope.launch {
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModelScope.launch {
                            direction.openLoginScreen()
                        }
                    }, 2000)
                }
            }
        } else {

        }
    }*/

    override val uiState = MutableStateFlow(SplashContract.UIState(false))

    override fun onEventDispatcher(intent: SplashContract.Intent) {
        when(intent) {
            is SplashContract.Intent.Loading -> {
                if (hasConnection()) {
                    uiState.update {
                        it.copy(isInternetAvailable = false)
                    }
                    if (sharedPref.hasToken) {
                        Log.d(
                            "HHH", "Phone -> ${sharedPref.phone}\n" +
                                    "Name -> ${sharedPref.name}\n" +
                                    "Token -> ${sharedPref.hasToken}\n" +
                                    "Sms Verification -> ${sharedPref.smsVerification}"
                        )

                        viewModelScope.launch {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModelScope.launch {
                                    direction.openMainScreen()
                                }
                            }, 2000)
                        }
                    } else {
                        viewModelScope.launch {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModelScope.launch {
                                    direction.openLoginScreen()
                                }
                            }, 2000)
                        }
                    }
                } else {
                    uiState.update {
                        it.copy(isInternetAvailable = true)
                    }
                }
            }
        }
    }

    override val container = container<SplashContract.UIState, SplashContract.SideEffect>(SplashContract.UIState(false))

}
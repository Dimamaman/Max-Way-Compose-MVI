package uz.gita.dimaa.mymaxway.presenter.screens.splash

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.dimaa.mymaxway.data.local.sharedPref.SharedPref
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val direction: SplashScreenDirection,
    private val sharedPref: SharedPref
) : ViewModel() {

    init {
        findScreen()
    }

    private fun findScreen() {
        if (sharedPref.hasToken) {
            Log.d("HHH","Phone -> ${sharedPref.phone}\n" +
                    "Name -> ${sharedPref.name}\n" +
                    "Token -> ${sharedPref.hasToken}\n" +
                    "Sms Verification -> ${sharedPref.smsVerification}")

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
    }
}
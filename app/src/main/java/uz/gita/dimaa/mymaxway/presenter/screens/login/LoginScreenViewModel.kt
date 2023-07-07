package uz.gita.dimaa.mymaxway.presenter.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.dimaa.mymaxway.data.locale.SharedPref
import uz.gita.dimaa.mymaxway.domain.repository.auth.AuthRepository
import uz.gita.dimaa.mymaxway.util.myLog
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val direction: LoginScreenContract.Direction,
    private val authRepository: AuthRepository,
    private val sharedPref: SharedPref

) : LoginScreenContract.ViewModel, ViewModel() {
    override val container =
        container<LoginScreenContract.UIState, LoginScreenContract.SideEffect>(LoginScreenContract.UIState.Loading)

    override fun onEventDispatcher(intent: LoginScreenContract.Intent) {
        when (intent) {
            is LoginScreenContract.Intent.Login -> {
                authRepository.createUserWithPhone(intent.phone, intent.activity).onEach {
                    it.onSuccess {
                        myLog("Sms code VIewModel -> $it")
                        direction.openVerifyScreen()
                        sharedPref.phone = intent.phone.trim()
                        sharedPref.name = intent.name.trim()
                    }

                    it.onFailure {
                        myLog("Sms code VIewModel -> $it")
                        intent {
                            postSideEffect(LoginScreenContract.SideEffect.HasError(it.message!!))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}

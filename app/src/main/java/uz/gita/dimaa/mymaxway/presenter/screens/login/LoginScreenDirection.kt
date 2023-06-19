package uz.gita.dimaa.mymaxway.presenter.screens.login

import uz.gita.dimaa.mymaxway.navigation.AppNavigator
import uz.gita.dimaa.mymaxway.presenter.screens.verify.VerifyScreen
import javax.inject.Inject

class LoginScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
): LoginScreenContract.Direction {
    override suspend fun openVerifyScreen() {
        appNavigator.navigateTo(VerifyScreen())
    }
}
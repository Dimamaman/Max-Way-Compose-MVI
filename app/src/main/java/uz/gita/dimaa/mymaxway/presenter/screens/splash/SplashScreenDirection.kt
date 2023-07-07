package uz.gita.dimaa.mymaxway.presenter.screens.splash

import uz.gita.dimaa.mymaxway.navigation.AppNavigator
import uz.gita.dimaa.mymaxway.presenter.screens.login.LoginScreen
import uz.gita.dimaa.mymaxway.presenter.screens.main.MainScreen
import javax.inject.Inject

class SplashScreenDirection @Inject constructor(private val appNavigator: AppNavigator): Direction {
    override suspend fun openMainScreen() {
        appNavigator.replace(MainScreen())
    }

    override suspend fun openLoginScreen() {
        appNavigator.replace(LoginScreen())
    }
}